import java.util.ArrayList;
import java.util.List;

public class FacilityResources {
    public static List<Facility> facilities = new ArrayList<>();
    private Facility facility;
    private double Oxegen = 0.0;
    private double Water = 0.0;
    private double Food = 0.0;
    private double Fuel = 0.0;
    private double Battery_power = 0.0;
    private double Medical_supplies = 0.0;
    private double Spare_parts = 0.0;

    public FacilityResources(Facility facility, double oxegen, double water, double food, double fuel,
            double battery_power, double medical_supplies, double spare_parts) {
        this.facility = facility;
        setFacilityOxegen(oxegen);
        setFacilityWater(water);
        setFacilityFood(food);
        setFacilityFuel(fuel);
        setFacilityBattery_power(battery_power);
        setFacilityMedical_supplies(medical_supplies);
        setFacilitySpare_parts(spare_parts);
    }

    // --- STATIC COLONY GETTERS ---

    public static double get_oxegen() {
        double totalOxegen = 0.0;
        for (Facility facility : facilities) {
            if (facility.getOperationalStatus()) {
                FacilityResources facilityResources = facility.getResources();
                if (facilityResources != null) {
                    totalOxegen += facilityResources.getFacilityOxegen();
                }
            }
        }
        return totalOxegen;
    }

    public static double get_water() {
        double totalWater = 0.0;
        for (Facility facility : facilities) {
            if (facility.getOperationalStatus()) {
                FacilityResources facilityResources = facility.getResources();
                if (facilityResources != null) {
                    totalWater += facilityResources.getFacilityWater();
                }
            }
        }
        return totalWater;
    }

    public static double get_food() {
        double totalFood = 0.0;
        for (Facility facility : facilities) {
            if (facility.getOperationalStatus()) {
                FacilityResources facilityResources = facility.getResources();
                if (facilityResources != null) {
                    totalFood += facilityResources.getFacilityFood();
                }
            }
        }
        return totalFood;
    }

    public static double get_fuel() {
        double totalFuel = 0.0;
        for (Facility facility : facilities) {
            if (facility.getOperationalStatus()) {
                FacilityResources facilityResources = facility.getResources();
                if (facilityResources != null) {
                    totalFuel += facilityResources.getFacilityFuel();
                }
            }
        }
        return totalFuel;
    }

    public static double get_battery_power() {
        double totalBatteryPower = 0.0;
        for (Facility facility : facilities) {
            if (facility.getOperationalStatus()) {
                FacilityResources facilityResources = facility.getResources();
                if (facilityResources != null) {
                    totalBatteryPower += facilityResources.getFacilityBattery_power();
                }
            }
        }
        return totalBatteryPower;
    }

    public static double get_medical_supplies() {
        double totalMedicalSupplies = 0.0;
        for (Facility facility : facilities) {
            if (facility.getOperationalStatus()) {
                FacilityResources facilityResources = facility.getResources();
                if (facilityResources != null) {
                    totalMedicalSupplies += facilityResources.getFacilityMedical_supplies();
                }
            }
        }
        return totalMedicalSupplies;
    }

    public static double get_spare_parts() {
        double totalSpareParts = 0.0;
        for (Facility facility : facilities) {
            if (facility.getOperationalStatus()) {
                FacilityResources facilityResources = facility.getResources();
                if (facilityResources != null) {
                    totalSpareParts += facilityResources.getFacilitySpare_parts();
                }
            }
        }
        return totalSpareParts;
    }

    // --- ACTION METHODS ---

    public Boolean Facility_Repair_Mission(RoboticUnit robot) {
        double neededParts = 2.0;
        double neededBattery = 5.0;

        if (this.facility == null
        || !FacilityResources.facilities.contains(this.facility)
        || !this.facility.getOperationalStatus()) {
            System.err.println("Cannot perform action: Facility is non-operational.");
            return false;
        }

        if (FacilityResources.get_spare_parts() < neededParts
                || FacilityResources.get_battery_power() < neededBattery) {
            System.err.println("Not enough spare parts or battery power across operational facilities.");
            return false;
        }

        double partsFromSelf = Math.min(this.getFacilitySpare_parts(), neededParts);
        this.setFacilitySpare_parts(this.getFacilitySpare_parts() - partsFromSelf);
        neededParts -= partsFromSelf;

        double batteryFromSelf = Math.min(this.getFacilityBattery_power(), neededBattery);
        this.setFacilityBattery_power(this.getFacilityBattery_power() - batteryFromSelf);
        neededBattery -= batteryFromSelf;

        for (Facility facility : FacilityResources.facilities) {
            if (!facility.getOperationalStatus())
                continue;

            FacilityResources fr = facility.getResources();
            if (fr == null || fr == this)
                continue;

            if (neededParts > 0) {
                double available = fr.getFacilitySpare_parts();
                double take = Math.min(available, neededParts);
                fr.setFacilitySpare_parts(available - take);
                neededParts -= take;
            }

            if (neededBattery > 0) {
                double available = fr.getFacilityBattery_power();
                double take = Math.min(available, neededBattery);
                fr.setFacilityBattery_power(available - take);
                neededBattery -= take;
            }

            if (neededParts <= 0 && neededBattery <= 0)
                break;
        }

        robot.setDamageStatus("Repaired");
        return true;
    }

