package parte1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

// 2.
public class AlgorithmUtils {

    public static <V> int forEachIf(BufferedReader in, Function<String, V> mapper,
                                    Predicate<V> pred, Consumer<V> action) throws IOException {
        int count = 0;
        String line;
        while( ( line = in.readLine() ) != null ){  // que por cada linha do stream in
            V value = mapper.apply( line );         // obtenha o valor através da aplicação da função mapper
            if( pred.test( value ) ){               // e caso o valor obedeça ao predicado definido em pred
                action.accept( value );             // execute a ação definida em action passando-lhe o valor retornado pela função mapper.
                ++count;
            }
        }
        return count;       // O método forEachIf retorna o número de valores que foram passados ao consumer action.
    }
}