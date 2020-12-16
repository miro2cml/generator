package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.miro_to_cml_converter;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.BoundedContext;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.BoundedContextBoard;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.Shape;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.Text;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.WidgetObject;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoundedContextConverterTest {

    @Test
    void generateBoundedContext() throws Exception {
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        MappingLog mappingLog = new MappingLog();
        MappingMessages messages = new MappingMessages();
        widgetObjects.add(new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Name: Test</strong></p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 20, 15, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>What benefits does this context provide, and how does it provide them?</p>", "", 0, ""));
        //add description tag
        widgetObjects.add(new Text(BigInteger.ONE, 5, 10, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>Description</p>", "", 0, ""));
        //add strategic classification tag
        widgetObjects.add(new Text(BigInteger.ONE, 30, 10, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>Strategic Classification</p>", "", 0, ""));
        //add inbound Communication tag
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
        InputBoard board = new InputBoard("123", widgetObjects);
        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board, mappingLog, messages);
        //run
        BoundedContext boundedContext = BoundedContextConverter.convertExtractedBoardToCMLBoundedContext(boundedContextBoard, mappingLog, messages);
        //check
        String expectedBoundedContext = "BoundedContext{comment='/** \n* Domain: core, supporting, generic, other?\n* Business Model: revenue, engagement, compliance, cost reduction\n* Evolution: genesis, custom built, product, commodity\n* Outbound Communications: Second Command, Second Event, Second Query\n*/', name='Test', domainVisionStatement='This is the Domain Vision Statement', aggregateName='Test_Aggregate', responsibilities=[- draft context, - execution context, - analysis context, - gateway context, - other], domainEvents=[], commands=[], queries=[], knowledgeLevel=CONCRETE, boundedContextType=FEATURE}";
        assertEquals(expectedBoundedContext, boundedContext.toString());
    }

    @Test
    void createBoundedContextWithLessElements() throws Exception{
        //setup
        ArrayList<WidgetObject> widgetObjects = new ArrayList<>();
        MappingLog mappingLog = new MappingLog();
        MappingMessages messages = new MappingMessages();
        widgetObjects.add(new Text(BigInteger.ONE, 30, 20, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>Inbound Communication</p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 50, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p><strong>Domain</strong></p>", "", 0, ""));
        widgetObjects.add(new Text(BigInteger.ONE, 5, 10, 0, 0, 0, "", 0, "", 0, "", 0, "", "<p>Description</p>", "", 0, ""));
        InputBoard board = new InputBoard("123", widgetObjects);
        BoundedContextBoard boundedContextBoard = BoundedContextBoard.createBoundedContextBoard(board, mappingLog, messages);

        BoundedContext boundedContext = BoundedContextConverter.convertExtractedBoardToCMLBoundedContext(boundedContextBoard, mappingLog, messages);
        String expectedName = "MyBoundedContext";
        BoundedContext expectedBoundedContext = new BoundedContext("/** \n* Domain\n*/", "MyBoundedContext", "This is the Domain Vision Statement", "MyBoundedContext_Aggregate", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), mappingLog, messages);
        assertEquals(expectedBoundedContext.toString(), boundedContext.toString());
    }
}