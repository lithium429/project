/**
*  artDialog弹出层
**/
artDialog.notice = function (options) {
    var opt = options || {},
        api, aConfig, hide, wrap, top,
        duration = 800;

    var config = {
        id: 'Notice',
        left: '100%',
        top: '100%',
        fixed: true,
        drag: false,
        resize: false,
        follow: null,
        lock: false,
        init: function (here) {
            api = this;
            aConfig = api.config;
            wrap = api.DOM.wrap;
            top = parseInt(wrap[0].style.top);
            hide = top + wrap[0].offsetHeight;

            wrap.css('top', hide + 'px')
                .animate({ top: top + 'px' }, duration, function () {
                    opt.init && opt.init.call(api, here);
                });
        },
        close: function (here) {
            wrap.animate({ top: hide + 'px' }, duration, function () {
                opt.close && opt.close.call(this, here);
                aConfig.close = $.noop;
                api.close();
            });

            return false;
        }
    };

    for (var i in opt) {
        if (config[i] === undefined) config[i] = opt[i];
    };

    return artDialog(config);
};

/*
*  显示自定义提示框
*  @param	{Object} artDialog原始参数
*/
function showDialog(options) {
    var opts,
    defaults = {
        id: 'Art_Dialog' + (+new Date),
        title: '友情提示',
        fixed: true,
        left: '50%',
        top: '30%',
        lock: true,
        background: 'grey'
    };
    opts = $.extend({}, defaults, options);
    art.dialog.through(opts);
}

/*
*  显示成功提示框
*  @param	{String} 成功提示信息
*  @param	{String Function} 为String类型时，是成功提示后的重定向地址，
*  为Function类型时，是成功提示后的回调函数
*/
function successMsg(msg, optional) {
    var opts = {
        time: 1,
        icon: 'succeed',
        content: msg || ''
    };
    showDialog(opts);
    if (!util.isNull(optional)) {
        if (util.isString(optional)) {
            window.setTimeout(function () {
                art.dialog.open.origin.location.href = optional;
            }, opts.time * 1000);
        }
        else if (util.isFunction(optional)) {
            window.setTimeout(optional, opts.time * 1000);
        }
    }
}

/*
*  显示错误提示框
*  @param	{String} 错误提示信息
*  @param	{Function} 错误提示后的回调函数
*/
function errorMsg(msg, callback) {
    showDialog({
        icon: 'error',
        content: msg || '',
        button: [{
            name: '关闭',
            focus: true,
            callback: function () {
                if (!util.isNull(callback) && util.isFunction(callback)) {
                    callback();
                }
            }
        }]
    });
}

/*
*  显示信息提示框
*  @param	{String} 提示信息
*  @param	{Function} 信息提示后的回调函数
*/
function informationMsg(msg, callback) {
    showDialog({
        icon: 'warning',
        content: msg || '',
        button: [{
            name: '关闭',
            focus: true,
            callback: function () {
                if (!util.isNull(callback) && util.isFunction(callback)) {
                    callback();
                }
            }
        }]
    });
}

/*
*  右下角自动提示框
*  @param	{String} 提示信息
*/
function notice(msg) {
    art.dialog.notice({
        title: '消息提示',
        id: 'MessageCenter',
        width: 229, // 必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致artDialog收缩
        content: msg || '',
        time: 6
    });
}

/*
*  以对话框的方式打开新页面
*  @param	{Object} 包括id、url、title、width、height，
*  id为对话框Id（同一个页面中打开多个弹出页面时需要设置成唯一的）、url为页面地址、
*  title为对话框顶部显示的信息、width为对话框宽度、height为对话框的高度
*/
function openPage(opts) {
    if (util.isNull(opts.url) || util.isEmpty(opts.url)) {
        return false;
    }
    if (util.isNull(opts.id)) {
        opts.id = "Page";
    }
    if (util.isNull(opts.init)){
    	opts.init = function(){
    		$('.aui_close', art.dialog.top.parent.document).removeAttr('style');
    	}
    }
    opts.url = encodeURI(opts.url);
    art.dialog.open(opts.url, { id: opts.id, title: opts.title, width: opts.width, height: opts.height, lock: true, background: 'grey', init: opts.init, close: opts.close });
}

// 以弹出层的方式展示div
function openDiv(opts) {
    if (util.isNull(opts.id)) {
        opts.id = "Div";
    }
    art.dialog({
        id: opts.id,
        title: opts.title,
        content: document.getElementById(opts.divId),
        lock: true,
        background: 'grey',
        width: opts.width,
        height: opts.height
    });
}

