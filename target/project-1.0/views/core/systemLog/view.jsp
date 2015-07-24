<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:PopLayout> 
	<div class="data_model data_cont_wrap">
	    <div class="view_data">
	        <table class="wc100">
				<tbody>
					<tr>
						<th class="w15per">所属模块</th>
						<td class="w35per">
							${model.gainModule()}
						</td>
						<th class="w15per">操作名称</th>
						<td class="">
							${model.operate}
						</td>
					</tr>
					<tr>
						<th class="">操作人</th>
						<td class="">
							${model.user_name}
						</td>
						<th class="">操作时间</th>
						<td class="">
							<fmt:formatDate value="${model.create_time}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					<tr>
						<th>内容</th>
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