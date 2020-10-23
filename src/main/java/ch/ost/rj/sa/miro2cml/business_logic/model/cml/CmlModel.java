package ch.ost.rj.sa.miro2cml.business_logic.model.cml;

import java.util.ArrayList;

public class CmlModel {
    private ArrayList<CmlArtifact> artifacts;

    public CmlModel(ArrayList<CmlArtifact> artifacts) {
        this.artifacts = artifacts;
    }

    public ArrayList<CmlArtifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(ArrayList<CmlArtifact> artifacts) {
        this.artifacts = artifacts;
    }

    public void add(CmlArtifact cmlArtifact) {
        this.artifacts.add(cmlArtifact);
    }

}
