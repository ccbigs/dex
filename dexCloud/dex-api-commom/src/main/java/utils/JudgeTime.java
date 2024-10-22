package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JudgeTime {
    /*
     * @author: 左先生
     * @date: 2021-07-21 15:18
     * @description: 判断属于的日期
     */
    public static int JudgeQuarter(String YMSHMS) throws ParseException {
        String[] time = getTimeNow.dataBasicToYMD(YMSHMS).split("-");
        String[] NowTime =new getTimeNow().getTimeNowYMD().split("-");

        if (time[0].equals(NowTime[0])){
            if ("01".equals(time[1]) || "02".equals(time[1]) || "03".equals(time[1])){
                return 1;
            }else if ("04".equals(time[1]) || "05".equals(time[1]) || "06".equals(time[1])){
                return 2;
            }else if ("07".equals(time[1]) || "08".equals(time[1]) || "09".equals(time[1])){
                return 3;
            }else {
                return 4;
            }
        }else {
            return 0;
        }
    }

    /**
     * @author: 左先生
     * @date: 2021-07-17 14:35
     * @description:工具类方法，主要功能是判断引入的日期是不是七天之前的日期，
     * 如果是的话，返回前7天对应的天数，如果不是的话，就返回1
     */
    public static int JudgeSevenDay(String YMSHMS) throws ParseException{
        LocalDate nowDate = LocalDate.now();

        String[] time = getTimeNow.dataBasicToYMD(YMSHMS).split("-");

        LocalDate lateTime = LocalDate.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));

        /*返回两个日期的时间差*/
        int day =(int) nowDate.until(lateTime, ChronoUnit.DAYS);
        /*0,-1,-2,-3,-4,-5,-6*/
        if (day > 0 || day <=-7){
            return 1;
        }else{
            return day;
        }
    }

    /*
     * @author: 左先生
     * @date: 2021-07-21 15:19
     * @description:比较两个日期，若是在前面则返回小于1，若是在同一天，就返回0，如果在之后，就大于1,用于yyyy-MM-dd HH:mm:ss格式
     */
    public static int JudgeOneDayToOtherDayByYMDHMS(String time1,String time2) throws ParseException {

        /*格式化两个日期，要求两个日期都满足YYYY-MM-DD格式*/
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time1Date=fmt.parse(time1);
        Date time2Date=fmt.parse(time2);

        return time1Date.compareTo(time2Date);
    }

    /*
     * @author: 左先生
     * @date: 2021-07-21 15:37
     * @description:比较两个日期，若是在前面则返回小于1，若是在同一天，就返回0，如果在之后，就大于1,用于yyyy-MM-dd格式
     */
    public static int JudgeOneDayToOtherDayByYMD(String time1,String time2) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date time1Date=fmt.parse(time1);
        Date time2Date=fmt.parse(time2);

        return time1Date.compareTo(time2Date);
    }



}
