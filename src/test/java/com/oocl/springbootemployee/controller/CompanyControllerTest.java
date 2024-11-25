package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.entity.Company;
import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.entity.Gender;
import com.oocl.springbootemployee.repository.CompanyRepository;
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
    private JacksonTester<List<Company>> listJson;

    @BeforeEach
    void setUp() {

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


}
