
public class ResearchMission extends Mission {

    private String researchTopic;

    public ResearchMission(int missionID, String missionName,int duration, String researchTopic) {

        super(missionID, missionName, duration);

        this.researchTopic = researchTopic;

        setRequiredOxygen(10);
        setRequiredBattery(5);
    }

    public void setResearchTopic(String researchTopic) {
        this.researchTopic = researchTopic;
    }

    public String getResearchTopic() {
        return researchTopic;
    }

    @Override
    public boolean isAllowedIn(Environment env) {
        return env != null;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Research Topic: " + researchTopic);
    }
}
