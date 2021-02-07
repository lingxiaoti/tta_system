-- #######################hmb start#######################--
/*==============================================================*/
/* Table: TTA_SA_STD_HEADER                                     */
/*==============================================================*/
create table TTA_SA_STD_HEADER
(
   SA_STD_HEADER_ID     INTEGER              not null,
   SA_STD_TPL_DEF_HEADER_ID INTEGER,
   SA_STD_CODE          VARCHAR2(50),
   SA_STD_VERSION       INTEGER,
   SA_TEYP              varchar2(15),
   VENDOR_CODE          VARCHAR2(100),
   VENDOR_NAME          VARCHAR2(500),
   TPL_ID               INTEGER,
   TPL_NAME             VARCHAR2(100),
   EFFECTIVE_START_TIME DATE,
   EFFECTIVE_END_TIME   date,
   STATUS               VARCHAR2(2),
   VERSION_NUM          INTEGER              default 0,
   CREATION_DATE        DATE                 default SYSDATE,
   CREATED_BY           INTEGER,
   LAST_UPDATED_BY      INTEGER,
   LAST_UPDATE_DATE     DATE                 default SYSDATE,
   LAST_UPDATE_LOGIN    INTEGER,
   constraint PK_TTA_SA_STD_HEADER primary key (SA_STD_HEADER_ID)
);

alter table TTA_SA_STD_HEADER add(approve_date date);
comment on column TTA_SA_STD_HEADER.approve_date is '审批通过时间';

alter table TTA_SA_STD_HEADER add(ALERT_BY INTEGER);
comment on column TTA_SA_STD_HEADER.ALERT_BY is '变更人';
alter table TTA_SA_STD_HEADER add(ALERT_DATE date);
comment on column TTA_SA_STD_HEADER.ALERT_DATE is '变更时间';
alter table TTA_SA_STD_HEADER add(IS_CHANGE varchar2(10));
comment on column TTA_SA_STD_HEADER.IS_CHANGE is '是否变更(Y是,N否)';
alter table TTA_SA_STD_HEADER add(version_status varchar2(10));
comment on column TTA_SA_STD_HEADER.version_status is '版本状态,(1生效0失效)';

alter table TTA_SA_STD_HEADER add(org_code varchar2(30));
alter table TTA_SA_STD_HEADER add(dept_code varchar2(30));
comment on column TTA_SA_STD_HEADER.org_code is '大部门';
comment on column TTA_SA_STD_HEADER.dept_code is '小部门';

alter table TTA_SA_STD_HEADER add(org_name varchar2(80));
alter table TTA_SA_STD_HEADER add(dept_name varchar2(80));
comment on column TTA_SA_STD_HEADER.org_name is '大部门';
comment on column TTA_SA_STD_HEADER.dept_name is '小部门';

comment on table TTA_SA_STD_HEADER is
'补充协议信息头表 （[补充协议字段说明（标准）]';

comment on column TTA_SA_STD_HEADER.SA_STD_HEADER_ID is
'主键';

comment on column TTA_SA_STD_HEADER.SA_STD_TPL_DEF_HEADER_ID is
'外键';

comment on column TTA_SA_STD_HEADER.SA_STD_CODE is
'单据号：按照单据规则生成（系统自动生成）';

comment on column TTA_SA_STD_HEADER.SA_STD_VERSION is
'版本号：从1开始创建（系统自动生成）';

comment on column TTA_SA_STD_HEADER.SA_TEYP is
'补充协议类型：standard.标准,nostandard.非标准';

comment on column TTA_SA_STD_HEADER.VENDOR_CODE is
'供应商编号';

comment on column TTA_SA_STD_HEADER.VENDOR_NAME is
'供应商名称';

comment on column TTA_SA_STD_HEADER.TPL_ID is
'模板ID TTA_SA_STD_TPL_DEF_HEADER表id';

comment on column TTA_SA_STD_HEADER.TPL_NAME is
'模板名称';

comment on column TTA_SA_STD_HEADER.EFFECTIVE_START_TIME is
'合同生效时间';

comment on column TTA_SA_STD_HEADER.EFFECTIVE_END_TIME is
'合同失效截止时间';

comment on column TTA_SA_STD_HEADER.STATUS is
'状态：A制单，B审批，C审批通过，D作废';

comment on column TTA_SA_STD_HEADER.VERSION_NUM is
'版本号';

comment on column TTA_SA_STD_HEADER.CREATION_DATE is
'创建日期';

comment on column TTA_SA_STD_HEADER.CREATED_BY is
'创建者';

comment on column TTA_SA_STD_HEADER.LAST_UPDATED_BY is
'最近修改人';

comment on column TTA_SA_STD_HEADER.LAST_UPDATE_DATE is
'最近修改时间';

comment on column TTA_SA_STD_HEADER.LAST_UPDATE_LOGIN is
'最近登录人';

/*==============================================================*/
/* Table: TTA_SA_STD_PROPOSAL_LINE                              */
/*==============================================================*/
create table TTA_SA_STD_PROPOSAL_LINE
(
   SA_STD_PROPOSAL_LINE INTEGER              not null,
   SA_STD_HEADER_ID     INTEGER,
   PROPOSAL_ORDER       VARCHAR2(80),
   PROPOSAL_VERSION     integer,
   PROPOSAL_YEAR        integer,
   VENDOR_CODE          VARCHAR2(80),
   VERSION_NUM          INTEGER              default 0,
   CREATION_DATE        DATE                 default SYSDATE,
   CREATED_BY           INTEGER,
   LAST_UPDATED_BY      INTEGER,
   LAST_UPDATE_DATE     DATE                 default SYSDATE,
   LAST_UPDATE_LOGIN    INTEGER,
   constraint PK_TTA_SA_STD_PROPOSAL_LINE primary key (SA_STD_PROPOSAL_LINE)
);

