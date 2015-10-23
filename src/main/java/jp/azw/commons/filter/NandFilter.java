package jp.azw.commons.filter;

public class NandFilter<T> extends AndFilter<T> {

	public NandFilter() {
		super();
	}

	public NandFilter(boolean isParallel) {
		super(isParallel);
	}

	@Override
	public boolean test(T t) {
		return isEmpty() || !super.test(t);
	}
}
