package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import org.contextmapper.dsl.contextMappingDSL.*;
import org.contextmapper.dsl.contextMappingDSL.Flow;
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
        Application application = ContextMappingDSLFactory.eINSTANCE.createApplication();
        Flow flowCML = ContextMappingDSLFactory.eINSTANCE.createFlow();
        application.getFlows().add(flowCML);
        for (AggregatesCML thisAggregate: aggregates) {
            ContextMappingDSLFactory factory =ContextMappingDSLFactory.eINSTANCE;
            Aggregate aggregate = factory.createAggregate();
            aggregate.setName(thisAggregate.getName());
            boundedContext.getAggregates().add(aggregate);
            addCommandsEventsFlow(aggregate, thisAggregate, application, flowCML);
            //addDomainEvents(aggregate, thisAggregate);
            //addCommands(aggregate, thisAggregate);
        }
        boundedContext.setComment(issues);
        boundedContext.setName("EventStormingBoundedContext");
        //Application application = ContextMappingDSLFactory.eINSTANCE.createApplication();
        //Flow flowCML = ContextMappingDSLFactory.eINSTANCE.createFlow();
        //application.getFlows().add(flowCML);
        //addElementsToFlow(application, flowCML);
        boundedContext.setApplication(application);
        return boundedContext;
    }

    private void addCommandsEventsFlow(Aggregate aggregate, AggregatesCML thisAggregate, Application application, Flow flowCML) {
        for(FlowStep steps: thisAggregate.getFlow()){
            //add event
            if(!steps.getCommand().equals("") && !steps.getEvent().equals("") && !steps.getTriggers().isEmpty()){
                DomainEvent domainEvent = TacticdslFactory.eINSTANCE.createDomainEvent();
                domainEvent.setName(steps.getEvent());
                aggregate.getDomainObjects().add(domainEvent);
                //add command
                CommandEvent commandEvent = TacticdslFactory.eINSTANCE.createCommandEvent();
                commandEvent.setName(steps.getCommand());
                aggregate.getDomainObjects().add(commandEvent);
                //steps one flow
                DomainEventProductionStep stepOne = ContextMappingDSLFactory.eINSTANCE.createDomainEventProductionStep();
                EitherCommandOrOperation commandOrOperation = ContextMappingDSLFactory.eINSTANCE.createEitherCommandOrOperation();
                if(!steps.getRole().equals("")){
                    commandOrOperation.setActor(steps.getRole());
                }
                commandOrOperation.setCommand(commandEvent);
                stepOne.setAggregate(aggregate);
                stepOne.setAction(commandOrOperation);
                SingleEventProduction singleEventProduction = ContextMappingDSLFactory.eINSTANCE.createSingleEventProduction();
                singleEventProduction.getEvents().add(domainEvent);
                stepOne.setEventProduction(singleEventProduction);

                //step two flow
                CommandEvent triggerCommand = TacticdslFactory.eINSTANCE.createCommandEvent();
                //TODO triggers
                triggerCommand.setName(steps.getTriggers());
                application.getCommands().add(triggerCommand);
                CommandInvokationStep stepTwo = ContextMappingDSLFactory.eINSTANCE.createCommandInvokationStep();
                stepTwo.getEvents().add(domainEvent);
                CommandInvokation commandInvokation = ContextMappingDSLFactory.eINSTANCE.createSingleCommandInvokation();
                commandInvokation.getCommands().add(triggerCommand);
                stepTwo.setAction(commandInvokation);
                flowCML.getSteps().add(stepOne);
                flowCML.getSteps().add(stepTwo);
            }

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
