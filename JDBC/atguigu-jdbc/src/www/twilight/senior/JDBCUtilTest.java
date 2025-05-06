package www.twilight.senior;

import org.junit.Test;
import www.twilight.senior.dao.BankDao;
import www.twilight.senior.dao.EmployeeDao;
import www.twilight.senior.dao.impl.BankDaoImpl;
import www.twilight.senior.dao.impl.EmployeeDaoImpl;
import www.twilight.senior.pojo.Employee;
import www.twilight.senior.util.JDBCUtil;
import www.twilight.senior.util.JDBCUtilV2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBCUtilTest {
    @Test
    public void testGetConnection() {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
        JDBCUtil.release(connection);
    }

    @Test
    public void testJDBCUtil2(){
        Connection connection1 = JDBCUtilV2.getConnection();
        Connection connection2 = JDBCUtilV2.getConnection();
        Connection connection3 = JDBCUtilV2.getConnection();

        System.out.println(connection1);
        System.out.println(connection2);
        System.out.println(connection3);

    }

    @Test
    public void testEmployeeDao(){
//        EmployeeDao employeeDao = new EmployeeDaoImpl();
//
//        List<Employee> employees = employeeDao.selectAll();
//
//        for (Employee employee : employees) {
//            System.out.println(employee);
//        }

//        EmployeeDao employeeDao = new EmployeeDaoImpl();
//        Employee employee = employeeDao.selectEmployeeById(5);
//        System.out.println(employee);

//        EmployeeDao employeeDao = new EmployeeDaoImpl();
//        Employee employee = new Employee(null,"小王",5000.0,18);
//        int i = employeeDao.insert(employee);
//        System.out.println(i);

        EmployeeDao employeeDao = new EmployeeDaoImpl();
        Employee employee = new Employee(11,"小王",5000.0,20);
        int i = employeeDao.update(employee);
        System.out.println(i);


    }

    @Test
    public void testTransaction(){
        Connection connection =null;
        try {
            BankDao bankDao = new BankDaoImpl();

            connection = JDBCUtilV2.getConnection();
            connection.setAutoCommit(false); //开启事务，修改成手动提交

            bankDao.addMoney(1,100);
            bankDao.subMoney(2,100);

            //前置的多条dao操作没有异常，就提交
            connection.commit();
        } catch (Exception e) {
            try {
                if (connection != null) {
                    //执行有异常就回滚
                    connection.rollback();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }finally{
            //释放连接
            JDBCUtilV2.release();
        }
    }
}
