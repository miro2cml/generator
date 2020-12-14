package ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserStoryConverterTest {

    /*
    @Test
    void convertExtractedBoardToCMLUserStories() throws WrongBoardException {
        List<WidgetObject> widgetObjects= new ArrayList<>();
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "input  text", "", ""));
        widgetObjects.add(new Card(BigInteger.ONE, 0, 0, 0, "<p>As an User I want to create a board so that I could see it</p>", "", ""));
        InputBoard board = new InputBoard("123", widgetObjects);
        UserStoryBoard userStoryBoard = UserStoryBoard.createUserStoryBoard(board,new MappingLog(), new MappingMessages());

        List<UserStory> userStories = UserStoryConverter.convertExtractedBoardToCMLUserStories(userStoryBoard);
        String expectedName = "createboard";
        assertEquals(expectedName, userStories.get(0).getName());
        assertEquals(1, userStories.size());
    }

     */
}