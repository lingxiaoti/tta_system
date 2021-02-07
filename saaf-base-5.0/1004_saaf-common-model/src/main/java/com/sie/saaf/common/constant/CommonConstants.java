package com.sie.saaf.common.constant;

import com.sie.saaf.common.util.DesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 全局的常量配置
 *
 * @author Zhang Jun
 */
public class CommonConstants {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonConstants.class);

    public static final String ENCODE_RULE = "SAAF5.0";
    /**
     * 树结构层级分隔符
     */
    public final static String HIERARCHY_ID_SPLIT = "x";

    /**
     * 树结构根节点父ID
     */
    public final static int ROOT_PARENT_ID = 0;

    /**
     * 已删除
     */
    public static final Integer DELETE_TRUE = 1;

    /**
     * 未删除
     */
    public static final Integer DELETE_FALSE = 0;

    /**
     * 叶子节点
     */
    public static final Integer IS_LEAF_TRUE = 0;

    /**
     * 非叶子节点
     */
    public static final Integer IS_LEAF_FALSE = 1;

    /**
     * 启用
     */
    public static final String ENABLED_TRUE = "Y";

    /**
     * 禁用
     */
    public static final String ENABLED_FALSE = "N";

    /**
     * 是：Yes
     */
    public static final String YES = "Yes";

    /**
     * 否：No
     */
    public static final String NO = "No";

    /**
     * cookie 在redis中过期时间/秒
     */
    public static final int COOKIE_EXPIRED = 3600;

    /**
     * profile表systemCode默认值
     */
    public static final String PROFILE_SYSTEM_CODE_DEFAULT_VALUE = "ALL";

    /**
     * 数据源类型
     */
    public static final String DATASOURCE_TYPE_DATABASE = "DataBase";

    /**
     * 数据源类型
     */
    public static final String DATASOURCE_TYPE_SOAP = "webServiceSOAP";

    /**
     * 数据源类型
     */
    public static final String DATASOURCE_TYPE_RESTFUL = "webServiceRestful";

    /**
     * 数据源访问类型
     */
    public static final String DATASOURCE_ACCESS_TYPE_MYSQL = "mysql";

    /**
     * 数据源访问类型
     */
    public static final String DATASOURCE_ACCESS_TYPE_ORACLE = "oracle";


    /**
     * 系统编码快码类型
     */
    public static final String DIC_TYPE_SYSTEM_CODE = "SYSTEM_CODE";

    /**
     * redis缓存管理类型
     */
    public static final String REDIS_CACHE_TYPE = "REDIS_CACHE_TYPE";

    /**
     * 虚拟仓库
     */
    public static final String VIRTUAL_WAREHOUSE = "00000";

    public static final String IMPORT_IP = "112.124.22.126";

    public static final String IMPORT_PORT = "21";

    public static final String IMPORT_IMG_ADMIN_USER = "img_web";

    public static final String IMPORT_IMG_ADMIN_PASSWORD;

    public static final String IMPORT_ADMIN_USER = "oa_appftp";

    public static final String IMPORT_ADMIN_PASSWORD;
    static{
        Properties properties = new Properties();
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        String importImgAdminPassword = null;
        String importAdminPassword = null;
        try {
            fileReader = new FileReader("1004_saaf-common-model/src/main/resources/com/sie/saaf/app/congif/encryptionPas.properties");
            bufferedReader = new BufferedReader(fileReader);
            properties.load(bufferedReader);
            // 获取key对应的value值
            importImgAdminPassword = properties.getProperty("IMPORT_IMG_ADMIN_PASSWORD");
            importAdminPassword = properties.getProperty("IMPORT_ADMIN_PASSWORD");
            importImgAdminPassword = DesUtil.AESDncode(ENCODE_RULE,importImgAdminPassword);
            importAdminPassword = DesUtil.AESDncode(ENCODE_RULE,importAdminPassword);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }finally {
            try {
                if(fileReader!=null){
                    fileReader.close();
                }
                if(bufferedReader!=null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(),e);
            }
            IMPORT_IMG_ADMIN_PASSWORD=importImgAdminPassword;
            IMPORT_ADMIN_PASSWORD=importAdminPassword;
        }
    }
    /**
     * Redis缓存KEY常量值
     */
    public interface RedisCacheKey {
        /**
         * 数据字典缓存Key，KEY：字典类型_系统编码
         */
        String BASE_LOOKUP_DATA_KEY_BY_LOOKUPTYPE = "BASE_LOOKUP_DATA_KEY_BY_LOOKUPTYPE";
        /**
         * 数据字典缓存Key，KEY：字典行表ID
         */
        String BASE_LOOKUP_DATA_KEY_BY_LOOKUPVALUEID = "BASE_LOOKUP_DATA_KEY_BY_LOOKUPVALUEID";
        /**
         * 全局的Redis缓存
         */
        String GLOBAL_REDIS_CACHE = "GLOBAL_REDIS_CACHE";
        /**
         * 最后一次同步更新时间
         * 用于保存在redis中同步的时间，使用hash保存
         */
        String LAST_SYNC_TIME = "LAST_SYNC_TIME";

        /**
         * 最后一次同步更新的主键key
         */
        String LAST_SYNC_PK_ID = "LAST_SYNC_TIME";
        /**
         * 产品在redis中缓存的KEY
         */
        String BASE_PRODUCT_INFO_BY_ITEM_ID_KEY = "BASE_PRODUCT_INFO_BY_ITEM_ID";
        /**
         * 产品在redis中缓存的KEY
         */
        String BASE_PRODUCT_INFO_BY_ITEM_CODE_KEY = "BASE_PRODUCT_INFO_BY_ITEM_CODE";

        /**
         * 子库在redis缓存中的KEY
         */
        String BASE_WAREHOUSE_MAPPING_KEY = "BASE_WAREHOUSE_MAPPING";

        /**
         * 库存组织在redis缓存中的KEY
         */
       String BASE_ORGANIZATION_INV_VIEW_KEY = "BASE_ORGANIZATION_INV";

        /**
         * 组织机构最后同步时间在redis缓存中的KEY
         */
        String BASE_ORGANIZATION_SYNC_KEY = "BASE_ORGANIZATION_SYNC";

        /**
         * 出入库调度锁标记，主要用于限制出入库一次只能有一次任务在跑
         */
        String BRC_RECEIVE_DELIVER_LOCK = "BRC_RECEIVE_DELIVER_LOCK";

        /**
         * 完工入库调度锁标记，主要用于限制完工入库一次只能有一个任务在跑
         */
        String BRC_IN_INV_LOCK = "BRC_IN_INV_LOCK";

        /**
         * edoc系统用户token(hash)
         */
        String EDOC_USER_TOKEN="EDOC_USER_TOKEN";
    }

    /**
     * 数据源驱动
     */
    public static final Map<String, String> DATASOURCE_DRIVER = new HashMap<>();

    public static final Map<String, String> SQL_DATA_TYPES = new HashMap<>();

    static {
        //数据库驱动
        DATASOURCE_DRIVER.put(DATASOURCE_ACCESS_TYPE_MYSQL, "com.mysql.jdbc.Driver");
        DATASOURCE_DRIVER.put(DATASOURCE_ACCESS_TYPE_ORACLE, "oracle.jdbc.driver.OracleDriver");
        //数据库字段类型
        SQL_DATA_TYPES.put("TINYINT", "NUMBER");
        SQL_DATA_TYPES.put("SMALLINT", "NUMBER");
        SQL_DATA_TYPES.put("MEDIUMINT", "NUMBER");
        SQL_DATA_TYPES.put("INTEGER", "NUMBER");
        SQL_DATA_TYPES.put("INT", "NUMBER");
        SQL_DATA_TYPES.put("BIGINT", "NUMBER");
        SQL_DATA_TYPES.put("FLOAT", "DOUBLE");
        SQL_DATA_TYPES.put("DOUBLE", "DOUBLE");
        SQL_DATA_TYPES.put("DECIMAL", "DOUBLE");
        SQL_DATA_TYPES.put("DATE", "DATE");
        SQL_DATA_TYPES.put("DATETIME", "DATETIME");
        SQL_DATA_TYPES.put("TIMESTAMP", "DATETIME");
    }

    /**
     * 工作流状态
     */
    public interface WorkflowStatus {
        /**
         * 流程状态：草稿
         */
        String DRAFT = "DRAFT";

        /**
         * 流程状态：审批中
         */
        String APPROVAL = "APPROVAL";

        /**
         * 流程状态：审批通过
         */
        String ALLOW = "ALLOW";

        /**
         * 流程状态：审批驳回
         */
        String REFUSAL = "REFUSAL";

        /**
         * 流程状态：关闭
         */
        String CLOSED = "CLOSED";

        /**
         * 流程状态：删除
         */
        String DELETED = "DELETED";
    }


}
