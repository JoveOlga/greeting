package com.olgaruban;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public final static String MORNING_START = "06:00:00";
    public final static String MORNING_END = "09:00:00";

    public final static String AFTERNOON_START = "09:00:00";
    public final static String AFTERNOON_END = "19:00:00";

    public final static String EVENING_START = "19:00:00";
    public final static String EVENING_END = "23:00:00";

    public final static String MORNING = "morning";
    public final static String AFTERNOON = "afternoon";
    public final static String EVENING = "evening";
    public final static String NIGHT = "night";
    public final static String DEFAULT_TIME_ZONE_ID = "GMT";

    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        initLogger();

        String[] cityAndTimeZone = parseArgs(args);

        LocalTime timeNow = LocalTime.now(determinateUserTimeZone(cityAndTimeZone[0], cityAndTimeZone[1]));

        printGreeting(timeNow, cityAndTimeZone[0]);
    }

    public static void initLogger() {
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(Level.OFF);
        try {
            FileHandler fileHandler = new FileHandler("greetting.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    public static String[] parseArgs(String[] args) {
        String city = "";
        String timeZoneId = "";

        if (args.length == 0) {
            logger.severe("Invalid arguments");
            System.exit(0);
        }

        if (args.length >= 3) {
            city = args[0] + " " + args[1];
            timeZoneId = args[2];
        }

        if (args.length == 2) {
            Pattern p = Pattern.compile("((?!GMT|UTC|UT)[a-zA-Z])*");
            Matcher m = p.matcher(args[1]);

            if (m.matches()) {
                city = args[0] + " " + args[1];
            } else {
                city = args[0];
                timeZoneId = args[1];
            }
        }

        if (args.length == 1) {
            city = args[0];
        }

        return new String[]{city, timeZoneId};
    }

    public static ZoneId determinateUserTimeZone(String city, String timeZoneId) {
        ZoneId zoneId;

        try {
            zoneId = ZoneId.of(timeZoneId);
        } catch (DateTimeException e) {
            zoneId = ZoneId.of(determinateUserTimeZoneByCity(city));
        }

        return zoneId;
    }

    public static String determinateUserTimeZoneByCity(String city) {
        String[] ids = TimeZone.getAvailableIDs();
        String transformedCity = city.replace(' ', '_');
        for (String id : ids) {
            if (id.contains(transformedCity)) {
                return id;
            }
        }
        return DEFAULT_TIME_ZONE_ID;
    }


    public static String determinePartOfDay(LocalTime timeNow) {
        String timeString = timeNow.format(DateTimeFormatter.ISO_LOCAL_TIME);
        if (timeString.compareTo(MORNING_START) > 0 && timeString.compareTo(MORNING_END) <= 0) {
            return MORNING;
        }
        if (timeString.compareTo(AFTERNOON_START) > 0 && timeString.compareTo(AFTERNOON_END) <= 0) {
            return AFTERNOON;
        }
        if (timeString.compareTo(EVENING_START) > 0 && timeString.compareTo(EVENING_END) <= 0) {
            return EVENING;
        }
        return NIGHT;
    }

    public static void printGreeting(LocalTime timeNow, String city) {
        String timeOfDay = determinePartOfDay(timeNow);

        Locale defaultLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("MessageResouce", defaultLocale);
        String greeting = bundle.getString(timeOfDay) + ", " + city + "!";
        System.out.println(greeting);
    }

}
