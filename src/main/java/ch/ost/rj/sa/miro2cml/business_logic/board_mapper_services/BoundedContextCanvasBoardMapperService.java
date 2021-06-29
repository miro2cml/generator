package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.miro_to_cml_converter.BoundedContextConverter;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.BoundedContext;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.miroboard_representation.BoundedContextBoard;



public class BoundedContextCanvasBoardMapperService implements IBoardMapperService {

    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException {
            mappingLog.addInfoLogEntry("Commence Board Mapping, Board Type: Bounded Context Canvas");
            CmlModel cmlModel= new CmlModel();
            BoundedContextBoard extractedBoard = BoundedContextBoard.createBoundedContextBoard(inputBoard, mappingLog, messages);
            BoundedContext boundedContextModel = BoundedContextConverter.convertExtractedBoardToCMLBoundedContext(extractedBoard, mappingLog, messages);
            cmlModel.getResource().getContextMappingModel().getBoundedContexts().add((org.contextmapper.dsl.contextMappingDSL.BoundedContext) boundedContextModel.provideEObject());
            if(!messages.getMessages().isEmpty()){
                messages.setPerfectMapping(false);
            }
            return cmlModel;
    }
}
