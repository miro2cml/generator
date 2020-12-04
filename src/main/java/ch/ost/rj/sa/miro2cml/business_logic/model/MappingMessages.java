package ch.ost.rj.sa.miro2cml.business_logic.model;

import java.util.ArrayList;
import java.util.List;

public class MappingMessages {
    private boolean mappingState = true;
    private final ArrayList<String> messages= new ArrayList<>();


    public List<String> getMessages() {
        return messages;
    }

    public void add(String message) {
        this.messages.add(message);
    }

    public boolean isMappingState() {
        return mappingState;
    }

    public void setMappingState(boolean mappingState) {
        this.mappingState = mappingState;
    }

    public void clear(){this.messages.clear();}

    @Override
    public String toString() {
        return "MappingMessages{" +
                "mappingState=" + mappingState +
                ", messages=" + messages +
                '}';
    }
}
