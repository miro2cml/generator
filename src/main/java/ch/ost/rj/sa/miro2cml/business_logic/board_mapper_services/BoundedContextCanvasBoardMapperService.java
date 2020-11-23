package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter.BoundedContextConverter;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.BoundedContext;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.BoundedContextBoard;



public class BoundedContextCanvasBoardMapperService implements IBoardMapperService {

    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard board) {
        CmlModel cmlModel= new CmlModel();
        BoundedContextBoard extractedBoard = BoundedContextBoard.createBoundedContextBoard(board);
        BoundedContext boundedContextModel = BoundedContextConverter.convertExtractedBoardToCMLBoundedContext(extractedBoard);
        cmlModel.getResource().getContextMappingModel().getBoundedContexts().add((org.contextmapper.dsl.contextMappingDSL.BoundedContext) boundedContextModel.provideEObject());
        return cmlModel;
    }
}
