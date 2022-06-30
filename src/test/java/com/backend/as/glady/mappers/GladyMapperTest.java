package com.backend.as.glady.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.backend.as.glady.data.entities.WalletUser;
import com.backend.as.glady.model.UserDto;
import com.backend.as.glady.model.WalletUserDto;
import com.backend.as.glady.data.entities.Company;
import com.backend.as.glady.data.entities.User;
import com.backend.as.glady.utils.CategoryEnum;

class GladyMapperTest {

	private User user;
	private WalletUser walUser1;
	private Company company;

	// given

	@BeforeEach
	public void setUp() {

		company = Company.builder().balance(10000L).name("Glady").build();
		walUser1 = WalletUser.builder().balance(100).category(CategoryEnum.GIFT.name()).build();

		List<WalletUser> balancesUser = new ArrayList<WalletUser>();
		user = User.builder().company(company).name("Said").walletUser(balancesUser).build();
	}

	@Test
	void testConvertUserToUserDtoVerifyIfWeHaveAllSubObjects() {
		// when
		UserDto userDto = GladyMapper.INSTANCE.userToUserDto(user);

		// then
		assertThat(userDto).isNotNull();
		assertThat(userDto.getCompany().getName()).isEqualTo(user.getCompany().getName());
		assertThat(userDto.getCompany().getBalance()).isEqualTo(user.getCompany().getBalance());
		assertThat(userDto.getWalletUser()).isNotNull();
	}

	@Test
	void testConvertWalletUserToWalletUserDtoVerifyIfWeHaveAllSubObjects() {
		walUser1.setUser(user);
		// when
		WalletUserDto walletUserDto = GladyMapper.INSTANCE.walletUserToWalletUserDto(walUser1);

		// then
		assertThat(walletUserDto).isNotNull();
		assertThat(walletUserDto.getBalance()).isEqualTo(walUser1.getBalance());
		assertThat(walletUserDto.getCategory()).isNotNull();
		assertThat(walletUserDto.getCategory()).isEqualTo(CategoryEnum.GIFT);

	}

}
