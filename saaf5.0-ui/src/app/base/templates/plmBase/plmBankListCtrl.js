'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('plmBankListCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {};
        $scope.groupNameList = [];
        $scope.addparams = {};
        $scope.btnNew=function()
        {
        	$('#banks').modal('show');
        	

        	
        }
        $scope.btnModify=function()
        {
            if ($scope.dataTable.selectRow == null) {
                JS.alert('请您先选中要操作的行!!');
                return;
            }
            var row=$scope.dataTable.selectRow;
            httpServer.post(URLAPI.findPlmseriesInfoById, {
            	"params":JSON.stringify({"id":row.id})
            },
            function (res) {
                if (res.status == 'S') {
                    $scope.addparams = res.data[0]; 
                    $('#banks').modal('show');
                }
            },
            function (err) {
                JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            }
               );
            
        }
        
        $scope.btnSave=function()
        {
            httpServer.post(URLAPI.savePlmseriesInfo, {
                'params': JSON.stringify($scope.addparams)
            }, function (res) {
                if (res.status == 'S') {                  	
                	SIEJS.alert("操作成功", "success", "确定"); 
                	$scope.dataTable.search(1);
                	$('#banks').modal('hide');
               }
                else {
                	SIEJS.alert(res.msg, "error", "确定");

              }
            }, function (err) {
            	SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");                 
           },true);
        }
        
        $scope.btnDel=function()
        {
            if ($scope.dataTable.selectRow == null) {
                JS.alert('请您先选中要删除的行!!');
                return;
            }
            var row=$scope.dataTable.selectRow;
            var id=row.id;
            
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                httpServer.post(URLAPI.delPlmseriesInfoById, {
                  'params': JSON.stringify({"id":id})
                },
                function (res) {
                  if (res.status == 'S') {
                    SIEJS.alert(res.msg, 'success');
                    $scope.dataTable.search(1);
                  } else {
                    SIEJS.alert(res.msg, "error", "确定");
                  }
                },
                function (err) {
                  SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                })
              })
            
        }

  



   
    });
});
