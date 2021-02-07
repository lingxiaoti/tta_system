drop table tta_supplier;
drop sequence seq_tta_supplier;
create sequence seq_tta_supplier start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
-- Create table
create table TTA_SUPPLIER
(
  supplier_id          INTEGER not null,
  supplier_code        VARCHAR2(50) not null,
  supplier_name        VARCHAR2(230) not null,
  status               CHAR(1) not null,
  is_latent            CHAR(1) not null,
  owner_dept           VARCHAR2(30),
  creation_date        DATE default SYSDATE,
  created_by           INTEGER,
  last_updated_by      INTEGER,
  last_update_date     DATE default SYSDATE,
  last_update_login    INTEGER,
  version_num          INTEGER,
  owner_group          VARCHAR2(30),
  contract_output      VARCHAR2(30),
  purchase_mode        VARCHAR2(30),
  proposal_brand_group VARCHAR2(30),
  dept_name            VARCHAR2(200),
  formal_code          VARCHAR2(30),
  formal_name          VARCHAR2(230)
)
tablespace TTA_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table TTA_SUPPLIER
  is '供应商头表信息表';
-- Add comments to the columns
comment on column TTA_SUPPLIER.supplier_id
  is '主键ID';
comment on column TTA_SUPPLIER.supplier_code
  is '供应商编号';
comment on column TTA_SUPPLIER.supplier_name
  is '供应商名称';
comment on column TTA_SUPPLIER.status
  is '状态';
comment on column TTA_SUPPLIER.is_latent
  is '是否潜在供应商';
comment on column TTA_SUPPLIER.creation_date
  is '创建日期';
comment on column TTA_SUPPLIER.created_by
  is '创建人';
comment on column TTA_SUPPLIER.last_updated_by
  is '更新人';
comment on column TTA_SUPPLIER.last_update_date
  is '更新日期';
comment on column TTA_SUPPLIER.last_update_login
  is '登录人';
comment on column TTA_SUPPLIER.version_num
  is '版本号';
comment on column TTA_SUPPLIER.dept_name
  is '部门名称';
comment on column TTA_SUPPLIER.formal_code
  is '正式供应商_CODE';
comment on column TTA_SUPPLIER.formal_name
  is '正式供应商_NAME';
-- Create/Recreate indexes
create index PK_TTA_SUPPLIER_U1 on TTA_SUPPLIER (SUPPLIER_CODE)
  tablespace TTA_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index PK_TTA_SUPPLIER_U2 on TTA_SUPPLIER (OWNER_DEPT)
  tablespace TTA_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints
alter table TTA_SUPPLIER
  add constraint PK_TTA_SUPPLIER primary key (SUPPLIER_ID)
  using index
  tablespace TTA_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );


drop table tta_rel_supplier;
drop sequence seq_tta_rel_supplier;
create sequence seq_tta_rel_supplier start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
create table tta_rel_supplier 
(
   rel_supplier_id    INTEGER              not null,
   rel_supplier_code  VARCHAR2(50)         not null,
   rel_supplier_name  VARCHAR2(30)         not null,
   rel_id             INTEGER              not null,
   status             CHAR(1)              not null,
   creation_date      DATE DEFAULT SYSDATE,
   created_by         INTEGER,
   last_updated_by    INTEGER,
   last_update_date   DATE DEFAULT SYSDATE,
   last_update_login  INTEGER,
   version_num        INTEGER,
   constraint PK_TTA_REL_SUPPLIER primary key (rel_supplier_id)
);

comment on table tta_rel_supplier is
'关联供应商表';

comment on column tta_rel_supplier.rel_supplier_id is
'主键ID';

comment on column tta_rel_supplier.rel_supplier_code is
'供应商编号';

comment on column tta_rel_supplier.rel_supplier_name is
'供应商名';

comment on column tta_rel_supplier.rel_id is
'外键，tta_base_supplier表id';

comment on column tta_rel_supplier.status is
'状态';

comment on column tta_rel_supplier.creation_date is
'创建日期';

comment on column tta_rel_supplier.created_by is
'创建人';

comment on column tta_rel_supplier.last_updated_by is
'更新人';

comment on column tta_rel_supplier.last_update_date is
'更新日期';

comment on column tta_rel_supplier.last_update_login is
'登录人';

comment on column tta_rel_supplier.version_num is
'版本号';


