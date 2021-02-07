CREATE OR REPLACE PACKAGE "PKG_TTA_FIT" IS


   --并更或复制    
  PROCEDURE P_TTA_FEE_DEPT_COPY(P_ORDER_ID    NUMBER,
                                P_USER_ID     NUMBER,
                                P_COPYORCHANGE VARCHAR2,
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                                P_FEEDEPT_ID  OUT VARCHAR2);
                                
  --变更生效                              
  PROCEDURE P_TTA_FEE_DEPT_EFFECT(P_ORDER_ID    NUMBER,
                                P_USER_ID     NUMBER,
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                );                              
                                

END PKG_TTA_FIT;
/

CREATE OR REPLACE PACKAGE "PKG_TTA_PROPOSAL" IS


     --并更或复制
  PROCEDURE P_TTA_PROPOSAL_COPY(P_ORDER_ID    NUMBER,
                                P_USER_ID     NUMBER,
                                P_COPYORCHANGE VARCHAR2,
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                                P_PROPOSAL_ID  OUT VARCHAR2);

      --恢复上一版本
 PROCEDURE P_TTA_PROPOSAL_CUT(P_ORDER_ID    NUMBER,
                                P_USER_ID     NUMBER,
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                                P_PROPOSAL_ID  OUT VARCHAR2);



       --品牌计划行表保存后回调
 PROCEDURE P_TTA_BRANDPLNL_SAVE(P_ORDER_ID    NUMBER,
                                P_USER_ID     NUMBER,
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                );


              --品牌计划生成
 PROCEDURE P_TTA_BRANDPLN_GEN(P_ORDER_ID    NUMBER,
                                P_USER_ID     NUMBER,
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                );



        --品牌计划计算
 PROCEDURE P_TTA_BRANDPLNL_COUNT(P_ORDER_ID    NUMBER,
                                P_USER_ID     NUMBER,
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                );


             --部门协定标准生成
 PROCEDURE P_TTA_DEPTFEE_GEN(P_ORDER_ID    NUMBER,
                                P_USER_ID     NUMBER,
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                );



           --部门协定标准生成
 PROCEDURE P_TTA_DEPTFEE_GENERATE(P_ORDER_ID    NUMBER,
                                P_USER_ID     NUMBER,
                                P_accord_type  VARCHAR2,
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                );




              --合同信息生成
 PROCEDURE P_TTA_CONTRACT_GEN(P_ORDER_ID    NUMBER,
                                P_USER_ID     NUMBER,
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                );







  PROCEDURE P_TTA_PROPOSAL_SPLIT(P_ORDER_ID    NUMBER,
                                 P_USER_ID     NUMBER,
                                 P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                 P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                 );
  --条款框架变更
  PROCEDURE P_TTA_CLAUSE_COPY  (P_ORDER_ID    NUMBER,
                                 P_USER_ID     NUMBER,
                                 P_ISSUCCESS   OUT NUMBER, --返回状态 -1：处理失败，1：成功；0：警告
                                 P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                                 P_ID OUT  NUMBER,
                                 P_YEAR2 OUT VARCHAR2
                                 );

    --条款框架复制
  PROCEDURE P_TTA_CLAUSE_COPY2  (P_ORDER_ID_OLD    NUMBER,
                                 P_DEPT_CODE   VARCHAR2,
                                 P_USER_ID     NUMBER,
                                 P_ISSUCCESS   OUT NUMBER, --返回状态 -1：处理失败，1：成功；0：警告
                                 P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                                 P_ID OUT  NUMBER,
                                 P_YEAR2 OUT VARCHAR2
                                 );

      --条款名目回写
  PROCEDURE P_TTA_CLAUSE_RETURN  (P_ORDER_ID    NUMBER,
                                 P_USER_ID     NUMBER,
                                 P_ISSUCCESS   OUT NUMBER, --返回状态 -1：处理失败，1：成功；0：警告
                                 P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                                 P_ID OUT  NUMBER
                                 );

END PKG_TTA_PROPOSAL;

/


CREATE OR REPLACE PACKAGE BODY PKG_TTA_FIT IS

  PROCEDURE P_TTA_FEE_DEPT_COPY(P_ORDER_ID     NUMBER,
                                P_USER_ID      NUMBER,
                                P_COPYORCHANGE VARCHAR2,
                                P_ISSUCCESS    OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE  OUT VARCHAR2, --返回信息
                                P_FEEDEPT_ID   OUT VARCHAR2) IS
    V_COUNT           NUMBER; --计数器
    V_FEEDEPT_LINE_ID NUMBER;
    V_TTA_FEE_DEPT_H  TTA_FEE_DEPT_HEADER%ROWTYPE;
    
    V_VERSION_INT    number;
  
  BEGIN
  
    P_ISSUCCESS := 1;
  
    P_ERR_MESSAGE := '执行成功';
    --  UPDATE TTA_FEE_DEPT_H T SET T.APPROVE_DATE = SYSDATE
    --  WHERE T.FEEDEPT_ID = P_ORDER_ID;
  
    BEGIN
      SELECT COUNT(1)
        INTO V_COUNT
        FROM TTA_FEE_DEPT_HEADER TT
       WHERE TT.FEEDEPT_ID = P_ORDER_ID
         AND TT.STATUS = 'C';
      IF V_COUNT = 0 THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']状态为审批通过的单据';
        RETURN;
      END IF;
      --复制数据
      SELECT TT.*
        INTO V_TTA_FEE_DEPT_H
        FROM TTA_FEE_DEPT_HEADER TT
       WHERE TT.FEEDEPT_ID = P_ORDER_ID
         AND TT.STATUS = 'C';
      P_FEEDEPT_ID                      := SEQ_TTA_FEE_DEPT_HEADER.NEXTVAL();
      V_TTA_FEE_DEPT_H.FEEDEPT_ID       := P_FEEDEPT_ID;
      V_TTA_FEE_DEPT_H.CREATION_DATE    := SYSDATE;
      V_TTA_FEE_DEPT_H.LAST_UPDATED_BY  := P_USER_ID;
      V_TTA_FEE_DEPT_H.LAST_UPDATED_BY  := P_USER_ID;
      V_TTA_FEE_DEPT_H.LAST_UPDATE_DATE := SYSDATE;
      V_TTA_FEE_DEPT_H.SOURCE_FEE_ID    := P_ORDER_ID;
      V_TTA_FEE_DEPT_H.STATUS           := 'A';
      V_TTA_FEE_DEPT_H.APPROVE_DATE     := NULL;
    
      IF P_COPYORCHANGE = 'COPY' THEN
        V_TTA_FEE_DEPT_H.YEAR_CODE := TO_CHAR(SYSDATE, 'YYYY');
        V_TTA_FEE_DEPT_H.IS_ALERT  := 'N';
        V_TTA_FEE_DEPT_H.Version_Code  := 001;
      ELSIF P_COPYORCHANGE = 'CHANGE' THEN
        V_TTA_FEE_DEPT_H.IS_ALERT := 'Y';
        V_VERSION_INT  :=  to_number(V_TTA_FEE_DEPT_H.Version_Code)+1;
        V_TTA_FEE_DEPT_H.Version_Code :=  to_char(V_VERSION_INT,'000');
       

        UPDATE TTA_FEE_DEPT_HEADER TT SET TT.STATUS ='E'
       WHERE TT.FEEDEPT_ID = P_ORDER_ID;
        
      END IF;
    
      INSERT INTO TTA_FEE_DEPT_HEADER VALUES V_TTA_FEE_DEPT_H;
    
      FOR TEMPL IN (SELECT *
                      FROM TTA_FEE_DEPT_LINE T
                     WHERE T.FEEDEPT_ID = P_ORDER_ID) LOOP
        NULL;
        SELECT SEQ_TTA_FEE_DEPT_LINE.NEXTVAL INTO V_FEEDEPT_LINE_ID FROM DUAL;
      
        INSERT INTO TTA_FEE_DEPT_LINE
          (FEEDEPT_LINE_ID,
           LINE_CODE,
           PARENT_LINE_ID,
           SORT_ID,
           ITEM_NBR,
           CREATION_DATE,
           CREATED_BY,
           LAST_UPDATED_BY,
           LAST_UPDATE_DATE,
           LAST_UPDATE_LOGIN,
           VERSION_NUM,
           IF_EFFECT,
           REMARK,
           FEEDEPT_ID,
           SOURCE_LINE_ID,
           ITEM_DESC_CN,
           ITEM_DESC_EN,
           UNIT,
           STANDARD_VALUE
           )
        VALUES
          (V_FEEDEPT_LINE_ID,
           TEMPL.LINE_CODE,
           TEMPL.PARENT_LINE_ID,
           TEMPL.SORT_ID,
           TEMPL.ITEM_NBR,
           SYSDATE,
           P_USER_ID,
           P_USER_ID,
           SYSDATE,
           TEMPL.LAST_UPDATE_LOGIN,
           TEMPL.VERSION_NUM,
           TEMPL.IF_EFFECT,
           TEMPL.REMARK,
           P_FEEDEPT_ID,
           TEMPL.FEEDEPT_LINE_ID,
           TEMPL.ITEM_DESC_CN,
           TEMPL.ITEM_DESC_EN,
           TEMPL.UNIT,
           TEMPL.STANDARD_VALUE);
        FOR TEMPD IN (SELECT *
                        FROM TTA_FEE_DEPT_DETAIL T
                       WHERE T.FEEDEPT_LINE_ID = TEMPL.FEEDEPT_LINE_ID) LOOP
          INSERT INTO TTA_FEE_DEPT_DETAIL
            (FEEDEPT_DETAIL_ID,
             FEEDEPT_ID,
             FEEDEPT_LINE_ID,
             DEPT_CODE,
             DEPT_DESC,
             CREATION_DATE,
             CREATED_BY,
             LAST_UPDATED_BY,
             LAST_UPDATE_DATE,
             LAST_UPDATE_LOGIN,
             VERSION_NUM,
             UNIT,
             STANDARD_VALUE,
             REMARK)
          VALUES
            (SEQ_TTA_FEE_DEPT_DETAIL.NEXTVAL,
             P_FEEDEPT_ID,
             V_FEEDEPT_LINE_ID,
             TEMPD.DEPT_CODE,
             TEMPD.DEPT_DESC,
             SYSDATE,
             P_USER_ID,
             P_USER_ID,
             SYSDATE,
             TEMPD.LAST_UPDATE_LOGIN,
             TEMPD.VERSION_NUM,
             TEMPD.UNIT,
             TEMPD.STANDARD_VALUE,
             TEMPD.REMARK);
        
        END LOOP;
      
      END LOOP;
    
      UPDATE TTA_FEE_DEPT_LINE T
         SET T.PARENT_LINE_ID = NVL((SELECT T3.FEEDEPT_LINE_ID
                                      FROM TTA_FEE_DEPT_LINE T1, --本条
                                           TTA_FEE_DEPT_LINE T2, --源记录
                                           TTA_FEE_DEPT_LINE T3 --本条上级
                                     WHERE T1.FEEDEPT_LINE_ID =
                                           T.FEEDEPT_LINE_ID
                                       AND T1.SOURCE_LINE_ID =
                                           T2.FEEDEPT_LINE_ID
                                       AND T2.FEEDEPT_ID = P_ORDER_ID
                                       AND T2.PARENT_LINE_ID =
                                           T3.SOURCE_LINE_ID
                                       AND T3.FEEDEPT_ID = P_FEEDEPT_ID),
                                    0)
       WHERE T.FEEDEPT_ID = P_FEEDEPT_ID;
    
      --SAVEPOINT SP_FREEZE_MATERIAL_ONHAND;
      NULL;
    
    EXCEPTION
      WHEN OTHERS THEN
        --  ROLLBACK TO SP_FREEZE_MATERIAL_ONHAND;
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;
  
  END P_TTA_FEE_DEPT_COPY;

  PROCEDURE P_TTA_FEE_DEPT_EFFECT(P_ORDER_ID    NUMBER,
                                  P_USER_ID     NUMBER,
                                  P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                  P_ERR_MESSAGE OUT VARCHAR2) --返回信息)
   IS
    V_COUNT           NUMBER; --计数器
    V_FEEDEPT_LINE_ID NUMBER;
    V_FEEDEPT_ID      NUMBER;
    V_TTA_FEE_DEPT_H  TTA_FEE_DEPT_HEADER%ROWTYPE;
  
  BEGIN
  
    P_ISSUCCESS := 1;
  
    P_ERR_MESSAGE := '执行成功';
    --  UPDATE TTA_FEE_DEPT_H T SET T.APPROVE_DATE = SYSDATE
    --  WHERE T.FEEDEPT_ID = P_ORDER_ID;
  
    BEGIN
      SELECT COUNT(1)
        INTO V_COUNT
        FROM TTA_FEE_DEPT_HEADER TT
       WHERE TT.FEEDEPT_ID = P_ORDER_ID
         AND TT.STATUS = 'C';
      IF V_COUNT = 0 THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']状态为审批通过的单据';
        RETURN;
      END IF;
    
      --复制数据
      SELECT TT.*
        INTO V_TTA_FEE_DEPT_H
        FROM TTA_FEE_DEPT_HEADER TT
       WHERE TT.FEEDEPT_ID = P_ORDER_ID
         AND TT.STATUS = 'C';
    
      IF V_TTA_FEE_DEPT_H.IS_ALERT <> 'Y' OR
         V_TTA_FEE_DEPT_H.SOURCE_FEE_ID IS NULL THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '不为变更单据，无法生效';
        RETURN;
      END IF;
      V_FEEDEPT_ID := V_TTA_FEE_DEPT_H.SOURCE_FEE_ID;
    
      SELECT COUNT(1)
        INTO V_COUNT
        FROM TTA_FEE_DEPT_HEADER TT
       WHERE TT.FEEDEPT_ID = V_FEEDEPT_ID
         AND TT.STATUS = 'C';
      IF V_COUNT = 0 THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '没有找到ID为[' || V_FEEDEPT_ID || ']状态为审批通过的单据';
        RETURN;
      END IF;
    
      UPDATE TTA_FEE_DEPT_HEADER T
         SET T.VERSION_CODE = V_TTA_FEE_DEPT_H.VERSION_CODE
       WHERE T.FEEDEPT_ID = V_TTA_FEE_DEPT_H.SOURCE_FEE_ID;
    
      DELETE FROM TTA_FEE_DEPT_LINE T
       WHERE T.FEEDEPT_ID = V_TTA_FEE_DEPT_H.SOURCE_FEE_ID;
      DELETE FROM TTA_FEE_DEPT_DETAIL T
       WHERE T.FEEDEPT_ID = V_TTA_FEE_DEPT_H.SOURCE_FEE_ID;
    
      FOR TEMPL IN (SELECT *
                      FROM TTA_FEE_DEPT_LINE T
                     WHERE T.FEEDEPT_ID = P_ORDER_ID) LOOP
        NULL;
        SELECT SEQ_TTA_FEE_DEPT_LINE.NEXTVAL INTO V_FEEDEPT_LINE_ID FROM DUAL;
      
        INSERT INTO TTA_FEE_DEPT_LINE
          (FEEDEPT_LINE_ID,
           LINE_CODE,
           PARENT_LINE_ID,
           SORT_ID,
           ITEM_NBR,
           CREATION_DATE,
           CREATED_BY,
           LAST_UPDATED_BY,
           LAST_UPDATE_DATE,
           LAST_UPDATE_LOGIN,
           VERSION_NUM,
           IF_EFFECT,
           REMARK,
           FEEDEPT_ID,
           SOURCE_LINE_ID,
           ITEM_DESC_CN,
           ITEM_DESC_EN,
           UNIT,
           STANDARD_VALUE)
        VALUES
          (V_FEEDEPT_LINE_ID,
           TEMPL.LINE_CODE,
           TEMPL.PARENT_LINE_ID,
           TEMPL.SORT_ID,
           TEMPL.ITEM_NBR,
           SYSDATE,
           P_USER_ID,
           P_USER_ID,
           SYSDATE,
           TEMPL.LAST_UPDATE_LOGIN,
           TEMPL.VERSION_NUM,
           TEMPL.IF_EFFECT,
           TEMPL.REMARK,
           V_FEEDEPT_ID,
           TEMPL.FEEDEPT_LINE_ID,
           TEMPL.ITEM_DESC_CN,
           TEMPL.ITEM_DESC_EN,
           TEMPL.UNIT,
           TEMPL.STANDARD_VALUE);
        FOR TEMPD IN (SELECT *
                        FROM TTA_FEE_DEPT_DETAIL T
                       WHERE T.FEEDEPT_LINE_ID = TEMPL.FEEDEPT_LINE_ID) LOOP
          INSERT INTO TTA_FEE_DEPT_DETAIL
            (FEEDEPT_DETAIL_ID,
             FEEDEPT_ID,
             FEEDEPT_LINE_ID,
             DEPT_CODE,
             DEPT_DESC,
             CREATION_DATE,
             CREATED_BY,
             LAST_UPDATED_BY,
             LAST_UPDATE_DATE,
             LAST_UPDATE_LOGIN,
             VERSION_NUM,
             UNIT,
             STANDARD_VALUE,
             REMARK)
          VALUES
            (SEQ_TTA_FEE_DEPT_DETAIL.NEXTVAL,
             V_FEEDEPT_ID,
             V_FEEDEPT_LINE_ID,
             TEMPD.DEPT_CODE,
             TEMPD.DEPT_DESC,
             SYSDATE,
             P_USER_ID,
             P_USER_ID,
             SYSDATE,
             TEMPD.LAST_UPDATE_LOGIN,
             TEMPD.VERSION_NUM,
             TEMPD.UNIT,
             TEMPD.STANDARD_VALUE,
             TEMPD.REMARK);
        
        END LOOP;
      
      END LOOP;
    
      UPDATE TTA_FEE_DEPT_LINE T
         SET T.PARENT_LINE_ID = NVL((SELECT T3.FEEDEPT_LINE_ID
                                      FROM TTA_FEE_DEPT_LINE T1, --本条
                                           TTA_FEE_DEPT_LINE T2, --源记录
                                           TTA_FEE_DEPT_LINE T3 --本条上级
                                     WHERE T1.FEEDEPT_LINE_ID =
                                           T.FEEDEPT_LINE_ID
                                       AND T1.SOURCE_LINE_ID =
                                           T2.FEEDEPT_LINE_ID
                                       AND T2.FEEDEPT_ID = P_ORDER_ID
                                       AND T2.PARENT_LINE_ID =
                                           T3.SOURCE_LINE_ID
                                       AND T3.FEEDEPT_ID = V_FEEDEPT_ID),
                                    0)
       WHERE T.FEEDEPT_ID = V_FEEDEPT_ID;
    
      --SAVEPOINT SP_FREEZE_MATERIAL_ONHAND;
      NULL;
    
    EXCEPTION
      WHEN OTHERS THEN
        --  ROLLBACK TO SP_FREEZE_MATERIAL_ONHAND;
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;
  
  END P_TTA_FEE_DEPT_EFFECT;

