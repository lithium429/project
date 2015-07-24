<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<gd:PopLayout> 
	<div class="tabs_hd">
		<ul class="clearfix Tab_Panels dib mr10" id="usersel">
			<li class="cur"><a href="javascript:void(0);" class="tab_inner" key="1">部门</a></li>
			<li><a href="javascript:void(0);" class="tab_inner" key="2">角色</a></li>
			<li><a href="javascript:void(0);" class="tab_inner" key="3">在线</a></li>
		</ul>
		<form id="spec_form1" action="admin/user/selUserType.do"
						data-ajax="true" data-ajax-begin="loadBegin"
						data-ajax-failure="loadFailure" data-ajax-complete="loadComplete"
						data-ajax-loading="#loading_img" data-ajax-method="POST"
						data-ajax-mode="replace" data-ajax-update="#usertype"
						novalidate="novalidate" class="dib">
				<input type="hidden" id="key_v" name="key_v" value="${key_v }" />
				<input type="hidden" name="is_my" value="${is_my }" />
				<input type="hidden" name="show_phone" value="${show_phone }" />
				<input type="hidden" name="type" id="type" value="1" />
		       	<input type="text" class="inp_t" name="keyword" id="keyword"  onkeyup="responseEnter(event);" value="" />
		       	
		       	<span class="btn_area ml10">
					<span class="btn btn_pub">
						<input id="btn_save" type="button" value="保存" />
					</span>
					<span class="ml10 btn btn_base">
						<input type="button" id="btn_pclose" value="取消" />
					</span>
				</span>
     	</form>
     	
	</div>
	<div class="data_model colum_two clearfix" id="usertype">
		
 	</div>
 	<input type="hidden" value="sel">  
	<img class="dn" id="loading_img" src="static/img/loading.gif" alt="loading" /> 
	<script type="text/javascript" src="static/js/index.js"></script>


