package by.st.hibernate.model;

import by.st.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.*;

import java.io.Console;

/**
 * Created by Administrator on 22.01.16.
 */

public class PersonTest extends Assert {

    private static Session session;
    private static Person testPerson;

    private static String testName = "Alexei Petkevich";
    private static String testAddress = "Minsk";

    @Before
    public void openSession() {
        try {
            session = HibernateUtils.getSession();
        } catch (Throwable e) {
            fail("failed to open session");
        }
    }

    @Before
    public void initResources() {
        testPerson = new Person(testName, testAddress);
    }

    @Test
    public void testGetPerson() {
        session.beginTransaction();
        session.save(testPerson);
        Person extractedPerson = (Person) session.get(Person.class, testPerson.getId());
        session.delete(testPerson);
        session.getTransaction().commit();
        String errorMessage = "testGetPerson failed: saved and extracted objects are not equal";
        if (extractedPerson == null) {
            fail(errorMessage);
        }
        assertEquals(errorMessage, testPerson, extractedPerson);
    }

    @Test
    public void testGetPerson_changedName() {
        session.beginTransaction();
        session.save(testPerson);
        testPerson.setName("test");
        session.getTransaction().commit();

        session.beginTransaction();
        Person extractedPerson = null;
        try {
            extractedPerson = (Person) session.get(Person.class, testPerson.getId());
            if (extractedPerson == null) {
                fail("testGetPerson_ChangedName failed: saved person not found in DB");
            }
        } finally {
            session.delete(testPerson);
            session.getTransaction().commit();
        }

        String errorMessage = "testGetPerson_ChangedName failed: saved and extracted objects are not equal";
        assertEquals(errorMessage, "test", extractedPerson.getName());
    }

    @Test
    public void testGetPerson_notFound() {
        session.beginTransaction();
        Person extractedPerson = (Person) session.get(Person.class, testPerson.getId());
        session.getTransaction().commit();
        assertEquals("testGetPerson_notFound failed: saved person found in DB", extractedPerson, null);
    }

    @Test
    public void testLoadPerson() {
        Person extractedPerson = null;
        String errorMessage = "testLoadPerson failed: saved and extracted objects";
        session.beginTransaction();
        session.save(testPerson);
        session.getTransaction().commit();

        session.beginTransaction();
        extractedPerson = (Person) session.load(Person.class, testPerson.getId());
        try {
            extractedPerson.setName("test");
        }
        catch (Throwable e) {
            fail("testLoadPerson failed: access exception wasn't thrown");
        }
        finally {
            session.delete(testPerson);
            session.getTransaction().commit();
        }
        assertEquals(errorMessage, testPerson, extractedPerson);
    }

    @Test
    public void testLoadPerson_notFound() {
        Person extractedPerson = null;

        session.beginTransaction();
        extractedPerson = (Person) session.load(Person.class, testPerson.getId());
        boolean isThrown = false;
        try {
            extractedPerson.setName("test");
        }
        catch (Throwable e) {
            isThrown = true;
        }
        session.delete(testPerson);
        session.getTransaction().commit();

        assertTrue("testLoadPerson_notFound failed: access exception wasn't thrown", isThrown);
    }

    @Test
    public void testSaveOrUpdatePerson() {
        session.beginTransaction();
        session.save(testPerson);
        session.getTransaction().commit();

        testPerson.setAddress("test");
        session.beginTransaction();
        session.saveOrUpdate(testPerson);
        session.getTransaction().commit();

        session.beginTransaction();
        Person extractedPerson = null;
        try {
            extractedPerson = (Person) session.get(Person.class, testPerson.getId());
            if (extractedPerson == null) {
                fail("testSaveOrUpdatePerson failed: saved object not found");
            }
        } finally {
            session.delete(extractedPerson);
            session.getTransaction().commit();
        }

        String errorMessage = "testSaveOrUpdatePerson failed: saved person and extracted are not equal";
        assertEquals(errorMessage, testPerson, extractedPerson);
    }

    @Test
    public void testFlushRefreshPerson() {
        session.save(testPerson);
        session.flush();
        testPerson.setAddress("test");
        session.refresh(testPerson);

        Person extractedPerson = (Person) session.get(Person.class, testPerson.getId());

        session.delete(testPerson);

        assertEquals(testAddress, extractedPerson.getAddress());
    }

    @Test
    public void testClearPerson() {
        session.save(testPerson);
        session.clear();
        testPerson.setAddress("test");

        Person extractedPerson = (Person) session.get(Person.class, testPerson.getId());

        session.delete(testPerson);

        assertNull(extractedPerson);
    }

    @After
    public void closeSession() {
        session.close();
    }
}
