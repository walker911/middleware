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

create table sys_user
(
    id               bigint auto_increment comment '主键'
        primary key,
    name             varchar(50)       not null comment '用户名',
    nickname         varchar(150)      null comment '昵称',
    avatar           varchar(150)      null comment '头像',
    password         varchar(100)      null comment '密码',
    salt             varchar(40)       null comment '加密盐',
    email            varchar(100)      null comment '邮箱',
    mobile           varchar(100)      null comment '手机号',
    status           tinyint           null comment '状态：0-禁用 1-正常',
    dept_id          bigint            null comment '机构id',
    del_flag         tinyint default 1 null comment '是否删除：0-删除 1-正常',
    create_by        varchar(50)       null comment '创建人',
    create_time      datetime          null comment '创建时间',
    last_update_by   varchar(50)       null comment '更新人',
    last_update_time datetime          null comment '更新时间',
    constraint uk_name
        unique (name)
)
    comment '用户管理';

create table sys_role
(
    id               bigint auto_increment comment '主键id'
        primary key,
    name             varchar(100)      null comment '角色名',
    remark           varchar(100)      null comment '备注',
    del_flag         tinyint default 1 null comment '是否删除：0-删除 1-正常',
    create_by        varchar(50)       null comment '创建人',
    create_time      datetime          null comment '创建时间',
    last_update_by   varchar(50)       null comment '更新人',
    last_update_time datetime          null comment '更新时间'
)
    comment '角色表';

create table sys_dept
(
    id               bigint auto_increment comment '主键id'
        primary key,
    name             varchar(100)      null comment '机构名称',
    parent_id        bigint            null comment '上级机构ID，一级机构为0',
    order_num        int               null comment '排序',
    del_flag         tinyint default 1 null comment '是否删除：0-删除 1-正常',
    create_by        varchar(50)       null comment '创建人',
    create_time      datetime          null comment '创建时间',
    last_update_by   varchar(50)       null comment '更新人',
    last_update_time datetime          null comment '更新时间'
)
    comment '机构表';

create table sys_menu
(
    id               bigint auto_increment comment '主键id'
        primary key,
    name             varchar(100)      null comment '菜单名称',
    parent_id        bigint            null comment '父级菜单ID，一级菜单为0',
    url              varchar(200)      null comment '菜单url：1.普通页面，如/sys/user 2.嵌套完整外部页面，以http(s)开头 3.嵌套服务器页面，使用iframe',
    perms            varchar(500)      null comment '授权，多个用逗号隔开，如：sys:user:add,sys:user.edit',
    type             tinyint           null comment '类型：0-目录 1-菜单 2-按钮',
    icon             varchar(50)       null comment '菜单图标',
    order_num        int               null comment '排序',
    del_flag         tinyint default 1 null comment '是否删除：0-删除 1-正常',
    create_by        varchar(50)       null comment '创建人',
    create_time      datetime          null comment '创建时间',
    last_update_by   varchar(50)       null comment '更新人',
    last_update_time datetime          null comment '更新时间'
)
    comment '菜单表';

create table sys_user_role
(
    id               bigint auto_increment comment '主键id'
        primary key,
    user_id          bigint            null comment '用户ID',
    role_id          bigint            null comment '角色ID',
    create_by        varchar(50)       null comment '创建人',
    create_time      datetime          null comment '创建时间',
    last_update_by   varchar(50)       null comment '更新人',
    last_update_time datetime          null comment '更新时间'
)
    comment '用户角色表';

create table sys_role_menu
(
    id               bigint auto_increment comment '主键id'
        primary key,
    role_id          bigint            null comment '角色ID',
    menu_id          bigint            null comment '菜单ID',
    create_by        varchar(50)       null comment '创建人',
    create_time      datetime          null comment '创建时间',
    last_update_by   varchar(50)       null comment '更新人',
    last_update_time datetime          null comment '更新时间'
)
    comment '角色菜单表';

create table sys_role_dept
(
    id               bigint auto_increment comment '主键id'
        primary key,
    role_id          bigint            null comment '角色ID',
    dept_id          bigint            null comment '机构ID',
    create_by        varchar(50)       null comment '创建人',
    create_time      datetime          null comment '创建时间',
    last_update_by   varchar(50)       null comment '更新人',
    last_update_time datetime          null comment '更新时间'
)
    comment '角色机构表';

create table sys_dict
(
    id               bigint auto_increment comment '主键id'
        primary key,
    value            varchar(100)      not null comment '数据值',
    label            varchar(100)      not null comment '标签名',
    type             varchar(100)      not null comment '类型',
    description      varchar(100)      not null comment '描述',
    sort             int               not null comment '排序',
    del_flag         tinyint default 1 null comment '是否删除：0-删除 1-正常',
    remark           varchar(255)      null comment '备注信息',
    create_by        varchar(50)       null comment '创建人',
    create_time      datetime          null comment '创建时间',
    last_update_by   varchar(50)       null comment '更新人',
    last_update_time datetime          null comment '更新时间'
)
    comment '字典表';

create table sys_config
(
    id               bigint auto_increment comment '主键id'
        primary key,
    value            varchar(100)      not null comment '数据值',
    label            varchar(100)      not null comment '标签名',
    type             varchar(100)      not null comment '类型',
    description      varchar(100)      not null comment '描述',
    sort             int               not null comment '排序',
    del_flag         tinyint default 1 null comment '是否删除：0-删除 1-正常',
    remark           varchar(255)      null comment '备注信息',
    create_by        varchar(50)       null comment '创建人',
    create_time      datetime          null comment '创建时间',
    last_update_by   varchar(50)       null comment '更新人',
    last_update_time datetime          null comment '更新时间'
)
    comment '系统配置表';

create table sys_log
(
    id               bigint auto_increment comment '主键id'
        primary key,
    username         varchar(50)   null comment '用户名',
    operation        varchar(50)   null comment '用户操作',
    method           varchar(200)  null comment '请求方法',
    params           varchar(5000) null comment '请求参数',
    time             bigint        not null comment '执行时长（毫秒）',
    ip               varchar(64)   null comment 'IP地址',
    create_by        varchar(50)   null comment '创建人',
    create_time      datetime      null comment '创建时间',
    last_update_by   varchar(50)   null comment '更新人',
    last_update_time datetime      null comment '更新时间'
)
    comment '系统操作日志表';

create table sys_login_log
(
    id               bigint auto_increment comment '主键id'
        primary key,
    username         varchar(50) null comment '用户名',
    status           varchar(50) null comment '登录状态：online-在线，login，logout-退出登录',
    ip               varchar(64) null comment 'IP地址',
    create_by        varchar(50) null comment '创建人',
    create_time      datetime    null comment '创建时间',
    last_update_by   varchar(50) null comment '更新人',
    last_update_time datetime    null comment '更新时间'
)
    comment '系统登录日志表';

create table book_stock
(
    id        int auto_increment comment '主键id'
        primary key,
    book_no   varchar(50) null comment '书籍编号',
    stock     int         null comment '库存',
    is_active tinyint     null comment '是否上架'
)
    comment '书籍记录库存表';

create table book_rob
(
    id       int auto_increment comment '主键id'
        primary key,
    user_id  int         null comment '用户id',
    book_no  varchar(50) null comment '书籍编号',
    rob_time datetime    null comment '抢购时间'
)
    comment '书籍抢购记录表';