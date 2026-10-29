package com.mycompany.marsopsproject;

public class RoboticUnit extends ColonyMembers {

    private int battery;
    private String damageStatus;
    private String robotType;

    public void setBattery(int battery) {
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

    @Override
    public void checkStatus() {
        System.out.println("Robot Type: " + robotType);
        System.out.println("Battery: " + battery);
        System.out.println("Damage Status: " + damageStatus);
    }
}
