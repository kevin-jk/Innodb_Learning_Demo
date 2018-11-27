create table user_info(
id BIGINT not null auto_increment comment "主键id",
user_id BIGINT not null comment "user_id",
name VARCHAR(60) not null comment "姓名",
femal VARCHAR(2) null comment "性别",
address VARCHAR(500) null comment "地址",
version BIGINT null comment "版本信息",
data_status VARCHAR(10) not null comment "状态",
created_data datetime not null comment "创建时间",
created VARCHAR(20) null comment "创建者",
modified_data timestamp not null default current_timestamp on update current_timestamp  comment "修改时间",
modified VARCHAR(20) comment "修改人",
primary key(id),
key name_key(name),
unique key uni_user_id(user_id)
)engine=innodb auto_increment=0 default charset=utf8 collate=utf8_bin comment='user_info';


create table user_course(
id BIGINT not null auto_increment comment "主键id",
course_id BIGINT not null comment "课程号",
name VARCHAR(60) not null comment "姓名",
user_id BIGINT not null  comment "用户ID",
data_status VARCHAR(10) not null comment "状态",
created_data datetime not null comment "创建时间",
created VARCHAR(20) null comment "创建者",
modified_data timestamp not null default current_timestamp on update current_timestamp  comment "修改时间",
modified VARCHAR(20) null comment "修改人",
primary key(id),
key user_name_key(name),
key  course_id(course_id)
)engine=innodb auto_increment=0 default charset=utf8 collate=utf8_bin comment='user_course';

