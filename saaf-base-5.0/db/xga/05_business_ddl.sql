 delete from base_common_txn_confirm;
 commit;
-- 注意：动态创建表需要管理员执行：grant create any table to 用户;
-- Create table
drop table TTA_PURCHASE_IN;
create table TTA_PURCHASE_IN
(
  receive_date         VARCHAR2(50),
  vendor_nbr           VARCHAR2(50),
  vendor_name          VARCHAR2(200),
  location             NUMBER(20),
  item_nbr             VARCHAR2(50),
  item_desc_en         VARCHAR2(800),
  item_desc_cn         VARCHAR2(500),
  receiving_qty        NUMBER(16,4),
  receiving_amount     NUMBER,
  cancel_receiving_qty NUMBER,
  cancel_receiving_amt NUMBER,
  currency             VARCHAR2(20),
  purch_type           VARCHAR2(20),
  location_type        VARCHAR2(20),
  po_nbr               VARCHAR2(50),
  dept_code            NUMBER(20),
  dept_desc            VARCHAR2(50),
  group_code           NUMBER(20),
  group_desc           VARCHAR2(100),
  brand_code           VARCHAR2(100),
  brand_cn             VARCHAR2(500),
  brand_en             VARCHAR2(500),
  split_supplier_code  VARCHAR2(50),
  split_supplier_name  VARCHAR2(100),
  split_count          INTEGER default 0,
  version_num          INTEGER default 0,
  creation_date        DATE default sysdate,
  created_by           INTEGER default -1,
  last_updated_by      INTEGER default -1,
  last_update_date     DATE default sysdate,
  last_update_login    INTEGER default -1
);
-- Add comments to the table
comment on table TTA_PURCHASE_IN
  is 'PURCHASE 数据接口模板表,其他表根据此模板表创建';
-- Add comments to the columns
comment on column TTA_PURCHASE_IN.receive_date
  is '收货日期';
comment on column TTA_PURCHASE_IN.vendor_nbr
  is '供应商编号';
comment on column TTA_PURCHASE_IN.vendor_name
  is '供应商名称';
comment on column TTA_PURCHASE_IN.location
  is '仓库地址';
comment on column TTA_PURCHASE_IN.item_nbr
  is 'ITEM编号';
comment on column TTA_PURCHASE_IN.receiving_qty
  is '收货数量';
comment on column TTA_PURCHASE_IN.receiving_amount
  is '收货金额';
comment on column TTA_PURCHASE_IN.cancel_receiving_qty
  is '采购退货数量';
comment on column TTA_PURCHASE_IN.cancel_receiving_amt
  is '采购退货金额';
comment on column TTA_PURCHASE_IN.currency
  is '币种,RMB';
comment on column TTA_PURCHASE_IN.purch_type
  is '采购模式(purchase、consignment数据)';
comment on column TTA_PURCHASE_IN.location_type
  is '1.S   2.W(区分是否DSD类型)';
comment on column TTA_PURCHASE_IN.po_nbr
  is 'PO单号';
comment on column TTA_PURCHASE_IN.split_supplier_code
  is '拆分后的供应商编码';
comment on column TTA_PURCHASE_IN.split_supplier_name
  is '拆分后的供应商名称';
comment on column TTA_PURCHASE_IN.split_count
  is '拆分次数';


-- Create table
drop table TTA_VENDOR_IN;
create table TTA_VENDOR_IN
(
  vendor_nbr        VARCHAR2(100),
  vendor_name       VARCHAR2(500),
  status            VARCHAR2(500),
  version_num       INTEGER default 0,
  creation_date     DATE default sysdate,
  created_by        INTEGER default -1,
  last_updated_by   INTEGER default -1,
  last_update_date  DATE default sysdate,
  last_update_login INTEGER default -1
);
-- Add comments to the table
comment on table TTA_VENDOR_IN
  is '供应商接口表';
-- Add comments to the columns
comment on column TTA_VENDOR_IN.vendor_nbr
  is '供应商编码';
comment on column TTA_VENDOR_IN.vendor_name
  is '供应商名称';
comment on column TTA_VENDOR_IN.status
  is '状态';


