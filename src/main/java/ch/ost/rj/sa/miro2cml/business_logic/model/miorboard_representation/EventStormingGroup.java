package ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation;

public class EventStormingGroup {
    private String domainEvent;
    private String command;
    private String agggregate;
    private String role;
    private String trigger;
    //TODO: modify with logic from event-storming CheatSheet (exapmle more than one aggregate possible)
    public EventStormingGroup(String domainEvent, String command, String agggregate, String role, String trigger) {
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

    public String getAgggregate() {
        return agggregate;
    }

    public void setAgggregate(String agggregate) {
        this.agggregate = agggregate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }
}