/*
*  确认对话框
*  @param	{String} 确认信息
*  @param	{Function} 点击确定按钮后的回调函数
*  @param	{Function} 点击取消按钮后的回调函数
*/
function confirmDialog(msg, yes, no) {
    art.dialog.confirm(msg, yes || EF, no);
}

/*
*  prompt对话框
*  @param	{String} 确认信息
*  @param	{Function} 点击确定按钮后的回调函数
*  @param	{String} 文本框中显示的默认值
*/
function promptDialog(msg, yes, value) {
    art.dialog.prompt(msg, yes, value);
}

/*
*  关闭对话框
*/
function closeDialog() {
    art.dialog.close();
}

function closeDialogById(id) {
    art.dialog.list[id].close();
}

var EF = function () { };
var tipMsg = {
    ajaxErrorMsg: "系统错误，请联系管理员！"
}

/**
*  常用正则
**/
var reg = {
    formatReg: /(\{\d+\})/g,
    notEmpty: /\S+/,
    guid: /[\da-f]{8}-(?:[\da-f]{4}-){3}[\da-f]{12}/i,
    email: /.+@.+\..+/
}

/**
*  常用辅助方法
**/
var util = {
    isNull: function (obj) {
        return obj === null || typeof obj === 'undefined';
    },
    isEmpty: function (text) {
        return !(/\S+/.test(text));
    },
    getType: function (obj) {
        var typeStr = Object.prototype.toString.call(obj);
        return typeStr.substring(8, typeStr.length - 1);
    },
    isObject: function (obj) {
        return this.getType(obj) === jsType.Object;
    },
    isString: function (obj) {
        return this.getType(obj) === jsType.String;
    },
    isArray: function (obj) {
        return this.getType(obj) === jsType.Array;
    },
    isFunction: function (obj) {
        return this.getType(obj) === jsType.Function;
    },
    isBoolean: function (obj) {
        return this.getType(obj) === jsType.Boolean;
    },
    trim: function (text) {
        return text.replace(/^\s+|\s+$/g, '');
    },
    now: function () {
        return new Date().getTime();
    },
    toArray: function (args, index) {
        return Array.prototype.slice.call(args, index ? index : 0);
    },
    formatString: function (text) {
        if (this.isNull(text)) {
            return '';
        }
        var array = text.match(reg.formatReg),
        args = this.toArray(arguments, 1);
        if (this.isNull(args)) {
            return text;
        }
        for (var i = 0; i < array.length; i++) {
            if (!this.isNull(args[i])) {
                text = text.replace(array[i], args[i]);
            }
        }
        return text;
    },
    $g: function (elementId) {
        return document.getElementById(elementId);
    },
    substring: function (text, number, addedText) {
        if (this.isNull(text)) {
            return '';
        }
        if (this.isNull(number) || text.length <= number) {
            return text;
        }
        if(this.isNull(addedText)){
        	addedText = '...';
        }
        
        return text.substr(0, number) + addedText;
    },
    /*
    *  验证Input值是否为空
    *  @param   {String Object}   Id选择器(包含#)或者jquery对象或者Dom对象
    *  @param   {String}   错误提示信息容器Id选择器(包含#)
    */
    checkItem: function (item, errorItem) {
        var target = $(item),
        flag = true;
        if (util.isEmpty(target.val())) {
            target.addClass("input-validation-error");
            if (!util.isNull(errorItem)) {
                $(errorItem).show();
            }
            flag = false;
        }
        else {
            target.removeClass("input-validation-error");
            if (!util.isNull(errorItem)) {
                $(errorItem).hide();
            }
        }
        return flag;
    },
    /*
    *  通过一个元素控制另一个元素是否只读
    *  @param   {String Object}   当前对象Id选择器(包含#)或者jquery对象
    *  @param	{String Object}   关联对象Id选择器(包含#)或者jquery对象
    *  @param	{String}   事件类型名称，如：'click'
    *  @param	{Function}   回调函数
    */
    setItem: function (selfItem, targetItem, eventType, func) {
        var self = $(selfItem),
        target = $(targetItem);
        self.bind(eventType, function () {
            if (self.attr('checked') === 'checked') {
                target.removeAttr('readonly');
            }
            else {
                target.attr('readonly', 'readonly').val('');
            }
            if (!util.isNull(func)) {
                func();
            }
        });
    },
    /*
    *  封装for循环操作
    *  @param   {Array}   需要遍历的数组
    *  @param   {Function}   应用于数组中每个元素的操作
    */
    forEach: function (array, action) {
        if (array === null || array.length === 0) { return; }
        for (var i = 0; i < array.length; i++) {
            action(array[i]);
        }
    },
    reduce: function () {

    },
    map: function () {

    },
    /*
    *  转义字符串中的特使字符
    *  @param	{String}	需要转义的字符串
    */
    escape: function (val) {
        return val.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
    },
    /*
    *  反转义字符串中的特殊字符
    *  @param   {String}   需要反转义的字符串
    */
    unescape: function (val) {
        return val.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&quot;/g, '"').replace(/&amp;/g, '&');
    },
    /*
     *  重命名文件路径
     *  @param   {String}   需要重命名的文件路径
     *  @param   {String}   需要添加的字符串
     */
    renameUrl: function(value, addText){
    	var startIndex = value.lastIndexOf('.'),
    	name = value.substring(0, startIndex),
    	ext = value.substring(startIndex);
    	
    	return name + addText + ext;
    },
    /*
     *  过滤字符串中的html标签以及空格
     *  @param   {String}   需要过滤的字符串
     */
    filterHtml: function(text){
    	if(this.isNull(text) || this.isEmpty(text)){
    		return '';
    	}
    	text = text.replace(/<\/?[^>]+>/g,'');
    	text = text.replace(/[\r\n\s+]/g,'');
    	return text;
    },
    /*
     *  在指定字符串的前面加指定数目的0
     *  @param   {String}   需要处理的字符串
     *  @param   {Number}   0的数量
     */
    paddingZero: function(text, count){
    	if(this.isNull(text) || this.isNull(count)){
    		return null;
    	}
    	
		var length = count - String(text).length; 
		if(length > 0){
			var array = [];
			for(var i=0; i < length; i++){
				array.push('0');
			}
			return array.join('') + text;
		}
		else{
			return text;
		}
    },
    /*
     *  把指定字符串中的回车、换行以及制表符替换成十六进制形式
     *  @param   {String}   需要处理的字符串
     */
    escapeChar:function(text){
    	if(this.isNull(text) || this.isEmpty(text)){
    		return text;
    	}
    	text = text.replace(/\r/g,'\u000D');
    	text = text.replace(/\n/g,'\u000A');
    	text = text.replace(/\t/g,'\u0009');
    	return text;
    },
    /*
     *  把指定的数值字符串转换成保留两位小数的形式
     *  @param   {String}   需要处理的字符串
     */
    formatDecimal: function(value){
    	if(this.isNull(value)){
    		return '';
    	}
    	if(isNaN(value)){
    		return value;
    	}
    	return parseFloat(value).toFixed(2); 
    },
    /*
     *  从url中获取给定参数的值
     *  @param   {String}  url
     *  @param   {String}  参数名称
     */
    getQueryParam: function(url, name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = url.match(reg);
        if (r != null) {
        	return unescape(r[2]); 
        }
        else{
        	return null;
        }
    },
    /*
     *  往url中添加参数
     *  @param   {String}  url
     *  @param   {String}  参数，如：name=david
     */
    appendUrlParam: function(url, keyValue){
    	if(url.indexOf('?') > -1){
    		url += '&' + keyValue;
    	}
    	else {
    		url += '?' + keyValue;
    	} 
    	return url;
    },
    /*
     *  时钟
     *  @param   {String}  id 包含'#'
     */
    initTime: function(id){    	
    	setInterval(function(){
    		var date = new Date(),
        	hours = util.paddingZero(date.getHours(), 2),
        	minutes = util.paddingZero(date.getMinutes(), 2);
    		$(id).html(hours + ":" + minutes);
    	}, 1000);
    },
    /*
     *  获取提示时间，如5秒种之前，5分种之前
     *  @param   {Date}  date 日期对象
     */
    getTimeTip: function(date){
    	var now = new Date(),
    	timeNow = now.getTime(),
    	time = date.getTime(),
    	timeSpan = timeNow - time,
    	day = date.getDate(),
    	dayNow = now.getDate(),
    	daySpan = dayNow - day,
    	tip = '';
    	
    	if(daySpan < 2){ 
    		if(timeSpan < 0 ){
    			tip = '刚刚';
    		}
    		else if(timeSpan < (1 * 60 * 1000)){
        		tip = Math.floor(timeSpan / 1000) + '秒前';
        	}
        	else if(timeSpan < (1 * 60 * 60 * 1000)){
        		tip = Math.floor(timeSpan / (60 * 1000)) + '分钟前';
        	}
        	else if(timeSpan < (1 * 60 * 60 * 60 * 1000)){
        		tip = Math.floor(timeSpan / (60 * 60 * 1000)) + '小时前';
        	} 
        	else{
        		tip = '昨天';
        	}
    	}
    	else if(daySpan < 3){
    		tip = '前天';
    	}
    	else if(daySpan > 3 && daySpan < 7){
    		tip = daySpan + '天前';
    	} 
    	else {
    		tip = dateUtil.format(date, 'yyyy-MM-dd');
    	}
    	
    	return tip;
    },
    /*
     *  简单的ajax请求
     *  @param   {msg}   需要的确认信息
     *  @param   {obj}   需要的链接地址对象
     */
     easyAjaxRequest: function (msg, obj, smsg) {
         function ajaxStart(obj) {
             if (util.isNull(obj)) { return; }
             $.ajax({
                 type: 'POST',
                 dataType: 'json',
                 url: $(obj).attr("href"),
                 beforeSend: function () {
                     $.ajaxTip.show();
                 },
                 success: function (data) {
                     if (data.flag) {
                    	 var sumsg = data.msg;
                    	 if(!util.isNull(smsg)) {
                    		 sumsg = smsg;
                		 }
                         successMsg(sumsg, function () {
                             $("#spec_form").submit();
                         });
                     } else {
                         errorMsg(data.msg);
                     }
                 },
                 error: function () {
                     errorMsg('请求失败！');
                 },
                 complete: function () {
                     $.ajaxTip.remove();
                 }
             });
         }
         if (util.isNull(msg)) {
             ajaxStart(obj);
         } else {
             art.dialog.confirm("您确定要" + msg + "吗?", function () {
                 ajaxStart(obj);
             });
         }
         //地址跳链需要加在调用里，这里不管用
     },
     /*
      *  简单的ajax请求
      *  @param   {msg}   需要的确认信息
      *  @param   {obj}   需要的链接地址对象
      */
      easyAjaxRequest_all: function (msg, obj, smsg,form_id) {
          function ajaxStart(obj) {
              if (util.isNull(obj)) { return; }
              $.ajax({
                  type: 'POST',
                  dataType: 'json',
                  url: $(obj).attr("href"),
                  beforeSend: function () {
                      $.ajaxTip.show();
                  },
                  success: function (data) {
                      if (data.flag) {
                     	 var sumsg = data.msg;
                     	 if(!util.isNull(smsg)) {
                     		 sumsg = smsg;
                 		 }
                          successMsg(sumsg, function () {
                              $("#"+(form_id==""?"spec_form":form_id)).submit();
                          });
                      } else {
                          errorMsg(data.msg);
                      }
                  },
                  error: function () {
                      errorMsg('请求失败！');
                  },
                  complete: function () {
                      $.ajaxTip.remove();
                  }
              });
          }
          if (util.isNull(msg)) {
              ajaxStart(obj);
          } else {
              art.dialog.confirm(msg, function () {
                  ajaxStart(obj);
              });
          }
          //地址跳链需要加在调用里，这里不管用
      }, 
     /*
      *  简单的ajax请求
      *  @param   {msg}   需要的确认信息
      *  @param   {obj}   需要的链接地址对象
      */
      easyAjaxBatchRequest: function (msg, obj, fkey, fmsg, smsg) {
    	  var idArray = [], $this = $(obj);
    	  var flag = false;

    	  $("[name='id']:checked").each(function () {
    	      idArray.push($(this).val());
    	      if (!util.isNull(fkey) && $(this).attr("key") == fkey) {
    	          flag = true;
    	      }
    	  });
    	  if (idArray.length === 0) {
    	      informationMsg('请选择要' + msg + '的信息！');
    	  }
    	  else {
    	      if (flag) {
    	          informationMsg(fmsg);
    	      } else {
    	          art.dialog.confirm('您确定要将选中的都' + msg + '？', function () {
    	              ajaxBatchStart(idArray.join(','), $this);
    	          });
    	      }
    	  }

    	  function ajaxBatchStart(selectedIds, linkClicked) {
    	      if (util.isNull(linkClicked)) { return; }
    	      $.ajax({
    	          type: 'POST',
    	          dataType: 'json',
    	          url: linkClicked.attr("href"),
    	          data: { ids: selectedIds },
    	          beforeSend: function () {
    	              $.ajaxTip.show();
    	          },
    	          success: function (data) {
    	              if (data.flag) {
						 var sumsg = data.msg;
						 if(!util.isNull(smsg)) {
							 sumsg = smsg;
						 }
    	                  successMsg(sumsg, function () {
    	                      $("#spec_form").submit();
    	                  });
    	              } else {
    	                  errorMsg(data.msg);
    	              }
    	          },
    	          error: function () {
    	              errorMsg('请求失败！');
    	          },
    	          complete: function () {
    	              $.ajaxTip.remove();
    	          }
    	      });
    	  }
      }, 
      /*
       *  简单的ajax请求（不提示提示信息，直接过滤）
       *  @param   {msg}   需要的确认信息
       *  @param   {obj}   需要的链接地址对象
       */
       easyAjaxBatchRequestFiter: function (msg, obj, filter_key, smsg) {
     	  var idArray = [], $this = $(obj);
     	  var flag = false;

     	  $("[name='id']:checked").each(function () {
     	      if (!util.isNull(filter_key) && filter_key.indexOf($(this).attr("key"))>=0 ) {
         	      idArray.push($(this).val());
     	      }
     	  });
     	  if (idArray.length === 0) {
     	      informationMsg('请选择要' + msg + '的信息！');
     	  }
     	  else {
     		 art.dialog.confirm('您确定要将选中的都' + msg + '？', function () {
	              ajaxBatchStart(idArray.join(','), $this);
	          });
     	  }

     	  function ajaxBatchStart(selectedIds, linkClicked) {
     	      if (util.isNull(linkClicked)) { return; }
     	      $.ajax({
     	          type: 'POST',
     	          dataType: 'json',
     	          url: linkClicked.attr("href"),
     	          data: { ids: selectedIds },
     	          beforeSend: function () {
     	              $.ajaxTip.show();
     	          },
     	          success: function (data) {
     	              if (data.flag) {
     	            	 var sumsg=data.msg;
                      	 if(!util.isNull(smsg)) {
                      		 sumsg = smsg;
                  		 }
     	                  successMsg(sumsg, function () {
     	                      $("#spec_form").submit();
     	                  });
     	              } else {
     	                  errorMsg(data.msg);
     	              }
     	          },
     	          error: function () {
     	              errorMsg('请求失败！');
     	          },
     	          complete: function () {
     	              $.ajaxTip.remove();
     	          }
     	      });
     	  }
       }, 
       /*
        *  简单的ajax请求（不提示提示信息，直接过滤）
        *  @param   {msg}   需要的确认信息
        *  @param   {obj}   需要的链接地址对象
        */
        easyAjaxBatchRequestFiterMore: function (msg, obj, filter_key, smsg) {
      	  var idArray = [],idArray1 = [], $this = $(obj);
      	  var flag = false;

      	  $("[name='id']:checked").each(function () {
      	      if (!util.isNull(filter_key) && filter_key.indexOf($(this).attr("key"))>=0) {
          	      idArray.push($(this).val());
          	      idArray1.push($(this).attr("key_id"));
      	      }
      	  });
      	  if (idArray.length === 0) {
      	      informationMsg('请选择要' + msg + '的信息！');
      	  }
      	  else {
      		 art.dialog.confirm('您确定要将选中的都' + msg + '？', function () {
 	              ajaxBatchStart(idArray.join(','),idArray1.join(','), $this);
 	          });
      	  }

      	  function ajaxBatchStart(selectedIds,selectedIds1, linkClicked) {
      	      if (util.isNull(linkClicked)) { return; }
      	      $.ajax({
      	          type: 'POST',
      	          dataType: 'json',
      	          url: linkClicked.attr("href"),
      	          data: { ids: selectedIds,ids1:selectedIds1 },
      	          beforeSend: function () {
      	              $.ajaxTip.show();
      	          },
      	          success: function (data) {
      	              if (data.flag) {
      	            	 var sumsg=data.msg;
                       	 if(!util.isNull(smsg)) {
                       		 sumsg = smsg;
                   		 }
      	                  successMsg(sumsg, function () {
      	                      $("#spec_form").submit();
      	                  });
      	              } else {
      	                  errorMsg(data.msg);
      	              }
      	          },
      	          error: function () {
      	              errorMsg('请求失败！');
      	          },
      	          complete: function () {
      	              $.ajaxTip.remove();
      	          }
      	      });
      	  }
        }, 
        /*
         *  简单的ajax请求（不提示提示信息，直接过滤,其他字段重复报错）
         *  @param   {msg}   需要的确认信息
         *  @param   {obj}   需要的链接地址对象
         */
         easyAjaxBatchRequestFiterMoreRepeat: function (msg, obj, filter_key, smsg,rmsg) {
       	  var idArray = [],idArray1 = [], $this = $(obj);
       	  var flag = false;

       	  $("[name='id']:checked").each(function () {
       	      if (!util.isNull(filter_key) && filter_key.indexOf($(this).attr("key"))>=0) {
           	      idArray.push($(this).val());
           	   if(!util.isNull(idArray1) && arrayUtil.has(idArray1,$(this).attr("key_id")))
	           	{
	           		flag=true;
	           		return false;
	           	}
           	      idArray1.push($(this).attr("key_id"));
       	      }
       	  });
       	  if(flag)
       	  {
       		informationMsg(rmsg);
       		return true;
   		  }
       	  if (idArray.length === 0) {
       	      informationMsg('请选择要' + msg + '的信息！');
       	  }
       	  else {
       		 art.dialog.confirm('您确定要将选中的都' + msg + '？', function () {
  	              ajaxBatchStart(idArray.join(','),idArray1.join(','), $this);
  	          });
       	  }

       	  function ajaxBatchStart(selectedIds,selectedIds1, linkClicked) {
       	      if (util.isNull(linkClicked)) { return; }
       	      $.ajax({
       	          type: 'POST',
       	          dataType: 'json',
       	          url: linkClicked.attr("href"),
       	          data: { ids: selectedIds,ids1:selectedIds1 },
       	          beforeSend: function () {
       	              $.ajaxTip.show();
       	          },
       	          success: function (data) {
       	              if (data.flag) {
       	            	 var sumsg=data.msg;
                        	 if(!util.isNull(smsg)) {
                        		 sumsg = smsg;
                    		 }
       	                  successMsg(sumsg, function () {
       	                      $("#spec_form").submit();
       	                  });
       	              } else {
       	                  errorMsg(data.msg);
       	              }
       	          },
       	          error: function () {
       	              errorMsg('请求失败！');
       	          },
       	          complete: function () {
       	              $.ajaxTip.remove();
       	          }
       	      });
       	  }
         },
       ajax: function(opts){
    	   opts.beforeSend = function(){
    		   showTip();
    	   }; 
    	   opts.complete = function(){
    		   hideTip();
    	   }
    	   $.ajax(opts);
       }
}

