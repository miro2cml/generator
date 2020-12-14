package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.InvalidBoardFormatException;
import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;

public interface IBoardMapperService {

    default MappedBoard mapBoard(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException, InvalidBoardFormatException {
        CmlModel cmlModel = mapWidgetObjectsToCmlArtifacts(inputBoard, mappingLog, messages);
        return new MappedBoard(inputBoard, cmlModel);
    }

    CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException, InvalidBoardFormatException;

}
