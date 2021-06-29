package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.UserStory;
import ch.ost.rj.sa.miro2cml.business_logic.model.miroboard_representation.UserStoryBoard;

import java.util.List;


public class UserStoryMapperService implements IBoardMapperService {

    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException {
        mappingLog.addInfoLogEntry("Commence Mapping, Board Type: UserStory");
        CmlModel cmlModel= new CmlModel();
        UserStoryBoard extractedBoard = UserStoryBoard.createUserStoryBoard(inputBoard, mappingLog, messages);
        List<UserStory> userStories = extractedBoard.getUserStories();
        for (UserStory userStory: userStories) {
            cmlModel.getResource().getContextMappingModel().getUserRequirements().add((org.contextmapper.dsl.contextMappingDSL.UserRequirement) userStory.provideEObject());
        }
        mappingLog.addInfoLogEntry("UserStory generation Finished");
        return cmlModel;
    }
}
