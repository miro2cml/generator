package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import org.contextmapper.dsl.contextMappingDSL.Aggregate;
import org.contextmapper.dsl.contextMappingDSL.BoundedContextType;
import org.contextmapper.dsl.contextMappingDSL.ContextMappingDSLFactory;
import org.contextmapper.dsl.contextMappingDSL.KnowledgeLevel;
import org.contextmapper.tactic.dsl.tacticdsl.ComplexType;
import org.contextmapper.tactic.dsl.tacticdsl.DomainEvent;
import org.contextmapper.tactic.dsl.tacticdsl.DomainObjectOperation;
import org.contextmapper.tactic.dsl.tacticdsl.TacticdslFactory;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;

public class BoundedContext implements ICmlArtifact{

  private String comment, name, domainVisionStatement, implementationStrategy, aggregateName;
  private ArrayList<String> responsibilites;
  private ArrayList<String> domainEvents;
  private ArrayList<String> operations;
  private KnowledgeLevel knowledgeLevel=KnowledgeLevel.CONCRETE;
  private BoundedContext refindedboundedContext;
  private BoundedContextType boundedContextType = BoundedContextType.FEATURE;

    public BoundedContext(String comment, String name, String domainVisionStatement, String aggregateName, ArrayList<String> responsibilites, ArrayList<String> domainEvents, ArrayList<String> operations) {
        this.comment = comment;
        this.name = name;
        this.domainVisionStatement = domainVisionStatement;
        this.aggregateName = aggregateName;
        this.responsibilites = responsibilites;
        this.domainEvents = domainEvents;
        this.operations = operations;
    }

    @Override
    public EObject provideEObject() {
        org.contextmapper.dsl.contextMappingDSL.BoundedContext boundedContext = ContextMappingDSLFactory.eINSTANCE.createBoundedContext();
        createBoundedContext(boundedContext);
        addAggregate(boundedContext);
        return boundedContext;
    }

    private void addAggregate(org.contextmapper.dsl.contextMappingDSL.BoundedContext boundedContext) {
        ContextMappingDSLFactory factory =ContextMappingDSLFactory.eINSTANCE;
        Aggregate aggregate = factory.createAggregate();
        aggregate.setName(aggregateName);
        boundedContext.getAggregates().add(aggregate);
        addDomainEvents(aggregate, 0);
    }

    private void addDomainEvents(Aggregate aggregate, int counter) {
        for(String s : domainEvents){
            DomainEvent domainEvent = TacticdslFactory.eINSTANCE.createDomainEvent();
            domainEvent.setName(s);
            aggregate.getDomainObjects().add(domainEvent);
            //operations sind im ersten DomainEvent enthalten
            counter++;
            if(counter==1){
                addOperations(domainEvent);
            }
        }
    }

    private void addOperations(DomainEvent domainEvent) {
        for(String str: operations){
            DomainObjectOperation operation = TacticdslFactory.eINSTANCE.createDomainObjectOperation();
            operation.setName(str);
            ComplexType returnType = TacticdslFactory.eINSTANCE.createComplexType();
            returnType.setType("void");
            operation.setReturnType(returnType);
            domainEvent.getOperations().add(operation);
        }
        ;
    }

    private void createBoundedContext(org.contextmapper.dsl.contextMappingDSL.BoundedContext boundedContext) {
        boundedContext.setName(name);
        boundedContext.setComment(comment);
        boundedContext.setDomainVisionStatement(domainVisionStatement);
        boundedContext.setKnowledgeLevel(knowledgeLevel);
        boundedContext.setType(boundedContextType);
        SetResponsibilities(boundedContext);
    }

    private void SetResponsibilities(org.contextmapper.dsl.contextMappingDSL.BoundedContext boundedContext) {
        for (String s: responsibilites) {
            boundedContext.getResponsibilities().add(s);
        }
    }

    @Override
    public String toString() {
        return "BoundedContext{" +
                "comment='" + comment + '\'' +
                ", name='" + name + '\'' +
                ", domainVisionStatement='" + domainVisionStatement + '\'' +
                ", aggregateName='" + aggregateName + '\'' +
                ", responsibilites=" + responsibilites +
                ", domainEvents=" + domainEvents +
                ", operations=" + operations +
                '}';
    }
}
