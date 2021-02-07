'use strict';
define(['app',"angularFileUpload"],function(app) {
  app.useModule('angularFileUpload');//按需加载弹出层模块
  app.controller('finishProcurementCtrl',['$scope', 'httpServer', 'URLAPI', '$timeout','SIE.JS', '$compile', '$stateParams','FileUploader','iframeTabAction',
    function($scope, httpServer, URLAPI, $timeout, SIEJS, $compile, $stateParams,FileUploader,iframeTabAction) {
      // 变量参数
      $scope.params = {};
      $scope.paramsDrug = {}; // 药品属性
      $scope.paramsMedical = {}; // 医疗器械属性
      $scope.liuchenparam="ADDPRODUCT.-999"; 
      $scope.productPlace = [];

      $scope.drugfilelist = [
        {
          qaFiletype: '药品注册证',
          qaUrl: '',
          qaCodetype: '3',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '质量标准',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '标签备案样式',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '说明书备案样式',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
      ]; // 药品资质文件
      $scope.medicalfilelist = [
        {
          qaFiletype: '产品技术要求',
          qaUrl: '',
          qaCodetype: '2',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '合格证明文件',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '标签备案样式',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '说明书备案样式',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
      ]; //  器械资质文件

      $scope.helpData = {
        productName: '这个字段就是货品名称',
      }

     $scope.userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
      
      $scope.formParams = {

              userId: $scope.userLoginInfo.userId

          };
      
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
          $scope.liuchengPicture = true;
      } else if ($scope.urlParams.businessKey) {
          $scope.liuchengPicture = false;
          $scope.id = $scope.urlParams.businessKey;
          //保存流程实例id
          $scope.businessparam={
        	productHeadId:$scope.urlParams.businessKey,
        	instanceid:$scope.urlParams.processInstanceId,
        	type:'save'
          };
          httpServer.post(URLAPI.saveProductInstanceById, {
              'params':JSON.stringify($scope.businessparam)
            },
            function (res) {
              if (res.status == 'S') {
              } else {               
              }
            },
            function (err) {
          });
          
      }
         
      $scope.isOB = false;
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
      $scope.packagelist = [], // 商品包装
      $scope.pricelist = [], // 商品售价
      $scope.placelist = [], // 地点信息关联
      $scope.saleslist = [], // 可销售属性
      $scope.channallist = [], // 线上渠道
      $scope.filelist = [], // 附件
      $scope.modifyLogData = [], // 修日志企
      $scope.modalParams = {}, // 模板数据
      $scope.copyDataParams = {
        orderStatus: '6'
      }, // 复制资料数据

      $scope.picfilelistIndex = '', // 图片上传是需要获取当前行数
      $scope.qalistIndex = '', // 资质文件上传需要的当前行数
      $scope.filelistIndex = '', // 附件上传需要的当前行数
      $scope.drugFileIndex = '', // 药品资质上传
      $scope.medFileIndex = '', // 器械资质上传

      $scope.qaFiletypeIndex = '';
      $scope.mdqaFiletypeIndex = '';
      $scope.meqaFiletypeIndex = '';

      $scope.SupplierNameIndex = '', // 供应商获取当前行
      $scope.diDianSupplierNameReturnIndex = '', // 地点获取当前行
      $scope.getValidDaysIndex = '', // 包装的当前行
      $scope.sxDaysDisabled = false, // 显示保质期天数

      $scope.tabAction = '', // 页签action标识
      $scope.processImageParams = {};// 流程图参数

      $scope.storeTypeAll = ''; // storeType是否全选标识
      $scope.TradeZoneAll = ''; // TradeZone是否全选标识

      $scope.addPriceInfoFlag = true; // addPriceInfo控制售价添加的标识

      $scope.addpriceCtrl = ''; // 售价分组的控制标识

      $scope.supplierReturnRemark = ''; // 供应商退回原因

      var userInfo = JSON.parse(localStorage.getItem('BASE_successLoginInfo')); // 获取用户信息

      
      
    // 切换页签
    $scope.tabChangeInfo = function (name) {
      $scope.tabAction = name;
      if (name == 'flowChart') {
        $scope.tabChange('flowChart');
      }
    };
  

      // 查询
      $scope.searchDetails = function () {
        httpServer.post(URLAPI.findProductById, {
          'params': JSON.stringify({'productHeadId': $scope.id})
        },
        function(res) {
          if (res.status == 'S') {
            $scope.params = res.data.headInfo;
            $scope.paramsDrug = res.data.drugInfo;
            $scope.paramsMedical = res.data.medicalInfo;
            // $scope.medicalfilelist = res.data.medicalfilelist;
            // $scope.drugfilelist = res.data.drugfilelist;
            $scope.barcodelist = res.data.barcodelist;
            $scope.channallist = res.data.channallist;
            $scope.filelist = res.data.filelist;
            $scope.packagelist = res.data.packagelist;
            $scope.picfilelist = res.data.picfilelist;
            $scope.placelist = res.data.placelist;
            $scope.pricelist = res.data.pricelist;
            $scope.qalist = res.data.qalist;
            $scope.saleslist = res.data.saleslist;
            $scope.supplierlist = res.data.supplierlist;

            $scope.qalist.forEach((item) => {
              item['returnResonFalg'] = true;
            });
            $scope.picfilelist.forEach((item) => {
              item['returnResonFalg'] = true;
            });
            
            // 字段帮助说明
            $scope.getHelpData();

            // if(res.data.qalist.length == 0 && $scope.params.otherInfo == '1') {
            //   $scope.qalist = $scope.drugfilelist;
            // }else if(res.data.qalist.length == 0 && $scope.params.otherInfo == '2') {
            //   $scope.qalist = $scope.medicalfilelist;
            // }else {
            //   $scope.qalist = res.data.qalist;
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

      // 自动查询
      if(true) {
        $scope.searchDetails();
      }

      // 保存
      $scope.btnSave = function () {

        $scope.storelist = [];
        //开供应商信息的数据分开两个表
        $scope.supplierlist.forEach((item) => {
          $scope.storelist.push({
            'substituteType':item.substituteType, 
            'substitutePropetion':item.substitutePropetion,
            'startDate':item.startDate,
            'endDate':item.endDate,
            'supplierId':item.supplierId,
            'sxWay':item.sxWay,
            'placeNote':item.placeNote,
            'area':item.area,
            'sxWarehouse':item.sxWarehouse,
            'sxStore':item.sxStore,
            'storeId': item.storeId
          });
        });
    	  $scope.params.buttonStatus="save";
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
          // 'packagelist': $scope.packagelist,
          'pricelist': $scope.pricelist,
          // 'placelist': $scope.placelist,
          'saleslist': $scope.saleslist,
          'channallist': $scope.channallist,
          'filelist': $scope.filelist,
          'storelist': $scope.storelist
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
          clearInterval(autoSave);
      })},

      // 自动保存
      $scope.autoSave = function () {
    	  $scope.params.buttonStatus="save";
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
          // 'packagelist': $scope.packagelist,
          'pricelist': $scope.pricelist,
          // 'placelist': $scope.placelist,
          'saleslist': $scope.saleslist,
          'channallist': $scope.channallist,
          'filelist': $scope.filelist
        }
        httpServer.post(URLAPI.saveProductInfo, {
          'params': JSON.stringify(allData)
        },
        function (res) {
          if (res.status == 'S') {
            $scope.searchDetails();
          }
        })},
      // 提交
      $scope.btnSubmit = function () {
        $scope.params.buttonStatus="submit";
        

        $scope.storelist = [];
        //开供应商信息的数据分开两个表
        $scope.supplierlist.forEach((item) => {
          $scope.storelist.push({
            'substituteType':item.substituteType, 
            'substitutePropetion':item.substitutePropetion,
            'startDate':item.startDate,
            'endDate':item.endDate,
            'supplierId':item.supplierId,
            'sxWay':item.sxWay,
            'placeNote':item.placeNote,
            'area':item.area,
            'sxWarehouse':item.sxWarehouse,
            'sxStore':item.sxStore,
            'storeId': item.storeId
          });
        });
        
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
            if((item.qaFiletype == ''|| item.qaFiletype == '999')&&!$scope.isOB) {
             noEmFlag = 1;
            }
            if(item.qaUrl == ''&&!$scope.isOB) {
              noEmFlag = 2;
            }
            // if(item.qaCodetype == '') {
            //   noEmFlag = 3;
            // }
            if(item.qaCode == ''&&!$scope.isOB) {
              noEmFlag = 4;
            }
            if(item.datecurent == ''&&!$scope.isOB) {
              noEmFlag = 5;
            }
            if((item.supplierId == ''||item.supplierId == undefined)&&!$scope.isOB) {
              noEmFlag = 6;
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
          if(noEmFlag == 6){
            SIEJS.alert('请选择资质文件对应的供应商ID', "error", "确定");
            return;
          }

          // 资质组与供应商对应验证
          var map=new Map();
          angular.forEach($scope.qalist,function(obj,rowindex){
                var supplierid=obj.supplierId;
                var groupId=obj.groupId; 
                var key=supplierid+"_"+groupId;
                  if(!map.has(key)){
                    map.set(key,key);
                  }
              });
          if(map.size!==$scope.supplierlist.length) {
            SIEJS.alert('请核对资质组与供应商是否已经完全匹配', "error", "确定");
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
            if((item.supplierId == ''||item.supplierId == undefined)&&!$scope.isOB) {
              noEmFlag = 2;
            }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请上传图片文件', "error", "确定");
            return;
          }
          if(noEmFlag == 2){
            SIEJS.alert('请选择图片文件对应的供应商ID', "error", "确定");
            return;
          }
        }

        if ($scope.pricelist.length==0) {
          SIEJS.alert('商品售价信息不能为空', "error", "确定");
          return;
        }else {
          var noEmFlag = 0;
          $scope.pricelist.forEach((item) => {
            if(item.priceGroup == '') {
             noEmFlag = 1;
            }
            if(item.priceArea == '') {
              noEmFlag = 2;
             }
             if(item.unitPrice == '') {
              noEmFlag = 3;
             }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请填写售价分组', "error", "确定");
            return;
          }
          if(noEmFlag == 2){
            SIEJS.alert('请填写售价区域', "error", "确定");
            return;
          }
          if(noEmFlag == 3){
            SIEJS.alert('请填写零售价', "error", "确定");
            return;
          }
        }

        // if ($scope.packagelist.length==0) {
        //   SIEJS.alert('商品包装信息不能为空', "error", "确定");
        //   return;
        // }else if ($scope.pricelist.length==0) {
        //   SIEJS.alert('商品售价信息不能为空', "error", "确定");
        //   return;
        // }

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
          // 'packagelist': $scope.packagelist,
          'pricelist': $scope.pricelist,
          // 'placelist': $scope.placelist,
          'saleslist': $scope.saleslist,
          'channallist': $scope.channallist,
          'filelist': $scope.filelist,
          'storelist': $scope.storelist
        }
        httpServer.post(URLAPI.saveProductInfo, {
          'params': JSON.stringify(allData)
      },
      function (res) {
        if (res.status == 'S') {
        	$scope.searchDetails();
          // SIEJS.alert(res.msg, 'success');      	
          if($scope.params)
        	  {
        	  $scope.variables = []; //流程变量
        	  var markerChannel=$scope.params.markerChannel; 
      		  var deptsort=$scope.params.department.substr(0,1); //截取开头
        		var deptsortnum=$scope.params.department.substr(0,4); 
        		
        	  if(markerChannel=='2'||markerChannel=='3'){ //判断是否线上渠道
        		
        	  if($scope.params.productType=='1'||$scope.params.productType=='4')  //ob商品
        		  {
      
        		  if(deptsort=='2'||deptsort=='3'||deptsort=='4') //判断部门
        			  {
        	       $scope.variables.push({ name:'NONOB',type: 'int',value:0});
        	       $scope.variables.push({ name:'OB',type: 'int',value: 1});
        	       $scope.variables.push({ name:'OBQA',type: 'int',value: 1});
        	       $scope.variables.push({ name:'SUPPLY',type: 'int',value:0});
        	       $scope.variables.push({ name:'OEM',type: 'int',value: 1});
        	       $scope.variables.push({ name:'isob',type: 'int',value: 1});
        	       $scope.variables.push({ name:'NONOBQA',type: 'int',value: 0});
        			  }
        		  else if(deptsort=='1'||deptsort=='5'||deptsort=='6') //
        			  {
        			  //
        			  $scope.variables.push({ name:'NONOB',type: 'int',value:1});
           	       $scope.variables.push({ name:'OB',type: 'int',value: 0});
           	       $scope.variables.push({ name:'OBQA',type: 'int',value: 0});
           	       $scope.variables.push({ name:'SUPPLY',type: 'int',value:0});
           	       $scope.variables.push({ name:'OEM',type: 'int',value: 1});
           	       $scope.variables.push({ name:'isob',type: 'int',value: 0});
           	       $scope.variables.push({ name:'NONOBQA',type: 'int',value: 1});
        			  }else if(deptsortnum=='9901'||deptsortnum=='9907')
        				  {
        				  $scope.variables.push({ name:'online',type: 'int',value:0});
                  	      $scope.variables.push({ name:'NOTONLINE',type: 'int',value: 0});                  	      
                  	      $scope.variables.push({ name:'ISDEPTS',type: 'int',value:1});
                	      $scope.variables.push({ name:'ISQA',type: 'int',value: 1});
                	      $scope.variables.push({ name:'DEPT',type: 'int',value:1});
                	      $scope.variables.push({ name:'NONOB',type: 'int',value: 0});
                	      
                  	      $scope.liuchenparam="ONLINEPRODUCTADD.-999";
        				  }
        			  else if(deptsortnum!='9901'&&deptsortnum!='9907') //其它99
        				  {
        				  $scope.variables.push({ name:'online',type: 'int',value:0});
                  	      $scope.variables.push({ name:'NOTONLINE',type: 'int',value: 1});                 	      
                  	      $scope.variables.push({ name:'ISDEPTS',type: 'int',value:0});
                	      $scope.variables.push({ name:'ISQA',type: 'int',value: 0});
                	      $scope.variables.push({ name:'DEPT',type: 'int',value:0});
                	      $scope.variables.push({ name:'NONOB',type: 'int',value: 0});
                	      
                  	      $scope.liuchenparam="ONLINEPRODUCTADD.-999";
        				  }
        		  } //ob商品判断
        	  
        	  else if($scope.params.productType=='3'||$scope.params.productType=='5'||$scope.params.productType=='6'){
        		  var nonedept=$scope.params.department.substr(0,2); //截取开头
        		  var nonedept4=$scope.params.department.substr(0,4);
        		  if(nonedept=='99') {
        			  if(nonedept4=='9901'||nonedept4=='9907') {
        				  $scope.variables.push({ name:'online',type: 'int',value:0});
                  	      $scope.variables.push({ name:'NOTONLINE',type: 'int',value: 0});                 	      
                  	      $scope.variables.push({ name:'ISDEPTS',type: 'int',value:1});
                	      $scope.variables.push({ name:'ISQA',type: 'int',value: 0});
                	      $scope.variables.push({ name:'DEPT',type: 'int',value:1});
                	      $scope.variables.push({ name:'NONOB',type: 'int',value:1});
                	      
                  	      $scope.liuchenparam="ONLINEPRODUCTADD.-999";
        				  }
        			  else {
        				  $scope.variables.push({ name:'online',type: 'int',value:0});
                  	      $scope.variables.push({ name:'NOTONLINE',type: 'int',value: 1});                 	      
                  	      $scope.variables.push({ name:'ISDEPTS',type: 'int',value:0});
                	      $scope.variables.push({ name:'ISQA',type: 'int',value: 0});
                	      $scope.variables.push({ name:'DEPT',type: 'int',value:0});
                	      $scope.variables.push({ name:'NONOB',type: 'int',value: 0});
                	      
                  	      $scope.liuchenparam="ONLINEPRODUCTADD.-999";
        				  }
        			  } //99开头
        		  else {
        		   $scope.variables.push({ name:'NONOB',type: 'int',value:1});
        	       $scope.variables.push({ name:'OB',type: 'int',value: 0});
        	       $scope.variables.push({ name:'OBQA',type: 'int',value: 0});
        	       $scope.variables.push({ name:'NONOBQA',type: 'int',value:1});
        	       $scope.variables.push({ name:'OEM',type: 'int',value: 0});
        	       if($scope.params.warehouseGetDay>300||$scope.params.warehousePostDay>300)
        	    	   {
        	       $scope.variables.push({ name:'SUPPLY',type: 'int',value:1});
        	    	   }
        	       else
        	    	   {
        	    	   $scope.variables.push({ name:'SUPPLY',type: 'int',value:0});
        	    	   }
        	       $scope.variables.push({ name:'isob',type: 'int',value: 0});
        		  }} else  {
        		  $scope.variables.push({ name:'NONOB',type: 'int',value:1});
       	       $scope.variables.push({ name:'OB',type: 'int',value: 0});
       	       $scope.variables.push({ name:'OBQA',type: 'int',value: 1});
       	    $scope.variables.push({ name:'NONOBQA',type: 'int',value:0});
       	       $scope.variables.push({ name:'OEM',type: 'int',value: 0});
       	    $scope.variables.push({ name:'SUPPLY',type: 'int',value:0});
       	 $scope.variables.push({ name:'isob',type: 'int',value: 0});
        		  }
        	  }//线下
        	  else   //线上渠道
        		  {

        		  $scope.variables.push({ name:'online',type: 'int',value:1});
          	      $scope.variables.push({ name:'NOTONLINE',type: 'int',value: 0});
          	      
          	      $scope.variables.push({ name:'ISDEPTS',type: 'int',value:0});
        	      $scope.variables.push({ name:'ISQA',type: 'int',value: 0});
        	      $scope.variables.push({ name:'DEPT',type: 'int',value:0});
        	      $scope.variables.push({ name:'NONOB',type: 'int',value: 0});
        	      
          	      $scope.liuchenparam="ONLINEPRODUCTADD.-999";
        		  }
        	  
            $scope.liuchen();
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
          var innerId = {id: $scope.qalist[index].qaId};
          if(innerId.id == undefined) {
            $scope.qalist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductQaFileById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.qalist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'picfilelist') {
          var innerId = {id: $scope.picfilelist[index].picId};
          if(innerId.id == undefined) {
            $scope.picfilelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeletePicFileById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.picfilelist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'drugfilelist') {
          var innerId = {id: $scope.drugfilelist[index].drugfileId};
          if(innerId.id == undefined) {
            $scope.drugfilelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteDrugfileById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.drugfilelist.splice(index, 1);
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'medicalfilelist') {
          var innerId = {id: $scope.medicalfilelist[index].id};
          if(innerId.id == undefined) {
            $scope.medicalfilelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteMedicalfileById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.medicalfilelist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'pricelist') {
          var innerId = {id: $scope.pricelist[index].priceId};
          if(innerId.id == undefined) {
            $scope.pricelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductPriceById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.pricelist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'placelist') {
          var innerId = {id: $scope.placelist[index].placeId};
          if(innerId.id == undefined) {
            $scope.placelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductPlaceById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.placelist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'saleslist') {
          var innerId = {id: $scope.saleslist[index].salesId};
          if(innerId.id == undefined) {
            $scope.saleslist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductSalePropertiesById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.saleslist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'channallist') {
          var innerId = {id: $scope.channallist[index].channalId};
          if(innerId.id == undefined) {
            $scope.channallist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductChannalById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.channallist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'filelist') {
          var innerId = {id: $scope.filelist[index].fileId};
          if(innerId.id == undefined) {
            $scope.filelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductFileById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.filelist.splice(index, 1);
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'supplierlist') {
          var innerId = {id: $scope.supplierlist[index].id};
          if(innerId.id == undefined) {
            $scope.supplierlist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductSupplierById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.supplierlist.splice(index, 1);
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'barcodelist') {
          var innerId = {id: $scope.barcodelist[index].barcodeId};
          if(innerId.id == undefined) {
            $scope.barcodelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteBarcodeById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.barcodelist.splice(index, 1);
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }
      };
      //作废
      $scope.btnDiscard = function(){
        SIEJS.confirm('作废', '是否确定作废？', '确定', function () {
          httpServer.post(URLAPI.updateobProduct, {
            'params': JSON.stringify({'productHeadId': $stateParams.productHeadId})
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
          })
        });
      };

      // ---------------------------------------------- 行表添加 ---------------------------------------------

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
          returnResonFalg: true,
        };
        $scope.qalist.push(qaInfoDataobj);
      };
      $scope.addImgInfo = function () {
        var imgInfoDataobj = {
          picType: '其他',
          picUrl: '',
          returnResonFalg: true,
        };
        $scope.picfilelist.push(imgInfoDataobj);
      };
      $scope.addPackageInfo = function () {
        var PackageInfoDataobj = {
          supplierId: '',
          supplierName: '',
          boxWeight: '',
          boxLength: '',
          boxBreadth: '',
          boxHeight: '',
          productLength: '',
          productBreadth: '',
          productHeight: '',
          innerpackageSpe: '',
          packageSpe: '',
          validDays: '',
          sxDays: '',
          place: '',
          productively: '',
        };
        $scope.packagelist.push(PackageInfoDataobj);
      };
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


      // 获取地区信息区域多选
      $scope.getAreaChecked = function(index) {
        var areaObj = document.getElementsByName('areaInfoArea');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        $scope.placelist[index].area = checkedArr;
      };


      // 获取线上销售的销售渠道多选
      $scope.channallistChannal = function (index) {
        var areaObj = document.getElementsByName('channallistChannal');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        $scope.channallist[index].channal = checkedArr;
      };

      // 有效期限控制
      $scope.getIndexValidDays = function(index) {
        $scope.getValidDaysIndex = index;
      };
      
      $scope.validDaysChange = function (key) {
        if(key == '0') {
          $scope.params.sxDays = 3650;
          $scope.sxDaysDisabled = $scope.getValidDaysIndex;
        }else{
          // $scope.sxDaysDisabled = false;
          $scope.params.sxDays = '';
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

      // 商品可退货属性回调
      $scope.productReturnChange = function (key) {
        if(key !== ''&& key !== '3') {
          $('#conditionId').attr('disabled', false)
        }else{
          $('#conditionId').attr('disabled', true)
          $scope.params.condition ='';
        }
      }


      // 药品项目品种Change
      $scope.projectClassChange = function() {
        $scope.paramsDrug.project = '';
      }

      // 售价区域change
      // $scope.priceAreaChange = function (key) {
      //   $scope.i = 0;
      //   $scope.pricelist.forEach((item,index) => {
      //     if($scope.params.otherInfo == '1'&&item.priceArea =='1'||$scope.params.otherInfo !== '1'&&item.priceArea =='17') {
      //       $scope.addPriceInfoFlag = false;
      //     }else{
      //       $scope.addPriceInfoFlag = true;
      //     }

      //     if(item.priceArea == key){
      //       $scope.i++;
      //       if($scope.i==2){
      //         $scope.innerIndex = index;
      //       }
      //     }
      //     if($scope.i>=2 ) {
      //       SIEJS.alert('不能选择重复的售价区域', "error", "确定");
      //       item.priceArea = '';
      //     }
      //   });
      // }

      // 线上渠道
      $scope.channalChange = function(index) {
        $scope.channallistIndex = index;
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


      // 药品与器械的资质文件类型的显示控制
      $scope.Number=function(value){
    	  if(isNaN(parseInt(value))) {
    		  return 1;
    		}else{
    		  return 0;
    		}
      }

      // 生效方式change
      $scope.sxWayGetIndex = function(index) {
        $scope.sxWayIndex=index;
      }

      $scope.sxWayChange = function() {
        // console.log($scope.supplierlist[sxWayIndex].sxWay,'$scope.supplierlist[sxWayIndex].sxWay');
        // console.log($scope.supplierlist[sxWayIndex].sxWay,'$scope.supplierlist[sxWayIndex].sxWay');
        if($scope.supplierlist[$scope.sxWayIndex].sxWay!=='5') {
          $scope.supplierlist[$scope.sxWayIndex].area = '';
        }
        if($scope.supplierlist[$scope.sxWayIndex].sxWay!=='3') {
          $scope.supplierlist[$scope.sxWayIndex].placeNote = '';
        }
      }




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
              if(res.data.length>0) {
                $scope.plmDevelopmentInfoId = res.data[0].plmDevelopmentInfoId;
                $scope.searchDevelopmentQaFileSummary(res.data[0].plmDevelopmentInfoId);
                $scope.searchBatchInfo(res.data[0].plmDevelopmentInfoId);
              }
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
        //if ($stateParams.productHeadId) {
          //$scope.id = $stateParams.productHeadId;
        //}else if ($scope.urlParams.businessKey) {
          //  $scope.id = $scope.urlParams.businessKey;
        //}
    
        //流程图参数
        $scope.processImageParams = {
          token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
          id: 'processImg',
          instanceId: null,
          key: 'ADDPRODUCT.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
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
        $scope.liuchen = function (id) {

          $scope.extend ={
                  "tasks_assignees":[]
              }
             
              //angular.forEach($scope.formParams, function (value, key) {
                 // $scope.variables.push({
                      //name: key,
                    //  type: 'string',
                  //    value: value
                //  });
              //});

              $scope.properties={
                  "menucode": "HTGL",
                  "opinion": ""
                  //"userId": "userId":userLoginInfo.userId,
              };
              $scope.paramsBpm={
                  "extend":$scope.extend,
                  "variables":$scope.variables,
                  "properties":$scope.properties,
                  "processDefinitionKey":$scope.liuchenparam, //流程唯一标识，需修改为对应业务表单流程唯一标识
                  "saveonly": false,
                  "businessKey": $stateParams.productHeadId, //单据ID
                  "title": "商品新增", //流程标题，修改为当前业务信息
                  "positionId": 0,
                  "orgId": 0,
                  "userId":$scope.userLoginInfo.userId,
                  "userType": $scope.userLoginInfo.userType,
                  "billNo": $scope.params.plmCode
              }
              httpServer.post(URLAPI.processStart, {
                  'params': JSON.stringify($scope.paramsBpm)
              }, function (res) {
                  if (res.status == 'S') {
                	  
                     SIEJS.alert("提交成功", "success", "确定");
                     $timeout(function() {
                      // 关闭当前页面
                      window.parent.deleteHeadTab();
                    },2000);
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
      // 显示参考货品名称弹窗数据
      $scope.consultProductNameMol = function() {
        $('#consultProductNameMol').modal('toggle');
      };
      // 显示供应商退回原因弹窗
      $scope.supplierReturnBackMol = function(index) {
        $('#supplierReturnBackMolId').modal('toggle');
        $scope.supplierReturnIndex = index;
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



      // 税收分类编码
      $scope.rateCodeShowMol = function() {
        $('#rateCodeShowMolID').modal('toggle');
        // $scope.rateCodeIndex = index;
      }
      $scope.rateCodeMolReturn = function(key, value, currentList) {
        $scope.params.rateClassCode = currentList[0].plmTaxTypeCode;
      }

      // 地点清单
      $scope.placeNoteShowMol = function(index) {
        $('#placeNoteShowMolID').modal('toggle');
        $scope.placeNoteIndex = index;
      }
      $scope.placeNoteMolReturn = function(key, value, currentList) {
        $scope.supplierlist[$scope.placeNoteIndex].placeNote = currentList[0].descName;
      }

      // 地点清单
      $scope.priceAreaShowMol = function(index) {
        $('#priceAreaShowMolID').modal('toggle');
        $scope.priceAreaIndex = index;
      }
      $scope.priceAreaMolReturn = function(key, value, currentList) {
        $scope.pricelist[$scope.priceAreaIndex].priceArea = currentList[0].areaName;
      }

      // 线上子渠道
      $scope.channalSubShowMol = function(index) {
        $scope.channallistIndex = index;
        $('#channalSubShowMolID').modal('toggle');
      }
      $scope.channalSubMolReturn = function(key, value, currentList) {
        var arr = [];
        currentList.forEach((itemInner) => {
          arr.push(itemInner.subrouteName);
        });
        $scope.channallist[$scope.channallistIndex].channalSub = arr.join(',');
      }

      // 地点清单
      $scope.originCountryMol = function() {
        $('#originCountryShowMolID').modal('toggle');
      }
      $scope.originCountryMolReturn = function(key, value, currentList) {
        $scope.params.originCountry = currentList[0].areaCn;
      }

      // 供应商退回确认
      $scope.supplierReturnSave = function() {
        if($scope.supplierReturnRemark == '') {
          SIEJS.alert('请填写退回原因', "error", "确定");
        }
        httpServer.post(URLAPI.productSupplierReturn, {
          'params': JSON.stringify({
            "productHeadId":$stateParams.productHeadId,
            "id":$scope.supplierlist[$scope.supplierReturnIndex].id,
            "returnReson": $scope.supplierReturnRemark,
            "versionNum":$scope.supplierlist[$scope.supplierReturnIndex].versionNum
          })
      }, function (res) {
          if (res.status == 'S') {
             SIEJS.alert("成功退回", "success", "确定");
             $timeout(function() {
              // 关闭当前页面
              window.parent.deleteHeadTab();
            },2000);
          }
          else {
              SIEJS.alert(res.msg, "error", "确定");
          }
      }, function (err) {
          SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
      });
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

      // 参考货号名称赋值
      $scope.referenceProductNameReturn = function (key, value, currentList) {
        $scope.params.consultProductno = currentList[0].plmCode;
        $scope.params.consultProductname = currentList[0].productName;
      }

      // 我的模板
      $scope.showUseMolId = function () {
        $('#useMolId').modal('toggle');
        $scope.getMolData();
      };

      $scope.getMolData = function () {
        httpServer.post(URLAPI.findTemple, {},
          function (res) {
            if (res.status == 'S') {
              $scope.modalParams = res.data[0]; 
            }
          },
          function (err) {
            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
          }
      );
      };
      // 确定应用模板
      $scope.qdUseMol = function () {
        $scope.params.productShape = $scope.modalParams.productShape;
        $scope.params.dayDamage = $scope.modalParams.dayDamage;
        $scope.params.rateClassCode = $scope.modalParams.rateClassCode;
        $scope.params.productResource = $scope.modalParams.productResource;
        $scope.params.specialLicence = $scope.modalParams.specialLicence;
        $scope.params.uniqueCommodities = $scope.modalParams.uniqueCommodities;
        $scope.params.productCategeery = $scope.modalParams.productCategeery;
        $scope.params.productProperties = $scope.modalParams.productProperties;
        $scope.params.specialtyProduct = $scope.modalParams.specialtyProduct;
        $scope.params.dangerousProduct = $scope.modalParams.dangerousProduct;
        $scope.params.buyingLevel = $scope.modalParams.buyingLevel;
        $scope.params.internationProduct = $scope.modalParams.internationProduct;
        $scope.params.posInfo = $scope.modalParams.posInfo;
        $scope.params.topProduct = $scope.modalParams.topProduct;
        $scope.params.sesionProduct = $scope.modalParams.sesionProduct;
        $scope.params.bluecapProduct = $scope.modalParams.bluecapProduct;
        $scope.params.motherCompany = $scope.modalParams.motherCompany;
        $scope.params.vcProduct = $scope.modalParams.vcProduct;
        $scope.params.crossborderProduct = $scope.modalParams.crossborderProduct;
        $scope.params.companyDeletion = $scope.modalParams.companyDeletion;
        $scope.params.warehouseResource = $scope.modalParams.warehouseResource;
        $scope.params.unit = $scope.modalParams.unit;
        $scope.params.originCountry = $scope.modalParams.originCountry;
        $scope.params.warehousePostDay = $scope.modalParams.warehousePostDay;
        $scope.params.warehouseGetDay = $scope.modalParams.warehouseGetDay;
        $scope.params.powerOb = $scope.modalParams.powerOb;
        $scope.params.rangOb = $scope.modalParams.rangOb;
        $scope.params.specialRequier = $scope.modalParams.specialRequier;
        $scope.params.tier = $scope.modalParams.tier;
        $scope.params.productLicense = $scope.modalParams.productLicense;
        $scope.params.transportStorage = $scope.modalParams.transportStorage;
        $scope.params.salesQty = $scope.modalParams.salesQty;
        $scope.params.consultProductno = $scope.modalParams.consultProductno;
        $scope.params.consultProductname = $scope.modalParams.consultProductname;
        $scope.params.pricewarProduct = $scope.modalParams.pricewarProduct;
        $scope.params.consultDate = $scope.modalParams.consultDate;
        $scope.params.consultEnddate = $scope.modalParams.consultEnddate;
        $scope.params.isDiaryproduct = $scope.modalParams.isDiaryproduct;
        $scope.params.productReturn = $scope.modalParams.productReturn;
        $scope.params.condition = $scope.modalParams.condition;
        $scope.params.allTier = $scope.modalParams.allTier;
        $scope.params.tier1 = $scope.modalParams.tier1;
        $scope.params.tier2 = $scope.modalParams.tier2;
        $scope.params.tier345 = $scope.modalParams.tier345;
        $scope.params.storeType = $scope.modalParams.storeType;
        $scope.params.tradeZone = $scope.modalParams.tradeZone;
        $scope.params.countUnit = $scope.modalParams.countUnit;
        // 商品种类pog控制
        if($scope.params.productCategeery == '1') {
          $scope.productCategeeryChangeFlag = true;
        }else{
          $scope.productCategeeryChangeFlag = false;
        }
      };

      // 复制资料
      $scope.showCopyData = function () {
        $('#copyDataId').modal('toggle');
      };
      $scope.search = function () {
        $scope.copyDataTable.search(1);
    }
      $scope.qdCopyData = function() {
        $scope.params.productShape = $scope.copyDataTable.selectRow.productShape;
        $scope.params.dayDamage = $scope.copyDataTable.selectRow.dayDamage;
        $scope.params.rateClassCode = $scope.copyDataTable.selectRow.rateClassCode;
        $scope.params.productResource = $scope.copyDataTable.selectRow.productResource;
        $scope.params.specialLicence = $scope.copyDataTable.selectRow.specialLicence;
        $scope.params.uniqueCommodities = $scope.copyDataTable.selectRow.uniqueCommodities;
        $scope.params.productCategeery = $scope.copyDataTable.selectRow.productCategeery;
        $scope.params.productProperties = $scope.copyDataTable.selectRow.productProperties;
        $scope.params.specialtyProduct = $scope.copyDataTable.selectRow.specialtyProduct;
        $scope.params.dangerousProduct = $scope.copyDataTable.selectRow.dangerousProduct;
        $scope.params.buyingLevel = $scope.copyDataTable.selectRow.buyingLevel;
        $scope.params.internationProduct = $scope.copyDataTable.selectRow.internationProduct;
        $scope.params.posInfo = $scope.copyDataTable.selectRow.posInfo;
        $scope.params.topProduct = $scope.copyDataTable.selectRow.topProduct;
        $scope.params.sesionProduct = $scope.copyDataTable.selectRow.sesionProduct;
        $scope.params.bluecapProduct = $scope.copyDataTable.selectRow.bluecapProduct;
        $scope.params.motherCompany = $scope.copyDataTable.selectRow.motherCompany;
        $scope.params.vcProduct = $scope.copyDataTable.selectRow.vcProduct;
        $scope.params.crossborderProduct = $scope.copyDataTable.selectRow.crossborderProduct;
        $scope.params.companyDeletion = $scope.copyDataTable.selectRow.companyDeletion;
        $scope.params.warehouseResource = $scope.copyDataTable.selectRow.warehouseResource;
        $scope.params.unit = $scope.copyDataTable.selectRow.unit;
        $scope.params.originCountry = $scope.copyDataTable.selectRow.originCountry;
        $scope.params.warehousePostDay = $scope.copyDataTable.selectRow.warehousePostDay;
        $scope.params.warehouseGetDay = $scope.copyDataTable.selectRow.warehouseGetDay;
        $scope.params.powerOb = $scope.copyDataTable.selectRow.powerOb;
        $scope.params.rangOb = $scope.copyDataTable.selectRow.rangOb;
        $scope.params.specialRequier = $scope.copyDataTable.selectRow.specialRequier;
        $scope.params.tier = $scope.copyDataTable.selectRow.tier;
        $scope.params.productLicense = $scope.copyDataTable.selectRow.productLicense;
        $scope.params.transportStorage = $scope.copyDataTable.selectRow.transportStorage;
        $scope.params.salesQty = $scope.copyDataTable.selectRow.salesQty;
        $scope.params.consultProductno = $scope.copyDataTable.selectRow.consultProductno;
        $scope.params.consultProductname = $scope.copyDataTable.selectRow.consultProductname;
        $scope.params.pricewarProduct = $scope.copyDataTable.selectRow.pricewarProduct;
        $scope.params.consultDate = $scope.copyDataTable.selectRow.consultDate;
        $scope.params.consultEnddate = $scope.copyDataTable.selectRow.consultEnddate;
        $scope.params.isDiaryproduct = $scope.copyDataTable.selectRow.isDiaryproduct;
        $scope.params.productReturn = $scope.copyDataTable.selectRow.productReturn;
        $scope.params.condition = $scope.copyDataTable.selectRow.condition;
        $scope.params.allTier = $scope.copyDataTable.selectRow.allTier;
        $scope.params.tier1 = $scope.copyDataTable.selectRow.tier1;
        $scope.params.tier2 = $scope.copyDataTable.selectRow.tier2;
        $scope.params.tier345 = $scope.copyDataTable.selectRow.tier345;
        $scope.params.storeType = $scope.copyDataTable.selectRow.storeType;
        $scope.params.tradeZone = $scope.copyDataTable.selectRow.tradeZone;
        $scope.params.countUnit = $scope.copyDataTable.selectRow.countUnit;
      };

      // 字段说明
      $scope.getHelpData = function() {
        httpServer.post(URLAPI.findPlmHelpStatementsInfo, {},
          function (res) {
            if (res.status == 'S') {
              $scope.helpData = res.data; 
              console.log(res.data,'res.data');
              
            }
          },
          function (err) {
            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
          });
      };

      // 地区选择
      $scope.selectArea = function() {
        $('#citysModal').modal('show');
      };

      $scope.citySave1 = function() {
        $('#citysModal').modal('hide');
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
          if($scope.qalist.length<1) {
            SIEJS.alert('请填写对应文件的驳回原因', "success", "确定");
          }else{
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
                  $timeout(function() {
                    // 关闭当前页面
                    window.parent.deleteHeadTab();
                  },2000);
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            },
            function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
          }
        }
        if(list == 'picfilelist') {
          var returnPicArr = [];
          if($scope.qalist.length<1) {
            SIEJS.alert('请填写对应文件的驳回原因', "success", "确定");
          }else{
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
      }



      // 每隔120秒，自动保存
      var autoSave = setInterval(function() {
        $scope.autoSave();
      },120000);

      // 优先供应商
      $scope.supplierSelectcheckbox=function(index){
        if($scope.supplierlist[index].isMainsupplier=='Y'){
          angular.forEach($scope.supplierlist,function(obj,rowindex){
            if(rowindex!=index){
              obj.isMainsupplier='N';
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

    }]
  );
})