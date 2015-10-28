package jp.azw.commons.tuple;

import java.util.Optional;

public class MutableRange<T extends Comparable<T>> implements Range<T> {
	private Optional<T> first;
	private Optional<T> second;

	protected MutableRange(T first, T second) {
		this.first = Optional.ofNullable(first);
		this.second = Optional.ofNullable(second);
	}

	private boolean isFirstMax() {
		throwIfStateIsntBoth();
		return first.get().compareTo(second.get()) >= 0;
	}

	public MutableRange<T> setFirst(T first) {
		this.first = Optional.ofNullable(first);
		return this;
	}
	public MutableRange<T> setSecond(T second) {
		this.second = Optional.ofNullable(second);
		return this;
	}

	public void setMax(T max) {
		switch (state()) {
		case BOTH:
			if (isFirstMax()) {
				setFirst(max);
			} else {
				setSecond(max);
			}
			break;
		case ONLY_FIRST:
			setSecond(max);
			break;
		case ONLY_SECOND:
			setFirst(max);
			break;
		case NONE:
			setFirst(max);
			break;
		}
	}
	
	public void setMin(T min) {
		switch (state()) {
		case BOTH:
			if (!isFirstMax()) {
				setFirst(min);
			} else {
				setSecond(min);
			}
			break;
		case ONLY_FIRST:
			setSecond(min);
			break;
		case ONLY_SECOND:
			setFirst(min);
			break;
		case NONE:
			setFirst(min);
			break;
		}
	}

	private void throwIfStateIsntBoth() {
		if (state() != PairState.BOTH) {
			throw new IllegalStateException("Both of variables should have value.");
		}
	}

	public static <T extends Comparable<T>> MutableRange<T> of(T first, T second) {
		return new MutableRange<T>(first, second);
	}

	public static <T extends Comparable<T>> MutableRange<T> empty() {
		return new MutableRange<T>(null, null);
	}

	@Override
	public T getMax() {
		switch (state()) {
		case BOTH:
			return isFirstMax() ? first.get() : second.get();
		case ONLY_FIRST:
			return first.get();
		case ONLY_SECOND:
			return second.get();
		default:
			return null;
		}
	}

	@Override
	public T getMin() {
		switch (state()) {
		case BOTH:
			return !isFirstMax() ? first.get() : second.get();
		case ONLY_FIRST:
			return first.get();
		case ONLY_SECOND:
			return second.get();
		default:
			return null;
		}
	}

	@Override
	public boolean includes(T object) {
		if (state() != PairState.BOTH) {
			throw new IllegalStateException("Both of variables should have value.");
		}
		return getMin().compareTo(object) <= 0 && getMax().compareTo(object) >= 0;
	}

	@Override
	public T getFirst() {
		return first.orElse(null);
	}

	@Override
	public T getSecond() {
		return second.orElse(null);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Range) {
			Range<?> range = (Range<?>) obj;
			return getMin().equals(range.getMin()) && getMax().equals(range.getMax());
		}

		return false;
	}

	@Override
	public PairState state() {
		return PairState.optionalState(first, second);
	}
}
