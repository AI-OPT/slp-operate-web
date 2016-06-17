<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>商品编辑</title>
	<%@ include file="/inc/inc.jsp" %>
	<link href="${_slpres }/styles/font-awesome.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/global.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/frame.css" rel="stylesheet" type="text/css">
	<link href="${_slpres }/styles/modular.css" rel="stylesheet" type="text/css">
</head>

<body>
<!--顶部菜单-->
<%@ include file="/inc/top-menu.jsp" %>
<!--顶部菜单结束-->
<!-- 左侧菜单 -->
<%@ include file="/inc/left-menu.jsp" %>
<!-- 左侧菜单结束 -->
<!--弹出删除弹出框  中-->
<div class="eject-big">
		<div class="eject-medium">
			<div class="eject-medium-title">
				<p>上传图片</p>
				<p class="img"><A href="#"></A></p>
			</div>
			<div class="eject-medium-list">
				<div class="account-title eject-martop ejetct-border"><p>本地上传</p></div>
				<div class="default-text">
					<p>上传至:</p>
					<p class="int-zk"><input type="text" class="int-medium int-deault" /><i class="icon-angle-down"></i></p>
				<div class="mouse-open" style="display: none;;">
					<ul>
						<li>默认文件夹</li>
						<li><a href="#">流量商品图片</a></li>
						<li><a href="#">话费商品图片</a></li>
						<li><a href="#">装修图片</a></li>
						<li class="newly-build-onclick" style="display:none;"><input type="text" class="int-small int-deault" /><a href="#">创建</a></li>
						<li class="newly-build"><a href="#"><i class="icon-plus"></i>新建文件夹</a></li>
						
					</ul>
				</div>
				</div>
				<div class="medium-list-form medium-list-form-center">
					<ul>
						<li class="img"><img src="../images/sp-04.png" /></li>
						<li><input type="button"  class="blling-btn qu-btn" value="上传图片"></li>
					</ul>
				</div>	
				<div class="medium-list-word">
					<ul>
				    <li>提示：</li>		
					<li>一次可上传最多6张图片，JPG/PNG/GIF格式，每张图片大小不超过3M；</li>	
					<li>建议上传详情图片宽度750px或以上；</li>
					</ul>
				</div>
			</div>	
		</div>	
		<div class="eject-mask"></div>	
</div>
<!--弹出删除弹出框  中结束-->		
<!--弹出删除弹出框 大-->
<div class="eject-big">
		<div class="eject-large eject-large2">		
			<div class="eject-large-title">
				<p>选择省市</p>
				<p class="img"><A href="#"></A></p>
			</div>

			<div class="eject-large-list">
			<div class="account-title eject-martop"><p><a href="#" class="wnc">完成选择</a></p></div>
		    <div class="user-list-title-list">
				<c:set value="0" var="areaNum"></c:set>
		     	<ul>
					<c:forEach var="areaInfo" items="${otherSet.areaInfos}">
						<li>
						<p><input type="checkbox" class="checkbox-medium" <c:if test="${areaInfo.own}">checked</c:if>></p>
						<p>${areaInfo.areaName}</p>
						<c:if test="${areaInfo.own}"><c:set var="areaNum" value="${areaNum+1}"></c:set></c:if>
						</li>
					</c:forEach>

		     	</ul>	
		    </div> 	
		     
			</div>	
		
		</div>	
		<div class="eject-mask"></div>	
