package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import org.checkerframework.common.value.qual.StringVal;

import java.util.ArrayList;

public class FlowStep {
    private final double position;
    private final String command;
    private final String event;
    private final String role;
    private final ArrayList<String> triggers;

    public FlowStep(double position,String command, String event, String role, ArrayList<String> triggers) {
        this.position = position;
        this.command = command;
        this.event = event;
        this.role = role;
        this.triggers = triggers;
    }

    public String getCommand() {
        return command;
    }

    public String getEvent() {
        return event;
    }

    public String getRole(){ return role; }

    public ArrayList<String> getTriggers() {
        return triggers;
    }

    public double getPosition() {
        return position;
    }

}
