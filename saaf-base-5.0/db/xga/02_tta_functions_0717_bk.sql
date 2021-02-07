CREATE OR REPLACE FUNCTION FORSTR(STR1 VARCHAR2, FORMAT VARCHAR2 := 'FM00')
  RETURN VARCHAR2 AS
  RESULT VARCHAR2(600);
  STR    VARCHAR2(300) := STR1;
BEGIN
  WHILE 0 <= INSTR(STR, '.') LOOP
    IF INSTR(STR, '.') = 0 THEN
      RESULT := RESULT || TO_CHAR(STR, FORMAT);
      EXIT;
    ELSE
      RESULT := RESULT ||
                TO_CHAR(TO_NUMBER(SUBSTR(STR, 0, INSTR(STR, '.'))), FORMAT) || '.';
    END IF;
    STR := SUBSTR(STR, INSTR(STR, '.') + 1, LENGTH(STR));
  END LOOP;
  RETURN RESULT;
EXCEPTION
  WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20000, '层次分隔符不匹配,请使用''.''符号!');
END FORSTR;
/


CREATE OR REPLACE FUNCTION get_exp( clauseId in number,tptLId in  number ,oldtproId in number)
RETURN NUMBER
as
v_is varchar2(1);
v_param varchar2(3000);
v_param2 varchar2(3000);
v_tta   varchar2(50) ;
v_Sql   varchar2(500) ;
      valuev   number ;
      oldClauseId  number ;

  BEGIN
    v_param :='';
    v_tta   :='';
    v_is    :='';
    valuev  :=0;
    if clauseId is null or tptLId  is  null or oldtproId is  null   then
      return   0;
    end   if ;
      select tct.is_global_variable,
             tct.expression_value
      into   v_is,
             v_param
             
      from   tta_clause_tree tct where tct.clause_id = clauseId ;
      if v_param='' or   v_param is  null   then
        
      return   null ;
      end    if  ;
      if '1'=v_is then
        v_param :=  v_param ||tptLId ;
                  EXECUTE IMMEDIATE    v_param into  valuev;
      else

              for v in   (SELECT REGEXP_SUBSTR(v_param,
                                                          '[^@]+',
                                                          1,
                                                          LEVEL) a
                                       FROM DUAL
                                     CONNECT BY REGEXP_SUBSTR(v_param,
                                                              '[^@]+',
                                                              1,
                                                              LEVEL) IS NOT NULL)
              loop
                    begin
                          if   replace(translate(v.a, '0123456789', '0'), '0', '') is  null  then
                          
                               select tc.old_clause_id into  oldClauseId from  tta_clause_tree  tc where tc.clause_id = v.a ;
                               select nvl(tptl.y_year,0) into  v_tta from  Tta_Proposal_Terms_Line  tptl where tptl.proposal_id =  oldtproId and tptl.clause_id = oldClauseId ;
                               v_param2 := v_param2 || v_tta ;
                        
                          else
                               v_param2  := v_param2 || v.a ;
                          end   if   ;
                    exception
                          when  NO_DATA_FOUND  then
                                v_param2  := v_param2 || v.a ;
                   end  ;
              end   loop ;
     v_Sql := 'SELECT trunc(' || v_param2 || ',2)  FROM dual';
     EXECUTE IMMEDIATE   v_Sql into  valuev ;
    end if ;
    return  valuev ;

 END get_exp;
/


CREATE OR REPLACE FUNCTION get_exp_tta( clauseId in number,tptLId in  number ,oldtproId in number)
RETURN NUMBER
as
v_is varchar2(1);
v_param varchar2(3000);
v_param2 varchar2(3000);
v_tta   varchar2(50) ;
v_Sql   varchar2(500) ;
      valuev   number ;
      oldClauseId  number ;

  BEGIN
    v_param :='';
    v_tta   :='';
    v_is    :='';
    valuev  :=0;
         if clauseId is null or tptLId  is  null or oldtproId is  null   then
      return   0;
    end   if ;
      select tct.is_global_tta,
             tct.expression_value_tta
      into   v_is,
             v_param
   
      from   tta_clause_tree tct where tct.clause_id = clauseId ;
      
      if v_param='' or  v_param  is  null    then

       return null ;
      end   if ;    
      
      if '1'=v_is then
        v_param :=  v_param ||tptLId ;
                  EXECUTE IMMEDIATE    v_param into  valuev;
      else

              for v in   (SELECT REGEXP_SUBSTR(v_param,
                                                          '[^@]+',
                                                          1,
                                                          LEVEL) a
                                       FROM DUAL
                                     CONNECT BY REGEXP_SUBSTR(v_param,
                                                              '[^@]+',
                                                              1,
                                                              LEVEL) IS NOT NULL)
              loop
                    begin
                          if   replace(translate(v.a, '0123456789', '0'), '0', '') is  null  then
                          
                               select tc.old_clause_id into  oldClauseId from  tta_clause_tree  tc where tc.clause_id = v.a ;
                               select nvl(tptl.y_year,0) into  v_tta from  Tta_Proposal_Terms_Line  tptl where tptl.proposal_id =  oldtproId and tptl.clause_id = oldClauseId ;
                               v_param2 := v_param2 || v_tta ;
                        
                          else
                               v_param2  := v_param2 || v.a ;
                          end   if   ;
                    exception
                          when  NO_DATA_FOUND  then
                                v_param2  := v_param2 || v.a ;
                   end  ;
              end   loop ;
     v_Sql := 'SELECT trunc(' || v_param2 || ',2)  FROM dual';
     EXECUTE IMMEDIATE   v_Sql into  valuev ;
    end if ;
    return  valuev ;

 END get_exp_tta;
/

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
/