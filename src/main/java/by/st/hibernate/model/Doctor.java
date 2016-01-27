package by.st.hibernate.model;

/**
 * Created by Administrator on 27.01.16.
 */
public class Doctor extends Person {
    private String specializationName;
    private String hospitalName;

    public Doctor() {
    }

    public Doctor(String name, String address, PhoneNumber phoneNumber, String specializationName, String hospitalName) {
        super(name, address, phoneNumber);
        this.specializationName = specializationName;
        this.hospitalName = hospitalName;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Doctor doctor = (Doctor) o;

        if (!getSpecializationName().equals(doctor.getSpecializationName())) return false;
        return getHospitalName().equals(doctor.getHospitalName());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getSpecializationName().hashCode();
        result = 31 * result + getHospitalName().hashCode();
        return result;
    }
}
