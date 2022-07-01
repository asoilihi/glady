package com.backend.as.glady.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.backend.as.glady.data.repositories.WalletRepository;
import com.backend.as.glady.mappers.GladyMapper;
import com.backend.as.glady.model.UserDto;
import com.backend.as.glady.model.WalletUserDto;
import com.backend.as.glady.services.impl.CompanyService;
import com.backend.as.glady.utils.CategoryEnum;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

	@InjectMocks
	private CompanyService companyService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private GladyMapper gladyMapper;

	private WalletUserDto walletUserDto;

	@Mock
	private WalletRepository walletUserRepository;

	@BeforeEach
	void setUp() {
		walletUserDto = WalletUserDto.builder().id(1L).balance(200).createdDate(LocalDate.now()).build();
	}

	@Test
	void testVerifyIfUserNotExistWeThrowEntityNotFoundException() {
		Optional<User> user = Optional.empty();
		// when
		when(userRepository.findById(65656L)).thenReturn(user);

		EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class,
				() -> companyService.distributeGiftOrMealDepositToUser(1L, 65656L, walletUserDto));

		assertNotNull(entityNotFoundException.getMessage());
		assertEquals(entityNotFoundException.getMessage(), "User with id: " + 65656L + " not found");
	}

	@Test
	void testWhenUserNotAffiliatedToCompanyThrowIllegalArgumentException() {
		// given

		Optional<User> user = Optional.of(User.builder().company(Company.builder().id(3L).build()).id(65656L).build());
		// when
		when(userRepository.findById(65656L)).thenReturn(user);

		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
				() -> companyService.distributeGiftOrMealDepositToUser(1L, 65656L, walletUserDto));

		assertNotNull(illegalArgumentException.getMessage());
		assertEquals(illegalArgumentException.getMessage(),
				"The user with id: " + 65656L + " is not affiliated to this company id: " + 1L);
	}

	@Test
	void testWhenBalanceCompanyAmountIsLowerThanTheRequestAmountToGiveToUserThrowUnsupportedOperationException() {

		// given

		walletUserDto.setBalance(2000);
		Optional<User> user = Optional
				.of(User.builder().company(Company.builder().id(1L).balance(1000L).build()).id(65656L).build());
		// when
		when(userRepository.findById(65656L)).thenReturn(user);

		UnsupportedOperationException unsupportedOperationException = assertThrows(UnsupportedOperationException.class,
				() -> companyService.distributeGiftOrMealDepositToUser(1L, 65656L, walletUserDto));

		assertNotNull(unsupportedOperationException.getMessage());
		assertThat(unsupportedOperationException.getMessage()).isEqualTo(
				"The amount requested: " + walletUserDto.getBalance() + " is greater than the total amount of company");
	}

	@Test
	void testReturnWalletUserSaved() {

		// given

		User userObj = User.builder().company(Company.builder().id(1L).balance(1000L).build())
				.walletUser(new ArrayList<>()).id(65656L).build();
		Optional<User> user = Optional.of(userObj);
		WalletUser walletUser1 = WalletUser.builder().balance(200).category(CategoryEnum.GIFT.name())
				.createdDate(LocalDate.now()).build();

		UserDto userDto = UserDto.builder().walletUser(new ArrayList<>()).id(2L).build();
		// when
		when(gladyMapper.userToUserDto(userObj)).thenReturn(userDto);

		when(gladyMapper.walletUserDtoToWalletUser(walletUserDto)).thenReturn(walletUser1);

		when(userRepository.findById(65656L)).thenReturn(user);
		when(walletUserRepository.save(walletUser1)).thenReturn(walletUser1);

		UserDto userDtoSaved = companyService.distributeGiftOrMealDepositToUser(1L, 65656L, walletUserDto);

		assertNotNull(userDtoSaved);
		assertNotNull(userDtoSaved.getWalletUser());
		assertNotNull(userDtoSaved.getId());
	}

}
