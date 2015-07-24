/**   
 * @Title: FileDownload.java 
 * @Package: com.xz.oa.core.service.file 
 * @Description: 
 * @author: davidwan
 * @date: 2014-7-26 上午10:46:44 
 * @version: V1.0   
 */
package com.xz.project.core.service.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
 
import com.xz.base.utils.ConfigValue;
import com.xz.base.utils.LogHelper;

public class FileDownload {

	private static final String ROOT_PATH = "rootPath";

	private HttpServletRequest request;

	private HttpServletResponse response;

	public FileDownload() {

	}

	public FileDownload(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void process(String url, String fileName) {
		if (StringUtils.isBlank(url)) {
			return;
		}
		String path = request.getServletContext().getRealPath("");
		String rootPath = ConfigValue.readValue(ROOT_PATH, "");
		if (StringUtils.isNotBlank(rootPath)) {
			path = path + url.replace(rootPath, "");
		}
		else{
			path = path + url;
		}

		// path是指要下载的文件的路径。
		File file = new File(path);
		OutputStream outputStream = null;
		try {
			// 以流的形式下载文件。
			InputStream inputStream = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			inputStream.close();

			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
			response.addHeader("Content-Length", String.valueOf(file.length()));
			response.setContentType("application/octet-stream");
			outputStream = new BufferedOutputStream(response.getOutputStream());
			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();
		} catch (Exception ex) {
			LogHelper.getLogger().error("下载文件时出错", ex);
			try { 
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("下载文件时出错！");
			} catch (IOException e) { 
				LogHelper.getLogger().error("获取Writer时出错", e);
			}
		}
	}
}
