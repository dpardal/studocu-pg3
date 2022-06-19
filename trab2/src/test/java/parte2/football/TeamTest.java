package parte2.football;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TeamTest {

    private Team testTeam;

    @Test
    public void test_team_constructor(){
        testTeam = new Team( "Teste Futebol Clube" );

        Assertions.assertEquals( testTeam.getName(), "Teste Futebol Clube" );
    }

    @Test
    public void test_addGame_and_getters(){
        test_team_constructor();

        testTeam.addGame( 3, 2 );

        Assertions.assertEquals( testTeam.getScored(), 3 );
        Assertions.assertEquals( testTeam.getConceded(), 2 );
        Assertions.assertEquals( testTeam.getDefeats(), 0 );
        Assertions.assertEquals( testTeam.getVictories(), 1 );
        Assertions.assertEquals( testTeam.getDraws(), 0 );
        Assertions.assertEquals( testTeam.getPoints(), 3 );
        Assertions.assertEquals( testTeam.diffGoals(), 1 );
        Assertions.assertEquals( testTeam.playedGames(), 1 );
    }

}
