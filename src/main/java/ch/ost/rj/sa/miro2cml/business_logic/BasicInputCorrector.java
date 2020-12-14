package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.IRelevantText;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.WidgetObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasicInputCorrector {

    @Autowired
    StringValidator stringValidator;

    public InputBoard prepareInput(InputBoard inputBoard, MappingLog log) {
        List<WidgetObject> relevantElements = inputBoard.getWidgetObjects().stream().filter(widgetObject -> widgetObject instanceof IRelevantText).collect(Collectors.toList());
        for (WidgetObject object: relevantElements) {
            IRelevantText extendedObject = (IRelevantText) object;
            extendedObject.setMappingRelevantText(stringValidator.correctInput(extendedObject.getMappingRelevantText(), log));
        }
        return inputBoard;
    }
}
