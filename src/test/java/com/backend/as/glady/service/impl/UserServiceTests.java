package com.backend.as.glady.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.as.glady.data.entities.Company;
import com.backend.as.glady.data.entities.User;
import com.backend.as.glady.data.entities.WalletUser;
import com.backend.as.glady.data.repositories.UserRepository;
import com.backend.as.glady.model.UserBalanceDto;
import com.backend.as.glady.services.impl.UserService;
import com.backend.as.glady.utils.CategoryEnum;
@ExtendWith(MockitoExtension.class)
class UserServiceTests {
	
	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;
	
	private WalletUser walletUserGift;

	private WalletUser walletUserMeal;

	private User user;
	
	private DateTimeFormatter dateFormatter;

	
	@BeforeEach
	void setUp() {
		dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		walletUserGift = WalletUser.builder().balance(200).category(CategoryEnum.GIFT.name()).id(3L)
				.createdDate(LocalDate.from(dateFormatter.parse("2021-04-01"))).build();

		walletUserMeal = WalletUser.builder().balance(187).category(CategoryEnum.MEAL.name()).id(10L)
				.createdDate(LocalDate.now()).build();

	}
	
	@Test
	void testcalculateUserSBalanceWithUserThenThrowEntityNotFoundException() {
		// given
		Optional<User> user = Optional.empty();
		// when
		when(userRepository.findById(65656L)).thenReturn(user);

		EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class,
				() -> userService.calculateUserSBalance(65656L));
		// then
		assertNotNull(entityNotFoundException.getMessage());
		assertEquals(entityNotFoundException.getMessage(), "User with id: " + 65656L + " not found");

	}

	@Test
	void testcalculateUserSBalanceWithEmptyWalletUserThenReturnZeros() {

		// given
		Company glady = Company.builder().balance(200000L).id(1L).name("Glady").build();
		List<WalletUser> listWallet = new ArrayList<WalletUser>();
		user = User.builder().id(1L).name("Toto").company(glady).walletUser(listWallet).build();
		Optional<User> userOpt = Optional.of(user);

		// when
		when(userRepository.findById(1L)).thenReturn(userOpt);

		UserBalanceDto userBalanceDto = userService.calculateUserSBalance(1L);

		// then
		assertThat(userBalanceDto).isNotNull();
		assertThat(userBalanceDto.getTotalBalanceGifts()).isZero();
		assertThat(userBalanceDto.getTotalBalanceMeals()).isZero();

	}

	@Test
	void testcalculateUserSBalanceWithWalletUserIsNullThenReturnZeros() {

		// given
		Company glady = Company.builder().balance(200000L).id(1L).name("Glady").build();
		List<WalletUser> listWallet = null;
		user = User.builder().id(1L).name("Toto").company(glady).walletUser(listWallet).build();
		Optional<User> userOpt = Optional.of(user);

		// when
		when(userRepository.findById(1L)).thenReturn(userOpt);

		UserBalanceDto userBalanceDto = userService.calculateUserSBalance(1L);

		// then
		assertThat(userBalanceDto).isNotNull();
		assertThat(userBalanceDto.getTotalBalanceGifts()).isZero();
		assertThat(userBalanceDto.getTotalBalanceMeals()).isZero();

	}

	@Test
	void testcalculateUserSBalanceWithInvalidDateWalletGiftUserThenReturnZeroForGiftAndValueForMeals() {

		// given
		Company glady = Company.builder().balance(200000L).id(1L).name("Glady").build();
		List<WalletUser> listWallet = new ArrayList<WalletUser>();
		listWallet.add(walletUserGift);
		listWallet.add(walletUserMeal);
		user = User.builder().id(1L).name("Toto").company(glady).walletUser(listWallet).build();
		Optional<User> userOpt = Optional.of(user);

		// when
		when(userRepository.findById(1L)).thenReturn(userOpt);

		UserBalanceDto userBalanceDto = userService.calculateUserSBalance(1L);

		// then
		assertThat(userBalanceDto).isNotNull();
		assertThat(userBalanceDto.getTotalBalanceGifts()).isZero();
		assertThat(userBalanceDto.getTotalBalanceMeals()).isEqualTo(walletUserMeal.getBalance().longValue());

	}

	@Test
	void testcalculateUserSBalanceWithInvalidWalletMealsUserThenReturnZeroForMealsAndValueForGifts() {

		// given

		Company glady = Company.builder().balance(200000L).id(1L).name("Glady").build();
		List<WalletUser> listWallet = new ArrayList<WalletUser>();
		walletUserGift.setCreatedDate(LocalDate.now());
		walletUserMeal.setCreatedDate(LocalDate.from(dateFormatter.parse("2021-04-01")));
		listWallet.add(walletUserGift);
		listWallet.add(walletUserMeal);
		user = User.builder().id(1L).name("Toto").company(glady).walletUser(listWallet).build();
		Optional<User> userOpt = Optional.of(user);

		// when
		when(userRepository.findById(1L)).thenReturn(userOpt);

		UserBalanceDto userBalanceDto = userService.calculateUserSBalance(1L);

		// then
		assertThat(userBalanceDto).isNotNull();
		assertThat(userBalanceDto.getTotalBalanceGifts()).isEqualTo(200L);
		assertThat(userBalanceDto.getTotalBalanceMeals()).isZero();

	}

	@Test
	void testcalculateUserSBalanceWithWalletGiftUserThenReturnTwoTotalsForGiftAndValueForMeals() {

		// given

		Company glady = Company.builder().balance(200000L).id(1L).name("Glady").build();
		List<WalletUser> listWallet = new ArrayList<WalletUser>();
		walletUserGift.setCreatedDate(LocalDate.now());
		walletUserMeal.setCreatedDate(LocalDate.now());
		listWallet.add(walletUserGift);
		WalletUser walletUserGift1 = WalletUser.builder().balance(120).createdDate(LocalDate.now())
				.category(CategoryEnum.GIFT.name()).build();
		listWallet.add(walletUserGift1);
		listWallet.add(walletUserMeal);
		WalletUser walletUserMeal1 = WalletUser.builder().balance(70).createdDate(LocalDate.now())
				.category(CategoryEnum.MEAL.name()).build();
		listWallet.add(walletUserMeal1);
		user = User.builder().id(1L).name("Toto").company(glady).walletUser(listWallet).build();
		Optional<User> userOpt = Optional.of(user);

		// when
		when(userRepository.findById(1L)).thenReturn(userOpt);

		UserBalanceDto userBalanceDto = userService.calculateUserSBalance(1L);

		// then
		assertThat(userBalanceDto).isNotNull();
		assertThat(userBalanceDto.getTotalBalanceGifts()).isEqualTo(320L);
		assertThat(userBalanceDto.getTotalBalanceMeals()).isEqualTo(257L);

	}

}
