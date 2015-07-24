<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:PopLayout> 
	<div class="data_model data_cont_wrap">
	    <div class="view_data">
	        <table class="wc100">
				<tbody>
					<tr>
						<th class="w15per">发信人</th>
						<td class="w35per">
		            		${model.sender_name }
						</td>
						<th class="w15per">收信人</th>
						<td class="">
							${model.receiver_name }
						</td>
					</tr>
					<tr>
						<th class="">发送时间</th>
						<td class="">
		            		<fmt:formatDate value="${model.create_time}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<th class="">收信人手机</th>
						<td class="">
							${model.phone }
						</td>
					</tr>
					<tr>
						<th>状态</th>
						<td>
							<c:choose>
			            		<c:when test="${model.state==0 }">成功</c:when>
			            		<c:when test="${model.state!=1 }">失败</c:when>
			            	</c:choose>
		            	</td>
						<th>重发次数</th>
						<td>
							${model.retry_count }
		            	</td>
					</tr>
					<tr>
						<th class="vtop">内容</th>
						<td colspan="3">
							${model.content}
						</td>
					</tr>
				</tbody>
			</table>      
	    </div>
	    <div class="btn_area">
	        <span class="btn btn_base"><input type="button" id="btn_pclose" value="关闭"></span>
	    </div>
	</div>
</gd:PopLayout> 