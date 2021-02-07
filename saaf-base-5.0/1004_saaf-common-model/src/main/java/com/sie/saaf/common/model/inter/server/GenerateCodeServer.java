package com.sie.saaf.common.model.inter.server;

import com.sie.saaf.common.util.SaafDateUtils;
import com.yhg.base.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.rmi.CORBA.Tie;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author  huangtao
 * @createTime 2017年12月25日 15:32:13
 */
@Component
public class GenerateCodeServer {

    @Autowired
    private JedisCluster jedisCluster;

    private SimpleDateFormat requestDateFormat=new SimpleDateFormat("yyyyMMddhhmm");
    private SimpleDateFormat requestKeyDateFormat=new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat requestKeyDateFormatYM=new SimpleDateFormat("yyyyMM");
    private SimpleDateFormat requestKeyDateFormatY=new SimpleDateFormat("yyyy");
    private SimpleDateFormat requestKeyDateFormatYYMM=new SimpleDateFormat("yyMM");
    private SimpleDateFormat requestKeyDateFormatYYMMDD=new SimpleDateFormat("yyMMdd");
    //  编码redis KEY的固定前缀
    private static final String GLOBAL_PREFIX = "SEQUENCE";


    public String getSequenceId(String type, int length) {
        Long seq = jedisCluster.incr("SEQUENCE_" + type);
        return String.format("%0"+length+"d", seq);
    }


    /**
     * 生成流水号 前缀+时间日期+序列
     *
     * @param financialCode  前缀
     * @param dateFormat     时间格式
     * @param sequenceLength 序列
     * @return
     */
    public String generateCode(String financialCode, SimpleDateFormat dateFormat, int sequenceLength) {
        if(StringUtils.isBlank(financialCode)){
            return generateCode(dateFormat,sequenceLength);
        }
        if (dateFormat == null)
            return financialCode + getSequenceId(financialCode, sequenceLength);
        String pre = financialCode + dateFormat.format(new Date());
        return pre + getSequenceId(pre, sequenceLength);
    }

    /**
     * 根据日期生成流程号
     * @param dateFormat 日期格式
     * @param sequenceLength 序列长度
     * @return 流水号
     * @author ZhangJun
     * @createTime 2018/1/26 16:55
     * @description 根据日期生成流程号
     */
    public String generateCode(SimpleDateFormat dateFormat,int sequenceLength){
        String pre = dateFormat.format(new Date());
        return pre + getSequenceId(pre,sequenceLength);
    }


    /**
     * 生成流水号 前缀+序列
     *
     * @param financialCode  前缀
     * @param sequenceLength 序列长度
     * @return
     */
    public String generateCode(String financialCode, int sequenceLength) {
        return generateCode(financialCode, null, sequenceLength);
    }


    /**
     * 分库分表时 统一生成主键id
     * @param tClass 实体类.class
     * @return
     */
    public Integer getGenerateId(Class tClass) {
        return getGenerateId(tClass.getName());
    }

    /**
     * 根据redis key生成主键id
     * @param key redis key
     * @author ZhangJun
     * @createTime 2018/1/25 14:39
     * @description 根据redis key生成主键id
     */
    public Integer getGenerateId(String key){
        return jedisCluster.hincrBy("GENERATE_ID",key,1).intValue();
    }

    public Long getGenerateLongId(String key){
        return jedisCluster.hincrBy("GENERATE_ID",key,1);
    }

    /**
     * 生成一个不带"-"的UUID字符串
     * @return uuid
     */
    public String generateUUIDString(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        return uuid;
    }


    /**
     * 生存请求id( yyyyMMddHHmm + 五位序列）
     *
     * @return
     */
    public String getRequestId(){
        Date now=new Date();
        String dateStr=requestDateFormat.format(now);
        String keyDate=requestKeyDateFormat.format(now);
        String key="REQUEST_KEY_"+keyDate;
        //将 key 的值设为 10000 ，当且仅当 key 不存在。生存时间一个自然日
        if ("1".equals(jedisCluster.setnx(key,"10000"))){
            Date tomorrow= DateUtil.addDay(SaafDateUtils.string2UtilDate(keyDate),1);
            jedisCluster.expireAt(key,tomorrow.getTime());
        }
        return dateStr+jedisCluster.incr(key);
    }

