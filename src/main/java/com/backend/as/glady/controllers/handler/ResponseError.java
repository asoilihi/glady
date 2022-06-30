package com.backend.as.glady.controllers.handler;

import lombok.Builder;
import lombok.Data;

/**
 * @author asoilihi
 * 
 * Formatting the error message
 */
@Data
@Builder
public class ResponseError {

	private Integer status;
	private String message;
	
}
