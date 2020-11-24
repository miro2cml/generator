package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import org.contextmapper.dsl.contextMappingDSL.Aggregate;
import org.contextmapper.dsl.contextMappingDSL.ContextMappingDSLFactory;
import org.contextmapper.tactic.dsl.tacticdsl.CommandEvent;
import org.contextmapper.tactic.dsl.tacticdsl.DomainEvent;
import org.contextmapper.tactic.dsl.tacticdsl.TacticdslFactory;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.Map;

public class EventStorming implements ICmlArtifact {
    private ArrayList<AggregatesCML> aggregates;
    private String issues;

    public EventStorming(ArrayList<AggregatesCML> aggregates, String issues) {
        this.aggregates = aggregates;
        this.issues = issues;
    }

    @Override
    public EObject provideEObject() {
        org.contextmapper.dsl.contextMappingDSL.BoundedContext boundedContext = ContextMappingDSLFactory.eINSTANCE.createBoundedContext();
        for (AggregatesCML thisAggregate: aggregates) {
            ContextMappingDSLFactory factory =ContextMappingDSLFactory.eINSTANCE;
            Aggregate aggregate = factory.createAggregate();
            aggregate.setName(thisAggregate.getName());
            boundedContext.getAggregates().add(aggregate);
            addDomainEvents(aggregate, thisAggregate);
            addCommands(aggregate, thisAggregate);
        }
        boundedContext.setComment(issues);
        boundedContext.setName("EventStormingBoundedContext");

        return boundedContext;
    }

    private void addCommands(Aggregate aggregate, AggregatesCML thisAggregate) {
        for ( Map.Entry<String, String> entry : thisAggregate.getCommands().entrySet()) {
            CommandEvent commandEvent = TacticdslFactory.eINSTANCE.createCommandEvent();
            commandEvent.setName(entry.getKey());
            commandEvent.setComment(entry.getValue());
            aggregate.getDomainObjects().add(commandEvent);
        }
    }

    private void addDomainEvents(Aggregate aggregate, AggregatesCML thisAggregate) {
        for ( Map.Entry<String, String> entry : thisAggregate.getDomainEvents().entrySet()) {
            DomainEvent domainEvent = TacticdslFactory.eINSTANCE.createDomainEvent();
            domainEvent.setName(entry.getKey());
            domainEvent.setComment(entry.getValue());
            aggregate.getDomainObjects().add(domainEvent);
        }
    }

    @Override
    public String toString() {
        return "EventStorming{" +
                "aggregates=" + aggregates +
                ", issues='" + issues + '\'' +
                '}';
    }
}
