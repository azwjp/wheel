package jp.azw.commons.filter;

public class OrFilter<T> extends Filter<T> {
	
	public OrFilter() {
		super();
	}
	
	public OrFilter(boolean isParallel) {
		super(isParallel);
	}
	
	@Override
	public boolean test (T t) {
		return isEmpty() || getFilterStream().anyMatch(filter -> filter.test(t));
	}
}
