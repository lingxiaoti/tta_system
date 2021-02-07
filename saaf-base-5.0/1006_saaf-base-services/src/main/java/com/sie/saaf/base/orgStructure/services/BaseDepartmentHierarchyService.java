package com.sie.saaf.base.orgStructure.services;

import com.sie.saaf.base.orgStructure.model.entities.BaseDepartmentHierarchyEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentHierarchyEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseDepartmentHierarchy;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther: huqitao 2018/8/13
 */
@RestController
@RequestMapping("/baseDepartmentHierarchyService")
public class BaseDepartmentHierarchyService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDepartmentHierarchyService.class);
    private final IBaseDepartmentHierarchy baseDepartmentHierarchyServer;

    @Autowired
    public BaseDepartmentHierarchyService(IBaseDepartmentHierarchy baseDepartmentHierarchyServer) {
        this.baseDepartmentHierarchyServer = baseDepartmentHierarchyServer;
    }

    @Override
    public IBaseCommon getBaseCommonServer() {
        return baseDepartmentHierarchyServer;
    }

    /**
     * 生成部门层级关系
     * @param params {}
     * @return 生成结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "departmentHierarchySyn")
    public String departmentHierarchySyn(@RequestParam(required = false) String params) {
        try {
            List<BaseDepartmentHierarchyEntity_HI_RO> ouList = baseDepartmentHierarchyServer.findAllOuId();
            for (BaseDepartmentHierarchyEntity_HI_RO ouInfo : ouList) {
                Integer ouId = ouInfo.getOuId();

                //先删除旧数据
                List<BaseDepartmentHierarchyEntity_HI> deleteList = baseDepartmentHierarchyServer.findByProperty(ouId);
                baseDepartmentHierarchyServer.deleteBaseDepartmentHierarchy(deleteList);

                Integer departmentLevel = 0;
                baseDepartmentHierarchyServer.saveBaseDepartmentHierarchy(ouId, departmentLevel);

                List<BaseDepartmentHierarchyEntity_HI_RO> deptLevelList = baseDepartmentHierarchyServer.findAllDeptLevelByOuId(ouId);
                for (BaseDepartmentHierarchyEntity_HI_RO deptLevelInfo : deptLevelList) {
                    departmentLevel = deptLevelInfo.getDepartmentLevel();
                    if (departmentLevel == 0) {
                        continue;
                    }
                    baseDepartmentHierarchyServer.saveBaseDepartmentHierarchy(ouId, departmentLevel);
                }
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.warn("生成部门层级关系异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }
}
