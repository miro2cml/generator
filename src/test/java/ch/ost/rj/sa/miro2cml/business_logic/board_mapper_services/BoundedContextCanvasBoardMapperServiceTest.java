package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.BoundedContext;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.model.widgets.Shape;
import ch.ost.rj.sa.miro2cml.model.widgets.Text;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoundedContextCanvasBoardMapperServiceTest {

    private BoundedContextCanvasBoardMapperService boundedContextCanvasBoardMapperServiceUnderTest;

    @BeforeEach
    void setUp() {
        boundedContextCanvasBoardMapperServiceUnderTest = new BoundedContextCanvasBoardMapperService();
    }
    @Test
    void mapWidgetObjectsToCmlArtifacts() throws Exception {
        //setup
        InputBoard board = getInputBoard();
        MappingLog mappingLog = new MappingLog("123");
        MappingMessages messages = new MappingMessages();
        //setup cml object
        ArrayList<String> responsibilites = new ArrayList<>();
        responsibilites.add("Role Types- draft context- execution context- analysis context- gateway context- other");
        ArrayList<String> domainevent = new ArrayList<>();
        domainevent.add("First_Event");
        ArrayList<String> queries = new ArrayList<>();
        queries.add("First_Query");
        queries.add("Second_Query");
        ArrayList<String> commands = new ArrayList<>();
        commands.add("First_Command");
        commands.add("Second_Command");
        BoundedContext expectedBoundedContext = new BoundedContext("/* Strategic Classifications: Domain, core, supporting, generic, other?/Business Model, revenue, engagement, compliance, cost reduction/Evolution, genesis, custom built, product, commodity/Ubiquitous Language: /Business Descisions: /Outbound Communications: , Second Command, Second Event, Second Query*/", "Test", "What benefits does this context provide, and how does it provide them?", "Test_Aggregate", responsibilites , domainevent, queries, commands, mappingLog, messages);
        CmlModel expectedResult = new CmlModel();
        expectedResult.getResource().getContextMappingModel().getBoundedContexts().add((org.contextmapper.dsl.contextMappingDSL.BoundedContext) expectedBoundedContext.provideEObject());

        //run
        CmlModel cmlModel = boundedContextCanvasBoardMapperServiceUnderTest.mapWidgetObjectsToCmlArtifacts(board,new MappingLog(""),new MappingMessages());

        //check
        assertEquals(true, cmlModel.equals(expectedResult));
    }

    private InputBoard getInputBoard() {
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        widgetObjects.add(new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Name: Test</strong></p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 20, 15, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>What benefits does this context provide, and how does it provide them?</p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 5, 10, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>Description</p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 30, 10, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>Strategic Classification</p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 30, 20, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>Inbound Communication</p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Domain</strong></p><p>- core</p><p>- supporting</p><p>- generic</p><p>- other?</p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Business Model</strong></p><p><span>- revenue</span></p><p>- engagement</p><p>- compliance</p><p>- cost reduction</p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Evolution</strong></p><p><span>- genesis</span></p><p>- custom built</p><p>- product</p><p>- commodity</p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Role Types</strong></p><p><span>- draft context</span></p><p>- execution context</p><p>- analysis context</p><p>- gateway context</p><p>- other</p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 300, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "Outbound Communication", "", 0, ""));
        widgetObjects.add(new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "#cbdcee", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>First Command</p>"));
        widgetObjects.add(new Shape(BigInteger.ONE, 350, 0, 0, 0, 0, "#cbdcee", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>Second Command</p>"));
        widgetObjects.add(new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "#f9f3c1", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>First Event</p>"));
        widgetObjects.add(new Shape(BigInteger.ONE, 350, 0, 0, 0, 0, "#f9f3c1", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>Second Event</p>"));
        widgetObjects.add(new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "#f0f7a9", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>First Query</p>"));
        widgetObjects.add(new Shape(BigInteger.ONE, 350, 0, 0, 0, 0, "#f0f7a9", 0, "", 0, "", 0, "", "", "", 0, "", "", "<p>Second Query</p>"));
        return new InputBoard("123", widgetObjects);
    }

}