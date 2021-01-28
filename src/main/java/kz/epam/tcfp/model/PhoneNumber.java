package kz.epam.tcfp.model;

import java.io.Serializable;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class PhoneNumber implements Serializable {
    private static final long serialVersionUID = 1234L;

    private Long phoneNumberId;
    private String phoneNumber;

    public PhoneNumber() {
    }

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumber(Long phoneNumberId, String phoneNumber) {
        this.phoneNumberId = phoneNumberId;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "phoneNumberId=" + phoneNumberId +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
