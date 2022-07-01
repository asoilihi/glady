package com.backend.as.glady.model;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.backend.as.glady.utils.CategoryEnum;
import com.backend.as.glady.utils.Constantes;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
public class WalletUserDto {

	@JsonIgnore
	private Long id;
	
	@NotNull(message = Constantes.BALANCE_VALUE_IS_REQUIRED)
	@Min(value = 1, message = Constantes.MIN_VALUE_IS_1)
	private Integer balance;
	
	@Schema(description = Constantes.CATEGORY_IS_ENUM_WITH_2_POSSIBLE_VALUES_GIFT_MEAL)
	@NotNull(message = Constantes.CATEGORY_VALUE_IS_REQUIRED_VALID_VALUES_GIFT_MEAL)
	private CategoryEnum category;
	
	@JsonFormat(pattern = Constantes.YYYY_MM_DD)
	private LocalDate createdDate;

}
