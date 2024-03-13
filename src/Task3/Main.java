package Task3;

import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.Field;

@Inherited
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@interface Save {
}
public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Test obj = new Test(10, "Hello", 3.14);
        try {
            saveObj(obj, "text.txt");

            Test showField = (Test) showObj(Test.class, "serialized_object.txt");
            System.out.println(showField);
        } catch (IOException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void saveObj (Object obj, String filename) throws IllegalAccessException, IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Save.class)) {
                field.setAccessible(true);
                oos.writeObject(field.get(obj));
            }
        }
        oos.close();
        fos.close();
    }

    public static Object showObj(Class<?> cls, String filename) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = cls.newInstance();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Save.class)) {
                field.setAccessible(true);
                field.set(obj, ois.readObject());
            }
        }
        ois.close();
        fis.close();
        return obj;
    }
}

class Test implements Serializable{
    @Save
    private int intValue;
    @Save
    private String stringValue;

    private double doubleValue;

    public Test(int intValue, String stringValue, double doubleValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.doubleValue = doubleValue;
    }

    public Test() {
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "intValue=" + intValue +
                ", stringValue='" + stringValue + '\'' +
                ", doubleValue=" + doubleValue +
                '}';
    }
}

