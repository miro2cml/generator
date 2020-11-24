package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.data_access.MiroApiServiceAdapter;
import ch.ost.rj.sa.miro2cml.model.boards.BoardPresentation;

import java.util.List;

public class BoardSelectionService {
    private String accessToken;
    private String teamId;

    public BoardSelectionService(String accessToken, String teamId) {
        this.accessToken = accessToken;
        this.teamId = teamId;
    }

    public List<BoardPresentation> run() {
        return MiroApiServiceAdapter.getMiroBoards("5832d294-d90a-40b2-a83b-9aa5232b1493", "3074457349657634050");
    }
}
