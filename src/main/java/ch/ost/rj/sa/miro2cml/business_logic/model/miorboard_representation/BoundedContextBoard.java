package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.model.widgets.Shape;
import ch.ost.rj.sa.miro2cml.model.widgets.Text;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;

import java.util.ArrayList;

public class BoundedContextBoard {
    //TODO: move static Variables
    public static final String DESCRIPTION = "Description";
    public static final String STRATEGIC_CLASSIFICATION = "Strategic Classification";
    public static final String INBOUND_COMMUNICATION = "Inbound Communication";
    public static final String OUTBOUND_COMMUNICATION = "Outbound Communication";
    private static final String QUERY_COLOR = "#f0f7a9";
    private static final String COMMAND_COLOR = "#cbdcee";
    private static final String EVENT_COLOR = "#f9f3c1";
    private static final String DECISION_COLOR ="#dcd4e6";
    private static final String UBIQUITOUS_LANGUAGE_COLOR = "#f4f4f4";
    private static final String SEARCH_NAME = "Name:";
    private static final String SEARCH_DOMAIN = "Domain";
    private static final String SEARCH_BUSINESSMODEL = "Business Model";
    private static final String SEARCH_EVOLUTION = "Evolution";
    private static final String SEARCH_ROLETYPES = "Role Types";
    private static final String EVENT_EXAMPLE="<p><strong>&lt;Event&gt;</strong></p>";
    private static final String COMMAND_EXAMPLE = "<p><strong>&lt;Command&gt;</strong></p>";
    private static final String QUERY_EXAMPLE = "<p><strong>&lt;Query&gt;</strong></p>";
    private InputBoard inputBoard;
    private final int middle;
    private final int top;
    private final int bottom;
    private final int right;
    private String name, description, domain, businessModel, evolution, roleTypes, aggregateName;
    private ArrayList<String> queries;
    private ArrayList<String> commands;
    private ArrayList<String> events;
    private ArrayList<String> outBoundCommunication;
    private ArrayList<String> businessDescisions;
    private ArrayList<String> ubiquitousLanguage;

    private BoundedContextBoard(InputBoard inputBoard) {
        this.inputBoard = inputBoard;
        this.middle = getMiddle();
        this.top = getTop();
        this.bottom=getBottom();
        this.right=getRight();
        this.name = searchForString(SEARCH_NAME);
        this.description = extractDescription();
        this.domain = searchForString(SEARCH_DOMAIN);
        this.businessModel = searchForString(SEARCH_BUSINESSMODEL);
        this.evolution = searchForString(SEARCH_EVOLUTION);
        this.roleTypes = searchForString(SEARCH_ROLETYPES);
        this.aggregateName = " Aggregate";
        this.queries = searchForColor(QUERY_COLOR);
        this.commands = searchForColor(COMMAND_COLOR);
        this.events = searchForColor(EVENT_COLOR);
        this.businessDescisions = searchForColor(DECISION_COLOR);
        this.ubiquitousLanguage= searchForColor(UBIQUITOUS_LANGUAGE_COLOR);
        this.outBoundCommunication = extractOutboundCommunication();
    }

    public static BoundedContextBoard createBoundedContextBoard(InputBoard inputBoard) {
        return new BoundedContextBoard(inputBoard);
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
        return widget instanceof Text && (((Text)widget).getY() > top && ((Text)widget).getY() < bottom && ((Text)widget).getX() < right);
    }

    private ArrayList<String> extractOutboundCommunication() {
        ArrayList<String> outputArray= new ArrayList<>();
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if(isAOutboundCommunicationAndNotExample(widget)){
                outputArray.add(((Shape)widget).getText());
            }
        }
        return outputArray;
    }
    private boolean isAOutboundCommunication(WidgetObject widget) {
        return widget instanceof Shape && (((Shape)widget).getX() > middle);
    }
    private boolean isAOutboundCommunicationAndNotExample(WidgetObject widget){
        return isAOutboundCommunication(widget)&& isNotCardExample(((Shape)widget).getText());
    }

    private ArrayList<String> searchForColor(String color){
        ArrayList<String> outputArray= new ArrayList<>();
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if(isInboundWithColorAndNotExample(color, widget)){
                outputArray.add(((Shape)widget).getText());
            }
        }
        return outputArray;
    }

    private boolean isInboundWithColorAndNotExample(String color, WidgetObject widget) {
        return isInboundCommunication(color, widget) && isNotCardExample(((Shape)widget).getText());
    }

    private boolean isInboundCommunication(String color, WidgetObject widget) {
        return widget instanceof Shape && ((Shape) widget).getBackgroundColor().equals(color) && ((Shape) widget).getX() < middle;
    }

    private boolean isNotCardExample(String text) {
        return !((text.equals(EVENT_EXAMPLE) || text.equals(COMMAND_EXAMPLE)
                || text.equals(QUERY_EXAMPLE)));
    }

    private int getMiddle() {
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if((isaBoolean(OUTBOUND_COMMUNICATION, widget))){
                return ((Text) widget).getX();
            }
        }
        return 0;
    }
    private int getBottom() {
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if((isaBoolean(INBOUND_COMMUNICATION, widget))){
                return ((Text) widget).getY();
            }
        }
        return 0;
    }
    private int getTop() {
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if((isaBoolean(DESCRIPTION, widget))){
                return ((Text) widget).getY();
            }
        }
        return 0;
    }
    private int getRight() {
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if((isaBoolean(STRATEGIC_CLASSIFICATION, widget))){
                return ((Text) widget).getX();
            }
        }
        return 0;
    }

    private String searchForString(String regex){
        for(WidgetObject widget: inputBoard.getWidgetObjects()){
            if(isaBoolean(regex, widget)){
                return widget.getMappingRelevantText();
            }
        }
        return null;
    }

    private boolean isaBoolean(String regex, WidgetObject widget) {
        return widget instanceof Text && ((Text) widget).getText().contains(regex);
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
