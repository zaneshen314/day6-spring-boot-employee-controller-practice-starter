package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.entity.Company;
import com.oocl.springbootemployee.entity.CompanyResponse;
import com.oocl.springbootemployee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    List<Company> companies = new ArrayList<>();

    public CompanyRepository() {

    }

    public List<Company> getAll() {
        return companies;
    }

    public Company getCompanyById(Long id) {
        return getAll().stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
    }
    public CompanyResponse getCompanyResById(Long id) {
        Company company = getCompanyById(id);
        return company != null ? new CompanyResponse(company.getId(),company.getName()) : null;
    }

    public List<Employee> getEmployeesByCompanyId(Long id) {
        Company company = getCompanyById(id);
        return company != null ? company.getEmployees() : null;
    }

    public List<Company> getCompaniesByPage(Integer page, Integer size) {
        return companies.stream().skip( (page - 1L) * size).limit(size).collect(Collectors.toList());
    }

    public Company updateCompany(Long id, Company company) {
        Company companyToUpdate = getCompanyById(id);
        companyToUpdate.setName(company.getName());
        companyToUpdate.setEmployees(company.getEmployees());
        return companyToUpdate;
    }

    public Company createCompany(Company company) {
        company.setId(companies.size() + 1L);
        companies.add(company);
        return company;
    }

    public void deleteCompany(Long id) {
        companies.removeIf(company -> company.getId().equals(id));
    }
}
