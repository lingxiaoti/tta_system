
define(["app",], function (app) {
  app.controller('productManageIndexCtrl', function ($scope, URLAPI, $rootScope, $state, $stateParams, iframeTabAction, httpServer,SIEJS) {

$scope.goSomewhere = function(index) {
  if(index == 'ob') {
      iframeTabAction('ob新增:', '/addObGoods')
    }else if(index == 'onob') {
      iframeTabAction('非ob新增:','/addNotObGoods/')
    }else if(index == 'myMod') {
      iframeTabAction('我的模板:', '/mytemple')
    }
};
  });
});
