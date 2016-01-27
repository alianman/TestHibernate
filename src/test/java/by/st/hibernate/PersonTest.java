package by.st.hibernate;

import by.st.hibernate.model.Person;
import by.st.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.*;

/**
 * Created by Administrator on 22.01.16.
 */
@Ignore
public class PersonTest extends Assert {

    private static Session session;

    @Before
    public void openSession() {
        try {
            session = HibernateUtils.getSession();
        } catch (Throwable e) {
            fail("failed to open session");
        }
    }

    @Test
    public void testGetPerson() {
        Person testPerson = new Person("Alexei Petkevich", "Minsk");
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
        assertEquals(errorMessage, "Alexei Petkevich", testPerson.getName());
        assertEquals(errorMessage, "Minsk", testPerson.getAddress());
        assertEquals(errorMessage, "Alexei Petkevich", extractedPerson.getName());
        assertEquals(errorMessage, "Minsk", extractedPerson.getAddress());
    }

    @Test
    public void testGetPerson_ChangedName() {
        Person testPerson = new Person("Alexei Petkevich", "Minsk");
        session.beginTransaction();
        session.save(testPerson);
        testPerson.setName("test");
        Person extractedPerson = (Person) session.get(Person.class, testPerson.getId());
        session.delete(testPerson);
        session.getTransaction().commit();
        String errorMessage = "testGetPerson_ChangedName failed: saved and extracted objects are not equal";
        if (extractedPerson == null) {
            fail(errorMessage);
        }
        assertEquals(errorMessage, testPerson, extractedPerson);
        assertEquals(errorMessage, "test", extractedPerson.getName());
    }

    @Test
    public void testLoadPerson() {
        Person testPerson = new Person("Alexei Petkevich", "Minsk");
        Person extractedPerson = null;
        String errorMessage = "testLoadPerson failed: saved and extracted objects are not equal";
        session.beginTransaction();
        //session.save(testPerson);
        extractedPerson = (Person) session.load(Person.class, testPerson.getId());
        session.delete(testPerson);
        session.getTransaction().commit();
        String test = extractedPerson.getName();
        assertEquals(errorMessage, testPerson, extractedPerson);
        assertEquals(errorMessage, "Alexei Petkevich", testPerson.getName());
        assertEquals(errorMessage, "Minsk", testPerson.getAddress());
        assertEquals(errorMessage, "Alexei Petkevich", extractedPerson.getName());
        assertEquals(errorMessage, "Minsk", extractedPerson.getAddress());
    }

    @Test
    public void testSaveOrUpdatePerson() {
        Person testPerson = new Person("Alexei Petkevich", "Minsk");
        session.beginTransaction();
        session.save(testPerson);
        testPerson.setAddress("testAddress");
        session.getTransaction().commit();
        String errorMessage = "testSaveOrUpdatePerson failed: address error";
        assertEquals(errorMessage, "testAddress", testPerson.getAddress());
    }

    @After
    public void closeSession() {
        session.close();
    }
}
