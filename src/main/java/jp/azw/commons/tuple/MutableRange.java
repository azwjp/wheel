package jp.azw.commons.tuple;

import java.util.Optional;

public class MutableRange<T extends Comparable<T>> implements Range<T> {
	private Optional<T> min;
	private Optional<T> max;

	protected MutableRange(T first, T second) {
		this.min = Optional.ofNullable(first);
		this.max = Optional.ofNullable(second);
		order();
	}
	
	private void order() {
		if (state() == PairState.BOTH && min.get().compareTo(max.get()) >= 0) {
			Optional<T> tmp = min;
			min = max;
			max = tmp;
		}
	}

	synchronized public MutableRange<T> changeMax(T max) {
		this.max = Optional.ofNullable(max);
		order();
		return this;
	}
	
	synchronized public MutableRange<T> changeMin(T min) {
		this.min = Optional.ofNullable(min);
		order();
		return this;
	}

	public static <T extends Comparable<T>> MutableRange<T> between(T min, T max) {
		return new MutableRange<T>(min, max);
	}
	
	public static <T extends Comparable<T>> MutableRange<T> beginAt(T min) {
		return new MutableRange<T>(min, null);
	}
	
	public static <T extends Comparable<T>> MutableRange<T> endWith(T max) {
		return new MutableRange<T>(null, max);
	}

	public static <T extends Comparable<T>> MutableRange<T> empty() {
		return new MutableRange<T>(null, null);
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
