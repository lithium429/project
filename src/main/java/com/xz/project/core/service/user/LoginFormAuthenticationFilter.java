/**   
 * @Title: LoginFormAuthenticationFilter.java 
 * @Package: com.xz.oa.core.service.user 
 * @Description: 
 * @author: davidwan
 * @date: 2014-7-29 下午3:46:38 
 * @version: V1.0   
 */
package com.xz.project.core.service.user;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/tlogout.do");
	}
}
