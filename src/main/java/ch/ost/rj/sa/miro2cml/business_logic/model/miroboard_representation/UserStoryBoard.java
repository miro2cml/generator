package ch.ost.rj.sa.miro2cml.business_logic.model.miroboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.StringUtility;
import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.UserStoryElementExtractionError;
import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.UserStory;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.Card;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.WidgetObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserStoryBoard {

    private final List<String> ignoredCardColors;
    private final List<List<String>> regexes;
    private final MappingMessages messages;
    private final MappingLog log;
    private final InputBoard inputBoard;
    private final List<UserStory> userStories;
    private List<String> correctRegexCollection;


    private UserStoryBoard(InputBoard inputBoard, MappingLog log, MappingMessages messages) throws WrongBoardException {
        UserStoryRegexProvider regexProvider = new UserStoryRegexProvider();
        this.regexes = regexProvider.getListOfUserStoriesRegexStringLists();
        this.ignoredCardColors = regexProvider.getIgnoredCardColors();
        this.inputBoard = inputBoard;
        this.log = log;
        this.messages = messages;
        this.userStories = extractUserStories();
    }

    public static UserStoryBoard createUserStoryBoard(InputBoard inputBoard, MappingLog log, MappingMessages messages) throws WrongBoardException {
        return new UserStoryBoard(inputBoard, log, messages);
    }

    private boolean isValidUserStory(Card card) {
        return !(isIgnoredColor(card)) && (!isCardTitleNull(card)) && containsUserStory(card);
    }

    private boolean containsUserStory(Card card) {
        String cardText = card.getTitle();

        for (List<String> regexStrings : regexes) {
            if (cardText.matches(regexStrings.get(0))) {
                correctRegexCollection = regexStrings;
                return true;
            }
        }
        determineNotMatchingReason(cardText);
        return false;
    }
    private void determineNotMatchingReason(String input){

        log.addErrorLogEntry("Card: " + input + ", didn't match any supported UserStoryFormats. Take a look at the format rules (on the webpage or in the User Guide) and the Tutorials for Event Storming on the webpage for specific Information, which criteria a DomainEvent has to meet to be recognized as part of an EventGroup.");

        log.addInfoLogEntry("The following log entries should help you to find the exact reason, why your Card didn't match the format rules.");
        for (int j = 0; j < regexes.size(); j++) {
            List<String> regexStrings = regexes.get(j);
            String completeRegexString = regexStrings.get(0);
            int currentInputPosition = 0;
            log.addInfoLogEntry("Test input against regex-variant "+(j+1) +" of "+regexes.size()+", current variant: ActorArticle:"+regexStrings.get(1)+" & EntityArticle" + regexStrings.get(5) + ")"  );

            for (int i = 1; i < regexStrings.size(); i++) {
                String regexPart = regexStrings.get(i);
                Pattern p = Pattern.compile(regexPart);
                Matcher m = p.matcher(input.substring(currentInputPosition));
                if (m.find()) {
                    int startPositionOfMatchingString = m.start();
                    if (startPositionOfMatchingString != 0) {
                        log.addInfoLogEntry("Card: " + input + ", didn't match regexPart: |" + regexPart + "| on expected Index, testing against the following complete regex: " + completeRegexString);
                        log.addInfoLogEntry("Should have matched this part on " + currentInputPosition + " but matched it on " + (currentInputPosition + startPositionOfMatchingString));
                        log.addInfoLogEntry("Searched in Substring: " + input.substring(currentInputPosition));
                        log.addInfoLogEntry("Already parsed Substring: " + input.substring(0, currentInputPosition));
                        break;
                    } else {
                        int endPosition = m.end();
                        currentInputPosition += endPosition;
                    }
                } else {
                    log.addInfoLogEntry("Card: " + input + " didn't match regex: " + completeRegexString + " at regexPart: |" + regexPart + "|");
                    log.addInfoLogEntry("Searched in Substring: " + input.substring(currentInputPosition));
                    log.addInfoLogEntry("Already parsed Substring: " + input.substring(0, currentInputPosition));
                    break;
                }
            }
        }
    }

    private void prepareCardForMapping(Card card){
        String cardTitle = card.getTitle();

        cardTitle = StringUtility.reduceMultipleSpacesToOne(cardTitle);
        cardTitle = StringUtility.removeSimpleHtmlTags(cardTitle);
        cardTitle = StringUtility.extractHtmlLink(cardTitle);
        cardTitle = StringUtility.removeAllHtmlTags(cardTitle);
        cardTitle = StringUtility.replaceSpecialCharCodesWithTheirSingleCharEquivalent(cardTitle);

        card.setTitle(cardTitle);
    }

    private boolean isCardTitleNull(Card card) {
        if (card.getTitle() == null) {
            log.addErrorLogEntry("Card title field is null");
            return true;
        }
        return false;
    }

    private List<UserStory> extractUserStories() throws WrongBoardException {
        ArrayList<UserStory> output = new ArrayList<>();
        List<WidgetObject> input = inputBoard.getWidgetObjects();
        int cardCounter = 0;
        for (WidgetObject widget : input) {
            if (widget instanceof Card) {
                Card card = (Card) widget;
                prepareCardForMapping(card);
                cardCounter++;
                if (isValidUserStory(card)) {
                    try {
                        UserStory userStory = extractUserStory(card);
                        String cardText = card.getTitle();

                        output.add(userStory);
                        log.addSuccessLogEntry("Card: " + cardText + ", has been mapped to: " + userStory.getName());
                    } catch (UserStoryElementExtractionError e) {
                        log.addErrorLogEntry("Discarded Card: " + card + " Reason: " + e.getMessage());
                    }
                }
            }
        }
        if (output.size() == cardCounter) {
            messages.add("All Cards have been Mapped to a UserStory");
        } else {
            messages.add("Errors/warnings occurred");
            messages.add("We received " + input.size() + " widgets from Miro");
            messages.add(cardCounter + " of them were Cards");
            messages.add(output.size() + " of these cards have been successfully converted to UserStories");
            messages.add("Consult the logfile for more information");
            messages.setPerfectMapping(false);
        }
        if (cardCounter == 0) {
            throw new WrongBoardException("Input Board doesn't match with expected Board Type: User Story. No Cards found.");
        }
        if (output.isEmpty()) {
            throw new WrongBoardException("Input Board doesn't match with expected Board Type: User Story. No UserStories found.");
        }

        return uniquifyUserStoryNames(output);
    }

    private static String getUserStoryElement(String input, String regex, int offset) throws UserStoryElementExtractionError {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        if (m.find(offset)) {
            return input.substring(m.start(), m.end());
        } else {
            throw new UserStoryElementExtractionError("Could not extract UserStory Element corresponding to: |" + regex + "| in: " + input);
        }
    }

    private UserStory extractUserStory(Card card) throws UserStoryElementExtractionError {
        String input = card.getTitle();

        List<String> regexParts = correctRegexCollection;
        int offset = 0;

        offset += correctRegexCollection.get(1).length()-1;

        String role = getUserStoryElement(input, correctRegexCollection.get(2), offset);

        offset += role.length();
        offset += correctRegexCollection.get(3).length();
        String verb = getUserStoryElement(input, correctRegexCollection.get(4), offset);
        offset += verb.length();
        offset += regexParts.get(5).length();

        String entity = getUserStoryElement(input, correctRegexCollection.get(6), offset);
        offset += entity.length();
        offset += regexParts.get(7).length();

        String benefit = getUserStoryElement(input, regexParts.get(8), offset);
        String article = StringUtility.removeSpaces(regexParts.get(5));
        return new UserStory(role, verb, entity, benefit, article);
    }


    private boolean isIgnoredColor(Card card) {
        if (ignoredCardColors.stream().anyMatch(e-> e.equals(card.getBackgroundColor()))) {
            log.addWarningLogEntry("Card: " + card.getTitle() + "has been discarded. Reason: Color mapping rule");
            return true;
        }
        return false;
    }

    public List<UserStory> getUserStories() {
        return userStories;
    }

    private List<UserStory> uniquifyUserStoryNames(List<UserStory> userStories){
        ArrayList<UserStory> output = new ArrayList<>();

        for (UserStory userStory : userStories){
            if(output.stream().anyMatch(story->story.getName().equals(userStory.getName()))){
                setUniqueUserStoryName(output,userStory);
                output.add(userStory);
            }else{
                output.add(userStory);
            }
        }
        return output;
    }
    private void setUniqueUserStoryName(List<UserStory> stories, UserStory inputStory){
        int counter = 0;
        String originalUserStoryName = inputStory.getName();
        while (stories.stream().anyMatch(story->story.getName().equals(inputStory.getName()))){
            inputStory.setName(originalUserStoryName+ ++counter);
        }
    }
}
