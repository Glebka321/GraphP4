import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

	@Test 
	public final void testPopulateGraph() throws IOException {
		Integer count = 427;
		assertEquals(gp.populateGraph("file.txt"), count);
	}

	@Test
	public final void testGetShortestPath() throws IOException {
		List<String> list = Stream.of("rapine", "ravine" , "raving", "roving", "roping", "coping", "coming", "coaming", "coaling",
										"colling", "collins", "collies", "jollies","jellies", "bellies", "bullies", "bullier",
										"burlier", "curlier", "currier", "carrier", "carries", "parries", "parties", "patties").collect(Collectors.toList());
		String word1 = "rapine";
		String word2 = "patties";
		gp.populateGraph("file.txt");
		gp.shortestPathPrecomputation();
		assertEquals(list, gp.getShortestPath(word1, word2));
	}

	@Test 
	public final void testGetShortestDistance() throws IOException {
		Integer distance = 41;
		String word1 = "rapine";
		String word2 = "giblets";
		gp.populateGraph("file.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}


	@Test
	public final void interestingCombosOne() throws IOException {
		Integer distance = 49;
		String word1 = "comedo";
		String word2 = "charge";
		gp.populateGraph("file.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}

	@Test
	public final void interestingCombosTwo() throws IOException {
		Integer distance = 2;
		String word1 = "bellies";
		String word2 = "jollies";
		gp.populateGraph("file.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}

	@Test
	public final void interestingCombosThree() throws IOException {
		Integer distance = 26;
		String word1 = "define";
		String word2 = "shinny";
		gp.populateGraph("file.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}

	@Test
	public final void sameWordsNotAdjacent() {
		assertTrue("dog should not be adjacent with itself", !WordProcessor.isAdjacent("dog", "dog"));
		assertTrue("alligator should not be adjacent with itself", !WordProcessor.isAdjacent("alligator", "alligator"));
		assertTrue("smith should not be adjacent with itself", !WordProcessor.isAdjacent("smith", "smith"));
		assertTrue("a should not be adjacent with itself", !WordProcessor.isAdjacent("a", "a"));
	}

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

	@Test
	public final void adjacentWithNulls() {
		assertTrue("null adjacency should return false", !WordProcessor.isAdjacent(null, null));
		assertTrue("null adjacency should return false", !WordProcessor.isAdjacent("", null));
		assertTrue("null adjacency should return false", !WordProcessor.isAdjacent(null, ""));
	}
}
