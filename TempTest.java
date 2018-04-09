import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TempTest {
    // Let's test some words ay

    public static void main(String[] args) {

        method();

        String[] wordsA = {"dog", "cat", "cat", "smith", "paper", "paper", "oaky"};
        String[] wordsB = {"dag", "ca", "cata", "zmith", "apaper", "papera", "aoigweh"};

        boolean[] expected = {true, true, true, true, true, true, false};
        boolean[] results = new boolean[expected.length];

        for ( int i = 0; i < wordsA.length; i++ ) {
            boolean result = WordProcessor.isAdjacent(wordsA[i], wordsB[i]);
            results[i] = result;

            if ( result != expected[i] ) {
                System.out.println("FAILED: " + wordsA[i] + " and " + wordsB[i] + " should have been " + expected[i] + " but were opposite");
            }
        }

        System.out.println(Arrays.equals(expected, results) ? "passed" : "failed");
    }

    public static void method() {
        ArrayList<String> words = null;
        try {
            Stream<String> stream = WordProcessor.getWordStream("file.txt");
            words = stream.map(string -> string.split(" "))
                                           .flatMap(Arrays::stream)
                                           .collect(Collectors.toCollection(ArrayList::new));
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        if ( words != null ) {
            System.out.println(words);
        }
    }
}
