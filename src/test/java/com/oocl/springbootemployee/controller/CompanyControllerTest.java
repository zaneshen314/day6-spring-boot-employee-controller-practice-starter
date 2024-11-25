package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.entity.Company;
import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.entity.Gender;
import com.oocl.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CompanyControllerTest {

    @Autowired
    private MockMvc client;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    public JacksonTester<Company> json;
    @Autowired
    private JacksonTester<List<Company>> listJson;

    @BeforeEach
    void setUp() {
        companyRepository.getAll().clear();
        Company ACompany = new Company(1L, "A");
        List<Employee> employees = new ArrayList<>();
        Employee don = new Employee(1L, "Don", 18, Gender.MALE, 50000.0);
        Employee zane = new Employee(2L, "Zane", 18, Gender.MALE, 10000.0);
        employees.add(don);
        employees.add(zane);
        ACompany.setEmployees(employees);
        companyRepository.createCompany(ACompany);
        companyRepository.createCompany(new Company(2L, "B"));
        companyRepository.createCompany(new Company(3L, "C"));
        companyRepository.createCompany(new Company(4L, "D"));
        companyRepository.createCompany(new Company(5L, "E"));
        companyRepository.createCompany(new Company(6L, "F"));
    }

    @Test
    void should_return_all_companies_when_get_all_given_companies() throws Exception {
        // Given
        final List<Company> givenCompanies = companyRepository.getAll();

        // When
        String companyJson = client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Company> employees = listJson.parseObject(companyJson);

        // Then
        assertThat(employees).isEqualTo(givenCompanies);
    }

    @Test
    void should_return_company_when_getCompanyResById_given_exist_id() throws Exception {
        // Given
        final List<Company> givenCompanies = companyRepository.getAll();
        Company company = givenCompanies.get(0);


        // When & Then
        client.perform(MockMvcRequestBuilders.get("/companies/" + company.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(company.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(company.getName()));
    }

    @Test
    void should_return_employees_when_getEmployeesByCompanyId_given_company_id() throws Exception {
        // Given
        final List<Company> givenCompanies = companyRepository.getAll();
        Company company = givenCompanies.get(0);

        // When & Then
        client.perform(MockMvcRequestBuilders.get("/companies/" + company.getId() + "/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Don"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("zane"));
    }

    @Test
    void should_return_page_1_with_size_5_when_getPage_given_page_1_and_size_5() throws Exception {
        // Given
        int page = 1;
        int size = 5;

        // When & Then
        client.perform(MockMvcRequestBuilders.get("/companies").param("page", String.valueOf(page)).param("size", String.valueOf(size)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].id").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].id").value(4L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[4].id").value(5L));
    }

    @Test
    void should_return_company_when_update_given_company() throws Exception {
        // Given
        final List<Company> companies = companyRepository.getAll();
        Company company = companies.get(0);
        String newName = "Zane Company";

        String content = "{\n" +
                "    \"name\": \"" + newName + "\"\n" +
                "}";

        // When & Then
        client.perform(MockMvcRequestBuilders.put("/companies/" + company.getId()).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newName));
    }

    @Test
    void should_return_company_when_createCompany_given_company() throws Exception {
        // Given
        String content = "{\n" +
                "    \"name\": \"Alwyn\"\n" +
                "}";

        // When & Then
        client.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Alwyn"));
    }

    @Test
    void should_return_no_content_when_deleteById_given_exist_id() throws Exception {
        // Given
        List<Company> companies = companyRepository.getAll();
        Company company = companies.get(0);

        // When & Then
        client.perform(MockMvcRequestBuilders.delete("/companies/" + company.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}
