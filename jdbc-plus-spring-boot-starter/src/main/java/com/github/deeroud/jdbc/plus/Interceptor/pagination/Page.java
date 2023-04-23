/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.deeroud.jdbc.plus.Interceptor.pagination;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis - 分页对象
 *
 * @author liuzh/abel533/isea533
 * @version 3.6.0
 * 项目地址 : http://git.oschina.net/free/Mybatis_PageHelper
 */
public class Page<E> extends ArrayList<E> implements Closeable {
    private static final long serialVersionUID = 1L;

    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 起始行
     */
    private long startRow;
    /**
     * 末行
     */
    private long endRow;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 包含count查询
     */
    private boolean count = true;
    /**
     * 分页合理化
     */
    private Boolean reasonable;
    /**
     * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果
     */
    private Boolean pageSizeZero;

    public Page() {
        super();
    }

    public Page(int pageNum, int pageSize) {
        this(pageNum, pageSize, true, null);
    }

    public Page(int pageNum, int pageSize, boolean count) {
        this(pageNum, pageSize, count, null);
    }

    public Page(int pageNum, int pageSize, boolean count, Boolean reasonable) {
        this(pageNum, pageSize, count, null, null);
    }

    public Page(int pageNum, int pageSize, boolean count, Boolean reasonable, Boolean pageSizeZero) {
        super(0);
        if (pageNum == 1 && pageSize == Integer.MAX_VALUE) {
            this.pageSizeZero = true;
            pageSize = 0;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.count = count;
        this.calculateStartAndEndRow();
        this.setReasonable(reasonable);
        this.setPageSizeZero(pageSizeZero);
    }

    public List<E> getResult() {
        return this;
    }

    public int getPages() {
        return this.pages;
    }

    public Page<E> setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public long getEndRow() {
        return this.endRow;
    }

    public Page<E> setEndRow(long endRow) {
        this.endRow = endRow;
        return this;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public Page<E> setPageNum(int pageNum) {
        //分页合理化，针对不合理的页码自动处理
        this.pageNum = ((this.reasonable != null && this.reasonable) && pageNum <= 0) ? 1 : pageNum;
        return this;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public Page<E> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public long getStartRow() {
        return this.startRow;
    }

    public Page<E> setStartRow(long startRow) {
        this.startRow = startRow;
        return this;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
        if (total == -1) {
            this.pages = 1;
            return;
        }
        if (this.pageSize > 0) {
            this.pages = (int) (total / this.pageSize + ((total % this.pageSize == 0) ? 0 : 1));
        } else {
            this.pages = 0;
        }
        //分页合理化，针对不合理的页码自动处理
        if ((this.reasonable != null && this.reasonable) && this.pageNum > this.pages) {
            if (this.pages != 0) {
                this.pageNum = this.pages;
            }
            this.calculateStartAndEndRow();
        }
    }

    public Boolean getReasonable() {
        return this.reasonable;
    }

    public Page<E> setReasonable(Boolean reasonable) {
        if (reasonable == null) {
            return this;
        }
        this.reasonable = reasonable;
        //分页合理化，针对不合理的页码自动处理
        if (this.reasonable && this.pageNum <= 0) {
            this.pageNum = 1;
            this.calculateStartAndEndRow();
        }
        return this;
    }

    public Boolean getPageSizeZero() {
        return this.pageSizeZero;
    }

    public Page<E> setPageSizeZero(Boolean pageSizeZero) {
        if (this.pageSizeZero == null && pageSizeZero != null) {
            this.pageSizeZero = pageSizeZero;
        }
        return this;
    }

    /**
     * 计算起止行号
     */
    private void calculateStartAndEndRow() {
        this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize : 0;
        this.endRow = this.startRow + this.pageSize * (this.pageNum > 0 ? 1 : 0);
    }

    public boolean isCount() {
        return this.count;
    }

    public Page<E> setCount(boolean count) {
        this.count = count;
        return this;
    }

    public PageInfo<E> toPageInfo() {
        return new PageInfo<E>(this);
    }

    public PageSerializable<E> toPageSerializable() {
        return new PageSerializable<E>(this);
    }

    @Override
    public String toString() {
        return "Page{" +
                "count=" + this.count +
                ", pageNum=" + this.pageNum +
                ", pageSize=" + this.pageSize +
                ", startRow=" + this.startRow +
                ", endRow=" + this.endRow +
                ", total=" + this.total +
                ", pages=" + this.pages +
                ", reasonable=" + this.reasonable +
                ", pageSizeZero=" + this.pageSizeZero +
                '}' + super.toString();
    }

    @Override
    public void close() {
        PageHelper.clearPage();
    }
}