drop table TTA_SALES_IN;
create table TTA_SALES_IN
(
  tran_date         NUMBER(10) not null,
  vendor_nbr        NUMBER,
  store             VARCHAR2(50),
  item              VARCHAR2(50),
  sales_qty         NUMBER,
  sales_amt         NUMBER(15,6),
  sales_exclude_amt NUMBER(15,6),
  purch_type        VARCHAR2(50),
  currency          VARCHAR2(10),
  cost              NUMBER(15,6),
  gp                NUMBER(15,6),
  version_num       INTEGER,
  creation_date     DATE default sysdate,
  created_by        INTEGER default -1,
  last_updated_by   INTEGER default -1,
  last_update_date  DATE default sysdate,
  last_update_login INTEGER  default -1
);
-- Add comments to the table
comment on table TTA_SALES_IN
  is 'TTA_SALES数据模板表';
-- Add comments to the columns
comment on column TTA_SALES_IN.tran_date
  is '年月日';
comment on column TTA_SALES_IN.vendor_nbr
  is '供应商编码';
comment on column TTA_SALES_IN.store
  is '门店';
comment on column TTA_SALES_IN.item
  is '物料';
comment on column TTA_SALES_IN.sales_qty
  is '销售数量';
comment on column TTA_SALES_IN.sales_amt
  is '销售金额（含税）';
comment on column TTA_SALES_IN.sales_exclude_amt
  is '销售金额（不含税）';
comment on column TTA_SALES_IN.purch_type
  is '采购模式PURCHASE\CVW\CONSIGNMENT';
comment on column TTA_SALES_IN.currency
  is '币种,RMB';
comment on column TTA_SALES_IN.cost
  is '成本';
comment on column TTA_SALES_IN.gp
  is '毛利';


drop table TTA_SHOP_MARKET_IN;
-- Create table
create table TTA_SHOP_MARKET_IN
(
  shop_code         number(20),
  shop_name         VARCHAR2(100),
  city_name         VARCHAR2(100),
  province_name     VARCHAR2(100),
  market_name       VARCHAR2(200),
  area              VARCHAR2(200),
  channel           VARCHAR2(100),
  shop_level        VARCHAR2(200),
  open_shop         NUMBER(10),
  close_shop        NUMBER(10),
  warehouse_code    NUMBER(20),
  warehouse_name    VARCHAR2(200),
  update_date       NUMBER(10),
  version_num       INTEGER,
  creation_date     DATE default sysdate,
  created_by        INTEGER default -1,
  last_updated_by   INTEGER default -1,
  last_update_date  DATE default sysdate,
  last_update_login INTEGER default -1
);
-- Add comments to the table
comment on table TTA_SHOP_MARKET_IN
  is '店铺对应市场';
-- Add comments to the columns
comment on column TTA_SHOP_MARKET_IN.shop_code
  is '店铺编码';
comment on column TTA_SHOP_MARKET_IN.shop_name
  is '店铺';
comment on column TTA_SHOP_MARKET_IN.city_name
  is '城市';
comment on column TTA_SHOP_MARKET_IN.province_name
  is '省份';
comment on column TTA_SHOP_MARKET_IN.market_name
  is '市场';
comment on column TTA_SHOP_MARKET_IN.area
  is '区域';
comment on column TTA_SHOP_MARKET_IN.channel
  is '渠道  Supper star/Star等';
comment on column TTA_SHOP_MARKET_IN.shop_level
  is '店铺级别';
comment on column TTA_SHOP_MARKET_IN.open_shop
  is '店铺开店时间';
comment on column TTA_SHOP_MARKET_IN.close_shop
  is '店铺关店时间';
comment on column TTA_SHOP_MARKET_IN.warehouse_code
  is '仓库编号';
comment on column TTA_SHOP_MARKET_IN.warehouse_name
  is '仓库名称';
comment on column TTA_SHOP_MARKET_IN.update_date
  is '外围系统的更新时间';

