package kz.epam.tcfp.model.inputform;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class SignInInput implements Serializable {
    private static final long serialVersionUID = 1234L;

    private String login;

    private String password;

    public SignInInput() {
    }

    public SignInInput(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
