package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import org.contextmapper.dsl.contextMappingDSL.Aggregate;
import org.contextmapper.dsl.contextMappingDSL.BoundedContextType;
import org.contextmapper.dsl.contextMappingDSL.ContextMappingDSLFactory;
import org.contextmapper.dsl.contextMappingDSL.KnowledgeLevel;
import org.contextmapper.tactic.dsl.tacticdsl.*;
import org.eclipse.emf.ecore.EObject;

import java.util.List;

public class BoundedContext implements ICmlArtifact {

    private final String comment;
    private final String name;
    private final String domainVisionStatement;
    private final String aggregateName;
    private final List<String> responsibilities;
    private final List<String> domainEvents;
    private final List<String> commands;
    private final List<String> queries;
    private static final KnowledgeLevel knowledgeLevel = KnowledgeLevel.CONCRETE;
    private static final BoundedContextType boundedContextType = BoundedContextType.FEATURE;
    private final MappingMessages messages;
    private final MappingLog mappingLog;

    public BoundedContext(String comment, String name, String domainVisionStatement, String aggregateName, List<String> responsibilities, List<String> domainEvents, List<String> queries, List<String> commands, MappingLog mappingLog, MappingMessages messages) {
        this.comment = comment;
        this.name = name;
        this.domainVisionStatement = domainVisionStatement;
        this.aggregateName = aggregateName;
        this.responsibilities = responsibilities;
        this.domainEvents = domainEvents;
        this.queries = queries;
        this.commands = commands;
        this.mappingLog = mappingLog;
        this.messages = messages;
    }

    @Override
    public EObject provideEObject() {
        org.contextmapper.dsl.contextMappingDSL.BoundedContext boundedContext = ContextMappingDSLFactory.eINSTANCE.createBoundedContext();
        createBoundedContext(boundedContext);
        addAggregate(boundedContext);
        return boundedContext;
    }

    private void addAggregate(org.contextmapper.dsl.contextMappingDSL.BoundedContext boundedContext) {
        ContextMappingDSLFactory factory = ContextMappingDSLFactory.eINSTANCE;
        Aggregate aggregate = factory.createAggregate();
        aggregate.setName(aggregateName);
        boundedContext.getAggregates().add(aggregate);
        mappingLog.addSuccessLogEntry("Add aggregate to Bounded Context with name: " + aggregateName);
        addDomainEvents(aggregate);
        addQueries(aggregate);
        addCommands(aggregate);
    }

    private void addCommands(Aggregate aggregate) {
        if (commands.isEmpty()) {
            mappingLog.addErrorLogEntry("No Commands were found");
            messages.add("No Commands found. Check if you use the blue Command box in the field Inbound Communication");
        } else {
            Entity entity = TacticdslFactory.eINSTANCE.createEntity();
            entity.setName(name + "_Commands");
            aggregate.getDomainObjects().add(entity);
            mappingLog.addInfoLogEntry("Automatic generated Domain Event with name: " + name + "_Commands");
            addOperations(entity, commands);
        }
    }

    private void addQueries(Aggregate aggregate) {
        if (queries.isEmpty()) {
            mappingLog.addErrorLogEntry("No Queries were found");
            messages.add("No Queries found. Check if you use the green Query box in the field Inbound Communication");
        } else {
            Entity entity = TacticdslFactory.eINSTANCE.createEntity();
            entity.setName(name + "_Queries");
            aggregate.getDomainObjects().add(entity);
            mappingLog.addInfoLogEntry("Automatic generated Domain Event with name: " + name + "_Queries");
            addOperations(entity, queries);
        }
    }

    private void addDomainEvents(Aggregate aggregate) {
        for (String s : domainEvents) {
            DomainEvent domainEvent = TacticdslFactory.eINSTANCE.createDomainEvent();
            domainEvent.setName(s);
            aggregate.getDomainObjects().add(domainEvent);
            mappingLog.addSuccessLogEntry("Add Domain Event to Aggregate with name: " + s);
        }
        if (domainEvents.isEmpty()) {
            mappingLog.addErrorLogEntry("No Domain Events were found");
            messages.add("No Domain Events found. Check if you use the yellow Domain Event box in the field Inbound Communication");
        }
    }

    private void addOperations(Entity entity, List<String> operations) {
        for (String str : operations) {
            DomainObjectOperation operation = TacticdslFactory.eINSTANCE.createDomainObjectOperation();
            operation.setName(str);
            ComplexType returnType = TacticdslFactory.eINSTANCE.createComplexType();
            returnType.setType("String");
            operation.setReturnType(returnType);
            mappingLog.addInfoLogEntry("Automatic generated return type String for " + str);
            entity.getOperations().add(operation);
            mappingLog.addSuccessLogEntry("Add Operation to Entity with name: " + str);
        }

    }

    private void createBoundedContext(org.contextmapper.dsl.contextMappingDSL.BoundedContext boundedContext) {
        boundedContext.setName(name);
        mappingLog.addSuccessLogEntry("Bounded Context created with name: " + name);
        boundedContext.setComment(comment);
        mappingLog.addSuccessLogEntry("Bounded Context comment set: " + comment);
        boundedContext.setDomainVisionStatement(domainVisionStatement);
        mappingLog.addSuccessLogEntry("Bounded Context Domain Vision Statement set: " + domainVisionStatement);
        boundedContext.setKnowledgeLevel(knowledgeLevel);
        mappingLog.addInfoLogEntry("Automatic Knowledge Level Concrete set");
        boundedContext.setType(boundedContextType);
        mappingLog.addInfoLogEntry("Automatic Context Type FEATURE set");
        setResponsibilities(boundedContext);
    }

    private void setResponsibilities(org.contextmapper.dsl.contextMappingDSL.BoundedContext boundedContext) {
        for (String s : responsibilities) {
            boundedContext.getResponsibilities().add(s);
            mappingLog.addSuccessLogEntry("Add responsibility: " + s);
        }
        if (responsibilities.isEmpty()) {
            mappingLog.addErrorLogEntry("No responsibilities detected");
            messages.add("Check whether you use the template correctly in the field Role Types");
        }
    }

    @Override
    public String toString() {
        return "BoundedContext{" +
                "comment='" + comment + '\'' +
                ", name='" + name + '\'' +
                ", domainVisionStatement='" + domainVisionStatement + '\'' +
                ", aggregateName='" + aggregateName + '\'' +
                ", responsibilities=" + responsibilities +
                ", domainEvents=" + domainEvents +
                ", commands=" + commands +
                ", queries=" + queries +
                ", knowledgeLevel=" + knowledgeLevel +
                ", boundedContextType=" + boundedContextType +
                '}';
    }
}
