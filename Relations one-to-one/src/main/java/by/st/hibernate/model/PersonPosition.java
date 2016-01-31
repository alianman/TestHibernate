package by.st.hibernate.model;

/**
 * Created by alian on 31.01.2016.
 */
public class PersonPosition {
    private long personId;
    private double x;
    private double y;
    private Person person;

    public PersonPosition() {
    }

    public PersonPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long id) {
        this.personId = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonPosition)) return false;

        PersonPosition that = (PersonPosition) o;

        if (getPersonId() != that.getPersonId()) return false;
        if (Double.compare(that.getX(), getX()) != 0) return false;
        if (Double.compare(that.getY(), getY()) != 0) return false;
        return !(getPerson() != null ? !getPerson().equals(that.getPerson()) : that.getPerson() != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (getPersonId() ^ (getPersonId() >>> 32));
        temp = Double.doubleToLongBits(getX());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getY());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getPerson() != null ? getPerson().hashCode() : 0);
        return result;
    }
}
