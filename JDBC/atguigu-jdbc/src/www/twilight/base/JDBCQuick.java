package www.twilight.base;

import com.mysql.cj.jdbc.Driver;

import javax.swing.text.html.StyleSheet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCQuick {
    public static void main(String[] args) throws Exception{
        // 注册驱动
        //Class.forName("com.mysql.cj.jdbc.Driver");

        DriverManager.registerDriver(new Driver());

        // 获取连接对象
        String url = "jdbc:mysql://127.0.0.1:3306/atguigu"; // 指明mysql服务运行的地址、端口号、数据库名，最前面的部分是固定的
        String user = "root"; // 用户名
        String password = "YUyue0702"; // 密码
        Connection connection = DriverManager.getConnection(url,user,password);

        // 获取执行SQL语句的对象，就是把sql语句发送给mysql数据库的对象
        Statement statement = connection.createStatement();

        // 编写sql语句，执行，接收返回的结果集
        String sql="SELECT * FROM t_emp;";
        ResultSet resultSet = statement.executeQuery(sql); // 获取结果集合（结果是虚拟表），封装到ResultSet中

        // 处理结果，遍历结果集
        while(resultSet.next()){
            // 可以用列名获取，也可以用索引id，推荐用列名
            int empId=resultSet.getInt("emp_id");
            String empName=resultSet.getString("emp_name");
            double empSalary=resultSet.getDouble("emp_salary");
            int empAge=resultSet.getInt("emp_age");

            System.out.println(empId+"\t"+empName+"\t"+empSalary+"\t"+empAge);
        }

        // 释放资源（先开后关）
        resultSet.close();
        statement.close();
        connection.close();
    }
}
