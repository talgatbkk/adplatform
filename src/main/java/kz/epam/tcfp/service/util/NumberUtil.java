package kz.epam.tcfp.service.util;

import kz.epam.tcfp.service.PagePath;
import kz.epam.tcfp.service.implementation.SearchAdvertisementService;
import org.apache.log4j.Logger;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class NumberUtil {
    private static final Logger LOGGER = Logger.getLogger(SearchAdvertisementService.class);

    public static Long tryParseLong(String input) {
        if (input == null) {
            return null;
        }
        Long parsedLong;
        try {
            parsedLong = Long.parseLong(input);
        } catch (NumberFormatException e) {
            LOGGER.warn("Error while parsing a number", e);
            return null;
        }
        return parsedLong;
    }
}
