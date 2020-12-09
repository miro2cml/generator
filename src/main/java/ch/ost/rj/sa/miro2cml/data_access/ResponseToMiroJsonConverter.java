package ch.ost.rj.sa.miro2cml.data_access;

import ch.ost.rj.sa.miro2cml.data_access.model.DataAccessLog;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.boards.BoardCollection;
import ch.ost.rj.sa.miro2cml.data_access.model.miro.widgets.WidgetsCollection;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ResponseToMiroJsonConverter {
    static WidgetsCollection convertJsonResponseStreamIntoWidgetCollection(InputStream response, DataAccessLog dataAccessLog) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(response, WidgetsCollection.class);
    }

    static BoardCollection convertJsonResponseStreamIntoMiroBoardCollection(InputStream response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(response, BoardCollection.class);
    }
}
