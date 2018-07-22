package org.interview.oauth.twitter.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

    private static final String TWITTER_TIME_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

    private DateUtil() { }

    public static Date formatDateStr(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(TWITTER_TIME_FORMAT);
        return sf.parse(date);
    }

}
