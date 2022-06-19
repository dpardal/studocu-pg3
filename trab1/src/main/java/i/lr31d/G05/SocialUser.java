package i.lr31d.G05;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.isNull;

public class SocialUser extends User {

    private RejectionRule[] rules;
    private ArrayList<User> friends = new ArrayList<>();

    public SocialUser( String name, String nickname, RejectionRule ... rules ){
        super(name, nickname);
        this.rules = rules;
    }

    public SocialUser( String name ){
        super(name, name.substring(0, 2));
        this.rules = null;
    }

    // O método friendRequest adiciona o amigo caso não
    // seja rejeitado por alguma das RejectionRules recebidas por parâmetro no construtor e retorna null, caso contrário
    // retorna a regra que o rejeitou.
    public RejectionRule friendRequest( SocialUser other ){
        for( RejectionRule r : rules ){
            if( r.reject(other) ) return r;
        }
        friends.add(other);
        return null;
    }

    // O método addFriend adiciona o amigo caso este não seja rejeitado por alguma das
    // RejectionRules e o amigo aceite o pedido de amizade (chamada a friendRequest)
    public SocialUser addFriend( SocialUser friend ) throws RuleException{
        for( RejectionRule r : rules ){
            if( !r.reject(friend) && !isNull( friend.friendRequest(this) ) ) { friends.add(friend); }
            else{ throw new RuleException(r); }
        }
        return friend;
    }

    @Override
    public List<User> getFriends(Predicate<User> filter) {
        ArrayList<User> result = new ArrayList<>();

        for( User u : friends ){
            if( filter.test( u ) )
                result.add( u );
        }
        return result;
    }

    @Override
    public int compareTo( User u ) {
        return this.getLikes() - u.getLikes();
    }

    public String getDescription( String prefix ) {
        String result = super.getDescription( prefix ) + " - ";
        if( friends.size() == 0 ) result += "not have friends";
        else{ result += friends.size() + " friends"; }
        return result;
    }
}