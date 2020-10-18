package ch.ost.rj.sa.miro2cml.data_access;

import ch.ost.rj.sa.miro2cml.data_access.miro_model.json.Data;
import ch.ost.rj.sa.miro2cml.data_access.miro_model.json.Root;
import ch.ost.rj.sa.miro2cml.data_access.miro_model.pojo.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

public class MiroApiConsumer {
    public static void getBoard(String accessToken, String boardID) {

        String query = "access_token=" + accessToken;
        String url = "https://api.miro.com/v1/boards/" + boardID + "/widgets/";
        Charset charset = java.nio.charset.StandardCharsets.UTF_8;
        try {
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset.displayName());
            InputStream response = connection.getInputStream();
            /*
            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();

            System.out.println(responseBody);
            */
            //create ObjectMapper instance


            ObjectMapper objectMapper = new ObjectMapper();

            //read json file and convert to customer object
            Root widgetList = objectMapper.readValue(response, Root.class);

            //print customer details
            System.out.println(widgetList);

            ArrayList<WidgetObject> list = convertMiroJsonToPojo(widgetList);
            System.out.println(list);

        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    System.out.println("line:");
                    System.out.println("line start:"+data.getStartWidget().getId());
                    System.out.println("line end: "+data.getEndWidget().getId());
                    break;
                default:
                    System.out.println("invalid widgetType: "+ data.getType());
            }
        }
        return widgetList;
    }
}
