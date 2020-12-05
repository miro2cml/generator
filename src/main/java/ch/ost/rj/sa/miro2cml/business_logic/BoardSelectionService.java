package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.data_access.MiroApiServiceAdapter;
import ch.ost.rj.sa.miro2cml.model.boards.BoardPresentation;

import java.util.List;

public class BoardSelectionService {
    private final String accessToken;
    private final String teamId;

    public BoardSelectionService(String accessToken, String teamId) {
        this.accessToken = accessToken;
        this.teamId = teamId;
    }

    public List<BoardPresentation> run() {
        return MiroApiServiceAdapter.getMiroBoards(accessToken, teamId);
    }
}
