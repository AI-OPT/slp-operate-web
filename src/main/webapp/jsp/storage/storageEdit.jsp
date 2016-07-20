<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>生成虚拟库存-编辑（无销售属性）</title>
<%@ include file="/inc/inc.jsp" %>
<link href="${_slpres }/styles/bootstrap.css"rel="stylesheet" type="text/css">
<link href="${_slpres }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<link href="${_slpres }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpres }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpres }/styles/modular.css" rel="stylesheet" type="text/css">

</head>

<body>
<!--添加库存分组 弹出框  小-->
<div class="eject-big">
<div class="eject-samll" id="eject-samll">
	<div class="eject-samll-title">
		<p>添加库存分组</p>
		<p class="img"><A href="#"></A></p>
	</div>
	<div class="medium-list-form">
			<ul>
				<li>
					<p>库存组名称</p>	
					<p><input type="text" class="int-text int-medium"></p>	
				</li>
				
			</ul>
	</div>	
	<div class="eject-samll-confirm mt-0">
		<ul>
			<li><input type="button"  class="slp-btn eject-small-btn" value="确认"><input type="button"  class="slp-btn eject-small-btn close-btn" value="取消"></li>		
		</ul>
	</div>
</div>	
<div class="eject-mask" id="eject-mask"></div>	
</div>
<!--/结束-->
<!--编辑库存分组 弹出框  小-->
<div class="eject-big">
<div class="eject-samll" id="eject-samll-1">
	<div class="eject-samll-title">
		<p>编辑名称</p>
		<p class="img"><A href="#"></A></p>
	</div>
	<div class="medium-list-form">
			<ul>
				<li>
					<p>库存组名称</p>	
					<p><input type="text" class="int-text int-medium"></p>	
				</li>
				
			</ul>
	</div>	
	<div class="eject-samll-confirm mt-0">
		<ul>
			<li><input type="button"  class="slp-btn eject-small-btn" value="确认"><input type="button"  class="slp-btn eject-small-btn close-btn" value="取消"></li>		
		</ul>
	</div>
</div>	
<div class="eject-mask" id="eject-mask"></div>	
</div>
<!--增加库存 弹出框  小-->
<div class="eject-big">
<div class="eject-samll" id="eject-samll-2">
	<div class="eject-samll-title">
		<p>增加库存</p>
		<p class="img"><A href="#"></A></p>
	</div>
	<div class="eject-form">
           		<ul>
	                <li class="width-xlag">
	                    <p class="word"><span>*</span>库存名称:</p>
	                    <p><input type="text" class="int-text int-medium"></p>
	                </li> 
	                 <li class="width-xlag">
	                    <p class="word"><span>*</span>虚拟库存量:</p>
	                    <p><input type="text" class="int-text int-medium"></p>
	                </li> 
	                <li class="width-xlag">
	                    <p class="word"><span>*</span>最低预警库存值:</p>
	                    <p><input type="text" class="int-text int-medium"></p>
	                </li> 
	                <li class="width-xlag">
	                    <p class="word">有效期:</p>
	                    <p><input type="text" class="int-text int-mini"><i class="icon-calendar"></i></p>
	                    <p><input type="text" class="int-text int-mini"><i class="icon-calendar"></i></p>
	                </li> 
           	 	</ul>
      	  </div>
	<div class="eject-samll-confirm mt-0">
		<ul>
			<li><input type="button"  class="slp-btn eject-small-btn mt-10" value="确认"><input type="button"  class="slp-btn eject-small-btn close-btn mt-10" value="取消"></li>		
		</ul>
	</div>
</div>	
<div class="eject-mask" id="eject-mask"></div>	
</div>
<!--/结束-->
<!--废弃 弹出框  小-->
<div class="eject-big">
<div class="eject-samll" id="eject-samll-3">
	<div class="eject-samll-title">
		<p>废弃组</p>
		<p class="img"><A href="#"></A></p>
	</div>
	<div class="eject-medium-complete">
					<p><img src="${_slpres }/images/eject-icon-prompt.png"></p>
					<p class="word">库存组废弃后不可再启用，确定废弃该库存组吗？</p>
				</div>
	<div class="eject-samll-confirm mt-0">
		<ul>
			<li><input type="button"  class="slp-btn eject-small-btn mt-10" value="确认"><input type="button"  class="slp-btn eject-small-btn close-btn mt-10" value="取消"></li>		
		</ul>
	</div>
