package ch.ost.rj.sa.miro2cml.data_access.model.miro.boards;

public class Data {
    private String type;

    private String viewLink;

    private String description;

    private String createdAt;

    private Team team;

    private Picture picture;

    private SharingPolicy sharingPolicy;

    private CurrentUserConnection currentUserConnection;

    private CreatedBy createdBy;

    private String modifiedAt;

    private ModifiedBy modifiedBy;

    private String name;

    private String id;

    private Owner owner;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getViewLink() {
        return this.viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Picture getPicture() {
        return this.picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public SharingPolicy getSharingPolicy() {
        return this.sharingPolicy;
    }

    public void setSharingPolicy(SharingPolicy sharingPolicy) {
        this.sharingPolicy = sharingPolicy;
    }

    public CurrentUserConnection getCurrentUserConnection() {
        return this.currentUserConnection;
    }

    public void setCurrentUserConnection(CurrentUserConnection currentUserConnection) {
        this.currentUserConnection = currentUserConnection;
    }

    public CreatedBy getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedAt() {
        return this.modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public ModifiedBy getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(ModifiedBy modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}