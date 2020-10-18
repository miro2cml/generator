package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.board_analyser_services.UseCaseBoardAnalyzerService;
import ch.ost.rj.sa.miro2cml.business_logic.cml_generator.UseCaseCmlGenerator;
import ch.ost.rj.sa.miro2cml.business_logic.model.ConceptBoard;
import ch.ost.rj.sa.miro2cml.model.BoardType;
import org.springframework.core.io.ByteArrayResource;

public class BusinessLogicController {
    BoardType boardType;
    String boardId;
    String accessToken;
    ByteArrayResource cml = null;

    public ByteArrayResource getCml() {
        return cml;
    }

    public BusinessLogicController(BoardType boardType, String boardId, String accessToken) {
        this.boardType = boardType;
        this.boardId = boardId;
        this.accessToken = accessToken;
    }

    public void run() {

        switch (boardType) {
            case USE_CASE:
                ConceptBoard useCaseBoard = new UseCaseBoardAnalyzerService().analyseBoard(accessToken,boardId);
                cml = new UseCaseCmlGenerator().generateCmlByteArrayResource(useCaseBoard);
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