END PKG_TTA_FIT;
/


CREATE OR REPLACE PACKAGE BODY PKG_TTA_PROPOSAL IS

  PROCEDURE P_TTA_PROPOSAL_COPY(P_ORDER_ID     NUMBER,
                                P_USER_ID      NUMBER,
                                P_COPYORCHANGE VARCHAR2,
                                P_ISSUCCESS    OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE  OUT VARCHAR2, --返回信息
                                P_PROPOSAL_ID  OUT VARCHAR2) IS
    V_COUNT               NUMBER; --计数器
    V_TTA_PROPOSAL_HEADER TTA_PROPOSAL_HEADER%ROWTYPE;
  
  BEGIN
  
    P_ISSUCCESS := 1;
  
    P_ERR_MESSAGE := '执行成功';
  
    BEGIN
      SELECT COUNT(1)
        INTO V_COUNT
        FROM TTA_PROPOSAL_HEADER TT
       WHERE TT.PROPOSAL_ID = P_ORDER_ID
         AND TT.STATUS = 'C';
      IF V_COUNT = 0 THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']状态为审批通过的单据';
        RETURN;
      END IF;
      --复制数据
      SELECT TT.*
        INTO V_TTA_PROPOSAL_HEADER
        FROM TTA_PROPOSAL_HEADER TT
       WHERE TT.PROPOSAL_ID = P_ORDER_ID
         AND TT.STATUS = 'C';
      P_PROPOSAL_ID                       := SEQ_TTA_PROPOSAL_HEADER.NEXTVAL();
      V_TTA_PROPOSAL_HEADER.PROPOSAL_ID   := P_PROPOSAL_ID;
      V_TTA_PROPOSAL_HEADER.CREATION_DATE := SYSDATE;
      V_TTA_PROPOSAL_HEADER.ALERT_BY      := P_USER_ID;
      V_TTA_PROPOSAL_HEADER.ALERT_DATE    := SYSDATE;
    
      V_TTA_PROPOSAL_HEADER.LAST_UPDATED_BY  := P_USER_ID;
      V_TTA_PROPOSAL_HEADER.LAST_UPDATE_DATE := SYSDATE;
      V_TTA_PROPOSAL_HEADER.SOURCE_ID        := P_ORDER_ID;
      V_TTA_PROPOSAL_HEADER.SOURCE_CODE      := V_TTA_PROPOSAL_HEADER.ORDER_NBR;
      V_TTA_PROPOSAL_HEADER.STATUS           := 'A';
      V_TTA_PROPOSAL_HEADER.APPROVE_DATE     := NULL;
      V_TTA_PROPOSAL_HEADER.REMARK    := P_COPYORCHANGE;
    
      V_TTA_PROPOSAL_HEADER.IS_CHANGE    := 'Y';
      V_TTA_PROPOSAL_HEADER.VERSION_CODE := V_TTA_PROPOSAL_HEADER.VERSION_CODE + 1;
    
      INSERT INTO TTA_PROPOSAL_HEADER VALUES V_TTA_PROPOSAL_HEADER;
    
      UPDATE TTA_PROPOSAL_HEADER T
         SET T.STATUS = 'E'
       WHERE T.PROPOSAL_ID = P_ORDER_ID;
       
    
    
      UPDATE TTA_DEPT_FEE T SET T.PROPOSAL_ID = P_PROPOSAL_ID   WHERE T.PROPOSAL_ID = P_ORDER_ID;
      
      UPDATE tta_brandpln_header T SET T.PROPOSAL_ID = P_PROPOSAL_ID WHERE T.PROPOSAL_ID = P_ORDER_ID;
    
      UPDATE tta_brandpln_line T SET T.PROPOSAL_ID = P_PROPOSAL_ID   WHERE T.PROPOSAL_ID = P_ORDER_ID;
    
      NULL;
    
    EXCEPTION
      WHEN OTHERS THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;
  
  END P_TTA_PROPOSAL_COPY;

  PROCEDURE P_TTA_PROPOSAL_CUT(P_ORDER_ID    NUMBER,
                               P_USER_ID     NUMBER,
                               P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                               P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                               P_PROPOSAL_ID OUT VARCHAR2) IS
    V_COUNT               NUMBER; --计数器
    V_TTA_PROPOSAL_HEADER TTA_PROPOSAL_HEADER%ROWTYPE;
    V_SOURCE_ID           NUMBER;
  
  BEGIN
  
    P_ISSUCCESS := 1;
  
    P_ERR_MESSAGE := '执行成功';
    BEGIN
    
    --  update TTA_PROPOSAL_HEADER t
   --      set t.approve_date = sysdate
     --  where t.proposal_id = P_ORDER_ID;
    
      BEGIN
        SELECT TT.SOURCE_ID
          INTO V_SOURCE_ID
          FROM TTA_PROPOSAL_HEADER TT
         WHERE TT.PROPOSAL_ID = P_ORDER_ID
        --   AND TT.STATUS = 'C'
        ;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
        WHEN TOO_MANY_ROWS THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '找到多条ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
      END;
    
      if V_SOURCE_ID is null then
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '未找到上一版本';
        RETURN;
      end if;
    
      UPDATE TTA_PROPOSAL_HEADER T
         SET T.STATUS           = 'C',
             T.LAST_UPDATED_BY  = P_USER_ID,
             T.LAST_UPDATE_DATE = SYSDATE
       WHERE T.PROPOSAL_ID = V_SOURCE_ID;
    
      UPDATE TTA_PROPOSAL_HEADER T
         SET T.STATUS = 'D'
       WHERE T.PROPOSAL_ID = P_ORDER_ID;
    
      P_PROPOSAL_ID := V_SOURCE_ID;
    
    EXCEPTION
      WHEN OTHERS THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;
  
  END P_TTA_PROPOSAL_CUT;

  PROCEDURE P_TTA_BRANDPLNL_SAVE(P_ORDER_ID    NUMBER,
                                 P_USER_ID     NUMBER,
                                 P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                 P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                 ) IS
    V_COUNT               NUMBER; --计数器
    V_TTA_PROPOSAL_HEADER TTA_PROPOSAL_HEADER%ROWTYPE;
    V_Proposal_Id         NUMBER;
  
  BEGIN
  
    P_ISSUCCESS := 1;
  
    P_ERR_MESSAGE := '执行成功';
    

    BEGIN
      
    
    BEGIN
        SELECT TT.Proposal_Id
          INTO V_Proposal_Id
          FROM tta_brandpln_header TT
         WHERE TT.BRANDPLN_H_ID = P_ORDER_ID;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
        WHEN TOO_MANY_ROWS THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '找到多条ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
      END;
      
      if  V_Proposal_Id is null then
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '没有找到proposal头信息！';
          RETURN;
      end if;
      
      
      select count(1) into V_COUNT from tta_proposal_header t
      where t.proposal_id = V_Proposal_Id and t.status in ('B','C');
      
      
      if  V_COUNT >0 then
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := 'proposal头信息再审批通过和审批中，操作失败！';
          RETURN;
      end if;
    
    
    
      --更新行表
      update tta_brandpln_line t
         set t.fcs_purchase = t.po_record * (1 + t.purchase_growth / 100),
             t.fcs_sales    = t.sales * (1 + t.sales_growth / 100)
       where t.brandpln_h_id = P_ORDER_ID
         and t.brand_details = 'Existing_Brand';
    
      --更新头表
      UPDATE tta_brandpln_header T
         SET T.Po_Record_Sum  =
             (select sum(nvl(t1.po_record,0))
                from tta_brandpln_line t1
               where t1.brandpln_h_id = P_ORDER_ID),
             T.Sales_Sum      =
             (select sum(nvl(t2.sales,0))
                from tta_brandpln_line t2
               where t2.brandpln_h_id = P_ORDER_ID),
             T.Actual_Gp      =
             (select avg(nvl(t3.actual_gp,0))
                from tta_brandpln_line t3
               where t3.brandpln_h_id = P_ORDER_ID),
             T.Po_Record_Sum2 =
             (select sum(nvl(t4.po_record2,0))
                from tta_brandpln_line t4
               where t4.brandpln_h_id = P_ORDER_ID),
             T.Sales_Sum2     =
             (select sum(nvl(t5.sales2,0))
                from tta_brandpln_line t5
               where t5.brandpln_h_id = P_ORDER_ID),
             T.Actual_Gp2     =
             (select avg(nvl(t6.actual_gp2,0))
                from tta_brandpln_line t6
               where t6.brandpln_h_id = P_ORDER_ID),
             T.Fcs_Purchase   =
             (select sum(nvl(t7.Fcs_Purchase,0))
                from tta_brandpln_line t7
               where t7.brandpln_h_id = P_ORDER_ID),
             T.Purchase_Growth =
             (select avg(nvl(t8.purchase_growth,0))
                from tta_brandpln_line t8
               where t8.brandpln_h_id = P_ORDER_ID),
             T.Fcs_Sales      =
             (select sum(nvl(t9.Fcs_Sales,0))
                from tta_brandpln_line t9
               where t9.brandpln_h_id = P_ORDER_ID),
             T.Sales_Growth   =
             (select avg(nvl(t10.Sales_Growth,0))
                from tta_brandpln_line t10
               where t10.brandpln_h_id = P_ORDER_ID),
             T.Gp             =
             (select avg(nvl(t11.Gp,0))
                from tta_brandpln_line t11
               where t11.brandpln_h_id = P_ORDER_ID)
       WHERE T.BRANDPLN_H_ID = P_ORDER_ID;
    
    EXCEPTION
      WHEN OTHERS THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;
  
  END P_TTA_BRANDPLNL_SAVE;
  
  
  PROCEDURE P_TTA_BRANDPLN_GEN(P_ORDER_ID    NUMBER,
                                  P_USER_ID     NUMBER,
                                  P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                  P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                  ) IS
    V_COUNT               NUMBER; --计数器
    V_TTA_PROPOSAL_HEADER TTA_PROPOSAL_HEADER%ROWTYPE;
    V_Proposal_Id         NUMBER;
  
  BEGIN
  
    P_ISSUCCESS := 1;
  
    P_ERR_MESSAGE := '执行成功';
    BEGIN
    
      BEGIN
        SELECT TT.*
          INTO V_TTA_PROPOSAL_HEADER
          FROM TTA_PROPOSAL_HEADER TT
         WHERE TT.PROPOSAL_ID = P_ORDER_ID;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
        WHEN TOO_MANY_ROWS THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '找到多条ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
      END;
      
      
      INSERT INTO TTA_BRANDPLN_HEADER (
         BRANDPLN_H_ID,
         NEW_OR_EXISTING,
         VENDOR_NBR,
         VENDOR_NAME,
         REMARK,
         CREATION_DATE,
         CREATED_BY,
         LAST_UPDATED_BY,
         LAST_UPDATE_DATE,
         LAST_UPDATE_LOGIN,
         VERSION_NUM,
         PROPOSAL_ID,
         YEAR_CODE
         
      ) VALUES (
         SEQ_TTA_BRANDPLN_HEADER.NEXTVAL,
         V_TTA_PROPOSAL_HEADER.NEW_OR_EXISTING,
         V_TTA_PROPOSAL_HEADER.VENDOR_NBR,
         V_TTA_PROPOSAL_HEADER.VENDOR_NAME,
         NULL,
         SYSDATE,
         P_USER_ID,
         P_USER_ID,
         SYSDATE,
         P_USER_ID,
         0,
         V_TTA_PROPOSAL_HEADER.PROPOSAL_ID,
         V_TTA_PROPOSAL_HEADER.PROPOSAL_YEAR
      );
    
      
    EXCEPTION
      WHEN OTHERS THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;
  
  END P_TTA_BRANDPLN_GEN;
  
  

  PROCEDURE P_TTA_BRANDPLNL_COUNT(P_ORDER_ID    NUMBER,
                                  P_USER_ID     NUMBER,
                                  P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                  P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                  ) IS
    V_COUNT               NUMBER; --计数器
    V_TTA_PROPOSAL_HEADER TTA_PROPOSAL_HEADER%ROWTYPE;
    V_Proposal_Id         NUMBER;
  
  BEGIN
  
    P_ISSUCCESS := 1;
  
    P_ERR_MESSAGE := '执行成功';
    BEGIN
    
      BEGIN
        SELECT TT.Proposal_Id
          INTO V_Proposal_Id
          FROM tta_brandpln_header TT
         WHERE TT.BRANDPLN_H_ID = P_ORDER_ID;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
        WHEN TOO_MANY_ROWS THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '找到多条ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
      END;
    
      -- UPDATE TTA_PROPOSAL_HEADER T SET T.Is_Pln_Confirm='Y',T.LAST_UPDATED_BY=P_USER_ID,T.LAST_UPDATE_DATE=SYSDATE WHERE T.PROPOSAL_ID = V_Proposal_Id;
    
      UPDATE TTA_PROPOSAL_HEADER T
         SET T.STATUS = 'D'
       WHERE T.PROPOSAL_ID = P_ORDER_ID;
    
    EXCEPTION
      WHEN OTHERS THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;
  
  END P_TTA_BRANDPLNL_COUNT;
  
  
   PROCEDURE P_TTA_DEPTFEE_GEN(P_ORDER_ID    NUMBER,
                                 P_USER_ID     NUMBER,
                                 P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                 P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                 ) IS
    P_COUNT NUMBER; --计数器
  
  BEGIN
  
    P_ISSUCCESS := 1;
  
    P_ERR_MESSAGE := '执行成功';
  
    BEGIN
    
      --SAVEPOINT SP_FREEZE_MATERIAL_ONHAND;
      NULL;
    
      DELETE FROM TTA_DEPT_FEE T WHERE T.PROPOSAL_ID = P_ORDER_ID;
      
      
      
      P_TTA_DEPTFEE_GENERATE(P_ORDER_ID    ,
                                   P_USER_ID     ,
                                   'A',
                                   P_ISSUCCESS   , --返回成功状态 -1：处理失败，1：成功；0：警告
                                   P_ERR_MESSAGE --返回信息
                                   );
      if  P_ISSUCCESS  <> 1 then
         return;
      end if; 
      
      
        P_TTA_DEPTFEE_GENERATE(P_ORDER_ID    ,
                                   P_USER_ID     ,
                                   'B',
                                   P_ISSUCCESS   , --返回成功状态 -1：处理失败，1：成功；0：警告
                                   P_ERR_MESSAGE --返回信息
                                   );
      if  P_ISSUCCESS  <> 1 then
         return;
      end if;                            
                                   
                                   
      
      
      
    
    EXCEPTION
      WHEN OTHERS THEN
        --  ROLLBACK TO SP_FREEZE_MATERIAL_ONHAND;
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;
  
  END P_TTA_DEPTFEE_GEN;
  
  
  
  PROCEDURE P_TTA_DEPTFEE_GENERATE(P_ORDER_ID    NUMBER,
                                   P_USER_ID     NUMBER,
                                   P_ACCORD_TYPE VARCHAR2,
                                   P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                   P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                   ) IS
    V_COUNT               NUMBER; --计数器
    V_TTA_PROPOSAL_HEADER TTA_PROPOSAL_HEADER%ROWTYPE;
  
    V_ACCORD_DESC VARCHAR2(50);
  
    V_TTA_DEPT_FEE_HEADER TTA_DEPT_FEE_HEADER%ROWTYPE;
    V_TTA_PROPOSAL_TERMS_HEADER   TTA_PROPOSAL_TERMS_HEADER%ROWTYPE;

    V_PROPOSAL_ID NUMBER;

    V_FEEDEPT_ID NUMBER;

    V_DEPTFEE_ID NUMBER;

    V_DEPTFEE_LINE_ID NUMBER;

    V_DEPT_COUNT NUMBER;

    V_I NUMBER;

  BEGIN

    P_ISSUCCESS := 1;

    P_ERR_MESSAGE := '执行成功';

    V_I := 0;

    BEGIN
      IF P_ACCORD_TYPE IS NULL THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '费用类型ACCORD_TYPE不能为空';
        RETURN;
      ELSIF P_ACCORD_TYPE = 'A' THEN
        V_ACCORD_DESC := '宣传单张服务费';
      ELSIF P_ACCORD_TYPE = 'B' THEN
        V_ACCORD_DESC := '促销陈列费';
      END IF;

      BEGIN

      --   校验 propasal  单据是否存在
        SELECT TT.*
          INTO V_TTA_PROPOSAL_HEADER
          FROM TTA_PROPOSAL_HEADER TT
         WHERE TT.PROPOSAL_ID = P_ORDER_ID;

      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
        WHEN TOO_MANY_ROWS THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '找到多条ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
      END;

          BEGIN

      --   校验 propasal  单据是否存在
        SELECT TH.*
          INTO V_TTA_PROPOSAL_TERMS_HEADER
          FROM Tta_Proposal_Terms_Header TH
         WHERE TH.PROPOSAL_ID = P_ORDER_ID;
         IF  V_TTA_PROPOSAL_TERMS_HEADER.DEPT_CODE IS NULL    THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := 'ID为[' || P_ORDER_ID || ']的TERMS单据部门为空';
         END   IF ;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']的TERMS单据';
          RETURN;
        WHEN TOO_MANY_ROWS THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '找到多条ID为[' || P_ORDER_ID || ']的TERMS单据';
          RETURN;
      END;

      -- 取 对应的协定标准类型, 对应年度, 对应 状态
      BEGIN
        SELECT T.FEEDEPT_ID
          INTO V_FEEDEPT_ID
          FROM TTA_FEE_DEPT_HEADER T
         WHERE T.YEAR_CODE = V_TTA_PROPOSAL_HEADER.PROPOSAL_YEAR
           AND T.DEPT_CODE = V_TTA_PROPOSAL_HEADER.MAJOR_DEPT_CODE
           AND T.STATUS = 'C'
           AND T.ACCORD_TYPE = P_ACCORD_TYPE;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '没有找到状态为[审批通过]类型为【' || V_ACCORD_DESC || '】的单据';
          RETURN;
        WHEN TOO_MANY_ROWS THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '找到多条状态为[审批通过]类型为【' || V_ACCORD_DESC || '】的单据';
          RETURN;
      END;

      SELECT SEQ_TTA_DEPT_FEE_HEADER.NEXTVAL INTO V_DEPTFEE_ID FROM DUAL;

      --查询 所有选中的行
      for temph in (select t1.*,t3.line_code as PARENT_LINE_CODE ,TD.UNIT UNITD,TD.STANDARD_VALUE STANDARD_VALUED
                                             from
                                               TTA_FEE_DEPT_LINE   T1
                                               left join TTA_FEE_DEPT_LINE   T3 on  t1.PARENT_LINE_ID = T3.FEEDEPT_LINE_ID
                                               left join TTA_FEE_DEPT_DETAIL  TD on t1.feedept_line_id =td.feedept_line_id
                                                                                 and td.dept_code = nvl(V_TTA_PROPOSAL_HEADER.Dept_Code1,V_TTA_PROPOSAL_TERMS_HEADER.Dept_Code)

                                             where
                                                 t1.feedept_id = V_FEEDEPT_ID
                                               and nvl(t1.if_effect,'N')  ='Y'


         order by  FORSTR(T1.line_code) asc  )
        loop
          V_I := 0;
           --  proposal协定标准  没有对应的行  就插入一条数据
           BEGIN
            SELECT TS.DEPTFEE_LINE_ID
              INTO V_DEPTFEE_LINE_ID
              FROM TTA_DEPT_FEE TS
             WHERE
                 TS.LINE_CODE = TEMPh.LINE_CODE
                and   TS.PROPOSAL_ID = P_ORDER_ID
                AND ts.Unit = temph.UNITD
               AND TS.ACCORD_TYPE = P_ACCORD_TYPE;
          EXCEPTION
            WHEN NO_DATA_FOUND THEN
              INSERT INTO TTA_DEPT_FEE
                (DEPTFEE_LINE_ID,
                 DEPT_FEE_ID,
                 LINE_CODE,
                 PARENT_LINE_CODE,
                 SORT_ID,
                 CREATION_DATE,
                 CREATED_BY,
                 LAST_UPDATED_BY,
                 LAST_UPDATE_DATE,
                 VERSION_NUM,
                 REMARK,
                 PROPOSAL_ID,
                 ITEM_DESC_CN,
                 ITEM_DESC_EN,
                 UNIT1,
                 STANDARD_VALUE1,
                 UNIT,
                 UNIT2,
                 UNIT3,
                 UNIT4,
                 UNIT5,
                 ACCORD_TYPE,
                 STANDARD_UNIT_VALUE)
              VALUES
                (SEQ_TTA_DEPT_FEE.NEXTVAL,
                 V_DEPTFEE_ID,
                 TEMPh.LINE_CODE,
                 TEMPh.PARENT_LINE_CODE,
                 TEMPh.SORT_ID,
                 SYSDATE,
                 P_USER_ID,
                 P_USER_ID,
                 SYSDATE,
                 0,
                 NULL,
                 V_TTA_PROPOSAL_HEADER.PROPOSAL_ID,
                 TEMPh.ITEM_DESC_CN,
                 TEMPh.ITEM_DESC_EN,
                 temph.UNITD,
                 null,
                 temph.UNITD,
                 temph.UNITD,
                 temph.UNITD,
                 temph.UNITD,
                 temph.UNITD,
                 P_ACCORD_TYPE,
                 to_char(temph.STANDARD_VALUED));

          END;
          --循环行下面的单位信息 根据行ID
    /*
      FOR TEMPL IN (SELECT
                           T2.UNIT           AS UNIT1,
                           T2.STANDARD_VALUE AS STANDARD_VALUE1
                      FROM
                           TTA_FEE_DEPT_DETAIL T2

                     WHERE
                        T2.FEEDEPT_LINE_ID = temph.FEEDEPT_LINE_ID
                           order by   t2.feedept_Detail_Id asc
                       ) LOOP
        V_I := V_I + 1;
        IF V_I = 1 THEN
        --   查询有没有行 没有就插入一条信息
          BEGIN
            SELECT TS.DEPTFEE_LINE_ID
              INTO V_DEPTFEE_LINE_ID
              FROM TTA_DEPT_FEE TS
             WHERE
                 TS.LINE_CODE = TEMPh.LINE_CODE
                and   TS.PROPOSAL_ID = P_ORDER_ID
               AND TS.ACCORD_TYPE = P_ACCORD_TYPE;
          EXCEPTION
            WHEN NO_DATA_FOUND THEN
              INSERT INTO TTA_DEPT_FEE
                (DEPTFEE_LINE_ID,
                 DEPT_FEE_ID,
                 LINE_CODE,
                 PARENT_LINE_CODE,
                 SORT_ID,
                 CREATION_DATE,
                 CREATED_BY,
                 LAST_UPDATED_BY,
                 LAST_UPDATE_DATE,
                 VERSION_NUM,
                 REMARK,
                 PROPOSAL_ID,
                 ITEM_DESC_CN,
                 ITEM_DESC_EN,
                 UNIT1,
                 STANDARD_VALUE1,
                 UNIT,
                 ACCORD_TYPE)
              VALUES
                (SEQ_TTA_DEPT_FEE.NEXTVAL,
                 V_DEPTFEE_ID,
                 TEMPh.LINE_CODE,
                 TEMPh.PARENT_LINE_CODE,
                 TEMPh.SORT_ID,
                 SYSDATE,
                 P_USER_ID,
                 P_USER_ID,
                 SYSDATE,
                 0,
                 NULL,
                 V_TTA_PROPOSAL_HEADER.PROPOSAL_ID,
                 TEMPh.ITEM_DESC_CN,
                 TEMPh.ITEM_DESC_EN,
                 TEMPL.UNIT1,
                 TEMPL.STANDARD_VALUE1,
                 TEMPL.UNIT1,
                 P_ACCORD_TYPE);
              CONTINUE;
          END;
          --更新单位值 1
          UPDATE TTA_DEPT_FEE T
             SET T.UNIT1           = TEMPL.UNIT1,
                 T.STANDARD_VALUE1 = TEMPL.STANDARD_VALUE1
           WHERE T.DEPTFEE_LINE_ID = V_DEPTFEE_LINE_ID;
        ELSIF V_I = 2 THEN

        --对应行单位有多个时 说 时 说明是跨部门的
          UPDATE TTA_PROPOSAL_HEADER T
             SET T.IS_TRANSDEPART = 'Y'
           WHERE T.PROPOSAL_ID = V_TTA_PROPOSAL_HEADER.PROPOSAL_ID;
          BEGIN
            SELECT TE.DEPTFEE_LINE_ID
              INTO V_DEPTFEE_LINE_ID
              FROM TTA_DEPT_FEE TE
             WHERE TE.LINE_CODE = TEMPh.LINE_CODE
               AND TE.ACCORD_TYPE = P_ACCORD_TYPE
               AND TE.PROPOSAL_ID = P_ORDER_ID;
          EXCEPTION
            WHEN NO_DATA_FOUND THEN
              INSERT INTO TTA_DEPT_FEE
                (DEPTFEE_LINE_ID,
                 DEPT_FEE_ID,
                 LINE_CODE,
                 PARENT_LINE_CODE,
                 SORT_ID,
                 CREATION_DATE,
                 CREATED_BY,
                 LAST_UPDATED_BY,
                 LAST_UPDATE_DATE,
                 VERSION_NUM,
                 REMARK,
                 PROPOSAL_ID,
                 ITEM_DESC_CN,
                 ITEM_DESC_EN,
                 UNIT2,
                 STANDARD_VALUE2,
                 UNIT,
                 ACCORD_TYPE)
              VALUES
                (SEQ_TTA_DEPT_FEE.NEXTVAL,
                 V_DEPTFEE_ID,
                 TEMPh.LINE_CODE,
                 TEMPh.PARENT_LINE_CODE,
                 TEMPh.SORT_ID,
                 SYSDATE,
                 P_USER_ID,
                 P_USER_ID,
                 SYSDATE,
                 0,
                 NULL,
                 V_TTA_PROPOSAL_HEADER.PROPOSAL_ID,
                 TEMPh.ITEM_DESC_CN,
                 TEMPh.ITEM_DESC_EN,
                 TEMPL.UNIT1,
                 TEMPL.STANDARD_VALUE1,
                 TEMPL.UNIT1,
                 P_ACCORD_TYPE);
              CONTINUE;
          END;
          UPDATE TTA_DEPT_FEE T
             SET T.UNIT2           = TEMPL.UNIT1,
                 T.UNIT           = TEMPL.UNIT1,
                 T.STANDARD_VALUE2 = TEMPL.STANDARD_VALUE1
           WHERE T.DEPTFEE_LINE_ID = V_DEPTFEE_LINE_ID;

        ELSIF V_I = 3 THEN
          BEGIN
            SELECT TE.DEPTFEE_LINE_ID
              INTO V_DEPTFEE_LINE_ID
              FROM TTA_DEPT_FEE TE
             WHERE TE.LINE_CODE = TEMPh.LINE_CODE
               AND TE.ACCORD_TYPE = P_ACCORD_TYPE
               AND TE.PROPOSAL_ID = P_ORDER_ID;
          EXCEPTION
            WHEN NO_DATA_FOUND THEN
              INSERT INTO TTA_DEPT_FEE
                (DEPTFEE_LINE_ID,
                 DEPT_FEE_ID,
                 LINE_CODE,
                 PARENT_LINE_CODE,
                 SORT_ID,
                 CREATION_DATE,
                 CREATED_BY,
                 LAST_UPDATED_BY,
                 LAST_UPDATE_DATE,
                 VERSION_NUM,
                 REMARK,
                 PROPOSAL_ID,
                 ITEM_DESC_CN,
                 ITEM_DESC_EN,
                 UNIT3,
                 STANDARD_VALUE3,
                 UNIT,
                 ACCORD_TYPE)
              VALUES
                (SEQ_TTA_DEPT_FEE.NEXTVAL,
                 V_DEPTFEE_ID,
                 TEMPh.LINE_CODE,
                 TEMPh.PARENT_LINE_CODE,
                 TEMPh.SORT_ID,
                 SYSDATE,
                 P_USER_ID,
                 P_USER_ID,
                 SYSDATE,
                 0,
                 NULL,
                 V_TTA_PROPOSAL_HEADER.PROPOSAL_ID,
                 TEMPh.ITEM_DESC_CN,
                 TEMPh.ITEM_DESC_EN,
                 TEMPL.UNIT1,
                 TEMPL.STANDARD_VALUE1,
                 TEMPL.UNIT1,
                 P_ACCORD_TYPE);
              CONTINUE;
          END;
          UPDATE TTA_DEPT_FEE T
             SET T.UNIT3           = TEMPL.UNIT1,
                 T.UNIT           = TEMPL.UNIT1,
                 T.STANDARD_VALUE3 = TEMPL.STANDARD_VALUE1
           WHERE T.DEPTFEE_LINE_ID = V_DEPTFEE_LINE_ID;

        ELSIF V_I = 4 THEN
          BEGIN
            SELECT TE.DEPTFEE_LINE_ID
              INTO V_DEPTFEE_LINE_ID
              FROM TTA_DEPT_FEE TE
             WHERE TE.LINE_CODE = TEMPh.LINE_CODE
               AND TE.ACCORD_TYPE = P_ACCORD_TYPE
               AND TE.PROPOSAL_ID = P_ORDER_ID;
          EXCEPTION
            WHEN NO_DATA_FOUND THEN
              INSERT INTO TTA_DEPT_FEE
                (DEPTFEE_LINE_ID,
                 DEPT_FEE_ID,
                 LINE_CODE,
                 PARENT_LINE_CODE,
                 SORT_ID,
                 CREATION_DATE,
                 CREATED_BY,
                 LAST_UPDATED_BY,
                 LAST_UPDATE_DATE,
                 VERSION_NUM,
                 REMARK,
                 PROPOSAL_ID,
                 ITEM_DESC_CN,
                 ITEM_DESC_EN,
                 UNIT4,
                 STANDARD_VALUE4,
                 UNIT,
                 ACCORD_TYPE)
              VALUES
                (SEQ_TTA_DEPT_FEE.NEXTVAL,
                 V_DEPTFEE_ID,
                 TEMPh.LINE_CODE,
                 TEMPh.PARENT_LINE_CODE,
                 TEMPh.SORT_ID,
                 SYSDATE,
                 P_USER_ID,
                 P_USER_ID,
                 SYSDATE,
                 0,
                 NULL,
                 V_TTA_PROPOSAL_HEADER.PROPOSAL_ID,
                 TEMPh.ITEM_DESC_CN,
                 TEMPh.ITEM_DESC_EN,
                 TEMPL.UNIT1,
                 TEMPL.STANDARD_VALUE1,
                 TEMPL.UNIT1,
                 P_ACCORD_TYPE);
              CONTINUE;
          END;
          UPDATE TTA_DEPT_FEE T
             SET T.UNIT4           = TEMPL.UNIT1,
                 T.UNIT           = TEMPL.UNIT1,
                 T.STANDARD_VALUE4 = TEMPL.STANDARD_VALUE1
           WHERE T.DEPTFEE_LINE_ID = V_DEPTFEE_LINE_ID;
        ELSIF V_I = 5 THEN
          BEGIN
            SELECT TE.DEPTFEE_LINE_ID
              INTO V_DEPTFEE_LINE_ID
              FROM TTA_DEPT_FEE TE
             WHERE TE.LINE_CODE = TEMPh.LINE_CODE
               AND TE.ACCORD_TYPE = P_ACCORD_TYPE
               AND TE.PROPOSAL_ID = P_ORDER_ID;
          EXCEPTION
            WHEN NO_DATA_FOUND THEN
              INSERT INTO TTA_DEPT_FEE
                (DEPTFEE_LINE_ID,
                 DEPT_FEE_ID,
                 LINE_CODE,
                 PARENT_LINE_CODE,
                 SORT_ID,
                 CREATION_DATE,
                 CREATED_BY,
                 LAST_UPDATED_BY,
                 LAST_UPDATE_DATE,
                 VERSION_NUM,
                 REMARK,
                 PROPOSAL_ID,
                 ITEM_DESC_CN,
                 ITEM_DESC_EN,
                 UNIT5,
                 STANDARD_VALUE5,
                 UNIT,
                 ACCORD_TYPE)
              VALUES
                (SEQ_TTA_DEPT_FEE.NEXTVAL,
                 V_DEPTFEE_ID,
                 TEMPh.LINE_CODE,
                 TEMPh.PARENT_LINE_CODE,
                 TEMPh.SORT_ID,
                 SYSDATE,
                 P_USER_ID,
                 P_USER_ID,
                 SYSDATE,
                 0,
                 NULL,
                 V_TTA_PROPOSAL_HEADER.PROPOSAL_ID,
                 TEMPh.ITEM_DESC_CN,
                 TEMPh.ITEM_DESC_EN,
                 TEMPL.UNIT1,
                 TEMPL.STANDARD_VALUE1,
                 TEMPL.UNIT1,
                 P_ACCORD_TYPE);
              CONTINUE;
          END;
          UPDATE TTA_DEPT_FEE T
             SET T.UNIT5           = TEMPL.UNIT1,
                 T.UNIT           = TEMPL.UNIT1,
                 T.STANDARD_VALUE5 = TEMPL.STANDARD_VALUE1
           WHERE T.DEPTFEE_LINE_ID = V_DEPTFEE_LINE_ID;

        END IF;

      END LOOP;
      */
      end loop;
      -- 更新所有的目录级别 --根据序号中 点 的 个数
      FOR TEMP IN (SELECT *
                     FROM TTA_DEPT_FEE TS
                    WHERE TS.PROPOSAL_ID = P_ORDER_ID
                      AND TS.ACCORD_TYPE = P_ACCORD_TYPE) LOOP
        UPDATE TTA_DEPT_FEE T
           SET T.DIRECTORY_LEVEL =
               (LENGTH(TEMP.LINE_CODE) -
               LENGTH(REPLACE(TEMP.LINE_CODE, '.', '')))
         WHERE T.DEPTFEE_LINE_ID = TEMP.DEPTFEE_LINE_ID;
      END LOOP;

    EXCEPTION
      WHEN OTHERS THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;

  END P_TTA_DEPTFEE_GENERATE;


  PROCEDURE P_TTA_CONTRACT_GEN(P_ORDER_ID    NUMBER,
                               P_USER_ID     NUMBER,
                               P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                               P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                               ) IS
    P_COUNT         NUMBER; --计数器
    V_PROPOSAL_ID   NUMBER;
    V_CONTRACT_L_ID NUMBER;

  BEGIN

    P_ISSUCCESS := 1;

    P_ERR_MESSAGE := '执行成功';

    BEGIN

      --SAVEPOINT SP_FREEZE_MATERIAL_ONHAND;
      IF P_ORDER_ID IS NULL THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '合同ID不能为空';
        RETURN;
      END IF;

      DELETE FROM TTA_CONTRACT_LINE T WHERE T.CONTRACT_H_ID = P_ORDER_ID;
      DELETE FROM TTA_CONTRACT_DETAIL T WHERE T.CONTRACT_H_ID = P_ORDER_ID;

      BEGIN
        SELECT TT.PROPOSAL_ID
          INTO V_PROPOSAL_ID
          FROM TTA_PROPOSAL_HEADER TT, TTA_CONTRACT_HEADER TH
         WHERE TH.CONTRACT_H_ID = P_ORDER_ID
           AND TH.PROPOSAL_CODE = TT.ORDER_NBR
           AND TT.STATUS = 'C';
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
        WHEN TOO_MANY_ROWS THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '找到多条ID为[' || P_ORDER_ID || ']的单据';
          RETURN;
      END;

      FOR TEMP IN (SELECT T2.*,t1.collect_type,t1.unit,t1.fee_notax,t1.fee_intas
                     FROM TTA_PROPOSAL_TERMS_LINE   T1,
                          TTA_PROPOSAL_TERMS_HEADER T2,
                          tta_brandpln_header t3
                    WHERE T1.TERMS_H_ID = T2.TERMS_H_ID(+)
                      AND T1.PROPOSAL_ID = V_PROPOSAL_ID
                      and t2.proposal_id = t3.proposal_id(+)
                      ) LOOP

        SELECT SEQ_TTA_CONTRACT_LINE.NEXTVAL
          INTO V_CONTRACT_L_ID
          FROM DUAL;

       /* INSERT INTO TTA_CONTRACT_LINE
          (CONTRACT_L_ID,
           CONTRACT_H_ID,
           VENDOR_NBR,
           VENDOR_NAME,
           DEPT_CODE,
           BRAND_CN,
           BRAND_EN，
           PURCHASE,
           SALES,
           SALES_AREA,
           DELIVERY_GRANARY,
           OI_TYPE,
           TERMS_NAME,
           COLLECTION_METHOD,
           TTA_VALUE,
           UNIT,
           TERMS,
           FEE_INTAS,
           FEE_NOTAX,
           STATUS,
           VERSION_NUM， CREATION_DATE,
           CREATED_BY,
           LAST_UPDATED_BY,
           LAST_UPDATE_DATE,
           LAST_UPDATE_LOGIN,
           DEPT_DESC,
           BRAND)
        VALUES
          (V_CONTRACT_L_ID,
           P_ORDER_ID,
           temp.vendor_nbr,
           temp.vendor_desc,
           temp.dept_code,
           temp.brand_cn,
           temp.brand_en,
           temp.fcs_purchse,
           temp.fcs_sales,
           temp.sales_area,
           temp.WAREHOUSE_DESC,
           NULL,
           NULL,
           temp.collect_type,
           NULL,
           temp.unit,
           NULL,
           temp.fee_intas,
           temp.fee_notax,
           'N',

           0， SYSDATE,
           P_USER_ID,
           P_USER_ID,
           SYSDATE,
           P_USER_ID,
           temp.dept_desc,
           temp.brand_code);*/

      END LOOP;

    EXCEPTION
      WHEN OTHERS THEN
        --  ROLLBACK TO SP_FREEZE_MATERIAL_ONHAND;
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;

  END P_TTA_CONTRACT_GEN;

  PROCEDURE P_TTA_PROPOSAL_SPLIT(P_ORDER_ID    NUMBER,
                                 P_USER_ID     NUMBER,
                                 P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                 P_ERR_MESSAGE OUT VARCHAR2 --返回信息
                                 ) IS
    P_COUNT NUMBER; --计数器

  BEGIN

    P_ISSUCCESS := 1;

    P_ERR_MESSAGE := '执行成功';

    BEGIN

      --SAVEPOINT SP_FREEZE_MATERIAL_ONHAND;
      NULL;

    EXCEPTION
      WHEN OTHERS THEN
        --  ROLLBACK TO SP_FREEZE_MATERIAL_ONHAND;
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;

  END P_TTA_PROPOSAL_SPLIT;

    PROCEDURE P_TTA_CLAUSE_COPY(P_ORDER_ID    NUMBER,
                                 P_USER_ID     NUMBER,
                                 P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                 P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                                 P_ID OUT  NUMBER,
                                 P_YEAR2 OUT VARCHAR2
                                 ) IS
    P_COUNT NUMBER; --计数器
    p_business_version  TTA_TERMS_FRAME_HEADER.Business_Version%type;
    p_old_id  number ;
    p_year   number ;
    p_next_business_version  TTA_TERMS_FRAME_HEADER.Business_Version%type;
    p_ttfhh_count number ;
    P_dept_code  TTA_TERMS_FRAME_HEADER.Dept_Code%type;

  BEGIN

    P_ISSUCCESS := 1;

    P_ERR_MESSAGE := '执行成功';
    p_old_id := 0;

    BEGIN
      select ttf.business_version,
             ttf.year,
             ttf.dept_code
      into
      p_business_version,
      p_year,
      P_dept_code
      from TTA_TERMS_FRAME_HEADER ttf where ttf.team_framework_id = P_ORDER_ID ;
      select get_FillZero(p_business_version+1,'L',3) into p_next_business_version  from dual ;

      select count(1) into p_ttfhh_count  from TTA_TERMS_FRAME_HEADER_h ttfhh
      where ttfhh.year =p_year
      and ttfhh.business_version = p_next_business_version
      and ttfhh.dept_code =  P_dept_code;

      if nvl(p_ttfhh_count,0)>0 then
            P_ISSUCCESS := -1;
            P_ERR_MESSAGE := '--条款名目已经存在相同年份,相同版本,相同部门的数据';
            return ;
      end if ;


      select SEQ_TTA_TERMS_FRAME_HEADER_H.NEXTVAL into p_old_id  from dual ;
      P_ID:= p_old_id;
      P_YEAR2:=p_year ;
      --//版本为001插入两次   留作备底
      if nvl(p_business_version,'001') ='001' then
        insert into TTA_TERMS_FRAME_HEADER_H
        (
        team_framework_id
        ,frame_code
        ,year
        ,frame_name
        ,bill_status
        ,business_version
        ,pass_date
        ,effective_date
        ,expiration_date
        ,resouce_id
        ,resouce_business_version
        ,created_by
        ,last_updated_by
        ,last_update_date
        ,creation_date
        ,last_update_login
        ,version_num
        ,delete_flag
        ,dept_id
        ,dept_code
        ,dept_name
        )

        select

        SEQ_TTA_TERMS_FRAME_HEADER_H.NEXTVAL
        ,frame_code
        ,year
        ,frame_name
        ,bill_status
        ,business_version
        ,pass_date
        ,effective_date
        ,expiration_date
        ,team_framework_id
        ,business_version
        ,created_by
        ,last_updated_by
        ,last_update_date
        ,creation_date
        ,last_update_login
       , 0
        ,delete_flag
        ,dept_id
        ,dept_code
        ,dept_name

        from   TTA_TERMS_FRAME_HEADER ttf where  ttf.team_framework_id = P_ORDER_ID ;
      end   if ;
        --存入
            insert into TTA_TERMS_FRAME_HEADER_H
        (
        team_framework_id
        ,frame_code
        ,year
        ,frame_name
        ,bill_status
        ,business_version
        ,pass_date
        ,effective_date
        ,expiration_date
        ,resouce_id
        ,resouce_business_version
        ,created_by
        ,last_updated_by
        ,last_update_date
        ,creation_date
        ,last_update_login
        ,version_num
        ,delete_flag
        ,dept_id
        ,dept_code
        ,dept_name
        )

        select

        p_old_id
        ,frame_code
        ,year
        ,frame_name
        ,'DS01'
        ,get_FillZero(business_version+1,'L',3)
        ,null
        ,effective_date
        ,expiration_date
        ,team_framework_id
        ,business_version
        ,P_USER_ID
        ,P_USER_ID
        ,sysdate
        ,sysdate
        ,P_USER_ID
       , 0
        ,delete_flag
        ,dept_id
        ,dept_code
        ,dept_name

        from   TTA_TERMS_FRAME_HEADER ttf where  ttf.team_framework_id = P_ORDER_ID ;

        --复制行
        if nvl(p_business_version,'001') ='001' then
          insert  into
          Tta_Clause_Tree_h
          (
          clause_id
          ,team_framework_id
          ,clause_code
          ,clause_cn
          ,clause_en
          ,status
          ,parent_id
          ,pass_date
          ,effective_date
          ,is_leaf
          ,is_hierarchy_show
          ,payment_method
          ,business_type
         , expression_value
          ,business_version
          ,resouce_id
          ,resouce_business_version
          ,order_no
         , created_by
          ,last_updated_by
         , last_update_date
         , creation_date
         , last_update_login
         , version_num
         , delete_flag
         , code
        ,  p_code
         , is_global_variable
          ,is_change
         , is_global_tta
          ,is_global_fee
         , expression_value_fee
        ,  expression_value_tta
        ,  term_category
         , global_value
         , global_value_tta
         , global_value_fee
         , old_order_no

          )
          select

           seq_Tta_Clause_Tree_h.Nextval
         , team_framework_id
         , clause_code
         , clause_cn
          ,clause_en
         , status
         , parent_id
         , pass_date
         , effective_date
         , is_leaf
         , is_hierarchy_show
         , payment_method
         , business_type
         , expression_value
          ,business_version
          ,clause_id
          ,business_version
          ,order_no
          ,created_by
         , last_updated_by
         , last_update_date
         , creation_date
         , last_update_login
         , version_num
         , delete_flag
         ,code
         , p_code
         , is_global_variable
         ,0
         , is_global_tta
          ,is_global_fee
         , expression_value_fee
         , expression_value_tta
         , term_category
         , global_value
         , global_value_tta
         , global_value_fee
         , old_order_no

          from Tta_Clause_Tree tct  where tct.team_framework_id = P_ORDER_ID ;
        end if ;

                   insert  into
          Tta_Clause_Tree_h
          (
          clause_id
          ,team_framework_id
          ,clause_code
          ,clause_cn
          ,clause_en
          ,status
          ,parent_id
          ,pass_date
          ,effective_date
          ,is_leaf
          ,is_hierarchy_show
          ,payment_method
          ,business_type
         , expression_value
          ,business_version
          ,resouce_id
          ,resouce_business_version
          ,order_no
         , created_by
          ,last_updated_by
         , last_update_date
         , creation_date
         , last_update_login
         , version_num
         , delete_flag
         , code
        ,  p_code
         , is_global_variable
          ,is_change
         , is_global_tta
          ,is_global_fee
         , expression_value_fee
        ,  expression_value_tta
        ,  term_category
         , global_value
         , global_value_tta
         , global_value_fee
         , old_order_no

          )
          select

           seq_Tta_Clause_Tree_h.Nextval
         , p_old_id
         , clause_code
         , clause_cn
          ,clause_en
         , status
         , parent_id
         , pass_date
         , effective_date
         , is_leaf
         , is_hierarchy_show
         , payment_method
         , business_type
         , expression_value
          ,business_version
          ,clause_id
          ,business_version
          ,order_no
          ,P_USER_ID
         , P_USER_ID
         , sysdate
         , sysdate
         , P_USER_ID
         , 0
         , delete_flag
         ,code
         , p_code
         , is_global_variable
         ,0
         , is_global_tta
          ,is_global_fee
         , expression_value_fee
         , expression_value_tta
         , term_category
         , global_value
         , global_value_tta
         , global_value_fee
         , old_order_no

          from Tta_Clause_Tree tct  where tct.team_framework_id = P_ORDER_ID ;

          --复制单位
          insert  into
          Tta_Collect_Type_Line_h
          (
          collect_type_id
          ,clause_id
          ,collect_type
         ,standard_value
         , unit_value
         , is_enable
         , is_default_value
         , version_num
         , creation_date
         , created_by
         , last_updated_by
         , last_update_date
         , last_update_login
          ,team_framework_id
         , resouce_id
         , delete_flag
          )
          select
         seq_Tta_Collect_Type_Line_h.Nextval
         ,(select tcth.clause_id from Tta_Clause_Tree_h tcth where tcth.resouce_id = tctl.clause_id and tcth.team_framework_id =p_old_id  and rownum =1)
          ,collect_type
          ,standard_value
          ,unit_value
         , is_enable
         , is_default_value
         , 0
         , sysdate
         , P_USER_ID
         , P_USER_ID
          ,sysdate
        ,  P_USER_ID
         , p_old_id
         ,collect_type_id
         , delete_flag

          from Tta_Collect_Type_Line  tctl  where tctl.team_framework_id =  P_ORDER_ID ;

      --SAVEPOINT SP_FREEZE_MATERIAL_ONHAND;
      NULL;

    EXCEPTION
      WHEN OTHERS THEN
        --  ROLLBACK TO SP_FREEZE_MATERIAL_ONHAND;
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;

  END P_TTA_CLAUSE_COPY;

      PROCEDURE P_TTA_CLAUSE_COPY2(P_ORDER_ID_OLD    NUMBER,
                                   P_DEPT_CODE   VARCHAR2,
                                   P_USER_ID     NUMBER,
                                   P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                   P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                                   P_ID OUT  NUMBER,
                                   P_YEAR2 OUT VARCHAR2
                                 ) IS
    P_COUNT NUMBER; --计数器
    p_business_version  TTA_TERMS_FRAME_HEADER.Business_Version%type;
    P_COUNT_TTFH NUMBER; --计数器
  --  p_dept_code TTA_TERMS_FRAME_HEADER.Dept_Code%type;
    p_max_year    number ;
    P_ORDER_ID NUMBER ;

  BEGIN

    P_ISSUCCESS := 1;

    P_ERR_MESSAGE := '执行成功';
    p_id := 0;
    p_max_year :=0;
    BEGIN
      IF  P_USER_ID IS NULL or  P_DEPT_CODE is null   THEN
            P_ISSUCCESS := -1;
            P_ERR_MESSAGE := '用户ID,部门CODE 不可为空';
        return ;
      END   IF ;
      begin
              select nvl(MAX(ttfhh.year),0) into p_max_year from    tta_terms_frame_header ttfhh  where ttfhh.Bill_Status = 'DS03' AND TTFHH.DEPT_CODE =  P_DEPT_CODE ;
              if p_max_year =0  then
                    P_ISSUCCESS := -1;
                    P_ERR_MESSAGE := '不存在部门为'||P_DEPT_CODE||'的审批通过的单据';
                    return ;
                end   if ;
              exception
                when no_data_found then
                    P_ISSUCCESS := -1;
                    P_ERR_MESSAGE := '不存在部门为'||P_DEPT_CODE||'的审批通过的单据';
                    return ;
                when TOO_MANY_ROWS then
                    P_ISSUCCESS := -1;
                    P_ERR_MESSAGE := '找到部门为'||P_DEPT_CODE||'的多条审批通过的单据';
                    return ;
      end ;

      select ttfhhr.team_framework_id  into P_ORDER_ID
      from    tta_terms_frame_header ttfhhr
      where
      ttfhhr.Bill_Status = 'DS03'
      AND TTFHHr.DEPT_CODE =  P_DEPT_CODE
      and ttfhhr.year =  p_max_year;
      P_YEAR2:= p_max_year+1;
      select count(1) into P_COUNT_TTFH from   tta_terms_frame_header ttfh
      where ttfh.year =p_max_year+1
      and ttfh.dept_code =  P_DEPT_CODE;
      IF NVL(P_COUNT_TTFH,0)>0 THEN
            P_ISSUCCESS := -1;
            P_ERR_MESSAGE := '--已经存在'||P_YEAR2||'年,部门为'||p_dept_code||'的单据';
            return ;
      END IF ;
      select SEQ_TTA_TERMS_FRAME_HEADER.NEXTVAL into p_id  from dual ;
        --存入
            insert into TTA_TERMS_FRAME_HEADER
        (
        team_framework_id
        ,frame_code
        ,year
        ,frame_name
        ,bill_status
        ,business_version
        ,pass_date
        ,effective_date
        ,expiration_date
        ,created_by
        ,last_updated_by
        ,last_update_date
        ,creation_date
        ,last_update_login
        ,version_num
        ,delete_flag
        ,dept_id
        ,dept_code
        ,dept_name
        )

        select

        p_id
        ,p_max_year+1
        ,p_max_year+1
        ,frame_name
        ,'DS01'
        ,'001'
        ,null
        ,effective_date
        ,expiration_date
        ,P_USER_ID
        ,P_USER_ID
        ,sysdate
        ,sysdate
        ,P_USER_ID
       , 0
        ,delete_flag
         ,dept_id
        ,dept_code
        ,dept_name

        from   TTA_TERMS_FRAME_HEADER ttf where  ttf.team_framework_id = P_ORDER_ID ;

          insert  into
          Tta_Clause_Tree
          (
           clause_id
          , team_framework_id
           ,clause_code
          , clause_cn
          , clause_en
          , status
          , parent_id
          , pass_date
          , effective_date
           ,is_leaf
           ,is_hierarchy_show
           ,payment_method
           ,business_type
          , expression_value
          , business_version
          , order_no
          , created_by
          , last_updated_by
          , last_update_date
          , creation_date
          , last_update_login
          , version_num
          , delete_flag
          , code
           ,p_code
           ,is_global_variable
          , old_clause_id
           ,old_order_no
          , is_global_tta
           ,is_global_fee
          , expression_value_fee
          , expression_value_tta
          , term_category
          , global_value
          , global_value_tta
          , global_value_fee
          )
          select


           seq_tta_clause_tree.nextval
           ,p_id
           ,clause_code
          , clause_cn
          , clause_en
          , status
          , parent_id
          , pass_date
          , effective_date
           ,is_leaf
           ,is_hierarchy_show
           ,payment_method
           ,business_type
          , expression_value
          , 1
          , order_no
          , P_USER_ID
          , P_USER_ID
          , sysdate
          , sysdate
          , P_USER_ID
          , 0
          , delete_flag
          , code
           ,p_code
           ,is_global_variable
          , clause_id
           ,order_no
          , is_global_tta
           ,is_global_fee
          , expression_value_fee
          , expression_value_tta
          , term_category
          , global_value
          , global_value_tta
          , global_value_fee

          from Tta_Clause_Tree tct  where tct.team_framework_id = P_ORDER_ID ;

          --复制单位
          insert  into
          Tta_Collect_Type_Line
          (
           collect_type_id
           ,clause_id
           ,collect_type
           ,standard_value
           ,unit_value
           ,is_enable
           ,is_default_value
           ,version_num
           ,creation_date
           ,created_by
           ,last_updated_by
           ,last_update_date
           ,last_update_login
           ,team_framework_id
           ,delete_flag
          )
          select
           seq_Tta_Collect_Type_Line.Nextval
           ,(select tct.clause_id from   Tta_Clause_Tree tct  where tct.old_clause_id = tctl.clause_id and tct.team_framework_id = p_id and rownum =1 )
           ,collect_type
           ,standard_value
           ,unit_value
           ,is_enable
           ,is_default_value
           ,0
           ,sysdate
           ,P_USER_ID
           ,P_USER_ID
           ,sysdate
           ,P_USER_ID
           ,p_id
           ,delete_flag

          from Tta_Collect_Type_Line  tctl  where tctl.team_framework_id =  P_ORDER_ID ;

      --SAVEPOINT SP_FREEZE_MATERIAL_ONHAND;
      NULL;

    EXCEPTION
      WHEN OTHERS THEN
        --  ROLLBACK TO SP_FREEZE_MATERIAL_ONHAND;
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;

  END P_TTA_CLAUSE_COPY2;

  PROCEDURE P_TTA_CLAUSE_RETURN(P_ORDER_ID    NUMBER,
                                 P_USER_ID     NUMBER,
                                 P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                 P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                                 P_ID OUT  NUMBER
                                 ) IS
    P_COUNT NUMBER; --计数器
    p_business_version  TTA_TERMS_FRAME_HEADER.Business_Version%type;
    P_OLD__TEAM_FRAMEWORK_ID   number ;
    P_seq_tta_clause_tree_id   number;
    tta_terms_frame_header_h_one   tta_terms_frame_header_h%ROWTYPE;
  BEGIN

    P_ISSUCCESS := 1;

    P_ERR_MESSAGE := '执行成功';
    p_id := 0;

    BEGIN
      select *into tta_terms_frame_header_h_one  from  tta_terms_frame_header_h   ttfh where ttfh.TEAM_FRAMEWORK_ID = P_ORDER_ID ;
      P_OLD__TEAM_FRAMEWORK_ID :=  tta_terms_frame_header_h_one.resouce_id;
     --更新头表
      UPDATE  tta_terms_frame_header ttf

      SET

       frame_code  =  tta_terms_frame_header_h_one.frame_code,
       year  =   tta_terms_frame_header_h_one.year,
       frame_name  =tta_terms_frame_header_h_one.frame_name ,
       business_version =tta_terms_frame_header_h_one.business_version,
       effective_date   = tta_terms_frame_header_h_one.effective_date,
       expiration_date  = tta_terms_frame_header_h_one.expiration_date,
       last_updated_by  = P_USER_ID,
       last_update_date = sysdate,
       last_update_login = P_USER_ID,
       version_num =   tta_terms_frame_header_h_one.version_num+1,
       dept_id    =   tta_terms_frame_header_h_one. dept_id ,
       dept_code  =   tta_terms_frame_header_h_one.dept_code,
       dept_name  =    tta_terms_frame_header_h_one.dept_name

              where
              ttf.team_framework_id = tta_terms_frame_header_h_one.resouce_id ;


       for  v in (select * from tta_clause_tree_h tcth where nvl(tcth.is_change,0) <>0
                                                       and   tcth.team_framework_id =P_ORDER_ID )

      loop
       if v.RESOUCE_ID is  not null   then
         update  tta_clause_tree tct set
         clause_code =  v.clause_code,
         clause_cn   =v.clause_cn ,
         clause_en   =v.clause_en ,
         status   = v.status ,
         effective_date = v.effective_date,
         is_leaf   =v.is_leaf ,
         is_hierarchy_show =v.is_hierarchy_show,
         payment_method  =v.payment_method,
         business_type   = v.business_type,
         expression_value  =v.expression_value,
         business_version  =v.business_version,
         order_no = v.order_no,
         last_updated_by   =P_USER_ID ,
         last_update_date   = sysdate ,
         last_update_login  = P_USER_ID,
         version_num  =  tct.version_num+1,
         delete_flag =v.delete_flag ,
         code =v.code,
         p_code =v.p_code,
         is_global_variable = v.is_global_variable,
        -- old_clause_id =v.old_clause_id ,
        -- old_order_no = v.old_order_no ,
         is_global_tta  =v.is_global_tta ,
         is_global_fee  =v.is_global_fee ,
         expression_value_fee =v.expression_value_fee,
         expression_value_tta =v.expression_value_tta,
         term_category  = v.term_category ,
         global_value =v.global_value  ,
         global_value_tta =v.global_value_tta ,
         global_value_fee =v.global_value_fee

         where   tct.clause_id = v.RESOUCE_ID ;

                      for  param  in  (select * from tta_collect_type_line_h  tctlh where tctlh.CLAUSE_ID = v.clause_id)
                          loop
                            if param.RESOUCE_ID is  not  null  then

                                  update   tta_collect_type_line tctl2 set
                                   standard_value   = param.standard_value,
                                   unit_value    =  param.unit_value,
                                   is_enable     =  param.is_enable ,
                                   is_default_value  = param.is_default_value,
                                   version_num   =  tctl2.version_num+1,
                                   last_updated_by  = P_USER_ID,
                                   last_update_date  = sysdate,
                                   last_update_login = P_USER_ID,
                                   delete_flag   = param.delete_flag

                                  where    tctl2.collect_type_id =  param.RESOUCE_ID ;

                            else
                                  insert  into tta_collect_type_line
                                  (
                                   collect_type_id
                                   ,clause_id
                                   ,collect_type
                                   ,standard_value
                                   ,unit_value
                                   ,is_enable
                                   ,is_default_value
                                   ,version_num
                                   ,creation_date
                                   ,created_by
                                   ,last_updated_by
                                   ,last_update_date
                                   ,last_update_login
                                   ,team_framework_id
                                   ,delete_flag
                                  ) values
                                  (
                                  seq_tta_collect_type_line.nextval,
                                  v.RESOUCE_ID,
                                  param.collect_type,
                                  param.standard_value,
                                  param.unit_value,
                                  param.is_enable,
                                  param.is_default_value,
                                  0,
                                  sysdate,
                                  P_USER_ID,
                                  P_USER_ID,
                                  sysdate,
                                  P_USER_ID,
                                  P_OLD__TEAM_FRAMEWORK_ID,
                                  param.delete_flag
                                  );


                            end if ;

                          end loop ;

        else

        select seq_tta_clause_tree.nextval into p_seq_tta_clause_tree_id  from  dual ;
        insert  into tta_clause_tree
        (
         clause_id,
         team_framework_id ,
         clause_code   ,
         clause_cn   ,
         clause_en   ,
         status   ,
         parent_id  ,
         pass_date    ,
         effective_date  ,
         is_leaf    ,
         is_hierarchy_show  ,
         payment_method   ,
         business_type    ,
         expression_value ,
         business_version ,
         order_no       ,
         created_by   ,
         last_updated_by  ,
         last_update_date ,
         creation_date     ,
         last_update_login ,
         version_num     ,
         delete_flag    ,
         code         ,
         p_code         ,
         is_global_variable  ,
         old_clause_id    ,
         old_order_no   ,
         is_global_tta   ,
         is_global_fee    ,
         expression_value_fee,
         expression_value_tta,
         term_category     ,
         global_value      ,
         global_value_tta   ,
         global_value_fee
        )values
        (P_seq_tta_clause_tree_id,
        P_OLD__TEAM_FRAMEWORK_ID,
         v.clause_code   ,
         v.clause_cn   ,
         v.clause_en   ,
         v.status   ,
         v.parent_id  ,
         v.pass_date    ,
         v.effective_date  ,
         v.is_leaf    ,
         v.is_hierarchy_show  ,
         v.payment_method   ,
         v.business_type    ,
         v.expression_value ,
        v.business_version ,
         v.order_no       ,
         P_USER_ID   ,
         P_USER_ID  ,
         sysdate ,
         sysdate     ,
         P_USER_ID ,
         0     ,
         v.delete_flag    ,
         v.code         ,
         v.p_code         ,
         v.is_global_variable  ,
         null    ,
         null   ,
         v.is_global_tta   ,
         v.is_global_fee    ,
         v.expression_value_fee,
         v.expression_value_tta,
         v.term_category     ,
         v.global_value      ,
         v.global_value_tta   ,
         v.global_value_fee

        );

                      for  param  in  (select * from tta_collect_type_line_h  tctlh where tctlh.CLAUSE_ID = v.clause_id)
                          loop

                                  insert  into tta_collect_type_line
                                  (
                                   collect_type_id
                                   ,clause_id
                                   ,collect_type
                                   ,standard_value
                                   ,unit_value
                                   ,is_enable
                                   ,is_default_value
                                   ,version_num
                                   ,creation_date
                                   ,created_by
                                   ,last_updated_by
                                   ,last_update_date
                                   ,last_update_login
                                   ,team_framework_id
                                   ,delete_flag
                                  ) values
                                  (
                                  seq_tta_collect_type_line.nextval,
                                  P_seq_tta_clause_tree_id,
                                  param.collect_type,
                                  param.standard_value,
                                  param.unit_value,
                                  param.is_enable,
                                  param.is_default_value,
                                  0,
                                  sysdate,
                                  P_USER_ID,
                                  P_USER_ID,
                                  sysdate,
                                  P_USER_ID,
                                  P_OLD__TEAM_FRAMEWORK_ID,
                                  param.delete_flag
                                  );



                          end loop ;

       end  if ;





      end loop ;

      --SAVEPOINT SP_FREEZE_MATERIAL_ONHAND;
      NULL;

    EXCEPTION
      WHEN OTHERS THEN
        --  ROLLBACK TO SP_FREEZE_MATERIAL_ONHAND;
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
    END;

  END P_TTA_CLAUSE_RETURN;

