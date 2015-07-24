<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<script type="text/javascript" src="static/js/flexigrid-1.1/js/flexigrid.js"></script> 
<div class="datalistbox" style="top:80px;">
	<table class="OptionTable" id="tableSort">
		<thead>
			<tr>
				<th class="check w3per">
					<input type="checkbox" id="check_all" class="inp_analog" />
					<span class="icon ico-checkbox"></span>
				</th>
				<th class="serial w5per">序号</th>
				<th class="w10per">部门科室</th>
				<th class="w10per">用户名</th>
				<th class="w10per">姓名</th>
				<th class="w15per">角色</th>
				<th class="w8per">性别</th>
				<th class="w10per">手机</th>
				<th class="w8per">状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr>
					<td class="check w3per">
						<input type="checkbox" name="id" class="inp_analog batch" value="${item.id}" />
						<span class="icon ico-checkbox"></span>
					</td>
					<td class="serial">${vs.count+pageInfo.startIndex}</td>
					<td>${item.dept_name}</td>
					<td>${item.name}</td>
					<td>${item.real_name}</td>
					<td>
						<c:forEach items="${item.roles}" var="temp" varStatus="vs1">
							<c:if test="${vs1.index!=0 }">,</c:if>${temp.role_name }
						</c:forEach>
					</td>
					<td>
						<c:choose>
							<c:when test="${item.sex==1 }">男</c:when>
							<c:when test="${item.sex==2 }">女</c:when>
						</c:choose>
					</td>
					<td>${item.mobile}</td>
					<td>
						<c:choose>
							<c:when test="${item.state==1 }">正常</c:when>
							<c:when test="${item.state==2 }">冻结</c:when>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${item.state==1 && gdf:judgeRoleMenu(roleMenuList,'禁用')}">
							<a href="admin/user/changeState.do?id=${item.id}&state=2" class="jy changeState">禁用</a>
							</c:when>
							<c:when test="${item.state==2 && gdf:judgeRoleMenu(roleMenuList,'启用') }">
							<a href="admin/user/changeState.do?id=${item.id}&state=1" class="qy changeState">启用</a>
							</c:when>
						</c:choose>
						<c:if test="${gdf:judgeRoleMenu(roleMenuList,'修改') }">
						<a href="admin/user/update.do?id=${item.id}" class="update">修改</a></c:if>
						<c:if test="${gdf:judgeRoleMenu(roleMenuList,'删除') }">
						<a href="admin/user/delete.do?id=${item.id}" class="delete">删除</a></c:if>
						<c:if test="${gdf:judgeRoleMenu(roleMenuList,'创建通讯录') && !item.has_address }">
						<a href="admin/user/address.do?id=${item.id}" class="address">创建通讯录</a></c:if>
						<c:if test="${gdf:judgeRoleMenu(roleMenuList,'查看') }">
						<a href="admin/user/view.do?id=${item.id}" class="view">查看</a></c:if>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
				<tr>
					<td colspan="10" class="t_c"><span class="no-records">暂无数据</span></td>
				</tr>
			</c:if>
		</tbody>
	</table>
</div>
<script type="text/javascript">
    $(function () {
        $('.OptionTable').flexigrid();
     });
</script>
<c:if test="${!empty list}">
	<gd:Pager />
	<div class="bot_fun" style="width: 300px;">
		<c:if test="${gdf:judgeRoleMenu(roleMenuList,'重置密码') }">
			<a class="btn" id="batch_reset" href="admin/user/batchreset.do">重置密码</a></c:if>
		<c:if test="${gdf:judgeRoleMenu(roleMenuList,'批量启用') }">
			<a class="btn qy" id="batch_change" href="admin/user/batchchangeState.do?state=1">启用</a></c:if>
		<c:if test="${gdf:judgeRoleMenu(roleMenuList,'批量禁用') }">
			<a class="btn jy" id="batch_change1" href="admin/user/batchchangeState.do?state=2">禁用</a></c:if>
		<c:if test="${gdf:judgeRoleMenu(roleMenuList,'批量删除') }">
			<a class="btn" id="batch_delete" href="admin/user/batchdelete.do">批量删除</a></c:if>
	</div>
</c:if>