package www.twilight.senior.dao.impl;

import www.twilight.senior.dao.BaseDao;
import www.twilight.senior.dao.EmployeeDao;
import www.twilight.senior.pojo.Employee;

import java.util.List;

public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {

    @Override
    public List<Employee> selectAll() {
        try {
            //注意要给别名，和类的字段对应上
            String sql="SELECT emp_id empId,emp_name empName,emp_salary empSalary,emp_age empAge FROM t_emp";
            return executeQuery(Employee.class,sql,null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee selectEmployeeById(Integer empId) {
        try {
            //注意要给别名，和类的字段对应上
            String sql="SELECT emp_id empId,emp_name empName,emp_salary empSalary,emp_age empAge FROM t_emp WHERE emp_id=?";
            return executeQueryBean(Employee.class,sql,empId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(Employee employee) {
        try {
            String sql="INSERT INTO t_emp(emp_name,emp_salary,emp_age) VALUES(?,?,?)";
            return executeUpdate(sql,employee.getEmpName(),employee.getEmpSalary(),employee.getEmpAge());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Employee employee) {
        try {
            String sql="UPDATE t_emp SET emp_name=?,emp_salary=?,emp_age=? WHERE emp_id=?";
            return executeUpdate(sql,employee.getEmpName(),employee.getEmpSalary(),employee.getEmpAge(),employee.getEmpId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Integer empId) {
        try {
            String sql="DELETE FROM t_emp WHERE emp_id=?";
            return executeUpdate(sql,empId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
