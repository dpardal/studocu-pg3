package parte2.football;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

    public Game testGame;

    @Test
    public void test_game_constructor(){
        testGame = new Game( "Porto", "Benfica", 0, 2 );

        Assertions.assertEquals( testGame.localTeam, "Porto" );
        Assertions.assertEquals( testGame.visitorTeam, "Benfica" );
        Assertions.assertEquals( testGame.localGoals, 0 );
        Assertions.assertEquals( testGame.visitorGoals, 2 );
    }

    @Test
    public void test_from(){
        testGame = Game.from( " SL Benfica : 3  -  Sporting CP : 1" );

        Assertions.assertEquals( testGame.localTeam, "SL Benfica" );
        Assertions.assertEquals( testGame.visitorTeam, "Sporting CP" );
        Assertions.assertEquals( testGame.localGoals, 3 );
        Assertions.assertEquals( testGame.visitorGoals, 1 );
    }

}
