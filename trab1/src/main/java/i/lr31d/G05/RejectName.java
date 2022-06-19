package i.lr31d.G05;

public class RejectName implements RejectionRule{

    private String description;

    public RejectName( String description ){
        this.description = description;
    }

    @Override
    public boolean reject(User u) {
        return u.getDescription("").contains(description);
    }

    @Override
    public String getDescription() {
        return "Reject name " + description;
    }
}
