package ch.ost.rj.sa.miro2cml.business_logic.model;

import ch.ost.rj.sa.miro2cml.business_logic.model.cml.CmlModel;

public class MappedBoard {
    private InputBoard inputBoard;
    private IOutputArtifact outputArtifact;

    public MappedBoard(InputBoard inputBoard, IOutputArtifact outputArtifact) {
        this.inputBoard = inputBoard;
        this.outputArtifact = outputArtifact;
    }

    public InputBoard getMiroBoard() {
        return inputBoard;
    }

    public void setMiroBoard(InputBoard inputBoard) {
        this.inputBoard = inputBoard;
    }

    public IOutputArtifact getOutputArtifact() {
        return outputArtifact;
    }

    public void setCmlModel(IOutputArtifact outputArtifact) {
        this.outputArtifact = outputArtifact;
    }

    public InputBoard getOriginalBoard() {
        return inputBoard;
    }

    public void setOriginalBoard(InputBoard originalInputBoard) {
        this.inputBoard = originalInputBoard;
    }
}
