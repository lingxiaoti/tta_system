package com.sie.saaf.bpm.model.entities.readonly;

public class WatsonTaskDelegateEntity_HI_RO {

    public static final String QUERY_DELEGATE_EMAIL = "  SELECT distinct bp2.email\n" +
            "    FROM act_bpm_task_delegate_config cfg\n" +
            "    inner join base_users bu1 on bu1.user_id= cfg.client_user_id\n" +
            "    inner join base_person bp1 on bp1.person_id = bu1.person_id\n" +
            "    inner join base_users bu2 on bu2.user_id= cfg.delegate_user_id\n" +
            "    inner join base_person bp2 on bp2.person_id = bu2.person_id\n" +
            "   WHERE 1 = 1\n" +
            "     and sysdate between cfg.start_time and cfg.end_time \n";

    public static final String QUERY_PERSON_EMAIL = " SELECT bp.email\n" +
            "       FROM base_person bp where 1=1 \n";

    private String email;

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

}
