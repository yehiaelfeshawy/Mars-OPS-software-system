
public class RoverBreakdown extends Emergency {
    private ExplorationRover rover;

    public RoverBreakdown(ExplorationRover rover) {
        super("ROVER_BREAKDOWN");
        this.rover = rover;
    }

    @Override
    public void respond(MarsColony colony) {
        System.out.println("Rover " + rover.getID() + " broke down.");

        rover.setDamageStatus("Damaged");
        rover.setLocation("Surface");

        Mission mission = colony.getActiveMission();
        if (mission != null && mission.getAssignedAgents().contains(rover)) {
            mission.fail("Rover breakdown");
            colony.releaseAgents(mission);
        }

        ColonyMembers human = colony.findResponder("ROVER_BREAKDOWN", false);
        ColonyMembers robot = colony.findResponder("ROVER_BREAKDOWN", true);

        double[] cost = new double[7];
        cost[6] = 2; // Spare parts
        cost[4] = 5; // Battery power

        if (human == null || robot == null) {
            unresolved("Required responders are unavailable");
            return;
        }

        String shortage = colony.checkResources(cost);
        if (shortage != null) {
            unresolved(shortage);
            return;
        }

        dispatch(human, "Surface");
        dispatch(robot, "Surface");
        colony.consume(cost);

        rover.setDamageStatus("Repaired");
        resolve();

        release(human);
        release(robot);

        System.out.println("Rover repaired on the surface.");
    }
}