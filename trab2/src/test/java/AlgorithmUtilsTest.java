import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parte2.AlgorithmUtils;

import java.util.*;
import java.util.function.Supplier;

public class AlgorithmUtilsTest {

    private static ArrayList<Integer> integerSequence= new ArrayList<>(
            Arrays.asList( 10, 20, 30, 12, 13, 8, 1, 2, 3 )
    );
    private static ArrayList<Double> doubleSequence= new ArrayList<>(
            Arrays.asList( 10.0, 20.0, 30.0, 12.0, 13.0, 8.0, 1.0, 2.0, 3.0 )
    );
    private static ArrayList<String> stringSequence= new ArrayList<>(
            Arrays.asList( "Am\u00EDlcar", "Jos\u00E9", "Manuel", "Ant\u00F3nio", "Jacinto", "Francisco", "Eduardo", "Jo\u00E3o", "Rui" )
    );
    private static Supplier<Collection<Integer>> integerListSupplier = ArrayList::new;
    private static Supplier<Collection<Double>> doubleListSupplier = ArrayList::new;
    private static Supplier<Collection<String>> stringListSupplier = ArrayList::new;

    private static Comparator<Integer> integerComparator = ( Integer::compareTo );
    private static Comparator<Double> doubleComparator = ( Double::compare );
    private static Comparator<String> stringComparator = ( String::compareTo );

    @Test
    public void test_getSubsequences_of_integers(){
        List<Collection<Integer>> integerCollectionsList =
                AlgorithmUtils.getSubsequences( integerSequence, integerComparator, integerListSupplier );
        List<Collection<Integer>> expected = new LinkedList<>( Arrays.asList(
                new ArrayList<>(Arrays.asList(10, 20, 30)),
                new ArrayList<>(Arrays.asList(12, 13)),
                new ArrayList<>(Arrays.asList(8)),
                new ArrayList<>(Arrays.asList(1, 2, 3))
        ));
        Assertions.assertEquals( expected, integerCollectionsList );
    }

    @Test
    public void test_getSubsequences_of_doubles(){
        List<Collection<Double>> doubleCollectionsList =
                AlgorithmUtils.getSubsequences( doubleSequence, doubleComparator, doubleListSupplier );
        List<Collection<Double>> expected = new LinkedList<>( Arrays.asList(
                new ArrayList<>(Arrays.asList(10.0, 20.0, 30.0)),
                new ArrayList<>(Arrays.asList(12.0, 13.0)),
                new ArrayList<>(Arrays.asList(8.0)),
                new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0))
        ));
        Assertions.assertEquals( expected, doubleCollectionsList );
    }

    @Test
    public void test_getSubsequences_of_strings(){
        List<Collection<String>> stringCollectionsList =
                AlgorithmUtils.getSubsequences( stringSequence, stringComparator, stringListSupplier );
        List<Collection<String>> expected = new LinkedList<>( Arrays.asList(
                new ArrayList<>(Arrays.asList("Am\u00EDlcar", "Jos\u00E9", "Manuel")),
                new ArrayList<>(Arrays.asList("Ant\u00F3nio", "Jacinto")),
                new ArrayList<>(Arrays.asList("Francisco")),
                new ArrayList<>(Arrays.asList("Eduardo", "Jo\u00E3o", "Rui"))
        ));
        Assertions.assertEquals( expected, stringCollectionsList );
    }
}
