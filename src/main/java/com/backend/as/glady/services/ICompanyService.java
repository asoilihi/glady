package com.backend.as.glady.services;

import com.backend.as.glady.model.UserDto;
import com.backend.as.glady.model.WalletUserDto;

public interface ICompanyService {

	/**
	 * Method user by company to attribute Meal or Gift deposit to User
	 * 
	 * @param idCompany
	 * @param idUser
	 * @param balanceUserDto
	 * @return UserDto
	 */
	public UserDto distributeGiftOrMealDepositToUser(Long idCompany, Long idUser, WalletUserDto balanceUserDto);
	
}