alter table TTA_SA_STD_PROPOSAL_LINE add(VENDOR_NAME varchar2(300));
comment on column TTA_SA_STD_PROPOSAL_LINE.VENDOR_NAME is '供应商名称';
alter table  tta_sa_std_proposal_line add(proposal_id number) ;
alter table  tta_sa_std_proposal_line add(contract_h_id number);

comment on table TTA_SA_STD_PROPOSAL_LINE is
'补充协议PROPOSAL行表 （[补充协议字段说明（标准）]';

comment on column TTA_SA_STD_PROPOSAL_LINE.SA_STD_PROPOSAL_LINE is
'主键';

comment on column TTA_SA_STD_PROPOSAL_LINE.SA_STD_HEADER_ID is
'外键(TTA_SA_STD_HEADER表的id)';

comment on column TTA_SA_STD_PROPOSAL_LINE.PROPOSAL_ORDER is
'Proposal单据号';

comment on column TTA_SA_STD_PROPOSAL_LINE.PROPOSAL_VERSION is
'Proposal版本号';

comment on column TTA_SA_STD_PROPOSAL_LINE.PROPOSAL_YEAR is
'proposal年度';

comment on column TTA_SA_STD_PROPOSAL_LINE.VENDOR_CODE is
'供应商编号';

comment on column TTA_SA_STD_PROPOSAL_LINE.VERSION_NUM is
'版本号';

comment on column TTA_SA_STD_PROPOSAL_LINE.CREATION_DATE is
'创建日期';

comment on column TTA_SA_STD_PROPOSAL_LINE.CREATED_BY is
'创建者';

comment on column TTA_SA_STD_PROPOSAL_LINE.LAST_UPDATED_BY is
'最近修改人';

comment on column TTA_SA_STD_PROPOSAL_LINE.LAST_UPDATE_DATE is
'最近修改时间';

comment on column TTA_SA_STD_PROPOSAL_LINE.LAST_UPDATE_LOGIN is
'最近登录人';

/*==============================================================*/
/* Table: TTA_SA_STD_FIELD_LINE                                 */
/*==============================================================*/
create table TTA_SA_STD_FIELD_LINE
(
   SA_STD_FIELD_LINE_ID INTEGER              not null,
   SA_STD_HEADER_ID     INTEGER,
   SA_STD_FIELD_CFG_LINE_ID INTEGER              not null,
   FILED_NAME           VARCHAR2(200)        not null,
   FILED_VALUE          VARCHAR2(200),
   FILED_TYPE           VARCHAR2(50)          not null,
   ORDER_NUM            INTEGER,
   VERSION_NUM          INTEGER              default 0,
   CREATION_DATE        DATE                 default SYSDATE,
   CREATED_BY           INTEGER,
   LAST_UPDATED_BY      INTEGER,
   LAST_UPDATE_DATE     DATE                 default SYSDATE,
   LAST_UPDATE_LOGIN    INTEGER,
   constraint PK_TTA_SA_STD_FIELD_LINE primary key (SA_STD_FIELD_LINE_ID)
);

alter table TTA_SA_STD_FIELD_LINE add(tpl_code varchar2(100));
comment on column TTA_SA_STD_FIELD_LINE.tpl_code is '字段编码';
alter table TTA_SA_STD_FIELD_LINE add(dic_code varchar2(80));
comment on column TTA_SA_STD_FIELD_LINE.dic_code is '字典值';

comment on table TTA_SA_STD_FIELD_LINE is
'补充协议标准字段表';
comment on column TTA_SA_STD_FIELD_LINE.SA_STD_FIELD_LINE_ID is '主键';
comment on column TTA_SA_STD_FIELD_LINE.SA_STD_HEADER_ID is
'外键(TTA_SA_STD_HEADER表的id)';

comment on column TTA_SA_STD_FIELD_LINE.SA_STD_FIELD_CFG_LINE_ID is
'外键：TTA_SA_STD_FIELD_CFG_LINE 表';

comment on column TTA_SA_STD_FIELD_LINE.FILED_NAME is
'字段名称';

comment on column TTA_SA_STD_FIELD_LINE.FILED_VALUE is
'字段值';

comment on column TTA_SA_STD_FIELD_LINE.FILED_TYPE is
'字段类型：1:文本/2.日期/3.数字';

comment on column TTA_SA_STD_FIELD_LINE.ORDER_NUM is
'排序号';

comment on column TTA_SA_STD_FIELD_LINE.VERSION_NUM is
'版本号';

comment on column TTA_SA_STD_FIELD_LINE.CREATION_DATE is
'创建日期';

comment on column TTA_SA_STD_FIELD_LINE.CREATED_BY is
'创建者';

comment on column TTA_SA_STD_FIELD_LINE.LAST_UPDATED_BY is
'最近修改人';

comment on column TTA_SA_STD_FIELD_LINE.LAST_UPDATE_DATE is
'最近修改时间';

comment on column TTA_SA_STD_FIELD_LINE.LAST_UPDATE_LOGIN is
'最近登录人';

