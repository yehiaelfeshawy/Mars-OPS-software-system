
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

    public void advanceTime() {

        currentTime++;

        System.out.println("----- TIME: " + currentTime + " -----");

        Mission mission = colony.getActiveMission();

        if (mission != null) {

            if (mission.getState() == MissionState.ASSIGNED || mission.getState() == MissionState.IN_PROGRESS) {

                if (!mission.isAllowedIn(colony.getEnvironment())) {
                    mission.fail("Mission interrupted by environment");
                } else {
                    mission.advance();
                }
            }

            if (mission.getState() == MissionState.COMPLETED || mission.getState() == MissionState.FAILED) {

                colony.releaseAgents(mission);
            }
        }
    }
}

