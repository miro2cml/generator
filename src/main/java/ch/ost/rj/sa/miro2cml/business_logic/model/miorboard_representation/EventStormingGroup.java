package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import java.util.List;

public class EventStormingGroup {
    private double position;
    private String domainEvent;
    private String command;
    private List<String> agggregate;
    private String role;
    private List<String> trigger;
    //TODO: modify with logic from event-storming CheatSheet (exapmle more than one aggregate possible)
    public EventStormingGroup(double position, String domainEvent, String command, List<String> agggregate, String role, List<String> trigger) {
        this.position = position;
        this.domainEvent = domainEvent;
        this.command = command;
        this.agggregate = agggregate;
        this.role = role;
        this.trigger = trigger;
    }

    public String getDomainEvent() {
        return domainEvent;
    }

    public void setDomainEvent(String domainEvent) {
        this.domainEvent = domainEvent;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<String> getAgggregate() {
        return agggregate;
    }

    public void setAgggregate(List<String> agggregate) {
        this.agggregate = agggregate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getTrigger() {
        return trigger;
    }

    public void setTrigger(List<String> trigger) {
        this.trigger = trigger;
    }

    public double getPosition() {
        return position;
    }

    public int compareTo(Object o) {
        EventStormingGroup group = (EventStormingGroup) o;
        if (getPosition() < group.getPosition()) {
            return -1;
        } else if (getPosition() > group.getPosition()) {
            return 1;
        }
        return 0;
    }
}
