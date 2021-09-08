package dev.abelab.rippy.service;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import lombok.*;
import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.db.entity.Event;
import dev.abelab.rippy.enums.UserRoleEnum;
import dev.abelab.rippy.api.request.EventCreateRequest;
import dev.abelab.rippy.api.request.EventUpdateRequest;
import dev.abelab.rippy.api.response.EventResponse;
import dev.abelab.rippy.api.response.EventsResponse;
import dev.abelab.rippy.repository.EventRepository;
import dev.abelab.rippy.util.EventUtil;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.ForbiddenException;
import dev.abelab.rippy.exception.BadRequestException;

@RequiredArgsConstructor
@Service
public class EventService {

    private final ModelMapper modelMapper;

    private final EventRepository eventRepository;

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
        final var events = this.eventRepository.selectAll();
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

        // イベントの作成
        this.eventRepository.insert(event);
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
        // 募集終了したイベント
        final var now = new Date();
        if (event.getExpiredAt().before(now)) {
            throw new BadRequestException(ErrorCode.PAST_EVENT_CANNOT_BE_UPDATED);
        }
        // 管理者でもイベントオーナーでもない
        if (loginUser.getRoleId() != UserRoleEnum.ADMIN.getId() && !event.getOwnerId().equals(loginUser.getId())) {
            throw new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION);
        }

        // 募集締め切りのバリデーション
        EventUtil.validateExpiredAt(requestBody.getExpiredAt());

        // イベントを更新
        event.setName(requestBody.getName());
        event.setDescription(requestBody.getDescription());
        event.setExpiredAt(requestBody.getExpiredAt());
        this.eventRepository.update(event);
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
        if (loginUser.getRoleId() != UserRoleEnum.ADMIN.getId() && !event.getOwnerId().equals(loginUser.getId())) {
            throw new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION);
        }

        // イベントを削除
        this.eventRepository.deleteById(eventId);
    }

}
