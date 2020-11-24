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

    public void validate(){return;}//TODO: refactor, remove this method from this(and subclasses) and move it elsewhere, See Gitlab Issue #29

    public String getMappingRelevantText(){return "";} //TODO: refactor, remove this method from this(and subclasses) and move it elsewhere, See Gitlab Issue #29
    @Override
    public String toString() {
        return "WidgetObject{" +
                "id=" + id +
                '}' + "Type: ";
    }
}
