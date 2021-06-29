package ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets;


import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.MiroWidget;

import java.math.BigInteger;
import java.util.Objects;

public class Shape extends WidgetObject implements IRelevantText {
    private int x;
    private int y;
    private double width;
    private double height;
    private double rotation;

    private String backgroundColor;
    private double backgroundOpacity;
    private String borderColor;
    private double borderOpacity;
    private String borderStyle;
    private double borderWidth;
    private String shapeType;
    private String textColor;

    private String fontFamily;
    private int fontSize;
    private String textAlign;
    private String textAlignVertical;
    private String text;

    public Shape(BigInteger id, int x, int y, double width, double height, double rotation, String backgroundColor, double backgroundOpacity, String borderColor, double borderOpacity, String borderStyle, double borderWidth, String shapeType, String textColor, String fontFamily, int fontSize, String textAlign, String textAlignVertical, String text) {
        super(id);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.backgroundColor = backgroundColor;
        this.backgroundOpacity = backgroundOpacity;
        this.borderColor = borderColor;
        this.borderOpacity = borderOpacity;
        this.borderStyle = borderStyle;
        this.borderWidth = borderWidth;
        this.shapeType = shapeType;
        this.textColor = textColor;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.textAlign = textAlign;
        this.textAlignVertical = textAlignVertical;
        this.text = text;
    }

    public Shape(MiroWidget miroWidget) {
        super(miroWidget.getId());
        this.x = miroWidget.getX();
        this.y = miroWidget.getY();
        this.width = miroWidget.getWidth();
        this.height = miroWidget.getHeight();
        this.rotation = miroWidget.getRotation();
        this.backgroundColor = miroWidget.getStyle().getBackgroundColor();
        this.backgroundOpacity = miroWidget.getStyle().getBackgroundOpacity();
        this.borderColor = miroWidget.getStyle().getBorderColor();
        this.borderOpacity = miroWidget.getStyle().getBorderOpacity();
        this.borderStyle = miroWidget.getStyle().getBorderStyle();
        this.borderWidth = miroWidget.getStyle().getBorderWidth();
        this.shapeType = miroWidget.getStyle().getShapeType();
        this.textColor = miroWidget.getStyle().getTextColor();
        this.fontFamily = miroWidget.getStyle().getFontFamily();
        this.fontSize = miroWidget.getStyle().getFontSize();
        this.textAlign = miroWidget.getStyle().getTextAlign();
        this.textAlignVertical = miroWidget.getStyle().getTextAlignVertical();
        this.text = miroWidget.getText();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "id=" + super.getId()+
                ", backgroundColor='" + backgroundColor + '\'' +
                ", shapeType='" + shapeType + '\'' +
                ", text='" + text + '\'' +
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }


    public double getBackgroundOpacity() {
        return backgroundOpacity;
    }

    public void setBackgroundOpacity(double backgroundOpacity) {
        this.backgroundOpacity = backgroundOpacity;
    }

    public double getBorderOpacity() {
        return borderOpacity;
    }

    public void setBorderOpacity(double borderOpacity) {
        this.borderOpacity = borderOpacity;
    }

    public String getBorderStyle() {
        return borderStyle;
    }

    public void setBorderStyle(String borderStyle) {
        this.borderStyle = borderStyle;
    }

    public double getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }

    public String getShapeType() {
        return shapeType;
    }

    public void setShapeType(String shapeType) {
        this.shapeType = shapeType;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public String getTextAlignVertical() {
        return textAlignVertical;
    }

    public void setTextAlignVertical(String textAlignVertical) {
        this.textAlignVertical = textAlignVertical;
    }

    @Override
    public String getMappingRelevantText() {
        return getText();
    }

    @Override
    public void setMappingRelevantText(String text) {
        setText(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shape)) return false;
        if (!super.equals(o)) return false;
        Shape shape = (Shape) o;
        return x == shape.x &&
                y == shape.y &&
                Double.compare(shape.width, width) == 0 &&
                Double.compare(shape.height, height) == 0 &&
                Double.compare(shape.rotation, rotation) == 0 &&
                Double.compare(shape.backgroundOpacity, backgroundOpacity) == 0 &&
                Double.compare(shape.borderOpacity, borderOpacity) == 0 &&
                Double.compare(shape.borderWidth, borderWidth) == 0 &&
                fontSize == shape.fontSize &&
                Objects.equals(backgroundColor, shape.backgroundColor) &&
                Objects.equals(borderColor, shape.borderColor) &&
                Objects.equals(borderStyle, shape.borderStyle) &&
                Objects.equals(shapeType, shape.shapeType) &&
                Objects.equals(textColor, shape.textColor) &&
                Objects.equals(fontFamily, shape.fontFamily) &&
                Objects.equals(textAlign, shape.textAlign) &&
                Objects.equals(textAlignVertical, shape.textAlignVertical) &&
                Objects.equals(text, shape.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x, y, width, height, rotation, backgroundColor, backgroundOpacity, borderColor, borderOpacity, borderStyle, borderWidth, shapeType, textColor, fontFamily, fontSize, textAlign, textAlignVertical, text);
    }
}
