package com.meta.ponkids.global.util.date;

import java.util.TimeZone;

public interface DateConstants {
    
    /**
     * 1일을 밀리초로 환산한 값(86400000 = 24*60*60*1000)
     */
    long MILLISECOND_OF_DAY = 24 * 60 * 60 * 1000;
    /**
     * 현재로케일에서 그리니치기준시와의 차이를 밀리세컨드로 환산한 값.
     */
    long GMT_RAW_OFFSET = TimeZone.getDefault().getRawOffset();
    
    /**
     * 기본형식(yyyyMMddHHmmss)
     */
    String DF_BASE = "yyyyMMddHHmmss";
    /**
     * 기본일자형식(yyyyMMdd)
     */
    String DF_DATE = "yyyyMMdd";
    /**
     * 기본시간형식(HHmmss)
     */
    String DF_TIME = "HHmmss";
    
    /**
     * 기본년월형식(yyyyMM)
     */
    String DF_YYYYMM = "yyyyMM";
    /**
     * 기본월일형식(MMdd)
     */
    String DF_MMDD = "MMdd";
    /**
     * 기본일형식(dd)
     */
    String DF_DD = "dd";
    /**
     * 기본시분형식(HHmm)
     */
    String DF_HHMM = "HHmm";
    
    /**
     * 기본년월일시분형식 (yyyyMMddHHmm)
     */
    String DF_YYYYMMDDHHMM = "yyyyMMddHHmm";
    /**
     * 기본년월일시분초형식 (yyyyMMddHHmmss)
     */
    String DF_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    
    /**
     * 간략일자형식 (yyMMdd)
     */
    String DF_YYMMDD = "yyMMdd";
    
    /**
     * 계산서일련번호발급시사용(yyMM)
     */
    String DF_YYMM = "yyMM";
    
    /**
     * 년월일 Display 형식 (yyyy-MM-dd)
     */
    String DF_DATE_DP = "yyyy-MM-dd";
    /**
     * 년월일시분 Display 형식 (yyyy-MM-dd HH:mm)
     */
    String DF_YYYYMMDDHHMM_DP = "yyyy-MM-dd HH:mm";
    /**
     * 년월일시분초 Display 형식 (yyyy-MM-dd HH:mm:ss)
     */
    String DF_YYYYMMDDHHMMSS_DP = "yyyy-MM-dd HH:mm:ss";
    /**
     * 년월일 Display 형식 (yyyy/MM/dd)
     */
    String DF_DATE_SLASH = "yyyy/MM/dd";
    /**
     * 시분 Display 형식 (HH:mm)
     */
    String DF_TIME_SLASH = "HH:mm";
    
    /**
     * 초기일자({@link #DF_DATE}) : "00010101"
     */
    String MINIMUM_DATE = "00010101";
    /**
     * 최종일자({@link #DF_DATE}) : "99991231"
     */
    String MAXIMUM_DATE = "99991231";
    /**
     * 년도초기일자({@link #DF_MMDD}) : "0101"
     */
    String MINIMUN_MMDD = "0101";
    /**
     * 년도최종일자({@link #DF_MMDD}) : "1231"
     */
    String MAXIMUM_MMDD = "1231";
    /**
     * 월최소일수({@link #DF_DD}) : "01"
     */
    String MINIMUN_DAY = "01";
    /**
     * 월최대일수({@link #DF_DD}) : "31"
     */
    String MAXIMUM_DAY = "31";
    /**
     * 일자초기시간({@link #DF_TIME}) : "000000"
     */
    String MINIMUN_TIME = "000000";
    /**
     * 일자최종시간({@link #DF_TIME}) : "235959"
     */
    String MAXIMUM_TIME = "235959";
    
    /**
     * 요일(한글명)
     */
    String[] DAY_OF_WEEKS_KOREAN = { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
    /**
     * 요일(축약 한글명)
     */
    String[] DAY_OF_WEEKS_SHORT_KOREAN = { "일", "월", "화", "수", "목", "금", "토" };
    /**
     * 요일(영문명)
     */
    String[] DAY_OF_WEEKS_ENGLISH = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
    /**
     * 월 (숫자)
     */
    String[] MONTHS_OF_YEAR = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
}
