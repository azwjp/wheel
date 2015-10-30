package jp.azw.commons.tuple;

public class MutableRangeTest extends RangeTest {
	
	@Override
	public void setUp() {
		super.setUp();
		range1 = MutableRange.between(cal1, cal3);
		range2 = MutableRange.between(cal3, cal1);
	}
}
