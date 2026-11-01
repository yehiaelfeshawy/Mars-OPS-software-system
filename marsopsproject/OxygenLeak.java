import java.util.HashMap;
import java.util.Map;

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

        if (habitat.getResources() != null) {
            double current = habitat.getResources().getFacilityOxegen();
            habitat.getResources().setFacilityOxegen(Math.max(0, current - loss));
        }

        ColonyMembers human = colony.findResponder(EmergencyType.OXYGEN_LEAK);
        ColonyMembers robot = colony.findResponder(EmergencyType.OXYGEN_LEAK);

        if (human == null || robot == null) {
            if (habitat.getResources() != null) {
                double current = habitat.getResources().getFacilityOxegen();
                habitat.getResources().setFacilityOxegen(Math.max(0, current - loss));
            }
            unresolved("Required human and robotic responders are unavailable");
            return;
        }

        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.SPARE_PARTS, 2);
        cost.put(ResourceType.BATTERY_POWER, 5);

        if (!colony.hasResources(cost)) {
            if (habitat.getResources() != null) {
                double current = habitat.getResources().getFacilityOxegen();
                habitat.getResources().setFacilityOxegen(Math.max(0, current - loss));
            }
            unresolved("Insufficient resources");
            return;
        }

        dispatch(human, habitat.getFacilityName());
        dispatch(robot, habitat.getFacilityName());

        colony.consumeResources(cost);
        resolve();

        release(human);
        release(robot);

        System.out.println("Leak repaired in " + habitat.getFacilityName() + ".");
    }
}