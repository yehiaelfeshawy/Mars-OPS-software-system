public class Drone extends RoboticUnit {

    private String model;

    public Drone(int ID, int battery,String damageStatus, String model) {
        setID(ID);
        setBattery(battery);
        setDamageStatus(damageStatus);
        setRobotType("Drone");
        setAvailability(true);

        this.model = model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    @Override
    public void checkStatus() {
        super.checkStatus();
        System.out.println("Model: " + model);
    }

    @Override
    public boolean canPerform(Mission mission) {
        if (mission == null || !getAvailability()
                || getBattery() <= 0) {
            return false;
        }
        return mission instanceof ExplorationMission
                || mission instanceof RescueMission;
    }

    @Override
    public boolean canHandle(EmergencyType type) {
        return false;
    }
}
