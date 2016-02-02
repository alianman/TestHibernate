package by.st.hibernate.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alian on 02.02.2016.
 */
public class Car {
    private long id;
    private String model;
    private Set<ServiceStation> serviceStations = new HashSet<ServiceStation>();

    public Car() {
    }

    public Car(String model) {
        this.model = model;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Set<ServiceStation> getServiceStations() {
        return serviceStations;
    }

    public void setServiceStations(Set<ServiceStation> serviceStations) {
        this.serviceStations = serviceStations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;

        Car car = (Car) o;

        if (getId() != car.getId()) return false;
        return !(getModel() != null ? !getModel().equals(car.getModel()) : car.getModel() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getModel() != null ? getModel().hashCode() : 0);
        return result;
    }
}
