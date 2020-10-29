package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import org.springframework.core.io.ByteArrayResource;

public class CmlByteArrayResourceGenerator {
    private CmlByteArrayResourceGenerator() {
    }

    public static ByteArrayResource generateCmlByteArrayResource(MappedBoard mappedBoard) {
        return new ByteArrayResource(mappedBoard.getCmlFileString().getBytes());
    }
}
