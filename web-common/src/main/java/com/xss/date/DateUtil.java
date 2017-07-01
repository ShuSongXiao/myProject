package com.xss.date;

import com.xss.util.U;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    /** 当前时间 */
    public static Date now() {
        return new Date();
    }

    /** 返回 yyyy-MM-dd HH:mm:ss 格式的当前时间 */
    public static String nowTime() {
        return now(DateFormatType.YYYY_MM_DD_HH_MM_SS);
    }
    /** 返回今天的 yyyy-MM-dd 格式 */
    public static String yeahMonthDay() {
        return now(DateFormatType.YYYY_MM_DD);
    }
    /** 返回今天的 yyyyMMdd 格式 */
    public static String today() {
        return now(DateFormatType.YYYYMMDD);
    }

    /** 获取当前时间日期的字符串 */
    public static String now(DateFormatType dateFormatType) {
        return format(now(), dateFormatType);
    }
    /** 格式化日期 yyyy-MM-dd */
    public static String formatDate(Date date) {
        return format(date, DateFormatType.YYYY_MM_DD);
    }
    /** 格式化时间 HH:mm:ss */
    public static String formatTime(Date date) {
        return format(date, DateFormatType.HH_MM_SS);
    }
    /** 格式化日期和时间 yyyy-MM-dd HH:mm:ss */
    public static String formatFull(Date date) {
        return format(date, DateFormatType.YYYY_MM_DD_HH_MM_SS);
    }

    /** 格式化日期对象成字符串 */
    public static String format(Date date, DateFormatType type) {
        if (U.isBlank(date) || U.isBlank(type)) return U.EMPTY;

        return new SimpleDateFormat(type.getValue()).format(date);
    }

    /**
     * 将字符串转换成 Date 对象. 类型基于 DateFormatType 一个一个试
     *
     * @see DateFormatType
     */
    public static Date parseSpecified(String source) {
        if (U.isBlank(source)) return null;

        source = source.trim();
        for (DateFormatType type : DateFormatType.values()) {
            SimpleDateFormat dateFormat;
            if (type.isCst()) {
                dateFormat = new SimpleDateFormat(type.getValue(), Locale.US);
            } else {
                dateFormat = new SimpleDateFormat(type.getValue());
            }
            try {
                Date date = dateFormat.parse(source);
                if (date != null) return date;
            } catch (ParseException e) {
                // ignore
            }
        }
        return null;
    }

    /** 获取一个日期所在天的最开始的时间(00:00:00 000), 对日期段查询尤其有用 */
    public static Date getDayStart(Date date) {
        return filterMin(new DateTime(date));
    }
    /** 获取一个日期所在天的最晚的时间(23:59:59 999), 对日期查询尤其有用 */
    public static Date getDayEnd(Date date) {
        return filterMax(new DateTime(date));
    }

    /**
     * 取得指定日期 N 天后的日期
     *
     * @param day 正数表示多少天后, 负数表示多少天前
     */
    public static Date addDays(Date date, int day) {
        return new DateTime(date).plusDays(day).toDate();
    }
    /**
     * 取得指定日期 N 个月后的日期
     *
     * @param month 正数表示多少月后, 负数表示多少月前
     */
    public static Date addMonths(Date date, int month) {
        return new DateTime(date).plusMonths(month).toDate();
    }
    /**
     * 取得指定日期 N 天后的日期
     *
     * @param year 正数表示多少年后, 负数表示多少年前
     */
    public static Date addYears(Date date, int year) {
        return new DateTime(date).plusYears(year).toDate();
    }
    /**
     * 取得指定日期 N 分钟后的日期
     *
     * @param minute 正数表示多少分钟后, 负数表示多少分钟前
     */
    public static Date addMinute(Date date, int minute) {
        return new DateTime(date).plusMinutes(minute).toDate();
    }
    /**
     * 取得指定日期 N 小时后的日期
     *
     * @param hour 正数表示多少小时后, 负数表示多少小时前
     */
    public static Date addHours(Date date, int hour) {
        return new DateTime(date).plusHours(hour).toDate();
    }
    /**
     * 取得指定日期 N 秒后的日期
     *
     * @param second 正数表示多少秒后, 负数表示多少秒前
     */
    public static Date addSeconds(Date date, int second) {
        return new DateTime(date).plusSeconds(second).toDate();
    }
    /**
     * 取得指定日期 N 周后的日期
     *
     * @param week 正数表示多少周后, 负数表示多少周前
     */
    public static Date addWeeks(Date date, int week) {
        return new DateTime(date).plusWeeks(week).toDate();
    }

    /** 传入的时间晚于当前时间就返回传入的时间, 否则就返回当前时间 */
    public static Date before(Date date) {
        Date now = now();
        return now.after(date) ? now : date;
    }

    /** 传入的时间是不是当月当日. 用来验证生日 */
    public static boolean wasBirthday(Date date) {
        DateTime dt = DateTime.now();
        DateTime dateTime = new DateTime(date);
        return dt.getMonthOfYear() == dateTime.getMonthOfYear() && dt.getDayOfMonth() == dateTime.getDayOfMonth();
    }

    /** 返回指定日期所在月的上个月的第一天第一毫秒 */
    public static Date getPreDayOfMonth(Date date) {
        return getPreDayOfMonth(date, 1);
    }
    private static Date getPreDayOfMonth(Date date, int month) {
        DateTime dateTime = new DateTime(date).minusMonths(month);
        return filterMin(dateTime.dayOfMonth().withMinimumValue());
    }
    /** 返回指定日期的当月第一毫秒 */
    public static Date getFirstDayOfMonth(Date date) {
        return getPreDayOfMonth(date, 0);
    }

    /** 返回指定日期所在月的上个月的最后一毫秒 */
    public static Date getPreLastDayOfMonth(Date date) {
        return geDayOfMonth(date, 1);
    }
    private static Date geDayOfMonth(Date date, int month) {
        DateTime dateTime = new DateTime(date).minusMonths(month);
        return filterMax(dateTime.dayOfMonth().withMaximumValue());
    }
    /** 返回指定日期的当月最后一毫秒 */
    public static Date getLastDayOfMonth(Date date) {
        return geDayOfMonth(date, 0);
    }

    /** 返回上个星期一的第一毫秒(星期一为一周的第一天) */
    public static Date getPreMondayOfWeek() {
        return getMondayOfWeek(now(), 1);
    }
    private static Date getMondayOfWeek(Date date, int week) {
        DateTime dateTime = new DateTime(date).minusWeeks(week);
        return filterMin(dateTime.dayOfWeek().withMinimumValue());
    }
    /** 返回指定日期的那个星期一的第一毫秒(星期一为一周的第一天) */
    public static Date getMondayOfWeek(Date date) {
        return getMondayOfWeek(date, 0);
    }

    /** 返回上个星期天的最后一毫秒(星期天为一周的最后一天) */
    public static Date getPreSundayOfWeek() {
        return getSundayOfWeek(now(), 1);
    }
    private static Date getSundayOfWeek(Date date, int week) {
        DateTime dateTime = new DateTime(date).minusWeeks(week);
        return filterMax(dateTime.dayOfWeek().withMaximumValue());
    }
    /** 返回指定日期的那个星期天的最后一毫秒(星期天为一周的最后一天) */
    public static Date getSundayOfWeek(Date date) {
        return getSundayOfWeek(date, 0);
    }

    /** 计算两个日期之间相差的天数(将忽略时分秒). 如果第一个参数晚于第二个参数, 将会返回 负数 */
    public static int daysBetween(Date bigDate, Date smallDate) {
        // 时分秒 弄成一样再做对比. 避免出问题
        DateTime big = filterMaxHourMinuteSecond(new DateTime(bigDate));
        DateTime small = filterMaxHourMinuteSecond(new DateTime(smallDate));
        return Days.daysBetween(big, small).getDays();
    }

    /** 计算两个日期字符串之间相差的天数(将忽略时分秒). 如果第一个参数早于第二个参数, 将会返回 负数 */
    public static int daysBetween(String bigDate, String smallDate) {
        return daysBetween(parseSpecified(bigDate), parseSpecified(smallDate));
    }

    /** 将时间的时分秒毫秒都设置为最大 */
    private static DateTime filterMaxHourMinuteSecond(DateTime dateTime) {
        return dateTime.hourOfDay().withMaximumValue()
                .minuteOfHour().withMaximumValue()
                .secondOfMinute().withMaximumValue()
                .millisOfSecond().withMaximumValue();
    }
    private static Date filterMax(DateTime dateTime) {
        return filterMaxHourMinuteSecond(dateTime).toDate();
    }
    /** 将时间的时分秒毫秒都设置为最小 */
    private static DateTime filterMinHourMinuteSecond(DateTime time) {
        return time.hourOfDay().withMinimumValue()
                .minuteOfHour().withMinimumValue()
                .secondOfMinute().withMinimumValue()
                .millisOfSecond().withMinimumValue();
    }
    private static Date filterMin(DateTime dateTime) {
        return filterMinHourMinuteSecond(dateTime).toDate();
    }

    private static final String[] WEEKS = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
    /** 得到某一日期是星期几 */
    public static String getWeek(String dateTime) {
        if (U.isBlank(dateTime)) return U.EMPTY;

        Date date = parseSpecified(dateTime);
        if (U.isBlank(date)) return dateTime;

        // 星期一是 1, 星期日是 7
        return WEEKS[new DateTime(date).getDayOfWeek() - 1];
    }


    /**
     * 得到两个日期之间的分钟差
     * <pre>
     * DateUtil.getMinuteInterval(  2017-5-4 15:00:08 ,2017-5-4 15:51:08  ) = 51
     * DateUtil.getMinuteInterval(  2017-5-4 15:00:34 ,2017-5-4 15:51:34  ) = 51
     * DateUtil.getMinuteInterval(  2016-5-4 15:56:30 ,2017-5-4 15:56:30  ) = 525600
     * </pre>
     *
     * @param bigDate  Date 类型,不分前后顺序
     * @param smallDate  Date 类型,不分前后顺序
     * @return 日期之间的分钟间隔
     */
    public static long getMinuteInterval(Date bigDate, Date smallDate) {
        // 秒 弄成一样再做对比. 避免出问题
        DateTime big = new DateTime(bigDate).secondOfMinute().withMaximumValue().millisOfSecond().withMaximumValue();
        DateTime small = new DateTime(smallDate).secondOfMinute().withMaximumValue().millisOfSecond().withMaximumValue();

        return Minutes.minutesBetween(big, small).getMinutes();
    }
}
