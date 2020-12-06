package ch.ost.rj.sa.miro2cml.data_access;

import ch.ost.rj.sa.miro2cml.data_access.model.DataAccessLog;
import ch.ost.rj.sa.miro2cml.data_access.model.WidgetCollection;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.boards.BoardCollection;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.WidgetsCollection;
import ch.ost.rj.sa.miro2cml.model.boards.BoardPresentation;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static ch.ost.rj.sa.miro2cml.data_access.MiroToGenericConverter.createGenericWidgetObjectsFromJsonStructuredObjects;
import static ch.ost.rj.sa.miro2cml.data_access.MiroToGenericConverter.createMiroBoardListFromJsonBoardCollection;
import static ch.ost.rj.sa.miro2cml.data_access.ResponseToMiroJsonConverter.*;

public class MiroApiServiceAdapter {
    private MiroApiServiceAdapter(){}

    public static WidgetCollection getBoardWidgets(String accessToken, String boardID) {
        DataAccessLog dataAccessLog = new DataAccessLog();
        dataAccessLog.addInfoLogEntry("Send GetAllWidgets Request to MiroApi");
        String query = "access_token=" + accessToken;
        String url = "https://api.miro.com/v1/boards/" + boardID + "/widgets/";
        Charset charset = java.nio.charset.StandardCharsets.UTF_8;
        try {
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset.displayName());
            InputStream responseStream = connection.getInputStream();
            dataAccessLog.addInfoLogEntry("Received BoardWidgets from Miro (as JSON)");
            dataAccessLog.addSectionSeparator();
            dataAccessLog.addInfoLogEntry("Start reading miroResponse (JSON-Parsing)");
            WidgetsCollection widgetList = convertJsonResponseStreamIntoWidgetCollection(responseStream, dataAccessLog);
            dataAccessLog.addInfoLogEntry("JSON reading finished");
            dataAccessLog.addSectionSeparator();

            dataAccessLog.addInfoLogEntry("Start restructuring data (convert it into useful typed instances)");
            List<WidgetObject> widgetObjectList = createGenericWidgetObjectsFromJsonStructuredObjects(widgetList, dataAccessLog);
            dataAccessLog.addInfoLogEntry("Finished data restructuring");
            dataAccessLog.addSectionSeparator();

            dataAccessLog.addInfoLogEntry("Finished Data retrieval for MiroBoard: " + boardID);
            return new WidgetCollection(widgetObjectList,dataAccessLog, true);
        } catch (IOException e) {
            e.printStackTrace();
            dataAccessLog.addErrorLogEntry("Error during MiroApiCall: " + e.getMessage());
            return new WidgetCollection(new ArrayList<>(),dataAccessLog, false);
        }
    }

    public static List<BoardPresentation> getMiroBoards(String accessToken, String teamId) {
        final int limit = 300;
        String query = "access_token=" + accessToken + "&" + "limit=" + limit;
        String url = "https://api.miro.com/v1/teams/" + teamId + "/boards/";
        Charset charset = java.nio.charset.StandardCharsets.UTF_8;
        try {
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset.displayName());
            InputStream responseStream = connection.getInputStream();

            BoardCollection boardCollection = convertJsonResponseStreamIntoMiroBoardCollection(responseStream);
            List<BoardPresentation> boardPresentationList = createMiroBoardListFromJsonBoardCollection(boardCollection);
            boardPresentationList.sort(((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName())));
            return boardPresentationList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(); //TODO: handle this case ( global error management with useful error messages in frontend...
    }
}