create table TTA_STOCK
(
   ID                   INTEGER              not null,
   YEAR_MONTH_DAY       DATE,
   ITEM                 VARCHAR(50),
   STORE_QTY            INTEGER,
   STORE_AMT            NUMBER(8,2),
   STORE_EXC_AMT        NUMBER(8,2),
   STOCK_QTY            INTEGER,
   STOCK_AMT            NUMBER(8,2),
   STOCK_EXC_AMT        NUMBER(8,2),
   RSV_QTY              INTEGER,
   RSV_AMT              NUMBER(8,2),
   RSV_EXC_AMT          NUMBER(8,2),
   TTL_QTY              NUMBER(8,2),
   TTL_AMT              NUMBER(8,2),
   TTL_EXC_AMT          NUMBER(8,2),
   VERSION_NUM          INTEGER              default NULL,
   CREATION_DATE        DATE                 default sysdate not null,
   CREATED_BY           INTEGER              default -1 not null,
   LAST_UPDATED_BY      INTEGER              default -1 not null,
   LAST_UPDATE_DATE     DATE                 default sysdate not null,
   LAST_UPDATE_LOGIN    INTEGER              default NULL,
   constraint PK_TTA_STOCK primary key (ID)
)
;

comment on table TTA_STOCK is
'库存数据表'
;

comment on column TTA_STOCK.ID is
'主键'
;

comment on column TTA_STOCK.YEAR_MONTH_DAY is
'财政月'
;

comment on column TTA_STOCK.ITEM is
'ITEM'
;

comment on column TTA_STOCK.STORE_QTY is
'店铺库存数量'
;

comment on column TTA_STOCK.STORE_AMT is
'店铺库存金额(含税)'
;

comment on column TTA_STOCK.STORE_EXC_AMT is
'店铺库存金额(不含税)'
;

comment on column TTA_STOCK.STOCK_QTY is
'仓库库存数量'
;

comment on column TTA_STOCK.STOCK_AMT is
'仓库库存金额（含税）'
;

comment on column TTA_STOCK.STOCK_EXC_AMT is
'仓库库存金额（不含税）'
;

comment on column TTA_STOCK.RSV_QTY is
'RSV数量'
;

comment on column TTA_STOCK.RSV_AMT is
'RSV金额（含税）'
;

comment on column TTA_STOCK.RSV_EXC_AMT is
'RSV金额(不含税)'
;

comment on column TTA_STOCK.TTL_QTY is
'总库存数量'
;

comment on column TTA_STOCK.TTL_AMT is
'总库存金额（含税）'
;

comment on column TTA_STOCK.TTL_EXC_AMT is
'总库存金额（不含税）'
;

drop table tta_questionnaire_line;
drop sequence seq_tta_questionnaire_line;

drop  table  tta_question_header;
drop  sequence seq_tta_question_header;
create sequence seq_tta_question_header start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;

-- Create table
drop table tta_question_header
create table tta_question_header
(
  q_header_id       NUMBER(9) not null,
  serial_number     NUMBER,
  project_type      VARCHAR2(100),
  project_cn_title  VARCHAR2(500),
  project_en_title  VARCHAR2(500),
  is_enable         VARCHAR2(2) default 'Y',
  description       VARCHAR2(500),
  version_num       NUMBER,
  creation_date     DATE default sysdate not null,
  created_by        NUMBER default -1 not null,
  last_updated_by   NUMBER default -1 not null,
  last_update_date  DATE default sysdate not null,
  last_update_login NUMBER
);

alter table TTA_QUESTION_HEADER add constraint PK_Q_HEADER_ID primary key (Q_HEADER_ID);
-- Add comments to the columns 
comment on column TTA_QUESTION_HEADER.q_header_id
  is '表ID，主键，供其他表做外键';
comment on column TTA_QUESTION_HEADER.serial_number
  is '题目序号';
comment on column TTA_QUESTION_HEADER.project_type
  is '题目类型(single_selection 单选/multi_selection 多选)';
comment on column TTA_QUESTION_HEADER.project_cn_title
  is '题目中文标题';
comment on column TTA_QUESTION_HEADER.project_en_title
  is '题目英文标题';
comment on column TTA_QUESTION_HEADER.is_enable
  is 'Y/N 是否启用';
comment on column TTA_QUESTION_HEADER.description
  is '说明、备注';


  
drop  table  tta_questionnaire_choice;
drop  sequence seq_tta_question_choice_line;
create sequence seq_tta_question_choice_line start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
drop  table  tta_question_choice_line;

