package com.backend.as.glady.services.impl;

import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.backend.as.glady.data.entities.User;
import com.backend.as.glady.data.entities.WalletUser;
import com.backend.as.glady.data.repositories.UserRepository;
import com.backend.as.glady.mappers.GladyMapper;
import com.backend.as.glady.model.UserDto;
import com.backend.as.glady.model.WalletUserDto;
import com.backend.as.glady.services.ICompanyService;

@Service
public class CompanyService implements ICompanyService {

	@Inject
	private GladyMapper gladyMapper;

	@Inject
	private UserRepository userRepository;

	/**
	 * it is a constructor
	 */
	public CompanyService() {
		// nothing here
	}

	@Override
	public UserDto distributeGiftOrMealDepositToUser(Long idCompany, Long idUser, WalletUserDto walletUserDto) {

		Optional<User> user = verifyIfUserIsPresent(idUser);

		verifyIfUserAffiliatedToThisCompany(idCompany, idUser, user);

		verifyIfAmountCompanyBalanceIsEnoughForOperation(walletUserDto, user);

		// We add the meal or gift deposit and save it to user

		WalletUser walletUser = gladyMapper.walletUserDtoToWalletUser(walletUserDto);
		
		user.get().getWalletUser().add(walletUser);

		return gladyMapper.userToUserDto(userRepository.save(user.get()));
	}

	/**
	 * @param walletUserDto
	 * @param user
	 */
	private void verifyIfAmountCompanyBalanceIsEnoughForOperation(WalletUserDto walletUserDto, Optional<User> user) {
		// verify if the company balance allows this transaction
		if (user.isPresent() && user.get().getCompany().getBalance() < walletUserDto.getBalance()) {
			throw new UnsupportedOperationException(
					"The mount request" + walletUserDto.getBalance() + " is greater than the total amount of company");
		}
	}

	/**
	 * @param idCompany
	 * @param idUser
	 * @param user
	 */
	private void verifyIfUserAffiliatedToThisCompany(Long idCompany, Long idUser, Optional<User> user) {
		// verify if user is affiliated to company
		if (user.isPresent() && !user.get().getCompany().getId().equals(idCompany)) {
			throw new IllegalArgumentException(
					"The user with id: " + idUser + " is not affiliated to this company id: " + idCompany);
		}
	}

	/**
	 * @param idUser
	 * @return Optional<User>
	 */
	private Optional<User> verifyIfUserIsPresent(Long idUser) {
		Optional<User> user = userRepository.findById(idUser);
		// verify if user exist
		if (!user.isPresent()) {
			throw new EntityNotFoundException("User with id: " + idUser + " not found");
		}
		return user;
	}

	

}
