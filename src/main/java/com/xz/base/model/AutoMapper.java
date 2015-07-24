package com.xz.base.model;

import java.util.List;

public class AutoMapper {
     private Boolean success;
     private String query;
     private List<String> suggestions;
 
	 private List<MapperChild> data;
 	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public List<String> getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}
	public List<MapperChild> getData() {
		return data;
	}
	public void setData(List<MapperChild> data) {
		this.data = data;
	}
   
}


