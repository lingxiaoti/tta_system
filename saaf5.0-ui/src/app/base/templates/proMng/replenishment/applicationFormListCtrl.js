define(["app","angularFileUpload"], function (app) {
  app.useModule('angularFileUpload');//按需加载弹出层模块
  app.controller('applicationFormListCtrl', function ($scope, URLAPI, $rootScope, $state, $stateParams, iframeTabAction, httpServer,FileUploader,SIEJS) {
      //do something here
      $scope.params={}
      //搜索
      $scope.search = function () {
          $scope.dataTable.search(1);
      }
     
      $scope.btnDetail = function (item) {
        item = item || $scope.dataTable.selectRow;
        iframeTabAction('新增申请单:' , '/newApplicationForm/' + item.plmSupplementHeadId)
       }


       // 新增
       $scope.btnNew = function() {
        iframeTabAction('新增申请单:' , '/newApplicationForm/')
       }
       
       
      
      



      // 查找正运行窗体是否为当前窗口，若是执行一次查询
      // setInterval(function () {
      //     let isUpcomingActive = false;
      //     angular.forEach(window.parent.saafrootScope.saafHeadTab,function (value, key) {
      //         if(value.active===true&&value.name==='待处理商品'){
      //             isUpcomingActive = true;
      //         }else if(value.active===false) {
      //             isUpcomingActive = false;
      //         }
      //     });
      //     if($scope.windowActive===false&&isUpcomingActive===true){
      //         $scope.dataTable.search(1);
      //         $scope.windowActive = true;
      //     }
      // },2000);
      
      
          
  });
});
