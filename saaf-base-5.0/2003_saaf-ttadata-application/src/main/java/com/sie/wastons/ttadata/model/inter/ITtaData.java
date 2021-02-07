package com.sie.wastons.ttadata.model.inter;

import com.sie.wastons.ttadata.model.entities.readyonly.UserInfoEntity_RO;

import java.util.List;

public interface ITtaData {
    List<UserInfoEntity_RO> findProccessUsersByuserId(Integer userId, String jobCode);
}
