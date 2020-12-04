package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.model.boards.BoardType;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutomaticBoardMapperService implements IBoardMapperService {
    @Override
    public MappedBoard mapBoard(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException {
        MappedBoard mappedBoard = null;
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
            } catch (WrongBoardException ignored) {
            } finally {
                localMessages.clear();
                localLog.clear();
            }
        }
        if(successfulMappings.isEmpty()){
            throw new WrongBoardException("Input Board doesn't match with any of the supported Board Types.");
        } else if (successfulMappings.size() > 1){
            messages.add("Input Board can be interpreted as one of the following board types: " + successfulMappings.toString());
            messages.add("We used "+successfulMappings.get(0) + ", please explicitly specify another board type if needed.");
            mappingLog.addInfoLogEntry("We used "+successfulMappings.get(0) + ", please explicitly specify another board type if needed.");
            mappingLog.addSectionSeparator();
            mappedBoard = mappingSwitch(successfulMappings.get(0), inputBoard,mappingLog,messages);
        } else {
            messages.add("We detected, that your board is most likely a "+successfulMappings.get(0) + " board.");
            mappingLog.addInfoLogEntry("We detected, that your board is most likely a "+successfulMappings.get(0) + " board.");
            mappingLog.addSectionSeparator();
            mappedBoard = mappingSwitch(successfulMappings.get(0), inputBoard,mappingLog,messages);
        }
        return mappedBoard;
    }

    private MappedBoard mappingSwitch(BoardType boardType, InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException {
        MappedBoard mappedBoard= null;
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
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard board, MappingLog mappingLog, MappingMessages messages) {
        return new CmlModel();
    }
}
