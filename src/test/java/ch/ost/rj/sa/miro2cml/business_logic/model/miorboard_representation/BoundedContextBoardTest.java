package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.model.widgets.Shape;
import ch.ost.rj.sa.miro2cml.model.widgets.Text;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoundedContextBoardTest {

    @Test
    void createBoundedContextBoard_Name() {
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        //add name
        widgetObjects.add(new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Name: Test</strong></p>", "", 0, ""));
        InputBoard board = new InputBoard("123", widgetObjects);

        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
        String expectedName = "<p><strong>Name: Test</strong></p>";

        assertEquals(expectedName, boundedContextBoard.getName());
    }
    @Test
    void createBoundedContext_Description() {
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        //add description
        widgetObjects.add(new Text(BigInteger.ONE, 20, 15, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>What benefits does this context provide, and how does it provide them?</p>", "", 0, ""));
        //add description tag
        widgetObjects.add(new Text(BigInteger.ONE, 5, 10, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>Description</p>", "", 0, ""));
        //add strategic classification tag
        widgetObjects.add(new Text(BigInteger.ONE, 30, 10, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>Strategic Classification</p>", "", 0, ""));
        //add inbound Communication tag
        widgetObjects.add(new Text(BigInteger.ONE, 30, 20, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>Inbound Communication</p>", "", 0, ""));
        InputBoard board = new InputBoard("123", widgetObjects);
        String expextedDescription = "<p>What benefits does this context provide, and how does it provide them?</p>";
        //run
        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
        //check
        assertEquals(expextedDescription, boundedContextBoard.getDescription());
    }
    @Test
    void createBoundedContextBoard_Domain() {
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        //add domain
        widgetObjects.add(new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Domain</strong></p><p>- core</p><p>- supporting</p><p>- generic</p><p>- other?</p>", "", 0, ""));
        InputBoard board = new InputBoard("123", widgetObjects);

        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
        String expectedDomain = "<p><strong>Domain</strong></p><p>- core</p><p>- supporting</p><p>- generic</p><p>- other?</p>";

        assertEquals(expectedDomain, boundedContextBoard.getDomain());
    }
    @Test
    void createBoundedContextBoard_BusinessModel() {
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        //add BusinessModel
        widgetObjects.add(new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Business Model</strong></p><p><span>- revenue</span></p><p>- engagement</p><p>- compliance</p><p>- cost reduction</p>", "", 0, ""));
        InputBoard board = new InputBoard("123", widgetObjects);

        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
        String expectedBusinessModel = "<p><strong>Business Model</strong></p><p><span>- revenue</span></p><p>- engagement</p><p>- compliance</p><p>- cost reduction</p>";

        assertEquals(expectedBusinessModel, boundedContextBoard.getBusinessModel());
    }
    @Test
    void createBoundedContextBoard_Evolution() {
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        //add Evolution
        widgetObjects.add(new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Evolution</strong></p><p><span>- genesis</span></p><p>- custom built</p><p>- product</p><p>- commodity</p>", "", 0, ""));
        InputBoard board = new InputBoard("123", widgetObjects);

        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
        String expectedEvolution="<p><strong>Evolution</strong></p><p><span>- genesis</span></p><p>- custom built</p><p>- product</p><p>- commodity</p>";

        assertEquals(expectedEvolution, boundedContextBoard.getEvolution());
    }
    @Test
    void createBoundedContextBoard_RoleTypes() {
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        //add RoleTypes
        widgetObjects.add(new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Role Types</strong></p><p><span>- draft context</span></p><p>- execution context</p><p>- analysis context</p><p>- gateway context</p><p>- other</p>", "", 0, ""));
        InputBoard board = new InputBoard("123", widgetObjects);

        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
        String expectedRoletypes="<p><strong>Role Types</strong></p><p><span>- draft context</span></p><p>- execution context</p><p>- analysis context</p><p>- gateway context</p><p>- other</p>";

        assertEquals(expectedRoletypes, boundedContextBoard.getRoleTypes());
    }
    @Test
    void createBoundedContextBoard_Commands(){
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        //add Outbound Tag
        widgetObjects.add(new Text(BigInteger.ONE, 300, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "Outbound Communication", "", 0, ""));
        // add command
        widgetObjects.add(new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "#cbdcee", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>First Command</p>"));
        widgetObjects.add(new Shape(BigInteger.ONE, 350, 0, 0, 0, 0, "#cbdcee", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>Second Command</p>"));
        InputBoard board = new InputBoard("123", widgetObjects);

        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
        String expectedCommand = "<p>First Command</p>";
        assertEquals(expectedCommand, boundedContextBoard.getCommands().get(0));
        assertEquals(1, boundedContextBoard.getCommands().size());
    }
    @Test
    void createBounded_DomainEvent(){
            //setup
            ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
            //add Outbound Tag
            widgetObjects.add(new Text(BigInteger.ONE, 300, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "Outbound Communication", "", 0, ""));
            // add events
            widgetObjects.add(new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "#f9f3c1", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>First Event</p>"));
            widgetObjects.add(new Shape(BigInteger.ONE, 350, 0, 0, 0, 0, "#f9f3c1", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>Second Event</p>"));
            InputBoard board = new InputBoard("123", widgetObjects);

            BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
            String expectedEvent = "<p>First Event</p>";
            assertEquals(expectedEvent, boundedContextBoard.getEvents().get(0));
        assertEquals(1, boundedContextBoard.getEvents().size());
    }
    @Test
    void createBounded_Query(){
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        //add Outbound Tag
        widgetObjects.add(new Text(BigInteger.ONE, 300, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "Outbound Communication", "", 0, ""));
        // add query
        widgetObjects.add(new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "#f0f7a9", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>First Query</p>"));
        widgetObjects.add(new Shape(BigInteger.ONE, 350, 0, 0, 0, 0, "#f0f7a9", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>Second Query</p>"));
        InputBoard board = new InputBoard("123", widgetObjects);

        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
        String expectedQuery = "<p>First Query</p>";
        assertEquals(expectedQuery, boundedContextBoard.getQueries().get(0));
        assertEquals(1, boundedContextBoard.getQueries().size());
    }
    @Test
    void createBounded_DomainEventOutbound(){
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        //add Outbound Tag
        widgetObjects.add(new Text(BigInteger.ONE, 300, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "Outbound Communication", "", 0, ""));
        // add events
        widgetObjects.add(new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "#f9f3c1", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>First Event</p>"));
        widgetObjects.add(new Shape(BigInteger.ONE, 350, 0, 0, 0, 0, "#f9f3c1", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>Second Event</p>"));
        InputBoard board = new InputBoard("123", widgetObjects);

        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
        String expectedEvent = "<p>Second Event</p>";
        assertEquals(expectedEvent, boundedContextBoard.getOutBoundCommunication().get(0));
        assertEquals(1, boundedContextBoard.getOutBoundCommunication().size());
    }
    @Test
    void createBoundedContextBoard_CommandsOutbound(){
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        //add Outbound Tag
        widgetObjects.add(new Text(BigInteger.ONE, 300, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "Outbound Communication", "", 0, ""));
        // add commands
        widgetObjects.add(new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "#cbdcee", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>First Command</p>"));
        widgetObjects.add(new Shape(BigInteger.ONE, 350, 0, 0, 0, 0, "#cbdcee", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>Second Command</p>"));
        InputBoard board = new InputBoard("123", widgetObjects);

        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
        String expectedCommand = "<p>Second Command</p>";
        assertEquals(expectedCommand, boundedContextBoard.getOutBoundCommunication().get(0));
        assertEquals(1, boundedContextBoard.getOutBoundCommunication().size());
    }
    @Test
    void createBounded_QueryOutbound(){
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        //add Outbound Tag
        widgetObjects.add(new Text(BigInteger.ONE, 300, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "Outbound Communication", "", 0, ""));
        // add query
        widgetObjects.add(new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "#f0f7a9", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>First Query</p>"));
        widgetObjects.add(new Shape(BigInteger.ONE, 350, 0, 0, 0, 0, "#f0f7a9", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>Second Query</p>"));
        InputBoard board = new InputBoard("123", widgetObjects);

        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board);
        String expectedQuery = "<p>Second Query</p>";
        assertEquals(expectedQuery, boundedContextBoard.getOutBoundCommunication().get(0));
        assertEquals(1, boundedContextBoard.getOutBoundCommunication().size());
    }
}