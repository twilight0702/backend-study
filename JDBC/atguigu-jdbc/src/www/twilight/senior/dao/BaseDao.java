package www.twilight.senior.dao;

import www.twilight.senior.util.JDBCUtilV2;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 将共性的数据库操作代码放到这里
 */
public class BaseDao {
    /**
     * 通用增删改，具体是什么操作取决于sql语句，这个负责接收sql语句和参数（list不方便，传入的时候还需要封装），然后执行
     * @param sql 调用者要执行的sql语句
     * @param params sql语句中的占位符参数
     * @return
     */
    public int executeUpdate(String sql, Object... params) throws Exception{
        //用JDBCUtilV2获取连接
        Connection connection = JDBCUtilV2.getConnection();

        //预编译sql语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //为占位符赋值（注意需要防止没有传入params或者长度为0，此时就不赋值）
        if(params!=null&&params.length>0) {
            for (int i = 0; i < params.length; i++) {
                //注意占位符从1开始，参数的下标是从0开始。
                //占位符的类型是不确定的，所以这里用Object（使用通用型的）
                preparedStatement.setObject(i + 1, params[i]);
            }
        }

        //执行sql语句
        int result = preparedStatement.executeUpdate();

        //释放资源
        preparedStatement.close();
        JDBCUtilV2.release();

        //返回结果
        return result;
    }


    /**
     * 通用的查询：多行多列，单行多列，单行单列
     * 1.多行多列：返回一个list
     * 2.单行多列：返回一个对象
     * 3.单行单列：返回一个值
     * 封装过程：
     * 1.返回的类型：泛型：类型不确定，但是调用者知道，调用试将此次查询的结果类型搞事BaseDAO就可以了
     * 2.返回的结果：通用list，可以存储多个结果，也可以存储一个结果 get(0)
     * 3.结果的封装：反射，要求调用者告知BaseDAO要封装的对象的类对象
     */
    public <T> List<T> executeQuery(Class<T> clazz,String sql,Object... params) throws Exception{
        //获取连接
        Connection connection = JDBCUtilV2.getConnection();

        //预编译sql语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //设置占位符的值
        if(params!=null&&params.length>0){
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1,params[i]);
            }
        }

        //执行sql语句，获取结果集
        ResultSet resultSet = preparedStatement.executeQuery();

        //获取结果集的元数据对象（因为不知道具体的表是什么样的，列名是什么）
        //包含了列的数量、每一个列的名称
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        List<T> list=new ArrayList<>();
        //处理结果
        while(resultSet.next()){
            //循环一次，代表有一行数据，就用反射创建一个对象
            T t = clazz.getDeclaredConstructor().newInstance();
            for (int i = 0; i < columnCount; i++) {
                //通过下标获取列的值
                Object value = resultSet.getObject(i + 1);

                //获取到的value值就是t这个对象的一个对应的属性值
                //获取当前拿到的列的名字 = 对象的属性名
                String fieldName = metaData.getColumnLabel(i + 1);//为什么是label不是name
                //通过类对象和fieldName获取到当前列对应的属性
                Field field = clazz.getDeclaredField(fieldName);
                //突破封装的private
                field.setAccessible(true);
                field.set(t,value);
            }
            list.add(t);
        }

        //释放资源
        resultSet.close();
        preparedStatement.close();
        JDBCUtilV2.release();

        return list;
    }

    /**
     * 通用查询：在上面的查询的结合结果中获取第一个结果，简化了单行单列的获取、单行多列的获取
     */
    public <T> T executeQueryBean(Class<T> clazz,String sql,Object... params) throws Exception{
        List<T> list=this.executeQuery(clazz,sql,params);
        if(list==null|| list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}
