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
    		//减少数量
            "click #delQtyBtn":"_delProductQty",
        },
    	//重写父类
    	setup: function () {
    		AddlistPager.superclass.setup.call(this);
    		this._loadProductCat();
    		this._loadPagination();
    	},
    	// 加载商品类目
    	_loadProductCat:function(){
    		if(count>1){
	    		for(var i=2;i<=count;i++){
	    			var innerHtml = '<p id="productCat"'+i+'>'
					                +'<select class="select-small">'
					                +'<c:forEach var="info" items="${prodInfoList}">'
					                    +'<option value="${info.productCatId}">${info.productCatName}</option>'
					                +'</c:forEach>'
					                +'</select>'
					                +'</p>';
	    			if(count==2){
	    				$("#productCat1").append(innerHtml);
	    			}else{
	    				$("#productCat"+i).append(innerHtml);
	    			}
	    		}
    		}
    	},
    	_loadPagination: function(){
    		var _this = this;
    		var productCatId = $("#productCat1 option:first").val();
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

