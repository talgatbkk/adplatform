package kz.epam.tcfp.model;


/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class Comment {

    private Integer authorId;
    private Integer adId;
    private String authorLogin;
    private String authorFirstName;
    private String authorLastName;
    private String content;
    private DateTimeInUTC postedDate;


    public Comment() {
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
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


    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

}
