package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.model.widgets.Line;
import ch.ost.rj.sa.miro2cml.model.widgets.Sticker;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private ArrayList<Sticker> domainEvents;
    private ArrayList<Sticker> commands;
    private ArrayList<Sticker> aggregates;
    private ArrayList<Sticker> userRole;
    private ArrayList<String> issues;
    private Map<String, String> triggers;
    private double height, width;
    private ArrayList<EventStormingGroup> connections;

    public List<String> getIssues() {
        ArrayList<String> output = new ArrayList<>();
        for (String s: issues) {
            output.add(StringValidator.validatorForStrings(s));
        }
        return output;
    }

    public ArrayList<EventStormingGroup> getConnections() {
        return connections;
    }

    private EventStormingBoard(InputBoard inputBoard) {
        this.inputBoard = inputBoard;
        this.domainEventColor = getColor(DOMAIN_EVENT_P);
        this.commandColor = getColor(COMMAND_P);
        this.aggregateColor = getColor(AGGREGATE_P);
        this.issueColor = getColor(ISSUE_P);
        this.userRoleColor = getColor(USER_ROLE_P);
        this.domainEvents = getStickerWithRelevantColor(domainEventColor);
        this.commands = getStickerWithRelevantColor(commandColor);
        this.aggregates = getStickerWithRelevantColor(aggregateColor);
        this.userRole = getStickerWithRelevantColor(userRoleColor);
        this.issues = getStringWithColor(issueColor);
        this.triggers = getLines();
        this.height = getHeight();
        this.width = getWidth();
        this.connections = generateMap();
    }


    private ArrayList<EventStormingGroup> generateMap() {
        ArrayList<EventStormingGroup> output = new ArrayList<>();
        for (Sticker sticker: commands) {
            String command = sticker.getText();
            double xStart = sticker.getX();
            double yMiddle = sticker.getY();
            //TODO: testing with other examples if constants work well
            double xEnd= xStart+ (1.2*width);
            double yStart = yMiddle - 0.5*height;
            double yEnd = yMiddle+ (0.5 *height);
            String domainEvent= getTextFromStickerWithCorrectPosition(domainEvents, xStart, xEnd, yStart, yEnd);
            String role= getTextFromStickerWithCorrectPosition(userRole, xStart, xEnd, yStart, yEnd);
            String aggregate= getTextFromStickerWithCorrectPosition(aggregates, xStart, xEnd, yStart, yEnd);
            String trigger = getTrigger(domainEvent);
            EventStormingGroup eventStormingGroup = new EventStormingGroup(domainEvent, command,  aggregate, role, trigger );
            output.add(eventStormingGroup);
        }

        return output;
    }

    private String getTrigger(String domainEvent) {
        if (triggers.containsKey(domainEvent)) {
            for ( Map.Entry<String, String> entry : triggers.entrySet()) {
                if(entry.getKey().equals(domainEvent)){
                    return entry.getValue();
                }
            }
        }
        return "";
    }

    private String getTextFromStickerWithCorrectPosition(ArrayList<Sticker> inputList, double xStart, double xEnd, double yStart, double yEnd ){
        for (Sticker innerSticker : inputList) {
            if(innerSticker.getX() > xStart && innerSticker.getX() < xEnd && innerSticker.getY() > yStart && innerSticker.getY() < yEnd){
                return innerSticker.getText();
            }
        }
        return "";
    }

    private int getWidth() {
        return (int) domainEvents.get(0).getWidth();
    }

    private int getHeight() {
        return (int) domainEvents.get(0).getHeight();
    }

    public static EventStormingBoard createEventStormingBoard(InputBoard inputBoard) {
        return new EventStormingBoard(inputBoard);
    }

    private Map<String, String> getLines() {
        Map<String, String> lines = new HashMap<>();
        for (WidgetObject widget:inputBoard.getWidgetObjects()) {
            if(widget instanceof Line){
                BigInteger startWidgetId = ((Line) widget).getStartWidgetId();
                BigInteger endWidgetId = ((Line) widget).getEndWidgetId();
                String start = getWidgetText(startWidgetId);
                String end = getWidgetText(endWidgetId);
                lines.put(start, end);
            }
        }
        return lines;
    }

    private String getWidgetText(BigInteger start) {
        for (WidgetObject widget:inputBoard.getWidgetObjects()) {
            if(widget.getId().equals(start)){
                return widget.getMappingRelevantText();
            }
        }
        return "";
    }

    private ArrayList<String> getStringWithColor(String color) {
        ArrayList<String> output = new ArrayList<>();
        for (WidgetObject widget:inputBoard.getWidgetObjects()) {
            if(widget instanceof Sticker && (((Sticker) widget).getBackgroundColor().equals(color))){
                output.add(((Sticker) widget).getText());
            }
        }
        return output;
    }

    private ArrayList<Sticker> getStickerWithRelevantColor(String color) {
        ArrayList<Sticker> output = new ArrayList<>();
        for (WidgetObject widget:inputBoard.getWidgetObjects()) {
            if(widget instanceof Sticker && (((Sticker) widget).getBackgroundColor().equals(color))){
                output.add((Sticker)widget);
            }
        }
        return output;
    }

    private String getColor(String matcher) {
        for (WidgetObject widget:inputBoard.getWidgetObjects()) {
            if(widget instanceof Sticker && (((Sticker) widget).getText().contains(matcher))){
                String color = ((Sticker)widget).getBackgroundColor();
                inputBoard.getWidgetObjects().remove(widget);
                return color;
            }
        }
        return "";
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
