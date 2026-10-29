
public class ExplorationRover extends RoboticUnit {

    private String model;

    public ExplorationRover(int ID, int battery,String damageStatus, String model) {
        setID(ID);
        setBattery(battery);
        setDamageStatus(damageStatus);
        setRobotType("Exploration Rover");
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
}

