package dev.abelab.rippy.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import lombok.*;
import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.db.entity.Event;
import dev.abelab.rippy.api.request.EventCreateRequest;
import dev.abelab.rippy.api.response.EventResponse;
import dev.abelab.rippy.api.response.EventsResponse;
import dev.abelab.rippy.repository.EventRepository;
import dev.abelab.rippy.util.EventUtil;

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
     * @param イベント作成リクエスト
     *
     * @param loginUser   ログインユーザ
     */
    @Transactional
    public void createEvent(final EventCreateRequest requestBody, final User loginUser) {
        final var event = this.modelMapper.map(requestBody, Event.class);
        event.setOwnerId(loginUser.getId());

        // 募集締め切りのバリデーション
        EventUtil.validateExpiredAt(event);

        // イベントの作成
        this.eventRepository.insert(event);
    }

}
