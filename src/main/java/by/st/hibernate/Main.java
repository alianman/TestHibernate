package by.st.hibernate;

import by.st.hibernate.model.Person;
import by.st.hibernate.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Administrator on 20.01.16.
 */
public class Main {

    public static void main(final String[] args) throws Exception {
/*        final Session session = HibernateUtils.getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Map metadataMap = session.getSessionFactory().getAllClassMetadata();
            for (Object key : metadataMap.keySet()) {
                final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
                final String entityName = classMetadata.getEntityName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }*/

        Session session = HibernateUtils.getSession();
        session.beginTransaction();

        Person person = new Person(0, "Test Name", "Test Adress");

        session.save(person);

        List<Person> persons = session.createQuery("from Person").list();
        for (Person person1 : persons) {
            System.out.println(person1.getId() + " , "
                    + person1.getName() + ", "
                    + person1.getAddress());
        }

        session.getTransaction().commit();
        session.close();
    }
}
