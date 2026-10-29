public class CommunicationStation extends Facility {

    private double signalRange;

    public CommunicationStation(String facilityName, String location,int capacity, double signalRange) {
        setFacilityType("Communication Station");
        setFacilityName(facilityName);
        setLocation(location);
        setCapacity(capacity);
        setOperationalStatus(true);

        this.signalRange = signalRange;
    }

    public void setSignalRange(double signalRange) {
        this.signalRange = signalRange;
    }

    public double getSignalRange() {
        return signalRange;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Signal Range: " + signalRange);
    }
}
