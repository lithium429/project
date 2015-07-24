<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<input type="hidden" name="id" value="${model.id}" />
<table class="view wc100">
	<tbody>
		<tr>
			<th class="w60"><span class="c_red">*</span>${type_str }</th>
			<td class="">
				<input type="text" class="inp_t" data-val="true"
					data-val-remote-url="admin/region/validate_name.do" 
					data-val-remote-type="POST" 
					data-val-remote-additionalfields="*.name,*.id,*.parent_id" 
					data-val-remote="该${type_str }已存在！" 
					data-val-required="请输入${type_str }！" id="name" name="name"
					value="${model.name}" />
				<span class="field-validation-error" data-valmsg-for="name"
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
	function backToList(result) {
		if (result.flag) {
			successMsg("保存成功", function() {
				var opener = art.dialog.open.origin;
				opener.$('#spec_form'+$("#type").val()).submit();
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