END PKG_TTA_PROPOSAL;


/



create or replace procedure pro_base_department is
begin
  -- 1.更新数据
  update base_department a
     set (a.department_code, a.department_name, a.department_full_name) =
         (select b.code, b.name_cn, b.name_en
            from tta_dept_in b
           where b.code = a.department_code)
   where exists
   (select 1 from tta_dept_in c where c.code = a.department_code);
  -- 2.有新增数据则新增
  insert into base_department
    (department_id,
     department_code,
     department_name,
     department_full_name,
     suffix,
     department_level,
     ou_id,
     parent_department_id,
     cost_center,
     department_seq,
     date_from,
     date_to,
     enable_flag,
     biz_line_type,
     channel,
     department_type,
     inventory_enable,
     creation_date,
     created_by,
     last_update_date,
     last_updated_by,
     last_update_login,
     delete_flag,
     version_num)
    select seq_base_department.nextval,
           t.code as department_code,
           t.name_cn as department_name,
           t.name_en as department_full_name,
           null as suffix,
           0 as department_level,
           261 as ou_id,
           0 as parent_department_id,
           null as cost_center,
           null as department_seq,
           sysdate date_from,
           null date_to,
           'Y' as enable_flag,
           null as biz_line_type,
           90 as channel,
           null as department_type,
           'Y' as inventory_enable,
           sysdate creation_date,
           -1 as created_by,
           sysdate as last_update_date,
           -1 as last_updated_by,
           -1 as last_update_login,
           0 as delete_flag,
           0 as version_num
      from tta_dept_in t
     where not exists
     (select 1 from base_department a where a.department_code = t.code);
  commit;
