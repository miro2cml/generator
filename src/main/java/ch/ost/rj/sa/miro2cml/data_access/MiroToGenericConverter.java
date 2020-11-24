package ch.ost.rj.sa.miro2cml.data_access;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.boards.BoardCollection;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.MiroWidget;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.WidgetsCollection;
import ch.ost.rj.sa.miro2cml.model.boards.BoardPresentation;
import ch.ost.rj.sa.miro2cml.model.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class MiroToGenericConverter {

    static List<WidgetObject> createGenericWidgetObjectsFromJsonStructuredObjects(WidgetsCollection miroJson) {
        ArrayList<WidgetObject> widgetList = new ArrayList<>();
        for (MiroWidget miroWidget : miroJson.getData()
        ) {
            switch (miroWidget.getType()) {
                case "card":
                    widgetList.add(new Card(miroWidget));
                    break;
                case "sticker":
                    widgetList.add(new Sticker(miroWidget));
                    break;
                case "text":
                    widgetList.add(new Text(miroWidget));
                    break;
                case "shape":
                    widgetList.add(new Shape(miroWidget));
                    break;
                case "line":
                    widgetList.add(new Line(miroWidget));
                    break;
                default:
                    //TODO: handle undefined dataTypes
            }
        }
        return widgetList;
    }

    static List<BoardPresentation> createMiroBoardListFromJsonBoardCollection(BoardCollection boardCollection) {
        List<BoardPresentation> boards = new ArrayList<>();
        for (ch.ost.rj.sa.miro2cml.data_access.model.miro.boards.Data data : boardCollection.getData()) {
            boards.add(new BoardPresentation(data));
        }
        return boards;
    }
}