drop table tta_rel_supplier_dept;
drop sequence seq_tta_rel_supplier_dept;
create sequence seq_tta_rel_supplier_dept start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
create table tta_rel_supplier_dept 
(
   rel_supplier_id    INTEGER              not null,
   rel_dept_code      VARCHAR2(50)         not null,
   rel_dept_name      VARCHAR2(30)         not null,
   rel_id             INTEGER              not null,
   status             CHAR(1)              not null,
   creation_date      DATE DEFAULT SYSDATE,
   created_by         INTEGER,
   last_updated_by    INTEGER,
   last_update_date   DATE DEFAULT SYSDATE,
   last_update_login  INTEGER,
   version_num        INTEGER,
   constraint PK_TTA_REL_SUPPLIER_DEPT primary key (rel_supplier_id)
);

comment on table tta_rel_supplier_dept is
'供应商部门关系表';

comment on column tta_rel_supplier_dept.rel_supplier_id is
'主键ID';

comment on column tta_rel_supplier_dept.rel_dept_code is
'所属关系部门编号';

comment on column tta_rel_supplier_dept.rel_dept_name is
'所属关系部门名称';

comment on column tta_rel_supplier_dept.rel_id is
'外键，tta_base_supplier表id';

comment on column tta_rel_supplier_dept.status is
'状态';

comment on column tta_rel_supplier_dept.creation_date is
'创建日期';

comment on column tta_rel_supplier_dept.created_by is
'创建人';

comment on column tta_rel_supplier_dept.last_updated_by is
'更新人';

comment on column tta_rel_supplier_dept.last_update_date is
'更新日期';

comment on column tta_rel_supplier_dept.last_update_login is
'登录人';

comment on column tta_rel_supplier_dept.version_num is
'版本号';


drop table tta_rel_supplier_brand;
drop sequence seq_tta_rel_supplier_brand;
create sequence seq_tta_rel_supplier_brand start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
-- Create table
create table TTA_REL_SUPPLIER_BRAND
(
  rel_supplier_id   INTEGER not null,
  rel_brand_code    VARCHAR2(50),
  rel_brand_name    VARCHAR2(230) not null,
  rel_id            INTEGER not null,
  status            CHAR(1),
  creation_date     DATE default SYSDATE,
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE default SYSDATE,
  last_update_login INTEGER,
  version_num       INTEGER,
  group_code        VARCHAR2(50),
  dept_code         VARCHAR2(50),
  bu_scorecard      VARCHAR2(150),
  win_top_supplier  VARCHAR2(150),
  kb_scorecard      VARCHAR2(150),
  group_name        VARCHAR2(150),
  dept_name         VARCHAR2(150),
  rel_brand_name_en VARCHAR2(230)
)
tablespace TTA_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table TTA_REL_SUPPLIER_BRAND
  is '供应商对应品牌信息表';
-- Add comments to the columns
comment on column TTA_REL_SUPPLIER_BRAND.rel_supplier_id
  is '主键ID';
comment on column TTA_REL_SUPPLIER_BRAND.rel_brand_code
  is '品牌编号';
comment on column TTA_REL_SUPPLIER_BRAND.rel_brand_name
  is '品牌名称';
comment on column TTA_REL_SUPPLIER_BRAND.rel_id
  is '外键，tta_base_supplier表id';
comment on column TTA_REL_SUPPLIER_BRAND.status
  is '状态';
comment on column TTA_REL_SUPPLIER_BRAND.creation_date
  is '创建日期';
comment on column TTA_REL_SUPPLIER_BRAND.created_by
  is '创建人';
comment on column TTA_REL_SUPPLIER_BRAND.last_updated_by
  is '更新人';
comment on column TTA_REL_SUPPLIER_BRAND.last_update_date
  is '更新日期';
comment on column TTA_REL_SUPPLIER_BRAND.last_update_login
  is '登录人';
comment on column TTA_REL_SUPPLIER_BRAND.version_num
  is '版本号';
comment on column TTA_REL_SUPPLIER_BRAND.dept_name
  is '部门名称';
comment on column TTA_REL_SUPPLIER_BRAND.rel_brand_name_en
  is '品牌名称EN';
-- Create/Recreate primary, unique and foreign key constraints
alter table TTA_REL_SUPPLIER_BRAND
  add constraint PK_TTA_REL_SUPPLIER_BRAND primary key (REL_SUPPLIER_ID)
  using index
  tablespace TTA_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );



