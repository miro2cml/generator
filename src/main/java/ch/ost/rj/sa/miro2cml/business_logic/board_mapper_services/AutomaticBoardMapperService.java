package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.InvalidBoardFormatException;
import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.BoardType;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutomaticBoardMapperService implements IBoardMapperService {
    @Override
    public MappedBoard mapBoard(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException, InvalidBoardFormatException {
        mappingLog.addInfoLogEntry("Selected Board Type: EducatedGuess");
        mappingLog.addInfoLogEntry("Commence with BoardType deducting");
        MappedBoard mappedBoard;
        MappingLog localLog = new MappingLog();
        MappingMessages localMessages = new MappingMessages();
        ArrayList<BoardType> successfulMappings = new ArrayList<>();
        List<BoardType> boardTypes = Arrays.asList(BoardType.BoundedContextCanvas, BoardType.EventStorming, BoardType.UserStory);
        for (BoardType specificBoardType : boardTypes) {
            InputBoard localInputBoard = SerializationUtils.clone(inputBoard);
            try {
                localLog.addInfoLogEntry(specificBoardType.toString());
                mappingSwitch(specificBoardType, localInputBoard,localLog,localMessages);
                successfulMappings.add(specificBoardType);
                mappingLog.addInfoLogEntry("Board might be a " + specificBoardType + ". Add BoardType " + specificBoardType + " to list of BoardType candidates.");
            } catch (WrongBoardException ignored) {
                mappingLog.addInfoLogEntry("Board is not of type: " + specificBoardType);
            } catch (InvalidBoardFormatException ignored){
                mappingLog.addWarningLogEntry("Board seems to have a format issue if interpreted as "+ specificBoardType + ", ignoring the issue for now.");
                successfulMappings.add(specificBoardType);
            } finally {
                localMessages.clear();
                localLog.clear();
            }
        }
        if(successfulMappings.isEmpty()){
            throw new WrongBoardException("Input Board doesn't match with any of the supported Board Types.");
        } else if (successfulMappings.size() > 1){
            messages.add("Input Board can be interpreted as one of the following Board Types: " + successfulMappings.toString());
            BoardType selectedBoardType = successfulMappings.get(0);
            messages.add("We used "+selectedBoardType + ", please explicitly specify another board type if needed.");
            mappingLog.addInfoLogEntry("We used "+selectedBoardType + ", please explicitly specify another Board Type if needed.");
            mappingLog.addSectionSeparator();
            mappedBoard = mappingSwitch(selectedBoardType, inputBoard,mappingLog,messages);
        } else {
            BoardType detectedBoardType = successfulMappings.get(0);
            messages.add("We detected that your board is most likely a(n) "+detectedBoardType + " Board.");
            mappingLog.addInfoLogEntry("We detected that your board is most likely a(n) "+detectedBoardType + " Board.");
            mappingLog.addSectionSeparator();
            mappedBoard = mappingSwitch(detectedBoardType, inputBoard,mappingLog,messages);
        }
        return mappedBoard;
    }

    private MappedBoard mappingSwitch(BoardType boardType, InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException, InvalidBoardFormatException {
        MappedBoard mappedBoard;
        switch (boardType) {
            case UserStory:
                mappedBoard = new UserStoryMapperService().mapBoard(inputBoard, mappingLog, messages);
                break;
            case EventStorming:
                mappedBoard = new EventStormingBoardMapperService().mapBoard(inputBoard, mappingLog, messages);
                break;
            default:
            case BoundedContextCanvas:
                mappedBoard = new BoundedContextCanvasBoardMapperService().mapBoard(inputBoard, mappingLog, messages);
                break;
        }
        return mappedBoard;
    }

    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard board, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException {
        throw new WrongBoardException("Invalid Access to mapping Chain");
    }
}
