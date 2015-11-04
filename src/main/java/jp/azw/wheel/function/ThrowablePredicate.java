package jp.azw.wheel.function;

@FunctionalInterface
public interface ThrowablePredicate<T, E extends Throwable> {
	boolean test(T t) throws E;

	default ThrowablePredicate<T, E> and (ThrowablePredicate<? super T, E> other) throws E {
		return other == null ? t -> test(t) : (t -> test(t) && other.test(t));
	}
	
	default ThrowablePredicate<T, E> negate () throws E {
		return t -> !test(t);
	}
	
	default ThrowablePredicate<T, E> or (ThrowablePredicate<? super T, E> other) throws E {
		return other == null ? t -> test(t) : (t -> test(t) || other.test(t));
	}
}
