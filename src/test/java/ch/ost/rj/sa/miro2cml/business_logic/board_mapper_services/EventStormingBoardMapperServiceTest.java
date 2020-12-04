package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.AggregatesCML;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.EventStorming;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.FlowStep;
import ch.ost.rj.sa.miro2cml.model.widgets.Line;
import ch.ost.rj.sa.miro2cml.model.widgets.Sticker;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventStormingBoardMapperServiceTest {

    private EventStormingBoardMapperService eventStormingMapperServiceUnderTest;

    @BeforeEach
    void setUp() {
        eventStormingMapperServiceUnderTest = new EventStormingBoardMapperService();
    }
    @Test
    void mapWidgetObjectsToCmlArtifacts() throws WrongBoardException {
        ArrayList<WidgetObject> widgetObjectArrayList = new ArrayList<>();
        MappingLog mappingLog = new MappingLog();
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
        InputBoard inputBoard = new InputBoard("123", widgetObjectArrayList);

        CmlModel expectedResult = new CmlModel();
        ArrayList<String> emptyTriggers= new ArrayList<>();
        ArrayList<String> triggers= new ArrayList<>(); triggers.add("moved_in");
        FlowStep buyingHouse = new FlowStep(0, "buyed_house", "buying_house", "", triggers);
        FlowStep movingIn = new FlowStep(0, "moved_in", "moving_in", "", emptyTriggers);
        List<FlowStep> flow = new ArrayList<>(); flow.add(buyingHouse);flow.add(movingIn);
        AggregatesCML house = new AggregatesCML("house", flow);
        ArrayList<AggregatesCML> input = new ArrayList<>();
        input.add(house);
        EventStorming eventStormingModel = new EventStorming(input, "*/ Issues */");
        expectedResult.getResource().getContextMappingModel().getBoundedContexts().add((org.contextmapper.dsl.contextMappingDSL.BoundedContext) eventStormingModel.provideEObject());

        //run
        CmlModel cmlModel = eventStormingMapperServiceUnderTest.mapWidgetObjectsToCmlArtifacts(inputBoard,new MappingLog(),new MappingMessages());

        //check
        assertEquals(cmlModel, expectedResult);
    }
}