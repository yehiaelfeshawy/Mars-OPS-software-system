
public class MarsOpsProject {

    public static void main(String[] args) {

        Environment env = new Environment("Normal");
        MarsColony colony = new MarsColony("Mars Base Alpha", env);

        Simulation simulation = new Simulation(colony);

        Scientist scientist = new Scientist(
            "Ahmed", 101, 100, "Research", 0, "Geology"
        );

        ExplorationRover rover = new ExplorationRover(
            201, 100, "Operational", "Rover X1"
        );

        Habitat habitat = new Habitat(
            "Habitat A", "Mars Base", 20, 8
        );

        habitat.setResources(300, 100, 50, 20, 100, 10, 5);

        colony.addMember(scientist);
        colony.addMember(rover);
        colony.addFacility(habitat);

        ExplorationMission mission = new ExplorationMission(
            301, "Explore Sector A", 3, "Sector A"
        );

        mission.addAgent(scientist);
        mission.addAgent(rover);

        System.out.println("----- SUBMIT MISSION -----");

        boolean accepted = colony.submitMission(mission);

        System.out.println("Mission accepted: " + accepted);
        System.out.println("Mission state: " + mission.getState());

        System.out.println("----- FIRST TICK -----");

        simulation.advanceTime();

        System.out.println("Mission state: " + mission.getState());
        System.out.println("Mission progress: " + mission.getProgress());

        System.out.println("----- COMMUNICATION DISRUPTION -----");

        env.setCondition("Communication Disruption");

        System.out.println("Environment: " + env.getCondition());
        System.out.println("Communication working: "
                + env.checkCommunication());

        System.out.println("----- SECOND TICK -----");

        simulation.advanceTime();

        System.out.println("Mission state: " + mission.getState());
        System.out.println("Mission progress: " + mission.getProgress());
        System.out.println("Failure reason: " + mission.getFailureReason());

        System.out.println("----- FINAL STATUS -----");

        System.out.println("Scientist available: "
                + scientist.getAvailability());

        System.out.println("Rover available: "
                + rover.getAvailability());

        System.out.println("Active mission: "
                + colony.getActiveMission());
    }
}
