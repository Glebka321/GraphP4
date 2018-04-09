import java.util.ArrayList;
import java.util.Arrays;

public class TempTest {
    // Let's test some words ay

    public static void main(String[] args) {

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
}
