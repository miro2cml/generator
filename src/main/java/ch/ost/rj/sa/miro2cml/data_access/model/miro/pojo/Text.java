package ch.ost.rj.sa.miro2cml.data_access.model.miro.pojo;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.json.Data;

import java.math.BigInteger;

public class Text extends WidgetObject {
    private int x;
    private int y;
    private double width;
    private double scale;
    private double rotation;

    private String backgroundColor;
    private double backgroundOpacity;
    private String borderColor;
    private double borderOpacity;
    private String borderStyle;
    private double borderWidth;
    private String textColor;

    private String fontFamily;
    private int fontSize;
    private String textAlign;

    public Text(BigInteger id, int x, int y, double width, double scale, double rotation, String backgroundColor, double backgroundOpacity, String borderColor, double borderOpacity, String borderStyle, double borderWidth, String textColor, String fontFamily, int fontSize, String textAlign) {
        super(id);
        this.x = x;
        this.y = y;
        this.width = width;
        this.scale = scale;
        this.rotation = rotation;
        this.backgroundColor = backgroundColor;
        this.backgroundOpacity = backgroundOpacity;
        this.borderColor = borderColor;
        this.borderOpacity = borderOpacity;
        this.borderStyle = borderStyle;
        this.borderWidth = borderWidth;
        this.textColor = textColor;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.textAlign = textAlign;
    }

    public Text(Data miroWidgetData) {
        super(miroWidgetData.getId());
        this.x = miroWidgetData.getX();
        this.y = miroWidgetData.getY();
        this.width = miroWidgetData.getWidth();
        this.scale = miroWidgetData.getScale();
        this.rotation = miroWidgetData.getRotation();
        this.backgroundColor = miroWidgetData.getStyle().getBackgroundColor();
        this.backgroundOpacity = miroWidgetData.getStyle().getBackgroundOpacity();
        this.borderColor = miroWidgetData.getStyle().getBorderColor();
        this.borderOpacity = miroWidgetData.getStyle().getBorderOpacity();
        this.borderStyle = miroWidgetData.getStyle().getBorderStyle();
        this.borderWidth = miroWidgetData.getStyle().getBorderWidth();
        this.textColor = miroWidgetData.getStyle().getTextColor();
        this.fontFamily = miroWidgetData.getStyle().getFontFamily();
        this.fontSize = miroWidgetData.getStyle().getFontSize();
        this.textAlign = miroWidgetData.getStyle().getTextAlign();
    }

    @Override
    public String toString() {
        return super.toString() + "Text{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", scale=" + scale +
                ", rotation=" + rotation +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", backgroundOpacity=" + backgroundOpacity +
                ", borderColor='" + borderColor + '\'' +
                ", borderOpacity=" + borderOpacity +
                ", borderStyle='" + borderStyle + '\'' +
                ", borderWidth=" + borderWidth +
                ", textColor='" + textColor + '\'' +
                ", fontFamily='" + fontFamily + '\'' +
                ", fontSize=" + fontSize +
                ", textAlign='" + textAlign + '\'' +
                '}';
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

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
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


}
