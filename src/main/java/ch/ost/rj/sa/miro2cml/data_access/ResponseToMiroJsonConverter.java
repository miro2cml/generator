package ch.ost.rj.sa.miro2cml.data_access;

import ch.ost.rj.sa.miro2cml.data_access.model.miro.boards.BoardCollection;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.WidgetsCollection;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ResponseToMiroJsonConverter {
    static WidgetsCollection convertJsonResponseStreamIntoWidgetCollection(InputStream response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, WidgetsCollection.class);
    }

    static BoardCollection convertJsonResponseStreamIntoMiroBoardCollection(InputStream response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, BoardCollection.class);
    }
}
