public class StorageFacility extends Facility {

    private String storageType;

    public StorageFacility(String facilityName, String location,int capacity, String storageType) {
        setFacilityType("Storage Facility");
        setFacilityName(facilityName);
        setLocation(location);
        setCapacity(capacity);
        setOperationalStatus(true);

        this.storageType = storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getStorageType() {
        return storageType;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Storage Type: " + storageType);
    }
}
