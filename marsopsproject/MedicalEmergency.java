
public class MedicalEmergency extends Emergency {
    private HumanPersonnel patient;
    private Facility facility;

    public MedicalEmergency(HumanPersonnel patient, Facility facility) {
        super("MEDICAL");
        this.patient = patient;
        this.facility = facility;
    }

    @Override
    public void respond(MarsColony colony) {
        System.out.println("Medical emergency: " + patient.getName() + ".");

        if (!facility.getOperationalStatus()) {
            unresolved("Facility is not operational");
            return;
        }

        ColonyMembers officer = colony.findResponder("MEDICAL", false);
        if (officer == null) {
            unresolved("No available medical officer");
            return;
        }

        double[] cost = new double[7];
        cost[5] = 2; // Medical supplies index
        cost[0] = 5; // Oxygen index

        String shortage = colony.checkResources(cost);
        if (shortage != null) {
            unresolved(shortage);
            return;
        }

        dispatch(officer, facility.getFacilityName());
        colony.consume(cost);

        int health = patient.getHealth() + 20;
        if (health > 100) {
            health = 100;
        }

        patient.setHealth(health);
        resolve();
        release(officer);

        System.out.println(patient.getName() + " treated. Health: " + patient.getHealth());
    }
}