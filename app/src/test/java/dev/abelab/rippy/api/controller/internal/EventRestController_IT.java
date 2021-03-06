package dev.abelab.rippy.api.controller.internal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;

import dev.abelab.rippy.api.controller.AbstractRestController_IT;
import dev.abelab.rippy.db.entity.UserSample;
import dev.abelab.rippy.db.entity.EventSample;
import dev.abelab.rippy.db.entity.EventDateSample;
import dev.abelab.rippy.db.entity.EventAnswerSample;
import dev.abelab.rippy.db.entity.EventDateAnswerSample;
import dev.abelab.rippy.enums.UserRoleEnum;
import dev.abelab.rippy.model.EventDateModel;
import dev.abelab.rippy.model.EventOwnerModel;
import dev.abelab.rippy.model.EventMemberModel;
import dev.abelab.rippy.repository.UserRepository;
import dev.abelab.rippy.repository.EventRepository;
import dev.abelab.rippy.repository.EventDateRepository;
import dev.abelab.rippy.repository.EventAnswerRepository;
import dev.abelab.rippy.repository.EventDateAnswerRepository;
import dev.abelab.rippy.api.request.EventCreateRequest;
import dev.abelab.rippy.api.request.EventUpdateRequest;
import dev.abelab.rippy.api.response.EventResponse;
import dev.abelab.rippy.api.response.EventsResponse;
import dev.abelab.rippy.api.response.EventDetailResponse;
import dev.abelab.rippy.util.DateTimeUtil;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.BaseException;
import dev.abelab.rippy.exception.NotFoundException;
import dev.abelab.rippy.exception.BadRequestException;
import dev.abelab.rippy.exception.ForbiddenException;
import dev.abelab.rippy.exception.UnauthorizedException;

/**
 * EventRestController Integration Test
 */
public class EventRestController_IT extends AbstractRestController_IT {

	// API PATH
	static final String BASE_PATH = "/api/events";
	static final String GET_EVENTS_PATH = BASE_PATH;
	static final String GET_EVENT_PATH = BASE_PATH + "/%s";
	static final String CREATE_EVENT_PATH = BASE_PATH;
	static final String UPDATE_EVENT_PATH = BASE_PATH + "/%s";
	static final String DELETE_EVENT_PATH = BASE_PATH + "/%s";

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	EventDateRepository eventDateRepository;

	@Autowired
	EventAnswerRepository eventAnswerRepository;

	@Autowired
	EventDateAnswerRepository eventDateAnswerRepository;

