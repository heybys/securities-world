package me.heybys.securitiesworld.exception;

public class BranchStatisticsNotFoundException extends RuntimeException{
    private String brName;

    public BranchStatisticsNotFoundException(String brName) {
        this.brName = brName;
    }

    public String getBrName() {
        return brName;
    }

    public void setBrName(String brName) {
        this.brName = brName;
    }
}
