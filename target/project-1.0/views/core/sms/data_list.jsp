<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<script type="text/javascript" src="static/js/flexigrid-1.1/js/flexigrid.js"></script> 
<div class="datalistbox" style="top:80px;">
	<table class="OptionTable" id="tableSort">
		    <thead>
		    	<tr>
			        <th class="check w3per">
			            <input type="checkbox" id="check_all" class="inp_analog" /><span class="icon ico-checkbox"></span>
			        </th>
			        <th class="serial w5per">序号</th>
	   				<th class="w40per">短信内容</th>
	   				<th class="w8per">发信人</th>
	   				<th class="w8per">收信人</th>
	   				<th class="w8per">收信人手机</th>
	   				<th class="w12per">发送时间</th>
	   				<th class="w6per">状态</th>
			        <th class="hd_operate">操作</th>
			  	</tr>
		    </thead>
	    	<tbody>
				<c:forEach items="${list}" var="item" varStatus="vs">  
	            	<tr >
						<td class="check w3per">
		                	<c:if test="${type==0 && item.state==2 }"><input type="checkbox" name="id" class="inp_analog batch" value="${item.id}" /><span
			                    class="icon ico-checkbox"></span></c:if>
			            </td>
			            <td class="serial">
			                ${vs.count+pageInfo.startIndex}
			            </td>
			            <td>
			            	${item.content}
			            </td>
			            <td>
			            	${item.sender_name}
			            </td>
			            <td>
			            	${item.receiver_name}
			            </td>
			            <td>
			            	${item.phone}
			            </td>
			            <td>
			            	<fmt:formatDate value="${item.create_time}" pattern="yyyy-MM-dd HH:mm:ss" />
			            </td>
			            <td>
			            	<c:choose>
			            		<c:when test="${item.state==0 }">成功</c:when>
			            		<c:when test="${item.state!=0 }">失败</c:when>
			            	</c:choose>
			            </td>
						<td  class="operations">
						<c:if test="${type==0 && item.state==2 && gdf:judgeRoleMenu(roleMenuList,'重新发送') }">
							<a href="admin/sms/send_again.do?id=${item.id}" class="send_again">重新发送</a>&nbsp;</c:if>
						<c:if test="${gdf:judgeRoleMenu(roleMenuList,'查看') }">
							<a href="admin/sms/view.do?id=${item.id}" class="view">查看</a></c:if>
						</td>
					</tr>            
				</c:forEach>
	         	<c:if test="${empty list}">
					<tr>
			        	<td colspan="9" class="t_c">
			                <span class="no-records">暂无数据</span>
			            </td>
			        </tr>
	        	</c:if>
    	</tbody>
    </table>
</div>
<c:if test="${!empty list}">
	<gd:Pager />
	<%-- <div class="bot_fun" style="width:300px;">
		已发短信条数统计：<span class="c_red">${scount }</span> 条
		<c:if test="${type==0 && gdf:judgeRoleMenu(roleMenuList,'批量重新发送') }">
			<a class="btn" id="bath_send_again" href="admin/sms/send_again.do">重新发送</a> </c:if>
	</div> --%>
</c:if>
<script type="text/javascript">
    $(function () {
        $('.OptionTable').flexigrid();
     });
</script>