	/**
	 * ????????????????????????API????????????
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class GetEventsTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void ???_???????????????????????????(final UserRoleEnum roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var events = Arrays.asList( //
				EventSample.builder().ownerId(loginUser.getId()).build(), //
				EventSample.builder().ownerId(loginUser.getId()).build() //
			);
			events.stream().forEach(eventRepository::insert);

			// ??????????????????
			final var eventDates1 = Arrays.asList( //
				EventDateSample.builder().eventId(events.get(0).getId()).dateOrder(1).build(), //
				EventDateSample.builder().eventId(events.get(0).getId()).dateOrder(2).build() //
			);
			final var eventDates2 = Arrays.asList( //
				EventDateSample.builder().eventId(events.get(1).getId()).dateOrder(1).build(), //
				EventDateSample.builder().eventId(events.get(1).getId()).dateOrder(2).build(), //
				EventDateSample.builder().eventId(events.get(1).getId()).dateOrder(3).build() //
			);
			eventDateRepository.bulkInsert(eventDates1);
			eventDateRepository.bulkInsert(eventDates2);

			// test
			final var request = getRequest(GET_EVENTS_PATH);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			final var response = execute(request, HttpStatus.OK, EventsResponse.class);

			// verify
			assertThat(response.getEvents().size()).isEqualTo(events.size());
			assertThat(response.getEvents()) //
				.extracting(EventResponse::getId, EventResponse::getName, EventResponse::getDescription) //
				.containsExactlyInAnyOrderElementsOf(
					events.stream().map(event -> tuple(event.getId(), event.getName(), event.getDescription())) //
						.collect(Collectors.toList()));
		}

		Stream<Arguments> ???_???????????????????????????() {
			return Stream.of( //
				// ?????????
				arguments(UserRoleEnum.ADMIN), //
				// ????????????
				arguments(UserRoleEnum.MEMBER) //
			);
		}

		@Test
		void ???_????????????????????????() throws Exception {
			// test
			final var request = getRequest(GET_EVENTS_PATH);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * ??????????????????API????????????
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class CreateEventTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void ???_?????????????????????(final UserRoleEnum roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			final var requestBody = modelMapper.map(event, EventCreateRequest.class);

			// ??????????????????
			final var eventDates = Arrays.asList( //
				EventDateModel.builder().dateOrder(1).startAt(DateTimeUtil.getDaysLater(2)).finishAt(DateTimeUtil.getDaysLater(3)).build(), //
				EventDateModel.builder().dateOrder(2).startAt(DateTimeUtil.getDaysLater(2)).finishAt(DateTimeUtil.getDaysLater(3)).build() //
			);
			requestBody.setDates(eventDates);

			// test
			final var request = postRequest(CREATE_EVENT_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.CREATED);

			// verify
			final var createdEvents = eventRepository.selectAllWithDates();
			assertThat(createdEvents.size()).isEqualTo(1);
			assertThat(createdEvents.get(0).getOwnerId()).isEqualTo(loginUser.getId());
			assertThat(createdEvents.get(0).getExpiredAt()).isInSameMinuteAs(event.getExpiredAt());
			assertThat(createdEvents.get(0).getDates().size()).isEqualTo(eventDates.size());
		}

		Stream<Arguments> ???_?????????????????????() {
			return Stream.of( //
				// ?????????
				arguments(UserRoleEnum.ADMIN), //
				// ????????????
				arguments(UserRoleEnum.MEMBER) //
			);
		}

		@ParameterizedTest
		@MethodSource
		void ??????????????????????????????????????????(final Date expiredAt, final BaseException exception) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(expiredAt) //
				.build();
			final var requestBody = modelMapper.map(event, EventCreateRequest.class);

			// ??????????????????
			final var eventDates = Arrays.asList( //
				EventDateModel.builder().dateOrder(1).startAt(DateTimeUtil.getDaysLater(2)).finishAt(DateTimeUtil.getDaysLater(3)).build(), //
				EventDateModel.builder().dateOrder(2).startAt(DateTimeUtil.getDaysLater(2)).finishAt(DateTimeUtil.getDaysLater(3)).build() //
			);
			requestBody.setDates(eventDates);

			// test
			final var request = postRequest(CREATE_EVENT_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			if (exception == null) {
				execute(request, HttpStatus.CREATED);
			} else {
				execute(request, exception);
			}
		}

		Stream<Arguments> ??????????????????????????????????????????() {
			return Stream.of( //
				// ???????????????
				arguments(DateTimeUtil.getYesterday(), new BadRequestException(ErrorCode.INVALID_EXPIRED_AT)), //
				// ???????????????
				arguments(DateTimeUtil.getTomorrow(), null) //
			);
		}

		@ParameterizedTest
		@MethodSource
		void ???_?????????????????????????????????(final Date expiredAt, final Date startAt, final Date finishAt) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(expiredAt) //
				.build();
			final var requestBody = modelMapper.map(event, EventCreateRequest.class);

			// ??????????????????
			final var eventDates = Arrays.asList( //
				EventDateModel.builder().dateOrder(1).startAt(startAt).finishAt(finishAt).build() //
			);
			requestBody.setDates(eventDates);

			// test
			final var request = postRequest(CREATE_EVENT_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new BadRequestException(ErrorCode.INVALID_EVENT_DATE));
		}

		Stream<Arguments> ???_?????????????????????????????????() {
			return Stream.of( //
				// ???????????????
				arguments(DateTimeUtil.getTomorrow(), DateTimeUtil.getYesterday(), DateTimeUtil.getTomorrow()), //
				// ????????????????????????????????????
				arguments(DateTimeUtil.getTomorrow(), DateTimeUtil.getDaysLater(3), DateTimeUtil.getDaysLater(2)), //
				// ?????????????????????
				arguments(DateTimeUtil.getNextWeek(), DateTimeUtil.getDaysLater(3), DateTimeUtil.getDaysLater(2)) //
			);
		}

		@ParameterizedTest
		@MethodSource
		void ??????????????????????????????????????????(final List<Integer> dateOrders, final BaseException exception) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			final var requestBody = modelMapper.map(event, EventCreateRequest.class);

			// ??????????????????
			final var eventDates = dateOrders.stream().map(dateOrder -> EventDateModel.builder() //
				.dateOrder(dateOrder).startAt(DateTimeUtil.getDaysLater(2)).finishAt(DateTimeUtil.getDaysLater(3)).build()) //
				.collect(Collectors.toList());
			requestBody.setDates(eventDates);

			// test
			final var request = postRequest(CREATE_EVENT_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			if (exception == null) {
				execute(request, HttpStatus.CREATED);
			} else {
				execute(request, exception);
			}
		}

		Stream<Arguments> ??????????????????????????????????????????() {
			return Stream.of( //
				// ??????
				arguments(Arrays.asList(1, 2, 3), null), //
				// 1???????????????????????????
				arguments(Arrays.asList(2, 3, 4), new BadRequestException(ErrorCode.INVALID_EVENT_DATE_ORDERS)), //
				// ????????????????????????
				arguments(Arrays.asList(1, 3, 4), new BadRequestException(ErrorCode.INVALID_EVENT_DATE_ORDERS)), //
				// ????????????????????????
				arguments(Arrays.asList(1, 2, 2), new BadRequestException(ErrorCode.INVALID_EVENT_DATE_ORDERS)) //
			);
		}

		@Test
		void ???_????????????????????????() throws Exception {
			// setup
			final var event = EventSample.builder().build();
			final var requestBody = modelMapper.map(event, EventCreateRequest.class);

			// test
			final var request = postRequest(CREATE_EVENT_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * ??????????????????API????????????
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class UpdateEventTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void ???_?????????????????????(UserRoleEnum roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			eventRepository.insert(event);

			// ???????????????????????????
			final var existsEventDates = Arrays.asList( //
				EventDateSample.builder().dateOrder(1).eventId(event.getId()).build(), //
				EventDateSample.builder().dateOrder(2).eventId(event.getId()).build(), //
				EventDateSample.builder().dateOrder(3).eventId(event.getId()).build() //
			);
			eventDateRepository.bulkInsert(existsEventDates);

			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);

			// ??????????????????
			final var eventDates = Arrays.asList( //
				EventDateModel.builder().dateOrder(1).startAt(DateTimeUtil.getDaysLater(2)).finishAt(DateTimeUtil.getDaysLater(3)).build(), //
				EventDateModel.builder().dateOrder(2).startAt(DateTimeUtil.getDaysLater(2)).finishAt(DateTimeUtil.getDaysLater(3)).build() //
			);
			requestBody.setDates(eventDates);

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, event.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.OK);

			// verify
			final var updatedEvents = eventRepository.selectAllWithDates();
			assertThat(updatedEvents.size()).isEqualTo(1);
			assertThat(updatedEvents.get(0).getOwnerId()).isEqualTo(loginUser.getId());
			assertThat(updatedEvents.get(0).getExpiredAt()).isInSameMinuteAs(event.getExpiredAt());
			assertThat(updatedEvents.get(0).getDates().size()).isEqualTo(eventDates.size());
		}

		Stream<Arguments> ???_?????????????????????() {
			return Stream.of( //
				// ?????????
				arguments(UserRoleEnum.ADMIN), //
				// ????????????
				arguments(UserRoleEnum.MEMBER) //
			);
		}

		@Test
		void ???_??????????????????????????????????????????() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var eventOwner = UserSample.builder().id(loginUser.getId() + 1).build();
			userRepository.insert(eventOwner);
			final var event = EventSample.builder() //
				.ownerId(eventOwner.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			eventRepository.insert(event);

			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);
			requestBody.setDates(Arrays.asList());

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, event.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION));
		}

		@ParameterizedTest
		@MethodSource
		void ??????????????????????????????????????????(final Date expiredAt, final BaseException exception) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			eventRepository.insert(event);

			event.setExpiredAt(expiredAt);
			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);
			requestBody.setDates(Arrays.asList());

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, event.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			if (exception == null) {
				execute(request, HttpStatus.OK);
			} else {
				execute(request, exception);
			}
		}

		Stream<Arguments> ??????????????????????????????????????????() {
			return Stream.of( //
				// ???????????????
				arguments(DateTimeUtil.getYesterday(), new BadRequestException(ErrorCode.INVALID_EXPIRED_AT)), //
				// ???????????????
				arguments(DateTimeUtil.getTomorrow(), null) //
			);
		}

		@ParameterizedTest
		@MethodSource
		void ??????????????????????????????????????????(final List<Integer> dateOrders, final BaseException exception) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			eventRepository.insert(event);

			final var requestBody = modelMapper.map(event, EventCreateRequest.class);

			// ??????????????????
			final var eventDates = dateOrders.stream().map(dateOrder -> EventDateModel.builder() //
				.dateOrder(dateOrder).startAt(DateTimeUtil.getDaysLater(2)).finishAt(DateTimeUtil.getDaysLater(3)).build()) //
				.collect(Collectors.toList());
			requestBody.setDates(eventDates);

			// test
			final var request = postRequest(CREATE_EVENT_PATH, requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			if (exception == null) {
				execute(request, HttpStatus.CREATED);
			} else {
				execute(request, exception);
			}
		}

		Stream<Arguments> ??????????????????????????????????????????() {
			return Stream.of( //
				// ??????
				arguments(Arrays.asList(1, 2, 3), null), //
				// 1???????????????????????????
				arguments(Arrays.asList(2, 3, 4), new BadRequestException(ErrorCode.INVALID_EVENT_DATE_ORDERS)), //
				// ????????????????????????
				arguments(Arrays.asList(1, 3, 4), new BadRequestException(ErrorCode.INVALID_EVENT_DATE_ORDERS)), //
				// ????????????????????????
				arguments(Arrays.asList(1, 2, 2), new BadRequestException(ErrorCode.INVALID_EVENT_DATE_ORDERS)) //
			);
		}

		@Test
		void ???_??????????????????????????????????????????() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);
			requestBody.setDates(Arrays.asList());

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, SAMPLE_INT), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new NotFoundException(ErrorCode.NOT_FOUND_EVENT));
		}

		@Test
		void ???_???????????????????????????????????????????????????() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getYesterday()) //
				.build();
			eventRepository.insert(event);

			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);
			requestBody.setDates(Arrays.asList());

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, event.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new BadRequestException(ErrorCode.PAST_EVENT_CANNOT_BE_UPDATED));
		}

		@ParameterizedTest
		@MethodSource
		void ???_?????????????????????????????????(final Date expiredAt, final Date startAt, final Date finishAt) throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(expiredAt) //
				.build();
			eventRepository.insert(event);

			final var requestBody = modelMapper.map(event, EventCreateRequest.class);

			// ??????????????????
			final var eventDates = Arrays.asList( //
				EventDateModel.builder().dateOrder(1).startAt(startAt).finishAt(finishAt).build() //
			);
			requestBody.setDates(eventDates);

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, event.getId()), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new BadRequestException(ErrorCode.INVALID_EVENT_DATE));
		}

		Stream<Arguments> ???_?????????????????????????????????() {
			return Stream.of( //
				// ???????????????
				arguments(DateTimeUtil.getTomorrow(), DateTimeUtil.getYesterday(), DateTimeUtil.getTomorrow()), //
				// ????????????????????????????????????
				arguments(DateTimeUtil.getTomorrow(), DateTimeUtil.getDaysLater(3), DateTimeUtil.getDaysLater(2)), //
				// ?????????????????????
				arguments(DateTimeUtil.getNextWeek(), DateTimeUtil.getDaysLater(3), DateTimeUtil.getDaysLater(2)) //
			);
		}

		@Test
		void ???_????????????????????????() throws Exception {
			// setup
			final var event = EventSample.builder().build();
			final var requestBody = modelMapper.map(event, EventUpdateRequest.class);

			// test
			final var request = postRequest(String.format(UPDATE_EVENT_PATH, SAMPLE_INT), requestBody);
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * ??????????????????API????????????
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class DeleteEventTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void ???_?????????????????????(UserRoleEnum roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder() //
				.ownerId(loginUser.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			eventRepository.insert(event);

			// test
			final var request = deleteRequest(String.format(UPDATE_EVENT_PATH, event.getId()));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, HttpStatus.OK);

			// verify
			final var events = eventRepository.selectByOwnerId(loginUser.getId());
			assertThat(events.size()).isEqualTo(0);
		}

		Stream<Arguments> ???_?????????????????????() {
			return Stream.of( //
				// ?????????
				arguments(UserRoleEnum.ADMIN), //
				// ????????????
				arguments(UserRoleEnum.MEMBER) //
			);
		}

		@Test
		void ???_??????????????????????????????????????????() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			final var eventOwner = UserSample.builder().id(loginUser.getId() + 1).build();
			userRepository.insert(eventOwner);
			final var event = EventSample.builder() //
				.ownerId(eventOwner.getId()) //
				.expiredAt(DateTimeUtil.getTomorrow()) //
				.build();
			eventRepository.insert(event);

			// test
			final var request = deleteRequest(String.format(UPDATE_EVENT_PATH, event.getId()));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION));
		}

		@Test
		void ???_?????????????????????????????????????????????() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			// test
			final var request = deleteRequest(String.format(UPDATE_EVENT_PATH, SAMPLE_INT));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new NotFoundException(ErrorCode.NOT_FOUND_EVENT));
		}

		@Test
		void ???_????????????????????????() throws Exception {
			// test
			final var request = deleteRequest(String.format(DELETE_EVENT_PATH, SAMPLE_INT));
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

	/**
	 * ????????????????????????API????????????
	 */
	@Nested
	@TestInstance(PER_CLASS)
	class GetEventTest extends AbstractRestControllerInitialization_IT {

