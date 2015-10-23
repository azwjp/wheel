package jp.azw.commons.filter;

import java.util.function.Predicate;

public class FilterUtils {
	public static <T> Predicate<T> not(Predicate<T> p) {
		return t -> !p.test(t);
	}
	
	public static <T> Predicate<T> tautology() {
		return t -> true;
	}
}
