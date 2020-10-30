package ch.ost.rj.sa.miro2cml.data_access.model.miro.pojo;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.json.Data;

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

    public Sticker(BigInteger id, int x, int y, double scale, double height, double width, String backgroundColor, String fontFamily, int fontSize, String textAlign, String textAlignVertical) {
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
    }

    public Sticker(Data miroWidgetData) {
        super(miroWidgetData.getId());
        this.x = miroWidgetData.getX();
        this.y = miroWidgetData.getY();
        this.scale = miroWidgetData.getScale();
        this.height = miroWidgetData.getHeight();
        this.width = miroWidgetData.getWidth();
        this.backgroundColor = miroWidgetData.getStyle().getBackgroundColor();
        this.fontFamily = miroWidgetData.getStyle().getFontFamily();
        this.fontSize = miroWidgetData.getStyle().getFontSize();
        this.textAlign = miroWidgetData.getStyle().getTextAlign();
        this.textAlignVertical = miroWidgetData.getStyle().getTextAlignVertical();
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
}