/*=======================20190522===============================*/
/* Table: att_taerms_framework_head                           */
/*==============================================================*/
drop table att_taerms_framework_head;
drop sequence seq_att_taerms_framework_head;
create sequence seq_att_taerms_framework_head start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
create table att_taerms_framework_head 
(
   team_framework_id  INTEGER              not null,
   frame_code         VARCHAR(50)          not null,
   year               INTEGER              not null,
   frame_name         VARCHAR2(50),
   bill_status        INTEGER              not null,
   business_version   INTEGER              not null,
   pass_date          DATE,
   effective_date     DATE,
   expiration_date    DATE,
   created_by         INTEGER,
   last_updated_by    INTEGER,
   last_update_date   DATE,
   creation_date      DATE,
   last_update_login  INTEGER,
   version_num        INTEGER,
   constraint PK_ATT_TAERMS_FRAMEWORK_HEAD primary key (team_framework_id)
);

comment on column att_taerms_framework_head.frame_code is
'框架编码，暂时采用年度';

comment on column att_taerms_framework_head.year is
'年度';

comment on column att_taerms_framework_head.frame_name is
'框架名称';

comment on column att_taerms_framework_head.bill_status is
'单据状态（1制作中、2待审批、3审批通过）';

comment on column att_taerms_framework_head.business_version is
'条款框架版本号';

comment on column att_taerms_framework_head.pass_date is
'审批通过时间';

comment on column att_taerms_framework_head.effective_date is
'版本生效日期';

comment on column att_taerms_framework_head.expiration_date is
'版本失效日期';

comment on column att_taerms_framework_head.created_by is
'创建人';

comment on column att_taerms_framework_head.last_updated_by is
'更新人';

comment on column att_taerms_framework_head.last_update_date is
'更新日期';

comment on column att_taerms_framework_head.creation_date is
'创建日期';

comment on column att_taerms_framework_head.last_update_login is
'登录人';

comment on column att_taerms_framework_head.version_num is
'版本号';

/*==============================================================*/
/* Table: att_taerms_framework_head_h                         */
/*==============================================================*/

drop table att_taerms_framework_head_h;
drop sequence seq_att_taerms_framework_head_h;
create sequence seq_att_taerms_framework_head_h start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;

create table att_taerms_framework_head_h 
(
   team_framework_id  INTEGER              not null,
   frame_code         VARCHAR(50)          not null,
   year               INTEGER              not null,
   frame_name         VARCHAR2(50),
   bill_status        INTEGER              not null,
   business_version   INTEGER              not null,
   pass_date          DATE,
   effective_date     DATE,
   expiration_date    DATE,
   resouce_id         INTEGER,
   resouce_business_version INTEGER,
   created_by         INTEGER,
   last_updated_by    INTEGER,
   last_update_date   DATE,
   creation_date      DATE,
   last_update_login  INTEGER,
   version_num        INTEGER,
   constraint PK_ATT_TAERMS_FRAMEWORK_HEAD_H primary key (team_framework_id)
);

comment on column att_taerms_framework_head_h.frame_code is
'框架编码，暂时采用年度';

comment on column att_taerms_framework_head_h.year is
'年度';

comment on column att_taerms_framework_head_h.frame_name is
'框架名称';

comment on column att_taerms_framework_head_h.bill_status is
'单据状态（1制作中、2待审批、3审批通过）';

comment on column att_taerms_framework_head_h.business_version is
'条款框架版本号';

comment on column att_taerms_framework_head_h.pass_date is
'审批通过时间';

comment on column att_taerms_framework_head_h.effective_date is
'版本生效日期';

comment on column att_taerms_framework_head_h.expiration_date is
'版本失效日期';

comment on column att_taerms_framework_head_h.resouce_id is
'来源版本Id，对应复制原纪录id';

comment on column att_taerms_framework_head_h.resouce_business_version is
'来源版本号，对应复制原纪录版本号';

comment on column att_taerms_framework_head_h.created_by is
'创建人';

comment on column att_taerms_framework_head_h.last_updated_by is
'更新人';

comment on column att_taerms_framework_head_h.last_update_date is
'更新日期';

comment on column att_taerms_framework_head_h.creation_date is
'创建日期';

comment on column att_taerms_framework_head_h.last_update_login is
'登录人';

comment on column att_taerms_framework_head_h.version_num is
'版本号';


