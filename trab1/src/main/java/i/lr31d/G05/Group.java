package i.lr31d.G05;

import java.util.*;
import java.util.function.Predicate;

public class Group extends User implements Iterable<User>{

    private ArrayList<User> members;

    public Group( String name, String nickName, User ... members ){
        super( name, nickName );
        this.members = new ArrayList<>(Arrays.asList(members));
    }

    public Group append( User user ) throws RuleException {
        if( !members.contains( user ) ){
            members.add( user );
            return this;
        }
        else {
            throw new RuleException( new RejectName( super.toString() ) );
        }
    }

    public Iterator<User> iterator(){
        return members.iterator();
    }

    @Override
    public List<User> getFriends( Predicate<User> filter ) {
        ArrayList<User> result = new ArrayList<>();
        for( User u : members ){
            if( filter.test( u ) )
                result.add( u );
        }
        return result;
    }

    public String getDescription( String prefix ){
        StringBuilder str = new StringBuilder( super.getDescription( "group " ) + " {");
        Collections.sort( members, (User u1, User u2) -> u1.compareTo( u2 ));
        for( User u : members ){
            str.append( u.getDescription( prefix ) + '\n' );
        }
        str.append( '}' );
        return str.toString();
    }

    @Override
    public int compareTo( User u ) {
        return this.creationDate.compareTo( u.creationDate );
    }
}
