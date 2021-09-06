package dev.abelab.rippy.util;

import java.util.Date;

import dev.abelab.rippy.exception.ErrorCode;
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

}
