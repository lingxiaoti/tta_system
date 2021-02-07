/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload','XLSX','GooFlow'], function (app, pinyin, ztree, angularFileUpload,XLSX,GooFlow) {
    app.useModule('angularFileUpload'); //按需加载弹出层模块
    app.controller('supplementDetailCtrl', function ($filter, httpServer, URLAPI, Base64, FileUploader,$window, userSave, $timeout, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS) {

        $scope.tabAction = 'supplementDetailBusiness';//默认显示业务表单

        $scope.id = $stateParams.id;
        $scope.params = {};
        $scope.project= {};
        $scope.project.billStatus = 'A';
        $scope.clause={};
        $scope.processStatus = "DRAFT";// status 流程状态  DRAFT.草稿   APPROVAL.审批中  ALLOW.审批通过  REFUSAL.审批驳回 CLOSED.已关闭

        $scope.relSupplierParams ={
            //functionId:'tta_side_agrt_header'//业务模块
        };

        $scope.attachmentParams = {};

        //保存
        $scope.btnSave = function () {
/*            var sideAgrtHId =  $scope.project.sideAgrtHId;
            if (sideAgrtHId != undefined && sideAgrtHId != null){
                SIEJS.alert("您已经保存过了,不需要再次保存", "warning", "确定");
                return;
            }*/

            httpServer.post(URLAPI.saveTtaSideAgrtHeader,
                {params: JSON.stringify($scope.project)},
                function (res) {
                    $scope.project.sideAgrtHId = res.data.sideAgrtHId;
                    $scope.id = res.data.sideAgrtHId;
                    $scope.dataTable.selectRow = null;
                    $scope.search($scope.project.sideAgrtHId);
                    SIEJS.alert(res.msg, 'success', '确定');
                });
        };

        /**********************************工作流配置 start**************************************/
        //获取url参数对象
        $scope.urlParams = urlToObj(location.hash);

        //先判断是否业务form是否有id 再判断流程form的id
        if ($stateParams.id) {
            $scope.id = $stateParams.id;
        } else if ($scope.urlParams.businessKey) {
            $scope.id = $scope.urlParams.businessKey;

        }

        //如果表单的ID与页面的头ID不一致，需要做兼容处理
        //url参数转对象
        function urlToObj(url) {
            var index = url.lastIndexOf('?');
            var params = url.substring(index + 1);
            var arr = params.split('&');
            var obj = {};
            arr.forEach(function (item) {
                obj[item.split('=')[0]] = item.split('=')[1];
            });
            return obj;
        }

        //获取单据ID
        function getId() {
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.id;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'SUPPLEMENT_APPROVE.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
        };

        $scope.tabChange = function (name) {
            $scope.tabAction = name;
            if (!$scope.processImgLoad) {
                $scope.getProcessInfo(function () {
                    var p = $scope.processImageParams;
                    $timeout(function () {
                        processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                        $scope.processImgLoad = true;
                    }, 100)
                });
            }
        };
        // 获取流程信息
        $scope.getProcessInfo = function (callback) {
            httpServer.post(URLAPI.processGet, {
                    'params': JSON.stringify({
                        processDefinitionKey: $scope.processImageParams.key,
                        businessKey: $scope.id
                    })
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.processImageParams.instanceId = res.data.processInstanceId;
                    }
                    callback && callback(res);
                });
        };


        $scope.submitApproval = function(){

            $("#submitApproval").attr("disabled","true");//提交前先禁用提交按钮
            var sideAgrtHId =  $scope.project.sideAgrtHId;
            if (sideAgrtHId == undefined && sideAgrtHId == null){
                SIEJS.alert("您还未保存此单据,请重新保存,再重新提交", "warning", "确定");
                return;
            }

            if($scope.project.billStatus !=undefined && $scope.project.billStatus !='A'){
                SIEJS.alert("您当前单据状态不在制作中,不能提交,请重新保存再提交", "warning", "确定");
                return;
            }

            $scope.extend = {
                "tasks_assignees": []
            }
            $scope.variables = []; //流程变量
            angular.forEach($scope.clause, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            $scope.properties = {
                "menucode": "HTGL",
                "i": ""
            };
            $scope.paramsBpm = {
                "extend": $scope.extend,
                "variables": $scope.variables,
                "properties": $scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "SUPPLEMENT_APPROVE.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.project.sideAgrtHId, //单据ID
                "title": "proposal补充协议" + $scope.project.sideAgrtCode, //流程标题，修改为当前业务信息
                "positionId": 0,
                "orgId": 0,
                "userType": "20",
                "billNo": $scope.project.sideAgrtCode
            };

            httpServer.post(URLAPI.processStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.search(getId());
                    SIEJS.alert("提交成功", "success", "确定");
                } else {
                    $("#submitApproval").removeAttr("disabled");
                    SIEJS.alert(res.msg, "error", "确定");

                }
            }, function (err) {
                //$scope.btnC('0',false);
                $("#submitApproval").removeAttr("disabled");
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                // $("#TJSP").removeAttr("disabled");
            });

        };

        /**********************************工作流配置 end****************************************/


