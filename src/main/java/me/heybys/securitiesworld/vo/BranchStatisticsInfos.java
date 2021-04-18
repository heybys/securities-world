package me.heybys.securitiesworld.vo;

import java.util.List;

public class BranchStatisticsInfos {
    private Integer year;

    private List<BranchStatistics> dataList;

    public BranchStatisticsInfos(Integer year, List<BranchStatistics> dataList) {
        this.year = year;
        this.dataList = dataList;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<BranchStatistics> getDataList() {
        return dataList;
    }

    public void setDataList(List<BranchStatistics> dataList) {
        this.dataList = dataList;
    }
}
