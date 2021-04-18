package me.heybys.securitiesworld.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Branch {

    @Id
    private String brCode;

    private String brName;

    public Branch() {
    }

    public Branch(String brCode, String brName) {
        this.brCode = brCode;
        this.brName = brName;
    }

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public String getBrName() {
        return brName;
    }

    public void setBrName(String brName) {
        this.brName = brName;
    }
}
