package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.AutomaticBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.BoundedContextCanvasBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.EventStormingBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.UserStoryMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.data_access.MiroApiServiceAdapter;
import ch.ost.rj.sa.miro2cml.data_access.model.WidgetCollection;
import ch.ost.rj.sa.miro2cml.model.boards.BoardType;
import org.contextmapper.dsl.contextMappingDSL.ContextMappingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class MappingController {
    private static final Logger logger = LoggerFactory.getLogger(MappingController.class);
    private final MappingLog mappingLog;
    private final BoardType boardType;
    private final String boardId;
    private final String accessToken;
    private final MappingMessages mappingMessages = new MappingMessages();
    private ByteArrayResource resource = null;
    private String cmlPreview = null;

    public MappingController(BoardType boardType, String boardId, String accessToken) {
        this.boardType = boardType;
        this.boardId = boardId;
        this.accessToken = accessToken;
        this.mappingLog = new MappingLog();
        addMetaDataToLog();
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
        mappingLog.addSectionSeparator();
        if (widgetCollection.isSuccess()) {
            logger.debug("BoardData received");
            mappingLog.addInfoLogEntry("BoardData received");

            logger.debug("Commence Board mapping");
            mappingLog.addInfoLogEntry("Commence Board mapping");
            try{
                MappedBoard mappedBoard = null;
                InputBoard validatedBoard = InputValidation.validate(inputBoard);
                mappingLog.addInfoLogEntry("Input is validated. Maximum size of text is 1200 characters.");
                mappingLog.addSectionSeparator();
                switch (boardType) {
                case UserStory:
                        mappedBoard = new UserStoryMapperService().mapBoard(validatedBoard, mappingLog, mappingMessages);
                        break;
                case BoundedContextCanvas:
                        mappedBoard = new BoundedContextCanvasBoardMapperService().mapBoard(validatedBoard, mappingLog, mappingMessages);
                        break;
                case EventStorming:
                        mappedBoard = new EventStormingBoardMapperService().mapBoard(validatedBoard, mappingLog, mappingMessages);
                        break;
                default:
                case EducatedGuess:
                        mappingLog.addInfoLogEntry("Board Type: EducatedGuess");
                        mappedBoard = new AutomaticBoardMapperService().mapBoard(validatedBoard, mappingLog, mappingMessages);
                    break;
                }
                mappingLog.addSectionSeparator();
                mappingLog.addInfoLogEntry("BoardMapping finished");

                addMetaDataToCml(mappedBoard.getCmlModel().getResource().getContextMappingModel());

                mappingLog.addInfoLogEntry("commence with cml serialization");
                resource = new ByteArrayResource(mappedBoard.getCmlModel().toByteArray());
                mappingLog.addInfoLogEntry("finished cml serialization");
                cmlPreview = new String(resource.getByteArray());
                return true;
            }catch(WrongBoardException e){
                mappingMessages.add(e.getMessage());
                mappingMessages.add("Input Board doesn't match expected Board Format. Take a look at the section Supported Templates for more information.");
                mappingLog.addSectionSeparator();
                mappingLog.addErrorLogEntry("Input Board doesn't match expected Board Format. Take a look at the section Supported Templates for more information.");
                return false;
            } catch (Exception e){
                mappingLog.addSectionSeparator();
                mappingLog.addErrorLogEntry("Critical ERROR during cml serialization");
                mappingMessages.add("Critical ERROR during cml serialization");
                mappingMessages.add("Please take a look at the logfile for further information");
                return false;
            }
        } else {
            mappingMessages.add("Critical ERROR during MiroApiCall");
            mappingMessages.add("Please take a look at the logfile for further information");
            mappingLog.addSectionSeparator();
        }
        return false;
    }

    private void addMetaDataToCml(ContextMappingModel cml) {
        String oldTopComment = cml.getTopComment();
        oldTopComment = oldTopComment == null ? "" : oldTopComment;
        oldTopComment = oldTopComment.replace("/*", "");
        oldTopComment = oldTopComment.replace("*/", "");
        StringBuilder topCommentWithMetaData = new StringBuilder()
                .append("/*").append(System.lineSeparator())
                .append(provideMetaDataString())
                .append(oldTopComment).append(System.lineSeparator())
                .append("*/");
        cml.setTopComment(topCommentWithMetaData.toString());
    }

    private void addMetaDataToLog() {
        mappingLog.setMetaData(provideMetaDataString());
    }

    private String provideMetaDataString() {
        String boardLink = "https://miro.com/app/board/" + boardId;
        String timestamp = new Timestamp(new Date().getTime()).toString();
        String miro2cmlVersion = "0.0.1"; //ToDo: automate this or at least externalize it into a properties.file
        String contextMapperVersion = "6.1.1-SNAPSHOT";  //ToDo: automate this or at least externalize it into a properties.file

        StringBuilder stringBuilder = new StringBuilder()
                .append("---------------------------------------------------------------------").append(System.lineSeparator())
                .append("Converted MiroBoard: ").append(boardLink).append(System.lineSeparator())
                .append("Converted at: ").append(timestamp).append(System.lineSeparator())
                .append("Converted with Miro2CML Version: ").append(miro2cmlVersion).append(System.lineSeparator())
                .append("Generated CML for ContextMapper Version: ").append(contextMapperVersion).append(System.lineSeparator())
                .append("---------------------------------------------------------------------").append(System.lineSeparator());

        return stringBuilder.toString();
    }
}
