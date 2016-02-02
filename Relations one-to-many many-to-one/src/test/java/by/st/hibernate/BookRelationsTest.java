package by.st.hibernate;

import by.st.hibernate.model.Book;
import by.st.hibernate.model.Person;
import by.st.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by alian on 01.02.2016.
 */
public class BookRelationsTest extends Assert {
    private static Session session;
    private static Person testPerson;
    private static Set<Book> testBooksList;

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
        testBooksList = new HashSet<Book>();
        for (int i = 0, size = 10; i < size; i++) {
            Book newBook = new Book("title " + (i + 1));
            newBook.setReader(testPerson);
            testBooksList.add(newBook);
        }
        testPerson.setBooks(testBooksList);
    }

    @Test
    public void testBooksRelation() {
        session.beginTransaction();
        session.save(testPerson);
        session.getTransaction().commit();

        session.beginTransaction();
        Person extracted = (Person) session.get(Person.class, testPerson.getId());
        session.getTransaction().commit();

        assertEquals(testPerson, extracted);
        assertFalse(extracted.getBooks().isEmpty());

        for (Book book : extracted.getBooks()) {
            System.out.println(book.getTitle() + " " + book.getReader().getName());
        }

        int size = extracted.getBooks().size();

        testPerson.getBooks().add(new Book("test", testPerson));

        session.beginTransaction();
        session.update(testPerson);
        session.getTransaction().commit();

        session.beginTransaction();
        extracted = (Person) session.get(Person.class, testPerson.getId());
        session.getTransaction().commit();

        assertEquals(size + 1, extracted.getBooks().size());
    }

    @After
    public void closeSession() {
        session.close();
    }
}
