package com.sie.saaf.base.user.model.inter.server;

import com.sie.saaf.base.user.model.entities.BaseLoginLogEntity_HI;
import com.sie.saaf.base.user.model.inter.IBaseLoginLog;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("baseLoginLogServer")
public class BaseLoginLogServer extends BaseCommonServer<BaseLoginLogEntity_HI> implements IBaseLoginLog {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseLoginLogServer.class);
	@Autowired
	private BaseCommonDAO_HI<BaseLoginLogEntity_HI> baseLoginLogDAO_HI;

//	public BaseLoginLogServer() {
//		super();
//	}

    @Override
    public Date getLastLoginDate(Integer userId){
        Date result = new Date();
        if (userId == null){
            return result;
        }
     /*   return Optional.ofNullable(baseLoginLogDAO_HI.get("from BaseLoginLogEntity_HI where userId=? order by creationDate desc",userId))
                .map(BaseLoginLogEntity_HI::getCreationDate)
                .orElse(new Date());*/
        // 改写查询单条的问题。
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("userId", userId);
        String sql = "select CREATION_DATE  from (select t.creation_date from base_login_log t where t.user_id=:userId order by creation_date desc) where rownum = 1 ";
        List<Map<String,Object>> userList = baseLoginLogDAO_HI.queryNamedSQLForList(sql, queryMap);
        if (userList != null && userList.size() != 0) {
            Object creationDate = userList.get(0).get("CREATION_DATE");
            result = creationDate == null ? result : (Date) creationDate;
        }
        return result;
    }





}
