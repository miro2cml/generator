package ch.ost.rj.sa.miro2cml.model.widgets;

import java.math.BigInteger;

public abstract class WidgetObject {
    private BigInteger id;

    public WidgetObject(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }


    public String getMappingRelevantText(){return "";}

    public void setMappingRelevantText(String text){return;}

    @Override
    public String toString() {
        return "WidgetObject{" +
                "id=" + id +
                '}' + "Type: ";
    }
}
