package ch.ost.rj.sa.miro2cml.business_logic.model;

import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;

public class MappedBoard {
    private InputBoard inputBoard;
    private CmlModel cmlModel;
    public MappedBoard(InputBoard inputBoard, CmlModel cmlModel) {
        this.inputBoard = inputBoard;
        this.cmlModel = cmlModel;
    }
    public InputBoard getMiroBoard() {
        return inputBoard;
    }
    public void setMiroBoard(InputBoard inputBoard) {
        this.inputBoard = inputBoard;
    }
    public CmlModel getCmlModel() {
        return cmlModel;
    }
    public void setCmlModel(CmlModel cmlModel) {
        this.cmlModel = cmlModel;
    }
    public void setOriginalBoard(InputBoard originalInputBoard) {
        this.inputBoard = originalInputBoard;
    }
}