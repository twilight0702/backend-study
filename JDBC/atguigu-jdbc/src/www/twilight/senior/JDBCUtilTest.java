package www.twilight.senior;

import org.junit.Test;
import www.twilight.senior.util.JDBCUtil;

import java.sql.Connection;

public class JDBCUtilTest {
    @Test
    public void testGetConnection() {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
        JDBCUtil.release(connection);
    }
}
