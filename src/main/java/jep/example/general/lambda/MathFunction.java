package jep.example.general.lambda;


@FunctionalInterface
public interface MathFunction<T extends Number> {

    public T apply(T x1, T x2);
    
}
