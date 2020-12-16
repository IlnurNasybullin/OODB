package lab_8.dbManager.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;

public class SQLDateConverter {

    public static Object getTime(Class<?> type, Time time) {
        LocalTime localTime = time.toLocalTime();
        if (type == LocalTime.class) {
            return localTime;
        }

        if (type == OffsetTime.class) {
            return OffsetTime.of(localTime, OffsetTime.now().getOffset());
        }

        throw new UnsupportedOperationException(String.format("Time with type %s isn't converted from java.sql.Time!", type));
    }

    public static Object getDateTime(Class<?> type, Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        if (type == LocalDateTime.class) {
            return localDateTime;
        }

        if (type == ZonedDateTime.class) {
            return ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        }

        if (type == OffsetDateTime.class) {
            ZoneOffset offset = OffsetDateTime.now().getOffset();
            return OffsetDateTime.of(localDateTime, offset);
        }

        throw new UnsupportedOperationException(String.format("Datetime with type %s isn't converted from java.sql.Timestamp!", type));
    }

    public static Object getDate(Class<?> type, Date date) {
        if (type == LocalDate.class) {
            return date.toLocalDate();
        }

        if (type == java.util.Date.class) {
            return new java.util.Date(date.getTime());
        }

        throw new UnsupportedOperationException(String.format("Date with type %s isn't converted from java.sql.Date!", type));
    }

    public static boolean isTime(Class<?> type) {
        return type == LocalTime.class || type == OffsetTime.class;
    }

    public static boolean isDateTime(Class<?> type) {
        return type == LocalDateTime.class || type == ZonedDateTime.class || type == OffsetDateTime.class;
    }

    public static boolean isDate(Class<?> type) {
        return type == java.util.Date.class || type == LocalDate.class;
    }
}