end;
/

create or replace procedure pro_base_job
    is
    begin
      insert into base_job(
        JOB_ID,
        JOB_CODE,
        JOB_NAME,
        "COMMENT",
        DATE_FROM,
        DATE_TO,
        ENABLE_FLAG,
        OU_ID,
        CREATION_DATE,
        CREATED_BY,
        LAST_UPDATE_DATE,
        LAST_UPDATED_BY,
        LAST_UPDATE_LOGIN,
        DELETE_FLAG,
        VERSION_NUM
       ) SELECT
        SEQ_BASE_JOB.NEXTVAL AS JOB_ID,
        A.POST_NAME AS JOB_CODE,
        A.POST_NAME AS JOB_NAME,
        NULL AS "COMMENT",
        SYSDATE DATE_FROM,
        NULL AS DATE_TO,
        'Y' ENABLE_FLAG,
        261 AS OU_ID,
        SYSDATE AS CREATION_DATE,
        -1 CREATED_BY,
        SYSDATE AS LAST_UPDATE_DATE,
        -1 AS LAST_UPDATED_BY,
        -1 AS LAST_UPDATE_LOGIN,
        0 AS  DELETE_FLAG,
        0 as VERSION_NUM
      FROM (select b.post_name from  tta_person_in b where b.post_name is not null group by b.post_name) a
      where not exists (select 1 from base_job b where b.job_code = a.post_name);
    commit;
    end;
