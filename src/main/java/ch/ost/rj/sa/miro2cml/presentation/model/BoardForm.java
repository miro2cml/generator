package ch.ost.rj.sa.miro2cml.presentation.model;

import ch.ost.rj.sa.miro2cml.model.boards.BoardType;

public class BoardForm {
    private String boardId;
    private BoardType boardType;

    public BoardForm(String accessToken, String boardId, BoardType boardType, String module) {
        this.boardId = boardId;
        this.boardType = boardType;
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


}
