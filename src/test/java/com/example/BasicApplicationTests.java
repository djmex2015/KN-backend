package com.example;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BasicApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void limpar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/reset"))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/listContacts?name=Ele&page=0&size=50&sort=name,asc"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.*", hasSize(0)));
    }

    @Test
    public void testAUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "people.csv", MediaType.TEXT_PLAIN_VALUE, getClass().getResourceAsStream("/people.csv"));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/processFile").file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void testBList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/listContacts").param("name", "").param("page", "0").param("size", "50").param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(11)))
//                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Edna Krabappel", "Tattoo Annie", "Mary Bailey")))
                .andDo(MockMvcResultHandlers.print());
    }
}
