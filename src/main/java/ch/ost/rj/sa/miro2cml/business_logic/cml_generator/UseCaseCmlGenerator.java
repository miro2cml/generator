package ch.ost.rj.sa.miro2cml.business_logic.cml_generator;

import ch.ost.rj.sa.miro2cml.business_logic.model.ConceptBoard;
import ch.ost.rj.sa.miro2cml.data_access.miro_model.pojo.WidgetObject;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UseCaseCmlGenerator implements ICmlGenerator {
    //Todo: write CML generator -> currently only passes widgets in textform
    @Override
    public ByteArrayResource generateCmlByteArrayResource(ConceptBoard conceptBoard) {
        ArrayList<WidgetObject> widgetObjects = conceptBoard.getOriginalBoard().getWidgetObjects();

        ByteArrayResource byteArrayResource = null;
        File cmlFile = new File("src/main/resources/" + conceptBoard.getOriginalBoard().getBoardId() + ".tmp");
        Path path = Paths.get(cmlFile.getAbsolutePath());
        try (FileWriter writer = new FileWriter(cmlFile)) {
            for (WidgetObject widgetObject : widgetObjects) {
                writer.write(widgetObject.toString() + System.lineSeparator());
            }
            writer.flush();
            byteArrayResource = new ByteArrayResource(Files.readAllBytes(path));
            java.nio.file.Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayResource;
    }
}
