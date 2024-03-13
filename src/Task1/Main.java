package Task1;
/**
 * Создать аннотацию, которая принимает параметры для метода. Написать код, который
 * вызовет метод, помеченный этой аннотацией, и передаст параметры аннотации в
 * вызываемый метод.
 */

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Inherited
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@interface FirstAnnotation {
    int first();
    int second();
}





public class Main {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> cls = Main.class;
        Method method = cls.getDeclaredMethod("getSum", int.class, int.class);
        if (method.isAnnotationPresent(FirstAnnotation.class)) {
            FirstAnnotation fa = method.getAnnotation(FirstAnnotation.class);
            System.out.println((Integer) method.invoke(null, fa.first(), fa.second()));
        }

    }

    @FirstAnnotation(first = 12, second = 15)
    public static int getSum(int a, int b) {
        return a + b;
    }
}