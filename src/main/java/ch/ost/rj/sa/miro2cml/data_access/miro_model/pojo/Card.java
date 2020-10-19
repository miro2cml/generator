package ch.ost.rj.sa.miro2cml.data_access.miro_model.pojo;

import ch.ost.rj.sa.miro2cml.data_access.miro_model.json.Data;

import java.math.BigInteger;

public class Card extends WidgetObject{
    private int x;
    private int y;
    private double scale;

    private String title;
    private String description;
    private String backgroundColor;

    //private Date date;
    //        assignee": {
    //        "userId": "3074457345803957924" }


    public Card(BigInteger id, int x, int y, double scale, String title, String description, String backgroundColor) {
        super(id);
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
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

    public Card(Data miroWidgetData) {
        super(miroWidgetData.getId());
        this.x = miroWidgetData.getX();
        this.y = miroWidgetData.getY();
        this.scale = miroWidgetData.getScale();
        this.title = miroWidgetData.getTitle();
        this.description = miroWidgetData.getDescription();
        this.backgroundColor = miroWidgetData.getStyle().getBackgroundColor();
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
}
