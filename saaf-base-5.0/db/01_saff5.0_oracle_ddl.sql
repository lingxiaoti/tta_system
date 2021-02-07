/* DBMS name:      ORACLE Version 11g */
drop table act_bpm_category;
drop table act_bpm_communicate;
drop table act_bpm_config;
drop table act_bpm_exception;
drop table act_bpm_list;
drop table act_bpm_notify_task;
drop table act_bpm_permission;
drop table act_bpm_role;
drop table act_bpm_task_config;
drop table act_bpm_task_delegate;
drop table act_bpm_task_delegate_config;
drop table act_bpm_task_leavel;
drop table act_bpm_task_processer;
drop table act_bpm_task_urge_config;
drop table act_evt_log;
drop table act_ge_bytearray;
drop table act_ge_property;
drop table act_hi_actinst;
drop table act_hi_attachment;
drop table act_hi_comment;
drop table act_hi_detail;
drop table act_hi_identitylink;
drop table act_hi_procinst;
drop table act_hi_taskinst;
drop table act_hi_varinst;
drop table act_id_group;
drop table act_id_info;
drop table act_id_membership;
drop table act_id_user;
drop table act_procdef_info;
drop table act_re_deployment;
drop table act_re_model;
drop table act_re_procdef;
drop table act_ru_event_subscr;
drop table act_ru_execution;
drop table act_ru_identitylink;
drop table act_ru_job;
drop table act_ru_task;
drop table act_ru_variable;
drop table admin_area_info;
drop table base_access_basedata;
drop table base_adminstrative_region;
drop table base_api_center_h;
drop table base_api_center_l;
drop table base_api_management;
drop table base_app_auth_contain;
drop table base_app_auth_exclude;
drop table base_app_deployee_info;
drop table base_app_deployee_menu;
drop table base_attachment;
drop table base_button_data;
drop table base_button_data_language;
drop table base_channel;
drop table base_channel_privilege;
drop table base_cms_article;
drop table base_cms_carousel;
drop table base_cms_carousel_city;
drop table base_cms_category;
drop table base_cms_web_notice;
drop table base_cms_web_notice_city;
drop table base_common_message_confirm;
drop table base_common_txn_confirm;
drop table base_cust_pda_user_relation;
drop table base_data_export;
drop table base_department;
drop table base_department_cust;
drop table base_department_hierarchy;
drop table base_deployee_app_host_info;
drop table base_deployee_app_info;
drop table base_deployee_app_menu;
drop table base_export_record;
drop table base_faq_help;
drop table base_function_collection;
drop table base_function_collection_user;
drop table base_inv_store_contact_t;
drop table base_inv_store_t;
drop table base_issue_feedback;
drop table base_job;
drop table base_login_log;
drop table base_lookup_types;
drop table base_lookup_types_lan;
drop table base_lookup_values;
drop table base_lookup_values_lan;
drop table base_manual_supplement;
drop table base_menu;
drop table base_menu_language;
drop table base_message_bu;
drop table base_message_content;
drop table base_message_department;
drop table base_message_instation;
drop table base_message_person;
drop table base_org_responsibility;
drop table base_organization;
drop table base_pda_inv_cfg;
drop table base_pda_role_cfg;
drop table base_person;
drop table base_person_assign;
drop table base_person_cust;
drop table base_person_level;
drop table base_person_organization;
drop table base_position;
drop table base_privilege_detail;
drop table base_privilege_record;
drop table base_product_info;
drop table base_product_inv;
drop table base_profile;
drop table base_profile_value;
drop table base_profile_value_lan;
drop table base_publish_app_info;
drop table base_report_charts_type;
drop table base_report_datasource;
drop table base_report_group;
drop table base_report_group_header;
drop table base_report_header;
drop table base_report_line;
drop table base_request_log;
drop table base_resource;
drop table base_responsibility;
drop table base_responsibility_lan;
drop table base_responsibility_role;
drop table base_responsibility_role_lan;
drop table base_role;
drop table base_role_language;
drop table base_role_menu;
drop table base_role_resource;
drop table base_system_sso;
drop table base_task_collection;
drop table base_user_responsibility;
drop table base_users;
drop table base_users_language;
drop table base_warehouse_mapping;
drop table base_wechat_mng;
drop table cux_cdm_access_basedata;
drop table demo;
drop table domain_wx;
drop table ew_configuration_head;
drop table ew_configuration_line;
drop table ew_configuration_man;
drop table ew_handle;
drop table msg_cfg;
drop table msg_history;
drop table msg_instance;
drop table msg_log;
drop table msg_receive_sql;
drop table msg_source_cfg;
drop table msg_td;
drop table msg_temple_cfg;
drop table msg_user;
drop table page_model_group_detail;
drop table page_model_group_info;
drop table page_model_info;
drop table per_all_assignments_f;
drop table per_all_people_f;
drop table pub_users_organization;
drop table qrtz_blob_triggers;
drop table qrtz_calendars;
drop table qrtz_cron_triggers;
drop table qrtz_fired_triggers;
drop table qrtz_job_details;
drop table qrtz_locks;
drop table qrtz_paused_trigger_grps;
drop table qrtz_scheduler_state;
drop table qrtz_simple_triggers;
drop table qrtz_simprop_triggers;
drop table qrtz_triggers;
drop table rule_business_line;
drop table rule_dim;
drop table rule_expression;
drop table rule_expressiondim;
drop table rule_mapping_business;
drop table saaf_file_upload;
drop table saaf_webservice_info;
drop table saaf_webservice_param_info;
drop table schedule_job_access_orgs;
drop table schedule_job_parameters;
drop table schedule_job_resp;
drop table schedule_jobs;
drop table schedule_log;
drop table schedule_schedules;
drop table schedule_schedules_error;
drop table user_pwd_temp;
drop table wx_user;
drop table z_test;
drop table base_function_collection_lan;
drop table base_common_transaction_confir;
drop table base_department_lan;
drop table base_job_lan;
drop table base_organization_lan;
drop table base_person_lan;
drop table base_position_lan;
drop table base_profile_lan;
drop table base_report_charts_type_lan;
drop table base_report_datasource_lan;
drop table base_resource_lan;


/*
 * act_bpm_list 数据过大
 *
 **/

/*========================create start===============================*/
/*==============================================================*/
/* Table: act_bpm_category                                    */
/*==============================================================*/
create table act_bpm_category 
(
   category_id        INTEGER            not null,
   category_code      VARCHAR2(255),
   category_name      VARCHAR2(64),
   parent_id          INTEGER              default -1 not null,
   process_key        VARCHAR2(64),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              not null,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_CATEGORY primary key (category_id)
);


/*==============================================================*/
/* Table: act_bpm_communicate                                 */
/*==============================================================*/
create table act_bpm_communicate 
(
   communicate_id     INTEGER            not null,
   type               VARCHAR2(16),
   content            VARCHAR2(4000),
   proc_inst_id       VARCHAR2(64),
   proc_def_key       VARCHAR2(64),
   task_id            VARCHAR2(64),
   task_name          VARCHAR2(64),
   receiver_id        INTEGER              default NULL,
   message_channel    VARCHAR2(255),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   attachment_url     VARCHAR2(255),
   constraint PK_ACT_BPM_COMMUNICATE primary key (communicate_id)
);

create index idx_bpm_type on act_bpm_communicate(type);
create index idx_bpm_procinstid on act_bpm_communicate(proc_inst_id);
create index idx_bpm_procdefkey on act_bpm_communicate(proc_def_key);
create index idx_bpm_taskid on act_bpm_communicate(task_id);
create index idx_bpm_receiver_id on act_bpm_communicate(receiver_id);
create index idx_bpm_created_by on act_bpm_communicate(created_by);


/*==============================================================*/
/* Table: act_bpm_config                                      */
/*==============================================================*/
create table act_bpm_config 
(
   config_id          INTEGER            not null,
   proc_def_key       VARCHAR2(64),
   coding_rule        VARCHAR2(255),
   title_format       VARCHAR2(255),
   cc_title_format    VARCHAR2(255),
   cc_content_format  VARCHAR2(1024),
   urge_title_format  VARCHAR2(255),
   urge_content_format VARCHAR2(1024),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              not null,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_CONFIG primary key (config_id)
);

/*==============================================================*/
/* Table: act_bpm_exception                                   */
/*==============================================================*/
create table act_bpm_exception 
(
   exception_id       INTEGER            not null,
   type               VARCHAR2(16),
   content            VARCHAR2(4000),
   proc_inst_id       VARCHAR2(64),
   proc_def_key       VARCHAR2(64),
   task_id            VARCHAR2(64),
   task_name          VARCHAR2(64),
   status             INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_EXCEPTION primary key (exception_id)
);
create index idx_bpm_exception_created_by	on act_bpm_exception(created_by);
create index idx_bpm_exception_procinstid	on act_bpm_exception(proc_inst_id);
create index idx_bpm_exception_procdefkey   on act_bpm_exception(proc_def_key);
create index idx_bpm_exception_taskid on act_bpm_exception(task_id);


/*==============================================================*/
/* Table: act_bpm_list                                        */
/*==============================================================*/
create table act_bpm_list 
(
   list_id            INTEGER            not null,
   list_code          VARCHAR2(64),
   list_name          VARCHAR2(64),
   category_id        INTEGER              default NULL,
   title              VARCHAR2(64),
   description        VARCHAR2(255),
   variables          CLOB,
   properties         CLOB,
   extend             CLOB,
   start_time         DATE                 default NULL,
   end_time           DATE                 default NULL,
   result             VARCHAR2(16),
   status             INTEGER              default NULL,
   business_key       VARCHAR2(64),
   bill_no            VARCHAR2(128),
   proc_def_id        VARCHAR2(64),
   proc_def_key       VARCHAR2(64),
   proc_inst_id       VARCHAR2(64),
   position_id        INTEGER              default NULL,
   apply_position_id  INTEGER              default NULL,
   responsibility_id  INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   org_id             INTEGER              default NULL,
   user_type          VARCHAR2(64),
   role_type          VARCHAR2(64),
   cust_account_id    INTEGER              default NULL,
   department_id      INTEGER              default NULL,
   apply_person_id    INTEGER              default NULL,
   constraint PK_ACT_BPM_LIST primary key (list_id)
);

comment on column act_bpm_list.apply_position_id is
'申请人职位ID';

comment on column act_bpm_list.cust_account_id is
'经销商ID';

comment on column act_bpm_list.department_id is
'部门ID';

comment on column act_bpm_list.apply_person_id is
'申请人ID';
create index idx_bpm_list_businesskey on act_bpm_list(business_key);
create index idx_bpm_list_listcode	  on act_bpm_list(list_code);
create index idx_bpm_list_categoryid  on act_bpm_list(category_id);
create index idx_bpm_list_titel	on act_bpm_list(title);
create index idx_bpm_list_procinstid  on act_bpm_list(proc_inst_id);
create index idx_bpm_list_createdby	  on act_bpm_list(created_by);


/*==============================================================*/
/* Table: act_bpm_notify_task                                 */
/*==============================================================*/
create table act_bpm_notify_task 
(
   notify_id          INTEGER            not null,
   title              VARCHAR2(64),
   content            VARCHAR2(1024),
   proc_inst_id       VARCHAR2(64),
   proc_def_key       VARCHAR2(64),
   task_id            VARCHAR2(64),
   task_name          VARCHAR2(64),
   receiver_id        INTEGER              default NULL,
   read_time          DATE                 default NULL,
   status             INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_NOTIFY_TASK primary key (notify_id)
);

/*==============================================================*/
/* Table: act_bpm_permission                                  */
/*==============================================================*/
create table act_bpm_permission 
(
   permission_id      INTEGER            not null,
   proc_def_key       VARCHAR2(64),
   system_code        VARCHAR2(30),
   ou_id              INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_PERMISSION primary key (permission_id)
);

/*==============================================================*/
/* Table: act_bpm_role                                        */
/*==============================================================*/
create table act_bpm_role 
(
   role_id            INTEGER            not null,
   role_key           VARCHAR2(32),
   role_name          VARCHAR2(64),
   system_code        VARCHAR2(30),
   handler_expression_type VARCHAR2(16),
   handler_expression VARCHAR2(255),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              not null,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_ROLE primary key (role_id)
);

/*==============================================================*/
/* Table: act_bpm_task_config                                 */
/*==============================================================*/
create table act_bpm_task_config 
(
   config_id          INTEGER            not null,
   proc_def_key       VARCHAR2(64),
   task_def_id        VARCHAR2(64),
   task_name          VARCHAR2(255),
   pcform_url         VARCHAR2(255),
   mobform_url        VARCHAR2(255),
   pc_data_url        VARCHAR2(255),
   mob_data_url       VARCHAR2(255),
   processer_ids      VARCHAR2(255),
   processer_role_keys VARCHAR2(255),
   cc_ids             VARCHAR2(255),
   cc_role_keys       VARCHAR2(255),
   variables          CLOB,
   callback_url       VARCHAR2(255),
   edit_status        INTEGER              default NULL,
   enable_autoJump    INTEGER              default NULL,
   extend             CLOB,
   sort_id            INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              not null,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_TASK_CONFIG primary key (config_id)
);

/*==============================================================*/
/* Table: act_bpm_task_delegate                               */
/*==============================================================*/
create table act_bpm_task_delegate 
(
   delegate_id        INTEGER            not null,
   task_id            VARCHAR2(64),
   task_name          VARCHAR2(64),
   proc_def_key       VARCHAR2(64),
   proc_inst_id       VARCHAR2(64),
   client_user_id     INTEGER              not null,
   delegate_user_id   INTEGER              default NULL,
   task_time          DATE                 default NULL,
   start_time         DATE                 default NULL,
   end_time           DATE                 default NULL,
   status             VARCHAR2(16),
   is_auto            INTEGER              default 0,
   config_id          INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              not null,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_TASK_DELEGATE primary key (delegate_id)
);

/*==============================================================*/
/* Table: act_bpm_task_delegate_config                        */
/*==============================================================*/
create table act_bpm_task_delegate_config 
(
   config_id          INTEGER            not null,
   client_user_id     INTEGER              not null,
   delegate_user_id   INTEGER              not null,
   category_ids       VARCHAR2(255),
   proc_def_keys      VARCHAR2(1024),
   system_code        VARCHAR2(30),
   start_time         DATE                 default NULL,
   end_time           DATE                 default NULL,
   disabled           INTEGER              default 0,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              not null,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_TASK_DELEGATE_CONFI primary key (config_id)
);

/*==============================================================*/
/* Table: act_bpm_task_leavel                                 */
/*==============================================================*/
create table act_bpm_task_leavel 
(
   task_id            VARCHAR2(64)         not null,
   parent_task_id     VARCHAR2(64),
   top_task_id        VARCHAR2(64),
   top_proc_inst_id   VARCHAR2(64),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              not null,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_TASK_LEAVEL primary key (task_id)
);
create index idx_parenttaskid	on act_bpm_task_leavel(parent_task_id);
create index idx_toptaskid	on act_bpm_task_leavel(top_task_id);
create index idx_topprocinstid on act_bpm_task_leavel(top_proc_inst_id);



/*==============================================================*/
/* Table: act_bpm_task_processer                              */
/*==============================================================*/
create table act_bpm_task_processer 
(
   processer_id       INTEGER            not null,
   config_id          INTEGER              not null,
   processer_ids      VARCHAR2(1024),
   processer_role_keys VARCHAR2(1024),
   sort_id            INTEGER              default NULL,
   disabled           SMALLINT             default 0 not null,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              not null,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_TASK_PROCESSER primary key (processer_id)
);

/*==============================================================*/
/* Table: act_bpm_task_urge_config                            */
/*==============================================================*/
create table act_bpm_task_urge_config 
(
   config_id          INTEGER            not null,
   proc_def_key       VARCHAR2(64),
   start_time         DATE                 default NULL,
   end_time           DATE                 default NULL,
   timeout            INTEGER              default NULL,
   timegap            INTEGER              default NULL,
   disabled           INTEGER              default 0,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 not null,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              not null,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_ACT_BPM_TASK_URGE_CONFIG primary key (config_id)
);

/*==============================================================*/
/* Table: act_evt_log                                         */
/*==============================================================*/
create table act_evt_log 
(
   LOG_NR_              INTEGER            not null,
   TYPE_                VARCHAR2(64),
   PROC_DEF_ID_         VARCHAR2(64),
   PROC_INST_ID_        VARCHAR2(64),
   EXECUTION_ID_        VARCHAR2(64),
   TASK_ID_             VARCHAR2(64),
   TIME_STAMP_          TIMESTAMP            default sysdate not null,
   USER_ID_             VARCHAR2(255),
   DATA_                BLOB,
   LOCK_OWNER_          VARCHAR2(255),
   LOCK_TIME_           TIMESTAMP            default NULL,
   IS_PROCESSED_        SMALLINT             default 0,
   constraint PK_ACT_EVT_LOG primary key (LOG_NR_)
);

/*==============================================================*/
/* Table: act_ge_bytearray                                    */
/*==============================================================*/
create table act_ge_bytearray 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   NAME_                VARCHAR2(255),
   DEPLOYMENT_ID_       VARCHAR2(64),
   BYTES_               BLOB,
   GENERATED_           SMALLINT             default NULL,
   constraint PK_ACT_GE_BYTEARRAY primary key (ID_)
);

/*==============================================================*/
/* Table: act_ge_property                                     */
/*==============================================================*/
create table act_ge_property 
(
   NAME_                VARCHAR2(64)         not null,
   VALUE_               VARCHAR2(300),
   REV_                 INTEGER              default NULL,
   constraint PK_ACT_GE_PROPERTY primary key (NAME_)
);

/*==============================================================*/
/* Table: act_hi_actinst                                      */
/*==============================================================*/
create table act_hi_actinst 
(
   ID_                  VARCHAR2(64)         not null,
   PROC_DEF_ID_         VARCHAR2(64),
   PROC_INST_ID_        VARCHAR2(64),
   EXECUTION_ID_        VARCHAR2(64),
   ACT_ID_              VARCHAR2(255),
   TASK_ID_             VARCHAR2(64),
   CALL_PROC_INST_ID_   VARCHAR2(64),
   ACT_NAME_            VARCHAR2(255),
   ACT_TYPE_            VARCHAR2(255),
   ASSIGNEE_            VARCHAR2(255),
   START_TIME_          DATE,
   END_TIME_            DATE,
   DURATION_            INTEGER              default NULL,
   TENANT_ID_           VARCHAR2(255),
   constraint PK_ACT_HI_ACTINST primary key (ID_)
);
create index ACT_IDX_HI_ACT_INST_START	on act_hi_actinst(START_TIME_);
create index ACT_IDX_HI_ACT_INST_END	on act_hi_actinst(END_TIME_);
create index ACT_IDX_HI_ACT_INST_PROCINST	on act_hi_actinst(PROC_INST_ID_, ACT_ID_);
create index ACT_IDX_HI_ACT_INST_EXEC	on act_hi_actinst(EXECUTION_ID_, ACT_ID_);



/*==============================================================*/
/* Table: act_hi_attachment                                   */
/*==============================================================*/
create table act_hi_attachment 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   USER_ID_             VARCHAR2(255),
   NAME_                VARCHAR2(255),
   DESCRIPTION_         VARCHAR2(4000),
   TYPE_                VARCHAR2(255),
   TASK_ID_             VARCHAR2(64),
   PROC_INST_ID_        VARCHAR2(64),
   URL_                 VARCHAR2(4000),
   CONTENT_ID_          VARCHAR2(64),
   TIME_                DATE,
   constraint PK_ACT_HI_ATTACHMENT primary key (ID_)
);

/*==============================================================*/
/* Table: act_hi_comment                                      */
/*==============================================================*/
create table act_hi_comment 
(
   ID_                  VARCHAR2(64)         not null,
   TYPE_                VARCHAR2(255),
   TIME_                DATE,
   USER_ID_             VARCHAR2(255),
   TASK_ID_             VARCHAR2(64),
   PROC_INST_ID_        VARCHAR2(64),
   ACTION_              VARCHAR2(255),
   MESSAGE_             VARCHAR2(4000),
   FULL_MSG_            BLOB,
   constraint PK_ACT_HI_COMMENT primary key (ID_)
);

/*==============================================================*/
/* Table: act_hi_detail                                       */
/*==============================================================*/
create table act_hi_detail 
(
   ID_                  VARCHAR2(64)         not null,
   TYPE_                VARCHAR2(255),
   PROC_INST_ID_        VARCHAR2(64),
   EXECUTION_ID_        VARCHAR2(64),
   TASK_ID_             VARCHAR2(64),
   ACT_INST_ID_         VARCHAR2(64),
   NAME_                VARCHAR2(255),
   VAR_TYPE_            VARCHAR2(255),
   REV_                 INTEGER              default NULL,
   TIME_                DATE,
   BYTEARRAY_ID_        VARCHAR2(64),
   DOUBLE_              BINARY_DOUBLE        default NULL,
   LONG_                INTEGER              default NULL,
   TEXT_                VARCHAR2(4000),
   TEXT2_               VARCHAR2(4000),
   constraint PK_ACT_HI_DETAIL primary key (ID_)
);
create index ACT_IDX_HI_DETAIL_PROC_INST	on act_hi_detail(PROC_INST_ID_);
create index ACT_IDX_HI_DETAIL_ACT_INST	on act_hi_detail(ACT_INST_ID_);
create index ACT_IDX_HI_DETAIL_TIME	on act_hi_detail(TIME_);
create index ACT_IDX_HI_DETAIL_NAME	on act_hi_detail(NAME_);
create index ACT_IDX_HI_DETAIL_TASK_ID	on act_hi_detail(TASK_ID_);


/*==============================================================*/
/* Table: act_hi_identitylink                                 */
/*==============================================================*/
create table act_hi_identitylink 
(
   ID_                  VARCHAR2(64)         not null,
   GROUP_ID_            VARCHAR2(255),
   TYPE_                VARCHAR2(255),
   USER_ID_             VARCHAR2(255),
   TASK_ID_             VARCHAR2(64),
   PROC_INST_ID_        VARCHAR2(64),
   constraint PK_ACT_HI_IDENTITYLINK primary key (ID_)
);
create index ACT_IDX_HI_IDENT_LNK_USER	on act_hi_identitylink(USER_ID_);
create index ACT_IDX_HI_IDENT_LNK_TASK	on act_hi_identitylink(TASK_ID_);
create index ACT_IDX_HI_IDENT_LNK_PROCINST	on act_hi_identitylink(PROC_INST_ID_);


/*==============================================================*/
/* Table: act_hi_procinst                                     */
/*==============================================================*/
create table act_hi_procinst 
(
   ID_                  VARCHAR2(64)         not null,
   PROC_INST_ID_        VARCHAR2(64),
   BUSINESS_KEY_        VARCHAR2(255),
   PROC_DEF_ID_         VARCHAR2(64),
   START_TIME_          DATE,
   END_TIME_            DATE,
   DURATION_            INTEGER              default NULL,
   START_USER_ID_       VARCHAR2(255),
   START_ACT_ID_        VARCHAR2(255),
   END_ACT_ID_          VARCHAR2(255),
   SUPER_PROCESS_INSTANCE_ID_ VARCHAR2(64),
   DELETE_REASON_       VARCHAR2(4000),
   TENANT_ID_           VARCHAR2(255),
   NAME_                VARCHAR2(255),
   constraint PK_ACT_HI_PROCINST primary key (ID_)
);
create unique index proc_inst_id_ on act_hi_procinst (PROC_INST_ID_);
create index ACT_IDX_HI_PRO_INST_END on act_hi_procinst (END_TIME_);
create index  ACT_IDX_HI_PRO_I_BUSKEY on act_hi_procinst (BUSINESS_KEY_);

/*==============================================================*/
/* Table: act_hi_taskinst                                     */
/*==============================================================*/
create table act_hi_taskinst 
(
   ID_                  VARCHAR2(64)         not null,
   PROC_DEF_ID_         VARCHAR2(64),
   TASK_DEF_KEY_        VARCHAR2(255),
   PROC_INST_ID_        VARCHAR2(64),
   EXECUTION_ID_        VARCHAR2(64),
   NAME_                VARCHAR2(255),
   PARENT_TASK_ID_      VARCHAR2(64),
   DESCRIPTION_         VARCHAR2(4000),
   OWNER_               VARCHAR2(255),
   ASSIGNEE_            VARCHAR2(255),
   START_TIME_          DATE,
   CLAIM_TIME_          DATE,
   END_TIME_            DATE,
   DURATION_            INTEGER              default NULL,
   DELETE_REASON_       VARCHAR2(4000),
   PRIORITY_            INTEGER              default NULL,
   DUE_DATE_            DATE,
   FORM_KEY_            VARCHAR2(255),
   CATEGORY_            VARCHAR2(255),
   TENANT_ID_           VARCHAR2(255),
   constraint PK_ACT_HI_TASKINST primary key (ID_)
);

create index ACT_IDX_HI_TASK_INST_PROCINST on act_hi_taskinst (PROC_INST_ID_);

/*==============================================================*/
/* Table: act_hi_varinst                                      */
/*==============================================================*/
create table act_hi_varinst 
(
   ID_                  VARCHAR2(64)         not null,
   PROC_INST_ID_        VARCHAR2(64),
   EXECUTION_ID_        VARCHAR2(64),
   TASK_ID_             VARCHAR2(64),
   NAME_                VARCHAR2(255),
   VAR_TYPE_            VARCHAR2(100),
   REV_                 INTEGER              default NULL,
   BYTEARRAY_ID_        VARCHAR2(64),
   DOUBLE_              BINARY_DOUBLE        default NULL,
   LONG_                INTEGER              default NULL,
   TEXT_                VARCHAR2(4000),
   TEXT2_               VARCHAR2(4000),
   CREATE_TIME_         DATE,
   LAST_UPDATED_TIME_   DATE,
   constraint PK_ACT_HI_VARINST primary key (ID_)
);

create index ACT_IDX_HI_PROCVAR_PROC_INST on act_hi_varinst (PROC_INST_ID_);
create index ACT_IDX_HI_PROCVAR_NAME_TYPE on act_hi_varinst (NAME_, VAR_TYPE_);
create index ACT_IDX_HI_PROCVAR_TASK_ID on act_hi_varinst (TASK_ID_);

/*==============================================================*/
/* Table: act_id_group                                        */
/*==============================================================*/
create table act_id_group 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   NAME_                VARCHAR2(255),
   TYPE_                VARCHAR2(255),
   constraint PK_ACT_ID_GROUP primary key (ID_)
);

/*==============================================================*/
/* Table: act_id_info                                         */
/*==============================================================*/
create table act_id_info 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   USER_ID_             VARCHAR2(64),
   TYPE_                VARCHAR2(64),
   KEY_                 VARCHAR2(255),
   VALUE_               VARCHAR2(255),
   PASSWORD_            BLOB,
   PARENT_ID_           VARCHAR2(255),
   constraint PK_ACT_ID_INFO primary key (ID_)
);

/*==============================================================*/
/* Table: act_id_membership                                   */
/*==============================================================*/
create table act_id_membership 
(
   USER_ID_             VARCHAR2(64)         not null,
   GROUP_ID_            VARCHAR2(64)         not null,
   constraint PK_ACT_ID_MEMBERSHIP primary key (USER_ID_, GROUP_ID_)
);

/*==============================================================*/
/* Table: act_id_user                                         */
/*==============================================================*/
create table act_id_user 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   FIRST_               VARCHAR2(255),
   LAST_                VARCHAR2(255),
   EMAIL_               VARCHAR2(255),
   PWD_                 VARCHAR2(255),
   PICTURE_ID_          VARCHAR2(64),
   constraint PK_ACT_ID_USER primary key (ID_)
);

/*==============================================================*/
/* Table: act_procdef_info                                    */
/*==============================================================*/
create table act_procdef_info 
(
   ID_                  VARCHAR2(64)         not null,
   PROC_DEF_ID_         VARCHAR2(64),
   REV_                 INTEGER              default NULL,
   INFO_JSON_ID_        VARCHAR2(64),
   constraint PK_ACT_PROCDEF_INFO primary key (ID_)
);
create unique index ACT_UNIQ_INFO_PROCDEF on act_procdef_info (PROC_DEF_ID_);

/*==============================================================*/
/* Table: act_re_deployment                                   */
/*==============================================================*/
create table act_re_deployment 
(
   ID_                  VARCHAR2(64)         not null,
   NAME_                VARCHAR2(255),
   CATEGORY_            VARCHAR2(255),
   TENANT_ID_           VARCHAR2(255),
   DEPLOY_TIME_         TIMESTAMP            default NULL,
   constraint PK_ACT_RE_DEPLOYMENT primary key (ID_)
);

/*==============================================================*/
/* Table: act_re_model                                        */
/*==============================================================*/
create table act_re_model 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   NAME_                VARCHAR2(255),
   KEY_                 VARCHAR2(255),
   CATEGORY_            VARCHAR2(255),
   CREATE_TIME_         TIMESTAMP            default NULL,
   LAST_UPDATE_TIME_    TIMESTAMP            default NULL,
   VERSION_             INTEGER              default NULL,
   META_INFO_           VARCHAR2(4000),
   DEPLOYMENT_ID_       VARCHAR2(64),
   EDITOR_SOURCE_VALUE_ID_ VARCHAR2(64),
   EDITOR_SOURCE_EXTRA_VALUE_ID_ VARCHAR2(64),
   TENANT_ID_           VARCHAR2(255),
   constraint PK_ACT_RE_MODEL primary key (ID_)
);

