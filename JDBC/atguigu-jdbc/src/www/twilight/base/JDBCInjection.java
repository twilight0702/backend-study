package www.twilight.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCInjection{
    public static void main(String[] args) throws Exception{
        Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "YUyue0702");
        Statement stmt=conn.createStatement();

        System.out.println("请输入要查询的员工姓名：");
        Scanner sc=new Scanner(System.in);
        String name=sc.nextLine(); //为了接收空格，所以用nextLine()而不是next()

        String sql="SELECT * FROM t_emp WHERE emp_name='"+name+"';";
        ResultSet resultSet=stmt.executeQuery(sql);

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
        stmt.close();
        conn.close();
    }


}
