import java.util.ArrayList;

public abstract class Mission {

    private int missionID;
    private String missionName;
    private MissionState state;
    private int duration;
    private int progress;
    private String failureReason;

    private ArrayList<ColonyMembers> assignedAgents;

    private double requiredOxygen;
    private double requiredWater;
    private double requiredFood;
    private double requiredFuel;
    private double requiredBattery;
    private double requiredMedicalSupplies;
    private double requiredSpareParts;

    private Facility targetFacility;

    public Mission(int missionID, String missionName, int duration) {

        this.missionID = missionID;
        this.missionName = missionName;
        setDuration(duration);

        state = MissionState.PENDING;
        progress = 0;
        failureReason = "";

        assignedAgents = new ArrayList<>();
    }

    public void setMissionID(int missionID) {
        this.missionID = missionID;
    }

    public int getMissionID() {
        return missionID;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getMissionName() {
        return missionName;
    }

    public MissionState getState() {
        return state;
    }

    public void setDuration(int duration) {
        if (duration > 0) {
            this.duration = duration;
        } else {
            System.out.println("Invalid duration");
            this.duration = 1;
        }
    }

    public int getDuration() {
        return duration;
    }

    public int getProgress() {
        return progress;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public ArrayList<ColonyMembers> getAssignedAgents() {
        return assignedAgents;
    }

    public void addAgent(ColonyMembers agent) {
        if (agent != null && !assignedAgents.contains(agent)) {
            assignedAgents.add(agent);
        }
    }

    public void setTargetFacility(Facility targetFacility) {
        this.targetFacility = targetFacility;
    }

    public Facility getTargetFacility() {
        return targetFacility;
    }

    public void setRequiredOxygen(double requiredOxygen) {
        if (requiredOxygen >= 0 && Double.isFinite(requiredOxygen)) {
            this.requiredOxygen = requiredOxygen;
        }
    }

    public double getRequiredOxygen() {
        return requiredOxygen;
    }

    public void setRequiredWater(double requiredWater) {
        if (requiredWater >= 0 && Double.isFinite(requiredWater)) {
            this.requiredWater = requiredWater;
        }
    }

    public double getRequiredWater() {
        return requiredWater;
    }

    public void setRequiredFood(double requiredFood) {
        if (requiredFood >= 0 && Double.isFinite(requiredFood)) {
            this.requiredFood = requiredFood;
        }
    }

    public double getRequiredFood() {
        return requiredFood;
    }

    public void setRequiredFuel(double requiredFuel) {
        if (requiredFuel >= 0 && Double.isFinite(requiredFuel)) {
            this.requiredFuel = requiredFuel;
        }
    }

    public double getRequiredFuel() {
        return requiredFuel;
    }

    public void setRequiredBattery(double requiredBattery) {
        if (requiredBattery >= 0 && Double.isFinite(requiredBattery)) {
            this.requiredBattery = requiredBattery;
        }
    }

    public double getRequiredBattery() {
        return requiredBattery;
    }

    public void setRequiredMedicalSupplies(double requiredMedicalSupplies) {
        if (requiredMedicalSupplies >= 0 && Double.isFinite(requiredMedicalSupplies)) {
            this.requiredMedicalSupplies = requiredMedicalSupplies;
        }
    }

    public double getRequiredMedicalSupplies() {
        return requiredMedicalSupplies;
    }

    public void setRequiredSpareParts(double requiredSpareParts) {
        if (requiredSpareParts >= 0 && Double.isFinite(requiredSpareParts)) {
            this.requiredSpareParts = requiredSpareParts;
        }
    }

    public double getRequiredSpareParts() {
        return requiredSpareParts;
    }

    public void assign() {
        if (state == MissionState.PENDING) {
            state = MissionState.ASSIGNED;
        }
    }

    public void advance() {

        if (state == MissionState.ASSIGNED) {
            state = MissionState.IN_PROGRESS;
        }

        if (state == MissionState.IN_PROGRESS) {
            progress++;

            if (progress >= duration) {
                complete();
            }
        }
    }

    public void fail(String reason) {
        if (state != MissionState.COMPLETED &&
            state != MissionState.FAILED) {

            failureReason = reason;
            state = MissionState.FAILED;
        }
    }

    public void complete() {
        if (state == MissionState.IN_PROGRESS) {
            state = MissionState.COMPLETED;
        }
    }

    public abstract boolean isAllowedIn(Environment env);

    public void displayInfo() {
        System.out.println("Mission ID: " + missionID);
        System.out.println("Mission Name: " + missionName);
        System.out.println("State: " + state);
        System.out.println("Duration: " + duration);
        System.out.println("Progress: " + progress);
        System.out.println("Failure Reason: " + failureReason);
    }
}
