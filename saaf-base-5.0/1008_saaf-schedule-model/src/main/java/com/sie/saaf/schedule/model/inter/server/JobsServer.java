package com.sie.saaf.schedule.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.schedule.model.entities.ScheduleJobsEntity_HI;
import com.sie.saaf.schedule.model.entities.readonly.ScheduleJobsEntity_HI_RO;
import com.sie.saaf.schedule.model.inter.IJobs;
import com.sie.saaf.schedule.utils.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("jobsServer")
public class JobsServer extends BaseCommonServer<ScheduleJobsEntity_HI> implements IJobs {

    private static Logger log = LoggerFactory.getLogger(JobsServer.class);

    @Autowired
    private ViewObject<ScheduleJobsEntity_HI> scheduleJobsDAO_HI;
    @Autowired
    private BaseViewObject<ScheduleJobsEntity_HI_RO> scheduleJobsDAO_HI_RO;

    public JobsServer() {
        super();
    }

    public Pagination<ScheduleJobsEntity_HI_RO> findJobs(JSONObject parameters, Integer pageIndex, Integer pageRows) {
        StringBuffer sqlSB = new StringBuffer(ScheduleJobsEntity_HI_RO.QUERY_SQL);
        sqlSB.append(" where 1=1 ");
        Map<String, Object> map = new HashMap<String, Object>();
        Pagination<ScheduleJobsEntity_HI_RO> rows = null;
        com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.JOB_ID", "jobId", sqlSB, map, "=");
//        if (null != parameters.get("jobId") && !"".equals(parameters.get("jobId"))) {
//            where.append(" and sj.JOB_ID = :varJobId ");
//            map.put("varJobId", Integer.parseInt(parameters.get("jobId").toString()));
//        }
        com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.JOB_NAME", "jobName", sqlSB, map, "like");
//        if (null != parameters.get("jobName") && !"".equals(parameters.get("jobName"))) {
//            where.append("and sj.JOB_NAME like :varJobName ");
//            map.put("varJobName", "%" + parameters.get("jobName") + "%");
//        }
        com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.EXECUTABLE_NAME", "executableName", sqlSB, map, "like");
//        if (null != parameters.get("executableName") && !"".equals(parameters.get("executableName"))) {
//            where.append("and sj.EXECUTABLE_NAME like :varExecutableName ");
//            map.put("varExecutableName", "%" + parameters.get("executableName") + "%");
//        }
        com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.METHOD", "method", sqlSB, map, "like");
//        if (null != parameters.get("method") && !"".equals(parameters.get("method"))) {
//            where.append("and sj.METHOD like :varMethod ");
//            map.put("varMethod", "%" + parameters.get("method") + "%");
//        }
        com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.SYSTEM", "system", sqlSB, map, "=");
//        if (null != parameters.get("system") && !"".equals(parameters.get("system"))) {
//            where.append("and sj.SYSTEM = :varSystem ");
//            map.put("varSystem", parameters.get("system"));
//        }
        com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.MODULE", "module", sqlSB, map, "=");
//        if (null != parameters.get("module") && !"".equals(parameters.get("module"))) {
//            where.append("and sj.MODULE = :varModule ");
//            map.put("varModule", parameters.get("module"));
//        }
        com.sie.saaf.common.util.SaafToolUtils.parperParam(parameters, "sj.JOB_TYPE", "varJobType", sqlSB, map, "=");
//        if (null != parameters.get("jobType") && !"".equals(parameters.get("jobType"))) {
//            where.append("and sj.JOB_TYPE = :varJobType ");
//            map.put("varJobType", parameters.get("jobType"));
//        }
//
//        //部门控制
		if (null != parameters.get("varUserId") && !"".equals(parameters.get("varUserId").toString())) {
			String queryJOBIdsSQL = "SELECT sjr.job_id FROM schedule_job_access_orgs sjr WHERE sjr.org_id IN (SELECT suao.org_id FROM base_person_organization suao WHERE suao.person_id = :varUserId)";
            String queryJOBIdInSQL = "sj.JOB_ID IN (" + queryJOBIdsSQL + ")";
            sqlSB.append(" AND ((");
            sqlSB.append(queryJOBIdInSQL);
            sqlSB.append(" or sj.CREATED_BY=:varUserId)");

//		    sqlSB.append("AND ((sj.JOB_ID IN (\n" + "	SELECT\n" + "		sjr.job_id\n" + "	FROM\n"
//					+ "		schedule_job_access_orgs sjr\n" + "	WHERE\n" + "		sjr.inst_id IN (\n"
//					+ "			SELECT\n" + "				suao.inst_id\n" + "			FROM\n"
//					+ "				base_person_organization suao\n" + "			WHERE\n"
//					+ "				suao.user_id = :varUserId \n" + "		)\n" + ") or sj.CREATED_BY=:varUserId )");
			// 职责控制
//			if (null != parameters.get("varResponsibilitiesId") && !"".equals(parameters.get("varResponsibilitiesId").toString())) {
            String queryJOBIdsSQL_ = "SELECT sjr.job_id FROM schedule_job_resp sjr, base_user_responsibility sur WHERE sjr.responsibility_id = sur.responsibility_id and sur.user_id =:varUserId";
            String queryJOBIdInSQL_ = "or sj.JOB_ID IN (" + queryJOBIdsSQL_ + ")";
            sqlSB.append(queryJOBIdInSQL_);
//				sqlSB.append("or sj.JOB_ID IN (\n" + "SELECT\n" +
//						"	sjr.job_id\n" +
//						"FROM\n" +
//						"	schedule_job_resp sjr,\n" +
//						"	base_user_responsibility sur\n" +
//						"WHERE\n" +
//						"	sjr.responsibility_id = sur.responsibility_id\n" +
//						"and sur.user_id =:varUserId" + ")");

//				map.put("varResponsibilitiesId", Integer.parseInt(parameters.get("varResponsibilitiesId").toString()));
//			}
			sqlSB.append(" )");
			map.put("varUserId", Integer.parseInt(parameters.get("varUserId").toString()));
		}
        if (map.size() > 0) {
            rows = scheduleJobsDAO_HI_RO.findPagination(sqlSB.toString(), map, pageIndex, pageRows);
        } else {
            rows = scheduleJobsDAO_HI_RO.findPagination(sqlSB.toString(), pageIndex, pageRows);
        }

        return rows;

    }

