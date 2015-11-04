/**
 * 
 */
package jp.azw.commons.tuple;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

/**
 * @author Akane Sayama
 *
 */
public class PairStateTest {

	/**
	 * Test method for {@link jp.azw.commons.tuple.PairState#optionalState(java.util.Optional, java.util.Optional)}.
	 */
	@Test
	public void testOptionalState() {
		assertThat(PairState.optionalState(Optional.empty(), Optional.empty()), is(PairState.NONE));
		assertThat(PairState.optionalState(Optional.of(new Integer(0)), Optional.empty()), is(PairState.ONLY_FIRST));
		assertThat(PairState.optionalState(Optional.empty(), Optional.of(new Integer(0))), is(PairState.ONLY_SECOND));
		assertThat(PairState.optionalState(Optional.of(new Integer(0)), Optional.of(new Integer(0))), is(PairState.BOTH));
		
		boolean thrown = false;
		try {
			PairState.optionalState(null, null);
			fail();
		} catch (NullPointerException e) {
			thrown = true;
		}
		assertThat(thrown, is(true));
	}

	/**
	 * Test method for {@link jp.azw.commons.tuple.PairState#state(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public void testState() {
		assertThat(PairState.state(null, null), is(PairState.NONE));
		assertThat(PairState.state(0, null), is(PairState.ONLY_FIRST));
		assertThat(PairState.state(null, 0), is(PairState.ONLY_SECOND));
		assertThat(PairState.state(0, 0), is(PairState.BOTH));
	}

}
