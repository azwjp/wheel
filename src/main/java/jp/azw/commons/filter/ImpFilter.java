package jp.azw.commons.filter;

import java.util.Optional;
import java.util.function.Predicate;

public class ImpFilter<T> extends Filter<T> {

	public ImpFilter() {
		super();
	}

	@Override
	public boolean test(T t) {
		Optional<Boolean> state = Optional.empty(); 
		for (Predicate<? super T> filter : getFilters()) {
			if (state.isPresent()) {
				state = Optional.of(state.get() && !filter.test(t) ? false : true);
			} else {
				state = Optional.of(filter.test(t));
			}
		}
		return state.orElse(true);
	}

}
