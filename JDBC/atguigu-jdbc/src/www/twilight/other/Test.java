package www.twilight.other;

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



