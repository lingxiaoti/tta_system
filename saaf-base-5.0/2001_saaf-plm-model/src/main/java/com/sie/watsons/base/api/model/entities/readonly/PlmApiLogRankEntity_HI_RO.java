package com.sie.watsons.base.api.model.entities.readonly;

/**
 * 排序
 */
public class PlmApiLogRankEntity_HI_RO {


    public static final String SQL = "  select rankView.requestId ,rankView.plmCode ,rankView.itemRank  from ( SELECT pal.item plmCode\n" +
            "        ,pal.request_id requestId\n" +
            "        ,rank() over(PARTITION BY pal.item ORDER BY pal.creation_date desc) AS itemRank\n" +
            "    FROM plm_api_log pal\n" +
            "   WHERE 1 = 1 ) rankView\n" +
            "   where rankView.itemRank =1 ";
    String requestId;
    String plmCode;
    Integer itemRank;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPlmCode() {
        return plmCode;
    }

    public void setPlmCode(String plmCode) {
        this.plmCode = plmCode;
    }

    public Integer getItemRank() {
        return itemRank;
    }

    public void setItemRank(Integer itemRank) {
        this.itemRank = itemRank;
    }
}
