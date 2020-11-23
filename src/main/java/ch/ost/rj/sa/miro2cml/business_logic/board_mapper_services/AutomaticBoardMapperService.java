package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;

public class AutomaticBoardMapperService implements IBoardMapperService {
    @Override
    public MappedBoard mapBoard(InputBoard inputBoard) {
        MappedBoard mappedBoard = null;
        //TODO: inspect inputBoard -> decide which analyzer should be called. (currently default in UseCaseBoardAnalyzer)
        //Switch (based on inputBoard inspect)
        //case(UseCase):
        mappedBoard = new UseCaseBoardMapperService().mapBoard(inputBoard);
        //case (other cases)....:
        //..
        //case default: error: could not recognize boardType automatically.

        return mappedBoard;
    }

    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard board) {
        return new CmlModel();
    }
}
