package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter.EventStormingConverter;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.EventStorming;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingBoard;

public class EventStormingBoardMapperService implements IBoardMapperService {


    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard board) {
        CmlModel cmlModel= new CmlModel();
        EventStormingBoard extractedBoard = EventStormingBoard.createEventStormingBoard(board);
        EventStorming eventStormingModel = EventStormingConverter.convertEventStormingBoardtoCML(extractedBoard);
        cmlModel.getResource().getContextMappingModel().getBoundedContexts().add((org.contextmapper.dsl.contextMappingDSL.BoundedContext) eventStormingModel.provideEObject());
        return cmlModel;
    }

}
