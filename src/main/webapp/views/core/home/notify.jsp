<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xz.base.utils.WebUtil"%>
<%
	String baseUrl = WebUtil.getBasePath(request);
	request.setAttribute("baseUrl", baseUrl);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<base href="<%=WebUtil.getBasePath(request)%>" />
		<script type="text/javascript" src="static/js/pushlet/ajax-pushlet-client.js"></script>  
	</head>
	<body>
		<script type="text/javascript">
			var uid = parent.$('#user_id').val();
			PL.parameters.push({'name': 'user_id', 'value': uid});
			
			//对pushlet的初始化，触发web.xml中的servlet。
	        PL._init(); 
	        //这里的监听的主题，必须在sources.properties中配置的对象中声明这个主题。
	        //sources.properties配置着事件源（EventSources），在服务器启动时会自动激活。
	        //可以通过服务器的启动记录查看得到。可以将这个文件放到WEB-INF目录下面或者classess目录下面都可以。
	        
	        PL.joinListen('/notify/message');
	        
	        //事件接收 
	        function onData(event) {
	        	function isEmpty(text){
	        		return !/\S+/.test(text);
	        	}
	        	
	        	var userCount = event.get('userCount'), 
	        	data = event.get('data'); 
	        	if(!isEmpty(userCount)){
	        		if(Number(userCount) === 0){
	        			parent.location.href = '${baseUrl}tlogout.do';
	        			return;
	        		}
	        		
	        		parent.$('#btn_online_user').attr('title', userCount + '人在线');
	        		parent.$('#user_count').text(userCount);
	        		if(parent.onlineUser.isShow()){
	        			parent.onlineUser.getData();
	        		}
	        	}
	        	
	        	if(isEmpty(data)){ 
	        		return;	
	        	} 
	        	
	        	// 把后台传过来的数据组装成json对象
	        	var array = data.split('|'),
	        	resultObj = {},
	        	type = null,
	        	arrayList = null,
	        	itemObj = null,
	        	validCount = 0;
	        	if(array.length > 0){
	        		for(var i = 0; i < array.length; i++){ 
	        			itemArray = array[i].split('^');
	        			if(itemArray.length > 0){
	        				arrayList = [];
		        			for(var j = 0; j < itemArray.length; j++){
		        				var elementArray = itemArray[j].split('#');
		        				if(isEmpty(elementArray[0]) || elementArray[6] != uid){
		        					continue;
		        				}
		        				type = elementArray[0];
		        				itemObj = {
		        					id: elementArray[1],
		        					url: elementArray[2],
		        					create_time: elementArray[3],
		        					sender_real_name: decodeURIComponent(elementArray[4]),
		        					content: decodeURIComponent(elementArray[5])
		        				};
		        				arrayList.push(itemObj);
		        				validCount++;
		        			}
		        			if(arrayList.length > 0){
		        				resultObj[type] = arrayList;
		        			}
	        			}
	        		}
	        		if(validCount > 0){ 
	        			// 标题闪烁
		        		parent.flashTitle.init();
		        		parent.notify.process(resultObj);
	        		}
	        	} 
	        }
	        
		</script>
	</body>
</html>