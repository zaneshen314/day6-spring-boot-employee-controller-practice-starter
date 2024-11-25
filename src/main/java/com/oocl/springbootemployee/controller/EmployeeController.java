package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.entity.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;


    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return employeeRepository.getById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getByGender(@RequestParam Gender gender){
        return employeeRepository.getByGender(gender);
    }

}
