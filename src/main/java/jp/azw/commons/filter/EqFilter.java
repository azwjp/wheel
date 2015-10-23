package jp.azw.commons.filter;

public class EqFilter<T> extends Filter<T> {

	public EqFilter() {
		super();
	}

	public EqFilter(boolean isParallel) {
		super(isParallel);
	}

	@Override
	public boolean test(T t) {
		long trues = getFilterStream().filter(filter -> filter.test(t)).count();
		return trues == 0 || trues == size();
	}

}