drop  table  tta_question_choice_line;
create table tta_question_choice_line
(
  choice_line_id    INTEGER not null,
  q_header_id       INTEGER not null,
  serial_number     INTEGER,
  choice_type       VARCHAR2(20),
  choice_cn_content VARCHAR2(500),
  choice_en_content VARCHAR2(500),
  is_show_child     VARCHAR2(2),
  is_enable         VARCHAR2(2),
  rule_id           INTEGER,
  version_num       INTEGER,
  creation_date     DATE default sysdate not null,
  created_by        INTEGER default -1 not null,
  last_updated_by   INTEGER default -1 not null,
  last_update_date  DATE default sysdate not null,
  last_update_login INTEGER,
  qn_choice         VARCHAR2(50),
  parent_id         INTEGER default 0
);
alter table TTA_QUESTION_CHOICE_LINE add constraint PK_CHOICE_LINE_ID primary key (CHOICE_LINE_ID);

-- Add comments to the columns
comment on column TTA_QUESTION_CHOICE_LINE.choice_line_id
  is '主键';
comment on column TTA_QUESTION_CHOICE_LINE.q_header_id
  is '关联tta_question_header主键ID';
comment on column TTA_QUESTION_CHOICE_LINE.serial_number
  is '序号';
comment on column TTA_QUESTION_CHOICE_LINE.choice_type
  is '1：自动计算,2:文本,3:值,4:值+自动计算,5:值+文本';
comment on column TTA_QUESTION_CHOICE_LINE.choice_cn_content
  is '选项中文名称';
comment on column TTA_QUESTION_CHOICE_LINE.choice_en_content
  is '选项英文名称';
comment on column TTA_QUESTION_CHOICE_LINE.is_show_child
  is '是否直接显示下级题目,Y/N';
comment on column TTA_QUESTION_CHOICE_LINE.is_enable
  is '应用规则TTA_BASE_RULE表Id';
comment on column TTA_QUESTION_CHOICE_LINE.qn_choice
  is '选项序号，如A,B,C..';
comment on column TTA_QUESTION_CHOICE_LINE.parent_id
  is '父ID';



create sequence SEQ_TTA_TEST_QUESTION_HEADER start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
DROP TABLE TTA_TEST_QUESTION_HEADER;
create table TTA_TEST_QUESTION_HEADER
(
  q_header_id       INTEGER not null,
  PROPOSAL_ID         INTEGER not null, -- proposalId
  serial_number     INTEGER,
  project_type      VARCHAR2(50),
  project_cn_title  VARCHAR2(500),
  project_en_title  VARCHAR2(500),
  is_enable         VARCHAR2(5) default 'Y',
  status            INTEGER  default '0' not null, -- 0:初始化状态 1:保存 2:提交状态
  description       VARCHAR2(500),
  source_q_header_id INTEGER NOT null, -- 来源模板id
  version_num       INTEGER,
  creation_date     DATE default sysdate not null,
  created_by        INTEGER default -1 not null,
  last_updated_by   INTEGER default -1 not null,
  last_update_date  DATE default sysdate not null,
  last_update_login INTEGER
);
-- Add comments to the columns
comment on column TTA_TEST_QUESTION_HEADER.q_header_id
  is '表ID，主键，供其他表做外键';
comment on column TTA_TEST_QUESTION_HEADER.serial_number
  is '题目序号';
comment on column TTA_TEST_QUESTION_HEADER.project_type
  is '题目类型(single_selection 单选/multi_selection 多选)';
comment on column TTA_TEST_QUESTION_HEADER.project_cn_title
  is '题目中文标题';
comment on column TTA_TEST_QUESTION_HEADER.project_en_title
  is '题目英文标题';
comment on column TTA_TEST_QUESTION_HEADER.is_enable
  is 'Y/N 是否启用';
comment on column TTA_TEST_QUESTION_HEADER.description
  is '说明、备注';
comment on column TTA_TEST_QUESTION_HEADER.status
  is '0:初始化状态 1:保存 2:提交状态';
comment on column TTA_TEST_QUESTION_HEADER.source_q_header_id
  is '来源模板表原始id';


