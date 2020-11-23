package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.model.widgets.Card;
import ch.ost.rj.sa.miro2cml.model.widgets.Shape;
import ch.ost.rj.sa.miro2cml.model.widgets.Text;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputValidationTest {

    @Test
    void validate_Card() {
        //Setup
        ArrayList<WidgetObject> widgetObjectList = new ArrayList<>();
        Card card = new Card(BigInteger.ONE, 0, 0, 0, "input  text", "input  text", "");
        widgetObjectList.add(card);
        InputBoard inputBoard = new InputBoard("123", widgetObjectList);
        String expectedTitle = "input text";
        String expectedDescription = "input  text";
        //run
        InputValidation.validate(inputBoard);
        //check
        assertEquals(expectedTitle, ((Card)inputBoard.getWidgetObjects().get(0)).getTitle());
        assertEquals(expectedDescription, ((Card)inputBoard.getWidgetObjects().get(0)).getDescription());
    }
    @Test
    void validate_Text(){
        //Setup
        ArrayList<WidgetObject> widgetObjectList = new ArrayList<>();
        Text text = new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "input  text", "", 0, "");
        widgetObjectList.add(text);
        InputBoard inputBoard = new InputBoard("123", widgetObjectList);
        String expectedTitle = "input text";
        //run
        InputValidation.validate(inputBoard);
        //check
        assertEquals(expectedTitle, ((Text)inputBoard.getWidgetObjects().get(0)).getText());
    }
    @Test
    void validate_Sticker(){
        //Setup
        ArrayList<WidgetObject> widgetObjectList = new ArrayList<>();
        Shape shape = new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "", "", 0, "", "", "inp  t text");
        widgetObjectList.add(shape);
        InputBoard inputBoard = new InputBoard("123", widgetObjectList);
        String expectedTitle = "inp t text";
        //run
        InputValidation.validate(inputBoard);
        //check
        assertEquals(expectedTitle, ((Shape)inputBoard.getWidgetObjects().get(0)).getText());
    }
    @Test
    void validate() {
        //Setup
        ArrayList<WidgetObject> widgetObjectList = new ArrayList<>();
        Text text = new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "input  text  ", "", 0, "");
        Card card = new Card(BigInteger.ONE, 0, 0, 0, "input  text ", "", "");
        Shape shape = new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "", "", 0, "", "", "input  text ");
        widgetObjectList.add(text);
        widgetObjectList.add(card);
        widgetObjectList.add(shape);
        InputBoard inputBoard = new InputBoard("123", widgetObjectList);
        String expectedValue= "input text ";
        //run
        InputValidation.validate(inputBoard);
        //check
        assertEquals(expectedValue, ((Text)inputBoard.getWidgetObjects().get(0)).getText());
        assertEquals(expectedValue, ((Card)inputBoard.getWidgetObjects().get(1)).getTitle());
        assertEquals(expectedValue, ((Shape)inputBoard.getWidgetObjects().get(2)).getText());
    }
}