package com.sie.saaf.business.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.sie.saaf.business.model.dao.UserInterfaceInDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.saaf.business.model.entities.UserInterfaceInEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.business.model.inter.IUserInterfaceIn;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("userInterfaceInServer")
public class UserInterfaceInServer extends BaseCommonServer<UserInterfaceInEntity_HI> implements IUserInterfaceIn{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserInterfaceInServer.class);

	@Autowired
	private ViewObject<UserInterfaceInEntity_HI> userInterfaceInDAO_HI;

	@Autowired
	private UserInterfaceInDAO_HI userInterfaceInDAOHi;

	public UserInterfaceInServer() {
		super();
	}

	@Override
	public void saveOrUpdateBatchUserInterfaceIn(LinkedHashSet<UserInterfaceInEntity_HI> reportList) {
		userInterfaceInDAO_HI.saveOrUpdateAll(reportList);
	}

	//调用存储过程,有就更新,没有就插入
    @Override
    public void callProUpdateTtaUserInterfaceIn() {
		//userInterfaceInDAOHi.callProName("存储名字");
    }

	@Override
	public void updateUserInterfaceIn() throws Exception{

		//这条sql有问题,不能使用
		//注释说明:这里的group_code,group_name可能存的值不对,更新base_users的信息,用于EP系统登录用的,没有密码
		String sql = "merge into \n" +
				" base_users bu\n" +
				" using user_interface_in uii\n" +
				" on (bu.user_name = uii.employee_no)\n" +
				" when matched then\n" +
				"     update set bu.job_status = uii.status\n" +
				" when not matched then\n" +
				"   insert (bu.user_id,bu.name_pingyin,bu.user_name,bu.user_full_name,bu.job_status,bu.group_code,bu.group_name)\n" +
				"   values(seq_base_users.nextval,uii.pinyin_name,uii.employee_no,uii.chinese_name,uii.status,uii.dept_no,uii.department)  ";

		userInterfaceInDAO_HI.executeSqlUpdate(sql);
	}
}
