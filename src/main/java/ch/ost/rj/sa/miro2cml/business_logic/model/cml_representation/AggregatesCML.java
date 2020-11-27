package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import java.util.ArrayList;
import java.util.Map;

public class AggregatesCML{
    private String name;
    private Map<String, ArrayList<String>> domainEvents;
    private Map<String, String> commands;

    public AggregatesCML(String name, Map<String, ArrayList<String>> domainEvents, Map<String, String> commands) {
        this.name = name;
        this.domainEvents = domainEvents;
        this.commands = commands;
    }

    public String getName() {
        return name;
    }

    public Map<String, ArrayList<String>> getDomainEvents() {
        return domainEvents;
    }

    public Map<String, String> getCommands() {
        return commands;
    }
}
