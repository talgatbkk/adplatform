package kz.epam.tcfp.model;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class DateTimeInUTC {

    private Date postedDate;

    public DateTimeInUTC() {
    }

    public DateTimeInUTC(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public LocalDateTime getTime() {
        return Instant.ofEpochMilli(postedDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

    }

}
