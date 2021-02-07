/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('ttaConAttrLineFileCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction','saafLoading','$http',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction,saafLoading,$http) {
            $scope.fileInfo = {};

            //新增附件
            $scope.btnNew = function () {
                $scope.fileInfo = {};
                $("#fileUpLoad").val('');
                $('#fileLov').modal('toggle');
            };

            //选择部门
            $scope.getOrgCode = function () {
                $('#selectOrgId').modal('toggle')
            };
            //选择部门
            $scope.getDeptCode = function () {
                $('#selectDeptId').modal('toggle')
            };

            //点击确认按钮currentNodeData
            $scope.selectOrgReturn = function (key, value, currentList) {
                    $scope.fileInfo.orgCode = currentList[0].departmentCode;
                    $scope.fileInfo.orgName = currentList[0].departmentName;
            };

            //点击确认按钮currentNodeData
            $scope.selectDeptReturn = function (key, value, currentList) {
                $scope.fileInfo.deptCode = currentList[0].departmentCode;
                $scope.fileInfo.deptName = currentList[0].departmentName;
            };


        $scope.downFileEvent = function (row) {
            //console.log(msg);
            if (row.fileId == undefined || row.fileId == null){
                SIEJS.alert("当前没有选中一行数据,不能下载",'warning','确定');
                return;
            }
            var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + row.fileId;
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        };
        
        //上传附件
        $scope.saveFile = function (invalid) {
            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            var loading = $("#saafLoading");
            if (loading.length === 0) {
                var loading = $('<div id="saafLoading"><div  style="position: absolute; top:50%;left:50%; text-align: center;margin: 0 auto; width:100px;"><img src="img/loading1.gif"></div></div>');
                loading.css({
                    position: "fixed",
                    top: 0,
                    width: "100%",
                    "z-index": 9000,
                    "height": "100%",
                    'display': 'none'
                });
                loading.prependTo(angular.element('body'));
            } else {
                loading.removeAttr('data-loading');
            }
            loading.show();
            var f = $scope.myFile;
            var fd = new FormData();
            var file = document.getElementById('fileUpLoad').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                loading.hide();
                return;
            }
            var fileName = file.name;
            fd.append('file', file);
            fd.append("fileName", fileName);
            fd.append("functionId", "tta_con_attr_line_code");
            fd.append("fileInfo", JSON.stringify($scope.fileInfo));
            $http.post(URLAPI.ttaConAttrLineDeptUploadFile, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                if (response.status == 'S') {
                   // $('#addFileUpload').modal('toggle');
                    loading.hide();
                   // $scope.cWUploadRow.sourceFileName = response.data[0].sourceFileName ;
                   // $scope.cWUploadRow.fileId = response.data[0].fileId ;
                    $('#fileLov').modal('hide');
                    $scope.dataTable.search(1);
                    SIEJS.alert(response.msg, 'success', '确定');

                } else {
                    loading.hide();
                    SIEJS.alert(response.msg, 'error', '确定');
                }
            }).error(function(response) {
                loading.hide();
                SIEJS.alert("上传失败", 'error', '确定');
            });
        };

            $scope.btnDel = function () {
                var row = $scope.dataTable.selectRow;
                SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                    httpServer.post(
                        URLAPI.ttaConAttrLineDeleteInfo,

                        {
                            'params': JSON.stringify({
                                "conAttrLineId": row.conAttrLineId,
                            })
                        },
                        function (res) {
                            if (res.status.toLowerCase() == 'e') {
                                SIEJS.alert(res.msg, "error");
                            } else {
                                SIEJS.alert(res.msg, "success");
                                $scope.dataTable.search(1);

                            }
                        }
                    )
                });
            };

        }])
});