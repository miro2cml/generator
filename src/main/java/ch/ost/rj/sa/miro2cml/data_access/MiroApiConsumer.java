package ch.ost.rj.sa.miro2cml.data_access;

import ch.ost.rj.sa.miro2cml.data_access.miro_model.json.Root;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class MiroApiConsumer {
    public static void getBoard(String accessToken, String boardID) {

        String query = "access_token=" + accessToken;
        String url = "https://api.miro.com/v1/boards/"+ boardID +"/widgets/";
        Charset charset = java.nio.charset.StandardCharsets.UTF_8;
        try {
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset.displayName());
            InputStream response = connection.getInputStream();
            /*
            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();

            System.out.println(responseBody);
            //create ObjectMapper instance
            */
            ///*
            ObjectMapper objectMapper = new ObjectMapper();


            //read json file and convert to customer object
            Root widgetList = objectMapper.readValue(response, Root.class);

            //print customer details
            System.out.println(widgetList);
            //*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
