package www.twilight.advanced.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DruidTest {
    @Test
    public void testHardCodeDruid() throws SQLException {
        // 创建连接池对象
        DruidDataSource druidDataSource = new DruidDataSource();

        // 设置连接池的配置信息（必须/非必须）
        // 必须的
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/atguigu");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("YUyue0702");
        //非必须的
        druidDataSource.setInitialSize(10); // 初始创建的连接数量
        druidDataSource.setMaxActive(20); // 最大连接数量

        // 通过连接池获取连接对象（这个是通用的，和用什么连接池无关）
        DruidPooledConnection connection = druidDataSource.getConnection(); // 实际也可以直接用Connection接收
        System.out.println(connection); // 只要有输出就是有连接

        // 基于connection进行CRUD

        // 回收连接
        connection.close();
    }

    @Test
    public void testResourcesDruid() throws Exception {
        // 创建Properties集合，用于存储外部配置文件的key和value值
        Properties properties = new Properties();

        // 读取外部配置文件，获取输入流，加载到properties集合中
        InputStream inputStream = DruidTest.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(inputStream);

        // 基于properties集合获取连接池对象
        // 注意要选择com.alibaba.druid.pool的DruidDataSourceFactory，createDataSource选哪个都可以
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

        // 获取连接
        Connection connection = ((DruidDataSource) dataSource).getConnection();
        System.out.println(connection);

        // CRUD

        // 回收连接
        connection.close();
    }
}
