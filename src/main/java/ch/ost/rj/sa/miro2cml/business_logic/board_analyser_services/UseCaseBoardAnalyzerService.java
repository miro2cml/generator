package ch.ost.rj.sa.miro2cml.business_logic.board_analyser_services;

import ch.ost.rj.sa.miro2cml.business_logic.cml_model.CMLModel;
import ch.ost.rj.sa.miro2cml.business_logic.cml_model.UserStory;
import ch.ost.rj.sa.miro2cml.business_logic.model.Board;
import ch.ost.rj.sa.miro2cml.business_logic.model.ConceptBoard;
import ch.ost.rj.sa.miro2cml.data_access.miro_model.pojo.Card;
import ch.ost.rj.sa.miro2cml.data_access.miro_model.pojo.WidgetObject;

import java.util.ArrayList;

public class UseCaseBoardAnalyzerService implements IBoardAnalyzerService {
    //TODO: Analyse
    @Override
    public ConceptBoard analyseBoard(Board board) {

        return new ConceptBoard(board);
    }
    @Override
    public CMLModel analyseInput(ConceptBoard conceptBoard){
        Board board = conceptBoard.getOriginalBoard();
        CMLModel cmlModel = new CMLModel(new ArrayList<>());
        //just card -> mapping rules
        var cardsOnBoardStream = board.getWidgetObjects().stream().filter(x -> x instanceof Card);
        cardsOnBoardStream.forEach(x -> generateUserStory((Card) x, cmlModel));
        return cmlModel;
    }

    private void generateUserStory(Card card, CMLModel model) {
        try{
            //ignore blue and yellow cards -> mapping rules
            if(card.getBackgroundColor().equals("#2d9bf0") || card.getBackgroundColor().equals("#fbc800")){
                return;
            }
            //nur einteilige Verben und nur a anstelle an unterstützt, keine Sonderzeichen beachtet
            if(card.getTitle().matches("<p>As an [A-Z,a-z]+ I want to [a-z]+ a [a-z, A-Z]+ so that [a-zA-Z\\p{Blank}]+</p>")){
                String userStory = card.getTitle();
                String actor = getPart("<p>As an ", " I want to ", userStory);
                String action = getPart(" I want to ", " a ", userStory);
                String object = getPart(" a ", " so that ", userStory);
                String goal = getPart(" so that ", "</p>", userStory);
                String name = action.concat(object);
                model.add(new UserStory(name, actor, action, object, goal));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getPart(String start, String end, String input) {
        int startIndex = input.indexOf(start);
        int endIndex = input.indexOf(end);
        return input.substring(startIndex+start.length(), endIndex);
    }
}
