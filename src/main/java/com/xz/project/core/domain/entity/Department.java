package com.xz.project.core.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xz.base.domain.BaseEntity;
import com.xz.base.model.ZTreeNode;

public class Department extends BaseEntity implements Serializable {

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
	 * 首字母组合
	 */
	private java.lang.String code;

	/**
	 * 类型：1.快赔中心，2.保险公司
	 */
	private java.lang.Integer type;

	/**
	 * 排序
	 */
	private java.lang.Integer sort;

	/**
	 * 父级id
	 */
	private java.lang.Integer parent_id;

	/**
	 * 层级
	 */
	private java.lang.Integer layer;

	/**
	 * is_leaf
	 */
	private java.lang.Boolean is_leaf;

	/**
	 * create_time
	 */
	private java.util.Date create_time;

	/**
	 * 通讯录数量
	 */
	private java.lang.Integer acount;

	public Department() {
	}

	public Department(Integer id) {
		this.id = id;
	}

	public Department(String name) {
		this.name = name;
	}

	public Department(Integer id, Boolean isLeaf) {
		this.id = id;
		this.is_leaf = isLeaf;
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

	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}

	public java.lang.Integer getSort() {
		return this.sort;
	}

	public void setParent_id(java.lang.Integer parent_id) {
		this.parent_id = parent_id;
	}

	public java.lang.Integer getParent_id() {
		return this.parent_id;
	}

	public void setLayer(java.lang.Integer layer) {
		this.layer = layer;
	}

	public java.lang.Integer getLayer() {
		return this.layer;
	}

	public java.lang.Boolean getIs_leaf() {
		return is_leaf;
	}

	public void setIs_leaf(java.lang.Boolean is_leaf) {
		this.is_leaf = is_leaf;
	}

	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}

	public java.util.Date getCreate_time() {
		return this.create_time;
	}

	public void setAcount(java.lang.Integer acount) {
		this.acount = acount;
	}

	public java.lang.Integer getAcount() {
		return this.acount;
	}

	/**
	 * @Description 是否有父节点
	 * @return boolean
	 */
	public boolean hasParent() {
		return this.parent_id != null;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	/**
	 * @Description 构造部门用户树
	 * @param userList
	 * @return List<ZTreeNode>
	 */
	public List<ZTreeNode> buildTreeNodeWithUsers(List<User> userList) {
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		// 部门作为根节点
		Integer rootId = 900000 + this.id;
		ZTreeNode node = new ZTreeNode(rootId, this.name);
		nodeList.add(node);

		// 用户作为叶节点
		for (User user : userList) {
			if (user.getDept_id().equals(this.id)) {
				node = new ZTreeNode(user.getId(), user.getReal_name(), rootId, rootId);
				nodeList.add(node);
			}
		}
		return nodeList;
	}

	/**
	 * @Description 构造部门在线用户树
	 * @param userList
	 * @return List<ZTreeNode>
	 */
	public List<ZTreeNode> buildTreeNodeWithOnlineUsers(List<User> userList) {
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		// 部门节点
		Integer rootId = 900000 + this.id;
		Integer prootId = 900000 + (this.getParent_id() != null ? this.getParent_id() : 0);
		ZTreeNode node = new ZTreeNode(rootId, this.name, prootId, prootId, true);
		nodeList.add(node);

		// 用户作为叶节点
		for (User user : userList) {
			if (user.getDept_id().equals(this.id)) {
				node = new ZTreeNode(user.getId(), user.getReal_name(), rootId, rootId);
				nodeList.add(node);
			}
		}
		return nodeList;
	}

	/**
	 * @Description 构造部门树形排序列表
	 * @param list
	 * @return List<Department>
	 */
	public List<Department> buildTreeList(List<Department> list) {
		List<Department> resultList = new ArrayList<Department>();
		if (list == null || list.size() == 0) {
			return resultList;
		}
		List<Department> tempList = null;
		for (Department dept : list) {
			if (dept.getLayer() == 1) {
				resultList.add(dept);
				tempList = buildChildrenList(dept, list);
				resultList.addAll(tempList);
			}
		}

		return resultList;
	}
	
	/**
	 * @Description 递归获取部门子节点列表
	 * @param parent
	 * @param list
	 * @return 
	 * List<Department>     
	 */
	public List<Department> buildChildrenList(Department parent, List<Department> list){
		List<Department> resultList = new ArrayList<Department>();
		List<Department> tempList = null;
		for(Department dept:list){
			if(dept.getParent_id() == parent.id){
				resultList.add(dept);
				tempList = buildChildrenList(dept, list);
				resultList.addAll(tempList);
			}
		}
		return resultList;
	}

}