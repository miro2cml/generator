package ch.ost.rj.sa.miro2cml.business_logic.board_analyser_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.Board;
import ch.ost.rj.sa.miro2cml.business_logic.model.ConceptBoard;

public class UseCaseBoardAnalyzerService implements IBoardAnalyzerService {
    //TODO: Analyse
    @Override
    public ConceptBoard analyseBoard(Board board) {
        return new ConceptBoard(board);
    }
}
