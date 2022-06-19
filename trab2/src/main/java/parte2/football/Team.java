package parte2.football;

public class Team {
    private final String name;

    // counters
    private int scored;
    private int conceded;
    private int defeats;
    private int draws;
    private int victories;


    public Team(String name) {
        this.name = name;
    }

    public void addGame(int scored, int conceded) {     //2a
        this.scored = scored;
        this.conceded = conceded;
        int k = scored - conceded;
        if (k > 0){ ++victories; }
        else {
            if (k < 0) { ++defeats; }
            else { ++draws; }
        }
    }

    public int playedGames() {                          //2a
        return victories + draws + defeats;
    }

    public int getPoints() {                            //2a
        return (victories * 3) + draws;
    }

    public int getScored() {
        return scored;
    }

    public int getConceded() {
        return conceded;
    }

    public int getDefeats() {
        return defeats;
    }

    public int getDraws()   {
        return draws;
    }

    public int getVictories() {
        return victories;
    }

    public String getName() {
        return name;
    }

    public int diffGoals() {
        return scored - conceded;
    }

    public String toString() {
        return String.format("%-18s%-8s%-8s%-12s%-8s%-10s%-10s%-10s\n",
            getName(),
            getPoints(),
            playedGames(),
            getVictories(),
            getDraws(),
            getDefeats(),
            getScored(),
            getConceded());
    }
}
