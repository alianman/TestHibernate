package by.st.hibernate.model;

import by.st.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.*;

import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Created by alian on 02.02.2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarRelationsTest extends Assert {
    private static Session session;
    protected static final Logger logger = LogManager.getLogger(CarRelationsTest.class);

    private static Car car1 = new Car("test car 1");
    private static Car car2 = new Car("test car 2");
    private static ServiceStation serviceStation1 = new ServiceStation("service 1");
    private static ServiceStation serviceStation2 = new ServiceStation("service 2");


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
        //serviceStation1.getCars().add(car2);
        serviceStation2.setCars(new HashSet<Car>());
        serviceStation2.getCars().add(car1);
/*        car2.setServiceStations(new HashSet<ServiceStation>());
        car2.getServiceStations().add(serviceStation1);*/
    }

    @Test
    public void testCarsRelations_get() {
        session = HibernateUtils.getSession();
        session.beginTransaction();
        session.saveOrUpdate(car1);
        session.getTransaction().commit();

        logger.info("Inserted cars and stations");

        //session.clear();

/*        logger.info("Extracting car1 with id = " + car1.getId() + "...");
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
        logger.info("Done!");*/

        logger.info("Deleting cars and stations...");
        session.beginTransaction();
        session.delete(serviceStation1);
        session.delete(serviceStation2);
        session.getTransaction().commit();
        logger.info("Done!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        session.close();
    }

    @Test
    public void testCarsRelations_oooo() {
        session = HibernateUtils.getSession();
        session.beginTransaction();
        session.saveOrUpdate(car1);
        session.getTransaction().commit();

        logger.info("Inserted cars and stations");

        //session.clear();

/*        logger.info("Extracting car1 with id = " + car1.getId() + "...");
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
        logger.info("Done!");*/

        logger.info("Deleting cars and stations...");
        session.beginTransaction();
        session.delete(serviceStation1);
        session.delete(serviceStation2);
        session.getTransaction().commit();
        logger.info("Done!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        session.close();
    }

    @Ignore
    @Test
    public void testCarsRelations_update() {

        session = HibernateUtils.getSession();
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
        car2.getServiceStations().clear();
        car2.getServiceStations().add(serviceStation2);

        logger.info("Removing new service station...");

        logger.info("Updating car 2...");
        session.beginTransaction();
        session.update(car2);
        session.delete(serviceStation);
        session.getTransaction().commit();
        logger.info("Update complete!");

        assertEquals(size, car2.getServiceStations().size());
/*        session.clear();

        session.beginTransaction();
        Car extracted = (Car) session.get(Car.class, car2.getId());
        session.getTransaction().commit();

        assertEquals(size + 1, extracted.getServiceStations().size());*/

        logger.info("Deleting cars and stations...");
        session.beginTransaction();
        session.delete(car1);
        session.getTransaction().commit();
        logger.info("Deleted!");
        session.close();
    }

    @Ignore
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

/*        session.clear();

        session.beginTransaction();
        Car extracted = (Car) session.get(Car.class, serviceStation2.getId());
        session.getTransaction().commit();

        assertNull(extracted);

        session.beginTransaction();
        for (Car car : serviceStation1.getCars()) {
            car.getServiceStations().remove(serviceStation1);
            session.update(car);
        }
        session.delete(serviceStation1);
        session.getTransaction().commit();*/
    }

    public void closeSession() {
        session.close();
    }
}
