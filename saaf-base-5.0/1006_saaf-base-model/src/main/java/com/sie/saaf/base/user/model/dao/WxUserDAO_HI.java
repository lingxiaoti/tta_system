package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.WxUserEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

/**
 * Created by chenzg on 2018/3/7.
 */
@Component("wxUserDAO_HI")
public class WxUserDAO_HI extends BaseCommonDAO_HI<WxUserEntity_HI> {
    public WxUserDAO_HI() {
        super();
    }
}
