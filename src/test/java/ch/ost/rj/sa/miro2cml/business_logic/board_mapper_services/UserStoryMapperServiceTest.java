package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.ICmlArtifact;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.UserStory;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.Card;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.WidgetObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserStoryMapperServiceTest {

    private UserStoryMapperService userStoryMapperServiceUnderTest;

    @BeforeEach
    void setUp() {
        userStoryMapperServiceUnderTest = new UserStoryMapperService();
    }

    @Test
    void testMapWidgetObjectsToCmlArtifacts() throws WrongBoardException {
        // Setup
        final Card card = new Card(new BigInteger(String.valueOf(1)), 1, 1, 1, "<p>As an user I want to create a account so that I could authorize myself</p>", "description", "#FFFFFF");
        final ArrayList<WidgetObject> widgetObjectArrayList = new ArrayList<>();
        widgetObjectArrayList.add(card);
        final InputBoard inputBoard = new InputBoard("boardId", widgetObjectArrayList);
        final UserStory story = new UserStory("user", "create", "account", "I could authorize myself", "an");
        final ArrayList<ICmlArtifact> list = new ArrayList<>();
        list.add(story);
        final CmlModel expectedResult = new CmlModel();
        // Run the test
        final CmlModel result = userStoryMapperServiceUnderTest.mapWidgetObjectsToCmlArtifacts(inputBoard,new MappingLog(),new MappingMessages());

        // Verify the results
        assertEquals(expectedResult, result);

    }
}
