package ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.MiroWidget;

import java.math.BigInteger;
import java.util.Objects;

public class Sticker extends WidgetObject implements IRelevantText {
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
        return "Sticker{" +
                "id: " + super.getId()+ " color: " + backgroundColor +" text: " + text + '}';
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
        if (!(o instanceof Sticker)) return false;
        Sticker sticker = (Sticker) o;
        return x == sticker.x &&
                y == sticker.y &&
                Double.compare(sticker.scale, scale) == 0 &&
                Double.compare(sticker.height, height) == 0 &&
                Double.compare(sticker.width, width) == 0 &&
                fontSize == sticker.fontSize &&
                backgroundColor.equals(sticker.backgroundColor) &&
                fontFamily.equals(sticker.fontFamily) &&
                textAlign.equals(sticker.textAlign) &&
                textAlignVertical.equals(sticker.textAlignVertical) &&
                text.equals(sticker.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, scale, height, width, backgroundColor, fontFamily, fontSize, textAlign, textAlignVertical, text);
    }
}
