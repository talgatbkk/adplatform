package kz.epam.tcfp.model;


import java.io.Serializable;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Comment implements Serializable {

    private static final long serialVersionUID = 1234L;
    private Long authorId;
    private Long adId;
    private String authorLogin;
    private String authorFirstName;
    private String authorLastName;
    private String content;
    private DateTimeInUTC postedDate;


    public Comment() {
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorLogin() {
        return authorLogin;
    }

    public void setAuthorLogin(String authorLogin) {
        this.authorLogin = authorLogin;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTimeInUTC getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(DateTimeInUTC postedDate) {
        this.postedDate = postedDate;
    }


    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

}
