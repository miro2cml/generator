package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import org.contextmapper.dsl.contextMappingDSL.ContextMappingDSLFactory;
import org.contextmapper.dsl.contextMappingDSL.Feature;
import org.eclipse.emf.ecore.EObject;

import java.util.Objects;
import static org.springframework.util.StringUtils.capitalize;


public class UserStory implements ICmlArtifact {
    private String name;
    private String role;
    private String verb;
    private String entity;
    private String benefit;
    private String article;

    public UserStory(String role, String verb, String entity, String benefit, String article) {
        this.role = role;
        this.verb = verb;
        this.entity = entity;
        this.benefit = benefit;
        this.name = verb + entity;
        this.article = article;
        this.name = verb+capitalize(entity);
    }

    @Override
    public EObject provideEObject() {
        org.contextmapper.dsl.contextMappingDSL.UserStory story = ContextMappingDSLFactory.eINSTANCE.createUserStory();

        story.setName(name);
        story.setRole(role);
        Feature feature = ContextMappingDSLFactory.eINSTANCE.createStoryFeature();
        if(isCRUDVerb()){
            feature.setVerb(verb);
        }else {
            feature.setVerb("\""+verb+"\"");
        }
        feature.setEntityArticle(article);
        feature.setEntity(entity);
        story.getFeatures().add(feature);
        story.setBenefit(benefit);
        return story;
    }

    private boolean isCRUDVerb() {
        return verb.equals("create")|| verb.equals("update")|| verb.equals("delete")|| verb.equals("read");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getArticle() { return article; }

    public void setArticle(String article) { this.article = article; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStory)) return false;
        UserStory userStory = (UserStory) o;
        return name.equals(userStory.name) &&
                role.equals(userStory.role) &&
                verb.equals(userStory.verb) &&
                entity.equals(userStory.entity) &&
                benefit.equals(userStory.benefit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, role, verb, entity, benefit);
    }

}
