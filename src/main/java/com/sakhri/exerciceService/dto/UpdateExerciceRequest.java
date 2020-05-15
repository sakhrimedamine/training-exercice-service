package com.sakhri.exerciceService.dto;


import java.io.Serializable;

import com.sakhri.exerciceService.enums.Muscle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateExerciceRequest implements Serializable{
	
	
	private static final long serialVersionUID = -7606163963483841465L;

	private String exerciceId;

	private String name;
	
	private String description;
	
	private Muscle muscle;
}
