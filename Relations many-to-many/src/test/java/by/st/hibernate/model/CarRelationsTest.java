package by.st.hibernate.model;

import by.st.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by alian on 02.02.2016.
 */
public class CarRelationsTest extends Assert {
    private static Session session;

    private static Car car1 = new Car("test car 1");
    private static Car car2 = new Car("test car 2");
    private static ServiceStation serviceStation1 = new ServiceStation("service 1");
    private static ServiceStation serviceStation2 = new ServiceStation("service 2");


    @Before
    public void openSession() {
        try {
            session = HibernateUtils.getSession();
        } catch (Throwable e) {
            fail("failed to open session: " + e.getMessage());
        }
    }

    @Before
    public void createCars() {
        car1.setServiceStations(new HashSet<ServiceStation>());
        car1.getServiceStations().add(serviceStation1);
        car1.getServiceStations().add(serviceStation2);
        serviceStation1.setCars(new HashSet<Car>());
        serviceStation1.getCars().add(car1);
        serviceStation1.getCars().add(car2);
        serviceStation2.setCars(new HashSet<Car>());
        serviceStation2.getCars().add(car1);
        car2.setServiceStations(new HashSet<ServiceStation>());
        car2.getServiceStations().add(serviceStation1);
    }

    @Test
    public void testCarsRelations_get() {
        session.beginTransaction();
        session.save(car1);
        session.getTransaction().commit();

        session.clear();

        session.beginTransaction();
        Car extracted = (Car) session.get(Car.class, car1.getId());
        session.getTransaction().commit();

        assertEquals(2, extracted.getServiceStations().size());

        session.beginTransaction();
        extracted = (Car) session.get(Car.class, car2.getId());
        session.getTransaction().commit();

        assertEquals(1, extracted.getServiceStations().size());

        for (Car car : serviceStation2.getCars()) {
            car.getServiceStations().remove(serviceStation2);
            session.update(car);
        }
        session.delete(serviceStation2);
        for (Car car : serviceStation1.getCars()) {
            car.getServiceStations().remove(serviceStation1);
            session.update(car);
        }
        session.delete(serviceStation1);
    }

    @Test
    public void testCarsRelations_update() {
        session.beginTransaction();
        session.save(car1);
        session.getTransaction().commit();

        int size = car2.getServiceStations().size();
        ServiceStation serviceStation = new ServiceStation("test service 3");
        serviceStation.setCars(new HashSet<Car>());
        serviceStation.getCars().add(car2);
        car2.getServiceStations().add(serviceStation);

        session.beginTransaction();
        session.update(car2);
        session.getTransaction().commit();

        session.clear();

        session.beginTransaction();
        Car extracted = (Car) session.get(Car.class, car2.getId());
        session.getTransaction().commit();

        assertEquals(size + 1, extracted.getServiceStations().size());

        for (Car car : serviceStation2.getCars()) {
            car.getServiceStations().remove(serviceStation2);
            session.update(car);
        }
        session.delete(serviceStation2);
        for (Car car : serviceStation1.getCars()) {
            car.getServiceStations().remove(serviceStation1);
            session.update(car);
        }
        session.delete(serviceStation1);
    }

    @Test
    public void testCarsRelations_delete() {
        session.beginTransaction();
        session.save(car1);
        session.getTransaction().commit();

        session.beginTransaction();
        for (Car car : serviceStation2.getCars()) {
            car.getServiceStations().remove(serviceStation2);
            session.update(car);
        }
        session.delete(serviceStation2);
        session.getTransaction().commit();

        session.clear();

        session.beginTransaction();
        Car extracted = (Car) session.get(Car.class, serviceStation2.getId());
        session.getTransaction().commit();

        assertNull(extracted);

        for (Car car : serviceStation1.getCars()) {
            car.getServiceStations().remove(serviceStation1);
            session.update(car);
        }
        session.delete(serviceStation1);
    }

    @After
    public void closeSession() {
        session.close();
    }
}
