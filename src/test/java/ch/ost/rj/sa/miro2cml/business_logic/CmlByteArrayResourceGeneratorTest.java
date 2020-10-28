package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MiroBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.UserStory;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CmlByteArrayResourceGeneratorTest {

    @Test
    void testGenerateCmlByteArrayResource() {
        // Setup
        final MappedBoard mappedBoard = new MappedBoard(new MiroBoard("boardId", new ArrayList<>(List.of())), new CmlModel(new ArrayList<>(List.of(new UserStory("name","user", "create","account", "I could authorize myself")))), "content");
        final ByteArrayResource expectedResult = new ByteArrayResource("content".getBytes(), "description");
        new ByteArrayResource("content".getBytes(), "description");
        // Run the test
        final ByteArrayResource result = CmlByteArrayResourceGenerator.generateCmlByteArrayResource(mappedBoard);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
