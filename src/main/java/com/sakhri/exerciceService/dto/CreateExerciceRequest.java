package com.sakhri.exerciceService.dto;


import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sakhri.exerciceService.enums.Muscle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateExerciceRequest implements Serializable{
	

	private static final long serialVersionUID = -1726376997855041362L;
	
	@NotBlank(message="Exercice name cannot be null")
	@Size(min=2, message= "Exercice name must not be less than two characters")
	private String name;
	
	private String description;
	
	@NotNull(message="Muscle name cannot be null")
	private Muscle muscle;
}
