package com.backend.as.glady.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;

@Data
@JsonDeserialize
@Builder
public class UserDto {
	
	private Long id;
	private String name;
	private CompanyDto company;
	private List<WalletUserDto> walletUser;

}
