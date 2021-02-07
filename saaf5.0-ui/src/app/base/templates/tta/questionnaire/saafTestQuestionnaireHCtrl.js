/**
 * Created by zl on 2019/2/16.
 */
define(["app"], function (app) {
    app.controller('saafTestQuestionnaireHCtrl', ['$scope','$rootScope', 'httpServer', 'URLAPI', 'SIE.JS', '$http', '$state', 'iframeTabAction', function ($scope,$rootScope, httpServer, URLAPI, JS, $http, $state, iframeTabAction) {

        $scope.current={};
        $scope.publish={};


        $scope.btnNew=function(){
            $scope.current={
                "status":"NEW",
                "startDateActive":$rootScope.$getToday(),
                "breakPointAnswer":"Y",
                "bgImagePath1":[]
            };
            $scope.allowUpdate=true;
            $('#myModal').modal('toggle');
        }

        $scope.btnModify=function(){
            if ($scope.dataTable.selectRow==null) {
                swal('请您先选中要修改的行!!');
                return;
            }
            $scope.searchLine($scope.dataTable.selectRow);
            $scope.allowUpdate=true;
        }

        $scope.save=function(){

            $("#saveButton").attr("disabled");
            if($scope.current.bgImagePath1 && $scope.current.bgImagePath1.length>=1){
                $scope.current.bgImagePath=$scope.current.bgImagePath1[0].accessPath;
                $scope.current.bgImagePath1=[];
            }

            httpServer.post(URLAPI.saveSaafTestQuestionnaireH, {
                params: JSON.stringify($scope.current)
            }, function (res) {
                if (res.status == 'S') {
                    JS.alert("保存成功", "success", "确定");
                    $("#saveButton").removeAttr("disabled");
                    $('#myModal').modal('toggle');
                    $scope.dataTable.search();
                }
                else {
                    JS.alert(res.msg, "error", "确定");
                    $("#saveButton").removeAttr("disabled");
                }
            }, function (error) {
            })
        }

        //图片删除执行方法
        $scope.deleteImgAction = function (dataValue, row) {
            var rows = dataValue.bgImagePath1;
            var index = $.inArray(row, rows);
            if (!$scope.current.hasOwnProperty('deletedImages')) {
                $scope.current.deletedImages = [];
            }

            if (rows[index].hasOwnProperty('id')) {
                $scope.current.deletedImages.push({'id': rows[index].id});
            }
            rows.splice(index, 1);
        }

        //图片添加执行方法
        $scope.addImgAction = function (rows, targetType, imgChannel, returnMessage) {
            var imgMessage = $.extend({}, returnMessage, {
                "targetId": '',
                "targetType": targetType,
                "disabled": "N",
                'imgChannel': imgChannel
            });
            //rows=imgMessage;
            $scope.current.bgImagePath1 = [imgMessage];

        }



        $scope.searchLine=function(index){
            $scope.allowUpdate=false;
            $scope.current=$scope.dataTable.selectRow;

            if ($scope.current.bgImagePath != null) {
                $scope.current.bgImagePath1 = [{'accessPath':$scope.current.bgImagePath}];
            } else {
                $scope.current.bgImagePath1 = [];
            }
            $('#myModal').modal('toggle');
        }

        $scope.modifyInfo=function(row){
            row = row || $scope.dataTable.selectRow;
            iframeTabAction('问卷行编辑', 'saafTestQuestionnaireL/Y/' + row.testQnHeadId);
        }

        $scope.checkInfo=function(row){
            row = row || $scope.dataTable.selectRow;
            console.log(row);
            $state.go("saafQuestionnaireDetail", {qnHeadId: row.qnHeadId, allowUpdate: 'N'});
        }

        $scope.copyInfo=function(row){
            JS.confirm('复制','是否复制问卷？','确定',function() {
                httpServer.post(URLAPI.updateVrQuestionToCopy,{
                    params: JSON.stringify({
                        "qnHeadId": row.qnHeadId
                    })
                }, function (res) {
                    if (res.status === 'S') {
                        JS.alert("复制成功!", "success", "确定");
                        $scope.dataTable.getData();
                    } else {
                        JS.alert(res.msg, "error", "确定");
                    }
                });
            })
        }

        $scope.delete=function(row){
            JS.confirm('删除','是否确认删除该条问卷？','确定',function() {
                httpServer.post(URLAPI.deleteSaafQuestionnaireH,{
                    params: JSON.stringify({
                        "qnHeadId": row.qnHeadId
                    })
                }, function (res) {
                    if (res.status === 'S') {
                        JS.alert("删除成功!", "success", "确定");
                        $scope.dataTable.search();
                    } else {
                        JS.alert(res.msg, "error", "确定");
                    }
                });
            })
        }

    }]);
});