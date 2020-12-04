package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.AutomaticBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.BoundedContextCanvasBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.EventStormingBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.UseCaseBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.data_access.MiroApiServiceAdapter;
import ch.ost.rj.sa.miro2cml.data_access.model.WidgetCollection;
import ch.ost.rj.sa.miro2cml.model.boards.BoardType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MappingController {
    private static final Logger logger = LoggerFactory.getLogger(MappingController.class);
    private final MappingLog mappingLog;
    private final BoardType boardType;
    private final String boardId;
    private final String accessToken;
    private final MappingMessages mappingMessages = new MappingMessages();
    private MappedBoard mappedBoard = null;
    private ByteArrayResource resource = null;
    private String cmlPreview = null;

    public MappingController(BoardType boardType, String boardId, String accessToken) {
        this.boardType = boardType;
        this.boardId = boardId;
        this.accessToken = accessToken;
        this.mappingLog = new MappingLog(boardId);
    }

    @Override
    public String toString() {
        return "MappingController{" +
                "boardType=" + boardType +
                ", boardId='" + boardId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }

    public ByteArrayResource getServableOutput() {
        return resource;
    }

    public List<String> getMappingMessages() {
        return mappingMessages.getMessages();
    }

    public boolean isMappingFullSuccess() {
        return mappingMessages.isMappingState();
    }

    public ByteArrayResource getServableMappingLog() {
        return new ByteArrayResource(mappingLog.toString().getBytes());
    }

    public String getLogPreview() {
        return mappingLog.toString();
    }

    public String getCmlPreview() {
        return cmlPreview;
    }

    public boolean startMappingProcess() {
        logger.debug("Get BoardData from data source");
        mappingLog.addInfoLogEntry("Get BoardData from data source");
        WidgetCollection widgetCollection = MiroApiServiceAdapter.getBoardWidgets(accessToken, boardId);
        InputBoard inputBoard = new InputBoard(boardId, widgetCollection.getWidgets());
        mappingLog.addDataAccessLogEntries(widgetCollection.getDataAccessLog());

        if (widgetCollection.isSuccess()) {
            logger.debug("BoardData received");
            mappingLog.addInfoLogEntry("BoardData received");

            logger.debug("Commence Board mapping");
            mappingLog.addInfoLogEntry("Commence Board mapping");
            try{
                InputBoard validatedBoard = InputValidation.validate(inputBoard);
                mappingLog.addInfoLogEntry("Input is validated. Maximum size of text is 1200 characters.");

                switch (boardType) {
                case UserStory:
                        logger.debug("Board Type: UserStory");
                        mappingLog.addInfoLogEntry("Board Type: UserStory");
                        mappedBoard = new UseCaseBoardMapperService().mapBoard(validatedBoard, mappingLog, mappingMessages);
                        break;
                case BoundedContextCanvas:
                        logger.debug("Board Type: Bounded Context Canvas");
                        mappingLog.addInfoLogEntry("Board Type: Bounded Context Canvas");
                        mappedBoard = new BoundedContextCanvasBoardMapperService().mapBoard(validatedBoard, mappingLog, mappingMessages);
                        break;
                case EventStorming:
                        mappingLog.addInfoLogEntry("Board Type: Event Storming");
                        mappedBoard = new EventStormingBoardMapperService().mapBoard(validatedBoard, mappingLog, mappingMessages);
                        break;

                default:
                case EducatedGuess:
                        mappingLog.addInfoLogEntry("Board Type: EducatedGuess");
                        mappedBoard = new AutomaticBoardMapperService().mapBoard(validatedBoard, mappingLog, mappingMessages);
                    break;
                }
                mappingLog.addInfoLogEntry("BoardMapping finished");
                mappingLog.addInfoLogEntry("commence with cml serialization");
                resource = new ByteArrayResource(mappedBoard.getCmlModel().toByteArray());
                mappingLog.addInfoLogEntry("finished cml serialization");
                cmlPreview = new String(resource.getByteArray());
                return true;
            }catch(WrongBoardException e){
                mappingMessages.add(e.getMessage());
                mappingMessages.add("Input Board doesn't match expected Board Format. Take a look at the section Supported Templates for more information.");
                mappingLog.addErrorLogEntry("Input Board doesn't match expected Board Format. Take a look at the section Supported Templates for more information.");
                return false;
            } catch (Exception e){
                mappingLog.addErrorLogEntry("Critical ERROR during cml serialization");
                mappingMessages.add("Critical ERROR during cml serialization");
                mappingMessages.add("Please take a look at the logfile for further information");
                return false;
            }
        } else {
            mappingMessages.add("Critical ERROR during MiroApiCall");
            mappingMessages.add("Please take a look at the logfile for further information");
        }
        return false;
    }

}
