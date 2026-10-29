
public class MarsOpsProject {

    public static void main(String[] args) {

        Equipment e1 = new Equipment(
            301, "Microscope", true
        );

        e1.displayInfo();

        System.out.println("------------------");

        e1.setOperationalStatus(false);

        System.out.println("Equipment ID: " + e1.getEquipmentID());
        System.out.println("Equipment Name: " + e1.getEquipmentName());
        System.out.println("Operational: " + e1.getOperationalStatus());
    }
}

