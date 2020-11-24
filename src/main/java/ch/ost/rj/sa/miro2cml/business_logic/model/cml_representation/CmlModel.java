package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import ch.ost.rj.sa.miro2cml.business_logic.model.IOutputArtifact;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.contextmapper.dsl.cml.CMLResource;
import org.contextmapper.dsl.standalone.ContextMapperStandaloneSetup;
import org.eclipse.xtext.resource.SaveOptions;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CmlModel implements IOutputArtifact {
    private CMLResource resource;

    public CmlModel() {
        File file = new File("temp.cml");
        this.resource = ContextMapperStandaloneSetup.getStandaloneAPI().createCML(file);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CmlModel)) return false;
        CmlModel cmlModel = (CmlModel) o;
        return Objects.equals(resource, cmlModel.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource);
    }

    public CMLResource getResource() {
        return resource;
    }

    public void setResource(CMLResource resource) {
        this.resource = resource;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            resource.save(outStream, SaveOptions.defaultOptions().toOptionsMap());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outStream.toByteArray();
    }
}
