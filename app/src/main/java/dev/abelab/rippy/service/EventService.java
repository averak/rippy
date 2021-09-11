package dev.abelab.rippy.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import lombok.*;
import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.db.entity.Event;
import dev.abelab.rippy.db.entity.EventDate;
import dev.abelab.rippy.model.EventDateModel;
import dev.abelab.rippy.api.request.EventCreateRequest;
import dev.abelab.rippy.api.request.EventUpdateRequest;
import dev.abelab.rippy.api.response.EventResponse;
import dev.abelab.rippy.api.response.EventsResponse;
import dev.abelab.rippy.repository.EventRepository;
import dev.abelab.rippy.repository.EventDateRepository;
import dev.abelab.rippy.util.EventUtil;
import dev.abelab.rippy.util.EventDateUtil;

@RequiredArgsConstructor
@Service
public class EventService {

    private final ModelMapper modelMapper;

    private final EventRepository eventRepository;

    private final EventDateRepository eventDateRepository;

    /**
     * イベント一覧を取得
     *
     * @param loginUser ログインユーザ
     *
     * @return イベント一覧レスポンス
     */
    @Transactional
    public EventsResponse getEvents(final User loginUser) {
        // イベント一覧の取得
        final var events = this.eventRepository.selectAllWithDates();
        final var eventResponses = events.stream() //
            .map(event -> this.modelMapper.map(event, EventResponse.class)) //
            .collect(Collectors.toList());

        return new EventsResponse(eventResponses);
    }

    /**
     * イベントを作成
     *
     * @param requestBody イベント作成リクエスト
     *
     * @param loginUser   ログインユーザ
     */
    @Transactional
    public void createEvent(final EventCreateRequest requestBody, final User loginUser) {
        final var event = this.modelMapper.map(requestBody, Event.class);
        event.setOwnerId(loginUser.getId());

        // 募集締め切りのバリデーション
        EventUtil.validateExpiredAt(event.getExpiredAt());

        // 候補日の順番が有効かチェック
        EventDateUtil.checkDateOrdersValid(requestBody.getDates().stream().map(EventDateModel::getDateOrder).collect(Collectors.toList()));

        // イベントの作成
        this.eventRepository.insert(event);

        // 候補日リストの作成
        final var eventDates = requestBody.getDates().stream().map(eventDateModel -> {
            final var eventDate = this.modelMapper.map(eventDateModel, EventDate.class);
            eventDate.setEventId(event.getId());

            // 候補日のバリデーション
            EventDateUtil.validateEventDate(event, eventDate);

            return eventDate;
        }).collect(Collectors.toList());
        this.eventDateRepository.bulkInsert(eventDates);
    }

    /**
     * イベントを更新
     *
     * @param eventId     イベントID
     *
     * @param requestBody イベント更新リクエスト
     *
     * @param loginUser   ログインユーザ
     */
    @Transactional
    public void updateEvent(final int eventId, final EventUpdateRequest requestBody, final User loginUser) {
        // 更新対象イベントを取得
        final var event = this.eventRepository.selectById(eventId);

        // 更新可能かチェック
        EventUtil.checkEditEventPermission(event, loginUser);

        // 募集締め切りのバリデーション
        EventUtil.validateExpiredAt(requestBody.getExpiredAt());

        // 候補日の順番が有効かチェック
        EventDateUtil.checkDateOrdersValid(requestBody.getDates().stream().map(EventDateModel::getDateOrder).collect(Collectors.toList()));

        // イベントを更新
        event.setName(requestBody.getName());
        event.setDescription(requestBody.getDescription());
        event.setExpiredAt(requestBody.getExpiredAt());
        this.eventRepository.update(event);

        // 既存の候補日リストを削除
        this.eventDateRepository.deleteByEventId(eventId);

        // 候補日リストの作成
        final var eventDates = requestBody.getDates().stream().map(eventDateModel -> {
            final var eventDate = this.modelMapper.map(eventDateModel, EventDate.class);
            eventDate.setEventId(event.getId());

            // 候補日のバリデーション
            EventDateUtil.validateEventDate(event, eventDate);

            return eventDate;
        }).collect(Collectors.toList());
        this.eventDateRepository.bulkInsert(eventDates);
    }

    /**
     * イベントを削除
     *
     * @param eventId   イベントID
     *
     * @param loginUser ログインユーザ
     */
    @Transactional
    public void deleteEvent(final int eventId, final User loginUser) {
        // 更新対象イベントを取得
        final var event = this.eventRepository.selectById(eventId);

        // 削除権限をチェック
        EventUtil.checkDeleteEventPermission(event, loginUser);

        // イベントを削除
        this.eventRepository.deleteById(eventId);
    }

}
