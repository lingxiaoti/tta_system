package com.sie.saaf.base.shiro.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.app.event.PermissionCacheSyncEvent;
import com.sie.saaf.base.shiro.model.entities.BaseUserResponsibilityEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseResponsibilityUserEntity_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseUserResponsibility;
import com.sie.saaf.base.shiro.model.inter.server.BaseResponsibilityServer;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.*;

/**
 * @author ZhangJun
 * @creteTime 2017-12-18
 */
@RestController
@RequestMapping("/baseUserResponsibilityService")
public class BaseUserResponsibilityService extends CommonAbstractService {
  private static final Logger logger = LoggerFactory.getLogger(BaseUserResponsibilityService.class);
  @Autowired
  private IBaseUserResponsibility baseUserResponsibilityServer;

  @Autowired
  private BaseResponsibilityServer baseResponsibilityServer;

  @Override
  public IBaseCommon getBaseCommonServer() {
    return baseUserResponsibilityServer;
  }

  /**
   * 查找数据
   *
   * @param params
   * @param pageIndex
   * @param pageRows
   * @return
   * @author ZhangJun
   * @creteTime 2017/12/18
   */
  @RequestMapping(method = RequestMethod.POST, value = "findPagination")
  public String findPagination(@RequestParam(required = false) String params,
                               @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                               @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
    return super.findPagination(params, pageIndex, pageRows);
  }

  /**
   * 保存或更新数据
   *
   * @param params
   * @return
   * @author ZhangJun
   * @creteTime 2017/12/18
   */
  @RequestMapping(method = RequestMethod.POST, value = "save")
  public String saveOrUpdate(@RequestParam(required = true) String params) {
    return super.saveOrUpdate(params);
  }

  /**
   * 删除数据
   *
   * @param params 参数id
   *               {
   *               id:需要删除的数据Id，如果需要删除多个，则用;分隔
   *               }
   * @return
   * @author ZhangJun
   * @creteTime 2017/12/18
   */
  @RequestMapping(method = RequestMethod.POST, value = "delete")
  public String delete(@RequestParam(required = false) String params) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      Integer id = queryParamJSON.getInteger("id");
      BaseUserResponsibilityEntity_HI baseUserResponsibilityEntityHi = baseUserResponsibilityServer.getById(id);

      // 获取当前登陆人的信息
      if (queryParamJSON.getInteger("varUserId") != null){
        List<BaseResponsibilityUserEntity_HI_RO> list = baseResponsibilityServer.findBaseResponsibilityUser(queryParamJSON.getInteger("varUserId"));
        Map<String,BigInteger> map = new HashMap<>();
        list.forEach((baseResponsibilityUser) -> {
          map.put(baseResponsibilityUser.getResponsibilityCode(),baseResponsibilityUser.getResponsibilityId());
        });
        if (map.containsKey("ZHGLY")){
          if (!map.containsValue(BigInteger.valueOf(baseUserResponsibilityEntityHi.getResponsibilityId()))){
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "非此账户管理员有效职责，不允许删除！", 0, null).toString();
          }
        }
      }


      baseUserResponsibilityEntityHi.setEndDateActive(SaafDateUtils.getDate(-1));

      baseUserResponsibilityServer.saveOrUpdate(baseUserResponsibilityEntityHi);
      Set<Integer> userIds = new HashSet<>();
      userIds.add(baseUserResponsibilityEntityHi.getUserId());
      PermissionCacheSyncEvent event = new PermissionCacheSyncEvent("update", userIds);
      SpringBeanUtil.applicationContext.publishEvent(event);
      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
    }
  }

  /**
   * 保存用户职责<br>
   * 当actionType=0时：responsibilityIds只有一个数据<br>
   * 当actionType=1时：userIds只有一个数据
   *
   * @param params 参数<br>
   *               {<br>
   *               responsibilityIds:[1,2,3,4]职责标识,<br>
   *               userIds:[1,2,3,4]用户标识数组,<br>
   *               actionType:操作类型,0:职责添加用户，1：用户添加职责<br>
   *               }
   * @author ZhangJun
   * @createTime 2018/1/8 12:39
   * @description 保存用户职责
   */
  @RequestMapping(method = RequestMethod.POST, value = "saveUserResp")
  public String saveUserResp(@RequestParam(required = false) String params) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      this.baseUserResponsibilityServer.saveUserResp(queryParamJSON);
      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
    }
  }

  //用户职责关系表同步
  @RequestMapping(method = RequestMethod.POST, value = "baseUserResponsibilitySyn")
  public String baseUserResponsibilitySyn(@RequestParam(required = false) String params) {
    try {
      JSONObject queryParamJSON = parseObject(params);
      this.baseUserResponsibilityServer.baseUserResponsibilitySyn(queryParamJSON);
      return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
    }
  }


}
