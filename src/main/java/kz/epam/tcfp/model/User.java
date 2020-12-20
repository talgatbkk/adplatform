package kz.epam.tcfp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1234L;

    private Integer userId;
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private Date createdTime;
    private String password;

    public User() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", createdTime=" + createdTime +
                ", password='" + password + '\'' +
                '}';
    }
}
