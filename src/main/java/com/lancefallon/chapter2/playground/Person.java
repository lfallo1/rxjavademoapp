package com.lancefallon.chapter2.playground;

import java.sql.Date;

public class Person implements Mailable {

    private Integer id;
    private String name;
    private Date dob;

    public Person() {
    }

    public Person(Integer id, String name, Date dob) {
        this.id = id;
        this.name = name;
        this.dob = dob;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public void sendEmail(String from, String msg) {
        System.out.println("Sending email from " + from + " to " + this.name + ". Message is " + msg);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                '}';
    }
}
