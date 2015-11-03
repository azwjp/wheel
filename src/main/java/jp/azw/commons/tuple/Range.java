package jp.azw.commons.tuple;

public interface Range<T extends Comparable<T>> {
	public T getMax();

	public T getMin();

	public boolean includes(T object);

	public boolean includesOpened(T object);

	public T getFirst();

	public T getSecond();

	public boolean equals(Object obj);

	public PairState state();
}