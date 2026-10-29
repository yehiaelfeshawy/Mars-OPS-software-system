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
        if (personnelType.equals("Scientist")) {
            System.out.println("Performs scientific research missions.");
        } else if (personnelType.equals("Engineer")) {
            System.out.println("Repairs damaged equipment.");
        } else if (personnelType.equals("Medical Officer")) {
            System.out.println("Responds to medical emergencies.");
        } else {
            System.out.println("Unknown personnel type.");
        }
    }

    @Override
    public void checkStatus() {
        System.out.println("Name: " + name);
        System.out.println("Health: " + health);
        System.out.println("Fatigue: " + fatigue);
    }
}
