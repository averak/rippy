package dev.abelab.rippy.service;

import mockit.Injectable;
import mockit.Tested;

import org.modelmapper.ModelMapper;

import dev.abelab.rippy.repository.UserRepository;

/**
 * UserService Unit Test
 */
class UserService_UT extends AbstractService_UT {

	@Injectable
	UserRepository userRepository;

	@Tested
	ModelMapper modelMapper;

	@Tested
	UserService userService;

}
