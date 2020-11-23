package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.UserStory;
import ch.ost.rj.sa.miro2cml.model.widgets.Card;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;

import java.util.ArrayList;
import java.util.List;

public class UserStoryBoard {
    public static final String BLUECARD = "#2d9bf0";
    public static final String YELLOWCARD = "#fbc800";
    private final InputBoard inputBoard;
    private final ArrayList<UserStory> userStories;
    private static final List<List<String>> regex = UserStoryRegex.createUserStoriesRegex();

    private UserStoryBoard(InputBoard inputBoard) {
        this.inputBoard = inputBoard;
        this.userStories = extractUserStories();
    }
    public static UserStoryBoard createUserStoryBoard(InputBoard inputBoard) {
        return new UserStoryBoard(inputBoard);
    }


    private  ArrayList<UserStory> extractUserStories() {
        ArrayList<UserStory> output = new ArrayList<>();
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if(widget instanceof Card && isValidUserStory(((Card)widget))){
                output.add(extractUserStory((Card)widget));
            }
        }
        return output;
    }

    private static boolean isValidUserStory(Card card) {
        return !(isBlueOrYellow(card)) && (card.getTitle()!= null) && findUserStory(card.getTitle());
    }


    private static boolean findUserStory(String input){
        for (List<String> strings : regex) {
            if (input.matches(strings.get(0))) {
                return true;
            }
        }
        return false;
    }
    private UserStory extractUserStory(Card card){
        String text = card.getTitle();
        for (List<String> strings : regex) {
            if (text.matches(strings.get(0))) {
                String role = getPart(strings.get(1), strings.get(2), text, 0);
                String verb = getPart(strings.get(2), strings.get(3), text, strings.get(1).length() + role.length());
                String entity = getPart(strings.get(3), strings.get(4), text, strings.get(1).length() + strings.get(2).length() + verb.length() + role.length());
                String benefit = getPart(strings.get(4), strings.get(5), text, strings.get(1).length() + strings.get(2).length() + strings.get(3).length() + verb.length() + role.length() + entity.length());
                return new UserStory("", role, verb, entity, benefit);
            }
        }
        return null;
    }

    private static boolean isBlueOrYellow(Card card) {
        return card.getBackgroundColor().equals(BLUECARD) || card.getBackgroundColor().equals(YELLOWCARD);
    }
    private static String getPart(String start, String end, String input, int index) {
        int startIndex = input.indexOf(start, index);
        int endIndex = input.indexOf(end, index);
        return input.substring(startIndex + start.length(), endIndex);
    }


    public ArrayList<UserStory> getUserStories() {
        return userStories;
    }
}
