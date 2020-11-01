package ch.ost.rj.sa.miro2cml.business_logic.model;

import ch.ost.rj.sa.miro2cml.business_logic.model.cml.CmlModel;

public class MappedBoard {
    private MiroBoard miroBoard;
    private CmlModel cmlModel;

    public MappedBoard(MiroBoard miroBoard, CmlModel cmlModel) {
        this.miroBoard = miroBoard;
        this.cmlModel = cmlModel;
    }

    public MiroBoard getMiroBoard() {
        return miroBoard;
    }

    public void setMiroBoard(MiroBoard miroBoard) {
        this.miroBoard = miroBoard;
    }

    public CmlModel getCmlModel() {
        return cmlModel;
    }

    public void setCmlModel(CmlModel cmlModel) {
        this.cmlModel = cmlModel;
    }

    public MiroBoard getOriginalBoard() {
        return miroBoard;
    }

    public void setOriginalBoard(MiroBoard originalMiroBoard) {
        this.miroBoard = originalMiroBoard;
    }
}
