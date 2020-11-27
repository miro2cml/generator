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
                //commandEvent.setComment(steps.getRole());
                aggregate.getDomainObjects().add(commandEvent);
                //steps one flow
                DomainEventProductionStep stepOne = ContextMappingDSLFactory.eINSTANCE.createDomainEventProductionStep();
                EitherCommandOrOperation commandOrOperation = ContextMappingDSLFactory.eINSTANCE.createEitherCommandOrOperation();
                commandOrOperation.setActor("User");
                commandOrOperation.setCommand(commandEvent);
                stepOne.setAggregate(aggregate);
                stepOne.setAction(commandOrOperation);
                SingleEventProduction singleEventProduction = ContextMappingDSLFactory.eINSTANCE.createSingleEventProduction();
                singleEventProduction.getEvents().add(domainEvent);
                stepOne.setEventProduction(singleEventProduction);

                //step two flow
                //CommandEvent triggerCommand = TacticdslFactory.eINSTANCE.createCommandEvent();
                //commandEvent.setName(StringValidator.convertForVariableName(steps.getTriggers().get(0)));
                //application.getCommands().add(commandEvent);
                CommandInvokationStep stepTwo = ContextMappingDSLFactory.eINSTANCE.createCommandInvokationStep();
                stepTwo.getEvents().add(domainEvent);
                CommandInvokation commandInvokation = ContextMappingDSLFactory.eINSTANCE.createSingleCommandInvokation();
                commandInvokation.getCommands().add(commandEvent);
                stepTwo.setAction(commandInvokation);
                flowCML.getSteps().add(stepOne);
                flowCML.getSteps().add(stepTwo);
            }

        }
    }

   /*private void addFlowElements(Aggregate aggregate, AggregatesCML thisAggregate, ArrayList<FlowStep> flow, Application application, Flow flowCML) {
        //add event
        DomainEvent domainEvent = TacticdslFactory.eINSTANCE.createDomainEvent();
        domainEvent.setName(entry.getKey());
        //add command
        CommandEvent commandEvent = TacticdslFactory.eINSTANCE.createCommandEvent();
        commandEvent.setName(entry.getKey());
        commandEvent.setComment(entry.getValue());
        aggregate.getDomainObjects().add(commandEvent);
        //step one flow

        //step two flow

    }

    private void addElementsToFlow(Application application, Flow flowCML) {
        for(FlowStep step : flow){
            //add command
            CommandEvent command = TacticdslFactory.eINSTANCE.createCommandEvent();
            command.setName(step.getCommand());
            application.getCommands().add(command);
            //add domain Event
            DomainEvent domainEvent = TacticdslFactory.eINSTANCE.createDomainEvent();
            domainEvent.setName(step.getEvent());
            //step One
            DomainEventProductionStep stepOne = ContextMappingDSLFactory.eINSTANCE.createDomainEventProductionStep();
            EitherCommandOrOperation commandOrOperation = ContextMappingDSLFactory.eINSTANCE.createEitherCommandOrOperation();
            commandOrOperation.setCommand(command);
            stepOne.setAction(commandOrOperation);
            SingleEventProduction singleEventProduction = ContextMappingDSLFactory.eINSTANCE.createSingleEventProduction();
            singleEventProduction.getEvents().add(domainEvent);
            stepOne.setEventProduction(singleEventProduction);
            flowCML.getSteps().add(stepOne);
            //Step two
            //add trigger command
            if(!step.getTriggers().isEmpty()){
                CommandEvent triggerCommand = TacticdslFactory.eINSTANCE.createCommandEvent();
                command.setName(StringValidator.convertForVariableName(step.getTriggers().get(0)));
                application.getCommands().add(command);
                CommandInvokationStep stepTwo = ContextMappingDSLFactory.eINSTANCE.createCommandInvokationStep();
                stepTwo.getEvents().add(domainEvent);
                CommandInvokation commandInvokation = ContextMappingDSLFactory.eINSTANCE.createSingleCommandInvokation();
                commandInvokation.getCommands().add(triggerCommand);
                stepTwo.setAction(commandInvokation);
                flowCML.getSteps().add(stepTwo);
            }
        }
    }

    private void addFlow() {
        Application application = ContextMappingDSLFactory.eINSTANCE.createApplication();
        Flow flow = ContextMappingDSLFactory.eINSTANCE.createFlow();
        application.getFlows().add(flow);

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
        for ( Map.Entry<String, ArrayList<String>> entry : thisAggregate.getDomainEvents().entrySet()) {
            DomainEvent domainEvent = TacticdslFactory.eINSTANCE.createDomainEvent();
            domainEvent.setName(entry.getKey());
            aggregate.getDomainObjects().add(domainEvent);
        }
    }
*/
    @Override
    public String toString() {
        return "EventStorming{" +
                "aggregates=" + aggregates +
                ", issues='" + issues + '\'' +
                '}';
    }
}
