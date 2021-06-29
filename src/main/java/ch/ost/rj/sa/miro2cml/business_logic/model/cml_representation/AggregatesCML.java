package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import java.util.List;

public class AggregatesCML{
    private final String name;
    private final List<FlowStep> flow;

    public AggregatesCML(String name, List<FlowStep> flow) {
        this.name = name;
        this.flow = flow;
    }

    public String getName() {
        return name;
    }

    public List<FlowStep> getFlow() {
        return flow;
    }

    @Override
    public String toString() {
        return "AggregatesCML{" +
                "name='" + name + '\'' +
                ", flow=" + flow.toString() +
                '}';
    }
}
