package com.olgaruban;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
import java.time.ZoneId;

public class MainTest {

    @Test
    public void determinateUserTimeZone() {
        ZoneId zoneId = Main.determinateUserTimeZone("Bujumbura", "fsfs");
        Assert.assertEquals("Africa/Bujumbura", zoneId.getId());
        zoneId = Main.determinateUserTimeZone("Kiev", "GMT+05:00");
        Assert.assertEquals("GMT+05:00", zoneId.getId());
        zoneId = Main.determinateUserTimeZone("Qatar", "");
        Assert.assertEquals("Asia/Qatar", zoneId.getId());
    }

    @Test
    public void determinateUserTimeZoneByCity() {
        Assert.assertEquals("America/Toronto", Main.determinateUserTimeZoneByCity("Toronto"));
        Assert.assertEquals("Asia/Istanbul", Main.determinateUserTimeZoneByCity("Istanbul"));
        Assert.assertEquals("Europe/Kiev", Main.determinateUserTimeZoneByCity("Kiev"));
        Assert.assertEquals("GMT", Main.determinateUserTimeZoneByCity("RGsDFSDF"));
    }

    @Test
    public void determinePartOfDay() {
        Assert.assertEquals(Main.NIGHT, Main.determinePartOfDay(LocalTime.of(6,0,0)));
        Assert.assertEquals(Main.NIGHT, Main.determinePartOfDay(LocalTime.of(2,0,0)));
        Assert.assertEquals(Main.NIGHT, Main.determinePartOfDay(LocalTime.of(5,0,0)));
        Assert.assertEquals(Main.MORNING, Main.determinePartOfDay(LocalTime.of(6,0,1)));
        Assert.assertEquals(Main.MORNING, Main.determinePartOfDay(LocalTime.of(7,50,1)));
        Assert.assertEquals(Main.MORNING, Main.determinePartOfDay(LocalTime.of(8,55,58)));
        Assert.assertEquals(Main.AFTERNOON, Main.determinePartOfDay(LocalTime.of(11,25,0)));
        Assert.assertEquals(Main.AFTERNOON, Main.determinePartOfDay(LocalTime.of(15,25,45)));
        Assert.assertEquals(Main.AFTERNOON, Main.determinePartOfDay(LocalTime.of(18,58,35)));
        Assert.assertEquals(Main.EVENING, Main.determinePartOfDay(LocalTime.of(19,1,0)));
        Assert.assertEquals(Main.EVENING, Main.determinePartOfDay(LocalTime.of(20,1,0)));
        Assert.assertEquals(Main.EVENING, Main.determinePartOfDay(LocalTime.of(22,59,12)));
    }

    @Test
    public void parseArgs() {
        String[] cityAndTimeZone = Main.parseArgs(new String[]{"New", "York", "GMT-2"});
        Assert.assertEquals("New York", cityAndTimeZone[0]);
        Assert.assertEquals("GMT-2", cityAndTimeZone[1]);

        cityAndTimeZone = Main.parseArgs(new String[]{"Los", "Angeles"});
        Assert.assertEquals("Los Angeles", cityAndTimeZone[0]);
        Assert.assertEquals("", cityAndTimeZone[1]);

        cityAndTimeZone = Main.parseArgs(new String[]{"Moscow", "GMT+5"});
        Assert.assertEquals("Moscow", cityAndTimeZone[0]);
        Assert.assertEquals("GMT+5", cityAndTimeZone[1]);

        cityAndTimeZone = Main.parseArgs(new String[]{"Kiev"});
        Assert.assertEquals("Kiev", cityAndTimeZone[0]);
        Assert.assertEquals("", cityAndTimeZone[1]);
    }
}