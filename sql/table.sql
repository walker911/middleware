create table user_reg
(
    id          int auto_increment
        primary key,
    user_name   varchar(255) not null comment '用户名',
    password    varchar(255) not null comment '密码',
    create_time datetime     null comment '创建时间'
)
    comment '用户注册信息表';