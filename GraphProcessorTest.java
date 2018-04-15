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
	public final void Test1() throws Exception {
	      gp.populateGraph("file.txt");
	      gp.shortestPathPrecomputation();
	      for(String s : gp.getShortestPath("RAPINE", "PATTIES")) System.out.println(s);
	      System.out.println(gp.getShortestDistance("RAPINE", "PATTIES"));
	      for(String s : gp.getShortestPath("RAPINE", "GIBLETS")) System.out.println(s);
	      System.out.println(gp.getShortestDistance("RAPINE", "GIBLETS"));
	}
	@Test 
        public final void testPopulateGraph() throws IOException {
              Integer count = 6;
              gp.clearGraph();
              assertEquals(gp.populateGraph("filegraph.txt"), count);
        }
    
        @Test
        public final void testGetShortestPath() throws IOException {
              List<String> list = Stream.of("cat", "hat", "heat", "wheat").collect(Collectors.toList());
              String word1 = "cat";
              String word2 = "wheat";
              gp.clearGraph();
              gp.populateGraph("filegraph.txt");
              assertEquals(list, gp.getShortestPath(word1, word2));
        }
    
       @Test 
       public final void testGetShortestDistance() throws IOException {
              Integer distance = 3;
              String word1 = "cat";
              String word2 = "wheat";
              gp.clearGraph();
              gp.populateGraph("filegraph.txt");
              assertEquals(gp.getShortestDistance(word1, word2), distance);
       }
}
