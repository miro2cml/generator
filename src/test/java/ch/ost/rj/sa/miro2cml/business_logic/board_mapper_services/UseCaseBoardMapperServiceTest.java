package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.MiroBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.ICmlArtifact;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.UserStory;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.pojo.Card;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.pojo.WidgetObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UseCaseBoardMapperServiceTest {

    private UseCaseBoardMapperService useCaseBoardMapperServiceUnderTest;

    @BeforeEach
    void setUp() {
        useCaseBoardMapperServiceUnderTest = new UseCaseBoardMapperService();
    }

    @Test
    void testMapWidgetObjectsToCmlArtifacts() {
        // Setup
        final Card card = new Card(new BigInteger(String.valueOf(1)), 1, 1, 1, "<p>As an user I want to create a account so that I could authorize myself</p>", "description", "#FFFFFF");
        final ArrayList<WidgetObject> widgetObjectArrayList = new ArrayList<>();
        widgetObjectArrayList.add(card);
        final MiroBoard miroBoard = new MiroBoard("boardId", widgetObjectArrayList);
        final UserStory story = new UserStory("createaccount", "user", "create", "account", "I could authorize myself");
        final ArrayList<ICmlArtifact> list = new ArrayList<>();
        list.add(story);
        final CmlModel expectedResult = new CmlModel();
        // Run the test
        final CmlModel result = useCaseBoardMapperServiceUnderTest.mapWidgetObjectsToCmlArtifacts(miroBoard);

        // Verify the results
        assertEquals(expectedResult, result);

    }
}
