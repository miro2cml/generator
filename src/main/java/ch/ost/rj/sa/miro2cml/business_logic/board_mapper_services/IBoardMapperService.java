package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services;

import ch.ost.rj.sa.miro2cml.business_logic.model.MappedBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MiroBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.ICmlArtifact;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml.CmlModel;

public interface IBoardMapperService {

    default MappedBoard mapBoard(MiroBoard miroBoard) {
        CmlModel cmlModel = mapWidgetObjectsToCmlArtifacts(miroBoard);
        return new MappedBoard(miroBoard, cmlModel, provideCMLFileString(cmlModel));
    }

    CmlModel mapWidgetObjectsToCmlArtifacts(MiroBoard miroBoard);

    default String provideCMLFileString(CmlModel cmlModel) {
        StringBuilder cmlFileStringBuilder = new StringBuilder();
        for (ICmlArtifact cmlArtifact : cmlModel.getArtifacts()) {
            cmlFileStringBuilder.append(cmlArtifact.toString());
            cmlFileStringBuilder.append(System.lineSeparator());
        }
        return cmlFileStringBuilder.toString();
    }
}
