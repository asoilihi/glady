package com.backend.as.glady.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.backend.as.glady.data.entities.Company;
import com.backend.as.glady.data.entities.User;
import com.backend.as.glady.data.entities.WalletUser;
import com.backend.as.glady.model.CompanyDto;
import com.backend.as.glady.model.UserDto;
import com.backend.as.glady.model.WalletUserDto;

/**
 * @author asoilihi Glady Mapper class for convrting entities to dtos vice versa
 *
 */
@Mapper
public interface GladyMapper {

	GladyMapper INSTANCE = Mappers.getMapper(GladyMapper.class);

	WalletUser walletUserDtoToWalletUser(WalletUserDto walletUserDto);
	@Mapping(source = "walletUser.category", target = "category")
	WalletUserDto walletUserToWalletUserDto(WalletUser walletUser);

	CompanyDto companyToCompanyDto(Company company);

	Company companyDtoToCompany(CompanyDto company);

	UserDto userToUserDto(User user);

	List<UserDto> usersToUsersDto(List<User> users);

	User userDtoToUser(UserDto userDto);

}
