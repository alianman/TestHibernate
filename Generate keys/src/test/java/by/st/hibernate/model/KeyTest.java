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
public class KeyTest extends Assert {
    private static Session session;
    private static AssignedKeyTestEntity assignedKeyTestEntity;
    private static NativeKeyTestEntity nativeKeyTestEntity;
    private static UuidKeyTestEntity uuidKeyTestEntity;

    private static long assignedId = 9999;

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
        String testName = "Alexei Petkevich";
        String testAddress = "Minsk";
        assignedKeyTestEntity = new AssignedKeyTestEntity(assignedId, testName, testAddress);
        nativeKeyTestEntity = new NativeKeyTestEntity(testName, testAddress);
        uuidKeyTestEntity = new UuidKeyTestEntity(testName, testAddress);
    }

    @Test
    public void testAssignedKey() {
        session.save(assignedKeyTestEntity);
        AssignedKeyTestEntity extracted = (AssignedKeyTestEntity) session.get(AssignedKeyTestEntity.class, assignedId);
        System.out.println("testAssignedKey new id: " + extracted.getId());
        assertEquals(extracted.getId(), assignedId);
    }

    @Test
    public void testNativeKey() {
        session.save(nativeKeyTestEntity);
        System.out.println("testNativeKey new id: " + nativeKeyTestEntity.getId());
    }

    @Test
    public void testUuidKey() {
        session.save(uuidKeyTestEntity);
        System.out.println("testUuidKey new id: " + uuidKeyTestEntity.getId());
    }

    @After
    public void closeSession() {
        session.close();
    }
}
