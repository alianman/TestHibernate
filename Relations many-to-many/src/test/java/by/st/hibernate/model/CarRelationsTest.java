package by.st.hibernate.model;

import by.st.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.*;

import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by alian on 02.02.2016.
 */
public class CarRelationsTest extends Assert {
    private static Session session;
    protected static final Logger logger = LogManager.getLogger(CarRelationsTest.class);

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

    public void clearCars() {
        car1.setId(0);
        car2.setId(0);
        serviceStation1.setId(0);
        serviceStation2.setId(0);
    }

    @Test
    public void testCarsRelations_get() {
        clearCars();
        session.beginTransaction();
        session.save(car1);
        session.getTransaction().commit();

        logger.info("Inserted cars and stations");

        //session.clear();

        logger.info("Extracting car1 with id = " + car1.getId() + "...");
        session.beginTransaction();
        Car extracted = (Car) session.get(Car.class, car1.getId());
        session.getTransaction().commit();
        logger.info("Done!");

        logger.info("Extracting car1 services...");
        assertEquals(2, extracted.getServiceStations().size());
        logger.info("Done!");

        logger.info("Extracting car2 with id = " + car2.getId() + "...");
        session.beginTransaction();
        extracted = (Car) session.get(Car.class, car2.getId());
        session.getTransaction().commit();
        logger.info("Done!");

        logger.info("Extracting car2 services...");
        assertEquals(1, extracted.getServiceStations().size());
        logger.info("Done!");

        logger.info("Deleting cars and stations...");
        session.beginTransaction();
        session.delete(serviceStation1);
        session.getTransaction().commit();
        logger.info("Done!");
    }

    @Test
    public void testCarsRelations_update() {
        clearCars();

        session.beginTransaction();
        session.saveOrUpdate(car1);
        session.getTransaction().commit();

        logger.info("Inserted cars and stations");

        int size = car2.getServiceStations().size();
        ServiceStation serviceStation = new ServiceStation("test service 3");
        serviceStation.setCars(new HashSet<Car>());
        serviceStation.getCars().add(car2);
        car2.getServiceStations().add(serviceStation);

        logger.info("New service station: test service 3...");

        logger.info("Updating car 2...");
        session.beginTransaction();
        session.update(car2);
        session.getTransaction().commit();
        logger.info("Update complete!");

        assertEquals(size + 1, car2.getServiceStations().size());

        serviceStation.getCars().clear();
        car2.getServiceStations().remove(serviceStation);

        logger.info("Removing new service station...");

        logger.info("Updating car 2...");
        session.beginTransaction();
        session.update(car2);
        session.getTransaction().commit();
        logger.info("Update complete!");

        assertEquals(size, car2.getServiceStations().size());

        session.clear();

        session.beginTransaction();
        Car extracted = (Car) session.get(Car.class, car2.getId());
        session.getTransaction().commit();

        assertEquals(size, extracted.getServiceStations().size());

        logger.info("Deleting cars and stations...");
        session.beginTransaction();
        session.delete(extracted);
        session.getTransaction().commit();
        logger.info("Deleted!");
    }

    @Test
    public void testCarsRelations_delete() {
        clearCars();
        session.beginTransaction();
        session.save(car1);
        session.getTransaction().commit();

        logger.info("Deleting " + serviceStation2.getName() + " station...");
        session.beginTransaction();
        car1.getServiceStations().remove(serviceStation2);
        car2.getServiceStations().remove(serviceStation2);
        serviceStation2.setCars(null);
        session.delete(serviceStation2);
        session.getTransaction().commit();
        logger.info("Done!");

        session.clear();

        session.beginTransaction();
        ServiceStation extracted = (ServiceStation) session.get(ServiceStation.class, serviceStation2.getId());
        session.getTransaction().commit();

        assertNull(extracted);

        session.beginTransaction();
        session.delete(car1);
        session.getTransaction().commit();
    }

    @After
    public void closeSession() {
        session.close();
    }
}
