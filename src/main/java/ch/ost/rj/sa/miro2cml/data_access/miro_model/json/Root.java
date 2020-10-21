package ch.ost.rj.sa.miro2cml.data_access.miro_model.json;

import java.util.List;

public class Root {
    private String type;

    private List<Data> data;

    private int size;

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

    public void setType(String type) {
        this.type = type;
    }

    public List<Data> getData() {
        return this.data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

