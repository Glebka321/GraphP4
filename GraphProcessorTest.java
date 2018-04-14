import java.io.IOException;

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
}
