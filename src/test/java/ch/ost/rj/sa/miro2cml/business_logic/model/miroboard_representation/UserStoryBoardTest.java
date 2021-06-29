package ch.ost.rj.sa.miro2cml.business_logic.model.miroboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.Card;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.Text;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.WidgetObject;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserStoryBoardTest {

    private static List<List<String>> regex = new UserStoryRegexProvider().getListOfUserStoriesRegexStringLists();

    @Test
    void createUserStoryBoard_1() throws WrongBoardException {
        //setup
        List<WidgetObject> widgetObjects= new ArrayList<>();
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "input  text", "", ""));
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "<p>As an User I want to create a board so that I could see it</p>", "", ""));
        InputBoard board = new InputBoard("123", widgetObjects);


        String expectedActor = "User";
        String expectedVerb = "create";
        String expectedEntity = "board";
        String expectedBenefit = "I could see it";
        String expectedArticle = "a";
        UserStoryBoard result = UserStoryBoard.createUserStoryBoard(board,new MappingLog(), new MappingMessages());

        assertEquals(expectedActor, result.getUserStories().get(0).getRole());
        assertEquals(expectedVerb, result.getUserStories().get(0).getVerb());
        assertEquals(expectedEntity, result.getUserStories().get(0).getEntity());
        assertEquals(expectedBenefit, result.getUserStories().get(0).getBenefit());
        assertEquals(expectedArticle, result.getUserStories().get(0).getArticle());

    }
    @Test
    void createUserStoryBoard_2() throws WrongBoardException {
        //setup
        List<WidgetObject> widgetObjects= new ArrayList<>();
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "<p>As an User I want to create an object so that I could see it</p>", "", ""));
        InputBoard board = new InputBoard("123", widgetObjects);


        String expectedActor = "User";
        String expectedVerb = "create";
        String expectedEntity = "object";
        String expectedBenefit = "I could see it";
        String expectedArticle = "an";
        UserStoryBoard result = UserStoryBoard.createUserStoryBoard(board, new MappingLog(), new MappingMessages());

        assertEquals(expectedActor, result.getUserStories().get(0).getRole());
        assertEquals(expectedVerb, result.getUserStories().get(0).getVerb());
        assertEquals(expectedEntity, result.getUserStories().get(0).getEntity());
        assertEquals(expectedBenefit, result.getUserStories().get(0).getBenefit());
        assertEquals(expectedArticle, result.getUserStories().get(0).getArticle());

    }
    @Test
    void createUserStoryBoard_3() throws WrongBoardException {
        //setup
        List<WidgetObject> widgetObjects= new ArrayList<>();
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "<p>As a Customer I want to create a board so that I could see it</p>", "", ""));
        InputBoard board = new InputBoard("123", widgetObjects);


        String expectedActor = "Customer";
        String expectedVerb = "create";
        String expectedEntity = "board";
        String expectedBenefit = "I could see it";
        String expectedArticle = "a";
        UserStoryBoard result = UserStoryBoard.createUserStoryBoard(board,new MappingLog(), new MappingMessages());

        assertEquals(expectedActor, result.getUserStories().get(0).getRole());
        assertEquals(expectedVerb, result.getUserStories().get(0).getVerb());
        assertEquals(expectedEntity, result.getUserStories().get(0).getEntity());
        assertEquals(expectedBenefit, result.getUserStories().get(0).getBenefit());
        assertEquals(expectedArticle, result.getUserStories().get(0).getArticle());

    }
    @Test
    void createUserStoryBoard_4() throws WrongBoardException {
        //setup
        List<WidgetObject> widgetObjects= new ArrayList<>();
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "<p>As a Customer I want to create an object so that I could see it</p>", "", ""));
        InputBoard board = new InputBoard("123", widgetObjects);


        String expectedActor = "Customer";
        String expectedVerb = "create";
        String expectedEntity = "object";
        String expectedBenefit = "I could see it";
        String expectedArticle = "an";
        UserStoryBoard result = UserStoryBoard.createUserStoryBoard(board,new MappingLog(), new MappingMessages());

        assertEquals(expectedActor, result.getUserStories().get(0).getRole());
        assertEquals(expectedVerb, result.getUserStories().get(0).getVerb());
        assertEquals(expectedEntity, result.getUserStories().get(0).getEntity());
        assertEquals(expectedBenefit, result.getUserStories().get(0).getBenefit());
        assertEquals(expectedArticle, result.getUserStories().get(0).getArticle());

    }
    @Test
    void createUserStoryBoard_WithoutUserStory() throws WrongBoardException{

        List<WidgetObject> widgetObjects= new ArrayList<>();
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "Text", "", ""));
        InputBoard board = new InputBoard("123", widgetObjects);

        Throwable exception = assertThrows(WrongBoardException.class, () -> UserStoryBoard.createUserStoryBoard(board, new MappingLog(), new MappingMessages()));
        assertEquals("Input Board doesn't match with expected Board Type: User Story. No UserStories found.", exception.getMessage());
    }
    @Test
    void createUserStoryBoard_WithoutCards() throws WrongBoardException{

        List<WidgetObject> widgetObjects= new ArrayList<>();
        widgetObjects.add(new Text(BigInteger.ONE, 30, 20, 0, 0, 0, "", 0, "", 0, "", 0, "", "some text", "", 0, ""));
        InputBoard board = new InputBoard("123", widgetObjects);

        Throwable exception = assertThrows(WrongBoardException.class, () -> UserStoryBoard.createUserStoryBoard(board, new MappingLog(), new MappingMessages()));
        assertEquals("Input Board doesn't match with expected Board Type: User Story. No Cards found.", exception.getMessage());
    }
}