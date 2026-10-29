
package com.mycompany.marsopsproject;


public class HumanPersonnel extends ColonyMembers {
    
    private String name;
    private int health;
    private String skills;
    private int fatigue;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSkills() {
        return skills;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public int getFatigue() {
        return fatigue;
    }

    @Override
    public void checkStatus() {
        System.out.println("Human Name: " + name);
        System.out.println("Health: " + health);
        System.out.println("Fatigue: " + fatigue);
    }
}