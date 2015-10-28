package jp.azw.commons.tuple;

import java.util.Optional;

public enum PairState {
	BOTH, ONLY_FIRST, ONLY_SECOND, NONE;

	public static PairState optionalState(Optional<?> first, Optional<?> second) {
		if (first.isPresent() && second.isPresent()) {
			return BOTH;
		} else if (first.isPresent() && !second.isPresent()) {
			return ONLY_FIRST;
		} else if (second.isPresent()) {
			return ONLY_SECOND;
		} else {
			return NONE;
		}
	}
	
	public static <T, U> PairState nullState(T first, U second) {
		return optionalState(Optional.ofNullable(first), Optional.ofNullable(second));
	}
}
