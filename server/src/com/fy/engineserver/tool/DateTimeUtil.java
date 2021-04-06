package com.fy.engineserver.tool;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

	/**
	 * 与0时区相差的毫秒数
	 */
	private static long timeEquation = 0;

	/**
	 * 纳秒与毫秒的单位标示
	 */
	public final static long NANOSECONDS_UNIT = 1000000L;

	/**
	 * 一秒钟的毫秒表示
	 */
	public final static long SECOND = 1000L;

	/**
	 * 一分钟的毫秒表示
	 */
	public final static long MINUTE = 60L * SECOND;

	/**
	 * 一小时的毫秒表示
	 */
	public final static long HOUR = 60L * MINUTE;

	/**
	 * 一天的毫秒表示
	 */
	public final static long DAY = 24L * HOUR;

	/**
	 * 一周的毫秒表示
	 */
	public final static long WEEK = 7L * DAY;

	/**
	 * 一个月的毫秒表示(按30天计算)
	 */
	public final static long MONTH = 30L * DAY;

	/**
	 * 一年的毫秒表示(按365天计算)
	 */
	public final static long YEAR = 365L * DAY;

	/**
	 * 用户友好的日期时间格式:yyyy-MM-dd HH:mm:ss
	 */
	public final static String FRIENDLY_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 业务逻辑数据的日期时间格式:YY/MM/dd hh:mm:ss
	 */
	public final static String DATE_TIME_FORMAT_GAME_LOGIC = "YY/MM/dd hh:mm:ss";

	/**
	 * 返回固定格式，精确到分钟yyyy-MM-dd HH:mm
	 */
	private final static String ONLY_MINUTES_FORMAT = "yyyy-MM-dd HH:mm";

	/**
	 * 返回固定格式，精确到秒HH:mm:ss
	 */
	public final static String TIME_FORMAT = "HH:mm:ss";

	/**
	 * 返回固定格式，精确到分钟HH:mm
	 */
	private final static String ONLY_TIME_FORMAT = "HH:mm";

	/**
	 * 返回天日期yyyy-MM-dd
	 */
	private final static String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";

	/**
	 * 返回月天日期MM-dd HH:mm
	 */
	private final static String MONTH_DAY_TIME_FORMAT = "MM-dd HH:mm";

	/**
	 * 返回月天日期MM月dd日HH点mm分
	 */
	private final static String MONTH_DAY_TIME_FORMAT_2 = "MM月dd日HH点mm分";

	/**
	 * 返回MM月dd日
	 */
	private final static String MONTH_DAY_FORMAT = "MM月dd日";

	private final static String DAY_TIME_MONTH = "月";

	// private final static String DAY_TIME_DAY = "日";

	private final static String DAY_TIME_YEAR = "年";

	private final static String DAY_TIME_TIAN = "天";

	private final static String DAY_TIME_HOUR = "小时";

	private final static String DAY_TIME_MINUTE = "分";

	private final static String DAY_TIME_FZ = "分钟";

	private final static String DAY_TIME_SECOND = "秒";

	/**
	 * 返回日期格式：yyyy年MM月dd日 HH:MM
	 */
	private final static String FRIENDLY_DATE_TIME_FORMAT_WITH_CHINESE = "yyyy年MM月dd日 HH:mm";

	private final static String[] WEEKLY_DETAIL = { "日", "一", "二", "三", "四", "五", "六" };

	private DateTimeUtil() {
	}

	/**
	 * 时间跨度的友好格式表示,精确到秒, 如:39年4月5天11小时55分钟7秒; 1天; 1小时.
	 * @param t
	 *            时间差(单位:毫秒), 负数将返回N/A;
	 * @param shortest
	 *            是否使用最短字符表示方式, 如:39年; 5天; 36秒等;
	 * @param ceil
	 *            当值为true时类似于c函数ceil(如2小时30分钟输出为3小时), 当值为false时类似于c函数floor(如2小时30分钟输出为2小时)
	 * @return
	 */
	public static String buildTimeString(long t, boolean shortest, boolean ceil) {
		if (t < 0) return "N/A";

		if (t == 0) return "0" + DAY_TIME_SECOND;
		else if (t < SECOND) {
			if (ceil) return "1" + DAY_TIME_SECOND;
			else return "0" + DAY_TIME_SECOND;
		}

		StringBuilder sb = new StringBuilder();
		buildTimeString(t, sb, shortest, ceil);
		return sb.toString();
	}

	/**
	 * 时间跨度的友好格式表示,精确到秒, 如:39年4月5天11小时55分钟7秒; 1年12天10分钟; 1天; 1小时.
	 * @param t
	 *            时间差(单位:毫秒), 负数将返回N/A;
	 * @return
	 */
	public static String buildTimeString(long t) {
		return buildTimeString(t, false, true);
	}

	/**
	 * 时间跨度的友好格式表示,精确到秒
	 * @param t
	 *            时间差(单位:毫秒)
	 * @param sb
	 * @param shortest
	 *            是否使用最短字符表示方式
	 * @param ceil
	 *            当值为true时类似于c函数ceil(如2小时30分钟输出为3小时), 当值为false时类似于c函数floor(如2小时30分钟输出为2小时)
	 */
	private static void buildTimeString(long t, StringBuilder sb, boolean shortest, boolean ceil) {
		if (t >= YEAR) {
			long y = t / YEAR;
			t -= y * YEAR;
			if (t > 0 && shortest) {
				if (ceil) y++;
				t = 0;
			}

			sb.append(y).append(DAY_TIME_YEAR);
			if (t > 0) buildTimeString(t, sb, shortest, ceil);
		} else if (t >= MONTH) {
			long m = t / MONTH;
			t -= m * MONTH;
			if (t > 0 && shortest) {
				if (ceil) m++;
				t = 0;
			}

			sb.append(m).append(DAY_TIME_MONTH);
			if (t > 0) buildTimeString(t, sb, shortest, ceil);
		} else if (t >= DAY) {
			long d = t / DAY;
			t -= d * DAY;
			if (t > 0 && shortest) {
				if (ceil) d++;
				t = 0;
			}

			sb.append(d).append(DAY_TIME_TIAN);
			if (t > 0) buildTimeString(t, sb, shortest, ceil);
		} else if (t >= HOUR) {
			long h = t / HOUR;
			t -= h * HOUR;
			if (t > 0 && shortest) {
				if (ceil) h++;
				t = 0;
			}
			sb.append(h).append(DAY_TIME_HOUR);
			if (t > 0) buildTimeString(t, sb, shortest, ceil);
		} else if (t >= MINUTE) {
			long m = t / MINUTE;
			t -= m * MINUTE;
			if (t > 0 && shortest) {
				if (ceil) m++;
				t = 0;
			}
			sb.append(m).append(DAY_TIME_FZ);
			if (t > 0) buildTimeString(t, sb, shortest, ceil);
		} else {
			long s = t / SECOND;
			t -= s * SECOND;
			if (t > 0 && ceil) s++;
			sb.append(s).append(DAY_TIME_SECOND);
		}
	}

	/**
	 * 返回友好格式：-月-日-点-分
	 * @param time
	 * @return
	 */
	public static String date2String(long time) {
		Date date = new Date();
		date.setTime(time);
		DateFormat format = new SimpleDateFormat(MONTH_DAY_TIME_FORMAT_2);
		return format.format(date);
	}

	/**
	 * 获取指定时间的yyyy-MM-dd HH:mm:ss表示格式
	 * @param timestamp
	 * @return
	 */
	public static String getDayString(long timestamp) {
		return new SimpleDateFormat(YEAR_MONTH_DAY_FORMAT).format(timestamp);
	}

	/**
	 * 获取指定时间的MM-dd表示格式
	 * @param timestamp
	 * @return String
	 */
	public static String getMonthDayString(long timestamp) {
		return new SimpleDateFormat(MONTH_DAY_FORMAT).format(timestamp);
	}

	/**
	 * 获取指定时间MM-dd HH:mm 格式
	 * @param timestamp
	 * @return String
	 */
	public static String getMonthDayTimeString(long timestamp) {
		return new SimpleDateFormat(MONTH_DAY_TIME_FORMAT).format(timestamp);
	}

	/**
	 * 获取指定时间的yyyy-MM-dd HH:mm:ss表示格式
	 * @param timestamp
	 * @return
	 */
	public static String getTimeString(long timestamp) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTimeInMillis(timestamp);
		return new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT).format(cal.getTimeInMillis());
	}

	/**
	 * 获取指定时间的YY/MM/dd hh:mm:ss表示格式
	 * @param timestamp
	 * @return
	 */
	public static String getTime2String(long timestamp) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTimeInMillis(timestamp);
		return new SimpleDateFormat(DATE_TIME_FORMAT_GAME_LOGIC).format(cal.getTimeInMillis());
	}

	/**
	 * 返回时间格式为yyyy-MM-dd HH:mm
	 * @param timestamp
	 * @return
	 */
	public static String getTimeMinutes(long timestamp) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTimeInMillis(timestamp);
		return new SimpleDateFormat(ONLY_MINUTES_FORMAT).format(cal.getTimeInMillis());
	}

	/**
	 * 返回时间格式为yyyy-MM-dd HH:mm
	 * @param time
	 * @return
	 */
	public static String getTimeMinutes(Date time) {
		if (time == null) return "";
		return getTimeMinutes(time.getTime());
	}

	/**
	 * 获取当前的时间
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String nowDate() {
		return new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT).format(new Date());
	}

	/**
	 * 获取当前的时间(只有时间)HH:mm:ss
	 * @return
	 */
	public static String nowDateOnlyTime() {
		return new SimpleDateFormat(TIME_FORMAT).format(new Date());
	}

	/**
	 * 获取指定日期的时间(HH:mm)
	 * @return
	 */
	public static String onlyTime(Date date) {
		return new SimpleDateFormat(ONLY_TIME_FORMAT).format(date);
	}

	/**
	 * 获取指定日期的时间(HH:mm)
	 * @return
	 */
	public static String onlyTime(long date) {
		return new SimpleDateFormat(ONLY_TIME_FORMAT).format(date);
	}

	/**
	 * 通过指定日历获取年份
	 * @param cal
	 * @return
	 */
	public static int getYear(Calendar cal) {
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 通过指定日历获取月份(1-12)
	 * @param cal
	 * @return 1月到12月分别返回1-12
	 */
	public static int getMonth(Calendar cal) {
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 通过指定日历获取天
	 * @param cal
	 * @return
	 */
	public static int getDayOfMonth(Calendar cal) {
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 通过指定日历获取星期
	 * @param cal
	 * @return 星期一到星期天分别对应1-7
	 */
	public static int getDayOfWeek(Calendar cal) {
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获取小时(24小时制)
	 * @param cal
	 * @return
	 */
	public static int getHourOfDay(Calendar cal) {
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取分钟
	 * @param cal
	 * @return
	 */
	public static int getMinute(Calendar cal) {
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * 获取秒
	 * @param cal
	 * @return
	 */
	public static int getSecond(Calendar cal) {
		return cal.get(Calendar.SECOND);
	}

	/**
	 * 返回当前的小时数(24小时 0-23)
	 * @return
	 */
	public static int getCurrentHour() {
		return getHourOfDay(Calendar.getInstance());
	}

	/**
	 * 保留date1的日期部分，保留Date2的时间部分
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date changeTime(Date date1, Date date2) {
		return new Date(date1.getTime() / DAY * DAY + date2.getTime() % DAY);
	}

	/**
	 * 比较两个日期的时间部分是否一致
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareTime(Date date1, Date date2) {
		return date1.getTime() % DAY == date2.getTime() % DAY;
	}

	/**
	 * 比较两个Date日期部分的大小
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(Date date1, Date date2) {
		long dayIndex1 = (date1.getTime() - timeEquation) / DateTimeUtil.DAY;
		long dayIndex2 = (date2.getTime() - timeEquation) / DateTimeUtil.DAY;
		return (int) (dayIndex1 - dayIndex2);
	}

	/**
	 * 根据输入日期的时间，获得下一次运行的时间的的毫秒数
	 * @param date
	 * @return
	 */
	public static long dayRunTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar runTime = Calendar.getInstance();
		runTime.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
		runTime.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		runTime.set(Calendar.SECOND, cal.get(Calendar.SECOND));
		return runTime.getTimeInMillis();
	}

	/**
	 * 如果是以周为周期进行循环，计算下一次启动的时间
	 * @param date
	 * @param dayOfWeek
	 *            从1开始到7
	 * @return
	 */
	public static long weekRunTime(Date date, byte dayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar runTime = Calendar.getInstance();
		if ((dayOfWeek + 8) % 7 >= runTime.get(Calendar.DAY_OF_WEEK)) {
			runTime.add(Calendar.DATE, (dayOfWeek + 8) % 7 - runTime.get(Calendar.DAY_OF_WEEK));
		} else {
			runTime.add(Calendar.DATE, (dayOfWeek + 8) % 7 - runTime.get(Calendar.DAY_OF_WEEK) + 7);
		}
		runTime.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
		runTime.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		runTime.set(Calendar.SECOND, cal.get(Calendar.SECOND));
		return runTime.getTimeInMillis();
	}

	/**
	 * 如果以月为周期启动，计算下一次启动时间
	 * @param date
	 * @param dayOfMonth
	 * @return
	 */
	public static long monthRunTime(Date date, byte dayOfMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar runTime = Calendar.getInstance();
		if (dayOfMonth >= runTime.get(Calendar.DAY_OF_MONTH)) {
			runTime.add(Calendar.DATE, dayOfMonth - runTime.get(Calendar.DAY_OF_MONTH));
		} else {
			runTime.add(Calendar.MONTH, 1);
			runTime.set(Calendar.DATE, dayOfMonth);
		}
		runTime.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
		runTime.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		runTime.set(Calendar.SECOND, cal.get(Calendar.SECOND));
		return runTime.getTimeInMillis();
	}

	/**
	 * 将指定的时间添加指定的天数
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	public static Date addDay(int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * 给指定的时间添加指定的小时
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	public static Date addMinute(int minute) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * @param date
	 * @param minute
	 * @return
	 * @description 给指定时间增加指定分钟数
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	public static Timestamp string2Timestamp(String time) {
		Timestamp date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT);
			Date d = sdf.parse(time);
			date = new Timestamp(d.getTime());
		} catch (Exception e) {
		}
		return date;
	}

	public static Date string2Date(String time) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT);
			date = sdf.parse(time);
		} catch (Exception e) {
			date = new Date();
		}
		return date;
	}

	public static String time2String(Date time) {
		String timeString = null;
		try {
			timeString = new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT).format(time);
		} catch (Exception e) {
			// logger.error("时间转字符串出错", e);
		}
		return timeString;
	}

	public static String time2String(long time) {
		Date d = new Date();
		d.setTime(time);
		return time2String(d);
	}

	public static String time2StringWhitChinese(Date time) {
		return new SimpleDateFormat(FRIENDLY_DATE_TIME_FORMAT_WITH_CHINESE).format(time);
	}

	/**
	 * 计算剩余的时间：返回格式：××天\××小时××分。 只返回剩余时间的最大单位和值；
	 * @param args
	 * @throws Exception
	 */
	public static String time2StringPrecise2Second(long t) {
		StringBuilder sb = new StringBuilder();
		long hour = 0l;
		long minute = 0l;

		hour = t / HOUR;
		if (hour != 0) {
			sb.append(hour).append(DAY_TIME_HOUR);
		}
		minute = t % HOUR / MINUTE;
		if (minute != 0) sb.append(minute).append(DAY_TIME_MINUTE);
		return sb.toString();
	}

	/**
	 * 获取整天日期
	 * @param dayTime
	 * @param offset
	 * @return
	 */
	public static final Calendar getDayDate(Calendar dayTime, int offset) {
		Calendar calendar = Calendar.getInstance();
		if (dayTime != null) calendar.setTimeInMillis(dayTime.getTimeInMillis());

		calendar.add(Calendar.DAY_OF_YEAR, offset);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}

	/**
	 * 获取指定时间的日历
	 * @param time
	 * @return
	 */
	public static final Calendar getCalendar(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);

		return calendar;
	}

	public static final String getWeeklyDetailByWeek(int week) {
		return WEEKLY_DETAIL[week];
	}

	/**
	 * 两个日期之间相隔天数的共通
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            　结束时间
	 * @return　天数
	 */
	public static int getDaysBetweenTwoDates(Date startDate, Date endDate) {
		long begin = startDate.getTime();
		long end = endDate.getTime();
		long inter = end - begin;
		if (inter < 0) {
			inter = inter * (-1);
		}

		long dateMillSec = 24 * 60 * 60 * 1000;
		long dateCnt = inter / dateMillSec;
		long remainder = inter % dateMillSec;

		if (remainder != 0) {
			dateCnt++;
		}
		return (int) dateCnt;
	}

	/**
	 * 判定是否是同一天
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static boolean isSameDay(long date1, long date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date1);
		int y1 = cal.get(Calendar.YEAR);
		int d1 = cal.get(Calendar.DAY_OF_YEAR);
		cal.setTimeInMillis(date2);
		int y2 = cal.get(Calendar.YEAR);
		int d2 = cal.get(Calendar.DAY_OF_YEAR);
		return (y1 == y2 && d1 == d2);
	}

	public static boolean isSameYear(long date1, long date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date1);
		int y1 = cal.get(Calendar.YEAR);
		cal.setTimeInMillis(date2);
		int y2 = cal.get(Calendar.YEAR);
		return y1 == y2;
	}

	/**
	 * 取得第二天零点的时间
	 */
	public static Date getNextDayZeroDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public enum formatter {
		varChar23(0, "varChar23", "时间类型:yyyy-MM-dd HH:mm;ss.SSS", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")),
		varChar19(1, "varChar19", "时间类型:yyyy-MM-dd HH:mm:ss", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")),
		varChar10(2, "varChar10", "时间类型:yyyy-MM-dd", new SimpleDateFormat("yyyy-MM-dd")),
		varChar7(3, "varChar7", "时间类型:yyyy-MM", new SimpleDateFormat("yyyy-MM")),
		varChar8(4, "varChar8", "时间类型:yyyy-MM-DD-HH-MM", new SimpleDateFormat("yyyy-MM-dd-HH-mm"));

		formatter(int id, String name, String des, DateFormat df) {
			this.id = id;
			this.name = name;
			this.des = des;
			this.df = df;
		}

		public String getDes() {
			return des;
		}

		public int getId() {
			return id;
		}

		public String format(Date d) {
			return df.format(d);
		}

		public long parse(String time) {
			synchronized (df) {
				Calendar calendar = Calendar.getInstance();
				try {
					if (time != null && !"".equals(time)) {
						Date date = df.parse(time);
						calendar.setTime(date);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return calendar.getTimeInMillis();
			}
		}

		public String format(long time) {
			synchronized (df) {
				Date date = new Date(time);
				return df.format(date);
			}
		}

		public String getMeg() {
			return getDes();
		}

		public String getName() {
			return name;
		}

		private int id;
		private String name;
		private String des;
		DateFormat df;
	}
}
