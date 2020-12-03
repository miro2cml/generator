package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import ch.ost.rj.sa.miro2cml.business_logic.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.model.widgets.Line;
import ch.ost.rj.sa.miro2cml.model.widgets.Sticker;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventStormingBoardTest {

    @Test
    void createEventStormingBoard_throwsWrongBoardException() {
        ArrayList<WidgetObject> widgetObjectArrayList = new ArrayList<>();
        MappingLog mappingLog = new MappingLog("123");
        MappingMessages messages = new MappingMessages();
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 0,0, "", "",0,  "", "", "textfeld"));
        InputBoard input = new InputBoard("123", widgetObjectArrayList);

        Throwable exception = assertThrows(WrongBoardException.class, ()-> EventStormingBoard.createEventStormingBoard(input, mappingLog, messages));
        assertEquals("Input Board doesn't match with expected Board Type: Event Storming", exception.getMessage());

    }
    @Test
    void createEventStormingBoard_Issue() throws WrongBoardException {
        ArrayList<WidgetObject> widgetObjectArrayList = new ArrayList<>();
        MappingLog mappingLog = new MappingLog("123");
        MappingMessages messages = new MappingMessages();
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "organge", "",0,  "", "", "<p>Domain Event</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "blue", "",0,  "", "", "<p>Command</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "green", "",0,  "", "", "<p>Aggregate</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "red", "",0,  "", "", "<p>Issue</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "yellow", "",0,  "", "", "<p>User Role</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "red", "",0,  "", "", "<p>This is an issue</p>"));
        InputBoard input = new InputBoard("123", widgetObjectArrayList);

        EventStormingBoard board = EventStormingBoard.createEventStormingBoard(input, mappingLog, messages);
        String expectedResult = "This is an issue";
        assertEquals(expectedResult, board.getIssues().get(0));

    }
    @Test
    void createEventStormingBoard_Connections() throws WrongBoardException {
        ArrayList<WidgetObject> widgetObjectArrayList = new ArrayList<>();
        MappingLog mappingLog = new MappingLog("123");
        MappingMessages messages = new MappingMessages();
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 228,199, "orange", "",0,  "", "", "<p>Domain Event</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "blue", "",0,  "", "", "<p>Command</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "green", "",0,  "", "", "<p>Aggregate</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "red", "",0,  "", "", "<p>Issue</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "yellow", "",0,  "", "", "<p>User Role</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.valueOf(333), -2420, 1307, 0, 30,20, "orange", "",0,  "", "", "buying a house"));
        widgetObjectArrayList.add(new Sticker(BigInteger.TWO, -2814, 1290, 0, 30,20, "blue", "",0,  "", "", "buyed house"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, -2584, 904, 0, 30,20, "green", "",0,  "", "", "house"));
        widgetObjectArrayList.add(new Sticker(BigInteger.TEN, -597, 1307, 0, 30,20, "orange", "",0,  "", "", "moving in"));
        widgetObjectArrayList.add(new Sticker(BigInteger.valueOf(307), -988, 1307, 0, 30,20, "blue", "",0,  "", "", "moved in"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, -751, 930, 0, 30,20, "green", "",0,  "", "", "house"));
        widgetObjectArrayList.add(new Line(BigInteger.ONE, BigInteger.valueOf(333), BigInteger.valueOf(307), "#000000","normal", 8.0,"bezier","bezier", "bezier"));

        InputBoard input = new InputBoard("123", widgetObjectArrayList);

        EventStormingBoard board = EventStormingBoard.createEventStormingBoard(input, mappingLog, messages);
        String expectedAggregate = "house";
        String expectedCommand = "buyed house";
        String expectedDomainEvent = "buying a house";
        String expectedSecondAggregate= "house";
        String expectedSecondCommand = "moved in";
        String expectedSecondDomainEvent = "moving in";

        assertEquals(expectedAggregate, board.getConnections().get(0).getAgggregate().get(0));
        assertEquals(expectedCommand, board.getConnections().get(0).getCommand());
        assertEquals(expectedDomainEvent, board.getConnections().get(0).getDomainEvent());
        assertTrue(board.getConnections().get(1).getTrigger().isEmpty());
        assertEquals(expectedSecondAggregate, board.getConnections().get(1).getAgggregate().get(0));
        assertEquals(expectedSecondCommand, board.getConnections().get(1).getCommand());
        assertEquals(expectedSecondDomainEvent, board.getConnections().get(1).getDomainEvent());
        assertEquals(expectedDomainEvent, board.getConnections().get(0).getTrigger().get(0));
    }
}
