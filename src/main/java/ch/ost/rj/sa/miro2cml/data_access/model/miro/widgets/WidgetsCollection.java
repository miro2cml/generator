package ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets;

import java.util.List;

public class WidgetsCollection {
    private String type;

    private List<MiroWidget> data;

    private int size;

    @Override
    public String toString() {
        return "WidgetsCollection{" +
                "type='" + type +
                ", size=" + size + '\'' +
                ", data=" + data +
                '}';
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MiroWidget> getData() {
        return this.data;
    }

    public void setData(List<MiroWidget> data) {
        this.data = data;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

