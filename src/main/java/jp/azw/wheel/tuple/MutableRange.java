package jp.azw.wheel.tuple;

import java.util.Optional;

public class MutableRange<T extends Comparable<T>> extends ImmutableRange<T> {

	protected MutableRange(T first, T second) {
		super(first, second);
	}

	synchronized public MutableRange<T> setMax(T max) {
		this.max = Optional.ofNullable(max);
		order();
		return this;
	}
	
	synchronized public MutableRange<T> setMin(T min) {
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
}
