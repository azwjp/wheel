package jp.azw.wheel.filter;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import jp.azw.wheel.filter.Filter;

public class FilterTest {
	private Filter<Void> filter;
	@Before
	public void setUp() {
		filter = new EmptyFilterForTest();
	}
	
	@Test
	public void testFilter() {
		Filter<?> filter = new EmptyFilterForTest();
		assertThat(filter.getFilterStream().isParallel(), is(false));
	}

	@Test
	public void testFilterBoolean() {
		Filter<?> filter = new EmptyFilterForTest(false);
		assertThat(filter.getFilterStream().isParallel(), is(false));
		filter = new EmptyFilterForTest(true);
		assertThat(filter.getFilterStream().isParallel(), is(true));
	}

	@Test
	public void testAddFilter() {
		IntStream.range(0, 10).forEach(i -> {
			assertThat(filter.size(), is(i));
			filter.addFilter(v -> true);
		});
	}

	@Test
	public void testAddAllFilters() {
		final int size = 10;
		List<Predicate<? super Void>> list = new LinkedList<>();
		IntStream.range(0, size).forEach(i -> list.add(v -> true));
		filter.addAllFilters(list);
		assertThat(filter.size(), is(size));
	}

	@Test
	public void testIsEmpty() {
		IntStream.range(0, 2).forEach(i -> {
			assertThat(filter.isEmpty(), allOf(is(filter.size() == 0), not(filter.hasFilter())));
			filter.addFilter(v -> true);
		});
	}

	class EmptyFilterForTest extends Filter<Void> {
		public EmptyFilterForTest() {
			super();
		}

		public EmptyFilterForTest(boolean isParallel) {
			super(isParallel);
		}

		@Override
		public boolean test(Void t) {
			return true;
		}
	}
}