<script type="text/javascript">
//key_v==0表示用户无特殊效果(多选);
//key_v==1表示用户(多选);
//key_v==2表示显示用户(单选);
//key_v==3表示员工管理的系统用户(单选);
	var nameArray=[],idArray=[];
	var opener = art.dialog.open.origin;
	var key_v=$("#key_v").val();
    $(function () { 
    	var selIds = null,selNames = null; 
    	if(key_v==0)//选择共享用户
   		{
    		selIds=opener.$("#user_ids").val();
    		selNames=opener.$("#user_names").val();
        	if(!util.isEmpty(selIds)){
        		idArray = selIds.split(",");
       		}
        	if(!util.isEmpty(selNames)){
        		nameArray = selNames.split(",");
       		} 
    		
   		}else if(key_v==1 || key_v==2 || key_v==3)//选择内部用户
  		{
   			opener.$("#innerUser span").each(function(){
   				var $item=$(this);
   				idArray.push($item.attr("key_id"));
   				nameArray.push($item.attr("key_name"));
   			}); 
  		}
    	$("#spec_form1").submit();
    	
    	//用户显示方式选择
    	$(".tab_inner").click(function(){
    		var $this=$(this);
    		$this.parent().addClass("cur").siblings("li").removeClass("cur");
    		$("#type").val($this.attr("key"));
        	$("#spec_form1").submit();
    	});
    	
    	
    	
    	$(".batch").live("click",function(){
    		var $this=$(this);
    		if($this.attr('checked') === 'checked')
   			{
    			if(key_v==2 || key_v==3)
    	    	{
    				nameArray=[];
    				idArray=[];
    				$this.parents("tr").siblings().find(".batch").removeAttr('checked').next(".icon").removeClass("ico-checkbox-checked");
    	    	}
    			 idArray.push($this.val());
    			 nameArray.push($this.attr("key"));
   			}else
 			{
   				var i=arrayUtil.get(idArray,$this.val());
   				if(i!=-1)
 			    {
   					idArray=arrayUtil.removeByIndex(idArray,i);
   					nameArray=arrayUtil.removeByIndex(nameArray,i);
 			    }
 			}
    	});
    	
    	// 批量操作的复选框
	    $('#check_all').live('click',function () {
	        if ($(this).attr('checked') === 'checked') {
	        	$(this).next(".icon").addClass("ico-checkbox-checked");
	            $('.batch:checkbox').each(function(index,item){
	                if($(item).attr('disabled') !== 'disabled'){
	                  if(arrayUtil.get(idArray,$(item).val())==-1)
	                	{
	                	  	idArray.push($(item).val());
	         			 	nameArray.push($(item).attr("key"));
	                	}
	                }
	            });
	        } else {
	        	$('.batch:checkbox').each(function(index,item){
	                if($(item).attr('disabled') !== 'disabled'){
	                	var i=arrayUtil.get(idArray,$(item).val());
         				if(i!=-1)
	       			    {
         					idArray=arrayUtil.removeByIndex(idArray,i);
         					nameArray=arrayUtil.removeByIndex(nameArray,i);
	       			    }
	                }
	            });
	        }
	    });
    	
    	$("#btn_save").click(function(){ 
    		if(key_v==0)
   			{
	    		opener.$("#user_ids").val(idArray.join(','));
	    		opener.$("#user_names").val(nameArray.join(','));
   			}else if(key_v==1|| key_v==3)
 			{
   				var html="";
   				arrayUtil.each(idArray, function (index, item) {
   					if(html=="")
  					{
   						html="<span class='user_child' key_id='"+item+"' key_name='"+nameArray[index]+"'>"+nameArray[index]+"<em class='del_user'>&times;</em></span>"
  					}else
 					{
  						html+="<span class='user_child' key_id='"+item+"' key_name='"+nameArray[index]+"'>"+nameArray[index]+"<em class='del_user'>&times;</em></span>"
 					}
   				});
   				opener.$("#innerUser").html(html);
 			}else if(key_v==2 )//key_v==3表示员工管理的系统用户
 			{
 				var html="";
   				arrayUtil.each(idArray, function (index, item) {
   					var name=nameArray[index].substring(0,nameArray[index].indexOf("("));
   					if(html=="")
  					{
   						html="<span class='user_child' key_id='"+item+"' key_name='"+name+"'>"+name+"<em class='del_user'>&times;</em></span>"
  					}else
 					{
  						html+="<span class='user_child' key_id='"+item+"' key_name='"+name+"'>"+name+"<em class='del_user'>&times;</em></span>"
 					}
   				});
   				opener.$("#innerUser").html(html);
 			}
    		if(key_v==3)
    		{
    			var $item=$("input.batch:checked").parent().parent();
    			if(!util.isNull($item)){
    				opener.$("#name").val($item.find(".real_name").html());
    				opener.$(".sex[value='"+$item.find(".sex").html()+"']").attr("checked","checked");
    				opener.$("#email").val($item.find(".email").html());
    				opener.$("#qq").val($item.find(".qq").html());
    				opener.$("#mobile").val($item.find(".mobile").html());
    			}
    		}
			closeDialog();
    	});
	});

    //在快速搜索中按回车触发的事件
    function responseEnter(e) {
        // 响应回车
        var key = window.event ? e.keyCode : e.which;
        if (key == 13) {
        	$("#usersel .cur").removeClass("cur");
        	$("#type").val('');
        	$("#spec_form1").submit();
        }

    }
    
    function checkId()
    {
    	if(!util.isNull(idArray))
   		{
    		$(".batch").each(function(){
    			var $this=$(this);
    			$.each(idArray,function(n,value){
       			if($this.val()==value)
    			{
       				$this.attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
    			}
       		 	});
    		})
    		if($('.batch:checked').size() == $('.batch').size() && $('.batch:checked').size()!=0){
                $('#check_all').attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
            }
    		 
   		}
    }
	</script>
</gd:PopLayout>
