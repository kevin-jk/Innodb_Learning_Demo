package com.kun.practise.innodb.leaning.test;

import com.kun.practise.innodb.learning.JdbcTemplateDemo;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void test_multiAddVerion() throws Exception{
        for(int i=100;i>0;i--){
            new Thread(()-> jdbcTemplateDemo.addUserVersionOneSql()).start();
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

    @Test
    public void deadLock() throws Exception {
        List<List> nodeIdLists = new ArrayList<>();
        List list1 = Arrays.asList(new String[]{"S11012.1","S111.1","P111.1809","P112.1809","P114.1809","P114.2"});
        List list2 = Arrays.asList(new String[]{"P1115.1","S12","P12.2","P141.1809","P151.2","P171.1"});
        List list3 = Arrays.asList(new String[]{"P211.1","P31.1","S311","P312.1809","P32.1","P322.1809","P72.1809"});
        List list4 = Arrays.asList(new String[]{"P314.1809","P36.1809","P41.1809","P52.1809","P611.1","P611.1809","P63.1809"});
        List list5 = Arrays.asList(new String[]{"P6112.1809","P613.1809","P6141.1809","P6142.1809","P6143.1809","P6144.1809","P7.2"});
        nodeIdLists.add(list1);
        nodeIdLists.add(list2);
        nodeIdLists.add(list3);
        nodeIdLists.add(list4);
        nodeIdLists.add(list5);

        nodeIdLists.forEach(nodes->
            new Thread(()->{
               nodes.forEach(nodeId->
                    jdbcTemplateDemo.deadLock("2018/9/26",(String)nodeId)
                );
            },"Thread-"+nodeIdLists.indexOf(nodes)).start()
        );
        Thread.sleep(1000000000l);
    }
}
