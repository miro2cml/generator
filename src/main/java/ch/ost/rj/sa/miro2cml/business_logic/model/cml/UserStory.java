package ch.ost.rj.sa.miro2cml.business_logic.model.cml;

public class UserStory extends CmlArtifact {
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
}
