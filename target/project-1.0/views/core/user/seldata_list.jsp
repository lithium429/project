<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>  
<table class="wc100">
	<thead>
		<tr>
			<th class="check w5per">
				<c:if test="${key_v!=2 &&  key_v!=3}"> <input type="checkbox" id="check_all" class="inp_analog" />
				<span class="icon ico-checkbox"></span></c:if>
			</th>
			<th class="serial w7per">序号</th>
			<th class="w12per">部门科室</th>
			<th class="w15per">用户名</th>
			<th class="w15per">姓名</th>
			<th class="w15per">角色</th>
			<th class="w8per">性别</th>
			<th class="w10per">手机</th>
			<th class="">状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="item" varStatus="vs">
			<tr>
				<td class="check w3per">
					<input type="checkbox" name="id" class="inp_analog batch" key="${show_phone?item.getKey():item.real_name}" value="${item.id}" />
					<span class="icon ico-checkbox"></span>
				</td>
				<td class="serial">${vs.count+pageInfo.startIndex}</td>
				<td><span class="dept_name">${item.dept_name}</span></td>
				<td><span class="name">${item.name}</span></td>
				<td><span class="real_name">${item.real_name}</span></td>
				<td>
					<c:forEach items="${item.roles}" var="temp" varStatus="vs1">
						<c:if test="${vs1.index!=0 }">,</c:if>${temp.role_name }
					</c:forEach>
				</td>
				<td>
				<span class="sex dn">${item.sex}</span>
				<span class="email dn">${item.email}</span>
				<span class="qq dn">${item.qq}</span>
					<c:choose>
						<c:when test="${item.sex==1 }">男</c:when>
						<c:when test="${item.sex==2 }">女</c:when>
					</c:choose>
				</td>
				<td><span class="mobile">${item.mobile}</span></td>
				<td>
					<c:choose>
						<c:when test="${item.state==1 }">正常</c:when>
						<c:when test="${item.state==2 }">冻结</c:when>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty list}">
			<tr>
				<td colspan="9" class="t_c"><span class="no-records">暂无数据</span></td>
			</tr>
		</c:if>
	</tbody>
</table>  
<c:if test="${!empty list}">
	<gd:Pager />
</c:if>
<script type="text/javascript">
    $(function () {
    	checkId();
	});
	</script>