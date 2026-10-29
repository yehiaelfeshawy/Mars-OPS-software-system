public class Laboratory extends Facility {

    private String researchType;

    public Laboratory(String facilityName, String location, int capacity, String researchType) {
        setFacilityType("Laboratory");
        setFacilityName(facilityName);
        setLocation(location);
        setCapacity(capacity);
        setOperationalStatus(true);

        this.researchType = researchType;
    }

    public void setResearchType(String researchType) {
        this.researchType = researchType;
    }

    public String getResearchType() {
        return researchType;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Research Type: " + researchType);
    }
}
