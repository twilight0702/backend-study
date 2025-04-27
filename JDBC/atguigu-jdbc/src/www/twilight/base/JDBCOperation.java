package www.twilight.base;

import com.mysql.cj.conf.ConnectionUrl;
import org.junit.Test;

import java.sql.*;

public class JDBCOperation {

    @Test
    public void testQuerySingleRowAndCol() throws Exception{
        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "YUyue0702");

        PreparedStatement preparedStatement=connection.prepareStatement("SELECT COUNT(*) FROM t_emp");

        ResultSet resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){ // 即使只有一行，也需要这样遍历一遍，至少要判断一次if(resultSet.next())
            System.out.println(resultSet.getInt(1));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testQuerySingleRow() throws Exception{
        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "YUyue0702");

        PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM t_emp WHERE emp_id = ?");

        preparedStatement.setString(1,"5"); // 有占位符一定要赋值

        ResultSet resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){
            System.out.println(resultSet.getInt("emp_id"));
            System.out.println(resultSet.getString("emp_name"));
            System.out.println(resultSet.getDouble("emp_salary"));
            System.out.println(resultSet.getInt("emp_age"));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testInsert() throws Exception {
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "YUyue0702");

        PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO t_emp(emp_name,emp_salary,emp_age) VALUES (?,?,?)");

        preparedStatement.setString(1,"twi");
        preparedStatement.setDouble(2,5000);
        preparedStatement.setInt(3,20);

        int count=preparedStatement.executeUpdate(); // 注意方法不同了，返回值是一个整数，表示受影响的行数

        //根据受影响的行数，得到成功或者失败
        if(count>0){
            System.out.println("插入成功");
        }else{
            System.out.println("插入失败");
        }

        preparedStatement.close();
        connection.close();//这里只用关闭两个
    }

    @Test
    public void testUpdate() throws Exception {
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "YUyue0702");

        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE t_emp SET emp_salary = ? WHERE emp_name = ?");

        preparedStatement.setDouble(1, 6000);
        preparedStatement.setString(2, "twi");

        int count=preparedStatement.executeUpdate();
        if(count>0){
            System.out.println("更新成功");
        }else{
            System.out.println("更新失败");
        }
    }

    @Test
    public void testDelete() throws Exception {
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "YUyue0702");

        PreparedStatement preparedStatement=connection.prepareStatement("DELETE FROM t_emp WHERE emp_name = ?");

        preparedStatement.setString(1, "twi");

        int count=preparedStatement.executeUpdate();
        if(count>0){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
    }
}
