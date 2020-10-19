package ch.ost.rj.sa.miro2cml.business_logic.cml_generator;

import ch.ost.rj.sa.miro2cml.business_logic.cml_model.CMLModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.ConceptBoard;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;

public interface ICmlGenerator {
    ByteArrayResource generateCmlByteArrayResource(ConceptBoard conceptBoard, CMLModel model);
}
