package ch.ost.rj.sa.miro2cml.business_logic.cml_generator;

import ch.ost.rj.sa.miro2cml.business_logic.model.ConceptBoard;

import java.io.File;

public interface ICmlGenerator {
    File generateCmlFile(ConceptBoard conceptBoard);
}
