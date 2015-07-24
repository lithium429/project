/**   
* @Title: UploadItem.java 
* @Package: com.xz.base.model 
* @Description: 
* @author: davidwan
* @date: 2014-9-5 上午9:25:13 
* @version: V1.0   
*/
package com.xz.base.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadItem {
	private CommonsMultipartFile fileData;

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
}
