package ch.ost.rj.sa.miro2cml.data_access.miro_model.json;

import java.math.BigInteger;

public class Data {
    private String type;

    private double scale;

    private double y;

    private int x;

    private int width;

    private int height;

    private String title;

    @Override
    public String toString() {
        return "Data{" +
                "type='" + type + '\'' +
                ", scale=" + scale +
                ", y=" + y +
                ", x=" + x +
                ", width=" + width +
                ", height=" + height +
                ", title='" + title + '\'' +
                ", startWidget=" + startWidget +
                ", endWidget=" + endWidget +
                ", url='" + url + '\'' +
                ", rotation=" + rotation +
                ", text='" + text + '\'' +
                ", style=" + style +
                ", id=" + id +
                ", createdBy=" + createdBy +
                ", modifiedAt='" + modifiedAt + '\'' +
                ", modifiedBy=" + modifiedBy +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    private Data startWidget;
    private Data endWidget;

    public Data getStartWidget() {
        return startWidget;
    }

    public void setStartWidget(Data startWidget) {
        this.startWidget = startWidget;
    }

    public Data getEndWidget() {
        return endWidget;
    }

    public void setEndWidget(Data endWidget) {
        this.endWidget = endWidget;
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private double rotation;

    private String text;

    private Style style;

    private BigInteger id;

    private CreatedBy createdBy;

    private String modifiedAt;

    private ModifiedBy modifiedBy;

    private String createdAt;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return this.scale;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Style getStyle() {
        return this.style;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return this.id;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public CreatedBy getCreatedBy() {
        return this.createdBy;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getModifiedAt() {
        return this.modifiedAt;
    }

    public void setModifiedBy(ModifiedBy modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ModifiedBy getModifiedBy() {
        return this.modifiedBy;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}