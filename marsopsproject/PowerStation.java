public class PowerStation extends Facility {

    private double powerOutput;

    public PowerStation(String facilityName, String location,int capacity, double powerOutput) {
        setFacilityType("Power Station");
        setFacilityName(facilityName);
        setLocation(location);
        setCapacity(capacity);
        setOperationalStatus(true);

        this.powerOutput = powerOutput;
    }

    public void setPowerOutput(double powerOutput) {
        this.powerOutput = powerOutput;
    }

    public double getPowerOutput() {
        return powerOutput;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Power Output: " + powerOutput);
    }
}
