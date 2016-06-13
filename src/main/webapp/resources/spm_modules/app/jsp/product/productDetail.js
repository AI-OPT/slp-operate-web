define('app/jsp/product/productDetail', function (require, exports, module) {
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
    var ProductDeatilPager = Widget.extend({
    	
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
            //修改数量
            "change #productQty":"_modifyProductQty",
            //增加数量
            "click #addQtyBtn":"_addProductQty",
            //查看更多配置信息
            "click #seeMoreConfigBtn":"_seeMoreConfig",
            //立即购买
            "click #buyBtn":"_buyProduct",
            //加入购物车
            "click #joinShopCart":"_joinShopCartClick"
        },
    	//重写父类
    	setup: function () {
    		ProductDeatilPager.superclass.setup.call(this);
    		this._renderProducSKUTemple();
    		this._renderImageBigTemple();
    		this._renderImageSmallTemple();
    		
    		this._controlActiveDate();
    		this._controlBtn();
    		this._getHotProduct();
    		this._getProductConfigParameter();
    	},
    	//渲染大图
    	_renderImageBigTemple:function(){
    		var template = $.templates("#bigImageTemple");
			var htmlOutput = template.render(imageArrayList);
			$("#bigpicarea").html(htmlOutput);
    	},
    	//渲染小图
    	_renderImageSmallTemple:function(){
    		var template = $.templates("#smallImageTemple");
			var htmlOutput = template.render(imageArrayList);
			$("#smallImageData").html(htmlOutput);
    	},
    	//渲染商品基本信息
    	_renderProducSKUTemple:function(){
    		$("#producSKUData").html("");
    		var template = $.templates("#producSKUTemple");
			var htmlOutput = template.render(producSKU);
			$("#producSKUData").html(htmlOutput);
    	},
    	//控制按钮
    	_controlBtn:function(){
    		if(producSKU.state == 5){
    			var usableNum = Number($("#usableNum").text());
    			if(usableNum<=0){
    				$("#invalidBtn").val("无货");
    				this._displayHideUI("buyBtnId");
        			this._displayHideUI("addCarBtnId");
        			this._displayShowUI("invalidBtnId");
    			}else{
	    			this._displayShowUI("buyBtnId");
	    			this._displayShowUI("addCarBtnId");
	    			this._displayHideUI("invalidBtnId");
    			}
    		}else{
    			$("#invalidBtn").val("已下架");
    			this._displayHideUI("buyBtnId");
    			this._displayHideUI("addCarBtnId");
    			this._displayShowUI("invalidBtnId");
    		}
    	},
    	//控制有效期
    	_controlActiveDate:function(){
    		if(activeDateValue != null && activeDateValue != undefined && activeDateValue != ""){
    			this._displayShowUI("activeDateDiv");
    			$("#activeDate").text(activeDateValue);
    		}else{
    			this._displayHideUI("activeDateDiv");
    			$("#activeDate").text("");
    		}
    	},
    	//隐藏区域
    	_displayHideUI:function(id)  
    	{  
    	    var ui =document.getElementById(id);  
    	    ui.style.display="none";  
    	},
    	//显示区域
    	_displayShowUI:function(id)  
    	{  
    	    var ui =document.getElementById(id);  
    	    ui.style.display="";//display为空的话会好使，为block会使后边的空间换行  
    	},
    	//减少数量
        _delProductQty:function(){
        	var qty = Number($("#productQty").val());
        	if(qty>1){
        		qty = qty - 1;
        	}
        	$("#productQty").val(qty);
        },
        //修改数量
        _modifyProductQty:function(){
        	var qty = Number($("#productQty").val());
        	var usableNum = Number($("#usableNum").text());
        	if(!this._isPositiveNum(qty)){
        		$("#productQty").val(1);
        	}else if(usableNum>99 && qty>99){
        		$("#productQty").val(99);
        	}else if(qty>usableNum){
        		$("#productQty").val(usableNum);
        	}else{
        		$("#productQty").val(qty);
        	}
        },
        //增加数量
        _addProductQty:function(){
        	var qty = Number($("#productQty").val());
        	var usableNum = Number($("#usableNum").text());
        	if(qty<usableNum && qty<99){
        		qty = qty + 1;
        	}
        	$("#productQty").val(qty);
        },
        //是否为正整数 
        _isPositiveNum:function(num){
        	var re = /^[1-9][0-9]*$/ ; 
        	return re.test(num) 
        },
        //点击修改属性
        _changeAttr:function(attrValueElmt,attrvalueDefId,attrId){
        	var attrValueClass = attrValueElmt.getElementsByTagName("a")[0].className;
        	if(attrValueClass !="current"){
        		//设置属性变亮
        		$("#attrValue_"+attrId+" a").removeClass("current");
        		attrValueElmt.getElementsByTagName("a")[0].className = "current";
        		attrValueElmt.getElementsByTagName("a")[0].name=attrvalueDefId;
        		//设置当前属性值
        		//$("#attrValue_"+attrId).val(attrvalueDefId);
        		var skuAttrs = this._getProductAttrs();
        		window.location.href=_base+"/product/detail?skuAttrs="+skuAttrs;
        	}
        },
        //获取属性串
        _getProductAttrs:function(){
        	var attrMapArray = document.getElementById("productAttrDiv").children;
        	var skuAttrs = "";
        	for(var i=0;i<attrMapArray.length;i++){
        		var atteArray = attrMapArray[i].children;
        		var attrName = atteArray[0].value;
        		var attrValue = "";
        		var attrValues = atteArray[1].children;
        		for(var j=0;j<attrValues.length;j++){
        			if(attrValues[j].getElementsByTagName("a")[0].className == "current"){
        				attrValue = attrValues[j].getElementsByTagName("a")[0].name;
        			}
        		}
        		if(skuAttrs == ""){
        			skuAttrs = attrName+":"+attrValue;
        		}else{
        			skuAttrs = skuAttrs+";"+attrName+":"+attrValue;
        		}
        	}
        	return skuAttrs;
        },
        //查看更多配置属性
        _seeMoreConfig:function(){
        	$("#productInfoTab").removeClass("current");
        	$("#productConfigTab").attr("class","current");
        	this._displayHideUI("date1");
        	this._displayShowUI("date2");
        },
        _getHotProduct:function(){
        	$("#hotProductData").html("");
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: false,
						//message: "查询中，请等待...",
						url: _base+"/search/getHotProduct",
						data:{areaCode:"81"},
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
      	_getProductConfigParameter:function(){
      		var _this = this;
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: false,
						//message: "查询中，请等待...",
						url: _base+"/product/getProductConfigParameter",
						data:{"skuId":skuId,"skuAttrs":skuAttrs},
						success: function(data){
							if(data.data){
								_this._displayShowUI("seeMoreConfigBtn");
								_this._displayHideUI("date2");
								var template = $.templates("#configParameterTemple");
								var htmlOut = template.render(data.data);
								$("#configParameterData").html(htmlOut);
								
								var template2 = $.templates("#configBriefParameterTemple");
								var htmlOut2 = template2.render(data.data);
								$("#configBriefParameterData").html(htmlOut2);
							}else{
								_this._displayHideUI("seeMoreConfigBtn");
								_this._displayHideUI("date2");
							}
						}
					}
      		);
      	},
      	//立即购买
      	_buyProduct:function(){
    		var _this=this;
    		var data = this._getBuyProductData();
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				url: _base+"/order/orderCommit",
				data:data,
				success: function(data){
					var key=data.data;
					window.location.href = _base
					+ "/order/toOrderPay?orderKey="+key;

				}
			});
    	},
    	//获得商品购买信息
    	_getBuyProductData:function(){
    		return {
    			orderType:"100000",//暂时传运营商的县官信息
				skuId:skuId,
				buySum:$("#productQty").val(),
    		};
    	},
      	//加入购物车
    	_joinShopCartClick:function(){
//			var skuId = $("#skuId").val();
			var buyNum = Number($("#productQty").val());
			ajaxController.ajax({
					type: "post",
					dataType: "json",
					processing: false,
					//message: "添加中，请等待...",
					url: _base+"/shopcart/addProd",
					data:{"skuId":skuId,"buyNum":buyNum},
					success: function(data){
						if(data.statusCode == "1"){
							var prodNum = data.data.prodNum;
							var prodTotal = data.data.prodTotal;
							var d = Dialog({
								content:"添加成功,本商品数量:"+prodNum+",商品总数量:"+prodTotal,
								ok:function(){
									this.close();
								}
							});
							d.show();
						}else{
							var d = Dialog({
								content:"添加失败",
								ok:function(){
									this.close();
								}
							});
							d.show();
						}
					}
				}
			);
    	},
    });
    
    module.exports = ProductDeatilPager
});

