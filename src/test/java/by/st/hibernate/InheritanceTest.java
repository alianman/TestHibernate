package by.st.hibernate;

import by.st.hibernate.model.Doctor;
import by.st.hibernate.model.Driver;
import by.st.hibernate.model.Person;
import by.st.hibernate.model.PhoneNumber;
import by.st.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 27.01.16.
 */
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
    public void testInheritance() {

        session.beginTransaction();
        session.save(testPerson);
        session.save(testDoctor);
        session.save(testDriver);

        session.getTransaction().commit();
    }

    @After
    public void closeSession() {
        session.close();
    }
}
