<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<input type="hidden" name="id" value="${model.id}" />
<table class="view wc100">
	<tbody>
		<tr>
			<th class="w20per"><span class="c_red">*</span>模板名称：</th>
			<td>
				<input type="text" class="inp_t" data-val="true"
				data-val-required="请输入模板名称！" id="name" name="name"
				data-val-remote-url="admin/smsTemplate/validateName.do" 
				data-val-remote-type="POST" 
				data-val-remote-additionalfields="*.name,*.id" 
				data-val-remote="该模板名称已存在！" 
				value="${model.name}" />
				<span class="field-validation-error" data-valmsg-for="name"
						data-valmsg-replace="true"></span>
			</td>
		</tr>
		<tr>
			<th>是否默认:&nbsp;</th>
			<td>
				<input type="radio" name="is_default" value="true" <c:if test="${model.is_default}">checked="checked"</c:if> />是
				<input type="radio" class="ml10" name="is_default" value="false"  <c:if test="${!model.is_default}">checked="checked"</c:if>/>否
			</td>
		</tr>
		<tr>
			<th class="vtop">内容:&nbsp;</th>
			<td>
				<textarea cols="60" rows="5" name="content" class="inp_t form_ta">${model.content}</textarea>
			</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<td class="btn_area" colspan="2"><span class="btn btn_pub">
					<input type="submit" value="保存" />
			</span><span class="ml10 btn btn_base"> <input type="button"
					id="btn_pclose" value="取消">
			</span></td>
		</tr>
	</tfoot>
</table>
<script type="text/javascript">
$(function(){
	
});
	function backToList(result) {
		if (result.flag) {
			successMsg("保存成功！", function() {
				var opener = art.dialog.open.origin;
				opener.$('#spec_form').submit();
				closeDialog();
			});
		} else {
			errorMsg(result.msg);
		}
	}

	function showError(result) {
		errorMsg(result.msg);
	}
	
</script>