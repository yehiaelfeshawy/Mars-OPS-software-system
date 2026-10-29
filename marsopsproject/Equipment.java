public class Equipment {

    private int equipmentID;
    private String equipmentName;
    private boolean operationalStatus;

    public Equipment(int equipmentID, String equipmentName, boolean operationalStatus) {
        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
        this.operationalStatus = operationalStatus;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setOperationalStatus(boolean operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    public boolean getOperationalStatus() {
        return operationalStatus;
    }

    public void displayInfo() {
        System.out.println("Equipment ID: " + equipmentID);
        System.out.println("Equipment Name: " + equipmentName);
        System.out.println("Operational: " + operationalStatus);
    }
}

