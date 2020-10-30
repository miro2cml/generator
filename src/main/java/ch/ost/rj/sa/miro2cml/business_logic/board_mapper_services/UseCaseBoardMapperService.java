package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.MiroBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.UserStory;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.pojo.Card;

import java.util.ArrayList;

public class UseCaseBoardMapperService implements IBoardMapperService {
    //TODO: Analyse
    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(MiroBoard miroBoard) {
        CmlModel cmlModel = new CmlModel(new ArrayList<>());
        //just cards -> mapping rules
        var cardsOnBoardStream = miroBoard.getWidgetObjects().stream().filter(x -> x instanceof Card);
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
                String userStory = card.getTitle();
                String actor = getPart("<p>As an ", " I want to ", userStory);
                String action = getPart(" I want to ", " a ", userStory);
                String object = getPart(" a ", " so that ", userStory);
                String goal = getPart(" so that ", "</p>", userStory);
                String name = getName(action, object);
                model.add(new UserStory(name, actor, action, object, goal));
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
