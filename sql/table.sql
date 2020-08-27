create table user_reg
(
    id          int auto_increment
        primary key,
    user_name   varchar(255) not null comment '用户名',
    password    varchar(255) not null comment '密码',
    create_time datetime     null comment '创建时间'
)
    comment '用户注册信息表';

create table sys_log
(
    id          int auto_increment comment '主键'
        primary key,
    user_id     int           not null comment '用户id',
    module      varchar(255)  null comment '模块',
    data        varchar(5000) null comment '操作数据',
    memo        varchar(500)  null comment '备注',
    create_time datetime      null comment '创建时间'
)
    comment '日志记录表';

create table praise
(
    id          int auto_increment comment '主键'
        primary key,
    blog_id     int               not null comment '博客id',
    user_id     int               not null comment '点赞id',
    praise_time datetime          null comment '点赞时间',
    status      tinyint default 1 null comment '状态：0-取消 1-正常',
    is_active   tinyint default 1 null comment '是否有效：0-否 1-是',
    create_time datetime          null comment '创建时间',
    update_time datetime          null comment '更新时间'
)
    comment '用户点赞记录表';