    public String saveJob(JSONObject parameters) {
        log.info("----------Job（create）--------------" + parameters + "---------------");
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        if (parameters.get("jobName") == null || parameters.get("jobName") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！jobName为必填!", 0, null);
            return jsonResult.toString();
        }
        if (parameters.get("executableName") == null || parameters.get("executableName") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！可执行（executableName）为必填!", 0, null);
            return jsonResult.toString();
        }
        if (parameters.get("jobType") == null || parameters.get("jobType") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！Job类型（jobType）为必填!", 0, null);
            return jsonResult.toString();
        }

        if (!(parameters.get("jobType").toString().equalsIgnoreCase("java") || parameters.get("jobType").toString().equalsIgnoreCase("webservice") ||
              parameters.get("jobType").toString().equalsIgnoreCase("package"))) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！jobType必须为‘webservice’、‘java’、‘package’之一", 0, null);
            return jsonResult.toString();
        }

        //如果jobType为 ‘webservice’ 或 ‘java’则， method 字段为必填
        if ((parameters.get("jobType").toString().equalsIgnoreCase("webservice") || parameters.get("jobType").toString().equalsIgnoreCase("java")) &&
            (parameters.get("method") == null || parameters.get("method") == "")) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！jobType为‘webservice’或‘java’时，method为必填!", 0, null);
            return jsonResult.toString();
        }

        if (isJobNameRepeat(parameters)) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！jobName重复!", 0, null);
            return jsonResult.toString();
        }

        //
        //        ViewObject vo = (ViewObject)SToolUtils.context.getBean("saafJobsDAO_HI");
        int userId = Integer.parseInt(parameters.get("varUserId").toString());
        ScheduleJobsEntity_HI job = new ScheduleJobsEntity_HI();
        job.setCreationDate(new Date());
        job.setCreatedBy(userId);
        job.setLastUpdateDate(new Date());
        job.setLastUpdatedBy(userId);
        job.setLastUpdateLogin(userId);

        //
        job.setJobName(parameters.get("jobName") != null ? parameters.get("jobName").toString() : "");
        job.setDescription(parameters.get("description") != null ? parameters.get("description").toString() : "");
        job.setExecutableName(parameters.get("executableName") != null ? parameters.get("executableName").toString() : "");
        job.setJobType(parameters.get("jobType") != null ? parameters.get("jobType").toString() : "");
        job.setMethod(parameters.get("method") != null ? parameters.get("method").toString() : "");
        job.setOutputFileType(parameters.get("outputFileType") != null ? parameters.get("outputFileType").toString() : "");
        job.setSystem(parameters.get("system") != null ? parameters.get("system").toString() : "");
        job.setModule(parameters.get("module") != null ? parameters.get("module").toString() : "");
        job.setSingleInstance(parameters.get("singleInstance") != null ? parameters.get("singleInstance").toString() : "");//添加“限制单实例运行”属性；
        //
        scheduleJobsDAO_HI.save(job);
        //
        jsonResult = SToolUtils.convertResultJSONObj("S", "保存成功!", 1, null);
        jsonResult.put("jobId", job.getJobId());

        return jsonResult.toString();

    }
    
	public String saveJobInfo(JSONObject parameters) {

		com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
		log.info("----------Job（create）--------------" + parameters + "---------------");

		//
		if (parameters.get("jobType") == null || parameters.get("jobType") == "") {
			jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！Job类型（jobType）为必填!", 0, null);
			return jsonResult.toString();
		}

		if (!(parameters.get("jobType").toString().equalsIgnoreCase("java")
				|| parameters.get("jobType").toString().equalsIgnoreCase("webservice")
				|| parameters.get("jobType").toString().equalsIgnoreCase("package"))) {
			jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！jobType必须为‘webservice’、‘java’、‘package’之一", 0,
					null);
			return jsonResult.toString();
		}

		// 如果jobType为 ‘webservice’ 或 ‘java’则， method 字段为必填
		if ((parameters.get("jobType").toString().equalsIgnoreCase("webservice")
				|| parameters.get("jobType").toString().equalsIgnoreCase("java"))
				&& (parameters.get("method") == null || parameters.get("method") == "")) {
			jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！jobType为‘webservice’或‘java’时，method为必填!", 0,
					null);
			return jsonResult.toString();
		}
		if (parameters.get("executableName") == null || parameters.get("executableName") == "") {
			jsonResult = SToolUtils.convertResultJSONObj("E", "编辑失败！可执行（executableName）必填!", 0, null);
			return jsonResult.toString();
		}

		if (parameters.get("jobId") == null || parameters.get("jobId") == "") {
			//
			if (parameters.get("jobName") == null || parameters.get("jobName") == "") {
				jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！jobName为必填!", 0, null);
				return jsonResult.toString();
			}

			if (isJobNameRepeat(parameters)) {
				jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！jobName重复!", 0, null);
				return jsonResult.toString();
			}

			//
			// ViewObject vo =
			// (ViewObject)SToolUtils.context.getBean("saafJobsDAO_HI");
			int userId = Integer.parseInt(parameters.get("varUserId").toString());
			ViewObject vo = this.scheduleJobsDAO_HI;
			ScheduleJobsEntity_HI job = new ScheduleJobsEntity_HI();
			job.setCreationDate(new Date());
			job.setCreatedBy(userId);
			job.setLastUpdateDate(new Date());
			job.setLastUpdatedBy(userId);
			job.setLastUpdateLogin(userId);

			//
			job.setJobName(parameters.get("jobName") != null ? parameters.get("jobName").toString() : "");
			job.setDescription(parameters.get("description") != null ? parameters.get("description").toString() : "");
			job.setExecutableName(
					parameters.get("executableName") != null ? parameters.get("executableName").toString() : "");
			job.setJobType(parameters.get("jobType") != null ? parameters.get("jobType").toString() : "");
			job.setMethod(parameters.get("method") != null ? parameters.get("method").toString() : "");
			job.setOutputFileType(
					parameters.get("outputFileType") != null ? parameters.get("outputFileType").toString() : "");
			job.setSystem(parameters.get("system") != null ? parameters.get("system").toString() : "");
			job.setModule(parameters.get("module") != null ? parameters.get("module").toString() : "");
            job.setSingleInstance(parameters.get("singleInstance") != null ? parameters.get("singleInstance").toString() : "");//添加“限制单实例运行”属性；
			//
			vo.save(job);
			//
			jsonResult = SToolUtils.convertResultJSONObj("S", "保存成功!", 1, job);
			jsonResult.put("jobId", job.getJobId());

			return jsonResult.toString();
		} else {

			// ViewObject vo =
			// (ViewObject)SToolUtils.context.getBean("saafJobsDAO_HI");
			ViewObject vo = this.scheduleJobsDAO_HI;
			ScheduleJobsEntity_HI job = null;
			try {
				job = (ScheduleJobsEntity_HI) vo
						.findByProperty("jobId", Integer.parseInt(parameters.get("jobId").toString())).get(0);
			} catch (Exception ignore) {
				log.error(ignore.getMessage(),ignore);
			}

            if (job == null) {
                jsonResult = SToolUtils.convertResultJSONObj("E", "编辑失败！指定jobId的记录不存在 或 服务异常", 0, null);
                return jsonResult.toString();
            }

			job.setLastUpdateDate(new Date());
			job.setLastUpdateLogin(-1);
			job.setLastUpdatedBy(Integer.parseInt(parameters.get("varUserId").toString()));
            job.setJobName(parameters.get("jobName") != null ? parameters.get("jobName").toString() : "");
            job.setDescription(parameters.get("description") != null ? parameters.get("description").toString() : "");
			job.setExecutableName(
					parameters.get("executableName") != null ? parameters.get("executableName").toString() : "");
			job.setJobType(parameters.get("jobType") != null ? parameters.get("jobType").toString() : "");
			job.setMethod(parameters.get("method") != null ? parameters.get("method").toString() : "");
			job.setOutputFileType(
					parameters.get("outputFileType") != null ? parameters.get("outputFileType").toString() : "");
			job.setSystem(parameters.get("system") != null ? parameters.get("system").toString() : "");
			job.setModule(parameters.get("module") != null ? parameters.get("module").toString() : "");
            job.setSingleInstance(parameters.get("singleInstance") != null ? parameters.get("singleInstance").toString() : "");//添加“限制单实例运行”属性；
			//
			vo.saveOrUpdate(job);

			jsonResult = SToolUtils.convertResultJSONObj("S", "保存成功", 1, job);
			return jsonResult.toString();
		}
	}

    /**
     * 修改并发程序
     * @param parameters
     * @return
     */
    public String updateJob(JSONObject parameters) {
        //前台修改Job时, jobName 并不允许被修改
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        log.info("-----------Job（edit）-------------" + parameters + "---------------");
        if (parameters.get("jobId") == null || parameters.get("jobId") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "编辑失败！jobId为必填!", 0, null);
            return jsonResult.toString();
        }
        //jobName不允许被再次修改
        //        if (parameters.get("jobName") == null || parameters.get("jobName") == "") {
        //            jsonResult = SToolUtils.convertResultJSONObj("E", "编辑失败！jobName为必填!", 0, null);
        //            return jsonResult.toString();
        //        }
        //
        if (parameters.get("executableName") == null || parameters.get("executableName") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "编辑失败！可执行（executableName）必填!", 0, null);
            return jsonResult.toString();
        }
        if (parameters.get("jobType") == null || parameters.get("jobType") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "编辑失败！（jobType）为必填!", 0, null);
            return jsonResult.toString();
        }

        if (!(parameters.get("jobType").toString().equalsIgnoreCase("java") || parameters.get("jobType").toString().equalsIgnoreCase("webservice") ||
              parameters.get("jobType").toString().equalsIgnoreCase("package"))) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！jobType必须为‘webservice’、‘java’、‘package’之一", 0, null);
            return jsonResult.toString();
        }

        //如果jobType为 ‘webservice’ 或 ‘java’则， method 字段为必填
        if ((parameters.get("jobType").toString().equalsIgnoreCase("webservice") || parameters.get("jobType").toString().equalsIgnoreCase("java")) &&
            (parameters.get("method") == null || parameters.get("method") == "")) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "保存失败！jobType为‘webservice’或‘java’时，method为必填!", 0, null);
            return jsonResult.toString();
        }
        //ViewObject vo = this.scheduleJobsDAO_HI;
        ScheduleJobsEntity_HI job = null;
        try {
            job = scheduleJobsDAO_HI.findByProperty("jobId", Integer.parseInt(parameters.get("jobId").toString())).get(0);
        } catch (Exception ignore) {
            log.error(ignore.getMessage(),ignore);
        }
        if (job == null) {
            jsonResult = SToolUtils.convertResultJSONObj("E", "编辑失败！指定jobId的记录不存在 或 服务异常", 0, null);
            return jsonResult.toString();
        }
        job.setLastUpdateDate(new Date());
        job.setLastUpdateLogin(-1);
        job.setLastUpdatedBy(Integer.parseInt(parameters.get("varUserId").toString()));
        job.setDescription(parameters.get("description") != null ? parameters.get("description").toString() : "");
        job.setExecutableName(parameters.get("executableName") != null ? parameters.get("executableName").toString() : "");
        job.setJobType(parameters.get("jobType") != null ? parameters.get("jobType").toString() : "");
        job.setMethod(parameters.get("method") != null ? parameters.get("method").toString() : "");
        job.setOutputFileType(parameters.get("outputFileType") != null ? parameters.get("outputFileType").toString() : "");
        job.setSystem(parameters.get("system") != null ? parameters.get("system").toString() : "");
        job.setModule(parameters.get("module") != null ? parameters.get("module").toString() : "");
        job.setSingleInstance(parameters.get("singleInstance") != null ? parameters.get("singleInstance").toString() : "");//添加“限制单实例运行”属性；
        scheduleJobsDAO_HI.saveOrUpdate(job);
        jsonResult = SToolUtils.convertResultJSONObj("S", "保存成功", 1, null);
        return jsonResult.toString();
    }

    private boolean isJobNameRepeat(JSONObject parameters) {
        //新增 Job 时，检查 jobName 是否重复。 jobName 只可在新增时编辑一次， 修改Job时 不允许编辑 jobName
        log.info("---------Job（isJobNameRepeat）--------------------------");
        List<ScheduleJobsEntity_HI> list = null;
        String where = " where 1=1 ";
        Map<String, Object> map = new HashMap<String, Object>();
        //        ViewObject vo = (ViewObject)SToolUtils.context.getBean("saafJobsDAO_HI");
        //ViewObject vo = this.scheduleJobsDAO_HI;
        where = where + " and jobName = :varJobName";
        map.put("varJobName", parameters.get("jobName"));
        list = scheduleJobsDAO_HI.findList("from " + scheduleJobsDAO_HI.getEntityClass().getSimpleName() + where, map);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

    public String deleteJob(JSONObject parameters) {
        com.alibaba.fastjson.JSONObject jsonResult = new com.alibaba.fastjson.JSONObject();
        if (parameters.get("jobId") == null || parameters.get("jobId") == "") {
            jsonResult = SToolUtils.convertResultJSONObj("E", "删除失败！未指定jobId", 0, null);
            return jsonResult.toString();
        }
        ScheduleJobsEntity_HI row = scheduleJobsDAO_HI.getById(Integer.parseInt(parameters.get("jobId").toString()));
        if (row != null) {
            scheduleJobsDAO_HI.delete(row);
        }
        jsonResult = SToolUtils.convertResultJSONObj("S", "删除成功!", 1, "");
        return jsonResult.toString();
    }

}
