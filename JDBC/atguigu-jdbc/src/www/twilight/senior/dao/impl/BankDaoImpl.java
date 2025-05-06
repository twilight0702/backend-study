package www.twilight.senior.dao.impl;


import www.twilight.senior.dao.BankDao;
import www.twilight.senior.dao.BaseDao;

import java.sql.SQLException;

public class BankDaoImpl  extends BaseDao implements BankDao{
    public int addMoney(Integer id,Integer money){
        try {
            String sql = "update t_bank set money = money + ? where id = ? ";
            return executeUpdate(sql,money,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int subMoney(Integer id,Integer money){
        try {
            String sql = "update t_bank set money = money - ? where id = ? ";
            return executeUpdate(sql,money,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
