package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.business_logic.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.model.widgets.Line;
import ch.ost.rj.sa.miro2cml.model.widgets.Sticker;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;

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
    private MappingLog mappingLog;
    private MappingMessages messages;
    private ArrayList<Sticker> domainEvents;
    private ArrayList<Sticker> commands;
    private ArrayList<Sticker> aggregates;
    private ArrayList<Sticker> userRole;
    private ArrayList<String> issues;
    private ArrayList<ArrayList<String>> triggers;
    private double height, width;
    private ArrayList<EventStormingGroup> connections;

    private EventStormingBoard(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException {
        this.mappingLog = mappingLog;
        this.messages = messages;
        this.inputBoard = inputBoard;
        this.domainEventColor = getColor(DOMAIN_EVENT_P);
        this.commandColor = getColor(COMMAND_P);
        this.aggregateColor = getColor(AGGREGATE_P);
        this.issueColor = getColor(ISSUE_P);
        this.userRoleColor = getColor(USER_ROLE_P);
        checkInputFields();
        this.domainEvents = getStickerWithRelevantColor(domainEventColor, "Domain Events ");
        this.commands = getStickerWithRelevantColor(commandColor, "Commands ");
        this.aggregates = getStickerWithRelevantColor(aggregateColor, "Aggregates ");
        this.userRole = getStickerWithRelevantColor(userRoleColor, "UserRoles ");
        this.issues = getStringWithColor(issueColor);
        this.triggers = getLines();
        this.connections = generateMap();
        sortEventStormingGroups();
    }

    private void sortEventStormingGroups() {
        connections.sort(EventStormingGroup::compareTo);
    }

    public static EventStormingBoard createEventStormingBoard(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException {
        return new EventStormingBoard(inputBoard, mappingLog, messages);
    }

    private ArrayList<EventStormingGroup> generateMap() {
        ArrayList<EventStormingGroup> output = new ArrayList<>();
        for (Sticker sticker: commands) {
            String command = sticker.getText();
            double xStart = sticker.getX();
            double yMiddle = sticker.getY();
            double xEnd= xStart+ (2.5*width);
            double yStart = yMiddle - 0.5*height;
            double yEnd = yMiddle+ (0.5 *height);
            double position = sticker.getX();
            String domainEvent= getTextFromStickerWithCorrectPosition(domainEvents, xStart, xEnd, yStart, yEnd);
            String role= getTextFromStickerWithCorrectPosition(userRole, xStart, xEnd+(2*width), yStart-(1*height), yEnd);
            List<String> aggregate= getTextsFromStickerWithCorrectPosition(aggregates, xStart, xEnd, yStart-(2*height), yEnd);
            List<String> trigger = getTrigger(domainEvent);
            if(!domainEvent.equals("") && !aggregate.contains("")){
                EventStormingGroup eventStormingGroup = new EventStormingGroup(position, domainEvent, command,  aggregate, role, trigger);
                mappingLog.addSuccessLogEntry("Group found with elements: DomainEvent -> "+domainEvent+"( triggers "+trigger+"), Command -> "+command+", Aggregate ->"+ aggregate+", User Role -> "+role+".");
                output.add(eventStormingGroup);
            }else{
                mappingLog.addErrorLogEntry("No groups elements for "+command+".");
            }

        }

        return output;
    }


    public List<String> getIssues() {
        ArrayList<String> output = new ArrayList<>();
        for (String s: issues) {
            output.add(StringValidator.validatorForStrings(s));
        }
        return output;
    }

    private List<String> getTrigger(String domainEvent) {
        ArrayList<String> output=new ArrayList<>();
        if(!domainEvent.equals("")){
            for(ArrayList<String>  text: triggers){
                if(text.get(0).contains(domainEvent)){
                    for (String s : text) {
                        output.add(s);
                        mappingLog.addSuccessLogEntry("Trigger "+s+" found for "+domainEvent);
                    }
                }
            }
            if(output.isEmpty()){
                mappingLog.addErrorLogEntry("For "+domainEvent+" no triggers found");
                messages.add("For "+StringValidator.validatorForStrings(domainEvent)+" no triggers found. Check if the lines are correct connected.");
            }
        }
        return output;
    }

    private String getTextFromStickerWithCorrectPosition(ArrayList<Sticker> inputList, double xStart, double xEnd, double yStart, double yEnd ){
        for (Sticker innerSticker : inputList) {
            if(innerSticker.getX() > xStart && innerSticker.getX() < xEnd && innerSticker.getY() > yStart && innerSticker.getY() < yEnd){
                return innerSticker.getText();
            }
        }
        return "";
    }

    private List<String> getTextsFromStickerWithCorrectPosition(ArrayList<Sticker> inputList, double xStart, double xEnd, double yStart, double yEnd) {
        ArrayList<String> output = new ArrayList<>();
        for (Sticker innerSticker : inputList) {
            if(innerSticker.getX() > xStart && innerSticker.getX() < xEnd && innerSticker.getY() > yStart && innerSticker.getY() < yEnd){
                output.add(innerSticker.getText());
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
        for (WidgetObject widget:inputBoard.getWidgetObjects()) {
            if(widget instanceof Line){
                BigInteger startWidgetId = ((Line) widget).getStartWidgetId();
                BigInteger endWidgetId = ((Line) widget).getEndWidgetId();
                String start = getWidgetText(startWidgetId);
                String end = getWidgetText(endWidgetId);
                mappingLog.addSuccessLogEntry("Connection found: "+start + " triggers " + end);
                if(!exists(lines, start, end) && !start.equals(end)){
                    ArrayList<String> newConnection = new ArrayList<>();
                    newConnection.add(start);
                    newConnection.add(end);
                    lines.add(newConnection);
                }
            }
        }
        if(lines.isEmpty()){
            mappingLog.addErrorLogEntry("No connections found.");
            messages.add("No connections found. Check if you use the lines correct.");
        }
        return lines;
    }

    private boolean exists(ArrayList<ArrayList<String>> lines, String start, String end){
        for(ArrayList<String> text: lines) {
            if (text.get(0).contains(start)) {
                text.add(end);
                return true;
            }
        }
        return false;
    }

    private String getWidgetText(BigInteger start) {
        for (WidgetObject widget:inputBoard.getWidgetObjects()) {
            if(widget.getId().equals(start) && widget instanceof Sticker){
                return ((Sticker) widget).getText();
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
        if(output.isEmpty()){
            mappingLog.addErrorLogEntry("No Issues found");
            messages.add("No Issues found. Check if you use any Issue Stickers.");
        }
        return output;
    }

    private ArrayList<Sticker> getStickerWithRelevantColor(String color, String event) {
        ArrayList<Sticker> output = new ArrayList<>();
        for (WidgetObject widget:inputBoard.getWidgetObjects()) {
            if(widget instanceof Sticker && (((Sticker) widget).getBackgroundColor().equals(color)) && !widget.getMappingRelevantText().equals("")){
                output.add((Sticker)widget);
                mappingLog.addSuccessLogEntry(event + " add " + ((Sticker) widget).getText());
            }
        }
        if(output.isEmpty()){
            mappingLog.addErrorLogEntry("No "+event+"found.");
            messages.add("No "+event+" found.");
        }
        return output;
    }

    private String getColor(String matcher) {
        for (WidgetObject widget:inputBoard.getWidgetObjects()) {
            if(widget instanceof Sticker && (((Sticker) widget).getText().contains(matcher))){
                if(matcher.equals(DOMAIN_EVENT_P)){
                    getHeight((Sticker) widget);
                    getWidth((Sticker) widget);
                }
                String color = ((Sticker)widget).getBackgroundColor();
                inputBoard.getWidgetObjects().remove(widget);
                mappingLog.addSuccessLogEntry(matcher + " found.");
                return color;
            }
        }

        mappingLog.addErrorLogEntry(matcher+ " not found.");
        messages.add("Sticker " + matcher + "  not found, there are mapping errors possible. Make sure you use the template correct!");
        return "";
    }

    private void checkInputFields() throws WrongBoardException{
        HashSet<String> colors = new HashSet<>();
        colors.add(domainEventColor);
        colors.add(commandColor);
        colors.add(aggregateColor);
        colors.add(userRoleColor);
        colors.add(issueColor);
        if(domainEventColor.equals("") || commandColor.equals("") || aggregateColor.equals("")|| commandColor.equals(domainEventColor) || aggregateColor.equals(domainEventColor) || aggregateColor.equals(commandColor) ||colors.size()<4){
            throw new WrongBoardException("Input Board doesn't match with expected Board Type: Event Storming");
        }
    }

    public ArrayList<EventStormingGroup> getConnections() {
        return connections;
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
