package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.model.widgets.Shape;
import ch.ost.rj.sa.miro2cml.model.widgets.Text;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;

import java.util.ArrayList;

public class BoundedContextBoard {
    //TODO: move static Variables
    public static final String DESCRIPTION = "Description";
    public static final String INBOUND_COMMUNICATION = "Inbound Communication";
    private static final String QUERY_COLOR = "#f0f7a9";
    private static final String COMMAND_COLOR = "#cbdcee";
    private static final String EVENT_COLOR = "#f9f3c1";
    private static final String DECISION_COLOR ="#dcd4e6";
    private static final String UBIQUITOUS_LANGUAGE_COLOR = "#f4f4f4";
    private static final String SEARCH_NAME = "Name:";
    private static final String SEARCH_DOMAIN = "<strong>Domain</strong>";
    private static final String SEARCH_BUSINESSMODEL = "<strong>Business Model</strong>";
    private static final String SEARCH_EVOLUTION = "<strong>Evolution</strong>";
    private static final String SEARCH_ROLETYPES = "<strong>Role Types</strong>";
    private static final String EVENT_EXAMPLE="<p><strong>&lt;Event&gt;</strong></p>";
    private static final String COMMAND_EXAMPLE = "<p><strong>&lt;Command&gt;</strong></p>";
    private static final String QUERY_EXAMPLE = "<p><strong>&lt;Query&gt;</strong></p>";
    private InputBoard inputBoard;
    private final int middle;
    private final int top;
    private final int bottom;
    private String name, description, domain, businessModel, evolution, roleTypes, aggregateName;
    private ArrayList<String> queries;
    private ArrayList<String> commands;
    private ArrayList<String> events;
    private ArrayList<String> outBoundCommunication;
    private ArrayList<String> businessDescisions;
    private ArrayList<String> ubiquitousLanguage;
    private MappingMessages messages;
    private MappingLog mappingLog;