    public Boolean Facility_Medical_emergency(HumanPersonnel personnel) {
        double neededMedical = 1.0;
        double neededOxegen = 50.0;

        if (this.facility == null
        || !FacilityResources.facilities.contains(this.facility)
        || !this.facility.getOperationalStatus()) {
            System.err.println("Cannot perform action: Facility is non-operational.");
            return false;
        }

        if (FacilityResources.get_medical_supplies() < neededMedical || FacilityResources.get_oxegen() < neededOxegen) {
            System.err.println("Not enough medical supplies or oxegen across operational facilities.");
            return false;
        }

        double medicalFromSelf = Math.min(this.getFacilityMedical_supplies(), neededMedical);
        this.setFacilityMedical_supplies(this.getFacilityMedical_supplies() - medicalFromSelf);
        neededMedical -= medicalFromSelf;

        double oxegenFromSelf = Math.min(this.getFacilityOxegen(), neededOxegen);
        this.setFacilityOxegen(this.getFacilityOxegen() - oxegenFromSelf);
        neededOxegen -= oxegenFromSelf;

        for (Facility facility : FacilityResources.facilities) {
            if (!facility.getOperationalStatus())
                continue;

            FacilityResources fr = facility.getResources();
            if (fr == null || fr == this)
                continue;

            if (neededMedical > 0) {
                double available = fr.getFacilityMedical_supplies();
                double take = Math.min(available, neededMedical);
                fr.setFacilityMedical_supplies(available - take);
                neededMedical -= take;
            }

            if (neededOxegen > 0) {
                double available = fr.getFacilityOxegen();
                double take = Math.min(available, neededOxegen);
                fr.setFacilityOxegen(available - take);
                neededOxegen -= take;
            }

            if (neededMedical <= 0 && neededOxegen <= 0)
                break;
        }

        if (personnel.getHealth() < 80) {
            personnel.setHealth(personnel.getHealth() + 20);
        } else {
            personnel.setHealth(100);
        }
        return true;
    }

    public Boolean Drink_water(HumanPersonnel personnel) {
        double neededWater = 1.0;

        if (this.facility == null
        || !FacilityResources.facilities.contains(this.facility)
        || !this.facility.getOperationalStatus()) {
            System.err.println("Cannot perform action: Facility is non-operational.");
            return false;
        }

        if (FacilityResources.get_water() < neededWater) {
            System.err.println("Not enough water available across operational facilities.");
            return false;
        }

        double waterFromSelf = Math.min(this.getFacilityWater(), neededWater);
        this.setFacilityWater(this.getFacilityWater() - waterFromSelf);
        neededWater -= waterFromSelf;

        for (Facility facility : FacilityResources.facilities) {
            if (!facility.getOperationalStatus())
                continue;

            FacilityResources fr = facility.getResources();
            if (fr == null || fr == this)
                continue;

            if (neededWater > 0) {
                double available = fr.getFacilityWater();
                double take = Math.min(available, neededWater);
                fr.setFacilityWater(available - take);
                neededWater -= take;
            } else {
                break;
            }
        }

        if (personnel.getFatigue() > 10) {
            personnel.setFatigue(personnel.getFatigue() - 10);
        } else {
            personnel.setFatigue(0);
        }
        return true;
    }

    public Boolean Consume_food(HumanPersonnel personnel) {
        double neededFood = 1.0;

        if (this.facility == null
        || !FacilityResources.facilities.contains(this.facility)
        || !this.facility.getOperationalStatus()) { 
            System.err.println("Cannot perform action: Facility is non-operational.");
            return false;
        }

        if (FacilityResources.get_food() < neededFood) {
            System.err.println("Not enough food available across operational facilities.");
            return false;
        }

        double foodFromSelf = Math.min(this.getFacilityFood(), neededFood);
        this.setFacilityFood(this.getFacilityFood() - foodFromSelf);
        neededFood -= foodFromSelf;

        for (Facility facility : FacilityResources.facilities) {
            if (!facility.getOperationalStatus())
                continue;

            FacilityResources fr = facility.getResources();
            if (fr == null || fr == this)
                continue;

            if (neededFood > 0) {
                double available = fr.getFacilityFood();
                double take = Math.min(available, neededFood);
                fr.setFacilityFood(available - take);
                neededFood -= take;
            } else {
                break;
            }
        }

        if (personnel.getFatigue() > 5) {
            personnel.setFatigue(personnel.getFatigue() - 5);
        } else {
            personnel.setFatigue(0);
        }
        return true;
    }

