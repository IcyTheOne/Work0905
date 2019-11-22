package com.example.work0905;

public class LoggedMonths {
    private String month;
    private String year;
    private int totalHours;
    private int overtimeHours;
    private int estimatedSalary;

    public LoggedMonths(String month, String year, int totalHours, int overtimeHours, int estimatedSalary) {
        this.month = month;
        this.year = year;
        this.totalHours = totalHours;
        this.overtimeHours = overtimeHours;
        this.estimatedSalary = estimatedSalary;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public int getEstimatedSalary() {
        return estimatedSalary;
    }

    public void setEstimatedSalary(int estimatedSalary) {
        this.estimatedSalary = estimatedSalary;
    }
}
