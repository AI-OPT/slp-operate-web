<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>生成虚拟库存</title>
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
    	<%@ include file="/inc/public-msg.jsp" %>
    <!--公告位置结束-->   
    <!--标签-->
    <div class="right-tags">
        <ul>
           <li>
	           <p class="none">您现在的位置：</p>
	           <p><a href="#">库存管理</a> > </p>  
	           <p>生成虚拟库存</p>
           </li>
        </ul>  
    </div>
   <!--标签结束-->
      <!--查询区域-->
    <div class="form-wrapper"><!--白底内侧-->
    
       <div class="form-label">
        <div class="nav-form nav-form-padding">
	            <ul>
	                <li class="width-xlag">
	                    <p>所属类目：</p>
	                    <p><select class="select select-small"></select></p>
	                    <p><select class="select select-small"></select></p>
	                    <p><select class="select select-small"></select></p>
	                </li> 
	            </ul>  
      	  </div> 
       	 <ul>
                <li class="width-xlag">
                    <p class="word">标准品名称</p>
                    <p><input type="text" class="int-text int-medium"></p>
                    <p><input type="button" value="查询" class="biu-btn btn-blue btn-mini"</p>
                    <p class="sos"><a href="#">高级搜索<i class="icon-caret-down"></i></a></p>
                </li>
            </ul>
            <!--点击展开-->
            <div class="open" style="display:none;">
            <ul>
                <li>
                    <p class="word">标准品ID</p>
                    <p><input type="text" class="int-text int-medium"></p>
                </li>
                <li>
                    <p class="word">标准品状态</p>
                    <p><select class="select select-medium"></select></p>
                </li>  
            </ul> 
            <ul>
                <li>
                    <p class="word">标准品类型</p>
                    <p><select class="select select-medium"></select></p>
                </li>
                <li>
                    <p class="word">操作人</p>
                    <p><select class="select select-medium"></select></p>
                </li>  
            </ul> 
            <ul>
                <li>
                    <p class="word">操作时间</p>
                    <p><input type="text" class="int-text int-medium"><a href="#" class="ccc"><i class="icon-calendar"></i></a></p>
                    <p>~</p>
                    <p><input type="text" class="int-text int-medium"><a href="#" class="ccc"><i class="icon-calendar"></i></a></p>
                </li>
            </ul>
            </div>  
            <!--点击展开结束-->
        </div>
    
    </div>
   <!--查询区域结束-->
   <!--查询结果-->
   <div class="form-wrapper"><!--白底内侧-->
         <div class="nav-tplist-wrapper"><!--白底内侧-->
          <!--结果标题-->
             <div class="nav-tplist-title">
                  <ul>
                    <li>标准品列表</li>
                  </ul>
             </div>
         <!--结果表格-->
        <div class="table table-border table-bordered table-bg table-hover mt-10">
			<table width="100%" border="0">
              <tbody><tr class="bj">  
                <td>序号</td>                                                                                                      
                <td>标准品ID</td>
                <td>标准品名称</td>
                <td>所属类目</td>
                <td>类型</td>
                <td>操作日期</td>
                <td>操作人</td>
                <td>操作</td>
              </tr>
              <tr>
                <td>1</td>
                <td>3434111</td>
                <td>Apple iPhone 6s plus</td>
                <td>手机</td>
                <td>实物</td>
                <td>2015-1-23  10:02:34</td>
                <td>hesuan</td>
                <td><a href="#" class="blue">编辑</a></td>
              </tr>
             <tr>
                <td>1</td>
                <td>3434111</td>
                <td>Apple iPhone 6s plus</td>
                <td>手机</td>
                <td>实物</td>
                <td>2015-1-23  10:02:34</td>
                <td>hesuan</td>
                <td><a href="#" class="blue">编辑</a></td>
             </tr>
              <tr>
                <td>1</td>
                <td>3434111</td>
                <td>Apple iPhone 6s plus</td>
                <td>手机</td>
                <td>实物</td>
                <td>2015-1-23  10:02:34</td>
                <td>hesuan</td>
                <td><a href="#" class="blue">编辑</a></td>
              </tr>
              
			</tbody></table>
          </div> 
        <!--结果表格结束-->
        <!--分页-->
        <div class="paging-large">
        <ul>
            <li class="prev-up"><a href="#">&lt;上一页</a> </li>
            <li class="active"> <a href="#">1 </a> </li>
            <li> <a href="#">2 </a> </li>
            <li> <span>…</span> </li>
            <li> <a href="#">38</a> </li>
            <li> <a href="#">39</a> </li>
            <li> <a href="#">40</a> </li>
            <li> <a href="#">41</a> </li>
            <li> <a href="#">42</a> </li>
            <li> <span>…</span> </li>
            <li class="next-down"><a href="#">下一页&gt;</a> </li>
            <li>共100页</li>
   		     <li>
                <span>到</span>
                <span><input type="text" class="int-verysmall"></span>
                <span>页</span>
                <span class="btn-span"><a class="but-determine">确定</a></span>
            </li> 
         </ul>
	 </div>
         </div> 
    </div>
   <!--查询结果结束-->

    </div>
</div>	
<!-- footer -->
<div class="footer">版权所有 © SLP版权归运营家所有</div>
</body>
</html>
<script src="${_slpres }/scripts/frame.js"  type="text/javascript" ></script>
<script src="${_slpres }/scripts/metismenu.js"></script>
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
		(function () {
			seajs.use('app/jsp/storage/prodstorage', function (ProdStoragePager) {
				pager = new ProdStoragePager({element: document.body});
				pager.render();
			});
		})();
</script>
