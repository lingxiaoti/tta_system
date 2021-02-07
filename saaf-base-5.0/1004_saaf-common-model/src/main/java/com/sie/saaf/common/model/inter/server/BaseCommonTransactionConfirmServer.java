package com.sie.saaf.common.model.inter.server;

import com.sie.saaf.common.model.entities.BaseCommonMessageConfirmEntity_HI;
import com.sie.saaf.common.model.entities.BaseCommonTransactionConfirmEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommonTransactionConfirm;
import com.yhg.hibernate.core.dao.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author huangtao
 */
@Component
public class BaseCommonTransactionConfirmServer implements IBaseCommonTransactionConfirm {


    @Autowired
    private ViewObject<BaseCommonTransactionConfirmEntity_HI> baseCommonTransactionConfirmDAO_HI;

    @Autowired
    private  ViewObject<BaseCommonMessageConfirmEntity_HI> baseCommonMessageConfirmDAO_HI;

    @Override
    public BaseCommonTransactionConfirmEntity_HI findByMessageIndex(Integer messageIndex){
        if (messageIndex==null)
            return null;
        List<BaseCommonTransactionConfirmEntity_HI> list= baseCommonTransactionConfirmDAO_HI.findByProperty("messageIndex",messageIndex);
        if (list.size()>0)
            return list.get(0);
        return null;
    }

    @Override
    public void saveMessageIndex(Integer messageIndex){
        if (messageIndex==null)
            return;
        BaseCommonTransactionConfirmEntity_HI entity = new BaseCommonTransactionConfirmEntity_HI();
        entity.setMessageIndex(messageIndex);
        entity.setOperatorUserId(1);
        baseCommonTransactionConfirmDAO_HI.save(entity);
    }

    @Override
    public void saveMessageSendConfirm(Integer messgeIndex){
        Assert.notNull(messgeIndex,"");
        BaseCommonMessageConfirmEntity_HI instance=new BaseCommonMessageConfirmEntity_HI();
        instance.setOperatorUserId(1);
        instance.setMessageIndex(messgeIndex);
        baseCommonMessageConfirmDAO_HI.save(instance);
    }

    @Override
    public List<BaseCommonMessageConfirmEntity_HI> findMessageConfirmByMessageId(Set<String> messageIds){
        if (messageIds ==null || messageIds.isEmpty())
            return Collections.emptyList();
        String splitIds=String.join("','", messageIds);
        StringBuilder sql=new StringBuilder(" from BaseCommonMessageConfirmEntity_HI where messageIndex in ( '").append(splitIds).append("')");
        return baseCommonMessageConfirmDAO_HI.findList(sql);
    }

    @Override
    public void deleteTransactionConfirm(BaseCommonTransactionConfirmEntity_HI transactionConfirm) {
        baseCommonTransactionConfirmDAO_HI.delete(transactionConfirm);
    }

    @Override
    public void deleteTransactionConfirmById(Integer confirmId){
        baseCommonTransactionConfirmDAO_HI.delete(confirmId);
    }
}
