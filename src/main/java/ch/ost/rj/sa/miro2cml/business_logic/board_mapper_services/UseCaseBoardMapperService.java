package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter.UserStoryConverter;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.UserStory;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.UserStoryBoard;

import java.util.List;


public class UseCaseBoardMapperService implements IBoardMapperService {
    
    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard inputBoard) {
        CmlModel cmlModel= new CmlModel();
        UserStoryBoard extractedBoard = UserStoryBoard.createUserStoryBoard(inputBoard);
        List<UserStory> userStories = UserStoryConverter.convertExtractedBoardToCMLUserStories(extractedBoard);
        for (UserStory userStory: userStories) {
            cmlModel.getResource().getContextMappingModel().getUserRequirements().add((org.contextmapper.dsl.contextMappingDSL.UserRequirement) userStory.provideEObject());
        }
        return cmlModel;
    }
}
