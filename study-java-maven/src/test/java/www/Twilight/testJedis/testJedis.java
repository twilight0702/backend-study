package www.Twilight.testJedis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class testJedis {
    private Jedis jedis;
    @BeforeEach
    public void SetUp(){
        jedis=JedisConnectionFactory.getJedis();
        System.out.println("连接成功");
    }


    @Test
    public void testJedis1(){
        System.out.println(jedis.ping());
        Set<String> keys = jedis.keys("*");
        for(String key:keys){
            System.out.println(key);
        }
    }

    @Test
    public void testHash(){
        jedis.hset("user:1","name","twilight");
        jedis.hset("user:1","age","18");
        System.out.println(jedis.hgetAll("user:1"));
    }

    @AfterEach
    public void tearDown(){
        if(jedis!=null){
            jedis.close();
        }
        System.out.println("关闭成功");
    }

}
