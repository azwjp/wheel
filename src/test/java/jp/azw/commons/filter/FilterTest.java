package jp.azw.commons.filter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class FilterTest {
	List<Predicate<? super Integer>> filter0to10 = new LinkedList<>();
	List<Predicate<? super Integer>> filterto0from10 = new LinkedList<>();

	List<Predicate<? super Integer>> isMultipleOf = new LinkedList<>();
	
	@Before
	public void setup() {
		{
			Predicate<Number> pre = n -> n.intValue() < 10;
			filter0to10.add(i -> 0 < i);
			filter0to10.add(pre);
			filter0to10.add(null);
		}

		{
			Predicate<Number> pre = n -> 10 < n.intValue();
			filterto0from10.add(i -> i < 0);
			filterto0from10.add(pre);
			filterto0from10.add(null);
		}
		
		{
			isMultipleOf.add(i -> i % 2 == 0);
			isMultipleOf.add(i -> i % 3 == 0);
			isMultipleOf.add(i -> i % 5 == 0);
		}
		
		{
			Filter<Integer> f = new AndFilter<>();
			f.addFilter(new EqFilter<>()).addFilter(new OrFilter<>());
		}
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
