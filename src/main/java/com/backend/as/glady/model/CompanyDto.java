package com.backend.as.glady.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;

@Data
@JsonDeserialize
@Builder
public class CompanyDto {
	
	private Long id; 
	private String name;
	private Long balance;

}
