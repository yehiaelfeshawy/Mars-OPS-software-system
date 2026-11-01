import java.util.ArrayList;
import java.util.Map;

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

        if (!agent.canPerform(mission)) {
            mission.fail("Agent cannot perform mission");
            addMission(mission);
            return false;
        }
    }

    if (!mission.isAllowedIn(environment)) {
        mission.fail("Environment does not allow mission");
        addMission(mission);
        return false;
    }

    Facility target = mission.getTargetFacility();

    if (target != null &&
            (!facilities.contains(target)
            || !target.getOperationalStatus())) {

        mission.fail("Target facility unavailable");
        addMission(mission);
        return false;
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
}public String checkResources(double[] needed) {
        String[] names = {"Oxygen", "Water", "Food", "Fuel", "Battery Power", "Medical Supplies", "Spare Parts"};
        for (int i = 0; i < needed.length; i++) {
            if (needed[i] <= 0) continue;
            double total = 0;
            for (Facility facility : facilities) {
                if (facility.getOperationalStatus() && facility.getResources() != null) {
                    total += getResourceAmount(facility.getResources(), i);
                }
            }
            if (total < needed[i]) {
                return "Insufficient " + names[i];
            }
        }
        return null;
    }

    public void consume(double[] needed) {
        for (int i = 0; i < needed.length; i++) {
            double remaining = needed[i];
            if (remaining <= 0) continue;
            for (Facility facility : facilities) {
                if (!facility.getOperationalStatus() || facility.getResources() == null) continue;
                FacilityResources store = facility.getResources();
                double available = getResourceAmount(store, i);
                double take = Math.min(available, remaining);
                if (take > 0) {
                    setResourceAmount(store, i, available - take);
                    remaining -= take;
                }
                if (remaining <= 0) break;
            }
        }
    }

    public ColonyMembers findResponder(EmergencyType type) {
        for (ColonyMembers member : members) {
            if (!member.getAvailability()) continue;
            if (member.canHandle(type)) {
                return member;
            }
        }
        return null;
    }

    public double getTotalResource(ResourceType type) {
        double total = 0.0;
        for (Facility facility : facilities) {
            if (facility.getOperationalStatus() && facility.getResources() != null) {
                total += getResourceAmount(facility.getResources(), typeToIndex(type));
            }
        }
        return total;
    }

    public boolean hasResources(Map<ResourceType, Integer> required) {
        for (Map.Entry<ResourceType, Integer> entry : required.entrySet()) {
            if (getTotalResource(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean consumeResources(Map<ResourceType, Integer> required) {
        if (!hasResources(required)) {
            return false;
        }
        for (Map.Entry<ResourceType, Integer> entry : required.entrySet()) {
            ResourceType type = entry.getKey();
            int remaining = entry.getValue();
            for (Facility facility : facilities) {
                if (!facility.getOperationalStatus() || facility.getResources() == null) {
                    continue;
                }
                FacilityResources store = facility.getResources();
                int index = typeToIndex(type);
                double available = getResourceAmount(store, index);
                double take = Math.min(available, remaining);
                if (take > 0) {
                    setResourceAmount(store, index, available - take);
                    remaining -= (int) take;
                }
                if (remaining <= 0) {
                    break;
                }
            }
        }
        return true;
    }

    public void displayStatus() {
        System.out.println("=== Colony Status Report: " + colonyName + " ===");
        System.out.println("Total Available Resources:");
        for (ResourceType type : ResourceType.values()) {
            System.out.println("  " + type + ": " + getTotalResource(type));
        }
        System.out.println("Facility Operational Status:");
        for (Facility facility : facilities) {
            System.out.println("  " + facility.getFacilityName() + ": "
                    + (facility.getOperationalStatus() ? "Operational" : "Offline"));
        }
    }

    private int typeToIndex(ResourceType type) {
        switch (type) {
            case OXYGEN: return 0;
            case WATER: return 1;
            case FOOD: return 2;
            case FUEL: return 3;
            case BATTERY_POWER: return 4;
            case MEDICAL_SUPPLIES: return 5;
            case SPARE_PARTS: return 6;
        }
        return -1;
    }

    public void displayInfo() {
        System.out.println("Colony Name: " + colonyName);
        System.out.println("Members: " + members.size());
        System.out.println("Facilities: " + facilities.size());
        System.out.println("Missions: " + missions.size());
    }
}

