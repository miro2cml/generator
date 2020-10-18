package ch.ost.rj.sa.miro2cml.business_logic.model;

public class ConceptBoard {
    private Board originalBoard;

    public ConceptBoard(Board originalBoard) {
        this.originalBoard = originalBoard;
    }

    public Board getOriginalBoard() {
        return originalBoard;
    }

    public void setOriginalBoard(Board originalBoard) {
        this.originalBoard = originalBoard;
    }
}
