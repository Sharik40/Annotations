package Task2;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;

@Inherited
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@interface SaveTo {
    String path() default "D:\\textSaver.txt";
}

@Inherited
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@interface Saver {
}

@SaveTo //(path = "d:\\Download\\textSaver.txt")
class TextContainer {
    String str;

    TextContainer(String str) {
        this.str = str;
    }

    @Saver
    public void save(String path) throws IOException {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(str);
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
}
public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        TextContainer tc = new TextContainer("Hello");
        Class<?> cls = tc.getClass();
        SaveTo saveto = cls.getAnnotation(SaveTo.class);
        String path = saveto.path();
        Method[] methods = cls.getDeclaredMethods();
        for (Method method: methods) {
            if (method.isAnnotationPresent(Saver.class)) {
                method.invoke(tc, path);
            }
        }
    }
}
