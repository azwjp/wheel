package jp.azw.commons.tuple;

public class ImmutableRangeTest extends RangeTest {
	@Override
	public void setUp() {
		range1 = ImmutableRange.between(min, max);
		range2 = ImmutableRange.between(max, min);
		rangeBegin = ImmutableRange.beginAt(min);
		rangeEnd = ImmutableRange.endWith(max);
		rangeEmpty = ImmutableRange.empty();
	}
}
