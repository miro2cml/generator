package ch.ost.rj.sa.miro2cml.data_access.model;

import ch.ost.rj.sa.miro2cml.model.widgets.WidgetObject;

import java.util.List;

public class WidgetCollection {
    private List<WidgetObject> widgets;
    private DataAccessLog dataAccessLog;
    private final boolean success;

    public List<WidgetObject> getWidgets() {
        return widgets;
    }

    public DataAccessLog getDataAccessLog() {
        return dataAccessLog;
    }

    public WidgetCollection(List<WidgetObject> widgets, DataAccessLog dataAccessLog, boolean success) {
        this.widgets = widgets;
        this.dataAccessLog = dataAccessLog;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
