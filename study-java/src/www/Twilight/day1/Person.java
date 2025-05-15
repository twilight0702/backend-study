package www.Twilight.day1;

public class Person {
    private String name;
    private Integer age;

    public Person(String name,Integer age){
        System.out.println("构造函数");
        this.name = name;
        this.age = age;
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
}
