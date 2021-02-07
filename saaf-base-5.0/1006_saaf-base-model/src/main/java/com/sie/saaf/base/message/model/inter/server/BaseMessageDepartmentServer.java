package com.sie.saaf.base.message.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessageDepartmentEntity_HI;
import com.sie.saaf.base.message.model.entities.BaseMessagePersonEntity_HI;
import com.sie.saaf.base.message.model.inter.IBaseMessageDepartment;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("baseMessageDepartmentServer")
public class BaseMessageDepartmentServer extends BaseCommonServer<BaseMessageDepartmentEntity_HI> implements IBaseMessageDepartment {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMessageDepartmentServer.class);
    @Autowired
    private ViewObject<BaseMessageDepartmentEntity_HI> baseMessageDepartmentDAO_HI;
    @Autowired
    private ViewObject<BaseMessagePersonEntity_HI> baseMessagePersonDAO_HI;

    public BaseMessageDepartmentServer() {
        super();
    }

    /**
     * 新增&修改站内消息待发送对象-部门和部门中的人员
     *
     * @param paramsJSON
     * {
     *     ouId：事业部ID，
     *     userType：用户类型
     *     conMessId：消息Id，
     *     buMessId：接收对象组合,
     *     deptData：[{
     *         deptId：部门ID，
     *         deptCode：部门编码，
     *         deptName：部门名称，
     *         personData：[1,2,3,4,5] 用户ID（员工用户、经销商用户、门店用户）
     *     }]
     * }
     * @param userId
     * @throws Exception
     */
    @Override
    public void saveOrUpdateMessDeptAndPerson(JSONObject paramsJSON, int userId) throws Exception {
        String userType = paramsJSON.getString("userType"); //用户类型
        Integer conMessId = paramsJSON.getInteger("conMessId"); //消息内容ID
        Integer buMessId = paramsJSON.getInteger("buMessId"); //接收对象组合表主键ID
        JSONArray deptDataArray = paramsJSON.getJSONArray("deptData");
        for (int i = 0; i < deptDataArray.size(); i++) {
            JSONObject deptDataJSON = deptDataArray.getJSONObject(i);
            JSONArray personData = deptDataJSON.getJSONArray("personData");
            if (personData.size() == 0) {
                continue;
            }
            BaseMessageDepartmentEntity_HI messageDepartmentEntity = JSON.toJavaObject(deptDataJSON, BaseMessageDepartmentEntity_HI.class);
            messageDepartmentEntity.setConMessId(conMessId);
            messageDepartmentEntity.setBuMessId(buMessId);
            messageDepartmentEntity.setSendingNum(personData.size());
            messageDepartmentEntity.setDeleteFlag(0);
            messageDepartmentEntity.setOperatorUserId(userId);
            baseMessageDepartmentDAO_HI.save(messageDepartmentEntity);

            Integer depMessId = messageDepartmentEntity.getDepMessId();
            List<BaseMessagePersonEntity_HI> messagePersonList = new ArrayList<>();
            for (int j = 0; j < personData.size(); j++) {
                BaseMessagePersonEntity_HI baseMessagePersonEntity = new BaseMessagePersonEntity_HI();
                baseMessagePersonEntity.setConMessId(conMessId);
                baseMessagePersonEntity.setBuMessId(buMessId);
                baseMessagePersonEntity.setDepMessId(depMessId);
                baseMessagePersonEntity.setDeptId(deptDataJSON.getInteger("deptId"));
                baseMessagePersonEntity.setUserType(userType);
                baseMessagePersonEntity.setUserId(personData.getInteger(j));
                baseMessagePersonEntity.setDeleteFlag(0);
                baseMessagePersonEntity.setOperatorUserId(userId);
                messagePersonList.add(baseMessagePersonEntity);
            }
            baseMessagePersonDAO_HI.save(messagePersonList);
        }
    }
}
