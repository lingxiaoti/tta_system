package com.sie.watsons.base.productEco.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.product.services.WatonsBpmService;
import com.sie.watsons.base.productEco.model.inter.ICommProcessDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.rmi.ServerException;

@Component("commProcessDealServer")
public class CommProcessDealServer  implements ICommProcessDeal {

    @Autowired
    private  WatonsBpmService watonsBpmService;

    @Async
    @Override
    public void ProcessDeal(String processname, String status, String comments, String name) throws ServerException {
        if (!StringUtils.isEmpty(processname)) {
            String incident = processname.split("&&&")[1];
            String version = processname.split("&&&")[2];
            String taskuser = "10000000";
            String orino = processname.split("&&&")[5];
            String taskId = this.getTaskIdByUser(orino,name);
            if (!taskId.equals("")) { // 存在taskId则进行审批
                JSONObject pa = new JSONObject(true);
                pa.put("Method", "SubmitIncident");
                pa.put("TASKUSER", taskuser);
                pa.put("PROCESSNAME", name);
                pa.put("billNo", orino);
                if (status.equals("Approval")) {
                    pa.put("action", "Approval");
                } else { // 拒绝
                    pa.put("action", "Reject");
                }
                pa.put("COMMENTS", comments);
                pa.put("INCIDENT", incident);
                pa.put("VERSION", version);
                pa.put("TASKID", taskId);
                pa.put("STEPLABEL", "RMS确认");
                if("商品新增流程".equals(name)){
                    watonsBpmService.startBpm(pa.toJSONString());
                }else if("商品修改流程".equals(name)){
                    watonsBpmService.startUpdateBpm(pa.toJSONString());
                }
            }
        }
    }

    @Override
    public String getTaskIdByUser(String billNo,String processname) throws ServerException {
        JSONObject pa = new JSONObject(true);
        pa.put("Method", "GetTaskList");
        pa.put("LISTTYPE", "1");
        pa.put("TASKUSER", "10000000");
        pa.put("PROCESSLIST", processname);
        pa.put("SUMMARY", billNo);

        pa.put("FROM", 1);
        pa.put("TO", 10);
        String result = watonsBpmService.ExecuteBpm(pa);
        JSONObject jsonresult = JSON.parseObject(result);

        JSONArray tasklist = jsonresult.getJSONArray("TASKLIST");
        if (tasklist.size() > 0) {
            JSONObject ja = tasklist.getJSONObject(0);
            return ja.getString("TASKID");
        } else {
            return "";
        }

    }
}
