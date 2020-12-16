package ch.ost.rj.sa.miro2cml.data_access.model.miro2cml;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.boards.Data;

public class BoardMetaData {
    String boardId;
    String name;
    String description;
    String boardLink;

    public BoardMetaData (Data data){
        boardId = data.getId();
        name = data.getName();
        description = data.getDescription();
        boardLink = data.getViewLink();
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