/*==============================================================*/
/* Table: TTA_SA_TAB_LINE                                       */
/*==============================================================*/
create table TTA_SA_TAB_LINE
(
   SA_TAB_LINE_ID       INTEGER              not null,
   SA_STD_HEADER_ID     INTEGER,
   SA_TPL_TAB_LINE_ID   INTEGER,
   ROW_NAME             VARCHAR2(200)        not null,
   COL_NAME             VARCHAR2(100)         not null,
   X_POINT              VARCHAR2(100)             not null,
   Y_POINT              INTEGER              not null,
   POINT_VALUE          VARCHAR2(500),
   VERSION_NUM          INTEGER              default 0,
   CREATION_DATE        DATE                 default SYSDATE,
   CREATED_BY           INTEGER,
   LAST_UPDATED_BY      INTEGER,
   LAST_UPDATE_DATE     DATE                 default SYSDATE,
   LAST_UPDATE_LOGIN    INTEGER,
   constraint PK_TTA_SA_TAB_LINE primary key (SA_TAB_LINE_ID)
);

alter table TTA_SA_TAB_LINE add(grid_point_value varchar2(100));
comment on column TTA_SA_TAB_LINE.grid_point_value is '格子的坐标值';

comment on table TTA_SA_TAB_LINE is
'补充协议表格信息表';

comment on column TTA_SA_TAB_LINE.SA_TAB_LINE_ID is
'行ID主键';

comment on column TTA_SA_TAB_LINE.SA_STD_HEADER_ID is
'外键 TTA_SA_STD_HEADER';

comment on column TTA_SA_TAB_LINE.SA_TPL_TAB_LINE_ID is
'外键 TTA_SA_TPL_TAB_LINE';

comment on column TTA_SA_TAB_LINE.ROW_NAME is
'行名称';

comment on column TTA_SA_TAB_LINE.COL_NAME is
'列名称';

comment on column TTA_SA_TAB_LINE.X_POINT is
'X坐标';

comment on column TTA_SA_TAB_LINE.Y_POINT is
'Y坐标';

comment on column TTA_SA_TAB_LINE.POINT_VALUE is
'坐标值';

comment on column TTA_SA_TAB_LINE.VERSION_NUM is
'版本号';

comment on column TTA_SA_TAB_LINE.CREATION_DATE is
'创建日期';

comment on column TTA_SA_TAB_LINE.CREATED_BY is
'创建者';

comment on column TTA_SA_TAB_LINE.LAST_UPDATED_BY is
'最近修改人';

comment on column TTA_SA_TAB_LINE.LAST_UPDATE_DATE is
'最近修改时间';

comment on column TTA_SA_TAB_LINE.LAST_UPDATE_LOGIN is
'最近登录人';

--创建序列

create sequence SEQ_SA_STD_HEADER
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocycle
nocache;
-----------------
create sequence SEQ_SA_STD_PROPOSAL_LINE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocycle
nocache;
-----------------
create sequence SEQ_SA_STD_FIELD_LINE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocycle
nocache;
------------------
create sequence SEQ_SA_TAB_LINE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocycle
nocache;
-------------
----补充协议标准变更功能
create or replace procedure P_TTA_SUPPLEMENT_AGREMENT_COPY(P_ORDER_ID    NUMBER,--saStdHeaderIdid
                                P_USER_ID     NUMBER,--用户id
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                                P_RT_STDHEADER_ID  OUT VARCHAR2) IS
     ------###############功能:补充协议标准变更或复制###################

     V_COUNT               NUMBER; --计数器
     V_TTA_SA_STD_HEADER          TTA_SA_STD_HEADER%rowtype; -- 补充协议标准头信息
     V_TTA_SA_STD_PROPOSAL_LINE   TTA_SA_STD_PROPOSAL_LINE%rowtype;--匹配Proposal信息
     V_TTA_SA_STD_FIELD_LINE      TTA_SA_STD_FIELD_LINE%rowtype;--补充协议拓展信息
     V_TTA_SA_TAB_LINE            TTA_SA_TAB_LINE%rowtype;--补充协议表格信息


   BEGIN
     P_ISSUCCESS := 1;
     P_ERR_MESSAGE := '执行成功';
     BEGIN
       --P_ORDER_ID 源id
       --P_RT_STDHEADER_ID 返回生成的id
        --判断是否是审核状态的数据
        SELECT COUNT(1)
          INTO V_COUNT
          FROM tta_sa_std_header tssh
         WHERE tssh.sa_std_header_id = P_ORDER_ID
           AND tssh.status = 'C';
        IF V_COUNT = 0 THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']状态为审批通过的单据';
          RETURN;
        END IF;

        --复制数据
         SELECT TT.*
          INTO V_TTA_SA_STD_HEADER
          FROM TTA_SA_STD_HEADER TT
         WHERE TT.SA_STD_HEADER_ID = P_ORDER_ID
           AND TT.STATUS = 'C';

         --修改数据
        P_RT_STDHEADER_ID                   := SEQ_SA_STD_HEADER.NEXTVAL();
        V_TTA_SA_STD_HEADER.sa_std_header_id   := P_RT_STDHEADER_ID;
        V_TTA_SA_STD_HEADER.ALERT_BY      := P_USER_ID; --变更人
        V_TTA_SA_STD_HEADER.ALERT_DATE    := SYSDATE;

        V_TTA_SA_STD_HEADER.LAST_UPDATED_BY  := P_USER_ID;
        V_TTA_SA_STD_HEADER.LAST_UPDATE_DATE := SYSDATE;
        V_TTA_SA_STD_HEADER.STATUS           := 'A'; --制作中
        V_TTA_SA_STD_HEADER.APPROVE_DATE     := NULL; --设置审核时间为null
        V_TTA_SA_STD_HEADER.IS_CHANGE    := 'Y'; --是变更
        V_TTA_SA_STD_HEADER.SA_STD_VERSION := V_TTA_SA_STD_HEADER.SA_STD_VERSION + 1;
        V_TTA_SA_STD_HEADER.VERSION_STATUS := '1'; --生效

         --基于原始数据,插入新的数据
         INSERT INTO TTA_SA_STD_HEADER VALUES V_TTA_SA_STD_HEADER;

         --原始数据 版本状态失效
         UPDATE TTA_SA_STD_HEADER T
            SET T.VERSION_STATUS = '0'
          WHERE T.SA_STD_HEADER_ID = P_ORDER_ID;

          --插入匹配Proposal供应商表信息
          for proposalTemp in (
              SELECT TSSP.* FROM tta_sa_std_proposal_line TSSP where tssp.sa_std_header_id = P_ORDER_ID
                    ) loop
             begin
               proposalTemp.Sa_Std_Proposal_Line := SEQ_SA_STD_PROPOSAL_LINE.NEXTVAL();
               proposalTemp.Sa_Std_Header_Id  := P_RT_STDHEADER_ID;
               proposalTemp.Last_Updated_By := P_USER_ID;
               proposalTemp.Last_Update_Login := P_USER_ID;
               proposalTemp.Last_Update_Date :=sysdate;

               insert into tta_sa_std_proposal_line values proposalTemp;
               end;
           end loop;

           --插入补充协议拓展信息
           for fieldTemp in(
               select tssf.* from tta_sa_std_field_line tssf where tssf.sa_std_header_id = P_ORDER_ID
             ) loop
               begin
                 fieldTemp.Sa_Std_Field_Line_Id := SEQ_SA_STD_FIELD_LINE.NEXTVAL();
                 fieldTemp.Sa_Std_Header_Id := P_RT_STDHEADER_ID;
                 fieldTemp.Last_Updated_By  := P_USER_ID;
                 fieldTemp.Last_Update_Login := P_USER_ID;
                 fieldTemp.Last_Update_Date := sysdate;

                 insert into tta_sa_std_field_line values fieldTemp;
                 end;
             end loop;


             ---插入补充协议表格信息
             for saTabLine in(
                 select tstl.* from tta_sa_tab_line tstl where tstl.sa_std_header_id = P_ORDER_ID
               ) loop
                 begin
                        saTabLine.Sa_Tab_Line_Id := SEQ_SA_TAB_LINE.NEXTVAL();
                        saTabLine.Sa_Std_Header_Id := P_RT_STDHEADER_ID;
                        saTabLine.Last_Updated_By  := P_USER_ID;
                        saTabLine.Last_Update_Login := P_USER_ID;
                        saTabLine.Last_Update_Date := sysdate;

                        insert into tta_sa_tab_line values saTabLine;
                   end;
               end loop;

            -- commit;
     EXCEPTION
      WHEN OTHERS THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
       -- rollback;
     END;
 END P_TTA_SUPPLEMENT_AGREMENT_COPY;
