package com.olgaruban;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void determinateUserTime() {
        LocalTime timeSart = LocalTime.now(ZoneId.of("Europe/Kiev"));
        LocalTime timeEnd = timeSart.minusMinutes(-5);

        LocalTime time = Main.determinateUserTime("Kiev", "");
        boolean inTheInterval = time.compareTo(timeSart) >= 0 && time.compareTo(timeEnd) < 0;
        Assert.assertEquals(inTheInterval, true);
    }

    @Test
    public void determinateUserTimeZoneByCity() {
        Assert.assertEquals("America/Toronto", Main.determinateUserTimeZoneByCity("Toronto"));
    }

    @Test
    public void determinePartOfDay() {
        LocalTime time = LocalTime.of(6, 0,0);
        Assert.assertEquals(Main.NIGHT, Main.determinePartOfDay(time));
    }
}