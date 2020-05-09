package com.sakhri.exerciceService.dto;


import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sakhri.exerciceService.enums.Muscle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExerciceDto implements Serializable{
	
	private static final long serialVersionUID = 8098665928043753217L;
	
	private Long id;
	
	@NotBlank(message="Le nom de l'exercice ne peut pas etre vide")
	private String name;
	
	@NotBlank(message="La description de l'exercice ne peut pas etre vide")
	private String description;
	
	@NotNull(message="Le muscle de l'exercice ne peut pas etre vide")
	private Muscle muscle;
}
