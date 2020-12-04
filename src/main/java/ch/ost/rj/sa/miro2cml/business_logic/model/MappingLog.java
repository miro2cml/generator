package ch.ost.rj.sa.miro2cml.business_logic.model;

import ch.ost.rj.sa.miro2cml.data_access.model.DataAccessLog;

import java.util.ArrayList;
import java.util.List;

public class MappingLog {
    private final String boardID;
    private final ArrayList<String> logEntries = new ArrayList<>();
    private String metaData = "";

    public MappingLog(String boardID) {
        this.boardID = boardID;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public void addDataAccessLogEntries(DataAccessLog dataAccessLog) {
        logEntries.addAll(dataAccessLog.getLogEntries());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(metaData);
        for (String entry : logEntries
        ) {
            builder.append(entry).append(System.lineSeparator());
        }
        return builder.toString();
    }

    public List<String> getLogEntries() {
        return logEntries;
    }

    public void addLogEntry(String entry) {
        logEntries.add(entry);
    }

    public void addInfoLogEntry(String entry) {
        logEntries.add("Info: " + entry);
    }

    public void addWarningLogEntry(String entry) {
        logEntries.add("WARNING: " + entry);
    }

    public void addSuccessLogEntry(String entry) {
        logEntries.add("Success: " + entry);
    }

    public void addErrorLogEntry(String entry) {
        logEntries.add("ERROR: " + entry);
    }
}
