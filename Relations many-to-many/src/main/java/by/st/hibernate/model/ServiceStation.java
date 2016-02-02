package by.st.hibernate.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alian on 02.02.2016.
 */
public class ServiceStation {
    private long id;
    private String name;
    private Set<Car> cars = new HashSet<Car>();


    public ServiceStation() {
    }

    public ServiceStation(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceStation)) return false;

        ServiceStation that = (ServiceStation) o;

        if (getId() != that.getId()) return false;
        return !(getName() != null ? !getName().equals(that.getName()) : that.getName() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
