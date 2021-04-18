package me.heybys.securitiesworld.entity;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    private String accNo;

    private String name;

    private String brCode;

    public Account() {
    }

    public Account(String accNo, String name, String brCode) {
        this.accNo = accNo;
        this.name = name;
        this.brCode = brCode;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }
}
