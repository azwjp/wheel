package jp.azw.wheel.filter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class Filter<T> implements Predicate<T> {
	private List<Predicate<? super T>> filters = new LinkedList<>();
	private boolean isParallel = false;

	public Filter() {
	}

	public Filter(boolean isParallel) {
		this.isParallel = isParallel;
	}

	public Filter<T> addFilter(Predicate<? super T> filter) {
		if (filter != null) {
			filters.add(filter);
		}
		return this;
	}

	public Filter<T> addAllFilters(List<Predicate<? super T>> filters) {
		filters.stream().forEach(filter -> addFilter(filter));
		return this;
	}

	protected List<Predicate<? super T>> getFilters() {
		return filters;
	}

	protected Stream<Predicate<? super T>> getFilterStream() {
		return (isParallel ? filters.parallelStream() : filters.stream());
	}

	public int size() {
		return filters.size();
	}
	
	public boolean isEmpty() {
		return filters.isEmpty();
	}
	
	public boolean hasFilter() {
		return !filters.isEmpty();
	}
}
