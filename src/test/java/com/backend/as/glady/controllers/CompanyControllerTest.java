package com.backend.as.glady.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.hamcrest.Matchers;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.backend.as.glady.model.CompanyDto;
import com.backend.as.glady.model.UserDto;
import com.backend.as.glady.model.WalletUserDto;
import com.backend.as.glady.services.impl.CompanyService;
import com.backend.as.glady.utils.CategoryEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

	@MockBean
	CompanyService companyService;

	@Autowired
	MockMvc mockMvc;

	@Test
	void testcalculateTotalBalanceOfUserReturnUserBalanceDtoWithTotalBalance() throws Exception {

		// given

		WalletUserDto walletDto = WalletUserDto.builder().category(CategoryEnum.GIFT)
				.createdDate(LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse("2022-06-30"))).balance(100)
				.build();
		List<WalletUserDto> listeWallet = new ArrayList<WalletUserDto>();
		listeWallet.add(walletDto);
		CompanyDto companyDto = CompanyDto.builder().balance(300000L).name("Glady").id(30L).build();
		UserDto userDto = UserDto.builder().walletUser(listeWallet).company(companyDto).build();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String json = objectMapper.writeValueAsString(walletDto);

		// when

		Mockito.when(companyService.distributeGiftOrMealDepositToUser(1L, 1L, walletDto)).thenReturn(userDto);

		// then

		mockMvc.perform(post("/api/company/1/user/1").contentType(MediaType.APPLICATION_JSON).content(json)
				.characterEncoding("utf-8")).andExpect(status().isCreated())
				.andExpect(jsonPath("id", Matchers.is(userDto.getId())))
				.andExpect(jsonPath("company.name", Matchers.is(userDto.getCompany().getName())))
				.andExpect(jsonPath("company.balance", Matchers.is(userDto.getCompany().getBalance().intValue())));
	}

	@Test
	void testcalculateTotalBalanceOfUserReturnUserThenThrowNotFoundException() throws Exception {

		// given

		WalletUserDto walletDto = WalletUserDto.builder().category(CategoryEnum.GIFT)
				.createdDate(LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse("2022-06-30"))).balance(100)
				.build();
		List<WalletUserDto> listeWallet = new ArrayList<WalletUserDto>();
		listeWallet.add(walletDto);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String json = objectMapper.writeValueAsString(walletDto);

		// when

		Mockito.when(companyService.distributeGiftOrMealDepositToUser(1L, 1L, walletDto))
				.thenThrow(new EntityNotFoundException("User with id: 1 not found"));

		// then

		mockMvc.perform(post("/api/company/1/user/1").contentType(MediaType.APPLICATION_JSON).content(json)
				.characterEncoding("utf-8")).andExpect(status().isNotFound())
				.andExpect(jsonPath("status", Matchers.is(HttpStatus.NOT_FOUND.value())))
				.andExpect(jsonPath("message", Matchers.is("User with id: 1 not found")));
	}

	@Test
	void testcalculateTotalBalanceOfUserReturnUserThenThrowIllegalArgumentException() throws Exception {

		// given

		WalletUserDto walletDto = WalletUserDto.builder().category(CategoryEnum.GIFT)
				.createdDate(LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse("2022-06-30"))).balance(100)
				.build();
		List<WalletUserDto> listeWallet = new ArrayList<WalletUserDto>();
		listeWallet.add(walletDto);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String json = objectMapper.writeValueAsString(walletDto);

		// when

		Mockito.when(companyService.distributeGiftOrMealDepositToUser(1L, 1L, walletDto))
				.thenThrow(new IllegalArgumentException("The user with id: 1 is not affiliated to this company id: 1"));

		// then

		mockMvc.perform(post("/api/company/1/user/1").contentType(MediaType.APPLICATION_JSON).content(json)
				.characterEncoding("utf-8")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("status", Matchers.is(HttpStatus.BAD_REQUEST.value()))).andExpect(jsonPath(
						"message", Matchers.is("The user with id: 1 is not affiliated to this company id: 1")));
	}

	@Test
	void testcalculateTotalBalanceOfUserReturnUserThenThrowUnsupportedOperationException() throws Exception {

		// given

		WalletUserDto walletDto = WalletUserDto.builder().category(CategoryEnum.GIFT)
				.createdDate(LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse("2022-06-30")))
				.balance(100000).build();
		List<WalletUserDto> listeWallet = new ArrayList<WalletUserDto>();
		listeWallet.add(walletDto);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String json = objectMapper.writeValueAsString(walletDto);

		// when

		Mockito.when(companyService.distributeGiftOrMealDepositToUser(1L, 1L, walletDto))
				.thenThrow(new UnsupportedOperationException(
						"The mount request 100000 is greater than the total amount of company"));

		// then

		mockMvc.perform(post("/api/company/1/user/1").contentType(MediaType.APPLICATION_JSON).content(json)
				.characterEncoding("utf-8")).andExpect(status().isNotAcceptable())
				.andExpect(jsonPath("status", Matchers.is(HttpStatus.NOT_ACCEPTABLE.value())))
				.andExpect(jsonPath("message",
						Matchers.is("The mount request 100000 is greater than the total amount of company")));
	}

	@Test
	void testcalculateTotalBalanceOfUserReturnUserThenThrowConstraintViolationException() throws Exception {

		// given

		WalletUserDto walletDto = WalletUserDto.builder().category(CategoryEnum.GIFT)
				.createdDate(LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse("2022-06-30")))
				.balance(100000).build();
		List<WalletUserDto> listeWallet = new ArrayList<WalletUserDto>();
		listeWallet.add(walletDto);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String json = objectMapper.writeValueAsString(walletDto);

		// when

		Mockito.when(companyService.distributeGiftOrMealDepositToUser(1L, 1L, walletDto))
				.thenThrow(new ConstraintViolationException("Constraint violation on thi primary key", null, ""));

		// then

		mockMvc.perform(post("/api/company/1/user/1").contentType(MediaType.APPLICATION_JSON).content(json)
				.characterEncoding("utf-8")).andExpect(status().isConflict())
				.andExpect(jsonPath("status", Matchers.is(HttpStatus.CONFLICT.value())))
				.andExpect(jsonPath("message", Matchers.is("Constraint violation on thi primary key")));
	}

}
