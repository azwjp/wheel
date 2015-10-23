package jp.azw.commons.function;

@FunctionalInterface
public interface ThrowableConsumer<T, E extends Throwable>{
	void accept(T t) throws E;
	
	default ThrowableConsumer<T, E> andThen(ThrowableConsumer<? super T, E> after) throws E {
        return after == null ? t -> accept(t) : t  -> { accept(t); after.accept(t); };
    }
}