		@ParameterizedTest
		@MethodSource
		void ???_???????????????????????????(final UserRoleEnum roleId) throws Exception {
			// setup
			final var loginUser = createLoginUser(roleId);
			final var credentials = getLoginUserCredentials(loginUser);

			final var event = EventSample.builder().ownerId(loginUser.getId()).build();
			eventRepository.insert(event);

			// ??????????????????
			final var eventDates = Arrays.asList( //
				EventDateSample.builder().eventId(event.getId()).dateOrder(1).build(), //
				EventDateSample.builder().eventId(event.getId()).dateOrder(2).build(), //
				EventDateSample.builder().eventId(event.getId()).dateOrder(3).build() //
			);
			eventDateRepository.bulkInsert(eventDates);

			// ??????????????????
			final var members = Arrays.asList( //
				UserSample.builder().email("USER1_EMAIL").build(), //
				UserSample.builder().email("USER2_EMAIL").build() //
			);
			members.stream().forEach(userRepository::insert);

			// ???????????????
			final var eventAnswers = Arrays.asList( //
				EventAnswerSample.builder().userId(members.get(0).getId()).eventId(event.getId()).build(), //
				EventAnswerSample.builder().userId(members.get(1).getId()).eventId(event.getId()).build() //
			);
			eventAnswers.stream().forEach(eventAnswerRepository::insert);

			// ????????????????????????
			final var eventDateAnswers = Arrays.asList( //
				EventDateAnswerSample.builder().answerId(eventAnswers.get(0).getId()).dateId(eventDates.get(0).getId()).isPossible(true)
					.build(), //
				EventDateAnswerSample.builder().answerId(eventAnswers.get(0).getId()).dateId(eventDates.get(1).getId()).isPossible(true)
					.build(), //
				EventDateAnswerSample.builder().answerId(eventAnswers.get(0).getId()).dateId(eventDates.get(2).getId()).isPossible(false)
					.build(), //
				EventDateAnswerSample.builder().answerId(eventAnswers.get(1).getId()).dateId(eventDates.get(0).getId()).isPossible(false)
					.build(), //
				EventDateAnswerSample.builder().answerId(eventAnswers.get(1).getId()).dateId(eventDates.get(1).getId()).isPossible(false)
					.build(), //
				EventDateAnswerSample.builder().answerId(eventAnswers.get(1).getId()).dateId(eventDates.get(2).getId()).isPossible(true)
					.build() //
			);
			eventDateAnswers.stream().forEach(eventDateAnswerRepository::insert);

			// test
			final var request = getRequest(String.format(GET_EVENT_PATH, event.getId()));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			final var response = execute(request, HttpStatus.OK, EventDetailResponse.class);

			// verify
			assertThat(response) //
				.extracting(EventDetailResponse::getId, EventDetailResponse::getName, EventDetailResponse::getDescription) //
				.contains(event.getId(), event.getName(), event.getDescription());
			assertThat(response.getOwner()) //
				.extracting(EventOwnerModel::getFirstName, EventOwnerModel::getLastName) //
				.contains(loginUser.getFirstName(), loginUser.getLastName());
			assertThat(response.getDates().size()).isEqualTo(eventDates.size());
			assertThat(response.getMembers().size()).isEqualTo(members.size());

			assertThat(response.getMembers()) //
				.extracting(EventMemberModel::getId, EventMemberModel::getFirstName, EventMemberModel::getLastName,
					EventMemberModel::getAdmissionYear) //
				.containsExactlyInAnyOrderElementsOf( //
					members.stream()
						.map(member -> tuple(member.getId(), member.getFirstName(), member.getLastName(), member.getAdmissionYear()))
						.collect(Collectors.toList()));

			assertThat(response.getMembers().get(0).getAvailableDates().stream() //
				.map(EventDateModel::getDateOrder).collect(Collectors.toList())).isEqualTo(Arrays.asList(1, 2));
			assertThat(response.getMembers().get(1).getAvailableDates().stream() //
				.map(EventDateModel::getDateOrder).collect(Collectors.toList())).isEqualTo(Arrays.asList(3));
		}

		Stream<Arguments> ???_???????????????????????????() {
			return Stream.of( //
				// ?????????
				arguments(UserRoleEnum.ADMIN), //
				// ????????????
				arguments(UserRoleEnum.MEMBER) //
			);
		}

		@Test
		void ???_??????????????????????????????????????????() throws Exception {
			// setup
			final var loginUser = createLoginUser(UserRoleEnum.MEMBER);
			final var credentials = getLoginUserCredentials(loginUser);

			// test
			final var request = getRequest(String.format(GET_EVENT_PATH, SAMPLE_INT));
			request.header(HttpHeaders.AUTHORIZATION, credentials);
			execute(request, new NotFoundException(ErrorCode.NOT_FOUND_EVENT));
		}

		@Test
		void ???_????????????????????????() throws Exception {
			// test
			final var request = getRequest(String.format(GET_EVENT_PATH, SAMPLE_INT));
			request.header(HttpHeaders.AUTHORIZATION, "");
			execute(request, new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN));
		}

	}

}