-- Create table
create sequence SEQ_TTA_TEST_QUESTION_CHOICE start with 1 increment by 1 nomaxvalue nominvalue nocycle nocache;
DROP TABLE TTA_TEST_QUESTION_CHOICE_LINE;
create table TTA_TEST_QUESTION_CHOICE_LINE
(
  choice_line_id    INTEGER not null,
  q_header_id       INTEGER not null,
  serial_number     INTEGER,
  choice_type       VARCHAR2(50),
  choice_cn_content VARCHAR2(500),
  choice_en_content VARCHAR2(500),
  is_show_child     VARCHAR2(2),
  is_enable         VARCHAR2(2),
  status            INTEGER  default '0' not null,
  qn_choice         VARCHAR2(100),
  rule_id           INTEGER,
  parent_id         INTEGER default 0,
  source_choice_line_id INTEGER NOT null,
  version_num       INTEGER,
  creation_date     DATE default sysdate not null,
  created_by        INTEGER default -1 not null,
  last_updated_by   INTEGER default -1 not null,
  last_update_date  DATE default sysdate not null,
  last_update_login INTEGER
);
-- Add comments to the columns
comment on column TTA_TEST_QUESTION_CHOICE_LINE.choice_line_id
  is '主键';
comment on column TTA_TEST_QUESTION_CHOICE_LINE.q_header_id
  is '关联TTA_TEST_QUESTION_HEADER主键ID';
comment on column TTA_TEST_QUESTION_CHOICE_LINE.serial_number
  is '序号';
comment on column TTA_TEST_QUESTION_CHOICE_LINE.choice_type
  is '1：自动计算,2:文本,3:值,4:值+自动计算,5:值+文本';
comment on column TTA_TEST_QUESTION_CHOICE_LINE.choice_cn_content
  is '选项中文名称';
comment on column TTA_TEST_QUESTION_CHOICE_LINE.choice_en_content
  is '选项英文名称';
comment on column TTA_TEST_QUESTION_CHOICE_LINE.is_show_child
  is '是否直接显示下级题目,Y/N';
comment on column TTA_TEST_QUESTION_CHOICE_LINE.is_enable
  is '应用规则TTA_BASE_RULE表Id';
comment on column TTA_TEST_QUESTION_CHOICE_LINE.qn_choice
  is '选项序号，如A,B,C..';
comment on column TTA_TEST_QUESTION_CHOICE_LINE.parent_id
  is '父ID';
comment on column TTA_TEST_QUESTION_CHOICE_LINE.status
  is '0:初始化状态 1:保存 2:提交状态';
comment on column TTA_TEST_QUESTION_CHOICE_LINE.source_choice_line_id
  is '来源模板表原始id';

drop table TTA_BASE_RULE_HEADER;
-- Create table
create table TTA_BASE_RULE_HEADER
(
  rule_id           INTEGER not null,
  Q_HEADER_ID       INTEGER not null,
  CHOICE_LINE_ID    INTEGER not null,
  is_enable         VARCHAR2(2),
  version_num       INTEGER,
  creation_date     DATE,
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE,
  last_update_login INTEGER
);
alter table TTA_BASE_RULE_HEADER add constraint pk_HEADER_rule_id primary key (rule_id);

-- Add comments to the columns
comment on column TTA_BASE_RULE_HEADER.rule_id
  is '规则id';
comment on column TTA_BASE_RULE_HEADER.CHOICE_LINE_ID
  is '引用tta_question_choice_line 表主键id';
comment on column TTA_BASE_RULE_HEADER.Q_HEADER_ID
  is '应用tta_question_header 表主键id';
comment on column TTA_BASE_RULE_HEADER.is_enable
  is '是否有效:Y有效,N无效';
comment on column TTA_BASE_RULE_HEADER.version_num
  is '版本号';
comment on column TTA_BASE_RULE_HEADER.creation_date
  is '创建时间';
comment on column TTA_BASE_RULE_HEADER.created_by
  is '创建者';

