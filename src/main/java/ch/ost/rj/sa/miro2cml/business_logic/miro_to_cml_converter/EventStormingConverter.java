package ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.AggregatesCML;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.EventStorming;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventStormingConverter {
    public static EventStorming convertEventStormingBoardtoCML(EventStormingBoard board){
        var inputs = board.getConnections();
        ArrayList<String> aggregateList = new ArrayList<>();
        ArrayList<AggregatesCML> aggregatesCMLS = new ArrayList<>();
        for(ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingGroup input : inputs) {
            String aggregate = input.getAgggregate();
            if (aggregateList.contains(aggregate)) {
                break;
            } else { aggregateList.add(aggregate);
            }
        }
        for (String aggregate: aggregateList) {
            String name = StringValidator.convertForVariableName(aggregate);
            Map<String, String> commands = new HashMap<>();
            Map<String, String> events = new HashMap<>();
            for (ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.EventStormingGroup input : inputs) {
                if (input.getAgggregate().equals(aggregate)) {
                    commands.put(StringValidator.convertForVariableName(input.getCommand()), "/* role " + StringValidator.validatorForStrings(input.getRole()) + "*/");
                    if (!(input.getDomainEvent().equals(""))) {
                        events.put(StringValidator.convertForVariableName(input.getDomainEvent()), "/* triggers" + StringValidator.validatorForStrings(input.getTrigger()) + "*/");
                    }
                }
            }
            aggregatesCMLS.add(new AggregatesCML(name, events, commands));

        }
        return new EventStorming(aggregatesCMLS, "/* " + board.getIssues().toString() + "*/");
    }
}
