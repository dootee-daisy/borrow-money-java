package com.dk.common.http;

import com.github.pagehelper.PageInfo;

/**
 * Created by jq.chen on 2017/6/6.
 */
public class PageResult extends DataResult {

    /**
     * Total number of records
     */
    private long allCount;
    /**
     * Total number of pages
     */
    private int pageCount;
    /**
     * Page number
     */
    private int pageNum;
    /**
     * Number of items per page
     */
    private int pageSize;

    public PageResult(){}

    public PageResult(int code){
        super(code);

    }

    public long getAllCount() {
        return allCount;
    }

    public void setAllCount(long allCount) {
        this.allCount = allCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNum() {
        return pageNum;
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

    public static PageResult init(){

        return new PageResult();
    }


    public PageResult buildDate(PageInfo page){
        setAllCount(page.getTotal());
        setPageCount(page.getPages());
        setPageNum(page.getPageNum());
        setPageSize(page.getPageSize());
        setData(page.getList());
        return this;
    }

    public PageResult setPage(){
        if (this.pageNum<1){
            this.pageNum = 1;
        }
        int count=(int) this.getAllCount();
        if (this.getAllCount() % this.getPageSize() == 0){
            this.setPageCount(count/this.getPageSize());
        }else {
            this.setPageCount(count/this.getPageSize() +1);
        }
        if (this.getPageCount()<1){
            this.setPageCount(1);
        }
        return this;
    }
}