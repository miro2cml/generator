package ch.ost.rj.sa.miro2cml.data_access.model;

import java.util.ArrayList;
import java.util.List;

public class DataAccessLog {
    private final ArrayList<String> logEntries = new ArrayList<>();

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
