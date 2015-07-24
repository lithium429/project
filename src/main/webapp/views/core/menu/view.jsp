<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:PopLayout> 
	<div class="data_model data_cont_wrap">
	    <table class="view_data wc100"> 
	    	<tbody> 
				<tr>
					<th class="w20per">用户名：</td>
					<td>${model.name}</td>
				</tr> 
				<tr>
					<td colspan="2">用户日志</td>
				</tr> 
				<c:if test="${model.logs != null}">
					<c:forEach items="${model.logs}" var="item">
						<tr>
							<th class="w20per">内容：</td>
							<td>${item.content}</td>
						</tr> 	
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	    <div class="btn_area">
	        <span class="btn btn_base"><input type="button" id="btn_pclose" value="关闭"></span>
	    </div>
	</div>
</gd:PopLayout> 