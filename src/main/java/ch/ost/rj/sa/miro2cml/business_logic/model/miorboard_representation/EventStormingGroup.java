package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

import java.util.List;

public class EventStormingGroup {
    private double position;
    private String domainEvent;
    private String command;
    private List<String> agggregates;
    private String role;
    private List<String> trigger;
    public EventStormingGroup(double position, String domainEvent, String command, List<String> agggregates, String role, List<String> trigger) {
        this.position = position;
        this.domainEvent = domainEvent;
        this.command = command;
        this.agggregates = agggregates;
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

    public List<String> getAgggregates() {
        return agggregates;
    }

    public void setAggregates(List<String> aggregates) {
        this.agggregates = aggregates;
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
