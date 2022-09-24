/*
This class provides util methods that are used in the application.
 */

package com.forumsServer.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    /**
     * @return the date and time in the format "yyyy-MM-dd HH:mm:ss"
     */
    public static String getDateTimeAsString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    /**
     * @return string containing the logged-in user id.
     */
    public static String getLoggedInUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
