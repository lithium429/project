package com.xz.base.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
* @Description: 
*
*/
public class BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6253216521884398807L;
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public Map<String, Object> getMap() {
		return map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
