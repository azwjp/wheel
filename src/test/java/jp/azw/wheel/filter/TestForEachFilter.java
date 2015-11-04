package jp.azw.wheel.filter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import jp.azw.wheel.filter.AndFilter;
import jp.azw.wheel.filter.EqFilter;
import jp.azw.wheel.filter.Filter;
import jp.azw.wheel.filter.ImpFilter;
import jp.azw.wheel.filter.NandFilter;
import jp.azw.wheel.filter.NorFilter;
import jp.azw.wheel.filter.OrFilter;
import jp.azw.wheel.filter.XorFilter;

public class TestForEachFilter {
	private List<Predicate<? super Integer>> isMultipleOf;
	
	@Before
	public void setUp() {
		isMultipleOf = new LinkedList<>();
		isMultipleOf.add(i -> i % 2 == 0);
		isMultipleOf.add(i -> i % 3 == 0);
		isMultipleOf.add(i -> i % 5 == 0);
	}
	
	@Test
	public void testAndFilter() {
		Filter<Integer> filter = new AndFilter<Integer>(true).addAllFilters(isMultipleOf);
		IntStream.rangeClosed(1, 30).parallel().forEach(i -> assertThat(filter.test(i), is(i % 2 == 0 && i % 3 == 0 && i % 5 == 0)));

		Filter<Integer> empty = new AndFilter<Integer>(true);
		assertThat(empty.test(0), is(true));
	}

	@Test
	public void testEqFilter() {
		Filter<Integer> filter = new EqFilter<Integer>(true).addAllFilters(isMultipleOf);
		IntStream.rangeClosed(1, 30).parallel().forEach(i -> assertThat(filter.test(i), is(i % 2 != 0 && i % 3 != 0 && i % 5 != 0 || i == 30)));
		
		Filter<Integer> empty = new EqFilter<Integer>(true);
		assertThat(empty.test(0), is(true));
	}
	
	@Test
	public void testImpFilter() {
		/*
		 * P -> Q
		 * iff not P or Q
		 * (P -> Q) -> R
		 * iff (not P or Q) -> R
		 * iff not (not P or Q) or R
		 * iff (P and not Q) or R
		 */
		Filter<Integer> filter = new ImpFilter<Integer>().addAllFilters(isMultipleOf);
		IntStream.rangeClosed(1, 30).parallel().forEach(i -> assertThat(filter.test(i), is(i % 2 == 0 && i % 3 != 0 || i % 5 == 0)));

		Filter<Integer> empty = new ImpFilter<Integer>();
		assertThat(empty.test(0), is(true));
	}

	@Test
	public void testNandFilter() {
		Filter<Integer> filter = new NandFilter<Integer>(true).addAllFilters(isMultipleOf);
		IntStream.rangeClosed(1, 30).parallel().forEach(i -> assertThat(filter.test(i), not(i % 2 == 0 && i % 3 == 0 && i % 5 == 0)));

		Filter<Integer> empty = new NandFilter<Integer>();
		assertThat(empty.test(0), is(true));
	}
	
	@Test
	public void testNorFilter() {
		Filter<Integer> filter = new NorFilter<Integer>(true).addAllFilters(isMultipleOf);
		IntStream.rangeClosed(1, 30).parallel().forEach(i -> assertThat(filter.test(i), not(i % 2 == 0 || i % 3 == 0 || i % 5 == 0)));

		Filter<Integer> empty = new NorFilter<Integer>();
		assertThat(empty.test(0), is(true));
	}

	@Test
	public void testOrFilter() {
		Filter<Integer> filter = new OrFilter<Integer>(true).addAllFilters(isMultipleOf);
		IntStream.rangeClosed(1, 30).parallel().forEach(i -> assertThat(filter.test(i), is(i % 2 == 0 || i % 3 == 0 || i % 5 == 0)));

		Filter<Integer> empty = new OrFilter<Integer>();
		assertThat(empty.test(0), is(true));
	}
	
	@Test
	public void testXorFilter() {
		Filter<Integer> filter = new XorFilter<Integer>(true).addAllFilters(isMultipleOf);
		IntStream.rangeClosed(1, 30).parallel().forEach(i -> assertThat(filter.test(i), is(((i % 2 == 0 ? 1 : 0) ^ (i % 3 == 0 ? 1 : 0) ^ (i % 5 == 0 ? 1 : 0)) == 1)));

		Filter<Integer> empty = new XorFilter<Integer>();
		assertThat(empty.test(0), is(true));
	}
}
