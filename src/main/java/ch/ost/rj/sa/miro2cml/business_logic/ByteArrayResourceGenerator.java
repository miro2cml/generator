package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import org.springframework.core.io.ByteArrayResource;

public class ByteArrayResourceGenerator {
    private ByteArrayResourceGenerator() {
    }

    public static ByteArrayResource generateByteArrayResource(MappedBoard mappedBoard) {
        return new ByteArrayResource(mappedBoard.getCmlModel().toByteArray());
    }
}
