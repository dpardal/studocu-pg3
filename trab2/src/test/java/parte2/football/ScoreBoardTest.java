package parte2.football;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;


public class ScoreBoardTest {

    private ScoreBoard testBoard = new ScoreBoard();

    @Test
    public void test_add_game() {
        Game test = Game.from( "FC Famalicão 		: 1 -  	FC Porto        : 2" );
        testBoard.addGame( test );
        testBoard.show();
    }

    @Test
    public void test_loadFrom() {
        String cwd = Path.of("").toAbsolutePath().toString();
        String fileIn = cwd + "\\src\\test\\java\\parte2\\football\\ligaportugalbwin.txt";
        // https://www.ligaportugal.pt/pt/liga/calendario/completo/20212022/ligaportugalbwin

        try{
            testBoard.loadFrom( fileIn );
            testBoard.show();
        }
        catch (Exception e) {
            Assertions.fail( "Student says \"Failure cause:\n" + e.getMessage() + "\"\n" );
        }
    }

    @Test
    public void test_toSortedTable() {

        test_loadFrom();

        Comparator<Team> teamComparator = (Team a, Team b ) -> {
            int goal_difference = b.diffGoals() - a.diffGoals();
            if( goal_difference != 0 ){ return goal_difference; }
            else{ return a.getName().compareTo( b.getName() ); }
        };
        Supplier<Collection<Team>> supplier = () -> new TreeSet<>(teamComparator);


        TreeMap<Integer, Collection<Team>> actual = testBoard.toSortedTable( supplier );

        Team famalicao = new Team("FC Famalic\u00E3o");  // só para teste
        Team braga = new Team("SC Braga");
        Team tondela = new Team("CD Tondela");
        Team boavista = new Team("Boavista FC");

        famalicao.addGame( 2, 2 );
        braga.addGame( 2, 2 );
        tondela.addGame( 1, 1 );
        boavista.addGame( 1, 1 );

        TreeSet<Team> collection = new TreeSet<>(teamComparator);
        collection.addAll(
                Arrays.asList(
                        famalicao,
                        braga,
                        tondela,
                        boavista
                )
        );

        TreeMap<Integer, Collection<Team>> expected = new TreeMap<>();
        expected.put( 1, collection );

        Assertions.assertEquals( expected.get(1), actual.get(1) ); // Draws
    }


    @Test
    // https://www.ligaportugal.pt/pt/liga/classificacao/20212022/ligaportugalbwin
    public void test_saveOn() {

        test_loadFrom();

        String cwd = Path.of("").toAbsolutePath().toString();
        String fileOut = cwd + "\\src\\test\\java\\parte2\\football\\TabelaClassificativa.txt";

        try {
            testBoard.saveOn(fileOut);
        } catch (Exception e) {
            Assertions.fail("Student says \"Failure cause:\n" + e.getMessage() + "\"\n");
        }
    }           // tudo OK

}
