package com.backend.as.glady.services;

import com.backend.as.glady.model.UserBalanceDto;

public interface IUserService {


	/**
	 * Return the total user's balance
	 * @param idUser
	 * @return UserBalanceDto
	 */
	public UserBalanceDto calculateUserSBalance(Long idUser);
}
