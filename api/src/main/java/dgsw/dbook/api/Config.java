package dgsw.dbook.api;

import dgsw.dbook.api.Exception.UserException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Config {

    public static LocalDateTime parseDateString(String dateString) {
        try {
            return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            throw new UserException(400, "Unparsable String");
        }
    }

    public static String parseLocalDateTime(LocalDateTime localDateTime) {
        try {
            return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            throw new UserException(400, "Unparsable LocalDateTime");
        }
    }

}
