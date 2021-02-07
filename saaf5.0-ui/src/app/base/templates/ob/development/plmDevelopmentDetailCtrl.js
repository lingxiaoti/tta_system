/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload','XLSX','jqueryUi'], function (app, pinyin, ztree, angularFileUpload, jqueryUi, XLSX) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('plmDevelopmentDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, FileUploader, iframeMessage) {
        var id = $stateParams.id;
        $scope.plmDevelopmentInfoId = id;


        $scope.headerData = {};
        var oldData = {};

        // $scope.fileButtonDisabled = false;

        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;



        //产品资质文件表格
        $scope.productQaFileDataTable = [];
        //资质明细表格
        $scope.qaDetailTable = [];
        //附件上传保存参数
        $scope.uploadFileData = {};

        //附件上传存储数组
        $scope.fileData = [];

        //产品成分表查询参数
        $scope.developmentIngredientsTableParams = {plmDevelopmentInfoId:id==''?-1:id};
        //产品成分表表格
        $scope.developmentIngredientsTable = [];
        //包装规格书表格
        $scope.packageSpecificationTable = [];

        //导入用表格
        $scope.dataTable = [];

        //批次信息表格
        $scope.batchInfoDataTable = [];




        //查询单据信息
        $scope.search = function () {


            $scope.plmDevelopmentInfoId = id;

            httpServer.post(URLAPI.findPlmDevelopmentInfoInfo, {
                    'params': JSON.stringify({plmDevelopmentInfoId: $scope.plmDevelopmentInfoId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.headerData = res.data[0];
                        oldData = angular.copy($scope.headerData);
                        $scope.searchDevelopmentQaFileSummary();
                        $scope.searchBatchInfo();
                        //$scope.searchProjectException();

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        /************** 数据是否修改校验 *****************/

        //关闭页签事件
        iframeMessage.set('plmDevelopmentDetail_check',function () {
            var eq = false;
            //注意存放顺序
            var tempOldDataString = JSON.stringify(oldData);
            if(tempOldDataString.indexOf("batchInfoList")!==-1&&tempOldDataString.indexOf("productQaFileList")!==-1){
                if(tempOldDataString.indexOf("batchInfoList")>tempOldDataString.indexOf("productQaFileList")){
                    var productQaFileList = angular.copy(oldData.productQaFileList);
                    delete oldData.productQaFileList;
                    oldData.productQaFileList = productQaFileList;
                }
            }
            if($scope.batchInfoDataTable.length > 0) {
                $scope.headerData.batchInfoList = angular.copy($scope.batchInfoDataTable);
            }
            if($scope.productQaFileDataTable.length > 0) {
                $scope.headerData.productQaFileList = angular.copy($scope.productQaFileDataTable);
            }

            // console.log(JSON.stringify($scope.headerData)+"\n");
            // console.log(JSON.stringify(oldData));
            if(JSON.stringify($scope.headerData)!==JSON.stringify(oldData)){
                eq = true;
                $('.modal').draggable();
                $('#saveModal').modal('show');
            }

            if(!eq) {
                // iframeMessage.remove('plmDevelopmentDetail_check');
                window.parent.deleteHeadTab();
            }
        });

        $scope.closeHeadTab = function(){
            window.parent.deleteHeadTab();
        };

        /************** 数据是否修改校验 *****************/




        //id不为空，初始搜索
        if ($stateParams.id) {
            //查询头信息
            $scope.search();
        } else {
            $scope.headerData.developmentBillStatus = 'DEVELOPING';
            $scope.headerData.productStatus = 'DEVELOPING';
            $scope.headerData.specialApprovalFlag = 'N';
            oldData = angular.copy($scope.headerData);
        }


        /**********************头按钮操作 start *********************/

        //全存
        $scope.btnSave = function (invalid, commandStatus) {

            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            $scope.checkData(commandStatus);

        };

        //检查数据是否符合规范
        $scope.checkData = function(commandStatus){
            if(commandStatus==='COMPLETED'||commandStatus==='TOBEADDED'){
                httpServer.post(URLAPI.findPlmDevelopmentQaDetailInfo, {
                        'params': JSON.stringify({plmDevelopmentInfoId: $scope.headerData.plmDevelopmentInfoId}),
                        'pageRows': 1000
                    },
                    function (res) {
                        if (res.status == 'S') {
                            var checkArrays = res.data;
                            for(var i = 0; i < checkArrays.length; i++){
                                if(checkArrays[i].fileStatus!=='PASSED'){
                                    SIEJS.alert('资质文件明细有未审批通过文件！','error','确定');
                                    return;
                                }
                            }
                            $scope.saveHeaderAndQa(commandStatus);
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            }
            else {
                $scope.saveHeaderAndQa(commandStatus);
            }
        };

        $scope.saveHeaderAndQa = function(commandStatus){

            var params = $scope.headerData;
            params.plmDevelopmentQaSummaryList = $scope.productQaFileDataTable;
            params.commandStatus = commandStatus;


            httpServer.post(URLAPI.savePlmDevelopmentInfoInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.plmDevelopmentInfoId;
                        $scope.developmentIngredientsTableParams.plmDevelopmentInfoId = res.data.plmDevelopmentInfoId;

                        $scope.search();
                        $('#saveModal').modal('hide');

                        SIEJS.alert(res.msg, 'success','确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //作废
        $scope.btnDiscard = function(){

            $scope.discardReason = "";
            $('#discardModal').modal('show');

        };

        $scope.discardAction = function(){
            var params = angular.copy($scope.headerData);
            // params.remarks = $scope.discardReason;
            if(params.remarks!==undefined&&params.remarks!=='') {
                params.remarks += '; 作废原因：' + $scope.discardReason;
            } else {
                params.remarks = '作废原因：' + $scope.discardReason;
            }
            params.plmDevelopmentQaSummaryList = $scope.productQaFileDataTable;
            params.commandStatus = 'ABANDONED';

            httpServer.post(URLAPI.savePlmDevelopmentInfoInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.plmDevelopmentInfoId;
                        $scope.search();
                        $('#discardModal').modal('hide');
                        SIEJS.alert(res.msg, 'success','确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.KFWC = function(invalid){
            SIEJS.confirm('开发完成', '是否确定开发完成？', '确定', function () {
                $scope.btnSave(invalid, 'COMPLETED');
            });
        };

        $scope.KFWCDBC = function(invalid){
            SIEJS.confirm('开发完成待补充', '是否确定开发完成待补充？', '确定', function () {
                $scope.btnSave(invalid, 'TOBEADDED');
            });
        };


        /**********************头按钮操作 end *********************/



        /**********************产品资质文件汇总 start *********************/

        //查询产品资质文件汇总
        $scope.searchDevelopmentQaFileSummary = function () {

            httpServer.post(URLAPI.findPlmDevelopmentQaSummaryInfo, {
                    'params': JSON.stringify({plmDevelopmentInfoId: $scope.plmDevelopmentInfoId}),
                    'pageRows': 1000
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.productQaFileDataTable = res.data;
                        if($scope.productQaFileDataTable.length!==0)
                            oldData.productQaFileList = angular.copy($scope.productQaFileDataTable);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //保存全部资质文件汇总表
        $scope.saveProductQaSummary = function(){

            var params = {};
            params.plmDevelopmentQaSummaryList = $scope.productQaFileDataTable

            httpServer.post(URLAPI.savePlmDevelopmentQaSummaryInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.search();
                        SIEJS.alert('操作成功');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }



        /**********************产品资质文件汇总 end *********************/


        /**********************产品成分表 start *********************/

        $scope.searchProductIngredients = function(){
            $scope.developmentIngredientsTable.search();
        };

        /**********************产品成分表 end *********************/

        $scope.searchPackageSpecification = function(){
            $scope.packageSpecificationTable.search();
        }

        /**********************弹窗 start *********************/

        //项目异常涉及产品返回参数
        $scope.selectProjectInfoReturn = function (key, value, returnList) {
            $scope.headerData.projectName = returnList[0].projectName;
            $scope.headerData.projectRange = returnList[0].projectRange;
            $scope.headerData.supplierId = returnList[0].supplierId;
            $scope.headerData.supplierCode = returnList[0].supplierCode;
            $scope.headerData.supplierName = returnList[0].supplierName;
            $scope.headerData.supplierType = returnList[0].supplierType;
            $scope.headerData.projectCreator = returnList[0].creatorName;
            $scope.headerData.biddingCode = returnList[0].biddingCode;
            $scope.headerData.plmDevelopmentInfoId = returnList[0].plmDevelopmentInfoId;
            $scope.headerData.plmProjectId = returnList[0].plmProjectId;
            if($scope.headerData.supplierType === 'PRODUCER'){
                $scope.headerData.producer = returnList[0].supplierName;
            }

        };

        //生产商返回值
        $scope.selectProducerInfoReturn = function(key, value, returnList){
            $scope.headerData.producer = '';
            for(var i = 0; i < returnList.length; i++){
                if($scope.headerData.producer!==undefined&&$scope.headerData.producer!=='') {
                    $scope.headerData.producer = $scope.headerData.producer + ',' + returnList[i].userName;
                }
                else {
                    $scope.headerData.producer = returnList[i].userName;
                }
            }
            if($scope.headerData.supplierType==='PRODUCER'&&$scope.headerData.producer.indexOf($scope.headerData.supplierName)===-1){
                if($scope.headerData.producer === undefined || $scope.headerData.producer === ''){
                    $scope.headerData.producer = $scope.headerData.supplierName;
                }
                else {
                    $scope.headerData.producer = $scope.headerData.producer + ',' + $scope.headerData.supplierName;
                }
            }
        };

        /**********************弹窗 end *********************/

        /**********************资质明细 start *********************/

        //打开上传资质文件窗口
        $scope.productQaFileUploadShow = function(index){
            if($scope.productQaFileDataTable[index].inapplicableSign==='Y'){
                SIEJS.alert('该产品不适用于'+$scope.productQaFileDataTable[index].fileAlterType,'error','确定');
                return;
            }
            $scope.fileData = [];
            $scope.productQaFileDataTable.selectRow = index;
            $scope.searchQaDetail();
            $('#fileUpload').modal('show');
        };

        //查询资质文件明细
        $scope.searchQaDetail = function(){
            var params = {};
            params.plmDevelopmentQaSummaryId = $scope.productQaFileDataTable[$scope.productQaFileDataTable.selectRow].plmDevelopmentQaSummaryId;
            if($scope.userType !== '60'){
                params.fileStatus_notLike = 'IN_APPROVAL';
            }
            httpServer.post(URLAPI.findPlmDevelopmentQaDetailInfo, {
                    'params': JSON.stringify(params),
                    'pageRows': 1000
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.qaDetailTable = res.data;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //上传保存
        $scope.saveFile = function (status) {
            if(status==='REJECTED'&&($scope.rejectReason===undefined||$scope.rejectReason==='')){
                SIEJS.alert('驳回原因必填','error','确定');
                return;
            }

            if(status ==='PASSED') {
                //是否有审批中的文件
                var allApproval = false;
                for (var i = 0; i < $scope.qaDetailTable.length; i++) {
                    if ($scope.qaDetailTable[i].fileStatusName === '审批中') {
                        allApproval = true;
                    }
                }
                if (allApproval === false) {
                    SIEJS.alert('无相关文件可同意，请检查数据', 'error', '确定');
                    return;
                }
            }

            var params = {};
            params.commandStatus = status;
            params.plmDevelopmentQaDetailList = $scope.qaDetailTable;
            if(status!=='APPROVING')
            params.rejectReason = $scope.rejectReason;

            httpServer.post(URLAPI.savePlmDevelopmentQaDetailInfo, {
                'params': JSON.stringify(params)
            }, function (res) {
                if ('S' == res.status) {
                    $("#fileUploadDetail").modal("toggle");
                    // 文件保存成功后，查询文件信息
                    $scope.searchQaDetail();
                    SIEJS.alert('操作成功');
                    if(status==='REJECTED'){
                        $scope.rejectReason = undefined;
                        $('#disagreeModal').modal('hide');
                    }
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                SIEJS.alert('保存失败', 'error', '确定');
            })

        };

        $scope.showRejectedModal = function(){

            //是否有审批中的文件
            var allApproval = false;
            for(var i = 0; i < $scope.qaDetailTable.length; i++){
                if($scope.qaDetailTable[i].fileStatusName==='审批中'){
                    allApproval = true;
                }
            }
            if(allApproval=== false){
                SIEJS.alert('无相关文件可驳回，请检查数据','error','确定');
                return;
            }

            $scope.rejectReason = undefined;
            $('#disagreeModal').modal('show');
        };


        //删除资质文件明细行按钮
        $scope.delQaDetail = function(){
            var index=$scope.qaDetailTable.selectRow;
            var plmDevelopmentQaDetailId = $scope.qaDetailTable[index].plmDevelopmentQaDetailId;
            if($scope.qaDetailTable[index].fileStatusName==='审批通过'){
                SIEJS.alert('审批通过文件禁止删除','error','确定');
                return;
            }
            if($scope.userType!=='60'&&$scope.qaDetailTable[index].fileStatusName!=='审批中'){
                SIEJS.alert('QA只允许删除审批中文件','error','确定');
                return;
            }
            if($scope.userType==='60'&&$scope.qaDetailTable[index].fileStatusName!=='待审批'&&$scope.qaDetailTable[index].fileStatusName!=='已驳回'){
                SIEJS.alert('供应商只允许删除待审批与已驳回文件','error','确定');
                return;
            }

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if(plmDevelopmentQaDetailId == null || plmDevelopmentQaDetailId =='' ){
                    $scope.qaDetailTable.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!已成功删除数据！", "success",'确定');
                    $scope.$apply();
                }
                else {
                    $scope.delDevelopmentQaDetailInfo(index);
                }
            })

        };

        //删除资质文件明细方法操作
        $scope.delDevelopmentQaDetailInfo = function(index){

            httpServer.post(URLAPI.deletePlmDevelopmentQaDetailInfo, {
                    'params': JSON.stringify($scope.qaDetailTable[index])
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.qaDetailTable.splice(index, 1);
                        SIEJS.alert("操作成功!已成功删除数据！", "success",'确定');
                        $scope.$apply();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //上传预告片
        var fileUploader = $scope.fileUploader = new FileUploader({
            url: URLAPI.imgUpload,
            autoUpload: true,
            removeAfterUpload: true,
            isUploading: true,
            headers: {
                "Certificate": sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing'
            }
        });
        fileUploader.filters.push({//文件限制
            name: 'imageFilter',
            fn: function (item, options) {
                var fileTypeData=item.name.split(".");
                var type = '|' + fileTypeData[fileTypeData.length-1] + '|';
                if($scope.allowFileType==undefined || $scope.allowFileType=="" || $scope.allowFileType==null){
                    return 1;
                }else{
                    return $scope.allowFileType.indexOf(type) !== -1;
                }
            }
        });

        // fileUploader.onAfterAddingFile = function(item) {
        //     $scope.fileButtonDisabled = true;
        // };

        fileUploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
            SIEJS.alert('仅支持'+$scope.allowFileType+'文件格式！！', "error", "确定");
        };

        //上传后调用的方法
        fileUploader.onCompleteItem = function (fileItem, res, status, headers) {
            // console.log(fileItem);
            console.log(res);
            if(status!=200){
                SIEJS.alert('上传失败', 'error', '确定');
                // $scope.fileButtonDisabled = false;
                return ;
            }

            if(res.status!='S'){
                // $scope.fileButtonDisabled = false;
                if(!res.msg) return ;
                SIEJS.alert(res.msg, 'error', '确定');
                return ;
            }
            if(sessionStorage[appName + '_successLoginInfo']==undefined || sessionStorage[appName + '_successLoginInfo']==null || sessionStorage[appName + '_successLoginInfo']==""){
                $scope.userName ="";
            }else {
                $scope.saafsuccessLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
                $scope.userName = $scope.saafsuccessLoginInfo.userName;
            }
            var now = new Date();
            $scope.fileData.push({
                "uploadId":"",
                "userFullName":$scope.userName,
                "fileName": res.data[0].fileName,
                "sourceFileName": res.data[0].fileName, // 文件名称
                "createdByUser": $scope.userName,   // 上传者
                "creationDate": $filter('timeToDate')(res.data[0].uploadDate),
                "fileAddress": res.data[0].accessPath,
                "fileType": res.data[0].fileType,
                "fileSize": $scope.fileSize,
                "sourceCode": $scope.sourceCode,
                "sourceId": $scope.sourceId,
                // "startDateActive": now.getFullYear() + '-' + ((now.getMonth() + 1) > 9 ? (now.getMonth() + 1) : '0' +
                //   (now.getMonth() + 1)) + '-' + (now.getDate() > 9 ? now.getDate() : '0' + now.getDate())
            });
            console.log($scope.fileData);

            //资质文件明细表格增加上传的文件数
            if(document.getElementById("fileUpload").hidden===false&&document.getElementById('batchInfoId').className !== 'active'){

                for(var i = 0; i < $scope.fileData.length; i++){
                    $scope.qaDetailTable.push({
                        "fileName":$scope.fileData[i].fileName,
                        "uploadDate":$scope.fileData[i].creationDate,
                        "fileStatus":$scope.userType==='60'?'IN_APPROVAL':'PASSED',
                        'fileStatusName':$scope.userType==='60'?'待审批':'审批通过',
                        'userFullName':$scope.fileData[i].userFullName,
                        'sourceFileName':$scope.fileData[i].sourceFileName,
                        'createdByUser':$scope.fileData[i].createdByUser,
                        'creationDate':$scope.fileData[i].creationDate,
                        'fileAddress':$scope.fileData[i].fileAddress,
                        'fileType':$scope.fileData[i].fileType,
                        'fileSize':$scope.fileData[i].fileSize,
                        'sourceCode':'PLM_DEVELOPMENT_QA_DETAIL',
                        'sourceId':$scope.productQaFileDataTable[$scope.productQaFileDataTable.selectRow].plmDevelopmentQaSummaryId,
                        'plmDevelopmentQaSummaryId':$scope.productQaFileDataTable[$scope.productQaFileDataTable.selectRow].plmDevelopmentQaSummaryId,
                        'plmProjectId':$scope.headerData.plmProjectId,
                        'plmDevelopmentInfoId':$scope.headerData.plmDevelopmentInfoId,
                        'obId':$scope.headerData.obId,
                        'createdBy':$scope.fileData[i].createdBy,
                        'barcode':$scope.headerData.barcode

                    })
                }

                // $scope.fileButtonDisabled = false;

                if($scope.userType!=='60') {
                    $scope.saveFile();
                }
            }
            else if(document.getElementById('batchInfoId').className === 'active'){
                for(var j = 0; j < $scope.fileData.length; j++){
                    $scope.batchInfoDataTable.push({
                        "fileName":$scope.fileData[j].fileName,
                        "uploadDate":$scope.fileData[j].creationDate,
                        'userFullName':$scope.fileData[j].userFullName,
                        'sourceFileName':$scope.fileData[j].sourceFileName,
                        'createdByUser':$scope.fileData[j].createdByUser,
                        'creationDate':$scope.fileData[j].creationDate,
                        'fileAddress':$scope.fileData[j].fileAddress,
                        'fileType':$scope.fileData[j].fileType,
                        'fileSize':$scope.fileData[j].fileSize,
                        'sourceCode':'PLM_DEVELOPMENT_BATCH_INFO',
                        'sourceId':$scope.headerData.plmDevelopmentInfoId,
                        'plmProjectId':$scope.headerData.plmProjectId,
                        'plmDevelopmentInfoId':$scope.headerData.plmDevelopmentInfoId,
                        'obId':$scope.headerData.obId,
                        'createdBy':$scope.fileData[j].createdBy,
                        'fileAlterType':$scope.fileAlterType

                    })
                }
            }

            // $scope.fileButtonDisabled = false;

            $scope.fileData = [];

        };





        /**********************资质明细 end *********************/

        /**********************批次信息 start *********************/
        $scope.uploadFileAndOpenButton = function (string) {
            $scope.fileData = [];
            $scope.fileAlterType = string;
            // $scope.fileButtonDisabled = true;
            $('#fileUploadButton').click();
        };

        $scope.searchBatchInfo = function () {
            httpServer.post(URLAPI.findPlmDevelopmentBatchInfoInfo, {
                    'params': JSON.stringify({plmDevelopmentInfoId: $scope.plmDevelopmentInfoId}),
                    'pageRows': 1000
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.batchInfoDataTable = res.data;
                        if($scope.batchInfoDataTable.length!==0)
                            oldData.batchInfoList = angular.copy($scope.batchInfoDataTable);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //保存批次信息
        $scope.saveBatchInfo = function (command) {
            for(var i=0; i < $scope.batchInfoDataTable.length; i++){
                if($scope.batchInfoDataTable[i].productionCount===undefined||$scope.batchInfoDataTable[i].productionCount===''){
                    SIEJS.alert('生产数量必填','error','确定');
                    return;
                }
                if($scope.batchInfoDataTable[i].productionBatch===undefined||$scope.batchInfoDataTable[i].productionBatch===''){
                    SIEJS.alert('生产批次必填','error','确定');
                    return;
                }
                if($scope.batchInfoDataTable[i].productionCount!==undefined&&$scope.batchInfoDataTable[i].productionCount!==''&&(isNaN($scope.batchInfoDataTable[i].productionCount)
                     || $scope.batchInfoDataTable[i].productionCount%1 !== 0)){
                    SIEJS.alert($scope.batchInfoDataTable[i].productionCount+'不为整数','error','确定');
                    return;
                }

            }

            var params = {};
            params.plmDevelopmentBatchInfoList = angular.copy($scope.batchInfoDataTable);

            httpServer.post(URLAPI.savePlmDevelopmentBatchInfoInfo, {
                'params': JSON.stringify(params)
            }, function (res) {
                if ('S' == res.status) {
                    // 文件保存成功后，查询信息
                    $scope.searchBatchInfo();
                    if(command!=='SAVE_ALL') {
                        SIEJS.alert('操作成功');
                    }else {
                        $scope.btnSave($scope.plmDevelopmentInfoForm.$invalid,'DEVELOPING')
                    }

                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                SIEJS.alert('保存失败', 'error', '确定');
            })
        };

        $scope.delBatchInfo = function () {
            var index=$scope.batchInfoDataTable.selectRow;
            var plmDevelopmentBatchInfoId = $scope.batchInfoDataTable[index].plmDevelopmentBatchInfoId;

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if(plmDevelopmentBatchInfoId == null || plmDevelopmentBatchInfoId =='' ){
                    $scope.batchInfoDataTable.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!已成功删除数据！", "success");
                    oldData.batchInfoList = $scope.batchInfoDataTable;
                    $scope.$apply();
                }
                else {
                    $scope.deleteBatchRowInfo(index);
                }
            })
        };

        //删除操作
        $scope.deleteBatchRowInfo = function(index){
            httpServer.post(URLAPI.deletePlmDevelopmentBatchInfoInfo, {
                    'params': JSON.stringify($scope.batchInfoDataTable[index])
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.batchInfoDataTable.splice(index, 1);
                        SIEJS.alert("操作成功!已成功删除数据！", "success");
                        $scope.$apply();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };


        /**********************批次信息 end *********************/



    });
});
