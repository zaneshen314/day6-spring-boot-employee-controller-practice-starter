package com.oocl.springbootemployee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class EmployeeControllerTest {

    private static final Logger log = LoggerFactory.getLogger(EmployeeControllerTest.class);
    @Autowired
    private MockMvc client;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    public JacksonTester<Employee> json;
    @Autowired
    private JacksonTester<List<Employee>> listJson;

    @Test
    void should_return_all_employees_when_get_all_given_employees() throws Exception {
        // Given
        final List<Employee> givenEmployees = employeeRepository.getAll();

        // When
        String employeeJson = client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Employee> employees = listJson.parseObject(employeeJson);

        // Then
        assertThat(employees.get(0).getId()).isEqualTo(givenEmployees.get(0).getId());
        assertThat(employees.get(1).getId()).isEqualTo(givenEmployees.get(1).getId());
        assertThat(employees.get(2).getId()).isEqualTo(givenEmployees.get(2).getId());

//        client.perform(MockMvcRequestBuilders.get("/employees"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(givenEmployees.get(0).getName()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(givenEmployees.get(1).getName()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(givenEmployees.get(2).getName()));

    }

    @Test
    void should_return_employee_when_getById_given_exist_id() throws Exception {
        // Given
        List<Employee> employees = employeeRepository.getAll();
        Employee employee = employees.get(0);

        // When
        String employeeJson = client.perform(MockMvcRequestBuilders.get("/employees/" + employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Employee returnedEmployee = json.parseObject(employeeJson);

        // Then
        assertThat(returnedEmployee.getId()).isEqualTo(employee.getId());
    }

    @Test
    void should_return_employee_when_getById_given_no_exist_id() {
        // Given
        long id = 999;

        // When & Then
        try {
            client.perform(MockMvcRequestBuilders.get("/employees/" + id))
                    .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
