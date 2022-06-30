package com.backend.as.glady.model;

import com.backend.as.glady.model.UserBalanceDto.UserBalanceDtoBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Data;

/**
 * @author asoilihi
 *
 */
@Data
@Builder
@JsonDeserialize(builder = UserBalanceDtoBuilder.class)
public class UserBalanceDto {

	private Long totalBalanceGifts;
	private Long totalBalanceMeals;

	/**
	 * @author asoilihi
	 *
	 */
	@JsonPOJOBuilder(withPrefix = "")
	public static class UserBalanceDtoBuilder {

	}

}

