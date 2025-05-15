package www.Twilight.day1;

/**
 * 2025.5.13
 * 该类用于学习反射相关
 */
public class Person {
    private String name;
    private Integer age;

    public Person(String name,Integer age){
        System.out.println("public构造函数");
        this.name = name;
        this.age = age;
    }

    private Person(){
        System.out.println("private构造函数");
        this.name="123";
        this.age=123;
    }

    public String getName(){
        return name;
    }
    public Integer getAge(){
        return age;
    }

    public String toString(){
        return "Person [name="+name+", age="+age+"]";
    }

    private void privateMethod(){
        System.out.println("privateMethod");
    }
}

