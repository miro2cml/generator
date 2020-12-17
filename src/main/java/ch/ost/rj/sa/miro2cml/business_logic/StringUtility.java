package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StringUtility {

    @Value("${miro2cml.stringValidator.maxTextSize:1200}")
    private Integer maxTextSize;

    public static String reduceMultipleSpacesToOne(String input) {
        return input.replaceAll("[ ]+", " ");
    }

    public static String removeSimpleHtmlTags(String input) {
        input = input.replaceAll("<[a-z]+>", "");
        input = input.replaceAll("</[a-z]+>", "");
        return input;
    }

    public static String extractHtmlLink(String input) {
        input = input.replace("<a href=\"", "");
        return input;
    }

    public static String removeAllHtmlTags(String input) {
        input = input.replaceAll("<[)(a-z A-Z,./:\"=_-]+>", "");
        return input.replace("\">", "");
    }

    public static String removeHtmlBreaks(String input) {
        input = input.replace("<br />", "");
        return input;
    }

    public static String replaceSpecialCharCodesWithTheirSingleCharEquivalent(String input) {
        input = input.replace("&gt;", ">");
        input = input.replace("&lt;", "<");
        input = input.replace("&#39;", "'");
        return input;
    }

    public static String removeSpecialCharacters(String input) {
        return input.replaceAll("[^A-Za-z0-9_ ]", "");
    }

    public static String replaceSpaceWithUnderscore(String input) {
        return input.replace(" ", "_");
    }

    public static String convertForVariableName(String input) {
        input = removeAllHtmlTags(input);
        input = removeSpecialCharacters(input);
        input = removeLeadingNumbers(input);
        return replaceSpaceWithUnderscore(input);
    }

    public static String validatorForStrings(String input) {
        input = removeSimpleHtmlTags(input);
        input = extractHtmlLink(input);
        input = removeAllHtmlTags(input);
        return replaceSpecialCharCodesWithTheirSingleCharEquivalent(input);
    }

    public static String replaceDashWithComma(String input) {
        return input.replace("-", ",");
    }

    public static String removeSpaces(String s) {
        return s.replace(" ", "");
    }

    public static String removeLeadingNumbers(String input) {
        return input.replaceFirst("[0-9]*", "");
    }

    public static String uniquifyValue(List<String> list, String input) {
        if (list.contains(input)) {
            input = input + "1";
            return uniquifyValue(list, input);
        } else {
            list.add(input);
            return input;
        }
    }

    public String correctInput(String input, MappingLog log) {
        input = input == null ? "" : input;

        String outputWithSingleSpaces = reduceMultipleSpacesToOne(input);
        if (!input.equals(outputWithSingleSpaces)) {
            log.addInfoLogEntry("removed multi-spaces, new Text: " + outputWithSingleSpaces);
        }

        String trimmedOutput = cutLargeTextAccordingToLimit(outputWithSingleSpaces);

        if (!input.equals(trimmedOutput)) {
            log.addInfoLogEntry("removed multi-spaces, new Text: " + outputWithSingleSpaces);
        }
        return trimmedOutput;
    }

    private String cutLargeTextAccordingToLimit(String input) {
        if (input.length() > maxTextSize) {
            input = input.substring(0, maxTextSize);
        }
        return input;
    }
}
