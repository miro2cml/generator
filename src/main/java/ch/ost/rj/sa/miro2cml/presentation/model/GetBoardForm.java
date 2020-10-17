package ch.ost.rj.sa.miro2cml.presentation.model;

public class GetBoardForm {
    private String accessToken;
    private String boardId;
    private String boardType;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public GetBoardForm(String accessToken, String boardId, String boardType) {
        this.accessToken = accessToken;
        this.boardId = boardId;
        this.boardType = boardType;
    }

    public GetBoardForm() {
    }
}
