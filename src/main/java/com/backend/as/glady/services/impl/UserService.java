/**
 * 
 */
package com.backend.as.glady.services.impl;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.backend.as.glady.data.entities.User;
import com.backend.as.glady.data.entities.WalletUser;
import com.backend.as.glady.data.repositories.UserRepository;
import com.backend.as.glady.model.UserBalanceDto;
import com.backend.as.glady.model.UserBalanceDto.UserBalanceDtoBuilder;
import com.backend.as.glady.services.IUserService;
import com.backend.as.glady.utils.CategoryEnum;
import com.backend.as.glady.utils.Constantes;

/**
 * @author asoilihi
 *
 */
@Service
public class UserService implements IUserService {

	@Inject
	private UserRepository userRepository;

	/**
	 * 
	 */
	public UserService() {
		// Nothing to do here
	}

	@Override
	public UserBalanceDto calculateUserSBalance(Long idUser) {

		Optional<User> user = verifyIfUserIsPresent(idUser);
		UserBalanceDtoBuilder userBalanceDtoBuilder = UserBalanceDto.builder();
		// Initialize
		userBalanceDtoBuilder.totalBalanceGifts(0L);
		userBalanceDtoBuilder.totalBalanceMeals(0L);

		if (user.get().getWalletUser() != null) {

			// calculate total amount of gifts deposits
			userBalanceDtoBuilder.totalBalanceGifts(user.get().getWalletUser().stream().filter(
					w -> CategoryEnum.GIFT.name().equals(w.getCategory()) && dateGiftDepositIsValid(w.getCreatedDate()))
					.collect(Collectors.summarizingInt(WalletUser::getBalance)).getSum());

			// calculate total amount of meals deposits
			userBalanceDtoBuilder.totalBalanceMeals(user.get().getWalletUser().stream().filter(
					w -> CategoryEnum.MEAL.name().equals(w.getCategory()) && dateMealDepositIsValid(w.getCreatedDate()))
					.collect(Collectors.summarizingInt(WalletUser::getBalance)).getSum());

		}

		return userBalanceDtoBuilder.build();
	}

	/**
	 * @param createdDate
	 * @return boolean
	 */
	private boolean dateMealDepositIsValid(LocalDate createdDate) {
		int year = createdDate.getYear() + Constantes.ONE_FOR_NEXT_YEAR_1;
		LocalDate deadLineDate = LocalDate.of(year, Month.FEBRUARY,
				isLeapYear(year) ? Constantes.DAYS_FEB_LEAP_YEAR_29 : Constantes.DAYS_FEB_28);
		return deadLineDate.isAfter(LocalDate.now());
	}

	/**
	 * verify if the year in param is leap year
	 * 
	 * @param year
	 * @return boolean
	 */
	private boolean isLeapYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);

		return calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > Constantes.LIFE_SPAN_DAYS_365;
	}

	/**
	 * Compare the created date to the deadline date of gift deposit (1 year)
	 * 
	 * @param createdDate
	 * @return boolean
	 */
	private boolean dateGiftDepositIsValid(LocalDate createdDate) {
		LocalDate deadLineDate = createdDate.plusDays(Constantes.LIFE_SPAN_DAYS_365);
		return deadLineDate.isAfter(LocalDate.now());
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
