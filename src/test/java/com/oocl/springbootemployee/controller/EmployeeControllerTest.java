package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class EmployeeControllerTest {

    @Autowired
    private MockMvc client;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void should_return_all_employees_when_get_all_given_employees() throws Exception {
        // Given
        final List<Employee> givenEmployees = employeeRepository.getAll();

        // When & Then
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(givenEmployees.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(givenEmployees.get(1).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(givenEmployees.get(2).getName()));
    }

    @Test
    void should_return_employee_when_getById_given_exist_id() throws Exception {
        // Given
        long id = 1;
        List<Employee> employees = employeeRepository.getAll();
        Employee employee = employees.get(0);

        // When & Then
        client.perform(MockMvcRequestBuilders.get("/employees/"+ employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(employee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(employee.getGender().name()));
    }
}
