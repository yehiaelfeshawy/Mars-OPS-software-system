
package com.mycompany.marsopsproject;


public class Facility {

    private String facilityType;
    private String location;
    private int capacity;
    private boolean operationalStatus;
    private String resources;
    private String equipment;

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setOperationalStatus(boolean operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    public boolean getOperationalStatus() {
        return operationalStatus;
    }

    public void setResources(String resources) {
    this.resources = resources;
    }

    public String getResources() {
    return resources;
    }

    public void setEquipment(String equipment) {
    this.equipment = equipment;
    }

    public String getEquipment() {
    return equipment;
    }

    public void displayInfo() {
        System.out.println("Facility Type: " + facilityType);
        System.out.println("Location: " + location);
        System.out.println("Capacity: " + capacity);
        System.out.println("Operational: " + operationalStatus);
    }
}