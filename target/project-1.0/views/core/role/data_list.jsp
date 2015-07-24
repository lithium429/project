<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<h2 class="inside_group">角色列表</h2>
<c:if test="${gdf:judgeRoleMenu(roleMenuList,'添加') }">
<span class="p_abs abs_rt btn btn_pub" style="top:4px;">
	<a href="javascript:void(0);" onclick="role.create();">添加角色</a>
</span></c:if>
<ul class="list role_ul">
	<c:forEach items="${list}" var="item" varStatus="vs">  
		<li class="role_li ${vs.index == 0 ? 'cur' : ''}" span_id="${item.id }">
		<span class="hand" style="padding-right:20px;">${item.name }</span>
		<c:if test="${!item.is_super}">
			<c:if test="${gdf:judgeRoleMenu(roleMenuList,'修改') }"><a href="admin/role/update.do?id=${item.id}" class="update">修改</a>&nbsp;</c:if>
			<c:if test="${gdf:judgeRoleMenu(roleMenuList,'删除') }"><a href="admin/role/delete.do?id=${item.id}" class="delete">删除</a></c:if>
		</c:if>
		</li>
	</c:forEach>
</ul>
<script type="text/javascript">
    $(function () {
        init();
    });

    function init() {
    	$("#list_rp").html('');
        if ($(".role_ul .cur").length > 0) {
            var $item = $(".role_ul .cur"), roleId = $item.attr("span_id");
            $("#roleId,#role_id").val(roleId);
            $("#spec_form_rp").submit();
        }
    }
</script>