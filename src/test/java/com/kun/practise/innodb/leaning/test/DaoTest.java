package com.kun.practise.innodb.leaning.test;

import com.kun.practise.innodb.learning.JdbcTemplateDemo;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by jrjiakun on 2018/11/27
 */
public class DaoTest extends BaseTest {
    @Resource
    JdbcTemplateDemo jdbcTemplateDemo;

    @Before
    public void clean(){
        jdbcTemplateDemo.cleanUserVersion();
    }

    @Test
    public void test_multiAdd() throws Exception{
        for(int i=100;i>0;i--){
            new Thread(()-> jdbcTemplateDemo.addUserVersion()).start();
        }
        Thread.sleep(1000000000l);
    }


    @Test
    public void insert_batch() throws Exception {
        for(int i=50;i>0;i--){
            new Thread(
                    ()-> jdbcTemplateDemo.insertBatch(500)).start();
        }
        Thread.sleep(1000000000l);
    }


    @Test
    public void notAutoCommit() throws Exception{
        jdbcTemplateDemo.update_session_1();
        Thread.sleep(1000000000l);
    }
}