/*==============================================================*/
/* Table: act_re_procdef                                      */
/*==============================================================*/
create table act_re_procdef 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   CATEGORY_            VARCHAR2(255),
   NAME_                VARCHAR2(255),
   KEY_                 VARCHAR2(255),
   VERSION_             INTEGER              not null,
   DEPLOYMENT_ID_       VARCHAR2(64),
   RESOURCE_NAME_       VARCHAR2(4000),
   DGRM_RESOURCE_NAME_  VARCHAR2(4000),
   DESCRIPTION_         VARCHAR2(4000),
   HAS_START_FORM_KEY_  SMALLINT             default NULL,
   HAS_GRAPHICAL_NOTATION_ SMALLINT             default NULL,
   SUSPENSION_STATE_    INTEGER              default NULL,
   TENANT_ID_           VARCHAR2(255),
   constraint PK_ACT_RE_PROCDEF primary key (ID_)
);
create unique index ACT_UNIQ_PROCDEF on act_re_procdef (KEY_, VERSION_, TENANT_ID_);

/*==============================================================*/
/* Table: act_ru_event_subscr                                 */
/*==============================================================*/
create table act_ru_event_subscr 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   EVENT_TYPE_          VARCHAR2(255),
   EVENT_NAME_          VARCHAR2(255),
   EXECUTION_ID_        VARCHAR2(64),
   PROC_INST_ID_        VARCHAR2(64),
   ACTIVITY_ID_         VARCHAR2(64),
   CONFIGURATION_       VARCHAR2(255),
   CREATED_             TIMESTAMP            default sysdate not null,
   PROC_DEF_ID_         VARCHAR2(64),
   TENANT_ID_           VARCHAR2(255),
   constraint PK_ACT_RU_EVENT_SUBSCR primary key (ID_)
);

/*==============================================================*/
/* Table: act_ru_execution                                    */
/*==============================================================*/
create table act_ru_execution 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   PROC_INST_ID_        VARCHAR2(64),
   BUSINESS_KEY_        VARCHAR2(255),
   PARENT_ID_           VARCHAR2(64),
   PROC_DEF_ID_         VARCHAR2(64),
   SUPER_EXEC_          VARCHAR2(64),
   ACT_ID_              VARCHAR2(255),
   IS_ACTIVE_           SMALLINT             default NULL,
   IS_CONCURRENT_       SMALLINT             default NULL,
   IS_SCOPE_            SMALLINT             default NULL,
   IS_EVENT_SCOPE_      SMALLINT             default NULL,
   SUSPENSION_STATE_    INTEGER              default NULL,
   CACHED_ENT_STATE_    INTEGER              default NULL,
   TENANT_ID_           VARCHAR2(255),
   NAME_                VARCHAR2(255),
   LOCK_TIME_           TIMESTAMP            default NULL,
   constraint PK_ACT_RU_EXECUTION primary key (ID_)
);

create index ACT_IDX_EXEC_BUSKEY on act_ru_execution (BUSINESS_KEY_);

/*==============================================================*/
/* Table: act_ru_identitylink                                 */
/*==============================================================*/
create table act_ru_identitylink 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   GROUP_ID_            VARCHAR2(255),
   TYPE_                VARCHAR2(255),
   USER_ID_             VARCHAR2(255),
   TASK_ID_             VARCHAR2(64),
   PROC_INST_ID_        VARCHAR2(64),
   PROC_DEF_ID_         VARCHAR2(64),
   constraint PK_ACT_RU_IDENTITYLINK primary key (ID_)
);

create index ACT_IDX_IDENT_LNK_USER on act_ru_identitylink (USER_ID_);
create index ACT_IDX_IDENT_LNK_GROUP on act_ru_identitylink (GROUP_ID_);
create index ACT_IDX_ATHRZ_PROCEDEF on act_ru_identitylink (PROC_DEF_ID_);

/*==============================================================*/
/* Table: act_ru_job                                          */
/*==============================================================*/
create table act_ru_job 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   TYPE_                VARCHAR2(255),
   LOCK_EXP_TIME_       TIMESTAMP            default NULL,
   LOCK_OWNER_          VARCHAR2(255),
   EXCLUSIVE_           SMALLINT             default NULL,
   EXECUTION_ID_        VARCHAR2(64),
   PROCESS_INSTANCE_ID_ VARCHAR2(64),
   PROC_DEF_ID_         VARCHAR2(64),
   RETRIES_             INTEGER              default NULL,
   EXCEPTION_STACK_ID_  VARCHAR2(64),
   EXCEPTION_MSG_       VARCHAR2(4000),
   DUEDATE_             TIMESTAMP            default NULL,
   REPEAT_              VARCHAR2(255),
   HANDLER_TYPE_        VARCHAR2(255),
   HANDLER_CFG_         VARCHAR2(4000),
   TENANT_ID_           VARCHAR2(255),
   constraint PK_ACT_RU_JOB primary key (ID_)
);

/*==============================================================*/
/* Table: act_ru_task                                         */
/*==============================================================*/
create table act_ru_task 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   EXECUTION_ID_        VARCHAR2(64),
   PROC_INST_ID_        VARCHAR2(64),
   PROC_DEF_ID_         VARCHAR2(64),
   NAME_                VARCHAR2(255),
   PARENT_TASK_ID_      VARCHAR2(64),
   DESCRIPTION_         VARCHAR2(4000),
   TASK_DEF_KEY_        VARCHAR2(255),
   OWNER_               VARCHAR2(255),
   ASSIGNEE_            VARCHAR2(255),
   DELEGATION_          VARCHAR2(64),
   PRIORITY_            INTEGER              default NULL,
   CREATE_TIME_         TIMESTAMP            default NULL,
   DUE_DATE_            DATE,
   CATEGORY_            VARCHAR2(255),
   SUSPENSION_STATE_    INTEGER              default NULL,
   TENANT_ID_           VARCHAR2(255),
   FORM_KEY_            VARCHAR2(255),
   constraint PK_ACT_RU_TASK primary key (ID_)
);
create index ACT_IDX_TASK_CREATE on act_ru_task (CREATE_TIME_);
create index IDX_ACT_RU_TASK_N1 on act_ru_task (PARENT_TASK_ID_);

/*==============================================================*/
/* Table: act_ru_variable                                     */
/*==============================================================*/
create table act_ru_variable 
(
   ID_                  VARCHAR2(64)         not null,
   REV_                 INTEGER              default NULL,
   TYPE_                VARCHAR2(255),
   NAME_                VARCHAR2(255),
   EXECUTION_ID_        VARCHAR2(64),
   PROC_INST_ID_        VARCHAR2(64),
   TASK_ID_             VARCHAR2(64),
   BYTEARRAY_ID_        VARCHAR2(64),
   DOUBLE_              BINARY_DOUBLE        default NULL,
   LONG_                INTEGER              default NULL,
   TEXT_                VARCHAR2(4000),
   TEXT2_               VARCHAR2(4000),
   constraint PK_ACT_RU_VARIABLE primary key (ID_)
);
create index ACT_IDX_VARIABLE_TASK_ID on act_ru_variable (TASK_ID_);

/*==============================================================*/
/* Table: admin_area_info                                     */
/*==============================================================*/
create table admin_area_info 
(
   area_id            INTEGER            not null,
   area_code          VARCHAR2(30),
   area_name          VARCHAR2(500),
   area_type          VARCHAR2(50),
   area_level         VARCHAR2(10),
   area_postcode      VARCHAR2(30),
   area_status        VARCHAR2(30),
   parent_area_code   VARCHAR2(30),
   parent_area_name   VARCHAR2(500),
   region_code        VARCHAR2(50),
   longitude          VARCHAR2(30),
   latitude           VARCHAR2(30),
   description        VARCHAR2(500),
   VERSION_NUM          INTEGER              default NULL,
   CREATION_DATE        DATE                 default sysdate not null,
   CREATED_BY           INTEGER              default -1 not null,
   LAST_UPDATED_BY      INTEGER              default -1 not null,
   LAST_UPDATE_DATE     DATE                 default sysdate not null,
   LAST_UPDATE_LOGIN    INTEGER              default NULL,
   constraint PK_ADMIN_AREA_INFO primary key (area_id)
);

comment on column admin_area_info.area_id is
'表ID，主键，供其他表做外键';
create index admin_area_info_n1 on admin_area_info (area_code);
create index admin_area_info_n2 on admin_area_info (parent_area_code);

/*==============================================================*/
/* Table: base_access_basedata                                */
/*==============================================================*/
create table base_access_basedata 
(
   access_id          INTEGER            not null,
   org_id             INTEGER              default NULL,
   access_type        VARCHAR2(10),
   user_id            INTEGER              default NULL,
   person_id          INTEGER              default NULL,
   position_id        INTEGER              default NULL,
   subordinate_person_id INTEGER              default NULL,
   subordinate_position_id INTEGER              default NULL,
   cust_account_id    INTEGER              default NULL,
   account_number     VARCHAR2(30),
   secondary_inventory_name VARCHAR2(30),
   channel_type       VARCHAR2(30),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_ACCESS_BASEDATA primary key (access_id)
);

comment on column base_access_basedata.access_id is
'主键ID';

comment on column base_access_basedata.org_id is
'事业部';

comment on column base_access_basedata.user_id is
'用户ID';

comment on column base_access_basedata.person_id is
'人员ID';

comment on column base_access_basedata.position_id is
'职位';

comment on column base_access_basedata.subordinate_person_id is
'下级人员ID';

comment on column base_access_basedata.subordinate_position_id is
'下级职位ID';

comment on column base_access_basedata.cust_account_id is
'经销商ID';

comment on column base_access_basedata.creation_date is
'创建时间';

comment on column base_access_basedata.created_by is
'创建人';

comment on column base_access_basedata.last_update_date is
'更新时间';

comment on column base_access_basedata.last_updated_by is
'更新人';

comment on column base_access_basedata.last_update_login is
'最后更新登录ID';

comment on column base_access_basedata.delete_flag is
'删除标识';

comment on column base_access_basedata.version_num is
'版本号';

create index idx_access_basedata_n1 on base_access_basedata (user_id, access_type, org_id);

/*==============================================================*/
/* Table: base_adminstrative_region                           */
/*==============================================================*/
create table base_adminstrative_region 
(
   region_id          INTEGER            not null,
   parent_region_id   INTEGER              default NULL,
   region_code        VARCHAR2(30)         default NULL,
   parent_region_code VARCHAR2(30)         default NULL,
   city_code          VARCHAR2(10)         default NULL,
   ad_code            VARCHAR2(10)         default NULL,
   region_name        VARCHAR2(100)        default NULL,
   region_spell_name  VARCHAR2(100)        default NULL,
   region_short_name  VARCHAR2(50)         default NULL,
   region_short_spell_name VARCHAR2(50)         default NULL,
   region_names       VARCHAR2(100)        default NULL,
   region_short_names VARCHAR2(80)         default NULL,
   region_center      VARCHAR2(50)         default NULL,
   region_level       VARCHAR2(20)         default NULL,
   region_description VARCHAR2(400)        default NULL,
   is_hot_city        VARCHAR2(5)          default NULL,
   enabled            VARCHAR2(5)          default NULL,
   delete_flag        INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_ADMINSTRATIVE_REGION primary key (region_id)
);

comment on column base_adminstrative_region.region_id is
'行动区域Id';

comment on column base_adminstrative_region.parent_region_id is
'上级行政区域Id';

comment on column base_adminstrative_region.region_code is
'区域代码';

comment on column base_adminstrative_region.parent_region_code is
'上级行政区域代码';

comment on column base_adminstrative_region.city_code is
'城市编码';

comment on column base_adminstrative_region.ad_code is
'区域编码';

comment on column base_adminstrative_region.region_name is
'行政区域名称';

comment on column base_adminstrative_region.region_spell_name is
'行政区域拼音';

comment on column base_adminstrative_region.region_short_name is
'行政区域简称';

comment on column base_adminstrative_region.region_short_spell_name is
'行政区域简称拼音';

comment on column base_adminstrative_region.region_names is
'省市区全称';

comment on column base_adminstrative_region.region_short_names is
'省市区简称';

comment on column base_adminstrative_region.region_center is
'城市中心点经纬度';

comment on column base_adminstrative_region.region_level is
'行政区划级别,country:国家,province:省份（直辖市会在province和city显示）,city:市（直辖市会在province和city显示,district:区县,street:街道';

comment on column base_adminstrative_region.region_description is
'行政区域描述';

comment on column base_adminstrative_region.is_hot_city is
'是否热门城市,Y/N';

comment on column base_adminstrative_region.enabled is
'是否生效';

comment on column base_adminstrative_region.delete_flag is
'删除标记';

comment on column base_adminstrative_region.created_by is
'创建人';

comment on column base_adminstrative_region.creation_date is
'创建时间';

comment on column base_adminstrative_region.last_updated_by is
'更新人';

comment on column base_adminstrative_region.last_update_date is
'更新时间';

comment on column base_adminstrative_region.last_update_login is
'最后一次更新人帐号';

comment on column base_adminstrative_region.version_num is
'版本号';

create index region_code_idx on base_adminstrative_region (region_code);

/*==============================================================*/
/* Table: base_api_center_h                                   */
/*==============================================================*/
create table base_api_center_h 
(
   apih_id            INTEGER            not null,
   center_name        VARCHAR2(100),
   center_code        VARCHAR2(100),
   version_num        INTEGER              default 0,
   CREATION_DATE        DATE                 default NULL,
   CREATED_BY           INTEGER              default NULL,
   LAST_UPDATED_BY      INTEGER              default NULL,
   LAST_UPDATE_DATE     DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_API_CENTER_H primary key (apih_id)
);

comment on column base_api_center_h.apih_id is
'主键ID';

comment on column base_api_center_h.version_num is
'版本号';

/*==============================================================*/
/* Table: base_api_center_l                                   */
/*==============================================================*/
create table base_api_center_l 
(
   apil_id            INTEGER            not null,
   center_code        VARCHAR2(100),
   model_name         VARCHAR2(400),
   model_code         VARCHAR2(400),
   version_num        INTEGER              default 0,
   CREATION_DATE        DATE                 default NULL,
   CREATED_BY           INTEGER              default NULL,
   LAST_UPDATED_BY      INTEGER              default NULL,
   LAST_UPDATE_DATE     DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_API_CENTER_L primary key (apil_id)
);

comment on column base_api_center_l.apil_id is
'主键ID';

comment on column base_api_center_l.version_num is
'版本号';

/*==============================================================*/
/* Table: base_api_management                                 */
/*==============================================================*/
create table base_api_management 
(
   api_id             INTEGER            not null,
   interface_name     VARCHAR2(400),
   request_mode       VARCHAR2(100),
   api_status         VARCHAR2(100),
   url_address        VARCHAR2(400),
   developer          VARCHAR2(128),
   api_desc           VARCHAR2(400),
   request_param      CLOB,
   request_param_dict CLOB,
   response_param     CLOB,
   response_param_dict CLOB,
   center_name        VARCHAR2(100),
   center_code        VARCHAR2(100),
   model_name         VARCHAR2(400),
   model_code         VARCHAR2(400),
   version_num        INTEGER              default 0,
   CREATION_DATE        DATE                 default NULL,
   CREATED_BY           INTEGER              default NULL,
   LAST_UPDATED_BY      INTEGER              default NULL,
   LAST_UPDATE_DATE     DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_API_MANAGEMENT primary key (api_id)
);

comment on column base_api_management.api_id is
'主键ID';

comment on column base_api_management.version_num is
'版本号';

/*==============================================================*/
/* Table: base_app_auth_contain                               */
/*==============================================================*/
create table base_app_auth_contain 
(
   auth_app_wap_id    INTEGER            not null,
   app_wap_id         INTEGER              default NULL,
   object_type        VARCHAR2(16),
   ou_id              INTEGER              default NULL,
   dep_code           VARCHAR2(64),
   emp_id             INTEGER              default NULL,
   province           VARCHAR2(64),
   city               VARCHAR2(64),
   area               VARCHAR2(64),
   store              VARCHAR2(64),
   dealer             VARCHAR2(64),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_APP_AUTH_CONTAIN primary key (auth_app_wap_id)
);

comment on column base_app_auth_contain.auth_app_wap_id is
'用户授权主键';

comment on column base_app_auth_contain.app_wap_id is
'app应用主键';

comment on column base_app_auth_contain.ou_id is
'OU ID';

comment on column base_app_auth_contain.emp_id is
'员工Id';

comment on column base_app_auth_contain.created_by is
'创建人';

comment on column base_app_auth_contain.creation_date is
'创建时间';

comment on column base_app_auth_contain.last_update_date is
'最后更新时间';

comment on column base_app_auth_contain.last_update_login is
'最后登录Id';

comment on column base_app_auth_contain.last_updated_by is
'最后更新人';

comment on column base_app_auth_contain.version_num is
'版本号';

comment on column base_app_auth_contain.delete_flag is
'是否删除0：未删除1：已删除';

/*==============================================================*/
/* Table: base_app_auth_exclude                               */
/*==============================================================*/
create table base_app_auth_exclude 
(
   auth_app_wap_id    INTEGER            not null,
   app_wap_id         INTEGER              default NULL,
   object_type        VARCHAR2(16),
   ou_id              INTEGER              default NULL,
   dep_code           VARCHAR2(64),
   emp_id             INTEGER              default NULL,
   province           VARCHAR2(64),
   city               VARCHAR2(64),
   area               VARCHAR2(64),
   store              VARCHAR2(64),
   dealer             VARCHAR2(64),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_APP_AUTH_EXCLUDE primary key (auth_app_wap_id)
);

comment on column base_app_auth_exclude.auth_app_wap_id is
'用户授权主键';

comment on column base_app_auth_exclude.app_wap_id is
'app应用主键';

comment on column base_app_auth_exclude.ou_id is
'OU ID';

comment on column base_app_auth_exclude.emp_id is
'员工Id';

comment on column base_app_auth_exclude.created_by is
'创建人';

comment on column base_app_auth_exclude.creation_date is
'创建时间';

comment on column base_app_auth_exclude.last_update_date is
'最后更新时间';

comment on column base_app_auth_exclude.last_update_login is
'最后登录Id';

comment on column base_app_auth_exclude.last_updated_by is
'最后更新人';

comment on column base_app_auth_exclude.version_num is
'版本号';

comment on column base_app_auth_exclude.delete_flag is
'是否删除0：未删除1：已删除';

/*==============================================================*/
/* Table: base_app_deployee_info                              */
/*==============================================================*/
create table base_app_deployee_info 
(
   app_wap_id         INTEGER            not null,
   app_name           VARCHAR2(32),
   app_wap_id_name    VARCHAR2(64),
   app_wap_name       VARCHAR2(64),
   app_wap_desc       VARCHAR2(256),
   app_wap_sort_id    INTEGER              default NULL,
   app_wap_version    VARCHAR2(16),
   wap_packing_version VARCHAR2(16),
   app_wap_access_home_path VARCHAR2(2000),
   app_wap_full_screen VARCHAR2(2),
   app_wap_backup_path VARCHAR2(1000),
   app_wap_image_path VARCHAR2(1000),
   app_wap_upload_path VARCHAR2(128),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_APP_DEPLOYEE_INFO primary key (app_wap_id)
);

comment on column base_app_deployee_info.app_wap_id is
'主键Id';

comment on column base_app_deployee_info.app_wap_sort_id is
'应用排序';

comment on column base_app_deployee_info.delete_flag is
'删除标记, 0表示未删除 1表示已删除';

/*==============================================================*/
/* Table: base_app_deployee_menu                              */
/*==============================================================*/
create table base_app_deployee_menu 
(
   app_menu_id        INTEGER            not null,
   app_wap_id         INTEGER              default NULL,
   app_menu_name      VARCHAR2(64),
   app_menu_code      VARCHAR2(64),
   app_menu_img       VARCHAR2(3000),
   app_menu_url       VARCHAR2(3000),
   app_menu_sort      INTEGER              default NULL,
   app_default_display INTEGER              default 0,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_APP_DEPLOYEE_MENU primary key (app_menu_id)
);

comment on column base_app_deployee_menu.app_menu_id is
'主键Id';

comment on column base_app_deployee_menu.app_menu_sort is
'app菜单排序';

comment on column base_app_deployee_menu.app_default_display is
'默认显示在最近使用栏,0表示显示 1表示不显示';

comment on column base_app_deployee_menu.delete_flag is
'删除标记, 0表示未删除 1表示已删除';

/*==============================================================*/
/* Table: base_attachment                                     */
/*==============================================================*/
create table base_attachment 
(
   file_id            INTEGER            not null,
   source_file_name   VARCHAR2(200),
   function_id        VARCHAR2(50),
   business_id        INTEGER              default NULL,
   file_store_system  INTEGER              default NULL,
   file_path          VARCHAR2(200),
   bucket_name        VARCHAR2(100),
   phy_file_name      VARCHAR2(100),
   status             VARCHAR2(2),
   file_size          NUMBER(10,2)         default NULL,
   file_type          VARCHAR2(20),
   delete_flag        INTEGER              default 0,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default 0,
   constraint PK_BASE_ATTACHMENT primary key (file_id)
);

comment on column base_attachment.file_id is
'文件ID';

comment on column base_attachment.business_id is
'业务来源主键';

comment on column base_attachment.file_store_system is
'文件存储系统--1阿里sso 2文档系统';

comment on column base_attachment.file_size is
'文件大小(MB)';

comment on column base_attachment.delete_flag is
'是否删除0未删除1已删除';

comment on column base_attachment.created_by is
'创建用户ID';

comment on column base_attachment.creation_date is
'创建时间';

comment on column base_attachment.last_updated_by is
'最后修改用户ID';

comment on column base_attachment.last_update_date is
'最后修改时间';

comment on column base_attachment.last_update_login is
'LAST_UPDATE_LOGIN';

comment on column base_attachment.version_num is
'版本号';

create index function_id_idx on base_attachment (function_id);
create index business_id_idx on base_attachment (business_id);
create index file_store_system_idx on base_attachment (file_store_system);
create index status_idx on base_attachment (status);

/*==============================================================*/
/* Table: base_button_data                                    */
/*==============================================================*/
create table base_button_data 
(
   bbd_id             INTEGER            not null,
   bbd_name           VARCHAR2(30),
   bbd_code           VARCHAR2(50),
   res_icon           VARCHAR2(50),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_BUTTON_DATA primary key (bbd_id)
);

comment on column base_button_data.bbd_id is
'主键ID';

comment on column base_button_data.creation_date is
'创建日期';

comment on column base_button_data.created_by is
'创建人';

comment on column base_button_data.last_updated_by is
'更新人';

comment on column base_button_data.last_update_date is
'更新日期';

comment on column base_button_data.version_num is
'版本号';

comment on column base_button_data.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_button_data_language                           */
/*==============================================================*/
create table base_button_data_language 
(
   bbd_language_id    INTEGER            not null,
   bbd_name           VARCHAR2(50),
   bbd_id             INTEGER              default NULL,
   language           VARCHAR2(30),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default 0,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_BUTTON_DATA_LANGUAGE primary key (bbd_language_id)
);

comment on column base_button_data_language.bbd_language_id is
'主键ID';

comment on column base_button_data_language.bbd_id is
'系统编码';

comment on column base_button_data_language.created_by is
'创建人';

comment on column base_button_data_language.last_updated_by is
'更新人';

comment on column base_button_data_language.last_update_date is
'更新日期';

comment on column base_button_data_language.version_num is
'版本号';

/*==============================================================*/
/* Table: base_channel                                        */
/*==============================================================*/
create table base_channel 
(
   channel_id         INTEGER            not null,
   CHANNEL_CODE         VARCHAR2(150),
   CHANNEL_NAME         VARCHAR2(240),
   CHANNEL_DESC         VARCHAR2(240),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_CHANNEL primary key (channel_id)
);

comment on column base_channel.channel_id is
'渠道Id';

comment on column base_channel.creation_date is
'创建日期';

comment on column base_channel.created_by is
'创建人';

comment on column base_channel.last_updated_by is
'更新人';

comment on column base_channel.last_update_date is
'更新日期';

comment on column base_channel.version_num is
'版本号';

comment on column base_channel.last_update_login is
'last_update_login';

create index INDEX_BC_V1 on base_channel (CHANNEL_CODE);

/*==============================================================*/
/* Table: base_channel_privilege                              */
/*==============================================================*/
create table base_channel_privilege 
(
   channel_privilege_id INTEGER            not null,
   ORG_ID               INTEGER              default NULL,
   ACCESS_TYPE          VARCHAR2(10),
   CHANNEL_TYPE         VARCHAR2(30),
   TRANSACTION_TYPE_ID  INTEGER              default NULL,
   ITEM_CODE            VARCHAR2(30),
   VALID_FLAG           VARCHAR2(5),
   CUSTOMER_ORDER_FLAG  VARCHAR2(5),
   DEFAULT_TYPE         VARCHAR2(30),
   ATTRIBUTE_CATEGORY   VARCHAR2(30),
   ATTRIBUTE1           VARCHAR2(150),
   ATTRIBUTE2           VARCHAR2(150),
   ATTRIBUTE3           VARCHAR2(150),
   ATTRIBUTE4           VARCHAR2(150),
   ATTRIBUTE5           VARCHAR2(150),
   ATTRIBUTE6           VARCHAR2(150),
   ATTRIBUTE7           VARCHAR2(150),
   ATTRIBUTE8           VARCHAR2(150),
   ATTRIBUTE9           VARCHAR2(150),
   ATTRIBUTE10          VARCHAR2(150),
   IS_BCREIMBURSEHEO    VARCHAR2(5),
   EXPENSE_PRICE_LIST_ID INTEGER              default NULL,
   IS_LINK_CASH_ORDER   VARCHAR2(5),
   EXPENSE_ACCOUNT      INTEGER              default NULL,
   IS_QUOTA_CONTROL     VARCHAR2(5),
   IS_PROMOTION_CONTROL VARCHAR2(5),
   IS_PAYMENT_CONTROL   VARCHAR2(5),
   IS_MOD_AMOUNT        VARCHAR2(5),
   PREFERRED_PAYMENT_ACCOUNT VARCHAR2(5),
   BALANCE_CHECK_IN_APPROVE VARCHAR2(5),
   VIEW_BALANCE_IN_OD_CREATE VARCHAR2(5),
   VIEW_BALANCE_IN_APPROVE VARCHAR2(5),
   BALANCE_CONTROL_OD_SUBMIT VARCHAR2(5),
   BALANCE_CONTROL_OD_APPROVE VARCHAR2(5),
   CARRIER_IN_APPROVE   VARCHAR2(5),
   IS_PURCHASE_RULE_CONTROL VARCHAR2(5),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_CHANNEL_PRIVILEGE primary key (channel_privilege_id)
);

comment on column base_channel_privilege.channel_privilege_id is
'主键';

comment on column base_channel_privilege.ORG_ID is
'OU组织Id';

comment on column base_channel_privilege.TRANSACTION_TYPE_ID is
'交易Id、物料Id';

comment on column base_channel_privilege.EXPENSE_PRICE_LIST_ID is
'费用价目表，订单计入费用科目时使用，当IS_BCREIMBURSEHEO字段为Y时必填';

comment on column base_channel_privilege.EXPENSE_ACCOUNT is
'费用科目code';

comment on column base_channel_privilege.creation_date is
'创建日期';

comment on column base_channel_privilege.created_by is
'创建人';

comment on column base_channel_privilege.last_updated_by is
'更新人';

comment on column base_channel_privilege.last_update_date is
'更新日期';

comment on column base_channel_privilege.version_num is
'版本号';

comment on column base_channel_privilege.last_update_login is
'last_update_login';

create index BASE_CHANNEL_PRI_N1 on base_channel_privilege (ITEM_CODE, ACCESS_TYPE);
create index BASE_CHANNEL_PRI_N2 on base_channel_privilege (TRANSACTION_TYPE_ID, ACCESS_TYPE);

/*==============================================================*/
/* Table: base_cms_article                                    */
/*==============================================================*/
create table base_cms_article 
(
   article_id         INTEGER            not null,
   article_category   VARCHAR2(5)          default NULL,
   article_type       VARCHAR2(5)          default NULL,
   top_flag           VARCHAR2(50)         default NULL,
   article_title      VARCHAR2(50)         default NULL,
   biz_id             INTEGER              default NULL,
   biz_code           VARCHAR2(50)         default NULL,
   article_author     VARCHAR2(30)         default NULL,
   en_article_description VARCHAR2(150)        default NULL,
   article_description VARCHAR2(150)        default NULL,
   enabled_flag       VARCHAR2(2)          default NULL,
   picture_web        VARCHAR2(150)        default NULL,
   picture_pc         VARCHAR2(150)        default NULL,
   article_content_url VARCHAR2(150)        default NULL,
   article_status     VARCHAR2(2)          default NULL,
   visited            INTEGER              default NULL,
   top_to_date        DATE                 default NULL,
   article_publish_date DATE                 default NULL,
   article_invalid_date DATE                 default NULL,
   en_article_content CLOB,
   article_content    CLOB,
   system_code        VARCHAR2(20)         default NULL,
   company_id         INTEGER              default NULL,
   delete_flag        INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_CMS_ARTICLE primary key (article_id)
);

comment on column base_cms_article.article_id is
'主键Id';

comment on column base_cms_article.article_category is
'文章分类';

comment on column base_cms_article.article_type is
'文章类型';

comment on column base_cms_article.top_flag is
'置顶标识';

comment on column base_cms_article.article_title is
'文章标题';

comment on column base_cms_article.biz_id is
'业务id';

comment on column base_cms_article.biz_code is
'业务编码';

comment on column base_cms_article.article_author is
'文章作者';

comment on column base_cms_article.en_article_description is
'文章简述英文';

comment on column base_cms_article.article_description is
'文章简述';

comment on column base_cms_article.enabled_flag is
'是否首页显示';