/**
*  日期常用辅助类
**/
var dateUtil = {
	format: function(dateObj, fmt){
	 var o = {
	        "M+": dateObj.getMonth() + 1, //月份 
	        "d+": dateObj.getDate(), //日 
	        "H+": dateObj.getHours(), //小时 
	        "m+": dateObj.getMinutes(), //分 
	        "s+": dateObj.getSeconds(), //秒 
	        "q+": Math.floor((dateObj.getMonth() + 3) / 3), //季度 
	        "S": dateObj.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) {
	    	fmt = fmt.replace(RegExp.$1, (dateObj.getFullYear() + '').substr(4 - RegExp.$1.length));
	    }
	    for (var k in o){
	    	if (new RegExp("(" + k + ")").test(fmt)) {
	    		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    	}
	    }
	    return fmt;
	}	
}

/**
*  数组辅助方法
**/
var arrayUtil = {
    has: function (array, target) {
        var flag = false;
        this.each(array, function (index, item) {
            if (target == item) {
                flag = true;
                return true;
            }
        });
        return flag;
    },
    hasByField: function (array, fieldName, fieldValue) {
        var flag = false;
        this.each(array, function (index, item) {
            if (item[fieldName] == fieldValue) {
                flag = true;
                return true;
            }
        });
        return flag;
    },
    get: function (array, target) {
        var flag = -1;
        this.each(array, function (index, item) {
            if (target == item) {
                flag = index;
                return true;
            }
        });
        return flag;
    },
    removeByIndex: function (array, index_target) {
        array.splice(index_target, 1);
        return array;
    },
    removeByField: function (array, fieldName, fieldValue) {
    	this.each(array, function (index, item) {
            if (item[fieldName] == fieldValue) {
                array.splice(index, 1);
                return true;
            }
        });
        return array;
    },
    remove: function (array, target) { 
        this.each(array, function (index, item) {
            if (item == target) {
                array.splice(index, 1);
                return true;
            }
        });
        return array;
    },
    each: function (array, callback) {
        if (!util.isArray(array)) { return; }
        var flag = false;
        for (var i = 0; i < array.length; i++) {
            flag = callback(i, array[i]);
            if (flag) { break; }
        }
    }
};

