package ch.ost.rj.sa.miro2cml.model.widgets;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.MiroWidget;

import java.math.BigInteger;

public class Sticker extends WidgetObject {
    private int x;
    private int y;
    private double scale;
    private double height;
    private double width;

    private String backgroundColor;
    private String fontFamily;
    private int fontSize;
    private String textAlign;
    private String textAlignVertical;
    private String text;

    public Sticker(BigInteger id, int x, int y, double scale, double height, double width, String backgroundColor, String fontFamily, int fontSize, String textAlign, String textAlignVertical, String text) {
        super(id);
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.height = height;
        this.width = width;
        this.backgroundColor = backgroundColor;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.textAlign = textAlign;
        this.textAlignVertical = textAlignVertical;
        this.text = text;
    }

    public Sticker(MiroWidget miroWidget) {
        super(miroWidget.getId());
        this.x = miroWidget.getX();
        this.y = miroWidget.getY();
        this.scale = miroWidget.getScale();
        this.height = miroWidget.getHeight();
        this.width = miroWidget.getWidth();
        this.backgroundColor = miroWidget.getStyle().getBackgroundColor();
        this.fontFamily = miroWidget.getStyle().getFontFamily();
        this.fontSize = miroWidget.getStyle().getFontSize();
        this.textAlign = miroWidget.getStyle().getTextAlign();
        this.textAlignVertical = miroWidget.getStyle().getTextAlignVertical();
        this.text = miroWidget.getText();

    }

    @Override
    public String toString() {
        return super.toString() + "Sticker{" +
                "x=" + x +
                ", y=" + y +
                ", scale=" + scale +
                ", height=" + height +
                ", width=" + width +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", fontFamily='" + fontFamily + '\'' +
                ", fontSize=" + fontSize +
                ", textAlign='" + textAlign + '\'' +
                ", textAlignVertical='" + textAlignVertical + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
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
    public void validate() {
        super.validate();
    }

    @Override
    public String getMappingRelevantText() {
        return super.getMappingRelevantText();
    }
}
