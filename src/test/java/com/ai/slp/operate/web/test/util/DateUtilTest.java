package com.ai.slp.operate.web.test.util;

import com.ai.slp.operate.web.util.DateUtil;
import org.junit.Test;

import java.sql.Timestamp;

/**
 * Created by jackieliu on 16/7/28.
 */
public class DateUtilTest {

    @Test
    public void getTheDayStartAndEnd(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("The start time:"+DateUtil.getTheDayFirstSecond(timestamp));
        System.out.println("The end time:"+DateUtil.getTheDayLastSecond(timestamp));
    }

    @Test
    public void getTheMonthStartAndEnd(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("The start time of month is :"+DateUtil.getTimeThisMonthFirstSec(timestamp));
        System.out.println("The end time of month is :"+DateUtil.getTimeThisMonthLastSec(timestamp));
    }

    @Test
    public void getTimeNextMonthFirstSec(){
        System.out.println(DateUtil.getCurYM()+":"+DateUtil.getDates());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(DateUtil.getTimeNextMonthFirstSec(timestamp));
        System.out.println(DateUtil.getTimeLastMonthLastSec(timestamp));
    }
}
