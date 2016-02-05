package jp.azw.wheel.filter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * The super class of the other filter classes.<br />
 * 
 * First of all, you should add filters.
 * {@link Predicate} (functional interface) is used as a filter.
 * <pre>
 * {@code
 * // true if (multiple of 2 AND multiple of 3)
 * // in other word, true if multiple of 6
 * Filter<Integer> filter = new AndFilter<Integer>()
 * 		.addFilter(i -> i % 2 == 0) // true if multiple of 2
 * 		.addFilter(i -> i % 3 == 0); // true if multiple of 6
 * }
 * </pre>
 * 
 * And then, call {@link #test(Object)} to use the filter.
 * <pre>
 * {@code
 * filter.test(2); // false
 * filter.test(6); // true
 * }
 * </pre>
 * 
 * You also can add another filter into filter.
 * <pre>
 * {@code
 * // 0 OR (multiple of 2 AND multiple of 3)
 * Filter<Integer> another = new OrFilter<Integer>()
 * 		.addFilter(filter) // true if multiple of 6
 * 		.addFilter(0); // true if 0
 * another.test(2); // false
 * another.test(6); // true
 * another.test(0); // true
 * }
 * </pre>
 * 
 * @author Akane Sayama
 *
 * @param <T>
 */
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