-----------
---独家协议标准
create table TTA_SOLE_AGRT
(
  sole_agrt_h_id    NUMBER not null,
  sole_agrt_code    VARCHAR2(50),
  status            VARCHAR2(50),
  sole_agrt_version VARCHAR2(50),
  apply_date        DATE,
  start_date        DATE,
  end_date          DATE,
  version_num       NUMBER,
  creation_date     DATE default sysdate,
  created_by        NUMBER ,
  last_updated_by   NUMBER ,
  last_update_date  DATE default sysdate,
  last_update_login NUMBER,
  agrt_type         VARCHAR2(2)
);

alter table TTA_SOLE_AGRT add(VENDOR_CODE VARCHAR2(100));
alter table TTA_SOLE_AGRT add(VENDOR_NAME varchar2(500));

comment on column TTA_SOLE_AGRT.VENDOR_CODE is '供应商编号';
comment on column TTA_SOLE_AGRT.VENDOR_NAME is '供应商名称';

-----------------
--- TTA_SOLE_SUPPLIER
alter table TTA_SOLE_SUPPLIER add(proposal_version INTEGER);
alter table TTA_SOLE_SUPPLIER add(proposal_year INTEGER);
---------
----- tta_sole_agrt_info
alter table tta_sole_agrt_info add(sole_agrt_h_id number);
comment on column tta_sole_agrt_info.sole_agrt_h_id is 'tta_sole_agrt表的外键';
-----tta_sole_item----
alter table tta_sole_item add(sole_agrt_h_id number);
comment on column tta_sole_item.sole_agrt_h_id is 'tta_sole_agrt表的外键';
alter table tta_sole_item add(group_name varchar2(100));

alter table tta_sole_agrt add(approve_date date);
alter table tta_sole_agrt add(alert_by integer);
alter table tta_sole_agrt add(alert_date date);
alter table tta_sole_agrt add(is_change varchar2(10));
alter table tta_sole_agrt add(version_status varchar2(10));

comment on column tta_sole_agrt.is_change is '是否变更(Y:是,N:否)';
comment on column tta_sole_agrt.version_status is '版本状态(1生效,0失效)';
comment on column tta_sole_agrt.approve_date is '审批通过时间';
comment on column tta_sole_agrt.alert_by is '变更人';
comment on column tta_sole_agrt.alert_date is '变更时间';

--------------------------TTA SYSTEM 更新历史Proposal单据,与Health有关数据的脚本-------------------------
---更新Health部门信息相关
----第一步骤
create or replace procedure call_update_dept_proposal
    is
    id_v number;
     P_ISSUCCESS number;
     P_ERR_MESSAGE  VARCHAR2(200);
    begin
        for tempv in(
            select tpot.order_nbr from  tta_proposal_orderNbr_temp tpot left join
            tta_proposal_header tph on tpot.order_nbr = tph.order_nbr
            where tph.major_dept_code != '0F'

            ) loop
             pkg_excel_daoru.P_TTA_TERMS_UPDATE(tempv.order_nbr,P_ISSUCCESS,P_ERR_MESSAGE);
              dbms_output.put_line('输出日志:'|| P_ISSUCCESS ||':' || P_ERR_MESSAGE);
              dbms_output.put_line('*************************************************');
            if P_ISSUCCESS = 1 then
              commit;
           end if;

    end loop;
 end;

