package com.demo.ssm.mybatis.page;

import java.io.Serializable;

/**
 * Created by xumiao on 4/20/18.
 */
public class Page implements Serializable {
    private static final long serialVersionUID = 8877198104841875149L;
    private Integer currentPage = 1;
    private Integer pageSize = 2;
    private Integer totalPages = 0;
    private Long totalRecords = 0l;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
        this.totalPages = (totalRecords.intValue() + this.pageSize -1) / this.pageSize;
    }

    public Integer getStartIndex(){
        return (this.currentPage - 1) * this.pageSize;
    }

    public Integer getEndIndex(){
        return this.currentPage * this.pageSize;
    }
}
