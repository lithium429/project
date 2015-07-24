// 列表
var list = (function(){
	function init(){
		checkInit();
		pagerInit();
		deleteInit();
	}
		
	function pagerInit(){
		$('#btn_search').click(function(){
			$('#page_index').val('1');
		});
		
		// ajax 翻页
	    $('#pager a').live('click',function () {
	        var url = $(this).attr('href'),
	        regex = /page_index=(\d+)/i,
	        arr = url.match(regex),
	        pageIndex = arr ? arr[1] : 1;      
	        $('#spec_form [name="page_index"]').val(pageIndex);
	        $('#spec_form').submit();
	        return false;
	        
	    });


	    $('#btn_goto_page').live('click',function () {
	        var gotoIndex = $('#text_goto_page').val(),
	        currentIndex = $('#spec_form [name="page_index"]').val();
	        var pageCount = $("#page_count").val();       
	        if (gotoIndex == currentIndex) {
	            return;
	        }
	        if(gotoIndex > pageCount)
	        {
	        	gotoIndex = pageCount;
	        }
	        $('#spec_form [name="page_index"]').val(gotoIndex);
	        $('#spec_form').submit();
	        return false;
	     });
	}
	
	function checkInit(){
		// 批量操作的复选框
	    $('#check_all').live('click',function () {
	        if ($(this).attr('checked') === 'checked') {
	        	$(this).next(".icon").addClass("ico-checkbox-checked");
	            $('.batch:checkbox').each(function(index,item){
	                if($(item).attr('disabled') !== 'disabled'){
	                    $(item).attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
	                }
	            });
	        } else {
	        	$(this).next(".icon").removeClass("ico-checkbox-checked");
	            $('.batch:checkbox').removeAttr('checked').next(".icon").removeClass("ico-checkbox-checked");
	        }
	    });

	    //页面上所有的checkbox点击的时候触发的事件，以此来判断
	    $('.batch').live('click',function(){
	        var $this = $(this);
	        if($this.attr('checked') === 'checked'){
	        	$(this).next(".icon").addClass("ico-checkbox-checked");
	            if($('.batch:checked').size() == $('.batch').size()){
	                $('#check_all').attr('checked', 'checked').next(".icon").addClass("ico-checkbox-checked");
	            }
	            else{
	                $('#check_all').removeAttr('checked').next(".icon").removeClass("ico-checkbox-checked");
	            }
	        }
	        else{
	        	 $(this).next(".icon").removeClass("ico-checkbox-checked");
	             $('#check_all').removeAttr('checked').next(".icon").removeClass("ico-checkbox-checked");
	        }
	    });
	}
	
	function deleteInit(){
		/**
		 * @param linkClicked 触发单击事件的目标对象
		 */
		function ajaxDelete(linkClicked) {
		    // 1 删除
		    $.ajax({
		        type: "POST",
		        url: linkClicked.attr("href"),
		        dataType: "json",
		        beforeSend: function(){
		        	$.ajaxTip.show();
		        },
		        success: function (data) {
		            if (data.flag) {
		                successMsg("删除成功！",function(){
		                	// 2 刷新列表
		                    $('#spec_form').submit();
		                });
		            } else {
		                errorMsg(data.msg ? data.msg : "删除失败！");
		            }

		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		            errorMsg("出现未知错误！");
		        },
		        complete: function(){
		        	$.ajaxTip.remove();
		        }
		    });
		}

		/**
		 * @param selectedIds 以逗号','连接的id字符串
		 * @param linkClicked 触发单击事件的目标对象
		 */
		function ajaxBatchDelete(selectedIds, linkClicked) {
		    // 1 删除
		    $.ajax({
		        type: "POST",
		        url: linkClicked.attr("href"),
		        data: { ids: selectedIds },
		        dataType: "json",
		        beforeSend: function(){
		        	$.ajaxTip.show();
		        },
		        success: function (data) {
		            if (data.flag) {
		                successMsg("删除成功！",function(){
		                    // 2 刷新列表
		                	$('#spec_form').submit();
		                });                
		            } else {
		                errorMsg(data.msg ? data.msg : "删除失败！");
		            }

		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		            errorMsg("出现未知错误！");
		        },
		        complete: function(){
		        	$.ajaxTip.remove();
		        }
		    });
		}
		
		// ajax 删除
	    $('a.delete').live('click',function () {

	        var linkClicked = $(this);
	        art.dialog.confirm('您确定要删除？', function () {
	            ajaxDelete(linkClicked);
	        });

	        return false;
	    });

	    // ajax批量删除
	    $('#batch_delete').live('click',function () {
	        var idArray = [], $this = $(this);
	        $("[name='id']:checked").each(function () {
	            idArray.push($(this).val());
	        });
	        
	        if (idArray.length === 0) {
	            informationMsg('请选择要删除的信息！');
	        }
	        else{
	            art.dialog.confirm('您确定要将选中的都删除？', function () {
	                ajaxBatchDelete(idArray.join(','), $this);
	            });
	        }
	        return false;
	    });
	}
	
	return {init:init};
})();

/**
 * 列表开始加载
 */
function loadBegin(){
	showTip();
	$('#error_msg').hide();
}

/**
 * 列表加载完成
 */
function loadComplete(){
	hideTip();
}

/**
 * 列表加载失败
 */
function loadFailure(){
	hideTip();
    $('#data_list').html('<div id="error_msg">读取数据失败，请重试</div>');
}

//列表页初始化绑定事件
$(function () { 
	list.init(); 
});