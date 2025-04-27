package www.twilight.advanced.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariTest {
    @Test
    public void testHardCodeHikari() throws SQLException {
        // 创建连接池对象
        HikariDataSource hikariDataSource=new HikariDataSource();

        // 设置连接池的配置信息（必须/非必须）
        // 必须
        hikariDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/atguigu"); // 注意这里和Druid不一样
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("YUyue0702");
        //非必须
        hikariDataSource.setMinimumIdle(10);
        hikariDataSource.setMaximumPoolSize(20);

        // 通过连接池获取连接对象
        Connection connection = hikariDataSource.getConnection(); // 因为都是实现了java官方的接口，所以用的方法都是一样的
        System.out.println(connection);

        // 回收连接
        connection.close();
    }

    @Test
    public void testResourceHikari() throws Exception {
        // 创建properties集合
        Properties properties=new Properties();

        // 读取配置文件，获得输入流，加载到properties集合中
        InputStream inputStream=HikariTest.class.getClassLoader().getResourceAsStream("hikari.properties");
        properties.load(inputStream);

        // 创建Hikari连接池配置对象，将Properties集合传进去
        HikariConfig hikariConfig=new HikariConfig(properties);

        // 基于Hikari配置对象，构建连接池
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        // 获取连接
        Connection connection = hikariDataSource.getConnection();
        System.out.println(connection);

        // 回收连接
        connection.close();
    }
}
