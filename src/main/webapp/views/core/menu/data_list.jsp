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
			<th class="w15per">菜单名称</th>
			<th class="w50per">菜单url</th>
			<th class="w5per">是否启用</th>
			<th class="w3per">排序</th>
	        <th class="hd_operate">操作</th>
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
				<td> 
					${gdf:paddingSpace(item.layer)}
					${item.name}
				</td>
				<td>${item.url}</td>
				<td>
					<c:choose>
						<c:when test="${item.is_enable }">是</c:when>
						<c:when test="${!item.is_enable }">否</c:when>
					</c:choose>
				</td>
				<td>${item.sort}</td>
				<td>
					<a href="admin/menu/update.do?id=${item.id}" class="update">修改</a>
					<a href="admin/menu/delete.do?id=${item.id}" class="delete">删除</a>
					<a href="admin/menu/view.do?id=${item.id}" class="view">查看</a>
					<c:if test="${!empty item.parent_id}">
						<a href="admin/action/list.do?menu_id=${item.id}" class="action">菜单动作</a></c:if>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty list}">
			<tr>
				<td colspan="7" class="t_c"><span class="no-records">暂无数据</span></td>
			</tr>
		</c:if>
	</tbody>
</table>
</div>
<c:if test="${!empty list}">
	<gd:Pager />
	<div class="bot_fun" style="width: 300px;">
		<a class="btn" id="batch_delete" href="admin/menu/batchdelete.do">批量删除</a>
	</div>
</c:if>
<span class="btn btn_pub" style="position:fixed;bottom:16px;left:115px;"><a href="admin/menu/add.do" id="btn_add_bottom">添加</a></span>
<script type="text/javascript">
    $(function () {
        $('.OptionTable').flexigrid();
     });
</script>