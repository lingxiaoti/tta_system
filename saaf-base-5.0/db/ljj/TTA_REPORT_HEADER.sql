create sequence SEQ_TTA_REPORT_HEADER  

increment by 1  

start with 1  

maxvalue 9999999  

cycle  

nocache;  

CREATE TABLE "TTA_REPORT_HEADER" (
"ID" NUMBER  NOT NULL,
"BATCH_ID" VARCHAR2(50),
"STATUS" VARCHAR2(50),
"REPORT_TYPE" VARCHAR2(50),
"BACK_UP" VARCHAR2(50),
"CREATION_DATE" DATE DEFAULT SYSDATE, 
"CREATED_BY" NUMBER(*,0), 
"LAST_UPDATED_BY" NUMBER(*,0), 
"LAST_UPDATE_DATE" DATE DEFAULT SYSDATE, 
"LAST_UPDATE_LOGIN" NUMBER(*,0)
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "TTA_REPORT_HEADER" IS ' 报表头表';
COMMENT ON COLUMN "TTA_REPORT_HEADER"."ID" IS 'ID';
COMMENT ON COLUMN "TTA_REPORT_HEADER"."BATCH_ID" IS '批次号';
COMMENT ON COLUMN "TTA_REPORT_HEADER"."STATUS" IS '状态';
COMMENT ON COLUMN "TTA_REPORT_HEADER"."REPORT_TYPE" IS '报表类型';
COMMENT ON COLUMN "TTA_REPORT_HEADER"."BACK_UP" IS '备用字段';

ALTER TABLE "TTA_REPORT_HEADER" ADD PRIMARY KEY ("ID");
