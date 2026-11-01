public class Scientist extends HumanPersonnel {

    private String specialization;

    public Scientist(String name, int ID, int health,String skills, int fatigue,String specialization) {

        setName(name);
        setID(ID);
        setHealth(health);
        setSkills(skills);
        setFatigue(fatigue);
        setPersonnelType("Scientist");
        setAvailability(true);

        this.specialization = specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public void checkStatus() {
        super.checkStatus();
        System.out.println("Specialization: " + specialization);
    }

    @Override
    public boolean canPerform(Mission mission) {
        if (mission == null || !getAvailability()) {
            return false;
        }
        return mission instanceof ResearchMission
                || mission instanceof ExplorationMission;
    }

    @Override
    public boolean canHandle(EmergencyType type) {
        return false;
    }
}