comment on column base_cms_article.picture_web is
'web端缩略图url';

comment on column base_cms_article.picture_pc is
'pc端缩略图url';

comment on column base_cms_article.article_content_url is
'文章链接';

comment on column base_cms_article.article_status is
'文章状态:Y=生效,N=失效';

comment on column base_cms_article.visited is
'浏览量';

comment on column base_cms_article.top_to_date is
'置顶结束时间';

comment on column base_cms_article.article_publish_date is
'文章发布时间';

comment on column base_cms_article.article_invalid_date is
'文章失效时间';

comment on column base_cms_article.en_article_content is
'文章内容英文';

comment on column base_cms_article.article_content is
'文章内容';

comment on column base_cms_article.system_code is
'系统编码';

comment on column base_cms_article.company_id is
'事业部';

comment on column base_cms_article.delete_flag is
'删除标识';

comment on column base_cms_article.creation_date is
'创建时间';

comment on column base_cms_article.created_by is
'创建人';

comment on column base_cms_article.last_updated_by is
'更新人';

comment on column base_cms_article.last_update_date is
'更新时间';

comment on column base_cms_article.last_update_login is
'最后一次更新人帐号';

comment on column base_cms_article.version_num is
'版本号';
create index article_category_idx on base_cms_article (article_category);
create index top_flag_idx on base_cms_article (top_flag);
create index company_id_idx on base_cms_article (company_id);

/*==============================================================*/
/* Table: base_cms_carousel                                   */
/*==============================================================*/
create table base_cms_carousel 
(
   carousel_id        INTEGER            not null,
   order_sequence     INTEGER              default NULL,
   carousel_type      VARCHAR2(30)         default NULL,
   carousel_name      VARCHAR2(240)        default NULL,
   carousel_position  VARCHAR2(240)        default NULL,
   display_flag       VARCHAR2(2)          default NULL,
   picture_desc       VARCHAR2(1000)       default NULL,
   picture_url        VARCHAR2(300)        default NULL,
   url_address        VARCHAR2(300)        default NULL,
   start_date_active  DATE                 default NULL,
   end_date_active    DATE                 default NULL,
   source_type        VARCHAR2(30)         default NULL,
   source_type_name   VARCHAR2(30)         default NULL,
   source_name        VARCHAR2(30)         default NULL,
   source_name_id     VARCHAR2(30)         default NULL,
   company_id         INTEGER              default NULL,
   delete_flag        INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_CMS_CAROUSEL primary key (carousel_id)
);

comment on column base_cms_carousel.carousel_id is
'主键Id';

comment on column base_cms_carousel.order_sequence is
'显示顺序';

comment on column base_cms_carousel.carousel_type is
'轮播图类型,0图片;1flash;2代码3文字';

comment on column base_cms_carousel.carousel_name is
'轮播图名称';

comment on column base_cms_carousel.carousel_position is
'轮播图位置';

comment on column base_cms_carousel.display_flag is
'是否显示';

comment on column base_cms_carousel.picture_desc is
'图片描述';

comment on column base_cms_carousel.picture_url is
'图片URL地址';

comment on column base_cms_carousel.url_address is
'超链接地址';

comment on column base_cms_carousel.start_date_active is
'开始时间';

comment on column base_cms_carousel.end_date_active is
'结束时间';

comment on column base_cms_carousel.source_type is
'来源类型';

comment on column base_cms_carousel.source_type_name is
'来源类型描述';

comment on column base_cms_carousel.source_name is
'来源名称';

comment on column base_cms_carousel.source_name_id is
'来源名称Id';

comment on column base_cms_carousel.company_id is
'事业部';

comment on column base_cms_carousel.delete_flag is
'删除标识';

comment on column base_cms_carousel.created_by is
'创建人';

comment on column base_cms_carousel.creation_date is
'创建时间';

comment on column base_cms_carousel.last_updated_by is
'更新人';

comment on column base_cms_carousel.last_update_date is
'更新时间';

comment on column base_cms_carousel.last_update_login is
'最后一次更新人帐号';

comment on column base_cms_carousel.version_num is
'版本号';

create index order_sequence_idx on base_cms_carousel (order_sequence);
create index display_flag_idx on base_cms_carousel (display_flag);
create index cpy_id_idx on base_cms_carousel (company_id);

/*==============================================================*/
/* Table: base_cms_carousel_city                              */
/*==============================================================*/
create table base_cms_carousel_city 
(
   carousel_city_id   INTEGER            not null,
   carousel_id        INTEGER              default NULL,
   city_id            INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   delete_flag        INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_CMS_CAROUSEL_CITY primary key (carousel_city_id)
);

comment on column base_cms_carousel_city.carousel_city_id is
'参与城市id';

comment on column base_cms_carousel_city.carousel_id is
'广告/轮播图Id';

comment on column base_cms_carousel_city.city_id is
'城市id';

comment on column base_cms_carousel_city.created_by is
'创建人';

comment on column base_cms_carousel_city.creation_date is
'创建时间';

comment on column base_cms_carousel_city.delete_flag is
'删除标识';

comment on column base_cms_carousel_city.last_updated_by is
'更新人';

comment on column base_cms_carousel_city.last_update_date is
'更新时间';

comment on column base_cms_carousel_city.last_update_login is
'最后一次更新人帐号';

comment on column base_cms_carousel_city.version_num is
'版本号';

create index city_id_idx on base_cms_carousel_city (city_id);
create index carousel_id_idx on base_cms_carousel_city (carousel_id);

/*==============================================================*/
/* Table: base_cms_category                                   */
/*==============================================================*/
create table base_cms_category 
(
   category_id        INTEGER            not null,
   parent_category_id INTEGER              default NULL,
   category_code      VARCHAR2(30)         default NULL,
   category_name      VARCHAR2(100)        default NULL,
   enabled            VARCHAR2(5)          default NULL,
   category_icon      VARCHAR2(400)        default NULL,
   company_id         INTEGER              default NULL,
   delete_flag        INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_CMS_CATEGORY primary key (category_id)
);

comment on column base_cms_category.category_id is
'分类Id';

comment on column base_cms_category.parent_category_id is
'上级分类';

comment on column base_cms_category.category_code is
'分类编码';

comment on column base_cms_category.category_name is
'分类名称';

comment on column base_cms_category.enabled is
'是否生效';

comment on column base_cms_category.category_icon is
'分类图标 冗余附件表url字段';

comment on column base_cms_category.company_id is
'品牌 快码COMPANY_TYPE';

comment on column base_cms_category.delete_flag is
'删除标记';

comment on column base_cms_category.created_by is
'创建人';

comment on column base_cms_category.creation_date is
'创建时间';

comment on column base_cms_category.last_updated_by is
'更新人';

comment on column base_cms_category.last_update_date is
'更新时间';

comment on column base_cms_category.last_update_login is
'最后一次更新人帐号';

comment on column base_cms_category.version_num is
'版本号';

/*==============================================================*/
/* Table: base_cms_web_notice                                 */
/*==============================================================*/
create table base_cms_web_notice 
(
   notice_id          INTEGER            not null,
   notice_title       VARCHAR2(240)        default NULL,
   notice_order       INTEGER              default NULL,
   is_display         VARCHAR2(5)          default NULL,
   start_time         DATE                 default NULL,
   end_time           DATE                 default NULL,
   notice_content     CLOB,
   delete_flag        INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   company_id         INTEGER              default NULL,
   constraint PK_BASE_CMS_WEB_NOTICE primary key (notice_id)
);

comment on column base_cms_web_notice.notice_id is
'主键Id';

comment on column base_cms_web_notice.notice_title is
'标题';

comment on column base_cms_web_notice.notice_order is
'显示顺序';

comment on column base_cms_web_notice.is_display is
'是否显示';

comment on column base_cms_web_notice.start_time is
'生效时间';

comment on column base_cms_web_notice.end_time is
'失效时间';

comment on column base_cms_web_notice.notice_content is
'内容';

comment on column base_cms_web_notice.delete_flag is
'删除标识';

comment on column base_cms_web_notice.creation_date is
'创建时间';

comment on column base_cms_web_notice.created_by is
'创建人';

comment on column base_cms_web_notice.last_updated_by is
'更新人';

comment on column base_cms_web_notice.last_update_date is
'更新时间';

comment on column base_cms_web_notice.last_update_login is
'最后一次更新人帐号';

comment on column base_cms_web_notice.version_num is
'版本号';

comment on column base_cms_web_notice.company_id is
'事业部';
create index is_display_idx on base_cms_web_notice (is_display);
create index notice_order_idx on base_cms_web_notice (notice_order);
create index notice_company_id_idx on base_cms_web_notice (company_id);

/*==============================================================*/
/* Table: base_cms_web_notice_city                            */
/*==============================================================*/
create table base_cms_web_notice_city 
(
   notice_city_id     INTEGER            not null,
   notice_id          INTEGER              default NULL,
   city_id            INTEGER              default NULL,
   delete_flag        INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_CMS_WEB_NOTICE_CITY primary key (notice_city_id)
);

comment on column base_cms_web_notice_city.notice_city_id is
'唯一Id';

comment on column base_cms_web_notice_city.notice_id is
'公告id';

comment on column base_cms_web_notice_city.city_id is
'城市id';

comment on column base_cms_web_notice_city.delete_flag is
'删除标识';

comment on column base_cms_web_notice_city.creation_date is
'创建时间';

comment on column base_cms_web_notice_city.created_by is
'创建人';

comment on column base_cms_web_notice_city.last_updated_by is
'更新人';

comment on column base_cms_web_notice_city.last_update_date is
'更新时间';

comment on column base_cms_web_notice_city.last_update_login is
'最后一次更新人帐号';

comment on column base_cms_web_notice_city.version_num is
'版本号';

create index notice_id_idx on base_cms_web_notice_city (notice_id);
create index notice_city_id_idx on base_cms_web_notice_city (city_id);

/*==============================================================*/
/* Table: base_common_message_confirm                         */
/*==============================================================*/
create table base_common_message_confirm 
(
   confirmId          INTEGER            not null,
   messageIndex       INTEGER              not null,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_COMMON_MESSAGE_CONFIRM primary key (confirmId)
);

comment on column base_common_message_confirm.creation_date is
'创建日期';

comment on column base_common_message_confirm.created_by is
'创建人';

comment on column base_common_message_confirm.last_updated_by is
'更新人';

comment on column base_common_message_confirm.last_update_date is
'更新日期';

comment on column base_common_message_confirm.version_num is
'版本号';

/*==============================================================*/
/* Table: base_common_transaction_confir                      */
/*==============================================================*/
create table base_common_transaction_confir 
(
   confirmId          INTEGER            not null,
   messageIndex       INTEGER              not null,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_COMMON_TRANSACTION_CON primary key (confirmId)
);

comment on column base_common_transaction_confir.creation_date is
'创建日期';

comment on column base_common_transaction_confir.created_by is
'创建人';

comment on column base_common_transaction_confir.last_updated_by is
'更新人';

comment on column base_common_transaction_confir.last_update_date is
'更新日期';

comment on column base_common_transaction_confir.version_num is
'版本号';
create unique index  messageIndex_UNIQUE on base_common_transaction_confir (messageIndex);

/*==============================================================*/
/* Table: base_common_txn_confirm                             */
/*==============================================================*/
create table base_common_txn_confirm 
(
   confirmId          INTEGER            not null,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   messageIndex       INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_COMMON_TXN_CONFIRM primary key (confirmId)
);

/*==============================================================*/
/* Table: base_cust_pda_user_relation                         */
/*==============================================================*/
create table base_cust_pda_user_relation 
(
   relation_id        INTEGER            not null,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   cust_user_id       INTEGER              default NULL,
   delete_flag        INTEGER              default NULL,
   enable_flag        VARCHAR2(1)          default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   pda_user_id        INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_CUST_PDA_USER_RELATION primary key (relation_id)
);

/*==============================================================*/
/* Table: base_data_export                                    */
/*==============================================================*/
create table base_data_export 
(
   data_exp_id        INTEGER            not null,
   create_date        DATE                 default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   download_path      CLOB,
   download_times     INTEGER              default NULL,
   file_name          VARCHAR2(100)        default NULL,
   group_name         VARCHAR2(250)        default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   remote_file_name   CLOB,
   request_date       DATE                 default NULL,
   user_id            INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_DATA_EXPORT primary key (data_exp_id)
);

/*==============================================================*/
/* Table: base_department                                     */
/*==============================================================*/
create table base_department 
(
   department_id      INTEGER            not null,
   department_code    VARCHAR2(50),
   department_name    VARCHAR2(200),
   department_full_name VARCHAR2(300),
   suffix             VARCHAR2(30),
   department_level   INTEGER              default NULL,
   ou_id              INTEGER              default NULL,
   parent_department_id INTEGER              default NULL,
   cost_center        VARCHAR2(30),
   department_seq     INTEGER              default NULL,
   date_from          DATE                 default NULL,
   date_to            DATE                 default NULL,
   enable_flag        VARCHAR2(6),
   biz_line_type      VARCHAR2(50),
   channel            VARCHAR2(50),
   department_type    VARCHAR2(30),
   inventory_enable   VARCHAR2(10),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_DEPARTMENT primary key (department_id)
);

comment on column base_department.department_id is
'部门ID';

comment on column base_department.department_level is
'部门层级';

comment on column base_department.ou_id is
'事业部';

comment on column base_department.parent_department_id is
'父部门';

comment on column base_department.department_seq is
'部门序号';

comment on column base_department.date_from is
'生效日期';

comment on column base_department.date_to is
'失效日期';

comment on column base_department.creation_date is
'创建时间';

comment on column base_department.created_by is
'创建人';

comment on column base_department.last_update_date is
'更新时间';

comment on column base_department.last_updated_by is
'更新人';

comment on column base_department.last_update_login is
'最后登录ID';

comment on column base_department.delete_flag is
'删除标识';

comment on column base_department.version_num is
'版本号';

create index idx_base_department_n1 on base_department (department_name);
create index idx_base_department_n2 on base_department (parent_department_id);
create index idx_base_department_n3 on base_department (department_code);
create index idx_base_department_n4 on base_department (channel);
create index idx_base_department_n5 on base_department (department_type);
create index idx_base_department_n6 on base_department (biz_line_type);

/*==============================================================*/
/* Table: base_department_cust                                */
/*==============================================================*/
create table base_department_cust 
(
   department_cust_id INTEGER              not null,
   ou_id              INTEGER              not null,
   department_id      INTEGER              not null,
   cust_account_id    INTEGER              default NULL,
   primary_flag       VARCHAR2(30),
   start_date         DATE                 default NULL,
   end_date           DATE                 default NULL,
   enable_flag        VARCHAR2(10),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_DEPARTMENT_CUST primary key (department_cust_id)
);

comment on column base_department_cust.department_cust_id is
'主键';

comment on column base_department_cust.ou_id is
'事业部';

comment on column base_department_cust.department_id is
'部门ID';

comment on column base_department_cust.cust_account_id is
'经销商';

comment on column base_department_cust.start_date is
'生效日期';

comment on column base_department_cust.end_date is
'失效日期';

comment on column base_department_cust.creation_date is
'创建时间';

comment on column base_department_cust.created_by is
'创建人';

comment on column base_department_cust.last_update_date is
'更新时间';

comment on column base_department_cust.last_updated_by is
'更新人';

comment on column base_department_cust.last_update_login is
'最后登录ID';

comment on column base_department_cust.delete_flag is
'删除标识';

comment on column base_department_cust.version_num is
'版本号';
create index base_organization_cust_n1 on base_department_cust (ou_id, department_id, cust_account_id, primary_flag);

/*==============================================================*/
/* Table: base_department_hierarchy                           */
/*==============================================================*/
create table base_department_hierarchy 
(
   seq_id             INTEGER            not null,
   ou_id              INTEGER              default NULL,
   department_id      INTEGER              default NULL,
   subordinate_department_id INTEGER              default NULL,
   level_difference   INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_DEPARTMENT_HIERARCHY primary key (seq_id)
);

comment on column base_department_hierarchy.seq_id is
'主键ID';

comment on column base_department_hierarchy.ou_id is
'事业部';

comment on column base_department_hierarchy.department_id is
'当前部门ID';

comment on column base_department_hierarchy.subordinate_department_id is
'下级部门ID';

comment on column base_department_hierarchy.level_difference is
'部门层级差异';

comment on column base_department_hierarchy.creation_date is
'创建时间';

comment on column base_department_hierarchy.created_by is
'创建人';

comment on column base_department_hierarchy.last_update_date is
'更新时间';

comment on column base_department_hierarchy.last_updated_by is
'更新人';

comment on column base_department_hierarchy.last_update_login is
'最后登录ID';

comment on column base_department_hierarchy.delete_flag is
'删除标识';

comment on column base_department_hierarchy.version_num is
'版本号';

create index idx_base_dept_hierarchy_n1 on base_department_hierarchy (ou_id, department_id, subordinate_department_id);

/*==============================================================*/
/* Table: base_department_lan                            */
/*==============================================================*/
create table base_department_lan 
(
   department_lan_id INTEGER            not null,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   department_id      INTEGER              default NULL,
   department_name    VARCHAR2(200),
   department_type    VARCHAR2(30),
   suffix             VARCHAR2(30),
   cost_center        VARCHAR2(30),
   language           VARCHAR2(30),
   constraint PK_base_department_lan primary key (department_lan_id)
);

comment on column base_department_lan.department_id is
'部门ID_外键';

comment on column base_department_lan.creation_date is
'创建时间';

comment on column base_department_lan.created_by is
'创建人';

comment on column base_department_lan.last_update_date is
'更新时间';

comment on column base_department_lan.last_updated_by is
'更新人';

comment on column base_department_lan.last_update_login is
'最后登录ID';

comment on column base_department_lan.delete_flag is
'删除标识';

comment on column base_department_lan.department_lan_id is
'语言部门ID';

create index IDDEX_BDPL_V1 on base_department_lan (department_id);
create index IDDEX_BDPL_V2 on base_department_lan (department_name);

/*==============================================================*/
/* Table: base_deployee_app_host_info                         */
/*==============================================================*/
create table base_deployee_app_host_info 
(
   host_id            INTEGER            not null,
   app_wap_code       VARCHAR2(64),
   host_name          VARCHAR2(64),
   host_port          INTEGER              default NULL,
   host_user_name     VARCHAR2(64),
   host_password      VARCHAR2(64),
   host_key           VARCHAR2(64),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_DEPLOYEE_APP_HOST_INFO primary key (host_id)
);

comment on column base_deployee_app_host_info.host_id is
'主机Id';

comment on column base_deployee_app_host_info.host_port is
'主机端口';

comment on column base_deployee_app_host_info.created_by is
'创建人';

comment on column base_deployee_app_host_info.creation_date is
'创建时间';

comment on column base_deployee_app_host_info.last_update_date is
'最后更新时间';

comment on column base_deployee_app_host_info.last_update_login is
'最后登录Id';

comment on column base_deployee_app_host_info.last_updated_by is
'最后更新人';

comment on column base_deployee_app_host_info.version_num is
'版本号';

comment on column base_deployee_app_host_info.delete_flag is
'是否删除0：未删除1：已删除';

/*==============================================================*/
/* Table: base_deployee_app_info                              */
/*==============================================================*/
create table base_deployee_app_info 
(
   app_wap_id         INTEGER            not null,
   host_id            INTEGER              default NULL,
   app_code           VARCHAR2(64),
   app_name           VARCHAR2(64),
   app_wap_code       VARCHAR2(64),
   app_wap_name       VARCHAR2(64),
   app_wap_desc       VARCHAR2(256),
   app_wap_sort_id    INTEGER              default NULL,
   app_wap_version    FLOAT(5)             default NULL,
   app_packing_version FLOAT(5)             default NULL,
   app_wap_access_home_path VARCHAR2(2000),
   app_wap_full_screen VARCHAR2(2),
   app_wap_backup_path VARCHAR2(1000),
   app_wap_image_path VARCHAR2(1000),
   app_wap_upload_path VARCHAR2(256),
   app_wap_deployee_time DATE                 default NULL,
   app_wap_status     INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_DEPLOYEE_APP_INFO primary key (app_wap_id)
);

comment on column base_deployee_app_info.app_wap_id is
'app应用主键';

comment on column base_deployee_app_info.host_id is
'app部署主机Id';

comment on column base_deployee_app_info.app_wap_sort_id is
'app应用排序';

comment on column base_deployee_app_info.app_wap_version is
'app应用版本';

comment on column base_deployee_app_info.app_packing_version is
'app打包版本';

comment on column base_deployee_app_info.app_wap_deployee_time is
'app应用发布时间';

comment on column base_deployee_app_info.app_wap_status is
'app状态';

comment on column base_deployee_app_info.created_by is
'创建人';

comment on column base_deployee_app_info.creation_date is
'创建时间';

comment on column base_deployee_app_info.last_update_date is
'最后更新时间';

comment on column base_deployee_app_info.last_update_login is
'最后登录id';

comment on column base_deployee_app_info.last_updated_by is
'最后更新人';

comment on column base_deployee_app_info.version_num is
'版本号';

comment on column base_deployee_app_info.delete_flag is
'是否删除0：未删除1：已删除';

/*==============================================================*/
/* Table: base_deployee_app_menu                              */
/*==============================================================*/
create table base_deployee_app_menu 
(
   app_menu_id        INTEGER            not null,
   app_wap_id         INTEGER              default NULL,
   app_menu_name      VARCHAR2(64),
   app_menu_code      VARCHAR2(64),
   app_menu_url       VARCHAR2(64),
   app_menu_image_path VARCHAR2(1000),
   app_menu_sort      INTEGER              default NULL,
   app_default_display INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_DEPLOYEE_APP_MENU primary key (app_menu_id)
);

comment on column base_deployee_app_menu.app_menu_id is
'app应用菜单主键';

comment on column base_deployee_app_menu.app_wap_id is
'app应用主键';

comment on column base_deployee_app_menu.app_menu_sort is
'app应用菜单排序';

comment on column base_deployee_app_menu.app_default_display is
'app应用默认是否显示';

comment on column base_deployee_app_menu.created_by is
'创建人';

comment on column base_deployee_app_menu.creation_date is
'创建时间';

comment on column base_deployee_app_menu.last_update_date is
'最后更新时间';

comment on column base_deployee_app_menu.last_update_login is
'最后登录id';

comment on column base_deployee_app_menu.last_updated_by is
'最后登录人';

comment on column base_deployee_app_menu.version_num is
'版本号';

comment on column base_deployee_app_menu.delete_flag is
'是否删除0：未删除1：已删除';

/*==============================================================*/
/* Table: base_export_record                                  */
/*==============================================================*/
create table base_export_record 
(
   id                 INTEGER            not null,
   export_id          VARCHAR2(45),
   user_id            INTEGER              default NULL,
   request_url        VARCHAR2(255),
   request_params     VARCHAR2(500),
   function_name      VARCHAR2(100),
   file_url           VARCHAR2(300),
   complete_date      DATE                 default NULL,
   status             VARCHAR2(2),
   version_num        INTEGER              default 0,
   CREATION_DATE        DATE                 default NULL,
   CREATED_BY           INTEGER              default NULL,
   LAST_UPDATED_BY      INTEGER              default NULL,
   LAST_UPDATE_DATE     DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_EXPORT_RECORD primary key (id)
);

comment on column base_export_record.complete_date is
'完成时间';

comment on column base_export_record.version_num is
'版本号';
create unique index export_id_UNIQUE on base_export_record (export_id);

/*==============================================================*/
/* Table: base_faq_help                                       */
/*==============================================================*/
create table base_faq_help 
(
   faq_id             INTEGER            not null,
   category_id        INTEGER              default NULL,
   faq_type           VARCHAR2(5)          default NULL,
   faq_title          VARCHAR2(240)        default NULL,
   faq_order          INTEGER              default NULL,
   is_display         VARCHAR2(5)          default NULL,
   is_top             VARCHAR2(5)          default NULL,
   start_time         DATE                 default NULL,
   end_time           DATE                 default NULL,
   faq_content        CLOB,
   company_id         INTEGER              default NULL,
   delete_flag        INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_FAQ_HELP primary key (faq_id)
);

comment on column base_faq_help.faq_id is
'主键Id';

comment on column base_faq_help.category_id is
'分类Id';

comment on column base_faq_help.faq_type is
'类别';

comment on column base_faq_help.faq_title is
'FAQ标题';

comment on column base_faq_help.faq_order is
'显示顺序';

comment on column base_faq_help.is_display is
'是否显示';

comment on column base_faq_help.is_top is
'是否置顶';

comment on column base_faq_help.start_time is
'生效时间';

comment on column base_faq_help.end_time is
'失效时间';

comment on column base_faq_help.faq_content is
'FAQ内容';

comment on column base_faq_help.company_id is
'品牌 快码COMPANY_TYPE';

comment on column base_faq_help.delete_flag is
'删除标识';

comment on column base_faq_help.created_by is
'创建人';

comment on column base_faq_help.creation_date is
'创建时间';

comment on column base_faq_help.last_updated_by is
'更新人';

comment on column base_faq_help.last_update_date is
'更新时间';

comment on column base_faq_help.last_update_login is
'最后一次更新人帐号';

comment on column base_faq_help.version_num is
'版本号';

create index faq_type_idx on base_faq_help (faq_type);
create index faq_order_idx on base_faq_help (faq_order);
create index help_company_id_idx on base_faq_help (company_id);

/*==============================================================*/
/* Table: base_function_collection                            */
/*==============================================================*/
create table base_function_collection 
(
   function_collection_id INTEGER            not null,
   user_id            INTEGER              default NULL,
   collection_type    VARCHAR2(45),
   function_name      VARCHAR2(45),
   function_url       VARCHAR2(255),
   request_method     VARCHAR2(10),
   menu_id            INTEGER              default NULL,
   resp_id            INTEGER              default NULL,
   system_code        VARCHAR2(100),
   params             VARCHAR2(255),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_FUNCTION_COLLECTION primary key (function_collection_id)
);

comment on column base_function_collection.user_id is
'用户id';

comment on column base_function_collection.menu_id is
'菜单id';

comment on column base_function_collection.resp_id is
'职责id';

comment on column base_function_collection.creation_date is
'创建日期';

comment on column base_function_collection.created_by is
'创建人';

comment on column base_function_collection.last_updated_by is
'更新人';

comment on column base_function_collection.last_update_date is
'更新日期';

comment on column base_function_collection.version_num is
'版本号';

comment on column base_function_collection.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_function_collection_lan                         */
/*==============================================================*/
create table base_function_collection_lan 
(
   collection_la_id   INTEGER            not null,
   function_name      VARCHAR2(80),
   function_collection_id INTEGER              not null,
   language           VARCHAR2(30),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default 0,
   constraint PK_BASE_FUN_COL_LAN primary key (collection_la_id)
);

comment on column base_function_collection_lan.collection_la_id is
'主键ID';

comment on column base_function_collection_lan.function_collection_id is
'系统编码';

comment on column base_function_collection_lan.created_by is
'创建人';

comment on column base_function_collection_lan.last_updated_by is
'更新人';

comment on column base_function_collection_lan.last_update_date is
'更新日期';

comment on column base_function_collection_lan.version_num is
'版本号';

create index INDEX_COLLECTION_V1 on base_function_collection_lan (function_collection_id);

/*==============================================================*/
/* Table: base_function_collection_user                       */
/*==============================================================*/
create table base_function_collection_user 
(
   function_collection_user_id INTEGER            not null,
   user_id            INTEGER              default NULL,
   function_collection_id INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_FUNCTION_COLLECTION_US primary key (function_collection_user_id)
);

comment on column base_function_collection_user.user_id is
'用户id';

comment on column base_function_collection_user.function_collection_id is
'菜单收藏id';

comment on column base_function_collection_user.creation_date is
'创建日期';

comment on column base_function_collection_user.created_by is
'创建人';

comment on column base_function_collection_user.last_updated_by is
'更新人';

comment on column base_function_collection_user.last_update_date is
'更新日期';

comment on column base_function_collection_user.version_num is
'版本号';

comment on column base_function_collection_user.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_inv_store_contact_t                            */
/*==============================================================*/
create table base_inv_store_contact_t 
(
   store_contact_id   INTEGER              not null,
   store_code         VARCHAR2(30),
   business_type      VARCHAR2(30),
   start_date         DATE                 default NULL,
   end_date           DATE                 default NULL,
   person_type        VARCHAR2(30),
   person_id          INTEGER              default NULL,
   person_name        VARCHAR2(100),
   phone              VARCHAR2(100),
   email              VARCHAR2(150),
   qq                 VARCHAR2(30),
   weixin_name        VARCHAR2(100),
   position_id        INTEGER              default NULL,
   memo               VARCHAR2(4000),
   status             CHAR(10),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_INV_STORE_CONTACT_T primary key (store_contact_id)
);

comment on column base_inv_store_contact_t.store_contact_id is
'主键';

comment on column base_inv_store_contact_t.start_date is
'开始日期';

comment on column base_inv_store_contact_t.end_date is
'结束日期';

comment on column base_inv_store_contact_t.person_id is
'人员ID';

comment on column base_inv_store_contact_t.position_id is
'人员职位ID';

comment on column base_inv_store_contact_t.creation_date is
'创建时间';

comment on column base_inv_store_contact_t.created_by is
'创建人';

comment on column base_inv_store_contact_t.last_update_date is
'更新时间';

comment on column base_inv_store_contact_t.last_updated_by is
'更新人';

comment on column base_inv_store_contact_t.last_update_login is
'最后登录ID';

comment on column base_inv_store_contact_t.delete_flag is
'删除标识';

