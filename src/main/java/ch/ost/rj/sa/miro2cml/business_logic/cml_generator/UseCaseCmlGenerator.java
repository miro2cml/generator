package ch.ost.rj.sa.miro2cml.business_logic.cml_generator;

import ch.ost.rj.sa.miro2cml.business_logic.cml_model.CMLModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.ConceptBoard;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UseCaseCmlGenerator implements ICmlGenerator {
    //Todo: write CML generator -> currently only passes widgets in textform
    @Override
    public ByteArrayResource generateCmlByteArrayResource(ConceptBoard conceptBoard, CMLModel model) {
        ArrayList<Object> cmlObjects = model.getAllObjects();

        ByteArrayResource byteArrayResource = null;
        File cmlFile = new File("src/main/resources/" + conceptBoard.getOriginalBoard().getBoardId() + ".tmp");
        Path path = Paths.get(cmlFile.getAbsolutePath());
        try (FileWriter writer = new FileWriter(cmlFile)) {
            for (Object object : cmlObjects) {
                writer.write(object.toString() + System.lineSeparator());
            }
            writer.flush();
            byteArrayResource = new ByteArrayResource(Files.readAllBytes(path));

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            java.nio.file.Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayResource;
    }
}
