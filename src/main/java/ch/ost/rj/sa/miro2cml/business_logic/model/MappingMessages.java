package ch.ost.rj.sa.miro2cml.business_logic.model;

import java.util.ArrayList;
import java.util.List;

public class MappingMessages {
    private boolean perfectMapping = true;
    private final ArrayList<String> messages= new ArrayList<>();


    public List<String> getMessages() {
        return messages;
    }

    public void add(String message) {
        this.messages.add(message);
    }

    public boolean isPerfectMapping() {
        return perfectMapping;
    }

    public void setPerfectMapping(boolean perfectMapping) {
        this.perfectMapping = perfectMapping;
    }

    public void clear(){this.messages.clear();}

    @Override
    public String toString() {
        return "MappingMessages{" +
                "mappingState=" + perfectMapping +
                ", messages=" + messages +
                '}';
    }
}
