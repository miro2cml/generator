package ch.ost.rj.sa.miro2cml.business_logic.model;

import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.BoardMetaData;

public class BoardRepresentation {
    String boardId;
    String name;
    String description;
    String boardLink;

    public BoardRepresentation(BoardMetaData boardMetaData) {
        boardId = boardMetaData.getBoardId();
        name = boardMetaData.getName();
        description = boardMetaData.getDescription();
        boardLink = boardMetaData.getBoardLink();
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