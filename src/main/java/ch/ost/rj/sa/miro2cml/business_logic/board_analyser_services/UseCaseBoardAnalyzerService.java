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
        var cardsOnBoardStream = board.getWidgetObjects().stream().filter(x -> x instanceof Card);
        cardsOnBoardStream.forEach(x -> generateUserStory((Card) x, cmlModel));
        return cmlModel;
    }

    private void generateUserStory(Card card, CMLModel model) {
        try{
            //nur einteilige Verben und nur a anstelle an unterstützt
            //ignore blue and yellow cards -> mapping rules
            if(card.getBackgroundColor().equals("#2d9bf0") || card.getBackgroundColor().equals("#fbc800")){
                return;
            }
            if(card.getTitle().matches("<p>As an [A-Z,a-z]+ I want to [a-z]+ a [a-z, A-Z]+ so that [a-zA-Z\\p{Blank}]+</p>")){
                //refactoring TODO
                String withoutStart = card.getTitle().substring(9);
                int index = withoutStart.indexOf(" I want to ");
                String actor = withoutStart.substring(0, index);
                String withoutActor = withoutStart.substring(index+11);
                System.out.println("withoutActor= " + withoutActor);
                int indexA = withoutActor.indexOf(" a ");
                String action = withoutActor.substring(0, indexA);
                String withoutAction = withoutActor.substring(indexA+3);
                int indexB = withoutAction.indexOf(" so that ");
                String object = withoutAction.substring(0, indexB);
                String withoutObject = withoutAction.substring(indexB+9);
                int indexC = withoutObject.indexOf("</p>");
                String goal = withoutObject.substring(0, indexC);
                String name = action.concat(object);
                UserStory thisUserStory = new UserStory(name, actor, action, object, goal);
                System.out.println(thisUserStory.toString());
                model.add(thisUserStory);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
