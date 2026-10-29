public class Facility {

    private String facilityType;
    private String location;
    private int capacity;
    private boolean operationalStatus;
    private FacilityResources resources;
    private String equipment;
    private String facilityName;
    Facility() {
        FacilityResources.facilities.add(this);
    }

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
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
    public String getFacilityName() {
        return facilityName;
    }

    public void setResources(double oxegen, double water, double food, double fuel, double battery_power,double medical_supplies,double spare_parts) {
        this.resources = new FacilityResources(this, oxegen, water, food, fuel, battery_power, medical_supplies, spare_parts);
    }

    public FacilityResources getResources() {
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