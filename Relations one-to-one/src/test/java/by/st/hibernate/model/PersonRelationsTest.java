package by.st.hibernate.model;

import by.st.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by alian on 31.01.2016.
 */
public class PersonRelationsTest extends Assert {
    private static Session session;
    private static Person testPerson;
    private static PersonPosition testPersonPosition;

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
        testPerson = new Person("Alexei Petkevich");
        testPersonPosition = new PersonPosition(1, 1);
        testPerson.setPosition(testPersonPosition);
        testPersonPosition.setPerson(testPerson);
    }

    @Test
    public void testPersonRelation() {
        session.beginTransaction();
        session.save(testPerson);
        session.getTransaction().commit();

        testPersonPosition.setX(1234.343);
        testPersonPosition.setY(75.1);

        session.beginTransaction();
        session.delete(testPerson);
        session.clear();
        session.getTransaction().commit();

        PersonPosition newPosition = new PersonPosition(23, 23);
        newPosition.setPerson(testPerson);
        testPerson.setPosition(newPosition);

        session.beginTransaction();
        session.save(newPosition);
        session.getTransaction().commit();

        session.beginTransaction();
        Person extracted = (Person) session.get(Person.class, testPerson.getId());
        session.getTransaction().commit();

        assertEquals("extracted and actual are not equal", testPerson, extracted);
    }

    @After
    public void closeSession() {
        session.close();
    }
}
