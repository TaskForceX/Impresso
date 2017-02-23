package com.baixing.impresso.batch.time;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * Created by onesuper on 06/02/2017.
 */
public class TimeIntervalTest {
    @Test
    public void testTime() throws Exception {
        TimeInterval epoch5s = new TimeInterval(0, new Seconds(5));
        Assert.assertEquals(5 * 1000, epoch5s.endsAt());

        TimeInterval epoch5m = new TimeInterval(0, new Minutes(5));
        Assert.assertEquals(5 * 60_000, epoch5m.endsAt());

        TimeInterval epoch5h = new TimeInterval(0, new Hours(5));
        Assert.assertEquals(5 * 60 * 60_000, epoch5h.endsAt());
    }

    @Test
    public void testEquals() throws Exception {
        TimeInterval t1 = new TimeInterval(0, new Seconds(20));
        TimeInterval t2 = new TimeInterval(0, new Seconds(20));
        Assert.assertEquals(t1, t2);
    }

    @Test
    public void testNextTimeInterval() throws Exception {
        TimeInterval t = new TimeInterval(0, new Seconds(20));
        Assert.assertEquals(20 * 1_000, t.nextTimeInterval().startsAt());
        Assert.assertEquals(40 * 1_000, t.nextTimeInterval().endsAt());
    }

    @Test
    public void testNextFewSeconds() throws Exception {
        long epoch = 1486369618000L;
        long millis = 1486369618123L;
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(epoch));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(millis));

        Iterator<TimeInterval> it = new TimeIntervalGenerator(
                new TimeInterval(millis, new Seconds(5)));

        TimeInterval t1 = it.next();
        System.out.println(t1);
        Assert.assertEquals(new TimeInterval(epoch, new Seconds(5)), t1);

        TimeInterval t2 = it.next();
        System.out.println(t2);
        Assert.assertEquals(new TimeInterval(epoch, new Seconds(5)).nextTimeInterval(), t2);
    }

    @Test
    public void testNextFewMinutes() throws Exception {
        long epoch = 1486369560000L;
        long millis = 1486369618123L;
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(epoch));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(millis));

        Iterator<TimeInterval> it = new TimeIntervalGenerator(
                new TimeInterval(millis, new Minutes(10)));

        TimeInterval t1 = it.next();
        System.out.println(t1);
        Assert.assertEquals(new TimeInterval(epoch, new Minutes(10)), t1);

        TimeInterval t2 = it.next();
        System.out.println(t2);
        Assert.assertEquals(new TimeInterval(epoch, new Minutes(10)).nextTimeInterval(), t2);
    }

    @Test
    public void testNextFewHours() throws Exception {
        long epoch = 1486369440000L;
        long millis = 1486369618123L;
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(epoch));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(millis));

        Iterator<TimeInterval> it = new TimeIntervalGenerator(
                new TimeInterval(millis, new Hours(3)));

        TimeInterval t1 = it.next();
        System.out.println(t1);
        Assert.assertEquals(new TimeInterval(epoch, new Hours(3)), t1);

        TimeInterval t2 = it.next();
        System.out.println(t2);
        Assert.assertEquals(new TimeInterval(epoch, new Hours(3)).nextTimeInterval(), t2);
    }
}