-- Create table
DROP TABLE TTA_ITEM_IN;
create table TTA_ITEM_IN
(
  create_date       VARCHAR2(20),
  item_nbr          VARCHAR2(50),
  item_desc_cn      VARCHAR2(800),
  item_desc_en      VARCHAR2(500),
  status            VARCHAR2(5),
  retail_group      VARCHAR2(100),
  unit_cost         NUMBER(20,4),
  vendor_nbr        NUMBER(20),
  vendor_name       VARCHAR2(150),
  dept_code         NUMBER(20),
  dept_desc         VARCHAR2(50),
  group_code        NUMBER(20),
  group_desc        VARCHAR2(100),
  class_code        NUMBER(20),
  class_desc        VARCHAR2(100),
  sub_class_code    NUMBER(20),
  sub_class_desc    VARCHAR2(500),
  upc               VARCHAR2(100),
  brand_code        VARCHAR2(100),
  brand_cn          VARCHAR2(500),
  brand_en          VARCHAR2(500),
  orginal_place     VARCHAR2(500),
  specs             VARCHAR2(500),
  unit              VARCHAR2(500),
  uda4              VARCHAR2(500),
  uda6              VARCHAR2(500),
  uda8              VARCHAR2(500),
  uda29             VARCHAR2(500),
  uda41             VARCHAR2(500),
  uda49             VARCHAR2(500),
  uda62             VARCHAR2(500),
  uda63             VARCHAR2(500),
  uda666            VARCHAR2(500),
  uda13             VARCHAR2(500),
  uda64             VARCHAR2(500),
  uda50             VARCHAR2(500),
  uda51             VARCHAR2(500),
  source_system     VARCHAR2(500),
  source_code       VARCHAR2(500),
  pack_size         NUMBER(12,4),
  remark            VARCHAR2(500),
  creation_date     DATE,
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE,
  last_update_login INTEGER,
  version_num       INTEGER
);
-- Add comments to the table
comment on table TTA_ITEM_IN
  is 'item物料接口表';
-- Add comments to the columns
comment on column TTA_ITEM_IN.item_nbr
  is '物料编码';
comment on column TTA_ITEM_IN.item_desc_cn
  is '物料名称';
comment on column TTA_ITEM_IN.item_desc_en
  is '英文名';
comment on column TTA_ITEM_IN.status
  is '状态';
comment on column TTA_ITEM_IN.vendor_nbr
  is '供应商编码';
comment on column TTA_ITEM_IN.vendor_name
  is '供应商名称';


drop table TTA_ITEM;
-- Create table
create table TTA_ITEM
(
  item_id           INTEGER not null,
  item_nbr          VARCHAR2(50) not null,
  item_desc_cn      VARCHAR2(800),
  start_date        DATE,
  end_date          DATE,
  create_date       DATE,
  item_desc_en      VARCHAR2(500),
  status            VARCHAR2(5),
  retail_group      VARCHAR2(100),
  unit_cost         NUMBER(20,4),
  vendor_nbr        NUMBER(20),
  vendor_name       VARCHAR2(150),
  dept_code         NUMBER(20),
  dept_desc         VARCHAR2(50),
  group_code        NUMBER(20),
  group_desc        VARCHAR2(100),
  class_code        NUMBER(20),
  class_desc        VARCHAR2(100),
  sub_class_code    NUMBER(20),
  sub_class_desc    VARCHAR2(500),
  upc               VARCHAR2(100),
  brand_code        VARCHAR2(100),
  brand_cn          VARCHAR2(500),
  brand_en          VARCHAR2(500),
  orginal_place     VARCHAR2(500),
  specs             VARCHAR2(500),
  unit              VARCHAR2(500),
  uda4              VARCHAR2(500),
  uda6              VARCHAR2(500),
  uda8              VARCHAR2(500),
  uda29             VARCHAR2(500),
  uda41             VARCHAR2(500),
  uda49             VARCHAR2(500),
  uda62             VARCHAR2(500),
  uda63             VARCHAR2(500),
  uda666            VARCHAR2(500),
  uda13             VARCHAR2(500),
  uda64             VARCHAR2(500),
  uda50             VARCHAR2(500),
  uda51             VARCHAR2(500),
  source_system     VARCHAR2(500),
  source_code       VARCHAR2(500),
  pack_size         NUMBER(12,4),
  remark            VARCHAR2(500),
  creation_date     DATE default SYSDATE,
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE default SYSDATE,
  last_update_login INTEGER,
  version_num       INTEGER
);
alter table TTA_ITEM add constraint pk_item_id primary key(item_id);

-- Add comments to the table
comment on table TTA_ITEM
  is 'item物料表';
-- Add comments to the columns
comment on column TTA_ITEM.item_nbr
  is '物料编码';
comment on column TTA_ITEM.item_desc_cn
  is '物料名称';