comment on column base_inv_store_contact_t.version_num is
'版本';
create index BASE_INV_STORE_CONTACT_T_N1 on base_inv_store_contact_t (start_date, end_date);
create index BASE_INV_STORE_CONTACT_T_N2 on base_inv_store_contact_t (store_code);
create index BASE_INV_STORE_CONTACT_T_N3 on base_inv_store_contact_t (person_id);

/*==============================================================*/
/* Table: base_inv_store_t                                    */
/*==============================================================*/
create table base_inv_store_t 
(
   store_cust_id      INTEGER              not null,
   store_code         VARCHAR2(30),
   start_date         DATE                 default NULL,
   end_date           DATE                 default NULL,
   subinv_code        VARCHAR2(30),
   store_name         VARCHAR2(250),
   store_type         VARCHAR2(150),
   store_attdef       VARCHAR2(150),
   store_mode         VARCHAR2(150),
   unite_store_name   VARCHAR2(150),
   cust_account_id    INTEGER              default NULL,
   account_number     VARCHAR2(30),
   main_subinv_code   VARCHAR2(30),
   province           VARCHAR2(150),
   city               VARCHAR2(150),
   area               VARCHAR2(150),
   address1           VARCHAR2(240),
   phone              VARCHAR2(150),
   jingwei            VARCHAR2(150),
   status             VARCHAR2(2),
   store_user_id      INTEGER              default NULL,
   ou_id              INTEGER              default NULL,
   version            INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   memo               VARCHAR2(4000),
   x_store_attdef     VARCHAR2(150),
   store_tier         VARCHAR2(150),
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_INV_STORE_T primary key (store_cust_id)
);

comment on column base_inv_store_t.store_cust_id is
'主键ID';

comment on column base_inv_store_t.start_date is
'开始日期';

comment on column base_inv_store_t.end_date is
'结束日期';

comment on column base_inv_store_t.cust_account_id is
'经销商客户ID(冗余)';

comment on column base_inv_store_t.store_user_id is
'门店用户名ID';

comment on column base_inv_store_t.ou_id is
'事业部';

comment on column base_inv_store_t.version is
'版本号';

comment on column base_inv_store_t.creation_date is
'创建时间';

comment on column base_inv_store_t.created_by is
'创建人';

comment on column base_inv_store_t.last_update_date is
'更新时间';

comment on column base_inv_store_t.last_updated_by is
'更新人';

comment on column base_inv_store_t.last_update_login is
'最后登录ID';

comment on column base_inv_store_t.delete_flag is
'删除标识';

comment on column base_inv_store_t.version_num is
'版本';

create index BASE_INV_STORE_T_N1 on base_inv_store_t (store_code);
create index BASE_INV_STORE_T_N2 on base_inv_store_t (subinv_code);
create index BASE_INV_STORE_T_N3 on base_inv_store_t (account_number);
create index BASE_INV_STORE_T_N4 on base_inv_store_t (main_subinv_code);
create index BASE_INV_STORE_T_N5 on base_inv_store_t (province);
create index BASE_INV_STORE_T_N6 on base_inv_store_t (city);
create index BASE_INV_STORE_T_N7 on base_inv_store_t (main_subinv_code, start_date, end_date);
create index BASE_INV_STORE_T_N8 on base_inv_store_t (ou_id);

/*==============================================================*/
/* Table: base_issue_feedback                                 */
/*==============================================================*/
create table base_issue_feedback 
(
   feedback_id        INTEGER            not null,
   title              VARCHAR2(100),
   content            CLOB,
   system_code        VARCHAR2(100),
   ou_id              INTEGER              default NULL,
   source             VARCHAR2(20),
   status             VARCHAR2(20),
   "comment"            CLOB,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_ISSUE_FEEDBACK primary key (feedback_id)
);

comment on column base_issue_feedback.feedback_id is
'主键ID';

comment on column base_issue_feedback.ou_id is
'事业部';

comment on column base_issue_feedback.created_by is
'创建人';

comment on column base_issue_feedback.creation_date is
'创建时间';

comment on column base_issue_feedback.last_updated_by is
'更新人';

comment on column base_issue_feedback.last_update_date is
'更新时间';

comment on column base_issue_feedback.last_update_login is
'更新登录ID';

comment on column base_issue_feedback.delete_flag is
'删除标识';

comment on column base_issue_feedback.version_num is
'版本号';

create index idx_base_issue_feedback_n1 on base_issue_feedback (system_code, ou_id, status);
create index idx_base_issue_feedback_n2 on base_issue_feedback (title);

/*==============================================================*/
/* Table: base_job                                            */
/*==============================================================*/
create table base_job 
(
   job_id             INTEGER            not null,
   job_code           VARCHAR2(20),
   job_name           VARCHAR2(60),
   "comment"            VARCHAR2(300),
   date_from          DATE                 default NULL,
   date_to            DATE                 default NULL,
   enable_flag        VARCHAR2(6),
   ou_id              INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_JOB primary key (job_id)
);

comment on column base_job.job_id is
'职务ID';

comment on column base_job.date_from is
'生效日期';

comment on column base_job.date_to is
'失效日期';

comment on column base_job.ou_id is
'事业部';

comment on column base_job.creation_date is
'创建时间';

comment on column base_job.created_by is
'创建人';

comment on column base_job.last_update_date is
'更新时间';

comment on column base_job.last_updated_by is
'更新人';

comment on column base_job.last_update_login is
'最后登录ID';

comment on column base_job.delete_flag is
'删除标识';

comment on column base_job.version_num is
'版本号';

create unique index idx_base_job_u1 on base_job (ou_id, job_name);

/*==============================================================*/
/* Table: base_job_language                                   */
/*==============================================================*/
create table base_job_lan 
(
   job_lan_id    INTEGER            not null,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   language           VARCHAR2(30),
   job_id             INTEGER              default NULL,
   job_name           VARCHAR2(60),
   constraint PK_BASE_JOB_LAN primary key (job_lan_id)
);

comment on column base_job_lan.job_lan_id is
'主键ID';

comment on column base_job_lan.creation_date is
'创建时间';

comment on column base_job_lan.created_by is
'创建人';

comment on column base_job_lan.last_update_date is
'更新时间';

comment on column base_job_lan.last_updated_by is
'更新人';

comment on column base_job_lan.last_update_login is
'最后登录ID';

comment on column base_job_lan.delete_flag is
'删除标识';

comment on column base_job_lan.job_id is
'外键ID_BASE_JOB';

/*==============================================================*/
/* Table: base_login_log                                      */
/*==============================================================*/
create table base_login_log 
(
   id                 INTEGER            not null,
   app_name           VARCHAR2(45)         default NULL,
   app_version        VARCHAR2(20)         default NULL,
   brand              VARCHAR2(45)         default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   deviceinfo         VARCHAR2(45)         default NULL,
   ip                 VARCHAR2(45)         default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   login_mode         VARCHAR2(2)          default NULL,
   phone_number       VARCHAR2(20)         default NULL,
   system             VARCHAR2(20)         default NULL,
   system_version     VARCHAR2(20)         default NULL,
   total_mem          VARCHAR2(20)         default NULL,
   user_agent         VARCHAR2(255)        default NULL,
   user_id            INTEGER              not null,
   user_name          VARCHAR2(100)        default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_LOGIN_LOG primary key (id)
);

/*==============================================================*/
/* Table: base_lookup_types                                   */
/*==============================================================*/
create table base_lookup_types 
(
   lookup_type_id     INTEGER            not null,
   lookup_type        VARCHAR2(30),
   meaning            VARCHAR2(150),
   description        VARCHAR2(240),
   customization_level VARCHAR2(1),
   system_code        VARCHAR2(30),
   parent_lookup_type_id INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   parent_type_id     INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   job_name           VARCHAR2(60),
   constraint PK_BASE_LOOKUP_TYPES primary key (lookup_type_id)
);

comment on column base_lookup_types.lookup_type_id is
'主键ID';

comment on column base_lookup_types.parent_lookup_type_id is
'父类型ID';

comment on column base_lookup_types.creation_date is
'创建日期';

comment on column base_lookup_types.created_by is
'创建人';

comment on column base_lookup_types.last_updated_by is
'更新人';

comment on column base_lookup_types.last_update_date is
'更新日期';

comment on column base_lookup_types.version_num is
'版本号';

create index IND_BASE_LOOKUP_TYPES_N1 on base_lookup_types (lookup_type);

/*==============================================================*/
/* Table: base_lookup_types_lan                          */
/*==============================================================*/
create table base_lookup_types_lan 
(
   lookup_type_lan_id INTEGER            not null,
   meaning            VARCHAR2(150),
   description        VARCHAR2(240),
   lookup_type_id     INTEGER              default NULL,
   lan           VARCHAR2(30),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default 0,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_LOOKUP_TYPES_LAN primary key (lookup_type_lan_id)
);

comment on column base_lookup_types_lan.lookup_type_lan_id is
'主键ID';

comment on column base_lookup_types_lan.lookup_type_id is
'系统编码';

comment on column base_lookup_types_lan.created_by is
'创建人';

comment on column base_lookup_types_lan.last_updated_by is
'更新人';

comment on column base_lookup_types_lan.last_update_date is
'更新日期';

comment on column base_lookup_types_lan.version_num is
'版本号';

/*==============================================================*/
/* Table: base_lookup_values                                  */
/*==============================================================*/
create table base_lookup_values 
(
   lookup_values_id   INTEGER            not null,
   lookup_type        VARCHAR2(30),
   lookup_code        VARCHAR2(30),
   meaning            VARCHAR2(150),
   description        VARCHAR2(240),
   parent_lookup_values_id VARCHAR2(30),
   enabled_flag       VARCHAR2(1),
   start_date_active  DATE                 default NULL,
   end_date_active    DATE                 default NULL,
   system_code        VARCHAR2(30),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   bu_org_id          VARCHAR2(50),
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_LOOKUP_VALUES primary key (lookup_values_id)
);

comment on column base_lookup_values.lookup_values_id is
'主键ID';

comment on column base_lookup_values.start_date_active is
'生效日期';

comment on column base_lookup_values.end_date_active is
'失效日期';

comment on column base_lookup_values.creation_date is
'创建日期';

comment on column base_lookup_values.created_by is
'创建人';

comment on column base_lookup_values.last_updated_by is
'更新人';

comment on column base_lookup_values.last_update_date is
'更新日期';

comment on column base_lookup_values.version_num is
'版本号';

create index normal_index_lookup_code on base_lookup_values (lookup_code);
create index IND_BASE_LOOKUP_VALUES_N2 on base_lookup_values (parent_lookup_values_id);
create index IND_BASE_LOOKUP_VALUES_N1 on base_lookup_values (lookup_type, lookup_code, system_code);

/*==============================================================*/
/* Table: base_lookup_values_lan                         */
/*==============================================================*/
create table base_lookup_values_lan 
(
   lookup_values_lan_id INTEGER            not null,
   meaning            VARCHAR2(500),
   description        VARCHAR2(240),
   lookup_values_id   INTEGER              not null,
   lan           VARCHAR2(30),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default 0,
   delete_flag        INTEGER              default 0,
   lookup_code        VARCHAR2(30),
   constraint PK_BASE_LOOKUP_VALUES_LAN primary key (lookup_values_lan_id)
);

comment on column base_lookup_values_lan.lookup_values_lan_id is
'主键ID';

comment on column base_lookup_values_lan.lookup_values_id is
'系统编码';

comment on column base_lookup_values_lan.created_by is
'创建人';

comment on column base_lookup_values_lan.last_updated_by is
'更新人';

comment on column base_lookup_values_lan.last_update_date is
'更新日期';

comment on column base_lookup_values_lan.version_num is
'版本号';

create index INDEX_BLVL_V1 on base_lookup_values_lan (lookup_values_id);

/*==============================================================*/
/* Table: base_manual_supplement                              */
/*==============================================================*/
create table base_manual_supplement 
(
   manual_id          INTEGER            not null,
   message_index      INTEGER              default NULL,
   message_body       BLOB,
   request_URL        VARCHAR2(1500),
   queue_name         VARCHAR2(200),
   status             VARCHAR2(255),
   send_queue_times   INTEGER              default NULL,
   message_content_bean BLOB,
   delete_flag        INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_MANUAL_SUPPLEMENT primary key (manual_id)
);

comment on column base_manual_supplement.message_index is
'消息体序号';

comment on column base_manual_supplement.message_body is
'消息体内容';

comment on column base_manual_supplement.send_queue_times is
'发送到mq 的次数';

comment on column base_manual_supplement.message_content_bean is
'在mq中的完整的消息内容';

comment on column base_manual_supplement.delete_flag is
'是否删除（0：未删除；1：已删除）';

comment on column base_manual_supplement.creation_date is
'创建日期';

comment on column base_manual_supplement.created_by is
'创建人';

comment on column base_manual_supplement.last_updated_by is
'更新人';

comment on column base_manual_supplement.last_update_date is
'更新日期';

comment on column base_manual_supplement.version_num is
'版本号';

comment on column base_manual_supplement.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_menu                                           */
/*==============================================================*/
create table base_menu 
(
   menu_id            INTEGER            not null,
   menu_parent_id     INTEGER              default NULL,
   menu_code          VARCHAR2(100),
   order_no           INTEGER              default NULL,
   menu_name          VARCHAR2(100),
   menu_desc          VARCHAR2(256),
   level_id           INTEGER              default NULL,
   menu_type          VARCHAR2(5),
   parameter          VARCHAR2(2000),
   system_code        VARCHAR2(30),
   image_link         VARCHAR2(60),
   image_color        VARCHAR2(10),
   html_url           VARCHAR2(240),
   from_type          VARCHAR2(10),
   enabled            VARCHAR2(10),
   start_date_active  DATE                 default NULL,
   end_date_active    DATE                 default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_MENU primary key (menu_id)
);

comment on column base_menu.menu_id is
'菜单Id';

comment on column base_menu.menu_parent_id is
'上级菜单Id';

comment on column base_menu.order_no is
'排序号';

comment on column base_menu.level_id is
'层级ID';

comment on column base_menu.start_date_active is
'启用时间';

comment on column base_menu.end_date_active is
'失效时间';

comment on column base_menu.creation_date is
'创建日期';

comment on column base_menu.created_by is
'创建人';

comment on column base_menu.last_updated_by is
'更新人';

comment on column base_menu.last_update_date is
'更新日期';

comment on column base_menu.version_num is
'版本号';

comment on column base_menu.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_menu_language                                  */
/*==============================================================*/
create table base_menu_language 
(
   lan_menu_id        INTEGER            not null,
   menu_id            INTEGER              not null,
   language           VARCHAR2(100)        not null,
   menu_name          VARCHAR2(100)        default NULL,
   menu_desc          VARCHAR2(256)        default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0 not null,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default NULL,
   constraint PK_BASE_MENU_LANGUAGE primary key (lan_menu_id, version_num)
);

comment on column base_menu_language.lan_menu_id is
'主键';

comment on column base_menu_language.menu_id is
'关联base_menu表主键';

comment on column base_menu_language.language is
'语言种类';

comment on column base_menu_language.menu_name is
'菜单名称';

comment on column base_menu_language.menu_desc is
'菜单提示信息';

comment on column base_menu_language.creation_date is
'创建日期';

comment on column base_menu_language.created_by is
'创建人';

comment on column base_menu_language.last_updated_by is
'更新人';

comment on column base_menu_language.last_update_date is
'更新日期';

comment on column base_menu_language.version_num is
'版本号';

comment on column base_menu_language.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_message_bu                                     */
/*==============================================================*/
create table base_message_bu 
(
   bu_mess_id         INTEGER            not null,
   con_mess_id        INTEGER              not null,
   bu_id              INTEGER              not null,
   user_type          VARCHAR2(30),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_MESSAGE_BU primary key (bu_mess_id)
);

comment on column base_message_bu.bu_mess_id is
'主键Id';

comment on column base_message_bu.con_mess_id is
'消息内容ID';

comment on column base_message_bu.bu_id is
'接收事业部ID';

/*==============================================================*/
/* Table: base_message_content                                */
/*==============================================================*/
create table base_message_content 
(
   con_mess_id        INTEGER            not null,
   con_mess_title     VARCHAR2(256),
   con_mess_type      VARCHAR2(10),
   con_mess_state     INTEGER              default NULL,
   con_mess_system    VARCHAR2(32),
   con_send_date      DATE                 default NULL,
   con_mess_content   VARCHAR2(2000),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_MESSAGE_CONTENT primary key (con_mess_id)
);

comment on column base_message_content.con_mess_id is
'主键Id';

comment on column base_message_content.con_mess_state is
'消息状态 0:未发送给任何人；1:已发送；2:撤回';

comment on column base_message_content.con_send_date is
'消息的发送时间';

/*==============================================================*/
/* Table: base_message_department                             */
/*==============================================================*/
create table base_message_department 
(
   dep_mess_id        INTEGER            not null,
   con_mess_id        INTEGER              not null,
   bu_mess_id         INTEGER              not null,
   dept_id            INTEGER              not null,
   dept_code          VARCHAR2(20),
   dept_name          VARCHAR2(200),
   sending_num        INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_MESSAGE_DEPARTMENT primary key (dep_mess_id)
);

comment on column base_message_department.dep_mess_id is
'主键Id';

comment on column base_message_department.con_mess_id is
'消息内容ID';

comment on column base_message_department.bu_mess_id is
'接收对象组合表主键ID';

comment on column base_message_department.dept_id is
'接收部门ID';

comment on column base_message_department.sending_num is
'发送人数';

/*==============================================================*/
/* Table: base_message_instation                              */
/*==============================================================*/
create table base_message_instation 
(
   ins_mess_id        INTEGER            not null,
   con_mess_id        INTEGER              default NULL,
   mess_title         VARCHAR2(256),
   mess_content       VARCHAR2(2000),
   mess_status        INTEGER              default 0,
   mess_from_system   VARCHAR2(32),
   mess_from_module   VARCHAR2(32),
   mess_receiver_id   INTEGER              default NULL,
   mess_receiver      VARCHAR2(150),
   mess_sender_id     INTEGER              default NULL,
   mess_sender        VARCHAR2(150),
   source_id          VARCHAR2(80),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_MESSAGE_INSTATION primary key (ins_mess_id)
);

comment on column base_message_instation.ins_mess_id is
'主键Id';

comment on column base_message_instation.con_mess_id is
'消息内容ID';

comment on column base_message_instation.mess_status is
'消息状态 已读/未读';

comment on column base_message_instation.mess_receiver_id is
'消息接收人ID';

comment on column base_message_instation.mess_sender_id is
'消息接收人员ID';

/*==============================================================*/
/* Table: base_message_person                                 */
/*==============================================================*/
create table base_message_person 
(
   per_mess_id        INTEGER            not null,
   con_mess_id        INTEGER              not null,
   bu_mess_id         INTEGER              not null,
   dep_mess_id        INTEGER              not null,
   dept_id            INTEGER              not null,
   user_type          VARCHAR2(30),
   user_id            INTEGER              not null,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_MESSAGE_PERSON primary key (per_mess_id)
);

comment on column base_message_person.per_mess_id is
'主键Id';

comment on column base_message_person.con_mess_id is
'消息内容ID';

comment on column base_message_person.bu_mess_id is
'接收对象组合表主键ID';

comment on column base_message_person.dep_mess_id is
'接收部门表主键ID';

comment on column base_message_person.dept_id is
'接收部门ID';

comment on column base_message_person.user_id is
'接收用户ID';

/*==============================================================*/
/* Table: base_org_responsibility                             */
/*==============================================================*/
create table base_org_responsibility 
(
   org_resp_id        INTEGER            not null,
   responsibility_id  INTEGER              default NULL,
   org_id             INTEGER              default NULL,
   start_date_active  DATE                 default NULL,
   end_date_active    DATE                 default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_ORG_RESPONSIBILITY primary key (org_resp_id)
);

comment on column base_org_responsibility.org_resp_id is
'主键';

comment on column base_org_responsibility.responsibility_id is
'职责标识';

comment on column base_org_responsibility.org_id is
'组织机构Id';

comment on column base_org_responsibility.start_date_active is
'生效时间';

comment on column base_org_responsibility.end_date_active is
'失效时间';

comment on column base_org_responsibility.creation_date is
'创建日期';

comment on column base_org_responsibility.created_by is
'创建人';

comment on column base_org_responsibility.last_updated_by is
'更新人';

comment on column base_org_responsibility.last_update_date is
'更新日期';

comment on column base_org_responsibility.version_num is
'版本号';

comment on column base_org_responsibility.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_organization                                   */
/*==============================================================*/
create table base_organization 
(
   org_id             INTEGER            not null,
   parent_org_id      INTEGER              default NULL,
   org_code           VARCHAR2(20),
   org_name           VARCHAR2(200),
   tree_type          VARCHAR2(20),
   channel_type       VARCHAR2(20),
   business_type      VARCHAR2(20),
   is_dep             VARCHAR2(1),
   org_type           VARCHAR2(20),
   org_level          INTEGER              default NULL,
   is_leaf            INTEGER              default NULL,
   start_date         DATE                 default NULL,
   end_date           DATE                 default NULL,
   enabled            VARCHAR2(2),
   remark             VARCHAR2(2000),
   org_pinyin_name    VARCHAR2(100),
   org_simple_pinyin_name VARCHAR2(50),
   order_no           INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   org_hierarchy_id   VARCHAR2(200),
   org_email          VARCHAR2(50),
   source_system_id   VARCHAR2(20),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   leader_id          INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   organization_id    INTEGER              default NULL,
   work_statistics    VARCHAR2(30),
   constraint PK_BASE_ORGANIZATION primary key (org_id)
);

comment on column base_organization.org_id is
'组织机构Id';

comment on column base_organization.parent_org_id is
'父机构Id';

comment on column base_organization.org_level is
'组织机构层级';

comment on column base_organization.is_leaf is
'是否是叶子节点，(0：叶子节点，1：非叶子节点)';

comment on column base_organization.start_date is
'启用日期';

comment on column base_organization.end_date is
'失效日期';

comment on column base_organization.order_no is
'排序号';

comment on column base_organization.delete_flag is
'是否删除（0：未删除；1：已删除）';

comment on column base_organization.creation_date is
'创建日期';

comment on column base_organization.created_by is
'创建人';

comment on column base_organization.last_updated_by is
'更新人';

comment on column base_organization.last_update_date is
'更新日期';

comment on column base_organization.version_num is
'版本号';

comment on column base_organization.leader_id is
'组织领导';

comment on column base_organization.organization_id is
'关联org_id，表示库存组织所属的机构';

/*==============================================================*/
/* Table: base_organization_lan                                */
/*==============================================================*/
create table base_organization_lan 
(
   org_en_id          INTEGER            not null,
   org_id             INTEGER              not null,
   language           VARCHAR2(22)         default NULL,
   parent_org_id      INTEGER              not null,
   org_name_en        VARCHAR2(200),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_ORGANIZATION_LAN primary key (org_en_id)
);

comment on column base_organization_lan.org_en_id is
'英文组织机构id';

comment on column base_organization_lan.language is
'语言标识';

comment on column base_organization_lan.version_num is
'版本号';

comment on column base_organization_lan.delete_flag is
'是否删除（0：未删除；1：已删除）';

/*==============================================================*/
/* Table: base_pda_inv_cfg                                    */
/*==============================================================*/
create table base_pda_inv_cfg 
(
   CFG_ID               INTEGER            not null,
   SUB_INV_CODE         VARCHAR2(32),
   ORGANIZATION_ID      INTEGER              default NULL,
   CHANNEL_CODE         VARCHAR2(10),
   ROLE_ID              INTEGER              default NULL,
   USER_ID              INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_PDA_INV_CFG primary key (CFG_ID)
);

comment on column base_pda_inv_cfg.CFG_ID is
'主键Id';

comment on column base_pda_inv_cfg.ORGANIZATION_ID is
'库存组织ID';

comment on column base_pda_inv_cfg.USER_ID is
'用户ID';

comment on column base_pda_inv_cfg.delete_flag is
'是否删除（0：未删除；1：已删除）';

comment on column base_pda_inv_cfg.creation_date is
'创建日期';

comment on column base_pda_inv_cfg.created_by is
'创建人';

comment on column base_pda_inv_cfg.last_updated_by is
'更新人';

comment on column base_pda_inv_cfg.last_update_date is
'更新日期';

comment on column base_pda_inv_cfg.version_num is
'版本号';

comment on column base_pda_inv_cfg.last_update_login is
'last_update_login';

create index  index_inv_code on base_pda_inv_cfg (SUB_INV_CODE);
create index  index_user_id on base_pda_inv_cfg (USER_ID);

/*==============================================================*/
/* Table: base_pda_role_cfg                                   */
/*==============================================================*/
create table base_pda_role_cfg 
(
   PDA_ROLE_CFG_ID      INTEGER            not null,
   ROLE_ID              INTEGER              default NULL,
   ORGANIZATION_ID      INTEGER              default NULL,
   CHANNEL_CODE         VARCHAR2(10),
   MENU_ID              INTEGER              default NULL,
   INV_CODE             VARCHAR2(255),
   INV_NAME             VARCHAR2(255),
   DESCRIPTION          VARCHAR2(400),
   enabled            VARCHAR2(5),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_PDA_ROLE_CFG primary key (PDA_ROLE_CFG_ID)
);

comment on column base_pda_role_cfg.PDA_ROLE_CFG_ID is
'主键';

comment on column base_pda_role_cfg.ROLE_ID is
'角色权限ID';

comment on column base_pda_role_cfg.ORGANIZATION_ID is
'库存组织ID';

comment on column base_pda_role_cfg.MENU_ID is
'菜单ID';

comment on column base_pda_role_cfg.creation_date is
'创建日期';

comment on column base_pda_role_cfg.created_by is
'创建人';

comment on column base_pda_role_cfg.last_updated_by is
'更新人';

comment on column base_pda_role_cfg.last_update_date is
'更新日期';

comment on column base_pda_role_cfg.version_num is
'版本号';

comment on column base_pda_role_cfg.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_person                                         */
/*==============================================================*/
create table base_person 
(
   person_id          INTEGER            not null,
   employee_number    VARCHAR2(30),
   person_name        VARCHAR2(150),
   person_type        VARCHAR2(10),
   sex                VARCHAR2(10),
   birth_day          DATE                 default NULL,
   card_no            VARCHAR2(20),
   enabled            VARCHAR2(1),
   tel_phone          VARCHAR2(30),
   mobile_phone       VARCHAR2(30),
   email              VARCHAR2(60),
   postal_address     VARCHAR2(240),
   postcode           VARCHAR2(10),
   service_store_id   INTEGER              default NULL,
   source_system_id   INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   source_code        VARCHAR2(30),
   source_id          INTEGER              default NULL,
   constraint PK_BASE_PERSON primary key (person_id)
);

comment on column base_person.person_id is
'人员Id';

comment on column base_person.birth_day is
'生日';

comment on column base_person.service_store_id is
'所属服务店';

comment on column base_person.source_system_id is
'源系统ID';

comment on column base_person.creation_date is
'创建日期';

comment on column base_person.created_by is
'创建人';

comment on column base_person.last_updated_by is
'更新人';

comment on column base_person.last_update_date is
'更新日期';

comment on column base_person.version_num is
'版本号';

/*==============================================================*/
/* Table: base_person_assign                                  */
/*==============================================================*/
create table base_person_assign 
(
   assign_id          INTEGER            not null,
   person_id          INTEGER              default NULL,
   position_id        INTEGER              default NULL,
   job_id             INTEGER              default NULL,
   date_from          DATE                 default NULL,
   date_to            DATE                 default NULL,
   ou_id              INTEGER              default NULL,
   mgr_flag           VARCHAR2(10),
   primary_flag       VARCHAR2(10),
   enable_flag        VARCHAR2(10),
   budget_view        VARCHAR2(30),
   budget_maintain    VARCHAR2(30),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_PERSON_ASSIGN primary key (assign_id)
);

comment on column base_person_assign.assign_id is
'分配ID';

comment on column base_person_assign.person_id is
'人员ID';

comment on column base_person_assign.position_id is
'职位ID';

comment on column base_person_assign.job_id is
'职务ID';

comment on column base_person_assign.date_from is
'生效日期';

comment on column base_person_assign.date_to is
'失效日期';

comment on column base_person_assign.ou_id is
'事业部';

comment on column base_person_assign.creation_date is
'创建时间';

comment on column base_person_assign.created_by is
'创建人';

comment on column base_person_assign.last_update_date is
'更新时间';

comment on column base_person_assign.last_updated_by is
'更新人';

comment on column base_person_assign.last_update_login is
'最后登录ID';

comment on column base_person_assign.delete_flag is
'删除标识';

comment on column base_person_assign.version_num is
'版本号';

create index  idx_base_person_assign_n1 on base_person_assign (person_id);
create index  idx_base_person_assign_n2 on base_person_assign (position_id);
create index idx_base_person_assign_n3 on base_person_assign (job_id);

/*==============================================================*/
/* Table: base_person_cust                                    */
/*==============================================================*/
create table base_person_cust 
(
   person_cust_id     INTEGER              not null,
   ou_id              INTEGER              not null,
   person_id          INTEGER              not null,
   position_id        INTEGER              default NULL,
   cust_account_id    INTEGER              default NULL,
   primary_flag       VARCHAR2(30),
   start_date         DATE                 default NULL,
   end_date           DATE                 default NULL,
   enable_flag        VARCHAR2(10),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_PERSON_CUST primary key (person_cust_id)
);

comment on column base_person_cust.person_cust_id is
'主键';

comment on column base_person_cust.ou_id is
'事业部';

comment on column base_person_cust.person_id is
'人员ID';

comment on column base_person_cust.position_id is
'职位ID';

