package whz.pti.eva.boundary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import whz.pti.eva.domain.Grade;
import whz.pti.eva.service.GradeService;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest//****** (classes = GradeCalculatorTekariChalehAljarrahApplicationTests.class) //(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
class whGradeControllerTest {


    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @MockBean
    private GradeService gradeServiceMock;

    @BeforeEach
    public  void setup(){
        mockMvc= MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
        when(gradeServiceMock.listAllGrades())
                .thenReturn(List.of(new Grade("Math","1.0"),new Grade("SE","3.3"),new Grade("p1","1.7")));
        when(gradeServiceMock.calculateAverage()).thenReturn(2.0);
    }
    @Test
    void listAllGrades() {
        try {
            mockMvc.perform(get("/grades")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(view().name("grades"))
                    .andExpect(model().attribute("grades", hasSize(3)))
                    .andExpect(model().attribute("average", 2.0))
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void addGrade() throws Exception {
        mockMvc.perform(post("/addGrade")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("lecture", "Mathe")
                        .param("grade", "1.1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("grades"));
}


}