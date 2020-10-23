package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.UseCaseBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MiroBoard;
import ch.ost.rj.sa.miro2cml.data_access.MiroApiConsumer;
import ch.ost.rj.sa.miro2cml.model.BoardType;
import org.springframework.core.io.ByteArrayResource;

import static ch.ost.rj.sa.miro2cml.business_logic.CmlByteArrayResourceGenerator.generateCmlByteArrayResource;

public class BusinessLogicController {
    BoardType boardType;
    String boardId;
    String accessToken;
    ByteArrayResource cmlByteArrayRessource = null;

    public BusinessLogicController(BoardType boardType, String boardId, String accessToken) {
        this.boardType = boardType;
        this.boardId = boardId;
        this.accessToken = accessToken;
    }

    public ByteArrayResource getCmlByteArrayRessource() {
        return cmlByteArrayRessource;
    }

    public void run() {
        MiroBoard miroBoard = new MiroBoard(boardId, MiroApiConsumer.getBoardWidgets(accessToken, boardId));
        switch (boardType) {
            case USE_CASE:
                MappedBoard mappedUseCaseBoard = new UseCaseBoardMapperService().mapBoard(miroBoard);
                cmlByteArrayRessource = generateCmlByteArrayResource(mappedUseCaseBoard);

                break;
            case BOUNDED_CONTEXT_CANVAS:
                break;

            case EVENT_STORMING:
                break;
            case CONTEXT_MAP:
                break;
            default:
            case AUTOMATIC:
                break;
        }


    }
}
