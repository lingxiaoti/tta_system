package com.sie.watsons.base.sync.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.sync.model.entities.TtaBrandEntity_HI;
import com.sie.watsons.base.sync.model.entities.readonly.TtaBrandEntity_HI_RO;
import com.sie.watsons.base.sync.model.inter.ITtaBrand;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaBrandServer")
public class TtaBrandServer extends BaseCommonServer<TtaBrandEntity_HI> implements ITtaBrand {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandServer.class);

	@Autowired
	private ViewObject<TtaBrandEntity_HI> ttaBrandDAO_HI;
	@Autowired
	private BaseViewObject<TtaBrandEntity_HI_RO> ttaBrandDAO_HI_RO;

    //plm
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate plmJdbcTemplate;

    //tta
    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate vmiJdbcTemplate;

	public TtaBrandServer() {
		super();
	}


	@Override
	public void find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws SQLException {
        //先删除本地数据
//        plmJdbcTemplate.execute("DELETE FROM VMI_SYS_ITEM_BRAND ");
//        plmJdbcTemplate.execute("DELETE FROM VMI_RPT_SALES_INV ");
        synchVmiShop();


        //查询每个表数据总数
//        String sql1 = "select count(*) from supptl.RPT_SALES_INV_VMI";
//        int count1 = vmiJdbcTemplate.queryForObject(sql1, Integer.class);

//        String sql2 = "select count(*) from supptl.RPT_SALES_INV_VMI";
//        int count2 = vmiJdbcTemplate.queryForObject(sql2, Integer.class);

//        String sql3 = "select count(*) from supptl.RPT_SALES_INV_VMI";


        //分批查出数据再插入
//        for(int i=0;i<count1;i+=1000){
//            String sqlstr="select * from ( select rownum as rn,w.* from supptl.RPT_SALES_INV_VMI w ) ww " +
//                    "where WW.rn <"+(i+1000)+" and WW.rn >="+i;
//            List<Map<String, Object>> rows = vmiJdbcTemplate.queryForList(sqlstr);
//            addrpt(rows);
//        }
//        for(int i=0;i<count2;i+=1000){
//            String sqlstr="select * from ( select rownum as rn,w.* from supptl.SYS_BI_INFO w ) ww " +
//                    "where WW.rn <"+(i+1000)+" and WW.rn >="+i;
//            List<Map<String, Object>> rows = vmiJdbcTemplate.queryForList(sqlstr);
//            addbi(rows);
//        }


    }

    private void synchVmiShop() {
        plmJdbcTemplate.execute("DELETE FROM VMI_SHOP ");
        String sql3 = "select count(*) from VMI_SHOP";
        int count3 = vmiJdbcTemplate.queryForObject(sql3, Integer.class);
        for(int i=0;i<count3;i+=1000){
            String sqlstr="select * from ( select rownum as rn,w.* from VMI_SHOP w ) ww " +
                    "where WW.rn <"+(i+1000)+" and WW.rn >="+i;
            List<Map<String, Object>> rows = vmiJdbcTemplate.queryForList(sqlstr);
            addVS(rows);
        }
    }

//    private void synchVmiShop() {
//        plmJdbcTemplate.execute("DELETE FROM VMI_SHOP ");
//        String sql3 = "select count(*) from VMI_SHOP";
//        int count3 = vmiJdbcTemplate.queryForObject(sql3, Integer.class);
//        for(int i=0;i<count3;i+=1000){
//            String sqlstr="select * from ( select rownum as rn,w.* from VMI_SHOP w ) ww " +
//                    "where WW.rn <"+(i+1000)+" and WW.rn >="+i;
//            List<Map<String, Object>> rows = vmiJdbcTemplate.queryForList(sqlstr);
//            addVS(rows);
//        }
//    }

    public void addrpt(List<Map<String,Object>> list)
    {
        String sql="insert into VMI_RPT_SALES_INV(UDA_ID,UDA_VALUE,ITEM_IDNT,BAR_CODE,LOC_KEY,LOC_IDNT,MIN_STOCK,MAX_STOCK,ALLOCATION,SALES_AMT,SALES_QTY,STORE_SOH,STORE_TRANSMIT_SOH,DC_SOH,DC_TRANSMIT_SOH,LAST_UPDATE_DATE,DAY_DT)\n" +
                " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        plmJdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {

            @Override
            public int getBatchSize() {
                return list.size();
            }
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                ps.setString(1, list.get(i).get("UDA_ID").toString());
                ps.setString(2, list.get(i).get("UDA_VALUE").toString());
                ps.setString(3, list.get(i).get("ITEM_IDNT").toString());
                ps.setString(4, list.get(i).get("BAR_CODE").toString());
                ps.setString(5, list.get(i).get("LOC_KEY").toString());
                ps.setString(6, list.get(i).get("LOC_IDNT").toString());
                ps.setString(7, list.get(i).get("MIN_STOCK").toString());
                ps.setString(8, list.get(i).get("MAX_STOCK").toString());
                ps.setString(9, list.get(i).get("ALLOCATION").toString());
                ps.setString(10, list.get(i).get("SALES_AMT").toString());
                ps.setString(11, list.get(i).get("SALES_QTY").toString());
                ps.setString(12, list.get(i).get("STORE_SOH").toString());
                ps.setString(13, list.get(i).get("STORE_TRANSMIT_SOH").toString());
                ps.setString(14, list.get(i).get("DC_SOH").toString());
                ps.setString(15, list.get(i).get("DC_TRANSMIT_SOH").toString());
                ps.setString(16, list.get(i).get("LAST_UPDATE_DATE").toString());
                ps.setString(17, list.get(i).get("DAY_DT").toString());
            }
        });
    }

    public void addVS(List<Map<String,Object>> list)
    {
        String sql="insert into VMI_SHOP(VS_ID,VH_PRE_CODE,VS_CODE,AREA_EN)" +
                " values(?,?,?,?)";
        plmJdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {

            @Override
            public int getBatchSize() {
                return list.size();
            }
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
//                ps.setString(1, list.get(i).get("IDNT").toString());
//                ps.setString(2, list.get(i).get("EXTERNAL_NM").toString());
//                ps.setString(3, list.get(i).get("BI_ID").toString());
                ps.setString(1, list.get(i).get("VS_ID").toString());
                ps.setString(2, list.get(i).get(",VH_PRE_CODE").toString());
                ps.setString(3, list.get(i).get(",VS_CODE").toString());
                ps.setString(4, list.get(i).get(",AREA_EN").toString());
            }
        });
    }

    public void addbrand(List<Map<String,Object>> list)
    {
        String sql="insert into VMI_SYS_ITEM_BRAND(P_BI_ID,C_BI_ID) " +
                " values(?,?)";
        plmJdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {

            @Override
            public int getBatchSize() {
                return list.size();
            }
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                ps.setString(1, list.get(i).get("P_BI_ID").toString());
                ps.setString(2, list.get(i).get("C_BI_ID").toString());
            }
        });
    }

}
