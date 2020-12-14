package ch.ost.rj.sa.miro2cml.business_logic.model;

import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.WidgetObject;

import java.io.Serializable;
import java.util.List;

public class InputBoard implements Serializable {
    private String boardId;
    private List<WidgetObject> widgetObjects;

    public InputBoard(String boardId, List<WidgetObject> widgetObjects) {
        this.boardId = boardId;
        this.widgetObjects = widgetObjects;
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

