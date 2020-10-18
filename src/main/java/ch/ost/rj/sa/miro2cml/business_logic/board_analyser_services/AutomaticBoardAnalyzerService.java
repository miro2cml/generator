package ch.ost.rj.sa.miro2cml.business_logic.board_analyser_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.Board;
import ch.ost.rj.sa.miro2cml.business_logic.model.ConceptBoard;

public class AutomaticBoardAnalyzerService implements IBoardAnalyzerService {
    @Override
    public ConceptBoard analyseBoard(Board board) {
        ConceptBoard conceptBoard = null;
        //TODO: inspect board -> decide which analyzer should be called. (currently default in UseCaseBoardAnalyzer)
        //Switch (based on board inspect)
        //case(UseCase):
        conceptBoard = new UseCaseBoardAnalyzerService().analyseBoard(board);
        //case (other cases)....:
        //..
        //case default: error: could not recognize boardType automatically.

        return conceptBoard;
    }
}
