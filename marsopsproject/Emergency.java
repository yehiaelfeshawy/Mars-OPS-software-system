
public abstract class Emergency {
    private String type;
    private String status;
    private String reason;

    public Emergency(String type) {
        this.type = type;
        this.status = "ACTIVE";
        this.reason = "";
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    protected void resolve() {
        status = "RESOLVED";
    }

    protected void unresolved(String reason) {
        status = "UNRESOLVED";
        this.reason = reason;
        System.out.println("Emergency " + type + " UNRESOLVED. Reason: " + reason);
    }

    protected void dispatch(ColonyMembers responder, String location) {
        responder.setLocation(location);
        responder.setAvailability(false);
    }

    protected void release(ColonyMembers responder) {
        responder.setAvailability(true);
    }

    public abstract void respond(MarsColony colony);
}