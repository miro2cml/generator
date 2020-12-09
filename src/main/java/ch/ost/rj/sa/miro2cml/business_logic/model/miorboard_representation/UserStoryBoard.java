package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.business_logic.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.UserStory;
import ch.ost.rj.sa.miro2cml.model.widgets.Card;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;

import java.util.ArrayList;
import java.util.List;

public class UserStoryBoard {

    public static final String BLUECARD = "#2d9bf0";
    public static final String YELLOWCARD = "#fbc800";
    private static final List<List<String>> regex = UserStoryRegex.createUserStoriesRegex();
    final MappingMessages messages;
    final MappingLog log;
    private final InputBoard inputBoard;
    private final ArrayList<UserStory> userStories;

    private UserStoryBoard(InputBoard inputBoard, MappingLog log, MappingMessages messages) throws WrongBoardException {
        this.inputBoard = inputBoard;
        this.log = log;
        this.messages = messages;
        this.userStories = extractUserStories();
    }

    public static UserStoryBoard createUserStoryBoard(InputBoard inputBoard, MappingLog log, MappingMessages messages) throws WrongBoardException {
        return new UserStoryBoard(inputBoard, log, messages);
    }

    private static String getPart(String start, String end, String input, int index) {
        int startIndex = input.indexOf(start, index);
        int endIndex = input.indexOf(end, index);
        return input.substring(startIndex + start.length(), endIndex);
    }

    private boolean isValidUserStory(Card card) {
        return !(isBlueOrYellow(card)) && (!isCardTitleNull(card)) && findUserStoryFromCard(card);
    }

    private boolean findUserStoryFromCard(Card card) {
        String input = card.getTitle();
        for (List<String> strings : regex) {
            if (input.matches(strings.get(0))) {
                return true;
            }
        }
        log.addWarningLogEntry("Card " + card.getTitle() + " didn't match any supported UserStoryFormats");
        return false;
    }

    private boolean isCardTitleNull(Card card) {
        if (card.getTitle() == null) {
            log.addErrorLogEntry("Card title field is null");
            return true;
        }
        return false;
    }

    private ArrayList<UserStory> extractUserStories() throws WrongBoardException {
        ArrayList<UserStory> output = new ArrayList<>();
        List<WidgetObject> input = inputBoard.getWidgetObjects();
        int cardCounter = 0;
        for (WidgetObject widget : input) {
            if (widget instanceof Card) {
                Card card = (Card) widget;
                cardCounter++;
                if (isValidUserStory(card)) {
                    UserStory userStory = extractUserStory(card);
                    if (userStory != null) {
                        output.add(userStory);
                        log.addSuccessLogEntry("Card: " + card.getTitle() + " has been mapped to: " + userStory.getName());
                    } else {
                        log.addErrorLogEntry("mapping of " + card.getTitle() + " resulted in invalid UserStory");
                    }
                }
            }
        }
        if (output.size() == cardCounter) {
            messages.add("No Errors/Warnings occurred during Mapping");
        } else {
            messages.add("Errors/warnings occurred");
            messages.add("We received " + input.size() + " widgets from Miro");
            messages.add(cardCounter + " of them were Cards");
            messages.add(output.size() + " of these cards have been successfully converted to UserStories");
            messages.add("Consult the logfile for more information");
            messages.setMappingState(false);
        }
        if(cardCounter==0){
            throw new WrongBoardException("Input Board doesn't match with expected Board Type: User Story. No Cards found.");
        }
        if(output.isEmpty()){
            throw new WrongBoardException("Input Board doesn't match with expected Board Type: User Story. No UserStories found.");
        }
        return output;
    }

    private UserStory extractUserStory(Card card) {
        String text = card.getTitle();
        for (List<String> strings : regex) {
            if (text.matches(strings.get(0))) {
                String role = getPart(strings.get(1), strings.get(2), text, 0);
                String verb = getPart(strings.get(2), strings.get(3), text, strings.get(1).length() + role.length());
                String entity = getPart(strings.get(3), strings.get(4), text, strings.get(1).length() + strings.get(2).length() + verb.length() + role.length());
                String benefit = getPart(strings.get(4), strings.get(5), text, strings.get(1).length() + strings.get(2).length() + strings.get(3).length() + verb.length() + role.length() + entity.length());
                String article = StringValidator.removeSpace(strings.get(3));
                return new UserStory(role, verb, entity, benefit, article);
            }
        }
        return null;
    }

    private boolean isBlueOrYellow(Card card) {
        if (card.getBackgroundColor().equals(BLUECARD) || card.getBackgroundColor().equals(YELLOWCARD)) {
            log.addWarningLogEntry("Card: " + card.getTitle() + "has been discarded. Reason: Color mapping rule");
            return true;
        }
        return false;
    }

    public ArrayList<UserStory> getUserStories() {
        return userStories;
    }
}
