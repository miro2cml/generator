package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.UseCaseBoardMapperService;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.data_access.MiroApiServiceAdapter;
import ch.ost.rj.sa.miro2cml.model.boards.BoardType;
import org.springframework.core.io.Resource;

import static ch.ost.rj.sa.miro2cml.business_logic.ByteArrayResourceGenerator.generateByteArrayResource;

public class MappingController {
    BoardType boardType;
    String boardId;
    String accessToken;
    Resource resource = null;

    public MappingController(BoardType boardType, String boardId, String accessToken) {
        this.boardType = boardType;
        this.boardId = boardId;
        this.accessToken = accessToken;
        System.out.println("log: created " + this);
    }

    @Override
    public String toString() {
        return "MappingController{" +
                "boardType=" + boardType +
                ", boardId='" + boardId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", ressource=" + resource +
                '}';
    }

    public Resource getResource() {
        return resource;
    }

    public void run() {
        InputBoard inputBoard = new InputBoard(boardId, MiroApiServiceAdapter.getBoardWidgets(accessToken, boardId));
        switch (boardType) {
            case USE_CASE:
                MappedBoard mappedUseCaseBoard = new UseCaseBoardMapperService().mapBoard(inputBoard);
                resource = generateByteArrayResource(mappedUseCaseBoard);

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
