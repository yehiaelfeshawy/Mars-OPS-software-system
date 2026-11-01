public class MedicalOfficer extends HumanPersonnel {

    private String specialization;

    public MedicalOfficer(String name, int ID, int health,String skills, int fatigue, String specialization) {

        setName(name);
        setID(ID);
        setHealth(health);
        setSkills(skills);
        setFatigue(fatigue);
        setPersonnelType("Medical Officer");
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
        return mission instanceof RescueMission;
    }

    @Override
    public boolean canHandle(EmergencyType type) {
        return type == EmergencyType.MEDICAL
                || type == EmergencyType.FIRE;
    }
}

