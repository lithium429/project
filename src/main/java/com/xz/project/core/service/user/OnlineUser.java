/**   
 * @Title: OnlineUser.java 
 * @Package: com.xz.oa.core.service.user 
 * @Description: 在线用户
 * @author: davidwan
 * @date: 2014-8-27 下午4:06:45 
 * @version: V1.0   
 */
package com.xz.project.core.service.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@SuppressWarnings("unchecked")
public class OnlineUser {

	public static final String ONLINE_USER_MAP = "online_user_map";

	private ServletContext context;

	private final Object lockObj = new Object();

	private OnlineUser() {

	}

	/**
	 * 通过内部类的静态字段保证实例只会被初始化一次，并且线程安全
	 */
	private static class OnlineUserHolder {
		private static OnlineUser instance = new OnlineUser();
	}

	/**
	 * @Description 获取在线用户单实例
	 * @return OnlineUser
	 */
	public static OnlineUser getInstance() {
		return OnlineUserHolder.instance;
	}

	/**
	 * @Description 初始化
	 * @param context
	 *            void
	 */
	public void init(ServletContext context) {
		this.context = context;
		Map<Integer, List<String>> idList = new HashMap<Integer, List<String>>();
		this.context.setAttribute(ONLINE_USER_MAP, idList);
	}

	/**
	 * @Description 登录时，添加当前登录用户
	 * @param userId
	 * @param session
	 *            void
	 */
	public void add(Integer userId, HttpSession session) {
		synchronized (lockObj) {
			Map<Integer, List<String>> map = (Map<Integer, List<String>>) this.context.getAttribute(ONLINE_USER_MAP);
			List<String> sidList = null;
			if (!map.containsKey(userId)) {
				sidList = new ArrayList<String>();
				sidList.add(session.getId());
				map.put(userId, sidList);
			} else {
				sidList = map.get(userId);
				if(sidList.contains(session.getId())){
					sidList.add(session.getId());
				}
			}
			// 保存当前用户id到session中
			session.setAttribute(session.getId(), userId);
		}
	}

	/**
	 * @Description 退出时，移除当前登录用户
	 * @param userId
	 * @param session
	 *            void
	 */
	public void remove(Integer userId, HttpSession session) {
		synchronized (lockObj) {
			Map<Integer, List<String>> map = (Map<Integer, List<String>>) this.context.getAttribute(ONLINE_USER_MAP);
			if (map.containsKey(userId)) {
				List<String> sidList = map.get(userId);
				if (sidList.contains(session.getId())) {
					sidList.remove(session.getId());
				}
				if (sidList.isEmpty()) {
					map.remove(userId);
				}
			}
		}
	}

	/**
	 * @Description 获取在线用户数量
	 * @return Integer
	 */
	public Integer gainUserCount() {
		Map<Integer, List<String>> map = (Map<Integer, List<String>>) this.context.getAttribute(ONLINE_USER_MAP);
		return map.keySet().size();
	}

	/**
	 * @Description 获取在线用户Id列表
	 * @return List<Integer>
	 */
	public Set<Integer> gainUserIdList() {
		Map<Integer, List<String>> map = (Map<Integer, List<String>>) this.context.getAttribute(ONLINE_USER_MAP);
		return (Set<Integer>) map.keySet();
	}
}