/*==============================================================*/
/* Table: att_clause_tree                                     */
/*==============================================================*/
drop table att_clause_tree;
drop sequence seq_att_clause_tree;
create sequence seq_att_clause_tree start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
create table att_clause_tree 
(
   clause_id          INTEGER              not null,
   team_framework_id  INTEGER,
   clause_code        VARCHAR(50)          not null,
   clause_cn          INTEGER              not null,
   clause_en          VARCHAR2(50),
   status             INTEGER              not null,
   parent_id          INTEGER,
   pass_date          DATE,
   effective_date     DATE,
   is_leaf            DATE,
   is_hierarchy_show  INT                  not null,
   payment_method     VARCHAR(100)         not null,
   business_type      VARCHAR(100)         not null,
   expression_value   VARCHAR2(800)        not null,
   business_version   INTEGER              not null,
   order_no           INTEGER              not null,
   created_by         INTEGER,
   last_updated_by    INTEGER,
   last_update_date   DATE,
   creation_date      DATE,
   last_update_login  INTEGER,
   version_num        INTEGER,
   constraint PK_ATT_CLAUSE_TREE primary key (clause_id)
);

comment on column att_clause_tree.clause_id is
'主键';

comment on column att_clause_tree.team_framework_id is
'外键，引用att_taerms_framework_head表id';

comment on column att_clause_tree.clause_code is
'框架编码，暂时采用年度';

comment on column att_clause_tree.clause_cn is
'年度';

comment on column att_clause_tree.clause_en is
'条款英文名称';

comment on column att_clause_tree.status is
'是否生效:1有效，0无效';

comment on column att_clause_tree.parent_id is
'上层条款id';

comment on column att_clause_tree.pass_date is
'审批通过时间';

comment on column att_clause_tree.effective_date is
'版本生效日期';

comment on column att_clause_tree.is_leaf is
'是否底层节点';

comment on column att_clause_tree.is_hierarchy_show is
'是否同层级显示';

comment on column att_clause_tree.payment_method is
'收入方式';

comment on column att_clause_tree.business_type is
'业务类型';

comment on column att_clause_tree.expression_value is
'表达式结果值';

comment on column att_clause_tree.business_version is
'条款版本号';

comment on column att_clause_tree.order_no is
'排版序号';

comment on column att_clause_tree.created_by is
'创建人';

comment on column att_clause_tree.last_updated_by is
'更新人';

comment on column att_clause_tree.last_update_date is
'更新日期';

comment on column att_clause_tree.creation_date is
'创建日期';

comment on column att_clause_tree.last_update_login is
'登录人';

comment on column att_clause_tree.version_num is
'版本号';

/*==============================================================*/
/* Table: att_clause_tree_h                                   */
/*==============================================================*/
drop table att_clause_tree_h;
drop sequence seq_att_clause_tree_h;
create sequence seq_att_clause_tree_h start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
create table att_clause_tree_h 
(
   clause_id          INTEGER              not null,
   team_framework_id  INTEGER,
   clause_code        VARCHAR(50)          not null,
   clause_cn          INTEGER              not null,
   clause_en          VARCHAR2(50),
   status             INTEGER              not null,
   parent_id          INTEGER,
   pass_date          DATE,
   effective_date     DATE,
   is_leaf            DATE,
   is_hierarchy_show  INT                  not null,
   payment_method     VARCHAR(100)         not null,
   business_type      VARCHAR(100)         not null,
   expression_value   VARCHAR2(800)        not null,
   business_version   INTEGER              not null,
   resouce_id         INTEGER,
   resouce_business_version INTEGER,
   order_no           INTEGER              not null,
   created_by         INTEGER,
   last_updated_by    INTEGER,
   last_update_date   DATE,
   creation_date      DATE,
   last_update_login  INTEGER,
   version_num        INTEGER,
   constraint PK_ATT_CLAUSE_TREE_H primary key (clause_id)
);

comment on column att_clause_tree_h.clause_id is
'主键';

comment on column att_clause_tree_h.team_framework_id is
'外键，引用att_taerms_framework_head表id';

comment on column att_clause_tree_h.clause_code is
'框架编码，暂时采用年度';

comment on column att_clause_tree_h.clause_cn is
'年度';

comment on column att_clause_tree_h.clause_en is
'条款英文名称';

