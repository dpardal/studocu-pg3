package i.lr31d.G05;

public class RuleException extends Exception{

    private RejectionRule rr;

    public RuleException( String msg, RejectionRule r ){
        super( msg + '"' + r.getDescription() + '"' );
        rr = r;
    }

    public RuleException( RejectionRule r ){
        super("Reject user by rule: " + '"' + r.getDescription() + '"');
        rr = r;
    }

    public RejectionRule getRule(){
        return rr;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "Reject user by rule:";
    }
}
