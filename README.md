本项目是学习InnoDB时候做实验项目。
主要涉及事务的使用，x锁使用的验证等。

# 隔离级别
- READ UNCOMMITED
- READ COMMITTED
- REPEATABLE READ(默认的隔离级别)
- SERILIZABLE
## 查看数据库隔离级别与设置隔离级别
* 查看数据库隔离级别：

select @@tx_isolation

select @@session.tx_isolation

* 设置数据库隔离级别

(read uncommitted|read committed|repeatable read|serializable)

set tx_isolation='repeatable-read';

set session tx_isolation='read-uncommitted';

* 设置不自动提交
set @@autocommit=0;
## 实验

- READ COMMITTED

    Session 1:

    1.1 set session tx_isolation='read-committed';

    1.2 start transaction;

    1.3 
    insert INTO `user_info` (`user_id`, `name`, `femal`, `address`, `data_status`,`created_data`, `created`, `modified_data`, `modified`, `version`) VALUES (201811272001,'201811272001',1,'addr','1',now(),'sys',now(),'sys',1);

    1.4 commit

    Session 2:

    2.1  after 1.3 , select * from user_info where user_id='201811272001', 返回结果集为空

    2.2  after 1.4, 上述返回结果集为对应记录

- READ UNCOMMITED

    同样的上述实验步骤，在2.1的时候有数据返回。即会话2读取到了未提交的数据。


GAP锁验证：

1. 设置隔离级别为RR
set session tx_isolation=‘REPEATABLE-READ’;

2. Session 1.

 update user_info set femal='1' where name = 3; 
 
 其中name 为非唯一索引， 此时记录name还有1,6，8
 
3. Session 2.
 以上可知，gap锁有（1,3），（3.6）。 因此插入记录name=2， 或者name=4, 5 即可以验证
 
# 笔记
http://note.youdao.com/noteshare?id=d0ba36c281e02d59aace92a7ba8db8e0&sub=66D956EC3958469196CB4F4A738D1EF9
    
    
    
    
  