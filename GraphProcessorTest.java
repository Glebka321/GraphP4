import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphProcessorTest {
	private GraphProcessor gp;
	@BeforeClass
	public static void setUpBeforeClass() {
		
	}

	@AfterClass
	public static void tearDownAfterClass() {

	}

	@Before
	public void setUp() {
		gp = new GraphProcessor();
	}

	@After
	public void tearDown() {
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
		gp.populateGraph("word_list.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}

	@Test
	public final void interestingCombosTwo() throws IOException {
		Integer distance = 2;
		String word1 = "bellies";
		String word2 = "jollies";
		gp.populateGraph("word_list.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}

	@Test
	public final void interestingCombosThree() throws IOException {
		Integer distance = 26;
		String word1 = "define";
		String word2 = "shinny";
		gp.populateGraph("word_list.txt");
		gp.shortestPathPrecomputation();
		assertEquals(gp.getShortestDistance(word1, word2), distance);
	}
}
