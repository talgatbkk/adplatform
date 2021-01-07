package kz.epam.tcfp.model;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class PrettyDateTime {
    private static final PrettyTime RUSSIAN_FORMATTER = new PrettyTime(new Locale("ru"));
    private static final PrettyTime ENGLISH_FORMATTER = new PrettyTime(new Locale("en"));
    private Date postedDate;

    public PrettyDateTime() {
    }

    public PrettyDateTime(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }
    public String getTimeInRussian() {
        return RUSSIAN_FORMATTER.format(postedDate);
    }

    public String getTimeInEnglish() {
        return ENGLISH_FORMATTER.format(postedDate);
    }
}
