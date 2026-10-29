public class Habitat extends Facility {

    private int numberOfResidents;

    public Habitat(String facilityName, String location,int capacity, int numberOfResidents) {
        setFacilityType("Habitat");
        setFacilityName(facilityName);
        setLocation(location);
        setCapacity(capacity);
        setOperationalStatus(true);

        this.numberOfResidents = numberOfResidents;
    }

    public void setNumberOfResidents(int numberOfResidents) {
        this.numberOfResidents = numberOfResidents;
    }

    public int getNumberOfResidents() {
        return numberOfResidents;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Residents: " + numberOfResidents);
    }
}
