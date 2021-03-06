package ch.ost.rj.sa.miro2cml.business_logic.model.miroboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.model.SearchBoundary;
import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.InvalidBoardFormatException;
import ch.ost.rj.sa.miro2cml.business_logic.StringUtility;
import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.Line;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.Sticker;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.WidgetObject;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.*;

public class EventStormingBoard {
    public static final String USER_ROLE_P = "<p>User Role</p>";
    public static final String ISSUE_P = "<p>Issue</p>";
    public static final String AGGREGATE_P = "<p>Aggregate</p>";
    public static final String COMMAND_P = "<p>Command</p>";
    public static final String DOMAIN_EVENT_P = "<p>Domain Event</p>";
    private final InputBoard inputBoard;
    private final String domainEventColor;
    private final String commandColor;
    private final String aggregateColor;
    private final String issueColor;
    private final String userRoleColor;
    private final MappingLog mappingLog;
    private final MappingMessages messages;
    private final List<Sticker> domainEvents;
    private final List<Sticker> commands;
    private final List<Sticker> aggregates;
    private final List<Sticker> userRole;
    private final List<String> issues;
    private final List<ArrayList<String>> triggers;
    private final List<EventStormingGroup> connections;
    private double height;
    private double width;

    private EventStormingBoard(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException, InvalidBoardFormatException {
        this.mappingLog = mappingLog;
        this.messages = messages;
        this.inputBoard = inputBoard;

        Optional<WidgetObject> object = inputBoard.getWidgetObjects().stream().filter(widgetObject -> widgetObject instanceof Sticker).findAny();
        if (object.isPresent()){
            Sticker sticker = (Sticker) object.get();
            height = sticker.getHeight();
            width = sticker.getWidth();
        } else {
            width = 150;
            height = 150;
        }

        this.domainEventColor = getColor(DOMAIN_EVENT_P);
        this.commandColor = getColor(COMMAND_P);
        this.aggregateColor = getColor(AGGREGATE_P);
        this.issueColor = getColor(ISSUE_P);
        this.userRoleColor = getColor(USER_ROLE_P);
        checkInputFields();
        this.domainEvents = uniquifyStickerContent(getStickerWithRelevantColor(domainEventColor, "Domain Event"));
        this.commands = uniquifyStickerContent(getStickerWithRelevantColor(commandColor, "Command"));
        this.aggregates = getStickerWithRelevantColor(aggregateColor, "Aggregate");

        this.userRole = getStickerWithRelevantColor(userRoleColor, "UserRole");
        this.issues = getStringWithColor(issueColor);

        this.triggers = getLines();
        this.connections = generateEventStormingGroups();
        sortEventStormingGroups();
    }

    public static EventStormingBoard createEventStormingBoard(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException, InvalidBoardFormatException {
        return new EventStormingBoard(inputBoard, mappingLog, messages);
    }

    private void sortEventStormingGroups() {
        connections.sort(EventStormingGroup::compareTo);
    }

    private ArrayList<EventStormingGroup> generateEventStormingGroups() {
        ArrayList<EventStormingGroup> output = new ArrayList<>();
        for (Sticker commandSticker : commands) {
            double groupPositionY = commandSticker.getY();
            double groupPositionX = commandSticker.getX();

            String command = commandSticker.getText();

            double width = commandSticker.getWidth();
            double height = commandSticker.getHeight();
            double scaledWidth = width * commandSticker.getScale();
            double scaledHeight = height * commandSticker.getScale();
            /*
            coordinates system notes:
            y-axis is positive downwards
            x-axis is positive rightwards
            sticker coordinates(getY Method) for y-Axis are center values.
            sticker coordinates(getX Method) for x-Axis are center values.
            */



            double xCommandPositionMiddle = commandSticker.getX();
            double xCommandPositionLeftBoundary = xCommandPositionMiddle - (0.5 * scaledWidth);
            double xCommandPositionRightBoundary = xCommandPositionMiddle + (0.5 * scaledWidth);

            double yCommandPositionMiddle = commandSticker.getY();
            double yCommandPositionTop = yCommandPositionMiddle - (0.5 * scaledHeight);
            double yCommandPositionBottom = yCommandPositionMiddle + (0.5 * scaledHeight);

            SearchBoundary domainEventSearchBoundary = new SearchBoundary(xCommandPositionLeftBoundary - (0.5*scaledWidth), xCommandPositionRightBoundary + (1.5 * scaledWidth), yCommandPositionTop - (0.5 * scaledHeight), yCommandPositionBottom + (0.5 * scaledHeight));
            SearchBoundary roleSearchBoundary = new SearchBoundary(xCommandPositionLeftBoundary- (0.5*scaledWidth), xCommandPositionRightBoundary + (2.5 * scaledWidth), yCommandPositionTop - (0.5 * scaledHeight), yCommandPositionBottom + (1.25 * scaledHeight));
            SearchBoundary aggregateSearchBoundary = new SearchBoundary(xCommandPositionLeftBoundary - (0.5* scaledWidth), xCommandPositionRightBoundary + (1.5 * scaledWidth), yCommandPositionTop - (1.5 * scaledHeight), yCommandPositionTop + (0.4 * scaledHeight));

            String localDomainEvent = getTextFromStickerWithCorrectPosition(domainEvents,domainEventSearchBoundary);
            String localRole = getTextFromStickerWithCorrectPosition(userRole, roleSearchBoundary);
            List<String> localAggregates = getTextsFromStickerWithCorrectPosition(aggregates, aggregateSearchBoundary);
            List<String> localTriggers = getTriggers(localDomainEvent);
            if (!localDomainEvent.equals("") && !localAggregates.contains("")) {
                EventStormingGroup eventStormingGroup = new EventStormingGroup(groupPositionX, groupPositionY, localDomainEvent, command, localAggregates, localRole, localTriggers);
                mappingLog.addSuccessLogEntry("Group found with elements: DomainEvent -> " + localDomainEvent + "( triggers " + localTriggers + "), Command -> " + command + ", Aggregate ->" + localAggregates + ", User Role -> " + localRole + ".");
                output.add(eventStormingGroup);
            } else {
                mappingLog.addErrorLogEntry("No grouped elements for " + command + ".");
            }

        }
        if (!domainEvents.isEmpty()) {
            mappingLog.addWarningLogEntry("Some DomainEvent Stickers were not part of a correctly formatted group: " + domainEvents);
            mappingLog.addInfoLogEntry("Take a look at the mapping heuristics (under Supported Templates on the webpage or in the User Guide) and the Tutorials for Event Storming on the webpage for specific Information, which criteria a DomainEvent has to meet to be recognized as part of an EventGroup.");
        }
        if (!userRole.isEmpty()) {
            mappingLog.addWarningLogEntry("Some UserRole Stickers were not part of a correctly formatted group: " + userRole);
            mappingLog.addInfoLogEntry("Take a look at the mapping heuristics (under Supported Templates on the webpage or in the User Guide) and the Tutorials for Event Storming on the webpage for specific Information, which criteria a UserRole has to meet to be recognized as part of an EventGroup.");
        }
        if (!aggregates.isEmpty()) {
            mappingLog.addWarningLogEntry("Some Aggregate Stickers were not part of a correctly formatted group: " + aggregates);
            mappingLog.addInfoLogEntry("Take a look at the mapping heuristics (under Supported Templates on the webpage or in the User Guide) and the Tutorials for Event Storming on the webpage for specific Information, which criteria an Aggregate has to meet to be recognized as part of an EventGroup.");
        }
        return output;
    }


    public List<String> getIssues() {
        ArrayList<String> output = new ArrayList<>();
        for (String s : issues) {
            output.add(StringUtility.validatorForStrings(s));
        }
        return output;
    }

    private List<String> getTriggers(String domainEvent) {
        ArrayList<String> output = new ArrayList<>();
        if (!domainEvent.equals("")) {
            for (ArrayList<String> trigger : triggers) {
                if (trigger.get(0).contains(domainEvent)) {
                    for (String s : trigger) {
                        output.add(s);
                        mappingLog.addSuccessLogEntry("Trigger " + s + " found for " + domainEvent);
                    }
                }
            }
            if (output.isEmpty()) {
                String logString = "No triggers found for " + StringUtility.validatorForStrings(domainEvent) + ". Check if the lines are correctly connected.";
                mappingLog.addErrorLogEntry(logString);
                messages.add(logString);
            }
        }
        return output;
    }

    private String getTextFromStickerWithCorrectPosition(List<Sticker> inputList, SearchBoundary searchBoundary) {
        ArrayList<Sticker> stickerArrayList = new ArrayList<>(inputList);
        ArrayList<Sticker> copiedInputList = SerializationUtils.clone(stickerArrayList);
        for (int i = 0; i < copiedInputList.size(); i++) {
            Sticker innerSticker = copiedInputList.get(i);
            if (innerSticker.getX() > searchBoundary.getLeftBoundary() && innerSticker.getX() < searchBoundary.getRightBoundary() && innerSticker.getY() > searchBoundary.getTopBoundary() && innerSticker.getY() < searchBoundary.getBottomBoundary()) {
                inputList.remove(i);
                return innerSticker.getText();
            }
        }
        return "";
    }

    private List<String> getTextsFromStickerWithCorrectPosition(List<Sticker> inputList, SearchBoundary searchBoundary) {
        ArrayList<Sticker> stickerArrayList = new ArrayList<>(inputList);
        ArrayList<Sticker> copiedInputList = SerializationUtils.clone(stickerArrayList);
        ArrayList<String> output = new ArrayList<>();
        for (Sticker innerSticker : copiedInputList) {
            if (innerSticker.getX() > searchBoundary.getLeftBoundary() && innerSticker.getX() < searchBoundary.getRightBoundary() && innerSticker.getY() > searchBoundary.getTopBoundary() && innerSticker.getY() < searchBoundary.getBottomBoundary()) {
                output.add(innerSticker.getText());
                var stickerToDelete= inputList.stream().filter((Sticker sticker) -> sticker.equals(innerSticker)).findAny();
                stickerToDelete.ifPresent(inputList::remove);
            }
        }
        return output;
    }

    private void getWidth(Sticker widget) {
        width = widget.getWidth();
    }

    private void getHeight(Sticker widget) {
        height = widget.getHeight();
    }


    private ArrayList<ArrayList<String>> getLines() {
        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        for (WidgetObject widget : inputBoard.getWidgetObjects()) {
            if (widget instanceof Line) {
                BigInteger startWidgetId = ((Line) widget).getStartWidgetId();
                BigInteger endWidgetId = ((Line) widget).getEndWidgetId();
                String start = getWidgetText(startWidgetId);
                String end = getWidgetText(endWidgetId);
                mappingLog.addSuccessLogEntry("Connection found: " + start + " triggers " + end);
                if (!exists(lines, start, end) && !start.equals(end)) {
                    ArrayList<String> newConnection = new ArrayList<>();
                    newConnection.add(start);
                    newConnection.add(end);
                    lines.add(newConnection);
                }
            }
        }
        if (lines.isEmpty()) {
            mappingLog.addErrorLogEntry("No connections found.");
            messages.add("No connections found. Check if you use the lines correct.");
        }
        return lines;
    }

    private boolean exists(ArrayList<ArrayList<String>> lines, String start, String end) {
        for (ArrayList<String> text : lines) {
            if (text.get(0).contains(start)) {
                text.add(end);
                return true;
            }
        }
        return false;
    }

    private String getWidgetText(BigInteger start) {
        for (WidgetObject widget : inputBoard.getWidgetObjects()) {
            if (widget.getId().equals(start) && widget instanceof Sticker) {
                return ((Sticker) widget).getText();
            }
        }
        return "";
    }

    private ArrayList<String> getStringWithColor(String color) {
        ArrayList<String> output = new ArrayList<>();
        for (WidgetObject widget : inputBoard.getWidgetObjects()) {
            if (widget instanceof Sticker && (((Sticker) widget).getBackgroundColor().equals(color))) {
                output.add(((Sticker) widget).getText());
            }
        }
        if (output.isEmpty()) {
            mappingLog.addErrorLogEntry("No Issues found. If intended to have Issues, do your Issue Stickers use the same background color as you have previously defined? Please check the mapping rules (under Supported Templates on the webpage or in the User Guide) and the Tutorials for more Information");

            messages.add("No Issues found. If intended to have Issues, do your Issue Stickers use the same background color as you have previously defined? Please check the mapping rules (under Supported Templates on the webpage or in the User Guide) and the Tutorials for more Information");
        }
        return output;
    }

    private List<Sticker> getStickerWithRelevantColor(String color, String stickerType) {
        ArrayList<Sticker> relevantStickers = new ArrayList<>();
        for (WidgetObject widget : inputBoard.getWidgetObjects()) {
            if (widget instanceof Sticker && (((Sticker) widget).getBackgroundColor().equals(color)) && !((Sticker) widget).getMappingRelevantText().equals("")) {
                Sticker sticker = (Sticker) widget;
                relevantStickers.add(sticker);
                mappingLog.addSuccessLogEntry("Identified " + ((Sticker) widget).getText() + " as a(n)" + stickerType);
            }
        }
        if (relevantStickers.isEmpty()) {
            mappingLog.addErrorLogEntry("No " + stickerType + "found.");
            messages.add("No " + stickerType + " found.");
        }
        return relevantStickers;
    }

    private String getColor(String matcher) {
        for (WidgetObject widget : inputBoard.getWidgetObjects()) {
            if (widget instanceof Sticker && (((Sticker) widget).getText().contains(matcher))) {
                if (matcher.equals(DOMAIN_EVENT_P)) {
                    getHeight((Sticker) widget);
                    getWidth((Sticker) widget);
                }
                String color = ((Sticker) widget).getBackgroundColor();
                inputBoard.getWidgetObjects().remove(widget);
                mappingLog.addSuccessLogEntry(matcher + " found.");
                return color;
            }
        }

        mappingLog.addErrorLogEntry(matcher + " not found.");
        messages.add("Sticker " + matcher + "  not found, there are mapping errors possible. Make sure you use the template correct!");
        return "";
    }

    private void checkInputFields() throws WrongBoardException, InvalidBoardFormatException {
        TreeMap<String, String> colorMap = new TreeMap<>();
        List<Pair<String, String>> colorList = Arrays.asList(new ImmutablePair<>(domainEventColor, "DomainEvent"),
                new ImmutablePair<>(commandColor, "Command"),
                new ImmutablePair<>(aggregateColor, "Aggregates"),
                new ImmutablePair<>(userRoleColor, "User Role"),
                new ImmutablePair<>(issueColor, "Issue"));

        for (Pair<String, String> stickerIdentifier : colorList) {
            if (stickerIdentifier.getLeft().equals("")) {
                throw new WrongBoardException("Input Board doesn't match with expected Board Type: Event Storming. Check if you have created a correctly formatted sticker to set the color for " + stickerIdentifier.getRight() + " Stickers.");
            }
            if (colorMap.get(stickerIdentifier.getLeft()) == null) {
                colorMap.put(stickerIdentifier.getLeft(), stickerIdentifier.getRight());
            } else {
                throw new InvalidBoardFormatException("Sticker color collision, " + stickerIdentifier.getRight() + "Stickers have the same color ("+stickerIdentifier.getLeft()+") as " + colorMap.get(stickerIdentifier.getLeft()) + " Stickers.");
            }
        }
    }

    public List<EventStormingGroup> getConnections() {
        return connections;
    }

    private List<Sticker> uniquifyStickerContent(List<Sticker> stickers){
        ArrayList<Sticker> output = new ArrayList<>();

        for (Sticker currentSticker : stickers){
            if(output.stream().anyMatch(sticker->sticker.getMappingRelevantText().equals(currentSticker.getMappingRelevantText()))){
                setUniqueStickerContent(output,currentSticker);
                output.add(currentSticker);
            }else{
                output.add(currentSticker);
            }
        }
        return output;
    }
    private void setUniqueStickerContent(List<Sticker> stickers, Sticker inputSticker){
        int counter = 0;
        String originalStickerContent = inputSticker.getMappingRelevantText();
        while (stickers.stream().anyMatch(sticker->sticker.getMappingRelevantText().equals(inputSticker.getMappingRelevantText()))){
            inputSticker.setMappingRelevantText(originalStickerContent+ ++counter);
        }
    }

    @Override
    public String toString() {
        return "EventStormingBoard{" +
                "inputBoard=" + inputBoard +
                ", domainEventColor='" + domainEventColor + '\'' +
                ", commandColor='" + commandColor + '\'' +
                ", aggregateColor='" + aggregateColor + '\'' +
                ", issueColor='" + issueColor + '\'' +
                ", userRoleColor='" + userRoleColor + '\'' +
                ", domainEvents=" + domainEvents +
                ", commands=" + commands +
                ", aggregates=" + aggregates +
                ", userRole=" + userRole +
                ", issues=" + issues +
                ", triggers=" + triggers +
                '}';
    }
}