    /**
     * @param type
     * @param key
     * @param length
     * @return java.lang.String
     * @author:ZhangLang
     * @creteTime 2018/6/11
     * @description 生成固定长度的流水号
     **/
    public String  getGenerateCode(String type ,String key,int length){
        Long seq = jedisCluster.hincrBy(type,key,1);
        return String.format("%0"+length+"d", seq);
    }

    public String  getGenerateCode(String key,int length){
        Long seq = jedisCluster.hincrBy(GLOBAL_PREFIX,key,1);
        return String.format("%0"+length+"d", seq);
    }

    public String  getGenerateCodeByProposal(String key,int length){
        Long seq = jedisCluster.hincrBy(GLOBAL_PREFIX,key,1);
        seq = seq + 100;
        return String.format("%0"+length+"d", seq);
    }

    /**
     * @param prefix
     * @param length
     * @param dateFlag  是否有日期
     * @param prefixFlag  是否有前缀
     * @return java.lang.String
     * @author:ZhangLang
     * @creteTime 2018/6/11
     * @description 有前缀编码
     **/
    public String getGenerateCode(String prefix ,int length,boolean dateFlag,boolean prefixFlag){
        Date now=new Date();
        String keyDate="";
        if(dateFlag){
            keyDate=requestKeyDateFormat.format(now);
        }
        String redisKey =prefix+"-"+keyDate;
        if(prefixFlag){
            return prefix+keyDate+getGenerateCode(redisKey,length);
        }else{
            return keyDate+getGenerateCode(redisKey,length);
        }

    }


    public String geRequestIdCode(int length,boolean dateFlag,boolean prefixFlag){
        Date now=new Date();
        String keyDate="";
        if(dateFlag){
            keyDate=requestKeyDateFormatYYMMDD.format(now);
        }
        String redisKey =keyDate;
        if(prefixFlag){
            return keyDate+getGenerateCode(redisKey,length);
        }else{
            return keyDate+getGenerateCode(redisKey,length);
        }

    }
    public String geEcoNoCode(Integer length, boolean dateFlag, boolean prefixFlag) {
        Date now=new Date();
        String keyDate="";
        if(dateFlag){
            keyDate=requestKeyDateFormat.format(now);
        }
        String redisKey =keyDate;
        if(prefixFlag){
            return keyDate+getGenerateCode(redisKey,length);
        }else{
            return keyDate+getGenerateCode(redisKey,length);
        }

    }

    /**
     * 生产单
     * @param prefix
     * @param length
     * @param dateFlag
     * @param prefixFlag
     * @return
     */
    public String getGenerateCodePickingApply(String prefix ,int length,boolean dateFlag,boolean prefixFlag){

        String redisKey =prefix;
        if(prefixFlag){
            return prefix+getGenerateCode(redisKey,length);
        }else{
            return getGenerateCode(redisKey,length);
        }

    }


    /**
     * 开票申请编码实例:CI+“YYMM”+5位流水（按月重置流水）
     * @param prefix
     * @param length
     * @param dateFlag  是否有日期
     * @param prefixFlag  是否有前缀
     * @return java.lang.String
     * @author:ZhangLang
     * @creteTime 2018/12/03
     * @description 有前缀编码
     **/
    public String getApplyCodeGenerateCode(String prefix ,int length,boolean dateFlag,boolean prefixFlag){
        Date now=new Date();
        String keyDate="";
        if(dateFlag){
            keyDate=requestKeyDateFormatYM.format(now);
        }
        String redisKey =prefix+keyDate;
        if(prefixFlag){
            return prefix+keyDate+getGenerateCode(redisKey,length);
        }else{
            return keyDate+getGenerateCode(redisKey,length);
        }

    }

    /**
     * 编码实例:CI+“YYMM”+5位流水（按月重置流水）
     * @param prefix
     * @param length
     * @param dateFlag  是否有日期
     * @param prefixFlag  是否有前缀
     * @return java.lang.String
     * @author:ZhangLang
     * @creteTime 2018/12/03
     * @description 有前缀编码
     **/
    public String getTtaContractRecordGenerateCode(String prefix ,String only,String endString, int length,boolean dateFlag,boolean prefixFlag){
        Date now=new Date();
        String keyDate="";
        Calendar ca = Calendar.getInstance();
        ca.setTime(now);
        if(dateFlag) {
            if ((ca.get(Calendar.YEAR) - 2020) % 2 == 0) {
                keyDate = String.valueOf(ca.get(Calendar.YEAR));
            } else {
                keyDate = String.valueOf(ca.get(Calendar.YEAR) - 1);
            }
        }
        String redisKey =only+keyDate;
        if(prefixFlag){
            return prefix+keyDate+endString+ getGenerateCode(redisKey,length);
        }else{
            return keyDate+getGenerateCode(redisKey,length);
        }

    }



