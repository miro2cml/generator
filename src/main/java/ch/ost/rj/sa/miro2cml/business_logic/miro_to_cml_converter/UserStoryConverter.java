package ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.UserStory;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.UserStoryBoard;

import java.util.ArrayList;

public class UserStoryConverter {
    public static ArrayList<UserStory> convertExtractedBoardToCMLUserStories(UserStoryBoard extractedBoard){
        var userStories = extractedBoard.getUserStories();
        for (UserStory userStory: userStories) {
            userStory.setName(StringValidator.convertForVariableName(generateName(userStory.getVerb(), userStory.getEntity())));
            userStory.setRole(StringValidator.validatorForStrings(userStory.getRole()));
            userStory.setVerb(StringValidator.validatorForStrings(userStory.getVerb()));
            userStory.setEntity(StringValidator.validatorForStrings(userStory.getEntity()));
            userStory.setBenefit(StringValidator.validatorForStrings(userStory.getBenefit()));
        }
        return makeAllNamesUnique(userStories);
    }

    private static ArrayList<UserStory> makeAllNamesUnique(ArrayList<UserStory> userStories) {
        return userStories;
    }

    private static String generateName(String action, String object) {
        return action.concat(object);
    }

}
