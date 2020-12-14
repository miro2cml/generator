package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.AutomaticBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.BoundedContextCanvasBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.EventStormingBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.UserStoryMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.model.*;
import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.CmlSerializerException;
import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.InvalidBoardFormatException;
import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.WrongBoardException;
import ch.ost.rj.sa.miro2cml.data_access.MiroApiServiceAdapter;
import ch.ost.rj.sa.miro2cml.data_access.model.WidgetCollection;
import org.contextmapper.dsl.contextMappingDSL.ContextMappingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class MappingController {
    private static final Logger logger = LoggerFactory.getLogger(MappingController.class);

    @Autowired
    MiroApiServiceAdapter miroApiServiceAdapter;

    @Autowired
    Environment environment;

    @Autowired
    BasicInputCorrector basicInputCorrector;


    public MappingResult startMappingProcess(BoardType boardType, String boardId, String accessToken) {

        MappingResult output = new MappingResult();
        MappingLog mappingLog = new MappingLog();
        MappingMessages mappingMessages = new MappingMessages();
        output.setMappingLog(mappingLog);
        output.setMappingMessages(mappingMessages);

        logger.debug("Get BoardData from data source");
        mappingLog.addInfoLogEntry("Get BoardData from data source (for more information, see Data Access Log Section down below.)");
        addMetaDataToLog(mappingLog,boardId);

        WidgetCollection widgetCollection = miroApiServiceAdapter.getBoardWidgets(accessToken, boardId);
        InputBoard inputBoard = new InputBoard(boardId, widgetCollection.getWidgets());
        mappingLog.setDataAccessLog(widgetCollection.getDataAccessLog());
        if(widgetCollection.getDataAccessLog().isMaxWidgetsCountExceeded()){
            mappingMessages.add("We received 1000 Widgets from Miro. This is the maximum we can receive per Board. Thus its possible, that we didn't receive all of your widgets. If you really need more than 1000 Widgets, please split them up and distribute them  on multiple boards.");
        }
        if (widgetCollection.isSuccess()) {
            logger.debug("BoardData received");

            logger.debug("Commence Board mapping");
            mappingLog.addInfoLogEntry("Commence Board mapping");
            try {
                MappedBoard mappedBoard;
                mappingLog.addInfoLogEntry("Start with basic input correction measures.");
                InputBoard validatedBoard = basicInputCorrector.prepareInput(inputBoard,mappingLog);
                mappingLog.addInfoLogEntry("Basic input correction measures have been performed.");

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

                addMetaDataToCml(mappedBoard.getCmlModel().getResource().getContextMappingModel(),boardId);

                mappingLog.addInfoLogEntry("commence with cml serialization");
                serializeCml(mappedBoard,output);
                mappingLog.addInfoLogEntry("finished cml serialization");
                if (mappingLog.getErrorCounter() != 0) {
                    mappingMessages.add(mappingLog.getErrorCounter() + " Error(s) occurred during mapping. Check the Logfile for further information.");
                }
                if (mappingLog.getWarningCounter() != 0) {
                    mappingMessages.add(mappingLog.getWarningCounter() + " Warning(s) occurred during mapping. Check the Logfile for further information.");
                }
                output.setSuccess(true);
                output.setPerfectSuccess(output.getMappingMessages().isPerfectMapping());
                return output;
            } catch (WrongBoardException wrongBoardException) {
                mappingMessages.add(wrongBoardException.getMessage());
                mappingMessages.add("Input Board doesn't match expected Board Format. Take a look at the section Supported Templates for more information.");
                mappingLog.addSectionSeparator();
                mappingLog.addErrorLogEntry("Input Board doesn't match expected Board Format. Take a look at the section Supported Templates for more information.");
                mappingLog.addErrorLogEntry("Error Message: " + wrongBoardException.getMessage());
                return output;
            } catch (InvalidBoardFormatException invalidBoardFormatException) {
                mappingMessages.add("A critical ERROR occurred during parsing of the InputBoard. (Invalid Board Format)");
                mappingMessages.add("Please take a look at the logfile for further information");
                mappingLog.addSectionSeparator();
                mappingLog.addErrorLogEntry("A critical ERROR occurred during parsing of the InputBoard. (Invalid Board Format)");
                mappingLog.addErrorLogEntry("Error Message: " + invalidBoardFormatException.getMessage());
                return output;
            } catch (CmlSerializerException cmlSerializerException) {
                mappingMessages.add("Critical ERROR during cml serialization");
                mappingMessages.add("Please take a look at the logfile for further information");
                mappingLog.addSectionSeparator();
                mappingLog.addErrorLogEntry("Critical ERROR during cml serialization");
                mappingLog.addErrorLogEntry("Error Message: " + cmlSerializerException.getMessage());
                return output;
            } catch (Exception e) {
                mappingMessages.add("A critical ERROR occurred during Mapping");
                mappingMessages.add("Please take a look at the logfile for further information");
                mappingLog.addSectionSeparator();
                mappingLog.addErrorLogEntry("A critical ERROR occurred during Mapping");
                mappingLog.addErrorLogEntry("Error Message: " + e.getMessage());
                return output;
            }
        } else {
            mappingMessages.add("Critical ERROR during MiroApiCall");
            mappingMessages.add("Please take a look at the logfile for further information");
            mappingLog.addSectionSeparator();
        }
        return output;
    }

    private void serializeCml(MappedBoard mappedBoard, MappingResult result) throws CmlSerializerException {
        try {
            ByteArrayResource resource = new ByteArrayResource(mappedBoard.getCmlModel().toByteArray());
            result.setCmlResource(resource);
            result.setCmlPreview(new String(resource.getByteArray()));
        } catch (Exception e) {
            throw new CmlSerializerException(e.getMessage());
        }
    }

    private void addMetaDataToCml(ContextMappingModel cml, String boardId) {
        String oldTopComment = cml.getTopComment();
        oldTopComment = oldTopComment == null ? "" : oldTopComment;
        oldTopComment = oldTopComment.replace("/*", "");
        oldTopComment = oldTopComment.replace("*/", "");
        StringBuilder topCommentWithMetaData = new StringBuilder()
                .append("/*").append(System.lineSeparator())
                .append(provideMetaDataString(boardId))
                .append(oldTopComment).append(System.lineSeparator())
                .append("*/");
        cml.setTopComment(topCommentWithMetaData.toString());
    }

    private void addMetaDataToLog(MappingLog mappingLog,String boardId) {
        mappingLog.setMetaData(provideMetaDataString(boardId));
    }

    private String provideMetaDataString(String boardId) {
        String boardLink = "https://miro.com/app/board/" + boardId;
        String timestamp = new Timestamp(new Date().getTime()).toString();
        String miro2cmlVersion = environment.getProperty("miro2cml.version");
        String contextMapperVersion = environment.getProperty("contextMapper.version");


        StringBuilder stringBuilder = new StringBuilder()
                .append("---------------------------------------------------------------------------------------------------------------------------------------------------------").append(System.lineSeparator())
                .append("Converted MiroBoard-ID: ").append(boardId).append(System.lineSeparator())
                .append("Converted MiroBoard-Link: ").append(boardLink).append(System.lineSeparator())
                .append("Converted at: ").append(timestamp).append(System.lineSeparator())
                .append("Converted with Miro2CML Version: ").append(miro2cmlVersion).append(System.lineSeparator())
                .append("Generated CML for ContextMapper Version: ").append(contextMapperVersion).append(System.lineSeparator())
                .append("---------------------------------------------------------------------------------------------------------------------------------------------------------").append(System.lineSeparator());

        return stringBuilder.toString();
    }
}
