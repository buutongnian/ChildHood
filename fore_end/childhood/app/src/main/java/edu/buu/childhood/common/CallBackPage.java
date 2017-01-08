package edu.buu.childhood.common;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.rank.pojo.RankList;

/**
 * Created by Administrator on 2016/7/10.
 */
public class CallBackPage<T> {
    private Boolean isSuccess;
    private int pageSize;
    private int currentPage;
    private int totalPage;
    private String falg;
    private RankList rankList;
    private List datalist=new ArrayList();

    public RankList getRankList() {
        return rankList;
    }

    public void setRankList(RankList rankList) {
        this.rankList = rankList;
    }

    public String getFalg() {
        return falg;
    }

    public void setFalg(String falg) {
        this.falg = falg;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<T> datalist) {
        this.datalist = datalist;
    }
}
