package com.bintime.entity;

public class ResultItem {
    private String value;
    private Integer count;

    public ResultItem(String value, Integer count) {
        this.value = value;
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public Integer getCount() {
        return count;
    }
}
