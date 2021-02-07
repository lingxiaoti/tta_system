create or replace function get_FillZero
 (
   P_String IN VARCHAR2,--传入的字串
   P_LR IN VARCHAR2,--L为左、R为右
   P_Length IN int--总长度
 ) return varchar2 is
   v_Temp varchar2(1000):='';
   v_Name varchar2(1000):='';
begin
  --去除小数点
  if P_String is not null then
    v_Name:=replace(P_String,'.','');
  end if;

  if length(v_Name)>=P_Length then
    v_Temp:=v_Name;
  else
    if P_LR='L' then--左补零
      v_Temp:=replace(lpad(v_Name,P_Length+(lengthb(P_String)-length(P_String))),' ','0');
    else
      v_Temp:=replace(RPAD(v_Name,P_Length+(lengthb(P_String)-length(P_String))),' ','0');
    end if;
  end if;

  return(v_Temp);

end get_FillZero;


CREATE OR REPLACE FUNCTION get_clause_analysis( is_global in varchar2,text in varchar2)
RETURN varchar2
as

      i           number ; --计数
      j           number ;
      e           number ;
      e2           number ;
      old_j       number ;
      sub_num     number ; --取出来的数字
      v_param     varchar2(4000) ;
      v_param2    varchar2(4000) ;
      p_clause_cn varchar2(4000) ;
      p_business_type   tta_clause_tree.business_type%type;
      p_p_code          tta_clause_tree.p_code%type;
      p_team_framework_id  number ;
      v_tta_in     varchar2(4000) ;

  BEGIN

        i := -1;
        j := -1;
        e :=1;
        e2 :=1;
        old_j :=0;
        sub_num :=0;
        v_param := text ;
        v_param2:='';
        p_clause_cn:='';
        v_tta_in:='';

      if nvl(is_global,'-1') !=0 or  nvl(text,'-1') = '-1'   then
        return   '';
      end   if ;

      --获取局部值
          LOOP

              i := instr(v_param,'{',1,e);
              j := instr(v_param,'}',1,e);
              DBMS_OUTPUT.put_line(i);
              DBMS_OUTPUT.put_line(j);

              if i =0 or e>500  then
                if e=1  then
                  v_param2 :=v_param;
                end   if ;

                 if e != 1 then
                   v_param2 := v_param2 || substr(v_param,old_j+1,lengthb(v_param)-old_j);
                   end if ;

                 exit;
              end if;

              v_param2 := v_param2 || substr(v_param,old_j+1,i-old_j-1);
              old_j :=j;
              sub_num := to_number( substr(v_param,i+1,j-i-1) );

              --获取往年的 TTA
                e2 :=1;
                v_tta_in :='';
                loop
                  if  e2=1 then
                      select
                        tc.clause_cn,
                        tc.business_type,
                        tc.p_code,
                        tc.team_framework_id
                      into
                        p_clause_cn,
                        p_business_type,
                        p_p_code,
                        p_team_framework_id
                      from  tta_clause_tree  tc where tc.clause_id = sub_num;
                    else
                        select
                        tc.clause_cn,
                        tc.business_type,
                        tc.p_code,
                        tc.team_framework_id
                      into
                        p_clause_cn,
                        p_business_type,
                        p_p_code,
                        p_team_framework_id
                      from  tta_clause_tree  tc where tc.team_framework_id = p_team_framework_id and tc.code = p_p_code;

                  end  if ;
                  v_tta_in :=  p_clause_cn || '→' ||  v_tta_in ;
                  if nvl(p_business_type,'01') = '01' or e2>500 then
                    exit;
                  end  if ;

                  e2 := e2 + 1;
                end   loop ;

              v_tta_in := substrb(v_tta_in,1,lengthb(v_tta_in)-3) ;
              v_tta_in :='【' ||  v_tta_in ||'】' ;

              v_param2 := v_param2 || v_tta_in;

              e := e + 1;
        END LOOP;

    return  v_param2 ;

 END get_clause_analysis;

CREATE OR REPLACE FUNCTION get_clause_analysis_h( is_global in varchar2,text in varchar2)
RETURN varchar2
as

      i           number ; --计数
      j           number ;
      e           number ;
      e2           number ;
      old_j       number ;
      sub_num     number ; --取出来的数字
      v_param     varchar2(4000) ;
      v_param2    varchar2(4000) ;
      p_clause_cn varchar2(4000) ;
      p_business_type   tta_clause_tree.business_type%type;
      p_p_code          tta_clause_tree.p_code%type;
      p_team_framework_id  number ;
      v_tta_in     varchar2(4000) ;

  BEGIN

        i := -1;
        j := -1;
        e :=1;
        e2 :=1;
        old_j :=0;
        sub_num :=0;
        v_param := text ;
        v_param2:='';
        p_clause_cn:='';
        v_tta_in:='';

      if nvl(is_global,'-1') !=0 or  nvl(text,'-1') = '-1'   then
        return   '';
      end   if ;

      --获取局部值
          LOOP

              i := instr(v_param,'{',1,e);
              j := instr(v_param,'}',1,e);
              DBMS_OUTPUT.put_line(i);
              DBMS_OUTPUT.put_line(j);

              if i =0 or e>500  then
                if e=1  then
                  v_param2 :=v_param;
                end   if ;

                 if e != 1 then
                   v_param2 := v_param2 || substr(v_param,old_j+1,lengthb(v_param)-old_j);
                   end if ;

                 exit;
              end if;

              v_param2 := v_param2 || substr(v_param,old_j+1,i-old_j-1);
              old_j :=j;
              sub_num := to_number( substr(v_param,i+1,j-i-1) );

              --获取往年的 TTA
                e2 :=1;
                v_tta_in :='';
                loop
                  if  e2=1 then
                      select
                        tc.clause_cn,
                        tc.business_type,
                        tc.p_code,
                        tc.team_framework_id
                      into
                        p_clause_cn,
                        p_business_type,
                        p_p_code,
                        p_team_framework_id
                      from  tta_clause_tree_h  tc where tc.clause_id = sub_num;
                    else
                        select
                        tc.clause_cn,
                        tc.business_type,
                        tc.p_code,
                        tc.team_framework_id
                      into
                        p_clause_cn,
                        p_business_type,
                        p_p_code,
                        p_team_framework_id
                      from  tta_clause_tree_h  tc where tc.team_framework_id = p_team_framework_id and tc.code = p_p_code;

                  end  if ;
                  v_tta_in :=  p_clause_cn || '→' ||  v_tta_in ;
                  if nvl(p_business_type,'01') = '01' or e2>500 then
                    exit;
                  end  if ;

                  e2 := e2 + 1;
                end   loop ;

              v_tta_in := substrb(v_tta_in,1,lengthb(v_tta_in)-3) ;
              v_tta_in :='【' ||  v_tta_in ||'】' ;

              v_param2 := v_param2 || v_tta_in;

              e := e + 1;
        END LOOP;

    return  v_param2 ;

 END get_clause_analysis_h;
