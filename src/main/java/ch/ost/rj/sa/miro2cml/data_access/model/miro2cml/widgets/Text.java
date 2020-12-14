package ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets;


import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.MiroWidget;

import java.math.BigInteger;
import java.util.Objects;

public class Text extends WidgetObject implements IRelevantText {
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
    private String text;

    private String fontFamily;
    private int fontSize;
    private String textAlign;

    public Text(BigInteger id, int x, int y, double width, double scale, double rotation, String backgroundColor, double backgroundOpacity, String borderColor, double borderOpacity, String borderStyle, double borderWidth, String textColor, String text, String fontFamily, int fontSize, String textAlign) {
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
        this.text = text;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.textAlign = textAlign;
    }

    public Text(MiroWidget miroWidget) {
        super(miroWidget.getId());
        this.x = miroWidget.getX();
        this.y = miroWidget.getY();
        this.width = miroWidget.getWidth();
        this.scale = miroWidget.getScale();
        this.rotation = miroWidget.getRotation();
        this.backgroundColor = miroWidget.getStyle().getBackgroundColor();
        this.backgroundOpacity = miroWidget.getStyle().getBackgroundOpacity();
        this.borderColor = miroWidget.getStyle().getBorderColor();
        this.borderOpacity = miroWidget.getStyle().getBorderOpacity();
        this.borderStyle = miroWidget.getStyle().getBorderStyle();
        this.borderWidth = miroWidget.getStyle().getBorderWidth();
        this.textColor = miroWidget.getStyle().getTextColor();
        this.fontFamily = miroWidget.getStyle().getFontFamily();
        this.fontSize = miroWidget.getStyle().getFontSize();
        this.textAlign = miroWidget.getStyle().getTextAlign();
        this.text = miroWidget.getText();
    }

    @Override
    public String toString() {
        return  "Text{" +
                "  id=" + super.getId()+
                ", backgroundColor='" + backgroundColor + '\'' +
                ", text='" + text + '\'' +
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        if (!(o instanceof Text)) return false;
        if (!super.equals(o)) return false;
        Text text1 = (Text) o;
        return x == text1.x &&
                y == text1.y &&
                Double.compare(text1.width, width) == 0 &&
                Double.compare(text1.scale, scale) == 0 &&
                Double.compare(text1.rotation, rotation) == 0 &&
                Double.compare(text1.backgroundOpacity, backgroundOpacity) == 0 &&
                Double.compare(text1.borderOpacity, borderOpacity) == 0 &&
                Double.compare(text1.borderWidth, borderWidth) == 0 &&
                fontSize == text1.fontSize &&
                Objects.equals(backgroundColor, text1.backgroundColor) &&
                Objects.equals(borderColor, text1.borderColor) &&
                Objects.equals(borderStyle, text1.borderStyle) &&
                Objects.equals(textColor, text1.textColor) &&
                Objects.equals(text, text1.text) &&
                Objects.equals(fontFamily, text1.fontFamily) &&
                Objects.equals(textAlign, text1.textAlign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x, y, width, scale, rotation, backgroundColor, backgroundOpacity, borderColor, borderOpacity, borderStyle, borderWidth, textColor, text, fontFamily, fontSize, textAlign);
    }
}