/


CREATE OR REPLACE PROCEDURE pro_base_organization
as
begin
INSERT INTO base_organization
  (ORG_ID,
   PARENT_ORG_ID,
   ORG_CODE,
   ORG_NAME,
   TREE_TYPE,
   CHANNEL_TYPE,
   BUSINESS_TYPE,
   IS_DEP,
   ORG_TYPE,
   ORG_LEVEL,
   IS_LEAF,
   START_DATE,
   END_DATE,
   ENABLED,
   REMARK,
   ORG_PINYIN_NAME,
   ORG_SIMPLE_PINYIN_NAME,
   ORDER_NO,
   DELETE_FLAG,
   ORG_HIERARCHY_ID,
   ORG_EMAIL,
   SOURCE_SYSTEM_ID,
   CREATION_DATE,
   CREATED_BY,
   LAST_UPDATED_BY,
   LAST_UPDATE_DATE,
   VERSION_NUM,
   LEADER_ID,
   LAST_UPDATE_LOGIN,
   ORGANIZATION_ID,
   WORK_STATISTICS)
  select
         t.org_code as  ORG_ID,
         t.parent_org_code as PARENT_ORG_ID,
         t.org_code as ORG_CODE,
         t.org_name as ORG_NAME,
         '业务组织' as TREE_TYPE,
         10 as CHANNEL_TYPE,
         20 as BUSINESS_TYPE,
         'N' as IS_DEP,
         'ORG' as ORG_TYPE,
         null as ORG_LEVEL,
         null as IS_LEAF,
         SYSDATE AS START_DATE,
         NULL AS END_DATE,
         'Y' AS ENABLED,
         NULL AS REMARK,
         NULL AS ORG_PINYIN_NAME,
         NULL AS ORG_SIMPLE_PINYIN_NAME,
         NULL AS ORDER_NO,
         0 AS DELETE_FLAG,
         NULL AS ORG_HIERARCHY_ID,
         NULL AS ORG_EMAIL,
         NULL AS SOURCE_SYSTEM_ID,
         SYSDATE AS CREATION_DATE,
         -1 CREATED_BY,
         -1 AS LAST_UPDATED_BY,
         SYSDATE LAST_UPDATE_DATE,
         0 AS VERSION_NUM,
         NULL LEADER_ID,
         -1 LAST_UPDATE_LOGIN,
         NULL AS ORGANIZATION_ID,
         NULL AS WORK_STATISTICS
    from tta_org_in  t where not exists (select 1 from base_organization a where a.org_code = t.org_code);
