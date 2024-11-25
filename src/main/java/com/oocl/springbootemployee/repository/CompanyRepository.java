package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.entity.Company;
import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.entity.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        companies.add(new Company(1L, "A"));
        companies.add(new Company(2L, "B"));
        companies.add(new Company(3L, "C"));
    }

    public List<Company> getAll() {
        return companies;
    }

}