</div>	
<div class="eject-mask" id="eject-mask"></div>	
</div>
<!--/结束-->

<!--右侧弹出框-->
<div class="msg-cnt">
 <div class="p">
      <a ng-click="$hide()" class="pull-right text-muted"><img src="${_slpres }/images/close.png"></a>
      审批待办事项
    </div>
 <div class="box-row">
      <div class="box-cell">
        <div class="box-inner">
          <div class="list-group no-radius no-borders">
            <a class="list-group-item p-h-md p-v-xs">
                <i class="icon-circle text-success text-xs m-r-xs"></i>
                <span>审批待办事项1</span>
            </a>
            <a class="list-group-item p-h-md p-v-xs">
                <i class="icon-circle text-success text-xs m-r-xs"></i>
                <span> 审批待办事项5条</span>
            </a>
            <a class="list-group-item p-h-md p-v-xs">
                <i class="icon-circle text-warning text-xs m-r-xs"></i>
                <span>审批待办事项2</span>
            </a>
            <a class="list-group-item p-h-md p-v-xs">
                <i class="icon-circle text-muted-lt text-xs m-r-xs"></i>
                <span>审批待办事项5条</span>
            </a>
            <a class="list-group-item p-h-md p-v-xs">
                <i class="icon-circle text-muted-lt text-xs m-r-xs"></i>
                <span>审批待办事项3个审批</span>
            </a>
            <a class="list-group-item p-h-md p-v-xs">
                <i class="icon-circle text-muted-lt text-xs m-r-xs"></i>
                <span>审批待办事项</span>
            </a>
            <a class="list-group-item p-h-md p-v-xs">
                <i class="icon-circle text-muted-lt text-xs m-r-xs"></i>
                <span>审批待办事项</span>
            </a>
            <a class="list-group-item p-h-md p-v-xs">
                <i class="icon-circle text-muted-lt text-xs m-r-xs"></i>
                <span>审批待办事项5条</span>
            </a>
            <a class="list-group-item p-h-md p-v-xs">
                <i class="icon-circle text-muted-lt text-xs m-r-xs"></i>
                <span>审批待办事项</span>
            </a>
            <a class="list-group-item p-h-md p-v-xs">
                <i class="icon-circle text-muted-lt text-xs m-r-xs"></i>
                <span>审批待办事项</span>
            </a>
          </div>
        </div>
      </div>
    </div>
