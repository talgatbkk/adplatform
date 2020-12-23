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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SignInInput)) return false;
        SignInInput that = (SignInInput) o;
        return Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword());
    }
}