/**
*  事件辅助方法
*/
var eventUtil = {
	//回车触发的事件
	enter: function(e, func){
        // 响应回车
        var key = window.event ? e.keyCode : e.which;
        if (key == 13 && func) {
        	func();
        }
	}
}

/*
*  js基本类型
*/
var jsType = {
    Undefined: 'Undefined',
    Null: 'Null',
    Object: 'Object',
    String: 'String',
    Array: 'Array',
    Function: 'Function',
    Date: 'Date',
    Boolean: 'Boolean',
    Number: 'Number',
    RegExp: 'RegExp'
}

/*
*  StringBuilder类
*/
function StringBuilder() {
    this.buffer = [];
}

StringBuilder.prototype = {
    append: function (text) {
        if (util.isNull(text)) {
            return;
        }
        this.buffer.push(text);
    },
    appendLine: function (text) {
        if (util.isNull(text)) {
            return;
        }
        this.buffer.push(text, '\n');
    },
    appendFormat: function (text) {
        if (util.isNull(text)) {
            return;
        }
        var args = [text];
        args = args.concat(util.toArray(arguments, 1));
        this.buffer.push(util.formatString.apply(util, args));
    },
    
    toString: function (seporator) {
        if (this.buffer.length === 0) {
            return '';
        }
        return this.buffer.join(util.isNull(seporator) ? '' : seporator);
    },
    getBuffer: function () {
        return this.buffer;
    },
    getLength: function () {
        return this.buffer.length;
    }
};

