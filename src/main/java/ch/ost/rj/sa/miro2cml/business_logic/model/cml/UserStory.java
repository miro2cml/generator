package ch.ost.rj.sa.miro2cml.business_logic.model.cml;

import org.contextmapper.dsl.contextMappingDSL.ContextMappingDSLFactory;
import org.contextmapper.dsl.contextMappingDSL.Feature;
import org.eclipse.emf.ecore.EObject;

import java.util.Objects;

public class UserStory implements ICmlArtifact {
    private String name, actor, action, object, goal;

    public UserStory(String name, String actor, String action, String object, String goal) {
        this.name = name;
        this.actor = actor;
        this.action = action;
        this.object = object;
        this.goal = goal;
    }

    @Override
    public String toString() {
        return "UserStory " + name + "{\n" +
                "As an \"" + actor + "\"\n" +
                "I want to \"" + action +
                "\" a \"" + object + "\"\n" +
                "so that \"" + goal + "\"\n" +
                '}';
    }

    @Override
    public EObject provideEObject() {
        org.contextmapper.dsl.contextMappingDSL.UserStory story = ContextMappingDSLFactory.eINSTANCE.createUserStory();

        story.setName(name);
        story.setRole(actor);
        Feature feature = ContextMappingDSLFactory.eINSTANCE.createFeature();
        feature.setVerb(action);
        feature.setEntity(object);
        story.getFeatures().add(feature);
        story.setBenefit(goal);
        return story;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStory)) return false;
        UserStory userStory = (UserStory) o;
        return name.equals(userStory.name) &&
                actor.equals(userStory.actor) &&
                action.equals(userStory.action) &&
                object.equals(userStory.object) &&
                goal.equals(userStory.goal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, actor, action, object, goal);
    }

}
