package ch.ost.rj.sa.miro2cml.business_logic.board_analyser_services;

import ch.ost.rj.sa.miro2cml.business_logic.cml_model.CMLModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.Board;
import ch.ost.rj.sa.miro2cml.business_logic.model.ConceptBoard;

public class BoundedContextCanvasBoardAnalyzerService implements IBoardAnalyzerService {

    @Override
    public ConceptBoard analyseBoard(Board board) {
        return null;
    }
    @Override
    public CMLModel analyseInput(ConceptBoard board){
        return new CMLModel(null);
    }
}
