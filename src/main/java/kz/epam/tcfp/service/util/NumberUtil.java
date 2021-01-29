package kz.epam.tcfp.service.util;

import org.apache.log4j.Logger;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class NumberUtil {
    private static final Logger LOGGER = Logger.getLogger(NumberUtil.class);
    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    public static final String ERROR_PARSING_NUMBER = "Error while parsing a number";

    private NumberUtil() {
    }

    public static Long tryParseLong(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        Long parsedLong;
        try {
            parsedLong = Long.parseLong(input);
        } catch (NumberFormatException e) {
            LOGGER.warn(ERROR_PARSING_NUMBER, e);
            return null;
        }
        return parsedLong;
    }

    public static Long tryCastToLong(Object input) {
        if (input == null) {
            return null;
        }
        Long castedLong;
        try {
            castedLong = (Long) input;
        } catch (ClassCastException e) {
            LOGGER.warn(ERROR_PARSING_NUMBER, e);
            return null;
        }
        return castedLong;
    }

    public static Integer tryParseInteger(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        Integer parsedValue;
        try {
            parsedValue = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            LOGGER.warn(ERROR_PARSING_NUMBER, e);
            return null;
        }
        return parsedValue;
    }

    public static Integer tryParsePageNumber(String input) {
        if (input == null || input.isEmpty()) {
            return DEFAULT_PAGE_NUMBER;
        }
        Integer parsedValue;
        try {
            parsedValue = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            LOGGER.warn(ERROR_PARSING_NUMBER, e);
            return DEFAULT_PAGE_NUMBER;
        }
        return parsedValue;
    }

}
