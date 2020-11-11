package ch.ost.rj.sa.miro2cml.data_access;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.boards.BoardCollection;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.WidgetsCollection;
import ch.ost.rj.sa.miro2cml.model.boards.BoardPresentation;
import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

import static ch.ost.rj.sa.miro2cml.data_access.MiroToGenericConverter.createGenericWidgetObjectsFromJsonStructuredObjects;
import static ch.ost.rj.sa.miro2cml.data_access.MiroToGenericConverter.createMiroBoardListFromJsonBoardCollection;
import static ch.ost.rj.sa.miro2cml.data_access.ResponseToMiroJsonConverter.convertJsonResponseStreamIntoMiroBoardCollection;
import static ch.ost.rj.sa.miro2cml.data_access.ResponseToMiroJsonConverter.convertJsonResponseStreamIntoWidgetCollection;

public class MiroApiServiceAdapter {
    public static List<WidgetObject> getBoardWidgets(String accessToken, String boardID) {

        String query = "access_token=" + accessToken;
        String url = "https://api.miro.com/v1/boards/" + boardID + "/widgets/";
        Charset charset = java.nio.charset.StandardCharsets.UTF_8;
        try {
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset.displayName());
            InputStream responseStream = connection.getInputStream();

            WidgetsCollection widgetList = convertJsonResponseStreamIntoWidgetCollection(responseStream);
            return createGenericWidgetObjectsFromJsonStructuredObjects(widgetList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; //TODO: handle this case ( global error management with useful error messages in frontend...
    }

    public static List<BoardPresentation> getMiroBoards(String accessToken, String teamId) {
        String query = "access_token=" + accessToken;
        String url = "https://api.miro.com/v1/teams/" + teamId + "/boards/";
        Charset charset = java.nio.charset.StandardCharsets.UTF_8;
        try {
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset.displayName());
            InputStream responseStream = connection.getInputStream();

            BoardCollection boardCollection = convertJsonResponseStreamIntoMiroBoardCollection(responseStream);

            return createMiroBoardListFromJsonBoardCollection(boardCollection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; //TODO: handle this case ( global error management with useful error messages in frontend...
    }
}
