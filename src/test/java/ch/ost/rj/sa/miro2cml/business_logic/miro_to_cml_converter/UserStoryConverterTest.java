package ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.UserStory;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.UserStoryBoard;
import ch.ost.rj.sa.miro2cml.model.widgets.Card;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserStoryConverterTest {

    @Test
    void convertExtractedBoardToCMLUserStories() {
        List<WidgetObject> widgetObjects= new ArrayList<>();
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "input  text", "", ""));
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "<p>As an User I want to create a board so that I could see it</p>", "", ""));
        InputBoard board = new InputBoard("123", widgetObjects);
        UserStoryBoard userStoryBoard = UserStoryBoard.createUserStoryBoard(board);

        List<UserStory> userStories = UserStoryConverter.convertExtractedBoardToCMLUserStories(userStoryBoard);
        String expectedName = "createboard";
        assertEquals(expectedName, userStories.get(0).getName());
        assertEquals(1, userStories.size());
    }
}