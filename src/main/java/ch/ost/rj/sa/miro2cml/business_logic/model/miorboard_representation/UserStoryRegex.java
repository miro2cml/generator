package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import java.util.ArrayList;
import java.util.List;

public class UserStoryRegex {
    private static final String ASAN = "<p>As an ";
    private static final String ASA = "<p>As a ";
    private static final String TEXT = "[A-Z,.a-z\\p{Blank}]+";
    private static final String IWANTTO = " I want to ";
    private static final String VERB = "[a-z]+";
    private static final String A = " a ";
    private static final String AN = " an ";
    private static final String ENTITY = "[a-z, A-Z]+";
    private static final String SOTHAT = " so that ";
    private static final String END = "</p>";
    public List<List<String>> userStoriesRegex;

    public List<List<String>> getUserStoriesRegex() {
        return userStoriesRegex;
    }

    public void setUserStoriesRegex(List<List<String>> userStoriesRegex) {
        this.userStoriesRegex = userStoriesRegex;
    }

    private UserStoryRegex(List<List<String>> userStoriesRegex) {
        this.userStoriesRegex = createUserStoriesRegex();
    }


    public static List<List<String>> createUserStoriesRegex() {
        ArrayList<List<String>> output = new ArrayList<>();
        output.add(getStrings(ASA, A));
        output.add(getStrings(ASA, AN));
        output.add(getStrings(ASAN, A));
        output.add(getStrings(ASAN, AN));
        return output;
    }

    private static ArrayList<String> getStrings(String first, String second) {
        ArrayList<String> output = new ArrayList<>();
        output.add(first+TEXT+IWANTTO+VERB+second+ENTITY+SOTHAT+TEXT+END);
        output.add(first);
        output.add(IWANTTO);
        output.add(second);
        output.add(SOTHAT);
        output.add(END);
        return output;
    }
}
