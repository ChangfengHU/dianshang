package com.dianshang.core.tools;

import java.io.Serializable;
import java.util.List;

/**
 * 我的分页
 *
 * @author by ChangFeng
 * @Created by Administrator on 2017/8/28 0028 20:58.
 */
public class MyPage<E> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int pageNum;
    private int pageSize;
    //起始下标
    private int startRow;
    //结束下标
    private int endRow;
    //总记录数
    private long total;
    //总页数
    private int pages;
    private List<E> result;
    // 工具条使用的结果

    public MyPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 4;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPageNum() {
        return pageNum;
    }

    @Override
    public String toString() {
        return "MyPage{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", total=" + total +
                ", pages=" + pages +
                ", result=" + result +
                '}';
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        this.startRow = this.pageNum > 0
                ? (this.pageNum - 1) * this.pageSize
                : 0;
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        this.endRow = pageNum * pageSize;
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {

        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        // 计算总页数
        long totalPage = this.getTotal() / this.getPageSize()
                + ((this.getTotal() % this.getPageSize() == 0) ? 0 : 1);
        this.setPages((int) totalPage);
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<E> getResult() {
        return result;
    }

    public void setResult(List<E> result) {
        this.result = result;
    }

}
