package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.model.InputBoard;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;


public class InputValidation {
    public static void validate(InputBoard inputBoard) {
        inputBoard.getWidgetObjects().forEach(WidgetObject::validate);
    }
}
