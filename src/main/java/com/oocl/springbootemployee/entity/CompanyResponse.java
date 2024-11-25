package com.oocl.springbootemployee.entity;


public class CompanyResponse {

    private Long id;
    private String name;

    public CompanyResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