comment on column TTA_ITEM.create_date
  is '物料创建日期';
comment on column TTA_ITEM.item_desc_en
  is '英文名';
comment on column TTA_ITEM.status
  is '状态';
comment on column TTA_ITEM.vendor_nbr
  is '供应商编码';
comment on column TTA_ITEM.vendor_name
  is '供应商名称';



-- 注意：动态创建表需要管理员执行：grant create any table to 用户;
-- 自动创建采购表
create or replace procedure PRC_CREATE_TTA_PURCHASE_IN is
  sqlstr varchar(500);
begin
    sqlstr := 'create table TTA_PURCHASE_IN_' || TO_CHAR(add_months(sysdate,12), 'YYYY') || ' as select *  from TTA_PURCHASE_IN';
    execute immediate sqlstr;
end;

declare
 job_num number;
begin
 dbms_job.submit(job_num,
     'PRC_CREATE_TTA_PURCHASE_IN;',
     ADD_MONTHS(trunc(sysdate,'YYYY'),11),
    'ADD_MONTHS(trunc(sysdate,''YYYY''),11)' );
 commit;
 dbms_output.put_line(job_num);
end;


/*
select * from dba_jobs;
begin
  dbms_job.remove(4);  -- 删除自动执行的job,参数是 job的id
  commit;
end;
*/


--自动创建销售表
create or replace procedure PRC_CREATE_TTA_SALE_IN is
  sqlstr varchar(500);
begin
    sqlstr := 'create table TTA_SALES_IN_' || TO_CHAR(add_months(sysdate,1), 'YYYYmm') || ' as select *  from TTA_SALES_IN';
    execute immediate sqlstr;
end;

declare
 job_num number;
begin
 dbms_job.submit(job_num,
     'PRC_CREATE_TTA_SALE_IN;',
     TRUNC(LAST_DAY(SYSDATE))-1+1/24,
    'TRUNC(LAST_DAY(SYSDATE))-1+1/24');
 commit;
 dbms_output.put_line(job_num);
end;


-- 手动创建采购表
declare
  sqlstr varchar(500);
  i      number;
begin
  i := 1;
  for i in 1 .. 3 loop
        sqlstr := 'create table TTA_PURCHASE_IN_' || TO_CHAR(add_months(sysdate,12*i-12), 'YYYY') || ' as select *  from TTA_PURCHASE_IN';
    execute immediate sqlstr;
  end loop;
end;


-- 创建当年度销售表
declare
  sqlstr varchar(500);
  i      number;
begin
  i := 0;
  for i in 1 .. 12 loop
    sqlstr := 'create table TTA_SALES_IN_' || TO_CHAR(add_months(sysdate, i-1), 'YYYYmm') || ' as select *  from TTA_SALES_IN';
    execute immediate sqlstr;
  end loop;
end;

