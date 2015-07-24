<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:Layout>
	<c:if test="${type==-1}">
		<gd:Navgation addr="手机短信  &gt; 发送短信"></gd:Navgation>
	</c:if>
	<!-- S 添加信息 -->
	<div class="data_model data_cont_wrap sms_info">
	<form id="data_form" method="post" action="admin/sms/send.do" data-ajax="true" data-ajax-begin="showTip" data-ajax-complete="hideTip" data-ajax-success="backToList" data-ajax-error="showError">	
	<input type="hidden" name="is_allowso" id="is_allowso" value="${is_allowso }" />
	<input type="hidden" name="innerUsers" id="innerUsers" value="" />
	<input type="hidden" name="outerUsers" id="outerUsers" value="" />
	<input type="hidden" name="type" id="type" value="${!empty user?10:type }" />
		<div class="rightbox">
			<h2>短信模板</h2>
			<ul id="tplUl" class="scroll_box">
				<c:forEach items="${tplList}" var="item" varStatus="vs">
				<li class="tplLi">${item.content }</li>
				</c:forEach>
			</ul>
		</div>
		<table class="view w70per left_box">
			<tbody>
				<tr>
					<th class="w15per"><span class="c_red">*</span>收信人[内部用户]：</th>
					<td>
						<div id="innerUser" class="dib main_inpt">
							<c:if test="${!empty user }">
								<span class="user_child" key_id="${user.id }" key_name="${user.real_name }(${user.mobile })">${user.real_name }(${user.mobile })<em class="del_user">×</em></span>
							</c:if>
						</div>
						<a href="javascript:void(0);" id="addInner">添加</a>
						<span class="error dn">请选择内部用户！</span>			
					</td>
				</tr>
				<c:if test="${is_allowso }">
				<tr>
					<th class="">收信人[外部人员]：</th>
					<td>
						<div id="outerUser" class="dib main_inpt">
							
						</div>
						<a href="javascript:void(0);" id="addOuter">添加</a>		
					</td>
				</tr>
				<tr>
					<th class="">其他人员：</th>
					<td>
						<textarea cols="90" rows="5" name="otherUser" id="otherUser" class="inp_t form_ta""></textarea>
						<a href="javascript:void(0);" class="clear">清空</a>		
						<div class="c_red">输入多个号码请以英文“;”隔开</div>
					</td>
				</tr></c:if>
				<tr>
					<th class="vtop"><span class="c_red">*</span>短信内容:&nbsp;</th>
					<td>
						<textarea maxlength="200" id="content" cols="90" rows="5" name="content" class="inp_t form_ta" data-val="true" data-val-required="请输入短信内容！"></textarea>
						<a href="javascript:void(0);" class="clear">清空</a>	
						<span class="field-validation-error" data-valmsg-for="content"
								data-valmsg-replace="true"></span>	
						<div class="">
							已输入<span class="c_red" id="a_write">0</span>字符，剩余<span class="c_red" id="s_write">200</span>字符，每条70字符，最多可拆分成3条发送
						</div>
						<div class="orange">
							温馨提示：您所发送的短信将在本系统中进行记录，请勿发送与工作无关的涉及个人隐私的信息！
						</div>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td class="btn_area" colspan="2"><span class="btn btn_pub">
							<input type="submit" id="btnSave" value="发送" />
					</span><c:if test="${type!=-1}"><span class="ml10 btn btn_base"> <input type="button"
					id="btn_pclose" value="取消">
			</span></c:if></td>
				</tr>
			</tfoot>
		</table>
		
	<script type="text/javascript">
	$(function(){
		//发送click事件
		$("#btnSave").click(function(){
			var flag=true;
			function validate() {
	            var validationInfo = $('#data_form').data('unobtrusiveValidation');
	            return !validationInfo || !validationInfo.validate || validationInfo.validate();
	        }
	        flag=validate()&&flag;
			var innerUsers="",outerUsers="";
			$("#innerUser .user_child").each(function(){
				var $this=$(this);
				if(innerUsers=="")
				{
					innerUsers=$this.attr("key_id")+"["+$this.attr("key_name")+"]";
				}else
				{
					innerUsers+=";"+$this.attr("key_id")+"["+$this.attr("key_name")+"]";
				}
			});
			$("#outerUser .user_child").each(function(){
				var $this=$(this);
				if(outerUsers=="")
				{
					outerUsers=$this.attr("key_id")+"["+$this.attr("key_name")+"]";
				}else
				{
					outerUsers+=";"+$this.attr("key_id")+"["+$this.attr("key_name")+"]";
				}
			});
			if(flag && innerUsers=="" && outerUsers=="" && ($("#is_allowso").val()=="false" || $("#otherUser").val()=="") )
			{
				informationMsg("请选择收信人！");
				return false;
			}
			$("#innerUsers").val(innerUsers);
			$("#outerUsers").val(outerUsers);
			return flag;
		});
		
		//清空多行文本框
		$(".clear").click(function(){
			$(this).parent().find("textarea").val('');
			$("#a_write").html(0);
			$("#s_write").html(200);
			return false;
		});
		
		//短信模板click事件
		$("#tplUl li.tplLi").click(function(){
			var $this=$(this);
			$this.addClass("cur").siblings("li").removeClass("cur");
			var length=$this.html().length;
			if(length>200)
			{
				informationMsg("短信模板内容超出200个字符！")
			}else{
				$("#a_write").html(length);
				$("#s_write").html(200-length);
				
				$("#content").val($this.html());
			}
			
		});
		
		//添加内部用户
		$("#addInner").click(function(){
			var url = 'user/sellist.do?key_v=1&show_phone=true';
			openPage({
				url:url,
				id:'sel_page',
				title:'选择内部用户',
				width:'800px',
				height:'475px'
			});
			return false;
		});
		//添加外部人员
		$("#addOuter").click(function(){
			var url = 'address/sellist.do';
			openPage({
				url:url,
				id:'sel_page',
				title:'选择外部人员',
				width:'800px',
				height:'600px'
			});
			return false;
		});
		
		//删除用户
		$(".del_user").live("click",function(){
			$(this).parents(".user_child").remove();
		});
		
		$("#content").keyup(function(){
			var $this=$(this),length=$this.val().length;
			$("#a_write").html(length);
			$("#s_write").html(200-length);
		});
	});
		function backToList(result) {
			if (result.flag) {
				successMsg("发送成功！", function() {
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
	</form>
</div>

	<!-- E 添加信息 -->
</gd:Layout>