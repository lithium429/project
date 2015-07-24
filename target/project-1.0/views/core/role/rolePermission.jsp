<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!-- S 添加信息 -->
 <div class="view_data">
      <table class="con_c wc80" id="CheckTable">
          <tr>
              <th class="w25per t_l">
                 	<span class="ml10">菜单</span>
                 	<label class="ml30"><input type="checkbox" id="QX" class="inp_analog" /><span class="mr5 icon ico-checkbox"></span>全选</label>
                 	<c:if test="${role != null && !role.is_super}">
                 		<span class="btn btn_pub ml10"><input type="button" value="保存" id="btn_save_top" /></span>
                 	</c:if>
              </th>
              <th class="t_l">
                 	<span class="ml10">权限动作</span>
              </th>
          </tr>
          <tbody>
          	<c:if test="${ menuList1.size()>0}">
          		<c:forEach items="${menuList1}" var="item" varStatus="vs1">
          			<tr>
                          <td colspan="2" style="height:8px;"></td>
                      </tr>
                      <tr>
                          <td colspan="2" class=" inside_group" style="text-indent:0;font-weight:700;">
                              <label><input type="checkbox" index="${vs1.count }" class="inp_analog check parent best${vs1.count }" value="${item.id}" <c:if test="${gdf:rolePowerSel(roleMenuList,item.id,false)}">checked="checked"</c:if>  />
                              <span class="mr5 icon ico-checkbox <c:if test="${gdf:rolePowerSel(roleMenuList,item.id,false)}">ico-checkbox-checked</c:if>"></span>${item.name }</label>
                          </td>
                      </tr>
          			<c:forEach items="${menuList2}" var="temp" varStatus="vs2">
          				<c:if test="${!empty temp.parent_id && temp.parent_id == item.id }">
          					<tr>
                                  <td>
                                      <label class="ml20"><input type="checkbox" index="${vs2.count }" parentIndex="${vs1.count }" class="inp_analog check moreParent parent${vs1.count } best${vs1.count }good${vs2.count }" value="${temp.id }" <c:if test="${gdf:rolePowerSel(roleMenuList,temp.id,false)}">checked="checked"</c:if> />
                                      <span class="mr5 icon ico-checkbox <c:if test="${gdf:rolePowerSel(roleMenuList,temp.id,false)}">ico-checkbox-checked</c:if>"></span>${temp.name }</label>
                                  </td>
                                  <td>
                                  	<c:if test="${temp.actions.size()>0 }">
                                  		<c:forEach items="${temp.actions}" var="remp" varStatus="vs">
                                  			<label class="ml5"><input type="checkbox" moreParentIndex="${vs1.count }" parentIndex="${vs2.count }" class="inp_analog check IsAction action${vs1.count } action parent${vs1.count }moreParent${vs2.count }" value="${remp.id }" <c:if test="${gdf:rolePowerSel(roleMenuList,remp.id,true)}">checked="checked"</c:if> />
                                              <span class="mr5 icon ico-checkbox <c:if test="${gdf:rolePowerSel(roleMenuList,remp.id,true)}">ico-checkbox-checked</c:if>"></span>${remp.name }</label>
                                  		</c:forEach>
                                  	</c:if>                   
                                  </td>
                              </tr>
                              <c:forEach items="${menuList3}" var="qemp" varStatus="vs3">
            				<c:if test="${!empty qemp.parent_id && qemp.parent_id == temp.id }">
            					<tr>
                                    <td>
                                        <label class="ml40"><input type="checkbox" index="${vs3.count }" parentIndex="${vs2.count }" moreParentIndex="${vs1.count }" class="inp_analog check bestParent moreParent${vs1.count } parent${vs1.count }moreParent${vs2.count }bestParent parent${vs1.count }moreParent${vs2.count } best${vs1.count }good${vs2.count }bad${vs3.count }" value="${qemp.id }" <c:if test="${gdf:rolePowerSel(roleMenuList,qemp.id,false)}">checked="checked"</c:if> />
                                        <span class="mr5 icon ico-checkbox <c:if test="${gdf:rolePowerSel(roleMenuList,qemp.id,false)}">ico-checkbox-checked</c:if>"></span>${qemp.name }</label>
                                    </td>
                                    <td>
                                    	<c:if test="${qemp.actions.size()>0 }">
                                    		<c:forEach items="${qemp.actions}" var="remp_thr" varStatus="vs">
                                    			<label class="ml5"><input type="checkbox" bestParentIndex="${vs1.count }" moreParentIndex="${vs2.count }" parentIndex="${vs3.count }" class="inp_analog check IsAction litteAaction${vs1.count } litteAaction parent${vs1.count }moreParent${vs2.count } parent${vs1.count }moreParent${vs2.count }bestParent${vs3.count }" value="${remp_thr.id }" <c:if test="${gdf:rolePowerSel(roleMenuList,remp_thr.id,true)}">checked="checked"</c:if> />
                                                <span class="mr5 icon ico-checkbox <c:if test="${gdf:rolePowerSel(roleMenuList,remp_thr.id,true)}">ico-checkbox-checked</c:if>"></span>${remp_thr.name }</label>
                                    		</c:forEach>
                                    	</c:if>                   
                                    </td>
                                </tr>
                                
            				</c:if>
            			</c:forEach>
          				</c:if>
          			</c:forEach>
                  </c:forEach>
              </c:if>
          </tbody>
      </table>
 </div>
 <div class="t_c ptb10">
 	<c:if test="${role != null && !role.is_super}">
	    <span class="btn btn_pub">
	        <input type="button" value="保存" id="btn_save" />
	    </span>
    </c:if>
 </div>
