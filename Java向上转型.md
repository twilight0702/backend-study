```java
public class Test {  
    public static void main(String[] args){  
        A a=new B();  
        a.f2();  
        a.f3();  
  
        B b=new B();  
        b.f2();  
        b.f3();  
  
        A aa=new A();  
        aa.f2();  
        aa.f3();  
    }  
}  
  
  
class A{  
    public void f1(){  
        System.out.println("A:f1");  
    }  
  
    public void f2(){  
        this.f1();  
    }  
  
    public void f3(){  
        f1();  
    }  
}  
  
class B extends A{  
    @Override  
    public void f1(){  
        System.out.println("B:f1");  
    }  
}
```
输出：
```bash
B:f1
B:f1
B:f1
B:f1
A:f1
A:f1
```

**向上转型发生的是：** 把子类对象当作父类类型来使用，但对象本身没变，只是**编译器限制了你能访问的方法范围**。