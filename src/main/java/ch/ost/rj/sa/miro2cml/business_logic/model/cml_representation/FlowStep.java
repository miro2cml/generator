package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import java.util.ArrayList;
import java.util.Comparator;

public class FlowStep {
    private final double position;
    private final String command;
    private final String event;
    private final ArrayList<String> triggers;

    public FlowStep(double position,String command, String event, ArrayList<String> triggers) {
        this.position = position;
        this.command = command;
        this.event = event;
        this.triggers = triggers;
    }

    public String getCommand() {
        return command;
    }

    public String getEvent() {
        return event;
    }

    public ArrayList<String> getTriggers() {
        return triggers;
    }

    public double getPosition() {
        return position;
    }

}
