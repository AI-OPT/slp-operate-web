define('app/jsp/producthome/productHome', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index');
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    var serviceNum={};
    
    //定义页面组件类
    var ProductHomePager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		//查询
            //"click #BTN_SEARCH":"_searchBtnClick"
            //"click #thumbnailId":"_changeImage",
            "click #moreproduct":"_getMore",
            "click #refresh":"_getHotProduct",
            "click #phoneBillCucc":"_getPhoneBill",
            "click #phoneBillCmcc":"_getPhoneBill",
            "click #phoneBillCtcc":"_getPhoneBill",
            "click #flowCmcc":"_getFlowProduct",
            "click #flowCtcc":"_getFlowProduct",
            "click #flowCucc":"_getFlowProduct",
            "keyup  #phoneNum1":"_getPhoneInfo"	,
            "keyup  #phoneNum2":"_getGprs",	
            "change #phoneFee":"_changeHuafei",
            "change #location":"_changeLocation",
            "click #CZ_BTN":"_submitOrder",
            "click #GPRS_BTN":"_submitGprs"
           
            
        },
    	//重写父类
    	setup: function () {
    		ProductHomePager.superclass.setup.call(this);
    		//初始化执行搜索
    		this._getPhoneBill();
    		this._getFlowProduct();
    		this._getHotProduct();
    	},
    	_submitGprs:function(){//话费的
    		var _this=this;
    		var phoneNum=$.trim($("#phoneNum2").val());
    		if(phoneNum==null||phoneNum==""||phoneNum==undefined){
    			Dialog({
							title : '提示',
							width : '200px',
							height : '50px',
							content : "手机号不能为空",
							okValue : "确定",
							ok : function() {
								this.close;
							}
						}).showModal();
    			return false;
    		}
    		var phoneFee=$.trim($("#gprs").val());
    		if(phoneFee==null||phoneFee==""||phoneFee==undefined){
    			Dialog({
					title : '提示',
					width : '200px',
					height : '50px',
					content : "请选择充值费用",
					okValue : "确定",
					ok : function() {
						this.close;
					}
				}).showModal();
    			return false;
    		}
    		var start=$("#gprs").val().indexOf(";")+1;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				url: _base+"/order/orderCommit",
				data:{
					orderType:"100010",//暂时传运营商的县官信息
					skuId:$("#gprs").val().substr(start,$("#gprs").val().length),
					buySum:"1",
					basicOrgId:$("#gbasicOrgId").val(),
					provinceCode:$("#PCode").val(),
					chargeFee:$("#gprs option:selected").text(),
					phoneNum:$("#phoneNum2").val()
					},
				success: function(data){
					var key=data.data;
					window.location.href = _base
					+ "/order/toOrderPay?orderKey="+key;

				}
			});
    	},
    	_submitOrder:function(){//话费的
    		var _this=this;
    		var phoneNum=$.trim($("#phoneNum1").val());
    		if(phoneNum==null||phoneNum==""||phoneNum==undefined){
    			Dialog({
							title : '提示',
							width : '200px',
							height : '50px',
							content : "手机号不能为空",
							okValue : "确定",
							ok : function() {
								this.close;
							}
						}).showModal();
    			return false;
    		}
    		var phoneFee=$.trim($("#phoneFee").val());
    		if(phoneFee==null||phoneFee==""||phoneFee==undefined){
    			Dialog({
					title : '提示',
					width : '200px',
					height : '50px',
					content : "请选择充值费用",
					okValue : "确定",
					ok : function() {
						this.close;
					}
				}).showModal();
    			return false;
    		}
    		
    		var start=$("#phoneFee").val().indexOf(";")+1;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				url: _base+"/order/orderCommit",
				data:{
					orderType:"100010",//暂时传运营商的县官信息
					skuId:$("#phoneFee").val().substr(start,$("#phoneFee").val().length),
					buySum:"1",
					basicOrgId:$("#basicOrgId1").val(),
					provinceCode:$("#PCode").val(),
					chargeFee:$("#phoneFee option:selected").text(),
					phoneNum:$("#phoneNum1").val()
					},
				success: function(data){
					var key=data.data;
					window.location.href = _base
					+ "/order/toOrderPay?orderKey="+key;

				}
			});
    	},
    	_getPhoneInfo:function(){
    		var _this=this;
    		//如果等于11去查询，如果小于11把之前查询出来的信息清除
    		if($.trim($("#phoneNum1").val()).length==11){
    			 var mobileReg = /^0?1[3|4|5|8|7][0-9]\d{8}$/; 
    			 if(mobileReg.test($.trim($("#phoneNum1").val()))==false){
    				 Dialog({
    						title : '提示',
    						width : '200px',
    						height : '50px',
    						content : "手机号格式不对，请重新输入",
    						okValue : "确定",
    						ok : function() {
    							this.close;
    						}
    					}).showModal();
    	    			return false;

    			 }
    			ajaxController.ajax({
					type: "post",
					dataType: "json",
				
					url: _base+"/getPhoneInfo",
					data:{
						phoneNum:$.trim($("#phoneNum1").val()).substr(0,7)
						},
					success: function(data){
						var d=data.data;
						if(d){
							serviceNum=data;
							//var productCatId="10000010010000";
							$("#basicOrgId1").val(d.basicOrgCode);
							$("#PCode").val(d.provinceCode);
							var provCode=d.provinceCode;
							var basicOrgId=d.basicOrgCode;
							//userType 
							//userId
							ajaxController.ajax({
								type: "post",
								dataType: "json",
							
								url: _base+"/getFastInfo",
								data:{
									provCode:provCode,
									basicOrgId:basicOrgId
									},
								success: function(data){
									var d=data.data;
									$("#phoneFee").html("");
									if(d){
										var phoneFee=d.phoneFee;
										$.each(phoneFee,function(index,item){
											var paramName = phoneFee[index].content;
											var paramCode = phoneFee[index].skuInfo.salePrice+";"+phoneFee[index].skuInfo.skuId;
											$("#phoneFee").append('<option value="'+paramCode+'">'+paramName+'</option>');
											_this._changeHuafei();
										})
									}
								}
							});
							
						}
					}
				});
    		}
    	
    	},
    	_changeHuafei:function(){
    		//console.log($("#phoneFee").val().substr(0,$("#phoneFee").val().indexOf(";")));
    		$("#realFee").text(this._liToYuan($("#phoneFee").val().substr(0,$("#phoneFee").val().indexOf(";"))));//liToYuan
    	},
    	_getGprs:function(){
    		var _this=this;
            if($.trim($("#phoneNum2").val()).length==11){
    			
    			ajaxController.ajax({
					type: "post",
					dataType: "json",
				
					url: _base+"/getPhoneInfo",
					data:{
						phoneNum:$.trim($("#phoneNum2").val()).substr(0,7)
						},
					success: function(data){
						var d=data.data;
						if(d){
							serviceNum=data;
							//var productCatId="10000010010000";
							$("#gbasicOrgId").val(d.basicOrgCode);
							$("#PCode1").val(d.provinceCode);
							
							var provCode=d.provinceCode;
							var basicOrgId=d.basicOrgCode;
							$("#gprs").html("");
							//userType 
							//userId
							ajaxController.ajax({
								type: "post",
								dataType: "json",
								url: _base+"/getFastGprs",
								data:{
									provCode:provCode,
									basicOrgId:basicOrgId,
									location:$("#location").val()
									},
								success: function(data){
									var d=data.data;
									
									if(d){
										var phoneFee=d.phoneFee;
										$.each(phoneFee,function(index,item){
											var paramName = phoneFee[index].content;
											var paramCode = phoneFee[index].skuInfo.salePrice+";"+phoneFee[index].skuInfo.skuId;
											$("#gprs").append('<option value="'+paramCode+'">'+paramName+'</option>');
											_this._changeGprsValue();
										})
									}
								}
							});
							
						}
					}
				});
    		}
    	},
    	_changeLocation:function(){
    		var _this=this;
    		$("#gprs").html("");
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				url: _base+"/getFastGprs",
				data:{
					provCode:$("#PCode1").val(),
					basicOrgId:$("#gbasicOrgId").val(),
					location:$("#location").val()
					},
				success: function(data){
					var d=data.data;
					if(d&&d.phoneFee){
						var phoneFee=d.phoneFee;
						$.each(phoneFee,function(index,item){
							var paramName = phoneFee[index].content;
							var paramCode = phoneFee[index].skuInfo.salePrice+";"+phoneFee[index].skuInfo.skuId;
							$("#gprs").append('<option value="'+paramCode+'">'+paramName+'</option>');
							_this._changeGprsValue();
						})
					}
				}
			});
    	},
    	_changeGprsValue:function(){
    		$("#realFee1").text(this._liToYuan($("#gprs").val().substr(0,$("#gprs").val().indexOf(";"))));//liToYuan
    	},
    	_getFastPhoneInfo:function(provCode,basicOrgId){
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
			
				url: _base+"/getFastInfo",
				data:{
					provCode:provCode,
					basicOrgId:basicOrgId
					},
				success: function(data){
					var d=data.data;
				}
			});
    	},
    	_getPhoneBill:function(){
    		//类目
    		var oprator;
    		//获取运营商类目
      		var isCmcc = $("#phoneBillCmcc").attr("class");
      		var isCtcc = $("#phoneBillCtcc").attr("class");
      		var isCucc = $("#phoneBillCucc").attr("class");
      		if(isCmcc=="current"){
      			$("#phoneOprator").val($("#phoneBillCmcc").attr("opratorid"));
      		}else if(isCtcc){
      			$("#phoneOprator").val($("#phoneBillCtcc").attr("opratorid"));
      		}else if(isCucc){
      			$("#phoneOprator").val($("#phoneBillCucc").attr("opratorid"));
      		}
      		var	param={
					areaCode:"11",  
					productCatId: "10000010010000",	   
					basicOrgIdIs:$("#phoneOprator").val()
				   };
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/getPhoneBill",
						data:param,
						success: function(data){
							if(data.data){
								var template = $.templates("#phoneBillTmpl");
								var htmlOut = template.render(data.data);
								$("#phoneBillData").html(htmlOut);
							}
						}
					}
      		);
      	},
      	_getFlowProduct: function(){
      		var _this=this;
      		//流量类目ID
      		var productId="10000010020000";
      		var oprator;
      		//获取运营商类目
          		var isCmcc = $("#flowCmcc").attr("class");
          		var isCtcc = $("#flowCtcc").attr("class");
          		var isCucc = $("#flowCucc").attr("class");
          		if(isCmcc=="current"){
          			$("#flowOprator").val($("#flowCmcc").attr("opratorid"));
          		}else if(isCtcc){
          			$("#flowOprator").val($("#flowCtcc").attr("opratorid"));
          		}else if(isCucc){
          			$("#flowOprator").val($("#flowCucc").attr("opratorid"));
          		}
          		var	param={
    					areaCode:"11",  
    					productCatId: "10000010020000",	   
    					basicOrgIdIs:$("#flowOprator").val()
    				   };
      		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "查询中，请等待...",
				url: _base+"/getFlow",
				data:param,
				success: function(data){
					if(data.data){
						var template = $.templates("#flowTmpl");
						var htmlOut = template.render(data.data);
						$("#flowData").html(htmlOut);
					}
				}
			});
      	},
      	_getMore: function(){
      		var agent = $("#flowOprator").val();
      		alert(agent);
      		var productCatId ="10000010020000";
      		window.location.href = _base + '/search/list?billType='+productCatId+"&orgired="+agent;;
      	},
      //详情页面
    	_detailPage: function(skuId){
    		window.location.href = _base +'/product/detail?skuId='+skuId;
    	},
      	_jumpToSearch: function(price,type){
      		//获取运营商类目
      		var isCmcc = $("#cmccShowId").is(":visible");
      		var isCtcc = $("#ctccShowId").is(":visible");
      		var isCucc = $("#cuccShowId").is(":visible");
      		if(isCmcc){
      			//移动
      			var orgired="10";
      			window.location.href = _base + '/search/list?priceId='+price+"&billType="+type+"&orgired="+orgired;
      		}else if(isCtcc){
      			//电信
      			var orgired="11";
      			window.location.href = _base + '/search/list?priceId='+price+"&billType="+type+"&orgired="+orgired;
      		}else if(isCucc){
      			//联通
      			var orgired="12";
      			window.location.href = _base + '/search/list?priceId='+price+"&billType="+type+"&orgired="+orgired;
      		}
      	},
      	_fmoney:function(s, n) {
      		n = n > 0 && n <= 20 ? n : 2;
      		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
      		var l = s.split(".")[0].split("").reverse(),
      		r = s.split(".")[1];
      		t = "";
      		for(i = 0; i < l.length; i ++ ){   
      			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
      		}
      		return t.split("").reverse().join("") + "." + r;
      	},
      		_liToYuan:function(li){
      			var result = '0.00';
      			if(isNaN(li) || !li){
      				return result;
      			}
      	        return this._fmoney(parseInt(li)/1000, 2);
      		},
      	_getHotProduct: function(){
      		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "查询中，请等待...",
				url: _base+"/getHotProduct",
				data:{areaCode:"11"},
				success: function(data){
					if(data.data){
						var template = $.templates("#hotTmpl");
						var htmlOut = template.render(data.data);
						$("#hotData").html(htmlOut);
					}
				}
			}
		);
      	}
    	
    });
    
    module.exports = ProductHomePager
});

