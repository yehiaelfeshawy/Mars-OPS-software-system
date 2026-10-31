
public class RepairMission extends Mission {

    private String repairDescription;

    public RepairMission(int missionID, String missionName,int duration, String repairDescription) {

        super(missionID, missionName, duration);

        this.repairDescription = repairDescription;

        setRequiredSpareParts(2);
        setRequiredBattery(5);
    }

    public void setRepairDescription(String repairDescription) {
        this.repairDescription = repairDescription;
    }

    public String getRepairDescription() {
        return repairDescription;
    }

    @Override
    public boolean isAllowedIn(Environment env) {
        return env != null;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Repair Description: " + repairDescription);
    }
}
