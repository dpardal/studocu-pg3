package parte2.football;

import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ScoreBoard {
    private final Map<String,Team> board;

    public ScoreBoard() {
        board = new HashMap<>();
    }


    public void loadFrom( String gamesFileName ) throws IOException  {
        /** 2c ii
            O método loadFrom(String gamesFileName) adiciona ao mapa do score board os resultados presentes
            no ficheiro de nome gamesFileName. Note que o método loadFrom pode lançar a excepção IOException.
            Os resultados existentes no ficheiro seguem o formato apresentado na alínea anterior.
        */
        // Corrigir charset em Game.from() ou aqui:
        // try( BufferedReader in = new BufferedReader(
        //      new InputStreamReader( new FileInputStream( gamesFileName ), "UTF-8" ) )
        // ){ ..... }
        try( BufferedReader in = new BufferedReader( new FileReader( gamesFileName ) ) ){
            String line;
            while( ( line = in.readLine() ) != null ){
                Game game = Game.from( line );
                this.addGame( game );
            }
        }
    }


    public void addGame( Game game ) {
        /** 2c i
            adiciona o resultado de game ao mapa do score board.
        */
        Team local = board.get( game.localTeam );
        Team visitor = board.get( game.visitorTeam );

        if( local == null ) local = new Team( game.localTeam );
        if( visitor == null ) visitor = new Team( game.visitorTeam );

        local.addGame( game.localGoals, game.visitorGoals);
        visitor.addGame( game.visitorGoals, game.localGoals );

        board.put( game.localTeam, local );
        board.put( game.visitorTeam, visitor );
    }


    public <C extends Collection<Team>> TreeMap<Integer, C> toSortedTable(Supplier<C> supplier) {
        /** 2c iii
                Assinatura do método alterada com autorização do Prof. Jorge Pião

            produz, a partir dos pares presentes no hashmap board, outro mapa ordenado pela
            chave inteira que representa uma dada pontuação, e cujo valor é a coleção
            das equipas com essa pontuação. Cada coleção é obtida do fornecedor supplier.
        */
        TreeMap<Integer, C> res = new TreeMap<>();
        for ( Map.Entry<String, Team> entry : board.entrySet() ){
            Team team = entry.getValue();
            int key = team.getPoints();

            C teams = res.get( key );
            if( teams == null ){
                teams = supplier.get();
                res.put( key, teams );
            }
            teams.add( team );
        }
        return res;
    }

    public static <C extends Collection<Team>>
    void processTable(SortedMap<Integer, C> table, BiConsumer<Integer,Team> action) {
        /** 2c iv
                                                DE ACORDO COM O ENUNCIADO

            recebe o mapa table, em que a chave são os pontos e o valor associado as equipas com esses pontos,
            executandp a ação action por cada par (pontos, equipa) disponível em table.
        */
        for ( Map.Entry<Integer, C> entry : table.entrySet() ){
            for ( Team team : entry.getValue() ){
               action.accept( entry.getKey(), team );
            }
        }
    }


    public void saveOn(String tableFileName) throws IOException {
        /** 2c v
            cria o ficheiro de texto de nome tableFileName com a tabela classificativa do campeonato, ordenando as
            equipas por ordem decrescente dos seus pontos. As equipas com os mesmos pontos devem ser apresentadas
            por ordem decrescente da sua diferença de golos.
        */
        try( FileWriter out = new FileWriter( tableFileName ) ){
            Comparator<Team> teamComparator = ( Team a, Team b ) -> {
                int goal_difference = b.diffGoals() - a.diffGoals();
                if( goal_difference != 0 ){ return goal_difference; }
                else{ return a.getName().compareTo( b.getName() ); }
            };
            Supplier<Collection<Team>> supplier = () -> new TreeSet<>(teamComparator);
            TreeMap<Integer, Collection<Team>> table = toSortedTable( supplier );
            NavigableMap<Integer, Collection<Team>> reversed = table.descendingMap();
            BiConsumer<Integer, Team> action = ( n, t ) -> {
                try {
                    out.write( t.toString() );
                } catch (IOException e) {
                    throw new UncheckedIOException( e );
                }
            };
            out.write(
                String.format(
                    "%-18s%-8s%-8s%-12s%-8s%-10s%-10s%-10s\n",
                    "Name",
                    "Points",
                    "Games",
                    "Victories",
                    "Draws",
                    "Defeats",
                    "Scored",
                    "Conceded",
                    "\n"
                )
            );
            ScoreBoard.processTable( reversed, action );
        }
    }

    /**
     * for debug purposes
     */
    void show() {
        List<Team> teams = new ArrayList<>();
        board.values().forEach(t -> teams.add(t));
        teams.sort(Comparator.comparingInt(Team::getPoints));
        System.out.format(
            "%-18s%-8s%-8s%-12s%-8s%-10s%-10s%-10s\n",
            "Name",
            "Points",
            "Games",
            "Winners",
            "Draws",
            "Defeats",
            "Scored",
            "Conceded");
        teams.forEach(System.out::println);
    }
}
