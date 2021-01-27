package com.chuck.client;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ChuckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ChuckFactClient client;

    @Test
    public void shouldReturnAMockedJoke() throws Exception {
        given(client.randomFact()).willReturn(new ChuckFact(1,"test"));
        this.mockMvc.perform(get("/chuck")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test")));
    }
}
