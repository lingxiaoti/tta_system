---创建PURCHASE表
create table TTA_PURCHASE
(
  purchase_id integer primary key,
  vendor_nbr  varchar2(50) not null, 
  vendor_name varchar2(230) not null,
  year_month_day date not null,
  location    varchar2(500),
  item_nbr    varchar2(50),
  receiving_qty number,
  receiving_amount number(10,2),
  cancel_receiving_qty number,
  cancel_receiving_amt number(10,2),
  currency varchar2(10),
  purchase_type varchar2(10),
  address_type  varchar2(10),
  po_nbr   varchar2(50),
  is_dsd   char(1) not null,
  store    varchar2(50),
  version_num       INTEGER,
  creation_date     DATE default sysdate not null,
  created_by        INTEGER default -1 not null,
  last_updated_by   INTEGER default -1 not null,
  last_update_date  DATE default sysdate not null,
  last_update_login INTEGER
);

comment on table TTA_PURCHASE
  is 'PURCHASE数据';
-- Add comments to the columns 
comment on column TTA_PURCHASE.purchase_id is '主键';
comment on column TTA_PURCHASE.vendor_nbr is '供应商编号';
comment on column TTA_PURCHASE.vendor_name is '供应商名称';
comment on column TTA_PURCHASE.year_month_day is '收货日期';
comment on column TTA_PURCHASE.location is '仓库地址';
comment on column TTA_PURCHASE.item_nbr is 'ITEM编号';
comment on column TTA_PURCHASE.receiving_qty is '收货数量';
comment on column TTA_PURCHASE.receiving_amount is '收货金额';
comment on column TTA_PURCHASE.cancel_receiving_qty is '采购退货数量';
comment on column TTA_PURCHASE.cancel_receiving_amt is '采购退货金额';
comment on column TTA_PURCHASE.currency is '币种';
comment on column TTA_PURCHASE.purchase_type is '采购模式';
comment on column TTA_PURCHASE.address_type is '地点类型';
comment on column TTA_PURCHASE.po_nbr is 'PO单号';
comment on column TTA_PURCHASE.is_dsd is 'IS_DSD';
comment on column TTA_PURCHASE.store is 'STORE';
comment on column TTA_PURCHASE.version_num is '版本号';

---------------------
alter table TTA_PURCHASE add constraint pk_purchase_id primary key (purchase_id);
--------------------
create sequence seq_tta_purchase 
minvalue 1
nomaxvalue 
start with 1
increment by 1 
nocycle
nocache;
--------------------

--表tta_sole_agrt_info
alter table tta_sole_agrt_info add(SOLE_AGRT_H_ID Integer);

---2019.7.18 创建序列
create sequence seq_tta_side_agrt_header
minvalue 1
nomaxvalue
start with 1
increment by 1
nocycle
nocache;

create sequence seq_tta_side_agrt_line
minvalue 1
nomaxvalue
start with 1
increment by 1
nocycle
nocache;

--修改字符大小
alter table tta_side_agrt_line modify(proposal_contract_code char(20));

---------------------附件下载表-------------------------------
---- Create table
create table tta_attachment_download
(
  file_id           INTEGER not null,
  source_file_name  VARCHAR2(200),
  function_id       VARCHAR2(50),
  business_id       INTEGER,
  file_store_system INTEGER,
  file_path         VARCHAR2(200),
  bucket_name       VARCHAR2(100),
  phy_file_name     VARCHAR2(100),
  status            VARCHAR2(2),
  file_size         NUMBER(10,2),
  file_type         VARCHAR2(20),
  delete_flag       INTEGER default 0,
  created_by        INTEGER,
  creation_date     DATE,
  last_updated_by   INTEGER,
  last_update_date  DATE,
  last_update_login INTEGER,
  version_num       INTEGER default 0
);
--add comments to the table
comment on table tta_attachment_download is 'TTA附件下载表';
-- Add comments to the columns
comment on column tta_attachment_download.file_id is '文件ID';
comment on column tta_attachment_download.business_id is '业务来源主键';
comment on column tta_attachment_download.file_store_system is '文件存储系统--1阿里sso 2文档系统';
comment on column tta_attachment_download.file_size is '文件大小(MB)';
comment on column tta_attachment_download.delete_flag is '是否删除 0未删除1 已删除';
comment on column tta_attachment_download.created_by is '创建用户ID';
comment on column tta_attachment_download.creation_date is '创建时间';
comment on column tta_attachment_download.last_updated_by is '最后修改用户ID';
comment on column tta_attachment_download.last_update_date  is '最后修改时间';
comment on column tta_attachment_download.last_update_login is 'LAST_UPDATE_LOGIN';
comment on column tta_attachment_download.version_num  is '版本号';
-- Create/Recreate indexes
create index D_BUSINESS_ID_IDX on tta_attachment_download (BUSINESS_ID);

create index D_FILE_STORE_SYSTEM_IDX on tta_attachment_download (FILE_STORE_SYSTEM);

create index D_FUNCTION_ID_IDX on tta_attachment_download (FUNCTION_ID);


-- Create/Recreate primary, unique and foreign key constraints
alter table tta_attachment_download
  add constraint PK_TTA_ATTACHMENT_DOWNLOAD primary key (FILE_ID);

---创建序列
create sequence seq_attachment_download
minvalue 1
nomaxvalue
start with 1
increment by 1
nocycle
nocache;

----------------------------------------------附件下载表 end----------------------------------
------创建表------- Proposal拆分与合并中间表--------------
-- Create table
create table TTA_SUPPLIER_ITEM_MID
(
  mid  INTEGER not null,
  supplier_item_h_id       INTEGER,
  orginal_id             INTEGER,
  supplier_code          VARCHAR2(50) not null,
  supplier_name          VARCHAR2(100) not null,
  dept_name              VARCHAR2(100) not null,
  dept_code              VARCHAR2(50) not null,
  brand_name             VARCHAR2(100) not null,
  brand_code             VARCHAR2(100) not null,
  item_code              VARCHAR2(50) not null,
  item_name              VARCHAR2(100) not null,
  split_supplier_code    VARCHAR2(50),
  split_supplier_name    VARCHAR2(100),
  version_num            INTEGER,
  creation_date          DATE default sysdate not null,
  created_by             INTEGER default -1 not null,
  last_updated_by        INTEGER default -1 not null,
  last_update_date       DATE default sysdate not null,
  last_update_login      INTEGER
);

-- Add comments to the table
comment on table TTA_SUPPLIER_ITEM_MID
  is 'Proposal拆分与合并中间表';

-- Add comments to the columns
comment on column TTA_SUPPLIER_ITEM_MID.mid
  is '主键';

comment on column TTA_SUPPLIER_ITEM_MID.supplier_item_h_id
is 'Proposal拆分与合并头表id';

comment on column TTA_SUPPLIER_ITEM_MID.supplier_code
  is '供应商编码';
comment on column TTA_SUPPLIER_ITEM_MID.supplier_name
  is '供应商名称';

comment on column TTA_SUPPLIER_ITEM_MID.dept_name
  is '部门名称';
comment on column TTA_SUPPLIER_ITEM_MID.dept_code
  is '部门编码';
comment on column TTA_SUPPLIER_ITEM_MID.brand_name
  is '品牌名称';
comment on column TTA_SUPPLIER_ITEM_MID.brand_code
  is '品牌编码';
comment on column TTA_SUPPLIER_ITEM_MID.item_code
  is '物料编码';
comment on column TTA_SUPPLIER_ITEM_MID.item_name
  is '物料名称';
comment on column TTA_SUPPLIER_ITEM_MID.split_supplier_code
  is '拆分(指定)供应商编码';
comment on column TTA_SUPPLIER_ITEM_MID.split_supplier_name
  is '拆分(指定)供应商名称';
comment on column TTA_SUPPLIER_ITEM_MID.Orginal_Id
is '记录被拆分表id';

  -- Create/Recreate primary, unique and foreign key constraints
  alter table TTA_SUPPLIER_ITEM_MID
  add constraint PK_TTA_SUPPLIER_ITEM_MID primary key (mid);
