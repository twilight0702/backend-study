package www.twilight.senior.dao;

import www.twilight.senior.pojo.Employee;

import java.util.List;

/**
 * EmployeeDao这个类对应的是t_emp这张表的增删改查的操作
 * 不同的数据库，具体增删改查的操作是不一样的，所以这个接口，就是用来定义通用的增删改查操作的，便于根据不同的数据库拓展
 * 面向接口编程
 */
public interface EmployeeDao {
    /**
     * 数据库中对应的查询所有的操作
     * @return 表中的所有数据
     */
    List<Employee> selectAll();

    /**
     * 根据id查询对应的数据
     * @param empId 主键列
     * @return 一个员工对象（一行数据）
     */
    Employee selectEmployeeById(Integer empId);

    /**
     * 数据库新增一条数据
     * @param employee ORM思想中的一个员工对象
     * @return 受影响的行数
     */
    int insert(Employee employee);

    /**
     * 数据库修改一条数据
     * @param employee ORM思想中的一个员工对象
     * @return 受影响的行数
     */
    int update(Employee employee);

    /**
     * 根据主键列删除一条数据
     * @param empId 主键列
     * @return 受影响的行数
     */
    int delete(Integer empId);
}
