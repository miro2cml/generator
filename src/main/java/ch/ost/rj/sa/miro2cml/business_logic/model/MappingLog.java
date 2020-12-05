package ch.ost.rj.sa.miro2cml.business_logic.model;

import ch.ost.rj.sa.miro2cml.data_access.model.DataAccessLog;

import java.util.ArrayList;
import java.util.List;

public class MappingLog implements IOutputArtifact{
    private final ArrayList<String> logEntries = new ArrayList<>();
    private String metaData = "";
    private DataAccessLog dataAccessLog = null;

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public void setDataAccessLog(DataAccessLog dataAccessLog) {
        this.dataAccessLog = dataAccessLog;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(metaData);
        for (String entry : logEntries
        ) {
            builder.append(entry).append(System.lineSeparator());
        }
        builder.append(dataAccessLog.toString());
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

    public void addMappingLog(MappingLog additionalMappingLog){
        logEntries.addAll(additionalMappingLog.getLogEntries());
    }
    public void clear(){
        logEntries.clear();
    }

    public void addSectionSeparator(){
        logEntries.add("---------------------------------------------------------------------");
    }

    @Override
    public byte[] toByteArray() {
        return toString().getBytes();
    }
}
