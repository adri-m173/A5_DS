package com.example.database;

public class Community {
    private String name, mobility, curfew, groupLimit;

    public Community(String name, String mobility, String curfew, String groupLimit) {
        this.name = name;
        this.mobility = mobility;
        this.curfew = curfew;
        this.groupLimit = groupLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobility() {
        return mobility;
    }

    public void setMobility(String mobility) {
        this.mobility = mobility;
    }

    public String getCurfew() {
        return curfew;
    }

    public void setCurfew(String curfew) {
        this.curfew = curfew;
    }

    public String getGroupLimit() {
        return groupLimit;
    }

    public void setGroupLimit(String groupLimit) {
        this.groupLimit = groupLimit;
    }
}

