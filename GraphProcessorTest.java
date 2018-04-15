import java.io.IOException;
import java.util.ArrayList;
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
}
