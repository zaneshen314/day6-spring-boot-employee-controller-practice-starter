package com.oocl.springbootemployee.entity;

import java.util.List;

public class BasePage<E> {
    private List<?> data;

    private Integer page;

    private Integer size;

    private Long total;

    public BasePage(List<?> data, Long total, Integer size, Integer page) {
        this.data = data;
        this.total = total;
        this.size = size;
        this.page = page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}