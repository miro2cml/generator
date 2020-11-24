package ch.ost.rj.sa.miro2cml.business_logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringValidatorTest {

    @Test
    void removeDoubleSpace_1() {
        final String inputOne = "String";
        final String expectedOne = "String";
        final String resultOne = StringValidator.removeDoubleSpace(inputOne);
        assertEquals(expectedOne, resultOne);
    }
    @Test
    void removeDoubleSpace_2() {
        final String inputOne = "Strin  g";
        final String expectedOne = "Strin g";
        final String resultOne = StringValidator.removeDoubleSpace(inputOne);
        assertEquals(expectedOne, resultOne);
    }
    @Test
    void removeDoubleSpace_3() {
        final String inputOne = "  String  ";
        final String expectedOne = " String ";
        final String resultOne = StringValidator.removeDoubleSpace(inputOne);
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
        final String expectedOne = "dasisteinLinkString";
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
        final String resultOne = StringValidator.replaceHtmlCodeWithString(inputOne);
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
        final String resultOne = StringValidator.replaceSpaceWithLine(inputOne);
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
}