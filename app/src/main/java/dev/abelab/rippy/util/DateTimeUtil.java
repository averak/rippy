package dev.abelab.rippy.util;

import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class DateTimeUtil {

    private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.JAPAN);

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM月dd日(E)", Locale.JAPAN);

    /**
     * 今日の日時を取得
     *
     * return 今日の日時
     */
    public static Date getToday() {
        final var calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 翌日の日時を取得
     *
     * return 翌日の日時
     */
    public static Date getTomorrow() {
        final var calendar = Calendar.getInstance();
        calendar.setTime(getToday());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 昨日の日時を取得
     *
     * return 翌日の日時
     */
    public static Date getYesterday() {
        final var calendar = Calendar.getInstance();
        calendar.setTime(getToday());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 指定した日時を取得
     *
     * @param year   年
     *
     * @param month  月
     *
     * @param day    日
     *
     * @param hour   時
     *
     * @param minute 分
     *
     * @return 日時
     */
    public static Date getDateTime(final int year, final int month, final int day, final int hour, final int minute) {
        final var calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar.getTime();
    }

    /**
     * 時間を文字列に変換
     *
     * @param date 時間
     *
     * @return 時間（文字列）
     */
    public static String convertTimeToString(final Date date) {
        return timeFormatter.format(date);
    }

    /**
     * 日時を文字列に変換
     *
     * @param date 日時
     *
     * @return 日時（文字列）
     */
    public static String convertDateToString(final Date date) {
        return dateFormatter.format(date);
    }

}
