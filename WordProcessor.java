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
        if ( word1.equals(word2) || sizeDifference > 1 ) {
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
        else if ( word1Size - word2Size == 1 ) {
            differenceCount = getDifferences(word1, word2);
        }
        else if ( word2Size - word1Size == 1 ) {
            differenceCount = getDifferences(word2, word1);
        }

        // Return true only when one difference exists.
        return differenceCount == 1;
    }

    /**
     * Gets the amount of differences between two words of Strings one character apart in length
     * @param longerWord The longer of the two words to compare
     * @param shorterWord The shorter of the two words to compare
     * @return the number of differences between the two words
     */
    private static int getDifferences(String longerWord, String shorterWord) {
        int differenceCount = 1;
        int i = 0;
        while (i < shorterWord.length() && Character.toString(longerWord.charAt(i))
                .compareTo(Character.toString(shorterWord.charAt(i))) == 0) {
            i++;
        }
        for (int j = i; j < shorterWord.length(); j++) {
            if (Character.toString(longerWord.charAt(j + 1))
                    .compareTo(Character.toString(shorterWord.charAt(j))) != 0) {
                differenceCount++;
            }
        }

        return differenceCount;
    }

}
