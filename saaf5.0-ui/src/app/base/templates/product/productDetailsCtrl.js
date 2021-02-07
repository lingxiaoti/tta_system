'use strict';
define(['app',"angularFileUpload"],function(app) {
  app.useModule('angularFileUpload');//按需加载弹出层模块
  app.controller('finishProcurementCtrl',['$scope', 'httpServer', 'URLAPI', '$timeout','SIE.JS', '$compile', '$stateParams','FileUploader','iframeTabAction',
    function($scope, httpServer, URLAPI, $timeout, SIEJS, $compile, $stateParams,FileUploader,iframeTabAction) {
      // 变量参数
      $scope.params = {};
      $scope.paramsDrug = {}; // 药品属性
      $scope.paramsMedical = {}; // 医疗器械属性
      $scope.drugfilelist = [
        {
          groupId: '',
          fileType: '药品注册证',
          fileUrl: '',
          certificateType: '',
          certificateNum: '',
          dateType: '',
          selectDate: '',
          flag: ' ',
        },
        {
          groupId: '',
          fileType: '质量标准',
          fileUrl: '',
          certificateType: '',
          certificateNum: '',
          dateType: '',
          selectDate: '',
          flag: ' ',
        },
        {
          groupId: '',
          fileType: '标签备案样式',
          fileUrl: '',
          certificateType: '',
          certificateNum: '',
          dateType: '',
          selectDate: '',
          flag: ' ',
        },
        {
          groupId: '',
          fileType: '说明书备案样式',
          fileUrl: '',
          certificateType: '',
          certificateNum: '',
          dateType: '',
          selectDate: '',
          flag: ' ',
        },
      ]; // 药品资质文件
      $scope.medicalfilelist = [
        {
          groupId: '',
          fileType: '产品技术要求',
          fileUrl: '',
          certificateType: '',
          certificateNum: '',
          dateType: '',
          selectDate: '',
          flag: ' ',
        },
        {
          groupId: '',
          fileType: '合格证明文件',
          fileUrl: '',
          certificateType: '',
          certificateNum: '',
          dateType: '',
          selectDate: '',
          flag: ' ',
        },
        {
          groupId: '',
          fileType: '标签备案样式',
          fileUrl: '',
          certificateType: '',
          certificateNum: '',
          dateType: '',
          selectDate: '',
          flag: ' ',
        },
        {
          groupId: '',
          fileType: '说明书备案样式',
          fileUrl: '',
          certificateType: '',
          certificateNum: '',
          dateType: '',
          selectDate: '',
          flag: ' ',
        },
      ]; //  器械资质文件
      $scope.supplierlist = [], // 供应商信息列表数据
      $scope.barcodelist = [], // 条形码列表数据
      $scope.qalist = [], // 资质文件列表数据
      $scope.picfilelist = [
        {
          picType: 'Logo',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '正面',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '背面',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '侧面',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '已开封（正面）',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '已开封（背面）',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '宝贝详情',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
      ], // 图片文件
      $scope.pricelist = [], // 商品售价
      $scope.placelist = [], // 地点信息关联
      $scope.saleslist = [], // 可销售属性
      $scope.channallist = [], // 线上渠道
      $scope.filelist = [], // 附件
      $scope.modifyLogData = [], // 修日志企
      $scope.modalParams = {}, // 模板数据
      $scope.copyDataParams = {}, // 复制资料数据

      $scope.picfilelistIndex = '', // 图片上传是需要获取当前行数
      $scope.qalistIndex = '', // 资质文件上传需要的当前行数
      $scope.filelistIndex = '', // 附件上传需要的当前行数
      $scope.drugFileIndex = '', // 药品资质上传
      $scope.medFileIndex = '', // 器械资质上传

      $scope.SupplierNameIndex = '', // 供应商赋值获取当前行
      $scope.diDianSupplierNameReturnIndex = '', // 地点供应商赋值获取当前行

      $scope.tabAction = '', // 页签action标识
      $scope.processImageParams = {};// 流程图参数

      var userInfo = JSON.parse(localStorage.getItem('BASE_successLoginInfo')); // 获取用户信息
      
      $scope.submitfalg = 0;
      
      
    // 切换页签
    $scope.tabChangeInfo = function (name) {
      $scope.tabAction = name;
      if (name == 'flowChart') {
        $scope.tabChange('flowChart');
      }
    };

      // ----------------------------------------- 头表多选 ---------------------------------------------


      // 商品种类change
      $scope.productCategeeryChange = function(key) {
        if(key == '1') {
          $scope.productCategeeryChangeFlag = true;
        }else{
          $scope.productCategeeryChangeFlag = false;
        }
      }


      // 获取TierOne
      $scope.getTierOne = function() {
        var areaObj = document.getElementsByName('TierOne');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        var checkedstr = checkedArr.toString();
        checkedstr.replace('[', '').replace(']', '');
        $scope.params.tier1 = checkedstr;
      };

      // 获取storeType
      $scope.getstoreType = function() {
        var areaObj = document.getElementsByName('storeType');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        var checkedstr = checkedArr.toString();
        checkedstr.replace('[', '').replace(']', '');
        $scope.params.storeType = checkedstr;
      };

      // 获取TierTow
      $scope.getTierTow = function() {
        var areaObj = document.getElementsByName('TierTow');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        var checkedstr = checkedArr.toString();
        checkedstr.replace('[', '').replace(']', '');
        $scope.params.tier2 = checkedstr;
      };

      // 获取getTierTFF
      $scope.getTierTow = function() {
        var areaObj = document.getElementsByName('TierTFF');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        var checkedstr = checkedArr.toString();
        checkedstr.replace('[', '').replace(']', '');
        $scope.params.tier345 = checkedstr;
      };

      // 获取getTradeZone
      $scope.getTradeZone = function() {
        var areaObj = document.getElementsByName('TradeZone');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        var checkedstr = checkedArr.toString();
        checkedstr.replace('[', '').replace(']', '');
        $scope.params.tradeZone = checkedstr;
      };

      // allTierChange
      $scope.allTierChange = function() {
        if($scope.params.allTier == 'Y') {
          $('input[name="TierOne"]').attr('disabled', true);
          $('input[name="TierTow"]').attr('disabled', true);
          $('input[name="TierTFF"]').attr('disabled', true);
        }else{
          $('input[name="TierOne"]').attr('disabled', false);
          $('input[name="TierTow"]').attr('disabled', false);
          $('input[name="TierTFF"]').attr('disabled', false);
        }
      };

      $scope.getstoreTypeAll = function () {
        if($scope.storeTypeAll == 'Y') {
          $('input[name="storeType"]').attr('checked', true);
          $timeout(function() {$scope.getstoreType();}, 500)  
        }else{
          $('input[name="storeType"]').attr('checked', false);
          $timeout(function() {$scope.getstoreType();}, 500)  
        }
      };

      $scope.tradeZoneAll = function () {
        if($scope.TradeZoneAll == 'Y') {
          $('input[name="TradeZone"]').attr('checked', true);
          $timeout(function() {$scope.getTradeZone();}, 500)  
        }else{
          $('input[name="TradeZone"]').attr('checked', false);
          $timeout(function() {$scope.getTradeZone();}, 500)  
        }
      };

    // 供应商信息有效期限控制
    $scope.getIndexValidDays = function(index) {
      $scope.getValidDaysIndex = index;
    };
    
    $scope.validDaysChange = function (key) {
      if(key == '0') {
        $scope.supplierlist[$scope.getValidDaysIndex].sxDays = 3650;
        $scope.sxDaysDisabled = $scope.getValidDaysIndex;
      }else{
        // $scope.sxDaysDisabled = false;
        $scope.supplierlist[$scope.getValidDaysIndex].sxDays = '';
      }
    }

    // 条形码单选
    $scope.selectcheckbox=function(index){
      if($scope.barcodelist[index].isMain=='Y'){
        angular.forEach($scope.barcodelist,function(obj,rowindex){
          if(rowindex!=index){
            obj.isMain='N';
            }
        });
      }     
    }
  

      // 查询
      $scope.searchDetails = function () {
    	  //alert("details");
        httpServer.post(URLAPI.findProductById, {
          'params': JSON.stringify({'productHeadId': $stateParams.productHeadId})
        },
        function(res) {
          if (res.status == 'S') {
        	  //$scope.formParams=res.data[1];
            $scope.params = res.data.headInfo;
            $scope.paramsDrug = res.data.drugInfo;
            $scope.paramsMedical = res.data.medicalInfo;
            // $scope.medicalfilelist = res.data.medicalfilelist;
            // $scope.drugfilelist = res.data.drugfilelist;
            $scope.barcodelist = res.data.barcodelist;
            $scope.channallist = res.data.channallist;
            $scope.filelist = res.data.filelist;
           // $scope.packagelist = res.data.packagelist;
            $scope.picfilelist = res.data.picfilelist;
            $scope.placelist = res.data.placelist;
            $scope.pricelist = res.data.pricelist;
            $scope.qalist = res.data.qalist;
            $scope.saleslist = res.data.saleslist;
            $scope.supplierlist = res.data.supplierlist;


            // 记录原始的商品售价数据
            if($scope.pricelist.length>0) {
              $scope.pricelist.forEach((item)=> {
                item['oldUnitPrice'] = item.unitPrice;
              });
            }
            
            // 记录原始的供应商成本价
            if($scope.supplierlist.length>0) {
              $scope.supplierlist.forEach((item)=> {
                if(item.price) {
                  item['oldPrice'] = item.price;
                }else{
                  item['oldPrice'] = '';
                }
              });
            }
            

            // if(res.data.drugfilelist.length == 0) {
            //   console.log($scope.drugfilelist);
            // }else {
            //   $scope.drugfilelist = res.data.drugfilelist;
            // }

            // if(res.data.medicalfilelist.length == 0) {
            //   console.log($scope.medicalfilelist);
            // }else {
            //   $scope.medicalfilelist = res.data.medicalfilelist;
            // }
            

            

            // ob商品需执行的操作
            if($scope.params.obId) {
              $scope.isOB = true;
              $scope.getObHeadInfo();
            }
            // 季节性商品默认为NO
            if(!$scope.params.sesionProduct) {
              $scope.params.sesionProduct = '0';
            }
            // allTier为Y时，默认禁用
            if($scope.params.allTier == 'Y') {
              $('input[name="TierOne"]').attr('disabled', true);
              $('input[name="TierTow"]').attr('disabled', true);
              $('input[name="TierTFF"]').attr('disabled', true);
            }

            // 售价分组的控制
            if($scope.params.otherInfo == '1') {
              $scope.addpriceCtrl = '1';
            }else {
              $scope.addpriceCtrl = '0';
            }

            // 商品种类pog控制
            if($scope.params.productCategeery == '1') {
              $scope.productCategeeryChangeFlag = true;
            }else{
              $scope.productCategeeryChangeFlag = false;

            }

            $scope.getModifyData();

            // var drugBaseList = [
            //   {type: 'text', name: '供应商名称',key: 'supplierName', value: ''},
            //   {type: 'lookupCode', name: '销售渠道',key: 'markerChannel', value: '', lookupCode: 'PLM_PRODUCT_SALES_CHANNAL'},
            //   {type: 'search', name: '部门编码',key: 'department', value: ''},
            //   {type: 'text', name: '部门描述',key: 'departmentDesc', value: ''},
            //   {type: 'text', name: '分类描述',key: 'classDesc', value: ''},
            // ];
            // for(var s= 0 ;s<drugBaseList.length;s++){
            //   if(drugBaseList[s].type == 'text') {
            //     var drugTextHtml = '<div class="form-group col-xs-4 col-md-3"> <div class="input-group input-group-xs"> <label class="input-group-addon bn"> <span class="w100">' + drugBaseList[s].name + ':</span> </label> <input class="form-control radius3"  ng-model="drugBaseList['+s+'].value" type="text"></div></div>';
            //     $('#drugBaseInfo').append(drugTextHtml);
            //   }
            //   if(drugBaseList[s].type == 'lookupCode') {
            //     var druglookupCodeHtml = '<div class="form-group col-xs-4 col-md-3"> <div class="input-group input-group-xs"> <label class="input-group-addon bn"> <span class="w100">' + drugBaseList[s].name + ':</span> </label> <div lookup-code="'+drugBaseList[s].lookupCode+'" name="'+drugBaseList[s].key+'" ng-model="drugBaseList['+s+'].value"></div></div></div>';
            //     var html = $compile(druglookupCodeHtml)($scope);
            //     $('#drugBaseInfo').append(html);
            //   }
            //   if(drugBaseList[s].type == 'search') {
            //     var drugsearchHtml = '<div class="form-group col-xs-4 col-md-3"> <div class="input-group input-group-xs"> <label class="input-group-addon bn"> <span class="w100">' + drugBaseList[s].name + ':</span> </label> <div class="input-group input-group-xs"> <input class="form-control radius3" ng-model="drugBaseList['+s+'].value" name ="department" type="text"/> <span class="input-group-btn ng-scope"> <button class="btn btn-default" ng-click="getDepCode('+drugBaseList[s].key+')" type="button"> <i class="fa fa-search"></i> </button> </span> </div></div></div>';
            //     var html = $compile(drugsearchHtml)($scope);
            //     $('#drugBaseInfo').append(html);
            //   }
            // }
            
            // $('#drugBaseInfo').append();
          }
        })
      };
      
      // 修改日志查询
      $scope.getModifyData = function () {
        httpServer.post(URLAPI.processFindHistoricActivities, {
          'params': JSON.stringify({'processInstanceId': $scope.params.processInstanceid})
        },
        function(res) {
          if (res.status == 'S') {
            let innerArr = [];
            let innerArr2 = [];
            innerArr.push(res.data[1]);
            res.data.forEach((item, index) => {
              if(item.his_detail_option == '驳回') {
                innerArr2.push(item);
              }
            });
            if(innerArr2.length>0) {
              $scope.modifyLogData = innerArr.concat(innerArr2);
            }else{
              innerArr.push(res.data[res.data.length - 2]);
              $scope.modifyLogData = innerArr;
            }
          }
        })
      };

      // 自动查询
      if(true) {
        $scope.searchDetails();
      }
      // if(true) {
      //   $scope.params.otherInfo = '1';
      // }
      // 保存
      $scope.btnSave = function () {
        var allData = {
          'headInfo': $scope.params,
          'drugInfo': $scope.paramsDrug,
          'medicalInfo': $scope.paramsMedical,
          'medicalfilelist': $scope.medicalfilelist,
          'drugfilelist': $scope.drugfilelist,
          'supplierlist': $scope.supplierlist,
          'barcodelist': $scope.barcodelist,
          'qalist': $scope.qalist,
          'picfilelist': $scope.picfilelist,
          //'packagelist': $scope.packagelist,
          'pricelist': $scope.pricelist,
          'placelist': $scope.placelist,
          'saleslist': $scope.saleslist,
          'channallist': $scope.channallist,
          'filelist': $scope.filelist
        }
        httpServer.post(URLAPI.saveProductInfo, {
          'params': JSON.stringify(allData)
        },
        function (res) {
          if (res.status == 'S') {
            SIEJS.alert(res.msg, 'success');
            $scope.searchDetails();
          } else {
              SIEJS.alert(res.msg, "error", "确定");
          }
        },
        function (err) {
          SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
      })},
      // 提交
      $scope.btnSubmit = function () {
        if($scope.supplierlist.length==0){
          SIEJS.alert('供应商信息不能为空', "error", "确定");
          return;
        }else {
          var noEmFlag = 0;
          $scope.supplierlist.forEach((item) => {
            if(item.supplierName == '') {
             noEmFlag = 1;
            }
            if(!item.sxWay||item.sxWay == '') {
              noEmFlag = 2;
            }
            if(!item.productLength||item.productLength == '') {
              noEmFlag = 3;
            }
            if(!item.productBreadth||item.productBreadth == '') {
              noEmFlag = 4;
            }
            if(!item.productHeight||item.productHeight == '') {
              noEmFlag = 5;
            }
            if(!item.validDays||item.validDays == '') {
              noEmFlag = 6;
            }
            if(!item.sxDays||item.sxDays == '') {
              noEmFlag = 7;
            }
            if(!item.place||item.place == '') {
              noEmFlag = 8;
            }
            if(!item.productively||item.productively == '') {
              noEmFlag = 9;
            }
            if(item.sxWay == '3'&&item.placeNote == '') {
              noEmFlag = 10;
            }
            if(item.sxWay == '5'&&item.area == '') {
              noEmFlag = 11;
            }
            if(!item.isMainsupplier||item.isMainsupplier == ''||item.isMainsupplier==undefined) {
              noEmFlag = 12;
             }
             if(item.PriceFlag&&(!item.updateSeson||item.updateSeson== ''||item.updateSeson == undefined)) {
              noEmFlag = 13;
             }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请选择供应商名称', "error", "确定");
            return;
          }
          if(noEmFlag == 2){
            SIEJS.alert('请选择生效方式', "error", "确定");
            return;
          }
          if(noEmFlag == 3){
            SIEJS.alert('请输入商品深', "error", "确定");
            return;
          }
          if(noEmFlag == 4){
            SIEJS.alert('请输入商品宽', "error", "确定");
            return;
          }
          if(noEmFlag == 5){
            SIEJS.alert('请输入商品高', "error", "确定");
            return;
          }
          if(noEmFlag == 6){
            SIEJS.alert('请输入有效期限（保质期）', "error", "确定");
            return;
          }
          if(noEmFlag == 7){
            SIEJS.alert('请输入保质期天数', "error", "确定");
            return;
          }
          if(noEmFlag == 8){
            SIEJS.alert('请输入产地', "error", "确定");
            return;
          }
          if(noEmFlag == 10){
            SIEJS.alert('请输入地点清单', "error", "确定");
            return;
          }
          if(noEmFlag == 11){
            SIEJS.alert('请选择区域', "error", "确定");
            return;
          }
          if(noEmFlag == 12){
            SIEJS.alert('请选择优先供应商', "error", "确定");
            return;
          }
          if(noEmFlag == 13){
            SIEJS.alert('请选择修改成本价原因', "error", "确定");
            return;
          }
        }
        
        
        if ($scope.barcodelist.length==0) {
          SIEJS.alert('条码信息不能为空', "error", "确定");
          return;
        }else{
          var noEmFlag = 0;
          $scope.barcodelist.forEach((item) => {
            if(item.barcode == '') {
             noEmFlag = 1;
            }
            if(item.isMain == '') {
              noEmFlag = 2;
             }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请填写条形码', "error", "确定");
            return;
          }
          if(noEmFlag == 2){
            SIEJS.alert('请选择主条形码', "error", "确定");
            return;
          }
        }
        
        if ($scope.qalist.length==0&&!$scope.isOB) {
          SIEJS.alert('资质文件信息不能为空', "error", "确定");
          return;
        }else{
          var noEmFlag = 0;
          $scope.qalist.forEach((item) => {
            if(item.qaFiletype == ''|| item.qaFiletype == '999') {
             noEmFlag = 1;
            }
            if(item.qaUrl == '') {
              noEmFlag = 2;
            }
            // if(item.qaCodetype == '') {
            //   noEmFlag = 3;
            // }
            if(item.qaCode == '') {
              noEmFlag = 4;
            }
            if(item.datecurent == '') {
              noEmFlag = 5;
            }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请输入资质文件类型', "error", "确定");
            return;
          }
          if(noEmFlag == 2){
            SIEJS.alert('请上传资质文件', "error", "确定");
            return;
          }
          // if(noEmFlag == 3){
          //   SIEJS.alert('请输入资质编码类型', "error", "确定");
          //   return;
          // }
          if(noEmFlag == 4){
            SIEJS.alert('请输入资质文件证书编码', "error", "确定");
            return;
          }
          if(noEmFlag == 5){
            SIEJS.alert('请选择资质文件信息日期', "error", "确定");
            return;
          }
        }
        
        if ($scope.picfilelist.length==0) {
          SIEJS.alert('图片信息不能为空', "error", "确定");
          return;
        }else {
          var noEmFlag = 0;
          $scope.picfilelist.forEach((item) => {
            if(item.picUrl == ''|| item.picUrl == undefined) {
             noEmFlag = 1;
            }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请上传图片文件', "error", "确定");
            return;
          }
        }

       if ($scope.pricelist.length==0) {
          SIEJS.alert('商品售价信息不能为空', "error", "确定");
          return;
        }else{
          var noEmFlag = 0;
          $scope.pricelist.forEach((item) => {
            if($scope.unitPriceFlag&&(item.updateType == ''|| item.updateType == undefined)) {
             noEmFlag = 1;
            }
            if($scope.unitPriceFlag&&(item.updateSeson == ''|| item.updateSeson == undefined)) {
              noEmFlag = 2;
             }
            //  if($scope.unitPriceFlag&&(item.Seson == ''|| item.Seson == undefined)) {
            //   noEmFlag = 3;
            //  }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请选择零售价修改类型', "error", "确定");
            return;
          }
          if(noEmFlag == 2){
            SIEJS.alert('请选择零售价修改原因', "error", "确定");
            return;
          }
          // if(noEmFlag == 3){
          //   SIEJS.alert('请选择零售价修改备注', "error", "确定");
          //   return;
          // }
        }


        var allData = {
          'headInfo': $scope.params,
          'drugInfo': $scope.paramsDrug,
          'medicalInfo': $scope.paramsMedical,
          'medicalfilelist': $scope.medicalfilelist,
          'drugfilelist': $scope.drugfilelist,
          'supplierlist': $scope.supplierlist,
          'barcodelist': $scope.barcodelist,
          'qalist': $scope.qalist,
          'picfilelist': $scope.picfilelist,
          //'packagelist': $scope.packagelist,
          'pricelist': $scope.pricelist,
          'placelist': $scope.placelist,
          'saleslist': $scope.saleslist,
          'channallist': $scope.channallist,
          'filelist': $scope.filelist
        }
        httpServer.post(URLAPI.updateProductInfo, {
          'params': JSON.stringify(allData)
      },
      function (res) {
        if (res.status == 'S') {
          // SIEJS.alert(res.msg, 'success');
        	$scope.formParams=res.data[1];
          $scope.submitfalg = 1;

          // 有修改记录就发起流程
          if($scope.submitfalg == 1&&res.data[2].length>0) {
            $scope.liuchen();
            $scope.submitfalg = 2;
          }else if($scope.submitfalg == 1&&res.data[2].length==0){
            console.log(res.data[2], 'res.data[2]');
            SIEJS.alert('你没有修改任何内容', "warning", "确定");
          }

          // 防止异步问题
          if($scope.submitfalg == 1) {
            $scope.searchDetails();
          }
          
        } else {
            SIEJS.alert(res.msg, "error", "确定");
        }
      },
      function (err) {
        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
      })
      },
      // 删除
      $scope.del = function(index, list) {
        if (list == 'qalist') {
          $scope.qalist[index].flags = '1';
        }else if (list == 'picfilelist') {
          $scope.picfilelist[index].flags = '1';
        }else if (list == 'drugfilelist') {
          $scope.drugfilelist[index].flags = '1';
        }else if (list == 'medicalfilelist') {
          $scope.medicalfilelist[index].flags = '1';
        }else if (list == 'pricelist') {
          $scope.pricelist[index].flags = '1';
        }else if (list == 'placelist') {
          $scope.placelist[index].flags = '1';
        }else if (list == 'saleslist') {
          $scope.saleslist[index].flags = '1';
        }else if (list == 'channallist') {
          $scope.channallist[index].flags = '1';
        }else if (list == 'filelist') {
          $scope.filelist[index].flags = '1';
        }else if (list == 'supplierlist') {
          $scope.supplierlist[index].flags = '1';
        }else if (list == 'barcodelist') {
          $scope.barcodelist[index].flags = '1';
        }
      };


      // 行子表
      $scope.addSupplierInfo = function () {
        var supplierInfoDataobj = {
          supplierId: '',
          supplierName: '',
          productReturn: '',
          condition: '',
        };
        $scope.supplierlist.push(supplierInfoDataobj);
      };
      $scope.addBarcodeInfo = function () {
        var barcodeInfoDataobj = {
          obId: $scope.params.obId,
          barcode: '',
          isMain: '',
        };
        $scope.barcodelist.push(barcodeInfoDataobj);
      };
      $scope.addqualInfo = function() { 
        var qaInfoDataobj = {
          qaFiletype: '999',
          qaUrl: '',
          qaCodetype: '',
          qaCode: '',
          dateType: '',
          datecurent: '',
        };
        $scope.qalist.push(qaInfoDataobj);
      };
      $scope.addImgInfo = function () {
        var imgInfoDataobj = {
          picType: '其他',
          picUrl: '',
        };
        $scope.picfilelist.push(imgInfoDataobj);
      };
//      $scope.addPackageInfo = function () {
//        var PackageInfoDataobj = {
//          supplierId: '',
//          supplierName: '',
//          boxWeight: '',
//          boxLength: '',
//          boxBreadth: '',
//          boxHeight: '',
//          productLength: '',
//          productBreadth: '',
//          productHeight: '',
//          innerpackageSpe: '',
//          packageSpe: '',
//          validDays: '',
//          sxDays: '',
//          place: '',
//          productively: '',
//        };
//        $scope.packagelist.push(PackageInfoDataobj);
//      };
      $scope.addPriceInfo1 = function () {
        var PackageInfoDataobj = {
          priceGroup: '1',
          priceArea: '',
          unitPrice: '',
        };
        $scope.pricelist.push(PackageInfoDataobj);
      };
      $scope.addPriceInfo2 = function () {
        var PackageInfoDataobj = {
          priceGroup: '2',
          priceArea: '',
          unitPrice: '',
        };
        $scope.pricelist.push(PackageInfoDataobj);
      };
      $scope.addPlaceInfo = function () {
        var PlaceInfoDataobj = {
          supplierId: '',
          supplierName: '',
          sxWay: '',
          DIDDIANQINGDAN: '',
          sxWarehouse: '',
          sxStore: '',
        };
        $scope.placelist.push(PlaceInfoDataobj);
      };
      $scope.addSalesInfo = function () {
        var SalesInfoDataobj = {
          placeType: '',
          placeDetail: '',
          salesProperties: '',
          remarks: '',
          status: '',
          // createdBy: userInfo.userFullName,
          // creationDate: new Date().getFullYear()+ '-' +( new Date().getMonth() +1 )+'-'+new Date().getDate(),
        };
        $scope.saleslist.push(SalesInfoDataobj);
      };
      $scope.addChannalInfo = function () {
        var channalInfoDataobj = {
          channal: '',
          channalSub: '',
          channalUnique: '',
        };
        $scope.channallist.push(channalInfoDataobj);
      };
      $scope.addFileInfo = function () {
        var fileInfoDataobj = {
          fileRemarks: '',
          fileUrl: '',
          flags: '2',
          // createdBy: userInfo.userFullName,
          // creationDate: new Date().getFullYear()+ '-' +( new Date().getMonth() +1 )+'-'+new Date().getDate(),
        };
        $scope.filelist.push(fileInfoDataobj);
      };
      
      $scope.addMedicalfileInfo = function() {
        var MedicalfileDataobj = {
          id: '',
          productEc: '',
          standardFile: '',
          tagClass: '',
          noteClass: '',
          fileType: '',
          fileUrl: '',
          certificateType: '',
          certificateNum: '',
          dateType: '',
          creationDate: '',
        };
        $scope.medicalfilelist.push(MedicalfileDataobj);
      };
      $scope.addDrugfileInfo = function() {
        var drugfileDataobj = {
          fileId: '',
          fileDesc: '',
          fileUrl: '',
          // createdBy: userInfo.userFullName,
          // creationDate: new Date().getFullYear()+ '-' +( new Date().getMonth() +1 )+'-'+new Date().getDate(),
        };
        $scope.drugfilelist.push(drugfileDataobj);
      };


    //  ----------------------------------ob资质表  start --------------------------------------

    //打开上传ob资质文件窗口
    $scope.productQaFileUploadShow = function(plmDevelopmentQaSummaryId){
      $scope.getObQaDetilasInfo(plmDevelopmentQaSummaryId);
      $('#fileUpload').modal('show');
    };

      // 获取ob头表信息
      $scope.getObHeadInfo = function() {
        httpServer.post(URLAPI.findPlmDevelopmentInfoInfo, {
          'params': JSON.stringify({obId: $scope.params.obId})
        },
        function (res) {
            if (res.status == 'S') {
              $scope.plmDevelopmentInfoId = res.data[0].plmDevelopmentInfoId;
              $scope.searchDevelopmentQaFileSummary(res.data[0].plmDevelopmentInfoId);
              $scope.searchBatchInfo(res.data[0].plmDevelopmentInfoId);
            } else {
                SIEJS.alert(res.msg, "error", "确定");
            }
        },
        function (err) {
            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
        });
      }
      // 查询ob产品资质文件详情信息
      $scope.getObQaDetilasInfo = function(plmDevelopmentInfoId) {
        httpServer.post(URLAPI.findPlmDevelopmentQaDetailInfo, {
          'params': JSON.stringify({plmDevelopmentQaSummaryId: plmDevelopmentInfoId})
        },
        function (res) {
            if (res.status == 'S') {
              $scope.qaDetailTable = res.data;
            } else {
                SIEJS.alert(res.msg, "error", "确定");
            }
        },
        function (err) {
            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
        });
      };
      //查询ob产品资质文件汇总
      $scope.searchDevelopmentQaFileSummary = function (plmDevelopmentInfoId) {

        httpServer.post(URLAPI.findPlmDevelopmentQaSummaryInfo, {
                'params': JSON.stringify({plmDevelopmentInfoId: plmDevelopmentInfoId}),
                'pageRows': 1000
            },
            function (res) {
                if (res.status == 'S') {
                    $scope.productQaFileDataTable = res.data;
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            },
            function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            }
        );
    };


    // ------------------------------------------ 批次信息 ---------------------------------------------

    $scope.searchBatchInfo = function (plmDevelopmentInfoId) {
        httpServer.post(URLAPI.findPlmDevelopmentBatchInfoInfo, {
                'params': JSON.stringify({plmDevelopmentInfoId: plmDevelopmentInfoId}),
                'pageRows': 1000
            },
            function (res) {
                if (res.status == 'S') {
                    $scope.batchInfoDataTable = res.data;
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            },
            function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            }
        );
    };

      //  ---------------------------------- 上传 start --------------------------------------

      // 上传获取当前行数
      $scope.getQaFileIndex = function(index) {
        $scope.qalistIndex = index;
      };
      //资质文件上传
      $scope.qaFileUploader = new FileUploader({
        url: URLAPI.imgUpload,
        autoUpload: true,
      });
      //资质文件上传后调用的方法
      $scope.qaFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {
            var filename=res.data[0].fileName;
            var length=filename.length;
            var index=filename.lastIndexOf(".")+1;
            
            var suffx=filename.substring(index,length);

            // if(suffx!='pdf'&&suffx!='jpeg'&&suffx!='jpg'&&suffx!='png') {
            //   SIEJS.alert("请选择pdf,jpeg,jpg,png文件！", "error", "确定");
            //   return;
            // }else {

              SIEJS.alert("上传成功！", "success", "确定");
              $('#saafLoading').hide();
              $scope.qalist[$scope.qalistIndex].qaUrl = res.data[0].filePath; 
              $scope.qalist[$scope.qalistIndex].qaFileName = res.data[0].fileName; 
              $scope.showloadingFlag = false;
            // }
        };
        if (res.status == 'E') {
          $('#saafLoading').hide();
            SIEJS.alert(res.msg, "error", "确定");
        };
      };

      $scope.qaFileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };

    

      // 上传获取当前行数
      $scope.getPicIndex = function(index) {
        $scope.picListIndex = index;
      };

      //图片文件上传
      $scope.picFileUploader = new FileUploader({
        url: URLAPI.imgUpload,
        autoUpload: true,
        isUploading: true

      });
      //图片文件上传后调用的方法
      $scope.picFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {

            var filename=res.data[0].fileName;
            var length=filename.length;
            var index=filename.lastIndexOf(".")+1;
            
            var suffx=filename.substring(index,length);

            // if(suffx!='pdf'&&suffx!='jpeg'&&suffx!='jpg'&&suffx!='png') {
            //   SIEJS.alert("请选择pdf,jpeg,jpg,png文件！", "error", "确定");
            //   return;
            // }else {
              SIEJS.alert("上传成功！", "success", "确定");
              $('#saafLoading').hide();
              $scope.picfilelist[$scope.picListIndex].picUrl = res.data[0].filePath; 
              $scope.picfilelist[$scope.picListIndex].picName = res.data[0].fileName; 
            // }
        };
        if (res.status == 'E') {
          $('#saafLoading').hide();
            SIEJS.alert(res.msg, "error", "确定");
        };
      };

      $scope.picFileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };
      

      // 上传获取当前行数
      $scope.getFileIndex = function(index) {
        $scope.filelistIndex = index;
      };
      //附件文件上传
      $scope.FileUploader = new FileUploader({
        url: URLAPI.imgUpload,
        autoUpload: true,
      });
      //附件文件上传后调用的方法
      $scope.FileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {
          var filename=res.data[0].fileName;
          var length=filename.length;
          var index=filename.lastIndexOf(".")+1;
          var suffx=filename.substring(index,length);
          // if(suffx!='pdf'&&suffx!='jpeg'&&suffx!='jpg'&&suffx!='png'&&suffx!='xlsx'&&suffx!='png') {
          //   SIEJS.alert("请选择pdf,jpeg,jpg,png文件！", "error", "确定");
          //   return;
          // }else {
            SIEJS.alert("上传成功！", "success", "确定");
            $('#saafLoading').hide();
            $scope.filelist[$scope.filelistIndex].fileUrl = res.data[0].filePath; 
            $scope.filelist[$scope.filelistIndex].fileName = res.data[0].fileName; 
          // }
        };
        if (res.status == 'E') {
          $('#saafLoading').hide();
            SIEJS.alert(res.msg, "error", "确定");
        };
      };

      $scope.FileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };

      // 上传获取当前行数
      $scope.getDrugFileIndex = function(index) {
        $scope.DrugFileIndex = index;
      };
      // 药品附件文件上传
      $scope.DrugFileUploader= new FileUploader({
        url: URLAPI.imgUpload,
        autoUpload: true,
      });
      // 药品附件文件上传后调用的方法
      $scope.DrugFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {
            var filename=res.data[0].fileName;
            var length=filename.length;
            var index=filename.lastIndexOf(".")+1;
            
            var suffx=filename.substring(index,length);

            // if(suffx!='pdf'&&suffx!='jpeg'&&suffx!='jpg'&&suffx!='png') {
            //   SIEJS.alert("请选择pdf,jpeg,jpg,png文件！", "error", "确定");
            //   return;
            // }else {
              SIEJS.alert("上传成功！", "success", "确定");
              $('#saafLoading').hide();
              $scope.qalist[$scope.DrugFileIndex].qaUrl = res.data[0].filePath; 
              $scope.qalist[$scope.DrugFileIndex].qaFileName = res.data[0].fileName; 
            // }
        };
        if (res.status == 'E') {
          $('#saafLoading').hide();
            SIEJS.alert(res.msg, "error", "确定");
        };
      };

      $scope.DrugFileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };

      // 上传获取当前行数
      $scope.getMedFileIndex = function(index) {
        $scope.medFileIndex = index;
      };
      // 器械附件文件上传
      $scope.medFileUploader= new FileUploader({
        url: URLAPI.imgUpload,
        autoUpload: true,
        isUploading: true
      });
      // 器械附件文件上传后调用的方法
      $scope.medFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {
          var filename=res.data[0].fileName;
          var length=filename.length;
          var index=filename.lastIndexOf(".")+1;
          
          var suffx=filename.substring(index,length);

          // if(suffx!='pdf'&&suffx!='jpeg'&&suffx!='jpg'&&suffx!='png') {
          //   SIEJS.alert("请选择pdf,jpeg,jpg,png文件！", "error", "确定");
          //   return;
          // }else {
            SIEJS.alert("上传成功！", "success", "确定");
            $('#saafLoading').hide();
            $scope.qalist[$scope.medFileIndex].qaUrl = res.data[0].filePath; 
            $scope.qalist[$scope.medFileIndex].qaFileName = res.data[0].fileName; 
          // }
        };
        if (res.status == 'E') {
          $('#saafLoading').hide();
            SIEJS.alert(res.msg, "error", "确定");
        };
      };

      $scope.medFileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };

      // 上传文件预览
      $scope.previewImg = function (index, list) {
        if(list == 'picfilelist') {
          var indexURL = $scope.picfilelist[index].picUrl;
        }else if (list == 'qalist') {
          var indexURL = $scope.qalist[index].qaUrl;
        }else if (list == 'filelist') {
          var indexURL = $scope.filelist[index].fileUrl;
        }else if (list == 'drugfilelist') {
          var indexURL = $scope.qalist[index].qaUrl;
        }else if (list == 'medicalfilelist') {
          var indexURL = $scope.qalist[index].qaUrl;
        }
        window.open(indexURL);
      };
      // 重新上传
      $scope.UploaderReset = function(index, list) {
        if(list == 'qalist') {
          SIEJS.confirm('重新上传需删除原有文件', '是否确定删除？', '确定', function () {
            $scope.qalist[index].qaUrl = '';
          });
        }else if(list == 'picfilelist') {
          SIEJS.confirm('重新上传需删除原有文件', '是否确定删除？', '确定', function () {
            $scope.picfilelist[index].picUrl = '';
          });
        }else if(list == 'filelist') {
          SIEJS.confirm('重新上传需删除原有文件', '是否确定删除？', '确定', function () {
            $scope.filelist[index].fileUrl = '';
          });
        }else if(list == 'drugfilelist') {
          SIEJS.confirm('重新上传需删除原有文件', '是否确定删除？', '确定', function () {
            $scope.qalist[index].qaUrl = '';
          });
        }else if(list == 'medicalfilelist') {
          SIEJS.confirm('重新上传需删除原有文件', '是否确定删除？', '确定', function () {
            $scope.qalist[index].qaUrl = '';
          });
        }
        
      };

      // 获取地区信息区域多选
      $scope.getAreaChecked = function(index) {
        var areaObj = document.getElementsByName('areaInfoArea');
        var checkedStr = '';
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedStr = checkedStr +','+ areaObj[k].value;
          }
        }
        $scope.placelist[index].area = checkedStr;
      };
      // 获取线上销售的销售渠道多选
      $scope.channallistChannal = function (index) {
        var areaObj = document.getElementsByName('channallistChannal');
        var checkedStr = '';
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedStr = checkedStr +','+ areaObj[k].value;
          }
        }
        $scope.channallist[index].channal = checkedStr;
      };


      // 商品可退货属性回调
      $scope.productReturnChange = function (key) {
        if(key !== ''&& key !== '3') {
          $('#conditionId').attr('disabled', false);
        }else{
          $('#conditionId').attr('disabled', true);
          $scope.params.condition ='';
        }
      }

      // 售价区域change
      $scope.priceAreaChange = function (key) {
        $scope.i = 0;
        $scope.pricelist.forEach((item,index) => {
          if($scope.params.otherInfo == '1'&&item.priceArea =='1'||$scope.params.otherInfo !== '1'&&item.priceArea =='17') {
            $scope.addPriceInfoFlag = false;
          }else{
            $scope.addPriceInfoFlag = true;
          }

          if(item.priceArea == key){
            $scope.i++;
            if($scope.i==2){
              $scope.innerIndex = index;
            }
          }
          if($scope.i>=2 ) {
            SIEJS.alert('不能选择重复的售价区域', "error", "确定");
            item.priceArea = '';
          }
        });
      }

      // 生效方式change
      $scope.sxWayGetIndex = function(index) {
        $scope.sxWayIndex=index;
      }

      $scope.sxWayChange = function() {
        if($scope.supplierlist[$scope.sxWayIndex].sxWay!=='5') {
          $scope.supplierlist[$scope.sxWayIndex].area = '';
        }
        if($scope.supplierlist[$scope.sxWayIndex].sxWay!=='3') {
          $scope.supplierlist[$scope.sxWayIndex].placeNote = '';
        }
      }

      // 药品项目品种Change
      $scope.projectClassChange = function() {
        $scope.paramsDrug.project = '';
      }

      // 资质文件配对
      $scope.getqaFiletypeIndex = function (index) {
        $scope.qaFiletypeIndex = index;
      }
      $scope.getmdqaFiletypeIndex = function (index) {
        $scope.mdqaFiletypeIndex = index;
      }
      $scope.getmeqaFiletypeIndex = function (index) {
        $scope.meqaFiletypeIndex = index;
      }
      // 普通商品资质文件类型change
      $scope.qaFiletypeChange = function () {
        switch($scope.qalist[$scope.qaFiletypeIndex].qaFiletype) {
          case '1': 
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '2':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '3':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '4':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '2';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '1';
            break;
          case '5':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '3';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '6':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '2';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '1';
            break;
          case '7':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '3';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '8':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '9':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '2';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '10':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '11':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
        }
      }

      // 零售价onchang （修改零售价）
      $scope.changPriceOnchange = function(index) {
        if($scope.pricelist[index].oldUnitPrice==$scope.pricelist[index].unitPrice) {
          $scope.unitPriceFlag = false;
        }else{
          $scope.unitPriceFlag = true;
        }
      };

      // 供应商成本价onchange
      $scope.changcostOnchange = function(index) {
        console.log(1231123);
        if($scope.supplierlist[index].oldPrice==$scope.supplierlist[index].price) {
          console.log(33333);
          $scope.supplierlist[index]['PriceFlag'] = false;
        }else{
          $scope.supplierlist[index]['PriceFlag'] = true;
          console.log(4444);
        }
      };

      // 药品资质文件类型change
      $scope.mdQaFiletypeChange = function () {
        switch($scope.qalist[$scope.mdqaFiletypeIndex].qaFiletype) {
          case '1': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '3';
          break;
          case '2': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '1';
          break;
          case '3': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '3';
          break;
          case '4': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '3';
          break;
          case '5': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '3';
          break;
          case '6': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '3';
          break;

        }
      }

      // 器械资质文件类型change
      $scope.meQaFiletypeChange = function () {
        switch($scope.qalist[$scope.meqaFiletypeIndex].qaFiletype) {
          case '1': 
            $scope.qalist[$scope.meqaFiletypeIndex].qaCodetype = '2';
            $scope.qalist[$scope.meqaFiletypeIndex].dateType = '1';
          break;
          case '2': 
            $scope.qalist[$scope.meqaFiletypeIndex].qaCodetype = '4';
            $scope.qalist[$scope.meqaFiletypeIndex].dateType = '3';
          break;
          case '3': 
            $scope.qalist[$scope.meqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.meqaFiletypeIndex].dateType = '3';
          break;
          case '4': 
            $scope.qalist[$scope.meqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.meqaFiletypeIndex].dateType = '3';
          break;
        }
      }

      // 独有商品change
      // $scope.uniqueCommoditiesCallBack = function (key) {
      //   if(key == '1'||key == '2') {   // 如果为1或2，商品类型为2
      //     $scope.params.productType =  '2';
      //   }
      // }

      


      // ------------------------------------------- 提交工作流 --------------------------------------
        //获取url参数对象
        $scope.urlParams = urlToObj(location.hash);
        //url参数转对象
        function urlToObj(url) {
          var index = url.lastIndexOf('?');
          var params = url.substring(index + 1);
          var arr = params.split('&');
          var obj = {};
          arr.forEach(function (item) {
              obj[item.split('=')[0]] = item.split('=')[1];
          });
          return obj;
        }

        //先判断是否业务form是否有id 再判断流程form的id
        if ($stateParams.productHeadId) {
          $scope.id = $stateParams.productHeadId;
        }else if ($scope.urlParams.businessKey) {
            $scope.id = $scope.urlParams.businessKey;
        }
    
        //流程图参数
        $scope.processImageParams = {
          token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
          id: 'processImg',
          instanceId: null,
          key: 'UPDATEPRODUCT.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
        };
    
        $scope.tabChange = function (name) {
            $scope.tabAction = name;
            if (!$scope.processImgLoad) {
                $scope.getProcessInfo(function () {
                    var p = $scope.processImageParams;
                    $timeout(function () {
                        processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                        $scope.processImgLoad = true;
                    }, 100)
                });
            }
        };
    
        // 获取流程信息
        $scope.getProcessInfo = function (callback) {
          httpServer.post(URLAPI.processGet, {
            'params': JSON.stringify({
              processDefinitionKey: $scope.processImageParams.key,
              businessKey: $stateParams.productHeadId,
            })
          },
          function (res) {
            if (res.status === 'S') {
              $scope.processImageParams.instanceId = res.data.processInstanceId;
            }
            callback && callback(res);
          });
        };


        //  提交审批流
        $scope.liuchen = function () {

          $scope.extend ={
                  "tasks_assignees":[]
              }
              $scope.variables = []; //流程变量
             angular.forEach($scope.formParams, function (value, key) {
                  $scope.variables.push({
                      name: key.replace(/\s*/g,""),
                      type: 'int',
                      value: value
                  });
       
              });

              $scope.properties={
                  "menucode": "HTGL",
                  "opinion": ""
              };
              $scope.paramsBpm={
                  "extend":$scope.extend,
                  "variables":$scope.variables,
                  "properties":$scope.properties,
                  "responsibilityId": "990021",
                  "respId": "990021",
                  "processDefinitionKey": "UPDATEPRODUCT.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                  "saveonly": false,
                  "businessKey": $stateParams.productHeadId+"_"+$scope.params.versionNum, //单据ID
                  "title": "商品修改", //流程标题，修改为当前业务信息
                  "positionId": 0,
                  "orgId": 0,
                  "userType": "45",
                  "billNo": $scope.params.plmCode
              }
              httpServer.post(URLAPI.processStart, {
                  'params': JSON.stringify($scope.paramsBpm)
              }, function (res) {
                  if (res.status == 'S') {
                     SIEJS.alert("提交成功", "success", "确定");
                  }
                  else {
                      SIEJS.alert(res.msg, "error", "确定");
                  }
              }, function (err) {
                  SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              });
      }


      // 显示弹窗数据
      $scope.showSupplierNameMol = function(index) {
        $('#supplierNameMol').modal('toggle');
        $scope.SupplierNameIndex = index;
      };
      // 显示弹窗数据
      $scope.diDianshowSupplierNameMol = function(index) {
        $('#diDianSupplierNameMol').modal('toggle');
        $scope.diDianSupplierNameReturnIndex = index;
      };

      // 显示主商品货号弹窗数据
      $scope.mainProductNameMol = function() {
        $('#mainProductNameMol').modal('toggle');
      };


      // 显示资质组数据
      $scope.showqaGroupNameMol = function(index) {
        $('#qaGroupNameMol').modal('toggle');
        $scope.qaGroupNameIndex = index;
      };
      // 选择资质组数据
      $scope.qaGroupNameReturn = function (key, value, currentList) {
        $scope.qalist[$scope.qaGroupNameIndex].groupId = currentList[0].qaGroupCode;
        $scope.qalist[$scope.qaGroupNameIndex].groupName = currentList[0].qaGroupName;
      };

      
      
      // 选择弹窗数据
      $scope.SupplierNameReturn = function (key, value, currentList) {
        var stop = 0;
        $scope.supplierlist.forEach((item) => {
          if(item.supplierId == currentList[0].userId) {
            SIEJS.alert('供应商已存在', "error", "确定");
            stop = 1;
          }
        }); 
        if(stop == 1) {
          return;
        }
        $scope.supplierlist[$scope.SupplierNameIndex].supplierName = currentList[0].userName;
        $scope.supplierlist[$scope.SupplierNameIndex].supplierId = currentList[0].userId;
      };

      // 地点供应商数据复制
      $scope.diDianSupplierNameReturn = function (key, value, currentList) {
        var stop = 0;
        $scope.placelist.forEach((item) => {
          if(item.supplierId == currentList[0].userId) {
            SIEJS.alert('供应商已存在', "error", "确定");
            stop = 1;
          }
        }); 
        if(stop == 1) {
          return;
        }
        $scope.placelist[$scope.diDianSupplierNameReturnIndex].supplierName = currentList[0].userName;
        $scope.placelist[$scope.diDianSupplierNameReturnIndex].supplierId = currentList[0].userId;
      }

      // 主商品货号名称赋值
      $scope.mainProductNameReturn = function (key, value, currentList) {
        $scope.params.mainProductid = currentList[0].plmCode;
        $scope.params.mainProductname = currentList[0].productName;
      }

      // 地区选择
      $scope.selectArea = function() {
        $('#citysModal').modal('show');
      };

      // 优先供应商
      $scope.supplierSelectcheckbox=function(index){
        if($scope.supplierlist[index].isMain=='Y'){
          angular.forEach($scope.supplierlist,function(obj,rowindex){
            if(rowindex!=index){
              obj.isMain='N';
              }
          });
        }     
      }

      // 保留两位小数
      $scope.toFixed2 = function() {
        if(isNaN($scope.params.salesQty)) {
          SIEJS.alert('请输入数字', "error", "确定");
          $scope.params.salesQty = '';
        }else{
          $scope.params.salesQty = Number($scope.params.salesQty).toFixed(2).toString();
        }
      }




      // 退回
      $scope.returnB = function(index, list) {
        if(list == 'qalist') {
          $scope.qalist[index]['returnReson'] = '';
          $scope.qalist[index].returnResonFalg = false;
        }
        if(list == 'picfilelist') {
          $scope.picfilelist[index]['returnReson'] = '';
          $scope.picfilelist[index].returnResonFalg = false;
        }
      }

      $scope.returnBackAll = function(list) {
        if(list == 'qalist') {
          var returnQaArr = [];
          $scope.qalist.forEach((item) => {
            if(item.returnReson) {
              returnQaArr.push(item);
            }
          });
          httpServer.post(URLAPI.productQafileReturn, {
            'params': JSON.stringify({qalist: returnQaArr}),
          },
          function (res) {
              if (res.status == 'S') {
                SIEJS.alert('成功驳回', "success", "确定");
              } else {
                  SIEJS.alert(res.msg, "error", "确定");
              }
          },
          function (err) {
              SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
          });
        }
        if(list == 'picfilelist') {
          var returnPicArr = [];
          $scope.picfilelist.forEach((item) => {
            if(item.returnReson) {
              returnPicArr.push(item);
            }
          });
          httpServer.post(URLAPI.productPicfileReturn, {
            'params': JSON.stringify({picfilelist: returnPicArr}),
          },
          function (res) {
              if (res.status == 'S') {
                SIEJS.alert('成功驳回', "success", "确定");
              } else {
                  SIEJS.alert(res.msg, "error", "确定");
              }
          },
          function (err) {
              SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
          });
        }
      }

      
    }]
  );
})