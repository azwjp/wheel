package jp.azw.commons.tuple;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Calendar;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(value = JUnit4.class)
public abstract class RangeTest {
	static final int MAX = 15;
	static final int MIN = 10;

	static final int YEAR = 1994;
	static final int MONTH = 1;
	static final int DAY1 = 10;
	static final int DAY2 = 20;
	static final int DAY3 = 27;

	Calendar cal1 = Calendar.getInstance();
	Calendar cal2 = Calendar.getInstance();
	Calendar cal3 = Calendar.getInstance();

	Range<Calendar> range1, range2;
	
	@Before
	public void setUp() {
		cal1.set(YEAR, MONTH, DAY1, 0, 0, 0);
		cal2.set(YEAR, MONTH, DAY2, 0, 0, 0);
		cal3.set(YEAR, MONTH, DAY3, 0, 0, 0);
	}

	@Test
	public void testGetMinMax() {
		assertThat(cal1, is(allOf(sameInstance(range1.getMin()), sameInstance(range2.getMin()))));
		assertThat(cal3, is(allOf(sameInstance(range1.getMax()), sameInstance(range2.getMax()))));

		cal1.set(Calendar.YEAR, 2016);

		assertThat(cal1, is(allOf(sameInstance(range1.getMax()), sameInstance(range2.getMax()))));
		assertThat(cal3, is(allOf(sameInstance(range1.getMin()), sameInstance(range2.getMin()))));
	}
	
	@Test
	public void testInclude(){
		assertThat(true, allOf(is(range1.includes(cal2)), is(range2.includes(cal2))));
		cal1.set(Calendar.YEAR, 2016);
		assertThat(false, allOf(is(range1.includes(cal2)), is(range2.includes(cal2))));
	}

}
