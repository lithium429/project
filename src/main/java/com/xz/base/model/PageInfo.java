/**  
* @Title: LogHelper.java
* @Package com.xz.base.utils
* @Description: 分页数据
* @author 万书德
* @date 2013-6-13
* @version V1.0  
*/
package com.xz.base.model;

import java.io.Serializable;
import java.util.*;

import com.xz.base.utils.ConfigValue;

public class PageInfo<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public final static int DefultSize = ConfigValue.PAGE_SIZE;
	public final static int Size20 = 20;
	public final static int Size50 = 50;
	public final static int Size100 = 100;
	private int pageSize; // 每页显示的条数
	private int pageIndex; // 页索引 （基于1）
	private int recordCount; // 总条数
	private int pageCount; // 总页数
	private Vector<Integer> pageList; // 需要输出页的列表
	private List<T> data; // 获取页面显示数据

	/**
	 * @param pageSize
	 * @param recordCount
	 * @param pageIndex
	 */
	public PageInfo(List<T> data, int pageSize, int recordCount, int pageIndex) {

		this.data = data;
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		this.setPageCount();
		this.setPageIndex(pageIndex);
		this.setPageList();
	}

	/**
	 * @param pageSize
	 * @param recordCount
	 */
	public PageInfo(List<T> data, int pageSize, int recordCount) {
		this(data, pageSize, recordCount, 1);
	}

	/**
	 * @Title: setPageCount
	 * @Description: 设置总页数
	 * @return void
	 */
	public void setPageCount() {
		this.pageCount = this.recordCount / this.pageSize + (this.recordCount % this.pageSize == 0 ? 0 : 1);
		if (this.pageCount == 0) {
			if (this.recordCount == 0) {
				this.pageCount = 0;
			} else {
				this.pageCount = 1;
			}
		}
	}

	/**
	 * @Title: getPageCount
	 * @Description: 获取总页数
	 * @return int
	 */
	public int getPageCount() {
		return this.pageCount;
	}

	/**
	 * @Title: getStartIndex
	 * @Description: 获取起始页索引
	 * @return int
	 */
	public int getStartIndex() {
		if (this.pageIndex == 0) {
			return 0;
		}
		return (this.pageIndex - 1) * this.pageSize;
	}

	/**
	 * @Title: getEndIndex
	 * @Description: 获取结束页索引
	 * @return int
	 */
	public int getEndIndex() {
		return Math.min(this.recordCount, this.pageIndex * this.pageSize);
	}

	/**
	 * @Title: getPageIndex
	 * @Description: 获取当前页索引
	 * @return int
	 */
	public int getPageIndex() {
		return this.pageIndex;
	}

	/**
	 * @Title: setPageIndex
	 * @Description: 设置当前页索引
	 * @param pageIndex
	 * @return void
	 */
	public void setPageIndex(int pageIndex) {
		int validPage = pageIndex <= 0 ? 1 : pageIndex;
		validPage = validPage > this.pageCount ? this.pageCount : validPage;
		this.pageIndex = validPage;
	}

	/**
	 * @Title: getPageSize
	 * @Description: 获取每页显示的条数
	 * @return int
	 */
	public int getPageSize() {
		return this.pageSize;
	}

	/**
	 * @Title: setPageSize
	 * @Description: 设置每页显示的条数
	 * @param pageSize
	 * @return void
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @Title: getRecordCount
	 * @Description: 获取总记录数
	 * @return int
	 */
	public int getRecordCount() {
		return this.recordCount;
	}

	/**
	 * @Title: setRecordCount
	 * @Description: 设置总记录数
	 * @param recordCount
	 * @return void
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * @Title: setPageList
	 * @Description: 设置输出页列表
	 * @return void
	 */
	public void setPageList() {
		int start = 1;
		int end = 8;
		if (end < this.pageCount) {
			if (this.pageIndex >= 7) {
				start = this.pageIndex - 3;
				end = this.pageIndex + 3;
			}
			if (start >= this.pageCount - 7) {
				start = this.pageCount - 7;
				end = this.pageCount;
			}
		} else {
			end = this.pageCount;
		}
		this.pageList = new Vector<Integer>();
		for (int i = start; i <= end; i++) {
			pageList.add(new Integer(i));
		}
	}

	/**
	 * @Title: getPageList
	 * @Description: 获取输出页列表
	 * @return Set<Integer>
	 */
	public Vector<Integer> getPageList() {
		return this.pageList;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
