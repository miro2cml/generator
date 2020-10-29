package ch.ost.rj.sa.miro2cml.presentation.model;

import ch.ost.rj.sa.miro2cml.model.BoardType;

public class GetBoardForm {
    private String accessToken;
    private String boardId;
    private BoardType boardType;
    private String module;

    public GetBoardForm(String accessToken, String boardId, BoardType boardType, String module) {
        this.accessToken = accessToken;
        this.boardId = boardId;
        this.boardType = boardType;
        this.module =module;
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
