package i.lr31d.G05;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public abstract class User implements Comparable<User> {
    public final Date creationDate;
    private String name;
    private String nickName;
    private int count=0;

    protected User(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
        creationDate = new Date();
    }

    public void incLikes() { ++count; }

    public int getLikes() { return count; }

    public abstract List<User> getFriends(Predicate<User> f );

    public String getDescription(String prefix) {
        return prefix + name + " (" + nickName.toUpperCase() + ")";
    }

    public final String toString() { return getDescription(""); }

}