------
--创建序列
create sequence seq_tta_supplier_item_mid
minvalue 1
nomaxvalue
start with 1
increment by 1
nocycle
nocache;
------------------end---------------------
--tta_supplier_item_mid 表添加三个字段
alter table tta_supplier_item_mid add(group_code varchar2(30));
alter table tta_supplier_item_mid add(group_name varchar2(100));
alter table tta_purchase_in_2019 add(split_count integer);
-------------------------------------
----tta_purchase_in_2019 表-----
alter table tta_purchase_in_2019 add(split_supplier_code varchar2(50));
alter table tta_purchase_in_2019 add(split_supplier_name varchar2(100));
comment on column  tta_purchase_in_2019.split_supplier_code is '拆分后的供应商编码';
comment on column  tta_purchase_in_2019.split_supplier_name is '拆分后的供应商名称';
comment on column  tta_purchase_in_2019.split_count is '拆分次数';
-------修改tta_supplier_item_header表
alter table tta_supplier_item_header modify(SPLIT_CONDITION varchar2(50));
-----

--Promotional leaflet（DM，针对2.1） 促销传单报表

--促销
-- Create table
create table tta_promotional_leaflet
(
  promotional_leaf_id number not null,--主键
  dm_full_line_id  integer, --外键(关联TTA_DM_FULL_LINE表)
  promotional_period  varchar2(100),--促销期间
  dm_page integer, --DM页码
  p_dis_position varchar2(50), --促销陈列位置
  effective_area  varchar2(60), --生效区域
  map_position varchar2(100),--图位
  p_activity   varchar2(255), --促销活动
  product_code varchar2(255), --产品编号
  product_name varchar2(255), --产品名称
  brand_name  varchar2(255), --品牌名称
  activity_content varchar2(200), -- 活动内容
  dept_name varchar2(100), -- 部门名称
  category_code varchar2(100), -- 品类编码
  category_name varchar2(100), --品类
  supplier_code varchar2(200), --供应商编号
  supplier_name varchar2(255), --供应商名称
  contract_status varchar2(50), --合作状态
  contract_year  varchar2(60), -- 合同年份
  contract_clause varchar2(100), -- 合同条款
  coll_standard  varchar2(100), --计收标准
  recei_amount  varchar2(50), --应收金额
  unconfirm_vendor_code varchar2(100), --采购确认供应商编号
  unconfirm_vendor_name varchar2(255), --采购确认供应商名称
  affirm_amount_dm varchar2(50), --采购确认下单金额DM（BIC下单金额）
  affirm_amount_mkt varchar2(50), -- 采购确认下单金额MKT cost(Non-trade) (BIC下单金额）
  diff_value1  varchar2(60), --差异(实收-应收)
  diff_value2  varchar2(60), --差异(仅计算负数)
  purchasing_reply varchar2(4000), -- 采购回复
  exemption_reason       VARCHAR2(255),
  exemption_reason2      VARCHAR2(255),
  ti_form_no varchar2(130),
  bic_remark varchar2(1000),
  contract_master_dept varchar2(255),
  item_master_dept varchar2(255),
  creation_date     DATE default SYSDATE,
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE default SYSDATE,
  last_update_login INTEGER,
  version_num       INTEGER
);

--add comments to the table
comment on table tta_promotional_leaflet is '促销传单报表';
-- Add comments to the columns
 comment on column tta_promotional_leaflet.promotional_leaf_id is '主键';
 comment on column tta_promotional_leaflet.dm_full_line_id   is '外键(关联TTA_DM_FULL_LINE表)';
 comment on column tta_promotional_leaflet.promotional_period   is '促销期间';
 comment on column tta_promotional_leaflet.dm_page   is 'DM页码';
 comment on column tta_promotional_leaflet.promotional_period is '促销陈列位置';
 comment on column tta_promotional_leaflet.effective_area is '生效区域';
 comment on column tta_promotional_leaflet.map_position is '图位';
 comment on column tta_promotional_leaflet.p_activity  is   '促销活动';
 comment on column tta_promotional_leaflet.product_code  is '产品编号';
 comment on column tta_promotional_leaflet.product_name  is '产品名称';
 comment on column tta_promotional_leaflet.brand_name  is '品牌名称';
 comment on column tta_promotional_leaflet.activity_content is  '活动内容(档期+页码+排序+区域+规格)';
 comment on column tta_promotional_leaflet.dept_name  is  '部门名称';
 comment on column tta_promotional_leaflet.category_code  is  '品类编码';
 comment on column tta_promotional_leaflet.category_name  is '品类';
 comment on column tta_promotional_leaflet.supplier_code is  '供应商编号';
 comment on column tta_promotional_leaflet.supplier_name  is '供应商名称';
 comment on column tta_promotional_leaflet.contract_status is  '合作状态';
 comment on column tta_promotional_leaflet.contract_year  is  '合同年份';
 comment on column tta_promotional_leaflet.contract_clause  is '合同条款';
 comment on column tta_promotional_leaflet.coll_standard  is  '计收标准';
 comment on column tta_promotional_leaflet.recei_amount  is '应收金额';
 comment on column tta_promotional_leaflet.unconfirm_vendor_code is  '采购确认供应商编号';
 comment on column tta_promotional_leaflet.unconfirm_vendor_name is  '采购确认供应商名称';
 comment on column tta_promotional_leaflet.affirm_amount_dm  is '采购确认下单金额DM(BIC下单金额)';
 comment on column tta_promotional_leaflet.affirm_amount_mkt  is  '采购确认下单金额MKT cost(Non-trade) (BIC下单金额)';
 comment on column tta_promotional_leaflet.diff_value1   is '差异(实收-应收)';
 comment on column tta_promotional_leaflet.diff_value2   is '差异(仅计算负数)';
 comment on column tta_promotional_leaflet.purchasing_reply  is '采购回复(↓BIC筛选收取/折扣收取下单)';
 comment on column tta_promotional_leaflet.exemption_reason is  '采购Z列为 "确认不收取"/ "折扣收取" 需填写(↓请选reason code ,如需增加，请通知BIC)';
 comment on column tta_promotional_leaflet.exemption_reason2  is '采购Z列为 "已收取" 需填借记单编号/备注';
 comment on column tta_promotional_leaflet.ti_form_no is  'TI Form NO.';
 comment on column tta_promotional_leaflet.bic_remark is  'BIC备注';
 comment on column tta_promotional_leaflet.contract_master_dept is 'Contract master department';
 comment on column tta_promotional_leaflet.item_master_dept is  'Item master deparment';

 -- Create/Recreate primary, unique and foreign key constraints
alter table tta_promotional_leaflet
  add constraint PK_TTA_PROMOTIONAL_LEAFLET primary key (promotional_leaf_id);

  ---创建序列
create sequence seq_promotional_leaflet
minvalue 1
nomaxvalue
start with 1
increment by 1
nocycle
nocache;
------
alter table tta_promotional_leaflet add(PROMOTION_GUIDELINE_id integer);
comment on column tta_promotional_leaflet.PROMOTION_GUIDELINE_id is '外键(关联TTA_PROMOTION_GUIDELINE表)';
alter table  tta_promotional_leaflet add(SALES_SITE_ID integer);
comment on column tta_promotional_leaflet.SALES_SITE_ID is '外键(关联TTA_OSD_SALES_SITE表)';
----------------------------------end---------------------------------------
---获取快码值描述函数---------------------------------
create or replace function get_lookup_desc( --获取快码值描述
                                           p_lookup_type in varchar2, --快码值
                                           flag          in integer, --区分字段
                                           type_code     in varchar2 -- 类型编码

                                           ) return varchar2 is
  v_string      varchar2(300);
  v_lookup_desc varchar2(500);
  v_value1      varchar2(50) := '';
  v_value2      varchar2(50) := '';
  contrat1      varchar2(50);
  contrat2      varchar2(50);

begin
  v_string := '';
  if p_lookup_type is null or p_lookup_type = '' then
    return v_string;
  end if;

  if flag = 0 then
    if p_lookup_type = 'REGIONAL_COR' then
      v_value1 := substr(type_code, 1, 2);
      v_value2 := substr(type_code, 3);
      for v in (select lookup_code, meaning, description
                  from base_lookup_values
                 where lookup_type = p_lookup_type
                   and enabled_flag = 'Y'
                   and delete_flag = 0
                   and start_date_active < sysdate
                   and (end_date_active >= sysdate or
                       end_date_active is null)
                   and system_code = 'BASE') loop
        begin

          if v_value1 = '00' then
            v_string := v.description;
            return v_string;
          end if;

          if v.lookup_code = v_value1 then
            contrat1 := v.description;
          end if;

          if v.lookup_code = v_value2 then
            contrat2 := v.description;
          end if;

        end;
      end loop;
    end if;

    v_string := contrat1 || contrat2;
  elsif flag=1 then
     for v1 in (select lookup_code, meaning, description
                  from base_lookup_values
                 where lookup_type = p_lookup_type
                   and enabled_flag = 'Y'
                   and delete_flag = 0
                   and start_date_active < sysdate
                   and (end_date_active >= sysdate or
                       end_date_active is null)
                   and system_code = 'BASE') loop
             if v1.lookup_code =type_code then
                 v_string := v1.meaning;
             end if;
             end loop;

  end if;

  return v_string;
end get_lookup_desc;
----------------------------------------
--2019.7.29,修改TTA_DM_FULL_LINE 表的字段类型
alter table TTA_DM_FULL_LINE modify(BMP varchar2(50));
----------------------------------------
--需要发到测试库
--2019.7.31 tta_proposal_header表添加字段
alter table tta_proposal_header add(major_dept_desc varchar2(100));
comment on column tta_proposal_header.major_dept_desc is '主部门';
alter table tta_proposal_header add(sale_type varchar2(20));
comment on column tta_proposal_header.sale_type is '销售方式';
alter table tta_proposal_header add(major_dept_id integer);
comment on column tta_proposal_header.major_dept_id is '主部门id';
--2019.8.4号添加
alter table tta_proposal_header add(MAJOR_DEPT_CODE varchar(30));
comment on column tta_proposal_header.MAJOR_DEPT_CODE is '主部门编号';
------------------------------------------------------
--2019.8.1创建视图
create or replace view tta_group_v as
select t."DEPARTMENT_ID",
        t."DEPARTMENT_CODE",
        t."DEPARTMENT_FULL_NAME",
        t."DEPARTMENT_NAME",
        t."PARENT_DEPARTMENT_ID",
        t."DEPARTMENT_TYPE",
        t."VERSION_NUM",
        t."CREATION_DATE",
        t."CREATED_BY",
        t."LAST_UPDATED_BY",
        t."LAST_UPDATE_DATE",
        t."LAST_UPDATE_LOGIN"
   from base_department t
  where 1 = 1;
-----------------------------------------
alter table base_users add(employee_number varchar2);
comment on column base_users.employee_number is '员工编号';
--------

--创建表 tta_supplier_item_rel_supplier ------
create table tta_supplier_item_rel_supplier
(
  sup_item_rel_s_id     INTEGER not null,
  rel_supplier_code   VARCHAR2(50) not null,
  rel_supplier_name   VARCHAR2(230) not null,
  supplier_item_h_id              INTEGER not null,
  marjor_supplier_code  varchar2(50),
  marjor_supplier_name  varchar2(230),
  status              CHAR(1),
  creation_date       DATE default SYSDATE,
  created_by          INTEGER,
  last_updated_by     INTEGER,
  last_update_date    DATE default SYSDATE,
  last_update_login   INTEGER,
  version_num         INTEGER
);

comment on table tta_supplier_item_rel_supplier is 'proposal拆分与合并关联供应商标';

comment on column tta_supplier_item_rel_supplier.sup_item_rel_s_id is '主键';
comment on column tta_supplier_item_rel_supplier.rel_supplier_code is '关联供应商编号';
comment on column tta_supplier_item_rel_supplier.rel_supplier_name is '关联供应商名字';
comment on column tta_supplier_item_rel_supplier.supplier_item_h_id is 'proposal拆分与合并单据头id';
comment on column tta_supplier_item_rel_supplier.marjor_supplier_code is '主供应商编号';
comment on column tta_supplier_item_rel_supplier.marjor_supplier_name is '主供应商名称';
comment on column tta_supplier_item_rel_supplier.status is '关联供应商编号';

--创建序列
create sequence seq_tta_supplier_item_rel_sup
minvalue 1
nomaxvalue
start with 1
increment by 1
nocycle
nocache;
---------------------------------------------
alter table tta_supplier_item_header add(SUPPLIER_ID integer);
comment on column tta_supplier_item_header.SUPPLIER_ID is '供应商id';

---------------------------------------------
--需要更新
--2019.8.14
alter table tta_supplier_item_header add(is_complete varchar2(5));
comment on column  tta_supplier_item_header.is_complete is 'purchase更新状态'
---------------------------------------------

---创建用户信息接口表-------------
create table user_interface_in(
       user_interface_id integer,
       account_type varchar2(8),
       employee_no  varchar2(10),
       email        varchar2(50),
       position_name varchar2(120),
       org_unit_name varchar2(240),
       chinese_name  varchar2(60),
       pinyin_name   varchar2(150),
       english_name  varchar2(150),
       Department    nvarchar2(255),
       STATUS        integer,
       Dept_No       varchar2(10),
       Market        varchar2(100),
       location      varchar2(100),
       org_type      varchar2(100),
       org_location  varchar2(100),
       date_join     varchar2(100),
       grade         varchar2(50),
       creation_date     DATE default SYSDATE,
       created_by        INTEGER,
       last_updated_by   INTEGER,
       last_update_date  DATE default SYSDATE,
       last_update_login INTEGER,
       version_num       INTEGER
);

comment on table user_interface_in is '用户信息接口表';
comment on column user_interface_in.user_interface_id is '主键';
comment on column user_interface_in.account_type is '账户类型';
comment on column user_interface_in.employee_no is '员工号';
comment on column user_interface_in.email is '邮箱地址';
comment on column user_interface_in.position_name is '职位';
comment on column user_interface_in.org_unit_name is '所属部门ID';
comment on column user_interface_in.chinese_name is '中文名';
comment on column user_interface_in.pinyin_name is '姓名拼音';

comment on column user_interface_in.english_name is '英文名';
comment on column user_interface_in.Department is '部门名';
comment on column user_interface_in.STATUS is '状态';
comment on column user_interface_in.Dept_No is '大部门编号';
comment on column user_interface_in.Market is '所属市场名称';

comment on column user_interface_in.location is '人员所属区域';
comment on column user_interface_in.org_type is '人员所属区域类型';
comment on column user_interface_in.org_location is '人员所属位置名称';


----创建序列----
create sequence seq_user_interface_in
minvalue 1
nomaxvalue
start with 1
increment by 1
nocycle
nocache;
-------------------
----不需要增加了,注释掉,不要发到测试环境去
 alter table base_users add(job_status integer);
 comment on column base_users.job_status is '工作状态(0 正常,1 离职)';

 alter table user_interface_in add(date_join varchar2(100));
 alter table user_interface_in add(grade varchar2(50));
-------------------
---更新版本1(已发版)
--2019.8.20,需要发到测试环境上
alter table tta_brandpln_line add(brand_Plan_Adjust number);
comment on column tta_brandpln_line.brand_Plan_Adjust is '品牌计划调整比例';

alter table tta_brandpln_line add(brand_en varchar2(120));
comment on column tta_brandpln_line.brand_en is '品牌英文名';
------------------------------------------
--
CREATE OR REPLACE VIEW TTA_BRANDPLN_LINE_V AS
SELECT t."BRANDPLN_L_ID",t."BRAND_DETAILS",t."GROUP_CODE",
t."GROUP_DESC",t."DEPT_CODE",
t."DEPT_DESC",t."BRAND_CODE",
t."BRAND_CN",
t."BRAND_EN",
t."PO_RECORD",
t."SALES",t."ACTUAL_GP",
t."FCS_PURCHASE",
nvl(t."PURCHASE_GROWTH",0) as PURCHASE_GROWTH,
t."FCS_SALES",
nvl(t."SALES_GROWTH",0) as SALES_GROWTH,
nvl(t."GP",t.ACTUAL_GP) as GP,
t."REMARK",
t."CREATION_DATE",
t."CREATED_BY",
t."LAST_UPDATED_BY",
t."LAST_UPDATE_DATE",
t."LAST_UPDATE_LOGIN",t."VERSION_NUM",
t."BRANDPLN_H_ID",t."PROPOSAL_ID",
t."PO_RECORD2",t."SALES2",t."ACTUAL_GP2",
   u1.user_name last_updated_name,
   u2.user_name created_name
   FROM tta_brandpln_line t,
      base_users u1,
      base_users u2
   where  t.last_updated_by = u1.user_id(+)
   and t.created_by = u2.user_id(+);

-------------------------
--更新版本2(已发版,测试环境已存在,开发环境未存在)
--时间:2019.9.6
alter table tta_dept_fee add(proposal_year varchar2(50));
comment on column tta_dept_fee.proposal_year is 'proposal制作年度';
-------------
---更新版本3(已发版)
--时间:2019.9.11
alter table TTA_FEE_DEPT_LINE add(dm_flyer varchar(20));
comment on column TTA_FEE_DEPT_LINE.dm_flyer is '宣传单类型';

---TTA_DEPT_FEE表 添加一个字段
alter table TTA_DEPT_FEE add (dm_flyer varchar2(20));
comment on column TTA_DEPT_FEE.dm_flyer is '宣传单类型,为快讯及传单类型使用';
-----------------------------

--------------------------------start-------------------------------------------
--更新版本4(已发版,测试环境已存在此脚本)------
--时间:2019.10.8
-------描述:品牌计划表拉取数据的临时表
create table TTA_COLLECT_SALES_TEMP
(
  tran_date            NUMBER,
  item_nbr             VARCHAR2(50),
  vendor_nbr           VARCHAR2(50),
  purch_type           VARCHAR2(50),
  sales_amt            NUMBER,
  po_amt               NUMBER,
  gp_amt               NUMBER,
  gp_supplier_popt_amt NUMBER,
  cost                 NUMBER,
  group_code           NUMBER(20),
  group_desc           VARCHAR2(100),
  dept_code            NUMBER(20),
  dept_desc            VARCHAR2(50),
  brand_code           VARCHAR2(100),
  brand_cn             VARCHAR2(500),
  brand_en             VARCHAR2(500),
  delete_symbol        VARCHAR2(20),
  split_supplier_code  VARCHAR2(50)
)

alter table tta_collect_sales_temp add(split_supplier_code varchar2(50))

--时间:2019.10.8
--描述:Proposal拆分与合并模块拉取拆分明细数据临时表
create table TTA_SPLIT_MERGE_DATA_TEMP
(
  tran_date           NUMBER,
  vendor_nbr          VARCHAR2(50),
  vendor_name         VARCHAR2(50),
  group_code          NUMBER(20),
  group_desc          VARCHAR2(100),
  dept_code           NUMBER(20),
  dept_desc           VARCHAR2(50),
  brand_code          VARCHAR2(100),
  brand_cn            VARCHAR2(500),
  brand_en            VARCHAR2(500),
  item_nbr            VARCHAR2(50),
  item_desc_cn        VARCHAR2(800),
  item_desc_en        VARCHAR2(500),
  split_supplier_code VARCHAR2(50),
  split_supplier_name VARCHAR2(500),
  delete_sign         VARCHAR2(30),
  create_date         DATE
)

--时间:2019.10.11(需要在测试环境发布,未发版)
--描述:增加一个字段last_split_supplier_code 上一版本的拆分供应商编码
alter table tta_supplier_item_mid add(last_split_supplier_code varchar2(50));
comment on column tta_supplier_item_mid.last_split_supplier_code is '上一版本的拆分供应商编码';

alter table tta_supplier_item_mid add(last_split_supplier_name varchar2(150));
comment on column tta_supplier_item_mid.last_split_supplier_name is '上一版本的拆分供应商编码';
-------------------------------end--------------------------------------------------

----时间:2019.10.21(需要在正式环境发布,未发版)-------------
create sequence SEQ_TTA_BEOI_BILL_LINE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

----2019.10.22(需要发布到正式环境,未发版)
create sequence SEQ_TTA_ABOI_SUMMARY
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocache;

alter table TTA_ABOI_SUMMARY
  add primary key (TTA_ABOI_SUMMARY_ID);

---时间2019.10.23(未发版)
--创建TTA_PURCHASE_IN_2018 和 TTA_PURCHASE_IN_2019的索引
create index TTA_PURCHASE_IN_2018_INDEX on TTA_PURCHASE_IN_2018 (SPLIT_SUPPLIER_CODE);
create index TTA_PURCHASE_IN_2019_INDEX on TTA_PURCHASE_IN_2019 (SPLIT_SUPPLIER_CODE)
--------------------------------------------------

---2019.10.24 TTA_SIDE_AGRT_HEADER表增加一个审批通过日期
alter table TTA_SIDE_AGRT_HEADER add(approve_date Date);

----------------时间:2019.11.10(未发布到正式环境,已发布到正式环境) start --------------
create table TTA_FREE_GOODS_POLIST
(
  id                      NUMBER not null,
  WEEK                    Number,
  LOCATION                VARCHAR2(100),
  SUPPLIER                VARCHAR2(100),
  ORDER_NO                VARCHAR2(100),
  ITEM                    VARCHAR2(250),
  SUPP_PACK_SIZE          VARCHAR2(50),
  WRITTEN_DATE            VARCHAR2(50),
  CLOSE_DATE              VARCHAR2(550),
  NOT_AFTER_DATE          VARCHAR2(50),
  UNIT_COST               VARCHAR2(250),
  QTY_ORDERED             VARCHAR2(250),
  QTY_RECEIVED            VARCHAR2(50),
  VUL_ORDERED             VARCHAR2(50),
  VUL_RECEIVED            VARCHAR2(500),
  COMMENTS                VARCHAR2(100),
  SUP_NAME                VARCHAR2(50),
  ORDER_TYPE              VARCHAR2(50),
  PACKAGE_ID              VARCHAR2(50),
  PROM_NUMBER             VARCHAR2(50),
  UNIT_COST_INIT          VARCHAR2(50),
  creation_date           DATE default SYSDATE,
  created_by              INTEGER,
  last_updated_by         INTEGER,
  last_update_date        DATE default SYSDATE,
  last_update_login       INTEGER,
  VERSION_NUM             Number,
  export_batch            varchar2(300)
)
alter table TTA_FREE_GOODS_POLIST add(is_calculate  varchar2(50));
alter table TTA_FREE_GOODS_POLIST add(charge_year  integer );
comment on column TTA_FREE_GOODS_POLIST.is_calculate is '是否加入计算(Y,N)';
comment on column TTA_FREE_GOODS_POLIST.charge_year is '费用年度';
comment on column TTA_FREE_GOODS_POLIST.export_batch is '导入批次';

---如下脚本已不用,不用发布到正式环境:2019.11.13号起不用
/*create table TTA_FREE_GOODS_POLIST
(
  id                      NUMBER not null,
  WEEK                    Number,
  LOCATION                VARCHAR2(100),
  SUPPLIER                VARCHAR2(100),
  ORDER_NO                VARCHAR2(100),
  ITEM                    VARCHAR2(250),
  SUPP_PACK_SIZE          VARCHAR2(50),
  WRITTEN_DATE            VARCHAR2(50),
  CLOSE_DATE              VARCHAR2(550),
  NOT_AFTER_DATE          VARCHAR2(50),
  UNIT_COST               VARCHAR2(250),
  QTY_ORDERED             VARCHAR2(250),
  QTY_RECEIVED            VARCHAR2(50),
  VUL_ORDERED             VARCHAR2(50),
  VUL_RECEIVED            VARCHAR2(500),
  COMMENTS                VARCHAR2(100),
  SUP_NAME                VARCHAR2(50),
  ORDER_TYPE              VARCHAR2(50),
  PACKAGE_ID              VARCHAR2(50),
  PROM_NUMBER             VARCHAR2(50),
  UNIT_COST_INIT          VARCHAR2(50),
  creation_date           DATE default SYSDATE,
  created_by              INTEGER,
  last_updated_by         INTEGER,
  last_update_date        DATE default SYSDATE,
  last_update_login       INTEGER,
  VERSION_NUM             Number,

  REL_WEEK                               Number,
  SUPPLIER_CODE                      VARCHAR2(100),
  SUPPLIER_NAME                      VARCHAR2(100),
  REL_PO                                 VARCHAR2(100),
  REL_COMMENTS                           VARCHAR2(300),
  ITEM_CODE                          Number,
  ITEM_NAME                          VARCHAR2(50),
  BRAND                              VARCHAR2(550),
  GROUP_DESC                         VARCHAR2(50),
  DEPT_DESC                          VARCHAR2(250),
  REL_Unit_cost                          VARCHAR2(250),
  REL_QTY_RECEIVED                       VARCHAR2(50),
  promotion_price                    VARCHAR2(50),
  actual_money                       VARCHAR2(100),
  REL_RDER_TYPE                         VARCHAR2(100),
  is_calculate                       VARCHAR2(50),
  times_version                      VARCHAR2(50)
)*/


create sequence seq_tta_free_goods_polist
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocycle
nocache;

alter table TTA_FREE_GOODS_POLIST
  add primary key (ID);

-----

--创建脚本 tta_Free_Goods_Order_detail
create table tta_Free_Goods_Order_detail
(
  id                      NUMBER not null,
  WEEK                    Number,
  LOCATION                VARCHAR2(100),
  SUPPLIER                VARCHAR2(100),
  ORDER_NO                VARCHAR2(100),
  ITEM                    VARCHAR2(250),
  SUPP_PACK_SIZE          VARCHAR2(50),
  WRITTEN_DATE            VARCHAR2(50),
  CLOSE_DATE              VARCHAR2(550),
  NOT_AFTER_DATE          VARCHAR2(50),
  UNIT_COST               VARCHAR2(250),
  QTY_ORDERED             VARCHAR2(250),
  QTY_RECEIVED            VARCHAR2(50),
  VUL_ORDERED             VARCHAR2(50),
  VUL_RECEIVED            VARCHAR2(500),
  COMMENTS                VARCHAR2(100),
  SUP_NAME                VARCHAR2(100),
  ORDER_TYPE              VARCHAR2(50),
  PACKAGE_ID              VARCHAR2(50),
  PROM_NUMBER             VARCHAR2(50),
  UNIT_COST_INIT          VARCHAR2(50),
  creation_date           DATE default SYSDATE,
  created_by              INTEGER,
  last_updated_by         INTEGER,
  last_update_date        DATE default SYSDATE,
  last_update_login       INTEGER,
  VERSION_NUM             Number,

  REL_WEEK                               Number,
  SUPPLIER_CODE                      VARCHAR2(100),
  SUPPLIER_NAME                      VARCHAR2(100),
  REL_PO                                 VARCHAR2(100),
  REL_COMMENTS                           VARCHAR2(300),
  ITEM_CODE                          Number,
  ITEM_NAME                          VARCHAR2(100),
  BRAND                              VARCHAR2(550),
  GROUP_DESC                         VARCHAR2(50),
  DEPT_DESC                          VARCHAR2(250),
  REL_Unit_cost                          VARCHAR2(250),
  REL_QTY_RECEIVED                       VARCHAR2(50),
  promotion_price                    VARCHAR2(50),
  actual_money                       VARCHAR2(100),
  REL_RDER_TYPE                         VARCHAR2(100),
  is_calculate                       VARCHAR2(50),
  times_version                      VARCHAR2(50)
)

alter table tta_Free_Goods_Order_detail add(charge_year  INTEGER);
comment on column tta_Free_Goods_Order_detail.is_calculate is '是否加入计算(Y,N)';
comment on column tta_Free_Goods_Order_detail.charge_year is '费用年度';
alter table tta_Free_Goods_Order_detail add(OPEN_SELECT varchar2(30));
comment on column tta_Free_Goods_Order_detail.OPEN_SELECT is '开发采购选择';

comment on table tta_Free_Goods_Order_detail
  is ' 免费货品po_list订单明细';

comment on column tta_Free_Goods_Order_detail.id
is '主键';
comment on column tta_Free_Goods_Order_detail.WEEK
is '周期';
comment on column tta_Free_Goods_Order_detail.LOCATION
is 'LOCATION';
comment on column tta_Free_Goods_Order_detail.SUPPLIER
is '供应商编号';
comment on column tta_Free_Goods_Order_detail.ORDER_NO
is '订单';
comment on column tta_Free_Goods_Order_detail.ITEM
is '产品编号';
comment on column tta_Free_Goods_Order_detail.SUPP_PACK_SIZE
is 'SUPP_PACK_SIZE';
comment on column tta_Free_Goods_Order_detail.WRITTEN_DATE
is 'WRITTEN_DATE';
comment on column tta_Free_Goods_Order_detail.CLOSE_DATE
is 'CLOSE_DATE';
comment on column tta_Free_Goods_Order_detail.NOT_AFTER_DATE
is 'NOT_AFTER_DATE';
comment on column tta_Free_Goods_Order_detail.UNIT_COST
is 'UNIT_COST';
comment on column tta_Free_Goods_Order_detail.QTY_ORDERED
is 'QTY_ORDERED';
comment on column tta_Free_Goods_Order_detail.QTY_RECEIVED
is 'QTY_RECEIVED';
comment on column tta_Free_Goods_Order_detail.COMMENTS
is '备注';
comment on column tta_Free_Goods_Order_detail.SUP_NAME
is '供应商名称';
comment on column tta_Free_Goods_Order_detail.ORDER_TYPE
is '订单类型';
comment on column tta_Free_Goods_Order_detail.PACKAGE_ID
is 'PACKAGE_ID';
comment on column tta_Free_Goods_Order_detail.PROM_NUMBER
is 'PROM_NUMBER';
comment on column tta_Free_Goods_Order_detail.UNIT_COST_INIT
is 'UNIT_COST_INIT';


comment on column tta_Free_Goods_Order_detail.REL_WEEK
is '周期_REL';
comment on column tta_Free_Goods_Order_detail.SUPPLIER_CODE
is '供应商编码_REL';
comment on column tta_Free_Goods_Order_detail.SUPPLIER_NAME
is '供应商名称_REL';
comment on column tta_Free_Goods_Order_detail.REL_PO
is 'PO_REL';
comment on column tta_Free_Goods_Order_detail.REL_COMMENTS
is '备注_REL';
comment on column tta_Free_Goods_Order_detail.ITEM_CODE
is '产品编号_REL';
comment on column tta_Free_Goods_Order_detail.ITEM_NAME
is '产品名称_REL';
comment on column tta_Free_Goods_Order_detail.BRAND
is '品牌_REL';
comment on column tta_Free_Goods_Order_detail.GROUP_DESC
is 'GROUP REL';
comment on column tta_Free_Goods_Order_detail.DEPT_DESC
is 'DEPT_DESC REL';
comment on column tta_Free_Goods_Order_detail.REL_Unit_cost
is 'Unit_cost REL';
comment on column tta_Free_Goods_Order_detail.REL_QTY_RECEIVED
is 'QTY_RECEIVED REL';
comment on column tta_Free_Goods_Order_detail.promotion_price
is '促销价_REL';
comment on column tta_Free_Goods_Order_detail.actual_money
is '实收金额_REL';
comment on column tta_Free_Goods_Order_detail.REL_RDER_TYPE
is '订单类型_REL';
comment on column tta_Free_Goods_Order_detail.is_calculate
is '是否加入计算_REL';
comment on column tta_Free_Goods_Order_detail.times_version
is '时间版本_REL';

----创建序列
create sequence seq_tta_Free_Goods_O_d
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocycle
nocache;

alter table tta_Free_Goods_Order_detail
  add primary key (id);

-----------------------时间:2019.11.10 end--------------------------------

----2019.11.19(未发布到正式环境)---------------start--------------------

--创建TTA_SYSTEM_CURRENT_LINE脚本
create table TTA_SYSTEM_CURRENT_LINE
(
  system_current_id NUMBER not null,
  annual_month      VARCHAR2(100),
  month           VARCHAR2(100),
  Item              VARCHAR2(200),
  ITEM_DESC_CN      VARCHAR2(200),
  BRAND_CN          VARCHAR2(200),
  VENDOR_NBR        VARCHAR2(200),
  VENDOR_NAME       VARCHAR2(200),
  GROUP_DESC        VARCHAR2(100),
  DEPT_DESC         VARCHAR2(100),
  Activity          VARCHAR2(200),
  UDA4              VARCHAR2(200),
  Store_Coun        NUMBER,
  CTW               VARCHAR2(100),
  EB                VARCHAR2(100),
  rate_card         number,
  annual_month_n    number,
  month_n           number,
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE,
  creation_date     DATE,
  last_update_login INTEGER,
  version_num       INTEGER
)

alter table TTA_SYSTEM_CURRENT_LINE add (import_batch varchar2(100));
comment on column TTA_SYSTEM_CURRENT_LINE.import_batch is '导入批次';

---添加备注-----
comment on table TTA_SYSTEM_CURRENT_LINE is 'SYSTEM_CURRENT导入表';
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
comment on column TTA_SYSTEM_CURRENT_LINE.annual_month_n
  is 'annual_month_n 整年的月份';
comment on column TTA_SYSTEM_CURRENT_LINE.month_n
 is 'month_n 具体的月份';
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
comment on column TTA_SYSTEM_CURRENT_LINE.annual_month
  is '12_month';

-- Create/Recreate primary, unique and foreign key constraints
alter table TTA_SYSTEM_CURRENT_LINE
  add constraint TTA_SYSTEM_CURRENT_LINE_U1 primary key (SYSTEM_CURRENT_ID);
----2019.11.19---------------end----------------------
---------------2019.11.20--------------start--------------------
--创建tta_npp_new_product_report脚本(未发布到正式环境,已发布)
---NPP 报表脚本
create table tta_npp_new_product_report(
       npp_id                 NUMBER not null,
       month                  varchar2(60),
       store_coun             NUMBER,
       GROUP_DESC             varchar2(100),
       DEPT_DESC              varchar2(100),
       item_code              varchar2(100),
       item_desc_cn           varchar2(250),
       brand_cn               varchar2(150),
       brand_en               varchar2(150),
       vendor_nbr             varchar2(100),
       vendor_name            varchar2(200),
       activity               varchar2(500),
       ctw                    VARCHAR2(100),
       eb                     VARCHAR2(100),
       contract_terms         VARCHAR2(700),
       collect_way            VARCHAR2(60),
       charge_standards       NUMBER,
       charge_unit            VARCHAR2(50),
       receive_amount         number,
       unconfirm_amount       number,
       difference             number,
       source_filepath        varchar2(200),
       source_filename        varchar2(100),
       collect_select         VARCHAR2(50),
       purchase_act           varchar2(50),
       exemption_reason       VARCHAR2(255),
       exemption_reason2      VARCHAR2(255),
       debit_order_code       varchar2(50),
       aboi_receipts          number,
       batch_code             VARCHAR2(255),
       transfer_in_person     NUMBER,
       remark                 VARCHAR2(255),
       status                 NUMBER,
       parent_id              NUMBER,
       parent_vendor_code     VARCHAR2(255),
       parent_vendor_name     VARCHAR2(255),
       creation_date          DATE,
       created_by             NUMBER,
       last_updated_by        NUMBER,
       last_update_date       DATE,
       last_update_login      NUMBER,
       transfer_out_person    NUMBER,
       transfer_last_person   NUMBER,
       transfer_in_date       DATE,
       transfer_out_date      DATE,
       transfer_last_date     DATE,
       charge_unit_name       VARCHAR2(100),
       original_before_amount NUMBER,
       addition_rate          NUMBER,
       group_code             varchar2(100),
       dept_code              varchar2(100),
       is_create              varchar2(50),
       receive_amount_add     number,
       charge_method          varchar2(30),
       annual_month_n         number,
       month_n                number,
       unconfirm_amount_a     number,
       process_id             number,
       file_id                number
)

comment on table tta_npp_new_product_report is 'NPP新品报表';

comment on column tta_npp_new_product_report.npp_id
  is '主键';
comment on column tta_npp_new_product_report.month
  is 'month';
comment on column tta_npp_new_product_report.store_coun
  is '店铺数量';
comment on column tta_npp_new_product_report.GROUP_DESC
  is 'GROUP_DESC';
comment on column tta_npp_new_product_report.DEPT_DESC
  is 'DEPT_DESC';
comment on column tta_npp_new_product_report.item_code
  is '产品编号';
comment on column tta_npp_new_product_report.item_desc_cn
  is '产品名称';
comment on column tta_npp_new_product_report.brand_cn
  is '品牌_CN';
comment on column tta_npp_new_product_report.brand_en
  is '品牌_EN';
comment on column tta_npp_new_product_report.vendor_nbr
  is '供应商编号';
comment on column tta_npp_new_product_report.vendor_name
  is '供应商名称';
comment on column tta_npp_new_product_report.activity
  is '活动内容';
comment on column tta_npp_new_product_report.ctw
  is 'Non-CVW/CVW 非寄售经仓/寄售经仓';
comment on column tta_npp_new_product_report.eb
  is 'EB /Non-EB 独家产品/非独家产品';
comment on column tta_npp_new_product_report.contract_terms
  is '合同条款';
comment on column tta_npp_new_product_report.collect_way
  is '收取方式';
comment on column tta_npp_new_product_report.charge_standards
  is '收费标准';
comment on column tta_npp_new_product_report.charge_unit
  is '收费单位';
comment on column tta_npp_new_product_report.receive_amount
  is '应收金额(不含加成)';
comment on column tta_npp_new_product_report.unconfirm_amount
  is '采购确认收取金额(不含加成)';

comment on column tta_npp_new_product_report.unconfirm_amount_a
  is '采购确认收取金额(含加成)';

comment on column tta_npp_new_product_report.difference
  is '差异(仅计算负数)';
comment on column tta_npp_new_product_report.source_filepath
  is '文件路径';
comment on column tta_npp_new_product_report.source_filename
  is '文件名称';
comment on column tta_npp_new_product_report.collect_select
  is 'TTA/OnTop(二选一)';
comment on column tta_npp_new_product_report.purchase_act
  is '采购行动';
comment on column tta_npp_new_product_report.exemption_reason
  is '豁免原因-1';
comment on column tta_npp_new_product_report.exemption_reason2
  is '豁免原因-2';
comment on column tta_npp_new_product_report.debit_order_code
  is '借记单编号';
comment on column tta_npp_new_product_report.aboi_receipts
  is 'ABOI系统实收金额';
comment on column tta_npp_new_product_report.batch_code
  is '批次编号';
comment on column tta_npp_new_product_report.transfer_in_person
  is '转办人(转入)';
comment on column tta_npp_new_product_report.remark
  is '备注';

comment on column tta_npp_new_product_report.status
  is '状态(1生效,0失效)';
comment on column tta_npp_new_product_report.parent_id
  is '拆分前的ID';
comment on column tta_npp_new_product_report.parent_vendor_code
  is '拆分前的VENDOR_CODE';
comment on column tta_npp_new_product_report.parent_vendor_name
  is '拆分前的VENDOR_NAME';
comment on column tta_npp_new_product_report.transfer_out_person
  is '转办人(转出)';
comment on column tta_npp_new_product_report.transfer_last_person
  is '转办人(最后一次转出的人)';
comment on column tta_npp_new_product_report.transfer_in_date
  is '转办人(转入)时间';
comment on column tta_npp_new_product_report.transfer_out_date
  is '转办人(转出)时间';
comment on column tta_npp_new_product_report.transfer_last_date
  is '转办人(最后一次转出的人)';
comment on column tta_npp_new_product_report.charge_unit_name
  is '计费单位名称';
comment on column tta_npp_new_product_report.original_before_amount
  is '拆分前的应收金额';
comment on column tta_npp_new_product_report.addition_rate
  is '加成比例';
comment on column tta_npp_new_product_report.is_create
  is '是否生成';
comment on column tta_npp_new_product_report.receive_amount_add
is '应收金额(含加成)';
comment on column tta_npp_new_product_report.group_code is 'group_code';
comment on column tta_npp_new_product_report.dept_code is 'dept_code';
comment on column tta_npp_new_product_report.charge_method is '收费方式';
comment on column tta_npp_new_product_report.annual_month_n is '实际月份';
comment on column tta_npp_new_product_report.month_n is '创建月份';
comment on column tta_npp_new_product_report.process_id is '流程单据id';

-- Create/Recreate primary, unique and foreign key constraints
alter table tta_npp_new_product_report
  add constraint tta_npp_new_product_report_pk primary key (npp_id);

--select * from tta_npp_new_product_report;
create sequence seq_tta_npp_new_product
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocycle
nocache;

---------------2019.11.20--------------end----------------------
----------------2019.12.2 添加tta_brandpln_line 表的字段 start(已发布到测试库,未发布到正式库,已不发)-------
alter table tta_brandpln_line add(annual_purchase number);
alter table tta_brandpln_line add(annual_sales number);
comment on column tta_brandpln_line.annual_purchase is '预估全年采购额';
comment on column tta_brandpln_line.annual_sales is '预估全年销售额';

alter table tta_brandpln_line add(fcs_purchase_con varchar2(10));
alter table tta_brandpln_line add(purchase_growth_con varchar2(10));
alter table tta_brandpln_line add(fcs_sales_con varchar2(10));
alter table tta_brandpln_line add(sales_growth_con varchar2(10));

------------------2019.12.2 end ------------------------------------

----------------2019.12.13 start---------------------------------
alter table tta_supplier_item_header add(user_group_code varchar2(30));
alter table tta_supplier_item_header add(user_group_name varchar2(200));
alter table tta_supplier_item_header add(major_dept_id integer not null);
alter table tta_supplier_item_header add(major_dept_code varchar2(100) not null);
alter table tta_supplier_item_header add(major_dept_desc varchar2(200) not null);

comment on column tta_supplier_item_header.user_group_code is '用户group';
comment on column tta_supplier_item_header.major_dept_desc is '用户主部门';

create or replace view tta_dept_fee_terms_report_v as
       -- 查询部门协定标准的值
select tdf.deptfee_line_id,
       tdf.line_code,
       tdf.parent_line_code,
       tdf.unit,
       tdf.standard_unit_value,
       tdf.unit1,
       tdf.standard_value1,
       tdf.unit2,
       tdf.standard_value2,
       tdf.unit3,
       tdf.standard_value3,
       tph.dept_code1,
       tph.dept_desc1,
       tph.dept_code2,
       tph.dept_desc2,
       tph.dept_code3,
       tph.dept_desc3
  from tta_proposal_terms_line tptl
 inner join tta_dept_fee tdf
    on tdf.proposal_id = tptl.proposal_Id
 inner join tta_proposal_header tph
    on tdf.proposal_id = tph.proposal_id
    and instr(tptl.y_terms,
             decode(nvl(tdf.dm_flyer, '陈列服务费'),
                    'DM',
                    'DM',
                    'FLYER',
                    'FYLER',
                    '陈列服务费')) > 0
   AND nvl(tptl.income_type, '按公司标准') = '按其他协定标准'
   and tptl.p_code <> '0';

----------------2019.12.13 end ----------------------------------
--------------- 2019.12.17 start --------------------------------
alter table tta_proposal_header add(user_group_code varchar2(30));
alter table tta_proposal_header add(user_group_name varchar2(200));

comment on column  tta_proposal_header.user_group_code is '用户group_code';
comment on column tta_proposal_header.user_group_name is '用户group_name';
--------------- 2019.12.17 end ----------------------------------
-------------2019.12.23 start ----------------------------------
alter table tta_supplier_item_header add(proposal_code varchar2(50));
alter table tta_supplier_item_header add(vendor_nbr varchar2(50));
alter table tta_supplier_item_header add(vendor_name varchar2(150));
alter table tta_supplier_item_header add(proposal_year varchar2(30));

comment on column tta_supplier_item_header.proposal_code is 'proposal单号';
comment on column tta_supplier_item_header.vendor_nbr is 'proposal供应商编号';
comment on column tta_supplier_item_header.vendor_name is 'proposal供应商名称';
comment on column tta_supplier_item_header.proposal_year is 'proposal年度';
-------------2019.12.23 end ------------------------------------
----------------2019.12.24 start--------------------------------
 alter table  tta_supplier_item_mid add(fcs_Purchase number);
 alter table  tta_supplier_item_mid add(fcs_Sales number);
----------------2019.12.24 end----------------------------------
----------------2019.12.30 start--------------------------------
--修改品牌计划头 调整GP
CREATE OR REPLACE VIEW TTA_BRANDPLN_HEADER_V AS
SELECT t."BRANDPLN_H_ID",
tph.new_or_existing  "NEW_OR_EXISTING",
tph.vendor_nbr  "VENDOR_NBR",
tph.vendor_name "VENDOR_NAME",
t."PO_RECORD_SUM",
t."SALES_SUM",
t."ACTUAL_GP",
t."FCS_PURCHASE",
t."PURCHASE_GROWTH",
t."FCS_SALES",
t."SALES_GROWTH",
t."GP",t."REMARK",
t."CREATION_DATE",
t."CREATED_BY",
t."LAST_UPDATED_BY",
t."LAST_UPDATE_DATE",
t."LAST_UPDATE_LOGIN",
t."VERSION_NUM",
t."PROPOSAL_ID",
t."PO_RECORD_SUM2",
t."SALES_SUM2",
t."ACTUAL_GP2",
t."ADJUST_GP",
tph.proposal_year "YEAR_CODE",
u1.user_name last_updated_name,
u2.user_name created_name

   FROM tta_brandpln_header t,
        tta_proposal_header tph,
      base_users u1,
      base_users u2
   where  t.last_updated_by = u1.user_id(+)
   and t.proposal_id = tph.proposal_id(+)
   and t.created_by = u2.user_id(+);

----------------2019.12.30 end----------------------------------

-------------------2020.2.5 start------------------------------------------
--tta_supplier_item_mid表 添加字段(正式环境已存在,测试环境未存在,需要添加,已添加)
alter table tta_supplier_item_mid add(purchase number);
alter table tta_supplier_item_mid add(sales number);
alter table tta_supplier_item_mid add(cost number);
alter table tta_supplier_item_mid add(purch_type varchar2(50));
-------------------2020.2.5 end--------------------------------------------
-------------------2020.2.5 start------------------------------------------
--tta_split_merge_data_temp表 添加字段(正式环境已存在,测试环境未存在,需要在测试环境添加,已添加)
alter table tta_split_merge_data_temp add(purch_type varchar2(50));
alter table tta_split_merge_data_temp add(cost number);
alter table tta_split_merge_data_temp add(po_amt number);
alter table tta_split_merge_data_temp add(sales_amt number);
alter table tta_split_merge_data_temp add(sales_exclude_amt number);
-------------------2020.2.5 end--------------------------------------------
-----------------------------2020.2.11 start---------------------------------------------------------
--tta_analysis_edit_data 添加字段(测试环境已发布,正式环境已发布)
alter table tta_analysis_edit_data add(old_aboi_list varchar2(4000));
comment on column tta_analysis_edit_data.old_aboi_list is 'ABOI类型下所有条款往年的onTop值';
alter table tta_analysis_edit_data  add(old_free_goods number(38));
comment on column tta_analysis_edit_data.old_free_goods is '往年的freeGoods';
-----------------------------2020.2.11 end ---------------------------------------------------------
----------------------2020.2.17 start(测试环境已发布,正式环境未发布,正式环境已发布)----------------------------------------------
alter table tta_supplier_item_mid add(brand_fcs_purchase number);
alter table tta_supplier_item_mid add(brand_fcs_sales number);
comment on column tta_supplier_item_mid.brand_fcs_purchase is '品牌计划表的Fcs Purchase';
comment on column tta_supplier_item_mid.brand_fcs_sales is '品牌计划表的Fcs Sales';
----------------------2020.2.17 end------------------------------------------------
----------------------2020.2.26 start------------------------------------------------
--tta_analysis_line 创建表(测试环境,正式环境已发布脚本)
create table tta_analysis_line(
  id number,
  anaysis_id varchar2(50),
  supplier_code varchar2(50),
  supplier_name varchar2(150),
  region varchar2(200),
  classs varchar2(100),
  buyer varchar2(80),
  owner_dept varchar2(100),
  trading_mode varchar2(150),
  contract_version varchar2(30),
  Brand varchar2(300),
  New_or_Existing varchar2(50),
  Forecast_Purchase number,
  Purchase number,
  GP number,
  BEOI number,
  SROI number,
  ABOI number,
  TotalOI number,
  NM number,
  Batch_number varchar2(100),
  proposal_year varchar2(50),
  proposal_id number,
  order_no number,
  data_type varchar2(50),
  version_code number,
  CONTRACT_L_ID number,
  Terms_Comparison varchar2(200),
  oi_type varchar2(80),
  terms_cn varchar2(300),
  terms_en varchar2(200),
  former_year_tta number,   --往年tta
  former_year_ontop number, --往年ontop
  former_year_tta_ontop number, --往年tta+ontop
  former_year_actual number,  --往年actual
  current_year_tta  number, --当年tta
  current_year_ontop number, --当年ontop
  current_year_tta_ontop number, --当年 tta +ontop
  tta_ontop_Incre_amt  number, --tta_ontop 升幅(+/-$)
  tta_ontop_percent_gain  number,  --tta_ontop 升幅(+/-%)

  tta_ontop_act_Incre_amt number,--tta_ontop_act 升幅(+/-$)
  tta_ontop_act_percent_gain  number,  --tta_ontop_act 升幅(+/-%)

  purchase_remark varchar2(3000),
  BIC_REMARK varchar2(4000),
  Bench_mark varchar2(2000),
  creation_date         DATE default SYSDATE,
  created_by            NUMBER,
  last_updated_by       NUMBER,
  last_update_date      DATE default SYSDATE,
  last_update_login     NUMBER,
  version_num           NUMBER
)

comment on table tta_analysis_line is 'analysis行表';

comment on column tta_analysis_line.anaysis_id is '行唯一标识';
comment on column tta_analysis_line.supplier_code is '供应商编号';
comment on column tta_analysis_line.supplier_name is '供应商名称';
comment on column tta_analysis_line.region is '区域';
comment on column tta_analysis_line.class is '类型';
comment on column tta_analysis_line.buyer is '采购人';
comment on column tta_analysis_line.owner_dept is '部门';
comment on column tta_analysis_line.trading_mode is '交易模式(销售方式)';
comment on column tta_analysis_line.contract_version is '合同版本';
comment on column tta_analysis_line.Brand is '品牌';
comment on column tta_analysis_line.New_or_Existing is '新供应或者续签供应商';
--comment on column tta_analysis_line.Forecast_Purchase is '';
--comment on column tta_analysis_line.Purchase is '';
--comment on column tta_analysis_line.GP is '';
--comment on column tta_analysis_line.BEOI is '';
--comment on column tta_analysis_line.SROI is '';
--comment on column tta_analysis_line.ABOI is '';
--comment on column tta_analysis_line.TotalOI is '';
--comment on column tta_analysis_line.NM is '';
comment on column tta_analysis_line.Batch_number is '批次号';
comment on column tta_analysis_line.proposal_year is 'proposal制作年度';
comment on column tta_analysis_line.order_no is '排序号';
comment on column tta_analysis_line.data_type is '数据类型(区分头部分还是行部分)';
comment on column tta_analysis_line.version_code is '版本';
comment on column tta_analysis_line.CONTRACT_L_ID is '合同行id(条款id)';

comment on column tta_analysis_line.Terms_Comparison is 'Terms_Comparison';
comment on column tta_analysis_line.oi_type is '费用类型(beoi,sroi,aboi)';
comment on column tta_analysis_line.terms_cn is '条款中文名';
comment on column tta_analysis_line.terms_en is '条款英文名';
comment on column tta_analysis_line.former_year_tta is '往年tta';
comment on column tta_analysis_line.former_year_ontop is '往年ontop';
comment on column tta_analysis_line.former_year_tta_ontop is '往年tta+ontop';
comment on column tta_analysis_line.former_year_actual is '往年actual';
comment on column tta_analysis_line.current_year_tta is '当年tta';
comment on column tta_analysis_line.current_year_ontop is '当年ontop';
comment on column tta_analysis_line.current_year_tta_ontop is '当年 tta +ontop';
comment on column tta_analysis_line.tta_ontop_Incre_amt is 'tta_ontop 升幅(+/-$)';
comment on column tta_analysis_line.tta_ontop_percent_gain is 'tta_ontop 升幅(+/-%)';
comment on column tta_analysis_line.tta_ontop_act_Incre_amt is 'tta_ontop_act 升幅(+/-$)';
comment on column tta_analysis_line.tta_ontop_act_percent_gain is 'tta_ontop_act 升幅(+/-%)';
comment on column tta_analysis_line.purchase_remark is '采购备注';
comment on column tta_analysis_line.BIC_REMARK is 'BIC备注';
--comment on column tta_analysis_line.Bench_mark is '';
alter table tta_analysis_line rename column class to classs;

create sequence SEQ_TTA_ANALYSIS_LINE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocache;
---------------------2020.2.26 end-------------------------------------------------
---------2020.3.24 start(测试环境已发布,正式环境已发布)------
create table tta_abnormal_supplier_brand(
       supplier_brand_id number not null,
       supplier_code varchar2(100),
       supplier_name varchar2(300),
       group_code varchar2(50),
       group_name varchar2(150),
       dept_code  varchar2(50),
       dept_name  varchar2(150),
       brand_code varchar2(50),
       brand_cn   varchar2(300),
       brand_en   varchar2(230),
       active_status varchar2(3) default '1',
       bu_scorecard      VARCHAR2(150),
       win_top_supplier  VARCHAR2(150),
       kb_scorecard      VARCHAR2(150),
       creation_date     DATE default SYSDATE,
       created_by        NUMBER,
       last_updated_by   NUMBER,
       last_update_date  DATE default SYSDATE,
       last_update_login NUMBER,
       version_num       NUMBER,
       actual_call_data Date
);
alter table tta_abnormal_supplier_brand add(batch_number varchar2(50));
alter table tta_abnormal_supplier_brand add(account_month varchar2(30));
alter table tta_abnormal_supplier_brand add(year varchar2(15));
alter table tta_abnormal_supplier_brand add(is_create varchar2(4) default '0');

comment on column tta_abnormal_supplier_brand.batch_number is '批次号';
comment on column tta_abnormal_supplier_brand.account_month is '月份';
comment on column tta_abnormal_supplier_brand.year is '年度';
comment on column tta_abnormal_supplier_brand.is_create is '是否生成供应商品牌信息(1:是,0:否)';

comment on table tta_abnormal_supplier_brand is '异常品牌信息表';

comment on column tta_abnormal_supplier_brand.supplier_brand_id is '主键';
comment on column tta_abnormal_supplier_brand.supplier_code is '供应商编号';
comment on column tta_abnormal_supplier_brand.supplier_name is '供应商名称';

comment on column tta_abnormal_supplier_brand.group_code is 'GROUP';
comment on column tta_abnormal_supplier_brand.group_name is 'GROUP名称';
comment on column tta_abnormal_supplier_brand.dept_code is 'dept_code';
comment on column tta_abnormal_supplier_brand.dept_name is 'dept_name';
comment on column tta_abnormal_supplier_brand.brand_code is '品牌编号';
comment on column tta_abnormal_supplier_brand.brand_cn is '品牌中文名';
comment on column tta_abnormal_supplier_brand.brand_en is '品牌英文名';
comment on column tta_abnormal_supplier_brand.active_status is '状态(生效:1失效:0)';
comment on column tta_abnormal_supplier_brand.actual_call_data is '拉取数据时间';

alter table tta_abnormal_supplier_brand add primary key (supplier_brand_id);

create sequence seq_tta_abnormal_supplier_brand
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocycle
nocache;
---------2020.3.24 end -------

-----------2020.3.27 start (测试环境已发布,正式环境)-------------
---创建拉取品牌数据更新时间表
create table tta_call_data_time_increase(
      id number,
      create_date varchar2(50) ,
      refer_batch_number varchar2(100)
);

comment on table tta_call_data_time_increase is '供应商品牌生成批次表';
comment on column tta_call_data_time_increase.create_date is '刷新时间';
comment on column tta_call_data_time_increase.refer_batch_number is '批次';

create sequence seq_tta_call_data_time_increase
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocycle
nocache;
-----------2020.3.27 end -------------