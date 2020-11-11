package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.UserStory;
import ch.ost.rj.sa.miro2cml.model.widgets.Card;


public class UseCaseBoardMapperService implements IBoardMapperService {

    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard inputBoard) {
        CmlModel cmlModel = new CmlModel();
        //just cards -> mapping rules
        var cardsOnBoardStream = inputBoard.getWidgetObjects().stream().filter(x -> x instanceof Card);
        cardsOnBoardStream.forEach(x -> generateUserStory((Card) x, cmlModel));

        return cmlModel;
    }

    private void generateUserStory(Card card, CmlModel model) {
        try {
            //ignore blue and yellow cards -> mapping rules
            if (card.getBackgroundColor().equals("#2d9bf0") || card.getBackgroundColor().equals("#fbc800")) {
                return;
            }
            //TODO refactoring
            //nur einteilige Verben und nur a anstelle an unterstützt, keine Sonderzeichen beachtet
            if (card.getTitle().matches("<p>As an [A-Z,a-z\\p{Blank}]+ I want to [a-z]+ a [a-z, A-Z]+ so that [a-zA-Z\\p{Blank},.]+</p>")) {
                String userStoryString = card.getTitle();
                String actor = getPart("<p>As an ", " I want to ", userStoryString);
                String action = getPart(" I want to ", " a ", userStoryString);
                String object = getPart(" a ", " so that ", userStoryString);
                String goal = getPart(" so that ", "</p>", userStoryString);
                String name = getName(action, object);
                UserStory userStory = new UserStory(name, actor, action, object, goal);

                org.contextmapper.dsl.contextMappingDSL.UserStory cmlUserStory = userStory.provideEObject() instanceof org.contextmapper.dsl.contextMappingDSL.UserStory ? (org.contextmapper.dsl.contextMappingDSL.UserStory) userStory.provideEObject() : null;
                model.getResource().getContextMappingModel().getUserRequirements().add(cmlUserStory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getName(String action, String object) {
        object.replaceAll("\\W+", "");
        return action.concat(object);
    }

    private String getPart(String start, String end, String input) {
        int startIndex = input.indexOf(start);
        int endIndex = input.indexOf(end);
        return input.substring(startIndex + start.length(), endIndex);
    }
}
