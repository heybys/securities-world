package me.heybys.securitiesworld.vo;

public class AccountStatisticsInfo {
    private Integer year;
    private String name;
    private String accNo;
    private Long sumAmt;

    public AccountStatisticsInfo() {
    }

    public AccountStatisticsInfo(Integer year, String name, String accNo, Long sumAmt) {
        this.year = year;
        this.name = name;
        this.accNo = accNo;
        this.sumAmt = sumAmt;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public Long getSumAmt() {
        return sumAmt;
    }

    public void setSumAmt(Long sumAmt) {
        this.sumAmt = sumAmt;
    }
}
