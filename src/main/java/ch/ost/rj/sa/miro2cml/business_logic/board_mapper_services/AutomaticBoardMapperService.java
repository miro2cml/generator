package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.model.boards.BoardType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AutomaticBoardMapperService implements IBoardMapperService {
    @Override
    public MappedBoard mapBoard(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException {
        MappedBoard mappedBoard = null;
        ArrayList<BoardType> succesfullMappings = new ArrayList<>();
        List<BoardType> boardTypes = Arrays.asList(BoardType.UserStory, BoardType.BoundedContextCanvas, BoardType.EventStorming);
        for (BoardType specificBoardType : boardTypes) {
            try {
                mappingSwitch(specificBoardType, inputBoard,mappingLog,messages);
                succesfullMappings.add(specificBoardType);
            } catch (WrongBoardException ignored) {
            } finally {
                messages.clear();
            }
        }
        if(succesfullMappings.isEmpty()){
            throw new WrongBoardException("Input Board doesn't match with any of the supported Board Types.");
        } else if (succesfullMappings.size() > 1){
            messages.add("Input Board can be interpreted as one of the following board types: " + succesfullMappings.toString());
            messages.add("We used "+succesfullMappings.get(0) + ", please explicitly specify another board type if needed.");
            mappingLog.addInfoLogEntry("We used "+succesfullMappings.get(0) + ", please explicitly specify another board type if needed.");
            mappedBoard = mappingSwitch(succesfullMappings.get(0), inputBoard,mappingLog,messages);
        } else {
            messages.add("We detected, that your board is most likely a "+succesfullMappings.get(0) + " board.");
            mappingLog.addInfoLogEntry("We detected, that your board is most likely a "+succesfullMappings.get(0) + " board.");
            mappedBoard = mappingSwitch(succesfullMappings.get(0), inputBoard,mappingLog,messages);
        }
        return mappedBoard;
    }

    private MappedBoard mappingSwitch(BoardType boardType, InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException {
        MappedBoard mappedBoard= null;
        switch (boardType) {
            case UserStory:
                mappedBoard = new UseCaseBoardMapperService().mapBoard(inputBoard, mappingLog, messages);
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
