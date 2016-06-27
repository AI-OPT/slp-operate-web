define('app/jsp/product/addlist', function (require, exports, module) {
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
    var AddlistPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询未编辑商品
            "click #selectProductEdit":"_selectProductEdit",
        },
    	//重写父类
    	setup: function () {
    		AddlistPager.superclass.setup.call(this);
    		this._loadPagination();
    	},
    	// 改变商品类目
    	_selectChange:function(osel){
    		var prodCatId = osel.options[osel.selectedIndex].value;
    		var clickId = $(osel).parent().attr('id');
    		ajaxController.ajax({
				type: "post",
				processing: false,
				// message: "加载中，请等待...",
				url: _base+"/prodquery/getCat",
				data:{"prodCatId":prodCatId},
				success: function(data){
					if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#prodCatTemple");
	            	    var htmlOutput = template.render(data);
	            	    $("#"+clickId).append(htmlOutput);
	            	}else{
	            		var d = Dialog({
							content:"已是叶子类目:"+data.statusInfo,
							ok:function(){
								this.close();
							}
						});
						d.show();
	            	}
				}
			});
    	},
    	//查询未编辑商品-点击查询触发
    	_selectProductEdit:function(){
    		var productCatId = $("#productCat"+level).val();
    		var productType = $("#productType").val().trim();
    		var productId = $("#productId").val().trim();
    		var productName = $("#productName").val().trim();
    		$("#pagination-ul").runnerPagination({
	 			url: _base+"/prodquery/getProductList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data: {"productCatId":productCatId,"productType":productType,"productId":productId,"productName":productName},
	           	pageSize: AddlistPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#searchProductTemple");
	            	    var htmlOutput = template.render(data);
	            	    $("#searchProductData").html(htmlOutput);
	            	}else{
    					$("#searchProductData").html("没有搜索到相关信息");
	            	}
	            }
    		});
    	},
    	//加载分页信息-首次进入页面
    	_loadPagination: function(){
    		var _this = this;
    		var productCatId = $("#productCat"+count).find("option").val();
    		$("#pagination-ul").runnerPagination({
	 			url: _base+"/prodquery/getList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data: {"productCatId":productCatId},
	           	pageSize: 1,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#searchProductTemple");
	            	    var htmlOutput = template.render(data);
	            	    $("#searchProductData").html(htmlOutput);
	            	}else{
    					$("#searchProductData").html("没有搜索到相关信息");
	            	}
	            }
    		});
    	}
    	
    	
    });
    
    module.exports = AddlistPager
});

