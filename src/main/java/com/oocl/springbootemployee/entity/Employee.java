package com.oocl.springbootemployee.entity;

public class Employee {

    private Long id;
    private String name;
    private Integer age;
    private Gender gender;
    private Double salary;

    public Employee() {
    }

    public Employee(Long id, String name, Integer age, Gender gender, Double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public Double getSalary() {
        return salary;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
