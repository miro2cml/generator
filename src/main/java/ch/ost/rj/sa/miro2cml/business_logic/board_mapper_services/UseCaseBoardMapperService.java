package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.business_logic.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter.UserStoryConverter;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.UserStory;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.UserStoryBoard;
import ch.ost.rj.sa.miro2cml.model.widgets.Card;

import java.util.ArrayList;
import java.util.List;


public class UseCaseBoardMapperService implements IBoardMapperService {

    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException {
        CmlModel cmlModel= new CmlModel();
        mappingLog.addInfoLogEntry("Commence with UserStory generation");
        UserStoryBoard extractedBoard = UserStoryBoard.createUserStoryBoard(inputBoard, mappingLog, messages);
        List<UserStory> userStories = UserStoryConverter.convertExtractedBoardToCMLUserStories(extractedBoard);
        ArrayList<String> userStoryNames = new ArrayList<>();
        for (UserStory userStory: userStories) {
            userStory.setName(StringValidator.uniqueValue(userStoryNames,userStory.getName()));
            cmlModel.getResource().getContextMappingModel().getUserRequirements().add((org.contextmapper.dsl.contextMappingDSL.UserRequirement) userStory.provideEObject());
        }
        mappingLog.addInfoLogEntry("UserStory generation Finished");
        return cmlModel;
    }
}
