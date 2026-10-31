

public class OxygenLeak extends Emergency {
    private Habitat habitat;
    private double loss;

    public OxygenLeak(Habitat habitat, double loss) {
        super("OXYGEN_LEAK");
        this.habitat = habitat;
        this.loss = loss;
    }

    @Override
    public void respond(MarsColony colony) {
        System.out.println("Oxygen leak detected in " + habitat.getFacilityName() + ".");

        // Deduct initial oxygen on detection
        if (habitat.getResources() != null) {
            double current = habitat.getResources().getFacilityOxegen();
            habitat.getResources().setFacilityOxegen(Math.max(0, current - loss));
        }

        ColonyMembers human = colony.findResponder("OXYGEN_LEAK", false);
        ColonyMembers robot = colony.findResponder("OXYGEN_LEAK", true);

        if (human == null || robot == null) {
            // Unresolved leak costs twice as much oxygen (spec rule)
            if (habitat.getResources() != null) {
                double current = habitat.getResources().getFacilityOxegen();
                habitat.getResources().setFacilityOxegen(Math.max(0, current - loss));
            }
            unresolved("Required human and robotic responders are unavailable");
            return;
        }

        double[] cost = new double[7];
        cost[6] = 2; // Spare parts index
        cost[4] = 5; // Battery power index

        String shortage = colony.checkResources(cost);

        if (shortage != null) {
            if (habitat.getResources() != null) {
                double current = habitat.getResources().getFacilityOxegen();
                habitat.getResources().setFacilityOxegen(Math.max(0, current - loss));
            }
            unresolved(shortage);
            return;
        }

        dispatch(human, habitat.getFacilityName());
        dispatch(robot, habitat.getFacilityName());

        colony.consume(cost);
        resolve();

        release(human);
        release(robot);

        System.out.println("Leak repaired in " + habitat.getFacilityName() + ".");
    }
}