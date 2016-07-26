<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>运营管理</title>
	<%@ include file="/inc/inc.jsp" %>
	<link href="${_slpres }/styles/bootstrap.css"rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/font-awesome.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/frame.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/global.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/modular.css" rel="stylesheet" type="text/css">
	
</head>

<body>

	<!--顶部菜单-->
	<%@ include file="/inc/top-menu.jsp" %>
	<!--顶部菜单结束-->
	<!-- 左侧菜单 -->
	<%@ include file="/inc/left-menu.jsp" %>
	<!-- 左侧菜单结束 -->


<div class="wrapper"><!--外围框架-->
<!--右侧框架-->
 <div class="wrapper-right">
    <!--公告位置-->
        <div class="right-topnav">
            <p class="gongg"><A href="#">［公告］:</A></p>
            <div  id="elem">
            <ul id="elem1">
                <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
                <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
                <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
                <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
            </ul>
            <ul id="elem2">
            </ul>
            </div>
             <p class="dclose"><A href="#"><i class="icon-remove"></i></A></p>
        </div>
    <!--公告位置结束-->   
    <!--标签-->
    <div class="right-tags">
        <ul>
           <li>
	           <p class="none">您现在的位置：</p>
	           <p><a href="#">标准商品库管理</a></p>  
	           <p>></p>  
	           <p>添加商品</p>
           </li>
        </ul>  
    </div>
    
   <!--标签结束-->
      <!--查询区域-->
     
  		
    <div class="form-wrapper"><!--白底内侧-->
    <div class="nav-tplist-wrapper"><!--白底内侧-->
    
       <div class="nav-form-title">选择类目</div>
           	<div class="add-commodity-title">
           		<ul>
           			<li>您当前选择的是：<a href="#">登山野营/旅行装备</a>><a href="#">户外露营/野炊装备</a>><a href="#">帐篷/天幕/帐篷配件</a></li>
           			<li class="color-word">（标准品一旦生成，类目信息不可更改，请谨慎选择类目信息）</li>
           		</ul>
           	</div>
           	







           	
           	
           	
              <div class="form-label">
	            <ul id="data1ProdCat">
                    <li class="width-xlag">
                        <p class="word">所属类目</p>
                        <c:forEach var="map" items="${catInfoMap}" varStatus="status">
							<p id="productCat${status.index}">
								<select class="select select-small"onChange="pager._selectChange(this);">
									<c:forEach var="info" items="${map.value}">
									<option value="${info.productCatId}">${info.productCatName}</option>   	
									</c:forEach>
								</select>
							</p>
						</c:forEach> 
                        <script id="prodCatTemple" type="text/template">
                                	 <p id="productCat{{:level}}">
										<select class="select select-small" onChange="pager._selectChange(this);">
											{{for prodCatList}}
                                   			 	<option value="{{:productCatId}}">{{:productCatName}}</option>
											{{/for}}
                               			</select>
									</p>
						</script>
                    </li>
                </ul>


       
        <div class="nav-form">
            <ul>
                <li class="width-xlag">
             <%--    <p><input href="${_base}/normprodedit/addinfo" type="button" class="blling-btn add-btn" value="下一步，填写详细信息"></p> --%>
             
                <p><a class="blling-btn add-btn"  href="${_base}/normprodedit/addinfo">下一步，填写详细信息</a></p>
            </ul>   
        </div>

    </div>
   <!--查询区域结束-->


    </div>
    </div>
</div>	
<!-- footer -->
<div class="footer">版权所有 © SLP版权归运营家所有</div>
</body>
</html>
<script src="../scripts/jquery-1.11.1.min.js"></script>
<script src="../scripts/frame.js"  type="text/javascript" ></script>
<script src="../scripts/metismenu.js"></script>
 <script type="text/javascript"> 
window.onload = function(){	
	var timer;
	var elem = document.getElementById('elem');
	var elem1 = document.getElementById('elem1');
	var elem2 = document.getElementById('elem2');
	elem2.innerHTML = elem1.innerHTML;
	timer = setInterval(Scroll,40);
	function Scroll(){
		if(elem.scrollTop>=elem1.offsetHeight){
			elem.scrollTop -= elem1.offsetHeight;
		}else{
			elem.scrollTop += 1;
		}
	}	
	elem.onmouseover = function(){
		clearInterval(timer);
	}	
	elem.onmouseout = function(){
		timer = setInterval(Scroll,40);
	}
}
</script>
<script type="text/javascript">
		var pager;
		var count = '${count}';
		var prodInfoList = '${prodInfoList}';
		var productEditInfo = '${productEditInfo}';
		(function () {
            seajs.use('app/jsp/normproduct/add', function(
    				normproductlistPager) {
    			pager = new normproductlistPager({
    				element : document.body
    			});
    			pager.render();
    		});
		})();
</script>
