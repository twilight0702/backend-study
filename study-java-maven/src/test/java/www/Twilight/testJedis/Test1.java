package www.Twilight.testJedis;

import org.junit.jupiter.api.Test;

public class Test1 {
    @Test
    public void test1() {
        Integer i1=null;
        test2(i1);
        System.out.println(i1);
    }

    private void test2(Integer i){
        i=10;
    }
}
