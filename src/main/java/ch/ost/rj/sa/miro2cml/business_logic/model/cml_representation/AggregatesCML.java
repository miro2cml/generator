package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import java.util.Map;

public class AggregatesCML{
    private String name;
    private Map<String, String> domainEvents;
    private Map<String, String> commands;

    public AggregatesCML(String name, Map<String, String> domainEvents, Map<String, String> commands) {
        this.name = name;
        this.domainEvents = domainEvents;
        this.commands = commands;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getDomainEvents() {
        return domainEvents;
    }

    public Map<String, String> getCommands() {
        return commands;
    }
}
