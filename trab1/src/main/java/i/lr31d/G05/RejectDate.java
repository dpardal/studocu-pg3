package i.lr31d.G05;

import java.util.Date;

public class RejectDate implements RejectionRule{

    private Date creationDate;

    public RejectDate( Date creationDate ){
        this.creationDate = creationDate;
    }

    @Override
    public boolean reject(User u) {
        if( u.creationDate.compareTo(creationDate) < 0) return true;
        return false;
    }

    @Override
    public String getDescription() {
        return "Creation date less than" + creationDate.toString();
    }

}
