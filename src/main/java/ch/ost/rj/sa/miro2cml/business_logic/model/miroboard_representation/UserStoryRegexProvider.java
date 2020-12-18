package ch.ost.rj.sa.miro2cml.business_logic.model.miroboard_representation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserStoryRegexProvider {

    //TODO: make configurable through application.properties
    private static final String ASAN = "^As an ";
    private static final String ASA = "^As a ";
    private static final String ACTOR = "[A-Za-z0-9]+";
    private static final String IWANTTO = " I want to ";
    private static final String VERB = "[a-z][a-zA-Z0-9]+";
    private static final String A = " a ";
    private static final String AN = " an ";
    private static final String ENTITY = "[a-zA-Z0-9]+";
    private static final String SOTHAT = " so that ";
    private static final String BENEFIT = "[A-Z,. ':/a-z0-9]+$";
    private static final List<String> ignoredCardColors = new ArrayList<>(Arrays.asList("#2d9bf0","#fbc800"));

    public List<String> getIgnoredCardColors() {
        return ignoredCardColors;
    }

    public List<List<String>> getListOfUserStoriesRegexStringLists() {
        ArrayList<List<String>> output = new ArrayList<>();
        output.add(createUserStoryRegexStringLists(ASA, A));
        output.add(createUserStoryRegexStringLists(ASA, AN));
        output.add(createUserStoryRegexStringLists(ASAN, A));
        output.add(createUserStoryRegexStringLists(ASAN, AN));
        return output;
    }

    private ArrayList<String> createUserStoryRegexStringLists(String actorArticle, String entityArticle) {
        ArrayList<String> regex = new ArrayList<>();
        regex.add(actorArticle + ACTOR + IWANTTO + VERB + entityArticle + ENTITY + SOTHAT + BENEFIT);
        regex.add(actorArticle);
        regex.add(ACTOR);
        regex.add(IWANTTO);
        regex.add(VERB);
        regex.add(entityArticle);
        regex.add(ENTITY);
        regex.add(SOTHAT);
        regex.add(BENEFIT);
        return regex;
    }
}