---- 第二步骤
PROCEDURE P_TTA_TERMS_UPDATE(PROPOSAL_ORDER_NO     VARCHAR2,
                                 P_ISSUCCESS    OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                 P_ERR_MESSAGE  OUT VARCHAR2)  IS

  P_CUR_PROPOSAL_ID NUMBER ;
  P_OLD_PROPOSAL_COUNT NUMBER ;
  P_MAJOR_DEPT_DESC    TTA_PROPOSAL_HEADER.MAJOR_DEPT_DESC%TYPE;
  P_MAJOR_DEPT_CODE    TTA_PROPOSAL_HEADER.MAJOR_DEPT_CODE%TYPE;
  P_PROPOSAL_YEAR      TTA_PROPOSAL_HEADER.PROPOSAL_YEAR%TYPE;
  P_TTA_CONTRACT_LINE_COUNT NUMBER ;
  P_TTA_CONTRACT_LINE_COUNT1 number ;
  P_CLAUSE_YEAR   number ;
  P_OLD_CLAUSE_YEAR  number ;
  BEGIN
    P_ISSUCCESS := 1;
    P_ERR_MESSAGE := '执行成功';
     SELECT
     TPH.PROPOSAL_ID,
     tph.major_dept_desc,
     tph.major_dept_code,
     tph.proposal_year
     INTO
      P_CUR_PROPOSAL_ID,
      P_MAJOR_DEPT_DESC,
      P_MAJOR_DEPT_CODE,
      P_PROPOSAL_YEAR
     FROM TTA_PROPOSAL_HEADER   TPH
     WHERE TPH.ORDER_NBR = PROPOSAL_ORDER_NO AND NVL(TPH.VERSION_STATUS,'1')  = '1' ;

     IF nvl(P_MAJOR_DEPT_DESC,'-1') = 'Health'  THEN
      P_ISSUCCESS := -1;
      P_ERR_MESSAGE := '执行失败当前单据，部门是 Health';
      RETURN ;
     END IF ;