--   /* where t.org_code in (822, 783, 674);*/
commit;

update base_organization a
   set (a.org_level, a.is_leaf) =
       (select t.ORG_LEVEL, t.IS_LEAF
          from (SELECT
                      LEVEL as ORG_LEVEL, 
                      connect_by_isleaf as IS_LEAF,
                      o.org_code
                   FROM tta_org_in o
                  START WITH o.parent_org_code IS NULL
                 CONNECT BY PRIOR o.org_code = o.parent_org_code) t
         where t.org_code = a.org_code);
commit;
end pro_base_organization;
/

create or replace procedure pro_base_person_assign
as
begin
insert into base_person_assign
(
     ASSIGN_ID,
     PERSON_ID,
     POSITION_ID,
     JOB_ID,
     DELETE_FLAG,
     DATE_FROM,
     DATE_TO,
     OU_ID,
     MGR_FLAG,
     PRIMARY_FLAG,
     ENABLE_FLAG,
     BUDGET_VIEW,
     BUDGET_MAINTAIN,
     CREATION_DATE,
     CREATED_BY,
     LAST_UPDATE_DATE,
     LAST_UPDATED_BY,
     LAST_UPDATE_LOGIN,
     VERSION_NUM
) select
     seq_base_person_assign.nextval ASSIGN_ID,
     a.person_id as PERSON_ID,
     b.position_id as POSITION_ID,
     b.job_id as JOB_ID,
     0 as DELETE_FLAG,
     sysdate as DATE_FROM,
     null as DATE_TO,
     261 as OU_ID,
     'N' as MGR_FLAG,
     'N' as PRIMARY_FLAG,
     'Y' as ENABLE_FLAG,
     1 AS BUDGET_VIEW,
     'N' AS BUDGET_MAINTAIN,
     sysdate as CREATION_DATE,
     -1 as CREATED_BY,
     sysdate as LAST_UPDATE_DATE,
     -1 as LAST_UPDATED_BY,
     -1 as LAST_UPDATE_LOGIN,
     0 as VERSION_NUM
   from (
          select
             x.person_id,
             x.dept_no || '.' || x.post_name  as dept_no_post_name
           from base_person x
           group by x.dept_no, x.post_name, x.person_id
        ) a
   INNER JOIN base_position b on b.POSITION_CODE = a.dept_no_post_name
   where not exists (select 1 from base_person_assign sg where sg.person_id = a.person_id
    and sg.person_id = b.position_id
    and sg.job_id = b.job_id
    );
   commit;
