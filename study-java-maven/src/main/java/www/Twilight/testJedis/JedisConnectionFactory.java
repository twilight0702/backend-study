package www.Twilight.testJedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisConnectionFactory {
    private static final JedisPool jedisPool;

    static{
        //配置连接池
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);//最大连接数
        poolConfig.setMaxIdle(8);//最大空闲连接数
        poolConfig.setMinIdle(2);//最小空闲连接数
        poolConfig.setMaxWaitMillis(1000);//最大等待时间

        jedisPool=new JedisPool(poolConfig, "127.0.0.1", 6379);
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
