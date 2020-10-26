package ch.ost.rj.sa.miro2cml.data_access;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.json.Data;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.json.Root;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.pojo.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MiroApiConsumer {
    public static ArrayList<WidgetObject> getBoardWidgets(String accessToken, String boardID) {

        String query = "access_token=" + accessToken;
        String url = "https://api.miro.com/v1/boards/" + boardID + "/widgets/";
        Charset charset = java.nio.charset.StandardCharsets.UTF_8;
        try {
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset.displayName());
            InputStream responseStream = connection.getInputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            Root widgetList = convertJsonResponseStreamIntoRootObject(responseStream, objectMapper);

            return convertMiroJsonToPojo(widgetList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; //TODO: handle this case ( global error management with useful error messages in frontend...
    }

    private static Root convertJsonResponseStreamIntoRootObject(InputStream response, ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(response, Root.class);
    }

    private static ArrayList<WidgetObject> convertMiroJsonToPojo(Root miroJson) {
        ArrayList<WidgetObject> widgetList = new ArrayList<>();
        for (Data data : miroJson.getData()
        ) {
            switch (data.getType()) {
                case "card":
                    widgetList.add(new Card(data));
                    break;
                case "sticker":
                    widgetList.add(new Sticker(data));
                    break;
                case "text":
                    widgetList.add(new Text(data));
                    break;
                case "shape":
                    widgetList.add(new Shape(data));
                    break;
                case "line":
                    widgetList.add(new Line(data));
                    break;
                default:
                    //TODO: handle undefined dataTypes
            }
        }
        return widgetList;
    }
}