/*
*  浏览器类型
*/
var browser = (function () {
    var userAgent = navigator.userAgent.toLowerCase();
    
    return {
        isIE: $.browser.msie,
        isIE6: this.isIE && $.browser.version === '6.0',
        isFireFox: userAgent.indexOf('firefox') !== -1,
        isChrome: userAgent.indexOf('chrome') !== -1
    };
})();

/*
*  ajax遮罩层提示
*/
$.ajaxTip = {
    show: function (options) {
        var defaults = {
            width: 280,
            height: 32,
            tipMsg: '正在处理，请稍候...',
            loadingImg: '/ybb/static/img/loading.gif'
        },
        opts = $.extend({}, defaults, options || {}),
        ajaxTip = $('#AjaxTip'),
        layer = $('#LayerCover'),
        height = !browser.isIE6 ? $(window).height() : $(document).height(),
        width = $(window).width(),
        top = height / 3,
        left = (width - opts.width) / 2,
        tipHtml = ['<div id="LayerCover" class="layer_cover" style="height:',
                    height,
                    'px"><iframe frameborder="0" class="zindex_iframe" style="width:0px;height:0px;"></iframe></div><div id="AjaxTip" class="layer_container" style="width:',
                    opts.width,
                    'px;height:',
                    opts.height,
                    'px;top:',
                    top,
                    'px;left:',
                    left,
                    'px;line-height:32px;"><div style="position:relative">',
                    opts.tipMsg,
                    '<img style="position:absolute;top:2px;_top:-5px;right:30px" src="',
                    opts.loadingImg,
                    '" /></div></div>'].join('');
        if (typeof ajaxTip !== 'undefined') {
            ajaxTip.remove();
        }
        if (typeof layer !== 'undefined') {
            layer.remove();
        }
        $('body').append(tipHtml);
        var ajaxTip = $('#AjaxTip'),
        win = $(window);
        if (!util.isNull(ajaxTip) && ajaxTip.length > 0) {
            win.resize(function () {
                ajaxTip.css({ 'top': win.height() / 3, 'left': (win.width() - 280) / 2 });
            });
        }
    },
    remove: function () {
        var ajaxTip = $('#AjaxTip'),
            layer = $('#LayerCover');
        if (!util.isNull(ajaxTip) && ajaxTip.length > 0) {
            ajaxTip.remove();
        }
        if (!util.isNull(layer) && layer.length > 0) {
            layer.remove();
        }
    }
};

