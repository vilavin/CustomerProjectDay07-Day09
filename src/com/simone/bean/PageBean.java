package com.simone.bean;

import java.util.List;

/**
 *用于储存分页查询后的结果
 */
public class PageBean<T> {
    //pc   pageCode-->当前的页码
    //tp   totalPage-->总页数]
    //tr   totalRecord 数据的总数量
    //ps   pageSize 每一页的数量
    private int pc,tp,tr,ps;
    //用集合存放每一页的数据
    private List<T> beans;
    //要访问的地址
    private String url;

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getTp() {
        tp=tr/ps;
        return tr%ps==0?tp:tp+1;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public int getTr() {
        return tr;
    }

    public void setTr(int tr) {
        this.tr = tr;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public List<T> getBeans() {
        return beans;
    }

    public void setBeans(List<T> beans) {
        this.beans = beans;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
