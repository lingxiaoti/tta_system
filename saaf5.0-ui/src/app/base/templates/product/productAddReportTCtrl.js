/**
 * Created by zhuchaopeng on 2018/6/15.
 */
define(["app"], function (app) {
    app.controller('productAddReportTCtrl', ['$scope', 'URLAPI', '$rootScope', 'iframeTabAction','$state', '$stateParams', "SIE.JS", 'httpServer',function saafTableController($scope, URLAPI, $rootScope,iframeTabAction, $state, $stateParams,JS, httpServer) {
        $scope.dataTable = {}
        
        //今天的时间
        var day2 = new Date();
        day2.setTime(day2.getTime());
        
        var month=day2.getMonth()+1;
        var day=day2.getDate();
        if(day<10)
        	{
               day="0"+day.toString();        	
        	}
        $scope.s2 = day2.getFullYear().toString()+"-" + month.toString() + "-" + day.toString();
        $scope.params={
        		creationbegin:$scope.s2
        };
        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
        }

//        $scope.btnDetail = function (item) {
//         	item = item || $scope.dataTable.selectRow; 
//                iframeTabAction('商品修改详情:' + item.productName , '/productDetails/'+ item.productHeadId)         
//         }


    }]);
});