function showTip() {
    $.ajaxTip.show();
}

function hideTip() {
    $.ajaxTip.remove();
}
  
var upDown = (function () {
    function init() {
        // 搜索条件伸缩
        var $flex = $(".flexible .icon"),
        $user_fun = $(".data_cont_wrap .data_cont_wrap_list"); 
        $flex.text("展开");
        $flex.toggle(function () {
            $(this).text("收起");
            var flex_text = $(this).text();
            $(this).addClass("ico_tdown");
            $user_fun.css({ height: "auto" });
            $(this).attr({
                title: flex_text
            });
        }, function () {
            $(this).text("展开");
            var flex_text = $(this).text();
            $(this).removeClass("ico_tdown"); 
            $(this).attr({
                title: flex_text
            });
        })
    }

    return { init: init };
})();


var pop =(function(){
	function init(){
		$('#btn_pclose').click(closeDialog)
	}
	
	return {init: init };
})();

//Tab
var tab = (function(){
	function init(){
		$(".Tab_Panels li").click(function(){
			var _this = $(this), 
			$tab_li = $(".Tab_Panels li"),
			$tab_num = $tab_li.index(_this),
			$tab_cont = $(".TabbedPanelsContent");
			_this.addClass("cur").siblings().removeClass("cur");
			$tab_cont.eq($tab_num).show().siblings().hide();
		});
	}
	
	return {init: init };
})();

