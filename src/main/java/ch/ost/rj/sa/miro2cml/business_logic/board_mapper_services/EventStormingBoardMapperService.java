package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.InvalidBoardFormatException;
import ch.ost.rj.sa.miro2cml.business_logic.model.exceptions.WrongBoardException;
import ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.miro_to_cml_converter.EventStormingConverter;
import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.CmlModel;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.EventStorming;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingBoard;

public class EventStormingBoardMapperService implements IBoardMapperService {

    @Override
    public CmlModel mapWidgetObjectsToCmlArtifacts(InputBoard inputBoard, MappingLog mappingLog, MappingMessages messages) throws WrongBoardException, InvalidBoardFormatException {
        mappingLog.addInfoLogEntry("Commence Board Mapping, Board Type: Event Storming");
        CmlModel cmlModel= new CmlModel();
        EventStormingBoard extractedBoard = EventStormingBoard.createEventStormingBoard(inputBoard, mappingLog, messages);
        EventStorming eventStormingModel = EventStormingConverter.convertEventStormingBoardToCML(extractedBoard);
        if(!messages.getMessages().isEmpty()){
            messages.setPerfectMapping(false);
        }
        cmlModel.getResource().getContextMappingModel().getBoundedContexts().add((org.contextmapper.dsl.contextMappingDSL.BoundedContext) eventStormingModel.provideEObject());
        return cmlModel;
    }
}
