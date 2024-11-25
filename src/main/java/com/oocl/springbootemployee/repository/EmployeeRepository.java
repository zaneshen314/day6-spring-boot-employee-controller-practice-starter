package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class EmployeeRepository {

    List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        Employee jackson = new Employee(1L, "Jackson", 18, Gender.FEMALE, 90000.0);
        Employee don = new Employee(2L, "Don", 18, Gender.MALE, 50000.0);
        Employee zane = new Employee(3L, "Zane", 18, Gender.MALE, 10000.0);
        employees.add(jackson);
        employees.add(don);
        employees.add(zane);
    }

    public List<Employee> getAll() {
      return employees;
    }

    public Employee getById(Long id) {
        return employees.stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Employee> getByGender(Gender gender){
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }


    public Employee save(Employee employee) {
        return null;
    }
}
