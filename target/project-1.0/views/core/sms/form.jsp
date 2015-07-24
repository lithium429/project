<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<input type="hidden" name="id" value="${model.id}" />
<table class="view wc100">
	<tbody>
		<tr>
			<th class="w15per">模板类型：</th>
			<td>
				<label><input type="radio" class="" name="type" value="1" <c:if test="${model.type==1 || empty model.id}">checked="checked"</c:if> />普通</label>
				<label class="ml10">
					<input type="radio" class="ml10" name="type" value="2" <c:if test="${model.type==2}">checked="checked"</c:if> />会议提醒
				</label>
							
			</td>
		</tr>
		<tr>
			<th class="vtop"><span class="c_red">*</span>内容:&nbsp;</th>
			<td>
				<textarea cols="60" rows="5" name="content" class="inp_t form_ta" data-val="true" data-val-required="请输入内容！">${model.content}</textarea>
				<span class="field-validation-error" data-valmsg-for="content"
						data-valmsg-replace="true"></span>
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