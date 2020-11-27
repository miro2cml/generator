package ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.*;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingBoard;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingGroup;
import com.google.inject.internal.util.$AsynchronousComputationException;

import java.util.*;

public class EventStormingConverter {
    public static EventStorming convertEventStormingBoardtoCML(EventStormingBoard board){
        var inputs = board.getConnections();
        ArrayList<String> aggregateList = new ArrayList<>();
        ArrayList<AggregatesCML> aggregatesCMLList = new ArrayList<>();
        for(ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingGroup input : inputs) {
            List<String> aggregates = input.getAgggregate();
            for(String aggregate: aggregates){
                if (aggregateList.contains(aggregate)) {
                    break;
                } else { aggregateList.add(aggregate);
                }
            }
        }
        for (String aggregate: aggregateList) {
            String name = StringValidator.convertForVariableName(aggregate);
            Map<String, String> commands = new HashMap<>();
            Map<String, ArrayList<String>> events = new HashMap<>();
            for (ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingGroup input : inputs) {
                if (input.getAgggregate().get(0).equals(aggregate)) {
                    commands.put(StringValidator.convertForVariableName(input.getCommand()), "/* role " + StringValidator.validatorForStrings(input.getRole()) + "*/");
                    if (!(input.getDomainEvent().equals(""))) {
                        events.put(StringValidator.convertForVariableName(input.getDomainEvent()), generateTriggers((ArrayList<String>) input.getTrigger()));
                    }
                }
            }
            aggregatesCMLList.add(new AggregatesCML(name, events, commands));

        }
        ArrayList<FlowStep> flow = createFlow(board.getConnections());
        return new EventStorming(aggregatesCMLList, flow, "/* " + board.getIssues().toString() + "*/");
    }

    private static ArrayList<FlowStep> createFlow(ArrayList<EventStormingGroup> connections) {
        ArrayList<FlowStep> output = new ArrayList<>();
        for(EventStormingGroup group: connections){
            output.add(new FlowStep(group.getPosition(), StringValidator.convertForVariableName(group.getCommand()), StringValidator.convertForVariableName(group.getDomainEvent()), generateTriggers((ArrayList<String>) group.getTrigger())));
        }
        //TODO Sorting with postion
        output.sort(new SortByPosition());
        return output;
    }

    private static ArrayList<String> generateTriggers(ArrayList<String> trigger) {
        trigger.remove(0);
        for (String s: trigger) {
            s=StringValidator.convertForVariableName(s);
        }
        return trigger;
    }
}