/*     SELECT COUNT(1) INTO P_OLD_PROPOSAL_COUNT FROM TTA_PROPOSAL_HEADER TPH WHERE TPH.LAST_YEAR_PROPOSAL_ID = P_CUR_PROPOSAL_ID ;

     IF NVL(P_OLD_PROPOSAL_COUNT,0) > 0  THEN
      P_ISSUCCESS := -1;
      P_ERR_MESSAGE := '执行失败当前单据，做了下一年的单据';
      RETURN ;
      END IF ;*/
      ---  UPDATE   TTA_PROPOSAL_HEADER
      UPDATE   TTA_PROPOSAL_HEADER tph set  tph.major_dept_desc  = 'Health',tph.major_dept_code = '0F' where tph.proposal_id = P_CUR_PROPOSAL_ID;
       dbms_output.put_line('TTA_PROPOSAL_HEADER单号:'||PROPOSAL_ORDER_NO);
       dbms_output.put_line('TTA_PROPOSAL_HEADER更新条数：'||SQL%ROWCOUNT) ;

       delete  from TTA_CONTRACT_LINE_BK_200528 tclb where tclb.proposal_id =P_CUR_PROPOSAL_ID ;
       -- 更新合同表
       insert into TTA_CONTRACT_LINE_BK_200528  SELECT * FROM TTA_CONTRACT_LINE tcl where tcl.proposal_id = P_CUR_PROPOSAL_ID and tcl.contract_h_id is null ;

       update  TTA_CONTRACT_LINE tcl  SET  TCL.CLAUSE_ID = (
       SELECT tct.clause_id FROM tta_clause_tree tct,
                     TTA_CONTRACT_LINE_BK_200528 TCLB,
                     TTA_CLAUSE_TREE tctt
       where tct.team_framework_id in (SELECT ttfh.team_framework_id FROM tta_terms_frame_header ttfh
                                                                         where ttfh.year = TO_NUMBER(P_PROPOSAL_YEAR)
                                                                          and ttfh.dept_code = '0F')
            and TCLB.CONTRACT_L_ID = TCL.CONTRACT_L_ID
            and TCLB.CONTRACT_H_ID IS NULL
            and TCLB.PROPOSAL_ID = TCL.PROPOSAL_ID
            and TCLB.CLAUSE_ID = tctt.CLAUSE_ID
            and tctt.code = tct.code )

       where tcl.proposal_id = P_CUR_PROPOSAL_ID  and tcl.contract_h_id is null ;
       --dbms_output.put_line('TTA_CONTRACT_LINE更新条数：'||SQL%ROWCOUNT) ;
       SELECT count(1) into P_TTA_CONTRACT_LINE_COUNT FROM  TTA_CONTRACT_LINE tcl where  tcl.proposal_id = P_CUR_PROPOSAL_ID  and tcl.contract_h_id is null  and tcl.clause_id is null ;
      IF NVL(P_TTA_CONTRACT_LINE_COUNT,0) <> 0 THEN
       P_ISSUCCESS := -1;
      P_ERR_MESSAGE := '执行失败当前单据，更新后 CLAUSE_ID 为空';
      rollback;
      RETURN ;
      END IF ;

     SELECT count(1) into P_TTA_CONTRACT_LINE_COUNT1 FROM  TTA_CONTRACT_LINE tcl ,
     tta_clause_tree tct,
     tta_terms_frame_header ttfh
     where  tcl.proposal_id = P_CUR_PROPOSAL_ID
     and tcl.contract_h_id is null
     and tcl.clause_id = tct.clause_id
     and tct.team_framework_id = ttfh.team_framework_id
     and (ttfh.dept_code != '0F' or ttfh.year != TO_NUMBER(P_PROPOSAL_YEAR) );

       IF NVL(P_TTA_CONTRACT_LINE_COUNT1,0) <>0 THEN
       P_ISSUCCESS := -1;
      P_ERR_MESSAGE := '执行失败当前单据，更新后 CLAUSE_ID 不正常';
      RETURN ;
      END IF ;


      --  更新  tta_proposal_terms_line
      delete  from  tta_proposal_terms_line_bk_200528  tptll where tptll.proposal_id  = P_CUR_PROPOSAL_ID;
      insert into tta_proposal_terms_line_bk_200528 SELECT * FROM tta_proposal_terms_line tptl where tptl.proposal_id  =  P_CUR_PROPOSAL_ID ;

      --update   CLAUSE_ID
      SELECT ttfh.year into P_CLAUSE_YEAR FROM tta_proposal_terms_line ttpl,
                    tta_clause_tree tct,
                    tta_terms_frame_header ttfh
                    where ttpl.proposal_id  = P_CUR_PROPOSAL_ID
                    and ttpl.clause_id = tct.clause_id
                    and ttpl.clause_id is not null
                    and tct.team_framework_id = ttfh.team_framework_id
                    and rownum =1;

                          SELECT ttfh.year  into P_OLD_CLAUSE_YEAR FROM tta_proposal_terms_line ttpl,
                    tta_clause_tree tct,
                    tta_terms_frame_header ttfh
                    where ttpl.proposal_id  = P_CUR_PROPOSAL_ID
                    and ttpl.old_clause_id = tct.clause_id
                    and ttpl.old_clause_id is not null
                    and tct.team_framework_id = ttfh.team_framework_id
                    and rownum =1;

       IF NVL(P_CLAUSE_YEAR,0) = 0 THEN
      P_ISSUCCESS := -1;
      P_ERR_MESSAGE := '执行失败当前单据，P_CLAUSE_YEAR 空';
      RETURN ;

       END  IF ;

       IF NVL(P_OLD_CLAUSE_YEAR,0) = 0   THEN
               P_ISSUCCESS := -1;
      P_ERR_MESSAGE := '执行失败当前单据，P_OLD_CLAUSE_YEAR 空';
      RETURN ;
       END IF ;

 --update   CLAUSE_ID
       update  tta_proposal_terms_line tptl  SET  tptl.CLAUSE_ID = (
      SELECT tct.clause_id FROM tta_proposal_terms_line_bk_200528 ttlb,
                    tta_clause_tree tctt,
                    tta_clause_tree tct
                    where ttlb.terms_l_id = tptl.terms_l_id
                    and ttlb.clause_id = tctt.clause_id
                    and tctt.code =  tct.code
                    and tct.team_framework_id in (SELECT ttfh.team_framework_id FROM tta_terms_frame_header ttfh
                                                                         where ttfh.year = TO_NUMBER(P_CLAUSE_YEAR)
                                                                          and ttfh.dept_code = '0F'))

       where tptl.proposal_id = P_CUR_PROPOSAL_ID  and tptl.clause_id is not null ;
       --dbms_output.put_line('tta_proposal_terms_line CLAUSE_ID更新条数：'||SQL%ROWCOUNT) ;
        --update   CLAUSE_TREE_ID
              update  tta_proposal_terms_line tptl  SET  tptl.clause_tree_id = (
      SELECT tct.clause_id FROM tta_proposal_terms_line_bk_200528 ttlb,
                    tta_clause_tree tctt,
                    tta_clause_tree tct
                    where ttlb.terms_l_id = tptl.terms_l_id
                    and ttlb.Clause_Tree_Id = tctt.clause_id
                    and tctt.code =  tct.code
                    and tct.team_framework_id in (SELECT ttfh.team_framework_id FROM tta_terms_frame_header ttfh
                                                                         where ttfh.year = TO_NUMBER(P_CLAUSE_YEAR)
                                                                          and ttfh.dept_code = '0F'))

       where tptl.proposal_id = P_CUR_PROPOSAL_ID  and tptl.Clause_Tree_Id is not null ;
       --dbms_output.put_line('tta_proposal_terms_line CLAUSE_TREE_ID更新条数：'||SQL%ROWCOUNT) ;
              --update   OLD_CLAUSE_ID
              update  tta_proposal_terms_line tptl  SET  tptl.old_clause_id = (
      SELECT tct.clause_id FROM tta_proposal_terms_line_bk_200528 ttlb,
                    tta_clause_tree tctt,
                    tta_clause_tree tct
                    where ttlb.terms_l_id = tptl.terms_l_id
                    and ttlb.Old_Clause_Id = tctt.clause_id
                    and tctt.code =  tct.code
                    and tct.team_framework_id in (SELECT ttfh.team_framework_id FROM tta_terms_frame_header ttfh
                                                                         where ttfh.year = TO_NUMBER(P_OLD_CLAUSE_YEAR)
                                                                          and ttfh.dept_code = '0F'))

       where tptl.proposal_id = P_CUR_PROPOSAL_ID  and tptl.Old_Clause_Id is not null ;
       --dbms_output.put_line('tta_proposal_terms_line OLD_CLAUSE_ID更新条数：'||SQL%ROWCOUNT) ;
               --update   OLD_CLAUSE_TREE_ID
      update  tta_proposal_terms_line tptl  SET  tptl.old_clause_tree_id2 = (
      SELECT tct.clause_id FROM tta_proposal_terms_line_bk_200528 ttlb,
                    tta_clause_tree tctt,
                    tta_clause_tree tct
                    where ttlb.terms_l_id = tptl.terms_l_id
                    and ttlb.Old_Clause_Tree_Id2 = tctt.clause_id
                    and tctt.code =  tct.code
                    and tct.team_framework_id in (SELECT ttfh.team_framework_id FROM tta_terms_frame_header ttfh
                                                                         where ttfh.year = TO_NUMBER(P_OLD_CLAUSE_YEAR)
                                                                          and ttfh.dept_code = '0F'))

       where tptl.proposal_id = P_CUR_PROPOSAL_ID  and tptl.Old_Clause_Tree_Id2 is not null ;
        --dbms_output.put_line('tta_proposal_terms_line OLD_CLAUSE_TREE_ID更新条数：'||SQL%ROWCOUNT) ;
       --update   unit_id
             update  tta_proposal_terms_line tptl  SET  tptl.unit_id = (
      SELECT tctll.collect_type_id FROM tta_proposal_terms_line_bk_200528 ttlb,
                    tta_clause_tree tctt,
                    tta_collect_type_line tctl,
                    tta_clause_tree tct,
                    tta_collect_type_line tctll
                    where ttlb.terms_l_id = tptl.terms_l_id
                    and ttlb.unit_id = tctl.collect_type_id
                    and tctl.clause_id = tctt.clause_id
                    and tctt.code =  tct.code
                    and tct.team_framework_id in (SELECT ttfh.team_framework_id FROM tta_terms_frame_header ttfh
                                                                         where ttfh.year = TO_NUMBER(P_CLAUSE_YEAR)
                                                                          and ttfh.dept_code = '0F')
                    and tct.clause_id = tctll.clause_id
                    and tctll.unit_value = tctl.unit_value)

       where tptl.proposal_id = P_CUR_PROPOSAL_ID  and tptl.unit_id is not null ;
        --dbms_output.put_line('tta_proposal_terms_line unit_id更新条数：'||SQL%ROWCOUNT) ;
       --update   parent_unit_id

                    update  tta_proposal_terms_line tptl  SET  tptl.parent_unit_id = (
      SELECT tctll.parent_id FROM tta_proposal_terms_line_bk_200528 ttlb,
                    tta_clause_tree tctt,
                    tta_collect_type_line tctl,
                    tta_clause_tree tct,
                    tta_collect_type_line tctll
                    where ttlb.terms_l_id = tptl.terms_l_id
                    and ttlb.unit_id = tctl.collect_type_id
                    and tctl.clause_id = tctt.clause_id
                    and tctt.code =  tct.code
                    and tct.team_framework_id in (SELECT ttfh.team_framework_id FROM tta_terms_frame_header ttfh
                                                                         where ttfh.year = TO_NUMBER(P_CLAUSE_YEAR)
                                                                          and ttfh.dept_code = '0F')
                    and tct.clause_id = tctll.clause_id
                    and tctll.unit_value = tctl.unit_value)

       where tptl.proposal_id = P_CUR_PROPOSAL_ID  and tptl.unit_id is not null ;
        --dbms_output.put_line('tta_proposal_terms_line parent_unit_id更新条数：'||SQL%ROWCOUNT) ;
    EXCEPTION
      WHEN OTHERS THEN
        ROLLBACK ;
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
        dbms_output.put_line(P_ERR_MESSAGE) ;

  END P_TTA_TERMS_UPDATE;
