package ch.ost.rj.sa.miro2cml.business_logic.model.miroboard_representation;

import java.util.List;

public class EventStormingGroup {
    private final double positionX;
    private final double positionY;
    private String domainEvent;
    private String command;
    private List<String> aggregates;
    private String role;
    private List<String> trigger;

    public EventStormingGroup(double positionX, double positionY, String domainEvent, String command, List<String> aggregates, String role, List<String> trigger) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.domainEvent = domainEvent;
        this.command = command;
        this.aggregates = aggregates;
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

    public List<String> getAggregates() {
        return aggregates;
    }

    public void setAggregates(List<String> aggregates) {
        this.aggregates = aggregates;
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

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }


    public int compareTo(Object o) {
        EventStormingGroup group = (EventStormingGroup) o;
        if (getPositionX() < group.getPositionX()) {
            return -1;
        } else if (getPositionX() > group.getPositionX()) {
            return 1;
        }
        if (getPositionY() < group.getPositionY()) {
            return -1;
        } else if (getPositionY() > group.getPositionY()) {
            return 1;
        }
        return 0;
    }
}
