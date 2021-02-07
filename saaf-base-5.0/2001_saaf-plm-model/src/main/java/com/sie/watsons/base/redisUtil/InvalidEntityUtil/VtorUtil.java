package com.sie.watsons.base.redisUtil.InvalidEntityUtil;

import com.alibaba.fastjson.JSON;
import jodd.vtor.Violation;
import jodd.vtor.Vtor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VtorUtil {


    public static void validate(Object obj, String[] profiles) {
        List<String> errMsg = new ArrayList();
        List<Violation> list = null != profiles ? getVtor(obj, profiles).getViolations() : getVtor(obj).getViolations();
        if (null != list) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                Violation violation = (Violation)var4.next();
                errMsg.add(violation.getCheck().getMessage());
            }
            if (!errMsg.isEmpty()) {
                throw new InvalidParamsException(JSON.toJSONString(errMsg));
            }
        }
    }
    private static Vtor getVtor(Object obj, String... profiles) {
        Vtor vtor = new Vtor();
        if (null != profiles && profiles.length > 0) {
            vtor.useProfiles(profiles);
        }
        vtor.validate(obj);
        return vtor;
    }


}
