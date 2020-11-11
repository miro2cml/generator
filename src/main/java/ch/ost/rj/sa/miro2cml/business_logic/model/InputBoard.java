package ch.ost.rj.sa.miro2cml.business_logic.model;

import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;

import java.util.List;

public class InputBoard {
    private String boardId;
    private List<WidgetObject> widgetObjects;

    public InputBoard(String boardId, List<WidgetObject> widgetObjects) {
        this.boardId = boardId;
        this.widgetObjects = widgetObjects;
        System.out.println("log: created " + this);
    }

    @Override
    public String toString() {
        return "InputBoard{" +
                "boardId='" + boardId + '\'' +
                ", widgetObjects=" + widgetObjects +
                '}';
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public List<WidgetObject> getWidgetObjects() {
        return widgetObjects;
    }

    public void setWidgetObjects(List<WidgetObject> widgetObjects) {
        this.widgetObjects = widgetObjects;
    }
}

