import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("boxing")
public class LogicTest {
	
	@Test
	public void testHiLow() {
		Competitor comp1 = new Competitor("Timothy", "AOSDA");
		/*comp1.addResult(18.0, 2);
		comp1.addResult(15.0, 1);
		comp1.addResult(17.0, 2);
		comp1.addResult(14.0, 3);
		comp1.addResult(19.0, 1);*/
		assertEquals(50.0, comp1.getHiLowPoints(), .001);	
		assertEquals(5.0, comp1.getHiLowRanks(), .001);
		assertEquals(17.0, comp1.getDblHiLowPoints(), .001);
		assertEquals(2.0, comp1.getDblHiLowRanks(), .001);
	}

}
