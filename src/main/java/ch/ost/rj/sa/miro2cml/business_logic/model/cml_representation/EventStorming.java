package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import org.contextmapper.dsl.contextMappingDSL.*;
import org.contextmapper.tactic.dsl.tacticdsl.CommandEvent;
import org.contextmapper.tactic.dsl.tacticdsl.DomainEvent;
import org.contextmapper.tactic.dsl.tacticdsl.TacticdslFactory;
import org.eclipse.emf.ecore.EObject;

import java.util.HashMap;
import java.util.List;

public class EventStorming implements ICmlArtifact {
    private final List<AggregatesCML> aggregates;
    private final String issues;

    public EventStorming(List<AggregatesCML> aggregates, String issues) {
        this.aggregates = aggregates;
        this.issues = issues;
    }

    @Override
    public EObject provideEObject() {
        org.contextmapper.dsl.contextMappingDSL.BoundedContext boundedContext = ContextMappingDSLFactory.eINSTANCE.createBoundedContext();
        Application application = ContextMappingDSLFactory.eINSTANCE.createApplication();
        Flow flowCML = ContextMappingDSLFactory.eINSTANCE.createFlow();
        HashMap<String, CommandEvent> commandList = new HashMap<>();
        application.getFlows().add(flowCML);
        if (!aggregates.isEmpty()) {
            flowCML.setName(aggregates.get(0).getFlow().get(0).getCommand());
        } else {
            flowCML.setName("emptyFlow");
        }
        for (AggregatesCML thisAggregate : aggregates) {
            ContextMappingDSLFactory factory = ContextMappingDSLFactory.eINSTANCE;
            Aggregate cmlAggregate = factory.createAggregate();
            cmlAggregate.setName(thisAggregate.getName());
            boundedContext.getAggregates().add(cmlAggregate);
            addCommandsEventsFlow(cmlAggregate, thisAggregate, application, flowCML, commandList);
        }

        boundedContext.setComment(issues);
        boundedContext.setName("EventStormingBoundedContext");
        boundedContext.setApplication(application);
        return boundedContext;
    }

    private void addCommandsEventsFlow(Aggregate cmlAggregate, AggregatesCML miro2cmlAggregate, Application application, Flow flowCML, HashMap<String, CommandEvent> list) {
        for (FlowStep steps : miro2cmlAggregate.getFlow()) {
            if (!steps.getCommand().equals("") && !steps.getEvent().equals("")) {
                DomainEvent domainEvent = getDomainEvent(cmlAggregate, steps);
                CommandEvent commandEvent = getCommandEvent(application, list, steps.getCommand());
                generateStepOne(cmlAggregate, flowCML, steps, domainEvent, commandEvent);
                if (steps.getTriggers().size() == 1) {
                    generateStepTwo(application, flowCML, list, steps.getTriggers().get(0), domainEvent);
                }
                if (steps.getTriggers().size() == 2) {
                    generateStepTwoWithTwoTriggers(application, flowCML, list, steps.getTriggers(), domainEvent);
                }
            }

        }
    }

    private void generateStepTwoWithTwoTriggers(Application application, Flow flowCML, HashMap<String, CommandEvent> list, List<String> triggers, DomainEvent domainEvent) {
        CommandEvent triggerCommand = getCommandEvent(application, list, triggers.get(0));
        CommandEvent triggerCommandTwo = getCommandEvent(application, list, triggers.get(1));
        CommandInvokationStep stepTwo = ContextMappingDSLFactory.eINSTANCE.createCommandInvokationStep();
        stepTwo.getEvents().add(domainEvent);
        CommandInvokation commandInvokation = ContextMappingDSLFactory.eINSTANCE.createExclusiveAlternativeCommandInvokation();
        commandInvokation.getCommands().add(triggerCommand);
        commandInvokation.getCommands().add(triggerCommandTwo);
        stepTwo.setAction(commandInvokation);
        flowCML.getSteps().add(stepTwo);
    }

    private DomainEvent getDomainEvent(Aggregate aggregate, FlowStep steps) {
        DomainEvent domainEvent = TacticdslFactory.eINSTANCE.createDomainEvent();
        domainEvent.setName(steps.getEvent()); //TODO: inspect (seems to return wrong event)
        aggregate.getDomainObjects().add(domainEvent);
        return domainEvent;
    }

    private void generateStepOne(Aggregate aggregate, Flow flowCML, FlowStep steps, DomainEvent domainEvent, CommandEvent commandEvent) {
        DomainEventProductionStep stepOne = ContextMappingDSLFactory.eINSTANCE.createDomainEventProductionStep();
        EitherCommandOrOperation commandOrOperation = ContextMappingDSLFactory.eINSTANCE.createEitherCommandOrOperation();
        if (!steps.getRole().equals("")) {
            commandOrOperation.setActor(steps.getRole());
        }
        commandOrOperation.setCommand(commandEvent);
        stepOne.setAggregate(aggregate);
        stepOne.setAction(commandOrOperation);
        SingleEventProduction singleEventProduction = ContextMappingDSLFactory.eINSTANCE.createSingleEventProduction();
        singleEventProduction.getEvents().add(domainEvent);
        stepOne.setEventProduction(singleEventProduction);
        flowCML.getSteps().add(stepOne);
    }

    private void generateStepTwo(Application application, Flow flowCML, HashMap<String, CommandEvent> list, String command, DomainEvent domainEvent) {
        CommandEvent triggerCommand = getCommandEvent(application, list, command);
        CommandInvokationStep stepTwo = ContextMappingDSLFactory.eINSTANCE.createCommandInvokationStep();
        stepTwo.getEvents().add(domainEvent);
        CommandInvokation commandInvokation = ContextMappingDSLFactory.eINSTANCE.createSingleCommandInvokation();
        commandInvokation.getCommands().add(triggerCommand);
        stepTwo.setAction(commandInvokation);
        flowCML.getSteps().add(stepTwo);
    }

    private CommandEvent getCommandEvent(Application application, HashMap<String, CommandEvent> map, String commandIdentifier) {
        CommandEvent commandEvent;
        if (map.containsKey(commandIdentifier)) {
            commandEvent = map.get(commandIdentifier);
        } else {
            commandEvent = TacticdslFactory.eINSTANCE.createCommandEvent();
            commandEvent.setName(commandIdentifier);
            application.getCommands().add(commandEvent);
            map.put(commandIdentifier, commandEvent);
        }
        return commandEvent;
    }

    @Override
    public String toString() {
        return "EventStorming{" +
                "aggregates=" + aggregates +
                ", issues='" + issues + '\'' +
                '}';
    }
}
