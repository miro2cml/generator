package ch.ost.rj.sa.miro2cml.data_access.miro_model;

import java.util.List;

public class WidgetList {
    private String type;

    private List<WidgetObject> widgetObjects;

    private int size;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setObject(List<WidgetObject> widgetObjects) {
        this.widgetObjects = widgetObjects;
    }

    public List<WidgetObject> getObjects() {
        return this.widgetObjects;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}

