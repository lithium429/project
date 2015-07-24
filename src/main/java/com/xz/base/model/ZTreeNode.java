package com.xz.base.model;
  
public class ZTreeNode {
	private Integer id;
	private Integer pId;
	private String name;
	private String title;

	private String url;
	private Boolean open;
	private Boolean checked;
	private Integer sort;
	private Integer is_leaf;
	private int type;
	private String iconSkin;
	private Boolean isParent;
	public Integer parentId;
	private Boolean showDeleteBtn;
	private Boolean showEditBtn;
	private Boolean showAddBtn;
	private Boolean showShareBtn;
	private Boolean isAction;
	private Boolean isHidden;
	private Boolean isShare;
	private String nodepid;
	private Integer creatorId;
	private Integer object_id;

	public ZTreeNode() {

	}
	
	public ZTreeNode(String name) { 
		this.name = name;
		this.isParent = true; 
		this.open = true;
	}

	public ZTreeNode(Integer id, String name) {
		this.id = id;
		this.name = name;
		this.isParent = true; 
		this.open = true;
	}

	public ZTreeNode(String name, Integer creatorId) {
		this.name = name;
		this.creatorId = creatorId;
		this.isParent = true;
		this.showDeleteBtn = false;
		this.showAddBtn = true;
		this.showEditBtn = false;
		this.showShareBtn = true;
	}
	
	public ZTreeNode(Integer id, String name, int type) {
		this.id = id;
		this.name = name;
		this.isParent = true; 
		this.open = true;
		this.type = type;
	}

	public ZTreeNode(Integer id, String name, String iconSkin, Integer creatorId) {
		this.id = id;
		this.name = name;
		this.iconSkin = iconSkin;
		this.creatorId = creatorId;
		this.open = true;
	}
	
	public ZTreeNode(Integer id, String name, Integer parentId, Integer pId) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.pId = pId;
		this.open = true;
	}
	
	public ZTreeNode(Integer id, String name, Integer parentId, Integer pId, Boolean isParent) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.pId = pId;
		this.open = true;
		this.isParent = isParent;
	}
	
	public ZTreeNode(Integer id, String name, Integer parentId, Integer pId, Boolean isParent, int type, Integer object_id) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.pId = pId;
		this.open = true;
		this.isParent = isParent;
		this.type = type;
		this.object_id = object_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPId() {
		return pId;
	}

	public void setPId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Boolean getShowDeleteBtn() {
		return showDeleteBtn;
	}

	public void setShowDeleteBtn(Boolean showDeleteBtn) {
		this.showDeleteBtn = showDeleteBtn;
	}

	public Boolean getShowAddBtn() {
		return showAddBtn;
	}

	public void setShowAddBtn(Boolean showAddBtn) {
		this.showAddBtn = showAddBtn;
	}

	public Boolean getShowEditBtn() {
		return showEditBtn;
	}

	public void setShowEditBtn(Boolean showEditBtn) {
		this.showEditBtn = showEditBtn;
	}

	public Boolean getShowShareBtn() {
		return showShareBtn;
	}

	public void setShowShareBtn(Boolean showShareBtn) {
		this.showShareBtn = showShareBtn;
	}

	public Boolean getIsAction() {
		return isAction;
	}

	public void setIsAction(Boolean isAction) {
		this.isAction = isAction;
	}

	public Boolean getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public String getNodepid() {
		return nodepid;
	}

	public void setNodepid(String nodepid) {
		this.nodepid = nodepid;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getIs_leaf() {
		return is_leaf;
	}

	public void setIs_leaf(Integer is_leaf) {
		this.is_leaf = is_leaf;
	}

	public Integer getObject_id() {
		return object_id;
	}

	public void setObject_id(Integer object_id) {
		this.object_id = object_id;
	} 
}
