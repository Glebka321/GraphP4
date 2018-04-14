import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class contains some utility helper methods
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class WordProcessor {

    /**
     * Gets a Stream of words from the filepath.
     * 
     * The Stream should only contain trimmed, non-empty and UPPERCASE words.
     * 
     * @see <a href=
     *      "http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8
     *      stream blog</a>
     * 
     * @param filepath file path to the dictionary file
     * @return Stream<String> stream of words read from the filepath
     * @throws IOException exception resulting from accessing the filepath
     */
    public static Stream<String> getWordStream(String filepath) throws IOException {
        Stream<String> stream = Files.lines(Paths.get(filepath));

        return stream.filter(line -> !line.isEmpty()).map(String::toUpperCase).map(String::trim);
        //@TODO: Connor, this fixes stream issue ATM
        //return stream.map(String::trim).filter(line -> line != null && !line.("")).map(String::toUpperCase);
    }

    /**
     * Adjacency between word1 and word2 is defined by: if the difference between word1 and word2 is
     * of 1 char replacement 1 char addition 1 char deletion then word1 and word2 are adjacent else
     * word1 and word2 are not adjacent
     * 
     * Note: if word1 is equal to word2, they are not adjacent
     * 
     * @param word1 first word
     * @param word2 second word
     * @return true if word1 and word2 are adjacent else false
     */
    public static boolean isAdjacent(String word1, String word2) {
        int differenceCount = 0; // counts the number of differences between the two strings

        int word1Size = word1.length();
        int word2Size = word2.length();

        int sizeDifference = Math.abs(word1Size - word2Size);

        // If words are the same, they are not adjacent
        if ( word1.compareTo(word2) == 0 ) {
            return false;
        }

        if ( sizeDifference > 1 ) {
            return false;
        }

        // if strings have the same length compare character by character, and see how many
        // replacements there are
        if (sizeDifference == 0) {
            for (int i = 0; i < word1Size; i++) {
                if ( word1.charAt(i) != word2.charAt(i) ) {
                    differenceCount++;
                }
            }
        }

        if (word1.length() - word2.length() == 1) {
            int i = 0;
            while (i < word2.length() && Character.toString(word1.charAt(i))
                            .compareTo(Character.toString(word2.charAt(i))) == 0) {
                i++;
            }
            differenceCount++;
            for (int j = i; j < word2.length(); j++) {
                if (Character.toString(word1.charAt(j + 1))
                                .compareTo(Character.toString(word2.charAt(j))) != 0) {
                    differenceCount++;
                }
            }
        }
        if (word1.length() - word2.length() == -1) {
            int i = 0;
            while (i < word1.length() && Character.toString(word1.charAt(i))
                            .compareTo(Character.toString(word2.charAt(i))) == 0) {
                i++;
            }
            differenceCount++;
            for (int j = i; j < word1.length(); j++) {
                if (Character.toString(word1.charAt(j))
                                .compareTo(Character.toString(word2.charAt(j + 1))) != 0) {
                    differenceCount++;
                }
            }
        }

        return (differenceCount == 1);
    }

}
