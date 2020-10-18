package ch.ost.rj.sa.miro2cml.data_access.miro_model.pojo;

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

    @Override
    public String toString() {
        return "WidgetObject{" +
                "id=" + id +
                '}' + "Type: ";
    }
}
