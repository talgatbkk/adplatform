package kz.epam.tcfp.service.util;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class LanguageUtil {
    private static final List<String> ISO_LANGUAGES_LIST = Arrays.asList(Locale.getISOLanguages());
    private static final Logger LOGGER = Logger.getLogger(LanguageUtil.class);

    private LanguageUtil() {
    }

    public static boolean validateLanguageCode(Object languageCodeInput) {
        try {
            String languageCode = (String) languageCodeInput;
            if (languageCode == null || languageCode.isEmpty()) {
                return false;
            }
            if (ISO_LANGUAGES_LIST.contains(languageCode)) {
                return true;
            }
        } catch (ClassCastException e) {
            LOGGER.warn("Incorrect language code", e);
        }
        return false;
    }

    public static String getLanguageCode(Object languageCodeInput) {
        try {
            String languageCode = (String) languageCodeInput;
            if (languageCode == null || languageCode.isEmpty()) {
                return null;
            }
            if (ISO_LANGUAGES_LIST.contains(languageCode)) {
                return languageCode;
            }
        } catch (ClassCastException e) {
            LOGGER.warn("Incorrect language code", e);
        }
        return null;
    }


}