// 美化checkbox初始化
var bcheckbox = (function () {
    function init() {
        $('.inp_analog').each(function (index, item) {
        	var target = $(item);
        	target.live('click', function(){
	            var checked = target.attr('checked');
	            if (checked === 'checked') {
	                target.next(".icon").addClass("ico-checkbox-checked");
	            }
	            else {
	                target.next(".icon").removeClass("ico-checkbox-checked");
	            }
        	});
        });
        
        //图片出错时替换
        $("img").error(function () { 

			$(this).attr("src", "static/images/emptyImage.jpg"); 

		});
    }

    return { init: init };
})();

var flash_title = {
    doc: document,
    timer: null,
    is_flash: false,
    o_title: '',
    msg: '有新消息',
    message: null,
    flash: function (msg) {

        if (this.is_flash) {
            this.clear(); //先停止
        } else {
            this.o_title = this.doc.title; //保存原来的title    
        }
        this.is_flash = true;
        if (typeof (msg) == 'undefined') {
            msg = this.msg;
        }
        this.message = [msg, this.getSpace(msg)];
        var th = this;
        this.timer = setInterval(function () { th._flash(msg); }, 800);
    },
    _flash: function (msg) {
        this.index = (!this.index) ? 1 : 0;
        this.doc.title = '【' + this.message[this.index] + '】';
    },
    clear: function () {
        clearInterval(this.timer);
        if (this.is_flash)// 如果正在闪
            this.doc.title = this.o_title; //将title复原
        this.is_flash = false;
    },
    getSpace: function (msg) {
        var n = msg.length;
        var s = '';
        var num = msg.match(/\w/g);
        var n2 = (num != null) ? num.length : 0; //半角字符的个数
        n = n - n2; //全角字符个数
        var t = parseInt(n2 / 2);
        if (t > 0) {//两个半角替换为一个全角
            n = n + t;
        }
        s = (n2 % 2) ? ' ' : ''; //如果半角字符个数为奇数    
        while (n > 0) {
            s = s + '　'; //全角空格
            n--;
        };
        return s;
    }
};

