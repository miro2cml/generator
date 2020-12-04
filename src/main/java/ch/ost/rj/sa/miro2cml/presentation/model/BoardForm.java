package ch.ost.rj.sa.miro2cml.presentation.model;

import ch.ost.rj.sa.miro2cml.model.boards.BoardType;

public class BoardForm {
    private String boardId;
    private BoardType boardType;
    private String filter;

    public BoardForm(String boardId, BoardType boardType, String filter) {
        this.boardId = boardId;
        this.boardType = boardType;
        this.filter = filter;
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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
