package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.model.widgets.*;
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
        inputBoard = InputValidation.validate(inputBoard);
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
        inputBoard = InputValidation.validate(inputBoard);
        //check
        assertEquals(expectedTitle, ((Text)inputBoard.getWidgetObjects().get(0)).getText());
    }
    @Test
    void validate_Sticker(){
        //Setup
        ArrayList<WidgetObject> widgetObjectList = new ArrayList<>();
        Shape shape = new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "", "", 0, "", "", "inp  t text");
        Card card = new Card(BigInteger.ONE, 0, 0, 0, "<p>As an  UseCase Designer  I want to specify a UseCase in Miro and then transfer it to  cml so that I can collaborativly work on my UseCases and use the functionality of the context mapper(correct, abstände doppelt).</p>", "input  text", "");
        widgetObjectList.add(shape);
        widgetObjectList.add(card);
        InputBoard inputBoard = new InputBoard("123", widgetObjectList);
        String expectedTitle = "inp t text";
        String expectedTitleTwo = "<p>As an UseCase Designer I want to specify a UseCase in Miro and then transfer it to cml so that I can collaborativly work on my UseCases and use the functionality of the context mapper(correct, abstände doppelt).</p>";
        //run
        inputBoard = InputValidation.validate(inputBoard);
        //check
        assertEquals(expectedTitle, ((Shape)inputBoard.getWidgetObjects().get(0)).getText());
        assertEquals(expectedTitleTwo, (inputBoard.getWidgetObjects().get(1).getMappingRelevantText()));
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
        inputBoard = InputValidation.validate(inputBoard);
        //check
        assertEquals(expectedValue, ((Text)inputBoard.getWidgetObjects().get(0)).getText());
        assertEquals(expectedValue, ((Card)inputBoard.getWidgetObjects().get(1)).getTitle());
        assertEquals(expectedValue, ((Shape)inputBoard.getWidgetObjects().get(2)).getText());
    }
    @Test
    void validate_length(){
        ArrayList<WidgetObject> widgetObjectList = new ArrayList<>();
        Text text = new Text(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "Object-oriented analysis and design\n" +
                "Although, in theory, the general idea of DDD need not be restricted to object-oriented approaches, in practice DDD seeks to exploit the advantages that object-oriented techniques make possible. These include entities/aggregate roots as receivers of commands/method invocations and the encapsulation of state within foremost aggregate roots and on a higher architectural level, bounded contexts.\n" +
                "Model-driven engineering (MDE) and Model-driven architecture (MDA)\n" +
                "While DDD is compatible with MDA/MDE (where MDE can be regarded as a superset of MDA) the intent of the two concepts is somewhat different. MDA is concerned more with the means of translating a model into code for different technology platforms than with the practice of defining better domain models. The techniques provided by MDE (to model domains, to create DSLs to facilitate the communication between domain experts and developers,...) facilitate the application of DDD in practice and help DDD practitioners to get more out of their models. Thanks to the model transformation and code generation techniques of MDE, the domain model can be used not only to represent the domain but also to generate the actual software system that will be used to manage it. This picture shows a possible representation of DDD and MDE combined.\n" +
                "Plain Old Java Objects (POJOs) and Plain Old CLR Objects (POCOs)\n" +
                "POJOs and POCOs are technical implementation concepts, specific to Java and the .NET Framework respectively. However, the emergence of the terms POJO and POCO reflect a growing view that, within the context of either of those technical platforms, domain objects should be defined purely to implement the business behaviour of the corresponding domain concept, rather than be defined by the requirements of a more specific technology framework.\n" +
                "The naked objects pattern\n" +
                "Based on the premise that if you have a good enough domain model, the user interface can simply be a reflection of this domain model; and that if you require the user interface to be a direct reflection of the domain model then this will force the design of a better domain model.[4]\n" +
                "Domain-specific modeling (DSM)\n" +
                "DSM is DDD applied through the use of Domain-specific languages.\n" +
                "Domain-specific language (DSL)\n" +
                "DDD does not specifically require the use of a DSL, though it could be used to help define a DSL and support methods like domain-specific multimodeling.\n" +
                "Aspect-oriented programming (AOP)\n" +
                "AOP makes it easy to factor out technical concerns (such as security, transaction management, logging) from a domain model, and as such makes it easier to design and implement domain models that focus purely on the business logic.\n" +
                "Command Query Responsibility Segregation (CQRS)\n" +
                "CQRS is an architectural pattern for separation of reads from writes, where the former is a Query and the latter is a Command. Commands mutate state and are hence approximately equivalent to method invocation on aggregate roots/entities. Queries read state but do not mutate it. CQRS is a derivative architectural pattern from the design pattern called Command and Query Separation (CQS) which was coined by Bertrand Meyer. While CQRS does not require DDD, domain-driven design makes the distinction between commands and queries explicit, around the concept of an aggregate root. The idea is that a given aggregate root has a method that corresponds to a command and a command handler invokes the method on the aggregate root. The aggregate root is responsible for performing the logic of the operation and yielding either a number of events or a failure (exception or execution result enumeration/number) response OR (if Event Sourcing (ES) is not used) just mutating its state for a persister implementation such as an ORM to write to a data store, while the command handler is responsible for pulling in infrastructure concerns related to the saving of the aggregate root's state or events and creating the needed contexts (e.g. transactions).", "", 0, "");
        Card card = new Card(BigInteger.ONE, 0, 0, 0, "input  text ", "", "");
        Shape shape = new Shape(BigInteger.ONE, 0, 0, 0, 0, 0, "", 0, "", 0, "", 0, "", "", "", 0, "", "", "input  text ");
        Line line = new Line(BigInteger.ONE, BigInteger.ONE, BigInteger.TEN, "", "", 0, "", "", "");
        widgetObjectList.add(text);
        widgetObjectList.add(card);
        widgetObjectList.add(shape);
        widgetObjectList.add(line);
        InputBoard inputBoard = new InputBoard("123", widgetObjectList);
        String expectedValue= "input text ";
        //run
        inputBoard = InputValidation.validate(inputBoard);
        //check
        assertEquals(1200, ((Text)inputBoard.getWidgetObjects().get(0)).getText().length());
        assertEquals(expectedValue, ((Card)inputBoard.getWidgetObjects().get(1)).getTitle());
        assertEquals(expectedValue, ((Shape)inputBoard.getWidgetObjects().get(2)).getText());
    }
}