package ch.ost.rj.sa.miro2cml.data_access;

import ch.ost.rj.sa.miro2cml.data_access.model.DataAccessLog;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.boards.BoardCollection;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.MiroWidget;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.WidgetsCollection;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.BoardPresentationData;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class MiroToGenericConverter {
    private MiroToGenericConverter() {
    }

    static List<WidgetObject> createGenericWidgetObjectsFromJsonStructuredObjects(WidgetsCollection miroJson, DataAccessLog dataAccessLog) {
        ArrayList<WidgetObject> widgetList = new ArrayList<>();
        for (MiroWidget miroWidget : miroJson.getData()
        ) {
            switch (miroWidget.getType()) {
                case "card":
                    Card card = new Card(miroWidget);
                    widgetList.add(card);
                    dataAccessLog.addSuccessLogEntry("Created " + card.toString() + " from " + miroWidget.toShortString());
                    break;
                case "sticker":
                    Sticker sticker = new Sticker(miroWidget);
                    widgetList.add(sticker);
                    dataAccessLog.addSuccessLogEntry("Created " + sticker.toString() + " from " + miroWidget.toShortString());
                    break;
                case "text":
                    Text text = new Text(miroWidget);
                    widgetList.add(text);
                    dataAccessLog.addSuccessLogEntry("Created " + text.toString() + " from " + miroWidget.toShortString());
                    break;
                case "shape":
                    Shape shape = new Shape(miroWidget);
                    widgetList.add(shape);
                    dataAccessLog.addSuccessLogEntry("Created " + shape.toString() + " from " + miroWidget.toShortString());
                    break;
                case "line":
                    Line line = new Line(miroWidget);
                    widgetList.add(line);
                    dataAccessLog.addSuccessLogEntry("Created " + line.toString() + " from " + miroWidget.toShortString());
                    break;
                default:
                    dataAccessLog.addWarningLogEntry("Unknown Widget Type detected, detected type: "+miroWidget.getType() + " in widget " + miroWidget.toString());
            }
        }
        return widgetList;
    }

    static List<BoardPresentationData> createMiroBoardListFromJsonBoardCollection(BoardCollection boardCollection) {
        List<BoardPresentationData> boards = new ArrayList<>();
        for (ch.ost.rj.sa.miro2cml.data_access.model.miro.boards.Data data : boardCollection.getData()) {
            boards.add(new BoardPresentationData(data));
        }
        return boards;
    }
}
