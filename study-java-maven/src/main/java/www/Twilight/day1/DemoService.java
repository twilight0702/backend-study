package www.Twilight.day1;

import static java.lang.Thread.sleep;

public class DemoService implements ServiceInterface{
    @Override
    @LogExecutionTime
    public void doSomething() {
        System.out.println("doSomething");
    }

    @LogExecutionTime
    @Override
    public void work() throws InterruptedException {
        sleep(1000);
        System.out.println("work over");
    }
}
