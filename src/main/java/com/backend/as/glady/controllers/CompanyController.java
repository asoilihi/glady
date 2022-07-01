package com.backend.as.glady.controllers;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.as.glady.model.UserDto;
import com.backend.as.glady.model.WalletUserDto;
import com.backend.as.glady.services.impl.CompanyService;

/**
 * @author asoilihi
 *
 */
@RestController
@RequestMapping("/api/company")
public class CompanyController {

	@Inject
	private CompanyService companyService;

	/**
	 * Service to distribute gift or meal deposit to user
	 * 
	 * @param walletUserDto
	 * @param idCompany
	 * @param idUser
	 * @return UserDto
	 */
	@PostMapping("/{idCompany}/user/{idUser}")
	public ResponseEntity<UserDto> addGiftOrMealDepositToUserSWallet(
			@RequestBody @Validated WalletUserDto walletUserDto, @PathVariable("idCompany") Long idCompany,
			@PathVariable("idUser") Long idUser) {

		return new ResponseEntity<>(companyService.distributeGiftOrMealDepositToUser(idCompany, idUser, walletUserDto),
				HttpStatus.CREATED);

	}

}