comment on column att_clause_tree_h.status is
'是否生效:1有效，0无效';

comment on column att_clause_tree_h.parent_id is
'上层条款id';

comment on column att_clause_tree_h.pass_date is
'审批通过时间';

comment on column att_clause_tree_h.effective_date is
'版本生效日期';

comment on column att_clause_tree_h.is_leaf is
'是否底层节点';

comment on column att_clause_tree_h.is_hierarchy_show is
'是否同层级显示';

comment on column att_clause_tree_h.payment_method is
'收入方式';

comment on column att_clause_tree_h.business_type is
'业务类型';

comment on column att_clause_tree_h.expression_value is
'表达式结果值';

comment on column att_clause_tree_h.business_version is
'条款版本号';

comment on column att_clause_tree_h.resouce_id is
'来源版本Id，对应复制原纪录id';

comment on column att_clause_tree_h.resouce_business_version is
'来源版本号，对应复制原纪录版本号';

comment on column att_clause_tree_h.order_no is
'排版序号';

comment on column att_clause_tree_h.created_by is
'创建人';

comment on column att_clause_tree_h.last_updated_by is
'更新人';

comment on column att_clause_tree_h.last_update_date is
'更新日期';

comment on column att_clause_tree_h.creation_date is
'创建日期';

comment on column att_clause_tree_h.last_update_login is
'登录人';

comment on column att_clause_tree_h.version_num is
'版本号';


/*==============================================================*/
/* Table: att_clause_expression                              */
/*==============================================================*/
/*
drop table att_clause_expression;
drop sequence seq_att_clause_expression;
create sequence seq_att_clause_expression start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
create table att_clause_expression 
(
   expression_id      INTEGER              not null,
   r_clause_id        INTEGER,
   local_value        VARCHAR(200)         not null,
   global_value       VARCHAR(200)         not null,
   symbol          VARCHAR2(5),
   input_value        VARCHAR2(200)        not null,
   order_no           INTEGER              not null,
   created_by         INTEGER,
   last_updated_by    INTEGER,
   last_update_date   DATE,
   creation_date      DATE,
   last_update_login  INTEGER,
   version_num        INTEGER,
   constraint PK_att_clause_expression primary key (expression_id)
);

comment on column att_clause_expression.expression_id is
'主键';

comment on column att_clause_expression.r_clause_id is
'外键，引用att_clause_tree表id';

comment on column att_clause_expression.local_value is
'全局值';

comment on column att_clause_expression.global_value is
'局部值';

comment on column att_clause_expression.symbol is
'符号';

comment on column att_clause_expression.input_value is
'输入值';

comment on column att_clause_expression.order_no is
'排版序号';

comment on column att_clause_expression.created_by is
'创建人';

comment on column att_clause_expression.last_updated_by is
'更新人';

comment on column att_clause_expression.last_update_date is
'更新日期';

comment on column att_clause_expression.creation_date is
'创建日期';

comment on column att_clause_expression.last_update_login is
'登录人';

comment on column att_clause_expression.version_num is
'版本号';
*/

/*==============================================================*/
/* Table: att_clause_expression_h                             */
/*==============================================================*/

/*
drop table att_clause_expression_h;
drop sequence seq_att_clause_expression_h;
create sequence seq_att_clause_expression_h start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;

create table att_clause_expression_h 
(
   expression_id      INTEGER              not null,
   r_clause_id        INTEGER,
   local_value        VARCHAR(200)         not null,
   global_value       VARCHAR(200)         not null,
   symbol          VARCHAR2(5),
   input_value        VARCHAR2(200)        not null,
   order_no           INTEGER              not null,
   resouce_id         INTEGER,
   resouce_business_version INTEGER,
   created_by         INTEGER,
   last_updated_by    INTEGER,
   last_update_date   DATE,
   creation_date      DATE,
   last_update_login  INTEGER,
   version_num        INTEGER,
   constraint PK_ATT_CLAUSE_EXPRESSION_H primary key (expression_id)
);

comment on column att_clause_expression_h.expression_id is
'主键';

comment on column att_clause_expression_h.r_clause_id is
'外键，引用att_clause_tree表id';

comment on column att_clause_expression_h.local_value is
'全局值';

comment on column att_clause_expression_h.global_value is
'局部值';

comment on column att_clause_expression_h.symbol is
'符号';

comment on column att_clause_expression_h.input_value is
'输入值';

comment on column att_clause_expression_h.order_no is
'排版序号';

comment on column att_clause_expression_h.resouce_id is
'来源版本Id，对应复制原纪录id';

comment on column att_clause_expression_h.resouce_business_version is
'来源版本号，对应复制原纪录版本号';

comment on column att_clause_expression_h.created_by is
'创建人';

comment on column att_clause_expression_h.last_updated_by is
'更新人';

comment on column att_clause_expression_h.last_update_date is
'更新日期';

comment on column att_clause_expression_h.creation_date is
'创建日期';

comment on column att_clause_expression_h.last_update_login is
'登录人';

comment on column att_clause_expression_h.version_num is
'版本号';
*/

