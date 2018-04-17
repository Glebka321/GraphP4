import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Contains test cases to test both GraphProcessor and WordProcessor classes, reads
 * from the files file.txt, and filetwo.txt
 */
public class GraphProcessorTest {
	GraphProcessor gp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		gp = new GraphProcessor();
	}

	@After
	public void tearDown() throws Exception {
		gp = null;
	}

	/**
	 * Ensures graph is properly populated with correct amount of nodes
	 * @throws IOException if file can not be read
	 */
	@Test
	public final void testPopulateGraph() throws IOException {
		Integer count = 427;
		Integer count2 = count + 2;
		assertEquals(gp.populateGraph("file.txt"), count);
		assertEquals(gp.populateGraph("filetwo.txt"), count2);
	}

	/**
	 * Tests that the shortest path between two nodes is correct
	 * @throws IOException if file can not be read
	 */
	@Test
	public final void testGetShortestPath() throws IOException {
		List<String> list = Stream.of("rapine", "ravine", "raving", "roving", "roping", "coping", "coming", "coaming", "coaling",
				"colling", "collins", "collies", "jollies", "jellies", "bellies", "bullies", "bullier",
				"burlier", "curlier", "currier", "carrier", "carries", "parries", "parties", "patties").collect(Collectors.toList());
		String word1 = "rapine";
		String word2 = "patties";
		gp.populateGraph("file.txt");
		gp.shortestPathPrecomputation();
		assertEquals(list, gp.getShortestPath(word1, word2));
	}

	/**
	 * Tests a distance between two nodes on graph
	 * @throws IOException if file can not be read
	 */
	@Test
	public final void testGetShortestDistance() throws IOException {
		Integer distance = 41;
		String word1 = "rapine";
		String word2 = "giblets";
		gp.populateGraph("file.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}

	/**
	 * From interesting combos, tests adjacency from file file.txt
	 * @throws IOException if file can not be read
	 */
	@Test
	public final void interestingCombosOne() throws IOException {
		Integer distance = 49;
		String word1 = "comedo";
		String word2 = "charge";
		gp.populateGraph("file.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}

	/**
	 * From interesting combos, tests adjacency from file file.txt
	 * @throws IOException if file can not be read
	 */
	@Test
	public final void interestingCombosTwo() throws IOException {
		Integer distance = 2;
		String word1 = "bellies";
		String word2 = "jollies";
		gp.populateGraph("file.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}

	/**
	 * From interesting combos, tests adjacency from file file.txt
	 * @throws IOException if file can not be read
	 */
	@Test
	public final void interestingCombosThree() throws IOException {
		Integer distance = 26;
		String word1 = "define";
		String word2 = "shinny";
		gp.populateGraph("file.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}

	/**
	 * Give a non existent vertex, should return false.
	 * @throws IOException if file can not be read
	 */
	@Test
	public final void adjacentNonExistantVertex() throws IOException {
		gp.populateGraph("filetwo.txt");
		gp.shortestPathPrecomputation();
		assertFalse("non existant vertex should return false", WordProcessor.isAdjacent("toys", "nothere"));
	}

	/**
	 * Tests that the same word is not adjacent with itself
	 */
	@Test
	public final void sameWordsNotAdjacent() {
		assertTrue("dog should not be adjacent with itself", !WordProcessor.isAdjacent("dog", "dog"));
		assertTrue("alligator should not be adjacent with itself", !WordProcessor.isAdjacent("alligator", "alligator"));
		assertTrue("smith should not be adjacent with itself", !WordProcessor.isAdjacent("smith", "smith"));
		assertTrue("a should not be adjacent with itself", !WordProcessor.isAdjacent("a", "a"));
	}

	/**
	 * tests cases where words are adjacent with different number of letters.
	 */
	@Test
	public final void adjacentOneLetterAddedAtEnd() {
		Boolean expected = true;

		String wordOne = "AAAA";
		String wordTwo = "AAAAb";
		Boolean result = WordProcessor.isAdjacent(wordOne, wordTwo);
		String message = wordOne + " and " + wordTwo + " should return " + expected + ", but return " + result;
		assertEquals(message, expected, result);

		wordOne = "AAAA";
		wordTwo = "bAAAA";
		result = WordProcessor.isAdjacent(wordOne, wordTwo);
		message = wordOne + " and " + wordTwo + " should return " + expected + ", but return " + result;
		assertEquals(message, expected, result);
	}

	/**
	 * Tests several cases where words are adjacent with only middle character differences
	 */
	@Test
	public final void adjacentOneLetterAddedInMiddle() {
		Boolean expected = true;
		String wordOne = "AAAA";
		String wordTwo = "AAbAA";

		Boolean result = WordProcessor.isAdjacent(wordOne, wordTwo);
		String message = wordOne + " and " + wordTwo + " should return " + expected + ", but return " + result;
		assertEquals(message, expected, result);

		wordOne = "AAAA";
		wordTwo = "AbAAA";
		result = WordProcessor.isAdjacent(wordOne, wordTwo);
		message = wordOne + " and " + wordTwo + " should return " + expected + ", but return " + result;
		assertEquals(message, expected, result);
	}

	/**
	 * Tests several cases where words are not adjacent
	 */
	@Test
	public final void notAdjacent() {
		Boolean expected = false;

		String wordOne = "AAAAb";
		String wordTwo = "bAAAA";
		Boolean result = WordProcessor.isAdjacent(wordOne, wordTwo);
		String message = wordOne + " and " + wordTwo + " should return " + expected + ", but return " + result;
		assertEquals(message, expected, result);

		wordOne = "AAAA";
		wordTwo = "AbbA";
		result = WordProcessor.isAdjacent(wordOne, wordTwo);
		message = wordOne + " and " + wordTwo + " should return " + expected + ", but return " + result;
		assertEquals(message, expected, result);
	}

	/**
	 * If one of two words is null, adjacency should return false.
	 */
	@Test
	public final void adjacentWithNulls() {
		assertFalse("null adjacency should return false", WordProcessor.isAdjacent(null, null));
		assertFalse("null adjacency should return false", WordProcessor.isAdjacent("", null));
		assertFalse("null adjacency should return false", WordProcessor.isAdjacent(null, ""));
	}

	/**
	 * if words are not in graph, distance should be -1
	 */
	@Test
	public final void shortestDistanceWordsDoNotExist() {
		int result = gp.getShortestDistance("zzzz", "zzzz");
		assertTrue("result of shortestDistance should be -1", result == -1);
	}

	/**
	 * shortest path from one word to itself should be an empty list
	 * @throws IOException if file can not be read
	 */
	@Test
	public final void shortestPathSameWord() throws IOException {
		gp.populateGraph("file.txt");
		gp.shortestPathPrecomputation();
		List<String> list = gp.getShortestPath("unity", "unity");
		System.out.println(list);
		assertTrue("shortest path between two of same word should be empty", list.isEmpty());
	}

	/**
	 * Adds two files to gp, makes sure all words are added.
	 * @throws IOException if file can not be read
	 */
	@Test
	public final void twoFiles() throws IOException {
		Integer distance = 1;
		String word1 = "homilys"; // contained in second file
		String word2 = "homily"; // contained in first file
		gp.populateGraph("file.txt");
		gp.populateGraph("filetwo.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}

	/**
	 * A word's distance to itself should be -1
	 * @throws IOException if file can not be read.
	 */
	@Test
	public final void distanceToItself() throws IOException {
		Integer distance = -1;
		String word1 = "homilys";
		String word2 = word1;
		gp.populateGraph("filetwo.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}
}
