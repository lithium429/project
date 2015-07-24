/**  
 * @Title: VerificationCode.java
 * @Package com.rz.common.utils
 * @Description: TODO
 * @author 万书德
 * @date 2013-10-28
 * @version V1.0  
 */
package com.xz.base.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.servlet.HttpServletBean;

/**
 * @Description: 图片验证码
 * 
 */  
public class VerifyCode extends HttpServletBean {
	private static final long serialVersionUID = 1L;

	private String codeType = "Number";

	private int codeCount = 4;

	private String codeString = "0123456789";

	private int imageWidth = 60;

	private int imageHeight = 18;

	private String fontName = "Arial Black";

	private int fontStyle = 0;

	private int fontSize = 16;

	private String sessionKey = "VerifyCode";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0L);
		response.setContentType("image/jpeg");

		BufferedImage image = new BufferedImage(this.imageWidth, this.imageHeight, 1);
		Random random = new Random();
		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.imageWidth, this.imageHeight);

		g.setColor(Color.GREEN);
		for (int i = 0; i < 50; i++) {
			g.setColor(new Color(150 + random.nextInt(75), 75 + random.nextInt(150), 150 + random.nextInt(75)));
			int x = random.nextInt(this.imageWidth);
			int y = random.nextInt(this.imageHeight);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String randomCode = getVerificationCodeCode(this.codeCount, this.codeType);
		Font font = new Font(this.fontName, this.fontStyle, this.fontSize);
		g.setFont(font);

		for (int i = 0; i < this.codeCount; i++) {
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(String.valueOf(randomCode.charAt(i)), 13 * i + 6, 16);
		}

		HttpSession session = request.getSession(true);
		session.setAttribute(this.sessionKey, randomCode);

		g.dispose();

		ServletOutputStream sos = response.getOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
		encoder.encode(image);
	}

	private String getVerificationCodeCode(int count, String type) {
		if ("Number".equalsIgnoreCase(type)) {
			return RandomStringUtils.randomNumeric(count);
		}
		if ("Alphabetic".equalsIgnoreCase(type)) {
			return RandomStringUtils.randomAlphabetic(count);
		}
		if ("Alphanumeric".equalsIgnoreCase(type)) {
			return RandomStringUtils.randomAlphanumeric(count);
		}
		if ("Ascii".equalsIgnoreCase(type)) {
			return RandomStringUtils.randomAscii(count);
		}
		if ("Custom".equalsIgnoreCase(type)) {
			return RandomStringUtils.random(count, this.codeString);
		}
		return RandomStringUtils.randomNumeric(count);
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public int getCodeCount() {
		return this.codeCount;
	}

	public void setCodeCount(int codeCount) {
		this.codeCount = codeCount;
	}

	public String getCodeString() {
		return this.codeString;
	}

	public void setCodeString(String codeString) {
		this.codeString = codeString;
	}

	public int getImageWidth() {
		return this.imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return this.imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getFontName() {
		return this.fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int getFontStyle() {
		return this.fontStyle;
	}

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}

	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getSessionKey() {
		return this.sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
}