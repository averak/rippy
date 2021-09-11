package dev.abelab.rippy.api.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.*;
import lombok.*;
import dev.abelab.rippy.annotation.Authenticated;
import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.api.request.EventCreateRequest;
import dev.abelab.rippy.api.request.EventUpdateRequest;
import dev.abelab.rippy.api.response.EventsResponse;
import dev.abelab.rippy.api.response.EventDetailResponse;
import dev.abelab.rippy.service.EventService;

@Api(tags = "Event")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Authenticated
public class EventRestController {

    private final EventService eventService;

    /**
     * イベント一覧取得API
     *
     * @param loginUser ログインユーザ
     *
     * @return イベント一覧レスポンス
     */
    @ApiOperation( //
        value = "イベント一覧の取得", //
        notes = "イベント一覧を取得する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 200, message = "取得成功", response = EventsResponse.class), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない") //
        })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EventsResponse getEvents( //
        @ModelAttribute("LoginUser") final User loginUser //
    ) {
        return this.eventService.getEvents(loginUser);
    }

    /**
     * イベント作成API
     *
     * @param loginUser   ログインユーザ
     *
     * @param requestBody イベント作成リクエスト
     */
    @ApiOperation( //
        value = "イベントの作成", //
        notes = "イベントを作成する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 201, message = "作成成功"), //
                @ApiResponse(code = 400, message = "無効な募集締め切り日時"), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない"), //
        } //
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent( //
        @ModelAttribute("LoginUser") final User loginUser, //
        @Validated @ApiParam(name = "body", required = true, value = "新規イベント情報") @RequestBody final EventCreateRequest requestBody //
    ) {
        this.eventService.createEvent(requestBody, loginUser);
    }

    /**
     * イベント更新API
     *
     * @param loginUser   ログインユーザ
     *
     * @param eventId     イベントID
     *
     * @param requestBody イベント更新リクエスト
     */
    @ApiOperation( //
        value = "イベントの更新", //
        notes = "イベントを更新する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 200, message = "更新成功"), //
                @ApiResponse(code = 400, message = "無効な募集締め切り日時"), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない"), //
                @ApiResponse(code = 403, message = "イベントを更新する権限がない"), //
                @ApiResponse(code = 404, message = "更新対象イベントが存在しない"), //
        } //
    )
    @PostMapping(value = "/{event_id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEvent( //
        @ModelAttribute("LoginUser") final User loginUser, //
        @ApiParam(name = "event_id", required = true, value = "イベントID") @PathVariable("event_id") final int eventId, //
        @Validated @ApiParam(name = "body", required = true, value = "イベント更新情報") @RequestBody final EventUpdateRequest requestBody //
    ) {
        this.eventService.updateEvent(eventId, requestBody, loginUser);
    }

    /**
     * イベント削除API
     *
     * @param loginUser ログインユーザ
     *
     * @param eventId   イベントID
     */
    @ApiOperation( //
        value = "イベントの削除", //
        notes = "イベントを削除する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 200, message = "削除成功"), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない"), //
                @ApiResponse(code = 403, message = "イベントを削除する権限がない"), //
                @ApiResponse(code = 404, message = "削除対象イベントが存在しない"), //
        } //
    )
    @DeleteMapping(value = "/{event_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEvent( //
        @ModelAttribute("LoginUser") final User loginUser, //
        @ApiParam(name = "event_id", required = true, value = "イベントID") @PathVariable("event_id") final int eventId //
    ) {
        this.eventService.deleteEvent(eventId, loginUser);
    }

    /**
     * イベント詳細取得API
     *
     * @param loginUser ログインユーザ
     *
     * @param eventId   イベントID
     */
    @ApiOperation( //
        value = "イベント詳細の取得", //
        notes = "イベント詳細を取得する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 200, message = "取得成功"), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない"), //
                @ApiResponse(code = 404, message = "取得対象イベントが存在しない"), //
        } //
    )
    @GetMapping(value = "/{event_id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDetailResponse getEventDetali( //
        @ModelAttribute("LoginUser") final User loginUser, //
        @ApiParam(name = "event_id", required = true, value = "イベントID") @PathVariable("event_id") final int eventId //
    ) {
        return this.eventService.getEventDetail(eventId, loginUser);
    }

}
