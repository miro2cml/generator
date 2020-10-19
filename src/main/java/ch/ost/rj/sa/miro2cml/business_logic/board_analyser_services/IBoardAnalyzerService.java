package ch.ost.rj.sa.miro2cml.business_logic.board_analyser_services;

import ch.ost.rj.sa.miro2cml.business_logic.cml_model.CMLModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.Board;
import ch.ost.rj.sa.miro2cml.business_logic.model.ConceptBoard;
import ch.ost.rj.sa.miro2cml.data_access.MiroApiConsumer;

public interface IBoardAnalyzerService {
    ConceptBoard analyseBoard(Board board);
    default ConceptBoard analyseBoard(String accessToken, String boardId){
        Board board = new Board(boardId, MiroApiConsumer.getBoardWidgets(accessToken,boardId));
        return analyseBoard(board);
    }
    CMLModel analyseInput(ConceptBoard board);
    default CMLModel analyseInput(String accessToken, String boardId){
        Board board = new Board(boardId, MiroApiConsumer.getBoardWidgets(accessToken,boardId));
        return new CMLModel(null);
    }

}