window.onerror =  function(){
	if(browser.isIE && art.dialog){
		$('.aui_close:last', art.dialog.top.parent.document).attr('style', 'visibility:visible;');
	}
}

//table列表
var tlist = (function(){
	function init(){
		
	}
	
	// 上下移动
	function move(self, tr, tbody, type){
		var trs = $(tbody).find(tr);
		if(trs.legth < 2){return;}
		var trObj = $(self).parents(tr); 
		if(type === 'up'){
			var prevObj = trObj.prev(tr);
			if(prevObj){
				trObj.insertBefore(prevObj);
			}
		 }
		 else if(type === 'down'){
			var nextObj = trObj.next(tr);
			if(nextObj){
				trObj.insertAfter(nextObj);
			}
		 }
		 
		 reset(tbody)
	}
		
	// 删除行
	function remove(self, tr, tbody, colspan){
		if(!util.isNull(tr)){
			$(self).parents(tr).remove();
			var trs = $(tbody).find('tr');
			if(trs.length > 0){
				reset(tbody);
			}
			else{
				$(tbody).append('<tr class="tr_empty"><td colspan="'+ colspan +'" class="t_c"><span class="no-records">暂无数据</span></td></tr>');
			}
		}
	}
	
	// 重新设置序号
	function reset(tbody){
		var trs = $(tbody).find('tr');
		trs.each(function(index, item){
			$(item).find('.serial').text(index + 1);
			$(item).find('.operate').find('input:hidden').each(function(subIndex, subItem){
				var name = $(subItem).attr('name');
				$(subItem).attr('name', name.replace(/items\[\d*\]/gi, 'items[' + index + ']'));
			});
			$(item).find('.set_img').attr('rel', index+1);
			$(item).attr('id', 'tr_'+ (index + 1));
		});		
	}
	
	return { move: move, remove: remove };
})();

// Dom ready 事件
$(function () {
    upDown.init();
    pop.init();
    tab.init();
    bcheckbox.init();
}); 
