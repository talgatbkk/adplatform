package kz.epam.tcfp.model;



import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class DateTimeInUTC implements Serializable {

    private static final long serialVersionUID = 1234L;
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
