package ch.ost.rj.sa.miro2cml.business_logic.model;

import org.springframework.core.io.ByteArrayResource;

public class MappingResult {
    private boolean success;

    private boolean perfectSuccess;
    private MappingMessages mappingMessages;
    private MappingLog mappingLog;

    private ByteArrayResource cmlResource;
    private String cmlPreview;

    public MappingMessages getMappingMessages() {
        return mappingMessages;
    }

    public void setMappingMessages(MappingMessages mappingMessages) {
        this.mappingMessages = mappingMessages;
    }

    public ByteArrayResource getServableMappingLog() {
        return new ByteArrayResource(mappingLog.toByteArray());
    }

    public String getLogPreview() {
        return mappingLog.toString();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isPerfectSuccess() {
        return perfectSuccess;
    }

    public void setPerfectSuccess(boolean perfectSuccess) {
        this.perfectSuccess = perfectSuccess;
    }

    public MappingLog getMappingLog() {
        return mappingLog;
    }

    public void setMappingLog(MappingLog mappingLog) {
        this.mappingLog = mappingLog;
    }

    public ByteArrayResource getCMLResource() {
        return cmlResource;
    }

    public void setCmlResource(ByteArrayResource cmlResource) {
        this.cmlResource = cmlResource;
    }

    public String getCmlPreview() {
        return cmlPreview;
    }

    public void setCmlPreview(String cmlPreview) {
        this.cmlPreview = cmlPreview;
    }
}
