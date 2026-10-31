public class RoboticUnit extends ColonyMembers {

    private int battery;
    private String damageStatus;
    private String robotType;

    public void setBattery(int battery) {
        if(battery < 0) {
            System.out.println("Battery level cannot be negative.");
            return;
        }else if(battery > 100) {
            System.out.println("Battery level cannot exceed 100.");
            return;
        }
        this.battery = battery;
    }

    public int getBattery() {
        return battery;
    }

    public void setDamageStatus(String damageStatus) {
        this.damageStatus = damageStatus;
    }

    public String getDamageStatus() {
        return damageStatus;
    }

    public void setRobotType(String robotType) {
        this.robotType = robotType;
    }

    public String getRobotType() {
        return robotType;
    }

    public void capabilities() {
        if (robotType.equals("Exploration Rover")) {
            System.out.println("Explores the Martian surface.");
            System.out.println("Carries equipment.");
            System.out.println("Performs exploration.");
        } else if (robotType.equals("Repair Robot")) {
            System.out.println("Repairs damaged equipment.");
            System.out.println("Operates inside facilities.");
        } else if (robotType.equals("Drone")) {
            System.out.println("Performs aerial reconnaissance.");
            System.out.println("Reaches areas inaccessible to ground vehicles.");
        } else {
            System.out.println("Unknown robot type.");
        }
    }
    
public boolean canPerform(Mission mission) {

    if (mission == null || !getAvailability()
            || getBattery() <= 0) {
        return false;
    }

    if (this instanceof ExplorationRover) {
        return mission instanceof ExplorationMission
                || mission instanceof RescueMission;
    }

    if (this instanceof RepairRobot) {
        return mission instanceof RepairMission;
    }

    if (this instanceof Drone) {
        return mission instanceof ExplorationMission
                || mission instanceof RescueMission;
    }

    return false;
}


    @Override
    public void checkStatus() {
        System.out.println("Robot Type: " + robotType);
        System.out.println("Battery: " + battery);
        System.out.println("Damage Status: " + damageStatus);
    }
}
