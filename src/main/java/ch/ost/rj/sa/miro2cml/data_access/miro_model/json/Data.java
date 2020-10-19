package ch.ost.rj.sa.miro2cml.data_access.miro_model.json;

import java.math.BigInteger;

public class Data {
    private String type;
    private BigInteger id;

    private double scale;
    private int y;
    private int x;
    private int width;
    private int height;
    private String title;
    private String description;
    private StartWidget startWidget;
    private EndWidget endWidget;
    private String url;
    private double rotation;
    private String text;
    private Style style;

    private CreatedBy createdBy;
    private String modifiedAt;
    private ModifiedBy modifiedBy;
    private String createdAt;
    private String date;
    private Assignee assignee;
    private String card; //isn't listed in Miro Documentation, but appears in CardWidgetObjects as field.

    @Override
    public String toString() {
        return "Data{" +
                "type='" + type + '\'' +
                ", id=" + id +
                ", scale=" + scale +
                ", y=" + y +
                ", x=" + x +
                ", width=" + width +
                ", height=" + height +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startWidget=" + startWidget +
                ", endWidget=" + endWidget +
                ", url='" + url + '\'' +
                ", rotation=" + rotation +
                ", text='" + text + '\'' +
                ", style=" + style +
                ", createdBy=" + createdBy +
                ", modifiedAt='" + modifiedAt + '\'' +
                ", modifiedBy=" + modifiedBy +
                ", createdAt='" + createdAt + '\'' +
                ", date='" + date + '\'' +
                ", assignee=" + assignee +
                ", card='" + card + '\'' +
                '}';
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StartWidget getStartWidget() {
        return startWidget;
    }

    public void setStartWidget(StartWidget startWidget) {
        this.startWidget = startWidget;
    }

    public EndWidget getEndWidget() {
        return endWidget;
    }

    public void setEndWidget(EndWidget endWidget) {
        this.endWidget = endWidget;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getScale() {
        return this.scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
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

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Style getStyle() {
        return this.style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public CreatedBy getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedAt() {
        return this.modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public ModifiedBy getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(ModifiedBy modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

}