package jp.azw.commons.filter;

public class AndFilter<T> extends Filter<T> {

	public AndFilter() {
		super();
	}

	public AndFilter(boolean isParallel) {
		super(isParallel);
	}

	@Override
	public boolean test(T t) {
		return getFilterStream().allMatch(filter -> filter.test(t));
	}
}
