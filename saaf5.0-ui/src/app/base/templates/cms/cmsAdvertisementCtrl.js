/**
 * Created by dengdunxin on 2018/2/3.
 */
'use strict';
define(["app", "angularFileUpload"], function (app) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('cmsAdvertisementCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', 'carouseDel', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, carouseDel) {

        $scope.cmsAdvertisementTable = {};
        $scope.current = {};
        $scope.imgArr = []
        $scope.params = {};
        $scope.params.displayFlag = 'Y'
        $scope.userRespList=JSON.parse(localStorage[window.appName + '_successLoginInfo']).userRespList;
        $scope.buData=[]
        angular.forEach($scope.userRespList,function(data,index){
            if(data.systemCode==window.appName){
                $scope.buData=data.orgBeans;
            }
        })
        //获取当前日期
        $scope.getMyDate = function () {
            var date = new Date()
            var sysDate = date.getFullYear() + "-"
            if ((date.getMonth() + 1) < 10) {
                sysDate = sysDate + "0" + (date.getMonth() + 1) + "-"
            } else {
                sysDate = sysDate + (date.getMonth() + 1) + "-"
            }
            if (date.getDate() < 10) {
                sysDate = sysDate + "0" + date.getDate();
            } else {
                sysDate = sysDate + date.getDate();
            }
            return sysDate;
        }
        //日期加减
        $scope.transDate = function (date, days, operator) {
            date = date.replace(/-/g, "/"); //更改日期格式
            var nd = new Date(date);
            nd = nd.valueOf();
            if (operator == "+") {
                nd = nd + days * 24 * 60 * 60 * 1000;
            } else if (operator == "-") {
                nd = nd - days * 24 * 60 * 60 * 1000;
            } else {
                return false;
            }
            nd = new Date(nd);

            var y = nd.getFullYear();
            var m = nd.getMonth() + 1;
            var d = nd.getDate();
            if (m <= 9) m = "0" + m;
            if (d <= 9) d = "0" + d;
            var cdate = y + "-" + m + "-" + d;
            return cdate;
        }
        $scope.currentstartDateActive = $scope.getMyDate();
        $('#varstartDateActive')
            .on('changeDate', function () {
                $scope.currentstartDateActive = $scope.current.startDateActive;
                $scope.currentstartDateActive = $scope.transDate($scope.currentstartDateActive, 0, '+');
                $('#varendDateActive').datetimepicker('setStartDate', $scope.currentstartDateActive);
            });
        $('#varendDateActive')
            .on('click', function () {
                $scope.currentstartDateActive = $scope.current.startDateActive;
                $scope.currentstartDateActive = $scope.transDate($scope.currentstartDateActive, 0, '+');
                $('#varendDateActive').datetimepicker('setStartDate', $scope.currentstartDateActive);
            });
        $scope.current1startDateActive = $scope.getMyDate();
        $('#endDateActive')
            .on('click', function () {
                $scope.current1startDateActive = $scope.params.startDateActiveFrom;
                $scope.current1startDateActive = $scope.transDate($scope.current1startDateActive, 0, '+');
                $('#endDateActive').datetimepicker('setStartDate', $scope.current1startDateActive);
            });
        //点击新增
        $scope.btnNew = function () {
            $scope.current = {};

            $scope.imgArr = []
            $('#myModal').modal('toggle');
        }
        //修改
        $scope.btnModify = function () {
            $scope.current = $scope.dataTable.selectRow;
            $scope.current.orgId=$scope.current.orgId.toString()=='0'?'':$scope.current.orgId.toString();
            $scope.imgArr = []
            $scope.imgArr.push({accessPath: $scope.current.pictureUrl})
            $('#myModal').modal('toggle');
        }

        //点击保存
        $scope.save = function () {
            $scope.current.cityId = 0;
            if ($scope.current.orderSequence.length >= 10) {
                JS.alert('显示顺序值过大', "error", "确定");
                return
            }
            else {

                if (!$scope.imgArr || $scope.imgArr.length===0){
                    JS.alert('请上传图片', "warning");
                    return;
                }

                $scope.current.pictureUrl = $scope.imgArr[0].accessPath;
                httpServer.post(URLAPI.carouseSave, {params: JSON.stringify($scope.current)}, function (res) {
                    if (res.status == "S") {
                        $scope.dataTable.selectRow=null;
                        $scope.dataTable.search(1);

                        JS.alert(res.msg, 'success', '确定');
                        $('#myModal').modal('toggle');
                    } else {
                        JS.alert(res.msg, 'error', '确定')
                    }
                })
            }
        }
        //切换位置
        $scope.changePosition = function () {
            $.each($scope.current.adviertisementMaintenance, function (n, value) {
                if (value.hasOwnProperty('id'))
                    $scope.current.deletedImages.push({'id': value.id});
            })
            $scope.current.adviertisementMaintenance = [];

        }


        $scope.btnDel = function (item) {
            item  = item || $scope.dataTable.selectRow;
            var id = item.carouselId
            carouseDel({
                params: JSON.stringify({
                    id: id
                })
            }, function (res) {
                if (res.status == "S") {
                    $scope.dataTable.search();
                    JS.alert(res.msg, "success", '确定')
                }
            })
        }

        //图片添加执行方法
        $scope.imgAddAction = function (rows, targetType, imgChannel, returnMessage) {

            var imgMessage = $.extend({}, returnMessage, {
                "targetType": targetType,
                "disabled": "Y",
            })
            rows.push(imgMessage);
        }

        //图片删除执行方法
        $scope.imGdeleteAction = function (rows, row) {

            var index = $.inArray(row, rows)
            if (!$scope.imgArr.hasOwnProperty('deletedImages')) {
                $scope.imgArr.deletedImages = [];
            }
            if (rows[index].hasOwnProperty('id')) {
                $scope.imgArr.deletedImages.push({'id': rows[index].id});
            }

            rows.splice(index, 1);

        }

        $scope.imageClickAction = function (value) {
            if (value == null) {
                JS.alert('请您先选择位置!!', 'warning', '确定');
                return false
            }
        };


    }]);
});
