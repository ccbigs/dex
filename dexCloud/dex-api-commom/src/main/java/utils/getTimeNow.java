package utils;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class getTimeNow {

    public String getTimeNowYMD(){
        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");

        return simpleDateFormat.format(date);
    }

    public static String getStaticTimeNowYMD(){
        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");

        return simpleDateFormat.format(date);
    }

    /*返回年月日小时分钟秒*/
    public String getTimeNowYMDHMS(){
        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        return simpleDateFormat.format(date);
    }

    /*返回年月日小时分钟秒*/
    public static String getStaticTimeNowYMDHMS(){
        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        return simpleDateFormat.format(date);
    }

    /*增加月份后返回的年月日*/
    public static String getMonthAddYMS(int month){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,month);
        Date dt=calendar.getTime();
        return sdf.format(dt);
    }

    /*增加天数后返回的年月日*/
    public static String getDayAddYMS(int day){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,day);
        Date dt=calendar.getTime();
        return sdf.format(dt);
    }

    /*增加分钟后返回的时间*/
    public static String getMinuteAddYMDHMS(int minute){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,minute);
        Date dt=calendar.getTime();
        return sdf.format(dt);
    }

    /*将数据库中读取的日期的后面的0去掉*/
    public static String dataBasicToYMDHMS(String dataBasicDate) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //创建要显示的日期格式
        //注意了，这里的   MM 在java中代表月份，而  mm 代表分钟， HH 代表24小时制的时间， hh 代表12小时制的时间,很严格的
        Date date = fmt.parse(dataBasicDate);
        return fmt.format(date);
    }

    /*将数据库中读取的日期转化为年月日*/
    public static String dataBasicToYMD(String dataBasicDate) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); //创建要显示的日期格式
        //注意了，这里的   MM 在java中代表月份，而  mm 代表分钟， HH 代表24小时制的时间， hh 代表12小时制的时间,很严格的
        Date date = fmt.parse(dataBasicDate);
        return fmt.format(date);
    }


    /*将字符串类型的时间转换为Date类型*/
    public static Date CheckStringDateToDate (String stringDate) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //使用SimpleDateFormat的parse()方法生成Date
        return sf.parse(stringDate);
    }

    /*将Date类型转换为String*/
    public static String DateToStringYMDHMS(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }



}
