package com.sie.watsons.base.proposal.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalTermsLineEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalTermsHeader;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalTermsLine;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ttaProposalTermsLineService")
public class TtaProposalTermsLineService extends CommonAbstractService{
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalTermsLineService.class);

    @Autowired
    private ITtaProposalTermsLine ttaProposalTermsLineServer;

    @Override
    public IBaseCommon getBaseCommonServer(){
        return this.ttaProposalTermsLineServer;
    }

    /**
     * @param params    {
     *                  }
     * @param pageIndex
     * @param pageRows
     * @return
     * @description 查询列表（带分页）
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                       @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {

            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params)) {
                jsonObject = JSON.parseObject(params);
            }
            Pagination<TtaProposalTermsLineEntity_HI_RO> result = ttaProposalTermsLineServer.find(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


}