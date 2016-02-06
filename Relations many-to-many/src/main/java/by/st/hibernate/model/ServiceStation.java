package by.st.hibernate.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alian on 02.02.2016.
 */
@Entity
public class ServiceStation {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "serviceStations", targetEntity = Car.class)
    @NotNull
    private Set<Car> cars = new HashSet<Car>();


    public ServiceStation() {
    }

    public ServiceStation(String name) {
        this.name = name;
    }

    public ServiceStation(long id, String name) {
        this.id = id;
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

        if (id != that.id) return false;
        return name.equals(that.name);

    }
}