comment on column base_person_cust.cust_account_id is
'经销商';

comment on column base_person_cust.start_date is
'生效日期';

comment on column base_person_cust.end_date is
'失效日期';

comment on column base_person_cust.creation_date is
'创建时间';

comment on column base_person_cust.created_by is
'创建人';

comment on column base_person_cust.last_update_date is
'更新时间';

comment on column base_person_cust.last_updated_by is
'更新人';

comment on column base_person_cust.last_update_login is
'最后登录ID';

comment on column base_person_cust.delete_flag is
'删除标识';

comment on column base_person_cust.version_num is
'版本号';

create index base_person_cust_n1 on base_person_cust (ou_id, person_id, cust_account_id, primary_flag);

/*==============================================================*/
/* Table: base_person_en                                      */
/*==============================================================*/
create table base_person_lan 
(
   person_id_en       INTEGER            not null,
   person_id          INTEGER              not null,
   person_name_en     VARCHAR2(150)        default NULL,
   language           VARCHAR2(22)         default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_PERSON_LAN primary key (person_id_en)
);

comment on column base_person_lan.person_name_en is
'人员姓名英文';

comment on column base_person_lan.language is
'语言标识';

/*==============================================================*/
/* Table: base_person_level                                   */
/*==============================================================*/
create table base_person_level 
(
   level_id           INTEGER            not null,
   person_id          INTEGER              default NULL,
   position_id        INTEGER              default NULL,
   mgr_person_id      INTEGER              default NULL,
   mgr_position_id    INTEGER              default NULL,
   date_from          DATE                 default NULL,
   date_to            DATE                 default NULL,
   ou_id              INTEGER              default NULL,
   enable_flag        VARCHAR2(10),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_PERSON_LEVEL primary key (level_id)
);

comment on column base_person_level.level_id is
'主键ID';

comment on column base_person_level.person_id is
'人员ID';

comment on column base_person_level.position_id is
'职位ID';

comment on column base_person_level.mgr_person_id is
'上级人员ID';

comment on column base_person_level.mgr_position_id is
'上级职位ID';

comment on column base_person_level.date_from is
'生效日期';

comment on column base_person_level.date_to is
'失效日期';

comment on column base_person_level.ou_id is
'事业部';

comment on column base_person_level.creation_date is
'创建时间';

comment on column base_person_level.created_by is
'创建人';

comment on column base_person_level.last_update_date is
'更新时间';

comment on column base_person_level.last_updated_by is
'更新人';

comment on column base_person_level.last_update_login is
'最后登录ID';

comment on column base_person_level.delete_flag is
'删除标识';

comment on column base_person_level.version_num is
'版本号';
create index idx_base_person_level_n1 on base_person_level (position_id);
create index  idx_base_person_level_n2 on base_person_level (mgr_position_id);

/*==============================================================*/
/* Table: base_person_organization                            */
/*==============================================================*/
create table base_person_organization 
(
   person_org_id      INTEGER            not null,
   position_id        INTEGER              default NULL,
   org_id             INTEGER              default NULL,
   person_id          INTEGER              default NULL,
   start_date         DATE                 default NULL,
   end_date           DATE                 default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_PERSON_ORGANIZATION primary key (person_org_id)
);

comment on column base_person_organization.person_org_id is
'主键Id';

comment on column base_person_organization.position_id is
'职位ID';

comment on column base_person_organization.org_id is
'组织机构Id';

comment on column base_person_organization.person_id is
'人员Id';

comment on column base_person_organization.start_date is
'生效日期';

comment on column base_person_organization.end_date is
'失效日期';

comment on column base_person_organization.creation_date is
'创建日期';

comment on column base_person_organization.created_by is
'创建人';

comment on column base_person_organization.last_updated_by is
'更新人';

comment on column base_person_organization.last_update_date is
'更新日期';

comment on column base_person_organization.version_num is
'版本号';

/*==============================================================*/
/* Table: base_position                                       */
/*==============================================================*/
create table base_position 
(
   position_id        INTEGER            not null,
   department_id      INTEGER              default NULL,
   job_id             INTEGER              default NULL,
   position_code      VARCHAR2(40),
   position_name      VARCHAR2(200),
   date_from          DATE                 default NULL,
   date_to            DATE                 default NULL,
   enable_flag        VARCHAR2(10),
   biz_line_type      VARCHAR2(50),
   channel            VARCHAR2(50),
   ou_id              INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   version_num        INTEGER              default 0,
   constraint PK_BASE_POSITION primary key (position_id)
);

comment on column base_position.position_id is
'职位ID';

comment on column base_position.department_id is
'部门ID';

comment on column base_position.job_id is
'职务ID';

comment on column base_position.date_from is
'生效日期';

comment on column base_position.date_to is
'失效日期';

comment on column base_position.ou_id is
'事业部';

comment on column base_position.creation_date is
'创建时间';

comment on column base_position.created_by is
'创建人';

comment on column base_position.last_update_date is
'更新时间';

comment on column base_position.last_updated_by is
'更新人';

comment on column base_position.last_update_login is
'最后登录ID';

comment on column base_position.delete_flag is
'删除标识';

comment on column base_position.version_num is
'版本号';

create index  idx_base_position_u1 on base_position(ou_id, position_name);
create index  idx_base_position_n1 on base_position(position_name);
create index  idx_base_position_n2 on base_position (department_id);
create index  idx_base_position_n3 on base_position (job_id);

   
   
/*==============================================================*/
/* Table: base_position_language                              */
/*==============================================================*/
create table base_position_lan 
(
   position_id_lan INTEGER            not null,
   language           VARCHAR2(10),
   position_id        INTEGER              default NULL,
   department_id      INTEGER              default NULL,
   job_id             INTEGER              default NULL,
   position_code      VARCHAR2(40),
   position_name      VARCHAR2(200),
   date_from          DATE                 default NULL,
   date_to            DATE                 default NULL,
   enable_flag        VARCHAR2(10),
   biz_line_type      VARCHAR2(50),
   channel            VARCHAR2(50),
   ou_id              INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_POSITION_LAN primary key (position_id_lan)
);

/*==============================================================*/
/* Table: base_privilege_detail                               */
/*==============================================================*/
create table base_privilege_detail 
(
   privilege_detail_id INTEGER            not null,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   privilege_detail_code VARCHAR2(150)        default NULL,
   privilege_detail_value VARCHAR2(50)         default NULL,
   privilege_record_id INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_PRIVILEGE_DETAIL primary key (privilege_detail_id)
);

/*==============================================================*/
/* Table: base_privilege_record                               */
/*==============================================================*/
create table base_privilege_record 
(
   privilege_record_id INTEGER            not null,
   access_type        VARCHAR2(10)         default NULL,
   business_code      VARCHAR2(36)         default NULL,
   businessTableName  VARCHAR2(30)         default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   enabled            VARCHAR2(5)          default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   org_id             INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_PRIVILEGE_RECORD primary key (privilege_record_id)
);

/*==============================================================*/
/* Table: base_product_info                                   */
/*==============================================================*/
create table base_product_info 
(
   product_id         INTEGER            not null,
   ORGANIZATION_ID      INTEGER              default NULL,
   ITEM_ID              INTEGER              default NULL,
   ORG_ID               INTEGER              default NULL,
   CHANNEL_CODE         VARCHAR2(100),
   ITEM_CODE            VARCHAR2(50),
   IS_VALID             VARCHAR2(1),
   INNER_ITEM_CODE      VARCHAR2(10),
   ITEM_NAME            VARCHAR2(100),
   ITEM_DESC            VARCHAR2(280),
   ITEM_TYPE            VARCHAR2(30),
   UNIT_                VARCHAR2(10),
   BOX_UNIT             VARCHAR2(10),
   TRAY_UNIT            VARCHAR2(10),
   TRACK_BARCODE        VARCHAR2(1),
   NET_WEIGHT           NUMBER(11,3)         default NULL,
   GROSS_WEIGHT         NUMBER(11,3)         default NULL,
   PKG_VOLUME           NUMBER(11,3)         default NULL,
   LOT_CONTROL_CODE     INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   series             VARCHAR2(10),
   shelf_life_month   INTEGER              default NULL,
   constraint PK_BASE_PRODUCT_INFO primary key (product_id)
);

comment on column base_product_info.product_id is
'主键Id';

comment on column base_product_info.ORGANIZATION_ID is
'库存组织ID';

comment on column base_product_info.ITEM_ID is
'物料ID';

comment on column base_product_info.ORG_ID is
'OU ID';

comment on column base_product_info.NET_WEIGHT is
'净重（NULL）';

comment on column base_product_info.GROSS_WEIGHT is
'毛重（NULL）';

comment on column base_product_info.PKG_VOLUME is
'体积（NULL）';

comment on column base_product_info.LOT_CONTROL_CODE is
'LOT_CONTROL_CODE';

comment on column base_product_info.creation_date is
'创建日期';

comment on column base_product_info.created_by is
'创建人';

comment on column base_product_info.last_updated_by is
'更新人';

comment on column base_product_info.last_update_date is
'更新日期';

comment on column base_product_info.version_num is
'版本号';

comment on column base_product_info.last_update_login is
'last_update_login';

alter table base_product_info
   add constraint IDX_PRODUCT_ITEM unique (ITEM_CODE, ORGANIZATION_ID);

alter table base_product_info
   add constraint IDX_PRODUCT_ITEM_ID unique (ITEM_ID, ORGANIZATION_ID);

/*==============================================================*/
/* Table: base_product_inv                                    */
/*==============================================================*/
create table base_product_inv 
(
   product_inv_id     INTEGER            not null,
   item_code          VARCHAR2(50),
   warehouse_code     VARCHAR2(50),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_PRODUCT_INV primary key (product_inv_id)
);

comment on column base_product_inv.product_inv_id is
'主键Id';

comment on column base_product_inv.creation_date is
'创建日期';

comment on column base_product_inv.created_by is
'创建人';

comment on column base_product_inv.last_updated_by is
'更新人';

comment on column base_product_inv.last_update_date is
'更新日期';

comment on column base_product_inv.version_num is
'版本号';

comment on column base_product_inv.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_profile                                        */
/*==============================================================*/
create table base_profile 
(
   profile_id         INTEGER            not null,
   profile_code       VARCHAR2(50),
   ds_id              INTEGER              default NULL,
   ds_name            VARCHAR2(100),
   source_sql         VARCHAR2(500),
   profile_name       VARCHAR2(50),
   profile_desc       VARCHAR2(200),
   system_code        VARCHAR2(50),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_PROFILE primary key (profile_id)
);

comment on column base_profile.profile_id is
'主键ID';

comment on column base_profile.ds_id is
'数据源Id';

comment on column base_profile.creation_date is
'创建日期';

comment on column base_profile.created_by is
'创建人';

comment on column base_profile.last_updated_by is
'更新人';

comment on column base_profile.last_update_date is
'更新日期';

comment on column base_profile.version_num is
'版本号';

comment on column base_profile.last_update_login is
'last_update_login';

comment on column base_profile.delete_flag is
'是否删除，0：否，1：删除';

/*==============================================================*/
/* Table: base_profile_lan 备注：关键字，字段language 改成了lan */
/*==============================================================*/
create table base_profile_lan 
(
   profile_lan_id INTEGER            not null,
   profile_id     INTEGER            not null,
   lan            VARCHAR2(20)         default NULL,
   profile_code       VARCHAR2(50)         default NULL,
   ds_id              INTEGER              default NULL,
   ds_name            VARCHAR2(100)        default NULL,
   source_sql         VARCHAR2(500)        default NULL,
   profile_name       VARCHAR2(50)         default NULL,
   profile_desc       VARCHAR2(100)        default NULL,
   system_code        VARCHAR2(50)         default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default NULL,
   constraint PK_BASE_PROFILE_LAN primary key (profile_lan_id)
);

comment on column base_profile_lan.profile_lan_id is
'主键ID';

comment on column base_profile_lan.lan is
'语音CN/EN';

comment on column base_profile_lan.profile_code is
'profile编码';

comment on column base_profile_lan.ds_id is
'数据源Id';

comment on column base_profile_lan.ds_name is
'数据源名称';

comment on column base_profile_lan.source_sql is
'source_sql';

comment on column base_profile_lan.profile_name is
'profile名称';

comment on column base_profile_lan.profile_desc is
'source_sql描述';

comment on column base_profile_lan.system_code is
'为哪个系统定义的profile，默认为All表示所有系统适用';

comment on column base_profile_lan.creation_date is
'创建日期';

comment on column base_profile_lan.created_by is
'创建人';

comment on column base_profile_lan.last_updated_by is
'更新人';

comment on column base_profile_lan.last_update_date is
'更新时间';

comment on column base_profile_lan.version_num is
'版本号';

comment on column base_profile_lan.last_update_login is
'版本号';

comment on column base_profile_lan.delete_flag is
'是否删除，0：否，1：删除';

/*==============================================================*/
/* Table: base_profile_value                                  */
/*==============================================================*/
create table base_profile_value 
(
   profile_value_id   INTEGER            not null,
   profile_id         INTEGER              default NULL,
   business_key       INTEGER              default NULL,
   system_code        VARCHAR2(100),
   key_table_name     VARCHAR2(100),
   profile_value      VARCHAR2(200),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_PROFILE_VALUE primary key (profile_value_id)
);

comment on column base_profile_value.profile_value_id is
'主键ID';

comment on column base_profile_value.profile_id is
'profile表Id';

comment on column base_profile_value.business_key is
'业务表对应的主键';

comment on column base_profile_value.creation_date is
'创建日期';

comment on column base_profile_value.created_by is
'创建人';

comment on column base_profile_value.last_updated_by is
'更新人';

comment on column base_profile_value.last_update_date is
'更新日期';

comment on column base_profile_value.version_num is
'版本号';

comment on column base_profile_value.last_update_login is
'last_update_login';

comment on column base_profile_value.delete_flag is
'是否删除，0：否，1：删除';

/*==============================================================*/
/* Table: base_profile_value_lan                              */
/*==============================================================*/
create table base_profile_value_lan 
(
   profile_value_lan_id INTEGER            not null,
   profile_lan_id     INTEGER              default NULL,
   lan                VARCHAR2(30),
   business_key       INTEGER              default NULL,
   system_code        VARCHAR2(100),
   key_table_name     VARCHAR2(100),
   profile_value      VARCHAR2(200),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_PROFILE_VALUE_LAN primary key (profile_value_lan_id)
);

comment on column base_profile_value_lan.profile_value_lan_id is
'主键ID';

comment on column base_profile_value_lan.profile_lan_id is
'profile_lan表Id';

comment on column base_profile_value_lan.business_key is
'业务表对应的主键';

comment on column base_profile_value_lan.creation_date is
'创建日期';

comment on column base_profile_value_lan.created_by is
'创建人';

comment on column base_profile_value_lan.last_updated_by is
'更新人';

comment on column base_profile_value_lan.last_update_date is
'更新日期';

comment on column base_profile_value_lan.version_num is
'版本号';

comment on column base_profile_value_lan.last_update_login is
'last_update_login';

comment on column base_profile_value_lan.delete_flag is
'是否删除，0：否，1：删除';

/*==============================================================*/
/* Table: base_publish_app_info                               */
/*==============================================================*/
create table base_publish_app_info 
(
   app_wap_id         INTEGER            not null,
   app_wap_name       VARCHAR2(10),
   app_system         VARCHAR2(10),
   app_publish_version VARCHAR2(10),
   app_update_tips    VARCHAR2(2000),
   app_download_url   VARCHAR2(256),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_PUBLISH_APP_INFO primary key (app_wap_id)
);

comment on column base_publish_app_info.app_wap_id is
'app应用主键';

comment on column base_publish_app_info.created_by is
'创建人';

comment on column base_publish_app_info.creation_date is
'创建时间';

comment on column base_publish_app_info.last_update_date is
'最后更新时间';

comment on column base_publish_app_info.last_update_login is
'最后登录id';

comment on column base_publish_app_info.last_updated_by is
'最后更新人';

comment on column base_publish_app_info.version_num is
'版本号';

comment on column base_publish_app_info.delete_flag is
'是否删除0：未删除1：已删除';

/*==============================================================*/
/* Table: base_report_charts_type                             */
/*==============================================================*/
create table base_report_charts_type 
(
   charts_id          INTEGER            not null,
   charts_code        VARCHAR2(30),
   charts_name        VARCHAR2(30),
   charts_script      VARCHAR2(4000),
   dimensions         VARCHAR2(300),
   dimension_length   INTEGER              default NULL,
   data_conversion_script VARCHAR2(4000),
   description        VARCHAR2(250),
   attribute_category VARCHAR2(30),
   charts_type        VARCHAR2(30),
   demo_url           VARCHAR2(250),
   charts_title       VARCHAR2(300),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_REPORT_CHARTS_TYPE primary key (charts_id)
);

comment on column base_report_charts_type.charts_id is
'主键ID';

comment on column base_report_charts_type.dimension_length is
'dimension_length';

comment on column base_report_charts_type.creation_date is
'创建日期';

comment on column base_report_charts_type.created_by is
'创建人';

comment on column base_report_charts_type.last_updated_by is
'更新人';

comment on column base_report_charts_type.last_update_date is
'更新日期';

comment on column base_report_charts_type.version_num is
'版本号';

comment on column base_report_charts_type.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_report_charts_type_lan                      */
/*==============================================================*/
create table base_report_charts_type_lan 
(
   charts_lan_id INTEGER            not null,
   charts_id          INTEGER              not null,
   lan           	  VARCHAR2(30),
   charts_name        VARCHAR2(30),
   description        VARCHAR2(250),
   charts_title       VARCHAR2(300),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_REPORT_CHARTS_TYPE_LAN primary key (charts_lan_id)
);

comment on column base_report_charts_type_lan.charts_lan_id is
'dimension_length';

comment on column base_report_charts_type_lan.creation_date is
'创建日期';

comment on column base_report_charts_type_lan.created_by is
'创建人';

comment on column base_report_charts_type_lan.last_updated_by is
'更新人';

comment on column base_report_charts_type_lan.last_update_date is
'更新日期';

comment on column base_report_charts_type_lan.version_num is
'版本号';

comment on column base_report_charts_type_lan.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_report_datasource                              */
/*==============================================================*/
create table base_report_datasource 
(
   ds_id              INTEGER            not null,
   ds_name            VARCHAR2(100),
   ds_type            VARCHAR2(25),
   ds_access_type     VARCHAR2(10),
   ds_access_protocal VARCHAR2(10),
   ds_ip              VARCHAR2(20),
   ds_port            VARCHAR2(10),
   ds_webServer_address VARCHAR2(500),
   ds_webServer_user  VARCHAR2(45),
   ds_webServer_password VARCHAR2(100),
   ds_webServer_token VARCHAR2(500),
   ds_db_instance_name VARCHAR2(100),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_REPORT_DATASOURCE primary key (ds_id)
);

comment on column base_report_datasource.ds_id is
'主键';

comment on column base_report_datasource.creation_date is
'创建日期';

comment on column base_report_datasource.created_by is
'创建人';

comment on column base_report_datasource.last_updated_by is
'更新人';

comment on column base_report_datasource.last_update_date is
'更新日期';

comment on column base_report_datasource.version_num is
'版本号';

comment on column base_report_datasource.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_report_datasource_lan                      */
/*==============================================================*/
create table base_report_datasource_lan 
(
   en_id              INTEGER            not null,
   ds_id              INTEGER              not null,
   lan           VARCHAR2(50),
   ds_name            VARCHAR2(100),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_REPORT_DATASOURCE_LAN primary key (en_id)
);

comment on column base_report_datasource_lan.en_id is
'主键';

comment on column base_report_datasource_lan.ds_id is
'主表ID';

comment on column base_report_datasource_lan.creation_date is
'创建日期';

comment on column base_report_datasource_lan.created_by is
'创建人';

comment on column base_report_datasource_lan.last_updated_by is
'更新人';

comment on column base_report_datasource_lan.last_update_date is
'更新日期';

comment on column base_report_datasource_lan.version_num is
'版本号';

comment on column base_report_datasource_lan.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_report_group                                   */
/*==============================================================*/
create table base_report_group 
(
   report_group_id    INTEGER            not null,
   menu_tab_name      VARCHAR2(200),
   report_group_desc  VARCHAR2(2000),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_REPORT_GROUP primary key (report_group_id)
);

comment on column base_report_group.report_group_id is
'主键Id';

comment on column base_report_group.creation_date is
'创建日期';

comment on column base_report_group.created_by is
'创建人';

comment on column base_report_group.last_updated_by is
'更新人';

comment on column base_report_group.last_update_date is
'更新日期';

comment on column base_report_group.version_num is
'版本号';

comment on column base_report_group.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_report_group_header                            */
/*==============================================================*/
create table base_report_group_header 
(
   report_group_header_id INTEGER            not null,
   report_header_id   INTEGER              default NULL,
   report_group_id    INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_REPORT_GROUP_HEADER primary key (report_group_header_id)
);

comment on column base_report_group_header.report_header_id is
'报表id';

comment on column base_report_group_header.report_group_id is
'报表组Id';

comment on column base_report_group_header.creation_date is
'创建日期';

comment on column base_report_group_header.created_by is
'创建人';

comment on column base_report_group_header.last_updated_by is
'更新人';

comment on column base_report_group_header.last_update_date is
'更新日期';

comment on column base_report_group_header.version_num is
'版本号';

comment on column base_report_group_header.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_report_header                                  */
/*==============================================================*/
create table base_report_header 
(
   report_header_id   INTEGER            not null,
   report_header_code VARCHAR2(30),
   report_type        VARCHAR2(50),
   page_size          INTEGER              default NULL,
   report_title       VARCHAR2(100),
   web_script         VARCHAR2(3999),
   count_sql          VARCHAR2(2000),
   query_sql          VARCHAR2(3999),
   ds_id              INTEGER              default NULL,
   where_clause       VARCHAR2(1000),
   order_by           VARCHAR2(500),
   query_flag         VARCHAR2(5),
   count_flag         VARCHAR2(30),
   report_desc        VARCHAR2(2000),
   charts_title       VARCHAR2(300),
   charts_type        VARCHAR2(30),
   charts_code        VARCHAR2(30),
   charts_name        VARCHAR2(30),
   data_conversion_script VARCHAR2(4000),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_REPORT_HEADER primary key (report_header_id)
);

comment on column base_report_header.report_header_id is
'主键ID';

comment on column base_report_header.page_size is
'列表行数';

comment on column base_report_header.ds_id is
'对应的数据源Id';

comment on column base_report_header.creation_date is
'创建日期';

comment on column base_report_header.created_by is
'创建人';

comment on column base_report_header.last_updated_by is
'更新人';

comment on column base_report_header.last_update_date is
'更新日期';

comment on column base_report_header.version_num is
'版本号';

comment on column base_report_header.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_report_line                                    */
/*==============================================================*/
create table base_report_line 
(
   report_line_id     INTEGER            not null,
   report_header_id   INTEGER              default NULL,
   report_header_code VARCHAR2(30),
   order_no           INTEGER              default NULL,
   column_code        VARCHAR2(50),
   column_name        VARCHAR2(50),
   column_display_width INTEGER              default NULL,
   column_data_type   VARCHAR2(30),
   column_display_flag VARCHAR2(5),
   param_required_flag VARCHAR2(5),
   param_display_type VARCHAR2(20),
   param_display_name VARCHAR2(200),
   param_use_code     VARCHAR2(200),
   param_data_from_type VARCHAR2(30),
   param_default_value VARCHAR2(200),
   count_flag         VARCHAR2(30),
   sum_flag           VARCHAR2(30),
   avg_flag           VARCHAR2(30),
   report_line_desc   CLOB,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   param_data_from_type_code VARCHAR2(100),
   range_flag         VARCHAR2(10),
   condition_flag     VARCHAR2(10),
   condition_expression VARCHAR2(10),
   constraint PK_BASE_REPORT_LINE primary key (report_line_id)
);

comment on column base_report_line.report_line_id is
'主键ID';

comment on column base_report_line.report_header_id is
'头表Id';

comment on column base_report_line.order_no is
'列排序编号';

comment on column base_report_line.column_display_width is
'列显示宽度';

comment on column base_report_line.creation_date is
'创建日期';

comment on column base_report_line.created_by is
'创建人';

comment on column base_report_line.last_updated_by is
'更新人';

comment on column base_report_line.last_update_date is
'更新日期';

comment on column base_report_line.version_num is
'版本号';

comment on column base_report_line.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_request_log                                    */
/*==============================================================*/
create table base_request_log 
(
   id                 INTEGER            not null,
   request_url        VARCHAR2(255),
   request_method     VARCHAR2(255),
   request_body       CLOB,
   response           CLOB,
   request_header     VARCHAR2(255),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_REQUEST_LOG primary key (id)
);

comment on column base_request_log.creation_date is
'创建日期';

comment on column base_request_log.created_by is
'创建人';

comment on column base_request_log.last_updated_by is
'更新人';

comment on column base_request_log.last_update_date is
'更新日期';

comment on column base_request_log.version_num is
'版本号';

/*==============================================================*/
/* Table: base_resource                                       */
/*==============================================================*/
create table base_resource 
(
   resource_id        INTEGER            not null,
   menu_id            INTEGER              default NULL,
   resource_code      VARCHAR2(200),
   button_url         VARCHAR2(200),
   order_no           INTEGER              default NULL,
   resource_type      VARCHAR2(30),
   resource_name      VARCHAR2(100),
   resource_desc      VARCHAR2(250),
   res_icon           VARCHAR2(50),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_RESOURCE primary key (resource_id)
);

comment on column base_resource.resource_id is
'资源标识';

comment on column base_resource.menu_id is
'菜单Id，节点标识 对应到功能';

comment on column base_resource.order_no is
'排序号';

comment on column base_resource.creation_date is
'创建日期';

comment on column base_resource.created_by is
'创建人';

comment on column base_resource.last_updated_by is
'更新人';

comment on column base_resource.last_update_date is
'更新日期';

comment on column base_resource.version_num is
'版本号';

comment on column base_resource.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_resource_lan                              */
/*==============================================================*/
create table base_resource_lan 
(
   resource_language_id INTEGER            not null,
   resource_name      VARCHAR2(100),
   resource_desc      VARCHAR2(250),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default NULL,
   language           VARCHAR2(30)         default NULL,
   last_update_login  INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   resource_type      VARCHAR2(30),
   resource_id        INTEGER              default NULL,
   menu_id            INTEGER              default NULL,
   constraint PK_BASE_RESOURCE_LAN primary key (resource_language_id)
);

comment on column base_resource_lan.resource_language_id is
'主键ID';

comment on column base_resource_lan.creation_date is
'创建日期';

comment on column base_resource_lan.created_by is
'创建人';

comment on column base_resource_lan.last_updated_by is
'更新人';

comment on column base_resource_lan.last_update_date is
'更新日期';

comment on column base_resource_lan.version_num is
'版本号';

/*==============================================================*/
/* Table: base_responsibility                                 */
/*==============================================================*/
create table base_responsibility 
(
   responsibility_id  INTEGER            not null,
   responsibility_code VARCHAR2(256),
   responsibility_name VARCHAR2(256),
   responsibility_desc VARCHAR2(1000),
   start_date_active  DATE                 default NULL,
   end_date_active    DATE                 default NULL,
   system_code        VARCHAR2(30),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_RESPONSIBILITY primary key (responsibility_id)
);

comment on column base_responsibility.responsibility_id is
'职责标识';

comment on column base_responsibility.start_date_active is
'生效时间';

comment on column base_responsibility.end_date_active is
'失效时间';

comment on column base_responsibility.creation_date is
'创建日期';

comment on column base_responsibility.created_by is
'创建人';

comment on column base_responsibility.last_updated_by is
'更新人';

comment on column base_responsibility.last_update_date is
'更新日期';

comment on column base_responsibility.version_num is
'版本号';

comment on column base_responsibility.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_responsibility_lan                        */
/*==============================================================*/
create table base_responsibility_lan 
(
   lan_id        INTEGER            not null,
   meaning            VARCHAR2(150),
   description        VARCHAR2(240),
   responsibility_id  INTEGER              default NULL,
   lan           VARCHAR2(30),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default 0,
   delete_flag        INTEGER              default 0,
   constraint PK_BASE_RESPONSIBILITY_LAN primary key (lan_id)
);

comment on column base_responsibility_lan.lan_id is
'主键ID';

comment on column base_responsibility_lan.responsibility_id is
'职责id';

comment on column base_responsibility_lan.created_by is
'创建人';

comment on column base_responsibility_lan.last_updated_by is
'更新人';

comment on column base_responsibility_lan.last_update_date is
'更新日期';

comment on column base_responsibility_lan.version_num is
'版本号';

/*==============================================================*/
/* Table: base_responsibility_role                            */
/*==============================================================*/
create table base_responsibility_role 
(
   resp_role_id       INTEGER            not null,
   responsibility_id  INTEGER              default NULL,
   role_id            INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_RESPONSIBILITY_ROLE primary key (resp_role_id)
);

comment on column base_responsibility_role.resp_role_id is
'主键ID';

comment on column base_responsibility_role.responsibility_id is
'职责标识';

comment on column base_responsibility_role.role_id is
'角色标识';

comment on column base_responsibility_role.creation_date is
'创建日期';

comment on column base_responsibility_role.created_by is
'创建人';

comment on column base_responsibility_role.last_updated_by is
'更新人';

comment on column base_responsibility_role.last_update_date is
'更新日期';

comment on column base_responsibility_role.version_num is
'版本号';

comment on column base_responsibility_role.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_responsibility_role_lan                        */
/*==============================================================*/
create table base_responsibility_role_lan 
(
   lan_role_id        INTEGER            not null,
   responsibility_lan_id INTEGER              default NULL,
   role_id            INTEGER              default NULL,
   lan                VARCHAR2(50),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_RESPONSIBILITY_ROLE_LA primary key (lan_role_id)
);

