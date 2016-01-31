package by.st.hibernate.model;

import by.st.hibernate.utils.HibernateUtils;
import org.junit.Ignore;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 27.01.16.
 */

@Ignore
public class InheritanceTest extends Assert{
    private static Session session;
    private static Person testPerson;
    private static Doctor testDoctor;
    private static Driver testDriver;

    @Before
    public void openSession() {
        try {
            session = HibernateUtils.getSession();
        } catch (Throwable e) {
            fail("failed to open session: " + e.getMessage());
        }
    }

    @Before
    public void createPersons() {
        testPerson = new Person("Alexei Petkevich", "Minsk", new PhoneNumber("+375", "29", "1111111"));
        testDoctor = new Doctor("test doctor", "Vitebsk", new PhoneNumber("+375", "44", "2222222"), "therapy", "Regional hospital");
        testDriver = new Driver("test driver", "New York", new PhoneNumber("+1", "23", "2539821"), "111-1111", "Ford Focus");

    }

    @Test
    public void testInsertInheritance() {
        session.beginTransaction();
        session.save(testPerson);
        session.save(testDoctor);
        session.save(testDriver);
        session.getTransaction().commit();
    }

    @Test
    public void testDeleteInheritance() {
        session.beginTransaction();
        session.save(testPerson);
        session.save(testDoctor);
        session.save(testDriver);

        session.delete(testDoctor);
        session.getTransaction().commit();
    }

    @After
    public void closeSession() {
        session.close();
    }
}
