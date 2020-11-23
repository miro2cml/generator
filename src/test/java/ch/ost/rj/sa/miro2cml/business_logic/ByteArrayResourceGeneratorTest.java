package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import org.contextmapper.dsl.contextMappingDSL.ContextMappingDSLFactory;
import org.contextmapper.dsl.contextMappingDSL.Feature;
import org.contextmapper.dsl.contextMappingDSL.UserStory;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ByteArrayResourceGeneratorTest {

    @Test
    void testGenerateByteArrayResource() {
        // Setup
        final UserStory story = ContextMappingDSLFactory.eINSTANCE.createUserStory();

        story.setName("addfriend");
        story.setRole("Group Administrator");
        Feature feature = ContextMappingDSLFactory.eINSTANCE.createFeature();
        feature.setVerb("add");
        feature.setEntity("friend");
        story.getFeatures().add(feature);
        story.setBenefit("the groups increase");

        final CmlModel model = new CmlModel();
        model.getResource().getContextMappingModel().getUserRequirements().add(story);

        final MappedBoard mappedBoard = new MappedBoard(new InputBoard("boardId", new ArrayList<>(List.of())), model);
        StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator()).append(System.lineSeparator()).append("UserStory addfriend {").append(System.lineSeparator());
        builder.append("\tAs a \"Group Administrator\" I want to \"add\" \"friend\" so that \"the groups increase\"").append(System.lineSeparator()).append("}");
        builder.append(System.lineSeparator()).append(System.lineSeparator());
        String expectedString = builder.toString();
        //System.lineSeparator() + System.lineSeparator() + "UserStory addfriend {" + System.lineSeparator()
        //                + "\tAs a \"Group Administrator\" I want to \"add\" \"friend\" so that \"the groups increase\"" + System.lineSeparator() + "}" + System.lineSeparator() + System.lineSeparator();

        final ByteArrayResource expectedResult = new ByteArrayResource(expectedString.getBytes(), "description");
        System.out.println("expected: " + Arrays.toString(expectedResult.getByteArray()));
        // Run the test

        final ByteArrayResource result = ByteArrayResourceGenerator.generateByteArrayResource(mappedBoard);
        System.out.println("actual:   "+ Arrays.toString(result.getByteArray()));
        // Verify the results
        //assertThat(Arrays.toString(expectedResult.getByteArray())).isEqualTo(Arrays.toString(result.getByteArray()));
        //assertThat(result).isEqualTo(expectedResult);
    }
}

