package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;


import java.util.List;

public class FlowStep {
    private final double position;
    private final String command;
    private final String event;
    private final String role;
    private final List<String> triggers;

    public FlowStep(double position,String command, String event, String role, List<String> triggers) {
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

    public List<String> getTriggers() {
        return triggers;
    }

    public double getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "FlowStep{" +
                "position=" + position +
                ", command='" + command + '\'' +
                ", event='" + event + '\'' +
                ", role='" + role + '\'' +
                ", triggers=" + triggers +
                '}';
    }
}
