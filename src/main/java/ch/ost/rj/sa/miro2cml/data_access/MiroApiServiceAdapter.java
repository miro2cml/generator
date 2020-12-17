package ch.ost.rj.sa.miro2cml.data_access;

import ch.ost.rj.sa.miro2cml.data_access.model.DataAccessLog;
import ch.ost.rj.sa.miro2cml.data_access.model.WidgetCollection;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.boards.BoardCollection;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.WidgetsCollection;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.BoardMetaData;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.widgets.WidgetObject;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ch.ost.rj.sa.miro2cml.data_access.MiroToMiro2cmlConverter.createGenericWidgetObjectsFromJsonStructuredObjects;
import static ch.ost.rj.sa.miro2cml.data_access.MiroToMiro2cmlConverter.createMiroBoardListFromJsonBoardCollection;
import static ch.ost.rj.sa.miro2cml.data_access.ResponseToMiroJsonConverter.*;

@Service
public class MiroApiServiceAdapter {


    private final String apiBaseUrl;
    private final Integer boardCountLimit;

    public MiroApiServiceAdapter(@Value("${miro.api.baseUrl}") String apiBaseUrl, @Value("${miro.api.boardCountLimit}") Integer boardCountLimit
    ) {
        this.boardCountLimit = boardCountLimit;
        this.apiBaseUrl = apiBaseUrl;
    }



    public WidgetCollection getBoardWidgets(String accessToken, String boardID) {
        System.out.println(new Date().getTime());
        DataAccessLog dataAccessLog = new DataAccessLog();
        dataAccessLog.addInfoLogEntry("Send GetAllWidgets Request to MiroApi");
        String query = "access_token=" + accessToken;
        String url = apiBaseUrl+"/boards/" + boardID + "/widgets/";
        Charset charset = java.nio.charset.StandardCharsets.UTF_8;
        try {
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset.displayName());
            InputStream responseStream = connection.getInputStream();
            dataAccessLog.addInfoLogEntry("Received BoardWidgets from Miro (as JSON)");
            dataAccessLog.addSectionSeparator();
            dataAccessLog.addInfoLogEntry("Start reading miroResponse (JSON-Parsing)");
            WidgetsCollection widgetList = convertJsonResponseStreamIntoWidgetCollection(responseStream);
            dataAccessLog.addInfoLogEntry("JSON reading finished");
            dataAccessLog.addSectionSeparator();

            dataAccessLog.addInfoLogEntry("Start restructuring data (convert data into usefully typed objects)");
            List<WidgetObject> widgetObjectList = createGenericWidgetObjectsFromJsonStructuredObjects(widgetList, dataAccessLog);
            dataAccessLog.addInfoLogEntry("Finished data restructuring");
            dataAccessLog.addSectionSeparator();

            dataAccessLog.addInfoLogEntry("Finished Data retrieval for MiroBoard: " + boardID);

            if (widgetObjectList.size()>=1000){
                dataAccessLog.setMaxWidgetsCountExceeded(true);
                dataAccessLog.addWarningLogEntry("We received 1000 Widgets from Miro. This is the maximum we can receive per Board. Thus its possible, that we didn't receive all of your widgets. If you really need more than 1000 Widgets, please split them up and distribute them  on multiple boards.");
            }
            System.out.println(new Date().getTime());
            return new WidgetCollection(widgetObjectList,dataAccessLog, true);
        } catch (IOException e) {
            e.printStackTrace();
            dataAccessLog.addErrorLogEntry("Error during MiroApiCall: " + e.getMessage());
            return new WidgetCollection(new ArrayList<>(),dataAccessLog, false);
        }
    }

    public ImmutableTriple<Boolean,Boolean,List<BoardMetaData>> getMiroBoards(String accessToken, String teamId) {
        boolean success = true;
        boolean limitExceeded = false;
        List<BoardMetaData> boardMetaDataList = new ArrayList<>();

        String query = "access_token=" + accessToken + "&" + "limit=" + boardCountLimit;
        String url = apiBaseUrl+"/teams/" + teamId + "/boards/";
        Charset charset = java.nio.charset.StandardCharsets.UTF_8;
        try {
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset.displayName());
            InputStream responseStream = connection.getInputStream();

            BoardCollection boardCollection = convertJsonResponseStreamIntoMiroBoardCollection(responseStream);
            boardMetaDataList = createMiroBoardListFromJsonBoardCollection(boardCollection);
            boardMetaDataList.sort(((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName())));
            limitExceeded = (boardMetaDataList.size()>boardCountLimit);


        } catch (IOException e) {
            success = false;
        }
        return new ImmutableTriple<>(success,limitExceeded, boardMetaDataList);
    }
}
