package ch.ost.rj.sa.miro2cml.presentation.model;

import ch.ost.rj.sa.miro2cml.model.BoardType;

public class GetBoardForm {
    private String accessToken;
    private String boardId;
    private BoardType boardType;

    public GetBoardForm(String accessToken, String boardId, BoardType boardType) {
        this.accessToken = accessToken;
        this.boardId = boardId;
        this.boardType = boardType;
    }

    public GetBoardForm() {
    }

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

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }
}