comment on column base_responsibility_role_lan.lan_role_id is
'主键ID';

comment on column base_responsibility_role_lan.responsibility_lan_id is
'职责标识';

comment on column base_responsibility_role_lan.role_id is
'角色标识';

comment on column base_responsibility_role_lan.creation_date is
'创建日期';

comment on column base_responsibility_role_lan.created_by is
'创建人';

comment on column base_responsibility_role_lan.last_updated_by is
'更新人';

comment on column base_responsibility_role_lan.last_update_date is
'更新日期';

comment on column base_responsibility_role_lan.version_num is
'版本号';

comment on column base_responsibility_role_lan.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_role                                           */
/*==============================================================*/
create table base_role 
(
   role_id            INTEGER            not null,
   role_name          VARCHAR2(100),
   role_code          VARCHAR2(100),
   role_desc          VARCHAR2(250),
   system_code        VARCHAR2(30),
   start_date_active  DATE                 default NULL,
   end_date_active    DATE                 default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_ROLE primary key (role_id)
);

comment on column base_role.role_id is
'角色Id';

comment on column base_role.start_date_active is
'生效时间';

comment on column base_role.end_date_active is
'失效时间';

comment on column base_role.creation_date is
'创建日期';

comment on column base_role.created_by is
'创建人';

comment on column base_role.last_updated_by is
'更新人';

comment on column base_role.last_update_date is
'更新日期';

comment on column base_role.version_num is
'版本号';

comment on column base_role.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_role_language                                  */
/*==============================================================*/
create table base_role_language 
(
   role_language_id   INTEGER            not null,
   role_name          VARCHAR2(100),
   role_desc          VARCHAR2(250),
   language           VARCHAR2(30),
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   delete_flag        INTEGER              default 0,
   role_id            INTEGER              default NULL,
   constraint PK_BASE_ROLE_LANGUAGE primary key (role_language_id)
);

comment on column base_role_language.role_language_id is
'主键ID';

comment on column base_role_language.created_by is
'创建人';

comment on column base_role_language.creation_date is
'创建日期';

comment on column base_role_language.last_updated_by is
'更新人';

comment on column base_role_language.last_update_date is
'更新日期';

comment on column base_role_language.version_num is
'版本号';

/*==============================================================*/
/* Table: base_role_menu                                      */
/*==============================================================*/
create table base_role_menu 
(
   role_menu_id       INTEGER            not null,
   menu_id            INTEGER              default NULL,
   role_id            INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_ROLE_MENU primary key (role_menu_id)
);

comment on column base_role_menu.role_menu_id is
'主键ID';

comment on column base_role_menu.menu_id is
'节点标识,引用功能，只能引用功能，不能引用菜单';

comment on column base_role_menu.role_id is
'角色Id';

comment on column base_role_menu.creation_date is
'创建日期';

comment on column base_role_menu.created_by is
'创建人';

comment on column base_role_menu.last_updated_by is
'更新人';

comment on column base_role_menu.last_update_date is
'更新日期';

comment on column base_role_menu.version_num is
'版本号';

comment on column base_role_menu.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_role_resource                                  */
/*==============================================================*/
create table base_role_resource 
(
   role_resource_id   INTEGER            not null,
   role_id            INTEGER              default NULL,
   resource_id        INTEGER              default NULL,
   resource_value     VARCHAR2(100),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_ROLE_RESOURCE primary key (role_resource_id)
);

comment on column base_role_resource.role_resource_id is
'主键ID';

comment on column base_role_resource.role_id is
'角色Id';

comment on column base_role_resource.resource_id is
'资源标识';

comment on column base_role_resource.creation_date is
'创建日期';

comment on column base_role_resource.created_by is
'创建人';

comment on column base_role_resource.last_updated_by is
'更新人';

comment on column base_role_resource.last_update_date is
'更新日期';

comment on column base_role_resource.version_num is
'版本号';

comment on column base_role_resource.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_system_sso                                     */
/*==============================================================*/
create table base_system_sso 
(
   sys_sso_id         INTEGER            not null,
   system_name        VARCHAR2(100),
   system_code        VARCHAR2(100),
   description        VARCHAR2(255),
   client_id          VARCHAR2(100),
   enable_flag        VARCHAR2(10),
   image_url          VARCHAR2(500),
   order_no           INTEGER              default NULL,
   params             VARCHAR2(500),
   request_method     VARCHAR2(45),
   request_url        VARCHAR2(255),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_SYSTEM_SSO primary key (sys_sso_id)
);

comment on column base_system_sso.sys_sso_id is
'主键Id';

comment on column base_system_sso.order_no is
'排序';

comment on column base_system_sso.creation_date is
'创建日期';

comment on column base_system_sso.created_by is
'创建人';

comment on column base_system_sso.last_updated_by is
'更新人';

comment on column base_system_sso.last_update_date is
'更新日期';

comment on column base_system_sso.version_num is
'版本号';

/*==============================================================*/
/* Table: base_task_collection                                */
/*==============================================================*/
create table base_task_collection 
(
   task_collection_id INTEGER            not null,
   business_key_id    VARCHAR2(64)         default NULL,
   create_date        DATE                 default NULL,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   function_url       VARCHAR2(200)        default NULL,
   handler            VARCHAR2(64)         default NULL,
   instance_id        VARCHAR2(64)         default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   status             VARCHAR2(10)         default NULL,
   system_code        VARCHAR2(64)         default NULL,
   system_name        VARCHAR2(200)        default NULL,
   task_id            VARCHAR2(64)         default NULL,
   task_title         VARCHAR2(200)        default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_BASE_TASK_COLLECTION primary key (task_collection_id)
);

/*==============================================================*/
/* Table: base_user_responsibility                            */
/*==============================================================*/
create table base_user_responsibility 
(
   resp_user_id       INTEGER            not null,
   responsibility_id  INTEGER              default NULL,
   user_id            INTEGER              not null,
   start_date_active  DATE                 default NULL,
   end_date_active    DATE                 default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_USER_RESPONSIBILITY primary key (resp_user_id)
);

comment on column base_user_responsibility.resp_user_id is
'主键';

comment on column base_user_responsibility.responsibility_id is
'职责标识';

comment on column base_user_responsibility.user_id is
'用户Id';

comment on column base_user_responsibility.start_date_active is
'生效时间';

comment on column base_user_responsibility.end_date_active is
'失效时间';

comment on column base_user_responsibility.creation_date is
'创建日期';

comment on column base_user_responsibility.created_by is
'创建人';

comment on column base_user_responsibility.last_updated_by is
'更新人';

comment on column base_user_responsibility.last_update_date is
'更新日期';

comment on column base_user_responsibility.version_num is
'版本号';

comment on column base_user_responsibility.last_update_login is
'last_update_login';

/*==============================================================*/
/* Table: base_users                                          */
/*==============================================================*/
create table base_users 
(
   user_id            INTEGER            not null,
   phone_number       VARCHAR2(30),
   name_pingyin       VARCHAR2(100),
   name_simple_pinyin VARCHAR2(50),
   person_id          VARCHAR2(50),
   source_id          VARCHAR2(36),
   isadmin            VARCHAR2(20),
   user_name          VARCHAR2(100),
   user_desc          VARCHAR2(256),
   user_type          VARCHAR2(10),
   user_full_name     VARCHAR2(100),
   user_head_img_url  VARCHAR2(255),
   encrypted_password VARCHAR2(100),
   internal_user      VARCHAR2(11),
   delete_flag        INTEGER              default 0,
   start_date         DATE                 default NULL,
   end_date           DATE                 default NULL,
   order_no           INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   cust_account_id    VARCHAR2(11),
   pwd_update_date    DATE                 default NULL,
   service_store_id   INTEGER              default NULL,
   source_code        VARCHAR2(50),
   store_code         VARCHAR2(50),
   constraint PK_BASE_USERS primary key (user_id)
);

comment on column base_users.user_id is
'用户Id';

comment on column base_users.delete_flag is
'是否删除（0：未删除；1：已删除）';

comment on column base_users.start_date is
'生效日期';

comment on column base_users.end_date is
'失效日期';

comment on column base_users.order_no is
'排序号';

comment on column base_users.creation_date is
'创建日期';

comment on column base_users.created_by is
'创建人';

comment on column base_users.last_updated_by is
'更新人';

comment on column base_users.last_update_date is
'更新日期';

comment on column base_users.version_num is
'版本号';

comment on column base_users.service_store_id is
'所属服务店id';

create index  normal_index_person_id on base_users (person_id);

/*==============================================================*/
/* Table: base_users_language                                 */
/*==============================================================*/
create table base_users_language 
(
   user_language_id   INTEGER            not null,
   user_id            INTEGER              not null,
   user_desc          VARCHAR2(256),
   user_full_name     VARCHAR2(100),
   delete_flag        INTEGER              default 0,
   start_date         DATE                 default NULL,
   end_date           DATE                 default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   cust_account_id    VARCHAR2(11),
   language           VARCHAR2(5),
   constraint PK_BASE_USERS_LANGUAGE primary key (user_language_id)
);

comment on column base_users_language.user_language_id is
'用户语言id';

comment on column base_users_language.user_id is
'用户Id';

comment on column base_users_language.delete_flag is
'是否删除（0：未删除；1：已删除）';

comment on column base_users_language.start_date is
'生效日期';

comment on column base_users_language.end_date is
'失效日期';

comment on column base_users_language.creation_date is
'创建日期';

comment on column base_users_language.created_by is
'创建人';

comment on column base_users_language.last_updated_by is
'更新人';

comment on column base_users_language.last_update_date is
'更新日期';

comment on column base_users_language.version_num is
'版本号';

/*==============================================================*/
/* Table: base_warehouse_mapping                              */
/*==============================================================*/
create table base_warehouse_mapping 
(
   WAREHOUSE_ID         INTEGER            not null,
   WAREHOUSE_CODE       VARCHAR2(50),
   WAREHOUSE_NAME       VARCHAR2(100),
   org_id             INTEGER              default NULL,
   ORGANIZATION_ID      INTEGER              default NULL,
   ORGANIZATION_Name  VARCHAR2(100),
   ADDR                 VARCHAR2(400),
   WAREHOUSE_TYPE       INTEGER              default NULL,
   DEFAULT_FLAG         VARCHAR2(500),
   DESCRIPTION          VARCHAR2(400),
   account_code       VARCHAR2(30),
   account_name       VARCHAR2(500),
   account_id         INTEGER              default NULL,
   parent_WAREHOUSE_CODE VARCHAR2(50),
   CHANNEL_CODE         VARCHAR2(100),
   Provincial         VARCHAR2(50),
   Municipal          VARCHAR2(50),
   County             VARCHAR2(50),
   address_detail     VARCHAR2(100),
   Longitude          NUMBER(10,6)         default NULL,
   Latitude           NUMBER(10,6)         default NULL,
   main_flag          VARCHAR2(5),
   delete_flag        VARCHAR2(1),
   START_DATE_ACTIVE    DATE                 default NULL,
   END_DATE_ACTIVE      DATE                 default NULL,
   allow_negative_onhand VARCHAR2(5),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_BASE_WAREHOUSE_MAPPING primary key (WAREHOUSE_ID)
);

comment on column base_warehouse_mapping.WAREHOUSE_ID is
'表ID，主键，供其他表做外键';

comment on column base_warehouse_mapping.org_id is
'组织ID（渠道库存组织必须关联OU）';

comment on column base_warehouse_mapping.ORGANIZATION_ID is
'库存组织ID';

comment on column base_warehouse_mapping.WAREHOUSE_TYPE is
'仓库类型（<20 内部子库、>=20 <30经销商子库、30>=门店子库）';

comment on column base_warehouse_mapping.account_id is
'所属经销商Id，财务编码';

comment on column base_warehouse_mapping.Longitude is
'地址经度';

comment on column base_warehouse_mapping.Latitude is
'地址纬度';

comment on column base_warehouse_mapping.START_DATE_ACTIVE is
'起始日期';

comment on column base_warehouse_mapping.END_DATE_ACTIVE is
'终止日期';

comment on column base_warehouse_mapping.creation_date is
'创建日期';

comment on column base_warehouse_mapping.created_by is
'创建人';

comment on column base_warehouse_mapping.last_updated_by is
'更新人';

comment on column base_warehouse_mapping.last_update_date is
'更新日期';

comment on column base_warehouse_mapping.version_num is
'版本号';

comment on column base_warehouse_mapping.last_update_login is
'last_update_login';

create index  index_warehouse_code on base_warehouse_mapping (WAREHOUSE_CODE);
create index  index_account_code on base_warehouse_mapping (account_code);
create index  index_org_id on base_warehouse_mapping (org_id);

/*==============================================================*/
/* Table: base_wechat_mng                                     */
/*==============================================================*/
create table base_wechat_mng 
(
   wx_id              INTEGER            not null,
   created_by         INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   union_id           VARCHAR2(200)        default NULL,
   user_id            INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   wx_open_id         VARCHAR2(200)        default NULL,
   constraint PK_BASE_WECHAT_MNG primary key (wx_id)
);

/*==============================================================*/
/* Table: cux_cdm_access_basedata                             */
/*==============================================================*/
create table cux_cdm_access_basedata 
(
   org_id             INTEGER              not null,
   access_type        VARCHAR2(10)         default NULL,
   account_int        VARCHAR2(30)         default NULL,
   attribute1         VARCHAR2(240)        default NULL,
   attribute10        VARCHAR2(240)        default NULL,
   attribute11        VARCHAR2(240)        default NULL,
   attribute12        VARCHAR2(240)        default NULL,
   attribute13        VARCHAR2(240)        default NULL,
   attribute14        VARCHAR2(240)        default NULL,
   attribute15        VARCHAR2(240)        default NULL,
   attribute2         VARCHAR2(240)        default NULL,
   attribute3         VARCHAR2(240)        default NULL,
   attribute4         VARCHAR2(240)        default NULL,
   attribute5         VARCHAR2(240)        default NULL,
   attribute6         VARCHAR2(240)        default NULL,
   attribute7         VARCHAR2(240)        default NULL,
   attribute8         VARCHAR2(240)        default NULL,
   attribute9         VARCHAR2(240)        default NULL,
   attribute_category VARCHAR2(30)         default NULL,
   channel_type       VARCHAR2(30)         default NULL,
   created_by         INTEGER              default NULL,
   creation_datetime  DATE                 default NULL,
   cust_account_id    INTEGER              default NULL,
   last_updatetime_datetime DATE                 default NULL,
   last_updatetime_login INTEGER              default NULL,
   last_updatetimed_by INTEGER              default NULL,
   oa_user_id         INTEGER              default NULL,
   person_id          INTEGER              default NULL,
   position_id        INTEGER              default NULL,
   secondary_inventory_name VARCHAR2(100)        default NULL,
   subordinate_person_id INTEGER              default NULL,
   subordinate_position_id INTEGER              default NULL,
   territory2         VARCHAR2(30)         default NULL,
   territory3         VARCHAR2(30)         default NULL,
   user_id            INTEGER              default NULL,
   constraint PK_CUX_CDM_ACCESS_BASEDATA primary key (org_id)
);

/*==============================================================*/
/* Table: demo                                                */
/*==============================================================*/
create table demo 
(
   demo_id            INTEGER            not null,
   demo_name          VARCHAR2(100)        default NULL,
   demo_desc          VARCHAR2(200)        default NULL,
   created_by         INTEGER              default 0,
   creation_date      DATE                 default NULL,
   last_updated_by    INTEGER              default 0,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default 0,
   last_update_login  INTEGER              default NULL,
   constraint PK_DEMO primary key (demo_id)
);

/*==============================================================*/
/* Table: domain_wx                                           */
/*==============================================================*/
create table domain_wx 
(
   configure_id       INTEGER            not null,
   domain             VARCHAR2(150),
   corp_id            VARCHAR2(150),
   corp_secret        VARCHAR2(150),
   agent_id           INTEGER              default NULL,
   secreth            VARCHAR2(150),
   department         VARCHAR2(150),
   callback_scope     VARCHAR2(150),
   creation_date      TIMESTAMP            default sysdate not null,
   created_by         INTEGER              default 0 not null,
   last_updated_by    INTEGER              default 0 not null,
   last_update_date   TIMESTAMP            default sysdate not null,
   last_update_login  INTEGER              default 0 not null,
   version_num        INTEGER              default 0,
   constraint PK_DOMAIN_WX primary key (configure_id)
);

comment on column domain_wx.configure_id is
'域名和微信配置主键ID';

comment on column domain_wx.agent_id is
'授权方的网页应用ID';

comment on column domain_wx.version_num is
'版本号';

/*==============================================================*/
/* Table: ew_configuration_head                               */
/*==============================================================*/
create table ew_configuration_head 
(
   EWC_HEAD_ID          INTEGER            not null,
   EWC_HEAD_NAME        VARCHAR2(30)         default NULL,
   EWC_HEAD_TYPE        VARCHAR2(30)         default NULL,
   RULE_BUSINESS_LINE_CODE VARCHAR2(100)        default NULL,
   RULE_EXP_CODE        VARCHAR2(100)        default NULL,
   START_DATE_ACTIVE    DATE                 not null,
   END_DATE_ACTIVE      DATE                 default NULL,
   DESCRIPTION          VARCHAR2(500)        default NULL,
   VERSION_NUM          INTEGER              default 0,
   CREATION_DATE        DATE                 default sysdate not null,
   CREATED_BY           INTEGER              default -1 not null,
   LAST_UPDATED_BY      INTEGER              default -1 not null,
   LAST_UPDATE_DATE     DATE                 default sysdate not null,
   LAST_UPDATE_LOGIN    INTEGER              default NULL,
   ATTRIBUTE_CATEGORY   VARCHAR2(30)         default NULL,
   ATTRIBUTE1           VARCHAR2(240)        default NULL,
   ATTRIBUTE2           VARCHAR2(240)        default NULL,
   ATTRIBUTE3           VARCHAR2(240)        default NULL,
   ATTRIBUTE4           VARCHAR2(240)        default NULL,
   ATTRIBUTE5           VARCHAR2(240)        default NULL,
   ATTRIBUTE6           VARCHAR2(240)        default NULL,
   ATTRIBUTE7           VARCHAR2(240)        default NULL,
   ATTRIBUTE8           VARCHAR2(240)        default NULL,
   ATTRIBUTE9           VARCHAR2(240)        default NULL,
   ATTRIBUTE10          VARCHAR2(240)        default NULL,
   constraint PK_EW_CONFIGURATION_HEAD primary key (EWC_HEAD_ID)
);

comment on column ew_configuration_head.EWC_HEAD_ID is
'表ID，主键，供其他表做外键';

comment on column ew_configuration_head.EWC_HEAD_NAME is
'预警名称';

comment on column ew_configuration_head.EWC_HEAD_TYPE is
'预警类型';

comment on column ew_configuration_head.RULE_BUSINESS_LINE_CODE is
'规则引擎业务编码';

comment on column ew_configuration_head.RULE_EXP_CODE is
'规则引擎表达式编码';

comment on column ew_configuration_head.START_DATE_ACTIVE is
'起始日期';

comment on column ew_configuration_head.END_DATE_ACTIVE is
'终止日期';

comment on column ew_configuration_head.DESCRIPTION is
'说明、备注';

create index VR_EW_CONFIGURATION_HEAD_n1 on ew_configuration_head (RULE_BUSINESS_LINE_CODE);
create index VR_EW_CONFIGURATION_HEAD_n2 on ew_configuration_head (RULE_EXP_CODE);

/*==============================================================*/
/* Table: ew_configuration_line                               */
/*==============================================================*/
create table ew_configuration_line 
(
   EWC_LINE_ID          INTEGER            not null,
   EWC_HEAD_ID          INTEGER              default NULL,
   EWC_LINE_MODE        VARCHAR2(30)         default NULL,
   EWC_LINE_NEED        VARCHAR2(30)         default NULL,
   EWC_LINE_TEMPLATE    INTEGER              default NULL,
   EWC_LINE_TYPE        VARCHAR2(30)         default NULL,
   START_DATE_ACTIVE    DATE                 not null,
   END_DATE_ACTIVE      DATE                 default NULL,
   DESCRIPTION          VARCHAR2(500)        default NULL,
   VERSION_NUM          INTEGER              default 0,
   CREATION_DATE        DATE                 default sysdate not null,
   CREATED_BY           INTEGER              default -1 not null,
   LAST_UPDATED_BY      INTEGER              default -1 not null,
   LAST_UPDATE_DATE     DATE                 default sysdate not null,
   LAST_UPDATE_LOGIN    INTEGER              default NULL,
   ATTRIBUTE_CATEGORY   VARCHAR2(30)         default NULL,
   ATTRIBUTE1           VARCHAR2(240)        default NULL,
   ATTRIBUTE2           VARCHAR2(240)        default NULL,
   ATTRIBUTE3           VARCHAR2(240)        default NULL,
   ATTRIBUTE4           VARCHAR2(240)        default NULL,
   ATTRIBUTE5           VARCHAR2(240)        default NULL,
   ATTRIBUTE6           VARCHAR2(240)        default NULL,
   ATTRIBUTE7           VARCHAR2(240)        default NULL,
   ATTRIBUTE8           VARCHAR2(240)        default NULL,
   ATTRIBUTE9           VARCHAR2(240)        default NULL,
   ATTRIBUTE10          VARCHAR2(240)        default NULL,
   constraint PK_EW_CONFIGURATION_LINE primary key (EWC_LINE_ID)
);

comment on column ew_configuration_line.EWC_LINE_ID is
'表ID，主键，供其他表做外键';

comment on column ew_configuration_line.EWC_HEAD_ID is
'预警配置头表ID';

comment on column ew_configuration_line.EWC_LINE_MODE is
'推送方式';

comment on column ew_configuration_line.EWC_LINE_NEED is
'待办页面';

comment on column ew_configuration_line.EWC_LINE_TEMPLATE is
'推送模板';

comment on column ew_configuration_line.EWC_LINE_TYPE is
'推送类型(个推)';

comment on column ew_configuration_line.START_DATE_ACTIVE is
'起始日期';

comment on column ew_configuration_line.END_DATE_ACTIVE is
'终止日期';

comment on column ew_configuration_line.DESCRIPTION is
'说明、备注';

create index VR_EW_CONFIGURATION_LINE_n1 on ew_configuration_line (EWC_HEAD_ID);

/*==============================================================*/
/* Table: ew_configuration_man                                */
/*==============================================================*/
create table ew_configuration_man 
(
   EWC_MAN_ID           INTEGER            not null,
   EWC_LINE_ID          INTEGER              default NULL,
   PUSH_OBJECT          INTEGER              default NULL,
   PUSH_TYPE            VARCHAR2(10)         default NULL,
   POSITION             VARCHAR2(10)         default NULL,
   START_DATE_ACTIVE    DATE                 not null,
   END_DATE_ACTIVE      DATE                 default NULL,
   DESCRIPTION          VARCHAR2(500)        default NULL,
   VERSION_NUM          INTEGER              default 0,
   CREATION_DATE        DATE                 default sysdate not null,
   CREATED_BY           INTEGER              default -1 not null,
   LAST_UPDATED_BY      INTEGER              default -1 not null,
   LAST_UPDATE_DATE     DATE                 default sysdate not null,
   LAST_UPDATE_LOGIN    INTEGER              default NULL,
   constraint PK_EW_CONFIGURATION_MAN primary key (EWC_MAN_ID)
);

comment on column ew_configuration_man.EWC_MAN_ID is
'表ID，主键，供其他表做外键';

comment on column ew_configuration_man.EWC_LINE_ID is
'行表ID';

comment on column ew_configuration_man.PUSH_OBJECT is
'推送对象';

comment on column ew_configuration_man.PUSH_TYPE is
'推送类型(岗位,个人)';

comment on column ew_configuration_man.POSITION is
'所在岗位';

comment on column ew_configuration_man.START_DATE_ACTIVE is
'起始日期';

comment on column ew_configuration_man.END_DATE_ACTIVE is
'终止日期';

comment on column ew_configuration_man.DESCRIPTION is
'说明、备注';

create index VR_EW_CONFIGURATION_MAN_n1 on ew_configuration_man (EWC_LINE_ID);

/*==============================================================*/
/* Table: ew_handle                                           */
/*==============================================================*/
create table ew_handle 
(
   handle_id          INTEGER            not null,
   batch_id           INTEGER              default NULL,
   city_id            INTEGER              default NULL,
   created_by         INTEGER              not null,
   creation_date      DATE                 not null,
   description        CLOB,
   end_date_active    DATE                 default NULL,
   ew_level           VARCHAR2(20)         default NULL,
   ew_valve           NUMBER(20,2)         default NULL,
   households         INTEGER              default NULL,
   involvement_date   DATE                 default NULL,
   involvement_households INTEGER              default NULL,
   last_update_date   DATE                 not null,
   last_update_login  INTEGER              default NULL,
   last_updated_by    INTEGER              not null,
   product_type       VARCHAR2(100)        default NULL,
   progress_deviation NUMBER(20,2)         default NULL,
   progress_ratio     NUMBER(20,2)         default NULL,
   project_id         INTEGER              default NULL,
   security_day       INTEGER              default NULL,
   start_date_active  DATE                 default NULL,
   version_num        INTEGER              default NULL,
   approve_status     VARCHAR2(20)         default NULL,
   project_status     VARCHAR2(20)         default NULL,
   remark             VARCHAR2(500)        default NULL,
   process_instance_id VARCHAR2(50)         default NULL,
   constraint PK_EW_HANDLE primary key (handle_id)
);

comment on column ew_handle.approve_status is
'审批状态';

comment on column ew_handle.project_status is
'项目批次完工状态';

comment on column ew_handle.remark is
'备注';

comment on column ew_handle.process_instance_id is
'流程ID';

/*==============================================================*/
/* Table: msg_cfg                                             */
/*==============================================================*/
create table msg_cfg 
(
   msg_cfg_id         INTEGER            not null,
   org_id             INTEGER              not null,
   channel_type       VARCHAR2(30)         not null,
   msg_type_code      VARCHAR2(20)         not null,
   msg_source_id      INTEGER              not null,
   temple_id          INTEGER              default NULL,
   function_id        INTEGER              default NULL,
   is_timing_delivery INTEGER              default NULL,
   need_black_validate VARCHAR2(2)          default '0',
   delivery_time      VARCHAR2(5)          default NULL,
   enabled_flag       VARCHAR2(2)          not null,
   version_num        INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   last_update_date   DATE                 not null,
   last_updated_by    INTEGER              not null,
   creation_date      DATE                 not null,
   created_by         INTEGER              not null,
   is_delete          INTEGER              not null,
   blacklist_flag     VARCHAR2(1)          default NULL,
   agent_id           VARCHAR2(10)         default NULL,
   compensate_flag    VARCHAR2(1)          default NULL,
   msg_cfg_name       VARCHAR2(255)        default NULL,
   constraint PK_MSG_CFG primary key (msg_cfg_id)
);

comment on column msg_cfg.msg_cfg_id is
'消息配置id';

comment on column msg_cfg.org_id is
'ou';

comment on column msg_cfg.channel_type is
'渠道类型';

comment on column msg_cfg.msg_type_code is
'消息类型code';

comment on column msg_cfg.is_timing_delivery is
'定时发送频率,间隔多少分钟';

comment on column msg_cfg.need_black_validate is
'0,否 1，是';

comment on column msg_cfg.delivery_time is
'定时发送开始时间';

comment on column msg_cfg.enabled_flag is
'启用状态:0.已停用 1.启用';

comment on column msg_cfg.last_update_date is
'最后更新时间';

comment on column msg_cfg.last_updated_by is
'最后更新用户id';

comment on column msg_cfg.creation_date is
'创建时间';

comment on column msg_cfg.created_by is
'创建用户id';

comment on column msg_cfg.is_delete is
'是否删除';

comment on column msg_cfg.blacklist_flag is
'黑名单';

comment on column msg_cfg.compensate_flag is
'是否补偿';

comment on column msg_cfg.msg_cfg_name is
'消息配置名称';

/*==============================================================*/
/* Table: msg_history                                         */
/*==============================================================*/
create table msg_history 
(
   msg_history_id     INTEGER            not null,
   org_id             INTEGER              not null,
   job_id             VARCHAR2(96)         default NULL,
   channel_type       VARCHAR2(30)         not null,
   msg_type_code      VARCHAR2(20)         not null,
   msg_cfg_id         INTEGER              default NULL,
   receive_id         VARCHAR2(64)         default NULL,
   receive_code       VARCHAR2(1024)       default NULL,
   receive_name       VARCHAR2(255)        default NULL,
   source_type        VARCHAR2(50)         default NULL,
   biz_type           VARCHAR2(64)         not null,
   biz_id             VARCHAR2(96)         not null,
   msg_transaction_date DATE                 default NULL,
   msg_template_id    VARCHAR2(64)         default NULL,
   value_list         VARCHAR2(1024)       default NULL,
   msg_subject        VARCHAR2(200)        default NULL,
   msg_content        CLOB,
   msg_priority       INTEGER              default NULL,
   send_date          DATE                 default NULL,
   send_status        VARCHAR2(30)         default NULL,
   failure_times      INTEGER              default NULL,
   return_msg         VARCHAR2(1024)       default NULL,
   synchro            CHAR(1)              default NULL,
   version_num        INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   request_id         VARCHAR2(96)         default NULL,
   is_delete          INTEGER              default NULL,
   msg_url            VARCHAR2(255)        default NULL,
   constraint PK_MSG_HISTORY primary key (msg_history_id)
);

