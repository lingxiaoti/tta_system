define(["app","angularFileUpload",'XLSX'], function (app, XLSX) {
  app.useModule('angularFileUpload');//按需加载弹出层模块
  app.controller('newApplicationFormCtrl', function ($scope,TableToExcel, URLAPI,$timeout, $rootScope, $state, $stateParams, iframeTabAction, httpServer,FileUploader,SIEJS) {
      //do something here
      $scope.params={};
      $scope.goodsSearchparams = {
        orderStatus:'6'
      }
      //搜索
      $scope.search = function () {
          $scope.dataTable.search(1);
      }
      if(true) {
        $scope.upOloadPlmSupplementHeadId = $stateParams.productId;
      }


      // 新增
      $scope.newGoods = function() {
        $scope.newDataTable.search(1);
        $('#goodsSearchId').modal('toggle');
        $scope.newDataTable.selectRowList=[];
        angular.forEach($scope.newDataTable.selectRowList,function(obj,rowindex){
           obj.checked=false;
        });
      }

      $scope.userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
      
      // $scope.formParams={
    	// 	  userId:$scope.userLoginInfo.userId
      // };
      
      
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

      $scope.tabChangeInfo = function (name) {
        $scope.tabAction = name;
        if (name == 'flowChart') {
          $scope.tabChange('flowChart');
        }
      };
      
      
      //流程图参数
      $scope.processImageParams = {
        token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
        id: 'processImg',
        instanceId: null,
        key: 'SUPPLEMENT.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
      };
  
      $scope.tabChange = function (name) {
        console.log(123123123);
          $scope.tabAction = name;
          if (!$scope.processImgLoad) {
              $scope.getProcessInfo(function () {
                  var p = $scope.processImageParams;
                  $timeout(function () {
                      processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                      $scope.processImgLoad = true;
                  }, 100);
              });
          }
      };
      
      $scope.processInstanceParams = {
        processInstanceId:''
    }
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
            console.log(res.data.toString());
            $scope.processImageParams.instanceId = res.data.processInstanceId;
            $scope.processInstanceParams.processInstanceId=res.data.processInstanceId;
            console.log($scope.processInstanceParams.processInstanceId, '$scope.processInstanceParams.processInstanceId');
          }
          callback && callback(res);
        });
      };
      
      
      // 查询
      $scope.query = function(productId) {
        httpServer.post(URLAPI.plmSupplementHeadService_findPlmsInfoDetail, {
          'params': JSON.stringify({'plmSupplementHeadId': productId})
        }, function (res) {
            if (res.status == 'S') {
              $scope.params = res.data;
              // 把所有元素禁用
              if($scope.params.orderStatus == '1') {
                $("#dataTable").find('input, textarea,select,span,a,button,input[type="radio"],input[type="checkbox"]').attr('disabled', true)
                $("#outbaseInfBoxID").find('input,textarea,select,span,a,button,input[type="radio"],input[type="checkbox"]').attr('disabled', true);
              }
            }
            else {
              SIEJS.alert(res.msg, "error", "确定");
            }
        }, function (err) {
            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
        });
      }
      
      // 导入完之后刷新
      $scope.reflash = function() {
        $scope.query($scope.id);
      }

      if($stateParams.productId) {
    	 $scope.id=$stateParams.productId;
        $scope.query($scope.id);
        $scope.liuchengPicture = true;
      }else if($scope.urlParams.businessKey){
    	  $scope.id = $scope.urlParams.businessKey;
    	  $scope.query($scope.id);
        $scope.liuchengPicture = false;
    	  
          //保存当前用户id
          $scope.businessparam={
        	type:'replenishment'
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
      else
    	  {
    	  $scope.params.orderStatus = '0';
    	  }
      

      // 保存
      $scope.btnSave = function() {
        httpServer.post(URLAPI.savePlmSupplementInfo, {
          'params': JSON.stringify($scope.params)
        }, function (res) {
            if (res.status == 'S') {
              SIEJS.alert('保存成功', "success", "确定");
              $scope.id=res.data;
              $scope.upOloadPlmSupplementHeadId=res.data;
              $scope.query(res.data);
            }
            else {
                SIEJS.alert(res.msg, "error", "确定");
            }
        }, function (err) {
            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
        });
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


      // 新增产品
      $scope.newData = function(){
        $scope.lines = $scope.newDataTable.selectRowList;
        $scope.lines.forEach((item) => {
          item['item'] = item.rmsCode;
        });
        httpServer.post(URLAPI.findPlmSupItem, {
          'params': JSON.stringify({lines: $scope.lines})
        }, function (res) {
            if (res.status == 'S') {
              if($scope.params.lines&&$scope.params.lines.length>0) {
                res.lines.forEach((item) => {
                  $scope.params.lines.push(item);
                });
              }else{
                $scope.params.lines = res.lines;
              }
            }
            else {
                SIEJS.alert(res.msg, "error", "确定");
            }
        }, function (err) {
            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
        });
      }

      // 删除
      $scope.del = function(item, index) {
        if(item.plmSupplementLineId == undefined) {
          $scope.params.lines.splice(index, 1);
        }else{
          SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
            httpServer.post(URLAPI.removePlmLine, {
              'params': JSON.stringify({'plmSupplementLineId': parseInt(item.plmSupplementLineId)})
            }, function (res) {
                if (res.status == 'S') {
                  SIEJS.alert('删除成功', "success", "确定");
                  if($stateParams.productId) {
                    $scope.query($stateParams.productId);
                  }else if($scope.id){
                    $scope.query($scope.id);
                  }
                }
                else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
          })
        }
      }

      // 提交验证
      $scope.btnSubmit = function() {
        if($scope.params.plmSupplementHeadId) {
          httpServer.post(URLAPI.updateShopsSupBefore, {
            'params': JSON.stringify({'headId': $scope.params.plmSupplementHeadId})
          }, function (res) {
              if (res.status == 'S') {
                $scope.submit();
              }
              else {
                SIEJS.alert(res.msg, "error", "确定");
              }
          }, function (err) {
              SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
          });
        }else{
          SIEJS.alert('请先保存，再提交', "warning", "确定");
        }
      }

      // 提交
      $scope.submit = function() {
          $scope.params.orderStatus= 1;
          httpServer.post(URLAPI.savePlmSupplementInfo, {
            'params': JSON.stringify($scope.params)
          }, function (res) {
              if (res.status == 'S') {
                //SIEJS.alert('提交成功', "success", "确定");
                $scope.id=res.data;
                $scope.query($scope.id);
                $scope.liuchen();
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

      // 作废
      $scope.btnDiscard = function() {
        $scope.params.orderStatus= -1;
        httpServer.post(URLAPI.savePlmSupplementInfo, {
          'params': JSON.stringify($scope.params)
        }, function (res) {
            if (res.status == 'S') {
              SIEJS.alert('作废成功', "success", "确定");
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

      // 跳转到商品维护
      $scope.goDetails = function(item){
        if(item.plmSupplementLineId!==undefined) {
          iframeTabAction('商品维护:' , '/goodsMaintenance/'+item.plmSupplementLineId)
          sessionStorage.setItem('orderStatus', $scope.params.orderStatus)
        }else{
          SIEJS.alert('请保存数据', "error", "确定");
        }
      }

      // 导入
      $scope.fileopen = function () {
        $('#filediv').modal('toggle');
      }

     
     $scope.qd=function(){
      $('#filediv').modal('toggle');
        httpServer.post(URLAPI.importWarehouse, {
            'params': JSON.stringify({'filepath':$scope.excelpath})
          },
        function(res) {
          if (res.status == 'S') {
            SIEJS.alert('操作成功','success',"确定");
          }
          else{
            SIEJS.alert(res.msg,'error',"确定");
          }
        });
     }

      $scope.ZsFileFileUploader=new FileUploader({
        url: URLAPI.imgUpload,
          autoUpload: true
      });
      $scope.ZsFileFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {
           // SIEJS.alert("上传成功！", "success", "确定");
          var filePath=res.data[0].filePath;
          var filename=res.data[0].fileName;
           var length=filename.length;
           var index=filename.lastIndexOf(".")+1;
           
           var suffx=filename.substring(index,length);

           if(suffx!='xls'&&suffx!='xlsx')
             {
             SIEJS.alert("请选择excel文件！", "error", "确定");
             return;
             }
           else
             {
            $scope.excelpath=filePath;
    
             }//else
           
        };
        if (res.status == 'E') {
            SIEJS.alert("操作失败! "+res.msg, "error", "确定");
        };
      };	
      
      $scope.showMePoMol = function(index) {
        $('#showMePoMol').modal('toggle');
        $scope.paramsLinesIndex = index;
      }
      
      $scope.showMePoMolReturn = function (key, value, currentList) {
        $scope.params.lines[$scope.paramsLinesIndex].pogCode = currentList[0].pogCode;
        $scope.params.lines[$scope.paramsLinesIndex].meter = currentList[0].meter;
      };

      //补货状态Change
      $scope.supplementStatusChange = function(index) {
        if($scope.params.lines[index].supplementStatus == '1') {
          $scope.params.lines[index].stopReason = '';
        }
      }
        
  //  提交审批流
      $scope.liuchen = function () {

        $scope.extend ={
                "tasks_assignees":[]
            }
        $scope.variables = []; //流程变量
        //$scope.variables.push({ name:'ISDEPTS',type: 'int',value:0});
            //angular.forEach($scope.formParams, function (value, key) {
               // $scope.variables.push({
                    //name: key,
                  //  type: 'string',
                //    value: value
              //  });
            //});
       var userType= $scope.userLoginInfo.userType;
       if(userType=='60')
    	   {
    	   $scope.variables.push({ name:'supply',type: 'int',value:1});
    	   $scope.variables.push({ name:'buyer',type: 'int',value:0});
    	   }
       else{
    	   //判断部门
    	   var deptcode=$scope.userLoginInfo.deptCode;
    	   var deptsort=deptcode.substr(0,1); //截取开头
    	   if(deptsort=='2')
    		   {
    	  	   $scope.variables.push({ name:'supply',type: 'int',value:0});
        	   $scope.variables.push({ name:'buyer',type: 'int',value:1});
        	   $scope.variables.push({ name:'dept1',type: 'int',value:1});
        	   $scope.variables.push({ name:'dept2',type: 'int',value:0});
        	   $scope.variables.push({ name:'dept3',type: 'int',value:0});

        	   
        	   //orderType
        	   var orderType=$scope.params.orderType;
        	   if(orderType=='0') //促销订单  promotion
        		   {
        		   $scope.variables.push({ name:'status2',type: 'int',value:1});
        		   $scope.variables.push({ name:'status1',type: 'int',value:0});
        		   $scope.variables.push({ name:'status3',type: 'int',value:0});
        		   }
        	   else if(orderType=='1') //陈列图订单
        		   {
        		   $scope.variables.push({ name:'status2',type: 'int',value:0});
        		   $scope.variables.push({ name:'status1',type: 'int',value:1});
        		   $scope.variables.push({ name:'status3',type: 'int',value:0});
        		   }
        	   else
        		   {
        		   $scope.variables.push({ name:'status2',type: 'int',value:0});
        		   $scope.variables.push({ name:'status1',type: 'int',value:0});
        		   $scope.variables.push({ name:'status3',type: 'int',value:1});
        		   }
    		   }
    	   else if(deptsort=='3')
    		   {
      	  	   $scope.variables.push({ name:'supply',type: 'int',value:0});
        	   $scope.variables.push({ name:'buyer',type: 'int',value:1});
        	   $scope.variables.push({ name:'dept1',type: 'int',value:0});
        	   $scope.variables.push({ name:'dept2',type: 'int',value:1});
        	   $scope.variables.push({ name:'dept3',type: 'int',value:0});
    		   }
    	   else if(deptsort=='5')
    		   {
    	 	   $scope.variables.push({ name:'supply',type: 'int',value:0});
        	   $scope.variables.push({ name:'buyer',type: 'int',value:1});
        	   $scope.variables.push({ name:'dept1',type: 'int',value:0});
        	   $scope.variables.push({ name:'dept2',type: 'int',value:0});
        	   $scope.variables.push({ name:'dept3',type: 'int',value:1});
    		   }
    	   
    	   
    	   
       }
            $scope.properties={
                "menucode": "HTGL",
                "opinion": ""
                //"userId": "userId":userLoginInfo.userId,
            };
            $scope.paramsBpm={
                "extend":$scope.extend,
                "variables":$scope.variables,
                "properties":$scope.properties,
                "processDefinitionKey":'SUPPLEMENT.-999', //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.id, //单据ID
                "title": "商品补货流程", //流程标题，修改为当前业务信息
                "positionId": 0,
                "orgId": 0,
                "userId":$scope.userLoginInfo.userId,
                "userType": $scope.userLoginInfo.userType,
                "billNo": $scope.params.supCode
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
      
      
  });
});
