package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.CmlModel;

public class EventStormingBoardMapperService implements IBoardMapperService {

    @Override
    public MappedBoard mapBoard(InputBoard inputBoard) {
        return null;
    }

    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard board) {
        return new CmlModel();
    }

}
