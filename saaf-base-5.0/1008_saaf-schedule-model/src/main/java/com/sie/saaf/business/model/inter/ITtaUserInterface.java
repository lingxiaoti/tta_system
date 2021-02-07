package com.sie.saaf.business.model.inter;

import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;
import java.util.Map;

/**
 * @author hmb
 * @date 2019/8/15 16:14
 */
public interface ITtaUserInterface extends IBaseCommon<Object> {
    public void saveJdbcBatchObject(final String tableName, List<Map<String,Object>> list);

    void updateUserInterfaceInfo();

    /**
     *将user_interface_in接口表中的数据推送到base_users
     */
    void callProUpdateTtaUserInterface();

    /**
     * 删除
     */
    void deleteTtaUserInterface();
}