    public Boolean Recharge_robot(RoboticUnit robot) {
        double neededFuel = 100.0;

        if (this.facility == null
        || !FacilityResources.facilities.contains(this.facility)
        || !this.facility.getOperationalStatus()) {
            System.err.println("Cannot perform action: Facility is non-operational.");
            return false;
        }

        if (FacilityResources.get_fuel() < neededFuel) {
            System.err.println("Not enough fuel available to recharge the robot.");
            return false;
        }

        double fuelFromSelf = Math.min(this.getFacilityFuel(), neededFuel);
        this.setFacilityFuel(this.getFacilityFuel() - fuelFromSelf);
        neededFuel -= fuelFromSelf;

        for (Facility facility : FacilityResources.facilities) {
            if (!facility.getOperationalStatus())
                continue;

            FacilityResources fr = facility.getResources();
            if (fr == null || fr == this)
                continue;

            if (neededFuel > 0) {
                double available = fr.getFacilityFuel();
                double take = Math.min(available, neededFuel);
                fr.setFacilityFuel(available - take);
                neededFuel -= take;
            } else {
                break;
            }
        }

        if (robot.getBattery() + 50 > 100) {
            robot.setBattery(100);
        } else {
            robot.setBattery(robot.getBattery() + 50);
        }
        return true;
    }

    // --- GETTERS AND SETTERS ---

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public void setFacilityOxegen(double oxegen) {
        if (oxegen < 0) {
            System.out.println("Oxegen level cannot be negative.");
            return;
        } else if (oxegen > 1000) {
            System.out.println("Oxegen level cannot exceed 1000.");
            return;
        }
        Oxegen = oxegen;
    }

    public void setFacilityWater(double water) {
        if (water < 0) {
            System.out.println("Water level cannot be negative.");
            return;
        } else if (water > 500) {
            System.out.println("Water level cannot exceed 500.");
            return;
        }
        Water = water;
    }

    public void setFacilityFood(double food) {
        if (food < 0) {
            System.out.println("Food level cannot be negative.");
            return;
        } else if (food > 100) {
            System.out.println("Food level cannot exceed 100.");
            return;
        }
        Food = food;
    }

    public void setFacilityFuel(double fuel) {
        if (fuel < 0) {
            System.out.println("Fuel level cannot be negative.");
            return;
        } else if (fuel > 2000) {
            System.out.println("Fuel level cannot exceed 2000.");
            return;
        }
        Fuel = fuel;
    }

    public void setFacilityBattery_power(double battery_power) {
        if (battery_power < 0) {
            System.out.println("Battery power level cannot be negative.");
            return;
        } else if (battery_power > 10000) {
            System.out.println("Battery power level cannot exceed 10000.");
            return;
        }
        Battery_power = battery_power;
    }

    public void setFacilityMedical_supplies(double medical_supplies) {
        if (medical_supplies < 0) {
            System.out.println("Medical supplies level cannot be negative.");
            return;
        } else if (medical_supplies > 100) {
            System.out.println("Medical supplies level cannot exceed 100.");
            return;
        }
        Medical_supplies = medical_supplies;
    }

    public void setFacilitySpare_parts(double spare_parts) {
        if (spare_parts < 0) {
            System.out.println("Spare parts level cannot be negative.");
            return;
        } else if (spare_parts > 50) {
            System.out.println("Spare parts level cannot exceed 50.");
            return;
        }
        Spare_parts = spare_parts;
    }

    public double getFacilityOxegen() {
        return Oxegen;
    }

    public double getFacilityWater() {
        return Water;
    }

    public double getFacilityFood() {
        return Food;
    }

    public double getFacilityFuel() {
        return Fuel;
    }

    public double getFacilityBattery_power() {
        return Battery_power;
    }

    public double getFacilityMedical_supplies() {
        return Medical_supplies;
    }

    public double getFacilitySpare_parts() {
        return Spare_parts;
    }

    public Facility getFacility() {
        return facility;
    }

    public void displayFacilityResources() {
        String name = (facility != null) ? facility.getFacilityName() : "Unknown";
        System.out.println("Resources for Facility: " + name);
        System.out.println("Oxegen: " + Oxegen + " liters");
        System.out.println("Water: " + Water + " liters");
        System.out.println("Food: " + Food + " kilograms");
        System.out.println("Fuel: " + Fuel + " liters");
        System.out.println("Battery Power: " + Battery_power + " watt-hours");
        System.out.println("Medical Supplies: " + Medical_supplies + " units");
        System.out.println("Spare Parts: " + Spare_parts + " units");
    }
}