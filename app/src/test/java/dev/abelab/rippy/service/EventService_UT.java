package dev.abelab.rippy.service;

import mockit.Injectable;
import mockit.Tested;

import org.modelmapper.ModelMapper;

import dev.abelab.rippy.repository.EventRepository;

/**
 * UserService Unit Test
 */
class EventService_UT extends AbstractService_UT {

	@Tested
	ModelMapper modelMapper;

	@Injectable
	EventRepository eventRepository;

	@Tested
	EventService eventService;

}
