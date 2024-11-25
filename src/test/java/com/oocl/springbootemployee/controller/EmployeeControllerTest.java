package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.entity.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @BeforeEach
    void setUp(){
        employeeRepository.getAll().clear();
        employeeRepository.save(new Employee(1L, "Jackson", 18, Gender.FEMALE, 90000.0));
        employeeRepository.save(new Employee(2L, "Don", 18, Gender.MALE, 50000.0));
        employeeRepository.save(new Employee(3L, "Zane", 18, Gender.MALE, 10000.0));
    }

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
        assertThat(employees).isEqualTo(givenEmployees);

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
        assertThat(returnedEmployee).isEqualTo(employee);
    }

    @Test
    void should_return_employee_when_getById_given_no_exist_id() throws Exception {
        // Given
        long id = 999;

        // When
        String employeeJson = client.perform(MockMvcRequestBuilders.get("/employees/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Then
        assertThat(employeeJson).isEqualTo("");
    }

    @Test
    void should_return_male_employees_when_getByGender_given_gender_male() throws Exception {
        // Given
        final List<Employee> givenEmployees = employeeRepository.getByGender(Gender.MALE);

        // When
        String employeeJson = client.perform(MockMvcRequestBuilders.get("/employees").param("gender", "MALE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Employee> employees = listJson.parseObject(employeeJson);

        // Then
        assertThat(employees).isEqualTo(givenEmployees);

    }

    @Test
    void should_return_employee_when_create_given_employee_info() throws Exception {
        // Given
        String content = "{\n" +
                "    \"name\": \"Alwyn\",\n" +
                "    \"age\": 18,\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"salary\": 900000.0\n" +
                "}";

        Employee expectEmployee = json.parseObject(content);

        // When & Then
        client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectEmployee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(expectEmployee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(expectEmployee.getGender().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(expectEmployee.getSalary()));
    }

    @Test
    void should_return_no_content_when_deleteById_given_exist_id() throws Exception {
        // Given
        List<Employee> employees = employeeRepository.getAll();
        Employee employee = employees.get(0);

        // When & Then
        client.perform(MockMvcRequestBuilders.delete("/employees/" + employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void should_return_employee_when_update_given_employee() throws Exception {
        // Given
        List<Employee> employees = employeeRepository.getAll();
        Employee employee = employees.get(0);
        Integer newAge = employee.getAge() + 1;
        Double newSalary = employee.getSalary() + 10;

        String content = "{\n" +
                "    \"name\": \"" + employee.getName() + "\",\n" +
                "    \"age\": " +  newAge +",\n" +
                "    \"gender\": \"" + employee.getGender() + "\",\n" +
                "    \"salary\": " + newSalary + "\n" +
                "}";

        // When & Then
        client.perform(MockMvcRequestBuilders.put("/employees/" + employee.getId()).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(newAge))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(employee.getGender().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(newSalary));
    }

}
