package www.twilight.base;

import java.sql.*;
import java.util.Scanner;

public class JDBCPrepared {
    public static void main(String[] args) throws Exception{
        Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "YUyue0702");
        PreparedStatement preparedStatement=conn.prepareStatement("SELECT * FROM t_emp WHERE emp_name = ?"); // 不支持无参构造，一定要先传入一个sql语句

        System.out.println("请输入要查询的员工姓名：");
        Scanner sc=new Scanner(System.in);
        String name=sc.nextLine(); //为了接收空格，所以用nextLine()而不是next()

        // 为？占位符赋值，然后执行、得到返回结果
        preparedStatement.setString(1,name);// 需要两个参数，第一个是参数的索引，从1开始，第二个是参数的值
        ResultSet resultSet=preparedStatement.executeQuery();

        // 处理结果，遍历结果集
        while(resultSet.next()){
            // 可以用列名获取，也可以用索引id，推荐用列名
            int empId=resultSet.getInt("emp_id");
            String empName=resultSet.getString("emp_name");
            double empSalary=resultSet.getDouble("emp_salary");
            int empAge=resultSet.getInt("emp_age");

            System.out.println(empId+"\t"+empName+"\t"+empSalary+"\t"+empAge);
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();
    }
}
