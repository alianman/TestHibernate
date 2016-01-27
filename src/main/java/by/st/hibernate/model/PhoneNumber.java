package by.st.hibernate.model;

/**
 * Created by Administrator on 27.01.16.
 */
public class PhoneNumber {
    private String internationalCode;
    private String providerCode;
    private String phoneNumber;

    public PhoneNumber() {
    }

    public PhoneNumber(String internationalCode, String providerCode, String phoneNumber) {
        this.internationalCode = internationalCode;
        this.providerCode = providerCode;
        this.phoneNumber = phoneNumber;
    }

    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        if (!getInternationalCode().equals(that.getInternationalCode())) return false;
        if (!getProviderCode().equals(that.getProviderCode())) return false;
        return getPhoneNumber().equals(that.getPhoneNumber());

    }

    @Override
    public int hashCode() {
        int result = getInternationalCode().hashCode();
        result = 31 * result + getProviderCode().hashCode();
        result = 31 * result + getPhoneNumber().hashCode();
        return result;
    }
}