drop table temp_param_def;
drop sequence seq_temp_param_def;
create sequence seq_temp_param_def start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
create table temp_param_def 
(
   param_id           INTEGER              not null,
   param_key          VARCHAR2(200)        not null,
   param_content       CLOB                 not null,
   is_sql             VARCHAR2(2)              not null,
   remark             VARCHAR2(100)        not null,
   created_by         INTEGER,
   last_updated_by    INTEGER,
   last_update_date   DATE default sysdate,
   creation_date      DATE default sysdate,
   last_update_login  INTEGER,
   version_num        INTEGER,
   constraint PK_TEMP_PARAM_DEF primary key (param_id)
);

comment on column temp_param_def.param_id is
'主键id';

comment on column temp_param_def.remark is
'描述信息';

comment on column temp_param_def.param_key is
'定义模板的key值';

comment on column temp_param_def.param_content is
'定义模板的内容值';

comment on column temp_param_def.is_sql is
'是否是sql';

comment on column temp_param_def.created_by is
'创建人';

comment on column temp_param_def.last_updated_by is
'更新人';

comment on column temp_param_def.last_update_date is
'更新日期';

comment on column temp_param_def.creation_date is
'创建日期';

comment on column temp_param_def.last_update_login is
'登录人';

comment on column temp_param_def.version_num is
'版本号';


-- 规则表定义
drop table temp_rule_def;
drop sequence seq_temp_rule_def;
create sequence seq_temp_rule_def start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
create table temp_rule_def 
(
   rul_id             INTEGER              not null,
   rule_name          VARCHAR2(100)        not null,
   is_enable          INTEGER              not null,
   sole_resouce_type  INT                  not null,
   sole_product_type  INT                  not null,
   is_include_ec      VARCHAR(2)           not null,
   is_include_special VARCHAR(2)           not null,
   file_url           VARCHAR2(500)        not null,
   remark            VARCHAR(200),
   created_by         INTEGER,
   last_updated_by    INTEGER,
   last_update_date   DATE default sysdate,
   creation_date      DATE default sysdate,
   last_update_login  INTEGER,
   version_num        INTEGER,
   constraint PK_TEMP_RULE_DEF primary key (rul_id)
);

comment on column temp_rule_def.rul_id is
'主键id';

comment on column temp_rule_def.rule_name is
'规则名称定义';

comment on column temp_rule_def.is_enable is
'是否启用（Y启用,N禁用）';

comment on column temp_rule_def.sole_resouce_type is
'1.全渠道独家 2.健与美渠道，药房，大卖场和超市独家 3.健与美渠道，大卖场和超市独家 4.健与美渠道独家';

comment on column temp_rule_def.sole_product_type is
'1.全品牌产品 2.指定产品';

comment on column temp_rule_def.is_include_ec is
'Y/N';

comment on column temp_rule_def.is_include_special is
'Y/N';

comment on column temp_rule_def.file_url is
'规则匹配的文件url';

comment on column temp_rule_def.remark is
'描述信息';

comment on column temp_rule_def.created_by is
'创建人';

comment on column temp_rule_def.last_updated_by is
'更新人';

comment on column temp_rule_def.last_update_date is
'更新日期';

comment on column temp_rule_def.creation_date is
'创建日期';

comment on column temp_rule_def.last_update_login is
'登录人';

comment on column temp_rule_def.version_num is
'版本号';

create unique index unique_rul_def on temp_rule_def (sole_resouce_type,sole_product_type,is_include_ec,is_include_special);


