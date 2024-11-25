package com.oocl.springbootemployee.controller;


import com.oocl.springbootemployee.entity.Company;
import com.oocl.springbootemployee.entity.CompanyResponse;
import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("companies")
@RestController
public class CompanyController {

    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.getAll();
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompanyById(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable Long id) {
        return null;
    }

    @GetMapping(params = {"page", "size"})
    public List<Company> getCompaniesByPage(@RequestParam Integer page, @RequestParam Integer size) {
        return null;
    }

    @PutMapping("/{id}")
    public void updateCompany(@PathVariable Long id, @RequestBody Company company) {

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company) {
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable Long id) {

    }


}