</div>
<!--右侧弹出框结束-->
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
	           <p>虚拟库存列表 > </p>  
	           <p>查看详情</p>
           </li>
        </ul>  
    </div>
    
   <!--标签结束-->
    <div class="form-wrapper"><!--白底内侧-->
    <div class="nav-tplist-wrapper"><!--白底内侧-->
    	      <div class="nav-form-title">所属类目：
					<c:forEach var="catInfo" items="${catLinkList}"
					 varStatus="stat">${catInfo.productCatName}<c:if test="${!stat.last}">&gt;</c:if></c:forEach>
			  </div>
        <div class="form-label nav-form-border">
           	<ul>
                <li class="width-xlag">
                    <p class="word">商品名称:</p>
                    <p>${normProdInfo.productName}</p>
                </li>
                <li class="width-xlag">
                    <p class="word">商品类型:</p>
                    <p>${prodType}</p>
                </li>  
            </ul>  
        </div>
       
        <div class="nav-form-title">商品关键属性</div>
        <div class="form-label nav-form-border">
	        <c:forEach var="aav" items="${attrAndVal}">
				<ul>
					<li>
						<p class="word">${aav.key.attrName}</p>
						<c:choose>
							<%--多选--%>
							<c:when test="${aav.key.attrType == '2'}">
								<div class="cit-width">
									<c:forEach var="attrVal" items="${aav.value}">
										<p>${attrVal.attrVal}</p></c:forEach>
								</div>
							</c:when>
							<c:otherwise>
								<p>${aav.value.get(0).attrVal}</p>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</c:forEach>
        </div>

    	<div class="nav-tplist-title">
        <ul>
           <li>库存设置</li>
        </ul>
    </div>
    <div class="title-right">
         <p class="plus" id＝"samll-eject"><a href="#"><i class="icon-plus"></i></a></p>
         <p class="plus-word" id="small-eject1"><a href="#">添加库存组</a></p>
    </div>
    <div class="table table-border table-bordered table-bg table-hover mt-10">
          <table width="100%" border="0">
              <tbody>
              	<tr>
                		<td colspan="9">
	                		<div class="setup-sku mg-0">
				         	<ul>
				         		<li>
				         			<p>库存组名称:个人库存北京地区</p>
				         			<p id="small-eject2"><input type="button"class="biu-btn btn-blue stock-btn" value="编辑名称 "></p>
				         			<p>总库存量:0</p>
				         			<p><input type="button"class="biu-btn btn-blue stock-btn" value="增加优先级 "></p>
				         			<p><input type="button"class="biu-btn btn-blue stock-btn" value="启动 "></p>
				         			<p id="small-eject4"><input type="button"class="biu-btn btn-blue stock-btn" value="废弃 "></p>
				         			<p>状态:停用</p>
				         		</li>
				         	</ul>
	         			</div>
                		</td>
              	</tr> 
              	<tr>
                		<td colspan="9">
                			<div class="setup-sku mg-0">
				         	<ul>
				         		<li>
				         			<p>库存组名称:个人库存北京地区</p>
				         			<p><a href="#"><img src="${_slpres }/images/down.png" /></a></p>
				         			<p><a href="#"><img src="${_slpres }/images/up.png" /></a></p>
				         			<p><input type="button"class="biu-btn btn-blue stock-btn" id="small-eject3" value="增加库存"></p>
				         			<p>状态:停用</p>
				         			<p>
				         				<span><input type="checkbox" class="checkbox-medium" /></span>
				         				<span>促销活动</span>
				         			</p>
				         			<p class="eject-int"><input type="input" class="int-text int-mini"><a href="#"><i class="icon-calendar"></i></a></p>
				         			<p class="eject-int">~</p>
				         			<p class="eject-int"><input type="input" class="int-text int-mini"><a href="#"><i class="icon-calendar"></i></a></p>
				         			<p class="word">(如果没有结束时间可不填)</p>
				         		</li>
				         	</ul>
	         			</div>
                		</td>
              	</tr> 
              	<tr class="bj">  
	                <td>序号</td>                                                                                                      
	                <td>库存ID</td>
	                <td>库存名称</td>
	                <td><span>*</span>虚拟库存量</td>
	                <td>生效时间</td>
	                <td>失效时间</td>
	                <td><span>*</span>最低预警库存量</td>
	                <td>状态</td>
	                <td>操作</td> 
              </tr> 
              <tr>
	                <td>1</td>
	                <td>345454545</td>
	                <td>个人库存北京地区</td>
	                <td>100000</td>
	                <td></td>
	                <td></td>
	                <td>100</td>
	                <td>停用</td>
	                <td><a href="#"  class="blue">编辑</a><a href="#"  class="blue">启用</a><a href="#"  class="blue">废弃</a><a href="#"  class="blue">管理预警接收人</a></td>
              </tr> 
              <tr>
	                <td>3</td>
	                <td>345454545</td>
	                <td>个人库存北京地区</td>
	                <td>100000</td>
	                <td></td>
	                <td></td>
	                <td>100</td>
	                <td>废弃</td>
	                <td><a href="#"  class="blue">查看</a></td>
              </tr> 
          </tbody>
          </table>
          </div>
   		<div class="pst-bttton">
	        <input type="button" class="biu-btn btn-blue btn-large mr-10" value="返  回">
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
			<%-- 展示日历 --%>
            $('#selectDiv').delegate('.setup-sku mg-0','click',function(){
                var calInput = $(this).parent().prev();
                var timeId = calInput.attr('id');
                console.log("click calendar "+timeId);
                WdatePicker({el:timeId,readOnly:true});
            });
			seajs.use('app/jsp/storage/storageEdit', function (StorageEditPager) {
				pager = new StorageEditPager({element: document.body});
				pager.render();
			});
		})();
</script>