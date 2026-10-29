
package com.mycompany.marsopsproject;

public class RoboticUnit extends ColonyMembers {

    private int battery;
    private String damageStatus;

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

    @Override
    public void checkStatus() {
        System.out.println("Battery: " + battery);
        System.out.println("Damage Status: " + damageStatus);
    }
}