package dev.abelab.rippy.api.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.*;
import lombok.*;
import dev.abelab.rippy.annotation.Authenticated;
import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.api.response.EventsResponse;
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

}