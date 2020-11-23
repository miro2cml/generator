package ch.ost.rj.sa.miro2cml.presentation.model;

import ch.ost.rj.sa.miro2cml.model.boards.BoardType;

public class BoardForm {
    private String boardId;
    private BoardType boardType;
    private String module;

    public BoardForm(String accessToken, String boardId, BoardType boardType, String module) {
        this.boardId = boardId;
        this.boardType = boardType;
        this.module = module;
    }

    public BoardForm() {
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
