package com.kun.practise.innodb.learning;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by jrjiakun on 2018/11/27
 */
@Repository
public class JdbcTemplateDemo {
    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    JdbcTemplate jdbcTemplate1;

    @Resource
    JdbcTemplate jdbcTemplate2;



    public void test() {
        String sql = "select * from user_info";
        List<Map<String, Object>> res = jdbcTemplate.queryForList(sql);
        System.out.println(res);
    }

    public void addUserVersion(){
        String sql = "select * from user_info where user_id=1";
        Map<String,Object> res = jdbcTemplate.queryForMap(sql);
        if(null!=res&&null!=res.get("version")){
            String update = "update user_info set version=? where id=?";
            jdbcTemplate.update(update,(Long)res.get("version")+1,res.get("user_id"));
        }
    }

    //select for update 只能在事务中
@Transactional
    public void addUserVersionBySafe(){
        String sql = "select * from user_info where user_id=1";
        Map<String,Object> res = jdbcTemplate.queryForMap(sql);
        if(null!=res&&null!=res.get("version")){
            String update = "update user_info set version=? where id=?";
            jdbcTemplate.update(update,(Long)res.get("version")+1,res.get("user_id"));
        }
    }

    public void addUserVersionOneSql(){
        String sql = "update user_info set version=version+1 where id=?";
        jdbcTemplate.update(sql,1);
    }

    public void cleanUserVersion(){
        String sql = "update user_info set version=0 where id=1";
        jdbcTemplate.update(sql);
    }

    public void insertBatch(int size){
        String sql = "INSERT INTO `user_info` (`user_id`, `name`, `femal`, `address`, `data_status`," +
                " `created_data`, `created`, `modified_data`, `modified`, `version`) VALUES (?,?,?,'addr','1',now(),'sys',now(),'sys',1);";
       System.out.println("Thread "+Thread.currentThread().getName()+" begin to insert data.");
        try{
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    String user_id=System.currentTimeMillis()+ UUID.randomUUID().toString();
                    ps.setLong(1,user_id.hashCode());
                    ps.setString(2,user_id.substring(user_id.length()-6));
                    ps.setString(3, String.valueOf(new Random().nextInt(2)));
                }

                @Override
                public int getBatchSize() {
                    return size;
                }
            });
        }catch (Exception e){
          System.out.println(e);
        }
        System.out.println("Thread "+Thread.currentThread().getName()+" insert data success.");
    }


    public void update_session_1() {
        Connection connection = null;
        try{

            String sql = "update user_info set version=version+1 where id=?";
            jdbcTemplate1.update(sql,1);
            //connection.commit();
        }catch (Exception e){
            System.out.println(e);
            try{
                connection.rollback();
            }catch (Exception ex){

            }

        }
    }

   public void deadLock(String date,String nodeId){
       String sql = "update biz_table_1 set data_status='0' where biz_date=? and id=?";
       try{
           jdbcTemplate2.update(sql,date,nodeId);
       }catch (Exception e){
           System.out.println(e);
       }
   }
}
