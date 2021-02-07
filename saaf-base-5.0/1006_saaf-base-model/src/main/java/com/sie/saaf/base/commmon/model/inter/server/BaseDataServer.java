package com.sie.saaf.base.commmon.model.inter.server;

import com.sie.saaf.base.commmon.model.inter.IBaseData;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class BaseDataServer implements IBaseData {

    @Autowired
    private BaseViewObject commonDAO_HI_DY;


    /**
     * 查询供应商
     * @return
     */
    @Override
    public List<String> findVendor(Collection<Integer> respId, Integer userId){
        if ((respId==null || respId.size()==0) && userId==null){
            return Collections.emptyList();
        }


        StringBuilder sql=new StringBuilder("SELECT DISTINCT PROFILE_VALUE  FROM BASE_PROFILE_VALUE T1 JOIN BASE_PROFILE T2 ON T2.PROFILE_CODE='VENDOR' AND T1.PROFILE_ID=T2.PROFILE_ID AND T1.KEY_TABLE_NAME='base_users' AND T1.DELETE_FLAG='0' AND T1.BUSINESS_KEY =?");
        List list=commonDAO_HI_DY.findList(sql,userId);
        if (respId!=null && respId.size()>0){
            StringJoiner joiner = new StringJoiner("','");
            for (Integer id:respId){
                joiner.add(id.toString());
            }
            sql=new StringBuilder("SELECT DISTINCT PROFILE_VALUE  FROM BASE_PROFILE_VALUE T1 JOIN BASE_PROFILE T2 ON T2.PROFILE_CODE='VENDOR' AND T1.PROFILE_ID=T2.PROFILE_ID AND T1.KEY_TABLE_NAME='base_responsibility' AND T1.SYSTEM_CODE='VMI' AND T1.DELETE_FLAG='0' AND T1.BUSINESS_KEY IN ('").append(joiner.toString()).append("') ");
            list.addAll(commonDAO_HI_DY.findList(sql));
        }

        if (list.size()==0){
            return Collections.emptyList();
        }
        List<String> result=new ArrayList<>();
        for (Object obj:list){
            if (!(obj instanceof HashMap)){
                continue;
            }
            Map<String,Object> map= (Map<String, Object>) obj;
            result.add(map.get("PROFILE_VALUE").toString());
        }
        return result.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> findVendorGroup(Collection<Integer> respId, Integer userId){
        if ((respId==null || respId.size()==0) && userId==null){
            return Collections.emptyList();
        }
        StringBuilder sql=new StringBuilder("SELECT DISTINCT PROFILE_VALUE  FROM BASE_PROFILE_VALUE T1 JOIN BASE_PROFILE T2 ON T2.PROFILE_CODE='VENDOR_GROUP' AND T1.PROFILE_ID=T2.PROFILE_ID AND T1.KEY_TABLE_NAME='base_users' AND T1.DELETE_FLAG='0' AND T1.BUSINESS_KEY =?");
        List list=commonDAO_HI_DY.findList(sql,userId);
        if (respId!=null && respId.size()>0){
            StringJoiner joiner = new StringJoiner("','");
            for (Integer id:respId){
                joiner.add(id.toString());
            }
            sql=new StringBuilder("SELECT DISTINCT PROFILE_VALUE  FROM BASE_PROFILE_VALUE T1 JOIN BASE_PROFILE T2 ON T2.PROFILE_CODE='VENDOR_GROUP' AND T1.PROFILE_ID=T2.PROFILE_ID AND T1.KEY_TABLE_NAME='base_responsibility' AND T1.SYSTEM_CODE='VMI' AND T1.DELETE_FLAG='0' AND T1.BUSINESS_KEY IN ('").append(joiner.toString()).append("') ");
            list.addAll(commonDAO_HI_DY.findList(sql));
        }
        if (list.size()==0){
            return Collections.emptyList();
        }
        List<String> result=new ArrayList<>();
        for (Object obj:list){
            if (!(obj instanceof HashMap)){
                continue;
            }
            Map<String,Object> map= (Map<String, Object>) obj;
            result.add(map.get("PROFILE_VALUE").toString());
        }
        return result.stream().distinct().collect(Collectors.toList());
    }

}
