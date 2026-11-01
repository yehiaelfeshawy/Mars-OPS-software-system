public class Simulation {

    private MarsColony colony;
    private int currentTime;

    public Simulation(MarsColony colony) {
        this.colony = colony;
        this.currentTime = 0;
    }

    public MarsColony getColony() {
        return colony;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void advanceTime(int units) {
        for (int i = 0; i < units; i++) {
            currentTime++;

            System.out.println("----- TIME: " + currentTime + " -----");

            Mission mission = colony.getActiveMission();

            if (mission != null) {

                if (mission.getState() == MissionState.ASSIGNED
                        || mission.getState() == MissionState.IN_PROGRESS) {

                    if (!mission.isAllowedIn(colony.getEnvironment())) {
                        mission.fail("Mission interrupted by environment");
                    } else {
                        mission.advance();
                    }
                }

                for (ColonyMembers agent : mission.getAssignedAgents()) {
                    if (agent instanceof RoboticUnit) {
                        RoboticUnit robot = (RoboticUnit) agent;
                        int currentBattery = robot.getBattery();
                        if (currentBattery > 0) {
                            robot.setBattery(currentBattery - 1);
                        }
                    } else if (agent instanceof HumanPersonnel) {
                        HumanPersonnel person = (HumanPersonnel) agent;
                        person.setFatigue(person.getFatigue() + 1);
                    }
                }

                if (mission.getState() == MissionState.COMPLETED
                        || mission.getState() == MissionState.FAILED) {

                    colony.releaseAgents(mission);
                }
            }
        }
    }
}
