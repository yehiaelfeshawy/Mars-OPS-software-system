
import java.util.ArrayList;

public class MarsColony {

    private String colonyName;
    private ArrayList<ColonyMembers> members;
    private ArrayList<Facility> facilities;
    private ArrayList<Mission> missions;
    private Mission activeMission;
    private Environment environment;

    public MarsColony(String colonyName, Environment environment) {
        this.colonyName = colonyName;
        this.environment = environment;

        members = new ArrayList<>();
        facilities = new ArrayList<>();
        missions = new ArrayList<>();

        activeMission = null;
    }

    public void setColonyName(String colonyName) {
        this.colonyName = colonyName;
    }

    public String getColonyName() {
        return colonyName;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Mission getActiveMission() {
        return activeMission;
    }

    public ArrayList<ColonyMembers> getMembers() {
        return members;
    }

    public ArrayList<Facility> getFacilities() {
        return facilities;
    }

    public ArrayList<Mission> getMissions() {
        return missions;
    }

    public void addMember(ColonyMembers member) {
        if (member != null && !members.contains(member)) {
            members.add(member);
        }
    }

    public void addFacility(Facility facility) {
        if (facility != null && !facilities.contains(facility)) {
            facilities.add(facility);
        }
    }

    public void addMission(Mission mission) {
        if (mission != null && !missions.contains(mission)) {
            missions.add(mission);
        }
    }


public boolean submitMission(Mission mission) {

    if (mission == null) {
        return false;
    }

    if (mission.getState() != MissionState.PENDING) {
        return false;
    }

    if (activeMission != null) {
        mission.fail("Another mission is already active");
        addMission(mission);
        return false;
    }

    if (mission.getAssignedAgents().isEmpty()) {
        mission.fail("No agents assigned");
        addMission(mission);
        return false;
    }

    boolean hasScientist = false;
    boolean hasEngineer = false;
    boolean hasHuman = false;
    boolean hasRover = false;
    boolean hasRepairRobot = false;

    for (ColonyMembers agent : mission.getAssignedAgents()) {

        if (agent == null || !members.contains(agent)) {
            mission.fail("Agent does not belong to colony");
            addMission(mission);
            return false;
        }

        if (!agent.getAvailability()) {
            mission.fail("Agent is unavailable");
            addMission(mission);
            return false;
        }

        if (agent instanceof HumanPersonnel) {

            HumanPersonnel human = (HumanPersonnel) agent;

            if (!human.canPerform(mission)) {
                mission.fail("Human cannot perform mission");
                addMission(mission);
                return false;
            }

            hasHuman = true;

            if (human instanceof Scientist) {
                hasScientist = true;
            }

            if (human instanceof Engineer) {
                hasEngineer = true;
            }

        } else if (agent instanceof RoboticUnit) {

            RoboticUnit robot = (RoboticUnit) agent;

            if (!robot.canPerform(mission)) {
                mission.fail("Robot cannot perform mission");
                addMission(mission);
                return false;
            }

            if (robot instanceof ExplorationRover
                    || robot instanceof Drone) {
                hasRover = true;
            }

            if (robot instanceof RepairRobot) {
                hasRepairRobot = true;
            }

        } else {
            mission.fail("Invalid agent");
            addMission(mission);
            return false;
        }
    }

    if (mission instanceof ExplorationMission) {
        if (!hasScientist || !hasRover) {
            mission.fail("Exploration needs scientist and rover/drone");
            addMission(mission);
            return false;
        }
    }

    if (mission instanceof RepairMission) {
        if (!hasEngineer || !hasRepairRobot) {
            mission.fail("Repair needs engineer and repair robot");
            addMission(mission);
            return false;
        }
    }

    if (mission instanceof ResearchMission) {
        if (!hasScientist) {
            mission.fail("Research needs a scientist");
            addMission(mission);
            return false;
        }
    }

    if (mission instanceof RescueMission) {
        if (!hasHuman || !hasRover) {
            mission.fail("Rescue needs personnel and vehicle");
            addMission(mission);
            return false;
        }
    }

    if (!mission.isAllowedIn(environment)) {
        mission.fail("Environment does not allow mission");
        addMission(mission);
        return false;
    }

    if (mission instanceof ResearchMission || mission instanceof RepairMission) {

        Facility target = mission.getTargetFacility();

        if (mission instanceof ResearchMission) {
            if (!(target instanceof Laboratory)|| !facilities.contains(target)|| !target.getOperationalStatus()) {

                mission.fail("Operational laboratory required");
                addMission(mission);
                return false;
            }

            if (target.getEquipment() == null
                    || target.getEquipment().isEmpty()) {

                mission.fail("Scientific equipment required");
                addMission(mission);
                return false;
            }
        }

        if (target != null &&
                (!facilities.contains(target)
                || !target.getOperationalStatus())) {

            mission.fail("Target facility unavailable");
            addMission(mission);
            return false;
        }
    }

    double[] needed = {
        mission.getRequiredOxygen(),
        mission.getRequiredWater(),
        mission.getRequiredFood(),
        mission.getRequiredFuel(),
        mission.getRequiredBattery(),
        mission.getRequiredMedicalSupplies(),
        mission.getRequiredSpareParts()
    };

    for (int i = 0; i < needed.length; i++) {

        if (!Double.isFinite(needed[i]) || needed[i] < 0) {
            mission.fail("Invalid resource requirement");
            addMission(mission);
            return false;
        }

        double total = 0;

        for (Facility facility : facilities) {
            if (facility.getOperationalStatus()
                    && facility.getResources() != null) {

                total += getResourceAmount(
                    facility.getResources(), i
                );
            }
        }

        if (total < needed[i]) {
            mission.fail("Insufficient resources");
            addMission(mission);
            return false;
        }
    }

    for (int i = 0; i < needed.length; i++) {

        double remaining = needed[i];

        for (Facility facility : facilities) {

            if (!facility.getOperationalStatus()
                    || facility.getResources() == null) {
                continue;
            }

            FacilityResources store = facility.getResources();

            double available = getResourceAmount(store, i);
            double take = Math.min(available, remaining);

            if (take > 0) {
                setResourceAmount(store, i, available - take);
                remaining -= take;
            }

            if (remaining <= 0) {
                break;
            }
        }
    }

    mission.assign();

    for (ColonyMembers agent : mission.getAssignedAgents()) {
        agent.setAvailability(false);
    }

    activeMission = mission;
    addMission(mission);

    return true;
}

private double getResourceAmount(FacilityResources store, int type) {

    switch (type) {
        case 0: return store.getFacilityOxegen();
        case 1: return store.getFacilityWater();
        case 2: return store.getFacilityFood();
        case 3: return store.getFacilityFuel();
        case 4: return store.getFacilityBattery_power();
        case 5: return store.getFacilityMedical_supplies();
        case 6: return store.getFacilitySpare_parts();
    }

    return 0;
}

private void setResourceAmount(FacilityResources store,int type, double amount) {

    switch (type) {
        case 0:
            store.setFacilityOxegen(amount);
            break;
        case 1:
            store.setFacilityWater(amount);
            break;
        case 2:
            store.setFacilityFood(amount);
            break;
        case 3:
            store.setFacilityFuel(amount);
            break;
        case 4:
            store.setFacilityBattery_power(amount);
            break;
        case 5:
            store.setFacilityMedical_supplies(amount);
            break;
        case 6:
            store.setFacilitySpare_parts(amount);
            break;
    }
}

public void releaseAgents(Mission mission) {

    if (mission == null) {
        return;
    }

    if (mission.getState() == MissionState.COMPLETED
            || mission.getState() == MissionState.FAILED) {

        for (ColonyMembers agent : mission.getAssignedAgents()) {
            agent.setAvailability(true);
        }

        if (activeMission == mission) {
            activeMission = null;
        }
    }
}


    public void displayInfo() {
        System.out.println("Colony Name: " + colonyName);
        System.out.println("Members: " + members.size());
        System.out.println("Facilities: " + facilities.size());
        System.out.println("Missions: " + missions.size());
    }
}

