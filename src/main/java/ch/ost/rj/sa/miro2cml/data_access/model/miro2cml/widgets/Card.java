package ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets;


import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.MiroWidget;

import java.math.BigInteger;
import java.util.Objects;

public class Card extends WidgetObject implements IRelevantText {
    private int x;
    private int y;
    private double scale;

    private String title;
    private String description;
    private String backgroundColor;

    public Card(BigInteger id, int x, int y, double scale, String title, String description, String backgroundColor) {
        super(id);
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
    }

    public Card(MiroWidget miroWidget) {
        super(miroWidget.getId());
        this.x = miroWidget.getX();
        this.y = miroWidget.getY();
        this.scale = miroWidget.getScale();
        this.title = miroWidget.getTitle();
        this.description = miroWidget.getDescription();
        this.backgroundColor = miroWidget.getStyle().getBackgroundColor();
    }

    @Override
    public String toString() {
        return  "Card{" +
                "id=" + super.getId()+
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public String getMappingRelevantText() {
        return getTitle();
    }

    @Override
    public void setMappingRelevantText(String text) {
        this.title=text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        if (!super.equals(o)) return false;
        Card card = (Card) o;
        return x == card.x &&
                y == card.y &&
                Double.compare(card.scale, scale) == 0 &&
                Objects.equals(title, card.title) &&
                Objects.equals(description, card.description) &&
                Objects.equals(backgroundColor, card.backgroundColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x, y, scale, title, description, backgroundColor);
    }
}
