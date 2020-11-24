package ch.ost.rj.sa.miro2cml.data_access.model.miro.boards;

public class SharingPolicy {
    private String access;

    private String teamAccess;

    private String accountAccess;

    public String getAccess() {
        return this.access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getTeamAccess() {
        return this.teamAccess;
    }

    public void setTeamAccess(String teamAccess) {
        this.teamAccess = teamAccess;
    }

    public String getAccountAccess() {
        return this.accountAccess;
    }

    public void setAccountAccess(String accountAccess) {
        this.accountAccess = accountAccess;
    }
}