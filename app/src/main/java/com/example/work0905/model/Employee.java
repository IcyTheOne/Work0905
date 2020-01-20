package com.example.work0905.model;

import java.io.Serializable;

public class Employee implements Serializable {

    private String id;
    private String email;
    private String password;
    private String name;
    private String last_name;
    private int salary;
    private String salary_type;
    private int standard_shift_hours;
    private int holidays_left;
    private int holidays_bonus;
    private String rfid;
    private boolean checkedOut;




    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getSalary_type() {
        return salary_type;
    }

    public void setSalary_type(String salary_type) {
        this.salary_type = salary_type;
    }

    public int getStandard_shift_hours() {
        return standard_shift_hours;
    }

    public void setStandard_shift_hours(int standard_shift_hours) {
        this.standard_shift_hours = standard_shift_hours;
    }

    public int getHolidays_left() {
        return holidays_left;
    }

    public void setHolidays_left(int holidays_left) {
        this.holidays_left = holidays_left;
    }

    public int getHolidays_bonus() {
        return holidays_bonus;
    }

    public void setHolidays_bonus(int holidays_bonus) {
        this.holidays_bonus = holidays_bonus;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }
}
