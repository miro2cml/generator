package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MiroBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.CmlModel;

public class ContextMapBoardMapperService implements IBoardMapperService {
    @Override
    public MappedBoard mapBoard(MiroBoard miroBoard) {
        return null;
    }

    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(MiroBoard board) {
        return new CmlModel();
    }
}
