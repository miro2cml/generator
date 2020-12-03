package ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.*;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingGroup;

import java.util.*;

public class EventStormingConverter {
    public static EventStorming convertEventStormingBoardtoCML(EventStormingBoard board){
        var inputs = board.getConnections();
        ArrayList<String> aggregateList = getAggregateList(inputs);
        ArrayList<AggregatesCML> aggregatesCMLList = getAggregateCMLList(inputs, aggregateList);
        return new EventStorming(aggregatesCMLList, "/* " + board.getIssues().toString() + "*/");
    }

    private static ArrayList<AggregatesCML> getAggregateCMLList(ArrayList<EventStormingGroup> inputs, ArrayList<String> aggregateList) {
        ArrayList<AggregatesCML> aggregatesCMLList = new ArrayList<>();
        for (String aggregate: aggregateList) {
            String name = StringValidator.convertForVariableName(aggregate);
            ArrayList<FlowStep> step = new ArrayList<>();
            for (EventStormingGroup input : inputs) {
                if (input.getAgggregate().get(0).equals(aggregate)) {
                    step.add(new FlowStep(input.getPosition(), StringValidator.convertForVariableName(input.getCommand()), StringValidator.convertForVariableName(input.getDomainEvent()), StringValidator.convertForVariableName(input.getRole()), generateTriggers((ArrayList<String>) input.getTrigger())));
                }
            }
            aggregatesCMLList.add(new AggregatesCML(name, step));

        }
        return aggregatesCMLList;
    }

    private static ArrayList<String> getAggregateList(ArrayList<EventStormingGroup> inputs) {
        ArrayList<String> output = new ArrayList<>();
        for(EventStormingGroup input : inputs) {
            List<String> aggregates = input.getAgggregate();
            for(String aggregate: aggregates){
                if (output.contains(aggregate)) {
                    break;
                } else { output.add(aggregate);
                }
            }
        }
        return output;
    }


    private static ArrayList<String> generateTriggers(ArrayList<String> trigger) {
        if (!trigger.isEmpty()) {
            trigger.remove(0);
            trigger.replaceAll(StringValidator::convertForVariableName);
        }
        return trigger;
    }
}
