package by.st.hibernate.model;

/**
 * Created by Administrator on 27.01.16.
 */
public class Driver extends Person {
    private String licenseNumber;
    private String carModel;

    public Driver() {
    }

    public Driver(String name, String address, PhoneNumber phoneNumber, String licenseNumber, String carModel) {
        super(name, address, phoneNumber);
        this.licenseNumber = licenseNumber;
        this.carModel = carModel;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Driver driver = (Driver) o;

        if (!getLicenseNumber().equals(driver.getLicenseNumber())) return false;
        return getCarModel().equals(driver.getCarModel());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getLicenseNumber().hashCode();
        result = 31 * result + getCarModel().hashCode();
        return result;
    }
}
