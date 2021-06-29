package ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.MiroWidget;

import java.math.BigInteger;
import java.util.Objects;

import static java.lang.String.valueOf;

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

    public Line(MiroWidget miroWidget) {
        super(miroWidget.getId());
        this.startWidgetId = resolveStartWidgetId(miroWidget);
        this.endWidgetId = resolveEndWIdgetId(miroWidget);
        this.borderColor = miroWidget.getStyle().getBorderColor();
        this.borderStyle = miroWidget.getStyle().getBorderStyle();
        this.borderWidth = miroWidget.getStyle().getBorderWidth();
        this.lineEndType = miroWidget.getStyle().getLineEndType();
        this.lineStartType = miroWidget.getStyle().getLineEndType();
        this.lineType = miroWidget.getStyle().getLineType();
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + super.getId()+
                "startWidgetId=" + startWidgetId +
                ", endWidgetId=" + endWidgetId +
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

    private BigInteger resolveEndWIdgetId(MiroWidget miroWidget){
        if (miroWidget.getEndWidget()!=null && miroWidget.getEndWidget().getId()!=null){
            return new BigInteger(miroWidget.getEndWidget().getId());
        }
        return new BigInteger(String.valueOf(0));
    }
    private BigInteger resolveStartWidgetId(MiroWidget miroWidget){
        if (miroWidget.getStartWidget()!=null && miroWidget.getStartWidget().getId()!=null){
            return new BigInteger(miroWidget.getStartWidget().getId());
        }
        return new BigInteger(String.valueOf(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        if (!super.equals(o)) return false;
        Line line = (Line) o;
        return Double.compare(line.borderWidth, borderWidth) == 0 &&
                Objects.equals(startWidgetId, line.startWidgetId) &&
                Objects.equals(endWidgetId, line.endWidgetId) &&
                Objects.equals(borderColor, line.borderColor) &&
                Objects.equals(borderStyle, line.borderStyle) &&
                Objects.equals(lineEndType, line.lineEndType) &&
                Objects.equals(lineStartType, line.lineStartType) &&
                Objects.equals(lineType, line.lineType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startWidgetId, endWidgetId, borderColor, borderStyle, borderWidth, lineEndType, lineStartType, lineType);
    }
}
