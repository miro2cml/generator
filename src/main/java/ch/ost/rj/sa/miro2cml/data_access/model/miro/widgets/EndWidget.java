package ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets;

public class EndWidget {
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EndWidget{" +
                "id='" + id + '\'' +
                '}';
    }
}