---------------------------------------------
----TTA_SA_STD_HEADER表增加GM审批状态字段
alter table TTA_SA_STD_HEADER add(is_skip_approve varchar2(2) default 'Y');
comment on column TTA_SA_STD_HEADER.Is_Skip_Approve is '是否需要gm审批,y是需要gm审批,n不需要gm审批';
-----TTA_SOLE_AGRT表增加GM审批状态字段
alter table TTA_SOLE_AGRT add(is_skip_approve varchar2(2) default 'Y');
comment on column TTA_SOLE_AGRT.Is_Skip_Approve is '是否需要gm审批,y是需要gm审批,n不需要gm审批';

------
---独家协议标准修改脚本:
 alter table tta_sole_item add(VENDOR_NBR varchar2(30));
 alter table tta_sole_item add(VENDOR_NAME varchar2(150));
 alter table tta_sole_item add(DEPT_CODE number(20));
 alter table tta_sole_item add(DEPT_DESC varchar2(100));
 alter table tta_sole_item add(brand_en varchar2(500));
 alter table tta_sole_item rename column BRAND to BRAND_CN;

---2020.7.13添加
alter table TTA_SOLE_AGRT_INFO add(brandpln_l_id number);
alter table TTA_SOLE_AGRT_INFO add(proposal_id NUMBER);
alter table TTA_SOLE_AGRT_INFO add(group_code VARCHAR2(30));
alter table TTA_SOLE_AGRT_INFO add(group_desc VARCHAR2(230));
alter table TTA_SOLE_AGRT_INFO add(dept_code VARCHAR2(30));
alter table TTA_SOLE_AGRT_INFO add(dept_desc VARCHAR2(230));
alter table TTA_SOLE_AGRT_INFO add(brand_cn VARCHAR2(230));
alter table TTA_SOLE_AGRT_INFO add(brand_en VARCHAR2(230));
alter table TTA_SOLE_AGRT_INFO add(org_name varchar2(230));