-- 模板参数规则中间表
drop table temp_param_rule_midle;
drop sequence seq_temp_param_rule_midle;
create sequence seq_temp_param_rule_midle start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
create table temp_param_rule_midle 
(
   rule_param_id      INTEGER              not null,
   rule_id            INTEGER              not null,
   param_id           INTEGER              not null,
   created_by         INTEGER,
   last_updated_by    INTEGER,
   last_update_date   DATE default sysdate,
   creation_date      DATE default sysdate,
   last_update_login  INTEGER,
   version_num        INTEGER,
   constraint PK_TEMP_PARAM_RULE_MIDLE primary key (rule_param_id)
);

comment on column temp_param_rule_midle.rule_param_id is
'主键id';

comment on column temp_param_rule_midle.rule_id is
'模板规则id';

comment on column temp_param_rule_midle.param_id is
'模板参数Id';

comment on column temp_param_rule_midle.created_by is
'创建人';

comment on column temp_param_rule_midle.last_updated_by is
'更新人';

comment on column temp_param_rule_midle.last_update_date is
'更新日期';

comment on column temp_param_rule_midle.creation_date is
'创建日期';

comment on column temp_param_rule_midle.last_update_login is
'登录人';

comment on column temp_param_rule_midle.version_num is
'版本号';

-- 文件规则模板
drop table    rule_file_template;
drop sequence seq_rule_file_template;
create sequence seq_rule_file_template start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
create table rule_file_template 
(
   file_temp_id       integer              not null,
   file_temp_name     varchar2(100)        not null,
   file_type          varchar2(50)         not null,
   file_content       clob                 not null,
   business_type      varchar2(50)         not null,
   created_by         integer,
   last_updated_by    integer,
   last_update_date   date default sysdate,
   creation_date      date default sysdate,
   last_update_login  integer,
   version_num        integer,
   constraint pk_rule_file_template primary key (file_temp_id)
);

comment on column rule_file_template.file_temp_id is
'主键id';

comment on column rule_file_template.file_temp_name is
'文件模板名称';

comment on column rule_file_template.file_type is
'文件模板类型';

comment on column rule_file_template.file_content is
'文件内容';

comment on column rule_file_template.business_type is
'业务类型';

comment on column temp_param_rule_midle.created_by is
'创建人';

comment on column temp_param_rule_midle.last_updated_by is
'更新人';

comment on column temp_param_rule_midle.last_update_date is
'更新日期';

comment on column temp_param_rule_midle.creation_date is
'创建日期';

comment on column temp_param_rule_midle.last_update_login is
'登录人';

comment on column temp_param_rule_midle.version_num is
'版本号';



-- 实际发生费用规则设置
drop table    tta_actual_cost_rule;
drop sequence seq_tta_actual_cost_rule;
create sequence seq_tta_actual_cost_rule start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
create table tta_actual_cost_rule 
(
   id                 integer              not null,
   oi_type            varchar(100)         not null,
   scenario           varchar(100),
   oi_bucket          varchar(100),
   is_appoint_detail  varchar2(2),
   rule_source_data   varchar(200),
   allocation_base    varchar(100)         not null,
   creation_date      date  default sysdate,
   created_by         integer,
   last_updated_by    integer,
   last_update_date   date  default sysdate,
   last_update_login  integer,
   version_num        integer,
   constraint pk_tta_actual_cost_rule primary key (id)
);

comment on column tta_actual_cost_rule.id is
'主键';

comment on column tta_actual_cost_rule.oi_type is
'beoi/sroi/aboi/other oi';

comment on column tta_actual_cost_rule.scenario is
'供应商属性:normal supplier/express supplir';

comment on column tta_actual_cost_rule.oi_bucket is
'条款小类,aboi及other oi 必填，选择内容来自条款管理中收入方式+条款英文名称与选择的oi type 过滤取唯一性';

comment on column tta_actual_cost_rule.is_appoint_detail is
'是否费用指定物料明细, y/n';

comment on column tta_actual_cost_rule.rule_source_data is
'lowest level of source data，数据规则';

comment on column tta_actual_cost_rule.allocation_base is
'系统接口';

comment on column tta_actual_cost_rule.creation_date is
'创建日期';

comment on column tta_actual_cost_rule.created_by is
'创建人';

comment on column tta_actual_cost_rule.last_updated_by is
'更新人';

comment on column tta_actual_cost_rule.last_update_date is
'更新日期';

comment on column tta_actual_cost_rule.last_update_login is
'登录人';

comment on column tta_actual_cost_rule.version_num is
'版本号';


