package com.backend.as.glady.model;

import java.time.LocalDate;

import com.backend.as.glady.utils.CategoryEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletUserDto {

	@JsonIgnore
	private Long id;
	private Integer balance;
	@Schema(description = "category is Enum with 2 possible values: 'GIFT' & 'MEAL'")
	private CategoryEnum category;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate createdDate;


}
