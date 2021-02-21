package com.taxi.pojos;

public class PieChartData {
    private String name;
    private Object value;

    public PieChartData(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
