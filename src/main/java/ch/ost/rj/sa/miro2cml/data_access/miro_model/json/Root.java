package ch.ost.rj.sa.miro2cml.data_access.miro_model.json;

import java.util.List;

public class Root {
    private String type;

    private List<Data> data;

    private int size;

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Root{" +
                "type='" + type +
                ", size=" + size + '\'' +
                ", data=" + data +
                '}';
    }

    public String getType() {
        return this.type;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return this.data;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}

