create table addresses
(
    address_id      bigint                             not null comment '地址的唯一标识符'
        primary key,
    user_id         bigint                             not null comment '关联用户表的逻辑外键',
    recipient_name  varchar(255)                       not null comment '收件人姓名',
    recipient_phone varchar(255)                       not null comment '收件人电话
',
    province        varchar(255)                       not null comment '省份',
    city            varchar(255)                       not null comment '城市
',
    address         varchar(255)                       not null comment '详细地址',
    is_delete       tinyint  default 0                 not null comment '逻辑删除字段 0--未删除 1--已删除',
    create_user     bigint                             null comment '创建人ID',
    update_user     bigint                             null comment '更新人ID',
    create_time     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table cart_items
(
    item_id     bigint                             not null comment '购物车中的Item唯一ID'
        primary key,
    cart_id     bigint                             not null comment '购物车ID',
    product_id  bigint                             not null comment '商品ID',
    quantity    int      default 1                 not null comment '产品的数量',
    create_user bigint                             null comment '创建人ID',
    update_user bigint                             null comment '更新人ID',
    create_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table carts
(
    cart_id     bigint                             not null comment '购物车唯一ID'
        primary key,
    user_id     bigint                             not null comment '用户ID',
    create_user bigint                             null comment '创建人ID',
    update_user bigint                             null comment '更新人ID',
    create_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
);

create table marketing_reports
(
    report_id        bigint         not null comment '报表的唯一标识符'
        primary key,
    date             datetime       not null comment '数据对应的日期',
    campaign_id      int            not null comment '营销活动的标识符',
    campaign_cost    decimal(10, 2) not null comment '活动成本',
    campaign_revenue varchar(255)   not null comment '活动产生的收入',
    conversion_rate  varchar(255)   not null comment '转化率'
);

create table order_details
(
    detail_id   bigint auto_increment comment '订单详情ID'
        primary key,
    order_id    bigint         not null comment '订单ID，关联订单表的逻辑外键',
    product_id  bigint         not null comment '商品ID，关联商品表的逻辑外键',
    quantity    int            not null comment '商品数量',
    unit_price  decimal(10, 2) not null comment '商品单价',
    total_price decimal(10, 2) not null comment '商品总价'
);

create index order_id_index
    on order_details (order_id);

create index product_id_index
    on order_details (product_id);

create table order_status_history
(
    history_id      bigint auto_increment comment '状态变更记录ID'
        primary key,
    order_id        bigint   not null comment '订单ID，关联订单表的逻辑外键',
    original_status tinyint  not null comment '原订单状态',
    new_status      tinyint  not null comment '新订单状态',
    change_time     datetime not null comment '状态变更时间'
);

create index order_id_index
    on order_status_history (order_id);

create table orders
(
    order_id     bigint                             not null comment '订单ID'
        primary key,
    user_id      bigint                             not null comment '关联用户表的逻辑外键',
    address_id   bigint                             not null comment '关联送货地址表的逻辑外键',
    order_status tinyint                            not null comment '0 -- 待发货
1 -- 运输中
2 -- 交易完成
3 -- 申请退款
4 -- 已退货
5 -- 已取消',
    total_amount decimal(10, 2)                     not null comment '订单的总金额',
    is_delete    tinyint  default 0                 not null comment '品牌的逻辑删除字段 0--未删除 1--已删除',
    create_user  bigint                             null comment '创建订单的管理员ID',
    update_user  bigint                             null comment '更新订单的管理员ID',
    create_time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '订单创建时间',
    update_time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '订单更新时间'
);

create table payments
(
    payment_id      bigint auto_increment comment '支付唯一标识符'
        primary key,
    order_id        bigint                             not null comment '关联订单表的逻辑外键',
    payment_channel varchar(255)                       not null comment '支付渠道',
    payment_status  tinyint  default 0                 not null comment '支付状态，0--待支付 1--已支付 2--支付失败',
    payment_amount  decimal(10, 2)                     not null comment '支付金额',
    payment_time    datetime                           not null comment '支付时间',
    create_user     bigint                             null comment '创建支付信息的管理员ID',
    update_user     bigint                             null comment '更新支付信息的管理员ID',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '支付信息创建时间',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '支付信息更新时间'
);

create index order_id_index
    on payments (order_id);

create table performance_reports
(
    report_id            bigint                             not null comment '报表的唯一标识符'
        primary key,
    date                 datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '数据对应的日期',
    total_sales          decimal(12, 2)                     not null comment '当日总销售额',
    total_orders         int                                not null comment '当日订单数量',
    average_order_amount decimal(12, 2)                     not null comment '平均订单金额'
);

create table product_brands
(
    brand_id       bigint                             not null comment '品牌唯一标识符'
        primary key,
    brand_name     varchar(255)                       not null comment '品牌名称',
    brand_logo_url varchar(255)                       null comment '品牌logo链接',
    is_enable      tinyint  default 1                 not null comment '品牌是否入驻 0--未入驻 1--已入驻 ',
    is_delete      tinyint  default 0                 not null comment '品牌的逻辑删除字段 0--未删除 1--已删除',
    create_user    bigint                             null comment '创建品牌的管理员ID',
    update_user    bigint                             null comment '更新品牌的管理员ID',
    create_time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '品牌创建时间',
    update_time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '品牌更新时间'
);

create table product_colors
(
    color_id    bigint                             not null comment '颜色唯一标识'
        primary key,
    color_name  varchar(255)                       null comment '颜色的名字',
    is_enable   tinyint  default 1                 not null comment '品牌是否入驻 0--未入驻 1--已入驻 ',
    is_delete   tinyint  default 0                 not null comment '品牌的逻辑删除字段 0--未删除 1--已删除',
    create_user bigint                             null comment '创建品牌的管理员ID',
    update_user bigint                             null comment '更新品牌的管理员ID',
    create_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '品牌创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '品牌更新时间'
);

create table product_sizes
(
    size_id     bigint                             not null comment '尺寸的ID'
        primary key,
    size_name   varchar(255)                       null comment '尺寸的描述',
    is_enable   tinyint  default 1                 not null comment '品牌是否入驻 0--未入驻 1--已入驻 ',
    is_delete   tinyint  default 0                 not null comment '品牌的逻辑删除字段 0--未删除 1--已删除',
    create_user bigint                             null comment '创建品牌的管理员ID',
    update_user bigint                             null comment '更新品牌的管理员ID',
    create_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '品牌创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '品牌更新时间'
);

create table product_types
(
    type_id     bigint                             not null comment '	类型唯一标识符'
        primary key,
    type_name   varchar(100)                       not null comment '类型名称',
    is_enable   tinyint  default 1                 not null comment '品牌是否入驻 0--未入驻 1--已入驻 ',
    is_delete   tinyint  default 0                 not null comment '品牌的逻辑删除字段 0--未删除 1--已删除',
    create_user bigint                             null comment '创建品牌的管理员ID',
    update_user bigint                             null comment '更新品牌的管理员ID',
    create_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '品牌创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '品牌更新时间'
);

create table products
(
    product_id      bigint                             not null comment '商品唯一标识符'
        primary key,
    name            varchar(255)                       not null comment '商品名称',
    detail_img_urls text                               null comment '商品详情图片',
    price           decimal(10, 2)                     not null comment '商品的价格',
    inventory       int                                not null comment '商品的库存',
    brand_id        bigint                             not null comment '商品的品牌 关联品牌表的逻辑外键',
    type_id         bigint                             not null comment '商品的类型 关联类型表的逻辑外键',
    color_id        bigint                             not null comment '颜色对应的ID',
    size_id         bigint                             not null comment '尺码对应的ID',
    is_enable       tinyint  default 1                 not null comment '商品是否上架 0--未上架 1--已上架',
    is_delete       tinyint  default 0                 not null comment '商品的逻辑删除字段 0--未删除 1--已删除',
    create_user     bigint                             null comment '创建商品的管理员ID',
    update_user     bigint                             null comment '更新商品的管理员ID',
    create_time     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '商品创建时间',
    update_time     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '商品更新时间'
);

create index name_index
    on products (name);

create table refund_requests
(
    request_id        bigint auto_increment comment '退款申请唯一标识符'
        primary key,
    order_id          bigint                             not null comment '关联订单表的逻辑外键',
    refund_amount     decimal(10, 2)                     not null comment '退款金额',
    refund_reason     varchar(255)                       not null comment '退款原因',
    processing_status tinyint  default 0                 not null comment '处理状态，0--待处理 1--已处理',
    request_time      datetime                           not null comment '申请退款时间',
    process_user      bigint                             null comment '处理退款申请的管理员ID',
    process_time      datetime                           null comment '处理退款申请的时间',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '退款申请创建时间',
    update_time       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '退款申请更新时间'
);

create index order_id_index
    on refund_requests (order_id);

create table traffic_reports
(
    report_id        int          not null comment '报表的唯一标识符'
        primary key,
    date             datetime     null comment '	数据对应的日期',
    page_views       varchar(255) null comment '页面浏览量',
    unique_visitors  varchar(255) null comment '独立访客数量',
    average_duration varchar(255) null comment '平均访问持续时间'
);

create table users
(
    user_id     bigint                             not null comment '用户的唯一标识符'
        primary key,
    username    varchar(255)                       not null comment '用户名，唯一',
    password    varchar(255)                       not null comment '密码哈希',
    email       varchar(255)                       not null comment '用户电子邮件',
    role        tinyint  default 0                 not null comment '用户角色 0 -- 普通用户  1 -- 管理员',
    avatar_url  varchar(255)                       null comment '头像URL',
    is_enable   tinyint  default 1                 not null comment '品牌是否入驻 0--未入驻 1--已入驻 ',
    is_delete   tinyint  default 0                 not null comment '品牌的逻辑删除字段 0--未删除 1--已删除',
    create_user bigint                             null comment '创建品牌的管理员ID',
    update_user bigint                             null comment '更新品牌的管理员ID',
    create_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '品牌创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '品牌更新时间'
);

