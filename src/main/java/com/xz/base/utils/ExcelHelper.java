/**  
 * @Title: ExcelHelper.java
 * @Package com.rz.pscc.web.util
 * @Description: Excel文件读写辅助类
 * @author 万书德
 * @date 2013-6-20
 * @version V1.0  
 */
package com.xz.base.utils;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

/**
 * @Description: Excel文件读写辅助类
 * 
 */
public class ExcelHelper {

	/**
	 * @Fields filePath : 文件路径
	 */
	private String filePath;

	/**
	 * @Fields fileStream : 文件流
	 */
	private FileInputStream fileStream;

	/**
	 * @Fields columnCount :列数
	 */
	private int columnCount;

	public ExcelHelper(String filePath, int columnCount) {
		this.filePath = filePath;
		this.columnCount = columnCount;
	}

	public ExcelHelper(FileInputStream fileStream, int columnCount) {
		this.fileStream = fileStream;
		this.columnCount = columnCount;
	}

	/**
	 * @Title: read
	 * @Description: 读取数据
	 * @return void
	 */
	public Map<Integer, List<Object>> read(int startRowIndex) {
		if (StringUtils.isBlank(this.filePath) && this.fileStream == null) {
			return null;
		}

		Map<Integer, List<Object>> map = new LinkedHashMap<Integer, List<Object>>();
		try {
			if (!StringUtils.isBlank(this.filePath)) {
				this.fileStream = new FileInputStream(new File(this.filePath));
			}
			HSSFWorkbook workbook = new HSSFWorkbook(this.fileStream);
			HSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			int id = 1;
			int rowNumber = 0;
			List<Object> list = null;

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				rowNumber++;
				if (rowNumber < startRowIndex) {
					continue;
				}

				boolean flag = false;
				list = new ArrayList<Object>();
				for (int i = 0; i < this.columnCount; i++) {
					Cell cell = row.getCell(i);
					if (cell != null) {
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							list.add(cell.getStringCellValue());
							flag = true;
							break;
						case Cell.CELL_TYPE_NUMERIC:
							list.add(String.valueOf(cell.getNumericCellValue()));
							flag = true;
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							list.add(cell.getBooleanCellValue() ? "是" : "否");
							flag = true;
							break;
						case Cell.CELL_TYPE_BLANK:
						case Cell.CELL_TYPE_ERROR:
							list.add(null);
							break;
						default:
							list.add(null);
							break;
						}
					} else {
						list.add(null);
					}
				}
				if (list.size() > 0 && flag) {
					map.put(id, list);
					id++;
				} 
			}
			this.fileStream.close();

		} catch (FileNotFoundException e) {
			map = null;
			LogHelper.getLogger().error("没有找到文件", e);
		} catch (IOException e) {
			map = null;
			LogHelper.getLogger().error("操作文件时出错", e);
		}
		return map;
	}
}