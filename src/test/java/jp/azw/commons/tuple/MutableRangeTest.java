/**
 * 
 */
package jp.azw.commons.tuple;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Akane Sayama
 *
 */
public class MutableRangeTest extends RangeTest {

	@Before
	public void setUp() {
		range1 = MutableRange.between(min, max);
		range2 = MutableRange.between(max, min);
		rangeBegin = MutableRange.beginAt(min);
		rangeEnd = MutableRange.endWith(max);
		rangeEmpty = MutableRange.empty();
	}

	/**
	 * Test method for
	 * {@link jp.azw.commons.tuple.MutableRange#setMax(java.lang.Comparable)},
	 * {@link jp.azw.commons.tuple.MutableRange#setMin(java.lang.Comparable)}.
	 */
	@Test
	public void testSetMinMax() {
		assertThat(((MutableRange<Int>) range1).setMax(new Int(END_3)).getMax().get(), is(END_3));
		assertThat(((MutableRange<Int>) range1).setMax(new Int(END_2)).getMin().get(), is(END_2));

		((MutableRange<Int>) rangeBegin).setMin(new Int(BEGIN_3));
		assertThat(rangeBegin.getMin().get(), is(BEGIN_3));
		((MutableRange<Int>) rangeBegin).setMax(new Int(END_3));
		assertThat(rangeBegin.getMax().get(), is(END_3));

		((MutableRange<Int>) rangeEnd).setMax(new Int(END_3));
		assertThat(rangeEnd.getMax().get(), is(END_3));
		((MutableRange<Int>) rangeEnd).setMin(new Int(BEGIN_3));
		assertThat(rangeEnd.getMin().get(), is(BEGIN_3));
	}
}