//=======================2019.10.22注释,注释原因:改成流程图形式========================================
        /*
        //提交
        $scope.btnSubmit = function () {
            var sideAgrtHId =  $scope.project.sideAgrtHId;
            if (sideAgrtHId == undefined && sideAgrtHId == null){
                SIEJS.alert("您还未保存此单据,请重新保存,再重新提交", "warning", "确定");
                return;
            }

            //单据状态
            if($scope.project.billStatus !=undefined && $scope.project.billStatus !='A'){
                SIEJS.alert("您当前单据状态不在制作中,不能提交,请重新保存再提交", "warning", "确定");
                return;
            }

            httpServer.post(URLAPI.submitTtaSideAgrtHeader,
                {params: JSON.stringify($scope.project)},
                function (res) {
                    $scope.project.sideAgrtHId = res.data.sideAgrtHId;
                    $scope.dataTable.selectRow = null;
                    $scope.search($scope.project.sideAgrtHId);
                    SIEJS.alert(res.msg, 'success', '确定');
                });
        };*/
//==================================================================================



        //作废
        $scope.btnDiscard = function () {
            var sideAgrtHId =  $scope.project.sideAgrtHId;
            if (sideAgrtHId == undefined && sideAgrtHId == null){
                SIEJS.alert("您还未保存此单据,不能进行作废操作", "warning", "确定");
                return;
            }

            //单据状态
            if($scope.project.billStatus !=undefined && $scope.project.billStatus !='C'){
                SIEJS.alert("您当前单据状态不在审批通过状态,不能作废", "warning", "确定");
                return;
            }

            httpServer.post(URLAPI.disSicradTtaSideAgrtHeader,
                {params: JSON.stringify($scope.project)},
                function (res) {
                    $scope.project.sideAgrtHId = res.data.sideAgrtHId;
                    $scope.dataTable.selectRow = null;
                    $scope.search($scope.project.sideAgrtHId);
                    SIEJS.alert(res.msg, 'success', '确定');
                });
        };

        //删除poposal
        $scope.btnDelProposalSupplier = function () {
            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                var item = $scope.dataTable.selectRow;
                //var n = arrayDeleteItem($scope.dataTable.list, item, 'poposalId');
                //$scope.dataTable.count = $scope.dataTable.count - n;
                httpServer.post(URLAPI.ttaProposalToSideAgrtLineDelete, {
                    'params': JSON.stringify({
                        id: item.sideAgrtLId
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.dataTable.selectRow = null;
                        $scope.params.sideAgrtHId = res.sideAgrtHId;
                        $scope.dataTable.search(1);
                        SIEJS.alert(res.msg, "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });

            });
        };

        //搜索
        $scope.search = function (sideAgrtHId) {
            httpServer.post(URLAPI.findSideAgrtHeaderList, {
                'params': JSON.stringify({
                    sideAgrtHId: sideAgrtHId
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.project = res.data[0];
                    $scope.params.sideAgrtHId = parseInt($scope.id);
                    $timeout(function(){
                        $scope.dataTable.search();
                    },200);

                    //刷新上传附件
                    $scope.relSupplierParams.functionId = 'tta_side_agrt_header';
                    $scope.relSupplierParams.businessId = parseInt($scope.id);

                    $timeout(function(){
                        $scope.relSupplierTable.search();
                    },200);

                    //刷新下载附件
                    $scope.attachmentParams.businessId = parseInt($scope.id);//头表id
                    $timeout(function(){
                        $scope.attachmentDownTable.search();
                    },200);

                } else {
                    //SIEJS.alert(res.msg, "error", "确定");
                    console.log("查询失败");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };


        //新增poposal
        $scope.btnPoposalSupplier = function () {
            $scope.proposalSupplierList.search(1);
        };

        //新增poposal弹框保存
        $scope.setProposalSupplierList = function () {
            console.log($scope.dataTable);
            if ($scope.project.sideAgrtHId == undefined || $scope.project.sideAgrtHId == null) {
                SIEJS.alert("请先保存头信息", 'warning');
                return;
            }

            var p = {
                sideAgrtHId: $scope.project.sideAgrtHId,
                sideAgrtVersion:$scope.project.sideAgrtVersion,
                proposalSupplierList: $scope.proposalSupplierList.selectRowList
            };
            httpServer.post(URLAPI.saveTtaPoposalToSideAgrtLine, {
                'params': JSON.stringify(p)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.params.sideAgrtHId = res.data[0].sideAgrtHId;
                    $scope.dataTable.search(1);
                    SIEJS.alert(res.msg, 'success', '确定');
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert("保存失败! " + err.msg, "error", "确定");
            });
        };

        //下载附件事件
        $scope.downFileEvent = function (m) {
           //console.log(msg);
            if (m.fileId == undefined || m.fileId == null){
                SIEJS.alert("当前没有选中一行数据,不能下载",'warning','确定');
                return;
            }

            var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + m.fileId;
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
            $scope.attachmentDownTable.search(1);

            //var p = {
            //    fileId:m.fileId
            //};
            /*
            SIEJS.confirm('提示','你确定要下载吗?','确定',function () {
               httpServer.post(URLAPI.ttaSideAgrtHeaderDownLoad, {
                    'params': JSON.stringify(p)
                }, function (res) {
                    if (res.status == 'S') {
                        console.log(res);
                        $scope.attachmentParams.businessId = $scope.project.sideAgrtHId;//头表id
                        $scope.attachmentDownTable.search(1);//刷新搜索下载信息列表
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    //SIEJS.alert("下载失败! " + err.msg, "error", "确定");
                    SIEJS.alert("下载失败!", "error", "确定");
                });
            });*/

        };

        //删除附件
        $scope.delUploadFile = function () {
            var item = $scope.relSupplierTable.selectRow;
            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                var p = {
                    fileId: item.fileId //文件id
                };

                httpServer.post(URLAPI.ttaSideAgrtHeaderFileDelete, {
                    'params': JSON.stringify(p)
                }, function (res) {
                    if (res.status == 'S') {
                        //$scope.params.sideAgrtHId = res.data[0].sideAgrtHId;
                        $scope.relSupplierParams.functionId = 'tta_side_agrt_header';
                        $scope.relSupplierParams.businessId = $scope.project.sideAgrtHId;
                        $scope.relSupplierTable.search(1);
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert("删除失败! " + err.msg, "error", "确定");
                });

            });
        };

        if ($scope.id){//编辑页面
            $scope.search($scope.id);
        }else {//新增
            $scope.project.sideAgrtVersion = 1;
        }

        /**
       //新增附件
        var uploader = $scope.uploader = new FileUploader({
            url:URLAPI.ttaSideAgrtHeaderUpload,
            method: 'POST',
            queueLimit: 1,//文件个数
            autoUpload: false,
            isUploading: true,//正在加载中
            removeAfterUpload:true,//上传后删除文件
            //withCredentials:true,
            formData:[
                {
                    sideAgrtHId:id
                }
            ],
            headers: {
                'certificate': window.sessionStorage[window.appName + '_certificate'],
                'TokenCode': 'INDEX_TOKEN_CODE'
            }

        });

        //uploader.headers['头']= "令牌"; //头信息 令牌
        $scope.clearItems = function(){   //重新选择文件时，清空队列，达到覆盖文件的效果
            uploader.clearQueue();
            console.log("切换文件")

        };

        uploader.onAfterAddingFile = function(fileItem) {
            $scope.fileItem = fileItem._file;    //添加文件之后，把文件信息赋给scope
            //console.log(fileItem);
            var sideAgrtHId = fileItem.formData[0].sideAgrtHId;
            if (sideAgrtHId == undefined || sideAgrtHId == null){
                SIEJS.alert("头信息为空,上传失败", 'error', '确定');
            }
        };
        uploader.onSuccessItem = function(fileItem, response, status, headers) {
            console.log("状态: "+ status);
            if (status == 200){
                $scope.relSupplierParams.functionId = 'tta_side_agrt_header';
                $scope.relSupplierParams.businessId = $scope.project.sideAgrtHId;
                $scope.relSupplierTable.search(1);
                SIEJS.alert("上传成功", 'success', '确定');
            } else {
                SIEJS.alert("上传失败", 'error', '确定');
            }

        };

        //上传附件
        $scope.UploadFile = function () {
            uploader.uploadAll();
            console.log(uploader.uploadAll());
        }
        **/

        //上传附件
        $scope.uploadFile = function () {

            var f = $scope.myFile;
            var fd = new FormData();
            var file = document.querySelector('input[type=file]').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var sideAgrtHId = $scope.id;
            if (sideAgrtHId == undefined || sideAgrtHId == null){
                SIEJS.alert("头信息为空,上传失败", 'error', '确定');
            }

            fd.append('sideAgrtHId', $scope.id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            fd.append("functionId","tta_side_agrt_header");
            $http.post(URLAPI.ttaSideAgrtHeaderUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                $scope.relSupplierParams.functionId = 'tta_side_agrt_header';
                $scope.relSupplierParams.businessId = $scope.project.sideAgrtHId;
                $scope.relSupplierTable.search(1);
                SIEJS.alert(response.msg, 'success', '确定');
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }


    });
});