    private BoundedContextBoard(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException {
        this.mappingLog = mappingLog;
        this.messages = messages;
        this.inputBoard = inputBoard;
        this.top = getTop();
        this.bottom=getBottom();
        this.middle = getMiddle();
        isCorrectInputBoard();
        this.name = searchForString(SEARCH_NAME);
        this.description = extractDescription();
        this.domain = searchForString(SEARCH_DOMAIN);
        this.businessModel = searchForString(SEARCH_BUSINESSMODEL);
        this.evolution = searchForString(SEARCH_EVOLUTION);
        this.roleTypes = searchForString(SEARCH_ROLETYPES);
        this.aggregateName = " Aggregate";
        this.queries = searchForColor(QUERY_COLOR, 1);
        this.commands = searchForColor(COMMAND_COLOR, 1);
        this.events = searchForColor(EVENT_COLOR,1);
        this.businessDescisions = searchForColor(DECISION_COLOR, 2);
        this.ubiquitousLanguage= searchForColor(UBIQUITOUS_LANGUAGE_COLOR, 2);
        this.outBoundCommunication = extractOutboundCommunication();
    }

    public static BoundedContextBoard createBoundedContextBoard(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws Exception {
        return new BoundedContextBoard(inputBoard, mappingLog, messages);
    }

    public InputBoard getInputBoard() {
        return inputBoard;
    }

    public void setInputBoard(InputBoard inputBoard) {
        this.inputBoard = inputBoard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBusinessModel() {
        return businessModel;
    }

    public void setBusinessModel(String businessModel) {
        this.businessModel = businessModel;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public String getRoleTypes() {
        return roleTypes;
    }

    public void setRoleTypes(String roleTypes) {
        this.roleTypes = roleTypes;
    }

    public String getAggregateName() {
        return aggregateName;
    }

    public void setAggregateName(String aggregateName) {
        this.aggregateName = aggregateName;
    }

    public ArrayList<String> getQueries() {
        return queries;
    }

    public void setQueries(ArrayList<String> queries) {
        this.queries = queries;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<String> commands) {
        this.commands = commands;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }

    public ArrayList<String> getOutBoundCommunication() {
        return outBoundCommunication;
    }

    public void setOutBoundCommunication(ArrayList<String> outBoundCommunication) {
        this.outBoundCommunication = outBoundCommunication;
    }

    public ArrayList<String> getBusinessDescisions() {
        return businessDescisions;
    }

    public void setBusinessDescisions(ArrayList<String> businessDescisions) {
        this.businessDescisions = businessDescisions;
    }

    public ArrayList<String> getUbiquitousLanguage() {
        return ubiquitousLanguage;
    }

    public void setUbiquitousLanguage(ArrayList<String> ubiquitousLanguage) {
        this.ubiquitousLanguage = ubiquitousLanguage;
    }

    private String extractDescription() {
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if(isDescription(widget)){
                return ((Text)widget).getText();
            }
        }
        return "";
    }

    private boolean isDescription(WidgetObject widget) {
        return widget instanceof Text && (((Text)widget).getY() > top && ((Text)widget).getY() < bottom && ((Text)widget).getX() < middle);
    }

    private ArrayList<String> extractOutboundCommunication() {
        ArrayList<String> outputArray= new ArrayList<>();
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if(isAOutboundCommunicationAndNotExample(widget)){
                outputArray.add(((Shape)widget).getText());
                mappingLog.addSuccessLogEntry("Add Inbound Communication: "+ ((Shape)widget).getText());
            }
        }
        if(outputArray.isEmpty()){
            mappingLog.addErrorLogEntry("No Outbound Communications found");
        }
        return outputArray;
    }
    private boolean isAOutboundCommunication(WidgetObject widget) {
        return widget instanceof Shape && (((Shape)widget).getX() > middle) && ( ((Shape)widget).getBackgroundColor().equals(EVENT_COLOR) || ((Shape)widget).getBackgroundColor().equals(COMMAND_COLOR)|| ((Shape)widget).getBackgroundColor().equals(QUERY_COLOR));
    }
    private boolean isAOutboundCommunicationAndNotExample(WidgetObject widget){
        return isAOutboundCommunication(widget)&& isNotCardExample(((Shape)widget).getText());
    }

    private ArrayList<String> searchForColor(String color, int condition){
        ArrayList<String> outputArray= new ArrayList<>();
        boolean matchCondition = false;
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if(condition ==1){
                matchCondition=isInboundWithColorAndNotExample(color, widget);
            }
            else if(condition==2){
                matchCondition=isUbiquitousLanguageOrBusinessDecision(color, widget);
            }
            if(matchCondition){
                mappingLog.addSuccessLogEntry("Element with color: "+ color + " found");
                outputArray.add(((Shape)widget).getText());
            }
        }
        if(outputArray.isEmpty()){
            mappingLog.addErrorLogEntry("No Elements with color: "+color + " found");
        }
        return outputArray;
    }

    private boolean isUbiquitousLanguageOrBusinessDecision(String color, WidgetObject widget) {
        return widget instanceof Shape && ((Shape) widget).getBackgroundColor().equals(color);
    }

    private boolean isInboundWithColorAndNotExample(String color, WidgetObject widget) {
        return isInboundCommunication(color, widget) && isNotCardExample(((Shape)widget).getText());
    }

    private boolean isInboundCommunication(String color, WidgetObject widget) {
        return widget instanceof Shape && ((Shape) widget).getBackgroundColor().equals(color) && ((Shape) widget).getX() < middle;
    }

    private boolean isNotCardExample(String text) {
        if(text != null){
            return !(text.equals(EVENT_EXAMPLE) || text.equals(COMMAND_EXAMPLE)
                    || text.equals(QUERY_EXAMPLE));
        }else {
            return false;
        }
    }

    private int getBottom() {
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if((isaBoolean(INBOUND_COMMUNICATION, widget))){
                mappingLog.addSuccessLogEntry("Field Inbound Communication found");
                return ((Text) widget).getY();
            }
        }
        mappingLog.addErrorLogEntry("Field Inbound Communication not found");
        messages.add("Field Inbound Communication not found, there are mapping errors possible. Make sure you use the template correct!");
        return 0;
    }
    private int getTop() {
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if((isaBoolean(DESCRIPTION, widget))){
                mappingLog.addSuccessLogEntry("Field Description found");
                return ((Text) widget).getY();
            }
        }
        mappingLog.addErrorLogEntry("Field Description not found");
        messages.add("Field Description not found, there are mapping errors possible. Make sure you use the template correct!");
        return 0;
    }
    private int getMiddle() {
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if((isaBoolean(SEARCH_DOMAIN, widget))){
                mappingLog.addSuccessLogEntry("Field Domain in Strateic Classification found");
                return ((Text) widget).getX();
            }
        }
        mappingLog.addErrorLogEntry("Field Domain in Strategic Classification not found");
        messages.add("Field Strategic Classification not found, there are mapping errors possible. Make sure you use the template correct!");
        return 0;
    }

    private String searchForString(String regex){
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if(isaBoolean(regex, widget)){
                mappingLog.addSuccessLogEntry(regex + " found");
                return widget.getMappingRelevantText();
            }
        }
        mappingLog.addErrorLogEntry(regex + " not found");
        return "";
    }

    private boolean isaBoolean(String regex, WidgetObject widget) {
        return widget instanceof Text && ((Text) widget).getText().contains(regex);
    }

    private void isCorrectInputBoard() throws WrongBoardException {
        if(middle==0 && top==0 && bottom==0){
            throw new WrongBoardException("Input Board doesn't match with expected Board Type: Bounded Context Canvas");
        }
    }


    @Override
    public String toString() {
        return "BoundedContextBoard{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", domain='" + domain + '\'' +
                ", businessModel='" + businessModel + '\'' +
                ", evolution='" + evolution + '\'' +
                ", roletypes='" + roleTypes + '\'' +
                ", aggregateName='" + aggregateName + '\'' +
                ", queries=" + queries +
                ", commands=" + commands +
                ", events=" + events +
                ", outBoundCommunication=" + outBoundCommunication +
                ", businessDescisions=" + businessDescisions +
                ", ubiquitousLanguage=" + ubiquitousLanguage +
                '}';
    }
}