</div>
<!--弹出删除弹出框 大结束-->
<!--弹出删除弹出框 大-->
<div class="eject-big">
		<div class="eject-large" >		
			<div class="eject-large-title">
				<p>选择代理商用户</p>
				<p class="img"><A href="#"></A></p>
			</div>

			<div class="eject-large-list">
				
				<div class="account-title eject-martop"><p>已选中<b>10个</b><a href="#" class="wnc">完成选择</a></p></div>
				<div class="eject-large-contacts">
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>	
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>	
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>	
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>	
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>	
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>	
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>	
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>	
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>	
					<p>阿里巴巴集团<a href="#"><i class="icon-remove-sign"></i></a></p>	
				</div>
			
		    <div class="search-firm">
		    		<p>搜索企业用户</p>
		    		<p><input type="input" class="int-xlarge" /></p>
		    		<p><input type="button" value="查询" class="blling-btn blue-btn"</p>
		    </div>
		    <div class="user-list-title-list">
		     	<ul>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>奥迪集团</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>阿里巴巴集团(alibabacompany)</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>奥德赛网络科技有限公司(德赛奥账户)</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>奥迪集团</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>阿里巴巴集团(alibabacompany)</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>奥德赛网络科技有限公司(德赛奥账户)</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>奥迪集团</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>阿里巴巴集团(alibabacompany)</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>奥德赛网络科技有限公司(德赛奥账户)</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>奥迪集团</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>阿里巴巴集团(alibabacompany)</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>企业注册名称（注册账户用户名）</p>
		     		</li>
		     		<li>
		     			<p><input type="checkbox" class="checkbox-medium"/></p>
		     			<p>奥德赛网络科技有限公司(德赛奥账户)</p>
		     		</li>
		     		
		     	</ul>	
		    </div> 	
		     
			</div>	
		 <!--分页-->
	    <div class="paging-large">
	            <ul>
	                <li>共100页</li>
	                <li class="prev-up"><a href="#">&lt;上一页</a> </li>
	                <li class="active"> <a href="#">1 </a> </li>
	                <li> <a href="#">2 </a> </li>
	                <li> <a href="#">3</a> </li>
	                <li><span>....</span></li>
	                <li> <a href="#">6</a> </li>
	                <li> <a href="#">7</a> </li>
	                <li class="next-down"><a href="#">下一页&gt;</a> </li>
	                 <li>
	                    <span>到</span>
	                    <span><input type="text" class="int-verysmall"></span>
	                    <span>页</span>
	                    <span class="btn-span"><a class="but-determine">确定</a></span>
	                </li>
	             </ul>
	      </div>     
		
		</div>	
		<div class="eject-mask"></div>	