comment on column msg_history.msg_history_id is
'消息记录id';

comment on column msg_history.org_id is
'ou';

comment on column msg_history.channel_type is
'渠道类型';

comment on column msg_history.msg_type_code is
'1.邮件 2.短信 3.微信 4.站内信';

comment on column msg_history.receive_code is
'接收对象';

comment on column msg_history.source_type is
'记录来源于哪个系统';

comment on column msg_history.biz_type is
'请求源功能id';

comment on column msg_history.biz_id is
'请求源记录的主键id';

comment on column msg_history.msg_transaction_date is
'消息请求时间';

comment on column msg_history.msg_subject is
'消息标题';

comment on column msg_history.msg_content is
'消息内容';

comment on column msg_history.send_date is
'发送时间';

comment on column msg_history.send_status is
'发送状态：1.待发送，2.已发送成功   3.发送异常';

comment on column msg_history.return_msg is
'返回消息';

comment on column msg_history.synchro is
'y  同步 n  异步';

/*==============================================================*/
/* Table: msg_instance                                        */
/*==============================================================*/
create table msg_instance 
(
   msg_instance_id    INTEGER            not null,
   org_id             INTEGER              not null,
   job_id             VARCHAR2(96)         default NULL,
   channel_type       VARCHAR2(30)         not null,
   msg_type_code      VARCHAR2(20)         not null,
   msg_cfg_id         INTEGER              not null,
   receive_id         VARCHAR2(64)         default NULL,
   receive_code       VARCHAR2(1024)       default NULL,
   receive_name       VARCHAR2(255)        default NULL,
   source_type        VARCHAR2(50)         default NULL,
   biz_type           VARCHAR2(64)         not null,
   biz_id             VARCHAR2(96)         not null,
   msg_transaction_date DATE                 default NULL,
   msg_template_id    VARCHAR2(64)         default NULL,
   template_value     VARCHAR2(1000)       default NULL,
   value_list         VARCHAR2(1024)       default NULL,
   msg_subject        VARCHAR2(200)        default NULL,
   msg_content        CLOB,
   msg_priority       INTEGER              default NULL,
   send_date          DATE                 default NULL,
   send_status        VARCHAR2(30)         default NULL,
   failure_times      INTEGER              default NULL,
   return_msg         VARCHAR2(1024)       default NULL,
   synchro            CHAR(1)              default NULL,
   version_num        INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   last_updated_by    INTEGER              default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   request_id         VARCHAR2(96)         default NULL,
   is_delete          INTEGER              default NULL,
   msg_url            VARCHAR2(255)        default NULL,
   constraint PK_MSG_INSTANCE primary key (msg_instance_id)
);

comment on column msg_instance.msg_instance_id is
'消息记录id';

comment on column msg_instance.org_id is
'ou';

comment on column msg_instance.channel_type is
'渠道类型';

comment on column msg_instance.msg_type_code is
'1.邮件 2.短信 3.微信 4.站内信';

comment on column msg_instance.receive_code is
'接收对象';

comment on column msg_instance.source_type is
'记录来源于哪个系统';

comment on column msg_instance.biz_type is
'请求源功能id';

comment on column msg_instance.biz_id is
'请求源记录的主键id';

comment on column msg_instance.msg_transaction_date is
'消息请求时间';

comment on column msg_instance.template_value is
'模板参数值';

comment on column msg_instance.msg_subject is
'消息标题';

comment on column msg_instance.msg_content is
'消息内容';

comment on column msg_instance.send_date is
'发送时间';

comment on column msg_instance.send_status is
'发送状态：1.待发送，2.已发送成功   3.发送异常';

comment on column msg_instance.return_msg is
'返回消息';

comment on column msg_instance.synchro is
'y  同步 n  异步';

create index msg_instance_index1 on msg_instance (channel_type);
create index msg_instance_index2 on msg_instance (org_id);
create index msg_instance_index3 on msg_instance (msg_type_code);
create index msg_instance_index4 on msg_instance (send_status);
create index msg_instance_index5 on msg_instance (msg_transaction_date);
   
   

/*==============================================================*/
/* Table: msg_log                                             */
/*==============================================================*/
create table msg_log 
(
   log_id             INTEGER            not null,
   request_param      VARCHAR2(2048)       default NULL,
   return_data        VARCHAR2(1024)       default NULL,
   job_id             VARCHAR2(96)         default NULL,
   org_id             INTEGER              default NULL,
   user_name          VARCHAR2(96)         default NULL,
   request_id         VARCHAR2(96)         default NULL,
   last_update_date   DATE                 not null,
   last_updated_by    INTEGER              not null,
   creation_date      DATE                 not null,
   created_by         INTEGER              not null,
   user_id            INTEGER              default NULL,
   is_delete          INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   constraint PK_MSG_LOG primary key (log_id)
);

comment on column msg_log.log_id is
'消息配置id';

comment on column msg_log.last_update_date is
'最后更新时间';

comment on column msg_log.last_updated_by is
'最后更新用户id';

comment on column msg_log.creation_date is
'创建时间';

comment on column msg_log.created_by is
'创建用户id';

/*==============================================================*/
/* Table: msg_receive_sql                                     */
/*==============================================================*/
create table msg_receive_sql 
(
   sql_id             INTEGER            not null,
   sql_sentence       VARCHAR2(2048)       default NULL,
   param              VARCHAR2(512)        default NULL,
   remark             VARCHAR2(256)        default NULL,
   last_update_date   DATE                 not null,
   last_updated_by    INTEGER              not null,
   creation_date      DATE                 not null,
   created_by         INTEGER              not null,
   version_num        INTEGER              not null,
   is_delete          INTEGER              default NULL,
   sql_name           VARCHAR2(100)        default NULL,
   constraint PK_MSG_RECEIVE_SQL primary key (sql_id)
);

comment on column msg_receive_sql.sql_id is
'消息配置id';

comment on column msg_receive_sql.last_update_date is
'最后更新时间';

comment on column msg_receive_sql.last_updated_by is
'最后更新用户id';

comment on column msg_receive_sql.creation_date is
'创建时间';

comment on column msg_receive_sql.created_by is
'创建用户id';

comment on column msg_receive_sql.sql_name is
'SQL名称';

/*==============================================================*/
/* Table: msg_source_cfg                                      */
/*==============================================================*/
create table msg_source_cfg 
(
   msg_source_id      INTEGER            not null,
   org_id             INTEGER              not null,
   msg_type_code      VARCHAR2(20)         not null,
   param_cfg          VARCHAR2(4000)       default NULL,
   source_user        VARCHAR2(255)        default NULL,
   source_pwd         VARCHAR2(255)        default NULL,
   source_name        VARCHAR2(40)         not null,
   source_desc        VARCHAR2(100)        default NULL,
   enabled_flag       VARCHAR2(2)          not null,
   version_num        INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   last_update_date   DATE                 not null,
   last_updated_by    INTEGER              not null,
   creation_date      DATE                 not null,
   created_by         INTEGER              not null,
   is_delete          INTEGER              not null,
   constraint PK_MSG_SOURCE_CFG primary key (msg_source_id)
);

comment on column msg_source_cfg.msg_source_id is
'消息配置id';

comment on column msg_source_cfg.org_id is
'ou';

comment on column msg_source_cfg.msg_type_code is
'消息类型code';

comment on column msg_source_cfg.param_cfg is
'接口配置参数';

comment on column msg_source_cfg.source_user is
'接口用户名';

comment on column msg_source_cfg.source_pwd is
'接口密码,des加密存放';

comment on column msg_source_cfg.source_name is
'消息接口名称';

comment on column msg_source_cfg.source_desc is
'接口描述';

comment on column msg_source_cfg.enabled_flag is
'启用状态:0.已停用 1.启用';

comment on column msg_source_cfg.last_update_date is
'最后更新时间';

comment on column msg_source_cfg.last_updated_by is
'最后更新用户id';

comment on column msg_source_cfg.creation_date is
'创建时间';

comment on column msg_source_cfg.created_by is
'创建用户id';

comment on column msg_source_cfg.is_delete is
'是否已删除';

/*==============================================================*/
/* Table: msg_td                                              */
/*==============================================================*/
create table msg_td 
(
   td_id              INTEGER            not null,
   phone              VARCHAR2(36),
   send_time          DATE                 default NULL,
   pass               VARCHAR2(36),
   content            VARCHAR2(500),
   org_id             INTEGER              default NULL,
   remark             VARCHAR2(36),
   last_update_date   DATE                 not null,
   last_updated_by    INTEGER              not null,
   last_update_login  INTEGER              default NULL,
   creation_date      DATE                 not null,
   created_by         INTEGER              not null,
   version_num        INTEGER              not null,
   msg_source_id      INTEGER              default NULL,
   is_delete          INTEGER              default 0,
   constraint PK_MSG_TD primary key (td_id)
);

comment on column msg_td.td_id is
'消息配置id';

comment on column msg_td.last_update_date is
'最后更新时间';

comment on column msg_td.last_updated_by is
'最后更新用户id';

comment on column msg_td.creation_date is
'创建时间';

comment on column msg_td.created_by is
'创建用户id';

comment on column msg_td.is_delete is
'是否删除';

/*==============================================================*/
/* Table: msg_temple_cfg                                      */
/*==============================================================*/
create table msg_temple_cfg 
(
   temple_id          INTEGER            not null,
   temple_name        VARCHAR2(50)         default NULL,
   temple_content     CLOB                 not null,
   provider_sign_name VARCHAR2(100)        default NULL,
   provider_template_code VARCHAR2(100)        default NULL,
   is_html            INTEGER              default NULL,
   org_id             INTEGER              default NULL,
   msg_type           VARCHAR2(20)         default NULL,
   channel            VARCHAR2(20)         default NULL,
   company_type       VARCHAR2(10)         default NULL,
   business           VARCHAR2(20)         default NULL,
   version_num        INTEGER              default NULL,
   last_updated_by    INTEGER              not null,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   creation_date      DATE                 not null,
   created_by         INTEGER              not null,
   temple_subject     VARCHAR2(255)        default NULL,
   is_delete          INTEGER              not null,
   msg_url            VARCHAR2(255)        default NULL,
   constraint PK_MSG_TEMPLE_CFG primary key (temple_id)
);

comment on column msg_temple_cfg.temple_id is
'消息配置id';

comment on column msg_temple_cfg.temple_content is
'最后更新时间';

comment on column msg_temple_cfg.provider_sign_name is
'供应商短信签名';

comment on column msg_temple_cfg.provider_template_code is
'供应商的模板编码';

comment on column msg_temple_cfg.msg_type is
'ou';

comment on column msg_temple_cfg.company_type is
'公司类型，用于微信接口对接微信公众号';

comment on column msg_temple_cfg.last_updated_by is
'最后更新用户id';

comment on column msg_temple_cfg.creation_date is
'创建时间';

comment on column msg_temple_cfg.created_by is
'创建用户id';

comment on column msg_temple_cfg.temple_subject is
'主题';

comment on column msg_temple_cfg.is_delete is
'是否删除';

comment on column msg_temple_cfg.msg_url is
'消息URL';

/*==============================================================*/
/* Table: msg_user                                            */
/*==============================================================*/
create table msg_user 
(
   msg_user_id        INTEGER            not null,
   org_id             INTEGER              default NULL,
   msg_user_name      VARCHAR2(96)         default NULL,
   msg_user_pwd       VARCHAR2(128)        default NULL,
   no_log             VARCHAR2(2)          default '1',
   remark             VARCHAR2(255)        default NULL,
   enabled_flag       VARCHAR2(2)          not null,
   last_update_date   DATE                 not null,
   last_updated_by    INTEGER              not null,
   creation_date      DATE                 not null,
   created_by         INTEGER              not null,
   version_num        INTEGER              not null,
   is_delete          INTEGER              not null,
   last_update_login  INTEGER              default NULL,
   constraint PK_MSG_USER primary key (msg_user_id)
);

comment on column msg_user.msg_user_id is
'消息配置id';

comment on column msg_user.msg_user_name is
'消息服务用户名';

comment on column msg_user.msg_user_pwd is
'消息服务用户密码,不可逆加密存放';

comment on column msg_user.no_log is
'0 不需要日志 1需要日志';

comment on column msg_user.enabled_flag is
'启用状态:0.已停用 1.启用';

comment on column msg_user.last_update_date is
'最后更新时间';

comment on column msg_user.last_updated_by is
'最后更新用户id';

comment on column msg_user.creation_date is
'创建时间';

comment on column msg_user.created_by is
'创建用户id';

comment on column msg_user.is_delete is
'是否删除';

/*==============================================================*/
/* Table: page_model_group_detail                             */
/*==============================================================*/
create table page_model_group_detail 
(
   group_det_id       INTEGER            not null,
   group_code         VARCHAR2(100),
   group_det_dim_code VARCHAR2(30),
   group_det_dim_type VARCHAR2(30),
   group_det_dim_action_view_type VARCHAR2(30),
   group_det_dim_name_en VARCHAR2(100),
   group_det_dim_name_cn VARCHAR2(100),
   group_det_dim_opt_code VARCHAR2(20),
   group_det_dim_opt_name VARCHAR2(30),
   version_num        INTEGER              default NULL,
   CREATION_DATE        DATE                 not null,
   CREATED_BY           INTEGER              default 0 not null,
   LAST_UPDATED_BY      INTEGER              default 0 not null,
   LAST_UPDATE_DATE     DATE                 not null,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   constraint PK_PAGE_MODEL_GROUP_DETAIL primary key (group_det_id)
);

create index group_code_index on page_model_group_detail (group_code);

/*==============================================================*/
/* Table: page_model_group_info                               */
/*==============================================================*/
create table page_model_group_info 
(
   group_id           INTEGER            not null,
   group_code         VARCHAR2(100),
   model_code         VARCHAR2(100),
   group_name_en      VARCHAR2(100),
   group_name_cn      VARCHAR2(100),
   group_level        INTEGER              default 0,
   group_parent_code  VARCHAR2(100),
   group_name_view_flag VARCHAR2(10),
   group_name_view_type VARCHAR2(45),
   group_order        INTEGER              default NULL,
   version_num        INTEGER              default NULL,
   CREATION_DATE        DATE                 not null,
   CREATED_BY           INTEGER              default 0 not null,
   LAST_UPDATED_BY      INTEGER              default 0 not null,
   LAST_UPDATE_DATE     DATE                 not null,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   constraint PK_PAGE_MODEL_GROUP_INFO primary key (group_id)
);

comment on column page_model_group_info.group_level is
'层次';
create unique index group_code_unique on page_model_group_info (group_code);
create index model_code_index on page_model_group_info (model_code);
create index group_parent_code on page_model_group_info (group_parent_code);

/*==============================================================*/
/* Table: page_model_info                                     */
/*==============================================================*/
create table page_model_info 
(
   model_id           INTEGER            not null,
   rule_business_line_code VARCHAR2(100),
   model_code         VARCHAR2(100),
   model_name         VARCHAR2(100),
   model_desc         VARCHAR2(400),
   version_num        INTEGER              default NULL,
   CREATION_DATE        DATE                 not null,
   CREATED_BY           INTEGER              default 0 not null,
   LAST_UPDATED_BY      INTEGER              default 0 not null,
   LAST_UPDATE_DATE     DATE                 not null,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   constraint PK_PAGE_MODEL_INFO primary key (model_id)
);

create unique index model_code_UNIQUE on page_model_info (model_code);

/*==============================================================*/
/* Table: per_all_assignments_f                               */
/*==============================================================*/
create table per_all_assignments_f 
(
   assignment_id      INTEGER            not null,
   applicant_rank     INTEGER              default NULL,
   application_id     INTEGER              default NULL,
   ass_attribute1     VARCHAR2(150)        default NULL,
   ass_attribute10    VARCHAR2(150)        default NULL,
   ass_attribute11    VARCHAR2(150)        default NULL,
   ass_attribute12    VARCHAR2(150)        default NULL,
   ass_attribute13    VARCHAR2(150)        default NULL,
   ass_attribute14    VARCHAR2(150)        default NULL,
   ass_attribute15    VARCHAR2(150)        default NULL,
   ass_attribute16    VARCHAR2(150)        default NULL,
   ass_attribute17    VARCHAR2(150)        default NULL,
   ass_attribute18    VARCHAR2(150)        default NULL,
   ass_attribute19    VARCHAR2(150)        default NULL,
   ass_attribute2     VARCHAR2(150)        default NULL,
   ass_attribute20    VARCHAR2(150)        default NULL,
   ass_attribute21    VARCHAR2(150)        default NULL,
   ass_attribute22    VARCHAR2(150)        default NULL,
   ass_attribute23    VARCHAR2(150)        default NULL,
   ass_attribute24    VARCHAR2(150)        default NULL,
   ass_attribute25    VARCHAR2(150)        default NULL,
   ass_attribute26    VARCHAR2(150)        default NULL,
   ass_attribute27    VARCHAR2(150)        default NULL,
   ass_attribute28    VARCHAR2(150)        default NULL,
   ass_attribute29    VARCHAR2(150)        default NULL,
   ass_attribute3     VARCHAR2(150)        default NULL,
   ass_attribute30    VARCHAR2(150)        default NULL,
   ass_attribute4     VARCHAR2(150)        default NULL,
   ass_attribute5     VARCHAR2(150)        default NULL,
   ass_attribute6     VARCHAR2(150)        default NULL,
   ass_attribute7     VARCHAR2(150)        default NULL,
   ass_attribute8     VARCHAR2(150)        default NULL,
   ass_attribute9     VARCHAR2(150)        default NULL,
   ass_attribute_category VARCHAR2(30)         default NULL,
   assignment_category VARCHAR2(30)         default NULL,
   assignment_int     VARCHAR2(30)         default NULL,
   assignment_sequence INTEGER              not null,
   assignment_status_type_id INTEGER              not null,
   assignment_type    VARCHAR2(1)          not null,
   bargaining_unit_code VARCHAR2(30)         default NULL,
   business_group_id  INTEGER              not null,
   cagr_grade_def_id  INTEGER              default NULL,
   cagr_id_flex_num   INTEGER              default NULL,
   change_reason      VARCHAR2(30)         default NULL,
   collective_agreement_id INTEGER              default NULL,
   comment_id         INTEGER              default NULL,
   contract_id        INTEGER              default NULL,
   created_by         INTEGER              default NULL,
   creation_datetime  DATE                 default NULL,
   datetime_probation_end DATE                 default NULL,
   default_code_comb_id INTEGER              default NULL,
   effective_end_datetime DATE                 not null,
   effective_start_datetime DATE                 not null,
   employee_category  VARCHAR2(30)         default NULL,
   employment_category VARCHAR2(30)         default NULL,
   establishment_id   INTEGER              default NULL,
   frequency          VARCHAR2(30)         default NULL,
   grade_id           INTEGER              default NULL,
   grade_ladder_pgm_id INTEGER              default NULL,
   hourly_salaried_code VARCHAR2(30)         default NULL,
   internal_address_line VARCHAR2(80)         default NULL,
   job_id             INTEGER              default NULL,
   job_post_source_name VARCHAR2(240)        default NULL,
   labour_union_member_flag VARCHAR2(30)         default NULL,
   last_updatetime_datetime DATE                 default NULL,
   last_updatetime_login INTEGER              default NULL,
   last_updatetimed_by INTEGER              default NULL,
   location_id        INTEGER              default NULL,
   manager_flag       VARCHAR2(30)         default NULL,
   normal_hours       NUMBER(22,3)         default NULL,
   notice_period      INTEGER              default NULL,
   notice_period_uom  VARCHAR2(30)         default NULL,
   object_version_int INTEGER              default NULL,
   organization_id    INTEGER              not null,
   pay_basis_id       INTEGER              default NULL,
   payroll_id         INTEGER              default NULL,
   people_group_id    INTEGER              default NULL,
   perf_review_period INTEGER              default NULL,
   perf_review_period_frequency VARCHAR2(30)         default NULL,
   period_of_placement_datetime_s DATE                 default NULL,
   period_of_service_id INTEGER              default NULL,
   person_id          INTEGER              not null,
   person_referred_by_id INTEGER              default NULL,
   po_header_id       INTEGER              default NULL,
   po_line_id         INTEGER              default NULL,
   position_id        INTEGER              default NULL,
   posting_content_id INTEGER              default NULL,
   primary_flag       VARCHAR2(30)         not null,
   probation_period   NUMBER(22,2)         default NULL,
   probation_unit     VARCHAR2(30)         default NULL,
   program_application_id INTEGER              default NULL,
   program_id         INTEGER              default NULL,
   program_updatetime_datetime DATE                 default NULL,
   project_title      VARCHAR2(30)         default NULL,
   projected_assignment_end DATE                 default NULL,
   recruiter_id       INTEGER              default NULL,
   recruitment_activity_id INTEGER              default NULL,
   request_id         INTEGER              default NULL,
   sal_review_period  INTEGER              default NULL,
   sal_review_period_frequency VARCHAR2(30)         default NULL,
   set_of_books_id    INTEGER              default NULL,
   soft_coding_keyflex_id INTEGER              default NULL,
   source_organization_id INTEGER              default NULL,
   source_type        VARCHAR2(30)         default NULL,
   special_ceiling_step_id INTEGER              default NULL,
   supervisor_assignment_id INTEGER              default NULL,
   supervisor_id      INTEGER              default NULL,
   time_normal_finish VARCHAR2(5)          default NULL,
   time_normal_start  VARCHAR2(5)          default NULL,
   title              VARCHAR2(30)         default NULL,
   vacancy_id         INTEGER              default NULL,
   vendor_assignment_int VARCHAR2(30)         default NULL,
   vendor_employee_int VARCHAR2(30)         default NULL,
   vendor_id          INTEGER              default NULL,
   vendor_site_id     INTEGER              default NULL,
   work_at_home       VARCHAR2(30)         default NULL,
   constraint PK_PER_ALL_ASSIGNMENTS_F primary key (assignment_id)
);

/*==============================================================*/
/* Table: per_all_people_f                                    */
/*==============================================================*/
create table per_all_people_f 
(
   person_id          INTEGER            not null,
   applicant_int      VARCHAR2(30)         default NULL,
   attribute1         VARCHAR2(150)        default NULL,
   attribute10        VARCHAR2(150)        default NULL,
   attribute11        VARCHAR2(150)        default NULL,
   attribute12        VARCHAR2(150)        default NULL,
   attribute13        VARCHAR2(150)        default NULL,
   attribute14        VARCHAR2(150)        default NULL,
   attribute15        VARCHAR2(150)        default NULL,
   attribute16        VARCHAR2(150)        default NULL,
   attribute17        VARCHAR2(150)        default NULL,
   attribute18        VARCHAR2(150)        default NULL,
   attribute19        VARCHAR2(150)        default NULL,
   attribute2         VARCHAR2(150)        default NULL,
   attribute20        VARCHAR2(150)        default NULL,
   attribute21        VARCHAR2(150)        default NULL,
   attribute22        VARCHAR2(150)        default NULL,
   attribute23        VARCHAR2(150)        default NULL,
   attribute24        VARCHAR2(150)        default NULL,
   attribute25        VARCHAR2(150)        default NULL,
   attribute26        VARCHAR2(150)        default NULL,
   attribute27        VARCHAR2(150)        default NULL,
   attribute28        VARCHAR2(150)        default NULL,
   attribute29        VARCHAR2(150)        default NULL,
   attribute3         VARCHAR2(150)        default NULL,
   attribute30        VARCHAR2(150)        default NULL,
   attribute4         VARCHAR2(150)        default NULL,
   attribute5         VARCHAR2(150)        default NULL,
   attribute6         VARCHAR2(150)        default NULL,
   attribute7         VARCHAR2(150)        default NULL,
   attribute8         VARCHAR2(150)        default NULL,
   attribute9         VARCHAR2(150)        default NULL,
   attribute_category VARCHAR2(30)         default NULL,
   background_check_status VARCHAR2(30)         default NULL,
   background_datetime_check DATE                 default NULL,
   benefit_group_id   INTEGER              default NULL,
   blood_type         VARCHAR2(30)         default NULL,
   business_group_id  INTEGER              not null,
   comment_id         INTEGER              default NULL,
   coord_ben_med_cvg_end_dt DATE                 default NULL,
   coord_ben_med_cvg_strt_dt DATE                 default NULL,
   coord_ben_med_ext_er VARCHAR2(80)         default NULL,
   coord_ben_med_insr_crr_ident VARCHAR2(80)         default NULL,
   coord_ben_med_insr_crr_name VARCHAR2(80)         default NULL,
   coord_ben_med_pl_name VARCHAR2(80)         default NULL,
   coord_ben_med_pln_no VARCHAR2(30)         default NULL,
   coord_ben_no_cvg_flag VARCHAR2(30)         default NULL,
   correspondence_language VARCHAR2(30)         default NULL,
   country_of_birth   VARCHAR2(90)         default NULL,
   created_by         INTEGER              default NULL,
   creation_datetime  DATE                 default NULL,
   current_applicant_flag VARCHAR2(30)         default NULL,
   current_emp_or_apl_flag VARCHAR2(30)         default NULL,
   current_employee_flag VARCHAR2(30)         default NULL,
   current_npw_flag   VARCHAR2(30)         default NULL,
   datetime_employee_data_verifie DATE                 default NULL,
   datetime_of_birth  DATE                 default NULL,
   datetime_of_death  DATE                 default NULL,
   dpdnt_adoption_datetime DATE                 default NULL,
   dpdnt_vlntry_svce_flag VARCHAR2(30)         default NULL,
   effective_end_datetime DATE                 not null,
   effective_start_datetime DATE                 not null,
   email_address      VARCHAR2(240)        default NULL,
   employee_int       VARCHAR2(30)         default NULL,
   expense_check_send_to_address VARCHAR2(30)         default NULL,
   fast_path_employee VARCHAR2(30)         default NULL,
   first_name         VARCHAR2(150)        default NULL,
   fte_capacity       NUMBER(5,2)          default NULL,
   full_name          VARCHAR2(240)        default NULL,
   global_name        VARCHAR2(240)        default NULL,
   global_person_id   VARCHAR2(30)         default NULL,
   hold_applicant_datetime_until DATE                 default NULL,
   honors             VARCHAR2(45)         default NULL,
   internal_location  VARCHAR2(45)         default NULL,
   known_as           VARCHAR2(80)         default NULL,
   last_medical_test_by VARCHAR2(60)         default NULL,
   last_medical_test_datetime DATE                 default NULL,
   last_name          VARCHAR2(150)        not null,
   last_updatetime_datetime DATE                 default NULL,
   last_updatetime_login INTEGER              default NULL,
   last_updatetimed_by INTEGER              default NULL,
   local_name         VARCHAR2(240)        default NULL,
   mailstop           VARCHAR2(45)         default NULL,
   marital_status     VARCHAR2(30)         default NULL,
   middle_names       VARCHAR2(60)         default NULL,
   national_identifier VARCHAR2(30)         default NULL,
   nationality        VARCHAR2(30)         default NULL,
   npw_int            VARCHAR2(30)         default NULL,
   object_version_int INTEGER              default NULL,
   office_int         VARCHAR2(45)         default NULL,
   on_military_service VARCHAR2(30)         default NULL,
   order_name         VARCHAR2(240)        default NULL,
   original_datetime_of_hire DATE                 default NULL,
   party_id           INTEGER              default NULL,
   per_information1   VARCHAR2(150)        default NULL,
   per_information10  VARCHAR2(150)        default NULL,
   per_information11  VARCHAR2(150)        default NULL,
   per_information12  VARCHAR2(150)        default NULL,
   per_information13  VARCHAR2(150)        default NULL,
   per_information14  VARCHAR2(150)        default NULL,
   per_information15  VARCHAR2(150)        default NULL,
   per_information16  VARCHAR2(150)        default NULL,
   per_information17  VARCHAR2(150)        default NULL,
   per_information18  VARCHAR2(150)        default NULL,
   per_information19  VARCHAR2(150)        default NULL,
   per_information2   VARCHAR2(150)        default NULL,
   per_information20  VARCHAR2(150)        default NULL,
   per_information21  VARCHAR2(150)        default NULL,
   per_information22  VARCHAR2(150)        default NULL,
   per_information23  VARCHAR2(150)        default NULL,
   per_information24  VARCHAR2(150)        default NULL,
   per_information25  VARCHAR2(150)        default NULL,
   per_information26  VARCHAR2(150)        default NULL,
   per_information27  VARCHAR2(150)        default NULL,
   per_information28  VARCHAR2(150)        default NULL,
   per_information29  VARCHAR2(150)        default NULL,
   per_information3   VARCHAR2(150)        default NULL,
   per_information30  VARCHAR2(150)        default NULL,
   per_information4   VARCHAR2(150)        default NULL,
   per_information5   VARCHAR2(150)        default NULL,
   per_information6   VARCHAR2(150)        default NULL,
   per_information7   VARCHAR2(150)        default NULL,
   per_information8   VARCHAR2(150)        default NULL,
   per_information9   VARCHAR2(150)        default NULL,
   per_information_category VARCHAR2(30)         default NULL,
   person_type_id     INTEGER              not null,
   pre_name_adjunct   VARCHAR2(30)         default NULL,
   previous_last_name VARCHAR2(150)        default NULL,
   program_application_id INTEGER              default NULL,
   program_id         INTEGER              default NULL,
   program_updatetime_datetime DATE                 default NULL,
   projected_start_datetime DATE                 default NULL,
   receipt_of_death_cert_datetime DATE                 default NULL,
   region_of_birth    VARCHAR2(90)         default NULL,
   registered_disabled_flag VARCHAR2(30)         default NULL,
   rehire_authorizor  VARCHAR2(60)         default NULL,
   rehire_reason      VARCHAR2(60)         default NULL,
   rehire_recommendation VARCHAR2(30)         default NULL,
   request_id         INTEGER              default NULL,
   resume_exists      VARCHAR2(30)         default NULL,
   resume_last_updatetimed DATE                 default NULL,
   second_passport_exists VARCHAR2(30)         default NULL,
   sex                VARCHAR2(30)         default NULL,
   start_datetime     DATE                 not null,
   student_status     VARCHAR2(30)         default NULL,
   suffix             VARCHAR2(30)         default NULL,
   title              VARCHAR2(30)         default NULL,
   town_of_birth      VARCHAR2(90)         default NULL,
   uses_tobacco_flag  VARCHAR2(30)         default NULL,
   vendor_id          INTEGER              default NULL,
   work_schedule      VARCHAR2(30)         default NULL,
   work_telephone     VARCHAR2(60)         default NULL,
   constraint PK_PER_ALL_PEOPLE_F primary key (person_id)
);

