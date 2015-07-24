/**   
 * @Title: User.java 
 * @Package: com.xz.oa.core.domain.entity 
 * @Description: 用户
 * @author: davidwan
 * @date: 2014-7-14 上午10:26:56 
 * @version: V1.0   
 */
package com.xz.project.core.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.xz.base.domain.BaseEntity;
import com.xz.base.utils.DESPlus;
import com.xz.base.utils.LogHelper;
import com.xz.base.utils.SpellUtil;
import com.xz.project.core.domain.enums.EnumUserState;

public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3199395314840652006L;

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	/**
	 * id
	 */
	private java.lang.Integer id;

	/**
	 * 用户名
	 */
	private java.lang.String name;

	/**
	 * 密码
	 */
	private java.lang.String password;

	/**
	 * 原密码
	 */
	private java.lang.String password_old;

	/**
	 * 隐藏密码
	 */
	private java.lang.String password_hid;

	/**
	 * 密码盐值
	 */
	private java.lang.String salt;

	/**
	 * 姓名
	 */
	private java.lang.String real_name;

	/**
	 * 部门科室id
	 */
	private java.lang.Integer dept_id;

	/**
	 * 部门科室名称
	 */
	private java.lang.String dept_name;

	/**
	 * 是否支持外发短信
	 */
	private java.lang.Boolean is_allowso;

	/**
	 * 创建内部通讯录
	 */
	private java.lang.Boolean is_create;

	/**
	 * 状态：1.正常，2.冻结
	 */
	private java.lang.Integer state;

	/**
	 * mobile
	 */
	private java.lang.String mobile;

	/**
	 * 电子邮箱
	 */
	private java.lang.String email;

	/**
	 * 性别：1.男，2.女
	 */
	private java.lang.Integer sex;

	/**
	 * qq
	 */
	private java.lang.String qq;

	/**
	 * remark
	 */
	private java.lang.String remark;

	/**
	 * create_time
	 */
	private java.util.Date create_time;

	private List<UserRole> roles;

	/**
	 * 角色字符串
	 */
	private java.lang.String role_ids;

	/**
	 * 角色字符串
	 */
	private List<Integer> roleIds;

	/**
	 * 是否第一次登录
	 */
	private java.lang.Boolean is_first_login;

	/**
	 * 是否重置密码
	 */
	private java.lang.Boolean is_reseted_psw;

	/**
	 * 是否创建通讯录
	 */
	private java.lang.Boolean has_address;

	private java.lang.String des_password;

	/**
	 * 名称拼音
	 */
	private java.lang.String pname;

	/**
	 * 名称首字母拼音
	 */
	private java.lang.String fname;  

	public User() {
	}

	public User(String name, String password, String realName) {
		this.name = name;
		this.password = password;
		this.real_name = realName;
		this.entryptPassword();
	}

	public User(String name, String password, String realName, Integer deptId, Boolean hasAddress) {
		this.name = name;
		this.password = password;
		this.real_name = realName;
		this.dept_id = deptId;
		this.state = EnumUserState.正常.getValue();
		this.entryptPassword();
		this.fname = SpellUtil.getFirstOneSpell(realName);
		this.pname = SpellUtil.getFullSpell(realName);
		this.has_address = hasAddress;
		this.create_time = new Date();
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

	public void setRole_ids(java.lang.String role_ids) {
		this.role_ids = role_ids;
	}

	public java.lang.String getRole_ids() {
		return this.role_ids;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public java.lang.String getPassword() {
		return this.password;
	}

	public void setSalt(java.lang.String salt) {
		this.salt = salt;
	}

	public java.lang.String getSalt() {
		return this.salt;
	}

	public void setReal_name(java.lang.String real_name) {
		this.real_name = real_name;
	}

	public java.lang.String getReal_name() {
		return this.real_name;
	}

	public void setDept_id(java.lang.Integer dept_id) {
		this.dept_id = dept_id;
	}

	public java.lang.Integer getDept_id() {
		return this.dept_id;
	}

	public void setDept_name(java.lang.String dept_name) {
		this.dept_name = dept_name;
	}

	public java.lang.String getDept_name() {
		return this.dept_name;
	}

	public void setIs_allowso(java.lang.Boolean is_allowso) {
		this.is_allowso = is_allowso;
	}

	public java.lang.Boolean getIs_allowso() {
		return this.is_allowso;
	}

	public void setState(java.lang.Integer state) {
		this.state = state;
	}

	public java.lang.Integer getState() {
		return this.state;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getMobile() {
		return this.mobile;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getEmail() {
		return this.email;
	}

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}

	public java.lang.Integer getSex() {
		return this.sex;
	}

	public void setQq(java.lang.String qq) {
		this.qq = qq;
	}

	public java.lang.String getQq() {
		return this.qq;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}

	public java.util.Date getCreate_time() {
		return this.create_time;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
		if (this.roles != null) {
			List<Integer> result = new ArrayList<Integer>();
			for (UserRole item : this.roles) {
				result.add(item.getRole_id());
			}
			roleIds = result;
		}
	}

	public void setIs_create(java.lang.Boolean is_create) {
		this.is_create = is_create;
	}

	public java.lang.Boolean getIs_create() {
		return this.is_create;
	}

	public java.lang.Boolean getIs_first_login() {
		return is_first_login;
	}

	public void setIs_first_login(java.lang.Boolean is_first_login) {
		this.is_first_login = is_first_login;
	}

	public java.lang.Boolean getIs_reseted_psw() {
		return is_reseted_psw;
	}

	public void setIs_reseted_psw(java.lang.Boolean is_reseted_psw) {
		this.is_reseted_psw = is_reseted_psw;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public void entryptPassword() {
		if (StringUtils.isBlank(this.password)) {
			return;
		}

		try {
			DESPlus des = new DESPlus();
			this.des_password = des.encrypt(this.password);
		} catch (Exception e) {
			LogHelper.getLogger().error("des加密出错", e);
		}

		byte[] salt = Digests.generateSalt(SALT_SIZE);
		this.salt = Encodes.encodeHex(salt);

		byte[] hashPassword = Digests.sha1(this.password.getBytes(), salt, HASH_INTERATIONS);
		this.password = Encodes.encodeHex(hashPassword);

	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getRoleIds() {
		String result = "";
		if (this.roles != null && this.role_ids == null) {
			for (UserRole item : this.roles) {
				if (result == "") {
					result = String.valueOf(item.getRole_id());
				} else {
					result = result + "," + String.valueOf(item.getRole_id());
				}
			}
			this.role_ids = result;
		} else {
			result = this.role_ids;
		}
		return result;
	}

	public List<Integer> getRoleListIds() {
		List<Integer> result = null;
		if (this.roles != null && this.roleIds.size() == 0) {
			result = new ArrayList<Integer>();
			for (UserRole item : this.roles) {
				result.add(item.getRole_id());
			}
			this.roleIds = result;
		} else {
			result = this.roleIds;
		}
		return result;
	} 

	public String getKey() {
		String r = "";
		r = this.real_name + "(" + this.mobile + ")";
		return r;
	}

	public java.lang.Boolean getHas_address() {
		return has_address;
	}

	public void setHas_address(java.lang.Boolean has_address) {
		this.has_address = has_address;
	}

	public java.lang.String getPname() {
		return pname;
	}

	public void setPname(java.lang.String pname) {
		this.pname = pname;
	}

	public java.lang.String getFname() {
		return fname;
	}

	public void setFname(java.lang.String fname) {
		this.fname = fname;
	}

	public java.lang.String getPassword_old() {
		return password_old;
	}

	public void setPassword_old(java.lang.String password_old) {
		this.password_old = password_old;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public void entryptPassword_old() {
		if (StringUtils.isBlank(this.password_old)) {
			return;
		}
		byte[] salt = Encodes.decodeHex(this.salt);

		byte[] hashPassword = Digests.sha1(this.password_old.getBytes(), salt, HASH_INTERATIONS);
		this.password_old = Encodes.encodeHex(hashPassword);

	}

	public java.lang.String getPassword_hid() {
		return password_hid;
	}

	public void setPassword_hid(java.lang.String password_hid) {
		this.password_hid = password_hid;
	}
 
	public java.lang.String getDes_password() {
		return des_password;
	}

	public void setDes_password(java.lang.String des_password) {
		this.des_password = des_password;
	} 

	public boolean gainIs_super() {
		boolean flag = false;
		if (this.roles != null && this.roleIds.size() == 0) { 
			for (UserRole item : this.roles) {
				if (item.getIs_super()) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

}