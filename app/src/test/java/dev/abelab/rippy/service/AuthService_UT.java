package dev.abelab.rippy.service;

import mockit.Injectable;
import mockit.Tested;

import dev.abelab.rippy.repository.UserRepository;

/**
 * AuthService Unit Test
 */
class AuthService_UT extends AbstractService_UT {

	@Injectable
	UserRepository userRepository;

	@Tested
	AuthService authService;

}
