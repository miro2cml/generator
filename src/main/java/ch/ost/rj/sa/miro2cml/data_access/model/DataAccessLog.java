package ch.ost.rj.sa.miro2cml.data_access.model;

import java.util.ArrayList;
import java.util.List;

public class DataAccessLog {
    private final ArrayList<String> logEntries = new ArrayList<>();
    private int warningCounter;
    private int errorCounter;
    private boolean maxWidgetsCountExceeded;

    public boolean isMaxWidgetsCountExceeded() {
        return maxWidgetsCountExceeded;
    }

    public void setMaxWidgetsCountExceeded(boolean maxWidgetsCountExceeded) {
        this.maxWidgetsCountExceeded = maxWidgetsCountExceeded;
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
        logEntries.add("---WARNING: " + entry);
        warningCounter++;
    }

    public void addSuccessLogEntry(String entry) {
        logEntries.add("Success: " + entry);
    }

    public void addErrorLogEntry(String entry) {
        logEntries.add("---ERROR: " + entry);
        errorCounter++;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("---------------------------------------------------------------------------------------------------------------------------------------------------------").append(System.lineSeparator());
        builder.append("---------------------------------------------------------------------Data Access Log---------------------------------------------------------------------").append(System.lineSeparator()).append(System.lineSeparator());
        builder.append(errorCounter).append(" Error(s) occurred").append(System.lineSeparator());
        builder.append(warningCounter).append(" Warning(s) occurred").append(System.lineSeparator());
        for (String entry : logEntries
        ) {
            builder.append(entry).append(System.lineSeparator());
        }
        builder.append("---------------------------------------------------------------------------------------------------------------------------------------------------------").append(System.lineSeparator());
        return builder.toString();
    }

    public void addSectionSeparator() {
        logEntries.add("---------------------------------------------------------------------");
    }
}
