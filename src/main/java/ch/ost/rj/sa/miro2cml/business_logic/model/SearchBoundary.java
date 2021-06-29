package ch.ost.rj.sa.miro2cml.business_logic.model;

public class SearchBoundary {

    private double leftBoundary;
    private double rightBoundary;
    private double topBoundary;
    private double bottomBoundary;

    public double getLeftBoundary() {
        return leftBoundary;
    }

    public double getRightBoundary() {
        return rightBoundary;
    }

    public double getTopBoundary() {
        return topBoundary;
    }

    public double getBottomBoundary() {
        return bottomBoundary;
    }

    public SearchBoundary(double leftBoundary, double rightBoundary, double topBoundary, double bottomBoundary) {
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.topBoundary = topBoundary;
        this.bottomBoundary = bottomBoundary;
    }
}
