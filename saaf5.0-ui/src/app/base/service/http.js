/**
 * Created by houxingzhang on 2018-01-05.
 *    // SAAF私有 http 服 务
 */
'use strict';
define(['angular',"../../processform/showProcessService"], function (angular, module) {

    // var module = angular.module('http.Service', []);
    module
    /*角色保存或修改 */
        .service('roleSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.roleSave, params, callBack, errCallBack)
            }
        })
        /*删除角色*/
        .service('roleDelete', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.roleDelete, params, callBack, errCallBack)
            }
        })

        /* 保存用户职责（用户分配到职责） */
        .service('userRespSave', function (httpServer, URLAPI, SIEJS) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.userRespSave, params, callBack, errCallBack)
            }
        })
        // 用户职责列表
        .service('userRespList', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.userRespList, params, callBack, errCallBack);
            }
        })
        // 保存职责
        .service('baseResponsibilitySave', function (httpServer, URLAPI, SIEJS) {
            return function (p, success, error) {
                httpServer.save(URLAPI.baseResponsibilitySave, p, success, error)
            }
        })

        // 删除职责
        .service('responsibilityDelete', function (httpServer, URLAPI, SIEJS) {
            return function (params, success, error) {
                httpServer.remove(URLAPI.responsibilityDelete, params, function (res) {
                    success(res);
                }, error)
            }
        })

        // sso 保存职责
        .service('ssoRespSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.remove(URLAPI.ssoRespSave, params, callBack, errCallBack)
            }
        })
        // 查询职责
        .service('responsibility', function (httpServer, URLAPI) {
            return function (params, callBack, err) {
                httpServer.post(URLAPI.responsibility, params, callBack, err);
            }
        })
        // 获取分配的机构
        .service('findListAssign', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.findListAssign, params, callBack, errCallBack)
            }
        })
        // 获取用户分配的机构
        .service('findPubUsersOrgInfo', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.findPubUsersOrg, params, callBack, errCallBack)
            }
        })
        // 获取机构分配的板块
        .service('findListOrgInst', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.findListOrgInst, params, callBack, errCallBack)
            }
        })

        // 根据职责Id查询职责
        .service('responsibilityById', function (httpServer, URLAPI) {
            return function (params, callBack, err) {
                httpServer.post(URLAPI.responsibilityById, params, callBack, err);
            }
        })

        // 查询profile里的ＳＱＬ值
        .service('findProfileSqlDatas', function (httpServer, URLAPI) {
            return function (params, callBack, err) {
                httpServer.post(URLAPI.findProfileSqlDatas, params, callBack, err);
            }
        })

        // 删除组织架构
        .service('orgDelete', function (httpServer, URLAPI) {
            return function (params, success, error) {
                httpServer.remove(URLAPI.orgDelete, params, success, error)
            }
        })

        // 保存组织架构 organizationSave
        .service('organizationSave', function (httpServer, URLAPI) {
            return function (params, success, error) {
                httpServer.save(URLAPI.organizationSave, params, success, error)
            }
        })

        // 删除组织架构里人员
        .service('orgPersonDelete', function (httpServer, URLAPI) {
            return function (params, success, error) {
                httpServer.remove(URLAPI.orgPersonDelete, params, success, error)
            }
        })

        // 保存职位 positionSave
        .service('positionSave', function (httpServer, URLAPI) {
            return function (params, success, error) {
                httpServer.save(URLAPI.positionSave, params, success, error)
            }
        })
        // 删除职位
        .service('positionDel', function (httpServer, URLAPI) {
            return function (params, success, error) {
                httpServer.remove(URLAPI.positionDel, params, success, error)
            }
        })
        // 保存组织或职位与多个员工关系
        .service('orgPersonSave', function (httpServer, URLAPI) {
            return function (params, success, error) {
                httpServer.save(URLAPI.orgPersonSave, params, success, error)
            }
        })


        .service('buttonList', function (httpServer, URLAPI) {
            return function (params, success, error) {
                var p = {
                    params: JSON.stringify(params),
                    pageIndex: 1,
                    pageRows: 1000
                }
                httpServer.post(URLAPI.buttonList, p, success, error)
            }
        })
        /* -----------------------------------------------------------------------------------------------------------------------------  */


        /*删除按钮列表*/
        .service('buttonSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.buttonSave, params, callBack, errCallBack)
            }
        })
        /*保存按钮列表*/
        .service('buttonDetel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.buttonDetel, params, callBack, errCallBack)
            }
        })
        /*微信公总号*/
        .service('wechatFind', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.wechatFind, params, callBack, errCallBack)
            }
        })
        .service('wechatSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.wechatSave, params, callBack, errCallBack)
            }
        })
        .service('wechatDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.wechatDel, params, callBack, errCallBack)
            }
        })


        /*查询用户中心*/
        .service('userFind', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.userFind, params, callBack, errCallBack)
            }
        })
        /*用户中心保存职责*/
        .service('responsibilitySave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.responsibilitySave, params, callBack, errCallBack)
            }
        })
        /*用户中心保存职责*/
        .service('saveUserResp', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.saveUserResp, params, callBack, errCallBack)
            }
        })
        /*用户保存*/
        .service('userSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.userSave, params, callBack, errCallBack)
            }
        })
        /*用户查询详情*/
        .service('userFindById', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.userFindById, params, callBack, errCallBack)
            }
        })
        /*用户删除*/
        .service('userDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.userDel, params, callBack, errCallBack)
            }
        })

        /*查询用户与员工信息*/
        .service('findBaseUsersJoinPersonPagination', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.findBaseUsersJoinPersonPagination, params, callBack, errCallBack)
            }
        })

        /*删除用户职责*/
        .service('deleteUserResp', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.remove(URLAPI.deleteUserResp, params, callBack, errCallBack)
            }
        })
        /*查询员工*/
        .service('personFind', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.personFind, params, callBack, errCallBack)
            }
        })
        /*保存员工*/
        .service('personSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.personSave, params, callBack, errCallBack)
            }
        })
        /*删除员工*/
        .service('personDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.personDel, params, callBack, errCallBack)
            }
        })
        /*保存profile*/
        .service('baseProfileSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.baseProfileSave, params, callBack, errCallBack)
            }
        })
        /*删除profile*/
        .service('baseProfileDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.baseProfileDel, params, callBack, errCallBack)
            }
        })
        /*删除资源*/
        .service('resourceDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.resourceDel, params, callBack, errCallBack)
            }
        })
        /*保存资源*/
        .service('resourceSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.resourceSave, params, callBack, errCallBack)
            }
        })
        /*修改密码*/
        .service('changePassword', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.changePassword, params, callBack, errCallBack)
            }
        })
        /*sso获取排序*/
        .service('getOrderNo', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.getOrderNo, params, callBack, errCallBack)
            }
        })

        // 动态报表 图表类型保存
        .service('dynamicReportTypeSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.dynamicReportTypeSave, params, callBack, errCallBack)
            }
        })
        // 动态报表 图表类型 删除
        .service('dynamicReportTypeDelete', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.remove(URLAPI.dynamicReportTypeDelete, params, callBack, errCallBack)
            }
        })
        // 收藏夹保存
        .service('collectSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.collectSave, params, callBack, errCallBack)
            }
        })
        // 收藏夹保存
        .service('collectDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.collectDel, params, callBack, errCallBack)
            }
        })

        // 获取菜单列表与按钮
        .service('menuListByButton', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.menuListByButton, params, callBack, errCallBack)
            }
        })

        /*   消息中心 start */
        // 消息源保存
        .service('msgSourceCfgSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.msgSourceCfgSave, params, callBack, errCallBack)
            }
        })
        // 消息源删除
        .service('msgSourceCfgDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.msgSourceCfgDel, params, callBack, errCallBack)
            }
        })
        // 模版保存
        .service('msgTempleCfgSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.msgTempleCfgSave, params, callBack, errCallBack)
            }
        })
        // 模版删除
        .service('msgTempleCfgDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.msgTempleCfgDel, params, callBack, errCallBack)
            }
        })
        // 配置保存
        .service('msgCfgSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.msgCfgSave, params, callBack, errCallBack)
            }
        })
        // 配置删除
        .service('msgCfgDelete', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.msgCfgDel, params, callBack, errCallBack)
            }
        })
        // 实例删除
        .service('msgInstanceDelete', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.msgInstanceDelete, params, callBack, errCallBack)
            }
        })
        // 历史删除
        .service('msgHistoryDelete', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.msgHistoryDelete, params, callBack, errCallBack)
            }
        })
        // 消息中心消息接收对象  删除
        .service('msgReceiveDelete', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.msgReceiveDelete, params, callBack, errCallBack)
            }
        })
        // 消息中心消息接收对象  新增修改
        .service('msgReceiveSaveOrUpdate', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.msgReceiveSaveOrUpdate, params, callBack, errCallBack)
            }
        })
        // 消息中心消息退订删除
        .service('msgTdDelete', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.msgTdDelete, params, callBack, errCallBack)
            }
        })
        // 消息中心用户 新增修改
        .service('msgUserSaveOrUpdate', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.msgUserSave, params, callBack, errCallBack)
            }
        })
        // 消息中心消息退订删除
        .service('msgUserDelete', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.msgUserDel, params, callBack, errCallBack)
            }
        })
        // 消息中心日志删除
        .service('msgLogDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.msgLogDel, params, callBack, errCallBack)
            }
        })
        /* end*/

        // 获取基础菜单列表
        .service('menuList', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.menuList, params, callBack, errCallBack)
            }
        })


        // 保存文章
        .service('articleSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.articleSave, params, callBack, errCallBack)
            }
        })
        // 删除文章
        .service('articleDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.remove(URLAPI.articleDel, params, callBack, errCallBack)
            }
        })
        // 删除轮播图
        .service('carouseDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.remove(URLAPI.carouseDel, params, callBack, errCallBack)
            }
        })

        // 删除行快码
        .service('deleteLookupLine', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.deleteLookupLine, params, callBack, errCallBack)
            }
        })


        // 保存redis
        .service('redisSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.redisSave, params, callBack, errCallBack)
            }
        })
        // 删除redis
        .service('redisDel', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.redisDel, params, callBack, errCallBack)
            }
        })
        // 调度错误详情
        .service('schedulingErrorDetail', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.schedulingErrorDetail, params, callBack, errCallBack)
            }
        })
        // 发布流程
        .service('processDeploy', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.processDeploy, params, callBack, errCallBack)
            }
        })
        // 流程分类保存、新增
        .service('processCategoriesSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.processCategoriesSave, params, callBack, errCallBack)
            }
        })

        // 流程分类删除 processCategoriesDelete

        .service('processCategoriesDelete', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.remove(URLAPI.processCategoriesDelete, params, callBack, errCallBack)
            }
        })
        // 流程模型 新增、修改 processModelsSave
        .service('processModelsSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.processModelsSave, params, callBack, errCallBack)
            }
        })
        // 流程角色查询
        .service('processFindRoles', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.processFindRoles, params, callBack, errCallBack)
            }
        })
        // 流程业务显示信息
        .service('processGetProcesser', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.processGetProcesser, params, callBack, errCallBack)
            }
        })

        // 流程设置 保存  //processTaskConfigSave

        .service('processTaskConfigSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.processTaskConfigSave, params, callBack, errCallBack)
            }
        })

        //流程挂起 processSuspend
        .service('processSuspend', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.processSuspend, params, callBack, errCallBack)
            }
        })
        //流程激活  processActivate
        .service('processActivate', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.processActivate, params, callBack, errCallBack)
            }
        })
        /*删除角色*/
        .service('requestDeleteMulti', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.remove(URLAPI.requestDeleteMulti, params, callBack, errCallBack)
            }
        })

        /* 流程设计服务 -------------------------------------------------------------------------------------------------- */
        //  	选人任务节点查询(发起/任务办理)
        .service('processFindAssigneeUserTasks', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.processFindAssigneeUserTasks, params, callBack, errCallBack)
            }
        })
        // 任务详细信息查询
        .service('processTaskDetail', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.processTaskDetail, params, callBack, errCallBack)
            }
        })

        // processGetStartUrl 获取流程表单跳转页面
        .service('processGetStartUrl', function (httpServer, URLAPI,$location,SIEJS,pageResp) {
            return function (params, callBack, errCallBack) {

                // var resp = JSON.parse(localStorage[appName + '_currentResp']);
                var resp = pageResp.get();
                var respId =null;
                    //  var respId =  $location.search().respId || resp.responsibilityId;
                /*if (!resp) {
                 SIEJS.alert('获取当前职责失败','error');
                 return
                 }
                 if (!resp.orgBean) {
                 SIEJS.alert('当前职责没有配置BU','error');
                 return
                 }
                 if (resp.positionList.length===0) {
                 SIEJS.confirm('当前职责没有配置职位信息','将无法获取当前用户完整信息','继续发起',ajax);
                 }else{
                 ajax();
                 }*/
                function ajax() {
                    var p= params.params;
                    if (typeof p ==='string') {
                        p=JSON.parse(p);
                    }
                    p.responsibilityId= respId;
                    params = {
                        params:JSON.stringify(p)
                    };
                    httpServer.post(URLAPI.processGetStartUrl, params, callBack, errCallBack)
                }

                ajax();
            }
        })

        // processGetStartUrl 任务办理表单地址查询
        .service('processGetTaskUrl', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.processGetTaskUrl, params, callBack, errCallBack)
            }
        })
        // processSave  发起流程 - 保存到草稿
        .service('processSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.processSave, params, callBack, errCallBack)
            }
        })

        // 发起流程 - 提交
        .service('processStart', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.processStart, params, callBack, errCallBack)
            }
        })

        // processTaskComplete 任务办理
        .service('processTaskComplete', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.processTaskComplete, params, callBack, errCallBack)
            }
        })

        // 流程任务代办 (委托审批) 批量添加
        .service('processEntrustApproval', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.save(URLAPI.processEntrustApproval, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })

        // 流程任务批量办理
        .service('processBatchComplete', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.save(URLAPI.processBatchComplete, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })

        // 获取 任务节点列表
        .service('processFindTaskConfig', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.processFindTaskConfig, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })

        // 发起沟通、催办
        .service('processCommunicateSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.processCommunicateSave, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })

        // 添加助审任务节点
        .service('processAddSubTask', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.processAddSubTask, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })

        //  流程撤回
        .service('processRevoke', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.processRevoke, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })
        // 查询当前流程是否可以撤回　１可以撤回、０不可
        .service('processRevokeStatus', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack,showLoading) {
                if (!params)return;
                httpServer.post(URLAPI.processRevokeStatus, {params: JSON.stringify(params)}, callBack, errCallBack,showLoading)
            }
        })
        //  当前活动的任务查询
        .service('processFindActiveTasks', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.processFindActiveTasks, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })

        // 任务节点办理人查询
        .service('processFindTaskUsers', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.processFindTaskUsers, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })

        // 流程待阅 状态更新
        .service('processNotifyTaskStatus', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.processNotifyTaskStatus, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })

        // 任务代办设置保存
        .service('processDelegateConfigSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.save(URLAPI.processDelegateConfigSave, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })
        //  任务代办设置状态更新
        .service('processDelegateConfigStatus', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.save(URLAPI.processDelegateConfigStatus, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })

        // processFindTaskNodes
        .service('processFindTaskNodes', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.processFindTaskNodes, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })

        // 催办设置保存
        .service('processTaskUrgeConfigSave', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.save(URLAPI.processTaskUrgeConfigSave, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })
        // 催办设置 更新状态
        .service('processTaskUrgeConfigUpdate', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.save(URLAPI.processTaskUrgeConfigUpdate, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })

        // 流程 终止 processStop
        .service('processStop', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.processStop, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })
        // 流程任务转办
        .service('processTaskTransfer', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                if (!params) return;
                httpServer.post(URLAPI.processTaskTransfer, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })
        //  查询已定义的流程详细信息
        .service('processModelDetail', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.processModelDetail, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })
        .service('samProcessImport', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.samProcessImport, params, callBack, errCallBack)
            }
        })
        .service('samProcessCopy', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.samProcessCopy, params, callBack, errCallBack)
            }
        })

        .service('samUpdateRoleProcessers', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.samUpdateRoleProcessers, params, callBack, errCallBack)
            }
        })
        .service('samUpdateTaskConfigProcessers', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.save(URLAPI.samUpdateTaskConfigProcessers, params, callBack, errCallBack)
            }
        })


        .service('buttonList', function (httpServer, URLAPI) {
            return function (params, success, error) {
                var p = {
                    params: JSON.stringify(params),
                    pageIndex: 1,
                    pageRows: 1000
                }
                httpServer.post(URLAPI.buttonList, p, success, error)
            }
        })

        /* ----------------------------------------IMS INV BEGIN  库存事物模块-------------------------------------------------------------------------------------  */

        //保存事物来源类型
        .service('saveMtlTxnSourceTypes', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.saveMtlTxnSourceTypes, params, callBack, errCallBack)
            }
        })
        //删除事物来源类型
        .service('delMtlTxnSourceTypes', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.delMtlTxnSourceTypes, params, callBack, errCallBack)
            }
        })
        //查询库存事物类型
        .service('findMtlTransactionTypes', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.findMtlTransactionTypes, params, callBack, errCallBack)
            }
        })
        //保存库存事物类型
        .service('saveMtlTransactionTypes', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.saveMtlTransactionTypes, params, callBack, errCallBack)
            }
        })
        //删除库存事物类型
        .service('delMtlTransactionTypes', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.delMtlTransactionTypes, params, callBack, errCallBack)
            }
        })
        /*gjc start*/
        .service('saveUserGroupAssignsForUser', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.saveUserGroupAssignsForUser, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })
        .service('deleteUserGroupAssignsForGroup', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.deleteUserGroupAssignsForGroup, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })
        .service('deleteUserGroupAssignsForUser', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.deleteUserGroupAssignsForUser, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })
        .service('saveUserGroupAssignsForGroup', function (httpServer, URLAPI) {
            return function (params, callBack, errCallBack) {
                httpServer.post(URLAPI.saveUserGroupAssignsForGroup, {params: JSON.stringify(params)}, callBack, errCallBack)
            }
        })
    /*gjc end*/
    /* ----------------------------------------IMS INV END  库存事物模块-------------------------------------------------------------------------------------  */


    /*  .factory('testWebSocket', function($websocket) {
     // Open a WebSocket connection
     var dataStream = $websocket('ws://123.207.167.163:9010/ajaxchattest');
     var collection = [];
     dataStream.onMessage(function(message) {
     if (message.data) collection.push(message.data);
     });

     var methods = {
     collection: collection,
     get: function(params) {
     dataStream.send(params);
     }
     };
     return methods;
     })*/
    return module;

});