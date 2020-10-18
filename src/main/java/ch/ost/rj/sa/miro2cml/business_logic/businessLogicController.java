package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.board_analyser_services.UseCaseBoardAnalyzerService;
import ch.ost.rj.sa.miro2cml.business_logic.cml_generator.UseCaseCmlGenerator;
import ch.ost.rj.sa.miro2cml.business_logic.model.ConceptBoard;
import ch.ost.rj.sa.miro2cml.model.BoardType;

import java.io.File;

public class businessLogicController {
    BoardType boardType;
    String boardId;
    String accessToken;

    public void run() {
        File cml = null;
        switch (boardType) {
            case USE_CASE:
                ConceptBoard useCaseBoard = new UseCaseBoardAnalyzerService().analyseBoard(accessToken,boardId);
                cml = new UseCaseCmlGenerator().generateCmlFile(useCaseBoard);
                break;
            case BOUNDED_CONTEXT_CANVAS:
                break;

            case CONTEXT_MAP:
                break;
            default:
            case AUTOMATIC:
                break;
        }


    }
}
