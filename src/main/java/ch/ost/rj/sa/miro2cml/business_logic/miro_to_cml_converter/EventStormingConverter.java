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
            ArrayList<FlowStep> step = new ArrayList<>();
            for (ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingGroup input : inputs) {
                if (input.getAgggregate().get(0).equals(aggregate)) {
                    step.add(new FlowStep(input.getPosition(), StringValidator.convertForVariableName(input.getCommand()), StringValidator.convertForVariableName(input.getDomainEvent()), StringValidator.convertForVariableName(input.getRole()), generateTriggers((ArrayList<String>) input.getTrigger())));
                }
            }
            aggregatesCMLList.add(new AggregatesCML(name, step));

        }
        return new EventStorming(aggregatesCMLList, "/* " + board.getIssues().toString() + "*/");
    }


    private static ArrayList<String> generateTriggers(ArrayList<String> trigger) {
        if(trigger.isEmpty()){
            return trigger;
        }else{
            trigger.remove(0);
            for (String s: trigger) {
                StringValidator.convertForVariableName(s);
            }
            return trigger;
        }
    }
}
