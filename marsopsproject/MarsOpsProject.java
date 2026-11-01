public class MarsOpsProject {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("SCENARIO 1: Successful Exploration");
        System.out.println("========================================");

        FacilityResources.facilities.clear();

        Environment env1 = new Environment("Normal");
        MarsColony colony1 = new MarsColony("Mars Base Alpha", env1);
        Simulation simulation1 = new Simulation(colony1);

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

        colony1.addMember(scientist);
        colony1.addMember(rover);
        colony1.addFacility(habitat);

        System.out.println("--- Colony State Before Mission ---");
        colony1.displayStatus();

        ExplorationMission mission1 = new ExplorationMission(
            301, "Explore Sector A", 3, "Sector A"
        );
        mission1.addAgent(scientist);
        mission1.addAgent(rover);

        boolean accepted1 = colony1.submitMission(mission1);
        System.out.println("Mission accepted: " + accepted1);
        System.out.println("Mission state: " + mission1.getState());

        simulation1.advanceTime(3);

        System.out.println("--- Colony State After Mission ---");
        colony1.displayStatus();
        System.out.println("Mission state: " + mission1.getState());
        System.out.println("Mission progress: " + mission1.getProgress());
        System.out.println("Scientist available: " + scientist.getAvailability());
        System.out.println("Rover available: " + rover.getAvailability());
        System.out.println("Active mission: " + colony1.getActiveMission());

        System.out.println();
        System.out.println("========================================");
        System.out.println("SCENARIO 2: Mission Rejection");
        System.out.println("========================================");

        FacilityResources.facilities.clear();

        Environment env2 = new Environment("Normal");
        MarsColony colony2 = new MarsColony("Mars Base Alpha", env2);

        Scientist unsuitable = new Scientist(
            "Unsuitable", 102, 100, "Research", 0, "Biology"
        );
        Habitat habitat2 = new Habitat(
            "Habitat B", "Mars Base", 20, 8
        );
        habitat2.setResources(300, 100, 50, 20, 100, 10, 5);

        colony2.addMember(unsuitable);
        colony2.addFacility(habitat2);

        RepairMission mission2 = new RepairMission(
            302, "Fix Habitat Airlock", 2, "Airlock seal"
        );
        mission2.addAgent(unsuitable);

        boolean accepted2 = colony2.submitMission(mission2);
        System.out.println("Mission accepted: " + accepted2);
        if (!accepted2) {
            System.out.println("Mission cannot be assigned. Reason: Agent does not possess the required capability.");
            System.out.println("Internal reason: " + mission2.getFailureReason());
        }
        System.out.println("Mission state: " + mission2.getState());

        System.out.println();
        System.out.println("========================================");
        System.out.println("SCENARIO 3: Resource Shortage");
        System.out.println("========================================");

        FacilityResources.facilities.clear();

        Environment env3 = new Environment("Normal");
        MarsColony colony3 = new MarsColony("Mars Base Alpha", env3);
        Simulation simulation3 = new Simulation(colony3);

        Engineer engineer = new Engineer(
            "Sarah", 201, 100, "Repair", 0, "Mechanical"
        );
        RepairRobot repairBot = new RepairRobot(
            401, 100, "Operational", "RepairBot-9"
        );
        Habitat habitat3 = new Habitat(
            "Habitat C", "Mars Base", 20, 8
        );
        habitat3.setResources(300, 100, 50, 20, 5, 10, 1);

        colony3.addMember(engineer);
        colony3.addMember(repairBot);
        colony3.addFacility(habitat3);

        System.out.println("--- Colony Resources Before Attempt ---");
        colony3.displayStatus();

        double spareBefore = habitat3.getResources().getFacilitySpare_parts();
        double batteryBefore = habitat3.getResources().getFacilityBattery_power();

        RepairMission mission3 = new RepairMission(
            303, "Overhaul Life Support", 4, "O2 generator"
        );
        mission3.addAgent(engineer);
        mission3.addAgent(repairBot);
        mission3.setRequiredSpareParts(999999);

        boolean accepted3 = colony3.submitMission(mission3);
        System.out.println("Mission accepted: " + accepted3);
        if (!accepted3) {
            System.out.println("Mission rejected. Reason: " + mission3.getFailureReason());
        }

        double spareAfter = habitat3.getResources().getFacilitySpare_parts();
        double batteryAfter = habitat3.getResources().getFacilityBattery_power();
        System.out.println("Spare parts before: " + spareBefore + " after: " + spareAfter);
        System.out.println("Battery before: " + batteryBefore + " after: " + batteryAfter);
        System.out.println("Resources consumed: " + (spareBefore == spareAfter && batteryBefore == batteryAfter ? "0 (none)" : "non-zero"));
        System.out.println("Mission state: " + mission3.getState());

        System.out.println();
        System.out.println("========================================");
        System.out.println("SCENARIO 4: Emergency Handling (Oxygen Leak)");
        System.out.println("========================================");

        FacilityResources.facilities.clear();

        Environment env4 = new Environment("Normal");
        MarsColony colony4 = new MarsColony("Mars Base Alpha", env4);

        Engineer eng4 = new Engineer(
            "Sarah", 201, 100, "Repair", 0, "Mechanical"
        );
        RepairRobot bot4 = new RepairRobot(
            401, 100, "Operational", "RepairBot-9"
        );
        Habitat habitat4 = new Habitat(
            "Habitat D", "Mars Base", 20, 8
        );
        habitat4.setResources(300, 100, 50, 20, 100, 10, 5);

        colony4.addMember(eng4);
        colony4.addMember(bot4);
        colony4.addFacility(habitat4);

        double oxygenBefore = habitat4.getResources().getFacilityOxegen();

        OxygenLeak leak = new OxygenLeak(habitat4, 50);
        leak.respond(colony4);

        double oxygenAfter = habitat4.getResources().getFacilityOxegen();
        System.out.println("Oxygen before leak: " + oxygenBefore);
        System.out.println("Oxygen after response: " + oxygenAfter);
        System.out.println("Emergency status: " + leak.getStatus());
        System.out.println("Engineer available: " + eng4.getAvailability());
        System.out.println("RepairRobot available: " + bot4.getAvailability());

        System.out.println();
        System.out.println("========================================");
        System.out.println("SCENARIO 5: Environmental Effect (Dust Storm)");
        System.out.println("========================================");

        FacilityResources.facilities.clear();

        Environment env5 = new Environment("Normal");
        MarsColony colony5 = new MarsColony("Mars Base Alpha", env5);

        Scientist sci5 = new Scientist(
            "Ahmed", 101, 100, "Research", 0, "Geology"
        );
        ExplorationRover rover5 = new ExplorationRover(
            201, 100, "Operational", "Rover X1"
        );
        Habitat habitat5 = new Habitat(
            "Habitat E", "Mars Base", 20, 8
        );
        habitat5.setResources(300, 100, 50, 20, 100, 10, 5);

        colony5.addMember(sci5);
        colony5.addMember(rover5);
        colony5.addFacility(habitat5);

        env5.setCondition("Dust Storm");

        ExplorationMission mission5 = new ExplorationMission(
            305, "Explore Sector B", 3, "Sector B"
        );
        mission5.addAgent(sci5);
        mission5.addAgent(rover5);

        boolean accepted5 = colony5.submitMission(mission5);
        System.out.println("Mission accepted: " + accepted5);
        if (!accepted5) {
            System.out.println("Exploration Mission cannot start. Reason: Surface operations are not allowed during a dust storm.");
            System.out.println("Internal reason: " + mission5.getFailureReason());
        }
        System.out.println("Mission state: " + mission5.getState());
        System.out.println("Environment condition: " + env5.getCondition());

        System.out.println();
        System.out.println("========================================");
        System.out.println("ALL SCENARIOS COMPLETE");
        System.out.println("========================================");
    }
}
