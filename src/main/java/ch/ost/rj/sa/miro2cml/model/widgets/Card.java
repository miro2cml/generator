package ch.ost.rj.sa.miro2cml.model.widgets;


import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.MiroWidget;

import java.math.BigInteger;

public class Card extends WidgetObject {
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

    public Card(MiroWidget miroWidgetMiroWidget) {
        super(miroWidgetMiroWidget.getId());
        this.x = miroWidgetMiroWidget.getX();
        this.y = miroWidgetMiroWidget.getY();
        this.scale = miroWidgetMiroWidget.getScale();
        this.title = miroWidgetMiroWidget.getTitle();
        this.description = miroWidgetMiroWidget.getDescription();
        this.backgroundColor = miroWidgetMiroWidget.getStyle().getBackgroundColor();
    }

    @Override
    public String toString() {
        return super.toString() + "Card{" +
                "x=" + x +
                ", y=" + y +
                ", scale=" + scale +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
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

    public void validate(){
        if(title != null) {
            title = StringValidator.removeDoubleSpace(title);
        }else{
            title="";
        }
    }

    @Override
    public String getMappingRelevantText() {
        return title;
    }
}
