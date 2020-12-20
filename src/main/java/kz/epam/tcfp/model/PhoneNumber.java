package kz.epam.tcfp.model;

/**
 * @author Talgat Bekkaliyev
 * @project AdPlatform
 */
public class PhoneNumber {
    private static final long serialVersionUID = 1234L;

    private Integer phoneNumberId;
    private String phoneNumber;

    public PhoneNumber(Integer phoneNumberId, String phoneNumber) {
        this.phoneNumberId = phoneNumberId;
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
