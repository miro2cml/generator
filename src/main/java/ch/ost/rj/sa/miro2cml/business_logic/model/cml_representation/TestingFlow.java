package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import ch.ost.rj.sa.miro2cml.Miro2cmlApplication;
import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.business_logic.model.IOutputArtifact;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.contextmapper.dsl.cml.CMLResource;
import org.contextmapper.dsl.contextMappingDSL.*;
import org.contextmapper.dsl.standalone.ContextMapperStandaloneSetup;
import org.contextmapper.tactic.dsl.tacticdsl.CommandEvent;
import org.contextmapper.tactic.dsl.tacticdsl.DomainEvent;
import org.contextmapper.tactic.dsl.tacticdsl.TacticdslFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.SaveOptions;
import org.springframework.boot.SpringApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TestingFlow {

        public static void main(String[] args) {


            //importierte Klassen

            class CmlModel implements IOutputArtifact {
                public CMLResource resource;

                public CmlModel() {
                    File file = new File("temp.cml");
                    this.resource = ContextMapperStandaloneSetup.getStandaloneAPI().createCML(file);
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (!(o instanceof CmlModel)) return false;
                    CmlModel cmlModel = (CmlModel) o;
                    return Objects.equals(resource, cmlModel.resource);
                }

                @Override
                public int hashCode() {
                    return Objects.hash(resource);
                }

                public CMLResource getResource() {
                    return resource;
                }

                public void setResource(CMLResource resource) {
                    this.resource = resource;
                }

                @Override
                public byte[] toByteArray() {
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    try {
                        resource.save(outStream, SaveOptions.defaultOptions().toOptionsMap());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return outStream.toByteArray();
                }
            }

            class FlowStep {
                private final double position;
                private final String command;
                private final String event;
                private final String role;
                private final ArrayList<String> triggers;

                public FlowStep(double position,String command, String event, String role, ArrayList<String> triggers) {
                    this.position = position;
                    this.command = command;
                    this.event = event;
                    this.role = role;
                    this.triggers = triggers;
                }

                public String getCommand() {
                    return command;
                }

                public String getEvent() {
                    return event;
                }

                public String getRole(){ return role; }

                public String getTriggers() {
                    //TODO add logic with more than one trigger
                    if(triggers.isEmpty()){
                        return "no trigger";
                    }
                    return StringValidator.convertForVariableName(triggers.get(0));
                }

                public double getPosition() {
                    return position;
                }

            }
            class AggregatesCML{
                private String name;
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
                            ", flow=" + flow +
                            '}';
                }
            }
            class EventStorming implements ICmlArtifact {
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
                    HashMap<String, CommandEvent> commandList = new HashMap<>();
                    application.getFlows().add(flowCML);
                    for (AggregatesCML thisAggregate: aggregates) {
                        ContextMappingDSLFactory factory =ContextMappingDSLFactory.eINSTANCE;
                        Aggregate aggregate = factory.createAggregate();
                        aggregate.setName(thisAggregate.getName());
                        boundedContext.getAggregates().add(aggregate);
                        addCommandsEventsFlow(aggregate, thisAggregate, application, flowCML, commandList);
                    }
                    boundedContext.setComment(issues);
                    boundedContext.setName("EventStormingBoundedContext");
                    boundedContext.setKnowledgeLevel(KnowledgeLevel.CONCRETE);
                    boundedContext.setType(BoundedContextType.FEATURE);
                    boundedContext.setApplication(application);
                    return boundedContext;
                }

                private void addCommandsEventsFlow(Aggregate aggregate, AggregatesCML thisAggregate, Application application, Flow flowCML, HashMap<String, CommandEvent> list) {
                    for(FlowStep steps: thisAggregate.getFlow()){
                        //add event
                        if(!steps.getCommand().equals("") && !steps.getEvent().equals("") && !steps.getTriggers().isEmpty()){
                            DomainEvent domainEvent = TacticdslFactory.eINSTANCE.createDomainEvent();
                            domainEvent.setName(steps.getEvent());
                            aggregate.getDomainObjects().add(domainEvent);
                            //add command (and check if already exists)
                            CommandEvent commandEvent;
                            if(list.containsKey(steps.getCommand())){
                                commandEvent = list.get(steps.getCommand());
                            }else{
                                commandEvent = TacticdslFactory.eINSTANCE.createCommandEvent();
                                commandEvent.setName(steps.getCommand());
                                application.getCommands().add(commandEvent);
                                list.put(steps.getCommand(), commandEvent);
                            }
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
                            CommandEvent triggerCommand;
                            //check if already exists
                            if(list.containsKey(steps.getTriggers())){
                                triggerCommand = list.get(steps.getTriggers());
                            }else{
                                triggerCommand = TacticdslFactory.eINSTANCE.createCommandEvent();
                                triggerCommand.setName(steps.getTriggers());
                                application.getCommands().add(triggerCommand);
                                list.put(steps.getEvent(), triggerCommand);
                            }
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

            CmlModel cmlModel = new CmlModel();
            // Test setup
            //Claim Submission Self_Service
            ArrayList<String> submitClaimTriggers= new ArrayList<>(); submitClaimTriggers.add("Check_documentation");
            FlowStep submitClaim = new FlowStep(0, "Submit_claim", "Claim_submitted", "Customer", submitClaimTriggers);
            List<FlowStep> claimSubmissionFlow = new ArrayList<>(); claimSubmissionFlow.add(submitClaim);
            AggregatesCML claimSubmission = new AggregatesCML("Claim_Submission_Self_Service", claimSubmissionFlow);
            //Claim
            ArrayList<String> checkDocuTriggers= new ArrayList<>(); submitClaimTriggers.add("Check_insurance");
            FlowStep checkDocu = new FlowStep(0, "Check_documentation", "Claim_submitted", "Customer", checkDocuTriggers);

            ArrayList<String> checkInsuranceTriggers = new ArrayList<>(); checkInsuranceTriggers.add("Reject_claim"); checkInsuranceTriggers.add("Accept_claim");
            FlowStep checkInsurance = new FlowStep(0, "Check_insurance", "Assessment_performed", "Customer", checkInsuranceTriggers);

            ArrayList<String> rejectClaimTrigger = new ArrayList<>(); rejectClaimTrigger.add("Notify_customer");
            FlowStep rejectClaim = new FlowStep(0, "Reject_claim", "Claim_rejected", "User", rejectClaimTrigger);

            ArrayList<String> acceptclaimTrigger = new ArrayList<>(); acceptclaimTrigger.add("Perform_payment");
            FlowStep acceptClaim = new FlowStep(0, "Accept_claim", "Payment_scheduled", "User", acceptclaimTrigger);

            List<FlowStep> claimFlow = new ArrayList<>(); claimFlow.add(checkDocu); claimFlow.add(checkInsurance); claimFlow.add(rejectClaim); claimFlow.add(acceptClaim);
            AggregatesCML claim = new AggregatesCML("Claim", claimFlow);

            //Payment
            ArrayList<String> performPaymentTrigger = new ArrayList<>(); performPaymentTrigger.add("Notify_customer");
            FlowStep performPayment = new FlowStep(0, "Perform_payment", "Payment_performed","", performPaymentTrigger );
            List<FlowStep> paymentFlow = new ArrayList<>(); paymentFlow.add(performPayment);
            AggregatesCML payment = new AggregatesCML("Payment", paymentFlow);
            //Notification
            ArrayList<String> notificationtrigger = new ArrayList<>();
            FlowStep notifyCustomer = new FlowStep(0, "Notify_customer", "Customer_notified", "", notificationtrigger);
            List<FlowStep> notifyFlow = new ArrayList<>(); paymentFlow.add(notifyCustomer);
            AggregatesCML notification = new AggregatesCML("Payment", notifyFlow);

            ArrayList<AggregatesCML> input = new ArrayList<>();
            input.add(claimSubmission); input.add(claim); input.add(payment); input.add(notification);
            EventStorming eventStormingModel = new EventStorming(input, "*/ Issues */");
            cmlModel.getResource().getContextMappingModel().getBoundedContexts().add((org.contextmapper.dsl.contextMappingDSL.BoundedContext) eventStormingModel.provideEObject());

            //serialisierung
            try{
                cmlModel.toByteArray();
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }

        }

}