end;
/

create or replace procedure pro_base_position
is
begin
INSERT INTO base_position(
     POSITION_ID,
     DEPARTMENT_ID,
     JOB_ID,
     POSITION_CODE,
     POSITION_NAME,
     DATE_FROM,
     DATE_TO,
     ENABLE_FLAG,
     BIZ_LINE_TYPE,
     CHANNEL,
     OU_ID,
     CREATION_DATE,
     CREATED_BY,
     LAST_UPDATE_DATE,
     LAST_UPDATED_BY,
     LAST_UPDATE_LOGIN,
     DELETE_FLAG,
     VERSION_NUM
  )
  select
         seq_base_position.nextval as POSITION_ID,
         d.department_id AS DEPARTMENT_ID,
         b.job_id AS JOB_ID,
         a.dept_no_post_name AS POSITION_CODE,
         (d.department_name || '.' || a.post_name) AS POSITION_NAME,
         sysdate AS DATE_FROM,
         null AS DATE_TO,
         'Y' AS ENABLE_FLAG,
         NULL AS BIZ_LINE_TYPE,
         NULL AS CHANNEL,
         261 AS OU_ID,
         sysdate AS CREATION_DATE,
         -1 AS CREATED_BY,
         sysdate AS LAST_UPDATE_DATE,
         -1 AS LAST_UPDATED_BY,
         -1 AS LAST_UPDATE_LOGIN,
         0 AS DELETE_FLAG,
         0 AS VERSION_NUM
    from (select dept_no,
                 post_name,
                 dept_no || '.' || post_name as dept_no_post_name
            from base_person
           group by dept_no, post_name) a
   inner join base_job b
      on a.post_name = b.job_code
   inner join base_department d
      on d.department_code = a.dept_no
      where not exists (select 1 from base_position t where t.department_id = d.department_id);
      commit;
end;
/

create or replace procedure pro_person_in
is
begin

  update base_person a
   set (a.area,
        a.email,
        a.grand,
        a.market,
        a.dept_no,
        a.org_code,
        a.post_name,
        a.person_name,
        a.person_name_en) =
       (select b.area,
               b.mail,
               b.grand,
               b.market,
               b.dept_no,
               b.org_code,
               b.post_name,
               b.name,
               b.name_en
          from tta_person_in b
         where b.code = a.employee_number)
 where exists
 (select 1 from tta_person_in b where b.code = a.employee_number);

insert into base_person  (
       GRAND,
       PERSON_NAME_EN,
       DEPT_NO,
       POST_NAME,
       AREA,
       MARKET,
       ORG_CODE,
       PERSON_ID,
       EMPLOYEE_NUMBER,
       PERSON_NAME,
       PERSON_TYPE,
       SEX,
       BIRTH_DAY,
       CARD_NO,
       ENABLED,
       TEL_PHONE,
       MOBILE_PHONE,
       EMAIL,
       POSTAL_ADDRESS,
       POSTCODE,
       SERVICE_STORE_ID,
       SOURCE_SYSTEM_ID,
       CREATION_DATE,
       CREATED_BY,
       LAST_UPDATED_BY,
       LAST_UPDATE_DATE,
       VERSION_NUM,
       LAST_UPDATE_LOGIN,
       SOURCE_CODE,
       SOURCE_ID)
  select
       t.grand,
       t.name_en,
       t.dept_no,
       t.post_name,
       t.area,
       t.market,
       t.org_code,
       seq_base_person.nextval,
       t.code,
       t.name,
       'IN' AS PERSON_TYPE,
       null as SEX,
       null as BIRTH_DAY,
       null as CARD_NO,
       'Y' AS  ENABLED,
       null as  TEL_PHONE,
       null as MOBILE_PHONE,
       t.mail,
       null as POSTAL_ADDRESS,
       null as POSTCODE,
       null as SERVICE_STORE_ID,
       null as SOURCE_SYSTEM_ID,
       sysdate as CREATION_DATE,
       -1 as CREATED_BY,
       -1 as LAST_UPDATED_BY,
       sysdate as LAST_UPDATE_DATE,
       0 as VERSION_NUM,
       -1 as LAST_UPDATE_LOGIN,
       'BPM' as SOURCE_CODE,
       null as SOURCE_ID
  from tta_person_in t where not exists (select 1 from  base_person b where b.employee_number=t.code);
  commit;
end;
/

create or replace procedure pro_base_person_job
    is
  begin
      PRO_PERSON_IN();
      PRO_BASE_JOB();
  end;
/


create or replace procedure pro_base_dept_position_assign
is
begin
   pro_base_department();
   pro_base_position();
   pro_base_person_assign();
end pro_base_dept_position_assign;
/



create or replace procedure MY_Pro_SearchKeyWord is
  v_sql VARCHAR2(4000);
  v_tb_column VARCHAR2(4000);
  v_cnt NUMBER(18,0);
  cursor cur is SELECT 'SELECT '''||'"'||t1.table_name||'"."'||t1.Column_Name||'"'||''''||' as col_name, NVL(COUNT(t."'||t1.Column_Name||'"),0) as cnt FROM "'||
         t1.table_name||'" t WHERE t."'||t1.column_name||'" like ''%attTaermsFrameworkHeadHService%''' AS str
    FROM cols t1 left join user_col_comments t2
      on t1.Table_name=t2.Table_name and t1.Column_Name=t2.Column_Name
    left join user_tab_comments t3
      on t1.Table_name=t3.Table_name
   WHERE NOT EXISTS ( SELECT t4.Object_Name FROM User_objects t4
               WHERE t4.Object_Type='TABLE'
                 AND t4.Temporary='Y'
                 AND t4.Object_Name=t1.Table_Name )
     AND (t1.Data_Type='CHAR' or t1.Data_Type='VARCHAR2' or t1.Data_Type='VARCHAR')
   ORDER BY t1.Table_Name, t1.Column_ID;

BEGIN
  FOR i IN cur LOOP
    v_sql := i.str; -- 获取将要执行的SQL语句；
    EXECUTE IMMEDIATE v_sql INTO v_tb_COLUMN, v_cnt;
    IF v_cnt > 0 THEN
      dbms_output.put_line('表：'||substr(v_tb_column,1,instr(v_tb_column,'.',1,1)-1)||' 列：'||substr(v_tb_column,instr(v_tb_column,'.',1,1)+1)||
                           '有 '||to_char(v_cnt)|| '条记录含有字串"关键字" ');
    END IF;
  END LOOP;
EXCEPTION WHEN OTHERS THEN
BEGIN
  dbms_output.put_line(v_sql);
  dbms_output.put_line(v_tb_column);
END;
end MY_Pro_SearchKeyWord;
/


