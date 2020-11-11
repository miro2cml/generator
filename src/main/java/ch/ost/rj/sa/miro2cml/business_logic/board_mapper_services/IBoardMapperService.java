package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.IOutputArtifact;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.CmlModel;

public interface IBoardMapperService {

    default MappedBoard mapBoard(InputBoard inputBoard) {
        IOutputArtifact cmlModel = mapWidgetObjectsToCmlArtifacts(inputBoard);
        return new MappedBoard(inputBoard, cmlModel);
    }

    CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard inputBoard);

}