create or replace procedure pro_update_tta_item is
begin
INSERT INTO TTA_ITEM
  (
   item_id,
   create_date,
   brand_cn,
   brand_code,
   brand_en,
   class_code,
   class_desc,
   created_by,
   creation_date,
   dept_code,
   dept_desc,
   end_date,
   group_code,
   group_desc,
   item_desc_cn,
   item_desc_en,
   item_nbr,
   last_updated_by,
   last_update_date,
   last_update_login,
   orginal_place,
   pack_size,
   remark,
   retail_group,
   source_code,
   source_system,
   specs,
   start_date,
   status,
   sub_class_code,
   sub_class_desc,
   uda13,
   uda29,
   uda4,
   uda41,
   uda49,
   uda50,
   uda51,
   uda6,
   uda62,
   uda63,
   uda64,
   uda666,
   uda8,
   unit,
   unit_cost,
   upc,
   vendor_name,
   vendor_nbr,
   version_num)
  SELECT
         SEQ_TTA_ITEM.NEXTVAL,
         to_date(create_date, 'yyyy-mm-dd HH24:MI:SS') as create_date,
         brand_cn,
         brand_code,
         brand_en,
         class_code,
         class_desc,
         created_by,
         creation_date,
         dept_code,
         dept_desc,
         null as end_date,
         group_code,
         group_desc,
         item_desc_cn,
         item_desc_en,
         item_nbr,
         -1 as last_updated_by,
         sysdate as last_update_date,
         -1 last_update_login,
         orginal_place,
         pack_size,
         remark,
         retail_group,
         source_code,
         source_system,
         specs,
         sysdate start_date,
         status,
         sub_class_code,
         sub_class_desc,
         uda13,
         uda29,
         uda4,
         uda41,
         uda49,
         uda50,
         uda51,
         uda6,
         uda62,
         uda63,
         uda64,
         uda666,
         uda8,
         unit,
         unit_cost,
         upc,
         vendor_name,
         vendor_nbr,
         0 as version_num
    FROM TTA_ITEM_IN T1
   WHERE NOT EXISTS (SELECT 1
            FROM TTA_ITEM T2
           WHERE T1.ITEM_NBR = T2.ITEM_NBR
             AND T1.VENDOR_NBR = T2.VENDOR_NBR
             AND T1.UPC = T2.UPC);

  update TTA_ITEM t1
     set (brand_cn,
          brand_code,
          brand_en,
          class_code,
          class_desc,
          dept_code,
          dept_desc,
          group_code,
          group_desc,
          item_desc_cn,
          item_desc_en,
          orginal_place,
          pack_size,
          remark,
          retail_group,
          source_code,
          source_system,
          specs,
          status,
          sub_class_code,
          sub_class_desc,
          uda13,
          uda29,
          uda4,
          uda41,
          uda49,
          uda50,
          uda51,
          uda6,
          uda62,
          uda63,
          uda64,
          uda666,
          uda8,
          unit,
          unit_cost,
          vendor_name) =
         (select brand_cn,
                 brand_code,
                 brand_en,
                 class_code,
                 class_desc,
                 dept_code,
                 dept_desc,
                 group_code,
                 group_desc,
                 item_desc_cn,
                 item_desc_en,
                 orginal_place,
                 pack_size,
                 remark,
                 retail_group,
                 source_code,
                 source_system,
                 specs,
                 status,
                 sub_class_code,
                 sub_class_desc,
                 uda13,
                 uda29,
                 uda4,
                 uda41,
                 uda49,
                 uda50,
                 uda51,
                 uda6,
                 uda62,
                 uda63,
                 uda64,
                 uda666,
                 uda8,
                 unit,
                 unit_cost,
                 vendor_name
            from TTA_ITEM_IN T2
           WHERE T1.Item_Nbr = t2.item_nbr
             and t1.vendor_nbr = t2.vendor_nbr
             and t1.upc = t2.upc);
    commit;
end;




create or replace procedure pro_update_tta_supplier is
begin
insert into TTA_SUPPLIER
(
  supplier_id,
  supplier_code,
  supplier_name,
  status,
  is_latent,
  owner_dept,
  owner_group,
  contract_output,
  purchase_mode,
  proposal_brand_group,
  dept_name,
  formal_code,
  formal_name,
  creation_date,
  created_by,
  last_updated_by,
  last_update_date,
  last_update_login,
  version_num
)
select
  SEQ_TTA_SUPPLIER.NEXTVAL as supplier_id,
  t.vendor_nbr as supplier_code,
  t.vendor_name as supplier_name,
  (case when status = 'A' THEN 'Y' ELSE 'N' END) AS status,
  'N' as is_latent,
  null as owner_dept,
  null as owner_group,
  null as contract_output,
  null as purchase_mode,
  null as proposal_brand_group,
  null as dept_name,
  null as  formal_code,
  null  as formal_name,
  sysdate as creation_date,
  -1 as created_by,
  -1 as last_updated_by,
  sysdate last_update_date,
  -1 as last_update_login,
  0 as version_num
 from TTA_VENDOR_IN t where not exists (select 1 from TTA_SUPPLIER t1 where t1.supplier_code = t.vendor_nbr);


 update TTA_SUPPLIER t
    set (t.supplier_name, t.status) =
        (select t1.vendor_name,
                (case
                  when status = 'A' THEN
                   'Y'
                  ELSE
                   'N'
                END)
           from TTA_VENDOR_IN t1
          where t1.vendor_nbr = t.supplier_code)
  where exists
  (select 1 from TTA_VENDOR_IN t2 where t2.vendor_nbr = t.supplier_code);
 commit;
 end;
