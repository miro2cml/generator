package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MiroBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.CmlModel;

public class AutomaticBoardMapperService implements IBoardMapperService {
    @Override
    public MappedBoard mapBoard(MiroBoard miroBoard) {
        MappedBoard mappedBoard = null;
        //TODO: inspect miroBoard -> decide which analyzer should be called. (currently default in UseCaseBoardAnalyzer)
        //Switch (based on miroBoard inspect)
        //case(UseCase):
        mappedBoard = new UseCaseBoardMapperService().mapBoard(miroBoard);
        //case (other cases)....:
        //..
        //case default: error: could not recognize boardType automatically.

        return mappedBoard;
    }

    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(MiroBoard board) {
        return new CmlModel();
    }
}
