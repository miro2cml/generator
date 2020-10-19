package ch.ost.rj.sa.miro2cml.business_logic.cml_model;

import java.util.ArrayList;

public class CMLModel {
    public CMLModel(ArrayList<Object> allObjects) {
        this.allObjects = allObjects;
    }

    public ArrayList<Object> getAllObjects() {
        return allObjects;
    }

    public void setAllObjects(ArrayList<Object> allObjects) {
        this.allObjects = allObjects;
    }

    private ArrayList<Object> allObjects;

    public void add(Object object){
        this.allObjects.add(object);
    }

}
