package www.Twilight.day1;

public interface ServiceInterface {
    public void doSomething();

    @LogExecutionTime
    void work() throws InterruptedException;
}
