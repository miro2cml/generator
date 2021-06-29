package ch.ost.rj.sa.miro2cml.business_logic.board_mapper_services.miro_to_cml_converter;

import ch.ost.rj.sa.miro2cml.business_logic.StringUtility;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.*;
import ch.ost.rj.sa.miro2cml.business_logic.model.miroboard_representation.EventStormingBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.miroboard_representation.EventStormingGroup;

import java.util.*;

public class EventStormingConverter {
    private EventStormingConverter(){}

    public static EventStorming convertEventStormingBoardToCML(EventStormingBoard board){
        List<EventStormingGroup> inputs = board.getConnections();
        List<String> aggregateList = getAggregateList(inputs);
        List<AggregatesCML> aggregatesCMLList = getAggregateCMLList(inputs, aggregateList);
        return new EventStorming(aggregatesCMLList, "/* " + board.getIssues().toString() + "*/");
    }

    private static List<AggregatesCML> getAggregateCMLList(List<EventStormingGroup> inputs, List<String> aggregateList) {
        ArrayList<AggregatesCML> aggregatesCMLList = new ArrayList<>();
        for (String aggregate: aggregateList) {
            String name = StringUtility.convertForVariableName(aggregate);
            ArrayList<FlowStep> step = new ArrayList<>();
            for (EventStormingGroup input : inputs) {
                if (!input.getAggregates().isEmpty()  && input.getAggregates().get(0).equals(aggregate) ) {
                    step.add(new FlowStep(input.getPositionX(), StringUtility.convertForVariableName(input.getCommand()), StringUtility.convertForVariableName(input.getDomainEvent()), StringUtility.convertForVariableName(input.getRole()), generateTriggers((ArrayList<String>) input.getTrigger())));
                }
            }
            aggregatesCMLList.add(new AggregatesCML(name, step));

        }
        return aggregatesCMLList;
    }

    private static List<String> getAggregateList(List<EventStormingGroup> inputs) {
        ArrayList<String> output = new ArrayList<>();
        for(EventStormingGroup input : inputs) {
            List<String> aggregates = input.getAggregates();
            for(String aggregate: aggregates){
                if (output.contains(aggregate)) {
                    break;
                } else { output.add(aggregate);
                }
            }
        }
        return output;
    }


    private static List<String> generateTriggers(List<String> trigger) {
        if (!trigger.isEmpty()) {
            trigger.remove(0);
            trigger.replaceAll(StringUtility::convertForVariableName);
        }
        return trigger;
    }
}
