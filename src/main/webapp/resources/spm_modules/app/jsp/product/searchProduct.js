define('app/jsp/product/searchProduct', function (require, exports, module) {
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
    //定义页面组件类
    var QueryProductPager = Widget.extend({
    	
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
            "click #BTN_SEARCH":"_search",
            "click #moreId":"_more"
        },
    	//重写父类
    	setup: function () {
    		QueryProductPager.superclass.setup.call(this);
    		//初始化执行搜索
    		this._getCity();
    		var sourceFlag = $("#sourceFlag").val();
    		var name = $("#skuName").val();
    		$("#serachName").val(name);
    		if(sourceFlag=="00"){
    			this._search();
    		}else{
    			this._searchBtnClick();
    		}
    		this._getHotProduct();
    	},
    	//搜索操作
    	_search: function(){
    		var code =$("#currentCity").attr("currentCityCode");
    		var	param={
					areaCode:code,  
					skuName:$("#serachName").val()
				   };
    		var _this = this;
    		var url = _base+"/search/commonSearch";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data : param,
	           	pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		//获取公共数据
    					_this._getCommonBySearch();
	            		var template = $.templates("#productListTemple");
    					var htmlOutput = template.render(data);
    					$("#productData").html(htmlOutput);
    					//设置title
    					var type = $("#catType").val();
    					if(type=="10000010010000"){
    						document.getElementById("typeTitleId").innerHTML="话费充值";
    					}else{
    						document.getElementById("typeTitleId").innerHTML="流量充值";
    					}
    					//获取所在地code
    					var name ="地域:"+$("#currentCity").attr("currentCityName");
    					document.getElementById("areaTile").innerHTML=name;
	            	}else{
	            		//隐藏公共信息
	            		$("#commonId").attr("style","display: none");
	            		$("#commonData").attr("style","display: none");
    					$("#productData").html("没有搜索到相关信息");
	            	}
	            },
	            callback: function(data){
					 $("#totalcount").text(data.count);
					 $("#pageno").text(data.pageNo);
					 $("#pagecount").text(data.pageCount);
					 
				},
    		});
    	},
    	
    	//首页搜索跳转操作
    	_searchBtnClick: function(){
    		//设置title
			var type = $("#billType").val();
			if(type=="10000010010000"){
				document.getElementById("typeTitleId").innerHTML="话费充值";
			}else{
				document.getElementById("xsWord").innerHTML="流量:";
				document.getElementById("typeTitleId").innerHTML="流量充值";
			}
			//获取所在地code
			var code =$("#currentCity").attr("currentCityCode");
			var name ="地域:"+$("#currentCity").attr("currentCityName");
			document.getElementById("areaTile").innerHTML=name;
    		var	param={
					areaCode:code,  
					productCatId: $("#billType").val(),
					basicOrgIdIs: $("#orgired").val(),
					attrDefId:$("#priceId").val()
				   };
    		var _this = this;
    		var url = _base+"/search/getProduct";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data : param,
	           	pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#productListTemple");
    					var htmlOutput = template.render(data);
    					$("#productData").html(htmlOutput);
    					//获取公共数据
    					_this._getCommonProduct();
	            	}else{
	            		$("#commonId").attr("style","display: none");
	            		$("#commonData").attr("style","display: none");
    					$("#productData").html("没有搜索到相关信息");
	            	}
	            },
	            callback: function(data){
					 $("#totalcount").text(data.count);
					 $("#pageno").text(data.pageNo);
					 $("#pagecount").text(data.pageCount);
				},
    		});
    	},
    	//地区显示
		_more: function(){
			var isCmcc = $("#lastArea").is(":visible");
			if(isCmcc){
				$("#lastArea").attr("style","display:none");
			}else{
				$("#lastArea").attr("style","display:");
			}
			
		},
		//热门推荐
    	_getHotProduct:function(){
    		//获取所在地code
			var code =$("#currentCity").attr("currentCityCode");
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/search/getHotProduct",
						data:{areaCode:code},
						success: function(data){
							if(data.data){
								var template = $.templates("#hotProductListTmpl");
								var htmlOut = template.render(data.data);
								$("#hotProductData").html(htmlOut);
							}
						}
					}
      		);
      	},
      	//搜索获取公共数据
      	_getCommonBySearch:function(){
      		var code =$("#currentCity").attr("currentCityCode");
      		var	param={
					areaCode:code,  
					skuName:$("#serachName").val()
				   };
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/search/getCommonBySearch",
						data:param,
						pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
						success: function(data){
							var template = $.templates("#agentTmpl");
							var htmlOut = template.render(data.data);
							$("#agentData").html(htmlOut);
							var template1 = $.templates("#accountTmpl");
							var htmlOut1 = template1.render(data.data);
							$("#accountData").html(htmlOut1);
							var template2 = $.templates("#areaTmpl");
							var htmlOut2 = template2.render(data.data);
							$("#areaData").html(htmlOut2);
							var template3 = $.templates("#lastAreaTmpl");
							var htmlOut3 = template3.render(data.data);
							$("#lastAreaData").html(htmlOut3);
						}
					}
      		);
      	},
      	//获取公共数据
      	_getCommonProduct:function(){
      		var code =$("#currentCity").attr("currentCityCode");
      		var	param={
					areaCode:code,  
					productCatId: $("#billType").val(),
					basicOrgIdIs:$("#orgired").val(),
					attrDefId:$("#priceId").val()
				   };
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/search/getCommon",
						data:param,
						pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
						success: function(data){
							var template = $.templates("#agentTmpl");
							var htmlOut = template.render(data.data);
							$("#agentData").html(htmlOut);
							var template1 = $.templates("#accountTmpl");
							var htmlOut1 = template1.render(data.data);
							$("#accountData").html(htmlOut1);
							var template2 = $.templates("#areaTmpl");
							var htmlOut2 = template2.render(data.data);
							$("#areaData").html(htmlOut2);
							var template3 = $.templates("#lastAreaTmpl");
							var htmlOut3 = template3.render(data.data);
							$("#lastAreaData").html(htmlOut3);
							
						}
					}
      		);
      	},
      //详情页面
    	_detailPage: function(skuId){
    		window.location.href = _base +'/product/detail?skuId='+skuId;
    	},
    	//改变运营商查询条件
    	_changeAgent: function(agentId){
    		var _this = this;
    		//删除原来样式
    		var oldAgent = $("#agentSearch").val();
    		document.getElementById(oldAgent).className="";
    		$("#agentSearch").val(agentId);
    		var newAgent=  "#"+agentId;
    		$(newAgent).addClass("current");
    		_this._changeDataClick();
    		
    	},
    	//改变面额
    	_changePrice: function(priceId){
    		var _this = this;
    		//删除原来样式
    		var oldPrice = $("#priceSearch").val();
    		document.getElementById(oldPrice).className="";
    		$("#priceSearch").val(priceId);
    		var newPrice=  "#"+priceId;
    		$(newPrice).addClass("current");
    		_this._changeDataClick();
    	},
    	//改变地区
    	_changeArea: function(areaId){
    		var _this = this;
    		//删除原来样式
    		var oldArea = $("#areaSearch").val();
    		document.getElementById(oldArea).className="";
    		$("#areaSearch").val(areaId);
    		var newArea=  "#"+areaId;
    		$(newArea).addClass("current");
    		_this._changeDataClick();
    	},
    	//根据选择条件进行查询
    	_changeDataClick: function(){
    		var	param={
					areaCode:$("#areaSearch").val(),
					productCatId: $("#catType").val(),
					basicOrgIdIs: $("#agentSearch").val(),
					attrDefId:$("#priceSearch").val(),
					priceOrderFlag:$("#priceOrder").attr("value"),
					saleNumOrderFlag:$("#saleOrder").attr("value")
				   };
    		var _this = this;
    		var url = _base+"/search/getProduct";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data : param,
	           	pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#productListTemple");
    					var htmlOutput = template.render(data);
    					$("#productData").html(htmlOutput);
    					//获取公共数据
    					//_this._getCommonBySearch();
	            	}else{
	            		//$("#commonId").attr("style","display: none");
	            		$("#commonData").attr("style","display: none");
    					$("#productData").html("没有搜索到相关信息");
	            	}
	            },
	            callback: function(data){
					 $("#totalcount").text(data.count);
					 $("#pageno").text(data.pageNo);
					 $("#pagecount").text(data.pageCount);
				},
    		});
    	},
    	//获取配送地区
    	_getCity: function(){
    		var _this = this;
      		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "查询中，请等待...",
				url: _base+"/head/getArea",
				data:'',
				success: function(data){
					if(data.data){
						var template = $.templates("#dispatchCityTmpl");
						var htmlOut = template.render(data.data);
						$("#dispatchCityShowData").html(htmlOut);
						_this._changeDispath();
					}
				}
			}
		);
      },
      _changeDispath : function() {
			$(".DSP_BTN").bind(
				"click",
				function() {
					var _this = this;
					var cityCode = $(_this).attr('areaCodeId');
					var cityName = $(_this).attr('areaNameId');
					$("#currentDispatch").attr("currentDispatchCode",cityCode);
					$("#currentDispatch").attr("currentDispatchName",cityName);
		    		document.getElementById("currentDispatch").innerHTML=cityName;
				})
		},
    	//点击销量触发的事件
		_changeSaleOrder: function(){
			var _this = this;
			$("#priceOrder").attr("value","");
			$("#saleOrder").attr("value","ASE");
			_this._changeDataClick();
		 },
		//点击价格排序触发事件
		_changePriceOder: function(){
			var _this = this;
			$("#saleOrder").attr("value","");
			$("#priceOrder").attr("value","ASE");
			_this._changeDataClick();
		 }
    });
    
    module.exports = QueryProductPager
});

