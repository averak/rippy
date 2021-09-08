package dev.abelab.rippy.util;

import java.util.Date;

import dev.abelab.rippy.db.entity.User;
import dev.abelab.rippy.db.entity.Event;
import dev.abelab.rippy.enums.UserRoleEnum;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.ForbiddenException;
import dev.abelab.rippy.exception.BadRequestException;

public class EventUtil {

    /**
     * 募集締め切りのバリデーション
     *
     * @param event イベント
     */
    public static void validateExpiredAt(final Date expiredAt) {
        final var now = new Date();

        // 過去の日時
        if (expiredAt.before(now)) {
            throw new BadRequestException(ErrorCode.INVALID_EXPIRED_AT);
        }
    }

    /**
     * イベントの編集権限をチェック
     *
     * @param event     イベント
     *
     * @param loginUser ログインユーザ
     */
    public static void checkEditEventPermission(final Event event, final User loginUser) {
        // 募集を締め切ったイベント
        final var now = new Date();
        if (event.getExpiredAt().before(now)) {
            throw new BadRequestException(ErrorCode.PAST_EVENT_CANNOT_BE_UPDATED);
        }

        // 管理者でもイベントオーナーでもない
        if (loginUser.getRoleId() != UserRoleEnum.ADMIN.getId() && !event.getOwnerId().equals(loginUser.getId())) {
            throw new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION);
        }
    }

    /**
     * イベントの削除権限をチェック
     *
     * @param event     イベント
     *
     * @param loginUser ログインユーザ
     */
    public static void checkDeleteEventPermission(final Event event, final User loginUser) {
        // 管理者でもイベントオーナーでもない
        if (loginUser.getRoleId() != UserRoleEnum.ADMIN.getId() && !event.getOwnerId().equals(loginUser.getId())) {
            throw new ForbiddenException(ErrorCode.USER_HAS_NO_PERMISSION);
        }
    }

}
