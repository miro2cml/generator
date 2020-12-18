package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.miro_to_cml_converter;

import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.InvalidBoardFormatException;
import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.miroboard_representation.EventStormingBoard;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.Line;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.Sticker;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.WidgetObject;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventStormingConverterTest {

    @Test
    void convertEventStormingBoardtoCML() throws WrongBoardException, InvalidBoardFormatException {
        ArrayList<WidgetObject> widgetObjectArrayList = new ArrayList<>();
        MappingLog mappingLog = new MappingLog();
        MappingMessages messages = new MappingMessages();
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 228,199, "orange", "",0,  "", "", "<p>Domain Event</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "blue", "",0,  "", "", "<p>Command</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "green", "",0,  "", "", "<p>Aggregate</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "red", "",0,  "", "", "<p>Issue</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, 0, 0, 0, 30,20, "yellow", "",0,  "", "", "<p>User Role</p>"));
        widgetObjectArrayList.add(new Sticker(BigInteger.valueOf(333), -2420, 1307, 0, 30,20, "orange", "",0,  "", "", "<p>buying a house"));
        widgetObjectArrayList.add(new Sticker(BigInteger.TWO, -2814, 1290, 0, 30,20, "blue", "",0,  "", "", "<p>buyed house"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, -2584, 904, 0, 30,20, "green", "",0,  "", "", "<p>house"));
        widgetObjectArrayList.add(new Sticker(BigInteger.TEN, -597, 1307, 0, 30,20, "orange", "",0,  "", "", "<p>moving in"));
        widgetObjectArrayList.add(new Sticker(BigInteger.valueOf(307), -988, 1307, 0, 30,20, "blue", "",0,  "", "", "<p>moved in"));
        widgetObjectArrayList.add(new Sticker(BigInteger.ONE, -751, 930, 0, 30,20, "green", "",0,  "", "", "<p>house"));
        widgetObjectArrayList.add(new Line(BigInteger.ONE, BigInteger.valueOf(333), BigInteger.valueOf(307), "#000000","normal", 8.0,"bezier","bezier", "bezier"));

        InputBoard input = new InputBoard("123", widgetObjectArrayList);

        EventStormingBoard board = EventStormingBoard.createEventStormingBoard(input, mappingLog, messages);
        var convertedBoard = EventStormingConverter.convertEventStormingBoardToCML(board);

        String expectedOutput = "EventStorming{aggregates=[AggregatesCML{name='house', flow=[FlowStep{position=-2814.0, command='buyed_house', event='buying_a_house', role='', triggers=[moved_in]}, FlowStep{position=-988.0, command='moved_in', event='moving_in', role='', triggers=[]}]}], issues='/* []*/'}";
        //assertEquals(expectedOutput, convertedBoard.toString());
    }
}