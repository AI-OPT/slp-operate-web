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
	           <p><a href="#">标准品库管理</a></p>  
	           <p>></p>  
	           <p>添加编辑页面</p>
           </li>
        </ul>  
    </div>
    
   <!--标签结束-->
      <!--查询区域-->
     
  		
    <div class="form-wrapper"><!--白底内侧-->
    <div class="nav-tplist-wrapper"><!--白底内侧-->
    
       <div class="nav-form-title">添加</div>
        <div class="nav-form nav-form-border">
           	<ul>
                <li class="width-xlag">
                    <p class="word"><b style="color:#f00;">*</b>标准品名称</p>
                    <p><input id="standedProductName" name="standedProductName" type="text" class="int-text int-xlarge"></p>
                    <p>限45字以内</p>
                </li>   
            </ul>
            <ul>
            		<li class="width-xlag">
                    <p class="word"><b style="color:#f00;">*</b>标准品类型</p>
                    <p>
                    	<select id="productType" name="productType" class="select select-medium">
		                   	<option value="1">实物</option>
		                   	<option value="2">虚拟</option>
		                </select>
		            </p>
                    
                    
                    <p><input id="state" name="state" type="checkbox" class="checkbox-small">标识商品状态：未使用，已使用，已过期</p>
               </li>
            </ul>  
        </div>
        <div class="nav-form-title">标准品关键属性</div>
        <div class="nav-form nav-form-border">
           		<ul>
                <li>
                    <p class="word"><b style="color:#f00;">*</b>品牌</p>
                    <p><select class="select select-medium"><option>请选择</option></select></p>
                </li>
                <li>
                    <p class="word"><b style="color:#f00;">*</b>货号</p>
                    <p><input type="text" class="int-text int-small"></p>
                </li>  
            </ul>  
            <ul>
                <li class="width-xlag">
                    <p class="word"><b style="color:#f00;">*</b>性别</p>
                    <p><select class="select select-medium"><option>请选择</option></select></p>
                </li>
            </ul>   
        </div>
        <div class="nav-form-title">标准品销售属性</div>
        <div class="nav-form nav-form-border">
           		<ul>
                <li class="width-xlag">
                    <p class="word">颜色:</p>
                    <p><input type="checkbox" class="checkbox-small">红色</p>
                    <p><input type="checkbox" class="checkbox-small">黑色</p>
                    <p><input type="checkbox" class="checkbox-small">蓝色</p>
                    <p><input type="checkbox" class="checkbox-small">黄色</p>
                    <p><input type="checkbox" class="checkbox-small">灰色</p>
                    <p><input type="checkbox" class="checkbox-small">白色</p>
                    <p><input type="checkbox" class="checkbox-small">紫色</p>
                    <p><input type="checkbox" class="checkbox-small">绿色</p>
                    <p><input type="checkbox" class="checkbox-small">橘红色</p>
                </li>
            </ul>  
            <ul>
                <li class="width-xlag">
                    <p class="word">尺码:</p>
                    <p><input type="checkbox" class="checkbox-small">36</p>
                    <p><input type="checkbox" class="checkbox-small">37</p>
                    <p><input type="checkbox" class="checkbox-small">38</p>
                    <p><input type="checkbox" class="checkbox-small">39</p>
                    <p><input type="checkbox" class="checkbox-small">40</p>
                    <p><input type="checkbox" class="checkbox-small">41</p>
                    <p><input type="checkbox" class="checkbox-small">42</p>
                    <p><input type="checkbox" class="checkbox-small">43</p>
                    <p><input type="checkbox" class="checkbox-small">44</p>
                </li>
            </ul>   
        </div>
        <div class="nav-form-title">地址定位</div>
        <div class="nav-form nav-form-border">
           		<ul>
                <li class="width-xlag">
                    <p class="word">店铺/商铺名称</p>
                    <p><input type="text" class="int-text int-xlarge"></p>
                </li>
            </ul>  
        </div>
        <div class="nav-form-title">标准品状态</div>
        <div class="nav-form">
           		<ul>
                <li class="width-xlag">
                    <p class="word">状态</p>
                    <p>
	                    <select class="select select-medium">
		                    <option>请选择</option>
		                    <option value="1">可使用</option>
				            <option value="2">不可用</option>
	                    </select>
                    </p>
                </li>
            </ul>  
        </div>
        <div class="nav-form">
            <ul>
                <li class="width-xlag">
                <p class="word">&nbsp;</p>
                <p><input type="button" class="biu-btn btn-blue btn-large mr-10" value="保  存"></p>
                <p><input type="button" class="biu-btn btn-blue btn-large mr-10" value="返回"></p></li>
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