</div>
<!--弹出删除弹出框 大结束-->


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
					<p><a href="#">商城商品管理</a>></p>
					<p>待编辑商品</p>
				</li>
			</ul>
		</div>
		<!--标签结束-->

		<div class="form-wrapper"><!--白底内侧-->
			<div class="nav-tplist-wrapper"><!--白底内侧-->
				<div class="nav-tplist-title nav-tplist-title-border  nav-tplist-title-edit">
					<ul>
						<li><span>*</span>标注为必填项</li>
					</ul>
				</div>
				<div class="nav-form-title">商品基础信息</div> <!--标题-->
				<div class="nav-form nav-form-border"><!--查询条件-->
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>类目信息</p>
							<p><c:forEach var="catInfo"
										  items="${catLinkList}">${catInfo.productCatName}&gt;</c:forEach></p>
						</li>
					</ul>
					<ul>
						<li>
							<p class="word"><b class="red">*</b>商品类型</p>
							<p>${prodType}</p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>商品名称</p>
							<p><input type="text" name="prodName" class="int-xlarge" value="${productInfo.prodName}">
							</p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word">商品卖点</p>
							<p><textarea class="textarea-xlarge">${productInfo.productSellPoint}</textarea></p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>商品有效期</p>
							<p><input type="text" class="int-small" value="${productInfo.activeCycle}"></p>
							<p><select class="select-small">
								<c:forEach var="unit" items="${prodUnits}">
									<option value="${unit.columnValue}">${unit.columnDesc}</option>
								</c:forEach>
							</select></p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>是否快充商品</p>
							<p><input type="radio" name="rechargeType" class="checkbox-small"
									  <c:if test="${productInfo.rechargeType == 'D'}">checked</c:if>>是</p>
							<p><input type="radio" name="rechargeType" class="checkbox-small"
									  <c:if test="${productInfo.rechargeType == 'C'}">checked</c:if>>否</p>
							<p><img src="${_slpres}/images/icon-a.png"/>请完善此必填项信息</p>
						</li>
					</ul>
				</div>
				<div class="nav-form-title">商品关键属性</div> <!--标题-->
				<div class="nav-form nav-form-border"><!--关键属性显示-->
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
				<div class="nav-form-title">商品非关键属性</div> <!--标题-->
				<div class="nav-form nav-form-border"><!--查询条件-->
					<c:forEach var="attr" items="${noKeyAttr}">
						<ul>
							<li class="width-xlag">
						<p class="word">${attr.key.attrName}</p>
						<c:choose>
							<%-- 下拉选择 --%>
							<c:when test="${attr.key.valueWay == '1'}">
								<select class="select-medium">
									<c:forEach var="valInfo" items="${attr.value}">
										<option value="${valInfo.attrValId}"
												<c:if test="${valInfo.productAttrValId}">selected</c:if>>${valInfo.attrVal}</option>
									</c:forEach>
								</select>
							</c:when>
							<%--多选--%>
							<c:when test="${attr.key.valueWay == '2'}">
								<div class="width-xlag">
									<c:forEach var="valInfo" items="${attr.value}">
										<p><input type="checkbox" class="checkbox-small"
												  <c:if test="${valInfo.productAttrValId}">checked</c:if> >${valInfo.attrVal}</p>
									</c:forEach>
								</div>
							</c:when>
							<%--单行输入--%>
							<c:when test="${attr.key.valueWay == '3'}">
								<c:set var="valInfo" value="${attr.value.get(0)}"></c:set>
								<p><input type="text" class="int-xlarge" <c:if test="${valInfo!=null}">value="${valInfo.attrVal}"</c:if> ></p>
							</c:when>
							<%--多行输入--%>
							<c:when test="${attr.key.valueWay == '4'}">
								<c:set var="valInfo" value="${attr.value.get(0)}"></c:set>
								<p><textarea class="textarea-xlarge"><c:if test="${valInfo!=null}">${valInfo.attrVal}</c:if></textarea></p>
							</c:when>
						</c:choose>
							</li>
						</ul>
					</c:forEach>

				</div>
				<div class="nav-form-title">其他设置</div> <!--标题-->
				<div class="nav-form nav-form-border"><!--查询条件-->
					<ul>
						<li class="width-xlag">
							<p class="word">话费/流量适用号段</p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>运营商</p>
							<c:forEach var="orgId" items="${orgIds}">
								<p><input type="radio" name="basicOrgId"
										  <c:if test="${productInfo.basicOrgId == orgId.columnValue}">checked</c:if>
										  class="checkbox-small" value="${orgId.columnValue}">${orgId.columnDesc}</p>
							</c:forEach>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word">选择商品目标受众类型</p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>个人用户</p>
							<p><input type="radio" class="checkbox-small">全部可见</p>
							<p><input type="radio" class="checkbox-small">全部不可见</p>
							<p><img src="${_slpres}/images/icon-a.png">请完善此必填项信息</p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>企业用户</p>
							<p><input type="radio" class="checkbox-small">全部企业可见</p>
							<p><input type="radio" class="checkbox-small">部分企业可见</p>
							<p><input type="radio" class="checkbox-small">全部企业不可见</p>
							<p><img src="${_slpres}/images/icon-a.png">请完善此必填项信息</p>
							<div class="cit-width cit-width-list2">
								<p class="width-xlag">已选中代理商20个<a href="#" class="modify">修改</a></p>
								<p>百度在线 （百度在线企业账户）</p>
								<p>百事（可乐百事）、</p>
								<p>宝马 （BMW-accuont)、</p>
								<p> C开头公司名称1（注册用户名）、</p>
								<p>C开头公司2 （注册用户名）、</p>
								<p>C开头公司3很长的公司注册名字换行（注册用户名）、</p>
								<p>D开头公司1（注册用户名）、</p>
								<p><a href="javascript:" class="zk">显示更多<i class="icon-angle-down"></i></a></p>
							</div>
							<div class="cit-width open" style="display:none;">
								<p>百度在线 （百度在线企业账户）</p>
								<p>百事（可乐百事）、</p>
								<p>宝马 （BMW-accuont)、</p>
								<p> C开头公司名称1（注册用户名）、</p>
								<p>C开头公司2 （注册用户名）、</p>
								<p>C开头公司3很长的公司注册名字换行（注册用户名）、</p>
								<p>D开头公司1（注册用户名）、</p>
							</div>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>代理商用户</p>
							<p><input type="radio" class="checkbox-small">全部代理可见</p>
							<p><input type="radio" class="checkbox-small">部分代理可见</p>
							<p><input type="radio" class="checkbox-small">全部代理不可见</p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word">亚信平台代销</p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>是否允许</p>
							<p><input type="radio" class="checkbox-small" name="isReplaceSell"
									  <c:if test="${productInfo.isReplaceSell == 'Y'}">checked</c:if>>允许亚信代销</p>
							<p><input type="radio" class="checkbox-small" name="isReplaceSell"
									  <c:if test="${productInfo.isReplaceSell == 'N'}">checked</c:if>>不允许亚信代销</p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word">商品目标地域</p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>选择商品目标地域</p>
							<p><input type="radio" name="isSaleNationwide" class="checkbox-small radioc "
									  <c:if test="${productInfo.isSaleNationwide == 'Y'}">checked</c:if> >全国</p>
							<p><input type="radio" name="isSaleNationwide"class="checkbox-small radiod city"
									  <c:if test="${productInfo.isSaleNationwide == 'N'}">checked</c:if> >部分</p>
							<div id="check3"></div>
							<div id="check4" style="display:none;">
								<div class="cit-width cit-width-list2">
									<p class="width-xlag">已选中省份${areaNum}个<a href="#" class="city">修改</a></p>
									<c:forEach var="areaInfo" items="${otherSet.areaInfos}">
										<c:if test="${areaInfo.own}">
										<p>${areaInfo.areaName}、</p>
										</c:if>
									</c:forEach>
								</div>
							</div>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word">商品上架时间</p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>选择商品上架时间</p>
							<p><input type="radio" class="checkbox-small">立即上架</p>
							<p><input type="radio" class="checkbox-small">放入仓库</p>
							<p><input type="radio" class="checkbox-small">定时上架</p>
							<p><input type="text" class="int-small"><a href="#" class="ccc"><i
									class="icon-calendar"></i></a></p>
						</li>
					</ul>
				</div>
				<div class="nav-form-title">商品主图</div> <!--标题-->
				<div class="nav-form nav-form-border"><!--查询条件-->
					<ul>
						<li class="width-xlag">
							提示：请上传商品主体正面照片jpg/png格式，不小于700x700px的方形图片，单张不能超过3M，最多6张。
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>商品主图</p>
							<div class="width-img">
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
							</div>
							<p class="upload"><input type="button" class="blling-btn file-btn" value="上传图片"/>
								<!--<input type="file" class="file">--></p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>红色</p>
							<div class="width-img">
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03-a.png"/></p>
								<p class="img"><img src="${_slpres}/images/sp-03-a.png"/></p>
							</div>
							<p class="upload"><input type="button" class="blling-btn file-btn" value="上传图片"/>
								<!--<input type="file" class="file">--></p>
						</li>
					</ul>
					<ul>
						<li class="width-xlag">
							<p class="word"><b class="red">*</b>白色</p>
							<div class="width-img">
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03.png"/><i class="icon-remove-sign"></i>
								</p>
								<p class="img"><img src="${_slpres}/images/sp-03-a.png"/></p>
								<p class="img"><img src="${_slpres}/images/sp-03-a.png"/></p>
							</div>
							<p class="upload"><input type="button" class="blling-btn file-btn" value="上传图片"/>
								<!--<input type="file" class="file">--></p>
						</li>
					</ul>
				</div>
				<div class="nav-form-title">商品详情图文描述</div> <!--标题-->
				<div class="nav-form"><!--查询条件-->
					<ul>
						<li class="width-xlag">
							<p><img src="${_slpres}/images/edit.png"/></p>
						</li>
					</ul>
					<ul>
						<li>
							<p><input type="button" class="blling-btn width-btn" value="提交审核"></p>
							<p><input type="button" class="blling-btn width-btn" value="保存"></p>
							<p><input type="button" class="blling-btn width-btn" value="预览"></p>
						</li>

					</ul>
				</div>

			</div>

		</div>

	</div>
</div>	
<!-- footer -->
<div class="footer">版权所有 © SLP版权归运营家所有</div>
</body>
</html>
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