comment on column TTA_SOLE_AGRT_INFO.brandpln_l_id is '品牌计划行数据的行id';
comment on column TTA_SOLE_AGRT_INFO.proposal_id is 'Proposal单据的id';
comment on column TTA_SOLE_AGRT_INFO.group_code is '品牌计划行数据的group_code';
comment on column TTA_SOLE_AGRT_INFO.group_desc is '品牌计划行数据的group_desc';
comment on column TTA_SOLE_AGRT_INFO.dept_code is '品牌计划行数据的dept_code';
comment on column TTA_SOLE_AGRT_INFO.dept_desc is '品牌计划行数据的dept_desc';
comment on column TTA_SOLE_AGRT_INFO.brand_cn is '品牌计划行数据的brand_cn';
comment on column TTA_SOLE_AGRT_INFO.brand_en is '品牌计划行数据的brand_en';

-------备份正式库查找供应商的数据的脚本(例如:主从供应商的查找)
create or replace view tta_supplier_major_v as
select "SUPPLIER_CODE","SUPPLIER_NAME","MAJOR_SUPPLIER_CODE"
  from (select b.rel_supplier_code  supplier_code,
               b.rel_supplier_name  supplier_name,
               a.supplier_code      major_supplier_code  -- 1.从供应商
          from tta_supplier a
         inner join tta_rel_supplier b
            on a.supplier_id = b.rel_id
        -- where a.supplier_code =:venderCode -- 1.从供应商
        union
        select a.supplier_code,
               a.supplier_name,
               a.formal_code major_supplier_code
          from tta_supplier a
         where a.is_latent = 'Y'
          -- and a.formal_code =:venderCode  --2.查找潜在供应商
        union
        -- 3.正式供应去 关联潜在供应商的从供应商
        select b.rel_supplier_code  supplier_code,
               b.rel_supplier_name  supplier_name,
               a.formal_code major_supplier_code
          from tta_supplier a
         inner join tta_rel_supplier b
            on a.supplier_id = b.rel_id
         where a.is_latent = 'Y'
          -- and a.formal_code =:venderCode

           union

      select t.supplier_code,
             t.supplier_name,
             t.supplier_code  major_supplier_code
               from tta_supplier t
     -- 4.自身供应商
   --  where t.supplier_code=:venderCode
         union
      select
             t.formal_code supplier_code,
             t.formal_name supplier_name,
             t.supplier_code  major_supplier_code
      from tta_supplier t where nvl(t.formal_code,-1) != -1

) t
;
----------------2020.8.13 OI分摊场景导出---------------------------------
create table TTA_SCENE_FIELD_HEADER
(
  scene_field_id INTEGER,
  field_name VARCHAR2(50),
  is_enable  VARCHAR2(2),
  order_no   NUMBER,
  is_calc_field VARCHAR2(2),
  created_by        INTEGER,
  last_updated_by   INTEGER,
  last_update_date  DATE,
  creation_date     DATE,
  last_update_login INTEGER,
  version_num       INTEGER
);

comment on column  TTA_SCENE_FIELD_HEADER.field_name is '字段名';
comment on column  TTA_SCENE_FIELD_HEADER.is_enable is '是否启用';
comment on column  TTA_SCENE_FIELD_HEADER.order_no is '排序号';
comment on column  TTA_SCENE_FIELD_HEADER.is_calc_field is '是否加入计算';

create sequence SEQ_SCENE_FIELD_HEADER
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
nocycle
nocache;

---备份独家协议,补充协议匹配Proposal信息的视图  -- 不使用
create or replace view tta_contract_split_proposal_v as
select "PROPOSAL_ORDER","VENDOR_CODE","VENDOR_NAME","PROPOSAL_VERSION","PROPOSAL_YEAR","BRAND_CN","BRAND_EN","PROPOSAL_ID","CONTRACT_H_ID","SALE_TYPE","SALETYPENAME" from (select
       max(tch.proposal_code) PROPOSAL_ORDER,
       nvl(tcl.vendor_code,tch.vendor_nbr) VENDOR_CODE,
       nvl(tcl.vendor_name,tch.vendor_name) VENDOR_NAME,
       max(tch.proposal_version) PROPOSAL_VERSION,
       max(tch.proposal_year) PROPOSAL_YEAR,
       max(tpth.brand_cn) brand_cn,
       max(tpth.brand_en) brand_en,
       nvl(tcl.proposal_id,tph.proposal_id) proposal_id,
       tch.contract_h_id,
       max(tph.SALE_TYPE) SALE_TYPE,
       max(lookup1.meaning) saleTypeName
  from tta_contract_header tch
  inner join tta_proposal_header_new_version_v tph --取最新版本的Proposal
    on tch.proposal_code = tph.order_nbr
   and tch.proposal_version = tph.version_code
  left join tta_contract_line tcl
    on tch.contract_h_id = tcl.contract_h_id
  left join tta_proposal_terms_header tpth
    on  tpth.proposal_id = tph.proposal_id
  left join
  (select lookup_type,lookup_code,meaning
        from base_lookup_values where lookup_type='SALE_WAY' and enabled_flag='Y'
      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'
      ) lookup1  on tph.SALE_TYPE = lookup1.lookup_code
    where tch.bill_status = 'C'
 group by nvl(tcl.vendor_code,tch.vendor_nbr),
          nvl(tcl.vendor_name,tch.vendor_name),
          nvl(tcl.proposal_id,tph.proposal_id),
          tch.contract_h_id) t where 1=1
------------------------------------------------------------------------



-- #######################hmb end#######################--
------------------------------------------------------------------------------


-- #######################xga start#######################--




-- #######################xga end########################--
------------------------------------------------------------------------------


-- #######################lx start#######################--




-- #######################lx end#######################--
