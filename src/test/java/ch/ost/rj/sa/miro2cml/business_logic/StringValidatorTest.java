package ch.ost.rj.sa.miro2cml.business_logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringValidatorTest {

    @Test
    void removeDoubleSpace_1() {
        final String inputOne = "String";
        final String expectedOne = "String";
        final String resultOne = StringValidator.reduceMultipleSpacesToOne(inputOne);
        assertEquals(expectedOne, resultOne);
    }
    @Test
    void removeDoubleSpace_2() {
        final String inputOne = "Strin  g";
        final String expectedOne = "Strin g";
        final String resultOne = StringValidator.reduceMultipleSpacesToOne(inputOne);
        assertEquals(expectedOne, resultOne);
    }
    @Test
    void removeDoubleSpace_3() {
        final String inputOne = "<p>  String  bla  bla";
        final String expectedOne = "<p> String bla bla";
        final String resultOne = StringValidator.reduceMultipleSpacesToOne(inputOne);
        assertEquals(expectedOne, resultOne);
    }
    @Test
    void removeDoubleSpace_4() {
        final String inputOne = "<p>As an  UseCase Designer I want to specify a UseCase in Miro and then transfer it to cml so that  I can collaborativly work on my UseCases and use the functionality of the context mapper(correct, abstände doppelt).</p>";
        final String expectedOne = "<p>As an UseCase Designer I want to specify a UseCase in Miro and then transfer it to cml so that I can collaborativly work on my UseCases and use the functionality of the context mapper(correct, abstände doppelt).</p>";
        final String resultOne = StringValidator.reduceMultipleSpacesToOne(inputOne);
        assertEquals(expectedOne, resultOne);
    }
    @Test
    void removeMultipleSpace() {
        final String inputOne = "<p>As an        UseCase Designer I want to specify a UseCase in Miro and then transfer it to cml so that  I          can collaborativly work on my UseCases and use the functionality of the    context mapper(correct, abstände doppelt).</p>";
        final String expectedOne = "<p>As an UseCase Designer I want to specify a UseCase in Miro and then transfer it to cml so that I can collaborativly work on my UseCases and use the functionality of the context mapper(correct, abstände doppelt).</p>";
        final String resultOne = StringValidator.reduceMultipleSpacesToOne(inputOne);
        assertEquals(expectedOne, resultOne);
    }

    @Test
    void removeSimpleHtmlTags_1() {
        final String inputOne = "String";
        final String expectedOne = "String";
        final String resultOne = StringValidator.removeSimpleHtmlTags(inputOne);
        assertEquals(expectedOne, resultOne);
    }
    @Test
    void removeSimpleHtmlTags_2() {
        final String inputOne = "<p>String</p>";
        final String expectedOne = "String";
        final String resultOne = StringValidator.removeSimpleHtmlTags(inputOne);
        assertEquals(expectedOne, resultOne);
    }
    @Test
    void removeSimpleHtmlTags_3() {
        final String inputOne = "<strong>String</strong>";
        final String expectedOne = "String";
        final String resultOne = StringValidator.removeSimpleHtmlTags(inputOne);
        assertEquals(expectedOne, resultOne);
    }

    @Test
    void extractHtmlLink_1() {
        final String inputOne = "String";
        final String expectedOne = "String";
        final String resultOne = StringValidator.extractHtmlLink(inputOne);
        assertEquals(expectedOne, resultOne);
    }
    @Test
    void extractHtmlLink_2() {
        final String inputOne = "<a href=\"dasisteinLink\">String";
        final String expectedOne = "dasisteinLink\">String";
        final String resultOne = StringValidator.extractHtmlLink(inputOne);
        assertEquals(expectedOne, resultOne);
    }

    @Test
    void removeHtmlBreaks_1() {
        final String inputOne = "String";
        final String expectedOne = "String";
        final String resultOne = StringValidator.removeHtmlBreaks(inputOne);
        assertEquals(expectedOne, resultOne);
    }
    @Test
    void removeHtmlBreaks_2() {
        final String inputOne = "String<br />";
        final String expectedOne = "String";
        final String resultOne = StringValidator.removeHtmlBreaks(inputOne);
        assertEquals(expectedOne, resultOne);
    }

    @Test
    void replaceHtmlCodeWithString_1() {
        final String inputOne = "&gt;String&lt;";
        final String expectedOne = ">String<";
        final String resultOne = StringValidator.replaceSpecialCharCodesWithTheirSingleCharEquivalent(inputOne);
        assertEquals(expectedOne, resultOne);
    }

    @Test
    void removeSonderzeichen_1() {
        final String inputOne = "çç*Str=ing'?";
        final String expectedOne = "String";
        final String resultOne = StringValidator.removeSpecialCharacters(inputOne);
        assertEquals(expectedOne, resultOne);
    }

    @Test
    void replaceSpaceWithLine() {
        final String inputOne = "St rin g";
        final String expectedOne = "St_rin_g";
        final String resultOne = StringValidator.replaceSpaceWithUnderscore(inputOne);
        assertEquals(expectedOne, resultOne);
    }

    @Test
    void convertForVariableName() {
        final String inputOne = "S trin?)g 12_";
        final String expectedOne = "S_tring_12_";
        final String resultOne = StringValidator.convertForVariableName(inputOne);
        assertEquals(expectedOne, resultOne);
    }

    @Test
    void validatorForStrings() {
        final String inputOne = "<s>&gt;String!<a href=\"123\">";
        final String expectedOne = ">String!123";
        final String resultOne = StringValidator.validatorForStrings(inputOne);
        assertEquals(expectedOne, resultOne);
    }

    @Test
    void removeAllHtmlTags(){
        final String inputOne = "<strong style=\"background-color:transparent\">";
        final String expectedOne = "";
        final String resultOne = StringValidator.removeAllHtmlTags(inputOne);
        assertEquals(expectedOne, resultOne);

        final String inputTwo = "<p><strong style=\"background-color:transparent\">&lt;HealthInsurance&gt";
        final String expectedTwo = "&lt;HealthInsurance&gt";
        final String resultTwo = StringValidator.removeAllHtmlTags(inputTwo);
        assertEquals(expectedTwo, resultTwo);
    }
    @Test
    void removeLeadingNumbers(){
        final String inputOne = "123test";
        final String expectedOne = "test";
        final String resultOne = StringValidator.removeLeadingNumbers(inputOne);
        assertEquals(expectedOne, resultOne);

        final String inputTwo = "test123";
        final String expectedTwo = "test123";
        final String resultTwo = StringValidator.removeLeadingNumbers(inputTwo);
        assertEquals(expectedTwo, resultTwo);

        final String inputThree = "123test123";
        final String expectedThree = "test123";
        final String resultThree = StringValidator.removeLeadingNumbers(inputThree);
        assertEquals(expectedThree, resultThree);
    }
    @Test
    void uniqueValue(){
        ArrayList<String> list = new ArrayList<>();
        list.add("FirstValue");

        final String expectedOne = "FirstValue1";
        final String resultOne = StringValidator.uniquifyValue(list, "FirstValue");
        assertEquals(expectedOne, resultOne);
        assertEquals(list.get(1), resultOne);

        final String expectedTwo = "FirstValue11";
        final String resultTwo = StringValidator.uniquifyValue(list, "FirstValue");
        assertEquals("FirstValue11",list.get(2));
        assertEquals(expectedTwo, resultTwo);


        final String expectedThree = "SecondValue";
        final String resultThree = StringValidator.uniquifyValue(list, "SecondValue");
        assertEquals(expectedThree, resultThree);
        assertEquals(list.get(3), resultThree);
    }
}