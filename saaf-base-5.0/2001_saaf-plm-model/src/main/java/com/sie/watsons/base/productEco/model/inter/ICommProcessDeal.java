package com.sie.watsons.base.productEco.model.inter;

import java.rmi.ServerException;

public interface ICommProcessDeal {

    /**
     * 调用流程方法
     * @param processname
     * @param status
     * @param comments
     * @param name
     * @throws ServerException
     */
    void ProcessDeal(String processname, String status,
                     String comments, String name) throws ServerException;
    /**
     * 获取下一节点审批人
     * @param billNo
     * @return
     * @throws ServerException
     */
    String getTaskIdByUser(String billNo,String processname) throws ServerException;

}
