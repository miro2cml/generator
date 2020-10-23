package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CmlByteArrayResourceGenerator {
    public static ByteArrayResource generateCmlByteArrayResource(MappedBoard mappedBoard) {
        ByteArrayResource byteArrayResource = null;
        File cmlFile = new File("src/main/resources/" + mappedBoard.getOriginalBoard().getBoardId() + ".tmp");
        Path path = Paths.get(cmlFile.getAbsolutePath());
        try (FileWriter writer = new FileWriter(cmlFile)) {
            writer.write(mappedBoard.getCmlFileString());
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
