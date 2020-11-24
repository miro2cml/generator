package ch.ost.rj.sa.miro2cml.model.boards;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.boards.Data;

public class BoardPresentation {
    String boardId;
    String name;
    String description;
    String boardLink;

    public BoardPresentation(String boardId, String name, String description, String boardLink) {
        this.boardId = boardId;
        this.name = name;
        this.description = description;
        this.boardLink = boardLink;
    }

    public BoardPresentation(Data data) {
        this.boardId = data.getId();
        this.name = data.getName();
        this.description = data.getDescription();
        this.boardLink = data.getViewLink();
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBoardLink() {
        return boardLink;
    }

    public void setBoardLink(String boardLink) {
        this.boardLink = boardLink;
    }
}
