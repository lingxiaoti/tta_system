/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('newBreedCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction) {

            /**
             * 新增
             */
            $scope.btnNew = function() {
                iframeTabAction('新增新品种设置','/newBreedDetail/' +'-1');
            }

            /**
             *修改
             * @param row
             */
            $scope.btnModify = function () {
                var row = $scope.dataTable.selectRow;
                iframeTabAction('编辑新品种设置'+row.newbreedSetId,'/newBreedDetail/' + row.newbreedSetId);
            }

            /**
             * 复制往年的数据
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectDeptReturn = function(key, value, currentList) {
                httpServer.post(
                    URLAPI.ttaNewbreedSetHeaderSaveOrUpdateCopy,
                    {
                        'params': JSON.stringify({"deptCode": key})
                    },
                    function (res) {
                        if (res.status == 'S') {
                            var arrays = JSON.parse(res.data.pValue);
                            for (var i=0;i<arrays.length;i++){
                                iframeTabAction('编辑新品种设置:'+ arrays[i].YEAR+'-'+arrays[i].NAME,'/newBreedDetail/' + arrays[i].ID);
                            }

                        }
                        else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('数据获取失败！', "error");
                    }
                );
            }

        }])
})