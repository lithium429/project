<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:PopLayout>
	<!-- S 添加信息 -->
	<div class="data_model data_cont_wrap pop_wrap">
		<form id="data_form" method="post" action="admin/user/add.do" data-ajax="true" data-ajax-begin="showTip" data-ajax-complete="hideTip" data-ajax-success="backToList" data-ajax-error="showError">
			<input type="hidden" id="role_ids" name="role_ids"/>
			<input type="hidden" id="branch_ids" name="branch_ids"/>
			<input type="hidden" id="state" name="state" value="1"/>
			<input type="hidden" id="id" name="id"/>
			<table class="view wc100">
				<tbody>
					<tr>
						<th class="w100"><span class="c_red">*</span>用户名:&nbsp;</th>
						<td class="w260">
							<input type="text" class="inp_t" data-val="true" 
							data-val-remote-url="admin/user/validateName.do" 
							data-val-remote-type="POST" 
							data-val-remote-additionalfields="*.name,*.id" 
							data-val-remote="该用户名已存在！"
							data-val-required="请输入用户名！" id="name" name="name" value="" />
							<span class="field-validation-error" data-valmsg-for="name" data-valmsg-replace="true"></span>
						</td>
						<th class="w95"><span class="c_red">*</span>姓名:&nbsp;</th>
						<td>
							<input type="text" class="inp_t" data-val="true" data-val-required="请输入姓名！" id="real_name" name="real_name" value="" />
							<span class="field-validation-error" data-valmsg-for="real_name" data-valmsg-replace="true"></span>
						</td>
						
					</tr>
					<tr>
						<th>性别:&nbsp;</th>
						<td>
							<label><input type="radio" checked="checked" name="sex" value="1" />男</label>
							<label><input type="radio" class="ml10" name="sex" value="2" />女</label>
						</td>
						<th><span class="c_red">*</span>所属部门:&nbsp;</th>
						<td>
							<select id="dept_id" name="dept_id" data-val="true" data-val-required="请选择所属部门！">
								<option value="">--请选择--</option>
								<c:forEach items="${departmentList}" var="item" varStatus="vs">
									<option value="${item.id }">${gdf:buildTreeName(item.name, item.layer)}</option>
								</c:forEach>
							</select>
							<span class="field-validation-error" data-valmsg-for="dept_id" data-valmsg-replace="true"></span>
						</td>
					</tr>
					<tr>
						<th><span class="c_red">*</span>所属角色:&nbsp;</th>
						<td>
							<select id="role_id" name="role_id" class="w100">
								<option value="">--请选择--</option>
								<c:forEach items="${roleList }" var="item" varStatus="vs">
									<option value="${item.id }">${item.name }</option>
								</c:forEach>
							</select>
							<span class="error dn" id="role_error">请选择所属角色！</span>
						</td>
						<th><span class="c_red">*</span>手机:&nbsp;</th>
						<td>
							<input type="text" class="inp_t" data-val="true" data-val-required="请输入手机！" id="mobile" name="mobile" value="" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
							<span class="field-validation-error" data-valmsg-for="mobile" data-valmsg-replace="true"></span>
						</td>
					</tr> 
					<tr>
						<th class="vtop">备注:&nbsp;</th>
						<td colspan="3">
							<textarea cols="60" rows="5" name="remark" class="inp_t form_ta">${model.remark}</textarea>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td></td>
						<td class="ptb10">
							<span class="btn btn_pub">
								<input id="btn_save" type="submit" value="保存" />
							</span>
							<span class="ml10 btn btn_base">
								<input type="button" id="btn_pclose" value="取消" />
							</span>
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</div>
	<!-- E 添加信息 -->
	<script type="text/javascript">
		var opener = art.dialog.open.origin;
		$(function(){
			
			// 选中部门
			var deptId = opener.$("#department_id").val(); 
			if(!util.isEmpty(deptId)){
				$("#dept_id option[value='" + deptId + "']").attr("selected", "selected");
			}
			
			// 角色多选下拉初始化
			$("#role_id").multiSelect({ oneOrMoreSelected: '*' });
			$('#role_id').blur(checkRole);
			 
			$("#btn_save").click(function(){
				var flag = true, role_ids = $("#role_ids").val();
		 		
				// 调用jquery.validate.unobtrusive.js 提供的验证方法 
		        function validate() {
		            var validationInfo = $('#data_form').data('unobtrusiveValidation');
		            return !validationInfo || !validationInfo.validate || validationInfo.validate();
		        }
				
		        flag = validate() && flag;
		        flag = checkRole() && flag; 
		        return flag;
		        
			});
		});
	
		// 验证角色
		function checkRole() {
		    var roleIdObj = $('#role_id'),
		    values = roleIdObj.selectedValuesString();
		    $("#role_ids").val(values);
		    if(util.isEmpty(values)){
		    	$("#role_error").removeClass("dn");
		    	return false;
	    	} else{
	    		$("#role_error").addClass("dn");
		    	return true;
	   		}
		}
	  
		function backToList(result) {
			if (result.flag) {
				successMsg(result.msg ? result.msg : "保存成功！", function() {
					opener.$('#spec_form').submit();
					closeDialog();
				});
			} else {
				errorMsg(result.msg ? result.msg : "保存失败！");
			}
		}

		function showError() {
			errorMsg("添加用户时出错！");
		}
	</script>
</gd:PopLayout>
