package jp.azw.commons.tuple;

import java.util.Optional;

public class ImmutableRange<T extends Comparable<T>> implements Range<T> {
	protected Optional<T> min;
	protected Optional<T> max;

	protected ImmutableRange(T first, T second) {
		this.min = Optional.ofNullable(first);
		this.max = Optional.ofNullable(second);
		order();
	}
	
	protected void order() {
		if (state() == PairState.BOTH && min.get().compareTo(max.get()) >= 0) {
			Optional<T> tmp = min;
			min = max;
			max = tmp;
		}
	}
	
	@Override
	synchronized public T getMax() {
		order();
		return max.orElse(null);
	}

	@Override
	synchronized public T getMin() {
		order();
		return min.orElse(null);
	}

	@Override
	synchronized public boolean includes(T object) {
		if (state() != PairState.BOTH) {
			throw new IllegalStateException("Both variables should have any value.");
		}
		return getMin().compareTo(object) <= 0 && getMax().compareTo(object) >= 0;
	}

	@Override
	public T getFirst() {
		return getMin();
	}

	@Override
	public T getSecond() {
		return getMax();
	}

	@Override
	synchronized public boolean equals(Object obj) {
		if (obj instanceof Range) {
			Range<?> range = (Range<?>) obj;
			return getMin().equals(range.getMin()) && getMax().equals(range.getMax());
		}

		return false;
	}

	@Override
	public PairState state() {
		return PairState.optionalState(min, max);
	}
}