<script type="text/javascript">
    var flag = true;
    $(function () {
        if ($(".check:checked").size() > 0 && $(".check").size() == $(".check:checked").size()) {
            $("#QX").attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
        } else {
            $('#QX').removeAttr('checked').next(".icon").removeClass("ico-checkbox-checked");
        }

        $(".parent").click(function () {
            var $this = $(this);
            var index = $this.attr("index");
            if ($this.attr("checked")) {
                $(".parent" + index + ",.moreParent" + index + ",.action" + index + ",.litteAaction" + index).attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
            } else {
                $(".parent" + index + ",.moreParent" + index + ",.action" + index + ",.litteAaction" + index).removeAttr('checked').next(".icon").removeClass("ico-checkbox-checked");
            }
        });

        $(".moreParent").click(function () {
            var $this = $(this);
            var index = $this.attr("index"), parentIndex = $this.attr("parentIndex");

            if ($this.attr("checked")) {
                if ($(".parent" + parentIndex + ":checked").size() == 1) {
                    $(".best" + parentIndex).attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
                }
                $(".parent" + parentIndex + "moreParent" + index).attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
            } else {
                $(".parent" + parentIndex + "moreParent" + index).removeAttr('checked').next(".icon").removeClass("ico-checkbox-checked");
            }
        });

        $(".bestParent").click(function () {
            var $this = $(this);
            var index = $this.attr("index"), parentIndex = $this.attr("parentIndex"), moreParentIndex = $this.attr("moreParentIndex");

            if ($this.attr("checked")) {
                if ($(".parent" + moreParentIndex + "moreParent" + parentIndex + "bestParent" + ":checked").size() == 1) {
                    $(".best" + moreParentIndex + "good" + parentIndex).attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
                }
                $(".parent" + moreParentIndex + "moreParent" + parentIndex + "bestParent" + index).attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
            } else {
                $(".parent" + moreParentIndex + "moreParent" + parentIndex + "bestParent" + index).removeAttr('checked').next(".icon").removeClass("ico-checkbox-checked");
            }
        });

        $(".action").click(function () {
            var $this = $(this);
            var parentIndex = $this.attr("parentIndex"), moreParentIndex = $this.attr("moreParentIndex");
            if ($this.attr("checked")) {
                if ($(".parent" + moreParentIndex + "moreParent" + parentIndex + ":checked").size() == 1) {
                    $(".best" + moreParentIndex + ",.best" + moreParentIndex + "good" + parentIndex).attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
                }
            }
        });

        $(".litteAaction").click(function () {
            var $this = $(this);
            var parentIndex = $this.attr("parentIndex"), moreParentIndex = $this.attr("moreParentIndex"), bestParentIndex = $this.attr("bestParentIndex");
            if ($this.attr("checked")) {
                if ($(".parent" + bestParentIndex + "moreParent" + moreParentIndex + "bestParent" + parentIndex + ":checked").size() == 1) {
                    $(".best" + bestParentIndex + ",.best" + bestParentIndex + "good" + moreParentIndex + ",.best" + bestParentIndex + "good" + moreParentIndex + "bad" + parentIndex).attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
                }
            }
        });

        // 批量操作的复选框
        $('#QX').click(function () {
            if ($(this).attr('checked')) {
                $(this).next(".icon").addClass("ico-checkbox-checked");
                $('.check:checkbox').each(function (index, item) {
                    if ($(item).attr('disabled') !== 'disabled') {
                        $(item).attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
                    }
                });
            } else {
                $(this).next(".icon").removeClass("ico-checkbox-checked");
                $('.check:checkbox').removeAttr('checked').next(".icon").removeClass("ico-checkbox-checked");
            }
        });

        //页面上所有的checkbox点击的时候触发的事件，以此来判断
        $(".check").click(function () {
            var $this = $(this);
            if ($this.attr("checked")) {
                $(this).next(".icon").addClass("ico-checkbox-checked");
                if ($(".check:checked").size() == $(".check").size()) {
                    $('#QX').attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
                }
                else {
                    $('#QX').removeAttr("checked").next(".icon").removeClass("ico-checkbox-checked");
                }
            }
            else {
                $(this).next(".icon").removeClass("ico-checkbox-checked");
                $('#QX').removeAttr("checked").next(".icon").removeClass("ico-checkbox-checked");
            }
        });

        $('#btn_save,#btn_save_top').click(function () {
            var idArray = [], idActionArray = [];
            $(".check:checked").each(function () {
                var $item = $(this);
                if ($item.hasClass("IsAction")) {
                    idActionArray.push($item.val());
                } else {
                    idArray.push($item.val());
                }
            });
            $('#MenuIds').val(idArray.join(','));
            $('#MenuActionIds').val(idActionArray.join(','));
            if(flag)
           	{
            	$("#data_form").submit();
           	}
        });
    });

    function backToList(result) {
		if (result.flag) {
			successMsg(result.msg, function() {
				//$('#spec_form').submit();
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
<!-- E 添加信息 -->