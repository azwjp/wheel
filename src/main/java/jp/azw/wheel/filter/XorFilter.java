package jp.azw.wheel.filter;

public class XorFilter<T> extends Filter<T> {

	public XorFilter() {
		super();
	}

	public XorFilter(boolean isParallel) {
		super(isParallel);
	}

	@Override
	public boolean test(T t) {
		return isEmpty() || getFilterStream().filter(p -> p.test(t)).count() % 2 == 1;
	}

}
