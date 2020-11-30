package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.model.widgets.Card;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserStoryBoardTest {
    private static List<List<String>> regex = UserStoryRegex.createUserStoriesRegex();

    @Test
    void createUserStoryBoard_1() {
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
        UserStoryBoard result = UserStoryBoard.createUserStoryBoard(board,new MappingLog(""), new MappingMessages());

        assertEquals(expectedActor, result.getUserStories().get(0).getRole());
        assertEquals(expectedVerb, result.getUserStories().get(0).getVerb());
        assertEquals(expectedEntity, result.getUserStories().get(0).getEntity());
        assertEquals(expectedBenefit, result.getUserStories().get(0).getBenefit());
        assertEquals(expectedArticle, result.getUserStories().get(0).getArticle());

    }
    @Test
    void createUserStoryBoard_2() {
        //setup
        List<WidgetObject> widgetObjects= new ArrayList<>();
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "<p>As an User I want to create an object so that I could see it</p>", "", ""));
        InputBoard board = new InputBoard("123", widgetObjects);


        String expectedActor = "User";
        String expectedVerb = "create";
        String expectedEntity = "object";
        String expectedBenefit = "I could see it";
        String expectedArticle = "an";
        UserStoryBoard result = UserStoryBoard.createUserStoryBoard(board, new MappingLog(""), new MappingMessages());

        assertEquals(expectedActor, result.getUserStories().get(0).getRole());
        assertEquals(expectedVerb, result.getUserStories().get(0).getVerb());
        assertEquals(expectedEntity, result.getUserStories().get(0).getEntity());
        assertEquals(expectedBenefit, result.getUserStories().get(0).getBenefit());
        assertEquals(expectedArticle, result.getUserStories().get(0).getArticle());

    }
    @Test
    void createUserStoryBoard_3() {
        //setup
        List<WidgetObject> widgetObjects= new ArrayList<>();
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "<p>As a Customer I want to create a board so that I could see it</p>", "", ""));
        InputBoard board = new InputBoard("123", widgetObjects);


        String expectedActor = "Customer";
        String expectedVerb = "create";
        String expectedEntity = "board";
        String expectedBenefit = "I could see it";
        String expectedArticle = "a";
        UserStoryBoard result = UserStoryBoard.createUserStoryBoard(board,new MappingLog(""), new MappingMessages());

        assertEquals(expectedActor, result.getUserStories().get(0).getRole());
        assertEquals(expectedVerb, result.getUserStories().get(0).getVerb());
        assertEquals(expectedEntity, result.getUserStories().get(0).getEntity());
        assertEquals(expectedBenefit, result.getUserStories().get(0).getBenefit());
        assertEquals(expectedArticle, result.getUserStories().get(0).getArticle());

    }
    @Test
    void createUserStoryBoard_4() {
        //setup
        List<WidgetObject> widgetObjects= new ArrayList<>();
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "<p>As a Customer I want to create an object so that I could see it</p>", "", ""));
        InputBoard board = new InputBoard("123", widgetObjects);


        String expectedActor = "Customer";
        String expectedVerb = "create";
        String expectedEntity = "object";
        String expectedBenefit = "I could see it";
        String expectedArticle = "an";
        UserStoryBoard result = UserStoryBoard.createUserStoryBoard(board,new MappingLog(""), new MappingMessages());

        assertEquals(expectedActor, result.getUserStories().get(0).getRole());
        assertEquals(expectedVerb, result.getUserStories().get(0).getVerb());
        assertEquals(expectedEntity, result.getUserStories().get(0).getEntity());
        assertEquals(expectedBenefit, result.getUserStories().get(0).getBenefit());
        assertEquals(expectedArticle, result.getUserStories().get(0).getArticle());

    }
}