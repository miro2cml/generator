package ch.ost.rj.sa.miro2cml.business_logic.model;

import ch.ost.rj.sa.miro2cml.data_access.miro_model.pojo.WidgetObject;

import java.util.ArrayList;

public class Board {
    private String boardId;
    private ArrayList<WidgetObject> widgetObjects;

    public Board(String boardId, ArrayList<WidgetObject> widgetObjects) {
        this.boardId = boardId;
        this.widgetObjects = widgetObjects;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public ArrayList<WidgetObject> getWidgetObjects() {
        return widgetObjects;
    }

    public void setWidgetObjects(ArrayList<WidgetObject> widgetObjects) {
        this.widgetObjects = widgetObjects;
    }
}

