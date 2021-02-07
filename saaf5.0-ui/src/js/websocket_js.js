$scope.tryTime = 0;//断线重连，已重连次数
$scope.wsApp = function () {
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in $window) {
        var url = "ws:"+URLAPI.wsServer+"websocket/"+$scope.userId+"/"+$scope.device+"/"+$scope.token;
        $scope.websocket = new $window.WebSocket(url);
    } else {
        SIEJS.alert("当前浏览器不支持 websocket！！", "error", "确定");
    }

    //连接发生错误的回调方法
    $scope.websocket.onerror = function(event) {
        if($scope.tryTime == 0){
            SIEJS.alert("WebSocket连接发生错误！！", "error", "确定");
        }
        console.log(event);
    };

    //连接成功建立的回调方法
    $scope.websocket.onopen = function(event) {
        console.log("websocket连接已开启！！！");
        $scope.tryTime = 0;
        $scope.websocketClosedReason = "";
    }

    //接收到消息的回调方法
    $scope.websocket.onmessage = function(event) {
        //
    }

    //连接关闭的回调方法
    $scope.websocket.onclose = function(event) {
        console.log("连接已断开！code:"+event.code+",reason:"+event.reason);
        $scope.websocketClosedReason = "连接已断开！";
        // if(event.code==1001&&event.reason=="The web application is stopping"){
        //     $scope.wsApp();
        // }
        // 重试10次，每次之间间隔10秒
        if ($scope.tryTime < 10) {
            $timeout(function(){
                    $scope.tryTime ++;
                    $scope.wsApp();
                },
                10*1000);
        } else {
            SIEJS.alert("WebSocket重新连接发生错误，请确保当前网络畅通，系统将在5分钟后重新尝试连接，您可以手动进行连接！", "error", "确定");
        }
    }
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    $window.onbeforeunload = function() {
        $scope.closeWebSocket(1000,"关闭或刷新窗口！！！");
    }
}

$scope.wsApp();