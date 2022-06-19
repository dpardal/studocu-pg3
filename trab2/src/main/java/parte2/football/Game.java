package parte2.football;

import java.nio.charset.StandardCharsets;

import static java.lang.Integer.parseInt;

public class Game {
    public final String localTeam;
    public final String visitorTeam;
    public final int localGoals;
    public final int visitorGoals;

    public static Game from(String desc) {
        /** 2b
            cria uma instância de Game a partir da string com o formato:
            <local_name> : <local_score> - <visitor_name> : <visitor_score> (ex: Benfica:3 – Porto:1 )
        */
        String[] subString = desc.split( "[:-]" );
        for ( int i = 0; i < subString.length; ++i )
            subString[i] = subString[i].trim();
        byte[] localTeamNameBytes = subString[0].getBytes();
        byte[] visitorTeamNameBytes = subString[2].getBytes();
        return new Game(
                new String( localTeamNameBytes, StandardCharsets.UTF_8 ),
                new String( visitorTeamNameBytes, StandardCharsets.UTF_8 ),
                parseInt( subString[1] ),
                parseInt( subString[3] )
        );
    }

    public Game(String local, String visitor,
                int localGoals, int visitorGoals) {
        this.localTeam = local;
        this.visitorTeam = visitor;
        this.localGoals = localGoals;
        this.visitorGoals = visitorGoals;
    }
}
