package ch.ost.rj.sa.miro2cml.business_logic;

public class StringValidator {

    public static String removeDoubleSpace(String input){
        return input.replaceAll("  ", " ");
    }

    public static String removeSimpleHtmlTags(String input){
        input = input.replaceAll("<[a-z]+>", "");
        input = input.replaceAll("</[a-z]+>", "");
        return input;
    }
    public static String extractHtmlLink(String input){
        input = input.replaceAll("<a href=\"", "");
        return input;
    }
    public static String removeAllHtmlTags(String input){
        input = input.replaceAll("<[)(a-z A-Z,./:\"=_-]+>", "");
        return input.replaceAll("\">", "");
    }
    public static String removeHtmlBreaks(String input){
        input = input.replaceAll("<br />", "");
        return input;
    }
    public static String replaceHtmlCodeWithString(String input){
        input = input.replaceAll("&gt;", ">");
        input = input.replaceAll("&lt;", "<");
        input = input.replaceAll("&#39;", "'");
        return input;
    }
    public static String removeSpecialCharacters(String input){
        return input.replaceAll("[^A-Za-z0-9_ ]", "");
    }
    public static String replaceSpaceWithLine(String input){
        return input.replaceAll(" ", "_");
    }

    public static String convertForVariableName(String input){
        input = removeAllHtmlTags(input);
        input = removeSpecialCharacters(input);
        return replaceSpaceWithLine(input);
    }
    public static String validatorForStrings(String input){
        input = removeSimpleHtmlTags(input);
        input = extractHtmlLink(input);
        input = removeAllHtmlTags(input);
        return replaceHtmlCodeWithString(input);
    }

    public static String replaceLineWithComma(String input) {
        return input.replaceAll("-", ",");
    }

    public static String removeSpace(String s) {
        return s.replaceAll(" ", "");
    }
}
