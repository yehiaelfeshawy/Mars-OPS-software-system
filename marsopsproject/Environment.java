public class Environment {

    private String condition;

    public Environment(String condition) {
        this.condition = condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public boolean checkSurfaceOperations() {
        if (condition.equals("Dust Storm")) {
            return false;
        }
        return true;
    }

    public boolean checkCommunication() {
        if (condition.equals("Communication Disruption")) {
            return false;
        }
        return true;
    }

    public void displayInfo() {
        System.out.println("Environment Condition: " + condition);
    }
}
