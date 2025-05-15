import org.junit.jupiter.api.Test;
import www.Twilight.day1.*;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 2025.5.13
 * 用于测试Person类
 * 主要是学习测试反射和注解相关，还有maven的使用
 */
public class MainTest {
    @Test
    public void test1(){
        Person person=new Person("Alice",20);
        System.out.println(person);
    }

    @Test
    public void test2() throws Exception{
        Person person=new Person("Alice",20); //自动装箱
        Class<? extends Person> clazz=person.getClass();
        Constructor<? extends Person> constructor = clazz.getConstructor(String.class,Integer.class);

        Person person1 = constructor.newInstance("Bob",30);

        System.out.println(person1);
    }

    @Test
    public void test3() throws Exception{
        Person person=new Person("Alice",20); //自动装箱

        Class<? extends Person> clazz=person.getClass();
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        String getName=(String) name.get(person);
        System.out.println("修改前："+getName);
        name.set(person,"Bob");
        System.out.println("修改后："+(String)name.get(person));
    }

    @Test
    public void test4() throws Exception {
        Class<?> person = Class.forName("www.Twilight.day1.Person");
        Constructor<?> constructor = person.getConstructor(String.class, Integer.class);
        Object o = constructor.newInstance("123", 20);
        System.out.println(o);
    }

    @Test
    public void test5() throws Exception {
        Class<?> aClass = Class.forName("www.Twilight.day1.Person");
        Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Object o = declaredConstructor.newInstance();
        System.out.println(o);
    }

    @Test
    public void test6() throws Exception {
        Class<?> aClass = Class.forName("www.Twilight.day1.Person");
        Method privateMethod = aClass.getDeclaredMethod("privateMethod");
        privateMethod.setAccessible(true);

        Constructor<?> constructor = aClass.getConstructor(String.class, Integer.class);
        Object o = constructor.newInstance("Alice", 20);

        privateMethod.invoke(o);
    }

    @Test
    public void test7() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String s1=properties.getProperty("className");

        Class<?> aClass = Class.forName(s1);
        Constructor<?> constructor = aClass.getConstructor(String.class, Integer.class);
        Object o = constructor.newInstance("Alice", 20);
        System.out.println(o);
    }

    /**
     * 解析自定义注解
     * @throws Exception
     */
    @Test
    public void test8() throws Exception {
        Class<?> aClass = Class.forName("www.Twilight.day1.UseAnnotation");
        Method useMyAnnotation = aClass.getDeclaredMethod("useMyAnnotation");
        MyAnnotation annotation = useMyAnnotation.getAnnotation(MyAnnotation.class);
        System.out.println(annotation.value());
    }

    @Test
    public void test9() throws Exception {
        DemoService service = new DemoService();
        ServiceInterface proxy = LogProxy.createProxy(service);

        proxy.doSomething();
        proxy.work();
    }

    @Test
    public void test10() throws Exception {
       String a=new String("123123");
       String b=new String("123123");

       System.out.println(a==b);
       System.out.println(a.equals(b));
    }

    @Test
    public void test11() throws Exception {
        String s1 = "hello";
        String s2 = "hello";
        System.out.println(s1 == s2);      // ✅ true：来自字符串常量池（地址相同）
        System.out.println(s1.equals(s2)); // ✅ true：内容也一样
    }

    @Test
    public void test12() throws Exception {
        Map<Integer,Integer> map=new HashMap<>();

        map.put(1,1);

        System.out.println(map.get(2));
    }
}
