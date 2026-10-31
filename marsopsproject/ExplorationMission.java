public class ExplorationMission extends Mission {

    private String explorationRegion;

    public ExplorationMission(int missionID, String missionName,
                              int duration, String explorationRegion) {

        super(missionID, missionName, duration);
        this.explorationRegion = explorationRegion;

        setRequiredBattery(10);
    }

    public void setExplorationRegion(String explorationRegion) {
        this.explorationRegion = explorationRegion;
    }

    public String getExplorationRegion() {
        return explorationRegion;
    }

    @Override
    public boolean isAllowedIn(Environment env) {
        return env != null && env.checkSurfaceOperations()
                && env.checkCommunication();
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Exploration Region: " + explorationRegion);
    }
}
