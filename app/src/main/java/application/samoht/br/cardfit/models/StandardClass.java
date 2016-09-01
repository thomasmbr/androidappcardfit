package application.samoht.br.cardfit.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas on 7/6/16.
 */
public class StandardClass {

    private String name;
    private Map<String,StandardActivity> activities;

    public StandardClass(){}

    public StandardClass(String name){
        this.name = name;
        this.activities = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String,StandardActivity> getActivities() {
        return activities;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActivities(Map<String,StandardActivity> activities) {
        this.activities = activities;
    }

    public void addActivity(String key, StandardActivity standardActivity){
        this.activities.put(key,standardActivity);
    }


}
