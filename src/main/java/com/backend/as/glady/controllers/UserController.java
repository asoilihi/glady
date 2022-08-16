package com.backend.as.glady.controllers;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.as.glady.model.UserBalanceDto;
import com.backend.as.glady.services.impl.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Inject
	private UserService userService;

	/**
	 * Calculate totals balances of user in param
	 * 
	 * @param idUser
	 * @return
	 */
	@GetMapping("/{idUser}/totalBalance")
	public ResponseEntity<UserBalanceDto> calculateTotalBalanceOfUser(@PathVariable("idUser") Long idUser) {

		return new ResponseEntity<>(userService.calculateUserSBalance(idUser), HttpStatus.OK);

	}
	

}
