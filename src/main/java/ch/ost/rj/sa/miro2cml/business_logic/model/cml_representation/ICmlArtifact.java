package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import org.eclipse.emf.ecore.EObject;

public interface ICmlArtifact {
    public boolean equals(Object o);

    public int hashCode();

    public String toString();

    public EObject provideEObject();
}
