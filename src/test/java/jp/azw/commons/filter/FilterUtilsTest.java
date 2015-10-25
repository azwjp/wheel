package jp.azw.commons.filter;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

public class FilterUtilsTest {
	Filter<Boolean> filter;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNot() {
		Predicate<Boolean> filter = FilterUtils.not(t -> t);
		assertThat(filter.test(true), is(false));
		assertThat(filter.test(false), is(true));
	}

	@Test
	public void testTautology() {
		Predicate<Boolean> filter = FilterUtils.tautology();
		assertThat(filter.test(true), is(true));
		assertThat(filter.test(false), is(true));
	}

}
