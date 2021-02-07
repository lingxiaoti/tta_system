package com.sie.saaf.common.model.inter;

import com.sie.saaf.common.model.entities.BaseCommonMessageConfirmEntity_HI;
import com.sie.saaf.common.model.entities.BaseCommonTransactionConfirmEntity_HI;

import java.util.List;
import java.util.Set;

public interface IBaseCommonTransactionConfirm {
    BaseCommonTransactionConfirmEntity_HI findByMessageIndex(Integer messageIndex);
    void saveMessageIndex(Integer messageIndex);

    void saveMessageSendConfirm(Integer messgeIndex);

    List<BaseCommonMessageConfirmEntity_HI> findMessageConfirmByMessageId(Set<String> messageIds);

    void deleteTransactionConfirm(BaseCommonTransactionConfirmEntity_HI transactionConfirm);

    void deleteTransactionConfirmById(Integer confirmId);
}
