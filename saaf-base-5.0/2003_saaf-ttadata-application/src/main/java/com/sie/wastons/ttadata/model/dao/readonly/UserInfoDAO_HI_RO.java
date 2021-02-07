package com.sie.wastons.ttadata.model.dao.readonly;

import com.sie.wastons.ttadata.model.entities.readyonly.UserInfoEntity_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("userInfoDAO_HI_RO")
public class UserInfoDAO_HI_RO extends DynamicViewObjectImpl<UserInfoEntity_RO> {

}
