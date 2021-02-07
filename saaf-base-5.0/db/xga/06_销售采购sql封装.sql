------- 采用模板表
create table TTA_SALE_SUM
(
  tran_date             NUMBER,
  vendor_nbr            VARCHAR2(50),
  shop_code             VARCHAR2(50),
  warehouse_code        NUMBER,
  item_nbr              VARCHAR2(50),
  sales_qty             NUMBER,
  sales_exclude_amt     NUMBER,
  purch_type            VARCHAR2(50),
  cost                  NUMBER,
  gp_rate               NUMBER,
  po_sum_amt            NUMBER,
  po_rate               NUMBER,
  gp_amt                NUMBER,
  gp_sale_supplier_rate NUMBER,
  sales_amt             NUMBER,
  gp_supplier_popt_amt  NUMBER,
  gp_rate_supplier      NUMBER,
  po_popt               NUMBER,
  po_amt                NUMBER,
  PO_TYPE               VARCHAR2(2),
  ORIGINAL_UNIT_COST    NUMBER,
  PO_REMARK             VARCHAR2(800),
  split_supplier_code   VARCHAR2(50),
  split_supplier_name   VARCHAR2(100),
  split_count           NUMBER,
  creation_date         DATE,
  last_update_date      DATE,
  version_num           NUMBER,
  created_by            NUMBER,
  last_updated_by       NUMBER,
  last_update_login     NUMBER
);

-- tta_purchase_sum_sale_v_2019
 create or replace view tta_purchase_detail_v as
    select
          trade_year_month,
          receive_date,
          po_nbr,
          purch_type,
          item_nbr,
          item_desc_en,
          item_desc_cn,
          receiving_amount,
          receiving_qty,
          warehouse_code,
          warehouse_name,
          shop_code,
          shop_name,
          vendor_nbr,
          receiving_amount_sum, -- 按月份，warehouse_code,item_nbr分组后的汇总
          round(nvl((case when receiving_amount_sum = 0 then 0 else  receiving_amount/receiving_amount_sum end),0),8)  as PO_RATE_AMT,
          po_type,
          remark,
         ORIGIN_UNIT_COST
      from
  (
      select  t.trade_year_month,
     t.receive_date,
     t.po_nbr,
     t.purch_type,
     t.item_nbr,
     t.item_desc_en,
     t.item_desc_cn,
     t.receiving_amount,
     t.receiving_qty,
     t.warehouse_code,
     t.warehouse_name,
     t.shop_code,
     t.shop_name,
     t.vendor_nbr,
     t.po_type,
     t.remark,
     t.ORIGIN_UNIT_COST,
     t1.receiving_amount_sum as receiving_amount_sum
  from TTA_PURCHASE_DETAIL_TEMP_TABLE t
  left join (select b.item_nbr,
             b.warehouse_code,
             round(sum(nvl(b.receiving_amount, 0)), 8) as receiving_amount_sum
        from TTA_PURCHASE_DETAIL_TEMP_TABLE b
       group by b.warehouse_code, b.item_nbr) t1
       on t.warehouse_code = t1.warehouse_code
       and t.item_nbr=t1.item_nbr
  ) v;

 -- step1.创建临时表及索引
    DROP TABLE TTA_PURCHASE_DETAIL_TEMP_TABLE;
    create table TTA_PURCHASE_DETAIL_TEMP_TABLE
    (
      trade_year_month NUMBER,
      receive_date     VARCHAR2(50),
      po_nbr           VARCHAR2(50),
      purch_type       VARCHAR2(20),
      item_nbr         VARCHAR2(50),
      item_desc_en     VARCHAR2(800),
      item_desc_cn     VARCHAR2(500),
      receiving_amount NUMBER,
      receiving_qty    NUMBER(16,4),
      warehouse_code   NUMBER,
      warehouse_name   VARCHAR2(200),
      shop_code        NUMBER,
      shop_name        VARCHAR2(100),
      vendor_nbr       VARCHAR2(50),
      po_type          VARCHAR2(20), -- RETRUN, PURCHASE
      remark           VARCHAR2(1000),
      origin_unit_cost  NUMBER
    );
    create index IDX_ITEM_NBR on TTA_PURCHASE_DETAIL_TEMP_TABLE (ITEM_NBR);
    create index IDX_WAREHOUSE_CODE on TTA_PURCHASE_DETAIL_TEMP_TABLE (WAREHOUSE_CODE);
    
   -- Create table
    create table tta_sales_dc_ytd_sum_temp
    (
      tran_date         CHAR(6),
      shop_code         VARCHAR2(50),
      item              VARCHAR2(50),
      sales_qty         NUMBER,
      sales_amt         NUMBER,
      sales_exclude_amt NUMBER,
      purch_type        VARCHAR2(50),
      cost              NUMBER,
      gp                NUMBER,
      warehouse_code    NUMBER(20)
    );
    create index IDX_SHOP_CODE on tta_sales_dc_ytd_sum_temp (SHOP_CODE);
    create index IDX_TEMP_WAREHOUSE_CODE on tta_sales_dc_ytd_sum_temp (WAREHOUSE_CODE);
            
    
   create table TTA_SALES_DC_SUM -- 记得自动创建表明，每年一张表
    (
      tran_date         number,
      shop_code         VARCHAR2(50),
      item              VARCHAR2(50),
      sales_qty         NUMBER,
      sales_amt         NUMBER,
      sales_exclude_amt NUMBER,
      purch_type        VARCHAR2(50),
      cost              NUMBER,
      gp                NUMBER,
      warehouse_code    NUMBER(20)
    );

-- 注意：动态创建表需要管理员执行：grant create any table to 用户;
-- 自动创建采购表
create or replace procedure PRC_CREATE_TTA_SALES_DC_SUM is
  sqlstr varchar(500);
begin
    sqlstr := 'create table TTA_SALES_DC_SUM_' || TO_CHAR(add_months(sysdate,12), 'YYYY') || ' as select *  from TTA_SALES_DC_SUM';
    execute immediate sqlstr;
end;

declare
 job_num number;
begin
 dbms_job.submit(job_num,
     'PRC_CREATE_TTA_SALES_DC_SUM;',
     ADD_MONTHS(trunc(sysdate,'YYYY'),11), --每1年12/1
    'ADD_MONTHS(trunc(sysdate,''YYYY''),11)' );
 commit;
 dbms_output.put_line(job_num);
end;



































































