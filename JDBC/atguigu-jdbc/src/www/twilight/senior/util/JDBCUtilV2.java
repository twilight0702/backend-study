package www.twilight.senior.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *  JDBC工具类（V2.0）：
 *      1、维护一个连接池对象、维护了一个线程绑定变量的ThreadLocal对象
 *      2、对外提供在ThreadLocal中获取连接的方法
 *      3、对外提供回收连接的方法，回收过程中，将要回收的连接从ThreadLocal中移除！
 *  注意：工具类仅对外提供共性的功能代码，所以方法均为静态方法！
 *  注意：使用ThreadLocal就是为了一个线程在多次数据库操作过程中，使用的是同一个连接！
 */

public class JDBCUtilV2 {
    //创建连接池引用，因为提供给当前项目的全局使用，所以创建为静态的
    private  static final DataSource dataSource;
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

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
            connection = threadLocal.get();
            //证明ThreadLocal中没有存储，也就是第一次获取
            if (connection==null){
                //在连接池中获取一个连接，存入threadLocal
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //只要没有释放连接，当前线程的connection就在threadLocal中存储着
        return connection;
    }

    public static void release(){
        try{
            Connection connection = threadLocal.get();
            if (connection!=null){
                //将connection从threadLocal中移除
                threadLocal.remove();
                //将连接还给连接池
                //如果开启了手动提交，操作完毕后归还给连接池的时候需要修改回自动提交
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
