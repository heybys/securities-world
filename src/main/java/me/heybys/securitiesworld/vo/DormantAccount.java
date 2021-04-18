package me.heybys.securitiesworld.vo;

public class DormantAccount {
    private Integer year;
    private String name;
    private String accNo;

    public DormantAccount() {
    }

    public DormantAccount(Integer year, String name, String accNo) {
        this.year = year;
        this.name = name;
        this.accNo = accNo;
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
}
