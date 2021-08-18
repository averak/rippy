package dev.abelab.rippy.api.controller.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.*;
import lombok.*;
import dev.abelab.rippy.api.request.UserCreateRequest;
import dev.abelab.rippy.api.request.UserUpdateRequest;
import dev.abelab.rippy.api.response.UsersResponse;
import dev.abelab.rippy.service.UserService;

@Api(tags = "User")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserRestController {

    private final UserService userService;

    /**
     * ユーザ一覧取得API
     *
     * @param token Bearerトークン
     *
     * @return ユーザ一覧レスポンス
     */
    @ApiOperation( //
        value = "ユーザ一覧の取得", //
        notes = "ユーザ一覧を取得する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 200, message = "取得成功", response = UsersResponse.class), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない"), //
                @ApiResponse(code = 403, message = "ユーザに権限がない") //
        })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UsersResponse getUsers( //
        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = true) final String token //
    ) {
        return this.userService.getUsers(token);
    }

    /**
     * ユーザ作成API
     *
     * @param token       Bearerトークン
     *
     * @param requestBody ユーザ作成リクエスト
     */
    @ApiOperation( //
        value = "ユーザの作成", //
        notes = "ユーザを作成する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 201, message = "作成成功"), //
                @ApiResponse(code = 400, message = "無効なパスワード"), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない"), //
                @ApiResponse(code = 403, message = "ユーザに権限がない"), //
                @ApiResponse(code = 409, message = "ユーザが既に存在している"), //
        } //
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser( //
        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = true) final String token, //
        @Validated @ApiParam(name = "body", required = true, value = "新規ユーザ情報") @RequestBody final UserCreateRequest requestBody //
    ) {
        this.userService.createUser(requestBody, token);
    }

    /**
     * ユーザ更新API
     *
     * @param token       Bearerトークン
     *
     * @param userId      ユーザID
     *
     * @param requestBody ユーザ更新リクエスト
     */
    @ApiOperation( //
        value = "ユーザの更新", //
        notes = "ユーザを更新する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 200, message = "更新成功"), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない"), //
                @ApiResponse(code = 403, message = "ユーザに権限がない"), //
                @ApiResponse(code = 404, message = "ユーザが存在しない"), //
        } //
    )
    @PutMapping(value = "/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser( //
        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = true) final String token, //
        @ApiParam(name = "user_id", required = true, value = "ユーザID") @PathVariable("user_id") final int userId, //
        @Validated @ApiParam(name = "body", required = true, value = "ユーザ更新情報") @RequestBody final UserUpdateRequest requestBody //
    ) {
        this.userService.updateUser(userId, requestBody, token);
    }

    /**
     * ユーザ削除API
     *
     * @param token  Bearerトークン
     *
     * @param userId ユーザID
     */
    @ApiOperation( //
        value = "ユーザの削除", //
        notes = "ユーザを削除する。" //
    )
    @ApiResponses( //
        value = { //
                @ApiResponse(code = 200, message = "削除成功"), //
                @ApiResponse(code = 401, message = "ユーザがログインしていない"), //
                @ApiResponse(code = 403, message = "ユーザに権限がない"), //
                @ApiResponse(code = 404, message = "ユーザが存在しない"), //
        } //
    )
    @DeleteMapping(value = "/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser( //
        @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = true) final String token, //
        @ApiParam(name = "user_id", required = true, value = "ユーザID") @PathVariable("user_id") final int userId //
    ) {
        this.userService.deleteUser(userId, token);
    }

}
