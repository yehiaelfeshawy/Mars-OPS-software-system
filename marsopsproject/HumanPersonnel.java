public abstract class HumanPersonnel extends ColonyMembers {

    private String name;
    private int health;
    private String skills;
    private int fatigue;
    private String personnelType;

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

    public void setPersonnelType(String personnelType) {
        this.personnelType = personnelType;
    }

    public String getPersonnelType() {
        return personnelType;
    }

   public void capabilities() {
    if ("Scientist".equals(personnelType)) {
        System.out.println("Performs scientific research missions.");
    } else if ("Engineer".equals(personnelType)) {
        System.out.println("Repairs damaged equipment.");
    } else if ("Medical Officer".equals(personnelType)) {
        System.out.println("Responds to medical emergencies.");
    } else {
        System.out.println("Unknown personnel type.");
    }
}

    @Override
    public void checkStatus() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + getID());
        System.out.println("Location: " + getLocation());
        System.out.println("Availability: " + getAvailability());
        System.out.println("Health: " + health);
        System.out.println("Skills: " + skills);
        System.out.println("Fatigue: " + fatigue);
}
}
