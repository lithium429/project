package com.xz.project.core.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;
import com.xz.base.domain.BaseEntity;

public class Menu extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1L;

	/**
	 * id
	 */
	private java.lang.Integer id;

	/**
	 * 名称
	 */
	private java.lang.String name;

	/**
	 * 路径
	 */
	private java.lang.String url;

	/**
	 * 父级id
	 */
	private java.lang.Integer parent_id;

	/**
	 * 是否叶子节点
	 */
	private java.lang.Boolean is_leaf;

	/**
	 * 层级
	 */
	private java.lang.Integer layer;

	/**
	 * 排序
	 */
	private java.lang.Integer sort;

	/**
	 * 子集id集合
	 */
	private java.lang.String child_ids;

	/**
	 * 是否启用
	 */
	private java.lang.Boolean is_enable;

	/**
	 * 图标类
	 */
	private java.lang.String icon_class;

	/**
	 * create_time
	 */
	private java.util.Date create_time;

	private List<Menu> menus;

	private List<Action> actions;

	public Menu() {
	}

	public Menu(String name, String url, Integer parent_id, Boolean is_leaf, Integer layer, Integer sort, String child_ids, Boolean is_enable, String icon_class, Date create_time) {
		this.name = name;
		this.url = url;
		this.parent_id = parent_id;
		this.is_leaf = is_leaf;
		this.layer = layer;
		this.sort = sort;
		this.child_ids = child_ids;
		this.is_enable = is_enable;
		this.icon_class = icon_class;
		this.create_time = create_time;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public java.lang.String getUrl() {
		return this.url;
	}

	public void setParent_id(java.lang.Integer parent_id) {
		this.parent_id = parent_id;
	}

	public java.lang.Integer getParent_id() {
		return this.parent_id;
	}

	public void setIs_leaf(java.lang.Boolean is_leaf) {
		this.is_leaf = is_leaf;
	}

	public java.lang.Boolean getIs_leaf() {
		return this.is_leaf;
	}

	public void setLayer(java.lang.Integer layer) {
		this.layer = layer;
	}

	public java.lang.Integer getLayer() {
		return this.layer;
	}

	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}

	public java.lang.Integer getSort() {
		return this.sort;
	}

	public void setChild_ids(java.lang.String child_ids) {
		this.child_ids = child_ids;
	}

	public java.lang.String getChild_ids() {
		return this.child_ids;
	}

	public void setIs_enable(java.lang.Boolean is_enable) {
		this.is_enable = is_enable;
	}

	public java.lang.Boolean getIs_enable() {
		return this.is_enable;
	}

	public java.lang.String getIcon_class() {
		return icon_class;
	}

	public void setIcon_class(java.lang.String icon_class) {
		this.icon_class = icon_class;
	}

	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}

	public java.util.Date getCreate_time() {
		return this.create_time;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Menu> getMenus() {
		return this.menus;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public List<Action> getActions() {
		return this.actions;
	}

	/**
	 * @Description 根据layer进行分组
	 * @param menuList
	 * @return Map<Integer,List<Menu>>
	 */
	public Map<Integer, List<Menu>> groupByLayer(List<RoleMenu> menuList) {
		Map<Integer, List<Menu>> map = new HashMap<Integer, List<Menu>>();
		List<Menu> menus = null;
		Integer key = null;
		for (RoleMenu item : menuList) {
			if (item == null) {
				continue;
			}
			key = item.getMenu().getLayer();
			if (map.containsKey(key)) {
				menus = map.get(key);
				menus.add(item.getMenu());
			} else {
				menus = new ArrayList<Menu>();
				menus.add(item.getMenu());
				map.put(key, menus);
			}
		}
		return map;
	}

}