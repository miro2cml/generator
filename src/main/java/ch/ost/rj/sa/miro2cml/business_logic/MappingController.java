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
        return new ByteArrayResource(mappingLog.toByteArray());
    }

    public String getLogPreview() {
        return mappingLog.toString();
    }

    public String getCmlPreview() {
        return cmlPreview;
    }

    public boolean startMappingProcess() {
        logger.debug("Get BoardData from data source");
        mappingLog.addInfoLogEntry("Get BoardData from data source (for more information, see Data Access Log Section down below.)");

        WidgetCollection widgetCollection = MiroApiServiceAdapter.getBoardWidgets(accessToken, boardId);
        InputBoard inputBoard = new InputBoard(boardId, widgetCollection.getWidgets());
        mappingLog.setDataAccessLog(widgetCollection.getDataAccessLog());
        if (widgetCollection.isSuccess()) {
            logger.debug("BoardData received");

            logger.debug("Commence Board mapping");
            mappingLog.addInfoLogEntry("Commence Board mapping");
            try{
                MappedBoard mappedBoard;
                InputBoard validatedBoard = InputValidation.validate(inputBoard);
                mappingLog.addInfoLogEntry("Input is validated. Maximum size of text has been set to 1200 characters.");
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
                        mappedBoard = new AutomaticBoardMapperService().mapBoard(validatedBoard, mappingLog, mappingMessages);
                    break;
                }
                mappingLog.addSectionSeparator();
                mappingLog.addInfoLogEntry("BoardMapping finished");

                addMetaDataToCml(mappedBoard.getCmlModel().getResource().getContextMappingModel());

                mappingLog.addInfoLogEntry("commence with cml serialization");
                serializeCml(mappedBoard);
                mappingLog.addInfoLogEntry("finished cml serialization");
                return true;
            }catch(WrongBoardException wrongBoardException){
                mappingMessages.add(wrongBoardException.getMessage());
                mappingMessages.add("Input Board doesn't match expected Board Format. Take a look at the section Supported Templates for more information.");
                mappingLog.addSectionSeparator();
                mappingLog.addErrorLogEntry("Input Board doesn't match expected Board Format. Take a look at the section Supported Templates for more information.");
                mappingLog.addErrorLogEntry("Error Message: " + wrongBoardException.getMessage());
                return false;
            } catch (CmlSerializerException cmlSerializerException) {
                mappingMessages.add("Critical ERROR during cml serialization");
                mappingMessages.add("Please take a look at the logfile for further information");
                mappingLog.addSectionSeparator();
                mappingLog.addErrorLogEntry("Critical ERROR during cml serialization");
                mappingLog.addErrorLogEntry("Error Message: " + cmlSerializerException.getMessage());
                return false;
            }
            catch (Exception e){
                mappingMessages.add("A critical ERROR occurred during Mapping");
                mappingMessages.add("Please take a look at the logfile for further information");
                mappingLog.addSectionSeparator();
                mappingLog.addErrorLogEntry("A critical ERROR occurred during Mapping");
                mappingLog.addErrorLogEntry("Error Message: " + e.getMessage());
                return false;
            }
        } else {
            mappingMessages.add("Critical ERROR during MiroApiCall");
            mappingMessages.add("Please take a look at the logfile for further information");
            mappingLog.addSectionSeparator();
        }
        return false;
    }

    private void serializeCml(MappedBoard mappedBoard) throws CmlSerializerException {
        try {
            resource = new ByteArrayResource(mappedBoard.getCmlModel().toByteArray());
            cmlPreview = new String(resource.getByteArray());
        } catch (Exception e){
            throw new CmlSerializerException(e.getMessage());
        }
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
                .append("---------------------------------------------------------------------------------------------------------------------------------------------------------").append(System.lineSeparator())
                .append("Converted MiroBoard: ").append(boardLink).append(System.lineSeparator())
                .append("Converted at: ").append(timestamp).append(System.lineSeparator())
                .append("Converted with Miro2CML Version: ").append(miro2cmlVersion).append(System.lineSeparator())
                .append("Generated CML for ContextMapper Version: ").append(contextMapperVersion).append(System.lineSeparator())
                .append("---------------------------------------------------------------------------------------------------------------------------------------------------------").append(System.lineSeparator());

        return stringBuilder.toString();
    }
}