create or replace procedure P_TTA_PROPOSAL_BUILD_BILL_COPY(P_ORDER_ID    NUMBER,
                                P_USER_ID     NUMBER,
                                P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                                P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                                P_PROPOSAL_ID  OUT VARCHAR2) IS
     ------###############功能:proposal生成页面中的变更或复制###################

     V_COUNT               NUMBER; --计数器
     V_TTA_PROPOSAL_HEADER  TTA_PROPOSAL_HEADER%ROWTYPE; --PROPOSAL主信息
     V_TTA_BRANDPLN_HEADER  TTA_BRANDPLN_HEADER%ROWTYPE; --品牌计划头信息
     V_TTA_BRANDPLN_LINE  TTA_BRANDPLN_LINE%ROWTYPE; -- 品牌计划行信息
     V_TTA_PROPOSAL_TERMS_HEADER  TTA_PROPOSAL_TERMS_HEADER%ROWTYPE; --TTA TERMS 主信息
     V_TTA_PROPOSAL_TERMS_LINE    TTA_PROPOSAL_TERMS_LINE%ROWTYPE;   -- TTA TERMS 行表
     v_tta_newbreed_extend_header  tta_newbreed_extend_header%ROWTYPE; -- 新品种宣传推广服务费用头表
     v_tta_contract_line           tta_contract_line%Rowtype;  --合同拆分行表
     v_tta_brandpln_header_id_s  NUMBER; --原来的品牌头id
     v_tta_brandpln_header_id      NUMBER; --品牌头id
     v_tta_brandpln_line           NUMBER;
     v_tta_p_terms_h_sco_id        number; --TTA TERMS 源 头信息id
     v_tta_p_terms_h_next_id       number; -- TTA TERMS 头信息id
     v_tta_nbreed_extend_h_sid     number; --新品种宣传推广服务费用头表源id

   BEGIN
     P_ISSUCCESS := 1;
     P_ERR_MESSAGE := '执行成功';
     BEGIN
        --判断是否是审核状态的数据
        SELECT COUNT(1)
          INTO V_COUNT
          FROM TTA_PROPOSAL_HEADER TT
         WHERE TT.PROPOSAL_ID = P_ORDER_ID
           AND TT.STATUS = 'C';
        IF V_COUNT = 0 THEN
          P_ISSUCCESS   := -1;
          P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']状态为审批通过的单据';
          RETURN;
        END IF;

        --复制数据
         SELECT TT.*
          INTO V_TTA_PROPOSAL_HEADER
          FROM TTA_PROPOSAL_HEADER TT
         WHERE TT.PROPOSAL_ID = P_ORDER_ID
           AND TT.STATUS = 'C';

         --修改数据
        P_PROPOSAL_ID                       := SEQ_TTA_PROPOSAL_HEADER.NEXTVAL();
        V_TTA_PROPOSAL_HEADER.PROPOSAL_ID   := P_PROPOSAL_ID;
        V_TTA_PROPOSAL_HEADER.ALERT_BY      := P_USER_ID; --变更人
        V_TTA_PROPOSAL_HEADER.ALERT_DATE    := SYSDATE;

        V_TTA_PROPOSAL_HEADER.LAST_UPDATED_BY  := P_USER_ID;
        V_TTA_PROPOSAL_HEADER.LAST_UPDATE_DATE := SYSDATE;
        V_TTA_PROPOSAL_HEADER.STATUS           := 'A';
        V_TTA_PROPOSAL_HEADER.APPROVE_DATE     := NULL; --设置审核时间为null
        V_TTA_PROPOSAL_HEADER.IS_CHANGE    := 'Y';
        V_TTA_PROPOSAL_HEADER.VERSION_CODE := V_TTA_PROPOSAL_HEADER.VERSION_CODE + 1;
        V_TTA_PROPOSAL_HEADER.VERSION_STATUS := '1'; --生效
        V_TTA_PROPOSAL_HEADER.ORIGIN_PROPOSAL_ID := P_ORDER_ID;

         --基于原始数据,插入新的数据
         INSERT INTO TTA_PROPOSAL_HEADER VALUES V_TTA_PROPOSAL_HEADER;

         --原始数据 版本状态失效
         UPDATE TTA_PROPOSAL_HEADER T
            SET T.VERSION_STATUS = '0'
          WHERE T.PROPOSAL_ID = P_ORDER_ID;

          --插入与proposal主信息相关的业务数据
          --插入品牌计划头和品牌计划行信息
          for temp in (
            SELECT TBH.*
               FROM TTA_BRANDPLN_HEADER TBH
              WHERE TBH.PROPOSAL_ID = P_ORDER_ID
                    ) loop
           begin
             v_tta_brandpln_header_id_s := temp.brandpln_h_id;
             v_tta_brandpln_header_id :=seq_tta_brandpln_header.nextval();
             temp.brandpln_h_id := v_tta_brandpln_header_id;
             temp.last_updated_by := P_USER_ID;
             temp.last_update_date :=sysdate;
             temp.proposal_id := P_PROPOSAL_ID;

             insert into tta_brandpln_header values temp;

             for templ in (
               select tbl.* from tta_brandpln_line tbl where tbl.brandpln_h_id = v_tta_brandpln_header_id_s
               ) loop

               begin
                 templ.brandpln_l_id := seq_tta_brandpln_line.nextval();
                 templ.brandpln_h_id :=v_tta_brandpln_header_id; --新的品牌头id
                 templ.proposal_id := P_PROPOSAL_ID;
                 templ.last_updated_by :=P_USER_ID;
                 templ.last_update_date :=sysdate;
                 insert into tta_brandpln_line values templ;

                 end;
             end loop;
             end;
           end loop;

          V_COUNT := 0;
          
          select  count(1) into V_COUNT from tta_proposal_terms_header tpth
                  where tpth.proposal_id = P_ORDER_ID; 
           --插入TTA TERMS数据
          if V_COUNT > 0 then 
           select tpth.* into V_TTA_PROPOSAL_TERMS_HEADER from tta_proposal_terms_header tpth
                  where tpth.proposal_id = P_ORDER_ID; 
                

           --赋值
           v_tta_p_terms_h_sco_id := V_TTA_PROPOSAL_TERMS_HEADER.terms_h_id; --原id,查询TTA TERMS明细数据
           v_tta_p_terms_h_next_id := seq_tta_proposal_terms_header.nextval();
           V_TTA_PROPOSAL_TERMS_HEADER.terms_h_id  := v_tta_p_terms_h_next_id;

           V_TTA_PROPOSAL_TERMS_HEADER.proposal_id := P_PROPOSAL_ID;
           V_TTA_PROPOSAL_TERMS_HEADER.terms_version := to_number(nvl(V_TTA_PROPOSAL_TERMS_HEADER.terms_version,0)) + 1;
           V_TTA_PROPOSAL_TERMS_HEADER.last_updated_by := P_USER_ID;
           V_TTA_PROPOSAL_TERMS_HEADER.last_update_date := sysdate;

           --插入IIA TERMS头信息
           INSERT INTO tta_proposal_terms_header VALUES V_TTA_PROPOSAL_TERMS_HEADER;

           for terms in(
               select tptl.* from TTA_PROPOSAL_TERMS_LINE tptl where tptl.terms_h_id = v_tta_p_terms_h_sco_id order by tptl.order_no
             ) loop
             begin
               --插入IIA TERMS明细信息
                  terms.terms_l_id := seq_tta_proposal_terms_line.nextval();
                  terms.proposal_id :=P_PROPOSAL_ID;
                  terms.terms_h_id := v_tta_p_terms_h_next_id;
                  terms.last_updated_by := P_USER_ID;
                  terms.last_update_date := sysdate;
                  terms.business_version := to_number(nvl(terms.business_version,0)) + 1;

                  insert into tta_proposal_terms_line values terms;

               end;
             end loop;
             
            end if;  
            
            V_COUNT := 0;
            select count(1)
                into V_COUNT
                from tta_newbreed_extend_header tneh
               where tneh.proposal_id = P_ORDER_ID;
               
            if V_COUNT > 0 then
             --插入 新品种宣传推广服务费用头表信息
              select tneh.*
                into v_tta_newbreed_extend_header
                from tta_newbreed_extend_header tneh
               where tneh.proposal_id = P_ORDER_ID;
  
              --传参
              v_tta_nbreed_extend_h_sid := v_tta_newbreed_extend_header.newbreed_extend_h_id; --源id
              v_tta_newbreed_extend_header.newbreed_extend_h_id := seq_tta_newbreed_extend_header.nextval();
              v_tta_newbreed_extend_header.proposal_id := P_PROPOSAL_ID;
              v_tta_newbreed_extend_header.last_updated_by := P_USER_ID;
              v_tta_newbreed_extend_header.last_update_date := sysdate;

              insert into tta_newbreed_extend_header VALUES v_tta_newbreed_extend_header;

              --插入新品种宣传推广服务费用行表 信息
             for newbreedl in (
                 select tnel.*
                   from tta_newbreed_extend_line tnel
                  where tnel.newbreed_extend_h_id =
                        v_tta_nbreed_extend_h_sid
               ) loop
               begin
                 newbreedl.newbreed_extend_l_id := seq_tta_newbreed_extend_line.nextval();
                 newbreedl.newbreed_extend_h_id := v_tta_newbreed_extend_header.newbreed_extend_h_id;
                 newbreedl.last_updated_by := P_USER_ID;
                 newbreedl.last_update_date := sysdate;
                 newbreedl.last_update_login := P_USER_ID;

                 insert into tta_newbreed_extend_line values newbreedl;
                 end;
             end loop;
             
             end if;

             ---插入合同行信息
             for contracts in (
               select tcl.* from tta_contract_line tcl where tcl.proposal_id  = P_ORDER_ID order by tcl.order_no asc
               ) loop
               begin
                 contracts.contract_l_id := seq_tta_contract_line.nextval();
                 contracts.proposal_id  := P_PROPOSAL_ID;
                 contracts.last_updated_by := P_USER_ID;
                 contracts.last_update_date := sysdate;
                 contracts.last_update_login := P_USER_ID;

                 insert into tta_contract_line values contracts;
                 end;
             end loop;
            -- commit;
     EXCEPTION
      WHEN OTHERS THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
       -- rollback;
     END;
 END P_TTA_PROPOSAL_BUILD_BILL_COPY;
/


create or replace procedure p_tta_proposal_build_bill_cut (
                               P_ORDER_ID  in  NUMBER,
                               P_USER_ID   in  NUMBER,
                               P_ISSUCCESS   OUT NUMBER, --返回成功状态 -1：处理失败，1：成功；0：警告
                               P_ERR_MESSAGE OUT VARCHAR2, --返回信息
                               P_PROPOSAL_ID OUT VARCHAR2) is
--########################### proposal生成管理: 切换上一版本 ##########################################                          
    V_COUNT               NUMBER; --计数器
    V_TTA_PROPOSAL_HEADER TTA_PROPOSAL_HEADER%ROWTYPE;
    v_origin_proposal_id           NUMBER; --源proposalId

   BEGIN
      P_ISSUCCESS := 1;
      P_ERR_MESSAGE := '执行成功';
      BEGIN

        BEGIN
        SELECT TT.origin_proposal_id
          INTO v_origin_proposal_id
          FROM TTA_PROPOSAL_HEADER TT
         WHERE TT.PROPOSAL_ID = P_ORDER_ID;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
           P_ISSUCCESS   := -1;
           P_ERR_MESSAGE := '没有找到ID为[' || P_ORDER_ID || ']的单据';
           RETURN;
          WHEN TOO_MANY_ROWS THEN
           P_ISSUCCESS   := -1;
           P_ERR_MESSAGE := '找到多条ID为[' || P_ORDER_ID || ']的单据';
           RETURN;
        END;

       if v_origin_proposal_id is null then
         P_ISSUCCESS   := -1;
         P_ERR_MESSAGE := '未找到上一版本';
         RETURN;
       end if;

       -- proposal 主信息
       --恢复上一版本的信息
       UPDATE TTA_PROPOSAL_HEADER T
         SET
           T.VERSION_STATUS = '1' --生效
       WHERE T.PROPOSAL_ID = v_origin_proposal_id;

      --删除proposal 主信息当前版本信息 
      delete from TTA_PROPOSAL_HEADER tph where tph.proposal_id = P_ORDER_ID;
       
      --删除当前选择的proposalId所对应的品牌计划头信息和品牌计划明细信息
     --明细表
       DELETE FROM TTA_BRANDPLN_LINE TBL WHERE TBL.BRANDPLN_H_ID in(
              select tbh.brandpln_h_id from TTA_BRANDPLN_HEADER tbh where tbh.proposal_id =P_ORDER_ID 
       ) and TBL.Proposal_Id =  P_ORDER_ID;
      
     --头表
       DELETE FROM TTA_BRANDPLN_HEADER TBH
              WHERE TBH.PROPOSAL_ID = P_ORDER_ID;
              
       --删除 TTA TERMS
       delete from TTA_PROPOSAL_TERMS_LINE tptl where tptl.terms_h_id in(
              select tpth.terms_h_id from tta_proposal_terms_header tpth where tpth.proposal_id = P_ORDER_ID
       ) and tptl.proposal_id = P_ORDER_ID;
       
       delete from tta_proposal_terms_header tpth where tpth.proposal_id = P_ORDER_ID;

       --删除新品种宣传推广服务费用信息
       delete from tta_newbreed_extend_header tneh
               where tneh.proposal_id = P_ORDER_ID;
               
       delete from tta_newbreed_extend_line tnel where tnel.newbreed_extend_h_id = (
             select tneh.newbreed_extend_h_id from tta_newbreed_extend_header tneh where tneh.proposal_id = P_ORDER_ID
       );  
       
       --删除合同信息
       delete from tta_contract_line tcl where tcl.proposal_id  = P_ORDER_ID;      
       
       --赋值源proposalId给调用方
      P_PROPOSAL_ID := v_origin_proposal_id;

      commit;
    EXCEPTION
      WHEN OTHERS THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
        rollback;
    END;
 END p_tta_proposal_build_bill_cut;
/

