package kz.epam.tcfp.model.inputform;

import kz.epam.tcfp.model.PhoneNumber;

import java.sql.Timestamp;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class SignUpInput {

    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private Timestamp createdTime;
    private String password;
    private PhoneNumber phoneInfo;

    public SignUpInput() {
        this.createdTime = new java.sql.Timestamp(new java.util.Date().getTime());
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PhoneNumber getPhoneInfo() {
        return phoneInfo;
    }

    public void setPhoneInfo(PhoneNumber phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }
}
