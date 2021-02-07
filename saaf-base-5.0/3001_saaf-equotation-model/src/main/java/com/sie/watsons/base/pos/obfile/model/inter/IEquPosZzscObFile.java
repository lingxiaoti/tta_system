package com.sie.watsons.base.pos.obfile.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.io.IOException;
import java.util.List;
import com.sie.watsons.base.pos.obfile.model.entities.EquPosZzscObFileEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPosZzscObFile extends IBaseCommon<EquPosZzscObFileEntity_HI>{

    JSONObject saveObFactoryFile() throws IOException;

    JSONObject saveFastDFSFile();
}
