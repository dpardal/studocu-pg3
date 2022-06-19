package parte2;

import java.util.*;
import java.util.function.Supplier;


//1
public class AlgorithmUtils {

    public static <V,C extends Collection<V>> List<C> getSubsequences(Iterable<V> sequence,
                                                                      Comparator<V> compareValue, Supplier<C> supplierList){
        /**
            produz uma lista de subsequências ordenadas, de forma crescente segundo o comparador cmp, existentes na
            sequência sequence. Exemplo com uma sequência de inteiros:
            Sendo a sequência => [10, 20, 30, 12, 13, 8, 1, 2, 3 ]
            Produz a lista de subsequências => [ [10, 20, 30], [12, 13], [8], [1, 2, 3] ]
        */
        List<C> res = new LinkedList<>();
        C subSequence = supplierList.get();
        for( V value : sequence ){
            if ( !subSequence.isEmpty() ) {
                if ( compareValue.compare( value, Collections.max( subSequence, compareValue ) ) < 0 ) {
                    res.add( subSequence );
                    subSequence = supplierList.get();
                }
            }
            subSequence.add( value );
        }
        res.add( subSequence );   // adicionar o ultimo troço. Como se fosse um flush() no fim.
        return res;
    }
}