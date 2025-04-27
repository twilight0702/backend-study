package www.twilight.senior.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * JDBC工具类
 * 1. 维护一个连接池对象
 * 2. 提供获取连接的方法
 * 3. 提供回收链接的方法
 *
 * 注意：工具类仅对外提供共性的功能代码，所有方法都是静态方法
 */
public class JDBCUtil {
    //创建连接池引用，因为提供给当前项目的全局使用，所以创建为静态的
    private  static final DataSource dataSource;

    static{
        try {
            Properties properties = new Properties();
            InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);

            dataSource = DruidDataSourceFactory.createDataSource(properties);

            if (inputStream != null) {//加一个关闭流的操作，比较合规
                inputStream.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void release(Connection connection){
        JdbcUtils.close(connection);
    }
}
