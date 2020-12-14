package ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

public abstract class WidgetObject implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WidgetObject)) return false;
        WidgetObject that = (WidgetObject) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
