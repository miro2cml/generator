package ch.ost.rj.sa.miro2cml.model.widgets;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.MiroWidget;

import java.math.BigInteger;

public class Line extends WidgetObject {
    private BigInteger startWidgetId;
    private BigInteger endWidgetId;

    private String borderColor;
    private String borderStyle;
    private double borderWidth;

    private String lineEndType;
    private String lineStartType;
    private String lineType;

    public Line(BigInteger id, BigInteger startWidgetId, BigInteger endWidgetId, String borderColor, String borderStyle, double borderWidth, String lineEndType, String lineStartType, String lineType) {
        super(id);
        this.startWidgetId = startWidgetId;
        this.endWidgetId = endWidgetId;
        this.borderColor = borderColor;
        this.borderStyle = borderStyle;
        this.borderWidth = borderWidth;
        this.lineEndType = lineEndType;
        this.lineStartType = lineStartType;
        this.lineType = lineType;
    }

    public Line(MiroWidget miroWidgetMiroWidget) {
        super(miroWidgetMiroWidget.getId());
        this.startWidgetId = new BigInteger(String.valueOf(0)); //miroWidgetMiroWidget.getStartWidget();
        this.endWidgetId = new BigInteger(String.valueOf(0)); //endWidgetId;
        this.borderColor = miroWidgetMiroWidget.getStyle().getBorderColor();
        this.borderStyle = miroWidgetMiroWidget.getStyle().getBorderStyle();
        this.borderWidth = miroWidgetMiroWidget.getStyle().getBorderWidth();
        this.lineEndType = miroWidgetMiroWidget.getStyle().getLineEndType();
        this.lineStartType = miroWidgetMiroWidget.getStyle().getLineEndType();
        this.lineType = miroWidgetMiroWidget.getStyle().getLineType();
    }

    @Override
    public String toString() {
        return super.toString() + "Line{" +
                "startWidgetId=" + startWidgetId +
                ", endWidgetId=" + endWidgetId +
                ", borderColor='" + borderColor + '\'' +
                ", borderStyle='" + borderStyle + '\'' +
                ", borderWidth=" + borderWidth +
                ", lineEndType='" + lineEndType + '\'' +
                ", lineStartType='" + lineStartType + '\'' +
                ", lineType='" + lineType + '\'' +
                '}';
    }

    public BigInteger getStartWidgetId() {
        return startWidgetId;
    }

    public void setStartWidgetId(BigInteger startWidgetId) {
        this.startWidgetId = startWidgetId;
    }

    public BigInteger getEndWidgetId() {
        return endWidgetId;
    }

    public void setEndWidgetId(BigInteger endWidgetId) {
        this.endWidgetId = endWidgetId;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
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

    public String getLineEndType() {
        return lineEndType;
    }

    public void setLineEndType(String lineEndType) {
        this.lineEndType = lineEndType;
    }

    public String getLineStartType() {
        return lineStartType;
    }

    public void setLineStartType(String lineStartType) {
        this.lineStartType = lineStartType;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }
}
