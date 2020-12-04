package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.data_access.MiroApiServiceAdapter;
import ch.ost.rj.sa.miro2cml.model.boards.BoardPresentation;

import java.util.List;

public class GetPotentialBoardsController {
    private final String accessToken;
    private final String teamId;

    public GetPotentialBoardsController(String accessToken, String teamId) {
        this.accessToken = accessToken;
        this.teamId = teamId;
    }

    private List<BoardPresentation> retrivePossibleBoards() {
        return MiroApiServiceAdapter.getMiroBoards(accessToken, teamId);
    }
    public static List<BoardPresentation> getPossibleBoards(String accessToken, String teamId){
        GetPotentialBoardsController controller = new GetPotentialBoardsController(accessToken,teamId);
        return controller.retrivePossibleBoards();
    }
}
