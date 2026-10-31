
public class RescueMission extends Mission {

    private String rescueLocation;

    public RescueMission(int missionID, String missionName,int duration, String rescueLocation) {

        super(missionID, missionName, duration);

        this.rescueLocation = rescueLocation;

        setRequiredBattery(10);
        setRequiredOxygen(20);
    }

    public void setRescueLocation(String rescueLocation) {
        this.rescueLocation = rescueLocation;
    }

    public String getRescueLocation() {
        return rescueLocation;
    }

    @Override
    public boolean isAllowedIn(Environment env) {
        return env != null && env.checkSurfaceOperations()
                && env.checkCommunication();
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Rescue Location: " + rescueLocation);
    }
}
