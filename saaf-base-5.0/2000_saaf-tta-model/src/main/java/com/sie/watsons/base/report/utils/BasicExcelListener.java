package com.sie.watsons.base.report.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class BasicExcelListener extends AnalysisEventListener<OrderStoreItemModal> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicExcelListener.class);
    private static final int initialCapacity = 5000;//初始化容量5000
    private List<OrderStoreItemModal> datas = new ArrayList<>(initialCapacity);
    private Connection conn = null;

    public BasicExcelListener(JdbcTemplate jdbcTemplate){
        try {
            this.conn = jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void invoke(OrderStoreItemModal orderStoreItemModal, AnalysisContext analysisContext) {
        LOGGER.info("解析行数:{}",analysisContext.getCurrentRowNum());
        datas.add(orderStoreItemModal);
        //方式1：当读取到initialCapacity条数据的时候，插入一次数据库，清空list
        if(datas.size() == initialCapacity){
           doSomethingForTwo();
           datas = new ArrayList<>(initialCapacity);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //解析结束销毁不用的资源
        //注意不要调用datas.clear(),否则getDatas为null
        //方式1：把剩下的插入数据库
        doSomethingForTwo();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void doSomethingForTwo() {
        PreparedStatement ps = null;
        try {
            //关闭自动提交，即开启事务
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }//TTA_ORDER_STORE_BY_ITEM   ORDER_STORE_BY_ITEM
        String sql = "insert into TTA_ORDER_STORE_BY_ITEM(SHOP_CODE,ITEM_CODE,ORDER_STORE,SUMMARY_QTY," +
                "SUMMARY_AMOUNT,SUMMARY_EXCLUDE_AMOUNT) values (?,?,?,?,?,?)";
        try {
            //创建语句对象
            ps = conn.prepareStatement(sql);
            for(int i = 0; i < datas.size(); i++){
                OrderStoreItemModal modal = datas.get(i);
                ps.setBigDecimal(1,modal.getShopCode());
                ps.setString(2,modal.getItemCode());
                ps.setBigDecimal(3,modal.getOrderStore());
                ps.setBigDecimal(4,modal.getSummaryQty());
                ps.setBigDecimal(5,modal.getSummaryAmount());
                ps.setBigDecimal(6,modal.getSummaryExcludeAmount());
                ps.addBatch();
            }
            // 如果数据不为倍数的话，最后一次会剩下一些
            // 语句执行完毕，提交本事务
            ps.executeBatch();
            ps.clearBatch();
            try {
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
            }
            // 在把自动提交打开
            conn.setAutoCommit(true);
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(ps,null);
        }
    }


}