/*==============================================================*/
/* Table: pub_users_organization                              */
/*==============================================================*/
create table pub_users_organization 
(
   user_org_id        INTEGER            not null,
   org_id             INTEGER              default NULL,
   user_id            INTEGER              default NULL,
   start_date         DATE                 default NULL,
   end_date           DATE                 default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   constraint PK_PUB_USERS_ORGANIZATION primary key (user_org_id)
);

comment on column pub_users_organization.user_org_id is
'主键Id';

comment on column pub_users_organization.user_id is
'用户ID';

comment on column pub_users_organization.start_date is
'生效日期';

comment on column pub_users_organization.end_date is
'失效日期';

comment on column pub_users_organization.creation_date is
'创建日期';

comment on column pub_users_organization.created_by is
'创建人';

comment on column pub_users_organization.last_updated_by is
'更新人';

comment on column pub_users_organization.last_update_date is
'更新日期';

comment on column pub_users_organization.version_num is
'版本号';

create index PUB_USERS_ORGANIZATION_N on pub_users_organization (user_id);

/*==============================================================*/
/* Table: qrtz_blob_triggers                                  */
/*==============================================================*/
create table qrtz_blob_triggers 
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   BLOB_DATA            BLOB,
   constraint PK_QRTZ_BLOB_TRIGGERS primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: qrtz_calendars                                      */
/*==============================================================*/
create table qrtz_calendars 
(
   SCHED_NAME           VARCHAR2(120)        not null,
   CALENDAR_NAME        VARCHAR2(200)        not null,
   CALENDAR             BLOB                 not null,
   constraint PK_QRTZ_CALENDARS primary key (SCHED_NAME, CALENDAR_NAME)
);

/*==============================================================*/
/* Table: qrtz_cron_triggers                                  */
/*==============================================================*/
create table qrtz_cron_triggers 
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   CRON_EXPRESSION      VARCHAR2(120),
   TIME_ZONE_ID         VARCHAR2(80),
   constraint PK_QRTZ_CRON_TRIGGERS primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: qrtz_fired_triggers                                 */
/*==============================================================*/
create table qrtz_fired_triggers 
(
   SCHED_NAME           VARCHAR2(120)        not null,
   ENTRY_ID             VARCHAR2(95)         not null,
   TRIGGER_NAME         VARCHAR2(200),
   TRIGGER_GROUP        VARCHAR2(200),
   INSTANCE_NAME        VARCHAR2(200),
   FIRED_TIME           INTEGER              not null,
   SCHED_TIME           INTEGER              not null,
   PRIORITY             INTEGER              not null,
   STATE                VARCHAR2(16),
   JOB_NAME             VARCHAR2(200),
   JOB_GROUP            VARCHAR2(200),
   IS_NONCONCURRENT     VARCHAR2(1),
   REQUESTS_RECOVERY    VARCHAR2(1),
   constraint PK_QRTZ_FIRED_TRIGGERS primary key (SCHED_NAME, ENTRY_ID)
);

create index IDX_QRTZ_FT_TRIG_INST_NAME on  qrtz_fired_triggers(SCHED_NAME, INSTANCE_NAME);
create index IDX_QRTZ_FT_INST_JOB_REQ_RCVRY on qrtz_fired_triggers(SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY);
create index IDX_QRTZ_FT_J_G on qrtz_fired_triggers (SCHED_NAME, JOB_NAME, JOB_GROUP);
create index  IDX_QRTZ_FT_JG on qrtz_fired_triggers (SCHED_NAME, JOB_GROUP);
create index  IDX_QRTZ_FT_T_G on qrtz_fired_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
create index  IDX_QRTZ_FT_TG on qrtz_fired_triggers (SCHED_NAME, TRIGGER_GROUP);

/*==============================================================*/
/* Table: qrtz_job_details                                    */
/*==============================================================*/
create table qrtz_job_details 
(
   SCHED_NAME           VARCHAR2(120)        not null,
   JOB_NAME             VARCHAR2(200)        not null,
   JOB_GROUP            VARCHAR2(200)        not null,
   DESCRIPTION          VARCHAR2(250),
   JOB_CLASS_NAME       VARCHAR2(250),
   IS_DURABLE           VARCHAR2(1),
   IS_NONCONCURRENT     VARCHAR2(1),
   IS_UPDATE_DATA       VARCHAR2(1),
   REQUESTS_RECOVERY    VARCHAR2(1),
   JOB_DATA             BLOB,
   constraint PK_QRTZ_JOB_DETAILS primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
);
create index IDX_QRTZ_J_REQ_RECOVERY on qrtz_job_details(SCHED_NAME, REQUESTS_RECOVERY);
create index IDX_QRTZ_J_GRP on qrtz_job_details(SCHED_NAME, JOB_GROUP);


/*==============================================================*/
/* Table: qrtz_locks                                          */
/*==============================================================*/
create table qrtz_locks 
(
   SCHED_NAME           VARCHAR2(120)        not null,
   LOCK_NAME            VARCHAR2(40)         not null,
   constraint PK_QRTZ_LOCKS primary key (SCHED_NAME, LOCK_NAME)
);

/*==============================================================*/
/* Table: qrtz_paused_trigger_grps                            */
/*==============================================================*/
create table qrtz_paused_trigger_grps 
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   constraint PK_QRTZ_PAUSED_TRIGGER_GRPS primary key (SCHED_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: qrtz_scheduler_state                                */
/*==============================================================*/
create table qrtz_scheduler_state 
(
   SCHED_NAME           VARCHAR2(120)        not null,
   INSTANCE_NAME        VARCHAR2(200)        not null,
   LAST_CHECKIN_TIME    INTEGER              not null,
   CHECKIN_INTERVAL     INTEGER              not null,
   constraint PK_QRTZ_SCHEDULER_STATE primary key (SCHED_NAME, INSTANCE_NAME)
);

/*==============================================================*/
/* Table: qrtz_simple_triggers                                */
/*==============================================================*/
create table qrtz_simple_triggers 
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   REPEAT_COUNT         INTEGER              not null,
   REPEAT_INTERVAL      INTEGER              not null,
   TIMES_TRIGGERED      INTEGER              not null,
   constraint PK_QRTZ_SIMPLE_TRIGGERS primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: qrtz_simprop_triggers                               */
/*==============================================================*/
create table qrtz_simprop_triggers 
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   STR_PROP_1           VARCHAR2(512),
   STR_PROP_2           VARCHAR2(512),
   STR_PROP_3           VARCHAR2(512),
   INT_PROP_1           INTEGER              default NULL,
   INT_PROP_2           INTEGER              default NULL,
   LONG_PROP_1          INTEGER              default NULL,
   LONG_PROP_2          INTEGER              default NULL,
   DEC_PROP_1           NUMBER(13,4)         default NULL,
   DEC_PROP_2           NUMBER(13,4)         default NULL,
   BOOL_PROP_1          VARCHAR2(1),
   BOOL_PROP_2          VARCHAR2(1),
   constraint PK_QRTZ_SIMPROP_TRIGGERS primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

/*==============================================================*/
/* Table: qrtz_triggers                                       */
/*==============================================================*/
create table qrtz_triggers 
(
   SCHED_NAME           VARCHAR2(120)        not null,
   TRIGGER_NAME         VARCHAR2(200)        not null,
   TRIGGER_GROUP        VARCHAR2(200)        not null,
   JOB_NAME             VARCHAR2(200),
   JOB_GROUP            VARCHAR2(200),
   DESCRIPTION          VARCHAR2(250),
   NEXT_FIRE_TIME       INTEGER              default NULL,
   PREV_FIRE_TIME       INTEGER              default NULL,
   PRIORITY             INTEGER              default NULL,
   TRIGGER_STATE        VARCHAR2(16),
   TRIGGER_TYPE         VARCHAR2(8),
   START_TIME           INTEGER              not null,
   END_TIME             INTEGER              default NULL,
   CALENDAR_NAME        VARCHAR2(200),
   MISFIRE_INSTR        SMALLINT             default NULL,
   JOB_DATA             BLOB,
   constraint PK_QRTZ_TRIGGERS primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create index IDX_QRTZ_T_J	on qrtz_triggers(SCHED_NAME, JOB_NAME, JOB_GROUP);
create index IDX_QRTZ_T_JG	on qrtz_triggers(SCHED_NAME, JOB_GROUP);
create index IDX_QRTZ_T_C	on qrtz_triggers(SCHED_NAME, CALENDAR_NAME);
create index IDX_QRTZ_T_G	on qrtz_triggers(SCHED_NAME, TRIGGER_GROUP);
create index IDX_QRTZ_T_STATE on qrtz_triggers(SCHED_NAME, TRIGGER_STATE);
create index IDX_QRTZ_T_N_STATE on qrtz_triggers(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE);
create index IDX_QRTZ_T_N_G_STATE on qrtz_triggers(SCHED_NAME, TRIGGER_GROUP, TRIGGER_STATE);
create index IDX_QRTZ_T_NEXT_FIRE_TIME on qrtz_triggers(SCHED_NAME, NEXT_FIRE_TIME);
create index IDX_QRTZ_T_NFT_ST on qrtz_triggers(SCHED_NAME, TRIGGER_STATE, NEXT_FIRE_TIME);
create index IDX_QRTZ_T_NFT_MISFIRE	on qrtz_triggers(SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME);
create index IDX_QRTZ_T_NFT_ST_MISFIRE on qrtz_triggers(SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_STATE);
create index IDX_QRTZ_T_NFT_ST_MISFIRE_GRP on qrtz_triggers(SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_GROUP, TRIGGER_STATE);


/*==============================================================*/
/* Table: rule_business_line                                  */
/*==============================================================*/
create table rule_business_line 
(
   rule_business_line_id INTEGER            not null,
   rule_business_line_code VARCHAR2(100),
   rule_business_line_type VARCHAR2(100),
   rule_business_line_name VARCHAR2(500),
   rule_business_line_Desc VARCHAR2(500),
   rule_business_line_parent_code VARCHAR2(100),
   rule_business_line_mappType VARCHAR2(100),
   version_num        INTEGER              default NULL,
   CREATION_DATE        DATE                 not null,
   CREATED_BY           INTEGER              default 0 not null,
   LAST_UPDATED_BY      INTEGER              default 0 not null,
   LAST_UPDATE_DATE     DATE                 not null,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   constraint PK_RULE_BUSINESS_LINE primary key (rule_business_line_id)
);

/*==============================================================*/
/* Table: rule_dim                                            */
/*==============================================================*/
create table rule_dim 
(
   RULE_DIM_ID          INTEGER            not null,
   rule_business_line_code VARCHAR2(100),
   RULE_view_TYPE     VARCHAR2(30),
   RULE_DIM_NAME        VARCHAR2(150),
   RULE_DIM_DATA_TYPE   VARCHAR2(15),
   RULE_DIM_default_VALUE VARCHAR2(150),
   RULE_DIM_CODE        VARCHAR2(150),
   RULE_DIM_DESC        VARCHAR2(500),
   rule_DIM_value_from VARCHAR2(100),
   rule_dim_target_source VARCHAR2(500),
   EFFECT_DATE          DATE                 default NULL,
   EFFECT_END_DATE      DATE                 default NULL,
   rule_dim_reference_from VARCHAR2(100),
   rule_dim_reference_code VARCHAR2(100),
   placeholder        VARCHAR2(45),
   version_num        INTEGER              default NULL,
   CREATION_DATE        DATE                 not null,
   CREATED_BY           INTEGER              default 0 not null,
   LAST_UPDATED_BY      INTEGER              default 0 not null,
   LAST_UPDATE_DATE     DATE                 not null,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   constraint PK_RULE_DIM primary key (RULE_DIM_ID)
);

/*==============================================================*/
/* Table: rule_expression                                     */
/*==============================================================*/
create table rule_expression 
(
   RULE_EXP_ID          INTEGER            not null,
   rule_business_line_code VARCHAR2(100),
   RULE_EXP_NAME        VARCHAR2(400),
   RULE_EXP_CODE        VARCHAR2(150),
   RULE_EXP_DESC        VARCHAR2(500),
   RULE_SIMPLE_EXP      VARCHAR2(3900),
   rule_exp           VARCHAR2(1000),
   rule_exp_params    VARCHAR2(500),
   RULE_EXP_WEIGHT      INTEGER              default NULL,
   EFFECT_DATE          DATE                 default NULL,
   EFFECT_END_DATE      DATE                 default NULL,
   version_num        INTEGER              default NULL,
   CREATION_DATE        DATE                 not null,
   CREATED_BY           INTEGER              default 0 not null,
   LAST_UPDATED_BY      INTEGER              default 0 not null,
   LAST_UPDATE_DATE     DATE                 not null,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   constraint PK_RULE_EXPRESSION primary key (RULE_EXP_ID)
);

/*==============================================================*/
/* Table: rule_expressiondim                                  */
/*==============================================================*/
create table rule_expressiondim 
(
   RULE_EXP_DIM_ID      INTEGER            not null,
   rule_business_line_code VARCHAR2(100),
   rule_order_Id      VARCHAR2(20),
   RULE_DIM_CODE        VARCHAR2(150),
   RULE_DIM_NAME        VARCHAR2(150),
   rule_dim_operators VARCHAR2(15),
   rule_dim_value     VARCHAR2(200),
   RULE_EXP_CODE        VARCHAR2(150),
   EFFECT_DATE          DATE                 default NULL,
   EFFECT_END_DATE      DATE                 default NULL,
   version_num        INTEGER              default NULL,
   CREATION_DATE        DATE                 not null,
   CREATED_BY           INTEGER              default 0 not null,
   LAST_UPDATED_BY      INTEGER              default 0 not null,
   LAST_UPDATE_DATE     DATE                 not null,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   constraint PK_RULE_EXPRESSIONDIM primary key (RULE_EXP_DIM_ID)
);

/*==============================================================*/
/* Table: rule_mapping_business                               */
/*==============================================================*/
create table rule_mapping_business 
(
   RULE_MAPP_BUS_ID     INTEGER            not null,
   RULE_BUS_dim       VARCHAR2(150),
   rule_bus_dim_operator VARCHAR2(255),
   rule_bus_dim_value VARCHAR2(15),
   rule_bus_target_type VARCHAR2(150),
   rule_bus_target_source VARCHAR2(150),
   rule_bus_result_dim VARCHAR2(3000),
   rule_bus_param     VARCHAR2(3000),
   rule_exc_code      VARCHAR2(100),
   rule_bus_level     INTEGER              default NULL,
   EFFECT_DATE          DATE                 default NULL,
   EFFECT_END_DATE      DATE                 default NULL,
   version_num        INTEGER              default NULL,
   CREATION_DATE        DATE                 not null,
   CREATED_BY           INTEGER              default 0 not null,
   LAST_UPDATED_BY      INTEGER              default 0 not null,
   LAST_UPDATE_DATE     DATE                 not null,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   constraint PK_RULE_MAPPING_BUSINESS primary key (RULE_MAPP_BUS_ID)
);

/*==============================================================*/
/* Table: saaf_file_upload                                    */
/*==============================================================*/
create table saaf_file_upload 
(
   UPLOAD_ID            INTEGER            not null,
   FILE_NAME            VARCHAR2(200),
   FILE_SIZE            NUMBER(20,2)         default NULL,
   FILE_TYPE            VARCHAR2(200),
   SOURCE_CODE          VARCHAR2(200),
   SOURCE_ID            INTEGER              default NULL,
   STATUS               VARCHAR2(100),
   FILE_ADDRESS         VARCHAR2(500),
   START_DATE_ACTIVE    DATE                 not null,
   END_DATE_ACTIVE      DATE                 default NULL,
   DESCRIPTION          VARCHAR2(500),
   INST_ID              INTEGER              default NULL,
   VERSION_NUM          INTEGER              default 0,
   CREATION_DATE        DATE                 default sysdate not null,
   CREATED_BY           INTEGER              default -1 not null,
   LAST_UPDATED_BY      INTEGER              default -1 not null,
   LAST_UPDATE_DATE     DATE                 default sysdate not null,
   LAST_UPDATE_LOGIN    INTEGER              default NULL,
   ATTRIBUTE_CATEGORY   VARCHAR2(30),
   ATTRIBUTE1           VARCHAR2(240),
   ATTRIBUTE2           VARCHAR2(240),
   ATTRIBUTE3           VARCHAR2(240),
   ATTRIBUTE4           VARCHAR2(240),
   ATTRIBUTE5           VARCHAR2(240),
   ATTRIBUTE6           VARCHAR2(240),
   ATTRIBUTE7           VARCHAR2(240),
   ATTRIBUTE8           VARCHAR2(240),
   ATTRIBUTE9           VARCHAR2(240),
   ATTRIBUTE10          VARCHAR2(240),
   constraint PK_SAAF_FILE_UPLOAD primary key (UPLOAD_ID)
);

comment on column saaf_file_upload.UPLOAD_ID is
'表ID，主键，供其他表做外键';

comment on column saaf_file_upload.FILE_SIZE is
'文件大小';

comment on column saaf_file_upload.SOURCE_ID is
'来源ID';

comment on column saaf_file_upload.START_DATE_ACTIVE is
'起始日期';

comment on column saaf_file_upload.END_DATE_ACTIVE is
'终止日期';

comment on column saaf_file_upload.INST_ID is
'所属中心ID，关联SAAF_INSTITUTION表';

create index VR_FILE_UPLOAD_n1 on saaf_file_upload(FILE_NAME);
create index VR_FILE_UPLOAD_n2 on saaf_file_upload(SOURCE_CODE);


/*==============================================================*/
/* Table: saaf_webservice_info                                */
/*==============================================================*/
create table saaf_webservice_info 
(
   webservice_id      INTEGER            not null,
   business_line_code VARCHAR2(100),
   webservice_code    VARCHAR2(100),
   webservice_url     VARCHAR2(3000),
   webservice_name    VARCHAR2(100),
   webservice_desc    VARCHAR2(3000),
   webserice_agreement VARCHAR2(10),
   webservice_type    VARCHAR2(10),
   request_param_demo VARCHAR2(3000),
   response_param_demo VARCHAR2(3000),
   version_num        INTEGER              default NULL,
   CREATION_DATE        DATE                 default NULL,
   CREATED_BY           INTEGER              default NULL,
   LAST_UPDATED_BY      DATE                 default NULL,
   LAST_UPDATE_DATE     DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   constraint PK_SAAF_WEBSERVICE_INFO primary key (webservice_id)
);
comment on column saaf_webservice_info.version_num is
'版本';

create index webservice_code_unique on saaf_webservice_info(webservice_code);

   
   

/*==============================================================*/
/* Table: saaf_webservice_param_info                          */
/*==============================================================*/
create table saaf_webservice_param_info 
(
   param_id           INTEGER            not null,
   webservice_code    VARCHAR2(100),
   param_code         VARCHAR2(80),
   param_name         VARCHAR2(100),
   param_desc         VARCHAR2(500),
   param_type         VARCHAR2(50),
   required_flag      VARCHAR2(10),
   version_num        INTEGER              default NULL,
   CREATION_DATE        DATE                 not null,
   CREATED_BY           INTEGER              default 0 not null,
   LAST_UPDATED_BY      INTEGER              default 0 not null,
   LAST_UPDATE_DATE     DATE                 not null,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   constraint PK_SAAF_WEBSERVICE_PARAM_INFO primary key (param_id)
);
create index webservice_code	on saaf_webservice_param_info(webservice_code, param_code);


/*==============================================================*/
/* Table: schedule_job_access_orgs                            */
/*==============================================================*/
create table schedule_job_access_orgs 
(
   access_org_id      INTEGER            not null,
   job_id             INTEGER              default NULL,
   org_id             INTEGER              default NULL,
   platform_code      VARCHAR2(30),
   start_date         DATE                 default NULL,
   end_date           DATE                 default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default -1,
   last_updated_by    INTEGER              default -1,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   constraint PK_SCHEDULE_JOB_ACCESS_ORGS primary key (access_org_id)
);

/*==============================================================*/
/* Table: schedule_job_parameters                             */
/*==============================================================*/
create table schedule_job_parameters 
(
   PARAM_ID             INTEGER            not null,
   JOB_ID               INTEGER              default NULL,
   PARAM_SEQ_NUM        INTEGER              default NULL,
   PARAM_NAME           VARCHAR2(500),
   PARAM_TYPE           VARCHAR2(50),
   DESCRIPTION          CLOB,
   IS_REQUIRED          VARCHAR2(10),
   IS_ENABLED           VARCHAR2(10),
   DEFAULT_VALUE        CLOB,
   PARAM_REGION         VARCHAR2(10),
   CREATION_DATE        DATE                 not null,
   CREATED_BY           INTEGER              default 0 not null,
   LAST_UPDATED_BY      INTEGER              default 0,
   LAST_UPDATE_DATE     DATE                 default NULL,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   constraint PK_SCHEDULE_JOB_PARAMETERS primary key (PARAM_ID)
);

comment on column schedule_job_parameters.PARAM_ID is
'唯一ID';

create index IND_JOB_ID on schedule_job_parameters (JOB_ID);


/*==============================================================*/
/* Table: schedule_job_resp                                   */
/*==============================================================*/
create table schedule_job_resp 
(
   job_resp_id        INTEGER            not null,
   job_id             INTEGER              not null,
   job_resp_name      VARCHAR2(100),
   responsibility_id  INTEGER              not null,
   platform_code      VARCHAR2(30),
   start_date_active  DATE                 default NULL,
   end_date_active    DATE                 default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default -1,
   last_updated_by    INTEGER              default -1,
   last_update_date   DATE                 default NULL,
   last_update_login  INTEGER              default NULL,
   version_num        INTEGER              default 0,
   constraint PK_SCHEDULE_JOB_RESP primary key (job_resp_id)
);

/*==============================================================*/
/* Table: schedule_jobs                                       */
/*==============================================================*/
create table schedule_jobs 
(
   JOB_ID               INTEGER            not null,
   JOB_NAME             VARCHAR2(200),
   DESCRIPTION          CLOB,
   EXECUTABLE_NAME      VARCHAR2(500),
   METHOD               VARCHAR2(240),
   OUTPUT_FILE_TYPE     VARCHAR2(100),
   SYSTEM               VARCHAR2(240),
   MODULE               VARCHAR2(240),
   JOB_TYPE             VARCHAR2(120),
   CREATION_DATE        DATE                 default NULL,
   CREATED_BY           INTEGER              default 0,
   LAST_UPDATED_BY      INTEGER              default 0,
   LAST_UPDATE_DATE     DATE                 default NULL,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   VERSION_NUM          INTEGER              default 0,
   SINGLE_INSTANCE      VARCHAR2(6),
   constraint PK_SCHEDULE_JOBS primary key (JOB_ID)
);

comment on column schedule_jobs.JOB_ID is
'唯一ID';
create index IND_JOB_NAME on  schedule_jobs(JOB_NAME);


/*==============================================================*/
/* Table: schedule_log                                        */
/*==============================================================*/
create table schedule_log 
(
   id_                INTEGER            not null,
   schedule_id        VARCHAR2(64)         default NULL,
   time_              DATE                 default NULL,
   message_           VARCHAR2(4000)       default NULL,
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default NULL,
   last_update_login  INTEGER              default NULL,
   constraint PK_SCHEDULE_LOG primary key (id_)
);

/*==============================================================*/
/* Table: schedule_schedules                                  */
/*==============================================================*/
create table schedule_schedules 
(
   SCHEDULE_ID          INTEGER            not null,
   PARENT_SCHEDULE_ID   INTEGER              default NULL,
   PHASE_CODE           VARCHAR2(240),
   STATUS_CODE          VARCHAR2(240),
   ACTUAL_START_DATE    DATE                 default NULL,
   ACTUAL_COMPLETION_DATE DATE                 default NULL,
   LOG_FILE_NAME        VARCHAR2(240),
   OUTPUT_FILE_NAME     VARCHAR2(240),
   OUTPUT_FILE_TYPE     VARCHAR2(10),
   SCHEDULE_EXPECT_START_DATE DATE                 default NULL,
   SCHEDULE_EXPECT_END_DATE DATE                 default NULL,
   JOB_ID               INTEGER              default NULL,
   QUARTZ_JOB_NAME      VARCHAR2(480),
   CORNEXPRESS          VARCHAR2(240),
   PRIORITY             INTEGER              default NULL,
   PREVIOUS_FIRE_TIME   DATE                 default NULL,
   NEXT_FIRE_TIME       DATE                 default NULL,
   SCHEDULE_TYPE        VARCHAR2(120),
   ARGUMENT_OBJ         BLOB,
   IS_DELETED           VARCHAR2(10),
   WAS_EXECUTED_TOTAL_COUNT INTEGER              default NULL,
   WAS_EXECUTED_SUCCESS_COUNT INTEGER              default NULL,
   WAS_EXECUTED_FAIL_COUNT INTEGER              default NULL,
   ARGUMENTS_TEXT       CLOB,
   FAIL_ATTEMPT_FREQUENCY INTEGER              default NULL,
   TIMEOUT              INTEGER              default NULL,
   CREATION_DATE        DATE                 not null,
   CREATED_BY           INTEGER              not null,
   LAST_UPDATED_BY      INTEGER              default NULL,
   LAST_UPDATE_DATE     DATE                 default NULL,
   LAST_UPDATE_LOGIN    INTEGER              default 0,
   VERSION_NUM          INTEGER              default 0,
   constraint PK_SCHEDULE_SCHEDULES primary key (SCHEDULE_ID)
);

comment on column schedule_schedules.PARENT_SCHEDULE_ID is
'父请求ID，计划请求的子请求自动产生此字段值';

create index IND_SCH_JOB_ID on schedule_schedules (JOB_ID);



/*==============================================================*/
/* Table: schedule_schedules_error                            */
/*==============================================================*/
create table schedule_schedules_error 
(
   error_id           INTEGER            not null,
   schedule_id        INTEGER              not null,
   schedule_data      VARCHAR2(3000),
   error_str          CLOB,
   status             VARCHAR2(30),
   description        VARCHAR2(500),
   VERSION_NUM          INTEGER              default 0,
   CREATION_DATE        DATE                 default sysdate not null,
   CREATED_BY           INTEGER              default -1 not null,
   LAST_UPDATED_BY      INTEGER              default -1 not null,
   LAST_UPDATE_DATE     DATE                 default sysdate not null,
   LAST_UPDATE_LOGIN    INTEGER              default NULL,
   constraint PK_SCHEDULE_SCHEDULES_ERROR primary key (error_id)
);

comment on column schedule_schedules_error.error_id is
'表ID，主键，供其他表做外键';

comment on column schedule_schedules_error.schedule_id is
'调度ID';

create index saaf_schedrules_error_n1 on schedule_schedules_error(schedule_id);
create index  saaf_schedrules_error_n2 on schedule_schedules_error(status);


/*==============================================================*/
/* Table: user_pwd_temp                                       */
/*==============================================================*/
create table user_pwd_temp 
(
   bu                 VARCHAR2(20),
   user_name          VARCHAR2(50),
   email              VARCHAR2(50),
   pwd                VARCHAR2(100),
   send_flag          VARCHAR2(6),
   creation_date      DATE                 default NULL,
   created_by         INTEGER              default NULL,
   last_updated_by    INTEGER              default NULL,
   last_update_date   DATE                 default NULL,
   version_num        INTEGER              default NULL,
   last_update_login  INTEGER              default NULL
);

/*==============================================================*/
/* Table: wx_user                                             */
/*==============================================================*/
create table wx_user 
(
   id                 VARCHAR2(200)        not null,
   userid             VARCHAR2(30),
   name               VARCHAR2(150),
   department         VARCHAR2(300),
   position           VARCHAR2(300),
   mobile             VARCHAR2(30),
   gender             VARCHAR2(20),
   email              VARCHAR2(30),
   avatar             VARCHAR2(300),
   status             VARCHAR2(60),
   enable             VARCHAR2(240),
   isleader           VARCHAR2(30),
   extattr            VARCHAR2(30),
   hide_mobile        VARCHAR2(30),
   english_name       VARCHAR2(30),
   telephone          VARCHAR2(30),
   order_             VARCHAR2(300),
   constraint PK_WX_USER primary key (id)
);

/*==============================================================*/
/* Table: z_test                                              */
/*==============================================================*/
create table z_test 
(
   avatar             VARCHAR2(500)        default NULL,
   description        VARCHAR2(255)        default NULL,
   homeMashup         VARCHAR2(255)        default NULL,
   isSystemObject     VARCHAR2(255)        default NULL,
   name               VARCHAR2(255)        default NULL,
   TooltipName        VARCHAR2(600)        default NULL,
   KWHByDay           VARCHAR2(500)        default NULL,
   KWHCostByHour      VARCHAR2(500)        default NULL,
   KWH                  VARCHAR2(500)      default NULL
);

