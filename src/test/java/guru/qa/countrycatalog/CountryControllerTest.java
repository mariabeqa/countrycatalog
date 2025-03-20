package guru.qa.countrycatalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.countrycatalog.domain.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ContryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Test
    void newCountryShouldBeCreated() throws Exception {
        Country country = new Country(
              "Thailand",
                "TH",
               513120
        );
        mockMvc.perform(MockMvcRequestBuilders
                                .post("/api/country/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(country))

                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Thailand"))
                .andExpect(jsonPath("$.code").value("TH"))
                .andExpect(jsonPath("$.area").value("513120"));
    }

    @Test
    @Sql(scripts = "/countryShouldBeUpdated.sql")
    void countryShouldBeUpdated() throws Exception {
        Country country = new Country(
                "Thailand",
                "TH",
                513120
        );
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/country/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(country))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Thailand"))
                .andExpect(jsonPath("$.code").value("TH"))
                .andExpect(jsonPath("$.area").value("513120"));
    }

    @Test
    @Sql(scripts = "/allCountriesShouldBeReturned.sql")
    void allCountriesShouldBeReturned() throws Exception {
        mockMvc.perform(get("/api/country/all")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].name").value("Russia"))
                .andExpect(jsonPath("$[0].code").value("RU"))
                .andExpect(jsonPath("$[0].area").value("17125191"));
    }
}