    public String getComApplyCodeGenerateCode(String prefix ,String only,int length,boolean dateFlag,boolean prefixFlag){
        Date now=new Date();
        String keyDate="";
        if(dateFlag){
            keyDate=requestKeyDateFormatYM.format(now);
        }
        String redisKey =only+keyDate;
        if(prefixFlag){
            return prefix+keyDate+getGenerateCode(redisKey,length);
        }else{
            return keyDate+getGenerateCode(redisKey,length);
        }

    }

    /**
     * 供应商暂停条例:tt+“YYMMDD”+流水（按月重置流水）
     * @param prefix
     * @param length
     * @param dateFlag  是否有日期
     * @param prefixFlag  是否有前缀
     * @return java.lang.String
     * @author:ZhangLang
     * @creteTime 2018/12/03
     * @description 有前缀编码
     **/
    public String getSupplierSuspendCode(String prefix ,int length,boolean dateFlag,boolean prefixFlag){
        Date now=new Date();
        String keyDate="";
        if(dateFlag){
            keyDate=requestKeyDateFormat.format(now);
        }
        String redisKey =prefix+keyDate;
        if(prefixFlag){
            return prefix+"-"+keyDate+"-"+getGenerateCode(redisKey,length);
        }else{
            return keyDate+"-"+getGenerateCode(redisKey,length);
        }

    }


    /**
     * 报价单号
     * @param prefix
     * @param length
     * @param dateFlag
     * @param prefixFlag
     * @return
     */
    public String getGenerateCodeCustCode(String prefix ,int length,boolean dateFlag,boolean prefixFlag){
        Date now=new Date();
        String keyDate="";

        if(dateFlag){
            keyDate=requestKeyDateFormatYYMM.format(now);
        }
        String redisKey =prefix+"-"+keyDate;
        if(prefixFlag){
            return prefix+keyDate+getGenerateCode(redisKey,length);
        }else{
            return getGenerateCode(redisKey,length);
        }

    }

    /**
     * 产品卖点尺寸
     * @param prefix
     * @param length
     * @param dateFlag
     * @param prefixFlag
     * @return
     */
    public String getGenerateItemSizeCode(String prefix ,int length,boolean dateFlag,boolean prefixFlag){
        Date now=new Date();
        String keyDate="";

        if(dateFlag){
            keyDate=requestKeyDateFormatYYMMDD.format(now);
        }
        String redisKey =prefix+"-"+keyDate;
        if(prefixFlag){
            return prefix+keyDate+getGenerateCode(redisKey,length);
        }else{
            return getGenerateCode(redisKey,length);
        }

    }


    //信用证修改件编码
    public String getCreditCodeCustCode(String customerCode,String prefix ,int length,boolean dateFlag,boolean prefixFlag){
        String redisKey =customerCode+"-"+prefix;
        if(prefixFlag){
            return customerCode+prefix+getGenerateCode(redisKey,length);
        }else{
            return getGenerateCode(redisKey,length);
        }
    }

    //信用证特批件编码
    public String getSpecialCodeCustCode(String customerCode,String prefix ,int length,boolean dateFlag,boolean prefixFlag){
        String redisKey =customerCode+"-"+prefix;
        if(prefixFlag){
            return customerCode+prefix+getGenerateCode(redisKey,length);
        }else{
            return getGenerateCode(redisKey,length);
        }
    }

