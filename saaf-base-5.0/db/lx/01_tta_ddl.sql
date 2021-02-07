drop table TTA_OI_BILL_LINE ;
-- Create table
create table TTA_OI_BILL_LINE
(
  account_month         DATE,
  tta_oi_bill_import_id NUMBER not null,
  rms_code              VARCHAR2(500),
  vender_name           VARCHAR2(500),
  importjv              VARCHAR2(200),
  invoicename           VARCHAR2(200),
  buyer                 VARCHAR2(200),
  tc                    VARCHAR2(200),
  department            VARCHAR2(200),
  brand                 VARCHAR2(200),
  venderab              VARCHAR2(2),
  user_contract_id      VARCHAR2(200),
  cooperate_status      VARCHAR2(100),
  vender_type           VARCHAR2(100),
  family_planing_flag   VARCHAR2(10),
  valid_begin_date      DATE,
  purchase              VARCHAR2(500),
  goods_return          VARCHAR2(500),
  net_purchase          VARCHAR2(500),
  dsd                   VARCHAR2(500),
  purchase_origin       VARCHAR2(500),
  goods_return_origin   VARCHAR2(500),
  net_purchase_origin   VARCHAR2(500),
  adrb_contract_text    VARCHAR2(500),
  adrb_sum_money        VARCHAR2(500),
  ep_contract_text      VARCHAR2(500),
  ep_sum_money          VARCHAR2(500),
  adpf_contract_text    VARCHAR2(500),
  adpf_sum_money        VARCHAR2(500),
  adda_contract_text    VARCHAR2(500),
  adda_sum_money        VARCHAR2(500),
  vir002_contract_text  VARCHAR2(500),
  vir002_sum_money      VARCHAR2(500),
  vir001_contract_text  VARCHAR2(500),
  vir001_sum_money      VARCHAR2(500),
  addg_contract_text    VARCHAR2(500),
  addg_sum_money        VARCHAR2(500),
  adtr_contract_text    VARCHAR2(500),
  adtr_sum_money        VARCHAR2(500),
  bdf_contract_text     VARCHAR2(500),
  bdf_sum_money         VARCHAR2(500),
  nss001_contract_text  VARCHAR2(500),
  nss001_contract_num   VARCHAR2(500),
  nss001_contract_data  VARCHAR2(500),
  nss001_sum_money      VARCHAR2(500),
  rss001_contract_text  VARCHAR2(500),
  rss001_contract_num   VARCHAR2(500),
  rss001_contract_data  VARCHAR2(500),
  rss001_sum_money      VARCHAR2(500),
  ass001_contract_text  VARCHAR2(500),
  ass001_contract_num   VARCHAR2(500),
  ass001_sum_money      VARCHAR2(500),
  wdp001_contract_text  VARCHAR2(500),
  wdp001_sum_money      VARCHAR2(500),
  npm001_contract_text  VARCHAR2(500),
  npm001_contract_num   VARCHAR2(500),
  npm001_contract_data  VARCHAR2(500),
  npm001_sum_money      VARCHAR2(500),
  drg001_contract_text  VARCHAR2(500),
  drg001_sum_money      VARCHAR2(500),
  drh001_contract_text  VARCHAR2(500),
  drh001summoney        VARCHAR2(500),
  drb001_contract_text  VARCHAR2(500),
  drb001_sum_money      VARCHAR2(500),
  dro001_contract_text  VARCHAR2(500),
  dro001_sum_money      VARCHAR2(500),
  cri001_contract_text  VARCHAR2(500),
  cri001_sum_money      VARCHAR2(500),
  dmi001_contract_text  VARCHAR2(500),
  dmi001_sum_money      VARCHAR2(500),
  hbi001_contract_text  VARCHAR2(500),
  hbi001_sum_money      VARCHAR2(500),
  npd001_contract_text  VARCHAR2(500),
  npd001_sum_money      VARCHAR2(500),
  bdf001_contract_text  VARCHAR2(500),
  bdf001_sum_money      VARCHAR2(500),
  cos001_contract_text  VARCHAR2(500),
  cos001_sum_money      VARCHAR2(500),
  oth001_contract_text  VARCHAR2(500),
  oth001_sum_money      VARCHAR2(500),
  nti001_contract_text  VARCHAR2(500),
  nti001_sum_money      VARCHAR2(500),
  ldp001_sum_money      VARCHAR2(500),
  ldp001_contract_text  VARCHAR2(500),
  nfp001_contract_text  VARCHAR2(500),
  nfp001_sum_money      VARCHAR2(500),
  cou001_contract_text  VARCHAR2(500),
  cou001_sum_money      VARCHAR2(500),
  vip001_contract_text  VARCHAR2(500),
  vip001_sum_money      VARCHAR2(500),
  lpu001_contract_text  VARCHAR2(500),
  lpu001_sum_money      VARCHAR2(500),
  bac001_contract_text  VARCHAR2(500),
  bac001_sum_money      VARCHAR2(500),
  uep001_contract_text  VARCHAR2(500),
  uep001_sum_money      VARCHAR2(500),
  otf001_contract_text  VARCHAR2(500),
  otf001_sum_money      VARCHAR2(500),
  psf001_contract_text  VARCHAR2(500),
  psf001_sum_money      VARCHAR2(500),
  cps001_contract_text  VARCHAR2(500),
  cps001_sum_money      VARCHAR2(500),
  ap_sum_money          VARCHAR2(500),
  remark                VARCHAR2(500),
  creation_date         DATE default SYSDATE,
  created_by            INTEGER,
  last_updated_by       INTEGER,
  last_update_date      DATE default SYSDATE,
  last_update_login     INTEGER,
  version_num           INTEGER
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
-- Add comments to the columns
comment on column TTA_OI_BILL_LINE.account_month
  is '导入月份';
comment on column TTA_OI_BILL_LINE.tta_oi_bill_import_id
  is 'ID';
comment on column TTA_OI_BILL_LINE.rms_code
  is '供应商编号';
comment on column TTA_OI_BILL_LINE.vender_name
  is '供应商名称';
comment on column TTA_OI_BILL_LINE.importjv
  is 'JV';
comment on column TTA_OI_BILL_LINE.invoicename
  is '开票抬头';
comment on column TTA_OI_BILL_LINE.buyer
  is 'Buyer';
comment on column TTA_OI_BILL_LINE.tc
  is 'TC';
comment on column TTA_OI_BILL_LINE.department
  is '部门';
comment on column TTA_OI_BILL_LINE.brand
  is '品牌';
comment on column TTA_OI_BILL_LINE.venderab
  is '供应商属性';
comment on column TTA_OI_BILL_LINE.user_contract_id
  is '合同编号';
comment on column TTA_OI_BILL_LINE.cooperate_status
  is '合作状态';
comment on column TTA_OI_BILL_LINE.vender_type
  is '供应商类型';
comment on column TTA_OI_BILL_LINE.family_planing_flag
  is '计生供应商';
comment on column TTA_OI_BILL_LINE.valid_begin_date
  is '合同开始日期';
comment on column TTA_OI_BILL_LINE.purchase
  is '采购额
（折扣前）';
comment on column TTA_OI_BILL_LINE.goods_return
  is '退货额
（折扣前）';
comment on column TTA_OI_BILL_LINE.net_purchase
  is '净采购额
（折扣前）';
comment on column TTA_OI_BILL_LINE.dsd
  is '免送额
（折扣前）';
comment on column TTA_OI_BILL_LINE.purchase_origin
  is '采购额
（折扣后）';
comment on column TTA_OI_BILL_LINE.goods_return_origin
  is '退货额
（折扣后）';
comment on column TTA_OI_BILL_LINE.net_purchase_origin
  is '净采购额（折扣后）';
comment on column TTA_OI_BILL_LINE.adrb_contract_text
  is '条款
一般购货折扣';
comment on column TTA_OI_BILL_LINE.adrb_sum_money
  is '一般购货折扣 Total';
comment on column TTA_OI_BILL_LINE.ep_contract_text
  is '条款
（提前付款）购货折扣';
comment on column TTA_OI_BILL_LINE.ep_sum_money
  is '（提前付款）购货折扣 Total';
comment on column TTA_OI_BILL_LINE.adpf_contract_text
  is '条款宣传牌及促销用品制作费';
comment on column TTA_OI_BILL_LINE.adpf_sum_money
  is '宣传牌及促销用品制作费';
comment on column TTA_OI_BILL_LINE.adda_contract_text
  is '条款
（集中收退货）购货折扣';
comment on column TTA_OI_BILL_LINE.adda_sum_money
  is '（集中收退货）购货折扣 Total';
comment on column TTA_OI_BILL_LINE.vir002_contract_text
  is '条款
(集中收货)购货折扣';
comment on column TTA_OI_BILL_LINE.vir002_sum_money
  is '(集中收货)购货折扣 Total';
comment on column TTA_OI_BILL_LINE.vir001_contract_text
  is '条款
退货运输费';
comment on column TTA_OI_BILL_LINE.vir001_sum_money
  is '退货运输费 Total';
comment on column TTA_OI_BILL_LINE.addg_contract_text
  is '条款
（残损）购货折扣';
comment on column TTA_OI_BILL_LINE.addg_sum_money
  is '（残损）购货折扣 Total';
comment on column TTA_OI_BILL_LINE.adtr_contract_text
  is '达到目标条款相应的点数
目标退佣';
comment on column TTA_OI_BILL_LINE.adtr_sum_money
  is '目标退佣Total';
comment on column TTA_OI_BILL_LINE.bdf_contract_text
  is '条款
%商业发展基金';
comment on column TTA_OI_BILL_LINE.bdf_sum_money
  is '%商业发展基金 Total';
comment on column TTA_OI_BILL_LINE.nss001_contract_text
  is '条款
新店宣传推广服务费';
comment on column TTA_OI_BILL_LINE.nss001_contract_num
  is '店铺数量新店宣传推广服务费';
comment on column TTA_OI_BILL_LINE.nss001_contract_data
  is '店铺资料新店宣传推广服务费';
comment on column TTA_OI_BILL_LINE.nss001_sum_money
  is '新店宣传推广服务费 total';
comment on column TTA_OI_BILL_LINE.rss001_contract_text
  is '条款
陈列区域装修费';
comment on column TTA_OI_BILL_LINE.rss001_contract_num
  is '店铺数量陈列区域装修费';
comment on column TTA_OI_BILL_LINE.rss001_contract_data
  is '店铺资料陈列区域装修费';
comment on column TTA_OI_BILL_LINE.rss001_sum_money
  is '陈列区域装修Total';
comment on column TTA_OI_BILL_LINE.ass001_contract_text
  is '条款
节庆促销服务费（周年庆、春节、劳动节、国庆节、圣诞节）';
comment on column TTA_OI_BILL_LINE.ass001_contract_num
  is '店铺数量节庆促销服务促销费（周年庆、春节、劳动节、国庆节、圣诞节）';
comment on column TTA_OI_BILL_LINE.ass001_sum_money
  is '节庆促销服务费（周年庆、春节、劳动节、国庆节、圣诞节） Total';
comment on column TTA_OI_BILL_LINE.wdp001_contract_text
  is '条款
节庆促销服务费（38妇女节）';
comment on column TTA_OI_BILL_LINE.wdp001_sum_money
  is '节庆促销服务费（38妇女节） Total';
comment on column TTA_OI_BILL_LINE.npm001_contract_text
  is '条款
新城市宣传推广服务费';
comment on column TTA_OI_BILL_LINE.npm001_contract_num
  is '店铺数量新城市宣传推广服务费';
comment on column TTA_OI_BILL_LINE.npm001_contract_data
  is '店铺资料新城市宣传推广服务费';
comment on column TTA_OI_BILL_LINE.npm001_sum_money
  is '新城市宣传推广服务费 Total';
comment on column TTA_OI_BILL_LINE.drg001_contract_text
  is '条款
促销陈列服务费（端架）';
comment on column TTA_OI_BILL_LINE.drg001_sum_money
  is '促销陈列服务费（端架） Total';
comment on column TTA_OI_BILL_LINE.drh001_contract_text
  is '条款
促销陈列服务费（焦点货架）';
comment on column TTA_OI_BILL_LINE.drh001summoney
  is '促销陈列服务费（焦点货架）Total';
comment on column TTA_OI_BILL_LINE.drb001_contract_text
  is '条款
促销陈列服务费（促销助理管理费）';
comment on column TTA_OI_BILL_LINE.drb001_sum_money
  is '促销陈列服务费（促销助理管理费）Total';
comment on column TTA_OI_BILL_LINE.dro001_contract_text
  is '条款
促销陈列服务费（其他）';
comment on column TTA_OI_BILL_LINE.dro001_sum_money
  is '促销陈列服务费（其他）Total';
comment on column TTA_OI_BILL_LINE.cri001_contract_text
  is '条款
专柜促销陈列服务费';
comment on column TTA_OI_BILL_LINE.cri001_sum_money
  is '专柜促销陈列服务费Total';
comment on column TTA_OI_BILL_LINE.dmi001_contract_text
  is '条款
宣传单张制作费（快讯及传单）';
comment on column TTA_OI_BILL_LINE.dmi001_sum_money
  is '宣传单张制作费（快讯及传单） Total';
comment on column TTA_OI_BILL_LINE.hbi001_contract_text
  is '条款
H & B
健与美';
comment on column TTA_OI_BILL_LINE.hbi001_sum_money
  is 'H & B
健与美
Total';
comment on column TTA_OI_BILL_LINE.npd001_contract_text
  is '条款
新品种宣传推广服务费';
comment on column TTA_OI_BILL_LINE.npd001_sum_money
  is '新品种宣传推广服务费Total';
comment on column TTA_OI_BILL_LINE.bdf001_contract_text
  is '条款
商业发展基金';
comment on column TTA_OI_BILL_LINE.bdf001_sum_money
  is '商业发展基金 Total';
comment on column TTA_OI_BILL_LINE.cos001_contract_text
  is '条款
成本补差';
comment on column TTA_OI_BILL_LINE.cos001_sum_money
  is '成本补差 Total';
comment on column TTA_OI_BILL_LINE.oth001_contract_text
  is '条款
其他';
comment on column TTA_OI_BILL_LINE.oth001_sum_money
  is '其他Total';
comment on column TTA_OI_BILL_LINE.nti001_contract_text
  is '条款
其他业务费用 ';
comment on column TTA_OI_BILL_LINE.nti001_sum_money
  is '其他业务费用 Total';
comment on column TTA_OI_BILL_LINE.ldp001_sum_money
  is '延误送货罚款 Total';
comment on column TTA_OI_BILL_LINE.ldp001_contract_text
  is '条款
延误送货罚款';
comment on column TTA_OI_BILL_LINE.nfp001_contract_text
  is '条款
送货满足率低于95%罚款';
comment on column TTA_OI_BILL_LINE.nfp001_sum_money
  is '送货满足率低于95%罚款 Total';
comment on column TTA_OI_BILL_LINE.cou001_contract_text
  is '条款
优惠券 ';
comment on column TTA_OI_BILL_LINE.cou001_sum_money
  is '优惠券 Total';
comment on column TTA_OI_BILL_LINE.vip001_contract_text
  is '条款
会员优惠 ';
comment on column TTA_OI_BILL_LINE.vip001_sum_money
  is '会员优惠 Total';
comment on column TTA_OI_BILL_LINE.lpu001_contract_text
  is '条款
延误退货罚款';
comment on column TTA_OI_BILL_LINE.lpu001_sum_money
  is '延误退货罚款 Total';
comment on column TTA_OI_BILL_LINE.bac001_contract_text
  is '条款
促销服务费';
comment on column TTA_OI_BILL_LINE.bac001_sum_money
  is '促销服务费 Total';
comment on column TTA_OI_BILL_LINE.uep001_contract_text
  is '条款
未在合同上的提早付款折扣 ';
comment on column TTA_OI_BILL_LINE.uep001_sum_money
  is '未在合同上的提早付款折扣 Total';
comment on column TTA_OI_BILL_LINE.otf001_contract_text
  is '条款
差旅费(供应商承担部份)';
comment on column TTA_OI_BILL_LINE.otf001_sum_money
  is '差旅费(供应商承担部份) Total';
comment on column TTA_OI_BILL_LINE.psf001_contract_text
  is '条款
促销服务费(VAT项目)';
comment on column TTA_OI_BILL_LINE.psf001_sum_money
  is '促销服务费(VAT项目)
Total';
comment on column TTA_OI_BILL_LINE.cps001_contract_text
  is '条款
其他业务收入-赔偿(补偿)收入';
comment on column TTA_OI_BILL_LINE.cps001_sum_money
  is '其他业务收入-赔偿(补偿)收入
Total';
comment on column TTA_OI_BILL_LINE.ap_sum_money
  is 'AP OI Total';
-- Create/Recreate primary, unique and foreign key constraints
alter table TTA_OI_BILL_LINE
  add constraint TTA_OI_BILL_LINE_V1 primary key (TTA_OI_BILL_IMPORT_ID)
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

DROP SEQUENCE  SEQ_TTA_OI_BILL_LINE ;
-- Create sequence
create sequence SEQ_TTA_OI_BILL_LINE
minvalue 1
maxvalue 99999999999999999999999999
start with 21
increment by 1
cache 20;



drop table TTA_POG_SPACE_LINE ;
-- Create table
create table TTA_POG_SPACE_LINE
(
  pog_space_line_id NUMBER not null,
  item_code         VARCHAR2(200),
  stores_num        NUMBER,
  time_dimension    NUMBER,
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE,
  creation_date     DATE,
  last_update_login INTEGER,
  version_num       INTEGER
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
-- Add comments to the columns
comment on column TTA_POG_SPACE_LINE.pog_space_line_id
  is '主键';
comment on column TTA_POG_SPACE_LINE.item_code
  is 'ITERM_CODE';
comment on column TTA_POG_SPACE_LINE.stores_num
  is '店铺数量';
comment on column TTA_POG_SPACE_LINE.time_dimension
  is '时间维度';
comment on column TTA_POG_SPACE_LINE.created_by
  is '创建人';
comment on column TTA_POG_SPACE_LINE.last_updated_by
  is '更新人';
comment on column TTA_POG_SPACE_LINE.last_update_date
  is '更新日期';
comment on column TTA_POG_SPACE_LINE.creation_date
  is '创建日期';
comment on column TTA_POG_SPACE_LINE.last_update_login
  is '登录人';
comment on column TTA_POG_SPACE_LINE.version_num
  is '版本号';
-- Create/Recreate indexes
create unique index TTA_POG_SPACE_LINE_U2 on TTA_POG_SPACE_LINE (ITEM_CODE)
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
alter table TTA_POG_SPACE_LINE
  add constraint TTA_POG_SPACE_LINE_U1 primary key (POG_SPACE_LINE_ID)
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

drop table TTA_DM_FULL_LINE ;
-- Create table
create table TTA_DM_FULL_LINE
(
  dm_full_line_id   NUMBER not null,
  effective_area    VARCHAR2(200),
  product_code      VARCHAR2(200),
  product_name      VARCHAR2(200),
  dm_page           NUMBER,
  location_code     VARCHAR2(200),
  bmp               NUMBER,
  dm_pament         VARCHAR2(200),
  duration_period   DATE,
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE,
  creation_date     DATE,
  last_update_login INTEGER,
  version_num       INTEGER
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
-- Add comments to the columns
comment on column TTA_DM_FULL_LINE.dm_full_line_id
  is '主键';
comment on column TTA_DM_FULL_LINE.effective_area
  is '生效地区';
comment on column TTA_DM_FULL_LINE.product_code
  is '产品编号';
comment on column TTA_DM_FULL_LINE.product_name
  is '产品名称';
comment on column TTA_DM_FULL_LINE.dm_page
  is 'DM_页数';
comment on column TTA_DM_FULL_LINE.location_code
  is '位置编码';
comment on column TTA_DM_FULL_LINE.bmp
  is 'BMP';
comment on column TTA_DM_FULL_LINE.dm_pament
  is 'DM_方式';
comment on column TTA_DM_FULL_LINE.duration_period
  is '档期期间';
comment on column TTA_DM_FULL_LINE.created_by
  is '创建人';
comment on column TTA_DM_FULL_LINE.last_updated_by
  is '更新人';
comment on column TTA_DM_FULL_LINE.last_update_date
  is '更新日期';
comment on column TTA_DM_FULL_LINE.creation_date
  is '创建日期';
comment on column TTA_DM_FULL_LINE.last_update_login
  is '登录人';
comment on column TTA_DM_FULL_LINE.version_num
  is '版本号';
-- Create/Recreate primary, unique and foreign key constraints
alter table TTA_DM_FULL_LINE
  add constraint TTA_DM_FULL_LINE_U1 primary key (DM_FULL_LINE_ID)
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

drop table TTA_SYSTEM_CURRENT_LINE ;
-- Create table
create table TTA_SYSTEM_CURRENT_LINE
(
  system_current_id NUMBER not null,
  month             DATE,
  item              VARCHAR2(200),
  item_desc_cn      VARCHAR2(200),
  brand_cn          VARCHAR2(200),
  vendor_nbr        VARCHAR2(200),
  vendor_name       VARCHAR2(200),
  group_desc        VARCHAR2(200),
  dept_desc         VARCHAR2(200),
  activity          VARCHAR2(200),
  uda4              VARCHAR2(200),
  store_coun        NUMBER,
  ctw               VARCHAR2(200),
  eb                VARCHAR2(200),
  rate_card         VARCHAR2(200),
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE,
  creation_date     DATE,
  last_update_login INTEGER,
  version_num       INTEGER
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
-- Add comments to the columns
comment on column TTA_SYSTEM_CURRENT_LINE.system_current_id
  is '主键';
comment on column TTA_SYSTEM_CURRENT_LINE.month
  is 'MONTH';
comment on column TTA_SYSTEM_CURRENT_LINE.item
  is 'ITEM';
comment on column TTA_SYSTEM_CURRENT_LINE.item_desc_cn
  is 'ITEM_DESC_CN';
comment on column TTA_SYSTEM_CURRENT_LINE.brand_cn
  is 'BRAND_CN';
comment on column TTA_SYSTEM_CURRENT_LINE.vendor_nbr
  is 'VENDOR_NBR';
comment on column TTA_SYSTEM_CURRENT_LINE.vendor_name
  is 'VENDOR_NAME';
comment on column TTA_SYSTEM_CURRENT_LINE.group_desc
  is 'GROUP_DESC';
comment on column TTA_SYSTEM_CURRENT_LINE.dept_desc
  is 'DEPT_DESC';
comment on column TTA_SYSTEM_CURRENT_LINE.activity
  is 'ACTIVITY';
comment on column TTA_SYSTEM_CURRENT_LINE.uda4
  is 'UDA4';
comment on column TTA_SYSTEM_CURRENT_LINE.store_coun
  is 'STORE_COUN';
comment on column TTA_SYSTEM_CURRENT_LINE.ctw
  is 'CTW';
comment on column TTA_SYSTEM_CURRENT_LINE.eb
  is 'EB';
comment on column TTA_SYSTEM_CURRENT_LINE.rate_card
  is 'RATE_CARD';
comment on column TTA_SYSTEM_CURRENT_LINE.created_by
  is '创建人';
comment on column TTA_SYSTEM_CURRENT_LINE.last_updated_by
  is '更新人';
comment on column TTA_SYSTEM_CURRENT_LINE.last_update_date
  is '更新日期';
comment on column TTA_SYSTEM_CURRENT_LINE.creation_date
  is '创建日期';
comment on column TTA_SYSTEM_CURRENT_LINE.last_update_login
  is '登录人';
comment on column TTA_SYSTEM_CURRENT_LINE.version_num
  is '版本号';
-- Create/Recreate primary, unique and foreign key constraints
alter table TTA_SYSTEM_CURRENT_LINE
  add constraint TTA_SYSTEM_CURRENT_LINE_U1 primary key (SYSTEM_CURRENT_ID)
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

DROP SEQUENCE  SEQ_TTA_POG_SPACE_LINE ;
-- Create sequence
create sequence SEQ_TTA_POG_SPACE_LINE
minvalue 1
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;

DROP SEQUENCE  SEQ_TTA_DM_FULL_LINE ;
-- Create sequence
create sequence SEQ_TTA_DM_FULL_LINE
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

DROP SEQUENCE  SEQ_TTA_SYSTEM_CURRENT_LINE ;
-- Create sequence
create sequence SEQ_TTA_SYSTEM_CURRENT_LINE
minvalue 1
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;


drop table TTA_TERMS_FRAME_HEADER ;
-- Create table
create table TTA_TERMS_FRAME_HEADER
(
  team_framework_id INTEGER not null,
  frame_code        VARCHAR2(50),
  year              INTEGER not null,
  frame_name        VARCHAR2(50),
  bill_status       VARCHAR2(20) not null,
  business_version  VARCHAR2(20) not null,
  pass_date         DATE,
  effective_date    DATE,
  expiration_date   DATE,
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE,
  creation_date     DATE,
  last_update_login INTEGER,
  version_num       INTEGER,
  delete_flag       INTEGER,
  dept_id           INTEGER not null,
  dept_code         VARCHAR2(50) not null,
  dept_name         VARCHAR2(200) not null
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
-- Add comments to the columns
comment on column TTA_TERMS_FRAME_HEADER.frame_code
  is '框架编码，暂时采用年度';
comment on column TTA_TERMS_FRAME_HEADER.year
  is '年度';
comment on column TTA_TERMS_FRAME_HEADER.frame_name
  is '框架名称';
comment on column TTA_TERMS_FRAME_HEADER.bill_status
  is '单据状态（1制作中、2待审批、3审批通过）';
comment on column TTA_TERMS_FRAME_HEADER.business_version
  is '条款框架版本号';
comment on column TTA_TERMS_FRAME_HEADER.pass_date
  is '审批通过时间';
comment on column TTA_TERMS_FRAME_HEADER.effective_date
  is '版本生效日期';
comment on column TTA_TERMS_FRAME_HEADER.expiration_date
  is '版本失效日期';
comment on column TTA_TERMS_FRAME_HEADER.created_by
  is '创建人';
comment on column TTA_TERMS_FRAME_HEADER.last_updated_by
  is '更新人';
comment on column TTA_TERMS_FRAME_HEADER.last_update_date
  is '更新日期';
comment on column TTA_TERMS_FRAME_HEADER.creation_date
  is '创建日期';
comment on column TTA_TERMS_FRAME_HEADER.last_update_login
  is '登录人';
comment on column TTA_TERMS_FRAME_HEADER.version_num
  is '版本号';
comment on column TTA_TERMS_FRAME_HEADER.delete_flag
  is '是否删除（0：未删除；1：已删除）';
comment on column TTA_TERMS_FRAME_HEADER.dept_id
  is '部门ID';
comment on column TTA_TERMS_FRAME_HEADER.dept_code
  is '部门code';
comment on column TTA_TERMS_FRAME_HEADER.dept_name
  is '部门NAME';
-- Create/Recreate primary, unique and foreign key constraints
alter table TTA_TERMS_FRAME_HEADER
  add constraint PK_ATT_TAERMS_FRAMEWORK_HEAD primary key (TEAM_FRAMEWORK_ID)
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

drop table TTA_TERMS_FRAME_HEADER_H ;
-- Create table
create table TTA_TERMS_FRAME_HEADER_H
(
  team_framework_id        INTEGER not null,
  frame_code               VARCHAR2(50) not null,
  year                     INTEGER not null,
  frame_name               VARCHAR2(50),
  bill_status              VARCHAR2(20) not null,
  business_version         VARCHAR2(20) not null,
  pass_date                DATE,
  effective_date           DATE,
  expiration_date          DATE,
  resouce_id               INTEGER,
  resouce_business_version VARCHAR2(20),
  created_by               INTEGER,
  last_updated_by          INTEGER,
  last_update_date         DATE,
  creation_date            DATE,
  last_update_login        INTEGER,
  version_num              INTEGER,
  delete_flag              INTEGER,
  dept_id                  INTEGER not null,
  dept_code                VARCHAR2(50) not null,
  dept_name                VARCHAR2(200) not null
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
-- Add comments to the columns
comment on column TTA_TERMS_FRAME_HEADER_H.frame_code
  is '框架编码，暂时采用年度';
comment on column TTA_TERMS_FRAME_HEADER_H.year
  is '年度';
comment on column TTA_TERMS_FRAME_HEADER_H.frame_name
  is '框架名称';
comment on column TTA_TERMS_FRAME_HEADER_H.bill_status
  is '单据状态（1制作中、2待审批、3审批通过）';
comment on column TTA_TERMS_FRAME_HEADER_H.business_version
  is '条款框架版本号';
comment on column TTA_TERMS_FRAME_HEADER_H.pass_date
  is '审批通过时间';
comment on column TTA_TERMS_FRAME_HEADER_H.effective_date
  is '版本生效日期';
comment on column TTA_TERMS_FRAME_HEADER_H.expiration_date
  is '版本失效日期';
comment on column TTA_TERMS_FRAME_HEADER_H.resouce_id
  is '来源版本Id，对应复制原纪录id';
comment on column TTA_TERMS_FRAME_HEADER_H.resouce_business_version
  is '来源版本号，对应复制原纪录版本号';
comment on column TTA_TERMS_FRAME_HEADER_H.created_by
  is '创建人';
comment on column TTA_TERMS_FRAME_HEADER_H.last_updated_by
  is '更新人';
comment on column TTA_TERMS_FRAME_HEADER_H.last_update_date
  is '更新日期';
comment on column TTA_TERMS_FRAME_HEADER_H.creation_date
  is '创建日期';
comment on column TTA_TERMS_FRAME_HEADER_H.last_update_login
  is '登录人';
comment on column TTA_TERMS_FRAME_HEADER_H.version_num
  is '版本号';
comment on column TTA_TERMS_FRAME_HEADER_H.delete_flag
  is '是否删除（0：未删除；1：已删除）';
comment on column TTA_TERMS_FRAME_HEADER_H.dept_id
  is '部门ID';
comment on column TTA_TERMS_FRAME_HEADER_H.dept_code
  is '部门code';
comment on column TTA_TERMS_FRAME_HEADER_H.dept_name
  is '部门NAME';
-- Create/Recreate primary, unique and foreign key constraints
alter table TTA_TERMS_FRAME_HEADER_H
  add constraint PK_ATT_TAERMS_FRAMEWORK_HEAD_H primary key (TEAM_FRAMEWORK_ID)
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

drop table TTA_COLLECT_TYPE_LINE_H ;

-- Create table
create table TTA_COLLECT_TYPE_LINE_H
(
  collect_type_id   INTEGER not null,
  clause_id         INTEGER not null,
  collect_type      VARCHAR2(50),
  standard_value    CHAR(50),
  unit_value        VARCHAR2(50) not null,
  is_enable         VARCHAR2(2) not null,
  is_default_value  VARCHAR2(2) not null,
  version_num       INTEGER,
  creation_date     DATE default sysdate not null,
  created_by        INTEGER default -1 not null,
  last_updated_by   INTEGER default -1 not null,
  last_update_date  DATE default sysdate not null,
  last_update_login INTEGER,
  team_framework_id INTEGER not null,
  resouce_id        INTEGER,
  delete_flag       INTEGER not null
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
-- Add comments to the columns
comment on column TTA_COLLECT_TYPE_LINE_H.team_framework_id
  is '条款框架头ID';
comment on column TTA_COLLECT_TYPE_LINE_H.resouce_id
  is '来源ID';
comment on column TTA_COLLECT_TYPE_LINE_H.delete_flag
  is '是否删除（0：未删除；1：已删除）';
-- Create/Recreate primary, unique and foreign key constraints
alter table TTA_COLLECT_TYPE_LINE_H
  add constraint PK_TTA_COLLECT_TYPE_LINE_H primary key (COLLECT_TYPE_ID)
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


drop table TTA_COLLECT_TYPE_LINE ;
-- Create table
create table TTA_COLLECT_TYPE_LINE
(
  collect_type_id   INTEGER not null,
  clause_id         INTEGER not null,
  collect_type      VARCHAR2(50),
  standard_value    CHAR(50),
  unit_value        VARCHAR2(50) not null,
  is_enable         VARCHAR2(2) not null,
  is_default_value  VARCHAR2(2) not null,
  version_num       INTEGER,
  creation_date     DATE default sysdate not null,
  created_by        INTEGER default -1 not null,
  last_updated_by   INTEGER default -1 not null,
  last_update_date  DATE default sysdate not null,
  last_update_login INTEGER,
  team_framework_id INTEGER not null,
  delete_flag       INTEGER not null
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
comment on table TTA_COLLECT_TYPE_LINE
  is '收取方式行信息表';
-- Add comments to the columns
comment on column TTA_COLLECT_TYPE_LINE.collect_type_id
  is '主键';
comment on column TTA_COLLECT_TYPE_LINE.clause_id
  is '引用tta_clause_tree_h 主键 id';
comment on column TTA_COLLECT_TYPE_LINE.collect_type
  is '收取方式';
comment on column TTA_COLLECT_TYPE_LINE.standard_value
  is '标准值';
comment on column TTA_COLLECT_TYPE_LINE.unit_value
  is '单位值';
comment on column TTA_COLLECT_TYPE_LINE.is_enable
  is '1：启用，0：禁用';
comment on column TTA_COLLECT_TYPE_LINE.is_default_value
  is '是否默认值,1：是，0：否';
comment on column TTA_COLLECT_TYPE_LINE.team_framework_id
  is '条款框架头ID';
comment on column TTA_COLLECT_TYPE_LINE.delete_flag
  is '是否删除（0：未删除；1：已删除）';
-- Create/Recreate indexes
create index TTA_COLLECT_TYPE_LINE_U1 on TTA_COLLECT_TYPE_LINE (TEAM_FRAMEWORK_ID)
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
create index TTA_COLLECT_TYPE_LINE_U2 on TTA_COLLECT_TYPE_LINE (CLAUSE_ID)
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
alter table TTA_COLLECT_TYPE_LINE
  add constraint PK_TTA_COLLECT_TYPE_LINE primary key (COLLECT_TYPE_ID)
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

drop table TTA_TERMS_FRAME_HEADER ;
-- Create table
create table TTA_TERMS_FRAME_HEADER
(
  team_framework_id INTEGER not null,
  frame_code        VARCHAR2(50),
  year              INTEGER not null,
  frame_name        VARCHAR2(50),
  bill_status       VARCHAR2(20) not null,
  business_version  VARCHAR2(20) not null,
  pass_date         DATE,
  effective_date    DATE,
  expiration_date   DATE,
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE,
  creation_date     DATE,
  last_update_login INTEGER,
  version_num       INTEGER,
  delete_flag       INTEGER,
  dept_id           INTEGER not null,
  dept_code         VARCHAR2(50) not null,
  dept_name         VARCHAR2(200) not null
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
-- Add comments to the columns
comment on column TTA_TERMS_FRAME_HEADER.frame_code
  is '框架编码，暂时采用年度';
comment on column TTA_TERMS_FRAME_HEADER.year
  is '年度';
comment on column TTA_TERMS_FRAME_HEADER.frame_name
  is '框架名称';
comment on column TTA_TERMS_FRAME_HEADER.bill_status
  is '单据状态（1制作中、2待审批、3审批通过）';
comment on column TTA_TERMS_FRAME_HEADER.business_version
  is '条款框架版本号';
comment on column TTA_TERMS_FRAME_HEADER.pass_date
  is '审批通过时间';
comment on column TTA_TERMS_FRAME_HEADER.effective_date
  is '版本生效日期';
comment on column TTA_TERMS_FRAME_HEADER.expiration_date
  is '版本失效日期';
comment on column TTA_TERMS_FRAME_HEADER.created_by
  is '创建人';
comment on column TTA_TERMS_FRAME_HEADER.last_updated_by
  is '更新人';
comment on column TTA_TERMS_FRAME_HEADER.last_update_date
  is '更新日期';
comment on column TTA_TERMS_FRAME_HEADER.creation_date
  is '创建日期';
comment on column TTA_TERMS_FRAME_HEADER.last_update_login
  is '登录人';
comment on column TTA_TERMS_FRAME_HEADER.version_num
  is '版本号';
comment on column TTA_TERMS_FRAME_HEADER.delete_flag
  is '是否删除（0：未删除；1：已删除）';
comment on column TTA_TERMS_FRAME_HEADER.dept_id
  is '部门ID';
comment on column TTA_TERMS_FRAME_HEADER.dept_code
  is '部门code';
comment on column TTA_TERMS_FRAME_HEADER.dept_name
  is '部门NAME';
-- Create/Recreate indexes
create unique index TTA_TAERMS_FRAMEWORK_HEAD_U1 on TTA_TERMS_FRAME_HEADER (YEAR)
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
alter table TTA_TERMS_FRAME_HEADER
  add constraint PK_ATT_TAERMS_FRAMEWORK_HEAD primary key (TEAM_FRAMEWORK_ID)
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


drop table TTA_TERMS_FRAME_HEADER_H ;
-- Create table
create table TTA_TERMS_FRAME_HEADER_H
(
  team_framework_id        INTEGER not null,
  frame_code               VARCHAR2(50) not null,
  year                     INTEGER not null,
  frame_name               VARCHAR2(50),
  bill_status              VARCHAR2(20) not null,
  business_version         VARCHAR2(20) not null,
  pass_date                DATE,
  effective_date           DATE,
  expiration_date          DATE,
  resouce_id               INTEGER,
  resouce_business_version VARCHAR2(20),
  created_by               INTEGER,
  last_updated_by          INTEGER,
  last_update_date         DATE,
  creation_date            DATE,
  last_update_login        INTEGER,
  version_num              INTEGER,
  delete_flag              INTEGER,
  dept_id                  INTEGER not null,
  dept_code                VARCHAR2(50) not null,
  dept_name                VARCHAR2(200) not null
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
-- Add comments to the columns
comment on column TTA_TERMS_FRAME_HEADER_H.frame_code
  is '框架编码，暂时采用年度';
comment on column TTA_TERMS_FRAME_HEADER_H.year
  is '年度';
comment on column TTA_TERMS_FRAME_HEADER_H.frame_name
  is '框架名称';
comment on column TTA_TERMS_FRAME_HEADER_H.bill_status
  is '单据状态（1制作中、2待审批、3审批通过）';
comment on column TTA_TERMS_FRAME_HEADER_H.business_version
  is '条款框架版本号';
comment on column TTA_TERMS_FRAME_HEADER_H.pass_date
  is '审批通过时间';
comment on column TTA_TERMS_FRAME_HEADER_H.effective_date
  is '版本生效日期';
comment on column TTA_TERMS_FRAME_HEADER_H.expiration_date
  is '版本失效日期';
comment on column TTA_TERMS_FRAME_HEADER_H.resouce_id
  is '来源版本Id，对应复制原纪录id';
comment on column TTA_TERMS_FRAME_HEADER_H.resouce_business_version
  is '来源版本号，对应复制原纪录版本号';
comment on column TTA_TERMS_FRAME_HEADER_H.created_by
  is '创建人';
comment on column TTA_TERMS_FRAME_HEADER_H.last_updated_by
  is '更新人';
comment on column TTA_TERMS_FRAME_HEADER_H.last_update_date
  is '更新日期';
comment on column TTA_TERMS_FRAME_HEADER_H.creation_date
  is '创建日期';
comment on column TTA_TERMS_FRAME_HEADER_H.last_update_login
  is '登录人';
comment on column TTA_TERMS_FRAME_HEADER_H.version_num
  is '版本号';
comment on column TTA_TERMS_FRAME_HEADER_H.delete_flag
  is '是否删除（0：未删除；1：已删除）';
comment on column TTA_TERMS_FRAME_HEADER_H.dept_id
  is '部门ID';
comment on column TTA_TERMS_FRAME_HEADER_H.dept_code
  is '部门code';
comment on column TTA_TERMS_FRAME_HEADER_H.dept_name
  is '部门NAME';
-- Create/Recreate indexes
create unique index TTA_T_F_HEAD_H_U1 on TTA_TERMS_FRAME_HEADER_H (YEAR, BUSINESS_VERSION)
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
alter table TTA_TERMS_FRAME_HEADER_H
  add constraint PK_ATT_TAERMS_FRAMEWORK_HEAD_H primary key (TEAM_FRAMEWORK_ID)
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
