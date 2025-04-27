package com.atguigu.base;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class JDBCOperation {
    @Test
    public void testQuerySingleRowAndCol() throws SQLException {
        //1.注册驱动 （可以省略）

        //2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "atguigu");

        //3.预编译SQL语句得到PreparedStatement对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) as count FROM t_emp");

        //4.执行SQL语句，获取结果
        ResultSet resultSet = preparedStatement.executeQuery();

        //5.处理结果(如果自己明确一定只有一个结果，那么resultSet最少要做一次next的判断，才能拿到我们要的列的结果)
        if(resultSet.next()){
            int count = resultSet.getInt("count");
            System.out.println(count);
        }

        //6.释放资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testQuerySingleRow()throws Exception{
        //1.注册驱动

        //2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "atguigu");

        //3.预编译SQL语句获得PreparedStatement对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT emp_id,emp_name,emp_salary,emp_age FROM t_emp WHERE emp_id = ?");

        //4.为占位符赋值，然后执行，并接受结果
        preparedStatement.setInt(1,5);
        ResultSet resultSet = preparedStatement.executeQuery();

        //5.处理结果
        while(resultSet.next()){
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");

            System.out.println(empId+"\t"+empName+"\t"+empSalary+"\t"+empAge);
        }

        //6.资源释放
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testQueryMoreRow()throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "atguigu");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT emp_id,emp_name,emp_salary,emp_age FROM t_emp WHERE emp_age > ?");

        //为占位符赋值，执行SQL语句，接受结果
        preparedStatement.setInt(1, 25);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");

            System.out.println(empId+"\t"+empName+"\t"+empSalary+"\t"+empAge);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testInsert() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "atguigu");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_emp(emp_name,emp_salary,emp_age) VALUES (?,?,?)");

        preparedStatement.setString(1, "rose");
        preparedStatement.setDouble(2,345.67);
        preparedStatement.setInt(3,28);

        int result = preparedStatement.executeUpdate();

        //根据受影响行数，做判断，得到成功或失败
        if(result > 0){
            System.out.println("成功！");
        }else{
            System.out.println("失败！");
        }

        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testUpdate() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "atguigu");

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE t_emp SET emp_salary = ? WHERE emp_id = ?");

        preparedStatement.setDouble(1, 888.88);
        preparedStatement.setInt(2, 6);

        int result = preparedStatement.executeUpdate();

        if(result > 0){
            System.out.println("成功！");
        }else{
            System.out.println("失败！");
        }

        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testDelete() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "atguigu");

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM t_emp WHERE emp_id = ?");

        preparedStatement.setDouble(1, 6);

        int result = preparedStatement.executeUpdate();

        if(result > 0){
            System.out.println("成功！");
        }else{
            System.out.println("失败！");
        }

        preparedStatement.close();
        connection.close();
    }
}