    //客户订单号
    public String getGenerateBNCodeBigGoodsOrderFlagY(String buCode ,String salesCode,int length,boolean dateFlag,boolean prefixFlag){
        String keyDate="";
        Date now=new Date();
        if(dateFlag){
            keyDate=requestKeyDateFormat.format(now);
        }
        String redisKey =keyDate.substring(2,6);
        if(prefixFlag){
            return redisKey+buCode+salesCode+getGenerateCode(redisKey,length);
        }else{
            return getGenerateCode(redisKey,length);
        }

    }
    //客户订单号
    public String getGenerateBNCodeBigGoodsOrderFlagN(String buCode ,String salesCode,int length,boolean dateFlag,boolean prefixFlag){
        String keyDate="";
        Date now=new Date();
        if(dateFlag){
            keyDate=requestKeyDateFormat.format(now);
        }
        String redisKey="buCode";
        if(null==buCode||null==salesCode)
         redisKey ="buCodesalesCode";
        else if(null!=salesCode)
            redisKey= salesCode;
        if(prefixFlag){
            return buCode+salesCode+"-"+getGenerateCode(redisKey,length);
        }else{
            return getGenerateCode(redisKey,length);
        }

    }
    //销售订单号
    public String getGenerateSalesOrderCode(String buCode ,String salesCode,int length,boolean dateFlag,boolean prefixFlag){
        String keyDate="";
        Date now=new Date();
        if(dateFlag){
            keyDate=requestKeyDateFormat.format(now);
        }
        String redisKey =keyDate.substring(2,6);
        if(prefixFlag){
            return redisKey+buCode+salesCode+getGenerateCode(redisKey,length);
        }else{
            return getGenerateCode(redisKey,length);
        }
    }

    /**
     * @invoiceNumber 发票号
     * @return java.lang.String
     * @author:chenzijie
     * @creteTime 2018/11/21
     * 商业发票放单流水号：商业发票号+“-”+FD+2位流水
     **/
    public String getInvReleaseApplyCode(String invoiceNumber ,int length){
        String redisKey =invoiceNumber+"F";
        return redisKey+getGenerateCode(redisKey,length);
    }

    //采购订单号
    public String getPurchaseOrderNumber(){
          String keyDate="";
          Date now=new Date();
            keyDate=requestKeyDateFormat.format(now);
            String redisKey =keyDate.substring(0,6);
            return "CG"+redisKey+getGenerateCode(redisKey,4);

    }

    //船务费用编号
    public String getFeeShipApplyCode(){
        String keyDate = "";
        Date now = new Date();
        keyDate = requestKeyDateFormat.format(now);
        String redisKey = keyDate.substring(0,6);
        return "Y" + redisKey + getGenerateCode(redisKey,4);
    }

    public String getCustomerCode(String type, int length)throws Exception{
        String customerCode = getGenerateCode(type, length);

        if("WX".equals(type)){
            Long seq = Long.valueOf(customerCode) + 2000000;
            if(seq>2999999){
                throw  new IllegalArgumentException("客户编码已经超过最大数据！");
            }
            customerCode = String.format("%0"+length+"d", seq);
        }else if("NX".equals(type)){
            Long seq = Long.valueOf(customerCode) + 1000000;
            if(seq>1999999){
                throw  new IllegalArgumentException("客户编码已经超过最大数据！");
            }
            customerCode = String.format("%0"+length+"d", seq);
        }
        return customerCode;
    }


    /**
     * 生成流水号 前缀+时间日期+部门编号+序列
     *
     * @param financialCode  前缀
     * @param dateFormat     时间格式
     *  @param inCode     部门编号
     * @param sequenceLength 序列
     * @return
     */
    public String generateIncodeCode(String financialCode, SimpleDateFormat dateFormat,String inCode, int sequenceLength) {
        if(StringUtils.isBlank(financialCode)){
            return generateCode(dateFormat,sequenceLength);
        }
        if (dateFormat == null)
            return financialCode + getSequenceId(financialCode, sequenceLength);
        String pre = financialCode + dateFormat.format(new Date())+inCode;
        return pre + getSequenceId(pre, sequenceLength);
    }

    public String getGenerateCodeByHistory(String prefix ,int length,boolean dateFlag,boolean prefixFlag){
        //获取往年时间
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR,-1);
        now = calendar.getTime();

        String keyDate = "";
        if(dateFlag){
            keyDate = requestKeyDateFormat.format(now);
        }
        String redisKey =prefix+"-"+keyDate;
        if(prefixFlag){
            return prefix + keyDate + getGenerateCodeByProposal(redisKey,length);
        }else{
            return keyDate+getGenerateCodeByProposal(redisKey,length);
        }
    }

}
