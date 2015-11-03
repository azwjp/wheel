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
		if (state() == PairState.BOTH && min.get().compareTo(max.get()) > 0) {
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
		order();
		switch (state()) {
		case BOTH:
			return getMin().compareTo(object) <= 0 && getMax().compareTo(object) >= 0;
		case ONLY_FIRST:
			return getMin().compareTo(object) <= 0;
		case ONLY_SECOND:
			return getMax().compareTo(object) >= 0;
		default:
			throw new IllegalStateException("One or more variables should have any value.");
		}
	}
	
	@Override
	synchronized public boolean includesOpened(T object) {
		order();
		switch (state()) {
		case BOTH:
			return getMin().compareTo(object) < 0 && getMax().compareTo(object) > 0;
		case ONLY_FIRST:
			return getMin().compareTo(object) < 0;
		case ONLY_SECOND:
			return getMax().compareTo(object) > 0;
		default:
			throw new IllegalStateException("One or more variables should have any value.");
		}
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
	
	public static <T extends Comparable<T>> ImmutableRange<T> between(T min, T max) {
		return new ImmutableRange<T>(min, max);
	}
	
	public static <T extends Comparable<T>> ImmutableRange<T> beginAt(T min) {
		return new ImmutableRange<T>(min, null);
	}
	
	public static <T extends Comparable<T>> ImmutableRange<T> endWith(T max) {
		return new ImmutableRange<T>(null, max);
	}

	public static <T extends Comparable<T>> ImmutableRange<T> empty() {
		return new ImmutableRange<T>(null, null);
	}
}