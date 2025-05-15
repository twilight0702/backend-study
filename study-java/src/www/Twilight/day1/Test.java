package www.Twilight.day1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) throws Exception{
//        Person person=new Person("Alice",20); //自动装箱
//        Class<? extends Person> clazz=person.getClass();
//        Constructor<? extends Person> constructor = clazz.getConstructor(String.class,Integer.class);
//
//        Person person1 = constructor.newInstance("Bob",30);
//
//        System.out.println(person1);

//
//        Class<? extends Person> clazz=person.getClass();
//        Field name = clazz.getDeclaredField("name");
//        name.setAccessible(true);
//        String getName=(String) name.get(person);
//        System.out.println(getName);

        Class<?> person = Class.forName("www.Twilight.day1.Person");
        Constructor<?> constructor = person.getConstructor(String.class, Integer.class);
        Object o = constructor.newInstance("123", 20);
        System.out.println(o);
    }
}
