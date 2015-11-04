package jp.azw.commons.tuple;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(value = JUnit4.class)
public abstract class RangeTest {
	protected static final int TEST_BEGIN = 0;
	protected static final int TEST_END = 30;

	protected static final int BEGIN_2 = 10;
	protected static final int END_2 = 15;
	protected static final int BEGIN = 20;
	protected static final int BEGIN_3 = 22;
	protected static final int END = 25;
	protected static final int END_3 = 30;

	protected Int min = new Int(BEGIN);
	protected Int max = new Int(END);

	protected Range<Int> range1, range2, rangeBegin, rangeEnd, rangeEmpty;

	@Before
	abstract public void setUp();

	@Test
	public void testGetMinMax() {
		assertThat(min, is(allOf(sameInstance(range1.getMin()), sameInstance(range2.getMin()))));
		assertThat(max, is(allOf(sameInstance(range1.getMax()), sameInstance(range2.getMax()))));

		max.set(BEGIN_2);
		min.set(END_2);

		assertThat(min, is(allOf(sameInstance(range1.getMax()), sameInstance(range2.getMax()))));
		assertThat(max, is(allOf(sameInstance(range1.getMin()), sameInstance(range2.getMin()))));

		assertThat(null, allOf(is(rangeBegin.getMax()), is(rangeEnd.getMin()), is(rangeEmpty.getMax()),
				is(rangeEmpty.getMin())));
	}

	@Test
	public void testInclude() {
		IntStream.rangeClosed(TEST_BEGIN, TEST_END).parallel().forEach(i -> {
			assertThat("error at " + i, range1.includes(new Int(i)), is(BEGIN <= i && i <= END));
			assertThat("error at " + i, rangeBegin.includes(new Int(i)), is(BEGIN <= i));
			assertThat("error at " + i, rangeEnd.includes(new Int(i)), is(i <= END));
		});

		min.set(END_2);
		max.set(BEGIN_2);

		IntStream.rangeClosed(TEST_BEGIN, TEST_END).parallel().forEach(i -> {
			assertThat("error at " + i, range1.includes(new Int(i)), is(BEGIN_2 <= i && i <= END_2));
			assertThat("error at " + i, rangeBegin.includes(new Int(i)), is(END_2 <= i));
			assertThat("error at " + i, rangeEnd.includes(new Int(i)), is(i <= BEGIN_2));
		});

		boolean thrown = false;
		try {
			rangeEmpty.includes(new Int(0));
			fail();
		} catch (IllegalStateException e) {
			thrown = true;
		}
		assertThat("Exception should be thrown.", thrown, is(true));
	}
	
	@Test
	public void testIncludeOpened() {
		IntStream.rangeClosed(TEST_BEGIN, TEST_END).parallel().forEach(i -> {
			assertThat("error at " + i, range1.includesOpened(new Int(i)), is(BEGIN < i && i < END));
			assertThat("error at " + i, rangeBegin.includesOpened(new Int(i)), is(BEGIN < i));
			assertThat("error at " + i, rangeEnd.includesOpened(new Int(i)), is(i < END));
		});

		min.set(END_2);
		max.set(BEGIN_2);

		IntStream.rangeClosed(TEST_BEGIN, TEST_END).parallel().forEach(i -> {
			assertThat("error at " + i, range1.includesOpened(new Int(i)), is(BEGIN_2 < i && i < END_2));
			assertThat("error at " + i, rangeBegin.includesOpened(new Int(i)), is(END_2 < i));
			assertThat("error at " + i, rangeEnd.includesOpened(new Int(i)), is(i < BEGIN_2));
		});

		boolean thrown = false;
		try {
			rangeEmpty.includesOpened(new Int(0));
			fail();
		} catch (IllegalStateException e) {
			thrown = true;
		}
		assertThat("Exception should be thrown.", thrown, is(true));
	}

	protected class Int implements Comparable<Int> {
		private Integer i;

		public Int(int i) {
			this.i = i;
		}

		public Int set(int i) {
			this.i = i;
			return this;
		}

		public int get() {
			return i;
		}

		@Override
		public int compareTo(Int o) {
			return i.compareTo(o.get());
		}

	}
}
