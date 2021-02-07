/**
 * Created by Administrator on 2018/5/25.
 */
'use strict';


define(['app', 'GooFlow'], function (app) {
    app.controller('showProcessCtrl', function ($scope, $timeout, $location, $rootScope, $state, $stateParams, SIEJS, URLAPI, httpServer,
                                                processFindAssigneeUserTasks, processTaskDetail, processSave, processStart, processTaskComplete, userInfo,
                                                processFindActiveTasks, processFindTaskUsers, saveTabToStorage, processHistoricOpinions, pageResp, processRevoke,
                                                processFindTaskNodes, processCommunicateSave, arrayUnique, processAddSubTask, $filter, processRevokeStatus) {
        $scope.userData = JSON.parse(localStorage.getItem(appName + '_successLoginInfo'));
        $scope.positionList = JSON.parse(localStorage.getItem(appName + '_successLoginInfo')).positionList;
        $scope.isShowFlag = $scope.userData.userType == '45' ? 1 : 0;

        $scope.showParams = {};
        $scope.urgeParams = {};
        /*   $scope.orgId = window.parent.saafrootScope.orgId;  //  注释 2018-9-17 hxz
           debugger;*/
        // $scope.errorNum = 0; // 请求错误次数
        $scope.addParams = {};
        $scope.approval = {};
        // 任务审核的参数 **************************************************
        $scope.approvalParams = {
            properties: {
                menucode:$location.$$search.menucode
            },
            variables: [],
            responsibilityId :$location.$$search.respId,
            respId :$scope.respId
        };

        $scope.extend = {};
        $scope.processImageParams = {};// 流程图参数
        $scope.showRepsChange = $location.$$search.from==='portal' && $location.$$search.isStart==='true';
        var url = decodeURIComponent($stateParams.url);
        $scope.isApproval = true; // 默认 保存 保存草搞
        //$scope.formUrl = 'app/' + window.appName.toLocaleLowerCase() + '/templates/processDesigner/formHtml/' + url;// 表单页面地址
        //var processDefinitionKey = url.split('?')[1].split('=')[1];
        var processDefinitionKey = $location.$$search.processDefinitionKey;
        var listId = $location.$$search.listId; //  有值侧为草稿
        var taskId = $location.$$search.taskId;
        var businessKey = $location.$$search.businessKey; // 流程与业务关联的key
        var menuId = $location.$$search.id;
        var formType = $location.$$search.type; //readonly
        var delegateId = $location.$$search.delegateId;
        $scope.respId = $location.$$search.respId;
        $scope.entrance = $location.$$search.entrance; // 是否是外部入口进来的，如邮件链接点进来
        ////console.log('taskId:'+taskId)
      //  //console.log('formType:'+formType)
        $scope.formType = $location.$$search.type;
        $scope.action = $location.$$search.action; // 当前表单的状态    new \ edit \ detail \ approval \  \readonly只读
        console.log('action:' + $scope.action);
        $scope.findData = false;
        //子路由参数
        $scope.childData = {
            //控制子页面按钮显示和不显示
            isEdit: true
        };

        // 流程发起的参数    ***************************************
        $scope.params = {
            extend: {
                tasks_assignees: []
            },
            variables: [],
            responsibilityId :$scope.respId,
            respId :$scope.respId,
            properties: {
                menucode:$location.$$search.menucode
            }
        };

        // 所有表单的对象 ，在设计表单的时候请使用这个对象参数进行设置设计  ***************************************
        $scope.formParams = {
            personName: '', //人员名称
            personId: '', //人员id
            userId: $scope.userData.userId,
            deptName: '', //部门名称,
            deptId: '', //部门id,
            jobId: '', //职务Id,
            jobName: '', //职务名称,
            positionId: '', //职位Id,
            positionName: '', //职位名称,
            responsibilityId :$scope.respId,
            respId :$scope.respId,
            processDefinitionKey: processDefinitionKey // 添加流程标识
        };


        $scope.businessFormParams = {};  // 在新发起流程时，保存或草稿的时候，业务保存成功后返回的结果　*********************


        // 获取业务表单详情接口的参数  *************************************** 2018-8-30
        $scope.apiDetailParams = {
            id: businessKey
        };
        // 储存页面对象，进行子级页面初始化等操作   *************************************** 2018-8-30
        $scope.pageView = {
            init: function () {
            },
            getBusinessFormCallback: function () {
                // 获取业务表单成功后的回调
            },
            form: function () {
                //表单封禁对外提供方法--ly
            },
            action: $scope.action
        };

        // 流程实例的参数
        /***1：当processInstanceId无值时 =-1000用于获取历史记录不报错，改为无值时不去查历史记录。
         *
         * 2：当单据是草稿processInstanceId无值时 =-1000，会导致查不到流程图***/
        $scope.processInstanceParams = {
            processInstanceId: $location.$$search.processInstanceId || ''
        };


        if (businessKey) {
            $scope.formParams.id = businessKey; // 表单修改标识
        }
        if (listId) {
            $scope.params.listId = listId; // 流程修改标识
        }
        //拼接流程视图Src
        $scope.getTaskImage = function () {
            var s;
            if ($location.$$search.processInstanceId) { // 流程进行中
                //if ($scope.action === 'detail') { // 流程进行中
                s = encodeURIComponent('{"processInstanceId":"' + $location.$$search.processInstanceId + '","type":"image"}');
            } else {
                s = encodeURIComponent('{"processDefinitionId":"' + $location.$$search.processDefinitionId + '","type":"image"}');
            }
            $scope.imgSrc = URLAPI.processExport + '?params=' + s;
        }();

        $scope.btnModify = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');

            }
        };
        $scope.btnDel = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');

            }
        };


        /**  保存到草稿及提交 / 驳回后保存及提交审批
         * saveonly 是否为草稿
         * txt  按钮名 /事件状态/ 弹窗提示标题
         *  outerApi 其它api 配置
         * */
        $scope.saveAndDraft = function (saveonly, txt, outerApi) {

            $scope.approvalStatus = txt || $scope.approvalStatus;
            var apiSave;
            var domApiName;
            if (!$scope.apiDetailParams.id) { // 新增
                domApiName = 'data-api-post';
            } else {
                domApiName = 'data-api-update';
            }
            apiSave = $('[data-api-post]').attr(domApiName);
            if (!apiSave || !URLAPI[apiSave]) {
                SIEJS.alert('找不到此表单对应的服务', 'error', '确定', '请检查表单页面 [' + domApiName + '] 值 ');
                return;
            }
            var url = URLAPI[apiSave];
            if (outerApi) {
                url = URLAPI[outerApi];
            }
            //  处理变量
            if ($scope.variables) {
                $scope.variables.map(function (item) {
                    item.value = item.name ? $scope.formParams[item.name] : '';
                });
            }
            // 资产调剂参数配置 start    [ LIJIAYAOTODO 20181108 临时使用 ]
            if(url.indexOf('/oaServer/oaAssetAdjustService/saveOrUpdateAdjustApply') != -1){
                $scope.variables = [{name:"adjustDepartmentId",type:"long",value:$scope.formParams.items[0].oldHrOrganizationId}]
            }

            var isSupplementRequest = false;
            if (url.indexOf('/ttaServer/ttaSaStdHeaderService/saveOrUpdate') !== -1){
                isSupplementRequest = true;
            }
            $scope.saveRequestParams = {};
            //补充协议
            if (isSupplementRequest) {
                $scope.saveRequestParams.supplementAgreementHead = $scope.formParams;
                $scope.saveRequestParams.dynamicFieldlDataList = $rootScope.dynamicFieldlDataList;
            }

            // 资产调剂参数配置 end
            $scope.params.variables = angular.copy($scope.variables);
            $scope.params.processDefinitionKey = processDefinitionKey;
            $scope.params.saveonly = saveonly;
// debugger;

            $scope.formParams.editFlag = saveonly ? 'Y' : 'N'; //  2018-8-28   [ OA-255 ]

            // 保存到业务 并获取对应的businessKey
            httpServer.post(url, {
                    params: JSON.stringify(isSupplementRequest === true ? $scope.saveRequestParams : $scope.formParams)
                },
                function (res) {
                    if (res.status === 'S') {

                        $scope.businessFormParams = res.data;  // 业务保存成功后返回的结果

                        $scope.formParams.id = res.data.id; //  / 返回id
                        $scope.params.businessKey = res.data.id;
                        $scope.params.title = res.data.title;
                        $scope.params.positionId = res.data.positionId;
                        $scope.params.orgId = res.data.orgId;
                        $scope.params.userType = res.data.userType;
                        $scope.params.billNo = res.data.billNo;


                        $scope.approvalParams.id = res.data.id; //  / 返回id
                        $scope.approvalParams.title = res.data.title;
                        $scope.approvalParams.positionId = res.data.positionId;
                        $scope.approvalParams.orgId = res.data.orgId;
                        $scope.approvalParams.userType = res.data.userType;
                        $scope.approvalParams.billNo = res.data.billNo;



                        switch ($scope.pageView.action) {
                            case 'refusal': // 被驳回的表单
                                $scope.processComplete(saveonly);
                                break;
                            case 'new':
                            case 'draft':
                                $scope.processSubmit(); // 开始流程
                                break;
                        }
                    } else {
                        if (res.data && res.data.billNo && $scope.pageView.action === 'new') {
                            // 当提交失败的时候　，且返回billNo　则把当前新发起的状态转为草稿
                            $scope.pageView.action = $scope.action = 'draft';
                            $scope.formParams.billNo = res.data.billNo;
                        }
                        SIEJS.alert($scope.approvalStatus + '失败', 'error', '确定', res.msg);
                    }

                })

        };

        // 开始流程
        $scope.processSubmit = function () {
            processStart({
                params: JSON.stringify($scope.params),
                __errorRepeated: 3 // 当请求错误的时候，重新请求3次
            }, function (result) {
                if ($scope.params.saveonly) {
                    SIEJS.alert($scope.approvalStatus + '成功');
                    if (!businessKey) {
                        businessKey = $scope.params.businessKey;
                        $scope.apiDetailParams.id = businessKey;
                        $scope.getBusinessFormParams();// 重新获取 表单数据。目的为了新发起流程后 获取 businessKey 对应的 业务id
                    }
                } else {
                    $scope.closeTab($scope.approvalStatus + '成功');
                }
            })
        };

        //   进行审批、驳回、等 任务办理
        $scope.processComplete = function (saveonly) {
            if ($scope.formArea.proposalForm && $scope.formArea.proposalForm.isSkipApprove) {
                var vIsSkipApprove = $scope.formArea.proposalForm.isSkipApprove.$modelValue;
                var vNewOrExisting = $scope.formArea.proposalForm.newOrExisting.$modelValue;
                var vPropcessFcsPurchse = $scope.formArea.proposalForm.propcessFcsPurchse.$modelValue;
                var vStartUserId = $scope.formArea.proposalForm.startUserId.$modelValue;
                var vMajorDeptCode  = $scope.formArea.proposalForm.majorDeptCode.$modelValue;
                if (!vNewOrExisting || !vPropcessFcsPurchse || !vStartUserId) {
                    SIEJS.alert('必传流程参数缺失，请IT协助检查！', "error", "确定");
                    return;
                }
                $scope.approvalParams.variables.push({
                    name: 'isSkipApprove',
                    type: 'string',
                    value:   vIsSkipApprove == 'Y' ? 'Y' : 'N'
                });
                
                $scope.approvalParams.variables.push({
                    name: 'isNew',
                    type: 'string',
                    value: vNewOrExisting
                });

                $scope.approvalParams.variables.push({
                    name: 'fcsPurchse',
                    type: 'double',
                    value: vPropcessFcsPurchse
                });

                $scope.approvalParams.variables.push({
                    name: 'startUserId',
                    type: 'int',
                    value: vStartUserId
                });

                $scope.approvalParams.variables.push({
                    name: 'majorDeptCode',
                    type: 'string',
                    value: vMajorDeptCode
                });


                console.log(vIsSkipApprove + ":" + vNewOrExisting + ":" + vPropcessFcsPurchse);
                
                 //#流程过程中，BIC 节点生成pdf文件start
                if ($scope.userData.userType == '45') {
                    console.log("proposal 生成pdf文件!");
                    $rootScope.printPdfAll('1');
                }
                 //#流程过程中，BIC 节点生成pdf文件end
                 
            } else if ($scope.formArea.protocolForm && $scope.formArea.protocolForm.isSkipApprove) {//独家和补充协议表单
                var vIsSkipApprove = $scope.formArea.protocolForm.isSkipApprove.$modelValue;
                /*
                var vStartUserId = $scope.formArea.protocolForm.startUserId.$modelValue;
                $scope.approvalParams.variables.push({
                    name: 'startUserId',
                    type: 'int',
                    value: vStartUserId
                });
                */
                $scope.approvalParams.variables.push({
                    name: 'isSkipApprove',
                    type: 'string',
                    value:   vIsSkipApprove == 'Y' ? 'Y' : 'N'
                });
            }
            processTaskComplete({
                params: JSON.stringify($scope.approvalParams)
            }, function (res) {
                if (res.status === 'S') {
                    if (saveonly) {
                        SIEJS.alert($scope.approvalStatus + '成功', 'success');
                    } else {
                        $scope.closeTab($scope.approvalStatus + '成功');
                    }
                    //更新proposal 是否指定GM审批状态
                    // var vProposalId = $scope.formParams.proposalId;
                    // httpServer.post(URLAPI.updateSkipStatus, {
                    //     'params': JSON.stringify({proposalId : vProposalId, isSkipApprove : vIsSkipApprove})
                    // }, function (res) {
                    //
                    // }, function (err) {
                    //     SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    // });
                } else {

                    SIEJS.alert($scope.approvalStatus + '失败', 'error', '确定', res.msg);
                }
            });
        };


        //   新流程草稿与提交事件
        /*  saveonly 是否为草稿
         * txt  按钮名 /事件状态/ 弹窗提示标题
         *  outerApi 其它api 配置
        * */
        function CreateSaveAndDraftEvent(saveonly, txt, outerApi) {
            this.click = function () {
                // 自定验证  用于表单页面在提交之前预计验证其有效性，cusValidator()必须返回 true 的时候才能往下执行
                if (this.cusValidator && !this.cusValidator()) {
                    console.warn('页面定义了cusValidator,请注意进行return 处理');
                    return;
                }
                 //   2018-10-30 注释，此处很可能 造成beforFunction 循环调用。
              /* if (this.beforFunction && !this.beforFunction()) {
                    console.warn('页面定义了beforFunction,请注意进行return 处理');
                    return;
                }*/
                console.log($scope.formParams);
                //debugger;
                $scope.saveAndDraft(saveonly, txt, outerApi);
            }
        }
        //  按钮定义
        // 提交流程 * *****************************************
        /* 若需要在提交之前再进行其它的业务判断请在业务表单页面进行 $scope.submitEvent.cusValidator()
            必须要返回 true 或 false
        */
        $scope.submitEvent = new CreateSaveAndDraftEvent(false, '提交');
        // 当前页面的提交按钮使用事件
        $scope.btnSubmit = function () {
            $scope.submitEvent.click();
        };

        // 保存草稿 ********************************************
        /* 若需要在提交之前再进行其它的业务判断请在业务表单页面进行 $scope.draftEvent.cusValidator()
            必须要返回 true 或 false
        */
        $scope.draftEvent = new CreateSaveAndDraftEvent(true, '保存草稿');

        // 保存草稿按钮
        $scope.btnDraft = function () {
            $scope.draftEvent.click();
        };


        //   审核功能事件
        function CreateApprovalEvent(option, saveonly, status) {
            this.click = function () {
                // 自定验证  用于表单页面在提交之前预计验证其有效性，cusValidator()必须返回 true 的时候才能往下执行
                if (this.cusValidator && !this.cusValidator()) {
                    console.warn('页面定义了cusValidator,请注意进行return 处理');
                    return;
                }
                  // 2018-10-30 注释，此处很可能 造成beforFunction 循环调用。
             /*  if (this.beforFunction && !this.beforFunction()) {
                    console.warn('页面定义了beforFunction,请注意进行return 处理');
                    return;
                }*/
                $scope.approvalEvent(option, saveonly, status)
            }
        }

        //  驳回后    保存按钮
        $scope.btnSave = function () {
            $scope.saveEvent.click();
        };

        $scope.saveEvent = new CreateApprovalEvent('Y', true, '保存');

        //  驳回后 提交审批按钮
        $scope.btnApproval = function () {
            if ($scope.isRetrial) { // 驳回重审的
                $scope.showBtnApproval('Y', false, '审批');
            } else {
                $scope.submitApprovalEvent.click();
            }
        };
        $scope.submitApprovalEvent = new CreateApprovalEvent('Y', false, '提交审批');


        // 通过（审批）按钮
        $scope.btnPass = function () {
            console.log('我在这里');
            if ($scope.passEvent.beforFunction && typeof $scope.passEvent.beforFunction === 'function') {
                $scope.passEvent.beforFunction(); //前置api校验，函数在业务页面自定义
            } else {
                $scope.showBtnApproval('Y', false, '审批');
                // debugger;
            }

        };
        // 通过审批事件
        $scope.passEvent = new CreateApprovalEvent();


        // 驳回按钮
        $scope.btnRefusal = function () {
            $scope.addParams.opinion = '驳回';
            $scope.showBtnApproval('RJ', false, '驳回');
        };
        $scope.refusalEvent = new CreateApprovalEvent();


        // 驳回复审
        $scope.btnRefusalApproval = function () {
            $scope.showBtnApproval('RT', false, '驳回重审');
        };
        $scope.refusaApprovallEvent = new CreateApprovalEvent();

        // 发送信息
        $scope.btnSendMessage = function () {
            $scope.showBtnApproval('-1', false, '发消息');
        };
        $scope.sendMessageEvent = new CreateApprovalEvent();

        // 增加助审
        $scope.btnHelpReview = function () {
            $scope.showBtnApproval('-1', false, '增加助审');
            var _userResp = pageResp.get();
            $scope.lovUserFindParams = {
                orgId:_userResp.orgBean.orgId
            }
        };
        $scope.helpReviewEvent = new CreateApprovalEvent();


        // 审批功能弹窗 确定 按钮  *************************************** 2018-8-31
        $scope.btnApprovalEvent = function () {
          switch ($scope.approvalStatus) {

                case '通过':
                case '审批':
                    $scope.passEvent.click();
                    break;
                case '驳回':
                    $scope.refusalEvent.click();
                    break;
                case '驳回重审':
                    $scope.refusaApprovallEvent.click();
                    break;
                case '发消息':
                    $scope.sendMessageEvent.click();
                    break;
                case '增加助审':
                    $scope.helpReviewEvent.click();
                    break;
            }
        };


        // 获取下个节点的审批人  设置：extend 参数
        $scope.getExtendParams = function () {

            processFindAssigneeUserTasks({
                params: JSON.stringify({
                    taskId: taskId,
                    listId: listId,
                    processDefinitionKey: processDefinitionKey
                })
            }, function (res) {
                $scope.extendList = res.data;
                for (var n in $scope.extendList) {
                    var item = $scope.extendList[n];
                    $scope.extend[item.id] = {
                        value: [],
                        key: []
                    };
                    for (var y in item.users) {
                        var user = item.users[y];
                        $scope.extend[item.id].value.push(user.userFullName);
                        $scope.extend[item.id].key.push(user.userId)
                    }
                }
            });
        };
        // 获取 ：variables 参数依据
        $scope.getAvailableParams = function () {
            processTaskDetail({
                params: JSON.stringify({
                    taskId: taskId,
                    listId: listId,
                    processDefinitionKey: processDefinitionKey
                })
            }, function (res) {
                if (res.status === 'S') {
                    //isStart 是不是是初始结点  true 是
                    // $scope.isApproval = taskId == undefined ? true : res.isStart//返回false不是草搞状态  true
                    if ($scope.action != 'unread' && $scope.action != 'new') {
                        if (res.data.editStatus == 0) {
                            $scope.formDisabled();
                            // console.log(res)
                        }
                    }

                    $scope.isStart = res.data.isStart; // 是否是初始节点。
                    $scope.isRetrial = res.data.isRetrial; // 是否是驳回重审

                    $scope.isRetrialAndStart = $scope.isStart && $scope.isRetrial;  // 是否是驳回重审到根节点


                    $scope.availableData = res.data;
                    $scope.variables = res.data.variables;

                } else {
                    SIEJS.alert(res.msg, 'error');

                }
            })
        };


        // 获取业务表单已填充的参数
        $scope.getBusinessFormParams = function () {

            var domApiName;
            if ($scope.action === 'version') {// 查看历史版本
                domApiName = 'data-api-version';
            } else {
                domApiName = 'data-api-get';
            }
            var apiDetail = $('[data-api-post]').attr(domApiName);
            if (!apiDetail || !URLAPI[apiDetail]) {
                SIEJS.alert('找不到此表单对应的服务', 'error', '确定', '请检查表单页面 [' + domApiName + '] 值 ');
                return;
            }

            // debugger;
            httpServer.post(URLAPI[apiDetail], {
                params: JSON.stringify($scope.apiDetailParams)
            }, function (res) {
                // debugger;
                if (res.status === 'S') {
                    $scope.formParams = res.data;
                    $scope.formParams.processDefinitionKey =processDefinitionKey;
                    $scope.formParams.responsibilityId =$scope.respId;
                    $scope.formParams.respId =$scope.respId;

                    //部门
                    $scope.departmentParams = {
                        orgId: res.data.orgId
                    };
                    //人员参数
                    $scope.personInfoParams = {
                        orgId: res.data.orgId,
                        deptId: res.data.deptId
                    };
                    //职责参数
                    $scope.positionByPersonParams = {
                        orgId: res.data.orgId,
                        personId: res.data.personId,
                        deptId: res.data.deptId
                    };

                    $scope.findData = true;
                    if ((formType === 'readonly' || $scope.action === 'detail') && $scope.action !== 'draft') {
                        $timeout(function () {
                            $scope.formDisabled();
                        }, 300)
                    }
                } else {
                    $scope.findData = false;
                    console.error('url报错：' + URLAPI[apiDetail]);
                    SIEJS.alert('获取业务表单信息失败，请刷新页面重试', 'error');
                }
                $scope.$broadcast("businessLoad", $scope.findData);
                if (typeof  $scope.pageView.getBusinessFormCallback === 'function') {
                    $scope.pageView.getBusinessFormCallback(res);
                }

            })
        };
        // 业务表单禁用、只读
        $scope.formDisabled = function () {
            $timeout(function () {
                $scope.childData.isEdit = false;

                $("#businessForm").find('input, textarea').not($("#businessForm").find('.hook-normal').find('input, textarea'))
                    .attr('disabled', true)
                    .removeAttr('data-toggle')
                    .removeAttr('data-target')
                    .removeAttr('placeholder')
                    .removeAttr('title')
                    .removeClass('readonly-white');

                $("#businessForm").find('select,span,a,button,input[type="radio"],input[type="checkbox"]')
                    .not($("#businessForm").find('.hook-normal').find('select,span,a,button,input[type="radio"],input[type="checkbox"]')).attr('disabled', true);

                $("#businessForm").find('.editDelete,.input-group-btn,span.red,.red').not($("#businessForm").find('.hook-normal').find('.editDelete,.input-group-btn,span.red,.red')).remove();

                $("#businessForm").find('.fa-print').attr('disabled', false);
                $("#businessForm").find('.dataMax').attr('disabled', false);
                $("#businessForm").find("button[name='fullWindow']").attr('disabled', false);
                $("#businessForm").find("button[name='printButton']").attr('disabled', false);

                if ($scope.formArea.proposalForm
                    && $scope.formArea.proposalForm.isShowFlag
                    && $scope.formArea.proposalForm.isShowFlag.$modelValue == '0'){
                    $("#isSkipApprove").attr('disabled', true);
                } else {
                    $("#isSkipApprove").attr('disabled', false);
                }
                if($scope.isShowFlag === 1) {
                   $("#saveTtaContractLine").attr('disabled', false);
                   $("#tableDisabledId1").attr('disabled', false);
                   $("#tableDisabledId2").attr('disabled', false);
                   $("#tableDisabledId3").attr('disabled', false);
                   $("#tableDisabledId4").attr('disabled', false);
                    
                }else{
                   $("#tableDisabledId1").attr('disabled', true);
                   $("#tableDisabledId2").attr('disabled', true);
                   $("#tableDisabledId3").attr('disabled', true);
                   $("#tableDisabledId4").attr('disabled', true);

                }

                // 子页面的流程图隐藏
                $("#liuchengPicture").style.display = 'none';


                //有些情况表单是可以操作，由子页面扩展
                if (typeof  $scope.pageView.form === 'function') {
                    $scope.pageView.form();
                }
            }, 200);

        };

        //OA获取申请人信息
        $scope.init = function () {
            //初始化 申请时间和申请编号
            $scope.formParams.requestNum = '自动填写';
            $scope.formParams.requestTime = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm');
            console.log('init');
            //申請人
            $scope.formParams.personId = $scope.userData.personId;
            $scope.formParams.userId = $scope.userData.userId;
            $scope.respId = $location.search().respId;


            /*  职责信息已经在路由跳转之前获取 ，所有注释掉 2018-10-14
            $scope.$on("changeCurrentResp", function(e, m) {
                 var userResp = pageResp.get($location.$$path);

                 if (userResp.primaryPosition) {
                     $scope.formParams.deptName = userResp.primaryPosition.deptName;
                     $scope.formParams.deptId = userResp.primaryPosition.deptId;
                     $scope.formParams.personName = userResp.primaryPosition.personName;
                     $scope.formParams.personId = userResp.primaryPosition.personId;
                     $scope.formParams.jobId = userResp.primaryPosition.jobId;  // 2018-9-14 职务id
                     $scope.formParams.jobName = userResp.primaryPosition.jobName;// 2018-9-14 职务名称

                     //职位
                     $scope.formParams.positionId = userResp.primaryPosition.positionId;
                     $scope.formParams.positionName = userResp.primaryPosition.positionName;
                 } else {
                     console.warn('当前职责没有配置职位！')
                 }
                 if (userResp.orgBean) {
                     $scope.orgId = userResp.orgBean.orgId;
                 } else {
                     console.warn('当前职责没有配置BU！')
                 }
             })*/

            $timeout(function () {
                var userResp = pageResp.get();
                if (userResp.primaryPosition) {
                    $scope.formParams.deptName = userResp.primaryPosition.deptName;
                    $scope.formParams.deptId = userResp.primaryPosition.deptId;
                    $scope.formParams.personName = userResp.primaryPosition.personName;
                    $scope.formParams.personId = userResp.primaryPosition.personId;
                    $scope.formParams.jobId = userResp.primaryPosition.jobId;  // 2018-9-14 职务id
                    $scope.formParams.jobName = userResp.primaryPosition.jobName;// 2018-9-14 职务名称

                    //职位
                    $scope.formParams.positionId = userResp.primaryPosition.positionId;
                    $scope.formParams.positionName = userResp.primaryPosition.positionName;


                    // 流程参数

         /*           $scope.approvalParams.positionId=userResp.primaryPosition.positionId;
                    $scope.approvalParams.positionId=userResp.primaryPosition.positionId;
                    $scope.approvalParams.orgId=userResp.orgBean.orgId;
                    $scope.approvalParams.departmentId= userResp.primaryPosition.deptId;
                    $scope.approvalParams.applyPositionId=userResp.primaryPosition.positionId;
                    $scope.approvalParams.applyPersonId=userResp.primaryPosition.personId;*/


                } else {
                    console.warn('当前职责没有配置职位！')
                }
                if (userResp.orgBean) {
                    $scope.orgId = userResp.orgBean.orgId;
                } else {
                    console.warn('当前职责没有配置BU！')
                }
            });


            //部门
            $scope.departmentParams = {
                orgId: $scope.orgId
            };
            //人员参数
            $scope.personInfoParams = {
                orgId: $scope.orgId,
                deptId: ''
            };
            //职责参数
            $scope.positionByPersonParams = {
                orgId: $scope.orgId,
                personId: $scope.formParams.personId,
                deptId: ''
            }

            // $scope.positionByPersonParams.personId = $scope.formParams.personId;

            /*            angular.forEach($scope.userData.positionList, function (data, index) {
                            if (data.primaryFlag === 'Y') {
                                //部門
                                $scope.formParams.deptName = data.deptName;
                                $scope.formParams.deptId = data.deptId;
                                $scope.formParams.personName = data.personName;
                                $scope.formParams.jobId = data.jobId;  // 2018-9-14 职务id
                                $scope.formParams.jobName = data.jobName;// 2018-9-14 职务名称

                                //职位
                                $scope.formParams.positionId = data.positionId;
                                $scope.formParams.positionName = data.positionName;
                                $scope.positionByPersonParams.personId = $scope.formParams.personId;
                                //$scope.formParams.hrOrganizationName = data.deptName;
                                //$scope.formParams.hrOrganizationName = data.deptId;

                            }
                        })

                        */

        };


        //部门lov回调
        $scope.getDepartment = function () {
            var row = $scope.departmentModel.selectRow;
            if (!row) return;

            $scope.formParams.deptId = row.deptId;
            $scope.formParams.deptName = row.deptName;

            $scope.personInfoParams.deptId = row.deptId;

        };
        //人员lov回调
        $scope.getPersonInfo = function () {
            var row = $scope.personInfoModel.selectRow;
            if (!row) return;
            //部门
            $scope.formParams.deptId = row.departmentId;
            $scope.formParams.deptName = row.departmentName;
            //人员
            $scope.formParams.personName = row.personName;
            $scope.formParams.personId = row.personId;
            //职务
            $scope.formParams.jobId = row.jobId;
            $scope.formParams.jobName = row.jobName;
            //职位
            $scope.formParams.positionId = row.positionId;
            $scope.formParams.positionName = row.positionName;

            $scope.positionByPersonParams.personId = row.personId;
        };
        // 职位回调
        $scope.getPositionByPerson = function () {
            var row = $scope.positionByPersonModel.selectRow;
            if (!row) retrn;
            $scope.formParams.deptId = row.departmentId;
            $scope.formParams.deptName = row.departmentName;
        };


        // 视图载完成
        $scope.loadView = function () {

            // 绘制流程图的参数
            $scope.processImageParams = {
                token: sessionStorage.getItem(window.appName + '_certificate')||localStorage.getItem(window.appName + '_certificate'),
                id: 'processImg',
                instanceId: $scope.processInstanceParams.processInstanceId || '',
                key: processDefinitionKey
            };

            switch ($scope.action) {
                case 'new': //新发起
                    $scope.getExtendParams();
                    $scope.getAvailableParams();
                    $scope.init();
                    break;
                case 'edit': //编辑流程
                    $scope.getBusinessFormParams();
                    $scope.getExtendParams();
                    $scope.getAvailableParams();

                    break;
                case 'draft': // 草稿
                    $scope.getExtendParams();
                    $scope.getAvailableParams();
                    $scope.getBusinessFormParams();

                    break;
                case 'refusal': // 驳回
                    $scope.getBusinessFormParams();
                    $scope.getExtendParams();
                    $scope.getAvailableParams();

                    break;
                case 'detail': // 查看 审批中，只能查看
                case 'version':
                    $scope.getBusinessFormParams();
                    processRevokeStatus({
                        processInstanceId: $scope.processInstanceParams.processInstanceId,
                        taskId: taskId
                    }, function (res) {
                        // 是否可以撤回
                        $scope.revokeStatus = res.data;
                    });
                    break;

                case 'approval': // 待审批
                    //$scope.isApproval = formType === 'readonly';
                    $scope.getBusinessFormParams();
                    $scope.getAvailableParams();
                    $scope.getExtendParams();
                    break;

                case 'unread': // 待阅
                    $scope.getBusinessFormParams();
                    $scope.formDisabled();
                    break;

                case 'outer': // 其他特珠类型
                    $scope.getBusinessFormParams();
                    break;
            }
        };

        // 选择审批人
        $scope.showLovAssessing = function (item) {
            $("#LovAssessing").modal('show');
            /* $scope.LovAssessing.list = [];
             $scope.LovAssessing.cancel();*/
            $scope.LovAssessingParams = {
                taskDefinitionId: item.id,
                processDefinitionKey: processDefinitionKey,
                listId: listId
            };

            $timeout(function () {
                $scope.LovAssessing.search(1);
            });


            // 当前审批节点
            $scope.currentAssessingId = item.id;
        };

        // 选中LOV选选择审核人员
        $scope.setAuditor = function (key, value, lists) {
            $scope.extend[$scope.currentAssessingId] = {
                key: key,
                value: value
            };

            // 设置extend
            $scope.params.extend.tasks_assignees = [];
            if ($scope.extendList) {
                $scope.extendList.map(function (item, index) {
                    $scope.params.extend.tasks_assignees.push({
                        id: item.id,
                        userIds: $scope.extend[item.id] ? $scope.extend[item.id].key : null
                    })
                })
            }


        };


        // 显示审批弹窗
        /*
        *  option   审核类型选项
        *  saveonly  是否草稿
        *  status 状态 、 按钮名、*/
        $scope.showBtnApproval = function (option, saveonly, status) {
            if (!$scope.availableData) {
                SIEJS.alert('任务详细信息查询失败，请刷新页面重试', 'error');
                return;
            }
            // 重置验证状态
            $("#approvalForm").removeClass('isInvalid');

           //直接审批不显示对话框需注释
            // $("#approvalFormModal").modal('show');
            $scope.approvalParamsOption = option;
            $scope.approvalParamsSaveonly = saveonly;
            $scope.approvalStatus = status;
            $scope.showParams = {};


            $scope.addParams = {
                'helpapprovalTitle': $scope.availableData.name
            };
            if (status === '驳回重审') {

                if ($scope.findTaskNodes) {
                    $scope.taskList = $scope.findTaskNodes;
                } else {
                    // 获取任务节点列表
                    processFindTaskNodes({
                        taskId: taskId,
                        type: 1 // 驳回
                    }, function (res) {
                        $scope.taskList = res.data;
                        $scope.findTaskNodes = res.data; // 存储当前列表，避免下次点击重新获取
                    })
                }

            } else if (status === '发消息') {
                // 设置 发消息的接收人列表
                $scope.taksUserList = arrayUnique(angular.copy($scope.taskTable.list), 'userId');
            } else if ($scope.isRetrial) {  // 驳回重审后，重新提交
                $scope.taskList = $scope.availableData.taskNodes;

            }

            //新增如下代码直接审批操作,注释$("#approvalFormModal").modal('show');  start
            if (option == 'Y' && saveonly == false && status == '审批' && ($scope.approvalStatus == '通过' || $scope.approvalStatus == '审批' )) {
                $scope.addParams.opinion = '审批通过'; //默认审批意见
                $scope.passEvent.click(); //调用审批通过事件
            } else {
                $("#approvalFormModal").modal('show');
            }
            //新增如下代码直接审批操作,注释$("#approvalFormModal").modal('show');  end
        };

        //
        /*  审核功能 审核事件
        *  option   审核类型选项
        *  saveonly  是否草稿
        *  status 状态 、 按钮名、
        * *
        * */
        $scope.approvalEvent = function (option, saveonly, status) {


            option = option || $scope.approvalParamsOption;
            saveonly = saveonly !== undefined ? saveonly : $scope.approvalParamsSaveonly;
            $scope.approvalStatus = status || $scope.approvalStatus;
            var apiSave = $('[data-api-update]').attr('data-api-update');
            if (!apiSave || !URLAPI[apiSave]) {
                SIEJS.alert('找不到此表单对应的服务', 'error', '确定', '请检查表单页面 [data-api-update] 值 ');
                return;
            }

            var variables;
            //  处理变量
            if ($scope.variables) {
                $scope.variables.map(function (item) {
                    item.value = item.name ? $scope.formParams[item.name] : '';
                });
                variables = angular.copy($scope.variables);
                // 追加流程审核人员
                variables.push({
                    name: "tasks_assignees",
                    type: "string",
                    value: $scope.params.extend.tasks_assignees
                });
            }


            /*         $scope.approvalParams = {
                         taskId: taskId,
                         properties: {
                             opinion: $scope.addParams.opinion,
                             option: option
                         },
                         variables: variables,
                         saveonly: saveonly,
                         delegateId: delegateId
                     };*/

            $scope.approvalParams.taskId = taskId;
            $scope.approvalParams.properties = {
                opinion: $scope.addParams.opinion,
                option: option,
                menucode:$location.$$search.menucode
            };
            $scope.approvalParams.variables = variables;
            $scope.approvalParams.saveonly = saveonly;
            $scope.approvalParams.delegateId = delegateId;


            if ($scope.approvalStatus === '驳回重审' || $scope.isRetrial) {
                $scope.approvalParams.properties.taskDefinitionId = $scope.addParams.taskItem ? $scope.addParams.taskItem.taskDefinitionId : null; // 设置 驳回重审任务节点ID
                $scope.approvalParams.properties.rejectTaskId = $scope.addParams.taskItem ? $scope.addParams.taskItem.taskId : null; // 设置 驳回重审任务节点ID
            }


            var p;
            switch ($scope.approvalStatus) {
                case '发消息':
                    p = {
                        taskId: taskId,
                        type: 'COMMON',
                        receiverId: $scope.addParams.receiverId,
                        content: $scope.addParams.opinion
                    };
                    processCommunicateSave(p, function (res) {

                        if (res.status === 'S') {
                            $scope.closeTab('发消息成功');
                        } else {
                            SIEJS.alert('发消息失败', 'error', '确定', res.msg);
                        }
                    });
                    break;

                case '增加助审':

                    p = {
                        "taskId": taskId,
                        "taskName": $scope.addParams.helpapprovalTitle,
                        "description": $scope.addParams.opinion,
                        "userId": $scope.addParams.helpapprovalUserId
                    };

                    processAddSubTask(p, function (res) {
                        if (res.status === 'S') {
                            $scope.closeTab('增加助审成功');
                        } else {
                            SIEJS.alert('增加助审失败', 'error', '确定', res.msg);
                        }
                    });

                    break;

                case '审批':
                case '驳回':
                case '通过':
                case '驳回重审':
                    // 当表单为可编辑时，需要先执行更新业务表单。
                    $scope.formParams.buttonStatus='commit';
                    if (formType === 'edit') { // 可编辑的表单
                        $scope.formParams.editFlag = saveonly ? 'Y' : 'N';

                        // 保存到业务 并获取对应的businessKey
                        httpServer.post(URLAPI[apiSave], {
                            params: JSON.stringify($scope.formParams)
                        }, function (res) {
                            if (res.status === 'S') {
                                // 进行任务办理
                                $scope.processComplete(saveonly);
                            } else {
                                SIEJS.alert($scope.approvalStatus + '失败', 'error', '确定', res.msg);
                            }
                        })
                    } else {
                        // 进行任务办理
                        $scope.processComplete(saveonly);
                    }


                    break;
                case '保存':
                    // $scope.saveAndDraft(true, '保存');
                    $scope.formParams.buttonStatus='save';
                    $scope.draftEvent.click();
                    break;
                case '提交审批':
                    $scope.formParams.buttonStatus='commit';
                    // $scope.saveAndDraft(false, '提交审批');
                    $scope.submitEvent.click();
                    break;
            }
        };

        $scope.closeTab = function (title) {
            $("#approvalFormModal").modal('hide');
            if ($scope.entrance) {
                swal({
                        title: title || "流程提交成功",
                        text: '',
                        type: "success",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        cancelButtonText: '关闭窗口',
                        confirmButtonText: "返回系统首页",
                        closeOnConfirm: true
                    },
                    function (isConfirm) {

                        if (isConfirm) {
                            $location.url(window.appName.toLocaleLowerCase() + '/main');
                        } else {
                            window.opener = null;
                            window.close();
                        }
                    });

            } else {

                swal({
                    title: title || "流程提交成功",
                    text: '',
                    type: "success",
                    showConfirmButton: false,
                    timer: 1500
                });

                $timeout(function () {

                    var activeTab = $location.search().activeTab || 'myUpcoming';
                    if (window.parent != window) { // 当前为 iframe
                        window.parent.deleteHeadTab(menuId, activeTab, true); // 关闭 Tab 返回
                    } else {
                        window.deleteHeadTab(menuId, activeTab, true); // 关闭 Tab 返回
                    }
                }, 1800)
            }

        };
        // 快捷回复
        $scope.toOpinion = function () {
            $scope.addParams.opinion = $scope.showParams.opinion + '';
        };

        $scope.getOpinionList = function () {
            processHistoricOpinions(function (res) {
                $scope.OpinionsList = res.data;
            })
        }();


        // 切换任务节点
        $scope.changeTask = function () {
            $scope.getTaskUserList($scope.messageTaskId);
        };
        // 获取任务下的用户
        $scope.getTaskUserList = function (taskId, isUrge) {
            $scope.currentUserList = [];
            processFindTaskUsers({
                taskId: taskId
            }, function (data) {

                for (var i = 0; i < data.data.length; i++) {
                    $scope.currentUserList.push({
                        "userFullName": data.data[i].userFullName,
                        "userId": data.data[i].userId,
                        "userName": data.data[i].userName
                    })
                }

                if (isUrge) {
                    $scope.urgeParams.userId = $scope.currentUserList[0].userId; // 默认选择第一个审批 人
                    $("#modalUrgeUser").modal('show')
                }

            })
        };

        // 视图加载完成
        $scope.$on('$viewContentLoaded', function () {
            //  页面初始化事件，子页面可以覆写此方法进行一些数据初始化。
            if (typeof  $scope.pageView.init === 'function') {
                $scope.pageView.init();
            }
            $scope.loadView();


            /*$timeout(function () {
               /!* $("[readonly]").click(function () {
                    $(this).blur();
                })*!/

                $("[readonly]").attr('unselectable','on')
            },250)*/
        });

        $scope.respLoad = function (respId) {
            // alert(respId);
        };

        // 审批记录加载完成
        $scope.historicListLoad = function (scope, list) {
            if (list && list.length > 0) {
                var node = list[0];
                if (node.bpm_result === 'ALLOW') {
                    $scope.processIsPass = true; // 审批状态已审核完成并通过
                }
                var isStart = $location.search().isStart;
                var user = userInfo.get();
                if (node.bpm_status === 0 && $scope.action === 'detail' && isStart === 'false' && user.userName === node.userName) {
                    $scope.isUrge = true; // 是否可以催办状态
                }
            }

        };

        // 撤回
        $scope.bntWithdraw = function () {
            var p = {
                processInstanceId: $scope.processInstanceParams.processInstanceId,
                taskId: taskId
            };
            processRevoke(p, function (res) {
                if (res.status === 'S') {
                    $scope.closeTab('撤回成功');

                } else {
                    SIEJS.alert(res.msg, 'warning');
                }
            })
        };


        // 催办弹窗
        $scope.bntUrge = function () {
            $scope.urgeParams.userId = '';

            // 催办参数
            $scope.urgeParams = {
                processInstanceId: $scope.processInstanceParams.processInstanceId
            };
            $scope.currentUserList = [];
            // 获取当前节点可选的人员
            processFindActiveTasks({
                processInstanceId: $scope.processInstanceParams.processInstanceId
            }, function (res) {
                $scope.taskList = res.data;

                var row = res.data[0];
                $scope.urgeParams.taskId = row.taskId; // 默认选择第一行
                if (row && row.userName) {
                    $scope.currentUserList.push({
                        "userFullName": row.userFullName,
                        "userId": row.userId,
                        "userName": row.userName
                    });
                    $("#modalUrgeUser").modal('show');
                    $scope.urgeParams.userId = $scope.currentUserList[0].userId; // 默认选择第一个审批 人
                } else {
                    $scope.getTaskUserList(row.taskId, true);

                }
                log($scope.currentUserList)
            })

        };

        // 催办
        $scope.urgeSave = function () {
            var p = {
                taskId: $scope.urgeParams.taskId,
                type: 'URGE',
                receiverId: $scope.urgeParams.userId,
                messageChannel: ''
            };

            processCommunicateSave(p, function (res) {
                if (res.status === 'S') {
                    SIEJS.alert('催办成功');
                    $("#modalUrgeUser").modal('hide');
                } else {
                    SIEJS.alert('催办失败', 'warning');
                }
            })
        };


        // tab 切换
        $scope.tabChange = function (name) {
            $scope.tabAction = name;
            if (!$scope.processImgLoad) {
                var p = $scope.processImageParams;
                $timeout(function () {
                    
                    processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                    $scope.processImgLoad = true;
                }, 100)
            }
        };

        // 表格操作　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
        // 创建行
        $scope.createRows = function (name, obj) {
            if (!$scope.formParams[name]) {
                $scope.formParams[name] = [];
            }
            $scope.formParams[name].push(obj);

        };

        $scope.logParams = function () {
            log($scope.formParams);
        };

        // 删除表格行
        $scope.deleteRow = function (name, index, row) {

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                $scope.formParams[name].splice(index, 1);
            })

            /*SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                if (row.id == null || row.id == '') {
                    $scope.formParams[name].splice(index, 1);
                    SIEJS.alert("删除成功!", "success");
                } else {
                    var apiDel = $('[data-api-del]').attr('data-api-del');
                    if (!apiDel || !URLAPI[apiDel]) {
                        SIEJS.alert('找不到此表单对应的服务', 'error', '确定', '请检查表单页面 [data-api-del] 值 ');
                        return;
                    }
                    httpServer.post(URLAPI[apiDel], {
                        params: JSON.stringify({
                            leaveId: row.id
                        })
                    }, function (res) {
                        if (res.status === 'S') {
                            $scope.formParams[name].splice(index, 1);
                            SIEJS.alert("删除成功!", "success");
                        } else {
                            SIEJS.alert('删除失败', 'error');
                        }
                    })
                }
            })*/


        };


        /**
         * create by hmx 2018年9月18日
         * 缩放：zoom
         * 放大：enlarge
         * 还原：reduction
         */
        $scope.zoomNum = 1;
        $scope.zoom = function (str) {
            if (str === 'zoom') {

                if ($scope.zoomNum <= 0.2) {
                    return;
                }
                $scope.zoomNum = $scope.zoomNum - 0.1;
                console.log($scope.zoomNum)
            }

            if (str === 'enlarge') {
                if ($scope.zoomNum > 1.5) {
                    return;
                }
                $scope.zoomNum = $scope.zoomNum + 0.1;
                console.log($scope.zoomNum)
            }

            if (str === 'reduction') {
                $scope.zoomNum = 1;
            }

            $('.GooFlow_work_inner').css({
                '-webkit-transform': 'scale(' + $scope.zoomNum + ')',
                '-moz-transform': 'scale(' + $scope.zoomNum + ')',
                '-ms-transform': 'scale(' + $scope.zoomNum + ')',
                '-o-transform': 'scale(' + $scope.zoomNum + ')',
                'transform': 'scale(' + $scope.zoomNum + ')',
            });
        };
        $scope.setFormParams=function(data){
            $scope.formParams=data;
        }


    });
});
