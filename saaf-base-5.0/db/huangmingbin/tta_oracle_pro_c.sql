---------------2020.3.24备份 Proposal变更--------------------------------
create or replace procedure "P_TTA_PROPOSAL_BUILD_BILL_COPY"(P_ORDER_ID    NUMBER,
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
     --v_tta_test_question_header    tta_test_question_header%rowtype; --问卷调查头表
     v_tta_contract_line           tta_contract_line%Rowtype;  --合同拆分行表

     v_tta_brandpln_header_id_s  NUMBER; --原来的品牌头id
     v_tta_brandpln_header_id      NUMBER; --品牌头id
     v_tta_brandpln_line           NUMBER;
     v_tta_p_terms_h_sco_id        number; --TTA TERMS 源 头信息id
     v_tta_p_terms_h_next_id       number; -- TTA TERMS 头信息id
     v_tta_nbreed_extend_h_sid     number; --新品种宣传推广服务费用头表源id
     v_q_header_id_s  number; -- 问卷调查头信息原id

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

            select count(1) into V_COUNT from tta_dept_fee tdf where tdf.proposal_id = P_ORDER_ID;

            --插入部门协定标准模块相关
            if V_COUNT > 0 then
              for deptTemp in(
                select tdf.* from tta_dept_fee tdf where tdf.proposal_id = P_ORDER_ID
                ) loop
                  begin
                    deptTemp.DEPTFEE_LINE_ID := seq_tta_dept_fee.nextval();
                    deptTemp.PROPOSAL_ID := P_PROPOSAL_ID;
                    deptTemp.last_updated_by := P_USER_ID;
                    deptTemp.last_update_date := sysdate;
                    deptTemp.last_update_login := P_USER_ID;

                    insert into tta_dept_fee values deptTemp;
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

             ----插入问卷调查相关的业务信息 start
              V_COUNT := 0;
              select count(1) into V_COUNT from tta_test_question_header ttqh where ttqh.proposal_id= P_ORDER_ID;
             if V_COUNT >0 then
            --select ttqh.*  into v_tta_test_question_header from tta_test_question_header
            --      ttqh where ttqh.proposal_id= P_ORDER_ID;

               for  testquestionheader in(
                    select ttqh.* from tta_test_question_header ttqh where ttqh.proposal_id= P_ORDER_ID
                 ) loop
                       begin
                         v_q_header_id_s := testquestionheader.q_header_id; -- 问卷调查头表源id
                         testquestionheader.q_header_id := seq_tta_test_question_header.nextval();
                         testquestionheader.proposal_id := P_PROPOSAL_ID;
                         testquestionheader.last_updated_by := P_USER_ID;
                         testquestionheader.last_update_login := P_USER_ID;
                         testquestionheader.last_update_date := sysdate;

                       --插入问卷调查头表
                       insert into tta_test_question_header VALUES testquestionheader;

                        --插入问卷调查行表
                       for questionline in (
                           select ttqcl.*
                             from tta_test_question_choice_line ttqcl
                            where ttqcl.q_header_id = v_q_header_id_s
                         ) loop
                         begin
                           questionline.choice_line_id := seq_tta_question_choice_line.nextval();
                           questionline.q_header_id := testquestionheader.q_header_id;
                           questionline.proposal_id := P_PROPOSAL_ID;
                           questionline.last_updated_by := P_USER_ID;
                           questionline.last_update_date := sysdate;
                           questionline.last_update_login := P_USER_ID;

                           insert into tta_test_question_choice_line values questionline;
                           end;
                          end loop;
                         end;
                 end loop;
              --v_q_header_id_s := v_tta_test_question_header.q_header_id; --源id
              --v_tta_test_question_header.q_header_id := seq_tta_test_question_header.nextval();
              --v_tta_test_question_header.proposal_id := P_PROPOSAL_ID;
              --v_tta_test_question_header.last_updated_by := P_USER_ID;
              --v_tta_test_question_header.last_update_login := P_USER_ID;
              --v_tta_test_question_header.last_update_date := sysdate;

              --插入问卷调查头表
              --insert into tta_test_question_header VALUES v_tta_test_question_header;

               for newmapdetail in (
                   select tqnd.* from tta_question_new_map_detail tqnd
                          where tqnd.proposal_id = P_ORDER_ID
                 ) loop
                   begin
                     newmapdetail.map_detail_id := seq_tta_question_new_map_detail.nextval();
                     newmapdetail.proposal_id := P_PROPOSAL_ID;
                     newmapdetail.last_updated_by := P_USER_ID;
                     newmapdetail.last_update_date := sysdate;
                     newmapdetail.last_update_login := P_USER_ID;

                     insert into tta_question_new_map_detail values newmapdetail;
                     end;

                 end loop;
               end if;
             ----插入问卷调查相关的业务信息 end

             ---插入合同行信息
             V_COUNT := 0;
             select count(1) into V_COUNT from tta_contract_line tcl where tcl.proposal_id  = P_ORDER_ID;
             if  V_COUNT > 0 then
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
             end if;

             ---插入合同行历史信息
             V_COUNT := 0;
             select count(1) into V_COUNT from tta_contract_line_h tclh where tclh.proposal_id  = P_ORDER_ID;
             if  V_COUNT > 0 then
             for contracth in (
               select tclh.* from tta_contract_line_h tclh where tclh.proposal_id  = P_ORDER_ID order by tclh.order_no asc
               ) loop
               begin
                 contracth.contract_lh_id := seq_tta_contract_line_h.nextval();
                 contracth.proposal_id  := P_PROPOSAL_ID;
                 contracth.last_updated_by := P_USER_ID;
                 contracth.last_update_date := sysdate;
                 contracth.last_update_login := P_USER_ID;

                 insert into tta_contract_line_h values contracth;
                 end;
             end loop;
             end if;

             V_COUNT := 0;
             select count(1) into V_COUNT from  tta_analysis_edit_data taed where taed.proposalid = P_ORDER_ID;
             if V_COUNT > 0 then
               for analysisdata in (
                 select taed.* from  tta_analysis_edit_data taed where taed.proposalid = P_ORDER_ID
                 ) loop
                  begin
                    analysisdata.id := seq_tta_analysis_edit_data.nextval();
                    analysisdata.proposalid  := P_PROPOSAL_ID;
                    analysisdata.last_updated_by := P_USER_ID;
                    analysisdata.last_update_date := sysdate;
                    analysisdata.last_update_login := P_USER_ID;

                    insert into tta_analysis_edit_data values analysisdata;
                    end;
                end loop;
             end if;

            -- 插入Terms Analysis的表数据
             V_COUNT := 0;
             select count(1) into V_COUNT from tta_analysis_line tal where tal.proposal_id = P_ORDER_ID;
             if V_COUNT > 0 then
               for analysisline in (
                 select tal.* from  tta_analysis_line tal where tal.proposal_id = P_ORDER_ID
                 ) loop
                  begin
                    analysisline.id := seq_tta_analysis_line.nextval();
                    analysisline.proposal_id  := P_PROPOSAL_ID;
                    analysisline.last_updated_by := P_USER_ID;
                    analysisline.last_update_date := sysdate;
                    analysisline.last_update_login := P_USER_ID;

                    insert into tta_analysis_line values analysisline;
                    end;
                 end loop;
             end if;


            -- commit;
     EXCEPTION
      WHEN OTHERS THEN
        P_ISSUCCESS   := -1;
        P_ERR_MESSAGE := '执行异常，异常信息：' || SQLERRM;
       -- rollback;
     END;
 END P_TTA_PROPOSAL_BUILD_BILL_COPY;
---------------2020.3.24备份-------------------------------------
--------------2020.3.24 Prposal切换上一版------------------
create or replace procedure "P_TTA_PROPOSAL_BUILD_BILL_CUT" (
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
           T.VERSION_STATUS = '1', --生效
           T.Last_Update_Date = sysdate
       WHERE T.PROPOSAL_ID = v_origin_proposal_id;

      --删除proposal 主信息当前版本信息
      delete from TTA_PROPOSAL_HEADER tph where tph.proposal_id = P_ORDER_ID;

      --删除当前选择的proposalId所对应的品牌计划头信息和品牌计划明细信息
     --明细表
       DELETE FROM TTA_BRANDPLN_LINE TBL WHERE TBL.BRANDPLN_H_ID in(
              select tbh.brandpln_h_id from TTA_BRANDPLN_HEADER tbh where tbh.proposal_id =P_ORDER_ID
       ) and TBL.Proposal_Id =  P_ORDER_ID;

     --品牌头表
       DELETE FROM TTA_BRANDPLN_HEADER TBH
              WHERE TBH.PROPOSAL_ID = P_ORDER_ID;

       --删除 TTA TERMS
       delete from TTA_PROPOSAL_TERMS_LINE tptl where tptl.terms_h_id in(
              select tpth.terms_h_id from tta_proposal_terms_header tpth where tpth.proposal_id = P_ORDER_ID
       ) and tptl.proposal_id = P_ORDER_ID;

       delete from tta_proposal_terms_header tpth where tpth.proposal_id = P_ORDER_ID;

       --删除部门协定信息
       delete from tta_dept_fee tdf where tdf.proposal_id = P_ORDER_ID;

       --删除新品种宣传推广服务费用信息
       delete from tta_newbreed_extend_line tnel where tnel.newbreed_extend_h_id = (
             select tneh.newbreed_extend_h_id from tta_newbreed_extend_header tneh where tneh.proposal_id = P_ORDER_ID
       );

        delete from tta_newbreed_extend_header tneh
               where tneh.proposal_id = P_ORDER_ID;

       --删除问卷相关的信息
        delete from  tta_test_question_choice_line  ttqcl where ttqcl.q_header_id in(
          select ttqh.q_header_id from tta_test_question_header
                    ttqh where ttqh.proposal_id= P_ORDER_ID
        );

      delete  from tta_question_new_map_detail tqnd
                          where tqnd.proposal_id = P_ORDER_ID;

      delete from tta_test_question_header
                  ttqh where ttqh.proposal_id= P_ORDER_ID;

       --删除合同信息
       delete from tta_contract_line tcl where tcl.proposal_id  = P_ORDER_ID;

       --删除合同行历史信息
       delete from tta_contract_line_h tclh where tclh.proposal_id = P_ORDER_ID;

       -- 删除TTA analysis信息
       --delete from  tta_analysis_edit_data taed where taed.proposalid = P_ORDER_ID;
       delete from  tta_analysis_line tal where tal.proposal_id = P_ORDER_ID;

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

--------------2020.3.24 Prposal切换上一版------------------