package ch.ost.rj.sa.miro2cml.business_logic.model.cml;

import java.util.List;
import java.util.Objects;

public class CmlModel {
    private List<ICmlArtifact> artifacts;

    public CmlModel(List<ICmlArtifact> artifacts) {
        this.artifacts = artifacts;
    }

    public List<ICmlArtifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<ICmlArtifact> artifacts) {
        this.artifacts = artifacts;
    }

    public void add(ICmlArtifact cmlArtifact) {
        this.artifacts.add(cmlArtifact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CmlModel cmlModel = (CmlModel) o;
        return artifacts.equals(cmlModel.artifacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artifacts);
    }

    @Override
    public String toString() {
        return "CmlModel{" +
                "artifacts=" + artifacts +
                '}';
    }
}
