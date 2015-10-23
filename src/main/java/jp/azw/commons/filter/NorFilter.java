package jp.azw.commons.filter;

public class NorFilter<T> extends OrFilter<T> {

	public NorFilter() {
		super();
	}
	
	public NorFilter(boolean isParallel) {
		super(isParallel);
	}
	
	@Override
	public boolean test(T t) {
		return isEmpty() || !super.test(t);
	}
}
