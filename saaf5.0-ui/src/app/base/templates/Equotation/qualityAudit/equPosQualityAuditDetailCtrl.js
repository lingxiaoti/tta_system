/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('equPosQualityAuditDetailCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'iframeTabAction','$http','SIEJS', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, iframeTabAction,$http,SIEJS) {
        var id = $stateParams.qualityAuditId;
        $scope.program = {};
        $scope.rowsIndex = 5;

        $scope.disabledFlag = false;
        $scope.showFlag = true;
        $scope.TPShowFlag = true;
debugger;
        //当前登录人所属部门
        $scope.program.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.program.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;

        $scope.program.qualityAuditStatus = 'DRAFT';

        /********************查询质量审核单据信息**********************/
        $scope.search = function (id) {
            httpServer.post(URLAPI.findSupplierQualityAudit, {
                params: JSON.stringify({
                    qualityAuditId: id
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.program = res.data[0];
                    if($scope.program.qualityAuditStatus == 'APPROVAL'){
                        $scope.readonlyFlag = true;
                        $scope.showFlag = false;
                    }else{
                        //更新信用审核信息
                        httpServer.post(URLAPI.findEquPosCreditAuditInfo, {
                            params: JSON.stringify({"supplierId" : $scope.program.supplierId})
                        }, function (res) {
                            if (res.status == 'S') {
                                if(res.data&&res.data.length>0){
                                    $scope.program.creditAuditNumber = res.data[0].supCreditAuditCode;
                                    $scope.program.creditAuditScoreMeaning = res.data[0].creditAuditScoreName;
                                    // $scope.program.creditAuditResuleMeaning = res.data[0].creditAuditResuleName;
                                    $scope.program.creditAuditResule = res.data[0].creditAuditResule;
                                    $scope.program.specialResults = res.data[0].specialResults;
                                    $scope.program.isSpecial = res.data[0].isSpecial;
                                    if($scope.program.creditAuditResule == 'Y'){
                                        $scope.program.creditAuditResuleMeaning = "合格";
                                    }else{
                                        if($scope.program.isSpecial == 'Y'){
                                            if($scope.program.specialResults == 'Y'){
                                                $scope.program.creditAuditResuleMeaning = "特批合格";
                                            }else{
                                                $scope.program.creditAuditResuleMeaning = "特批不合格";
                                            }
                                        }else {
                                            $scope.program.creditAuditResuleMeaning = "不合格";
                                        }
                                    }
                                }else{
                                    $scope.program.creditAuditNumber = undefined;
                                    $scope.program.creditAuditScoreMeaning = undefined;
                                    $scope.program.creditAuditResuleMeaning = undefined;
                                    $scope.program.creditAuditResule = undefined;
                                    $scope.program.specialResults = undefined;
                                }
                            }
                        }, function (error) {
                            console.error(error)
                        })
                    }
                    if($scope.program.qualityAuditStatus == 'APPROVAL'){
                        $scope.TPShowFlag = true;
                    }else if($scope.program.sceneType == '20' && $scope.program.qualityAuditResult == '20'){
                        $scope.TPShowFlag = false;
                    }else{
                        $scope.TPShowFlag = true;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        };

        /********************判断是新增还是修改********************/
        if (id == "" || id == undefined) {

        } else {
            $scope.search(id);
        };

        /********************保存质量审核单据信息********************/
        $scope.btnSave = function () {
            // if($scope.validate()){
            //     return;
            // };
            httpServer.post(URLAPI.saveSupplierQualityAudit, {
                'params': JSON.stringify($scope.program)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.program = res.data;
                    $scope.search($scope.program.qualityAuditId);
                    JS.alert('保存成功');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('保存失败', 'error', '确定');
            })
        }

        /********************质量审核单据特批********************/
        $scope.btnTP = function () {
            iframeTabAction('供应商入库临时特批审核新增', 'editTempSpecial/')
        }

        $scope.validate = function(){
            //校验业务类型是否为空
            if($scope.program.sceneType == "" || $scope.program.sceneType == null || $scope.program.sceneType == undefined){
                JS.alert('请选择业务类型', 'error', '确定');
                return true;
            }

            //校验供应商是否为空
            if($scope.program.supplierName == "" || $scope.program.supplierName == null || $scope.program.supplierName == undefined){
                JS.alert('请选择供应商', 'error', '确定');
                return true;
            }

            //校验资质审核单号是否为空
            if($scope.program.qualificationAuditNumber == "" || $scope.program.qualificationAuditNumber == null || $scope.program.qualificationAuditNumber == undefined){
                JS.alert('请选择资质审核单号', 'error', '确定');
                return true;
            }

            //校验信用审核单号是否为空
            if($scope.program.creditAuditNumber == "" || $scope.program.creditAuditNumber == null || $scope.program.creditAuditNumber == undefined){
                JS.alert('信用审核单号不能为空', 'error', '确定');
                return true;
            }

            //校验信用审核分数是否为空
            if($scope.program.creditAuditScoreMeaning == "" || $scope.program.creditAuditScoreMeaning == null || $scope.program.creditAuditScoreMeaning == undefined){
                JS.alert('信用审核分数不能为空', 'error', '确定');
                return true;
            }

            //校验信用审核结果是否为空
            if($scope.program.creditAuditResuleMeaning == "" || $scope.program.creditAuditResuleMeaning == null || $scope.program.creditAuditResuleMeaning == undefined){
                JS.alert('信用审核结果不能为空', 'error', '确定');
                return true;
            }

            //校验质量审核分数是否为空
            if($scope.program.qualityAuditScore == "" || $scope.program.qualityAuditScore == null || $scope.program.qualityAuditScore == undefined){
                JS.alert('请填写质量审核分数', 'error', '确定');
                return true;
            }

            //校验质量审核结果是否为空
            if($scope.program.qualityAuditResult == "" || $scope.program.qualityAuditResult == null || $scope.program.qualityAuditResult == undefined){
                JS.alert('请填写质量审核结果', 'error', '确定');
                return true;
            }

            //校验审核CAP报告是否为空
            if($scope.program.auditCapReportId == "" || $scope.program.auditCapReportId == null || $scope.program.auditCapReportId == undefined){
                JS.alert('请上传审核CAP报告', 'error', '确定');
                return true;
            }

            //当是质量审核结果为“合格”，校验有效期限是否为空
            if($scope.program.qualityAuditResult == "10"){
                if($scope.program.effectiveDate == "" || $scope.program.effectiveDate == null || $scope.program.effectiveDate == undefined){
                    JS.alert('请填写有效期限', 'error', '确定');
                    return true;
                }
            }

            //校验信用审核结果是否特批不合格
            if($scope.program.creditAuditResule == 'N' && $scope.program.specialResults == 'N'){
                JS.alert('信用审核结果不合格，不允许提交！', 'error', '确定');
                return true;
            }

            //校验业务类型为全新制造工厂准入OEM(20)，跨部门制造工厂准入OEM时(40)时，供应商状态是否为在审状态(APPROVING)
            if($scope.program.sceneType == '20' || $scope.program.sceneType == '40'){
                if($scope.program.supplierStatus != 'APPROVING' && $scope.program.supplierStatus != 'TEMPLATE'){
                    JS.alert('业务类型与供应商状态不匹配，请处理！', 'error', '确定');
                    return true;
                }
            }

            //校验业务类型为部门内制造工厂新增品类准入OEM(50)、，供应商状态是否为合格状态(QUALIFIED)
            if($scope.program.sceneType == '50'){
                if($scope.program.supplierStatus != 'QUALIFIED'){
                    JS.alert('业务类型与供应商状态不匹配，请处理！', 'error', '确定');
                    return true;
                }
            }
        }

        /********************提交质量审核单据信息********************/
        $scope.btnSubmit = function () {
            if($scope.validate()){
                return;
            }

            if($scope.program.creditAuditResule != 'Y' && $scope.program.specialResults != 'Y'){
                JS.alert('信用审核结果不合格，不能提交！', 'error', '确定');
                return;
            }

            httpServer.post(URLAPI.saveSupplierQualityAudit, {
                'params': JSON.stringify($scope.program)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.program = res.data;
                    //提交质量审核单据
                    httpServer.post(URLAPI.submitSupplierQualityAudit, {
                        'params': JSON.stringify($scope.program)
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.search($scope.program.qualityAuditId);
                            JS.alert('提交成功');
                        } else {
                            JS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        JS.alert('提交失败', 'error', '确定');
                    })
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('提交失败', 'error', '确定');
            })
        }


        //选择供应商
        $scope.selectSupplierInfo = function (value) {
            $scope.supplierSelectParams = {"deptCode": $scope.program.deptCode};
            $('#selectSupplierLov').modal('toggle')
        }

        //选择供应商-回调
        $scope.selectSupplierReturn = function (key, value, currentList) {
            $scope.program.supplierName = currentList[0].supplierName;
            $scope.program.supplierNumber = currentList[0].supplierNumber;
            $scope.program.supplierId = currentList[0].supplierId;
            $scope.program.supplierStatus = currentList[0].supplierStatus;
            $scope.program.qualificationAuditNumber = undefined;
            $scope.program.qualificationStatusMeaning = undefined;

            var csrFileId = currentList[0].csrFileId; //csr文件id
            //判断供应商基础信息里是否维护了有效的csr报告
            debugger;
            if(csrFileId > 0){
                //勾选是否豁免
                $scope.program.isExemption = 'Y';
                //csr审核结果默认“合格”
                $scope.program.csrAuditResult = '10';
                $scope.program.csrReportId = currentList[0].csrFileId;
                $scope.program.csrReportName = currentList[0].csrFileName;
                $scope.program.csrReportPath = currentList[0].csrFilePath;
            }else{
                $scope.program.isExemption = 'N';
                $scope.program.csrAuditResult = '';
                $scope.program.csrReportId = null;
                $scope.program.csrReportName = null;
                $scope.program.csrReportPath = null;
            }
            //查询供应商信用审核单据信息
            debugger;
            httpServer.post(URLAPI.findEquPosCreditAuditInfo, {
                params: JSON.stringify({"supplierId" : $scope.program.supplierId})
            }, function (res) {
                if (res.status == 'S') {
                    if(res.data&&res.data.length>0){
                        $scope.program.creditAuditNumber = res.data[0].supCreditAuditCode;
                        $scope.program.creditAuditScoreMeaning = res.data[0].creditAuditScoreName;
                        // $scope.program.creditAuditResuleMeaning = res.data[0].creditAuditResuleName;
                        $scope.program.creditAuditResule = res.data[0].creditAuditResule;
                        $scope.program.specialResults = res.data[0].specialResults;
                        $scope.program.isSpecial = res.data[0].isSpecial;
                        if($scope.program.creditAuditResule == 'Y'){
                            $scope.program.creditAuditResuleMeaning = "合格";
                        }else{
                            if($scope.program.isSpecial == 'Y'){
                                if($scope.program.specialResults == 'Y'){
                                    $scope.program.creditAuditResuleMeaning = "特批合格";
                                }else{
                                    $scope.program.creditAuditResuleMeaning = "特批不合格";
                                }
                            }else {
                                $scope.program.creditAuditResuleMeaning = "不合格";
                            }
                        }
                    }else{
                        $scope.program.creditAuditNumber = undefined;
                        $scope.program.creditAuditScoreMeaning = undefined;
                        $scope.program.creditAuditResuleMeaning = undefined;
                        $scope.program.creditAuditResule = undefined;
                        $scope.program.specialResults = undefined;
                    }
                }
            }, function (error) {
                console.error(error)
            })
        }

        //选择供应商资质审核单据
        $scope.getQualificationCode = function () {
            if($scope.program.sceneType == null || $scope.program.sceneType == "" || $scope.program.sceneType == undefined){
                JS.alert('请先选择业务类型！', 'error', '确定');
                return;
            }
            if($scope.program.supplierId == null || $scope.program.supplierId == "" || $scope.program.supplierId == undefined){
                JS.alert('请先选择供应商！', 'error', '确定');
                return;
            }
            $scope.qualificationParams = {"supplierId": $scope.program.supplierId,"sceneType":$scope.program.sceneType,"qualificationStatus":"50","isLov":"Y","queryType":"QUALITY"};
            $('#selectQualificationLov').modal('toggle')
        }

        //选择供应商资质审核单据-回调
        $scope.selectQualificationReturn = function (key, value, currentList) {
            $scope.program.qualificationAuditNumber = currentList[0].qualificationNumber;
            $scope.program.qualificationStatusMeaning = currentList[0].qualificationStatusMeaning;
        }

        //上传-审核CAP报告-附件
        $scope.uploadAuditCapReportFile = function () {
            debugger;
            var fd = new FormData();
            var file = document.querySelector('#auditCapReportFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.program.auditCapReportId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.program.auditCapReportId = response.data[0].fileId;
                $scope.program.auditCapReportName = response.data[0].fileName;
                $scope.program.auditCapReportPath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-审核CAP报告-附件
        $scope.changeAuditCapReportFile = function () {
            $scope.program.auditCapReportId = null;
            $scope.program.auditCapReportPath = null;
            $scope.program.auditCapReportName = null;
        }

        //上传-CAP整改汇总-附件
        $scope.uploadCapRectificationSummaryFile = function () {
            var fd = new FormData();
            var file = document.querySelector('#capRectificationSummaryFile').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.program.capRectificationSummaryId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.program.capRectificationSummaryId = response.data[0].fileId;
                $scope.program.capRectificationSummaryName = response.data[0].fileName;
                $scope.program.capRectificationSummaryPath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        //重新上传-CAP整改汇总-附件
        $scope.changeCapRectificationSummaryFile = function () {
            $scope.program.capRectificationSummaryId = null;
            $scope.program.capRectificationSummaryPath = null;
            $scope.program.capRectificationSummaryName = null;
        }
    }]);
});
