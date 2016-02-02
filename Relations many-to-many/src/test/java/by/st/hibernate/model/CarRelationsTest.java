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
    private static Set<Car> cars = new HashSet<Car>();
    private static Set<ServiceStation> serviceStations = new HashSet<ServiceStation>();

    private static int getRandomIn(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

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

        int carsCount = getRandomIn(1, 50);
        int servicesCount = getRandomIn(1, 6);

        for (int i = 0; i < carsCount; i++) {
            cars.add(new Car("car #" + (i + 1)));
        }
        for (int i = 0; i < servicesCount; i++) {
            serviceStations.add(new ServiceStation("service station #" + (i + 1)));
        }

        for (Car car : cars) {
            Set<ServiceStation> carServices = new HashSet<ServiceStation>();
            ServiceStation[] serviceStationsArray = serviceStations.toArray(new ServiceStation[serviceStations.size()]);
            int serviceCount = getRandomIn(0, servicesCount);
            for (int i = 0; i < serviceCount; i++) {
                ServiceStation serviceStation = serviceStationsArray[getRandomIn(0, serviceStationsArray.length - 1)];
                serviceStation.getCars().add(car);
                carServices.add(serviceStation);
            }
            car.setServiceStations(carServices);
        }
    }

    @Test
    public void testCarsRelations() {
        session.beginTransaction();
        for (Car car : cars) {
            session.save(car);
        }
        for (ServiceStation serviceStation : serviceStations) {
            session.save(serviceStation);
        }
        session.getTransaction().commit();
    }

    @After
    public void closeSession() {
        session.close();
    }
}
