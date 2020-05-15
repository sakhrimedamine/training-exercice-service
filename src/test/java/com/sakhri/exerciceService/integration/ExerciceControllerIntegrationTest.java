package com.sakhri.exerciceService.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakhri.exerciceService.dto.ApiResponseDto;
import com.sakhri.exerciceService.dto.CreateExerciceRequest;
import com.sakhri.exerciceService.enums.Muscle;
import com.sakhri.exerciceService.service.ExerciceService;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
class ExerciceControllerIntegrationTest {

	@Autowired
	private ExerciceService exerciceService; 

    private static MockMvc mvc;
    
    @BeforeAll
    public static void init(WebApplicationContext context) {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
    
    @AfterEach
    public void cleanDB() {
    	exerciceService.deleteAllExercices();
    }
    
    
	@Test
	void whenCreateExerciceWithValidDataThenReturnTrue() throws Exception {
		
		// Given
		
		CreateExerciceRequest ex = CreateExerciceRequest.builder()
										.name("ex1")
										.description("desc1")
										.muscle(Muscle.BICEPS)
										.build();		
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(ex);
    	
		// When
		MvcResult mvcResult= mvc.perform(post("/exercices")
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(json)
							).andReturn();

		// then
		String responseAsString = mvcResult.getResponse().getContentAsString();

		Boolean myResponse = mapper.readValue(responseAsString, Boolean.class);

		
		// Then
		assertTrue(myResponse);
	}
	
	@Test
	void whenCreateExerciceWithNonValidDataThenReturn400Status() throws Exception {
		
		// Given
		
		CreateExerciceRequest ex = CreateExerciceRequest.builder()
//										.name("ex1")
										.description("desc1")
										.muscle(Muscle.BICEPS)
										.build();		
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(ex);
    	
		// When
		MvcResult mvcResult= mvc.perform(post("/exercices")
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(json)
							).andReturn();

		// then

		String responseAsString = mvcResult.getResponse().getContentAsString();
		
		ObjectMapper objectMapper = new ObjectMapper(); 
		
		ApiResponseDto myResponse = objectMapper.readValue(responseAsString, ApiResponseDto.class);
	
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
		
		assertThat(myResponse.getMessage()).contains("Exercice name cannot be null");
	}

}
