
public class Fire extends Emergency {
    private Laboratory laboratory;
    private double oxygenBurned;

    public Fire(Laboratory laboratory, double oxygenBurned) {
        super("FIRE");
        this.laboratory = laboratory;
        this.oxygenBurned = oxygenBurned;
    }

    @Override
    public void respond(MarsColony colony) {
        System.out.println("Fire detected in " + laboratory.getFacilityName() + ".");

        laboratory.setOperationalStatus(false);
        laboratory.setEquipment("Damaged");
        
        if (laboratory.getResources() != null) {
            double current = laboratory.getResources().getFacilityOxegen();
            laboratory.getResources().setFacilityOxegen(Math.max(0, current - oxygenBurned));
        }

        ColonyMembers human = colony.findResponder("FIRE", false);

        double[] cost = new double[7];
        cost[6] = 1; // Spare parts

        String shortage = colony.checkResources(cost);

        if (human == null) {
            unresolved("No available fire-response personnel");
            return;
        }

        if (shortage != null) {
            unresolved(shortage);
            return;
        }

        dispatch(human, laboratory.getFacilityName());
        colony.consume(cost);
        resolve();
        release(human);

        System.out.println("Fire extinguished. " + laboratory.getFacilityName() + " stays offline until repaired.");
    }
}