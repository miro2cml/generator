package ch.ost.rj.sa.miro2cml.business_logic;

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

import java.util.List;

public class MappingController {
    private static final Logger logger = LoggerFactory.getLogger(MappingController.class);
    private final MappingLog mappingLog;
    BoardType boardType;
    String boardId;
    String accessToken;
    MappingMessages mappingMessages = new MappingMessages();
    private MappedBoard mappedBoard = null;
    private Resource resource = null;

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

    public Resource getServableOutput() {
        return resource;
    }

    public List<String> getMappingMessages() {
        return mappingMessages.getMessages();
    }

    public boolean isMappingFullSuccess() {
        return mappingMessages.isMappingState();
    }

    public Resource getServableMappingLog() {
        return new ByteArrayResource(mappingLog.toString().getBytes());
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
                switch (boardType) {
                    case USE_CASE:
                        logger.debug("Board Type: UserStory");
                        mappingLog.addInfoLogEntry("Board Type: UserStory");
                        mappedBoard = new UseCaseBoardMapperService().mapBoard(inputBoard, mappingLog, mappingMessages);
                        break;
                    case BOUNDED_CONTEXT_CANVAS:
                        logger.debug("Board Type: Bounded Context Canvas");
                        mappingLog.addInfoLogEntry("Board Type: Bounded Context Canvas");
                        mappedBoard = new BoundedContextCanvasBoardMapperService().mapBoard(inputBoard, mappingLog, mappingMessages);
                        break;
                    case EVENT_STORMING:
                        mappedBoard = new EventStormingBoardMapperService().mapBoard(inputBoard, mappingLog, mappingMessages);
                        break;
                    case CONTEXT_MAP:
                        break;
                    default:
                    case AUTOMATIC:
                        break;
                }
                mappingLog.addInfoLogEntry("BoardMapping finished");
                mappingLog.addInfoLogEntry("commence with cml serialization");
                resource = ByteArrayResourceGenerator.generateByteArrayResource(mappedBoard);
                mappingLog.addInfoLogEntry("finished cml serialization");
                return true;

            }catch(WrongBoardException e){
                mappingMessages.clear();
                mappingMessages.add(e.getMessage());
                mappingLog.addErrorLogEntry("Board doesn't match expected Input Board. Take a look at the section Supported Templates for more informations.");
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
