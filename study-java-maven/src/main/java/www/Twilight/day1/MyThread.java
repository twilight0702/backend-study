package www.Twilight.day1;

public class MyThread implements Runnable{
    public static volatile int total=10;
    public static final Object lock=new Object();
    @Override
    public void run() {
        synchronized(lock){
            if(total>0){
                System.out.println(Thread.currentThread().getName()+"正在卖第"+total+"张票");
                total--;
            }
            else{
                System.out.println("票卖完了");
            }
        }
    }
}

class TestThread{
    public static void main(String[] args) {
        for(int i=0;i<20;i++){
            new Thread(new MyThread(),"Thread"+i).start();
        }
    }
}