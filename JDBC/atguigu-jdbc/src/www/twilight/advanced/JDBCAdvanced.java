package www.twilight.advanced;

import org.junit.Test;
import www.twilight.advanced.pojo.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAdvanced {

    @Test
    public void testORM() throws SQLException {
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "YUyue0702");

        PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM t_emp WHERE emp_id=?");

        preparedStatement.setInt(1,5);

        ResultSet resultSet=preparedStatement.executeQuery();

        Employee employee=null; // 先赋值null，有查询到再赋值

        while (resultSet.next()){
            employee=new Employee(); // 注意这里要new一个
            employee.setEmpId(resultSet.getInt("emp_id"));
            employee.setEmpName(resultSet.getString("emp_name"));
            employee.setEmpSalary(resultSet.getDouble("emp_salary"));
            employee.setEmpAge(resultSet.getInt("emp_age"));
        }

        System.out.println(employee);

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testORMList() throws SQLException {
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "YUyue0702");

        PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM t_emp");

        ResultSet resultSet=preparedStatement.executeQuery();

        List<Employee> employees=new ArrayList<>();// 注意List的写法

        while (resultSet.next()){
            Employee employee=new Employee(); // 注意这里要new一个
            employee.setEmpId(resultSet.getInt("emp_id"));
            employee.setEmpName(resultSet.getString("emp_name"));
            employee.setEmpSalary(resultSet.getDouble("emp_salary"));
            employee.setEmpAge(resultSet.getInt("emp_age"));

            employees.add(employee);
        }

        for(Employee employee:employees){
            System.out.println(employee);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testReturnPK() throws SQLException{
        Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "YUyue0702");

        // 预编译SQL语句，告知preparedStatement，需要返回生成的主键列的值
        String sql="INSERT INTO t_emp(emp_name,emp_salary,emp_age) VALUES (?,?,?)";
        PreparedStatement preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // 创建对象，然后填充到？占位符中（体现ORM）
        Employee employee=new Employee(null, "twil", 10000.0, 20);
        preparedStatement.setString(1,employee.getEmpName());
        preparedStatement.setDouble(2,employee.getEmpSalary());
        preparedStatement.setInt(3,employee.getEmpAge());

        // 执行SQL语句，获取返回结果
        int result=preparedStatement.executeUpdate();

        if(result>0){
            System.out.println("插入成功");

            // 获取当前新增数据的主键列，回显到对象中（接收回显值）
            // 返回的主键值是一个单行单列的结果
            ResultSet resultSet=preparedStatement.getGeneratedKeys(); // 注意除了受影响行数，返回的都是ResultSet
            if(resultSet.next()){
                employee.setEmpId(resultSet.getInt(1)); // 直接用列的索引取，然后赋值给原对象
            }
            resultSet.close();
            System.out.println(employee);
        }
        else{
            System.out.println("插入失败");
        }

        preparedStatement.close();
        connection.close();
    }
}
