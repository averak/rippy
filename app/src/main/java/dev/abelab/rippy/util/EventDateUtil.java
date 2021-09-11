package dev.abelab.rippy.util;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import dev.abelab.rippy.db.entity.Event;
import dev.abelab.rippy.db.entity.EventDate;
import dev.abelab.rippy.exception.ErrorCode;
import dev.abelab.rippy.exception.BadRequestException;

public class EventDateUtil {

    /**
     * イベント候補日のバリデーション
     *
     * @param event     イベント
     *
     * @param eventDate イベント候補日
     */
    public static void validateEventDate(final Event event, final EventDate eventDate) {
        final var now = new Date();

        // 過去の日時
        if (eventDate.getStartAt().before(now)) {
            throw new BadRequestException(ErrorCode.INVALID_EVENT_DATE);
        }
        // 募集締め切り前
        if (eventDate.getStartAt().before(event.getExpiredAt())) {
            throw new BadRequestException(ErrorCode.INVALID_EVENT_DATE);
        }
        // 開始・終了時刻の順序が逆
        if (eventDate.getFinishAt().before(eventDate.getStartAt())) {
            throw new BadRequestException(ErrorCode.INVALID_EVENT_DATE);
        }
    }

    /**
     * 候補日の順番が有効かチェック
     *
     * @param dateOrders 候補日の順番リスト
     */
    public static void checkDateOrdersValid(List<Integer> dateOrders) {
        final var validDateOrders = IntStream.rangeClosed(1, dateOrders.size()).boxed().collect(Collectors.toList());

        if (!dateOrders.equals(validDateOrders)) {
            throw new BadRequestException(ErrorCode.INVALID_EVENT_DATE_ORDERS);
        }
    }

}
